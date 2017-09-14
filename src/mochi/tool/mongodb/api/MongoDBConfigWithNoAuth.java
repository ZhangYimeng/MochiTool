package mochi.tool.mongodb.api;

import mochi.tool.mongodb.api.exception.IllegalPortValueException;
import mochi.tool.mongodb.api.exception.NoServerIPException;
import mochi.tool.mongodb.api.foundation.MongoDBConfig;

public class MongoDBConfigWithNoAuth implements MongoDBConfig {

	public String des;
	public int port;
	
	public MongoDBConfigWithNoAuth(String des, int port) throws NoServerIPException, IllegalPortValueException {
		if(des == null) {
			throw new NoServerIPException();
		}
		if(port >= 65536 || port <= 0) {
			throw new IllegalPortValueException();
		}
		this.des = des;
		this.port = port;
	}
	
	public String getIP() {
		return des;
	}

	public int getPort() {
		return port;
	}

	public String getUsername() {
		return null;
	}

	public String getPassword() {
		return null;
	}

}
