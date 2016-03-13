package com.imxiaomai.convenience.store.scan.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
	private static SimpleDateFormat sdf_hhmm = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");
	private static String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四",
			"星期五", "星期六" };

	/**
	 * 得到当前天的之前或之后的某个日期
	 * 
	 * @param berforDays
	 *            往前推算日期用负数，往后推用正数
	 * @return
	 */
	public static String getTodayBefor(int berforDays) {

		return getBefor(new Date(), berforDays);
	}

	public static String dateFormat2(Date date) {
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTime(date);
		return sdf2.format(calendar.getTime());
	}

	/**
	 * 得到某一天的之前或之后日期
	 * 
	 * @param date
	 * @param berforDays
	 *            往前推算日期用负数，往后推用正数
	 * @return
	 */
	public static String getBefor(Date date, int berforDays) {
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTime(date);
		calendar.add(calendar.DATE, berforDays);
		return sdf.format(calendar.getTime());
	}
	public static String getBefor(String dateStr, int berforDays) {
		Date date = getDate(dateStr);
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTime(date);
		calendar.add(calendar.DATE, berforDays);
		return sdf.format(calendar.getTime());
	}
	public static String getYearBefor(Date date, int num) {
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTime(date);
		calendar.add(calendar.YEAR, num);
		calendar.add(calendar.DATE, -1);
		return sdf.format(calendar.getTime());
	}
	public static String getMothBefor(Date date, int num) {
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTime(date);
		calendar.add(calendar.MONTH, num);
		calendar.add(calendar.DATE, -1);
		return sdf.format(calendar.getTime());
	}

	public static Date getDate(long time) {
		return new Date(time);
	}
	public static Date getDate(String str) {
		if(StringUtil.empty(str))  {
			new Date();
		}
		Date date = new Date();
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String getDateStr(long time) {
		return sdf.format(new Date(time));
	}

	public static String getDateStr(Date date) {
		return sdf.format(date);
	}
	public static String getDateStrHHMM(Date date) {
		return sdf_hhmm.format(date);
	}

	public static int[] parseStr(String dateStr) {
		int[] dateInt = new int[3];
		try {
			Date date = sdf.parse(dateStr);
			dateInt = parseDate(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateInt;
	}

	public static int[] parseDate(Date date) {
		int[] dateInt = new int[3];
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTime(date);
		dateInt[0] = calendar.get(Calendar.YEAR);
		dateInt[1] = calendar.get(Calendar.MONTH);
		dateInt[2] = calendar.get(Calendar.DAY_OF_MONTH);
		return dateInt;
	}

	/**
	 * 获取本周周日的日期
	 * 
	 * @return
	 */
	public static String getCurrWeekSunday() {
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		// calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		return getBefor(calendar.getTime(), 7);
	}

	/**
	 * 获取count月前的日期
	 * 
	 * @param count
	 * @return
	 */
	public static String getBeforMonthDay(int count) {
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.add(Calendar.MONTH, count);
		return sdf.format(calendar.getTime());
	}
	public static String getStrMMDD(Date date) {
		String dateStr = getDateStr(date);
		return dateStr.substring(5);
	}
	public static String getStrMMDD(String date) {
		return date.substring(5);
	}

	public static String getCurrentDayAndWeek() {
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTime(new Date());
		String date = sdf.format(calendar.getTime());
		String week = weekDays[calendar.get(Calendar.DAY_OF_WEEK) - 1];
		return date + "  " + week;
	}
	public static String getDayAndWeek(String dateStr) {
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		try {
			calendar.setTime(sdf.parse(dateStr));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String date = sdf.format(calendar.getTime());
		String week = weekDays[calendar.get(Calendar.DAY_OF_WEEK) - 1];
		return date + "  " + week;
	}

	public static String getDayAndWeek(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.set(year, month, day);
		String date = sdf.format(calendar.getTime());
		String week = weekDays[calendar.get(Calendar.DAY_OF_WEEK) - 1];
		return date + "  " + week;
	}
	public static Date getDate(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.set(year, month, day);
		return calendar.getTime();
	}

	public static int getDayInWeekIndex(String dateStr) {
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		Date date;
		try {
			date = sdf.parse(dateStr);
			calendar.setTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return calendar.get(Calendar.DAY_OF_WEEK) - 2;
	}

	public static String[] getWeekFirstAndLastDay(String dateStr) {
		String [] fldays = new String[2];
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		Date date;
		try {
			date = sdf.parse(dateStr);
			calendar.setFirstDayOfWeek(Calendar.MONDAY);
			calendar.setTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		
		fldays[0] = sdf.format(calendar.getTime());
		
		//获取本周日日期
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		calendar.add(Calendar.WEEK_OF_YEAR, 1);
		fldays[1] = sdf.format(calendar.getTime());
		return fldays;
	}
	
	public static Date getMondayByDate(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
		return cal.getTime();
	}
	
	/**
	 * 获取时间所在周的周日
	 * @param date
	 * @return
	 */
	public static Date getSundayByDate(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
		return cal.getTime();
	}
	
	/**
	 * 获取时间所在周的周一
	 * @param date  "2014-11-06"
	 * @return		String  "2014-11-03"
	 */
	public static String getMondayByDate(String date){
		Date targetdate = getMondayByDate(getDate(date));
		return getDateStr(targetdate);
	}
	
	/**
	 * 获取时间所在周的周日
	 * @param date  "2014-11-06"
	 * @return		String  "2014-11-09"
	 */
	public static String getSundayByDate(String date){
		Date targetdate = getSundayByDate(getDate(date));
		return getDateStr(targetdate);
	}
	
	
	public static boolean isCurrWeek(Date date) {
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		String currWeekMonday = sdf.format(calendar.getTime());
		
		String dateStr = sdf.format(date);
		
		if(currWeekMonday.equals(dateStr)) {
			return true;
		} 
		return false;
		
	}
	public static boolean isCurrWeek(String dateStr) {
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		String currWeekMonday = sdf.format(calendar.getTime());
		
		if(currWeekMonday.equals(dateStr)) {
			return true;
		} 
		return false;
		
	}
	
	public static boolean isDueto(Date startDate,Date endDate) {
		
		if(startDate == null || endDate == null ) {
			return true;
		}
		
		long diff = endDate.getTime() - startDate.getTime();
		int minuTime = (int) (diff/(1000*60));
		if(minuTime > 5) {//距离上次发送的时间小于5分钟就不再发送
			return true;
		}
		return false;
	}
	
	public static int daysBetween(Date smdate,Date bdate) {    
        long between_days;
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
			smdate=sdf.parse(sdf.format(smdate));  
			bdate=sdf.parse(sdf.format(bdate));  
			Calendar cal = Calendar.getInstance();    
			cal.setTime(smdate);    
			long time1 = cal.getTimeInMillis();                 
			cal.setTime(bdate);    
			long time2 = cal.getTimeInMillis();         
			between_days = (time2-time1)/(1000*3600*24);
			return Integer.parseInt(String.valueOf(between_days));      
		} catch (ParseException e) {
			e.printStackTrace();
		}  
            
         return 0;   
    } 
	
	public static String[] convertWeekByDate(String date) {
		return convertWeekByDate(getDate(date));
	}
	 public static String[] convertWeekByDate(Date time) {
		 String[] strs =new String[2];
		Calendar cal = Calendar.getInstance();  
		 cal.setTime(time);  
		 //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了  
		 int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天  
		 if(1 == dayWeek) {  
		     cal.add(Calendar.DAY_OF_MONTH, -1);  
		 }  
		 cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一  
		 int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天  
		 cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值   
		 String imptimeBegin = sdf.format(cal.getTime());  
		 strs[0] = imptimeBegin;
		 cal.add(Calendar.DATE, 6);  
		 String imptimeEnd = sdf.format(cal.getTime());  
		 strs[1] = imptimeEnd;
		 
		 return strs;
	 }
	
	public static boolean isBetweenDay(String start,String end,String compare) {
		Date startDate = getDate(start);
		Date endDate = getDate(end);
		Date compareDate = getDate(compare);
		
		long startTime = startDate.getTime();
		long endTime = endDate.getTime();
		long compareTime = compareDate.getTime();
		
		if(startTime <= compareTime && compareTime <=endTime) {
			return true;
		}
		
		return false;
	}
	
	public static boolean dayCompare(Date startDate,Date endDate) {
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(startDate);
		 
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endDate);
		
		int sy = startCalendar.get(Calendar.YEAR);//获取年份
		int ey = endCalendar.get(Calendar.YEAR);//获取年份
		
	    int sm = startCalendar.get(Calendar.MONTH);//获取月份
	    int em = endCalendar.get(Calendar.MONTH);//获取月份
	    
	    int sd = startCalendar.get(Calendar.DATE);//获取日 
	    int ed = endCalendar.get(Calendar.DATE);//获取日 
	    
		if(ey > sy ) {
			return true;
		}
		if(ey == sy && em > sm ) {
			return true;
		}
		if(ey == sy && em == sm && ed > sd ) {
			return true;
		}
		
		return false;
	}
}
