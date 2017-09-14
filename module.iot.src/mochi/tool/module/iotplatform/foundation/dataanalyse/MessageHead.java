package mochi.tool.module.iotplatform.foundation.dataanalyse;

import java.util.Arrays;

import mochi.tool.data.interconversion.DataInterconversionTool;

public class MessageHead {
	
	private byte[] bytes;
	private short length;
	
	public MessageHead(short length, byte[] bytes) {
		this.bytes = bytes;
		this.length = length;
	}
	
	public short getMessageLength() {
		return length;
	}
	
	public short getCommandID() {
		short commandid = DataInterconversionTool.bytesToShort(Arrays.copyOfRange(bytes, 0, DataConfig.COMMANDID_LENGTH));
		return commandid;
	}

	public long getTime() {
		long time = DataInterconversionTool.bytesToLong(Arrays.copyOfRange(bytes, DataConfig.COMMANDID_LENGTH, bytes.length));
		return time;
	}
	
	public byte[] getHeadBytes() {
		return bytes;
	}
	
}
