package com.lobinary.实用工具.Java源码注释翻译工具;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

import com.lobinary.实用工具.主窗口.实用工具标签标准类;
import com.lobinary.工具类.GTU;
import com.lobinary.工具类.JAU;
import com.lobinary.工具类.file.FileUtil;
import com.lobinary.工具类.http.HttpUtil;

public class Java源码注释翻译工具 extends 实用工具标签标准类 {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3767156681875412565L;
	private JTextField 源码文件夹路径 = new JTextField("C:/test");
	private JFileChooser fileFolderChooser = new JFileChooser("");
	private boolean 正在执行 = false;
	
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
//						翻译当前Java文件的注释(f);
						JAU.翻译(f);
					}
				}
				if(Java文件个数>0){
					alert("翻译完成，共"+Java文件个数+"个Java文件的注释被更改");
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
		String 翻译完成标志 = "/***** Lobxxx Translate Finished ******/";
//		if(list.get(0).equals(翻译完成标志)){
//			out("发现已经翻译完成的java文件:"+f.getAbsolutePath());
//			return ;//如果是已经翻译的文件，将会被跳过
//		}
		boolean 注释开始 = false;
		List<String> 注释数据 = new ArrayList<String>();
		for (int i=0;i<list.size();i++) {
			String l = list.get(i);
			if(l.contains("*/")||l.contains("@ClassName")||
					l.contains("@author")||l.contains("@date")||
					l.contains("@version")||l.contains("@param")||
					l.contains("@see")||l.contains("@since")){
				if(注释开始){
					注释开始 = false;
					String 注释数据完整字符串 = 解析注释数据(注释数据);
					System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
					System.out.println(注释数据完整字符串);
					注释数据.clear();
					String 翻译后的注释数据 = 翻译数据(注释数据完整字符串);
					System.out.println(翻译后的注释数据);
					System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
					String 前缀数据Str;
					String 前缀数据 ;
					int 向上计数器 = 1;
					while(true){
						try {
							前缀数据Str = list.get(i-向上计数器);
							前缀数据 = 前缀数据Str.substring(0,前缀数据Str.indexOf("*"))+"* ";
							break;
						} catch (Exception e) {
							向上计数器++;
						}
					}
					List<String> 调整翻译后的注释数据 = 装配注释数据(前缀数据,翻译后的注释数据);
					list.add(i,前缀数据);
					list.addAll(i, 调整翻译后的注释数据);
					list.add(i,前缀数据+"<p>");
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
			if(l.contains("/**")||l.contains("/*")){
				if(!l.endsWith("*/"))注释开始 = true;
			}
		}
		list.add(0,翻译完成标志);
		FileUtil.insertList2File(list, f);
	}
	
	/**
	 * 将普通数据装配成前边带*的注释数据
	 * @param 翻译后的注释数据
	 * @return
	 * @throws Exception 
	 * 
	 */
	private List<String> 装配注释数据(String 前缀,String 翻译后的注释数据) throws Exception {
		List<String> 注释数据 = new ArrayList<String>();
		int 每行的长度 = 80;
		int 上次截取位置 = 0;
		boolean 标签记录标志 = false;
		String 已经记录的数据 = "";
		int 当前缩进长度 = 0;
		for (int i = 1; i <= 翻译后的注释数据.length(); i++) {
			
			String 本次字符 = 翻译后的注释数据.substring(i-1, i);
			
			if("<".equals(本次字符)||"{".equals(本次字符)){//存储特殊数据
				标签记录标志 = true;
			}
			
			
			if(标签记录标志){
				
				已经记录的数据 += 本次字符;
				
				switch (已经记录的数据) {

				case "<b":
				case "<a":
					break;
					
				case "<p":
				case "<table":
				case "</table":
				case "<tr":
				case "</tr":
				case "<td":
				case "</td":
				case "</ td":
				case "<th":
				case "</th":
				case "<pre":
				case "</pre":
				
				/**
				 * 
				 * 对于该类标志 <p class="cls" > test </p>
				 * 转换成 <p class="cls" >
				 * 		   very important
				 * 		   <b>
				 * 		     test
				 * 		   </b>
				 *         case
				 * 		 </p>
				 * 规则如下：
				 * 	查看标签是否需要换行：
				 * 		否：则继续输入，但是需要注意不应该将标签分割成两行，宁愿长度较长
				 * 		是：终止当前记录的数据，查看是缩进标签还是不缩进标签，并根据换行标签缩进规则，对标签进行换行
				 */
					if("<p".equals(已经记录的数据)){
						String 下一位字符 = 翻译后的注释数据.length()>=i?翻译后的注释数据.substring(i-1, i):null;
						if(下一位字符!=null&&"r".equals(下一位字符)){//如果是pre，则继续往下执行
							break;
						}
					}
					
					
					
//					System.out.println("发现特殊数据:"+已经记录的数据);
					String s = null;
//					System.out.println("截取数据："+上次截取位置+","+(i-已经记录的数据.length()));//481 482 <tr>
					try {
						s = 翻译后的注释数据.substring(上次截取位置, i-已经记录的数据.length());
					} catch (Exception e) {
						System.out.println("发现截取异常数据："+翻译后的注释数据);
						throw e;
					}
					注释数据.add(前缀+s);
					System.out.println(前缀+s);
					s = 翻译后的注释数据.substring(i-已经记录的数据.length(), i);
					注释数据.add(前缀+s);
					System.out.println(前缀+s);
					上次截取位置 = i;
					标签记录标志 = false;
					已经记录的数据 = "";
					break;

				default:
					
					if(已经记录的数据.length()>50){
						throw new Exception("发现未识别标签：["+已经记录的数据+"]");
//						标签记录标志 = false;
//						已经记录的数据 = "";
					}
					
					/**
					 * 下方的注释表明，java中的注释都是闭环的，也就是<a 后边都会跟随</a>,所以以此作为准则
					 * 如果我们设定<a为不换行标签的话，那么就一定要等到</a>后在换行，否则就抛出异常
				     * <a href="{@docRoot}/../platform/serialization/spec/output.html">
				     * Object Serialization Specification, Section 6.2, "Stream Elements"</a>
					 */
					if(">".equals(本次字符)||"}".equals(本次字符)){
						if(i-上次截取位置>每行的长度){
							s = 翻译后的注释数据.substring(上次截取位置, i);
							上次截取位置 = i;
							注释数据.add(前缀+s);
						}else{
							标签记录标志 = false;
						}
					}
					break;
				}
			}else{
				if(i%每行的长度==0){
					try {
						while( i < 翻译后的注释数据.length()&&!本次字符.equals(" ")){
							i++;
						}
					} catch (Exception e) {
						System.out.println("错误数据："+翻译后的注释数据);
						System.out.println("错误数据："+i);
						e.printStackTrace();
						throw e;
					}
					String s = 翻译后的注释数据.substring(上次截取位置, i);
					上次截取位置 = i;
					注释数据.add(前缀+s);
				}
			}


			if(">".equals(本次字符)){//存储特殊数据
				标签记录标志 = false;
			}
			
		}
		return 注释数据;
	}

	/**
	 * 将字符串进行翻译
	 * @param 注释数据完整字符串
	 * @return
	 * @throws Exception 
	 */
	private String 翻译数据(String 注释数据完整字符串) throws Exception {
		String 翻译后数据 = GTU.t(注释数据完整字符串);
		return 翻译后数据;
	}

	/**
	 * 将对应数据予以解析(整体翻译，不是单行的翻译)
	 * @param 注释数据
	 * @return
	 */
	private String 解析注释数据(List<String> 注释数据) {
		StringBuilder sb = new StringBuilder();
		System.out.println("##########################解析后的数据每个字符串VVVV################################");
		for (int i = 0; i < 注释数据.size(); i++) {
			String s = 注释数据.get(i).trim();
			if(s.startsWith("*")){
				s = s.substring(1).trim();
			}
			if(s.length()>0){
				sb.append(" ").append(s);
				System.out.println(s);
			}else{
				sb.append(" \\n ").append(s);
				System.out.println(" \n ");
			}
		}
		
		System.out.println("##########################解析后的数据完整字符串VVV################################");
		System.out.println("解析后的数据完整字符串:"+sb.toString());
		System.out.println("##########################解析后的数据完整字符串AAA################################");
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
