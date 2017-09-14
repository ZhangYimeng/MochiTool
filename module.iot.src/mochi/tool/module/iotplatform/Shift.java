package mochi.tool.module.iotplatform;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Shift {

	private static ServerSocket serverSocket;
	
	static {
		
	}
	
	public static void start() throws IOException {
		serverSocket = new ServerSocket(ShiftConfig.PORT);
		while(true) {
			Socket socket = serverSocket.accept();
			RequestAcceptThread rat = new RequestAcceptThread(socket);
			rat.start();
		}
	}
	
}
