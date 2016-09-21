package com.lobinary.实用工具.数据备份;

import java.io.File;
import java.io.Serializable;

import com.lobinary.工具类.常用工具;

public class 备份记录 implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9165097031291570625L;
	
	public final static int 手动添加 = 0;
	public final static int 扫描添加 = 1;
	public final static int 其他添加 = 1;
	
	public final static int BACKUP_ONLY_FILE = 0;
	public final static int BACKUP_ONLY_FOLDER = 1;
	public final static int BACKUP_FILE_AND_FOLDER = 2;
	public final static int BACKUP_OTHER = 9;
	

	public String unBackupFolder;
	public String localFolder;
	public String outStoreFolder;
	public boolean backup;
	public int addType = 扫描添加;//默认扫描添加
	public int fileNums;
	public long size;
	public String formetFileSize;
	public int folderLevel;
	public int backupType;
	public String data;
	public String data2;
	
	public 备份记录() {
		super();
	}
	public 备份记录(String unBackupFolder, String localFolder, String outStoreFolder, int fileNums, String totalSize) {
		super();
		this.unBackupFolder = unBackupFolder;
		this.localFolder = localFolder;
		this.outStoreFolder = outStoreFolder;
		this.fileNums = fileNums;
		this.formetFileSize = totalSize;
	}
	public Object get(int i){
		switch (i) {
		case 0: return unBackupFolder;
		case 1: return localFolder;
		case 2: return outStoreFolder;
		case 3: return fileNums;
		case 4: return formetFileSize;
		default:return null;
		}
	}
	public Object set(int i,Object v){
		switch (i) {
		case 0: return unBackupFolder = (String) v;
		case 1: return localFolder = (String) v;
		case 2: return outStoreFolder = (String) v;
		case 3: return fileNums = (int) v;
		case 4: return formetFileSize = (String) v;
		default:return null;
		}
	}
	
	public String getUnBackupFolder() {
		return unBackupFolder;
	}
	public void setUnBackupFolder(String unBackupFolder) throws Exception {
		this.unBackupFolder = unBackupFolder;
		File fo = new File(unBackupFolder);
		int fileNums = 常用工具.getFileNums(fo);
		long fileSize = 常用工具.getFileSize(fo);
		String formetFileSize = 常用工具.formetFileSize(fileSize);
		this.fileNums = fileNums;
		this.size = fileSize;
		this.formetFileSize = formetFileSize;
	}
	public String getLocalFolder() {
		return localFolder;
	}
	public void setLocalFolder(String localFolder) {
		this.localFolder = localFolder;
	}
	public String getOutStoreFolder() {
		return outStoreFolder;
	}
	public void setOutStoreFolder(String outStoreFolder) {
		this.outStoreFolder = outStoreFolder;
	}
	public int getFileNums() {
		return fileNums;
	}
	public void setFileNums(int fileNums) {
		this.fileNums = fileNums;
	}
	public int getFolderLevel() {
		return folderLevel;
	}
	public void setFolderLevel(int folderLevel) {
		this.folderLevel = folderLevel;
	}
	public int getBackupType() {
		return backupType;
	}
	public void setBackupType(int backupType) {
		this.backupType = backupType;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getData2() {
		return data2;
	}
	public void setData2(String data2) {
		this.data2 = data2;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getFormetFileSize() {
		return formetFileSize;
	}
	public void setFormetFileSize(String formetFileSize) {
		this.formetFileSize = formetFileSize;
	}
	public boolean isBackup() {
		return backup;
	}
	public void setBackup(boolean backup) {
		this.backup = backup;
	}
	public int getAddType() {
		return addType;
	}
	public void setAddType(int addType) {
		this.addType = addType;
	}

}
