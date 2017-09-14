package mochi.tool.module.iotplatform.test;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class DataTypeTest {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws UnknownHostException {
		@SuppressWarnings("resource")
		MongoClient mc = new MongoClient("127.0.0.1", 27017);
		DB db = mc.getDB("test");
		DBCollection dbc = db.getCollection("test");
		BasicDBObject dbo = new BasicDBObject();
		long a = 9999;
		int b = 9999;
		short c = 9999;
		boolean d = true;
		String e = "abcd";
		float f = 9.999f;
		double g = 9.999;
		dbo.append("a", a);
		dbo.append("b", b);
		dbo.append("c", c);
		dbo.append("d", d);
		dbo.append("e", e);
		dbo.append("f", f);
		dbo.append("g", g);
		dbc.insert(dbo);
		DBObject oo = dbc.findOne(dbo);
		System.out.println(oo.get("a").getClass());
		System.out.println(oo.get("d").getClass());
		System.out.println(oo.get("b").getClass());
		System.out.println(oo.get("e").getClass());
	}

}
