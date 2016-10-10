package com.lobinary.实用工具;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
import com.lobinary.实用工具.主窗口.实用工具标签标准类;
import com.lobinary.实用工具.数据备份.数据备份工具;
import com.lobinary.实用工具.文件批量替换工具.文件批量替换工具;
import com.lobinary.工具类.date.DateUtil;
import com.lobinary.工具类.file.FileUtil;

@SuppressWarnings("unchecked")
public class 实用工具 {

	private JFrame frame;
	private static JTextArea 日志TextArea = new JTextArea("欢迎使用实用工具");
	final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	
	private static File configFile = new File("c:/Lobxxx/实用工具/配置信息.xxx");
	private static Map<String,Object> config = new HashMap<String,Object>();
	
	public static void log(Object log){
		System.out.println(DateUtil.getCurrentTime()+" : "+log);
		String 原始内容 = 日志TextArea.getText();
		日志TextArea.setText(原始内容+"\n"+log);
	}
	
	static{
		try {
			log("准备加载配置信息...");
			if(configFile.exists()){//如果有配置文件，我们就装载配置
				config = FileUtil.getObj(configFile , config.getClass());
			}else{
				File configFolder = new File(configFile.getAbsolutePath().substring(0,configFile.getAbsolutePath().lastIndexOf("\\")));
				if(!configFolder.exists())configFolder.mkdirs();
				FileUtil.saveObj(configFile, config);
			}
			log("配置信息加载完毕");
		} catch (Exception e) {
			log("初始化异常:"+e);
		}
	}
	
	public static Object getConfig(String key){
		return config.get(key);
	}
	
	public static void saveConfig(String key ,Object value){
		config.put(key, value);
		FileUtil.saveObj(configFile, config);
	}

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
		
		panel.add(tabbedPane);

		数据备份工具 数据备份主窗口 = new 数据备份工具();
		tabbedPane.addTab("数据备份", null, 数据备份主窗口, null);

		文件批量替换工具 文件批量替换工具 = new 文件批量替换工具();
		tabbedPane.addTab("文件批量替换工具", null, 文件批量替换工具, null);
		
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
		
		JMenuItem 保存当前配置菜单按钮 = new JMenuItem("保存配置信息");
		保存当前配置菜单按钮.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				实用工具标签标准类 实用工具标签 = (实用工具标签标准类) tabbedPane.getSelectedComponent();
				实用工具标签.saveConfig();
			}
		});
		menu.add(保存当前配置菜单按钮);
		
		JMenuItem 恢复默认配置菜单按钮 = new JMenuItem("恢复默认配置(不保存，仅本次有效)");
		恢复默认配置菜单按钮.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				实用工具标签标准类 实用工具标签 = (实用工具标签标准类) tabbedPane.getSelectedComponent();
				实用工具标签.loadDefaultConfig();
			}
		});
		
		JMenuItem menuItem_2 = new JMenuItem("加载配置信息");
		menuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				实用工具标签标准类 实用工具标签 = (实用工具标签标准类) tabbedPane.getSelectedComponent();
				实用工具标签.loadConfig();
			}
		});
		menu.add(menuItem_2);
		menu.add(恢复默认配置菜单按钮);
		
		JMenuItem 安全退出菜单按钮 = new JMenuItem("安全退出");
		安全退出菜单按钮.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("系统准备安全退出！");
				System.exit(1);
			}
		});
		menu.add(安全退出菜单按钮);
		
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
