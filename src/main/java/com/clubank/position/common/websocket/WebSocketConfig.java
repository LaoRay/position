package com.clubank.position.common.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// 1.注册WebSocket
		registry.addHandler(webSocketHandler(), "/webSocketServer").addInterceptors(new WebSocketHandshakeInterceptor())
				.setAllowedOrigins("*");
		// 2.注册SockJS，提供SockJS支持(主要是兼容ie8)
		registry.addHandler(webSocketHandler(), "/sockjs/webSocketServer")
				.addInterceptors(new WebSocketHandshakeInterceptor()).withSockJS();
	}

	@Bean
	public TextWebSocketHandler webSocketHandler() {
		return new WebSocketHandler();
	}
}
