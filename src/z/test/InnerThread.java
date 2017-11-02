package z.test;

import java.io.IOException;

import mochi.tool.net.socket.KeepAliveSocket;

public class InnerThread implements Runnable {

	private KeepAliveSocket kas;
	
	public InnerThread(KeepAliveSocket kas) {
		this.kas = kas;
	}
	
	@Override
	public void run() {
		for(int i = 0; i < 100000; i++) {
			try {
				kas.fire(new BuletM());
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (i%10000 == 0) {
				System.out.println(i);
			}
		}
	}

}
