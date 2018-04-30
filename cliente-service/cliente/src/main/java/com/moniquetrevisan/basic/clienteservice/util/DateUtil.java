package com.moniquetrevisan.basic.clienteservice.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.joda.time.LocalDate;
import org.springframework.format.datetime.DateFormatter;

public class DateUtil {

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	private static DateFormatter formatter = new DateFormatter("yyyy-MM-dd");

	/**
	 * 
	 * @return current date
	 */
	public static Date today() {
		return LocalDate.now().toDate();
	}

	/**
	 * Verify is date is the currentDate
	 * @param date
	 * @return true if the date is today
	 * @throws ParseException
	 */
	public static boolean isToday(Date date) {
		try {
			String dateStr = formatter.print(date, new Locale("pt", "BR"));
			String todayStr = formatter.print(today(), new Locale("pt", "BR"));
			
			Date dateFormatted = dateFormat.parse(dateStr);
			Date todayFormatted = dateFormat.parse(todayStr);
			if (todayFormatted.compareTo(dateFormatted) == 0) { 
	        	return true;
			}
		} catch (ParseException e) {
			// nothing yet ... 
		}
		return false;
	}
	
	public static Date plusDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}

	public static Date getDateByString(String dateStr) {
		try {
			Date date = formatter.parse(dateStr, new Locale("pt", "BR")); 
			return date; 
		} catch (Exception e) {
			// String erro = e.getMessage();
		}
		// omg... im sorry about that... data in java is a fucking thing...
		// im expecting receive the data in the right format... ><
		// in tese this is never hppn
		return new Date();
	}
	
}