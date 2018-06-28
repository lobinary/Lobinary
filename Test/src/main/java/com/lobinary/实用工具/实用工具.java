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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import javax.swing.JTextField;

import com.lobinary.实用工具.Java源码注释翻译工具.Java源码注释翻译工具;
import com.lobinary.实用工具.主窗口.关于我们弹出窗口;
import com.lobinary.实用工具.主窗口.实用工具标签标准类;
import com.lobinary.实用工具.数据备份.数据备份工具;
import com.lobinary.实用工具.文件夹工具.文件夹扫描工具;
import com.lobinary.实用工具.文件批量替换工具.文件批量替换工具;
import com.lobinary.工具类.date.DateUtil;
import com.lobinary.工具类.file.FileUtil;

@SuppressWarnings("unchecked")
public class 实用工具 {

	public static JFrame 主窗口框架;
	private static JTextArea 日志TextArea = new JTextArea("欢迎使用实用工具");
	final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private static long 日志最大长度 = 20000;
	
	private static File configFile = new File("c:/Lobxxx/实用工具/配置信息.xxx");
	private static Map<String,Object> config = new HashMap<String,Object>();
	private static JTextField menuLog = new JTextField("系统最后一条日志");
	
	public static void log(Object log){
		System.out.println(DateUtil.getCurrentTime()+" : "+log);
		String 原始内容 = 日志TextArea.getText();
		if(原始内容.length()>日志最大长度){
			原始内容 = 原始内容.substring(log.toString().length());
		}
		日志TextArea.setText(原始内容+"\n"+log);
		日志TextArea.setCaretPosition(日志TextArea.getDocument().getLength());
		menuLog.setText(""+log);
	}
	
	/**
	 * 如需添加新Tab，请在这里添加，便会自动加载tab信息
	 * @return
	 */
	public List<实用工具标签标准类> loadTabInfo(){
		List<实用工具标签标准类> list = new ArrayList<实用工具标签标准类>();
		list.add(new Java源码注释翻译工具());
		list.add(new 数据备份工具());
		list.add(new 文件批量替换工具());
		list.add(new 文件夹扫描工具());
		return list;
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
					window.主窗口框架.setVisible(true);
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
		主窗口框架 = new JFrame();
//		frame.setResizable(false);
		主窗口框架.setIconImage(Toolkit.getDefaultToolkit().getImage(实用工具.class.getResource("/image/L-logo.jpg")));
		主窗口框架.setTitle("实用工具1.0——————By Lobxxx");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int framewidth = 1280;
		int frameHight = 800;
		主窗口框架.setBounds((dim.width-framewidth)/2, (dim.height-frameHight)/2, 1280, 800);
		主窗口框架.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		主窗口框架.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		panel.add(tabbedPane);

		for (实用工具标签标准类 实用工具标签标准类 : loadTabInfo()) {
			tabbedPane.addTab(实用工具标签标准类.getClass().getSimpleName(), null, 实用工具标签标准类, null);
		}
		
		JPanel 日志主窗口 = new JPanel();
		tabbedPane.addTab("日志", null, 日志主窗口, null);
		日志主窗口.setLayout(new GridLayout(0, 1, 0, 0));
		
		日志TextArea.setBackground(new Color(235, 235, 235));
		日志TextArea.setEditable(false);
		日志TextArea.setLineWrap(true);
		JScrollPane 日志ScrollPaneTextArea =new JScrollPane(日志TextArea);
		日志主窗口.add(日志ScrollPaneTextArea);
		
		JMenuBar menuBar = new JMenuBar();
		主窗口框架.setJMenuBar(menuBar);
		
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
				JDialog 关于我们弹出窗口 = new 关于我们弹出窗口(主窗口框架,true);
				关于我们弹出窗口.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				关于我们弹出窗口.setVisible(true);
			}
		});
		menu_3.add(menuItem);
		
		
		menuLog.setSize(80, 30);
		menuLog.setLocation(300, 0);
		menuLog.setEnabled(false);
		menuBar.add(menuLog);
		menuLog.setColumns(10);
	}
}
