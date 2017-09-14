package mochi.tool.module.iotplatform.foundation.application;

import java.util.LinkedList;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import mochi.tool.module.iotplatform.foundation.GatewayDevice;
import mochi.tool.module.iotplatform.foundation.exception.DuplicateException;
import mochi.tool.module.iotplatform.foundation.mongodbtool.DBReader;
import mochi.tool.module.iotplatform.foundation.mongodbtool.DBResult;
import mochi.tool.module.iotplatform.foundation.mongodbtool.DBResultList;
import mochi.tool.module.iotplatform.foundation.mongodbtool.DBWriter;
import mochi.tool.module.iotplatform.foundation.mongodbtool.MongoDBConfig;
import mochi.tool.module.iotplatform.foundation.mongodbtool.exception.DBCollectionNotExistException;
import mochi.tool.module.iotplatform.foundation.mongodbtool.exception.DBObjectNullPointerException;

public class WaterPressure {

	public static void setWaterPressureInfo(String username, String deviceID, String buildingName, String buildingLocation, 
			String waterPressureMaintenance, String waterPressureMaintenanceStuff, String waterPressureMaintenanceContact, 
			String waterPressureManager, String waterPressureManagerContact, String waterPressureInstallDate) throws 
			DBCollectionNotExistException, DuplicateException {
		LinkedList<Object[]> content = new LinkedList<Object[]>();
		Object[] buildingname = {WaterPressureConfig.APPLICATION_WATER_PRESSURE_BUILDING_NAME, buildingName};
		Object[] buildinglocation = {WaterPressureConfig.APPLICATION_WATER_PRESSURE_BUILDING_LOCATION, buildingLocation};
		Object[] maintenance = {WaterPressureConfig.APPLICATION_WATER_PRESSURE_MAINTENANCE, waterPressureMaintenance};
		Object[] maintenanceStuff = {WaterPressureConfig.APPLICATION_WATER_PRESSURE_MAINTENANCE_STUFF, waterPressureMaintenanceStuff};
		Object[] maintenanceContact = {WaterPressureConfig.APPLICATION_WATER_PRESSURE_MAINTENANCE_CONTACT, waterPressureMaintenanceContact};
		Object[] manager = {WaterPressureConfig.APPLICATION_WATER_PRESSURE_MANAGER, waterPressureManager};
		Object[] managerContact = {WaterPressureConfig.APPLICATION_WATER_PRESSURE_MANAGER_CONTACT, waterPressureManagerContact};
		Object[] installDate = {WaterPressureConfig.APPLICATION_WATER_PRESSURE_INSTALL_DATE, waterPressureInstallDate};
		content.add(buildingname);
		content.add(buildinglocation);
		content.add(maintenance);
		content.add(maintenanceStuff);
		content.add(maintenanceContact);
		content.add(manager);
		content.add(managerContact);
		content.add(installDate);
		DBWriter.write(username + "_" + deviceID, content);
	}
	
	public static DBResult queryWaterPressureInfo(String username, String deviceID, String contentKey) throws DBObjectNullPointerException, DBCollectionNotExistException {
		DBObject dbo = new BasicDBObject(contentKey, MongoDBConfig.MONGODB_OPTION_EXISTS_DBO);
		return DBReader.readOne(username + "_" + deviceID, dbo);
	}
	
	public static DBResult queryWaterPressureSensor(String username, String deviceID, String sensorKey) throws DBObjectNullPointerException, DBCollectionNotExistException {
		DBObject dbo = new BasicDBObject(sensorKey, MongoDBConfig.MONGODB_OPTION_EXISTS_DBO);
		return DBReader.readOne(username + "_" + deviceID, dbo);
	}
	
	public static DBResultList queryWaterPressureSensorList(String username, String deviceID) throws DBCollectionNotExistException {
		return GatewayDevice.queryDataSources(username, deviceID);
	}
	
	public static void setWaterPressureSensor(String username, String deviceID, String sensorKey, String sensorTag) throws DBCollectionNotExistException, DuplicateException {
		DBWriter.write(username + "_" + deviceID, sensorKey, sensorTag);
	}
	
	public static DBResult queryRealTimeData(String username, String deviceID, String sensorTag) throws DBObjectNullPointerException, DBCollectionNotExistException {
		return GatewayDevice.queryRealTimeData(username, deviceID, sensorTag);
	}
	
	public static DBResultList queryHistoryData(String username, String deviceID, String sensorTag, long startTimeStamp, long endTimeStamp) throws DBCollectionNotExistException {
		BasicDBObject dbo = new BasicDBObject(MongoDBConfig.DEVICE_DATA_COLLECTION_KEYS.SOURCE_TAG, sensorTag).
				append(MongoDBConfig.DEVICE_DATA_COLLECTION_KEYS.TIME_STAMP, new BasicDBObject(MongoDBConfig.MONGODB_OPTION_GTE, startTimeStamp).
						append(MongoDBConfig.MONGODB_OPTION_LTE, endTimeStamp));
		return DBReader.read(username + "_" + deviceID, dbo);
	}
	
}
