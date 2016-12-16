package com.lobinary.实用工具.数据备份;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import com.lobinary.实用工具.实用工具;
import com.lobinary.工具类.常用工具;

public class 备份数据表格模板 extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3406342210670532970L;
	private String[] columnNames = { "未备份文件夹位置", "本机存储文件夹位置", "外部存储文件夹位置","文件数量", "文件大小" };
	public static List<备份记录> 备份记录列表 = new ArrayList<备份记录>();
	
	public synchronized void 清除数据(int addType){
		for (int i = 0; i < 备份记录列表.size();) {
			备份记录 备份记录 = 备份记录列表.get(i);
			if(备份记录.addType == addType){
//				实用工具.log("准备移除："+备份记录.getUnBackupFolder());
				备份数据表格模板.备份记录列表.remove(i);
				continue;
			}
			i++;
		}
		fireTableDataChanged();
	}
	
	public void addData(JTable t,备份记录 bd) throws Exception{
		File fo = new File(bd.getUnBackupFolder());
		int fileNums = 常用工具.getFileNums(fo);
		long fileSize = 常用工具.getFileSize(fo);
		String formetFileSize = 常用工具.formetFileSize(fileSize);
		bd.setSize(fileSize);
		bd.setFileNums(fileNums);
		bd.setFormetFileSize(formetFileSize);
		备份记录列表.add(bd);
		数据备份工具.totalSize = 0l;
		fireTableDataChanged();
	}

	public void refreshTotalSize() {
		for (备份记录 dd : 备份记录列表) {
			数据备份工具.totalSize += dd.size;
		}
	}
	
	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return 50;//data.length;
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public Object getValueAt(int row, int col) {
		Object object = "";
		try {
			object = 备份记录列表.get(row).get(col);
		} catch (Exception e) {
		}
		return object;
	}

	public Class<?> getColumnClass(int c) {
		Object v = getValueAt(0, c);
		if(v==null)return String.class;
		return v.getClass();
	}

	/*
	 * Don't need to implement this method unless your table's editable.
	 */
	public boolean isCellEditable(int row, int col) {
		return false;
	}

	/*
	 * Don't need to implement this method unless your table's data can change.
	 */
	public void setValueAt(Object value, int row, int col) {
		备份记录列表.get(row).set(col,value);
		fireTableCellUpdated(row, col);
	}

	public List<备份记录> getData() {
		return 备份记录列表;
	}

	public void addData(JTable 备份文件夹列表, String dirStr) throws Exception {
		备份记录 bd = new 备份记录();
		bd.setAddType(备份记录.手动添加);
		bd.setUnBackupFolder(dirStr);
		bd.setLocalFolder("未扫描到本地“备份文件夹”");
		bd.setOutStoreFolder("未扫描到“外部存储备份文件夹”");
		addData(备份文件夹列表, bd);
	}

	public void removeDate(int index) {
		备份记录列表.remove(index);
		fireTableDataChanged();
	}

	public void loadConfigData() throws Exception {
		@SuppressWarnings("unchecked")
		List<备份记录> btt = (List<备份记录>) 实用工具.getConfig("备份记录列表");
		if(btt!=null){
			备份记录列表 = btt;
			for (int i = 0; i < 备份记录列表.size(); i++) {
				备份记录 备份记录 = 备份记录列表.get(i);
				File fo = new File(备份记录.getUnBackupFolder());
				int fileNums = 常用工具.getFileNums(fo);
				long fileSize = 常用工具.getFileSize(fo);
				String formetFileSize = 常用工具.formetFileSize(fileSize);
				备份记录.setSize(fileSize);
				备份记录.setFileNums(fileNums);
				备份记录.setFormetFileSize(formetFileSize);
//				实用工具.log(备份记录.getAddType());
				备份记录列表.set(i,备份记录);
			}
			fireTableDataChanged();
		}
	}

}
