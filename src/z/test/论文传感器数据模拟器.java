package z.test;

import mochi.tool.data.bytescombinetool.BytesCombineTool;
import mochi.tool.data.interconversion.DataInterconversionTool;

public class 论文传感器数据模拟器 {

	public static void main(String[] args) {
		byte[] seperator = {0x7E, (byte) 0xEE};
		short fVer = 1;
		short sVer = 5;
		short tVer = 1;
		String tag = "MMXS1501";
		String uniqueCodeStatic = "AHU906LKI5642F76HY78U9I09H6BB9HG";
		String uniqueCodeDynamic = "1001";
		long sequence = 6547;
		byte[] _1_2 = {4, 9};
		short _3 = 2435;
		long value = 175312693;
		byte[] message = new byte[0];
		message = BytesCombineTool.combineThreeBytes(seperator, DataInterconversionTool.shortToBytes(fVer), DataInterconversionTool.shortToBytes(sVer));
		message = BytesCombineTool.append(message, DataInterconversionTool.shortToBytes(tVer));
		message = BytesCombineTool.append(message, DataInterconversionTool.stringToBytes(tag));
		message = BytesCombineTool.append(message, DataInterconversionTool.stringToBytes(uniqueCodeStatic));
		message = BytesCombineTool.append(message, DataInterconversionTool.stringToBytes(uniqueCodeDynamic));
		message = BytesCombineTool.append(message, DataInterconversionTool.longToBytes(sequence));
		message = BytesCombineTool.append(message, _1_2);
		message = BytesCombineTool.append(message, DataInterconversionTool.shortToBytes(_3));
		message = BytesCombineTool.append(message, DataInterconversionTool.longToBytes(value));
		message = BytesCombineTool.append(message, seperator);
		for(byte b: message) {
			System.out.print(b + " ");
		}
		System.out.println();
		byte[] num = {0, 0, 0, 0, 10, 115, 51, -125};
		System.out.println(DataInterconversionTool.bytesToLong(num));
	}

}
