package mochi.tool.module.iotplatform.open.api.datatool;

public class MessageBodyGenerator {
	
	private byte[] body;
	
	public MessageBodyGenerator() {
		body = new byte[0];
	}
	
	public void append(short tag, byte[] value) {
		byte[] t = DataInterconversionTool.shortToBytes(tag);
		byte[] l = DataInterconversionTool.shortToBytes((short) value.length);
		byte[] v = value;
		body = BytesCombineTool.append(body, BytesCombineTool.combineThreeBytes(t, l, v));
	}

	public short getBodyLength() {
		return (short) body.length;
	}
	
	public byte[] getBodyBytes() {
		return body;
	}
	
}
