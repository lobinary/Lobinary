package com.ljr.IncrementalFetch;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.jar.JarArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;


public class DealWar {
	public void unzip(String warPath,String unzipPath){
		File warFile = new File(warPath);
		try {
			BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(warFile));
			ArchiveInputStream in = new ArchiveStreamFactory().createArchiveInputStream(ArchiveStreamFactory.JAR,bufferedInputStream);
			JarArchiveEntry entry = null;
			
			while ((entry = (JarArchiveEntry) in.getNextEntry()) != null){
				if(entry.isDirectory()){
					new File(unzipPath,entry.getName()).mkdir();
				}else{
					OutputStream out = FileUtils.openOutputStream(new File(unzipPath,entry.getName()));
					IOUtils.copy(in, out);
					out.close();
				}
			}
			
			in.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ArchiveException e) {
			e.printStackTrace();
		}
	}
	
	public void zip(String destFile,String zipDir){
		File outFile = new File(destFile);
		try {
			outFile.createNewFile();
			//创建文件
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(outFile));
			ArchiveOutputStream out = new ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.JAR, bufferedOutputStream);
			if(zipDir.charAt(zipDir.length() - 1) != '/'){
				zipDir += '/';
			}
			
			Iterator<File> files = FileUtils.iterateFiles(new File(zipDir), null, true);
			while (files.hasNext()){
				File file = files.next();
				ZipArchiveEntry zipArchiveEntry  = new ZipArchiveEntry(file,file.getPath().replace(zipDir.replace("/", "\\"), ""));
				out.putArchiveEntry(zipArchiveEntry);
				IOUtils.copy(new FileInputStream(file), out);
				out.closeArchiveEntry();
			}
			out.finish();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (ArchiveException e) {
			e.printStackTrace();
		}
	}
	
	public String getFileNameWithNoSux(String fileName){
		if ((fileName !=null) && (fileName.length()>0)){
			int dot = fileName.lastIndexOf(".");
			if ((dot>-1)&&(dot<(fileName.length()))){
				return fileName.substring(0, dot);
			}
		}
		return fileName;
	}
}
