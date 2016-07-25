package com.ljr.IncrementalFetch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
/**
 * 
 * @author lvbin
 * 将svn diff产生的文件清单中的文件路径，经过处理与判断，改编成工程编译后的全量包中文件在文件系统中的路径，方便进一步根据列表抽取文件。
 *
 */
public class CorrectList {
	public void dealFileList(String fileName,String unzipPath){
		File listFile = new File(fileName);
		String brContext = null;
		BufferedReader br = null;
		String contextTrans = null;
		ArrayList<String> listFileContext = new ArrayList<String>();
		int i,k=0;

			try {
				br = new BufferedReader(new FileReader(listFile));
				while((brContext = br.readLine())!=null){
					listFileContext.add(brContext);
				}
				
				for (i=0;i<listFileContext.size();i++){
					contextTrans = listFileContext.get(i);
					//SearchFileInPath(contextTrans,unzipPath);
					searchFile(unzipPath,contextTrans);
					}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
//	public String SearchFileInPath(String path,String unzipPath){
//		int i,j;
//		String halfPath = null;
//		String[] cutPath = path.replaceAll("\\\\", "/").trim().replaceAll(".java", ".class").split("/");
//		for (j=0;j<cutPath.length;j++){
//			halfPath = unzipPath;
//			halfPath = halfPath+"\\"+cutPath[j];
//			if((new File(halfPath)).exists()==true){
//				for(i=j+1;i<cutPath.length;i++){
//					halfPath=halfPath+"\\"+cutPath[i];
//				}
//				break;
//			}
//		}
//		System.out.println(halfPath);
//		return halfPath;
//	}
	
	/**
	 * 寻找文件
	 * @param fileStr
	 * @return
	 */
	private static File searchFile(String localFolder,String fileStr) {
		System.out.println("localFolder:"+localFolder+",fileStr:"+fileStr);
		String fileStrTemp = fileStr;
		while(fileStrTemp!=null){
			System.out.println("2准备开始寻找文件："+fileStrTemp);
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
			System.out.println("寻找到文件"+localFile.getAbsolutePath());
			return localFile;
		}else{
			String[] fileList = localFolder.list();
			for (String fileStrTemp : fileList) {
				File f = new File(localFolderStr+"/"+fileStrTemp);
				if(f.isDirectory()){
					System.out.println("遍历到路径:"+f.getAbsolutePath()+"寻找文件"+fileStr);
					result = searchFileInFolder(f.getAbsolutePath(),fileStr);
					if(result!=null){
						System.out.println("寻找到文件"+f.getAbsolutePath().replace("\\", "/")+"/"+fileStr);
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
			System.out.println("截取路径为:"+substring);
			return substring;
		}
		return null;
	}
	
	public static void main(String[] args) {
		CorrectList test = new CorrectList();
		test.dealFileList("D:/Draw/list.txt","D:/Draw/unzip/");
	}

}
