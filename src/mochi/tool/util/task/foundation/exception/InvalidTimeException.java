package mochi.tool.util.task.foundation.exception;

public class InvalidTimeException extends Exception {

	private static final long serialVersionUID = -2996720016702348214L;
	private static final String info = "检查时间是否有效！";
	
	public InvalidTimeException() {
		super(info);
	}

}
