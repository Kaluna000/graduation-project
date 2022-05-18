package com.learn.admin.websocket;

import com.learn.admin.constant.Constant;
import com.learn.admin.service.WebSSHService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.util.Objects;

/**
 * websocket处理类
 * 处理连接、关闭、发送消息、异常等
 */
@Slf4j
@Component
public class WebSSHWebSocketHandler implements WebSocketHandler {

    @Autowired
    private WebSSHService webSSHService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("用户：{}，连接ssh", getUserId(session));
        webSSHService.connection(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String userId = getUserId(session);
        // 目前仅处理文本消息
        if(message instanceof TextMessage){
            TextMessage textMessage = (TextMessage) message;
            log.info("用户:{},发送命令:{}", userId, textMessage);
            String payload = textMessage.getPayload();
            webSSHService.clientMessageHandle(payload, session);
        }else{
            log.error("客户端发送非文本消息:{}",message);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("发送数据失败:{}",exception.getMessage(),exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.info("用户：{} 断开连接", getUserId(session));
        webSSHService.close(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
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
}
