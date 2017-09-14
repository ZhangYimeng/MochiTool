package mochi.tool.module.iotplatform.foundation.exception;

public class DuplicatedDeviceIDException extends Exception{
	
	private static final long serialVersionUID = -4183366782479269966L;

	public DuplicatedDeviceIDException(String msg) {
		super(msg);
	}
	
	public DuplicatedDeviceIDException() {
		super();
	}

}
