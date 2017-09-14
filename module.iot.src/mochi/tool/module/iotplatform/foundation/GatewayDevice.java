package mochi.tool.module.iotplatform.foundation;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

import mochi.tool.data.interconversion.DataInterconversionTool;
import mochi.tool.module.iotplatform.foundation.dataanalyse.MessageProtocolConfig;
import mochi.tool.module.iotplatform.foundation.exception.DeviceNotExistsException;
import mochi.tool.module.iotplatform.foundation.exception.DeviceNotOnlineException;
import mochi.tool.module.iotplatform.foundation.exception.DuplicateException;
import mochi.tool.module.iotplatform.foundation.exception.DuplicatedDeviceIDException;
import mochi.tool.module.iotplatform.foundation.exception.UsernameWrongException;
import mochi.tool.module.iotplatform.foundation.mongodbtool.DBReader;
import mochi.tool.module.iotplatform.foundation.mongodbtool.DBResult;
import mochi.tool.module.iotplatform.foundation.mongodbtool.DBResultList;
import mochi.tool.module.iotplatform.foundation.mongodbtool.DBWriter;
import mochi.tool.module.iotplatform.foundation.mongodbtool.MongoDBConfig;
import mochi.tool.module.iotplatform.foundation.mongodbtool.exception.DBCollectionNotExistException;
import mochi.tool.module.iotplatform.foundation.mongodbtool.exception.DBObjectNullPointerException;
import mochi.tool.module.iotplatform.open.api.datatool.DataContent;

public class GatewayDevice {

	public static void createUserDevicelistCollection(String username) throws DBCollectionNotExistException {
		DBWriter.createCollection(MongoDBConfig.USER_DEVICE_COLLECTIONS_PREFIX + username);
		DBWriter.setIndex(MongoDBConfig.USER_DEVICE_COLLECTIONS_PREFIX + username, 
				MongoDBConfig.DEVICES_LIST_KEYS.DEVICE_ID, MongoDBConfig.MONGODB_OPTION_UNIQUE_DBO);
		DBWriter.setIndex(MongoDBConfig.USER_DEVICE_COLLECTIONS_PREFIX + username, 
				MongoDBConfig.DEVICES_LIST_KEYS.DEVICE_OWNER);
	}
	
	public static String registerNewGatewayDevice(String username, String devicename, String deviceDescription, String deviceLocation, 
			String deviceWriteType, String fixipFlag, String devicefixip, String deviceType) throws DuplicatedDeviceIDException, DBCollectionNotExistException {
		try {
			LinkedList<Object[]> content = new LinkedList<Object[]>();
			String deviceID = generateDeviceID();
			Object[] id = {MongoDBConfig.DEVICES_LIST_KEYS.DEVICE_ID, deviceID};
			Object[] name = {MongoDBConfig.DEVICES_LIST_KEYS.DEVICE_NAME, devicename};
			Object[] description = {MongoDBConfig.DEVICES_LIST_KEYS.DEVICE_DESCRIPTION, deviceDescription};
			Object[] location = {MongoDBConfig.DEVICES_LIST_KEYS.DEVICE_LOCATION, deviceLocation};
			Object[] writetype = {MongoDBConfig.DEVICES_LIST_KEYS.DEVICE_WRITE_TYPE, deviceWriteType};
			Object[] fixipflag = {MongoDBConfig.DEVICES_LIST_KEYS.DEVICE_FIX_IP_FLAG, fixipFlag};
			Object[] fixip = {MongoDBConfig.DEVICES_LIST_KEYS.DEVICE_FIX_IP, devicefixip};
			Object[] type = {MongoDBConfig.DEVICES_LIST_KEYS.DEVICE_TYPE, deviceType};
			Object[] owner = {MongoDBConfig.DEVICES_LIST_KEYS.DEVICE_OWNER, username};
			Object[] application = {MongoDBConfig.DEVICES_LIST_KEYS.DEVICE_APPLICATION, "none"};
			Object[] interval = {MongoDBConfig.DEVICES_LIST_KEYS.DEVICE_INTERVAL, 0l};
			content.add(id);
			content.add(name);
			content.add(description);
			content.add(location);
			content.add(writetype);
			content.add(fixipflag);
			if(fixipFlag.equals(MongoDBConfig.DEVICE_FIX_IP_FLAG_TRUE)) {
				content.add(fixip);
			}
			content.add(type);
			content.add(owner);
			content.add(application);
			content.add(interval);
			DBWriter.write(MongoDBConfig.USER_DEVICE_COLLECTIONS_PREFIX + username, content);
			String DeviceDataCollectionName = username + "_" + id[1];
			DBWriter.createCollection(DeviceDataCollectionName);
			System.out.println("注册的deviceDataCollection：" + DeviceDataCollectionName);
			DBWriter.setIndex(DeviceDataCollectionName, MongoDBConfig.DEVICE_DATA_COLLECTION_KEYS.TIME_STAMP);
			DBWriter.setIndex(DeviceDataCollectionName, MongoDBConfig.DEVICE_DATA_COLLECTION_KEYS.SOURCE_TAG);
			DBWriter.setIndex(DeviceDataCollectionName, MongoDBConfig.DEVICE_DATA_COLLECTION_KEYS.VALUE_TYPE);
			return deviceID;
		} catch (DuplicateException e) {
			throw new DuplicatedDeviceIDException();
		}
	}
	
