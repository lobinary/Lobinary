package com.lobinary.实用工具.数据备份;

import javax.swing.table.AbstractTableModel;

public class 备份数据表格模板 extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3406342210670532970L;
	 private String[] columnNames = { "未备份文件夹位置", "本机存储文件夹位置", "外部存储文件夹位置","文件数量", "文件大小" };
	// private Object[][] data = new Object[2][5];
//	String[] columnNames = { "First Name", "Last Name", "Sport", "# of Years", "Vegetarian" };
	Object[][] data = { { "c:/test", "f:/asdfasdf", "h:/sdflksdf", new Integer(845), "1.24GB" }, { "c:/test2", "f:/klefsdf", "h:/sjdkf", new Integer(542), "100M"}};

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return data.length;
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public Object getValueAt(int row, int col) {
		return data[row][col];
	}

	@SuppressWarnings("unchecked")
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
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
		data[row][col] = value;
		fireTableCellUpdated(row, col);
	}

}
