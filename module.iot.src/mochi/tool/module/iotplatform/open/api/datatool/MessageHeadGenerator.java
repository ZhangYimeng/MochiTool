package mochi.tool.module.iotplatform.open.api.datatool;

public class MessageHeadGenerator {

	private byte[] head;
	
	public MessageHeadGenerator() {
		head = new byte[0];
	}
	
	public void generate(short commandid, long time) {
		head = BytesCombineTool.append(DataInterconversionTool.shortToBytes(commandid), DataInterconversionTool.longToBytes(time));
	}
	
	public short getHeadLength() {
		return (short) DataConfig.HEAD_LENGTH;
	}
	
	public byte[] getHeadBytes() {
		return head;
	}
	
}
