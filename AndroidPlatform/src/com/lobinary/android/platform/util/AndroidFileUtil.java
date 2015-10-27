package com.lobinary.android.platform.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import com.lobinary.android.common.util.log.LogUtil;

import android.os.Environment;

public class AndroidFileUtil {
	
	public static File getFile(String fileName){

		File extDir = Environment.getExternalStorageDirectory();
		File androidLocalFile = new File(extDir, fileName);
		 
		try {
			if(androidLocalFile.exists()){
				LogUtil.out("文件已经存在,准备返回该文件");
			    androidLocalFile.setWritable(Boolean.TRUE);
				return androidLocalFile;
			}else{
				LogUtil.out("文件不存在,新建文件后返回");
			    androidLocalFile.createNewFile();
			    androidLocalFile.setWritable(Boolean.TRUE);
			}
		    System.out.println("准备返回本地文件"+androidLocalFile.exists()+":"+androidLocalFile);
		} catch (Exception e) {
		    e.printStackTrace();
		    System.out.println(e.getMessage());
		}
		return null;
	}
	
	/**
	 * 从文件读取对象(单个)
	 * @param objFile
	 * @param c
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getObj(File objFile,Class<T> c) {
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		T object = null;
		try {
			fis = new FileInputStream(objFile);
			ois = new ObjectInputStream(fis);
			object = (T) ois.readObject();
		} catch(Exception e) {
			System.out.println("读取文件失败"+e.getMessage());
		} finally {
			try {
				ois.close();
				fis.close();
			} catch (IOException e) {
				System.out.println("关闭异常"+e.getMessage());
			}
		}
		return object;
		
	}

}
