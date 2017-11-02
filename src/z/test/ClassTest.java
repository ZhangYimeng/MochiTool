package z.test;

import mochi.tool.mongodb.cache.MongoDBCacheConfig;
import mochi.tool.mongodb.cache.foundation.CacheConfig;

public class ClassTest {

	public static void main(String[] args) {
		CacheConfig config = new MongoDBCacheConfig("127.0.0.1", 0, null, null);
		System.out.println(config.getClass().equals(MongoDBCacheConfig.class));
	}

}
