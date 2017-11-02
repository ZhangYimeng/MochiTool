package z.test;

import java.io.IOException;

import mochi.tool.net.server.socket.KeepAliveServerSocket;

public class MochiSocketServerTest {

	public static void main(String[] args) throws IOException {
		KeepAliveServerSocket kass = new KeepAliveServerSocket(7715, new MochiSocketServerTestHandler());
		kass.start();
	}

}
