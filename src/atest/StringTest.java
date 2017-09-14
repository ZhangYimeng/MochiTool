package atest;

import java.io.UnsupportedEncodingException;

import mochi.tool.data.interconversion.DataInterconversionTool;

public class StringTest {

	public static void main(String[] args) throws UnsupportedEncodingException {
		
		short lenth = 4;
		int val = 786;
		
		byte[] lb = DataInterconversionTool.shortToBytes(lenth);
		for(byte b: lb) {
			System.out.println(Integer.toHexString(b));
		}
		
		byte[] vb = DataInterconversionTool.intToBytes(val);
		for(byte b: vb) {
			System.out.println(Integer.toHexString(b));
		}
		System.out.println("=====");
		short lenth1 = 8;
		long val1 = 298898998;
		
		lb = DataInterconversionTool.shortToBytes(lenth1);
		for(byte b: lb) {
			System.out.println(Integer.toHexString(b));
		}
		
		vb = DataInterconversionTool.longToBytes(val1);
		for(byte b: vb) {
			System.out.println(Integer.toHexString(b));
		}
		System.out.println("=====");
		short lenth2 = 2;
		short val2 = 564;
		
		lb = DataInterconversionTool.shortToBytes(lenth2);
		for(byte b: lb) {
			System.out.println(Integer.toHexString(b));
		}
		
		vb = DataInterconversionTool.shortToBytes(val2);
		for(byte b: vb) {
			System.out.println(Integer.toHexString(b));
		}
		System.out.println("=====");
		short lenth3 = 2;
		String val3 = "eu";
		
		lb = DataInterconversionTool.shortToBytes(lenth3);
		for(byte b: lb) {
			System.out.println(Integer.toHexString(b));
		}
		
		vb = DataInterconversionTool.stringToBytes(val3);
		for(byte b: vb) {
			System.out.println(Integer.toHexString(b));
		}
		
		System.out.println("------------");
		String val4 = "eu";
		vb = DataInterconversionTool.stringToBytes(val4);
		for(byte b: vb) {
			System.out.println(Integer.toHexString(b));
		}
		
		System.out.println("=====");
		val4 = "luxx";
		vb = DataInterconversionTool.stringToBytes(val4);
		for(byte b: vb) {
			System.out.println(Integer.toHexString(b));
		}
		System.out.println("=====");
		long val5 = 154666;
		vb = DataInterconversionTool.longToBytes(val5);
		for(byte b: vb) {
			System.out.println(Integer.toHexString(b));
		}
		System.out.println("=====");
		short val6 = 6634;
		vb = DataInterconversionTool.shortToBytes(val6);
		for(byte b: vb) {
			System.out.println(Integer.toHexString(b));
		}
		System.out.println("---------------");
		String val7 = ",";
		vb = DataInterconversionTool.stringToBytes(val7);
		for(byte b: vb) {
			System.out.println(Integer.toHexString(b));
		}
	}

}
