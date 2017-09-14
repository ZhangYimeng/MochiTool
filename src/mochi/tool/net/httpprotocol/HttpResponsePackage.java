package mochi.tool.net.httpprotocol;

public class HttpResponsePackage {

	private byte[] responseBytes;
	
	public HttpResponsePackage(byte[] response) {
		this.responseBytes = response;
	}
	
	public byte[] getResposeBytes() {
		return this.responseBytes;
	}
	
}
