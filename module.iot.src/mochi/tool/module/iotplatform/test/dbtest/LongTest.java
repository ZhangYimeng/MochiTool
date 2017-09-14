package mochi.tool.module.iotplatform.test.dbtest;

public class LongTest {

	public static void main(String[] args) {
		Long l = 89l;
		Long ll = l;
		System.out.println(l + " " + ll);
		l = null;
		System.out.println(l + " " + ll);
	}

}
