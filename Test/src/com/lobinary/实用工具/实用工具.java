package com.lobinary.实用工具;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import com.lobinary.实用工具.主窗口.关于我们弹出窗口;
import com.lobinary.实用工具.数据备份.数据备份工具;
import javax.swing.JSeparator;

public class 实用工具 {

	private JFrame frame;
	private static JTextArea 日志TextArea = new JTextArea("欢迎使用实用工具");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					实用工具 window = new 实用工具();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void log(String log){
		System.out.println(log);
		String 原始内容 = 日志TextArea.getText();
		日志TextArea.setText(原始内容+"\n"+log);
	}

	/**
	 * Create the application.
	 */
	public 实用工具() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
//		frame.setResizable(false);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(实用工具.class.getResource("/image/L-logo.jpg")));
		frame.setTitle("实用工具1.0——————By Lobxxx");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int framewidth = 1280;
		int frameHight = 800;
		frame.setBounds((dim.width-framewidth)/2, (dim.height-frameHight)/2, 1280, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		panel.add(tabbedPane);
		
		数据备份工具 数据备份主窗口 = new 数据备份工具();
		tabbedPane.addTab("数据备份", null, 数据备份主窗口, null);
		
		JPanel 日志主窗口 = new JPanel();
		tabbedPane.addTab("日志", null, 日志主窗口, null);
		日志主窗口.setLayout(new GridLayout(0, 1, 0, 0));
		
		日志TextArea.setBackground(new Color(235, 235, 235));
		日志TextArea.setEditable(false);
		日志TextArea.setLineWrap(true);
		JScrollPane 日志ScrollPaneTextArea =new JScrollPane(日志TextArea);
		日志主窗口.add(日志ScrollPaneTextArea);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu menu = new JMenu("菜单");
		menuBar.add(menu);
		
		JMenuItem menuItem_2 = new JMenuItem("保存当前配置");
		menu.add(menuItem_2);
		
		JMenuItem menuItem_3 = new JMenuItem("恢复默认配置");
		menu.add(menuItem_3);
		
		JMenuItem menuItem_4 = new JMenuItem("安全退出");
		menuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("系统准备安全退出！");
				System.exit(1);
			}
		});
		menu.add(menuItem_4);
		
		JMenu menu_1 = new JMenu("工具");
		menuBar.add(menu_1);
		
		JMenu menu_2 = new JMenu("窗口");
		menuBar.add(menu_2);
		
		JMenu menu_3 = new JMenu("关于");
		menuBar.add(menu_3);
		
		JMenuItem menuItem_1 = new JMenuItem("版本介绍");
		menu_3.add(menuItem_1);
		
		JMenuItem menuItem = new JMenuItem("关于我们");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog 关于我们弹出窗口 = new 关于我们弹出窗口(frame,true);
				关于我们弹出窗口.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				关于我们弹出窗口.setVisible(true);
			}
		});
		menu_3.add(menuItem);
	}
}
