package com.lobinary.实用工具.Java源码注释翻译工具;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import com.lobinary.java.多线程.TU;
import com.lobinary.实用工具.主窗口.实用工具标签标准类;
import com.lobinary.工具类.JAU2;

public class Java源码注释翻译工具 extends 实用工具标签标准类 {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3767156681875412565L;
	private JTextField 源码文件夹路径 = new JTextField("C:/test");
	private JFileChooser fileFolderChooser = new JFileChooser("");
	private boolean 正在执行 = false;
	private JTextField 线程1处理文件内容;
	private JTextField 线程2处理文件内容;
	private JTextField 线程3处理文件内容;
	private JTextField 线程4处理文件内容;
	
	private List<File> 该目录下所有文件列表;
	
	/**
	 * Create the panel.
	 */
	public Java源码注释翻译工具() {
		final Container 父窗口 = this.getParent();
		this.setSize(1280, 800);
		setLayout(null);
		
		JButton 翻译按钮 = new JButton("开始翻译");
		翻译按钮.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(正在执行)alert("程序正在执行中，请等待执行完毕");
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
					}
				}).start();
				
			}
		});
		翻译按钮.setBounds(0, 0, 93, 23);
		add(翻译按钮);
		
		源码文件夹路径.setBounds(100, 1, 1064, 21);
		add(源码文件夹路径);
		源码文件夹路径.setColumns(10);
		
		JButton 选择源码目录按钮 = new JButton("选择源码目录");
		选择源码目录按钮.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				选择源码路径(父窗口);
			}

		});
		选择源码目录按钮.setBounds(1174, 0, 106, 23);
		add(选择源码目录按钮);
		
		总文件数量 = new JLabel("总文件数量：0个");
		总文件数量.setForeground(Color.BLUE);
		总文件数量.setFont(new Font("宋体", Font.PLAIN, 50));
		总文件数量.setBounds(14, 36, 854, 88);
		add(总文件数量);
		
		已完成文件数量 = new JLabel("已完成文件数量：0个");
		已完成文件数量.setForeground(new Color(0, 128, 0));
		已完成文件数量.setFont(new Font("宋体", Font.PLAIN, 50));
		已完成文件数量.setBounds(14, 103, 854, 88);
		add(已完成文件数量);
		
		剩余文件数量 = new JLabel("剩余文件数量：0个");
		剩余文件数量.setForeground(new Color(95, 158, 160));
		剩余文件数量.setFont(new Font("宋体", Font.PLAIN, 50));
		剩余文件数量.setBounds(14, 167, 854, 88);
		add(剩余文件数量);
		
		任务执行进度 = new JProgressBar();
		任务执行进度.setBounds(14, 268, 1102, 63);
		add(任务执行进度);
		
		JLabel 当前处理文件列表 = new JLabel("当前处理文件列表");
		当前处理文件列表.setFont(new Font("宋体", Font.PLAIN, 30));
		当前处理文件列表.setBounds(14, 358, 330, 55);
		add(当前处理文件列表);
		
		JLabel 线程1 = new JLabel("线程1：");
		线程1.setFont(new Font("宋体", Font.BOLD, 20));
		线程1.setBounds(14, 444, 84, 34);
		add(线程1);
		
		JLabel 线程2 = new JLabel("线程2：");
		线程2.setFont(new Font("宋体", Font.BOLD, 20));
		线程2.setBounds(14, 491, 84, 34);
		add(线程2);
		
		JLabel 线程3 = new JLabel("线程3：");
		线程3.setFont(new Font("宋体", Font.BOLD, 20));
		线程3.setBounds(14, 538, 84, 34);
		add(线程3);
		
		JLabel 线程4 = new JLabel("线程4：");
		线程4.setFont(new Font("宋体", Font.BOLD, 20));
		线程4.setBounds(14, 585, 84, 34);
		add(线程4);
		
		线程1处理文件内容 = new JTextField();
		线程1处理文件内容.setEditable(false);
		线程1处理文件内容.setBounds(82, 439, 1004, 36);
		add(线程1处理文件内容);
		线程1处理文件内容.setColumns(10);
		
		线程2处理文件内容 = new JTextField();
		线程2处理文件内容.setEditable(false);
		线程2处理文件内容.setColumns(10);
		线程2处理文件内容.setBounds(82, 485, 1004, 36);
		add(线程2处理文件内容);
		
		线程3处理文件内容 = new JTextField();
		线程3处理文件内容.setEditable(false);
		线程3处理文件内容.setColumns(10);
		线程3处理文件内容.setBounds(82, 532, 1004, 36);
		add(线程3处理文件内容);
		
		线程4处理文件内容 = new JTextField();
		线程4处理文件内容.setEditable(false);
		线程4处理文件内容.setColumns(10);
		线程4处理文件内容.setBounds(82, 579, 1004, 36);
		add(线程4处理文件内容);
		线程3处理文件内容.setText("");
	}
	
	private void 选择源码路径(final Container 父窗口) {
		fileFolderChooser.setCurrentDirectory(new File("c:/"));
		fileFolderChooser.changeToParentDirectory();
		fileFolderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int intRetVal = fileFolderChooser.showDialog(父窗口, "选择源码目录");
		if (intRetVal == JFileChooser.APPROVE_OPTION) {
			String dirStr = fileFolderChooser.getSelectedFile().getPath();
			源码文件夹路径.setText(dirStr);
			out("源码路径为:"+dirStr);
		}
		fileFolderChooser.setVisible(true);
	}

	private void 翻译路径下的所有Java文件注释(String 源码路径) throws Exception {
		File 源码根路径 = new File(源码路径);
		if(源码根路径.exists()&&源码根路径.isDirectory()){
			String[] 根目录下路径与文件 = 源码根路径.list();
			if(根目录下路径与文件==null||根目录下路径与文件.length==0){
				alert("该目录下无文件");
			}else{
				当前线程个数=0;
				当前线程个数 = 0;
				文件总数 = 0;
				完成文件个数 = 0;
				
				该目录下所有文件列表 = 扫描目录下的文件(源码根路径);
				文件总数 = 该目录下所有文件列表.size();
				
				总文件数量.setText("总文件数量："+文件总数+"个");
				已完成文件数量.setText("已完成文件数量："+0+"个");
				剩余文件数量.setText("剩余文件数量："+文件总数+"个");
				
				for (int i = 0; i < 4; i++) {
					new Thread(){
						@Override
						public void run() {
							this.setName(获取线程名称());
							while(true){
								File f = 获取需要翻译的文件();
								if(f==null)break;
								更新正在翻译的文件状态(this.getName(),f);
								try {
									JAU2.翻译(f);
								} catch (Exception e) {
									任务异常(this.getName(),e);
									e.printStackTrace();
								}
								更新完成文件个数();
							}
							更新线程完成状态(this.getName());
						}
					}.start();
				}
				while(!任务异常&&完成文件个数>0){
					TU.s(1000);
				}
				if(文件总数>0){
					alert("翻译完成，共"+完成文件个数+"个Java文件的注释被更改");
				}else{
					alert("该目录下没有Java文件或已经全部翻译完毕");
				}
			}
		}else{
			alert("源码根路径不存在");
		}
	}
	
	private synchronized void 更新正在翻译的文件状态(String name, File f) {
		switch (name) {
		case "1":
			线程1处理文件内容.setText(f.getAbsolutePath());
			break;
		case "2":
			线程2处理文件内容.setText(f.getAbsolutePath());
			break;
		case "3":
			线程3处理文件内容.setText(f.getAbsolutePath());
			break;
		case "4":
			线程4处理文件内容.setText(f.getAbsolutePath());
			break;
		default:
			break;
		}
	}

	private synchronized void 更新线程完成状态(String threadName) {
		switch (threadName) {
		case "1":
			线程1处理文件内容.setBackground(Color.green);
			break;
		case "2":
			线程2处理文件内容.setBackground(Color.green);
			break;
		case "3":
			线程3处理文件内容.setBackground(Color.green);
			break;
		case "4":
			线程4处理文件内容.setBackground(Color.green);
			break;
		default:
			break;
		}
	}
	
	private synchronized String 获取线程名称(){
		当前线程个数++;
		return ""+当前线程个数;
	}
	
	private int 当前线程个数 = 0;

	private int 文件总数 = 0;
	private int 完成文件个数 = 0;
	private boolean 任务异常 = false;
	private JLabel 总文件数量;
	private JLabel 已完成文件数量;
	private JLabel 剩余文件数量;
	private JProgressBar 任务执行进度;
	private synchronized void 任务异常(String threadName,Throwable e){
		任务异常 = true;
		switch (threadName) {
		case "1":
			线程1处理文件内容.setBackground(Color.red);
			break;
		case "2":
			线程2处理文件内容.setBackground(Color.red);
			break;
		case "3":
			线程3处理文件内容.setBackground(Color.red);
			break;
		case "4":
			线程4处理文件内容.setBackground(Color.red);
			break;
		default:
			break;
		}
		alert("扫描文件报错："+e);
	}
	
	public synchronized void 更新完成文件个数(){
		完成文件个数++;
		总文件数量.setText("总文件数量："+文件总数+"个");
		已完成文件数量.setText("已完成文件数量："+完成文件个数+"个");
		剩余文件数量.setText("剩余文件数量："+(文件总数-完成文件个数)+"个");
		任务执行进度.setValue(完成文件个数/文件总数*100);
	}
	
	public synchronized File 获取需要翻译的文件(){
		if(任务异常)return null;//任务出现异常，让所有线程全部终止
		return 该目录下所有文件列表.size()>0 ? 该目录下所有文件列表.remove(0) : null;
	}
	
	private List<File> 扫描目录下的文件(File 目录){
		List<File> 该目录下所有文件列表 = new ArrayList<File>();
		File[] 目录或文件 = 目录.listFiles();
		for (File f : 目录或文件) {
			if(f.isFile()&&f.getName().endsWith(".java")||f.getName().endsWith(".Java")||f.getName().endsWith(".JAVA")){
				该目录下所有文件列表.add(f);
			}else{
				该目录下所有文件列表.addAll(扫描目录下的文件(f));
			}
		}
		return 该目录下所有文件列表;
	}
}
