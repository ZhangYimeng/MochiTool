package atest;

import mochi.tool.data.bytescombinetool.BytesCombineTool;

public class BytesCombineToolTest {

	public static void main(String[] args) {
		byte[] result = {1,2,3};
		byte[] temp = {12,12,12,1,21,2,12,1,2,1,21,2,1,2,1,2,1,2};
		System.out.println(result);
		result = BytesCombineTool.append(result, temp);
		for(byte b: result) {
			System.out.print(b + " ");
		}
		System.out.println();
		byte[] a = {1};
		byte[] b = {2};
		byte[] c = {3};
		a = BytesCombineTool.combineThreeBytes(a, b, c);
		for(byte bb: a) {
			System.out.print(bb + " ");
		}
	}

}
