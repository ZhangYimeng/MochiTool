package mochi.tool.module.iotplatform.foundation.mongodbtool.exception;

public class DBCollectionNotExistException extends Exception {

	private static final long serialVersionUID = 3604668938447792121L;

	public DBCollectionNotExistException() {
		super();
	}
	
	public DBCollectionNotExistException(String s) {
		super(s);
	}
	
}
