package com.lobinary.工具类;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import com.lobinary.工具类.date.DateUtil;
import com.lobinary.工具类.file.FileUtil;

/**
 * 日志工具类
 * 
 * @see DateUtil
 * @author Lobinary
 *
 */
public class LU {
	
	private static File logFile;
	private static FileWriter fileWritter;
	private static BufferedWriter bufferWritter;
	private static boolean changedSystemOut = false;
	
	static{
		try {
			logFile = FileUtil.createFile(new File("/apps/logs/default/"+DateUtil.getDate(new Date())+".lu"));
			if(!logFile.exists())logFile.createNewFile();
			System.out.println("当前日志工具LU输出路径为默认路径："+logFile.getAbsolutePath());
			fileWritter = new FileWriter(logFile.getAbsolutePath(),true);
			bufferWritter = new BufferedWriter(fileWritter);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void changeLogFile(String fileLocation){
		try {
			bufferWritter.close();
			logFile = FileUtil.createFile(new File(fileLocation));
			System.out.println("日志工具LU输出路径切换为："+logFile.getAbsolutePath());
			fileWritter = new FileWriter(logFile.getAbsolutePath(),true);
			bufferWritter = new BufferedWriter(fileWritter);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 更改System Out 流到文件
	 * 注意，如果将此输出流切换后，将不会再控制台显示日志
	 */
	public static boolean changeSystemOut2Log(){
		try {
			LUSystemOutPrintStream out = new LUSystemOutPrintStream(new FileOutputStream(logFile,true));
			out.setSystemOut(System.out);
			System.setOut(out);
			System.setErr(out);
			changedSystemOut = true;
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void l(String log){
		try {
			bufferWritter.write(DateUtil.getCurrentTime()+" - "+log+"\n");
			bufferWritter.flush();
			if(!changedSystemOut){
			    System.out.println(log);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
