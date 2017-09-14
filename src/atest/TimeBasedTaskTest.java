package atest;

import java.text.ParseException;

import mochi.tool.util.task.foundation.exception.InvalidTimeException;

public class TimeBasedTaskTest {
	
	public static void main(String[] args) throws InvalidTimeException, ParseException {
		TimeBasedTTT ttt = new TimeBasedTTT();
		ttt.schedule(16, 19);
	}

}
