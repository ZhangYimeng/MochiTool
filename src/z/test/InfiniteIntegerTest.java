package z.test;

import mochi.tool.infiniteinteger.InfiniteInteger;

public class InfiniteIntegerTest {

	public static void main(String[] args) {
		System.out.println(Integer.MAX_VALUE);
		InfiniteInteger  ii = new InfiniteInteger("-123456789012345678901234567890");
		System.out.println(ii.PositiveOrNegtive());
		System.out.println(999999999 + 999999999);
	}
	
}
