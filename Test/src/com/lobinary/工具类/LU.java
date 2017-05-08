package com.lobinary.工具类;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
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
	
	static{
		try {
			logFile = FileUtil.createFile(new File("/apps/log/"+DateUtil.getDate(new Date())+".log"));
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
	public static void changeSystemOut2Log(){
		try {
			System.setOut(new PrintStream(new FileOutputStream(logFile,true)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void l(String log){
		try {
			bufferWritter.write(DateUtil.getCurrentTime()+" - "+log+"\n");
			bufferWritter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