	public static GatewayDevicesList queryDevicesByUsername(String username) throws DBCollectionNotExistException {
		DBResultList dbr = DBReader.read(MongoDBConfig.USER_DEVICE_COLLECTIONS_PREFIX + username, MongoDBConfig.DEVICES_LIST_KEYS.DEVICE_OWNER, username);
		return new GatewayDevicesList(dbr);
	}
	
	public static GatewayDeviceInfo queryGatewayDeviceInfo(String username, String deviceID) throws DBObjectNullPointerException, DBCollectionNotExistException {
		return new GatewayDeviceInfo(DBReader.readOne(MongoDBConfig.USER_DEVICE_COLLECTIONS_PREFIX + username, MongoDBConfig.DEVICES_LIST_KEYS.DEVICE_ID, deviceID));
	}
	
	public static long queryDeviceInterval(String username, String deviceID) throws DeviceNotExistsException, UsernameWrongException {
		try {
			DBResult result = DBReader.readOne(MongoDBConfig.USER_DEVICE_COLLECTIONS_PREFIX + username, new BasicDBObject(MongoDBConfig.DEVICES_LIST_KEYS.DEVICE_ID, deviceID));
			return (long) result.getData(MongoDBConfig.DEVICES_LIST_KEYS.DEVICE_INTERVAL);
		} catch (DBObjectNullPointerException e) {
			throw new DeviceNotExistsException();
		} catch (DBCollectionNotExistException e) {
			throw new UsernameWrongException();
		}
		
	}
	
	private static String generateDeviceID() {
		byte[] bytes = {generateByte(), generateByte(), generateByte(), generateByte(), generateByte(), generateByte(), generateByte(),
				generateByte(), generateByte(), generateByte()};
		return DataInterconversionTool.bytesToString(bytes);
	}
	
	private static byte generateByte() {
		Random random = new Random();
		return (byte) ((random.nextInt(2)%2 != 0? random.nextInt(26) + 65: random.nextInt(10) + 48));
	}
	
	public static void attatchApplication(String username, String deviceID, String application) throws DBCollectionNotExistException {
		DBWriter.update(MongoDBConfig.USER_DEVICE_COLLECTIONS_PREFIX + username, MongoDBConfig.DEVICES_LIST_KEYS.DEVICE_ID, deviceID, MongoDBConfig.DEVICES_LIST_KEYS.DEVICE_APPLICATION, application);
	}
	
