package mochi.tool.net.httpprotocol;

import java.io.File;
import java.io.InputStream;

import mochi.tool.data.interconversion.DataInterconversionTool;

/**
 * @author saito
 *
 */
public class HttpRequestBody {

	private File file;
	private String text;
	private InputStream in;
	private byte[] bytes;
	private short flag;
	
	public HttpRequestBody(File file) {
		this.file = file;
		this.flag = 1;
	}
	
	public HttpRequestBody(String text) {
		this.text = text;
		this.flag = 2;
	}
	
	public HttpRequestBody(InputStream in) {
		this.in = in;
		this.flag = 3;
	}
	
	public HttpRequestBody(byte[] bytes) {
		this.bytes = bytes;
		this.flag = 4;
	}

	public File getFile() {
		return file;
	}

	public String getText() {
		return text;
	}

	public InputStream getIn() {
		return in;
	}
	
	public byte[] getBytes() {
		return bytes;
	}
	
	/**
	 * 1对应file，2对应text，3对应inputstream，4对应bytes。
	 * @return flag的值。
	 */
	public short getFlag() {
		return this.flag;
	}
	
	public byte[] getBody() {
		switch(this.flag) {
		case 1:
			return null;
		case 2:
			return DataInterconversionTool.stringToBytes(this.text);
		case 3:
			return null;
		case 4:
			return null;
		default:
			return null;
		}
	}

}
