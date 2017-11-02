package z.test;

import mochi.tool.net.sustainingconnector.MasterServer;

public class 服务端1 {

	public static void main(String[] args) {
		MasterServer ms = new MasterServer(8881, "atest.DataHandlerImplement");
		ms.start();
	}

}
