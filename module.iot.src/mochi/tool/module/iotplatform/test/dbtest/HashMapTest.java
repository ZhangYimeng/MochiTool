package mochi.tool.module.iotplatform.test.dbtest;

import java.util.concurrent.ConcurrentHashMap;

public class HashMapTest {

	public static void main(String[] args) {
		ConcurrentHashMap<String, Long> mp = new ConcurrentHashMap<String, Long>();
		mp.put("qwe", 9090l);
		Long h = mp.remove("qwe");
		System.out.println(h);
		h = mp.remove("qwe");
		System.out.println(h);
	}

}
