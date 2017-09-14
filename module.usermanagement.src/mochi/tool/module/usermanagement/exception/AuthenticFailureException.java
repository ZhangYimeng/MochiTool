package mochi.tool.module.usermanagement.exception;

public class AuthenticFailureException extends Exception {

	private static final long serialVersionUID = 2596551771273609213L;
	
	public AuthenticFailureException() {
		super();
	}
	
	public AuthenticFailureException(String s) {
		super(s);
	}

}
