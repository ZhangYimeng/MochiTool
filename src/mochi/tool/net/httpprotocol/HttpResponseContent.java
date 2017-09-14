package mochi.tool.net.httpprotocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.stream.Stream;

public class HttpResponseContent {

	private BufferedReader br;
	private InputStream in;
	private Stream<String> stream;
	private Iterator<String> it;
	
	public HttpResponseContent(InputStream in) {
		this.in = in;
		br = new BufferedReader(new InputStreamReader(in));
		stream = br.lines();
		it = stream.iterator();
	}
	
	public boolean hasNext() {
		return it.hasNext();
	}
	
	public String readNext() throws IOException {
		return br.readLine();
	}
	
	public void close() throws IOException {
		in.close();
		br.close();
	}
		
}
