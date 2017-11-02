package mochi.tool.net.server.socket;

public interface SocketHandlerInterface {
	
	public byte[] handle(byte[] data);
	
	public void whenConnectionClose();
	
}
