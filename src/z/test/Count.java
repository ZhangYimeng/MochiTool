package z.test;

import mochi.tool.net.server.socket.SocketHandlerInterface;

public class Count implements SocketHandlerInterface {

	private int i = 0;
	
	@Override
	public byte[] handle(byte[] data) {
		if(i%10000 == 0) {
			System.out.println(System.currentTimeMillis());
		}
		i++;
		return new byte[2];
	}

	@Override
	public void whenConnectionClose() {
		
	}

}
