package com.lobinary.实用工具.Java源码注释翻译工具;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

import com.lobinary.实用工具.主窗口.实用工具标签标准类;
import com.lobinary.工具类.file.FileUtil;
import com.lobinary.工具类.http.HttpUtil;

public class Java源码注释翻译工具 extends 实用工具标签标准类 {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3767156681875412565L;
	private JTextField 源码文件夹路径 = new JTextField("C:/test/javasource");
	private JFileChooser fileFolderChooser = new JFileChooser("");
	
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
				String 源码路径 = 源码文件夹路径.getText();
				if(源码路径.length()==0){
					alert("温馨提示","请选择源码路径");
					选择源码路径(父窗口);
				}
				try {
					源码路径 = 源码文件夹路径.getText();
					翻译路径下的所有Java文件注释(源码路径);
				} catch (Exception e1) {
					out(e1);
					alert("扫描文件报错："+e1);
				}
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
				List<File> 该目录下所有文件列表 = 扫描目录下的文件(源码根路径);
				int Java文件个数 = 0;
				for(File f:该目录下所有文件列表){
					if(f.getName().endsWith(".java")||f.getName().endsWith(".Java")||f.getName().endsWith(".JAVA")){
						Java文件个数++;
						翻译当前Java文件的注释(f);
					}
				}
				if(Java文件个数>0){
//					alert("共"+Java文件个数+"个Java文件的注释被更改");
				}else{
					alert("该目录下没有Java文件");
				}
			}
		}else{
			alert("源码根路径不存在");
		}
	}

	private void 翻译当前Java文件的注释(File f) throws Exception {
		List<String> list = FileUtil.readLine2List(f);
		boolean 注释开始 = false;
		List<String> 注释数据 = new ArrayList<String>();
		for (int i=0;i<list.size();i++) {
			String l = list.get(i);
			if(l.contains("*/")||l.contains("@ClassName")||
					l.contains("@author")||l.contains("@date")||
					l.contains("@version")||l.contains("@param")){
				if(注释开始){
					注释开始 = false;
					String 注释数据完整字符串 = 解析注释数据(注释数据);
					String 翻译后的注释数据 = 翻译数据(注释数据完整字符串);
					List<String> 调整翻译后的注释数据 = 装配注释数据(翻译后的注释数据);
//				}else{
//					out("===================报错文件内容如下================================");
//					out(f);
//					out("===================报错文件内容如上================================");
//					throw new Exception("文件"+f.getAbsolutePath()+"发现不能处理的注释代码:"+l);
				}
			}
			if(注释开始){
				注释数据.add(l);
				out(i+":"+l);
			}
			if(l.contains("/**")||l.contains("/*"))注释开始 = true;
		}
	}
	
	/**
	 * 将普通数据装配成前边带*的注释数据
	 * @param 翻译后的注释数据
	 * @return
	 * 
	 */
	private List<String> 装配注释数据(String 翻译后的注释数据) {
		List<String> 注释数据 = new ArrayList<String>();
		for (int i = 0; i < 翻译后的注释数据.length()/20; i++) {
			String sb = 翻译后的注释数据.substring(i*20,(i+1)*20);
			注释数据.add("	 * "+sb);
		}
		return 注释数据;
	}

	/**
	 * 将字符串进行翻译
	 * @param 注释数据完整字符串
	 * @return
	 */
	private String 翻译数据(String 注释数据完整字符串) {
		String q = URLEncoder.encode(注释数据完整字符串);
		String resp = HttpUtil.sendGetRequest("http://translate.google.cn/translate_a/single?client=t&sl=auto&tl=zh-CN&hl=zh-CN&dt=at&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss&dt=t&ie=UTF-8&oe=UTF-8&source=btn&ssel=0&tsel=0&kc=1&tk=419886.9854&q="+q);
		return resp;
	}

	/**
	 * 将对应数据予以解析(整体翻译，不是单行的翻译)
	 * @param 注释数据
	 * @return
	 */
	private String 解析注释数据(List<String> 注释数据) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 注释数据.size(); i++) {
			String s = 注释数据.get(i).trim();
			if(s.startsWith("*")){
				s = s.replaceFirst("*", "");
			}
			sb.append(s);
		}
		return sb.toString();
	}

	
	private List<File> 扫描目录下的文件(File 目录){
		List<File> 该目录下所有文件列表 = new ArrayList<File>();
		File[] 目录或文件 = 目录.listFiles();
		for (File file : 目录或文件) {
			if(file.isFile()){
				该目录下所有文件列表.add(file);
			}else{
				该目录下所有文件列表.addAll(扫描目录下的文件(file));
			}
		}
		return 该目录下所有文件列表;
	}
	
}
