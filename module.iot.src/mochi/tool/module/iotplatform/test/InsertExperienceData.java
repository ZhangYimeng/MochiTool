package mochi.tool.module.iotplatform.test;

import java.net.UnknownHostException;
import java.util.Date;

import mochi.tool.module.iotplatform.foundation.mongodbtool.exception.DBCollectionNotExistException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class InsertExperienceData {

	@SuppressWarnings({ "deprecation", "resource" })
	public static void main(String[] args) throws UnknownHostException, DBCollectionNotExistException {
		MongoClient mc = new MongoClient();
		DB db = mc.getDB("mochi");
		DBCollection dbc = db.getCollection("saitoiyoli_4EZC8XVW9I");
		//dbc.drop();
		int i = 0;
		for(; i < 10; i++) {
			BasicDBObject dbo = new BasicDBObject("time", new Date().getTime())
			.append("tag", "pressure1")
			.append("type", (short) 1)
			.append("value", i);
			dbc.insert(dbo);
		}
		//DBWriter.createCollection("saitoiyoli_J7O37KY8Q8");
		//System.out.println("注册的deviceDataCollection：" + "saitoiyoli_J7O37KY8Q8");
		//DBWriter.setIndex("saitoiyoli_J7O37KY8Q8", MongoDBConfig.DEVICE_DATA_COLLECTION_KEYS.TIME_STAMP);
		//DBWriter.setIndex("saitoiyoli_J7O37KY8Q8", MongoDBConfig.DEVICE_DATA_COLLECTION_KEYS.SOURCE_TAG);
		//DBWriter.setIndex("saitoiyoli_J7O37KY8Q8", MongoDBConfig.DEVICE_DATA_COLLECTION_KEYS.VALUE_TYPE);
		mc.close();
	}

}
