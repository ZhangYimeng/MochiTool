package mochi.tool.net.httpprotocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Arrays;

import mochi.tool.data.bytescombinetool.BytesCombineTool;
import mochi.tool.data.interconversion.DataInterconversionTool;
import mochi.tool.net.httpprotocol.exception.HttpRequestHeaderNoHostException;
import mochi.tool.net.httpprotocol.exception.HttpRequestLineNoMethodException;
import mochi.tool.net.httpprotocol.exception.HttpRequestLineNoUrlException;
import mochi.tool.net.httpprotocol.exception.HttpRequestLineNoVersionException;

public class HttpRequestSender {

	private int port = 80;
	private HttpRequestLine hrl;
	private HttpRequestHeader hrh;
	private HttpRequestBody hrb;
	
	public HttpRequestSender(HttpRequestLine hrl, HttpRequestHeader hrh, HttpRequestBody hrb, int port) {
		this.port = port;
		this.hrl = hrl;
		this.hrh = hrh;
		this.hrb = hrb;
	}
	
	public HttpRequestSender(HttpRequestLine hrl, HttpRequestHeader hrh, HttpRequestBody hrb) {
		this.hrl = hrl;
		this.hrh = hrh;
		this.hrb = hrb;
	}
	
	@Deprecated
	public HttpResponsePackage sendRequest() throws IOException, HttpRequestLineNoMethodException, 
	HttpRequestLineNoUrlException, HttpRequestLineNoVersionException, HttpRequestHeaderNoHostException {
		Socket s = new Socket();
		SocketAddress socketAddress = new InetSocketAddress(this.hrh.getHost().substring(6), this.port);  
	    s.connect(socketAddress);
		OutputStreamWriter out = new OutputStreamWriter(s.getOutputStream());
		out.write(this.hrl.generateRequestLine() + "\r\n");
		if(this.hrl.isGetMethod()) {
			if(this.hrh.getAccept() != null) {
				out.write(this.hrh.getAccept() + "\r\n");
			}
			if(this.hrh.getAccept_language() != null) {
				out.write(this.hrh.getAccept_language() + "\r\n");
			}
			if(this.hrh.getHost() != null) {
				out.write(this.hrh.getHost() + "\r\n");
			}
			if(this.hrh.getCache_control() != null) {
				out.write(this.hrh.getCache_control() + "\r\n");
			}
			if(this.hrh.getConnection() != null) {
				out.write(this.hrh.getConnection() + "\r\n");
			}
			if(this.hrh.getContent_lenth() != null) {
				out.write(this.hrh.getContent_lenth() + "\r\n");
			}
			if(this.hrh.getContent_type() != null) {
				out.write(this.hrh.getContent_type() + "\r\n");
			}
			if(this.hrh.getCookie() != null) {
				out.write(this.hrh.getCookie() + "\r\n");
			}
			if(this.hrh.getReferer() != null) {
				out.write(this.hrh.getReferer() + "\r\n");
			}
			if(this.hrh.getUser_agent() != null) {
				out.write(this.hrh.getUser_agent() + "\r\n");
			}
			out.write("\r\n");
		} else {
			if(this.hrh.getAccept() != null) {
				out.write(this.hrh.getAccept() + "\r\n");
			}
			if(this.hrh.getAccept_language() != null) {
				out.write(this.hrh.getAccept_language() + "\r\n");
			}
			if(this.hrh.getHost() != null) {
				out.write(this.hrh.getHost() + "\r\n");
			}
			if(this.hrh.getCache_control() != null) {
				out.write(this.hrh.getCache_control() + "\r\n");
			}
			if(this.hrh.getConnection() != null) {
				out.write(this.hrh.getConnection() + "\r\n");
			}
			if(this.hrh.getContent_lenth() != null) {
				out.write(this.hrh.getContent_lenth() + "\r\n");
			}
			if(this.hrh.getContent_type() != null) {
				out.write(this.hrh.getContent_type() + "\r\n");
			}
			if(this.hrh.getCookie() != null) {
				out.write(this.hrh.getCookie() + "\r\n");
			}
			if(this.hrh.getReferer() != null) {
				out.write(this.hrh.getReferer() + "\r\n");
			}
			if(this.hrh.getUser_agent() != null) {
				out.write(this.hrh.getUser_agent() + "\r\n");
			}
			if(this.hrb != null) {
				out.write("\r\n");
				switch(this.hrb.getFlag()) {
				case 1:
					break;
				case 2:
					out.write(this.hrb.getText());
					break;
				case 3:
					break;
				case 4:
					break;
				default:
					break;
				}
				}
			out.write("\r\n");
		}
		out.flush();
		InputStream in = s.getInputStream();
		byte[] temp = new byte[64];
		byte[] result = new byte[0];
		int number = 0;
		while((number = in.read(temp)) > 0) {
			if(number == 64) {
				result = BytesCombineTool.append(result, temp);
			} else {
				result = BytesCombineTool.append(result, Arrays.copyOf(temp, number));
				break;
			}
		}
		System.out.println(DataInterconversionTool.bytesToString(result));
		out.close();
		in.close();
		s.close();
		return new HttpResponsePackage(result);
	}
	
