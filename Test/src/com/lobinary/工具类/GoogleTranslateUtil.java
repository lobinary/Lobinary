package com.lobinary.工具类;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URLEncoder;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import com.lobinary.工具类.http.HttpUtil;

// http://www.cnblogs.com/charlexu/p/3424963.html
public class GoogleTranslateUtil {

	private static boolean 秘钥加载完毕 = true;
	private static long a = 3397762296L;
	private static long b = -253483058L;
	private static int r = 413359;
	

	public static void main(String[] args) throws Exception {

		String q = "fuck you";
		System.out.println("q:"+q);

//		String tk = "419886.9854";
		String tkk = getTKK();
		String tk = 加签(q);
		System.out.println(tk);
		q = URLEncoder.encode(q);
		System.out.println(q);
		String url = "http://translate.google.cn/translate_a/single?client=t&sl=auto&tl=zh-CN&hl=zh-CN&dt=at&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss&dt=t&ie=UTF-8&oe=UTF-8&source=btn&ssel=0&tsel=0&kc=1&tk="
				+ tk + "&q=" + q;
		System.out.println(url);
//		String resp = HttpUtil.doGet(url);
		String resp = HttpUtil.sendGet(url,"UTF-8");
		System.out.println(resp);
//		System.out.println(tkk);
//		System.out.println(tk);
//		System.out.println("694560.841561");
		
		System.out.println(423749350>>>6);
		int x = 'a';
		System.out.println(413414<<10);
	}
	
	
	public static String 加签(String query) throws Exception {
		getTKK();
		// 得到一个ScriptEngine对象
		ScriptEngineManager maneger = new ScriptEngineManager();
		ScriptEngine engine = maneger.getEngineByName("JavaScript");
		// 读js文件
		String jsFile = "D:/Tool/Git/Lobinary/Test/src/com/lobinary/实用工具/Java源码注释翻译工具/google-key-generator2.js";
		FileInputStream fileInputStream = new FileInputStream(new File(jsFile));
		Reader scriptReader = new InputStreamReader(fileInputStream, "utf-8");
//console.info(tk('fuck you'));
		String result=null;
		try {
			engine.eval(scriptReader);
			if (engine instanceof Invocable) {
				// 调用JS方法
				Invocable invocable = (Invocable) engine;
				result = (String) invocable.invokeFunction("getKey", a,b,r,"fuck you");
				System.out.println(result);
				System.out.println(result.length());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			scriptReader.close();
		}
		return result;
	}
	
	
	/**
	 * TKK=eval('((function(){var a\x3d2232569751;var b\x3d-1646195174;return 413305
	 * 413305.586374577
	 * tk:	694560.841561
	 * 		694560.841561
	 * @return
	 * @throws Exception
	 */
	private static String getTKK() throws Exception{
		
		if(秘钥加载完毕) return ""+r + "." + (a + b);  //如果秘钥加载完成，就不需要二次加载秘钥
		
		String resp = HttpUtil.sendGetRequest("http://translate.google.cn");
		resp = resp.substring(resp.indexOf("TKK=eval"));
		resp = resp.substring(0,resp.indexOf("+\\x27"));
		System.out.println(resp);
		//TKK=eval('((function(){var a\x3d3119618383;var b\x3d-570961749;
		//TKK=eval('((function(){var a\x3d1343057443;var b\x3d1205599191;
		String[] 变量 = resp.split("var");
		for (String s : 变量) {
			System.out.println(s);
			if(s.contains(";")){
				if(s.contains("a")){
					String as = s.trim().replace("a\\x3d", "").replace(";", "");
					System.out.println("as:"+as);
					a = Long.parseLong(as);
				}else if(s.contains("b")){
					String[] ss = s.split(";return ");
					for (String sss : ss) {
						if(sss.contains("b")){
							b = Long.parseLong(sss.trim().replace("b\\x3d", "").replace(";", ""));
						}else{
							r = Integer.parseInt(sss.trim());
						}
					}
					
				}
			}
		}
		System.out.println("a:"+a);
		System.out.println("b:"+b);
		System.out.println("r:"+r);
		if(a!=0&&b!=0&&r!=0){
			System.out.println("获取TKK成功，秘钥信息如下：");
			秘钥加载完毕 = true;
			return ""+r + "." + (a + b);  
		}else{
			throw new Exception("获取TKK失败");
		}
	}

}
