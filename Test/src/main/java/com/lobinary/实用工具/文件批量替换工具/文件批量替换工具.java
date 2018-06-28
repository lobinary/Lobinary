package com.lobinary.实用工具.文件批量替换工具;

import com.lobinary.实用工具.主窗口.实用工具标签标准类;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextField;

public class 文件批量替换工具 extends 实用工具标签标准类 {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3767156681875416565L;
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public 文件批量替换工具() {
		this.setSize(1280, 800);
		setLayout(null);
		
		JButton 扫描目录按钮 = new JButton("扫描目录");
		扫描目录按钮.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		扫描目录按钮.setBounds(0, 0, 93, 23);
		add(扫描目录按钮);
		
		textField = new JTextField();
		textField.setBounds(100, 1, 1156, 21);
		add(textField);
		textField.setColumns(10);
	}
}
