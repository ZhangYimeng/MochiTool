package z.test;

import java.io.IOException;
import java.net.UnknownHostException;

import mochi.tool.net.socket.KeepAliveSocket;

public class ClientQ {

	public static void main(String[] args) throws UnknownHostException, IOException {
		KeepAliveSocket kas1 = new KeepAliveSocket("192.168.137.1", 7715);
		KeepAliveSocket kas2 = new KeepAliveSocket("192.168.137.1", 7715);
		new Thread(new InnerThread(kas1)).start();
//		new Thread(new InnerThread(kas2)).start();
	}

}
