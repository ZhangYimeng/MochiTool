package mochi.tool.module.iotplatform.foundation.dataanalyse;

import java.util.Arrays;

import mochi.tool.data.interconversion.DataInterconversionTool;
import mochi.tool.module.iotplatform.foundation.dataanalyse.exception.MessageProtocolException;

public class MessageBody {

	private byte[] body;
	private int index;
	
	public MessageBody(byte[] body) {
		this.body = body;
		index = 0;
	}
	
	public boolean hasNextTLV() {
		if(index < body.length) {
			return true;
		} else {
			return false;
		}
	}
	
	public TLV nextTVL() throws MessageProtocolException {
		try {
			short tag = DataInterconversionTool.bytesToShort(Arrays.copyOfRange(body, index, index + DataConfig.TAG_LENGTH));
			index += DataConfig.TAG_LENGTH;
			short length = DataInterconversionTool.bytesToShort(Arrays.copyOfRange(body, index, index + DataConfig.LENGTH_LENGTH));
			index += DataConfig.LENGTH_LENGTH;
			byte[] value = Arrays.copyOfRange(body, index, index + length);
			index += length;
			return new TLV(tag, length, value);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new MessageProtocolException();
		}
	}
	
}
