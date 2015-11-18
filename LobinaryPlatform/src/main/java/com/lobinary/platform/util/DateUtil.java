package com.lobinary.platform.util;

import java.util.Calendar;
import java.util.Date;

/**
 * 
 * 日期工具类
 * 
 * @author lvbin 
 * @since 2014年11月18日 下午6:30:57
 * @version V1.0.0 Description : Create this file
 * 
 *         
 *
 */
public class DateUtil {
	
	private static Calendar calendar = Calendar.getInstance();
	
	/**
	 * 返回YYYY-MM-DD格式日期
	 * @return
	 */
	public static String getNowFormatedDate(){
		calendar.setTime(new Date(System.currentTimeMillis()));
		return "" + calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONDAY)+1) + "-"  + calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 返回YYYYMMDD格式日期
	 * @return
	 */
	public static String getNowDate(){
		calendar.setTime(new Date(System.currentTimeMillis()));
		return "" + calendar.get(Calendar.YEAR) + (calendar.get(Calendar.MONDAY)+1) + calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 返回YYYY-MM-DD_hh24:mm:ss格式日期
	 * @return
	 */
	public static String getNowFormatedTime(){
		calendar.setTime(new Date(System.currentTimeMillis()));
		return "" + calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONDAY)+1) + "-"  + calendar.get(Calendar.DAY_OF_MONTH) + "_"  + calendar.get(Calendar.HOUR_OF_DAY) + "-"  + calendar.get(Calendar.MINUTE) + "-"  + calendar.get(Calendar.SECOND);
	}
	
	/**
	 * 返回YYYYMMDDhh24mmss格式日期
	 * @return
	 */
	public static String getNowTime(){
		calendar.setTime(new Date(System.currentTimeMillis()));
		return "" + calendar.get(Calendar.YEAR) + (calendar.get(Calendar.MONDAY)+1) + calendar.get(Calendar.DAY_OF_MONTH) + calendar.get(Calendar.HOUR_OF_DAY) + calendar.get(Calendar.MINUTE) + calendar.get(Calendar.SECOND);
	}
	
	public static void main(String[] args) {
		System.out.println(getNowTime());
	}
	
}
