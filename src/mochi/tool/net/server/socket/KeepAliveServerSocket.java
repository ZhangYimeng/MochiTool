package mochi.tool.net.server.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class KeepAliveServerSocket implements Runnable {

	private ServerSocket server;
	private SocketHandlerInterface handler;

	public KeepAliveServerSocket(int port, SocketHandlerInterface handler) throws IOException {
		server = new ServerSocket(port);
		this.handler = handler;
	}
	
	public void start() {
		new Thread(this).start();
	}

	@Override
	public void run() {
		while(true) {
			try {
				Socket socket = server.accept();
				SocketHandler sh = new SocketHandler(socket, handler);
				sh.doBusiness();
			} catch (IOException e) {
				System.err.println("连接创建失败!");
				e.printStackTrace();
			}
		}
	}

}
