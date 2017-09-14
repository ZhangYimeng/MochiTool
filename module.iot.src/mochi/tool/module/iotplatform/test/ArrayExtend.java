package mochi.tool.module.iotplatform.test;

import java.util.Arrays;

import mochi.tool.module.iotplatform.open.api.datatool.BytesCombineTool;

public class ArrayExtend {

	public static void main(String[] args) {
		byte[] b = {1, 2};
		System.out.println(b);
		byte[] temp = Arrays.copyOf(b, 3);
		System.out.println(temp[0] + " " + temp[1] + " " + temp[2]);
		System.out.println(b);
		System.out.println(temp);
		byte[] a = {2,3,4};
		a = BytesCombineTool.append(a, b);
		for(byte d: a) {
			System.out.println(d);
		}
		byte[] c = {89,90};
		a = BytesCombineTool.combineThreeBytes(a, b, c);
		for(byte d: a) {
			System.out.println(d);
		}
	}

}
