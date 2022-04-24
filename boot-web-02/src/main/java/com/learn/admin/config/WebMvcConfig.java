package com.learn.admin.config;

import com.learn.admin.websocket.WebSSHWebSocketHandler;
import com.learn.admin.interceptor.WebSocketInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * 配置类
 */
@Configuration
@EnableWebSocket
public class WebMvcConfig implements WebSocketConfigurer {

    @Autowired
    private WebSSHWebSocketHandler webSSHWebSocketHandler;

    @Autowired
    private WebSocketInterceptor webSocketInterceptor;

    @Value("/webssh")
    private String wsPath;

    @Value("*")
    private String wsAllowOrigins;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSSHWebSocketHandler, wsPath)
                .addInterceptors(webSocketInterceptor)
                .setAllowedOrigins(wsAllowOrigins);
    }
}
