package mochi.tool.module.iotplatform.foundation;

import mochi.tool.module.iotplatform.foundation.mongodbtool.DBResultList;
import mochi.tool.module.iotplatform.foundation.mongodbtool.MongoDBConfig;

public class GatewayDevicesList {
	
	private DBResultList dbr;
	
	public GatewayDevicesList(DBResultList dbr) {
		this.dbr = dbr;
	}
	
	public boolean hasNextDevice() {
		return dbr.hasNext();
	}
	
	public void nextDevice() {
		dbr.next();
	}
	
	public String getCurrentDeviceID() {
		return dbr.getData(MongoDBConfig.DEVICES_LIST_KEYS.DEVICE_ID).toString();
	}
	
	public String getCurrentDeviceName() {
		return dbr.getData(MongoDBConfig.DEVICES_LIST_KEYS.DEVICE_NAME).toString();
	}
	
	public String getCurrentDeviceDescription() {
		return dbr.getData(MongoDBConfig.DEVICES_LIST_KEYS.DEVICE_DESCRIPTION).toString();
	}
	
	public String getCurrentDeviceLocation() {
		return dbr.getData(MongoDBConfig.DEVICES_LIST_KEYS.DEVICE_LOCATION).toString();
	}
	
	public String getCurrentDeviceWriteType() {
		return dbr.getData(MongoDBConfig.DEVICES_LIST_KEYS.DEVICE_WRITE_TYPE).toString();
	}
	
	public String getCurrentDeviceFixIPFlag() {
		return dbr.getData(MongoDBConfig.DEVICES_LIST_KEYS.DEVICE_FIX_IP_FLAG).toString();
	}
	
	public String getCurrentDeviceFixIP() {
		return (String) dbr.getData(MongoDBConfig.DEVICES_LIST_KEYS.DEVICE_FIX_IP);
	}
	
	public String getCurrentDeviceType() {
		return dbr.getData(MongoDBConfig.DEVICES_LIST_KEYS.DEVICE_TYPE).toString();
	}
	
	public String getCurrentDeviceApplication() {
		return dbr.getData(MongoDBConfig.DEVICES_LIST_KEYS.DEVICE_APPLICATION).toString();
	}
	
}
