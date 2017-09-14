package mochi.tool.module.iotplatform.foundation.mongodbtool;

import mochi.tool.module.iotplatform.foundation.mongodbtool.exception.DBObjectNullPointerException;

import com.mongodb.DBObject;

public class DBResult {

	private DBObject result;
	
	public DBResult(DBObject result) throws DBObjectNullPointerException {
		if(result != null) {
			this.result = result;
		} else {
			throw new DBObjectNullPointerException();
		}
	}
	
	public Object getData(String key) {
		return result.get(key);
	}
	
	@Override
	public String toString() {
		return result.toString();
	}
	
}
