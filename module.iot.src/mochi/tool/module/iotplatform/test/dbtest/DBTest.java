package mochi.tool.module.iotplatform.test.dbtest;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.MongoClient;

import mochi.tool.module.iotplatform.foundation.mongodbtool.MongoDBConfig;

public class DBTest {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws UnknownHostException {
		MongoClient mc = new MongoClient("127.0.0.1", MongoDBConfig.PORT);
		@SuppressWarnings("deprecation")
		DB db = mc.getDB("mochi");
		//DBCollection dbc = db.getCollection("mochi");
		System.out.println(db.collectionExists("mochi"));
		//DBCursor cursor = dbc.find(new BasicDBObject("op", 13));
		//System.out.println(cursor.count());
	}

}
