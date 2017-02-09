package com.l.web.house.util;

import java.util.Calendar;
import java.util.Date;

public class DU {

	public static Date getDate(String Year){
		Year = Year.trim();
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, Integer.parseInt(Year));
		c.set(Calendar.MONTH,0);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	public static Date getDate(String Year,String month){
		Year = Year.trim();
		month = month.trim();
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, Integer.parseInt(Year));
		c.set(Calendar.MONTH,Integer.parseInt(month));
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	public static Date 获取几月前的时间(String month){
		month = month.trim();
		Calendar c1 = Calendar.getInstance();
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.SECOND, 0);
		int m1 = Integer.parseInt(month);
		c1.add(Calendar.MONTH, (-1*m1));
		return c1.getTime();
	}

	public static Date 获取几天前的时间(String day){
		Calendar c1 = Calendar.getInstance();
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.SECOND, 0);
		int m1 = Integer.parseInt(day.trim());
		c1.add(Calendar.DAY_OF_MONTH, (-1*m1));
		return c1.getTime();
	}
	
}
