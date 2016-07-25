package com.ljr.IncrementalFetch;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class GetIncreFile {
	public GetIncreFile(File file,String localWorkspace){
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String fileinfo;
			try {
				while((fileinfo = br.readLine())!=null){
					MoveFile.moveFile(fileinfo,localWorkspace);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("没有这个文件"+file.getName());
		}
	}
}
