package z.test;

import java.io.IOException;

import mochi.tool.net.httpserver.MochiHttpServer;
import mochi.tool.net.httpserver.foundation.MochiPathAndHttpHandler;

public class HTTP实现相关测试 {

	public static void main(String[] args) {
		MochiHttpServer mochi = null;
		try {
			mochi = new MochiHttpServer(80, 100);
			CustomHandler ch = new CustomHandler();
			MochiPathAndHttpHandler mph = new MochiPathAndHttpHandler("/receivefile", ch);
			mochi.setHandler(mph);
			mochi.start();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		/*try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		HttpClientMochi hcm = new HttpClientMochi("http://127.0.0.1:8080/receivefile", "POST");
		hcm.sendRequest();*/
	}

}
