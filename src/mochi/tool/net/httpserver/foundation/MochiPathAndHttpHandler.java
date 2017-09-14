package mochi.tool.net.httpserver.foundation;

import com.sun.net.httpserver.HttpHandler;

public class MochiPathAndHttpHandler {
	
	private String path;
	private HttpHandler handler;
	
	public MochiPathAndHttpHandler(String path, HttpHandler handler) {
		this.path = path;
		this.handler = handler;
	}
	
	public String getPath() {
		return this.path;
	}

	public HttpHandler getHttpHandler() {
		return this.handler;
	}
	
}
