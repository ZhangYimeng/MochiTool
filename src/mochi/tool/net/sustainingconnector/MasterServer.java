package mochi.tool.net.sustainingconnector;

import java.io.IOException;
import java.net.ServerSocket;

import mochi.tool.net.sustainingconnector.foundation.MasterServerListenerThread;

public class MasterServer {

	private ServerSocket ss;
	private MasterServerListenerThread mslt;
	private String nameOfDataHandler;
	
	/**
	 * @param port 将要打开的端口号。
	 * @param nameOfDataHandler DataHandlerInterface实现类的名称，该名称包含类的package信息，如String类的名称为java.lang.String。
	 */
	public MasterServer(int port, String nameOfDataHandler) {
		try {
			ss = new ServerSocket(port);
			this.nameOfDataHandler = nameOfDataHandler;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void start() {
		mslt = new MasterServerListenerThread(ss, nameOfDataHandler);
		Thread it = new Thread(mslt);
		it.start();
	}
	
}
