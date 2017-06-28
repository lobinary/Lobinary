package com.lobinary.test.normal;

import java.io.File;

public class 递归删除目录 {
	
	public static void main(String[] args) throws Exception {
		File 根目录 = new File("C:/temp/test");
		移除子目录(根目录);
	}
	
	
	public static void 移除子目录(File folder) throws Exception{
		if(folder.length()==0){
			boolean deleteSuccess = folder.delete();
			if(!deleteSuccess)throw new Exception("删除文件夹"+folder.getAbsolutePath()+"失败");
		}
		File[] fileList = folder.listFiles();
		for(File file:fileList){
			if(file.isDirectory()){
				移除子目录(file);
			}
		}
	}

}
