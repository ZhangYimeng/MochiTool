package mochi.tool.module.iotplatform.foundation.dataanalyse;

import java.util.Iterator;
import java.util.LinkedList;

import mochi.tool.data.interconversion.DataInterconversionTool;
import mochi.tool.module.iotplatform.open.api.datatool.BytesCombineTool;

public class ResponseGenerator {

	private MessageHead mh;
	private byte[] body;
	private byte[] response;
	
	public ResponseGenerator(MessageHead mh) {
		this.mh = mh;
		body = new byte[0];
		response = new byte[0];
	}
	
	public byte[] generateResponseMessage(LinkedList<byte[][]> responseBody) {
		Iterator<byte[][]> it = responseBody.iterator();
		while(it.hasNext()) {
			byte[][] os = it.next();
			byte[] tlv = BytesCombineTool.combineThreeBytes(os[0], 
					DataInterconversionTool.shortToBytes((short) os[1].length), os[1]);
			body = BytesCombineTool.append(body, tlv);
		}
		response = BytesCombineTool.combineThreeBytes(DataInterconversionTool.shortToBytes((short) (DataConfig.HEAD_LENGTH + body.length)), 
				mh.getHeadBytes(), body);
		return response;
	}
	
}
