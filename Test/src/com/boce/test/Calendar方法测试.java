package com.boce.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Calendar方法测试 {

	/**
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {
		test4();
	}
	
	public static void 测试一(){
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);
		System.out.println(c.getTime().toLocaleString());
		c.add(Calendar.MONTH, 1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.add(Calendar.DAY_OF_MONTH, -1);
		System.out.println(c.getTime().toLocaleString());
		System.out.println(c.get(Calendar.MONTH));
	}
	
	/**
	 * 将开始日期和结束日期随着时间不同置成1和本月最后一天
	 */
	public static void 测试二(){
		Date startDate = new Date(111200000L);
		Date endDate = new Date(2112000000L);
		System.out.println(startDate.toLocaleString());
		System.out.println(endDate.toLocaleString());
		Calendar c = Calendar.getInstance();
		c.setTime(startDate);
		c.set(Calendar.DAY_OF_MONTH, 1);
		startDate = c.getTime();
		c.setTime(endDate);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.add(Calendar.MONTH, 1);
		c.add(Calendar.DAY_OF_MONTH, -1);
		endDate = c.getTime();
		System.out.println(startDate.toLocaleString());
		System.out.println(endDate.toLocaleString());
	}
	
	/**
	 * 开始2001-1-1到2013-12-31
	 */
	public static void test3(){
		Date startDate = new Date(32464562346l);
		Date endDate = new Date(34523234523l);
		System.out.println(startDate.toLocaleString());
		System.out.println(endDate.toLocaleString());
		Calendar c = Calendar.getInstance();
		c.setTime(startDate);
		c.set(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);
		startDate = c.getTime();
		c.setTime(endDate);
		c.set(Calendar.MONTH,0);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.add(Calendar.YEAR, 1);
		c.add(Calendar.DAY_OF_MONTH, -1);
		endDate = c.getTime();
		System.out.println(startDate.toLocaleString());
		System.out.println(endDate.toLocaleString());
	}
	
	public static void test4() throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse("20160101"));
		c.add(Calendar.DAY_OF_YEAR, -1);
		String beforeDay = sdf.format(c.getTime());
		System.out.println(beforeDay);
	}

}
