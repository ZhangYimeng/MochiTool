package mochi.tool.mongodb.cache.foundation;

/**
 * Only for local connect.
 * @author zhangyimeng
 */
public class CacheConfigDefault implements CacheConfig {

	public String des;
	public int port;
	
	private CacheConfigDefault() {
		des = "127.0.0.1";
		port = 27017;
	}
	
	public static CacheConfigDefault getCacheConfigDefault() {
		return new CacheConfigDefault();
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
		return null;
	}

	@Override
	public String getPassword() {
		return null;
	}
	
}
