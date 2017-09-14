package mochi.tool.module.iotplatform.test;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoDBDumplicatedTest {

	@SuppressWarnings({ "deprecation", "resource" })
	public static void main(String[] args) throws UnknownHostException {
		MongoClient mc = new MongoClient("127.0.0.1", 27017);
		DB db = mc.getDB("authentication");
		DBCollection dbc = db.getCollection("userinfo");
		//dbc.createIndex(new BasicDBObject("id", 1), new BasicDBObject("unique", true));
		//BasicDBObject dbo = new BasicDBObject("id", 2);
		//dbc.insert(dbo);
		//BasicDBObject dbo1 = new BasicDBObject("id", 2);
		//dbc.insert(dbo1);
		//byte[] hu = {1,2,3,4,5,6,7,8,9};
		//BasicDBObject dbo = new BasicDBObject("id", hu);
		//dbc.insert(dbo);
		//DBObject dbii = dbc.findOne();
		//System.out.println(dbii.get("id").getClass());
		//byte[] b = (byte[]) dbii.get("id");
		//for(byte o: b) {
			//System.out.println(o);
		//}
		DBObject dbo = dbc.findOne(new BasicDBObject("username", "saitoiyoli"));
		System.out.println(dbo.get("password"));
	}

}
