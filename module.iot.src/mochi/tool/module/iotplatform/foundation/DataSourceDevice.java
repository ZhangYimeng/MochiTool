package mochi.tool.module.iotplatform.foundation;

import java.util.LinkedList;

import com.mongodb.BasicDBObject;

import mochi.tool.module.iotplatform.foundation.exception.DuplicateException;
import mochi.tool.module.iotplatform.foundation.exception.DuplicatedSourceTagException;
import mochi.tool.module.iotplatform.foundation.mongodbtool.DBReader;
import mochi.tool.module.iotplatform.foundation.mongodbtool.DBWriter;
import mochi.tool.module.iotplatform.foundation.mongodbtool.MongoDBConfig;
import mochi.tool.module.iotplatform.foundation.mongodbtool.exception.DBCollectionNotExistException;

public class DataSourceDevice {

	public static void registerDataSourceDevice(String username, String deviceID, String dataSourceName, String dataSourceTag, 
			String dataSourceDescription, String dataSourceLocation, String dataSourceDiagram) throws DBCollectionNotExistException, DuplicatedSourceTagException {
		LinkedList<Object[]> content = new LinkedList<Object[]>();
		String[] name = {MongoDBConfig.SOURCE_LIST_KEYS.SOURCE_NAME, dataSourceName};
		String[] tag = {MongoDBConfig.SOURCE_LIST_KEYS.SOURCE_TAG, dataSourceTag};
		String[] description = {MongoDBConfig.SOURCE_LIST_KEYS.SOURCE_DESCRIPTION, dataSourceDescription};
		String[] location = {MongoDBConfig.SOURCE_LIST_KEYS.SOURCE_LOCATION, dataSourceLocation};
		String[] diagram = {MongoDBConfig.SOURCE_LIST_KEYS.SOURCE_DIAGRAM, dataSourceDiagram};
		String[] property = {MongoDBConfig.SOURCE_LIST_KEYS.SOURCE_PROPERTY, deviceID};
		String[] owner = {MongoDBConfig.SOURCE_LIST_KEYS.SOURCE_OWNER, username};
		content.add(name);
		content.add(tag);
		content.add(description);
		content.add(location);
		content.add(diagram);
		content.add(property);
		content.add(owner);
		try {
			DBWriter.write(MongoDBConfig.USER_SOURCE_COLLECTIONS_PREFIX + username, content);
		} catch (DuplicateException e) {
			throw new DuplicatedSourceTagException();
		}
	}
	
	public static void createUserSourcelistCollection(String username) throws DBCollectionNotExistException {
		DBWriter.createCollection(MongoDBConfig.USER_SOURCE_COLLECTIONS_PREFIX + username);
		DBWriter.setIndex(MongoDBConfig.USER_SOURCE_COLLECTIONS_PREFIX + username, MongoDBConfig.SOURCE_LIST_KEYS.SOURCE_TAG, 
				MongoDBConfig.MONGODB_OPTION_UNIQUE_DBO);
		DBWriter.setIndex(MongoDBConfig.USER_SOURCE_COLLECTIONS_PREFIX + username, MongoDBConfig.SOURCE_LIST_KEYS.SOURCE_PROPERTY);
	}
	
	public static void deleteDataSourceDevice(String username, String deviceID, String sourceTag) throws DBCollectionNotExistException {
		//在sourcelist中删除source
		DBWriter.deleteDBObject(MongoDBConfig.USER_SOURCE_COLLECTIONS_PREFIX + username, MongoDBConfig.SOURCE_LIST_KEYS.SOURCE_TAG, sourceTag);
		//在网关设备数据表里面删除该数据源的历史数据
		DBWriter.deleteDBObject(username + "_" + deviceID, MongoDBConfig.DEVICE_DATA_COLLECTION_KEYS.SOURCE_TAG, sourceTag);
	}
	
	public static DataSourceDevicesList queryDataSourceDevicesByDeviceID(String username, String deviceID) throws DBCollectionNotExistException {
		return new DataSourceDevicesList(DBReader.read(MongoDBConfig.USER_SOURCE_COLLECTIONS_PREFIX + username, 
				new BasicDBObject(MongoDBConfig.SOURCE_LIST_KEYS.SOURCE_PROPERTY, deviceID)));
	}
	
}
