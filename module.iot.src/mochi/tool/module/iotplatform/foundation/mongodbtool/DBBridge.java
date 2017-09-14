package mochi.tool.module.iotplatform.foundation.mongodbtool;

import com.mongodb.DB;
import com.mongodb.MongoClient;

@SuppressWarnings("deprecation")
public class DBBridge {
	
	protected static MongoClient mc;
	protected static DB db;
	static {
		mc = new MongoClient(MongoDBConfig.IP, MongoDBConfig.PORT);
		db = mc.getDB(MongoDBConfig.DATABASE);
	}
	
	/**
	 * 如果不调用构造函数，则默认使用本地MongoDB数据库。
	 * @param ip
	 * @param port
	 */
	public DBBridge(String ip, int port) {
		mc = new MongoClient(ip, port);
		db = mc.getDB(MongoDBConfig.DATABASE);
	}
	
}
