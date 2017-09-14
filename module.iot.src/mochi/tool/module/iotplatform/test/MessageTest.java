package mochi.tool.module.iotplatform.test;

import java.util.Arrays;

import mochi.tool.data.interconversion.DataInterconversionTool;
import mochi.tool.module.iotplatform.foundation.dataanalyse.MessageBody;
import mochi.tool.module.iotplatform.foundation.dataanalyse.MessageHead;
import mochi.tool.module.iotplatform.foundation.dataanalyse.MessageReader;
import mochi.tool.module.iotplatform.foundation.dataanalyse.TLV;
import mochi.tool.module.iotplatform.foundation.dataanalyse.exception.MessageProtocolException;
import mochi.tool.module.iotplatform.open.api.datatool.MessageBodyGenerator;
import mochi.tool.module.iotplatform.open.api.datatool.MessageGenerator;
import mochi.tool.module.iotplatform.open.api.datatool.MessageHeadGenerator;

public class MessageTest {

	public static void main(String[] args) throws MessageProtocolException {
		MessageHeadGenerator mhg = new MessageHeadGenerator();
		mhg.generate((short) 0x4001, System.currentTimeMillis());
		MessageBodyGenerator mbg = new MessageBodyGenerator();
		mbg.append((short) 0x0001, DataInterconversionTool.stringToBytes("zhangyimeng"));
		mbg.append((short) 0x0002, DataInterconversionTool.longToBytes(19890715));
		MessageGenerator mg = new MessageGenerator(mhg, mbg);
		byte[] message = mg.getMessageBytes();
		for(byte b: message) {
			System.out.print(b + " ");
		}
		System.out.println();
		System.out.println(message.length);
		MessageReader mr = new MessageReader((short) message.length, Arrays.copyOfRange(message, 2, message.length));
		MessageHead mh = mr.getHead();
		System.out.println(mh.getMessageLength());
		System.out.println(mh.getCommandID());
		System.out.println(mh.getTime());
		MessageBody mb = mr.getBody();
		while(mb.hasNextTLV()) {
			TLV tlv = mb.nextTVL();
			System.out.println(tlv.getTag());
			System.out.println(tlv.getLength());
			System.out.println(DataInterconversionTool.bytesToString(tlv.getValue()));
			System.out.println(DataInterconversionTool.bytesToLong(tlv.getValue()));
			System.out.println("-------------------");
		}
	}

}
