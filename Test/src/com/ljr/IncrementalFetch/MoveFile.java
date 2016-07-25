package com.ljr.IncrementalFetch;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;


public class MoveFile {
	
	public static void main(String[] args) {
		String path = "F:\\A";
		System.out.println(path);
		String qualifiedPath = path.replaceAll("\\\\", "/").trim().replaceAll(".java", ".class");
		System.out.println(qualifiedPath);
	}
	
	public static void moveFile(String path,String localWorkspace){
		System.out.println("path:"+path+";localWorkspace:"+localWorkspace);
		String qualifiedPath = path.replaceAll("\\\\", "/").replace("\\", "/").trim().replaceAll(".java", ".class");
//		String desPath = pathDes + qualifiedPath;
//		String sourcePath = pathSource + qualifiedPath;
		
		localWorkspace = localWorkspace.replaceAll("\\\\", "/").trim().replaceAll(".java", ".class");
		System.out.println("qualifiedPath:"+qualifiedPath+";localWorkspace:"+localWorkspace);
		String desPath = qualifiedPath.replace(localWorkspace,localWorkspace+"/incre/");
		String sourcePath =qualifiedPath;
		
		int a = 0;
		if(desPath.lastIndexOf("/")!=-1){
			a = desPath.lastIndexOf("/");
		}
		
		String initPath = desPath.substring(0, a);
		
		File sourceFile = new File(sourcePath);
		File desFile = new File(initPath);
		desFile.mkdirs();
		if(sourceFile.exists()==true){
//			System.out.println(sourceFile.getName());
//			sourceFile.renameTo(new File(desPath));
			try {
				System.out.println("desPath:"+desPath);
				FileUtils.copyFile(sourceFile, new File(desPath));
//				FileUtils.
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else{
			System.out.println(qualifiedPath+"-------do not exists!");
		}
	}
}
