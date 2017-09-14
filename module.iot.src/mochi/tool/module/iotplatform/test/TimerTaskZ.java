package mochi.tool.module.iotplatform.test;

import java.util.TimerTask;

public class TimerTaskZ extends TimerTask implements InterfaceTimerTaskZ {

	int count = 0;
	
	@Override
	public void run() {
		System.out.println(count++);
	}
	
	public int getCount() {
		return count;
	}

}
