package mochi.tool.net.httpprotocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Deprecated
public class HttpResponseContent {

	private BufferedReader br;
	private InputStream in;
	
	public HttpResponseContent(InputStream in) throws IOException {
		this.in = in;
		br = new BufferedReader(new InputStreamReader(in));
	}
	
	public String readNextLine() throws IOException {
		return br.readLine();
	}
	
	public void close() throws IOException {
		br.close();
		in.close();
		System.out.println("连接关闭!");
	}
		
}
