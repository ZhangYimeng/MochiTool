package z.test;

import java.io.DataOutputStream;
import java.io.IOException;

import mochi.tool.module.iotplatform.open.api.datatool.DataInterconversionTool;
import mochi.tool.net.sustainingconnector.foundation.DataFrame;
import mochi.tool.net.sustainingconnector.foundation.DataHandlerInterface;

public class DataHandlerImplement implements DataHandlerInterface {
	
	private byte[] data;
	private DataOutputStream replier;

	public DataHandlerImplement() {
		
	}
	
	@Override
	public void run() {
		System.out.println(DataInterconversionTool.bytesToString(this.data));
		DataFrame df = new DataFrame(DataInterconversionTool.stringToBytes("这是给你的答复!"));
		try {
			this.replier.write(df.getDataBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setBytesData(byte[] data) {
		this.data = data;
	}

	@Override
	public void setReplier(DataOutputStream replier) {
		this.replier = replier;
	}

}
