package mochi.tool.net.httpprotocol;

import mochi.tool.net.httpprotocol.exception.HttpRequestLineNoMethodException;
import mochi.tool.net.httpprotocol.exception.HttpRequestLineNoUrlException;
import mochi.tool.net.httpprotocol.exception.HttpRequestLineNoVersionException;

/**
 * 用于生成Http请求的请求行。
 * @author saito
 */
public class HttpRequestLine {

	private String method;
	private String url;
	private String version;
	
	public HttpRequestLine() {
		
	}
	
	public HttpRequestLine(String method, String url, String version) {
		this.method = method;
		this.url = url;
		this.version = version;
	}
	
	public void setMethod(String method) {
		this.method = method;
	}
	
	public boolean isGetMethod() throws HttpRequestLineNoMethodException {
		if(this.method != null) {
			return this.method.equals("GET")? true: false;
		} else {
			throw new HttpRequestLineNoMethodException("Method未设置！");
		}
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String generateRequestLine() throws HttpRequestLineNoMethodException, HttpRequestLineNoUrlException, HttpRequestLineNoVersionException {
		StringBuffer requestLine = new StringBuffer();
		if(this.method == null) {
			throw new HttpRequestLineNoMethodException("Http请求行缺少Method！");
		}
		if(this.url == null) {
			throw new HttpRequestLineNoUrlException("Http请求行缺少Url！");
		}
		if(this.version == null) {
			throw new HttpRequestLineNoVersionException("Http请求行缺少Version！");
		}
		requestLine.append(this.method + " ");
		requestLine.append(this.url + " ");
		requestLine.append(this.version);
		return requestLine.toString();
	}
	
}
