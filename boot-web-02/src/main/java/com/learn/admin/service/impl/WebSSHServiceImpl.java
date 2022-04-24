package com.learn.admin.service.impl;

import com.learn.admin.constant.Constant;
import com.learn.admin.util.JsonUtil;
import com.learn.admin.util.RSAUtil;
import com.learn.admin.bean.ConnectInfo;
import com.learn.admin.bean.MessageData;
import com.learn.admin.service.WebSSHService;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class WebSSHServiceImpl implements WebSSHService {

    // ssh连接池
    private static Map<String, ConnectInfo> sshMap = new ConcurrentHashMap<>(50);

    /**
     * 处理发送消息线程池
     * 未做定制化处理
     */
    private ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    public void connection(WebSocketSession session) {
        JSch jSch = new JSch();
        ConnectInfo connectInfo = new ConnectInfo();
        connectInfo.setJSch(jSch);
        connectInfo.setWebSocketSession(session);
        String userId = getUserId(session);
        sshMap.put(userId,connectInfo);
    }

    @Override
    public void clientMessageHandle(String payload, WebSocketSession session) {
        log.info("clientMessageHandle payload:{}", payload);
        if(!StringUtils.hasText(payload)){
            return;
        }

        MessageData messageData = JsonUtil.string2Obj(payload, MessageData.class);
        if("Y".equals(messageData.getEncrypt())){
            String username = new String(RSAUtil.decryptByPrivateKey(Base64.decodeBase64(messageData.getUsername()), RSAUtil.privateKey));
            String password = new String(RSAUtil.decryptByPrivateKey(Base64.decodeBase64(messageData.getPassword()), RSAUtil.privateKey));
            if(!StringUtils.hasText(username)){
                return;
            }
            messageData.setUsername(username);
            messageData.setPassword(password);
        }
        String userId = getUserId(session);
        ConnectInfo connectInfo = sshMap.get(userId);
        if(Objects.isNull(connectInfo)){
            return;
        }
        // 登录ssh操作
        if(Constant.WEBSSH_OPERATE_CONNECT.equals(messageData.getOperate())){
            // 获取ssh连接信息
            final MessageData finalMsg = messageData;
            executorService.execute(() -> {
                try {
                    connectToSSH(connectInfo, finalMsg, session);
                } catch (JSchException | IOException e) {
                    try {
                        String errorMsg = "401" + e.getMessage();
                        sendMessageToClient(session,errorMsg.getBytes(StandardCharsets.UTF_8));
                    } catch (IOException ex) {
                        log.error("发送数据异常:{}", e.getMessage(), e);
                    }
                    log.error("webssh连接异常:{}", e.getMessage(), e);
                    close(session);
                }
            });
            return;
        }

        // 执行ssh命令
        if(Constant.WEBSSH_OPERATE_COMMAND.equals(messageData.getOperate())){
            if(Objects.nonNull(connectInfo)){
                try {
                    transToSSH(connectInfo.getChannel(), messageData.getCommand());
                } catch (IOException e) {
                    log.error("webssh连接异常:{}", e.getMessage(), e);
                    close(session);
                }
            }
            return;
        }

        // 调整终端窗口大小
        if(Constant.WEBSSH_OPERATE_RESIZE.equals(messageData.getOperate())){
            try {
                resizeCommand(connectInfo.getChannel(), messageData);
            }catch (IOException e){
                log.error("webssh连接异常:{}", e.getMessage(), e);
                close(session);
            }
            return;
        }

        // 不支持的操作类型
        log.error("不支持的操作类型:{}", messageData.getOperate());
        close(session);
    }

    @Override
    public void sendMessageToClient(WebSocketSession session, byte[] buffer) throws IOException {
        session.sendMessage(new TextMessage(buffer));
    }

    @Override
    public void close(WebSocketSession session) {
        String userId = getUserId(session);
        ConnectInfo connectInfo = sshMap.get(userId);
        if (connectInfo != null) {
            //断开连接
            if (connectInfo.getChannel() != null) {
                connectInfo.getChannel().disconnect();
            }
            //map中移除
            sshMap.remove(userId);
        }
    }

    /**
     * 获取用户id
     * @param session
     * @return
     */
    public String getUserId(WebSocketSession session){
        if(Objects.isNull(session)){
            return null;
        }
        return (String) session.getAttributes().get(Constant.USER_UUID_KEY);
    }

    private void connectToSSH(ConnectInfo connectInfo, MessageData messageData, WebSocketSession webSocketSession) throws JSchException, IOException {
        Properties config = new Properties();
        // 不安全
        config.put("StrictHostKeyChecking", "no");
        //获取jsch的会话
        Session session = connectInfo.getJSch().getSession(messageData.getUsername(), messageData.getHost(), messageData.getPort());
        session.setConfig(config);
        //设置密码
        session.setPassword(messageData.getPassword());
        //连接  超时时间30s
        session.connect(30000);
        //开启shell通道
        Channel channel = session.openChannel("shell");
        //通道连接 超时时间3s
        channel.connect(3000);
        //设置channel
        connectInfo.setChannel(channel);

        //设置终端窗口大小
        resizeCommand(channel, messageData);

        //读取终端返回的信息流
        InputStream inputStream = channel.getInputStream();
        try {
            //循环读取
            byte[] buffer = new byte[1024];
            int i = 0;
            //如果没有数据来，线程会一直阻塞在这个地方等待数据。
            while ((i = inputStream.read(buffer)) != -1) {
                sendMessageToClient(webSocketSession, Arrays.copyOfRange(buffer, 0, i));
            }
        } finally {
            //断开连接后关闭会话
            session.disconnect();
            channel.disconnect();
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    private void transToSSH(Channel channel, String command) throws IOException {
        if (channel != null) {
            OutputStream outputStream = channel.getOutputStream();
            outputStream.write(command.getBytes());
            outputStream.flush();
        }
    }

    /**
     * 设置终端窗口大小
     * @param channel
     * @param messageData
     * @throws IOException
     */
    private void resizeCommand(Channel channel, MessageData messageData) throws IOException {
        String command = "stty cols ";
        if(StringUtils.hasText(messageData.getCols())){
            command = command + messageData.getCols();

            if(StringUtils.hasText(messageData.getRows())){
                command = command + " rows " + messageData.getRows();
            }
            command = command + " \r";
        }
        transToSSH(channel, command);
        transToSSH(channel,"clear \r");
    }
}
