package com.lobinary.工具类;

import java.util.Date;

import com.lobinary.工具类.date.DateUtil;

/**
 * 定时器工具类
 * 
 * 负责定时启动以及相关的提示等功能
 * @author Lobinary
 * @see 本工具类使用了下方代码
 * @see com.lobinary.工具类.LU
 * @see com.lobinary.工具类.date.DateUtil
 *
 */
public class TU {
	
	private static String appName = "House";
	
	/**
	 * 启动定时启动器
	 * @param args
	 */
	public static void startJob(){
		LU.changeLogFile("/apps/logs/house/house"+DateUtil.getDate(new Date())+".log");
		
		LU.l("======================"+appName+"定时任务=====执行开始======================");
		System.out.println("A");
		LU.changeSystemOut2Log();
		System.out.println("B");
		LU.l("");
		LU.l("======================"+appName+"定时任务=====执行结束======================");
	
		
	}

}
