package z.test;

import java.io.IOException;
import java.net.UnknownHostException;

import mochi.tool.net.socket.KeepAliveSocket;

public class SomeTest {

	public static void main(String[] args) throws UnknownHostException, IOException {
		KeepAliveSocket kas = new KeepAliveSocket("192.168.1.7", 3000);
		kas.fire(new BuletM());
	}

}
