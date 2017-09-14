/**
 * 
 */
package atest;

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

/**
 * @author saito
 *
 */
public class CustomHandler implements HttpHandler {

	public CustomHandler() {
		
	}

	@Override
	public void handle(HttpExchange he) throws IOException {
		System.out.println(he);
		InputStream in = he.getRequestBody();
		byte[] b = new byte[1024];
		in.read(b);
		System.out.println(DataInterconversionTool.bytesToString(b));
		Headers headers = he.getRequestHeaders();
	    Set<Map.Entry<String, List<String>>> entries = headers.entrySet();

	    StringBuffer response = new StringBuffer();
	    for (Map.Entry<String, List<String>> entry : entries) {
	    	response.append("<p>" + entry.toString() + "</p>");
	    	System.out.println(entry.toString());
	    }
	    InputStream bodyInput = he.getRequestBody();
	    byte[] c = new byte[65536];
	    bodyInput.read(c);
	    for(byte d: c) {
	    	System.out.print(d + " ");
	    }
	    he.sendResponseHeaders(200, response.length());
	    OutputStream os = he.getResponseBody();
	    os.write(response.toString().getBytes());
	    os.close();
	}
	
	
	
}
