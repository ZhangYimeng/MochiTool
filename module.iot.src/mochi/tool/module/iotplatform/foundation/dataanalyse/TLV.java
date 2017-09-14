package mochi.tool.module.iotplatform.foundation.dataanalyse;

public class TLV {
	
	private short tag;
	private short length;
	private byte[] value;
	
	public TLV(short tag, short length, byte[] value) {
		this.tag = tag;
		this.length = length;
		this.value = value;
	}

	public short getTag() {
		return tag;
	}
	
	public short getLength() {
		return length;
	}
	
	public byte[] getValue() {
		return value;
	}
	
}
