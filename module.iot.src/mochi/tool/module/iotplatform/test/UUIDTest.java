package mochi.tool.module.iotplatform.test;

import java.util.Random;

import mochi.tool.data.interconversion.DataInterconversionTool;

public class UUIDTest {

	public static void main(String[] args) {
		byte[] b = {generateByte(), generateByte(), generateByte() ,generateByte() ,generateByte(), generateByte(), generateByte(), generateByte() ,generateByte() ,generateByte(), generateByte()};
		System.out.println(DataInterconversionTool.bytesToString(b));
		Random random = new Random();
		System.out.println((random.nextInt(2)%2 != 0? 65: 48));
		//System.out.println(GatewayDevice.generateDeviceID());
		//System.out.println(GatewayDevice.generateDeviceID());
		//System.out.println(GatewayDevice.generateDeviceID());
		//System.out.println(GatewayDevice.generateDeviceID());
		//System.out.println(GatewayDevice.generateDeviceID());
		//System.out.println(GatewayDevice.generateDeviceID());
		//System.out.println(GatewayDevice.generateDeviceID());
		//System.out.println(GatewayDevice.generateDeviceID());
		//System.out.println(GatewayDevice.generateDeviceID());
		//System.out.println(GatewayDevice.generateDeviceID());
		//System.out.println(GatewayDevice.generateDeviceID());
		//System.out.println(GatewayDevice.generateDeviceID());
		Object o = new String("asdasd");
		System.out.println(o);
	}
	
	public static byte generateByte() {
		Random random = new Random();
		return (byte) (random.nextInt(26) + 65);
	}

}
