package com.lobinary.实用工具.同文件匹配工具;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lobinary.工具类.file.FileUtil;

public class 相同文件对象 {
	
	public static long 文件总大小 = 0l;
	
	Map<String,List<File>> sameFileMap = new HashMap<String,List<File>>();
	
	public static 相同文件对象 get(File f) throws FileNotFoundException, IOException{
		相同文件对象 相同文件对象 = new 相同文件对象();
		ArrayList<File> fl = new ArrayList<File>();
		fl.add(f);
		相同文件对象.sameFileMap.put(FileUtil.getMD5(f),fl);
		return 相同文件对象;
	}

	public 相同文件对象 add(File f) throws FileNotFoundException, IOException{
		List<File> list = sameFileMap.get(FileUtil.getMD5(f));
		if(list==null){
			ArrayList<File> fl = new ArrayList<File>();
			fl.add(f);
			this.sameFileMap.put(FileUtil.getMD5(f),fl);
		}else{
			list.add(f);
		}
		return this;
	}
	
	public boolean removeSameFile(){
		boolean result = true;
		for(List<File> fl:sameFileMap.values()){
			if(fl.size()==1)continue;
			
			//查找出最短文件名的文件予以保留
			File saveFile = fl.get(0);
			for (int i=1;i<fl.size();i++){
				File f = fl.get(i);
				if(f.getName().length()<saveFile.getName().length()){
					saveFile = f;
				}
			}
			fl.remove(saveFile);
			System.out.println(saveFile.getAbsolutePath()+"被保留");
			
			//删除剩余文件
			for(File f:fl){
				文件总大小  += f.length();//计算总大小
				boolean delete = f.delete();
				if(!delete)result = false;
				System.out.println(f.getAbsolutePath()+"文件移除结果："+delete);
			}
		}
		return result;
	}

	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("======================================================================================\n");
		for (List<File> fl : sameFileMap.values()) {
			for(File f:fl){
				sb.append(f.getAbsolutePath()+"\n");
			}
		}
		sb.append("======================================================================================\n");
		return sb.toString();
	}
	
}
