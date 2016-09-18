package com.lobinary.工具类;

import java.text.SimpleDateFormat;
import java.util.Date;

public class 常用工具 {
	
	public static void main(String[] args) {
		long转日期(1471017599000l);
	}

	private static void long转日期(long time) {
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		System.out.println("long转日期 : 输入("+time+"),输出:("+sdf.format(new Date(time))+")");  
	}
	
	

}
