package mochi.tool.module.iotplatform.foundation.mongodbtool;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class MongoDBConfig {
	
	public static final String IP = "127.0.0.1";
	public static final int PORT = 27017;
	public static final String DATABASE = "mochi";
	
	public static final String USER_DEVICE_COLLECTIONS_PREFIX = "deviceslist_";
	public static final String DEVICE_WRITE_TYPE_SYN = "syn";
	public static final String DEVICE_WRITE_TYPE_ASYN = "asyn";
	public static final String DEVICE_FIX_IP_FLAG_TRUE = "true";
	public static final String DEVICE_FIX_IP_FLAG_FALSE = "false";
	public static final String DEVICE_TYPE_STANDARD = "s";
	public static final String DEVICE_TYPE_CUSTOM = "c";
	public static final String DEVICE_APPLICATION_ELEVATOR = "elevator";
	public static final String DEVICE_APPLICATION_WATER_PRESSURE = "waterpressure";
	public static final String DEVICE_APPLICATION_NONE = "none";
	public static class DEVICES_LIST_KEYS {
		public static final String DEVICE_ID = "id";
		public static final String DEVICE_NAME = "name";
		public static final String DEVICE_DESCRIPTION = "description";
		public static final String DEVICE_LOCATION = "location";
		public static final String DEVICE_WRITE_TYPE = "writetype";
		public static final String DEVICE_FIX_IP_FLAG = "fixipflag";
		public static final String DEVICE_FIX_IP = "ip";
		public static final String DEVICE_OWNER = "owner";
		public static final String DEVICE_TYPE = "type";
		public static final String DEVICE_APPLICATION = "application";
		public static final String DEVICE_INTERVAL = "interval";
	}
	
	public static final String USER_SOURCE_COLLECTIONS_PREFIX = "sourceslist_";
	public static final String SOURCE_DIAGRAM_CURVE = "1";
	public static class SOURCE_LIST_KEYS {
		public static final String SOURCE_TAG = "tag";
		public static final String SOURCE_NAME = "name";
		public static final String SOURCE_DESCRIPTION = "description";
		public static final String SOURCE_LOCATION = "location";
		public static final String SOURCE_PROPERTY = "property";
		public static final String SOURCE_OWNER = "owner";
		public static final String SOURCE_DIAGRAM = "diagram";
	}
	
	public static class DEVICE_DATA_COLLECTION_KEYS {
		public static final String TIME_STAMP = "time";
		public static final String SOURCE_TAG = "tag";
		public static final String VALUE_TYPE = "type";
		public static final String VALUE = "value";
	}
	
	public static final String MONGODB_OPTION_UNIQUE = "unique";
	public static final DBObject MONGODB_OPTION_UNIQUE_DBO = new BasicDBObject("unique", true);
	public static final String MONGODB_OPTION_EXISTS = "$exists";
	public static final DBObject MONGODB_OPTION_EXISTS_DBO = new BasicDBObject("$exists", true);
	public static final String MONGODB_OPTION_GTE = "$gte";
	public static BasicDBObject MONGODB_OPTION_GTE_DBO(long number) {
		return new BasicDBObject("$gte", number);
	}
	public static final String MONGODB_OPTION_GT = "$gt";
	public static BasicDBObject MONGODB_OPTION_GT_DBO(long number) {
		return new BasicDBObject("$gt", number);
	}
	public static final String MONGODB_OPTION_LTE = "$lte";
	public static BasicDBObject MONGODB_OPTION_LTE_DBO(long number) {
		return new BasicDBObject("$lte", number);
	}
	public static final String MONGODB_OPTION_LT = "$lt";
	public static BasicDBObject MONGODB_OPTION_LT_DBO(long number) {
		return new BasicDBObject("$lt", number);
	}
	
}
