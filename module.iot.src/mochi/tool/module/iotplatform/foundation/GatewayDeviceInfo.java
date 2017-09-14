package mochi.tool.module.iotplatform.foundation;

import mochi.tool.module.iotplatform.foundation.mongodbtool.DBResult;
import mochi.tool.module.iotplatform.foundation.mongodbtool.MongoDBConfig;

public class GatewayDeviceInfo {

	private DBResult dbr;
	
	public GatewayDeviceInfo(DBResult dbr) {
		this.dbr = dbr;
	}
	
	public String getDeviceID() {
		return dbr.getData(MongoDBConfig.DEVICES_LIST_KEYS.DEVICE_ID).toString();
	}
	
	public String getDeviceName() {
		return dbr.getData(MongoDBConfig.DEVICES_LIST_KEYS.DEVICE_NAME).toString();
	}
	
	public String getDeviceDescription() {
		return dbr.getData(MongoDBConfig.DEVICES_LIST_KEYS.DEVICE_DESCRIPTION).toString();
	}
	
	public String getDeviceLocation() {
		return dbr.getData(MongoDBConfig.DEVICES_LIST_KEYS.DEVICE_LOCATION).toString();
	}
	
	public String getDeviceWriteType() {
		return dbr.getData(MongoDBConfig.DEVICES_LIST_KEYS.DEVICE_WRITE_TYPE).toString();
	}
	
	public String getDeviceFixIPFlag() {
		return dbr.getData(MongoDBConfig.DEVICES_LIST_KEYS.DEVICE_FIX_IP_FLAG).toString();
	}
	
	public String getDeviceFixIP() {
		return (String) dbr.getData(MongoDBConfig.DEVICES_LIST_KEYS.DEVICE_FIX_IP);
	}
	
	public String getDeviceType() {
		return dbr.getData(MongoDBConfig.DEVICES_LIST_KEYS.DEVICE_TYPE).toString();
	}
	
	public String getDeviceApplication() {
		return dbr.getData(MongoDBConfig.DEVICES_LIST_KEYS.DEVICE_APPLICATION).toString();
	}
	
}
