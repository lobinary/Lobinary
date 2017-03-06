package com.lobinary.工具类;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import com.lobinary.实用工具.Java源码注释翻译工具.注释数据对象;
import com.lobinary.工具类.file.FileUtil;

public class JAU {

//	private final static Logger log = LoggerFactory.getLogger(JAU.class);
	
	private static List<String> 注释终止符号 = new ArrayList<String>();
	private final static int 每行翻译后的注释的推荐长度 = 100;
	private final static String 翻译完成标志 = "/***** Lobxxx Translate Finished ******/";
	
	static{
		注释终止符号.add("*/");
		注释终止符号.add("@ClassName");
		注释终止符号.add("@author");
		注释终止符号.add("@date");
		注释终止符号.add("@version");
		注释终止符号.add("@param");
		注释终止符号.add("@see");
		注释终止符号.add("@since");
		注释终止符号.add("@return");
		注释终止符号.add("@throws");
		注释终止符号.add("@serial");
		注释终止符号.add("@exception");
		 
		
	}

	public static void main(String[] args) throws Exception {
		File f = new File("C:/test/CheckboxMenuItem.java");
		翻译(f);
	}
	
	public static void 翻译(File f) throws Exception{
		List<String> list = FileUtil.readLine2List(f);
		if(list.get(0).equals(翻译完成标志)){
			System.out.println("发现翻译完成Java文件:"+f.getAbsolutePath());
			return ;
		}
		long startTime=System.currentTimeMillis();
		List<String> 翻译后的数据 = 抓取注释数据(list);
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%最后的数据%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		long endTime = System.currentTimeMillis();
		for (String l : 翻译后的数据) {
			System.out.println(l);
		}
		FileUtil.insertList2File(翻译后的数据, f);
		System.out.println("翻译耗时："+(endTime-startTime));
		System.out.println("写入文件耗时："+(System.currentTimeMillis()-endTime));
		System.out.println("翻译总耗时:"+GTU.总耗时时间);
		System.out.println("加签总耗时:"+GTU.加签总耗时);
		System.out.println("请求总耗时:"+GTU.请求总耗时);
	}

	private static List<String> 抓取注释数据(List<String> java文件数据List) throws Exception {
		boolean 注释开始 = false;
		List<String> 注释数据 = new ArrayList<String>();
		for (int i=0;i<java文件数据List.size();i++) {
			String 每行数据 = java文件数据List.get(i);
			if(包含注释终止符号(每行数据)){
				if(注释开始){
					System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@华丽的分割线@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
					System.out.println("#########  V V V V V V V V V V V V 原始单个注释数据 V V V V V V V V V V V V V V  ###############");
					for (String 注释数据的每行 : 注释数据) {
						System.out.println(注释数据的每行);
					}
					System.out.println("#########  A A A A A A A A A A A A 原始单个注释数据 A A A A A A A A A A A A A A  ###############");
					System.out.println("#########  V V V V V V V V V V V V 解析注释数据 V V V V V V V V V V V V V V  ###############");
					List<String> 翻译后注释数据 = 解析并翻译注释(注释数据);
					System.out.println("#########  A A A A A A A A A A A A 解析注释数据 A A A A A A A A A A A A A A  ###############");
					System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@华丽的分割线@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
					注释开始 = false;
					注释数据.clear();
					String 前缀数据Str;
					String 前缀数据 ;
					int 向上计数器 = 1;
					while(true){
						try {
							前缀数据Str = java文件数据List.get(i-向上计数器);
							前缀数据 = 前缀数据Str.substring(0,前缀数据Str.indexOf("*"))+"* ";
							break;
						} catch (Exception e) {
							向上计数器++;
						}
					}
					
					String 下一行数据 = 解析本行数据(java文件数据List.get(i));
					if(下一行数据.length()!=0)java文件数据List.add(i,前缀数据);
					List<String> 增加前缀后的翻译数据List  = new ArrayList<String>();
					for (String 每行的原始翻译数据 : 翻译后注释数据) {//增加前缀
						增加前缀后的翻译数据List.add(前缀数据+每行的原始翻译数据);
					}
					java文件数据List.addAll(i, 增加前缀后的翻译数据List);
					java文件数据List.add(i,前缀数据+"<p>");
				}
			}
			if(注释开始){
				注释数据.add(每行数据);
			}
			if(每行数据.contains("/**")||每行数据.contains("/*")){
				if(!每行数据.endsWith("*/"))注释开始 = true;//XXX 此处没有翻译单行注释数据，如： /** **/ , /* */ ,// 这三类的数据
			}
		}
		java文件数据List.add(0,翻译完成标志);
		return java文件数据List;
	}
	
