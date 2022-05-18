package com.learn.admin.service;

import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * webssh处理类
 */
public interface WebSSHService {

    /**
     * ssh初始化连接
     * @param session websocket当前会话数据，主要是获取登录用户信息
     */
    void connection(WebSocketSession session);

    /**
     * 处理客户端发的数据
     * @param payload 消息数据
     * @param session websocket连接会话数据
     */
     void clientMessageHandle(String payload, WebSocketSession session);

    /**
     * 发送数据给客户端
     * @param session 当前登录用户
     * @param buffer 发送的数据
     * @throws IOException
     */
     void sendMessageToClient(WebSocketSession session, byte[] buffer) throws IOException;

    /**
     * 关闭websocket连接
     * @param session
     */
    void close(WebSocketSession session);
}
