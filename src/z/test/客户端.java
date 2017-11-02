package z.test;

import java.io.IOException;
import java.net.UnknownHostException;

import mochi.tool.net.sustainingconnector.MinionClient;

public class 客户端 {

	public static void main(String[] args) {
		try {
			MinionClient mc = new MinionClient("127.0.0.1", 8881);
			System.out.println(mc.connect());
			mc.openHeartBeat();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
