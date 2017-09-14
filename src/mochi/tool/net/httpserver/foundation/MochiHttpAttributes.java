package mochi.tool.net.httpserver.foundation;

import java.util.HashMap;

public class MochiHttpAttributes {

	private HashMap<String, String> attributes;
	
	public MochiHttpAttributes(String query) {
		String[] rawAttributes = query.split("&");
		for(String ra: rawAttributes) {
			String[] temp = ra.split("=");
			this.attributes.put(temp[0], temp[1]);
		}
	}
	
	public String getAttribute(String tag) {
		return this.attributes.get(tag);
	}
	
}
