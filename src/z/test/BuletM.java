package z.test;

import mochi.tool.net.socket.MessageBullet;

public class BuletM implements MessageBullet {

	@Override
	public byte[] toBytes() {
		return "1".getBytes();
	}

}
