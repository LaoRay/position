package com.clubank.position.jt808.server;

import com.clubank.position.common.util.PropertyUtil;

//@Component
public class StartServer {

	public StartServer() {
		TCPServer server = new TCPServer(Integer.parseInt(PropertyUtil.getProperty("tcp.server.port")));
		server.startServer();
	}
}
