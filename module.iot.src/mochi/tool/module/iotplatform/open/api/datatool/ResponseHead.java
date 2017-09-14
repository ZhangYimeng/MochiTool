package mochi.tool.module.iotplatform.open.api.datatool;

import java.util.Arrays;

public class ResponseHead {

	private byte[] head;
	private short totalLength;
	
	public ResponseHead(short totalLength, byte[] head) {
		this.head = head;
		this.totalLength = totalLength;
	}
	
	public short getTotalLength() {
		return totalLength;
	}
	
	public short getCommandID() {
		return DataInterconversionTool.bytesToShort(Arrays.copyOfRange(head, 0, DataConfig.COMMANDID_LENGTH));
	}
	
	public long getTime() {
		return DataInterconversionTool.bytesToLong(Arrays.copyOfRange(head, DataConfig.COMMANDID_LENGTH, head.length));
	}
	
}