	public HttpResponseContent sendHttpRequest() throws IOException, HttpRequestLineNoMethodException, 
	HttpRequestLineNoUrlException, HttpRequestLineNoVersionException, HttpRequestHeaderNoHostException {
		@SuppressWarnings("resource")
		Socket s = new Socket();
		SocketAddress socketAddress = new InetSocketAddress(this.hrh.getHost().substring(6), this.port);
	    s.connect(socketAddress);
		OutputStreamWriter out = new OutputStreamWriter(s.getOutputStream());
		out.write(this.hrl.generateRequestLine() + "\r\n");
		if(this.hrl.isGetMethod()) {
			if(this.hrh.getAccept() != null) {
				out.write(this.hrh.getAccept() + "\r\n");
			}
			if(this.hrh.getAccept_language() != null) {
				out.write(this.hrh.getAccept_language() + "\r\n");
			}
			if(this.hrh.getHost() != null) {
				out.write(this.hrh.getHost() + "\r\n");
			}
			if(this.hrh.getCache_control() != null) {
				out.write(this.hrh.getCache_control() + "\r\n");
			}
			if(this.hrh.getConnection() != null) {
				out.write(this.hrh.getConnection() + "\r\n");
			}
			if(this.hrh.getContent_lenth() != null) {
				out.write(this.hrh.getContent_lenth() + "\r\n");
			}
			if(this.hrh.getContent_type() != null) {
				out.write(this.hrh.getContent_type() + "\r\n");
			}
			if(this.hrh.getCookie() != null) {
				out.write(this.hrh.getCookie() + "\r\n");
			}
			if(this.hrh.getReferer() != null) {
				out.write(this.hrh.getReferer() + "\r\n");
			}
			if(this.hrh.getUser_agent() != null) {
				out.write(this.hrh.getUser_agent() + "\r\n");
			}
			out.write("\r\n");
		} else {
			if(this.hrh.getAccept() != null) {
				out.write(this.hrh.getAccept() + "\r\n");
			}
			if(this.hrh.getAccept_language() != null) {
				out.write(this.hrh.getAccept_language() + "\r\n");
			}
			if(this.hrh.getHost() != null) {
				out.write(this.hrh.getHost() + "\r\n");
			}
			if(this.hrh.getCache_control() != null) {
				out.write(this.hrh.getCache_control() + "\r\n");
			}
			if(this.hrh.getConnection() != null) {
				out.write(this.hrh.getConnection() + "\r\n");
			}
			if(this.hrh.getContent_lenth() != null) {
				out.write(this.hrh.getContent_lenth() + "\r\n");
			}
			if(this.hrh.getContent_type() != null) {
				out.write(this.hrh.getContent_type() + "\r\n");
			}
			if(this.hrh.getCookie() != null) {
				out.write(this.hrh.getCookie() + "\r\n");
			}
			if(this.hrh.getReferer() != null) {
				out.write(this.hrh.getReferer() + "\r\n");
			}
			if(this.hrh.getUser_agent() != null) {
				out.write(this.hrh.getUser_agent() + "\r\n");
			}
			if(this.hrb != null) {
				out.write("\r\n");
				switch(this.hrb.getFlag()) {
				case 1:
					break;
				case 2:
					out.write(this.hrb.getText());
					break;
				case 3:
					break;
				case 4:
					break;
				default:
					break;
				}
				}
			out.write("\r\n");
		}
		out.flush();
		InputStream in = s.getInputStream();
		//out.close();
		//s.close();
		return new HttpResponseContent(in);
	}
	
	public String toString() {
		StringBuffer request = new StringBuffer();
		try {
			request.append(this.hrl.generateRequestLine());
		} catch (HttpRequestLineNoMethodException e1) {
			e1.printStackTrace();
		} catch (HttpRequestLineNoUrlException e1) {
			e1.printStackTrace();
		} catch (HttpRequestLineNoVersionException e1) {
			e1.printStackTrace();
		}
		request.append("\n");
		try {
			request.append(this.hrh.generateRequestHeader());
		} catch (HttpRequestHeaderNoHostException e) {
			e.printStackTrace();
		}
		request.append("\n");
		if(this.hrb != null) {
			request.append(this.hrb.getText());
		}
		request.append("\n");
		return request.toString();
	}
	
}
