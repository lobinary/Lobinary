package com.lobinary.platform.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {

	//Logger logger = LoggerFactory.getLogger(this.getClass());
	static Logger logger = LoggerFactory.getLogger(LogUtil.class);
	private static File logFolder = new File("D:/Lobinary项目/log/Platform");
	private static File logFile = new File("D:/Lobinary项目/log/Platform/log.log");
	private static File exceptionFile = new File("D:/Lobinary项目/log/exception-platform.log");
	private static OutputStream logOutStream;
	private static OutputStreamWriter logOutStreamWriter;
	private static OutputStream exceptionOutStream;
	private static OutputStreamWriter exceptionOutStreamWriter;
	

	static {
		if (!logFolder.exists()) {
			logFolder.mkdir();
		}
		if (!logFile.exists()) {
			try {
				logFile.createNewFile();
				logOutStream = new FileOutputStream(logFile, true);
				logOutStreamWriter = new OutputStreamWriter(logOutStream, "GB18030");
				logOutStreamWriter.write("本文件为Lobinary-platform系统运行日志文件，记录日常的执行日志，便于程序出现问题时，及时维修相关功能模块，请勿删除该文件\n");
				logOutStreamWriter.flush();
				exceptionFile.createNewFile();
				exceptionOutStream = new FileOutputStream(exceptionFile, true);
				exceptionOutStreamWriter = new OutputStreamWriter(exceptionOutStream, "GB18030");
				exceptionOutStreamWriter.write("本文件为Lobinary-platform系统运行异常日志文件，提供程序的异常记录，及时发现问题，请勿删除该文件\n");
				exceptionOutStreamWriter.flush();
			} catch (IOException e) {
				logger.warn("创建日志文件出现错误，请检查程序执行模块是否出现问题");
				e.printStackTrace();
			}
		}
		try {
			logOutStream = new FileOutputStream(logFile, true);
			logOutStreamWriter = new OutputStreamWriter(logOutStream, "GB18030");
			exceptionOutStream = new FileOutputStream(exceptionFile, true);
			exceptionOutStreamWriter = new OutputStreamWriter(exceptionOutStream, "GB18030");
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("日志系统初始化完成！log文件位置为：" + logFile.getAbsolutePath() + "  ,  Exception文件位置为：" + exceptionFile.getAbsolutePath());
	}

	@SuppressWarnings("deprecation")
	synchronized public static boolean log(String logStr) {
		try {
			String logInfo = new Date(System.currentTimeMillis()).toLocaleString() + "      " + logStr + "\n";
			logOutStreamWriter.write(logInfo);
			logOutStreamWriter.flush();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("deprecation")
	synchronized public static boolean logException(String logStr) {
		StringBuffer stringBuffer = new StringBuffer();
		try {
			String logInfo = new Date(System.currentTimeMillis()).toLocaleString() + "      " + logStr + "\n";
			stringBuffer.append("***************************************************************\n");
			stringBuffer.append("*******************手动Exception错误提示开始************************\n");
			stringBuffer.append(logInfo);
			stringBuffer.append("*******************手动Exception错误提示结束************************\n");
			stringBuffer.append("***************************************************************\n");
			logger.info(stringBuffer.toString());
			exceptionOutStreamWriter.write(stringBuffer.toString());
			exceptionOutStreamWriter.flush();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("deprecation")
	synchronized public static boolean logException(Exception e) {
		try {
			e.printStackTrace();
			StringBuffer logInfo = new StringBuffer();
			logInfo.append("=============================Exception开始=============================\n");
			logInfo.append("Exception抛出时间:" + new Date(System.currentTimeMillis()).toLocaleString() + "\n");
			logInfo.append(e.toString() + "\n");
			for (StackTraceElement ste : e.getStackTrace()) {
				logInfo.append(ste.toString() + "\n");
			}
			logInfo.append("=============================Exception结束=============================\n");
			exceptionOutStreamWriter.write(logInfo.toString());
			exceptionOutStreamWriter.flush();
			return true;
		} catch (IOException ee) {
			ee.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 帮助输出
	 * @param map
	 */
	public static void systemOut(Map<String,Object> map){
		for(String key :map.keySet()){
			logger.info("key:	" + key + "\t value :" + map.get(key));
		}
	}
	
	public static void systemOut(Object[] array){
		for(Object s : array){
			logger.info(s+ "");
		}
	}
	
	public static void systemOut(Set<Object> set){
		for(Object o:set){
			logger.info(o + "");
		}
	}

	/**
	 * 返回logger
	 * @param classParam
	 * @return
	 */
	public static Logger getLog(Class<?> classParam) {
		return LoggerFactory.getLogger(classParam);
	}
	
}
