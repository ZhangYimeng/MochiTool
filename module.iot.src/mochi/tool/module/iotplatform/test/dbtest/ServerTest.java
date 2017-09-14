package mochi.tool.module.iotplatform.test.dbtest;

import java.io.IOException;

import mochi.tool.module.iotplatform.Shift;

public class ServerTest {

	public static void main(String[] args) {
		try {
			Shift.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