	/**
	 * 根据谷歌翻译
	 *  An abstract class for service providers that provide localized string representations (display names) of {@code Calendar} field values.
	 *  的结果为：
	 *  用于提供{@code Calendar}字段值的本地化字符串表示（显示名称）的服务提供程序的抽象类。
	 *  
	 *  因此我们分析得出，{@code Calendar}的顺序被调整，由此我们认为google认为他是一个对象，所以我们的翻译数据中忽略{@code Calendar}这类的特殊标志
	 *  也就是说把他们当成普通数据，不当做标签处理，也就是说我们只处理<p>这类的标签
	 *  所以我们要把标签以外的数据进行处理
	 * @param 注释数据
	 * @return
	 * @throws Exception 
	 */
	private static List<String> 解析并翻译注释(List<String> 注释数据) throws Exception {
		List<注释数据对象> 组注释 = new ArrayList<注释数据对象>();
		for (int i = 0; i < 注释数据.size(); i++) {
			String 每行数据 = 注释数据.get(i);
			每行数据 = 解析本行数据(每行数据);
			if(每行数据.trim().length()==0){
				if(组注释.size()>0&&组注释.get(组注释.size()-1).get本行属性()!=注释数据对象.空行){
					组注释.add(new 注释数据对象(注释数据对象.空行," "));
				}
			}else if(本行仅一个标签(每行数据)){
				组注释.add(new 注释数据对象(注释数据对象.标签,每行数据));
			}else{
				String 临时数据 = "";
				while(true){
					if(注释数据.size()>i){
						每行数据 = 解析本行数据(注释数据.get(i));
						if(每行数据.trim().length()==0||本行仅一个标签(每行数据)){
							//如果下一行是空行或者是纯标签，代表这行以上的数据是一个数据块，所以需要分割
//							System.out.println("发现下一行是空行数据，准备将临时数据写入到组注释中");
							组注释.add(new 注释数据对象(注释数据对象.文本,临时数据));
							if(本行仅一个标签(每行数据)){
								组注释.add(new 注释数据对象(注释数据对象.标签,每行数据));
							}else{
								if(组注释.size()>0&&组注释.get(组注释.size()-1).get本行属性()!=注释数据对象.空行){
									组注释.add(new 注释数据对象(注释数据对象.空行,""));
								}
							}
							System.out.println(临时数据);
							System.out.println(每行数据);
							break;
						}else{
							临时数据 = 临时数据 + " " + 每行数据;
							i++;
						}
					}else{
						if(临时数据.trim().length()>0){
							组注释.add(new 注释数据对象(注释数据对象.文本,临时数据));
							组注释.add(new 注释数据对象(注释数据对象.空行," "));
						}
						i++;
						break;
					}
				}
				
			}
			
		}
		List<String> 结果数据 = new ArrayList<String>();
		for (注释数据对象 每行临时数据 : 组注释) {
			System.out.println("准备对注释进行翻译，原始注释数据为："+每行临时数据.get本行内容());
			if(每行临时数据.get本行属性()==注释数据对象.空行){
				if(结果数据.size()>0&&结果数据.get(结果数据.size()-1).length()!=0){
					结果数据.add(""); 
				}
			}else if(每行临时数据.get本行属性()==注释数据对象.标签){
				结果数据.add(每行临时数据.get本行内容());
			}else{
				String 翻译后的数据 = 翻译数据(每行临时数据.get本行内容());
				if(每行翻译后的注释的推荐长度>翻译后的数据.length()){
					System.out.println("翻译后的数据:"+翻译后的数据);
					结果数据.add(翻译后的数据);
				}else{
					结果数据.addAll(超长数据格式化(翻译后的数据));
				}
			}
		}
		System.out.println("**************************************************");
		for (String texst : 结果数据) {
			System.out.println(texst);
		}
		System.out.println("**************************************************");
		return 结果数据;
	}

