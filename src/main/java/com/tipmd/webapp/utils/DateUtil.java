package com.tipmd.webapp.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 日期处理类
 * @author bowee2010
 */
public final class DateUtil 
{
	/**
     * Format a java.util.Date to string by specified pattern
     * @param date  to format
     * @param pattern used to format date
     * @return Formatted date
     */
	public static String format(Date date, String pattern) 
	{
		if(date == null)
			return "";
		
		DateFormat df = createDateFormat(pattern);
	    return df.format(date);
	}

	/**
     * Format time in milliseconds to string by specified pattern
     * @param time in milliseconds.
     * @param pattern  used to format date.
     * @return Formatted date
     */
    public static String format(long time, String pattern) {
        return format(new Date(time), pattern);
    }
    
    /**
     * Get the year of current date
     * @return the year of current date.
     */
    public static int getCurrentYear()
    {
    	return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * Get the year of d represented date
     * @param d Date
     * @return the year of d represented date.
     * @throws NullPointerException If d is null, throw this exception.
     */
    public static int getYear(Date d) throws NullPointerException 
    {
    	if(d == null)
    		throw new NullPointerException("Date can not be null");
		
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(d);
    	
    	return cal.get(Calendar.YEAR);
    }
    
    public static int getHours(Date d) throws NullPointerException 
    {
    	if(d == null)
    		throw new NullPointerException("Date can not be null");
		
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(d);
    	
    	return cal.get(Calendar.HOUR_OF_DAY);
    }
    
    public static int getMinutes(Date d) throws NullPointerException 
    {
    	if(d == null)
    		throw new NullPointerException("Date can not be null");
		
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(d);
    	
    	return cal.get(Calendar.MINUTE);
    }
    
    
    /**
     * Get the month of current date
     * @return the month of current date.
     */
    public static int getCurrentMonth()
    {
    	return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }
    
    /**
     * Get the month of d represented date
     * @param d Date
     * @return the month of d represented date
     * @throws NullPointerException If d is null, throw this exception.
     */
    public static int getMonth(Date d) throws NullPointerException
    {
    	if(d == null)
    		throw new NullPointerException("Date can not be null");
  
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(d);
    	
    	return cal.get(Calendar.MONTH) + 1;
    }
    
    /**
     * Get the days of current date
     * @return the days of current date
     */
    public static int getCurrentDay()
    {
    	return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }
    
    /**
    * Get the days of dt represented date
    * @param dt Date
    * @return int the days of d represented date
    * @throws NullPointerException If d is null, throw this exception.
    */
    public static int getDay(Date dt)throws NullPointerException
    {
    	if(dt == null)
    		throw new NullPointerException("Date can not be null");

	    Calendar cld=Calendar.getInstance();
	    cld.setTime(dt);
	    
	    return cld.get(Calendar.DAY_OF_MONTH);
    }
    
    
    public static int getDayOfWeek(Date dt)throws NullPointerException
    {
    	if(dt == null)
    		throw new NullPointerException("Date can not be null");

	    Calendar cld=Calendar.getInstance();
	    cld.setTime(dt);
	    
	    return cld.get(Calendar.DAY_OF_WEEK);
    }
    
    //convert String to Date by patternOfString
    public static Date string2Date(String str, String patternOfString) {
    	    	
    	if(str == null || str.trim().length() == 0)
    	{
    		str = "1970-01-01";
    		patternOfString = Constants.DEFAULT_DATE_FORMAT;
    	}
    	SimpleDateFormat sdf = new SimpleDateFormat(patternOfString);
    	try {
			return sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
    }
    
    //return a list contains all weekend days by year and month
    public static List<Integer> getWeekendDays(Date date)
    {
    	List<Integer> weekendList = new ArrayList<Integer>();
    	int year = getYear(date);
    	int month = getMonth(date);
    	
    	Calendar cal = Calendar.getInstance();
    	
    	//daysOfMonth equals how many days in parameter 'date' represented date
    	int daysOfMonth = daysInMonth(date);
    	//new Date().getd
    	
    	for(int i = 1; i <= daysOfMonth; i++)
    	{
    		//because Jan. is 0, so here we must minus 1 to month
    		cal.set(year, month - 1 , i);
    		// 1 - Sunday, 2 - Monday.....  , 7 - Saturday
    		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
    		//weekend day, add them to weekend day list
    		if(dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY)
    			weekendList.add(new Integer(i));
    	}
    	
    	return weekendList;
    }
    
	
	
	
	private static DateFormat createDateFormat(String pattern) 
	{
		SimpleDateFormat sdf = new SimpleDateFormat(pattern,Locale.ENGLISH);
		//TimeZone gmt = TimeZone.getTimeZone("GMT");
		//sdf.setTimeZone(gmt);
		sdf.setLenient(true);
		
		return sdf;
	}

	//return how many days in d represented date
	public static int daysInMonth(Date d)
	{
		int year = getYear(d);
		
		// Jan. is 0, Feb. is 1.... and so on
    	int month = getMonth(d);
    	
    	if(month == 1 || month == 3 || month == 5 || month == 7 
    			|| month == 8 || month == 10  || month == 12)
    		return 31;
    	
    	if(month == 2)
    	{
    		if(isLeapYear(year))
    			return 29;
    		else
    			return 28;
    	}
    	
    	return 30;
    		
	}
	
	//是否为闰年
	public static boolean isLeapYear(int year) {
		int gregorianCutoverYear = 1582;
	    
		return year >= gregorianCutoverYear ?
	            ((year%4 == 0) && ((year%100 != 0) || (year%400 == 0))) : 
	            (year%4 == 0); 
	    
	}
	
	public static Date getFirstSecond(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();

	}
	
	public static Date getLastSecond(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();

	}	
	
	public static Date getFirstDayOfWeek(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		int daycount = calendar.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY;
		calendar.add(Calendar.DAY_OF_WEEK, -daycount);
		return calendar.getTime();
	}	
	
	@Deprecated
	public static int sub(Date begin, Date end)
	{
//	    long from = begin.getTime();
//	    long to = end.getTime();
//	    return (int)((to - from) / (1000 * 60 * 60 * 24));
		
		return getDistDays(begin, end);
	}
	
	public static int getDistDays(Date start, Date end) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(start);
		long timeStart = calendar.getTimeInMillis();
		calendar.setTime(end);
		long timeEnd = calendar.getTimeInMillis();
		
		return (int)(Math.abs(timeEnd-timeStart) / (1000 * 60 * 60 * 24));
	}
	
	public static Date sum(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, days);
		
		return cal.getTime();
	}
	
}
