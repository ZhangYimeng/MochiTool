package mochi.tool.module.iotplatform.test;

import java.io.IOException;

import mochi.tool.module.iotplatform.Shift;

public class IOTSocketTest {

	public static void main(String[] args) {
		try {
			Shift.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
