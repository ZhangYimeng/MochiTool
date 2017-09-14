package atest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mochi.tool.calendar.MochiCalendar;

public class JavaCalendarTest {

	public static void main(String[] args) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		System.out.println(calendar.getTimeZone());
		SimpleDateFormat dateFormat = new SimpleDateFormat("y/M/d/h/m/s");
	    Date date = dateFormat.parse("2013/1/1/12/12/12");
	    calendar.setTime(date);
	    System.out.println(calendar.toString());
	    
	    MochiCalendar mc = new MochiCalendar();
	    mc.setDateFormat("yyyy/M/d/H/m/s");
	    mc.setFormatDate("1970/1/1/0/0/0");
	    System.out.println(mc.getFormatDate());
	    System.out.println(mc.getRawDate());
	    dateFormat.setTimeZone(calendar.getTimeZone());
	    Date d = dateFormat.parse("1970/1/1/8/0/0");
	    System.out.println(d.getTime());
	}

}
