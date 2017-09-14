package mochi.tool.module.iotplatform.open.api.datatool;

import java.util.Arrays;

public class BytesCombineTool {
	
	public static byte[] append(byte[] bytesA, byte[] bytesB) {
		byte[] bytesTemp = Arrays.copyOf(bytesA, bytesA.length + bytesB.length);
		System.arraycopy(bytesB, 0, bytesTemp, bytesA.length, bytesB.length);
		return bytesTemp;
	}
	
	public static byte[] combineThreeBytes(byte[] bytesA, byte[] bytesB, byte[] bytesC) {
		byte[] bytesTemp = Arrays.copyOf(bytesA, bytesA.length + bytesB.length + bytesC.length);
		System.arraycopy(bytesB, 0, bytesTemp, bytesA.length, bytesB.length);
		System.arraycopy(bytesC, 0, bytesTemp, bytesA.length + bytesB.length, bytesC.length);
		return bytesTemp;
	}
}
