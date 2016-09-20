package com.lobinary.实用工具.数据备份;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public class 备份数据表格模板 extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3406342210670532970L;
	 private String[] columnNames = { "未备份文件夹位置", "本机存储文件夹位置", "外部存储文件夹位置","文件数量", "文件大小" };
	private static List<备份记录> 备份记录列表 = new ArrayList<备份记录>();
	
	public void setData(List<备份记录> 备份记录列表){
		备份数据表格模板.备份记录列表 = 备份记录列表;
		fireTableDataChanged();
	}
	
	public void addData(JTable t,备份记录 bd){
		备份记录列表.add(bd);
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

}
