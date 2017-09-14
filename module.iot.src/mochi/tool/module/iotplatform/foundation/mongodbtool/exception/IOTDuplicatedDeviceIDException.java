package mochi.tool.module.iotplatform.foundation.mongodbtool.exception;

public class IOTDuplicatedDeviceIDException extends Exception{
	
	private static final long serialVersionUID = -4183366782479269966L;

	public IOTDuplicatedDeviceIDException(String msg) {
		super(msg);
	}
	
	public IOTDuplicatedDeviceIDException() {
		super();
	}

}
