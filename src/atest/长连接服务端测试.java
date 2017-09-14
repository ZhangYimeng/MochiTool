package atest;

import mochi.tool.net.sustainingconnector.MasterServer;

public class 长连接服务端测试 {

	public static void main(String[] args) {
		MasterServer ms = new MasterServer(8888, "atest.DataHandlerImplement");
		ms.start();
	}
	
}
