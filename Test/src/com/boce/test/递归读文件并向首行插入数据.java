package com.boce.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class 递归读文件并向首行插入数据 {

	static int fileCounter = 0;
	static int folderCounter = 0;
	public static List<File> allFileList = new ArrayList<File>();

	public static void main(String[] args) throws IOException {
		readFile("F:\\tool\\Eclipse\\MyEclipseWorkSpace\\FAPPortal\\src\\main\\java\\com\\unicompayment\\fap\\portal");
//		readFile("C:\\portal");
		System.out.println("文件夹总数：" + folderCounter);
		System.out.println("文件总数：" + fileCounter);
		System.out.println(allFileList.size());
		outFileFirstLine();
//		File f = new File("C:\\Test.java");
	}

	public static boolean readFile(String filePath) {

		File file = new File(filePath);
		if (file.exists()) {// 如果该目录存在
			if (!file.isDirectory()) {// 如果是文件，输出目录路径
			// System.out.println("是文件");
			// System.out.println("path="+file.getPath());
				fileCounter++;
				allFileList.add(file);
			} else if (file.isDirectory()) {// 如果不是文件，而是文件夹，则循环
			// System.out.println("是文件夹！");
				folderCounter++;
				String[] fileList = file.list();// 返回一个字符串数组，这些字符串指定此抽象路径名表示的目录中的文件和目录。
				if (fileList != null && fileList.length > 0) {
					for (int i = 0; i < fileList.length; i++) {// 循环文件，或者是文件夹
						File readFile = new File(filePath + "\\" + fileList[i]);// 重新设置路径
						if (!readFile.isDirectory()) {// 如果是文件，输出目录路径
							allFileList.add(readFile);
							// System.out.println("path="+readFile.getPath());
							fileCounter++;
						} else if (readFile.isDirectory()) {// 如果不是文件，而是文件夹的话，则返回去重新执行readFile方法(迭代)
							readFile(filePath + "\\" + fileList[i]);
						}
					}
				} else {
					// System.out.println("该目录下面为空！");
				}

			}
		} else if (!file.exists()) {// 该目录不存在
			System.out.println("该目录或文件不存在");
		}

		return true;
	}

	public static void outFileFirstLine() throws IOException {
		int add = 0;
		int unadd = 0;
		int java = 0;
		BufferedReader reader = null;
		for (File file : allFileList) {
			// System.out.println(file.getPath());
			if (file.getPath().substring(file.getPath().length() - 4).equals("java")) {
				java++;
				reader = new BufferedReader(new FileReader(file));
				String firstLine = reader.readLine();
//				System.out.print(firstLine);
				if (firstLine.contains("package")) {
					unadd++;
					插入数据到首行技术展示(file);
//					System.out.println("未加过注释" + file.getPath());
				} else {
					add++;
//					System.out.println("加过注释" + file.getPath());
				}
			} else {
				System.out.println("████████████发现非JAVA文件████████████：" + file.getPath());
			}
		}
		System.out.println("未加过注释java文件总数：" + unadd);
		System.out.println("加过注释java文件总数：" + add);
		System.out.println("文件总数：" + allFileList.size());
		System.out.println("java文件总数：" + java);
		BufferedWriter writer = null;
		for (File file : allFileList) {
			// System.out.println(file.getPath());
			if (file.getPath().substring(file.getPath().length() - 4).equals("java")) {
				java++;
				reader = new BufferedReader(new FileReader(file));
				String firstLine = reader.readLine();
//				System.out.print(firstLine);
				if (firstLine.contains("package")) {
//					writer = new BufferedWriter(new FileWriter(file));
					unadd++;
//					System.out.println("未加过注释" + file.getPath());
				} else {
					add++;
//					System.out.println("加过注释" + file.getPath());
				}
			} else {
				System.out.println("████████████发现非JAVA文件████████████：" + file.getPath());
			}
		}
	}
	
	@SuppressWarnings("resource")
	public static void 插入数据到首行技术展示(File f) throws IOException{
		List<String> fileContentList = new ArrayList<String>();
//		File f = new File("C:\\Test.java");
		BufferedReader reader = null;
		reader = new BufferedReader(new FileReader(f));
		String s;
		while((s = reader.readLine())!=null){
//			System.out.println(s);
			fileContentList.add(s);
		}
//		System.out.println(reader.readLine());
		BufferedWriter writer = null;
		writer = new BufferedWriter(new FileWriter(f));
		writer.append("/*");
		writer.newLine();
		writer.append(" * @(#)" + f.getName() + "     V1.0.0      @2015-3-24");
		writer.newLine();
		writer.append(" *");
		writer.newLine();
		writer.append(" * Project: ");
		writer.newLine();
		writer.append(" *");
		writer.newLine();
		writer.append(" * Modify Information:");
		writer.newLine();
		writer.append(" *    Author        Date        Description");
		writer.newLine();
		writer.append(" *    ============  ==========  =======================================");
		writer.newLine();
		writer.append(" *    lvbin       	2015-3-24     Create this file");
		writer.newLine();
		writer.append(" * ");
		writer.newLine();
		writer.append(" * Copyright Notice:");
		writer.newLine();
		writer.append(" *     Copyright (c) 2009-2014 Unicompay Co., Ltd. ");
		writer.newLine();
		writer.append(" *     1002 Room, No. 133 North Street, Xi Dan, ");
		writer.newLine();
		writer.append(" *     Xicheng District, Beijing ,100032, China ");
		writer.newLine();
		writer.append(" *     All rights reserved.");
		writer.newLine();
		writer.append(" *");
		writer.newLine();
		writer.append(" *     This software is the confidential and proprietary information of");
		writer.newLine();
		writer.append(" *     Unicompay Co., Ltd. (\"Confidential Information\").");
		writer.newLine();
		writer.append(" *     You shall not disclose such Confidential Information and shall use ");
		writer.newLine();
		writer.append(" *     it only in accordance with the terms of the license agreement you ");
		writer.newLine();
		writer.append(" *     entered into with Unicompay.");
		writer.newLine();
		writer.append(" */");
		for(String ss:fileContentList){
			writer.newLine();
			writer.append(ss);
		}
		writer.flush();
		writer.close();
		reader.close();
	}

}
