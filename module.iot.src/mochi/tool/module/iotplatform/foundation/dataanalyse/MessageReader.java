package mochi.tool.module.iotplatform.foundation.dataanalyse;

import java.util.Arrays;

public class MessageReader {

	private short totalLength;
	private byte[] bytes;
	
	public MessageReader(short totalLength, byte[] bytes) {
		this.totalLength = totalLength;
		this.bytes = bytes;
	}
	
	public MessageHead getHead() {
		byte[] head = Arrays.copyOfRange(bytes, 0, DataConfig.HEAD_LENGTH - 2);
		MessageHead mh = new MessageHead(totalLength, head);
		return mh;
	}
	
	public MessageBody getBody() {
		byte[] body = Arrays.copyOfRange(bytes, DataConfig.HEAD_LENGTH - 2, bytes.length);
		MessageBody mb = new MessageBody(body);
		return mb;
	}
	
}
