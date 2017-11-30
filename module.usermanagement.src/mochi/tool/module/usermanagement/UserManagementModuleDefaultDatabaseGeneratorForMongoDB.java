package mochi.tool.module.usermanagement;

import java.net.UnknownHostException;

import mochi.tool.module.usermanagement.foundation.UserManagementConfiguration_MongoDBMode;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class UserManagementModuleDefaultDatabaseGeneratorForMongoDB {

	private MongoClient mc;
	private DB db;
	private DBCollection collection;
	
	@SuppressWarnings("deprecation")
	public UserManagementModuleDefaultDatabaseGeneratorForMongoDB() throws UnknownHostException {
		mc = new MongoClient(UserManagementConfiguration_MongoDBMode.IP, UserManagementConfiguration_MongoDBMode.PORT);
		db = mc.getDB(UserManagementConfiguration_MongoDBMode.DATABASE);
		db.dropDatabase();
		db = mc.getDB(UserManagementConfiguration_MongoDBMode.DATABASE);
	}
	
	public void generate() {
		this.creatUserInfoTable();
		this.mc.close();
	}
	
	private void creatUserInfoTable() {
		DBObject option = new BasicDBObject("capped", false);
		db.createCollection(UserManagementConfiguration_MongoDBMode.USER_INFO_COLLECTION, option);
		collection = db.getCollection(UserManagementConfiguration_MongoDBMode.USER_INFO_COLLECTION);
		collection.createIndex(new BasicDBObject(UserManagementConfiguration_MongoDBMode.USER_INFO_COLLECTION_KEYS.username,1),
				new BasicDBObject("unique", true));
		collection.createIndex(new BasicDBObject(UserManagementConfiguration_MongoDBMode.USER_INFO_COLLECTION_KEYS.token,1),
				new BasicDBObject("unique",true));
	}
	
}
