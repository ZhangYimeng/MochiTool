package mochi.tool.module.iotplatform.open.api.datatool;

public class MessageGenerator {

	private byte[] message;
	private short totalLength;
	private MessageBodyGenerator mb;
	private MessageHeadGenerator mh;
	
	public MessageGenerator(MessageHeadGenerator mh, MessageBodyGenerator mb) {
		this.mh = mh;
		this.mb = mb;
		message = new byte[0];
		totalLength = (short) (mh.getHeadLength() + mb.getBodyLength());
	}
	
	public byte[] getMessageBytes() {
		byte[] totalLengthBytes = DataInterconversionTool.shortToBytes(totalLength);
		message = BytesCombineTool.combineThreeBytes(totalLengthBytes, mh.getHeadBytes(), mb.getBodyBytes());
		return message;
	}
	
	public short getTotalLength() {
		return totalLength;
	}
	
}
