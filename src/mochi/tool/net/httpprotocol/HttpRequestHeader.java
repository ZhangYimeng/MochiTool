package mochi.tool.net.httpprotocol;

import mochi.tool.net.httpprotocol.exception.HttpRequestHeaderNoHostException;

/**
 * 生成Http请求的报文头。
 * @author saito
 *
 */
public class HttpRequestHeader {
	
	private String accept;
	private String referer;
	private String accept_language;
	private String accept_encoding;
	private String user_agent;
	private String content_type;
	private String host;
	private String content_lenth;
	private String connection;
	private String cache_control;
	private String cookie;
	private StringBuffer sb;
	
	public HttpRequestHeader() {
		sb = new StringBuffer();
	}
	
	public String generateRequestHeader() throws HttpRequestHeaderNoHostException {
		StringBuffer header = new StringBuffer();
		if(this.accept != null) {
			header.append(this.accept + "\n");
		}
		if(this.referer != null) {
			header.append(this.referer + "\n");
		}
		if(this.accept_language != null) {
			header.append(this.accept_language + "\n");
		}
		if(this.user_agent != null) {
			header.append(this.user_agent + "\n");
		}
		if(this.content_type != null) {
			header.append(this.content_type + "\n");
		}
		if(this.host != null) {
			header.append(this.host + "\n");
		} else {
			throw new HttpRequestHeaderNoHostException("未指定Host！");
		}
		if(this.content_lenth != null) {
			header.append(this.content_lenth + "\n");
		}
		if(this.connection != null) {
			header.append(this.connection + "\n");
		}
		if(this.cache_control != null) {
			header.append(this.cache_control + "\n");
		}
		if(this.cookie != null) {
			header.append(this.cookie + "\n");
		}
		if(this.accept_encoding != null) {
			header.append(this.accept_encoding);
		}
		return header.toString();
	}
	
	public String getAccept_Encoding() {
		return accept_encoding;
	}
	
	public String getAccept_language() {
		return accept_language;
	}

	public void setAccept_language(String accept_language) {
		this.accept_language = accept_language;
	}

	public String getUser_agent() {
		return user_agent;
	}

	public void setUser_agent(String user_agent) {
		this.user_agent = user_agent;
	}

	public String getContent_type() {
		return content_type;
	}

	public void setContent_type(String content_type) {
		this.content_type = content_type;
	}

	public String getHost() throws HttpRequestHeaderNoHostException {
		if(this.host != null) {
			return host;
		} else {
			throw new HttpRequestHeaderNoHostException("HttpRequestHeader中的Host未设置，请先行设置！");
		}
	}

	public void setAccept_encoding(String accept_encoding) {
		this.accept_encoding = accept_encoding;
	}
	
	public void setHost(String host) {
		this.host = host;
	}

	public String getContent_lenth() {
		return content_lenth;
	}

	public void setContent_lenth(String content_lenth) {
		this.content_lenth = content_lenth;
	}

	public String getConnection() {
		return connection;
	}

	public void setConnection(String connection) {
		this.connection = connection;
	}

	public String getCache_control() {
		return cache_control;
	}

	public void setCache_control(String cache_control) {
		this.cache_control = cache_control;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public String getAccept() {
		return accept;
	}

	public void setAccept(String accept) {
		this.accept = accept;
	}
	
	public void setSomeThing(String something) {
		sb.append(something + "\r\n"); 
	}
	
	public String getSomeThing() {
		return sb.toString();
	}

}
