package z.test;

import mochi.tool.module.iotplatform.open.api.datatool.DataInterconversionTool;
import mochi.tool.net.socket.MessageBullet;

public class MochiSocketTestMessageBullet implements MessageBullet{

	@Override
	public byte[] toBytes() {
		return DataInterconversionTool.stringToBytes("你好服务器！");
	}


}
