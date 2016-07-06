package com.boce.test.婧然问题;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 
  *
  *	从svn提交路径列表中，找到maven打包的数据
  *
 */
public class SVN目录遍历问题 {
	
	
	public static void main(String[] args) {
		List<String> svn文件路径  = new ArrayList<String>();
		svn文件路径.add("svn/svn2/svn3/project-svn/特殊/src/com/lobinary/test/test.java");
		svn文件路径.add("svn/svn2/svn3/project-svn/src/com/lobinary/test/util/test.java");
		svn文件路径.add("svn/svn2/svn3/project-svn/resource/spring/spring.xml");
		svn文件路径.add("svn/svn2/svn3/project-svn/resource/spring.xml");
		svn文件路径.add("svn/svn2/svn3/project-svn/特殊/resource/log4j.properties");
		//为表现出特殊形式，我们在路径中加一个“特殊”路径,如无特殊路径，则非常容易截取无用路径

		String 本地项目基本路径 = "C:/temp/test/";
		long startTime = System.currentTimeMillis();
		//简单实现过程
		for (String fileStr : svn文件路径) {
//			System.out.println("准备开始寻找文件:"+fileStr);
			File f = searchFile(本地项目基本路径,fileStr.replace(".java", ".classes"));
			System.out.println("寻找到文件:"+f.getAbsolutePath());
		}
		long endTime = System.currentTimeMillis();
		System.out.println("总耗时："+(endTime-startTime)+"毫秒");
	}

	/**
	 * 寻找文件
	 * @param fileStr
	 * @return
	 */
	private static File searchFile(String localFolder,String fileStr) {
		String fileStrTemp = fileStr;
		while(fileStrTemp!=null){
//			System.out.println("2准备开始寻找文件："+fileStrTemp);
			File searchedFile = searchFileInFolder(localFolder,fileStrTemp);
			if(searchedFile!=null)return searchedFile;
			fileStrTemp = getNextFileStr(fileStrTemp);
		}
		System.out.println("没有寻找到文件："+fileStr);
		return null;
	}

	/**
	 * 在指定目录搜索文件
	 * @param fileStr
	 * @return
	 */
	private static File searchFileInFolder(String localFolderStr,String fileStr) {
		File localFolder = new File(localFolderStr);
		File localFile = new File(localFolderStr+"/"+fileStr);
		File result= null;
		if(localFile.isFile()&&localFile.exists()){
			return localFile;
		}else{
			String[] fileList = localFolder.list();
			for (String fileStrTemp : fileList) {
				File f = new File(localFolderStr+"/"+fileStrTemp);
				if(f.isDirectory()){
//					System.out.println("遍历到路径:"+f.getAbsolutePath()+"寻找文件"+fileStr);
					result = searchFileInFolder(f.getAbsolutePath(), fileStr);
					if(result!=null){
						break;
					}
				}
			}
		}
//		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!路径"+localFolderStr+"没有找到文件"+fileStr);
		return result;
	}

	/**
	 * 获取文件路径
	 * @param fileStr
	 * @return
	 */
	private static String getNextFileStr(String fileStr) {
		if(fileStr.contains("/")){
			String substring = fileStr.substring(fileStr.indexOf("/")+1);
//			System.out.println("截取路径为:"+substring);
			return substring;
		}
		return null;
	}

}
