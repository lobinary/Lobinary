package com.lobinary.实用工具.数据备份;

import java.awt.Component;
import java.util.EventObject;

import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

public class 表格单元格编辑器之文件夹路径编辑器 implements TableCellEditor{
	
	private Object cellValue = "null";

	public 表格单元格编辑器之文件夹路径编辑器(Object cellValue) {
		super();
		this.cellValue = cellValue;
	}

	@Override
	public Object getCellEditorValue() {
		return cellValue;
	}

	@Override
	public boolean isCellEditable(EventObject anEvent) {
		return true;
	}

	@Override
	public boolean shouldSelectCell(EventObject anEvent) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean stopCellEditing() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void cancelCellEditing() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addCellEditorListener(CellEditorListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeCellEditorListener(CellEditorListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		// TODO Auto-generated method stub
		return null;
	}

}
