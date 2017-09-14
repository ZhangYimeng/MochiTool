package mochi.tool.module.iotplatform.foundation;

import mochi.tool.module.iotplatform.foundation.mongodbtool.DBResultList;
import mochi.tool.module.iotplatform.foundation.mongodbtool.MongoDBConfig;

public class DataSourceDevicesList {

private DBResultList dbr;
	
	public DataSourceDevicesList(DBResultList dbr) {
		this.dbr = dbr;
	}
	
	public boolean hasNextDevice() {
		return dbr.hasNext();
	}
	
	public void nextDevice() {
		dbr.next();
	}
	
	public String getCurrentDataSourceJSONInfo() {
		return dbr.getCurrentDBObject().toString();
	}
	
	public String getCurrentDataSourceTag() {
		return dbr.getData(MongoDBConfig.SOURCE_LIST_KEYS.SOURCE_TAG).toString();
	}
	
	public String getCurrentDataSourceName() {
		return dbr.getData(MongoDBConfig.SOURCE_LIST_KEYS.SOURCE_NAME).toString();
	}
	
	public String getCurrentDataSourceDescription() {
		return dbr.getData(MongoDBConfig.SOURCE_LIST_KEYS.SOURCE_DESCRIPTION).toString();
	}
	
	public String getCurrentDataSourceLocation() {
		return dbr.getData(MongoDBConfig.SOURCE_LIST_KEYS.SOURCE_LOCATION).toString();
	}
	
	public String getCurrentDataSourceOwner() {
		return dbr.getData(MongoDBConfig.SOURCE_LIST_KEYS.SOURCE_OWNER).toString();
	}
	
	public String getCurrentDataSourceProperty() {
		return dbr.getData(MongoDBConfig.SOURCE_LIST_KEYS.SOURCE_PROPERTY).toString();
	}
	
	public String getCurrentDataSourceDiagram() {
		return dbr.getData(MongoDBConfig.SOURCE_LIST_KEYS.SOURCE_DIAGRAM).toString();
	}
	
}
