package z.test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class 服务端2 {

	public 服务端2(String tansferIp, int port) throws IOException {
		@SuppressWarnings("resource")
		ServerSocket ss = new ServerSocket(port);
		while(true) {
			Socket s = ss.accept();
			Thread handler = new Thread(new 服务端2服务线程(tansferIp, s));
			handler.start();
		}
	}
	
}
