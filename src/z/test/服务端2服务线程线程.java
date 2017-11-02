package z.test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class 服务端2服务线程线程 implements Runnable {

	private Socket s;
	private Socket ss;
	private ObjectOutputStream oout;
	private ObjectInputStream oin;
	
	public 服务端2服务线程线程(String ip, Socket s) {
		this.s = s;
		try {
			ss = new Socket(ip, 8887);
			oout = new ObjectOutputStream(ss.getOutputStream());
			oin = new ObjectInputStream(ss.getInputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			oout.writeObject(s);
			RedundantInfo info = (RedundantInfo) oin.readObject();
			oout.close();
			oin.close();
			info.info();
			ss.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
