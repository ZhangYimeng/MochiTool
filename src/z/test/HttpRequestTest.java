package z.test;

import java.io.IOException;
import java.net.UnknownHostException;

import mochi.tool.net.httpprotocol.HttpRequestBody;
import mochi.tool.net.httpprotocol.HttpRequestHeader;
import mochi.tool.net.httpprotocol.HttpRequestHeaderInfo;
import mochi.tool.net.httpprotocol.HttpRequestLine;
import mochi.tool.net.httpprotocol.HttpRequestLineFieldsInfo;
import mochi.tool.net.httpprotocol.HttpRequestSender;
import mochi.tool.net.httpprotocol.exception.HttpRequestHeaderNoHostException;
import mochi.tool.net.httpprotocol.exception.HttpRequestLineNoMethodException;
import mochi.tool.net.httpprotocol.exception.HttpRequestLineNoUrlException;
import mochi.tool.net.httpprotocol.exception.HttpRequestLineNoVersionException;

public class HttpRequestTest {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws UnknownHostException, IOException, HttpRequestLineNoMethodException, HttpRequestLineNoUrlException, HttpRequestLineNoVersionException, HttpRequestHeaderNoHostException {
		HttpRequestLine hrl = new HttpRequestLine(HttpRequestLineFieldsInfo.POST, "/Gateway/receivedevice", HttpRequestLineFieldsInfo.HTTP_1_1);
		HttpRequestHeader hrh = new HttpRequestHeader();
		hrh.setAccept_encoding(HttpRequestHeaderInfo.Accept_encoding_gzip_deflate);
		hrh.setAccept(HttpRequestHeaderInfo.Accept_text_html_utf_8);
		hrh.setConnection(HttpRequestHeaderInfo.Connection_keep_alive);
		hrh.setHost("Host: 127.0.0.1");
		hrh.setUser_agent(HttpRequestHeaderInfo.User_Agent_Zhang_Yimeng);
		hrh.setAccept_language(HttpRequestHeaderInfo.Accept_Language_zh_CN);
		hrh.setContent_type(HttpRequestHeaderInfo.Content_Type_application_x_www_form_urlencoded);
		hrh.setContent_lenth(HttpRequestHeaderInfo.Content_Length + "11");
		HttpRequestBody hrb = new HttpRequestBody("zhangyimeng");
		HttpRequestSender hrs = new HttpRequestSender(hrl, hrh, hrb, 80);
		System.out.println(hrs.toString());
		hrs.sendRequest();
		
		/*Socket s = new Socket("localhost", 80);
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
		pw.println("POST /zym HTTP/1.1" +"\r\n"+ "Host: localhost" +"\r\n"+ "Content-Type: text/html" + "\r\n\r\n" + "qwertyui");
		pw.println();
		pw.flush();*/
		/*Socket s = new Socket("localhost", 80);
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
		pw.println("POST /Gateway/devicelist.jsp HTTP/1.1" +"\r\n"+ "Host: localhost" +"\r\n"+ "Content-Type: text/html" + "\r\n\r\n" + "qwertyui");
		pw.println();
		pw.flush();*/
		
		/*Socket s = new Socket();
		SocketAddress dest = new InetSocketAddress("localhost", 80);  
        s.connect(dest);  
        OutputStreamWriter streamWriter = new OutputStreamWriter(s.getOutputStream(), "utf-8");  
        BufferedWriter out = new BufferedWriter(streamWriter);  
          
        out.write("POST " + "/zym" + " HTTP/1.1\r\n");
        out.write("Host: " + "localhost" + "\r\n");
        out.write("Content-Length: " + 5 + "\r\n");
        out.write("Content-Type: application/x-www-form-urlencoded\r\n");
        out.write("\r\n");
        out.write("zzzzz");
        out.flush();
        out.write("\r\n");
        out.flush();

		BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		br.close();
		out.close();
		s.close();*/
		
		
//		out.write("POST " + "/zym" + " HTTP/1.1\r\n");
//        out.write("Host: " + "localhost" + "\r\n");
//        out.write("Content-Length: " + 5 + "\r\n");
//        out.write("Content-Type: application/x-www-form-urlencoded\r\n");
//        out.write("\r\n");
//        out.write("zzzzz");
//        out.flush();
//        out.write("\r\n");
//        out.flush();
		/*Socket s = new Socket();
		SocketAddress socketAddress = new InetSocketAddress(hrh.getHost().substring(6), 80);  
        s.connect(socketAddress);
		OutputStreamWriter out = new OutputStreamWriter(s.getOutputStream());
		out.write(hrl.generateRequestLine() + "\r\n");
		out.write(hrh.getHost() + "\r\n");
		out.write(hrh.getContent_lenth() + "\r\n");
		out.write(hrh.getContent_type() + "\r\n");
		out.write("\r\n");
		System.out.println(hrb.getText());
		out.write(hrb.getText());
		out.write("\r\n");
		out.flush();
		BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		while(br.ready()) {
			System.out.println(br.readLine());
		}
		out.close();
		br.close();
		s.close();*/
		
	}

}
