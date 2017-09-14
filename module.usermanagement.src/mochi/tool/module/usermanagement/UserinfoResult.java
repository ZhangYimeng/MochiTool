package mochi.tool.module.usermanagement;

import java.util.HashMap;
import java.util.Map;

public class UserinfoResult {
	
	private HashMap<String, String> userinfo;
	
	public UserinfoResult() {
		userinfo = new HashMap<String, String>();
	}
	
	public UserinfoResult(Map<String, String> map) {
		userinfo = (HashMap<String, String>) map;
	}
	
	public void setUserinfo(String key, String value) {
		userinfo.put(key, value);
	}
	
	public String getUserinfoByKey(String key) {
		return userinfo.get(key);
	}
	
}
