package backup.com.sun.net.httpserver;

/**
 * A Http error
 */
class HttpError extends RuntimeException {

	private static final long serialVersionUID = 6308056058863047512L;

	public HttpError (String msg) {
	super (msg);
    }
}
