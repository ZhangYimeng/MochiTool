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

public class Elevator {

	public static void setElevatorInfo(String username, String deviceID, String elevatorName, String elevatorLocation, 
			String elevatorLowest, String elevatorHighest, String elevatorMaintenance, String elevatorMaintenanceStuff, 
			String elevatorMaintenanceContact, String elevatorManager, String elevatorManagerContact, String elevatorInstallDate) throws DBCollectionNotExistException, DuplicateException {
		LinkedList<Object[]> content = new LinkedList<Object[]>();
		Object[] name = {ElevatorConfig.APPLICATION_ELEVATOR_NAME, elevatorName};
		Object[] location = {ElevatorConfig.APPLICATION_ELEVATOR_LOCATION, elevatorLocation};
		Object[] lowest = {ElevatorConfig.APPLICATION_ELEVATOR_LOWEST, elevatorLowest};
		Object[] highest = {ElevatorConfig.APPLICATION_ELEVATOR_HIGHEST, elevatorHighest};
		Object[] maintenance = {ElevatorConfig.APPLICATION_ELEVATOR_MAINTENANCE, elevatorMaintenance};
		Object[] maintenanceStuff = {ElevatorConfig.APPLICATION_ELEVATOR_MAINTENANCE_STUFF, elevatorMaintenanceStuff};
		Object[] maintenanceContact = {ElevatorConfig.APPLICATION_ELEVATOR_MAINTENANCE_CONTACT, elevatorMaintenanceContact};
		Object[] manager = {ElevatorConfig.APPLICATION_ELEVATOR_MANAGER, elevatorManager};
		Object[] managerContact = {ElevatorConfig.APPLICATION_ELEVATOR_MANAGER_CONTACT, elevatorManagerContact};
		Object[] installDate = {ElevatorConfig.APPLICATION_ELEVATOR_INSTALL_DATE, elevatorInstallDate};
		content.add(name);
		content.add(location);
		content.add(lowest);
		content.add(highest);
		content.add(maintenance);
		content.add(maintenanceStuff);
		content.add(maintenanceContact);
		content.add(manager);
		content.add(managerContact);
		content.add(installDate);
		DBWriter.write(username + "_" + deviceID, content);
	}
	
	public static DBResult queryElevatorInfo(String username, String deviceID, String contentKey) throws DBObjectNullPointerException, DBCollectionNotExistException {
		DBObject dbo = new BasicDBObject(contentKey, MongoDBConfig.MONGODB_OPTION_EXISTS_DBO);
		return DBReader.readOne(username + "_" + deviceID, dbo);
	}
	
	public static DBResult queryElevatorSensor(String username, String deviceID, String sensorKey) throws DBObjectNullPointerException, DBCollectionNotExistException {
		DBObject dbo = new BasicDBObject(sensorKey, MongoDBConfig.MONGODB_OPTION_EXISTS_DBO);
		return DBReader.readOne(username + "_" + deviceID, dbo);
	}
	
	public static DBResultList queryElevatorSensorList(String username, String deviceID) throws DBCollectionNotExistException {
		return GatewayDevice.queryDataSources(username, deviceID);
	}
	
	public static void setElevatorSensor(String username, String deviceID, String sensorKey, String sensorTag) throws DBCollectionNotExistException, DuplicateException {
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
