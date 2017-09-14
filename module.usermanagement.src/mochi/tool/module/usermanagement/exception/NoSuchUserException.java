package mochi.tool.module.usermanagement.exception;

public class NoSuchUserException extends Exception{

	private static final long serialVersionUID = 1069098528524659346L;
	
	public NoSuchUserException() {
		super();
	}
	
	public NoSuchUserException(String s) {
		super(s);
	}

}
