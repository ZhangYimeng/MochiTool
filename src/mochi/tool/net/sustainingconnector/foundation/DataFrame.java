package mochi.tool.net.sustainingconnector.foundation;

import mochi.tool.data.bytescombinetool.BytesCombineTool;
import mochi.tool.data.interconversion.DataInterconversionTool;

public class DataFrame {
	
	private byte[] lengthIntegrant = new byte[2];
	private byte[] dataIntegrant;
	
	public DataFrame() {
		
	}
	
	public DataFrame(byte[] dataIntegrant) {
		this.dataIntegrant = dataIntegrant;
	}
	
	public byte[] getDataBytes() {
		lengthIntegrant = DataInterconversionTool.shortToBytes((short) dataIntegrant.length);
		return BytesCombineTool.append(lengthIntegrant, dataIntegrant);
	}
	
	public void setDataIntegrant(byte[] dataIntegrant) {
		this.dataIntegrant = dataIntegrant;
	}

}
