package com.lobinary.实用工具.数据备份;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import com.lobinary.java.多线程.TU;
import com.lobinary.实用工具.实用工具;

public class 数据备份工具 extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8854133778693907627L;
	private static boolean isLoading = false;
	private JLabel 提示信息 = new JLabel("提示信息：");
	JLabel 提示信息内容 = new JLabel("欢迎使用备份工具,如需备份数据,请先点击\"扫描数据\"进行 未备份数据 扫描");
	private final JButton 备份数据到外部存储按钮 = new JButton("备份数据到外部存储");
	private final JButton 从未备份迁移到备份文件夹按钮 = new JButton("从未备份迁移到备份文件夹");

	/**
	 * Create the panel.
	 */
	public 数据备份工具() {
		setLayout(null);
		this.setSize(1280, 800);
		JButton 扫描数据按钮 = new JButton("扫描数据");
		扫描数据按钮.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loging("扫描数据中");
			}
		});
		扫描数据按钮.setBounds(0, 0, 101, 23);
		add(扫描数据按钮);
		备份数据到外部存储按钮.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				log("备份数据到外部存储中");
			}
		});
		备份数据到外部存储按钮.setBounds(109, 0, 166, 23);
		
		add(备份数据到外部存储按钮);
		从未备份迁移到备份文件夹按钮.setBounds(279, 0, 195, 23);
		
		add(从未备份迁移到备份文件夹按钮);
		
		提示信息.setBounds(484, 4, 78, 15);
		add(提示信息);
		
		提示信息内容.setForeground(Color.BLUE);
		提示信息内容.setBounds(547, 4, 733, 15);
		add(提示信息内容);
		TableModel 表格模板 = new 备份数据表格模板();
		JTable 备份文件夹列表 = new JTable(表格模板);
		TableColumn column = null;  
		for (int i = 0; i < 5; i++) {  
		    column = 备份文件夹列表.getColumnModel().getColumn(i);
		    switch (i) {
			case 0:  
			case 1:  
			case 2:  
				column.setPreferredWidth(400); 
				TableCellEditor 文件夹路径编辑器 = new 表格单元格编辑器之文件夹路径编辑器();
				column.setCellEditor(文件夹路径编辑器);
				break;
			case 3:  column.setPreferredWidth(90); break;
			case 4:  column.setPreferredWidth(90); break;
			default: column.setPreferredWidth(400); break;
			}
		} 
		JScrollPane tsp = new JScrollPane(备份文件夹列表);
		tsp.setBounds(0, 33, 1262, 680);
		备份文件夹列表.setFillsViewportHeight(true);
		add(tsp);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(new Color(200, 200, 200));
		g.drawLine(0, 26, 1280, 26);
	}
	
	public void log(String log){
		isLoading = false;
		提示信息内容.setText(log);
		实用工具.log(log);
	}
	
	public void loging(String log){
		提示信息内容.setText(log);
		if(!isLoading){
			isLoading = true;
			System.out.println("启动新县城");
			new Thread(){
				@Override
				public void run() {
					super.run();
					while(isLoading){
						TU.s(500);
						提示信息内容.setText(提示信息内容.getText()+".");
					}
				}
				
			}.start();
		}
		实用工具.log(log);
	}
}
