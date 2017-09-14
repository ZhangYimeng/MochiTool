package mochi.tool.net.httpprotocol.exception;

/**
 * @author saito
 *
 */
public class HttpRequestLineNoMethodException extends Exception {

	private static final long serialVersionUID = 9118719758979764612L;
	
	public HttpRequestLineNoMethodException() {
		super();
	}
	
	public HttpRequestLineNoMethodException(String s) {
		super(s);
	}

}
