package mochi.tool.data.bytescombinetool;

import java.util.Arrays;

public class BytesCombineTool {
	
	/*public static byte[] append(byte[] bytesA, byte[] bytesB) {
		int length = bytesA.length + bytesB.length;
		byte[] temp = new byte[length];
		System.arraycopy(bytesA, 0, temp, 0, bytesA.length);
		System.arraycopy(bytesB, 0, temp, bytesA.length, bytesB.length);
		return temp;
	}*/
	
	public static byte[] append(byte[] bytesA, byte[] bytesB) {
		byte[] bytesTemp = Arrays.copyOf(bytesA, bytesA.length + bytesB.length);
		System.arraycopy(bytesB, 0, bytesTemp, bytesA.length, bytesB.length);
		return bytesTemp;
	}
	
	/*public static byte[] combineThreeBytes(byte[] bytesA, byte[] bytesB, byte[] bytesC) {
		int length = bytesA.length + bytesB.length + bytesC.length;
		byte[] temp = new byte[length];
		System.arraycopy(bytesA, 0, temp, 0, bytesA.length);
		System.arraycopy(bytesB, 0, temp, bytesA.length, bytesB.length);
		System.arraycopy(bytesC, 0, temp, bytesA.length + bytesB.length, bytesC.length);
		return temp;
	}*/
	
	public static byte[] combineThreeBytes(byte[] bytesA, byte[] bytesB, byte[] bytesC) {
		byte[] bytesTemp = Arrays.copyOf(bytesA, bytesA.length + bytesB.length + bytesC.length);
		System.arraycopy(bytesB, 0, bytesTemp, bytesA.length, bytesB.length);
		System.arraycopy(bytesC, 0, bytesTemp, bytesA.length + bytesB.length, bytesC.length);
		return bytesTemp;
	}
	
}
