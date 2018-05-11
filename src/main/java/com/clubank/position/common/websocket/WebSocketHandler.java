package com.clubank.position.common.websocket;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class WebSocketHandler extends TextWebSocketHandler {

	private static CopyOnWriteArraySet<WebSocketSession> sessionSet = new CopyOnWriteArraySet<>();

	/**
	 * 处理前端发送的文本信息 js调用websocket.send时候，会调用该方法
	 * 
	 * @param session
	 * @param message
	 * @throws Exception
	 */
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String userName = (String) session.getAttributes().get("websocket_name");
		// 获取提交过来的消息详情
		log.debug("收到用户 " + userName + "的消息:" + message.toString());
		// 回复一条信息
		session.sendMessage(new TextMessage("reply msg:" + message.getPayload()));
	}

	/**
	 * 当新连接建立的时候，被调用 连接成功时候，会触发页面上onOpen方法
	 * 
	 * @param session
	 * @throws Exception
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessionSet.add(session);
		String userName = (String) session.getAttributes().get("websocket_name");
		log.info("用户 " + userName + " Connection Established");
	}

	/**
	 * 当连接关闭时被调用
	 * 
	 * @param session
	 * @param status
	 * @throws Exception
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		String userName = (String) session.getAttributes().get("websocket_name");
		log.info("用户 " + userName + " Connection closed. Status: " + status);
		sessionSet.remove(session);
	}

	/**
	 * 传输错误时调用
	 * 
	 * @param session
	 * @param exception
	 * @throws Exception
	 */
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		String userName = (String) session.getAttributes().get("websocket_name");
		if (session.isOpen()) {
			session.close();
		}
		log.debug("用户: " + userName + " websocket connection closed......");
		sessionSet.remove(session);
	}

	/**
	 * 给所有在线用户发送消息
	 * 
	 * @param message
	 */
	public void sendMessageToUsers(TextMessage message) {
		for (WebSocketSession session : sessionSet) {
			try {
				if (session.isOpen()) {
					session.sendMessage(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 给某个用户发送消息
	 * 
	 * @param userName
	 * @param message
	 */
	public void sendMessageToUser(String userName, TextMessage message) {
		for (WebSocketSession session : sessionSet) {
			if (session.getAttributes().get("websocket_name").equals(userName)) {
				try {
					if (session.isOpen()) {
						session.sendMessage(message);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				// break;
			}
		}
	}
}
