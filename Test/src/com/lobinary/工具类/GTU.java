package com.lobinary.工具类;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lobinary.工具类.http.HttpUtil;

// http://www.cnblogs.com/charlexu/p/3424963.html
public class GTU {

	private final static Logger log = LoggerFactory.getLogger(GTU.class);
	
	private static boolean 秘钥加载完毕 = false;
	private static long a = 3397762296L;
	private static long b = -253483058L;
	private static int r = 413359;
	private static int 每次翻译的长度 = 500;
	private static Invocable invocable;
	static {
		try {
			// 得到一个ScriptEngine对象
					ScriptEngineManager maneger = new ScriptEngineManager();
					ScriptEngine engine = maneger.getEngineByName("JavaScript");
					// 读js文件
					String jsFile = "D:/Tool/Git/Lobinary/Test/src/com/lobinary/实用工具/Java源码注释翻译工具/GTU.js";
					FileInputStream fileInputStream = new FileInputStream(new File(jsFile));
					Reader scriptReader = new InputStreamReader(fileInputStream, "utf-8");
					// console.info(tk('fuck you'));
					try {
						engine.eval(scriptReader);
						if (engine instanceof Invocable) {
							// 调用JS方法
							invocable = (Invocable) engine;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					log.info("翻译工具加密JS加载完毕");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public static String t(String q) throws Exception {
		if(q.length()==0) return "";
		//XXX 分割翻译丢数据
		//			   ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms
		if(q.contains("ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms."))return "";
		StringBuilder 最终翻译数据 = new StringBuilder();
		String 本次翻译数据 = null;
		int 上一个点的位置 = 0;//用于记录上一个点的位置
		int 上次截取的最后位置 = 0;//用于记录上次截取后的最后位置
		log.info("q:"+q);
		for (int i = 0; i < q.length(); i++) {
			String s = q.substring(i,i+1);
			if(s.equals(".")){
				i++;
				if(i - 上次截取的最后位置  > 每次翻译的长度){//当前点到上一个点的长度是不是超长了
					if(上一个点的位置==上次截取的最后位置){//如果超长了，并且上次截取的位置等于上一个点
						//这个时候只能强制截取，因为单句已经超长，并且更新上一个点的位置
						本次翻译数据 = q.substring(上次截取的最后位置,上次截取的最后位置+每次翻译的长度);
						上次截取的最后位置 = 上次截取的最后位置+每次翻译的长度;
						上一个点的位置 = 上次截取的最后位置;
					}else{//如果超长了，但是上一个点和最后截取的点不相等，那么就证明我们这个点已经是第二句或者更多句的话，
						//那么我们就截取上到一句话，上一个点的位置不需要动，只需要更改最后截取位置就行
						本次翻译数据 = q.substring(上次截取的最后位置,上一个点的位置);
						上次截取的最后位置 = 上一个点的位置;
					}
					最终翻译数据.append(translate(本次翻译数据));
					i = 上次截取的最后位置;
				}
				上一个点的位置 = i;
			}else{
				if(每次翻译的长度<(i-上次截取的最后位置)){
					本次翻译数据 = q.substring(上次截取的最后位置,上一个点的位置);
					最终翻译数据.append(translate(本次翻译数据));
					上次截取的最后位置 = 上一个点的位置;
				}
			}
			
			if(i == q.length()-1){
				if(上次截取的最后位置!=i){
					本次翻译数据 = q.substring(上次截取的最后位置,i);
					最终翻译数据.append(translate(本次翻译数据));
					上次截取的最后位置 = i;
				}
			}
		}
		if(上次截取的最后位置==0){
			最终翻译数据.append(translate(q));
		}
		return 最终翻译数据.toString();
	}
	
	public static String translate(String q) throws Exception {
		String tk = 加签(q);
		q = URLEncoder.encode(q, "UTF-8");
//		//log.info(q);
		String url = "http://translate.google.cn/translate_a/single?client=t&sl=auto&tl=zh-CN&hl=zh-CN&dt=at&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss&dt=t&ie=UTF-8&oe=UTF-8&source=btn&ssel=0&tsel=0&kc=1&tk="
				+ tk + "&q=" + q;
		String resp;
		try {
//			resp = HttpUtil.doGet(url);
//			resp = HttpUtil.sendGetRequest(url);
			System.out.println(url);
			resp = HttpUtil.sendGet(url,"UTF-8");
		} catch (Exception e) {
			throw new Exception("连接谷歌翻译服务器失败,请求网址为:"+url);
		}
		System.out.println("谷歌服务器返回的数据为:"+resp);
		return 解析返回数据(resp);
	}

	private static String 解析返回数据(String j) throws Exception {
		boolean end = false;
		StringBuilder sb = new StringBuilder();
		int index = 2;
		log.info(j);
//		System.out.print("  ");
		int 左括号数量 = 1;
		List<String> 分段数据 = new ArrayList<String>();
		while(!end){
			String jc = j.substring(index, index+1);
			if(jc.equals("["))左括号数量++;
			if(jc.equals("]"))左括号数量--;
			if(左括号数量==0)break;
			
			if(jc.equals("\\")){
				index++;
				jc = j.substring(index, index+1);
				switch (jc) {
				case "u":
					index++;
					jc = j.substring(index, index+4);
					int data = Integer.parseInt(jc, 16);
			         sb.append((char) data);
			         index += 3;
					break;
				case "n":
					sb.append("\\n");
					break;
				case "\"":
					sb.append("\"");
					break;
				default:
					sb.append(jc);
					break;
				}
			}else{
				sb.append(jc);
			}
			if(左括号数量==1&&!sb.toString().equals(",")){
				分段数据.add(sb.toString());
				sb = new StringBuilder();
			}else if(sb.toString().equals(",")){
				sb = new StringBuilder();
			}
			index++;
			if(index>=j.length())break;
		}
//		log.info(sb.toString());
//		log.info("==========================================================================================================");
//		for (String sss : 分段数据) {
//			log.info(sss);
//		}
//		log.info("==========================================================================================================");
//		log.info(分段数据.size());
		StringBuffer 整合后翻译数据 = new StringBuffer();
		for (int i = 0; i < 分段数据.size(); i++) {
			String 单条翻译数据 = 分段数据.get(i);
//			log.info(i+":"+单条翻译数据);
			if(单条翻译数据.startsWith("[\"")){
				if(!单条翻译数据.contains("\",\""))throw new Exception("发现未知格式翻译数据:"+单条翻译数据);
				String 翻译数据 = 单条翻译数据.substring(2,单条翻译数据.indexOf("\",\""));
//				log.info(翻译数据);
				整合后翻译数据.append(翻译数据);
			}else{
				if(i==分段数据.size()-1){
					break;
				}else{
					throw new Exception("发现未知格式翻译数据:"+单条翻译数据);
				}
			}
		}
//		//log.info("========================================================================================");
//		//log.info(整合后翻译数据.toString());
		return 整合后翻译数据.toString()
				.replaceAll("“", "\"")
				.replaceAll("”", "\"")
				.replaceAll("</ ", "</")
				.replaceAll("＃", "#")
				.replaceAll("，", ",")
				.replaceAll("（", "(")
				.replaceAll("）", ")")
				.replaceAll("＆", "&")
				.replaceAll("？", "?");
	}

	/**
	 * <p>
	 * {@code String}表示UTF-16格式的字符串，其中<em>补充字符</ em>由<em> surrogate\n   对</
	 * em>（请参阅<a href="Character.html#unicode"> Unicode\n   {@code Character}
	 * 类中的字符表示</a>\n   更多信息）。 索引值是指{@code char}代码单位，因此补充字符在{@code String}
	 * 中使用两个位置。
	 * <p>
	 * {@code String}类提供了处理方法\n   Unicode代码点（即，字符），以及用于处理Unicode代码单元（即，
	 * {@code char}）值的代码。
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		String q = "i <want> to eat something";
		//log.info("q:" + q);

		String tk = 加签(q);
		//log.info(tk);
		q = URLEncoder.encode(q, "UTF-8");
		//log.info(q);
		String url = "http://translate.google.cn/translate_a/single?client=t&sl=auto&tl=zh-CN&hl=zh-CN&dt=at&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss&dt=t&ie=UTF-8&oe=UTF-8&source=btn&ssel=0&tsel=0&kc=1&tk="
				+ tk + "&q=" + q;
		//log.info(url);
		String resp = HttpUtil.sendGet(url, "UTF-8");
		System.out.println(resp);
		/*
		 * 
		 * [[["我想吃东西","i want to eat something",,,1],[,,"Wǒ xiǎng chī dōngxi"
		 * ]],,"en",,,[["i want to eat something"
		 * ,,[["我想吃东西",1000,true,false],["我想吃点东西",0,true,false]],[[0,23]],
		 * "i want to eat something"
		 * ,0,0]],0.29146308,,[["en"],,[0.29146308],["en"]],,,,,,[["want","to",
		 * "eat","something","want to","to eat","want to eat"]]]
		 */
		//log.info(resp);
	}

	public static String 加签(String query) throws Exception {
		getTKK();
		System.out.println("准备对数据进行加签"+a+","+b+","+ r+","+ query);
		Object result = invocable.invokeFunction("getKey", a, b, r, query);
		return result.toString();
	}

	/**
	 * TKK=eval('((function(){var a\x3d2232569751;var b\x3d-1646195174;return
	 * 413305 413305.586374577 tk: 694560.841561 694560.841561
	 * 
	 * @return
	 * @throws Exception
	 */
	private static String getTKK() throws Exception {

		String 秘钥 = "" + r + "." + (a + b);
		if (秘钥加载完毕)
			return 秘钥; // 如果秘钥加载完成，就不需要二次加载秘钥

		String resp = HttpUtil.sendGetRequest("http://translate.google.cn");
		System.out.println("从Google获取TKK返回数据为:"+resp);
		resp = resp.substring(resp.indexOf("TKK=eval"));
		resp = resp.substring(0, resp.indexOf("+\\x27"));
		//log.info(resp);
		// TKK=eval('((function(){var a\x3d3119618383;var b\x3d-570961749;
		// TKK=eval('((function(){var a\x3d1343057443;var b\x3d1205599191;
		String[] 变量 = resp.split("var");
		for (String s : 变量) {
			//log.info(s);
			if (s.contains(";")) {
				if (s.contains("a")) {
					String as = s.trim().replace("a\\x3d", "").replace(";", "");
					//log.info("as:" + as);
					a = Long.parseLong(as);
				} else if (s.contains("b")) {
					String[] ss = s.split(";return ");
					for (String sss : ss) {
						if (sss.contains("b")) {
							b = Long.parseLong(sss.trim().replace("b\\x3d", "").replace(";", ""));
						} else {
							r = Integer.parseInt(sss.trim());
						}
					}

				}
			}
		}
		//log.info("a:" + a);
		//log.info("b:" + b);
		//log.info("r:" + r);
		if (a != 0 && b != 0 && r != 0) {
			log.info("获取TKK成功，秘钥信息为："+秘钥);
			秘钥加载完毕 = true;
			return 秘钥;
		} else {
			throw new Exception("获取TKK失败");
		}
	}

}
