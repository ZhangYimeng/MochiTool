package mochi.tool.mongodb.api;

import mochi.tool.mongodb.api.exception.IllegalPortValueException;
import mochi.tool.mongodb.api.exception.NoServerIPException;
import mochi.tool.mongodb.api.foundation.MongoDBConfig;

/**
 * It doesn't has a lot fields, because it is just a cache tool.
 * @author zhangyimeng
 *
 */
public class MongoDBConfigWithAuth implements MongoDBConfig{

	public String des;
	public int port;
	public String username;
	public String password;
	
	public MongoDBConfigWithAuth(String des, int port, String username, String password) throws NoServerIPException, IllegalPortValueException {
		if(des == null) {
			throw new NoServerIPException();
		}
		if(port >= 65536 || port <= 0) {
			throw new IllegalPortValueException();
		}
		this.des = des;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	public String getIP() {
		return des;
	}

	public int getPort() {
		return port;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}
	
}
