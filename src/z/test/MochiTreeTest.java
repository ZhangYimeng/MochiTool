package z.test;

import java.util.TreeSet;

public class MochiTreeTest {

	public static void main(String[] args) {
		/*MochiTree<Integer, Integer> mt = new MochiTree<Integer, Integer>();
		for(int i = 0; i < 1000000; i++) {
			mt.put(i, i);
		}
		TreeMap<Integer, Integer> tm = new TreeMap<Integer, Integer>();
		for(int i = 0; i < 1000000; i++) {
			tm.put(i, i);
		}
		long t1 = System.currentTimeMillis();
		for(int i = 0; i < 1000000; i++) {
			mt.deleteMin();
		}
		System.out.println(System.currentTimeMillis() - t1);
		t1 = System.currentTimeMillis();
		for(int i = 0; i < 1000000; i++) {
			tm.pollLastEntry();
		}
		System.out.println(System.currentTimeMillis() - t1);*/
		KeyObject kk1 = new KeyObject(3);
		TreeSet<KeyObject> ts = new TreeSet<KeyObject>();
		ts.add(kk1);
		ts.add(kk1);
		System.out.println(ts.size());
		
		//MochiTree<Integer, Integer> mt = new MochiTree<Integer, Integer>();
		
	}
	
}
