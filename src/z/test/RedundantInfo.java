package z.test;

import java.io.Serializable;

public class RedundantInfo implements Serializable {

	private static final long serialVersionUID = 5215525097913299672L;
	
	public RedundantInfo() {
		
	}
	
	public void info() {
		System.out.println("你可以随意删除它了!");
	}

}
