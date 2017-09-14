package mochi.tool.module.iotplatform.open.api.datatool;

import java.util.Arrays;

import mochi.tool.module.iotplatform.open.api.exception.BodyLengthZeroException;

public class MessageResponse {
	
	public short totalLength;
	public byte[] response;
	
	public MessageResponse(short totalLength, byte[] response) {
		this.response = response;
		this.totalLength = totalLength;
	}
	
	public ResponseHead getResponseHead() {
		return new ResponseHead(totalLength, Arrays.copyOfRange(response, 0, DataConfig.HEAD_LENGTH - 2));
	}
	
	public ResponseBody getResponseBody() throws BodyLengthZeroException {
		return new ResponseBody(Arrays.copyOfRange(response, DataConfig.HEAD_LENGTH - 2, response.length));
	}

}
