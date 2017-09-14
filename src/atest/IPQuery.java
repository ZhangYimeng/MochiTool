package atest;

import java.io.IOException;
import java.net.URLDecoder;

import mochi.tool.net.httpprotocol.HttpRequestHeader;
import mochi.tool.net.httpprotocol.HttpRequestLine;
import mochi.tool.net.httpprotocol.HttpRequestLineFieldsInfo;
import mochi.tool.net.httpprotocol.HttpRequestSender;
import mochi.tool.net.httpprotocol.HttpResponseContent;
import mochi.tool.net.httpprotocol.exception.HttpRequestHeaderNoHostException;
import mochi.tool.net.httpprotocol.exception.HttpRequestLineNoMethodException;
import mochi.tool.net.httpprotocol.exception.HttpRequestLineNoUrlException;
import mochi.tool.net.httpprotocol.exception.HttpRequestLineNoVersionException;

public class IPQuery {

	public static void main(String[] args) throws IOException, HttpRequestLineNoMethodException, HttpRequestLineNoUrlException, HttpRequestLineNoVersionException, HttpRequestHeaderNoHostException {
		HttpRequestLine hrl = new HttpRequestLine();
		hrl.setMethod("GET");
		hrl.setUrl("/service/getIpInfo.php?ip=117.57.235.235");
		hrl.setVersion(HttpRequestLineFieldsInfo.HTTP_1_1);
		HttpRequestHeader hrh = new HttpRequestHeader();
		hrh.setHost("Host: ip.taobao.com");
		//hrh.setUser_agent(HttpRequestHeaderInfo.User_Agent_Zhang_Yimeng);
		//hrh.setAccept(HttpRequestHeaderInfo.Accept_text_plain);
		HttpRequestSender hrs = new HttpRequestSender(hrl, hrh, null);
		HttpResponseContent hrc = hrs.sendHttpRequest();
		String line = new String();
		while((line = hrc.readNext()) != null) {
			System.out.println(URLDecoder.decode(line, "Unicode"));
		}
	}

}
