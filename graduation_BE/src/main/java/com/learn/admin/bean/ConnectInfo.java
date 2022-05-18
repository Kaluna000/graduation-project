package com.learn.admin.bean;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

/**
 * ssh连接信息
 */
@Data
public class ConnectInfo {

    /**
     * websocket当前连接
     * 用于发送消息
     */
    private WebSocketSession webSocketSession;

    /**
     * ssh当前连接信息
     * 用于获取ssh连接信息
     */
    private JSch jSch;

    /**
     * ssh当前连接通道
     * 用于获取ssh执行命令返回数据
     */
    private Channel channel;
}
