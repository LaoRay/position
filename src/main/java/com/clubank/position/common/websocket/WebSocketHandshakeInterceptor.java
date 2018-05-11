package com.clubank.position.common.websocket;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
			HttpServletRequest req = serverRequest.getServletRequest();
			// 使用websocket_name区分WebSocketHandler，以便定向发送消息
			String websocketName = req.getHeader("websocket_name");
			if (StringUtils.isBlank(websocketName)) {
				websocketName = "default_websocket_name";
			}
			attributes.put("websocket_name", websocketName);
		}
		return true;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
		log.info("afterHandshake, websocket connect");
	}
}
