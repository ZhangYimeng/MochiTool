package z.test;

import java.util.TreeSet;

public class SomeThing implements Comparable<Object> {

	public String a;
	public String b;
	public int c;

	public SomeThing() {
		a = new String();
		b = new String();
		c = 0;
	}

	public void setA(String s) {
		a = s;
	}

	public void setB(String s) {
		b = s;
	}

	public void setC(int i) {
		c = i;
	}

	public static void main(String[] args) {
		TreeSet<SomeThing> ts = new TreeSet<SomeThing>();
		SomeThing a = new SomeThing();
		SomeThing b = new SomeThing();
		System.out.println(a);
		System.out.println(b);
		System.out.println(a.equals(b));
		ts.add(a);
		ts.add(b);
		for(SomeThing s: ts) {
			System.out.println(s);
		}
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}

}
