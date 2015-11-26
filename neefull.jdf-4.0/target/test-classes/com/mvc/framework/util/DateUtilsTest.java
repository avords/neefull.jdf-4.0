package com.mvc.framework.util;

import java.util.Calendar;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;


public class DateUtilsTest {

	private static final int DAYS_20 = 20;
	private static final int MIN_MONTH_DAY_28 = 28;
	private static final int YEAR_2010 = 2010;
	private static final int DAYS_6 = 6;
	private static final int DAYS_80 = 80;
	private static final int DAYS_3 = 3;
	private static final int DAYS_10 = 10;
	private static final int MINUTES_40 = 40;
	private static final int DAYS_31 = 31;
	private static final int THREE_DAY = 3;
	private static final int YEAR_BEGIN = 1900;

	@Test
	public void testGetPreviousOrNextDaysOfNow() {
		Calendar calendar = Calendar.getInstance();
		Date date = DateUtils.getPreviousOrNextDaysOfNow(THREE_DAY);
		calendar.add(Calendar.DAY_OF_MONTH, THREE_DAY);
		Assert.assertEquals(calendar.get(Calendar.DAY_OF_MONTH), date.getDate());
		date = DateUtils.getPreviousOrNextDaysOfNow(-THREE_DAY);
		calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -THREE_DAY);
		Assert.assertEquals(calendar.get(Calendar.DAY_OF_MONTH), date.getDate());
	}

	@Test
	public void testGetLastDayByCalendar() {
		Calendar calendar = Calendar.getInstance();
		Date date = DateUtils.getLastDayByCalendar(calendar);
		Assert.assertEquals(calendar.get(Calendar.MONTH), date.getMonth());
		Assert.assertEquals(calendar.get(Calendar.YEAR) - YEAR_BEGIN, date
				.getYear());
	}

	@Test
	public void testIsDaysInterval() {
		Date date = DateUtils.getPreviousOrNextDaysOfNow(MINUTES_40);
		boolean result = DateUtils.isDaysInterval(date, MINUTES_40);
		Assert.assertTrue(result);
	}

	@Test
	public void testGetFirstDayOfTheMonth() {
		Calendar calendar = Calendar.getInstance();
		Date date = DateUtils.getFirstDayOfTheMonth(calendar);
		Assert.assertEquals(1, date.getDate());
	}

	@Test
	public void testGetPreviousOrNextDaysOfDate() {
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		Date date = DateUtils.getPreviousOrNextDaysOfDate(now, DAYS_10);
		calendar.add(Calendar.DAY_OF_MONTH, DAYS_10);
		Assert
				.assertEquals(calendar.get(Calendar.DAY_OF_MONTH), date
						.getDate());
		date = DateUtils.getPreviousOrNextDaysOfDate(now, -DAYS_10 * 2);
		calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -DAYS_10 * 2);
		Assert
				.assertEquals(calendar.get(Calendar.DAY_OF_MONTH), date
						.getDate());
	}

	@Test
	public void testGetPreviousOrNextMonthsOfDate() {
		// 2009-12-31
		Date date = new Date(2009 - YEAR_BEGIN, 11, DAYS_31);
		Date compare = DateUtils.getPreviousOrNextMonthsOfDate(date, -1);
		Assert.assertEquals(DAYS_31 - 1, compare.getDate());
		Assert.assertEquals(10, compare.getMonth());
		Assert.assertEquals(2009 - YEAR_BEGIN, compare.getYear());
		// 2010-01-31
		date = new Date(2010 - YEAR_BEGIN, 0, DAYS_31);
		compare = DateUtils.getPreviousOrNextMonthsOfDate(date, -1);
		Assert.assertEquals(DAYS_31, compare.getDate());
		Assert.assertEquals(11, compare.getMonth());
		Assert.assertEquals(2009 - YEAR_BEGIN, compare.getYear());
		// 2010-03-31
		date = new Date(2010 - YEAR_BEGIN, 2, DAYS_31);
		compare = DateUtils.getPreviousOrNextMonthsOfDate(date, -1);
		Assert.assertEquals(28, compare.getDate());
		Assert.assertEquals(1, compare.getMonth());
		Assert.assertEquals(2010 - YEAR_BEGIN, compare.getYear());
		// 2010-03-31
		date = new Date(2010 - YEAR_BEGIN, 2, DAYS_31);
		compare = DateUtils.getPreviousOrNextMonthsOfDate(date, -2);
		Assert.assertEquals(DAYS_31, compare.getDate());
		Assert.assertEquals(0, compare.getMonth());
		Assert.assertEquals(2010 - YEAR_BEGIN, compare.getYear());
		// 2010-03-31
		date = new Date(2010 - YEAR_BEGIN, 2, DAYS_31);
		compare = DateUtils.getPreviousOrNextMonthsOfDate(date, -DAYS_3);
		Assert.assertEquals(DAYS_31, compare.getDate());
		Assert.assertEquals(11, compare.getMonth());
		Assert.assertEquals(2009 - YEAR_BEGIN, compare.getYear());
		// 2010-03-31
		date = new Date(2010 - YEAR_BEGIN, 2, DAYS_31);
		compare = DateUtils.getPreviousOrNextMonthsOfDate(date, -DAYS_10);
		Assert.assertEquals(DAYS_31, compare.getDate());
		Assert.assertEquals(4, compare.getMonth());
		Assert.assertEquals(2009 - YEAR_BEGIN, compare.getYear());
	}

	@Test
	public void testGetPreviousOrNextMinutesOfDate() {
		// 2009-12-31 12:20
		Date date = new Date(2009 - YEAR_BEGIN, 11, DAYS_31, 12, 20);
		Date compare = DateUtils.getPreviousOrNextMinutesOfDate(date, -1);
		Assert.assertEquals(19, compare.getMinutes());
		Assert.assertEquals(12, compare.getHours());
		Assert.assertEquals(11, compare.getMonth());
		Assert.assertEquals(2009 - YEAR_BEGIN, compare.getYear());

		compare = DateUtils.getPreviousOrNextMinutesOfDate(date, 1);
		Assert.assertEquals(21, compare.getMinutes());
		Assert.assertEquals(12, compare.getHours());
		Assert.assertEquals(11, compare.getMonth());
		Assert.assertEquals(2009 - YEAR_BEGIN, compare.getYear());

		compare = DateUtils.getPreviousOrNextMinutesOfDate(date, -DAYS_80);
		Assert.assertEquals(0, compare.getMinutes());
		Assert.assertEquals(11, compare.getHours());
		Assert.assertEquals(11, compare.getMonth());
		Assert.assertEquals(2009 - YEAR_BEGIN, compare.getYear());
	}

	@Test
	public void testGetLastDateOfTheMonth() {
		// 2009-12-01 12:20
		Date date = new Date(2009 - YEAR_BEGIN, 11, 01, 12, DAYS_20);
		Date compare = DateUtils.getLastDateOfTheMonth(date);
		Assert.assertEquals(DAYS_31, compare.getDate());
		Assert.assertEquals(11, compare.getMonth());
		Assert.assertEquals(2009 - YEAR_BEGIN, compare.getYear());

		// 2010-02-20 12:20
		date = new Date(YEAR_2010 - YEAR_BEGIN, 1, DAYS_20, 12, DAYS_20);
		compare = DateUtils.getLastDateOfTheMonth(date);
		Assert.assertEquals(MIN_MONTH_DAY_28, compare.getDate());
		Assert.assertEquals(1, compare.getMonth());
		Assert.assertEquals(YEAR_2010 - YEAR_BEGIN, compare.getYear());
	}

	@Test
	public void testGetDayOfTheNextMonth() {
		// 2010-01-06 12:20
		Date date = new Date();
		date.setDate(DAYS_6);
		date.setMonth(0);
		date.setYear(YEAR_2010 - YEAR_BEGIN);
		long compare = DateUtils.getTheSameDayOfTheNextMonth(date);
		Assert.assertEquals("20100206", String.valueOf(compare));
	}

	@Test
	public void testGetTheSameDayOfNextMonth() {
		Date date = new Date();
		long compare = DateUtils.getTheSameDayOfNextMonth();
		date.setMonth(date.getMonth() + 1);
		long o = Long.parseLong(DateUtils.getDateFormat(
				DateUtils.FORMAT_YYYYMMDD).format(date));
		Assert.assertEquals(o, compare);
	}
	
	@Test
	public void testGetPreviousOrNextWorkDaysOfDate(){
		Date date = new Date(2011 - YEAR_BEGIN, 4, 31, 12, DAYS_20);
		Date compare = DateUtils.getPreviousOrNextWorkDaysOfDate(date, -9);
		Assert.assertEquals(date.getMonth(), compare.getMonth());
		Assert.assertEquals(18 , compare.getDate());
		Assert.assertEquals(date.getYear(), compare.getYear());
		
		compare = DateUtils.getPreviousOrNextWorkDaysOfDate(date, 9);
		Assert.assertEquals(date.getMonth()+1, compare.getMonth());
		Assert.assertEquals( 13, compare.getDate());
		Assert.assertEquals(date.getYear(), compare.getYear());
		
		compare = DateUtils.getPreviousOrNextWorkDaysOfDate(date, 14);
		Assert.assertEquals(date.getMonth()+1, compare.getMonth());
		Assert.assertEquals(20, compare.getDate());
		Assert.assertEquals(date.getYear(), compare.getYear());
	}
}
