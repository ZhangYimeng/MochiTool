package z.test;

import mochi.tool.module.iotplatform.open.api.datatool.DataInterconversionTool;
import mochi.tool.net.server.socket.SocketHandlerInterface;

public class MochiSocketServerTestHandler implements SocketHandlerInterface {

	@Override
	public byte[] handle(byte[] data) {
		System.out.println(DataInterconversionTool.bytesToString(data));
		return DataInterconversionTool.stringToBytes("我都到了你的信息，这是你的反馈信息！");
	}

	@Override
	public void whenConnectionClose() {
		
	}

}
