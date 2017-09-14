package mochi.tool.module.iotplatform.open.api.datatool;

import java.util.Arrays;

public class TLVBlock {

	private byte[] tlvBlock;
	private short tag;
	private short length;
	private byte[] value;
	
	public TLVBlock(byte[] tlvBlock) {
		this.tlvBlock = tlvBlock;
		tag = DataInterconversionTool.bytesToShort(Arrays.copyOfRange(this.tlvBlock, 0, DataConfig.TAG_LENGTH));
		length = DataInterconversionTool.bytesToShort(Arrays.copyOfRange(this.tlvBlock, DataConfig.TAG_LENGTH, DataConfig.TAG_LENGTH + DataConfig.LENGTH_LENGTH));
		value = Arrays.copyOfRange(this.tlvBlock, DataConfig.TAG_LENGTH + DataConfig.LENGTH_LENGTH, this.tlvBlock.length);
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