	public static void uploadDataToDatabase(String username, String deviceID, long time, LinkedList<DataContent> dataContentList) throws DBCollectionNotExistException, DuplicateException {
		Iterator<DataContent> it = dataContentList.iterator();
		while(it.hasNext()) {
			DataContent dc = it.next();
			LinkedList<Object[]> content = new LinkedList<Object[]>();
			Object[] timeSeq = {MongoDBConfig.DEVICE_DATA_COLLECTION_KEYS.TIME_STAMP, time};
			Object[] tagSeq = {MongoDBConfig.DEVICE_DATA_COLLECTION_KEYS.SOURCE_TAG, dc.sourceTag};
			Object[] dataTypeSeq = {MongoDBConfig.DEVICE_DATA_COLLECTION_KEYS.VALUE_TYPE, dc.valueType};
			content.add(timeSeq);
			content.add(tagSeq);
			content.add(dataTypeSeq);
			switch(dc.valueType) {
			case MessageProtocolConfig.DATA_SOURCE_DATATYPE_BINARY:
				Object[] valueSeq = {MongoDBConfig.DEVICE_DATA_COLLECTION_KEYS.VALUE, dc.value};
				content.add(valueSeq);
				break;
			case MessageProtocolConfig.DATA_SOURCE_DATATYPE_DOUBLE:
				Object[] valueSeq1 = {MongoDBConfig.DEVICE_DATA_COLLECTION_KEYS.VALUE, DataInterconversionTool.bytesToDouble(dc.value)};
				content.add(valueSeq1);
				DBWriter.write(username + "_" + deviceID, content);
				break;
			case MessageProtocolConfig.DATA_SOURCE_DATATYPE_FLOAT:
				Object[] valueSeq2 = {MongoDBConfig.DEVICE_DATA_COLLECTION_KEYS.VALUE, DataInterconversionTool.bytesToFloat(dc.value)};
				content.add(valueSeq2);
				DBWriter.write(username + "_" + deviceID, content);
				break;
			case MessageProtocolConfig.DATA_SOURCE_DATATYPE_INT:
				Object[] valueSeq3 = {MongoDBConfig.DEVICE_DATA_COLLECTION_KEYS.VALUE, DataInterconversionTool.bytesToInt(dc.value)};
				content.add(valueSeq3);
				DBWriter.write(username + "_" + deviceID, content);
				break;
			case MessageProtocolConfig.DATA_SOURCE_DATATYPE_LONG:
				Object[] valueSeq4 = {MongoDBConfig.DEVICE_DATA_COLLECTION_KEYS.VALUE, DataInterconversionTool.bytesToLong(dc.value)};
				content.add(valueSeq4);
				DBWriter.write(username + "_" + deviceID, content);
				break;
			case MessageProtocolConfig.DATA_SOURCE_DATATYPE_SHORT:
				Object[] valueSeq5 = {MongoDBConfig.DEVICE_DATA_COLLECTION_KEYS.VALUE, DataInterconversionTool.bytesToShort(dc.value)};
				content.add(valueSeq5);
				DBWriter.write(username + "_" + deviceID, content);
				break;
			case MessageProtocolConfig.DATA_SOURCE_DATATYPE_VARCHAR:
				Object[] valueSeq6 = {MongoDBConfig.DEVICE_DATA_COLLECTION_KEYS.VALUE, DataInterconversionTool.bytesToString(dc.value)};
				content.add(valueSeq6);
				DBWriter.write(username + "_" + deviceID, content);
				break;
			}
		}
		
	}
	
	public static void deleteGatewayDevice(String username, String deviceID) throws DBCollectionNotExistException {
		//删除数据集合
		DBWriter.deleteDBCollection(username + "_" + deviceID);
		//在devicelist中删除该gateway
		DBWriter.deleteDBObject(MongoDBConfig.USER_DEVICE_COLLECTIONS_PREFIX + username, MongoDBConfig.DEVICES_LIST_KEYS.DEVICE_ID, deviceID);
		//在sourcelist中删除该gateway下辖的数据源
		DBWriter.deleteDBObject(MongoDBConfig.USER_SOURCE_COLLECTIONS_PREFIX + username, MongoDBConfig.SOURCE_LIST_KEYS.SOURCE_PROPERTY, deviceID);
	}
	
	public static DBResultList queryDataByTimeSpan(String username, String deviceID, String sourceTag, long timeSpan) throws DBCollectionNotExistException {
		DBObject dbo = new BasicDBObject(MongoDBConfig.DEVICE_DATA_COLLECTION_KEYS.SOURCE_TAG, sourceTag)
			.append(MongoDBConfig.DEVICE_DATA_COLLECTION_KEYS.TIME_STAMP, new BasicDBObject(QueryOperators.GTE, System.currentTimeMillis() - timeSpan));
		return DBReader.read(username + "_" + deviceID, dbo);
	}
	
	public static DBResult queryRealTimeData(String username, String deviceID, String sourceTag) throws DBObjectNullPointerException, DBCollectionNotExistException {
		DBObject dbo = new BasicDBObject(MongoDBConfig.DEVICE_DATA_COLLECTION_KEYS.SOURCE_TAG, sourceTag);
		return DBReader.readOne(username + "_" + deviceID, dbo);
	}
	
	public static DBResultList queryDataByLastOnes() {
		return null;
	}
	
	public static void setDeviceUploadInterval(String username, String deviceID, long interval) throws DBCollectionNotExistException, DeviceNotOnlineException {
		DBWriter.update(MongoDBConfig.USER_DEVICE_COLLECTIONS_PREFIX + username, 
				MongoDBConfig.DEVICES_LIST_KEYS.DEVICE_ID, deviceID, 
				MongoDBConfig.DEVICES_LIST_KEYS.DEVICE_INTERVAL, 
				interval);
		OnlineGatewayDevicePool.setDeviceInterval(deviceID, interval);
	}
	
	public static boolean doesDeviceExist(String username, String deviceID) {
		return DBReader.doesCollectionExist(username + "_" + deviceID);
	}
	
	public static DBResultList queryDataSources(String username, String deviceID) throws DBCollectionNotExistException {
		return DBReader.read(MongoDBConfig.USER_SOURCE_COLLECTIONS_PREFIX + username, MongoDBConfig.SOURCE_LIST_KEYS.SOURCE_PROPERTY, 
				deviceID);
	}
	
}
