package mochi.tool.mongodb.cache;

import mochi.tool.mongodb.cache.foundation.CacheConfig;

/**
 * It doesn't has a lot fields, because it is just a cache tool.
 * @author zhangyimeng
 *
 */
public class MongoDBCacheConfig implements CacheConfig {

	public String des;
	public int port;
	public String username;
	public String password;
	
	public MongoDBCacheConfig(String des, int port, String username, String password) {
		this.des = des;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	@Override
	public String getIP() {
		return des;
	}

	@Override
	public int getPort() {
		return port;
	}

	@Override
	public String getUser() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}
	
}
