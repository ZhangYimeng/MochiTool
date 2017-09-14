package mochi.tool.module.iotplatform.foundation.mongodbtool;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class DBResultList {
	
	private DBCursor cursor;
	private DBObject currentResult;
	
	public DBResultList(DBCursor cursor) {
		this.cursor = cursor;
	}
	
	public boolean hasNext() {
		return cursor.hasNext();
	}
	
	public void next() {
		currentResult = cursor.next();
	}
	
	public DBObject getCurrentDBObject() {
		return currentResult;
	}
	
	public Object getData(String key) {
		return currentResult.get(key);
	}
	
	public int count() {
		return cursor.count();
	}
	
}
