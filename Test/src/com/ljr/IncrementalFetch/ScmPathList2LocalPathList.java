package com.ljr.IncrementalFetch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
/**
 * 
 * @author liujingran
 * 将svn diff产生的文件清单中的文件路径，经过处理与判断，改编成工程编译后的全量包中文件在文件系统中的路径，方便进一步根据列表抽取文件。
 *
 */
public class ScmPathList2LocalPathList {
	
	ArrayList<String> RecursionPath = new ArrayList<String>();
	static ArrayList<String> CorrectList = new ArrayList<String>();
	static ArrayList<String> listFileContext = new ArrayList<String>();
	ArrayList<String> CutPath = new ArrayList<String>();
	
	/**
	 * 判断一个指定的文件（带路径）是否完整（带路径）存在于指定的路径下
	 * @param localPathTemp
	 * @param svnPathTemp
	 * @return
	 */
	public boolean judgeFileInPath(String localPathTemp,String scmPathTemp){
		String pathCombine = (localPathTemp+"/"+scmPathTemp);
		System.out.println(pathCombine);
		if(new File(pathCombine).exists()&&(new File(pathCombine).isFile())==true){
			CorrectList.add(pathCombine);
			return true;
		}else
			return false;
	}
	
	/**
	 * 给出一个目录下的所有递归目录
	 * @param localPathTemp
	 * @return
	 */
	public ArrayList<String> getRecursionPath(String localPathTemp){
		String[] CurrentPathList = new File(localPathTemp).list();
		for(String CurrentPathListTemp:CurrentPathList){
			String combinePath = localPathTemp +"/"+ CurrentPathListTemp;
			if((new File(combinePath).isFile())==false&&new File(combinePath).exists()==true){
				RecursionPath.add(combinePath);
				getRecursionPath(combinePath);
			}
		}
		return RecursionPath;
	}
	
	/**
	 * 截掉scmPath的第一段
	 * @param scmPathTemp
	 * @return
	 */
	public ArrayList<String> cutPath(String scmPathTemp){
		String[] cutPath = scmPathTemp.replaceAll("\\\\", "/").replace(".java", ".class").trim().split("/");
		CutPath.clear();
		for(int i=0;i<cutPath.length;i++){
			CutPath.add(cutPath[i].toString());
		}
		return CutPath;
	}
	
	/**
	 * 将文件内容读入List：listFileContext
	 * @param filePath
	 */
	public void ReadFile(String filePath){
		String brContext = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
			while((brContext = br.readLine())!=null){
				listFileContext.add(brContext);
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
	}

	/**
	 * 判断外部路径在本地文件系统中是否存在
	 * 
	 */
//	public void findPathInLocalFileSystem(){
//		for(int i=0;i<listFileContext.size();i++){
//			String cutbrContext = listFileContext.get(i);
//			Out:
//			for (int j=0;j<RecursionPath.size();j++){
//				cutPath(cutbrContext);
//				for(int k=0;k<CutPath.size();k++){
//					if(judgeFileInPath(RecursionPath.get(j),CutPath.get(k))==true){
//						listFileContext.remove(i);
//						i--;
//						break Out;
//					}
//				}
//			}
//		}
//	}
	
	public void findPathInLocalFileSystem(){
		for(int i=0;i<listFileContext.size();i++){
			String cutbrContext = listFileContext.get(i).replaceAll("\\\\", "/").replace(".java", ".class").trim();
			while (cutbrContext.contains("/")){
				cutbrContext = cutbrContext.substring(cutbrContext.indexOf("/")+1);
				for(int j=0;j<RecursionPath.size();j++){
					if(judgeFileInPath(RecursionPath.get(j),cutbrContext)==true){
//						listFileContext.remove(i);
//						i--;
						break;
					}
				}
			}
		}
	}
	
	/**
	 * 将list输出到文件
	 * @param list
	 * @param fileName
	 * @return
	 */
	public File list2File(ArrayList<String> list,String fileName){
		try {
			FileWriter bw = new FileWriter(fileName);
			for(int i=0;i<list.size();i++){
				bw.write(list.get(i));
				bw.write("\r\n");
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		File afterTransFile = new File(fileName);
		return afterTransFile;
	}
	
	/**
	 * 将外部路径转换为本地路径
	 * @param localPath
	 * @param scmFileList
	 * @param localPathList
	 * @return
	 */
	public File ScmPath2LocalPath(String localPath,String scmFileList,String localPathList,String notFoundList) {
		ScmPathList2LocalPathList a = new ScmPathList2LocalPathList();	
		a.getRecursionPath(localPath);
		a.ReadFile(scmFileList);
		a.findPathInLocalFileSystem();
		a.list2File(listFileContext, notFoundList);
		return a.list2File(CorrectList,localPathList);
	}
	
	public static void main(String[] args) {
		ScmPathList2LocalPathList a = new ScmPathList2LocalPathList();
		a.ScmPath2LocalPath("D:\\Draw\\unzip", "D:\\Draw\\list.txt", "D:\\Draw\\CorrectList","D:\\Draw\\notFoundList");
	}
}
