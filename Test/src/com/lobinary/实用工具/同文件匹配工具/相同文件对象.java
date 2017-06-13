package com.lobinary.实用工具.同文件匹配工具;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.lobinary.工具类.file.FileUtil;

public class 相同文件对象 {
	
	List<File> sameFileList = new ArrayList<File>();
	
	public static 相同文件对象 get(File f) throws FileNotFoundException, IOException{
		相同文件对象 相同文件对象 = new 相同文件对象();
		相同文件对象.sameFileList.add(f);
		return 相同文件对象;
	}

	public 相同文件对象 add(File f){
		sameFileList.add(f);
		return this;
	}
	
	public boolean removeSameFile(){
		boolean result = true;
		for (int i = 1; i < sameFileList.size(); i++) {
			File f = sameFileList.get(i);
			boolean delete = f.delete();
			if(!delete)result = false;
			System.out.println(f.getAbsolutePath()+"文件移除结果："+delete);
		}
		return result;
	}

	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("=================================="+sameFileList.get(0).length()+"====================================================\n");
		for (File f : sameFileList) {
			sb.append(f.getAbsolutePath()+"\n");
		}
		sb.append("=================================="+sameFileList.get(0).length()+"====================================================\n");
		return sb.toString();
	}
	
}
