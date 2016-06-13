package com.boce.test.normal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class 以文本做固态存储 {
	
	public static void main(String[] args) throws IOException {
		File 文本数据 = new File("c:\\Test.data");
		File 备份文本数据 = new File("c:\\Test.data.bak");
		copyFile(文本数据,备份文本数据);
//		if(!备份文本数据.exists()){
//			备份文本数据.
//		}
//		BufferedReader 文本数据读取器 = null;
//		文本数据读取器 = new BufferedReader(new FileReader(文本数据));
//		BufferedWriter 文本数据写入器 = null;
//		文本数据写入器 = new BufferedWriter(new FileWriter(文本数据));
//		List<File> allFileList = new ArrayList<File>();
//		文本数据读取器.readLine();
	}
	
	/**
	 * 将读取的文件放到list中
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static List<String> readLine2List(File file) throws IOException{
		List<String> fileLineContent = new ArrayList<String>();
		BufferedReader reader = null;
		reader = new BufferedReader(new FileReader(file));
		String fileLine ;
		while((fileLine = reader.readLine())!=null){
//			System.out.println(s);
			fileLineContent.add(fileLine);
		}
		reader.close();
		return fileLineContent;
	}
	
	/**
	 * 复制文件
	 * @param fromFile
	 * @param toFile
	 * @throws IOException
	 */
	public static void copyFile(File fromFile,File toFile) throws IOException{
		fileIsExist(fromFile);
		fileIsExist(toFile);
		List<String> fileLineContent = readLine2List(fromFile);
		BufferedWriter writer = null;
		writer = new BufferedWriter(new FileWriter(toFile));
		for(String writerLine:fileLineContent){
			writer.append(writerLine);
			writer.newLine();
		}
		writer.close();
	}
	
	/**
	 * 判断文件是否存在，如果不存在就创建该文件
	 * @param file
	 * @throws IOException
	 */
	public static void fileIsExist(File file) throws IOException{
		if(!file.exists()){
			file.createNewFile();
		}
	}

}
