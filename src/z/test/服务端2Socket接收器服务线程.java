package z.test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class 服务端2Socket接收器服务线程 implements Runnable {

	private Socket s;
	private String transferIp;
	private ObjectInputStream oin;
	private ObjectOutputStream oout;
	
	public 服务端2Socket接收器服务线程(String transferIp, Socket s) throws IOException {
		this.s = s;
		oin = new ObjectInputStream(s.getInputStream());
		oout = new ObjectOutputStream(s.getOutputStream());
	}
	
	@Override
	public void run() {
		try {
			Socket ins = (Socket) oin.readObject();
			Thread it = new Thread(new 服务端2服务线程(transferIp, ins));
			it.start();
			oout.writeObject(new RedundantInfo());
			oin.close();
			oout.close();
			s.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
