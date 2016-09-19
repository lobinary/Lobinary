package com.lobinary.实用工具.数据备份;

public class 备份记录 {
	
	public 备份记录() {
		super();
	}
	public 备份记录(String unBackupFolder, String localFolder, String outStoreFolder, int fileNums, String totalSize) {
		super();
		this.unBackupFolder = unBackupFolder;
		this.localFolder = localFolder;
		this.outStoreFolder = outStoreFolder;
		this.fileNums = fileNums;
		this.totalSize = totalSize;
	}
	public String unBackupFolder;
	public String localFolder;
	public String outStoreFolder;
	public int fileNums;
	public String totalSize;
	public Object get(int i){
		switch (i) {
		case 0: return unBackupFolder;
		case 1: return localFolder;
		case 2: return outStoreFolder;
		case 3: return fileNums;
		case 4: return totalSize;
		default:return null;
		}
	}
	public Object set(int i,Object v){
		switch (i) {
		case 0: return unBackupFolder = (String) v;
		case 1: return localFolder = (String) v;
		case 2: return outStoreFolder = (String) v;
		case 3: return fileNums = (int) v;
		case 4: return totalSize = (String) v;
		default:return null;
		}
	}
	
	public String getUnBackupFolder() {
		return unBackupFolder;
	}
	public void setUnBackupFolder(String unBackupFolder) {
		this.unBackupFolder = unBackupFolder;
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
	public String getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(String totalSize) {
		this.totalSize = totalSize;
	}

}
