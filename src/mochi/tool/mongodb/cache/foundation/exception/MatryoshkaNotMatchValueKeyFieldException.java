package mochi.tool.mongodb.cache.foundation.exception;

public class MatryoshkaNotMatchValueKeyFieldException extends Exception {

	private static final long serialVersionUID = -5471086180427408517L;
	private static final String info = "Matryoshka声明的域与提供的Values不匹配。";
	
	public MatryoshkaNotMatchValueKeyFieldException() {
		super(info);
	}
	
}
