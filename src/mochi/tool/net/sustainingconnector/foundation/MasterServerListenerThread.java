package mochi.tool.net.sustainingconnector.foundation;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MasterServerListenerThread implements Runnable {

	private ServerSocket ss;
	private String nameOfDataHandler;
	
	public MasterServerListenerThread(ServerSocket ss, String nameOfDataHandler) {
		this.ss = ss;
		this.nameOfDataHandler = nameOfDataHandler;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				Socket s = ss.accept();
				Thread it = new Thread(new DataComingHandlerThread(s, nameOfDataHandler));
				it.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
