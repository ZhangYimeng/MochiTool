package mochi.tool.net.httpserver.foundation;

import java.util.Map;

import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.HttpContext;

public class MochiHttpContext {
	
	private HttpContext httpContext;
	
	public MochiHttpContext(HttpContext httpContext) {
		this.httpContext = httpContext;
	}
	
	public Map<String, Object> getAttributes() {
		return this.httpContext.getAttributes();
	}
	
	public Authenticator getAuthenticator() {
		return this.httpContext.getAuthenticator();
	}

}
