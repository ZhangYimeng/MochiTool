package mochi.tool.module.iotplatform.test;

import java.util.TimerTask;

public interface InterfaceTimerZ {

	public void schedule(TimerTask task, long delay, long period);
	
	public void cancel();
	
}
