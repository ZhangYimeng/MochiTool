package z.test;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class 服务端2服务线程 implements Runnable {
	
	private Socket s;
	private DataInputStream din;
	
	public 服务端2服务线程(String tansferIp, Socket s) throws IOException {
		this.s = s;
		din = new DataInputStream(s.getInputStream());
		Thread t = new Thread(new 服务端2服务线程线程(tansferIp, this.s));
		t.start();
	}

	@Override
	public void run() {
		while (true) {
			try {
				System.out.println(din.readUTF());
				System.out.println("读取完毕!");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
