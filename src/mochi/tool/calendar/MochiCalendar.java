package mochi.tool.calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MochiCalendar {

	private long rawTimeStamp = 0;
	private String formatTimeStamp = new String();
	private SimpleDateFormat defaultSDF = new SimpleDateFormat("yyyy/M/d/h/m/s");
	
	public MochiCalendar() {
		rawTimeStamp = System.currentTimeMillis();
		formatTimeStamp = defaultSDF.format(rawTimeStamp);
	}
	
	public MochiCalendar(long timeStamp) {
		this.rawTimeStamp = timeStamp;
		formatTimeStamp = defaultSDF.format(rawTimeStamp);
	}
	
	public void setDateFormat(String pattern) {
		defaultSDF = new SimpleDateFormat(pattern);
	}
	
	public void setFormatDate(String date) {
		formatTimeStamp = date;
		try {
			rawTimeStamp = defaultSDF.parse(formatTimeStamp).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public long getRawDate() {
		return rawTimeStamp;
	}
	
	public String getFormatDate() {
		return defaultSDF.format(rawTimeStamp);
	}
	
}
