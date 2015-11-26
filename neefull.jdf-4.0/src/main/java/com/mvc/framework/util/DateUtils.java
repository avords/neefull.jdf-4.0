package com.mvc.framework.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class DateUtils {

	public final static String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

	public final static String FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd hh:mm:ss";

	public final static String FORMAT_YYYYMMDD = "yyyyMMdd";

	public final static String FORMAT_YYYYMM = "yyyyMM";

	public final static String FORMAT_YYYY = "yyyy";

	public final static String FORMAT_YYYYMMDDHHMISS = "yyyyMMdd HH:mm:ss";

	public final static String FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	public final static String FORMAT_YYYY_MM_DD_HH_MI_SS = "yyyy-MM-dd HH:mm:ss";
	
	public final static String FORMAT_YYYY_MM_DD_HH_MI_SS_SSS = "yyyy-MM-dd HH:mm:ss:SSS";

	public final static String FORMAT_YYYY_MM = "yyyy-MM";

	public final static String FORMAT_DD_HH_MM_SS = "dd HH:mm:ss";

	public final static String FORMAT_HH_MM_SS = "HH:mm:ss";

	public static final int USE_YEAR = 1;

	public static final int USE_MONTH = 2;

	public static final int USE_DAY = 3;

	public static DateFormat getDateFormat(final String pattern) {
		return new SimpleDateFormat(pattern);
	}
	
	public static Date getPreviousOrNextDaysOfNow(int days) {
		return getPreviousOrNextDaysOfDate(new Date(), days);
	}

	/**
	 * Current date with format(yyyy-MM-dd)
	 * @return
	 */
	public static String getCurrentDate() {
		Date now = new Date();
		return getDateFormat(FORMAT_YYYY_MM_DD).format(now);
	}

	/**
	 * Current date with format(yyyy-MM-dd HH:MM:SS)
	 * @return
	 */
	public static String getCurrentDateTime() {
		Date now = new Date();
		return getDateFormat(FORMAT_YYYY_MM_DD_HH_MM_SS).format(now);
	}
	
	/**
	 * Current date with format(yyyy-MM-dd HH:MM:SS:SSS)
	 * @return
	 */
	public static String getCurrentDateTimeMilliSecond(Date date) {
		return getDateFormat(FORMAT_YYYY_MM_DD_HH_MI_SS_SSS).format(date);
	}
	
	/**
	 * Current date with format(yyyy-MM-dd HH:MM:SS:SSS)
	 * @return
	 */
	public static Date getDateByTimeMilliSecond(String date) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YYYY_MM_DD_HH_MI_SS_SSS);
		return sdf.parse(date);
	}
	
	public static Date getFirstDayOfThisMonth() {
		Calendar nowday = Calendar.getInstance();
		nowday.set(Calendar.DAY_OF_MONTH, 1);
		nowday.set(Calendar.HOUR_OF_DAY, 0);
		nowday.set(Calendar.MINUTE, 0);
		nowday.set(Calendar.SECOND, 0);
		nowday.set(Calendar.MILLISECOND, 0);
		return nowday.getTime();
	}

	public static Date getLastMonth() {
		Calendar nowday = Calendar.getInstance();
		nowday.setTime(new Date());
		nowday.set(Calendar.DAY_OF_MONTH, 1);
		nowday.add(Calendar.MONTH, -1);
		return nowday.getTime();
	}

	
	public static Date getLastDayByCalendar(Calendar calendar) {
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}

	

	/**
	 * Is in <code>days</code> of the specific date
	 * @param old
	 * @param days
	 * @return
	 */
	public static boolean isDaysInterval(Date old, int days) {
		Calendar now = Calendar.getInstance();
		return (now.getTimeInMillis() - old.getTime()) <= days * 24 * 3600 * 1000;
	}

	public static Date getFirstDayOfTheMonth(Calendar calendar) {
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * Next count by a positive number or previous by a negtive number
	 * @param days Day
	 * @return
	 */
	public static Date getPreviousOrNextDaysOfDate(Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, days);
		return calendar.getTime();
	}

	public static Date getPreviousOrNextMonthsOfDate(Date date, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, month);
		return calendar.getTime();
	}

	public static Date getPreviousOrNextYearsOfDate(Date date, int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, year);
		return calendar.getTime();
	}

	
	public static Date getBeginOfCurrentDay() {
		return getBeginOfTheDate(new Date());
	}

	public static Date getEndOfCurrentDay() {
		return getEndOfTheDate(new Date());
	}

	public static long getDifferenceDays(Calendar beginCalender, Calendar endCalendar) {
		long days = (endCalendar.getTimeInMillis() - beginCalender.getTimeInMillis()) / (24 * 60 * 60 * 1000);
		days = days + 1;
		return days;
	}

	public static Date getBeginOfCurrentYear() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date getPreviousOrNextMinutesOfDate(Date date, int minute) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.add(Calendar.MINUTE, minute);
		return now.getTime();
	}

	
	public static Date getLastDateOfTheMonth(Date date) {
		Calendar newday = Calendar.getInstance();
		newday.setTime(date);
		newday.set(Calendar.DAY_OF_MONTH, newday.getActualMaximum(Calendar.DAY_OF_MONTH));
		newday.set(Calendar.HOUR_OF_DAY, 23);
		newday.set(Calendar.MINUTE, 59);
		newday.set(Calendar.SECOND, 59);
		newday.set(Calendar.MILLISECOND, 999);
		return newday.getTime();
	}

	
	public static Date getBeginOfTheDate(Date date) {
		Calendar nowday = Calendar.getInstance();
		nowday.setTime(date);
		nowday.set(Calendar.HOUR_OF_DAY, 0);
		nowday.set(Calendar.MINUTE, 0);
		nowday.set(Calendar.SECOND, 0);
		nowday.set(Calendar.MILLISECOND, 0);
		return nowday.getTime();
	}

	
	public static Date getEndOfTheDate(Date date) {
		Calendar nowday = Calendar.getInstance();
		nowday.setTime(date);
		nowday.set(Calendar.HOUR_OF_DAY, 23);
		nowday.set(Calendar.MINUTE, 59);
		nowday.set(Calendar.SECOND, 59);
		nowday.set(Calendar.MILLISECOND, 998);//Sql Server BUG
		return nowday.getTime();
	}

	public static List<String> getAreaTime(String beginDate, String endDate) throws Exception {
		List<String> timeList = new ArrayList<String>();
		if (endDate == null || "".equals(endDate)) {
			timeList.add(beginDate);
			return timeList;
		}
		// day
		if (beginDate.length() <= 7) {
			return getAreaMonthTime(beginDate, endDate);
			// month
		} else {
			return getAreaDayTime(beginDate, endDate);
		}
	}

	private static List<String> getAreaMonthTime(String beginMonth, String endMonth) throws Exception {
		Date parsedBeginMonth = getDateFormat(FORMAT_YYYY_MM).parse(beginMonth);
		String tempStr = "";
		List<String> timeList = new ArrayList<String>();
		int months = 0;
		while (!endMonth.equals(tempStr)) {
			tempStr = getDateFormat(FORMAT_YYYY_MM).format(getPreviousOrNextMonthsOfDate(parsedBeginMonth, months));
			timeList.add(tempStr);
			months++;
		}
		return timeList;
	}

	public static List<String> getAreaDayTime(String beginDate, String endDate) throws Exception {
		Date parsedBeginMonth = getDateFormat(FORMAT_YYYY_MM_DD).parse(beginDate);
		String tempStr = "";
		List<String> timeList = new ArrayList<String>();
		int months = 0;
		while (!endDate.equals(tempStr)) {
			tempStr = getDateFormat(FORMAT_YYYY_MM_DD).format(getPreviousOrNextDaysOfDate(parsedBeginMonth, months));
			timeList.add(tempStr);
			months++;
		}
		return timeList;
	}

	public static Long getPreviousDay() {
		Date date = getPreviousOrNextDaysOfDate(new Date(), -1);
		return Long.valueOf((getDateFormat(FORMAT_YYYYMMDD).format(date)));
	}

	public static String getPreviousOrNextMonthsOfTheMonth(String date, int n) throws Exception {
		Calendar calendar = new GregorianCalendar();
		DateFormat dateFormat = getDateFormat(FORMAT_YYYYMM);
		calendar.setTime(dateFormat.parse(date));
		calendar.add(Calendar.MONTH, n);
		return dateFormat.format(calendar.getTime());
	}

	public static String getPreviousOrNextDaysOfTheDay(String date, int days) throws Exception {
		Calendar calendar = new GregorianCalendar();
		DateFormat dateFormat = getDateFormat(FORMAT_YYYYMMDD);
		calendar.setTime(dateFormat.parse(date));
		calendar.add(Calendar.DAY_OF_MONTH, days);
		return dateFormat.format(calendar.getTime());
	}

	public static Long getFirstDayOfCurrentMonth() {
		Date date = getFirstDayOfThisMonth();
		String dateStr = getDateFormat(FORMAT_YYYYMMDD).format(date);
		return Long.valueOf(dateStr);
	}

	public static Long getFirstMonthOfNow() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, 0);
		return Long.valueOf(getDateFormat(FORMAT_YYYYMM).format(calendar.getTime()));
	}

	public static Integer getCurrentDay() {
		Date date = new Date();
		return Integer.parseInt(getDateFormat(FORMAT_YYYYMMDD).format(date));
	}

	public static Integer getCurrentMonth() {
		Date date = new Date();
		return Integer.parseInt(getDateFormat(FORMAT_YYYYMM).format(date));
	}

	public static Integer getCurrentYear() {
		Date date = new Date();
		return Integer.parseInt(getDateFormat(FORMAT_YYYY).format(date));
	}

	public static Integer getKpiDbAnyOffsetDayOfNow(int day) {
		Calendar nowday = Calendar.getInstance();
		nowday.set(Calendar.DAY_OF_YEAR, nowday.get(Calendar.DAY_OF_YEAR) + day);
		return Integer.parseInt(getDateFormat(FORMAT_YYYYMMDD).format(nowday.getTime()));
	}

	public static Long getPreviousMonthOfNextYear() {
		Calendar nowday = Calendar.getInstance();
		nowday.setTime(new Date());
		nowday.set(Calendar.DAY_OF_MONTH, 1);
		nowday.add(Calendar.YEAR, +1);
		nowday.add(Calendar.MONTH, -1);
		Long time = Long.valueOf(getDateFormat(FORMAT_YYYYMM).format(nowday.getTime()));
		return time;
	}

	public static Long getTheSameDayOfTheNextMonth(Date date) {
		Date next = getPreviousOrNextMonthsOfDate(date, 1);
		Long time = Long.valueOf(getDateFormat(FORMAT_YYYYMMDD).format(next));
		return time;
	}

	public static Long getTheSameDayOfNextMonth() {
		return getTheSameDayOfTheNextMonth(new Date());
	}

	public static Long getPreviousYear() {
		Calendar nowday = Calendar.getInstance();
		nowday.setTime(new Date());
		nowday.add(Calendar.YEAR, -1);
		return Long.valueOf(getDateFormat(FORMAT_YYYY).format(nowday.getTime()));
	}

	public static List<String> getTimeListByParameter(Date paramDate, int paramter, int type) {
		if (type == USE_YEAR) {
			Date newDate = getPreviousOfNextYearOfDate(paramDate, -paramter);
			return getDaysBetweenDate(paramDate, newDate);
		} else if (type == USE_MONTH) {
			Date newDate = getPreviousOrNextMonthsOfDate(paramDate, paramter);
			return getDaysBetweenDate(paramDate, newDate);
		} else {
			return getDaysByBeginDate(paramDate, paramter);
		}
	}

	private static Date getPreviousOfNextYearOfDate(Date date, int year) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.add(Calendar.YEAR,year);
		return now.getTime();
	}

	private static List<String> getDaysBetweenDate(Date paramDate, Date newDate) {
		DateFormat dateFormat = getDateFormat(FORMAT_YYYYMMDD);
		String formatNewDate = dateFormat.format(newDate);
		String tempStr = "";
		List<String> timeList = new ArrayList<String>();
		int days = 0;
		while (!formatNewDate.equals(tempStr)) {
			tempStr = dateFormat.format(getPreviousOrNextDaysOfDate(paramDate, -days));
			timeList.add(tempStr);
			days++;
		}
		return timeList;
	}

	private static List<String> getDaysByBeginDate(Date paramDate, int paramter) {
		List<String> timeList = new ArrayList<String>();
		for (int i = 1; i <= paramter; i++) {
			timeList.add(getDateFormat(FORMAT_YYYYMMDD).format(getPreviousOrNextDaysOfDate(paramDate, -i)));
		}
		return timeList;
	}

	
	public static Long getFirstMonthOfCurrentYear() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH,0);
		return Long.valueOf(getDateFormat(FORMAT_YYYYMM).format(calendar.getTime()));
	}

	public static Date getPreviousOrNextMonthsOfDateTime(Date date, int months){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, months);
		return calendar.getTime();
	}

	public static String  getPreviousOrNextDaysOfNow(String ymd,int days) throws ParseException {
		Date date = getDateFormat(FORMAT_YYYY_MM_DD).parse(ymd);
		Date result = getPreviousOrNextDaysOfDate(date, days);
		return getDateFormat(FORMAT_YYYY_MM_DD).format(result);
	}

	public static Date getBeginOfThePreviousOrNextMonths(int month){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	public static Date getPreviousOrNextWorkDaysOfDate(Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int step = 0;
		if(days>0){
			step = 1;
		}else{
			step = -1;
		}
		for(int i = 0; i < Math.abs(days) ; ){
			calendar.add(Calendar.DAY_OF_YEAR, step);
			if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY||calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
				continue;
			}
			i++;
		}
		return calendar.getTime();
	}
	
	public static Date getBeginDateOfTheMonth(Date date) {
		Calendar newday = Calendar.getInstance();
		newday.setTime(date);
		newday.set(Calendar.DAY_OF_MONTH, 1);
		newday.set(Calendar.HOUR_OF_DAY, 0);
		newday.set(Calendar.MINUTE, 0);
		newday.set(Calendar.SECOND, 0);
		newday.set(Calendar.MILLISECOND, 0);
		return newday.getTime();
	}

}