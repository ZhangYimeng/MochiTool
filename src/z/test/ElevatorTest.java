package z.test;

import java.net.UnknownHostException;

import mochi.tool.module.iotplatform.foundation.mongodbtool.MongoDBConfig;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class ElevatorTest {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws UnknownHostException {
		MongoClient mc = new MongoClient();
		DB db = mc.getDB("test");
		DBCollection dbc = db.getCollection("test");
		/*BasicDBObject dbo1 = new BasicDBObject(MongoDBConfig.DEVICE_DATA_COLLECTION_KEYS.SOURCE_TAG, "1234").
				append(MongoDBConfig.DEVICE_DATA_COLLECTION_KEYS.TIME_STAMP, 42);
		BasicDBObject dbo2 = new BasicDBObject(MongoDBConfig.DEVICE_DATA_COLLECTION_KEYS.SOURCE_TAG, "1234").
				append(MongoDBConfig.DEVICE_DATA_COLLECTION_KEYS.TIME_STAMP, 415);
		BasicDBObject dbo3 = new BasicDBObject(MongoDBConfig.DEVICE_DATA_COLLECTION_KEYS.SOURCE_TAG, "1234").
				append(MongoDBConfig.DEVICE_DATA_COLLECTION_KEYS.TIME_STAMP, 674);
		BasicDBObject dbo4 = new BasicDBObject(MongoDBConfig.DEVICE_DATA_COLLECTION_KEYS.SOURCE_TAG, "1234").
				append(MongoDBConfig.DEVICE_DATA_COLLECTION_KEYS.TIME_STAMP, 993);
		dbc.insert(dbo1);
		dbc.insert(dbo2);
		dbc.insert(dbo3);
		dbc.insert(dbo4);*/
		BasicDBObject dbo = new BasicDBObject(MongoDBConfig.DEVICE_DATA_COLLECTION_KEYS.SOURCE_TAG, "123").
				append(MongoDBConfig.DEVICE_DATA_COLLECTION_KEYS.TIME_STAMP, new BasicDBObject(MongoDBConfig.MONGODB_OPTION_GTE, 10).
						append(MongoDBConfig.MONGODB_OPTION_LTE, 80));
		System.out.println(dbo);
		DBCursor cursor = dbc.find(dbo);
		while(cursor.hasNext()) {
			DBObject dbok = cursor.next();
			System.out.println(dbok.get(MongoDBConfig.DEVICE_DATA_COLLECTION_KEYS.TIME_STAMP));
		}
		mc.close();
	}

}
