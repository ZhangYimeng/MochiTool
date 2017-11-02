package mochi.tool.distributed.tolerance;

import java.io.IOException;

import mochi.tool.net.server.socket.KeepAliveServerSocket;

public class MasterServer {

	private KeepAliveServerSocket kass;
	
	public MasterServer(int port) throws IOException {
		kass = new KeepAliveServerSocket(port, null);
	}
	
	public void doBusiness() {
		kass.start();
	}
	
}
