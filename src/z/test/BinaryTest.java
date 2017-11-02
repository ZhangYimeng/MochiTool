package z.test;

import mochi.tool.module.iotplatform.open.api.datatool.DataInterconversionTool;

public class BinaryTest {

	public static void main(String[] args) {
		byte[] byets = DataInterconversionTool.stringToBytes("Styx");
		for(byte b: byets) {
			System.out.print(b + " ");
		}
	}

}
