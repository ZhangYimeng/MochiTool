package mochi.tool.module.iotplatform.test;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import mochi.tool.module.iotplatform.foundation.mongodbtool.MongoDBConfig;

public class MongoDBExistsTest {

	@SuppressWarnings({ "deprecation", "resource" })
	public static void main(String[] args) throws UnknownHostException {
		MongoClient mc = new MongoClient(MongoDBConfig.IP, MongoDBConfig.PORT);
		DB db = mc.getDB("test");
		DBCollection dbc = db.getCollection("test");
		BasicDBObject dbo = new BasicDBObject("uuu", 9090);
		dbo.append("iii", 888);
		dbc.insert(dbo);
		dbo = new BasicDBObject("uuu", 909090);
		dbo.append("ooo", 8899);
		dbc.insert(dbo);
		DBObject dbo1 = new BasicDBObject("iii", new BasicDBObject(MongoDBConfig.MONGODB_OPTION_EXISTS, true));
		DBCursor cursor = dbc.find(dbo1);
		while(cursor.hasNext()) {
			BasicDBObject res = (BasicDBObject) cursor.next();
			System.out.println(res.get("iii"));
		}
		dbo = (BasicDBObject) dbc.findOne(dbo1);
		System.out.println(dbo.get("iii"));
		dbo = new BasicDBObject("uuiu", 9090);
		//DBObject dbo2 = dbc.findOne(dbo);
		//System.out.println(dbo2.get("op"));
		DBCursor cursor1 = dbc.find();
		System.out.println(cursor1);
	}

}
