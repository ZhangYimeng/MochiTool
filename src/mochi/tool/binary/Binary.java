package mochi.tool.binary;

import mochi.tool.data.bytescombinetool.BytesCombineTool;

public class Binary {
	
	private byte[] bytes;
	private static byte MASK_00000001 = 0x01;
	private static byte MASK_10000000 = (byte) 0x80;
	
	public Binary() {
		bytes = new byte[8];
	}
	
	public Binary(int length) {
		bytes = new byte[length];
	}
	
	public Binary(byte[] bytes) {
		this.bytes = bytes;
	}
	
	public byte[] getBytes() {
		return bytes;
	}
	
	public void extendBinaryFromRight(int length) {
		bytes = BytesCombineTool.append(bytes, new byte[length]);
	}
	
	public void extendBinaryFromLeft(int length) {
		bytes = BytesCombineTool.append(new byte[length], bytes);
	}
	
	private byte leftShiftWith1ForByte(byte b) {
		return (byte) ((b << 1) + MASK_00000001);
	}
	
	private byte leftShiftWith0ForByte(byte b) {
		return (byte) (b << 1);
	}
	
	private byte rightShiftWith1ForByte(byte b) {
		if(b > 0) {
			return (byte) ((b >>> 1) + MASK_10000000);
		} else {
			return (byte) (b >>> 1);
		}
	}
	
	private byte rightShiftWith0ForByte(byte b) {
		if(b > 0) {
			return (byte) (b >>> 1);
		} else {
			return (byte) ((b >>> 1) + MASK_10000000);
		}
	}
	
	public void leftShiftWith1() {
		byte[] carryBit = new byte[bytes.length];
		for(int i = 0; i < bytes.length; i++) {
			carryBit[i] = (byte) (bytes[i] & MASK_10000000);
		}
		bytes[bytes.length - 1] = leftShiftWith1ForByte(bytes[bytes.length - 1]);
		for(int i = bytes.length - 2; i >= 0; i--) {
			if(carryBit[i + 1] == MASK_10000000) {
				bytes[i] = leftShiftWith1ForByte(bytes[i]);
			} else {
				bytes[i] = leftShiftWith0ForByte(bytes[i]);
			}
		}
	}
	
	public void leftShiftWith1(int loop) {
		for(int i = 0; i < loop; i++) {
			leftShiftWith1();
		}
	}
	
	public void leftShiftWith0() {
		byte[] carryBit = new byte[bytes.length];
		for(int i = 0; i < bytes.length; i++) {
			carryBit[i] = (byte) (bytes[i] & MASK_10000000);
		}
		bytes[bytes.length - 1] = leftShiftWith0ForByte(bytes[bytes.length - 1]);
		for(int i = bytes.length - 2; i >= 0; i--) {
			if(carryBit[i + 1] == MASK_10000000) {
				bytes[i] = leftShiftWith1ForByte(bytes[i]);
			} else {
				bytes[i] = leftShiftWith0ForByte(bytes[i]);
			}
		}
	}
	
	public void leftShiftWith0(int loop) {
		for(int i = 0; i < loop; i++) {
			leftShiftWith0();
		}
	}
	
	public void rightShiftWith1() {
		byte[] carryBit = new byte[bytes.length];
		for(int i = 0; i < bytes.length; i++) {
			carryBit[i] = (byte) (bytes[i] & MASK_00000001);
		}
		bytes[0] = this.rightShiftWith1ForByte(bytes[0]);
		for(int i = 1; i < bytes.length; i++) {
			if(carryBit[i - 1] == MASK_00000001) {
				bytes[i] = rightShiftWith1ForByte(bytes[i]);
			} else {
				bytes[i] = rightShiftWith0ForByte(bytes[i]);
			}
		}
	}
	
	public void rightShiftWith0() {
		byte[] carryBit = new byte[bytes.length];
		for(int i = 0; i < bytes.length; i++) {
			carryBit[i] = (byte) (bytes[i] & MASK_00000001);
		}
		bytes[0] = this.rightShiftWith0ForByte(bytes[0]);
		for(int i = 1; i < bytes.length; i++) {
			if(carryBit[i - 1] == MASK_00000001) {
				bytes[i] = rightShiftWith1ForByte(bytes[i]);
			} else {
				bytes[i] = rightShiftWith0ForByte(bytes[i]);
			}
		}
	}
	
	public static void main(String[] args) {
		byte[] bytes = {1, 1, 1, -3};
		Binary bin = new Binary(bytes);
		for(byte b: bin.getBytes()) {
			System.out.print(b);
		}
		System.out.println();
		for(byte b: bin.getBytes()) {
			System.out.print(b);
		}
		System.out.println();
		System.out.println(MASK_10000000);
		byte b = (byte) (bytes[bytes.length - 1] & MASK_10000000);
		System.out.println(b);
		b = -3;
		System.out.println(bin.leftShiftWith1ForByte(b));
		System.out.println(bin.leftShiftWith0ForByte(b));
		bin.leftShiftWith0();
		System.out.println();
		for(byte b1: bin.getBytes()) {
			System.out.print(b1);
		}
		System.out.println();
		byte[] bytes1 = {1, 1, 1, -3};
		bin = new Binary(bytes1);
		bin.leftShiftWith1();
		for(byte b1: bin.getBytes()) {
			System.out.print(b1);
		}
		System.out.println();
		bin.leftShiftWith1(2);
		for(byte b1: bin.getBytes()) {
			System.out.print(b1);
		}
		System.out.println();
		byte bb = -5;
		System.out.println(bb << 1);
		System.out.println(bb >> 1);
		bb = -5;
		System.out.println(bb << 1);
		System.out.println(bb >>> 1);
		bb = 7;
		System.out.println(bin.rightShiftWith0ForByte(bb));
		System.out.println(bin.rightShiftWith1ForByte(bb));
		byte[] bbb = {1, 1, 1, -3};
		bin = new Binary(bbb);
		bin.rightShiftWith0();
		for(byte b1: bin.getBytes()) {
			System.out.print(b1);
		}
		System.out.println();
		byte[] bbbb = {1, 1, 1, -3};
		bin = new Binary(bbbb);
		bin.rightShiftWith1();
		for(byte b1: bin.getBytes()) {
			System.out.print(b1);
		}
		System.out.println();
		b = 1;
		System.out.println(bin.rightShiftWith1ForByte(b));
		b = 1;
		System.out.println(bin.rightShiftWith0ForByte(b));
		b = -1;
		System.out.println(bin.rightShiftWith1ForByte(b));
		b = -1;
		System.out.println(bin.rightShiftWith0ForByte(b));
	}
	
}
