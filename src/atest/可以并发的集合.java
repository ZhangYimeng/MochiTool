package atest;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class 可以并发的集合 {

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		ConcurrentLinkedQueue<String> clq = new ConcurrentLinkedQueue<String>();
		@SuppressWarnings("unused")
		ArrayList<Object> al = new ArrayList<Object>();
	}
	
}
