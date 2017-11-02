package z.test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class 服务端2Socket接收器 {

	@SuppressWarnings("resource")
	public 服务端2Socket接收器(String transferIp, int port) throws IOException {
		ServerSocket ss = new ServerSocket(port);
		while(true) {
			Socket s = ss.accept();
			Thread handler = new Thread(new 服务端2Socket接收器服务线程(transferIp, s));
			handler.start();
		}
	}

}
