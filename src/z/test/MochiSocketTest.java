package z.test;

import java.io.IOException;
import java.net.UnknownHostException;

import mochi.tool.module.iotplatform.open.api.datatool.DataInterconversionTool;
import mochi.tool.net.socket.KeepAliveSocket;

public class MochiSocketTest {

	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		KeepAliveSocket kas = new KeepAliveSocket("221.131.113.45", 7715);
		while(true) {
			System.out.println(DataInterconversionTool.bytesToString(kas.fire(new MochiSocketTestMessageBullet())));
			Thread.sleep(5000);
		}
	}

}
