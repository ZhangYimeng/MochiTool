package mochi.tool.util.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mochi.tool.util.task.foundation.TimeMeasure;
import mochi.tool.util.task.foundation.exception.InvalidTimeException;

public abstract class TimeBasedTask {
	
	private Calendar c;
	private StringBuffer formatTime;
	private String hour;
	private String minute;
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	private final SimpleDateFormat formatTimeToTime = new SimpleDateFormat("yyyyMMddHHmm");
	private long nextTime;

	public abstract void runTask();
	
	private class InnerThread implements Runnable {

		@Override
		public void run() {
			while(true) {
				if(System.currentTimeMillis() < nextTime) {
					if(nextTime - System.currentTimeMillis() > 10000) {
						try {
							Thread.sleep(nextTime - System.currentTimeMillis() - 5000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} else {
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				} else {
					runTask();
					nextTime = nextTime + TimeMeasure.MillisecondsOfDay;
				}
			}
		}
		
	}
	
	public void schedule(int hour, int minute) throws InvalidTimeException, ParseException {
		if(hour < 24 && hour >= 0 && minute < 60 && minute >= 0) {
			if(hour < 10) {
				this.hour = "0" + String.valueOf(hour);
			} else {
				this.hour = String.valueOf(hour);
			}
			if(minute < 10) {
				this.minute = "0" + String.valueOf(minute);
			} else {
				this.minute = String.valueOf(minute);
			}
		} else {
			throw new InvalidTimeException();
		}
		formatTime = new StringBuffer();
		c = Calendar.getInstance();
		formatTime.append(dateFormat.format(new Date()));
		formatTime.append(this.hour);
		formatTime.append(this.minute);
		Date temp = formatTimeToTime.parse(formatTime.toString());
		if(c.getTime().getTime() < temp.getTime()) {
			nextTime = temp.getTime();
		} else {
			nextTime = temp.getTime() + TimeMeasure.MillisecondsOfDay;
		}
		new Thread(new InnerThread()).start();;
	}
	
	public long getNextTime() {
		return nextTime;
	}

}
