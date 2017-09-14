package mochi.tool.module.iotplatform.test;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoDBQueryLastData {

	@SuppressWarnings({ "deprecation", "resource" })
	public static void main(String[] args) throws UnknownHostException {
			MongoClient mc = new MongoClient();
			DB db = mc.getDB("mochi");
			DBCollection dbc = db.getCollection("saitoiyoli_J7O37KY8Q8");
			DBCursor cursor = dbc.find();
			cursor.sort(new BasicDBObject("_id", -1));
			while(cursor.hasNext()) {
				System.out.println(cursor.next().get("value"));
			}
			DBObject dbo = dbc.findOne(null, null, new BasicDBObject("_id", -1));
			System.out.println(dbo.get("value"));
	}

}