	/**
	 * 我们可以这样判断，根据句号来分割
	 * 如果一句话的长度大于每行推荐长度，我们就强制分割这句话，执行换行
	 * 如果一句话的长度小于每行推荐长度，那么我们就不强制分割
	 * @param 翻译后的数据
	 * @return
	 */
	private static Collection<? extends String> 超长数据格式化(String 翻译后的数据) {
		List<String> list = new ArrayList<String>();
		//翻译后的中文一句的结束符号是   。
		//因此我们可以判断哪句是不是已经结束了
		String[] 根据句号分割的语句 = 翻译后的数据.split("。");
		StringBuilder 每行数据缓存池 = new StringBuilder();
		for (String s : 根据句号分割的语句) {//XXX 碰到{}不换行
			s = s+"。";//因为句号被split自动去掉了，我们需要再把他加上
			if(s.length()>每行翻译后的注释的推荐长度){
				if(每行数据缓存池.length()>0)list.add(每行数据缓存池.toString());//现将缓冲池的数据写入，再写入本行数据
				StringBuilder 分割缓冲池 = new StringBuilder();
				boolean 尖括号标志 = false;//如果有{}就不会换行
				boolean 尖括号II标志 = false;//如果有<>就不会换行
				for (int i = 0; i < s.length(); i++) {
					String 临时字符 = s.substring(i,i+1);
					if(临时字符.equals("{"))尖括号标志 = true;
					if(临时字符.equals("}"))尖括号标志 = false;
					if(临时字符.equals("<"))尖括号II标志 = true;
					if(临时字符.equals(">"))尖括号II标志 = false;
					分割缓冲池.append(临时字符);
					if(!(尖括号标志||尖括号II标志)){
						if(分割缓冲池.length()>每行翻译后的注释的推荐长度){
							list.add(分割缓冲池.toString());
							分割缓冲池.delete(0, 分割缓冲池.length());
						}
					}
				}
				if(分割缓冲池.length()>0){
					list.add(分割缓冲池.toString());
				}
			}else{
				if(每行数据缓存池.length()+s.length()>每行翻译后的注释的推荐长度){
					//如果本句话加上缓冲池的话长度大于规定长度，那么就把缓冲池的数据写入
					//因为本句话肯定不会超过每行推荐长度，因为如果超过会被上方条件拦截，因此可以放心的插入缓冲池
					list.add(每行数据缓存池.toString());
					每行数据缓存池.delete(0, 每行数据缓存池.length());
					每行数据缓存池.append(s);
				}else if(每行数据缓存池.length()+s.length()==每行翻译后的注释的推荐长度){//长度正好，就将这句话写入缓冲池，然后插入到list
					每行数据缓存池.append(s);
					list.add(每行数据缓存池.toString());
					每行数据缓存池.delete(0, 每行数据缓存池.length());
				}else{//也就是长度小于每行长度，那就让缓冲池继续缓冲数据
					每行数据缓存池.append(s);
				}
			}
		}
		if(每行数据缓存池.length()>0){//证明缓冲池还有数据，需要写入到list中
			list.add(每行数据缓存池.toString());
		}
		return list;
	}

	private static String 翻译数据(String 每组数据) throws Exception {
//		return 每组数据;
		return GTU.t(每组数据);
	}

	private static List<String> 翻译数据(List<String> 每组数据) throws Exception {
//		return 每组数据;
		return GTU.t(每组数据);
	}

	/**
	 * 查看本行是不是有一个标签
	 * @param 每行数据
	 * @return
	 */
	private static boolean 本行仅一个标签(String 每行数据) {
		return 每行数据.indexOf("<", 1)==-1&&每行数据.endsWith(">");
	}

	/**
	 * 去掉前方的*
	 * @param 每行数据
	 * @return
	 */
	private static String 解析本行数据(String 每行数据) {
//		System.out.println("解析前数据："+每行数据);
		每行数据 = 每行数据.trim();
		if(每行数据.equals("*")){
			每行数据 = "";
//			System.out.println("解析后数据："+每行数据);
			return 每行数据;
		}
		if(每行数据.length()>1&&每行数据.substring(0,1).equals("*")){
			每行数据 = 每行数据.substring(2,每行数据.length()).trim();
		}else{
			每行数据 = 每行数据.substring(0,每行数据.length()).trim();
		}
//		System.out.println("解析后数据："+每行数据);
		return 每行数据;
	}

	/**
	 * the first year in each era. It should be returned when a long style ({@link Calendar#LONG_FORMAT} or {@link Calendar#LONG_STANDALONE}) is specified.
	 *  See also the <a href="../../text/SimpleDateFormat.html#year"> Year representation in {@code SimpleDateFormat}</a>.
	 * 
	 * 每个时代的第一年。 如果指定了长字体（{@link Calendar＃LONG_FORMAT}或{@link Calendar＃LONG_STANDALONE}），
	 * 则应返回。 另请参阅{@code SimpleDateFormat}中的  <a href="../../text/SimpleDateFormat.html#year">年份表示</a>。
	 * 
	 * 
	 * @param s
	 * @return
	 */
	private static boolean 包含注释终止符号(String s){
		for(String 终止符号:注释终止符号){
			if(s.contains(终止符号))return true;
		}
		return false;
	}
	
}
