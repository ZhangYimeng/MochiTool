package z.test;

import java.io.IOException;
import java.net.UnknownHostException;

import mochi.tool.module.iotplatform.open.api.datatool.DataInterconversionTool;
import mochi.tool.net.sustainingconnector.MinionClient;
import mochi.tool.net.sustainingconnector.foundation.DataFrame;

public class 长连接客户端发送测试 {

	public static void main(String[] args) {		
		try {
			MinionClient mc = new MinionClient("127.0.0.1", 8888);
			System.out.println(mc.connect());
			mc.openHeartBeat();
			for(int i = 0; i < 5; i++) {
				String s = "消息序列" + i;
				System.out.println(s);
				DataFrame df = new DataFrame();
				df.setDataIntegrant(DataInterconversionTool.stringToBytes(s));
				System.out.println(DataInterconversionTool.bytesToString(mc.sendData(df)));
				Thread.sleep(3000);
			}
			System.out.println(mc.disconnect());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
