package mochi.tool.net.newhttpprotocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class HttpResponseContent {

	private BufferedReader br;
	private InputStream in;
	private Socket s;
	
	public HttpResponseContent(Socket s) throws IOException {
		in = s.getInputStream();
		br = new BufferedReader(new InputStreamReader(in));
		this.s = s;
	}
	
	public String readNextLine() throws IOException {
		return br.readLine();
	}
	
	public void close() throws IOException {
		br.close();
		in.close();
		s.close();
		System.out.println("连接关闭!");
	}
		
}
