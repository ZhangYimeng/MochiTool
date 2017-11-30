package z.test;

import java.io.IOException;

import mochi.tool.net.httpprotocol.exception.HttpRequestHeaderNoHostException;
import mochi.tool.net.httpprotocol.exception.HttpRequestLineNoMethodException;
import mochi.tool.net.httpprotocol.exception.HttpRequestLineNoUrlException;
import mochi.tool.net.httpprotocol.exception.HttpRequestLineNoVersionException;
import mochi.tool.net.newhttpprotocol.HttpRequestHeader;
import mochi.tool.net.newhttpprotocol.HttpRequestLine;
import mochi.tool.net.newhttpprotocol.HttpRequestLineFieldsInfo;
import mochi.tool.net.newhttpprotocol.HttpRequestSender;
import mochi.tool.net.newhttpprotocol.HttpResponseContent;

public class NewHTTPProtocolTest {

	public static void main(String[] args) throws IOException, HttpRequestLineNoMethodException, HttpRequestLineNoUrlException, HttpRequestLineNoVersionException, HttpRequestHeaderNoHostException {
		HttpRequestLine hrl = new HttpRequestLine();
		hrl.setMethod(HttpRequestLineFieldsInfo.GET);
		hrl.setUrl("/");
		hrl.setVersion(HttpRequestLineFieldsInfo.HTTP_1_1);
		
		HttpRequestHeader hrh = new HttpRequestHeader();
		hrh.setHost("www.baidu.com");
		
		HttpRequestSender hrs = new HttpRequestSender(hrl, hrh, null);
		HttpResponseContent hrc = hrs.sendHttpRequest();
		String line = new String();
		while((line = hrc.readNextLine()) != null) {
			System.out.println(line);
		}
		hrc.close();
	}

}
