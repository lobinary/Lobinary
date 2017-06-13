package com.lobinary.实用工具.同文件匹配工具;

import com.lobinary.实用工具.主窗口.实用工具标签标准类;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;

public class 同文件匹配工具  extends 实用工具标签标准类{
	

	private JTextField 源码文件夹路径 = new JTextField("D:/tool/Git/Lobinary/JDK8Source/src/main/java");
	private JFileChooser fileFolderChooser = new JFileChooser("");
	private boolean 正在执行 = false;
	
	private List<File> 该目录下所有文件列表;
	
	private int 当前线程个数 = 0;

	private int 文件总数 = 0;
	private int 异常文件个数 = 0;
	private int 成功文件个数 = 0;
	private int 完成文件个数 = 0;
	private boolean 终止任务 = false;
	
	public 同文件匹配工具() {
			final Container 父窗口 = this.getParent();
			this.setSize(1280, 800);
			setLayout(null);
			
			JButton 翻译按钮 = new JButton("开始匹配");
			翻译按钮.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(正在执行){
						翻译按钮.setText("终止翻译中");
						终止任务 = true;
						return ;
					}else{
						翻译按钮.setText("结束翻译");
					}
					
					new Thread(new Runnable() {
						@Override
						public void run() {
							正在执行 = true;
							String 源码路径 = 源码文件夹路径.getText();
							if(源码路径.length()==0){
								alert("温馨提示","请选择源码路径");
								选择源码路径(父窗口);
							}
							try {
								源码路径 = 源码文件夹路径.getText();
								翻译路径下的所有Java文件注释(源码路径);
							} catch (Exception e1) {
								e1.printStackTrace();
								out(e1);
								alert("扫描文件报错："+e1);
							}
							正在执行 = false;
							翻译按钮.setText("开始翻译");
						}
					}).start();
					
				}
			});
			翻译按钮.setBounds(0, 0, 121, 23);
			add(翻译按钮);
			
			源码文件夹路径.setBounds(122, 1, 1042, 21);
			add(源码文件夹路径);
			源码文件夹路径.setColumns(10);
			
			JButton 选择源码目录按钮 = new JButton("选择目标目录");
			选择源码目录按钮.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					选择源码路径(父窗口);
				}

			});
			选择源码目录按钮.setBounds(1174, 0, 106, 23);
			add(选择源码目录按钮);
		}

	/**
	 * 
	 */
	private static final long serialVersionUID = -967610964325420971L;
	
	
	
	

}
