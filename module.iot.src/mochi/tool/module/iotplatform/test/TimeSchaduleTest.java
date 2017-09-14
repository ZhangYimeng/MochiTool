 package mochi.tool.module.iotplatform.test;

import mochi.tool.util.task.ScheduledTask;

public class TimeSchaduleTest {

	public static void main(String[] args) {
		
		ScheduledTask timer = new ScheduledTask() {
			int count = 0;
			
			@Override
			public void run() {
				System.out.println(count++ + " " + this.getTaskExecuteCount());
				if(this.getTaskExecuteCount() == 10) {
					this.reschedule(5000);
				}
			}
			
		};
		timer.schedule(timer, 0, 100);
		try {
			Thread.sleep(5000);
			timer.reschedule(1000);
			Thread.sleep(5000);
			timer.reschedule(0);
			Thread.sleep(5000);
			timer.reschedule(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
