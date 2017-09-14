package mochi.tool.mongodb.cache.foundation.exception;

public class ValueNullException extends Exception {

	private static final long serialVersionUID = -6795173314373092716L;
	private static final String info = "Key-Value peer的Value为null。";
	
	public ValueNullException() {
		super(info);
	}
	
	public ValueNullException(String info) {
		super(info);
	}

}
