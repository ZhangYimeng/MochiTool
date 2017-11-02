package z.test;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class 客户端2 {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		Socket s = new Socket("192.168.1.36", 8888);
		DataOutputStream dout = new DataOutputStream(s.getOutputStream());
		while(true) {
			dout.writeUTF("Hello Server!");
			Thread.sleep(1000);
		}
	}

}
