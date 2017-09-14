package mochi.tool.net.httpprotocol.exception;

public class HttpRequestHeaderNoHostException extends Exception {

	private static final long serialVersionUID = 6373236356157832179L;
	
	public HttpRequestHeaderNoHostException() {
		super();
	}
	
	public HttpRequestHeaderNoHostException(String s) {
		super(s);
	}

}
