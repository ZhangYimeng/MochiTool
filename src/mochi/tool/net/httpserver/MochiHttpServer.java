package mochi.tool.net.httpserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

import mochi.tool.net.httpserver.foundation.MochiPathAndHttpHandler;
import mochi.tool.net.httpserver.foundation.MochiHttpHandlerModel;

import com.sun.net.httpserver.HttpServer;

public class MochiHttpServer {
	
	private int port;
	private InetSocketAddress netSocketAddress;
	private int requestCount;
	private HttpServer server;
	
	public MochiHttpServer(int port, int requestCount) throws IOException {
		this.port = port;
		this.netSocketAddress = new InetSocketAddress(this.port);
		this.requestCount = requestCount;
		this.server = HttpServer.create(this.netSocketAddress, this.requestCount);
	}
	
	public void setHandler(MochiPathAndHttpHandler handler) {
		this.server.createContext(handler.getPath(), handler.getHttpHandler());
	}
	
	public void setHandlers(List<MochiPathAndHttpHandler> handler) {
		for(MochiPathAndHttpHandler httpContext: handler) {
			this.server.createContext(httpContext.getPath(), httpContext.getHttpHandler());
		}
	}
	
	public void start() {
		server.createContext("/zym", new MochiHttpHandlerModel());
		server.start();
		System.out.println("HTTPServer has started!");
	}
}
