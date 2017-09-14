package mochi.tool.module.iotplatform.test;

public class SwitchTest {

	public static void main(String[] args) {
		int x = 0;
		switch(x) {
		case 0:
			System.out.println("x=0");
			break;
		case 1:
			System.out.println("x=1");
			break;
		case 7:
			System.out.println("x=7");
			break;
		default:
			System.out.println("x=" + x);
			break;
		}
		System.out.println("over");
	}

}
