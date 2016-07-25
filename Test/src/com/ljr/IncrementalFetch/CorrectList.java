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
 * ��svn diff�������ļ��嵥�е��ļ�·���������������жϣ��ı�ɹ��̱�����ȫ�������ļ����ļ�ϵͳ�е�·���������һ�������б��ȡ�ļ���
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
	 * Ѱ���ļ�
	 * @param fileStr
	 * @return
	 */
	private static File searchFile(String localFolder,String fileStr) {
		System.out.println("localFolder:"+localFolder+",fileStr:"+fileStr);
		String fileStrTemp = fileStr;
		while(fileStrTemp!=null){
			System.out.println("2׼����ʼѰ���ļ���"+fileStrTemp);
			File searchedFile = searchFileInFolder(localFolder,fileStrTemp);
			if(searchedFile!=null)return searchedFile;
			fileStrTemp = getNextFileStr(fileStrTemp);
		}
		System.out.println("û��Ѱ�ҵ��ļ���"+fileStr);
		return null;
	}

	/**
	 * ��ָ��Ŀ¼�����ļ�
	 * @param fileStr
	 * @return
	 */
	private static File searchFileInFolder(String localFolderStr,String fileStr) {
		File localFolder = new File(localFolderStr);
		File localFile = new File(localFolderStr+"/"+fileStr);
		File result= null;
		if(localFile.isFile()&&localFile.exists()){
			System.out.println("Ѱ�ҵ��ļ�"+localFile.getAbsolutePath());
			return localFile;
		}else{
			String[] fileList = localFolder.list();
			for (String fileStrTemp : fileList) {
				File f = new File(localFolderStr+"/"+fileStrTemp);
				if(f.isDirectory()){
					System.out.println("������·��:"+f.getAbsolutePath()+"Ѱ���ļ�"+fileStr);
					result = searchFileInFolder(f.getAbsolutePath(),fileStr);
					if(result!=null){
						System.out.println("Ѱ�ҵ��ļ�"+f.getAbsolutePath().replace("\\", "/")+"/"+fileStr);
						break;
					}
				}
			}
		}
//		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!·��"+localFolderStr+"û���ҵ��ļ�"+fileStr);
		return result;
	}

	/**
	 * ��ȡ�ļ�·��
	 * @param fileStr
	 * @return
	 */
	private static String getNextFileStr(String fileStr) {
		if(fileStr.contains("/")){
			String substring = fileStr.substring(fileStr.indexOf("/")+1);
			System.out.println("��ȡ·��Ϊ:"+substring);
			return substring;
		}
		return null;
	}
	
	public static void main(String[] args) {
		CorrectList test = new CorrectList();
		test.dealFileList("D:/Draw/list.txt","D:/Draw/unzip/");
	}

}
