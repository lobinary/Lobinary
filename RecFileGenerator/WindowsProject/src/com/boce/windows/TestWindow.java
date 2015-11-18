/*
 * @(#)TestWindow.java     V1.0.0      @上午9:42:42
 *
 * Project: WindowsProject
 *
 * Modify Information:
 *    Author        Date        Description
 *    ============  ==========  =======================================
 *    lvbin       	2015年5月26日    	Create this file
 * 
 * Copyright Notice:
 *     Copyright (c) 2009-2014 Unicompay Co., Ltd. 
 *     1002 Room, No. 133 North Street, Xi Dan, 
 *     Xicheng District, Beijing ,100032, China 
 *     All rights reserved.
 *
 *     This software is the confidential and proprietary information of
 *     Unicompay Co., Ltd. ("Confidential Information").
 *     You shall not disclose such Confidential Information and shall use 
 *     it only in accordance with the terms of the license agreement you 
 *     entered into with Unicompay.
 */
package com.boce.windows;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.boce.util.FileUtil;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年5月26日 上午10:40:42
 * @version V1.0.0 描述 : 创建文件TestWindow
 * 
 * 
 * 
 */
public class TestWindow extends JFrame {

	private static final long serialVersionUID = -8467577457627001142L;

	/**
	 * 主窗口
	 */
	private JFrame mainFrame = new JFrame();

	/**
	 * 保存文件路径
	 */
	private File saveFile = new File("c:\\WebTool.txt");
	
	/**
	 * 用来使用文件存储数据的map
	 */
	private List<JButton> fileList = new ArrayList<JButton>();

	/**
	 * 清除所有数据按钮(恢复默认设置)
	 */
	private JButton addButton = new JButton("添加新网址");

	@SuppressWarnings("unchecked")
	public void start() {
		try {
			mainFrame.setLayout(new GridLayout(6,1));
			addButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					final JDialog j = new JDialog();
					final JTextField webName = new JTextField();
					final JTextField webUrl = new JTextField();
					JLabel webNameLabel = new JLabel("网站名称：");
					JLabel webUrlLabel = new JLabel("网站地址：");
					JButton resetButton = new JButton("重置");
					JButton submitButton = new JButton("提交");
					resetButton.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							webName.setText("");
							webUrl.setText("");
						}
					});
					submitButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							JButton webButton = new JButton(webName.getText());
							webButton.setActionCommand(webUrl.getText());
							webButton.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									openUrl(webUrl.getText());
								}
							});
							fileList.add(webButton);//在此处将新添加的按钮写入到文件中保存，然后执行下边的添加操作
							try {
								FileUtil.fileIsExist(saveFile);
								FileUtil.saveObj(saveFile, fileList);
							} catch (IOException e1) {
								System.out.println("存储数据异常，异常原因为："+e);
							}
							mainFrame.add(webButton);
							mainFrame.setVisible(true);
							System.out.println("添加完毕");
							j.dispose();
						}
						
					});
					j.setLayout(new GridLayout(3, 1));
					j.setModal(true);
					j.add(webNameLabel);
					j.add(webName);
					j.add(webUrlLabel);
					j.add(webUrl);
					j.add(resetButton);
					j.add(submitButton);
					j.setBounds(550, 450, 300, 100);
					j.setVisible(true);
					
				}
			});
			mainFrame.add(addButton);
			if(!FileUtil.fileIsExist(saveFile)){//如果第一次使用，文件不存在，创建对象
				fileList = new ArrayList<JButton>();
				FileUtil.saveObj(saveFile, fileList);
			}
			fileList = FileUtil.getObj(saveFile, List.class);
			for (final JButton j:fileList) {
				j.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						openUrl(j.getActionCommand());
					}
				});
				mainFrame.add(j);
			}
			mainFrame.setBounds(500, 400, 200, 200);
			mainFrame.addWindowListener(new WindowAdapter() {

				@Override
				public void windowClosing(WindowEvent e) {
					super.windowClosing(e);
					System.out.println("系统关闭");
					System.exit(0);
				}
				
			});
			mainFrame.setVisible(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * <pre>
	 * 使用浏览器打开url
	 * </pre>
	 *
	 * @param url
	 */
	public void openUrl(String url){
		try {
			System.out.println("准备打开网址："+url);
			Runtime.getRuntime().exec("rundll32   url.dll,FileProtocolHandler   " + url);
		} catch (IOException e) {
			System.out.println("打开网址异常");
		}
	}
	
}
