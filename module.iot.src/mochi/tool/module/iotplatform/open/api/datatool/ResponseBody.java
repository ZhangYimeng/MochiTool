package mochi.tool.module.iotplatform.open.api.datatool;

import java.util.Arrays;

import mochi.tool.module.iotplatform.open.api.exception.BodyLengthZeroException;

public class ResponseBody {

	private byte[] body;
	private int index;
	private TLVBlock current;
	private boolean flag = true;
	
	public ResponseBody(byte[] body) throws BodyLengthZeroException {
		this.body = body;
		index = 0;
		if(body.length == 0) {
			throw new BodyLengthZeroException();
		}
	}
	
	public boolean next() {
		if(flag == false) {
			return false;
		} else {
			current = new TLVBlock(Arrays.copyOfRange(this.body, index, 
					index + DataConfig.TAG_LENGTH + DataConfig.LENGTH_LENGTH + 
					DataInterconversionTool.bytesToShort(Arrays.copyOfRange(this.body, index + DataConfig.TAG_LENGTH, 
							index + DataConfig.TAG_LENGTH + DataConfig.LENGTH_LENGTH))));
			index += DataConfig.TAG_LENGTH + DataConfig.LENGTH_LENGTH + current.getLength();
			if(index < body.length) {
				flag = true;
			} else {
				flag = false;
			}
			return true;
		}
	}
	
	public short getCurrentTag() {
		return current.getTag();
	}
	
	public short getCurrentLength() {
		return current.getLength();
	}
	
	public byte[] getCurrentValue() {
		return current.getValue();
	}
	
}
