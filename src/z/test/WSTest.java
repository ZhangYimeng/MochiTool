package z.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import mochi.tool.module.iotplatform.open.api.datatool.DataInterconversionTool;

public class WSTest {

	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket ws = new Socket("cn.bing.com", 80);
		System.out.println(ws);
		String s = "GET / HTTP/1.1\n" + 
				"Host: cn.bing.com";
		System.out.println(s);
		InputStream in = ws.getInputStream();
		OutputStreamWriter dout = new OutputStreamWriter(ws.getOutputStream());
		dout.write(s);
		dout.flush();
		byte[] ss = new byte[1000];
		in.read(ss);
		System.out.println(DataInterconversionTool.bytesToString(ss));
		in.close();
		dout.close();
		ws.close();
	}

}
