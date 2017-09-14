package mochi.tool.net.httpserver.foundation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import mochi.tool.data.interconversion.DataInterconversionTool;

public class MochiHttpHandlerModel implements HttpHandler {
	
	public MochiHttpHandlerModel() {
		
	}

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		Headers headers = httpExchange.getRequestHeaders();
	    Set<Map.Entry<String, List<String>>> entries = headers.entrySet();

	    StringBuffer response = new StringBuffer();
	    for (Map.Entry<String, List<String>> entry : entries) {
	    	response.append("<p>" + entry.toString() + "</p>\n");
	    	System.out.println(entry.toString());
	    }
	    System.out.println("下面是response==================");
	    response.append("<p>Attribute:" + (String)httpExchange.getAttribute("") + "</p>\n");
	    response.append("<p>Method:" + httpExchange.getRequestMethod() + "</p>\n");
	    response.append("<p>Protocol:" + httpExchange.getProtocol() + "</p>\n");
	    response.append("<p>ResponseCode:" + httpExchange.getResponseCode() + "</p>\n");
	    response.append("<p>HttpContext:" + httpExchange.getHttpContext() + "</p>\n");
	    response.append("<p>LocalAddress:" + httpExchange.getLocalAddress() + "</p>\n");
	    response.append("<p>Principal:" + httpExchange.getPrincipal() + "</p>\n");
	    response.append("<p>RemoteAddress:" + httpExchange.getRemoteAddress() + "</p>\n");
	    InputStream in = httpExchange.getRequestBody();
	    //InputStreamReader isr = new InputStreamReader(in, "utf-8");
	    //BufferedReader br = new BufferedReader(isr);
	    //System.out.println(br.readLine());
	    if(httpExchange.getRequestMethod().equals("POST")) {
	    	byte[] b = new byte[12];
	    	in.read(b);
	    	System.out.println("body:" + DataInterconversionTool.bytesToString(b));
	    }
	    httpExchange.sendResponseHeaders(200, response.length());
	    OutputStream os = httpExchange.getResponseBody();
	    os.write(response.toString().getBytes());
	    os.close();
	    System.out.println("handler is finished!");
	}

}
