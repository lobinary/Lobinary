package com.boce.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 移除重复文件方法
 * 方法步骤
 * 		1.获取文件夹文件名称list
 * 		2.轮番用每个文件,用用户想要的长度字符进行比较,一次类推,
 * @author Boce
 *
 */
public class RemoveDuplicateFile {
	
	public static int DUPLICATE_SIZE = 8;
	public static List<String> DUPLICATE_FILE_NAME_LIST = new ArrayList<String>();
	
	public static void main(String[] args) {
//		String folderName = "d:/TestFolder";
		String folderName = "G:/KuGou";
		File folder = new File(folderName);
		String[] foldersFileList = folder.list();
		System.out.println("==========去除后缀名和空格任务开始!==========");
		for(int i=0;i<foldersFileList.length;i++){
			System.out.print(foldersFileList[i] + ":");
			foldersFileList[i] = foldersFileList[i].substring(0, foldersFileList[i].length()-4).replace(" ", "");
			System.out.println(foldersFileList[i]);
		}
		System.out.println("==========去除后缀名和空格任务结束!==========");
		for(String fileName : foldersFileList){
			for(int index=0;index<=(fileName.length()-DUPLICATE_SIZE);index++){
				String strTemp = fileName.substring(index, index + DUPLICATE_SIZE);
				for(String fileNameTemp : foldersFileList){
					if(fileNameTemp.equals(fileName)){
						continue;
					}
					if(fileNameTemp.contains(strTemp)){
						if(!DUPLICATE_FILE_NAME_LIST.contains(fileNameTemp)){
							DUPLICATE_FILE_NAME_LIST.add(fileNameTemp);
							System.out.println(fileNameTemp + "与" + fileName + "名称存在重复字符串:" + strTemp);
						}
						if(!DUPLICATE_FILE_NAME_LIST.contains(fileName)){
							DUPLICATE_FILE_NAME_LIST.add(fileName);
						}
						
					}
				}
			}
		}
		File test = new File(folderName + "/haha.txt");
		System.out.println(test.renameTo(new File(folderName + "/hahaReName.txt")));
	}

}
