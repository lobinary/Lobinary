package com.lobinary.test;

import java.net.URLEncoder;

import com.lobinary.工具类.http.HttpUtil;

public class TranslateUtil {

	private static boolean 秘钥加载完毕 = true;
	private static long a = 2090155966L;
	private static long b = -2085086943L;
	private static long r = 413312L;
	
	public static void main(String[] args) throws Exception {

		String q = "file";
		System.out.println("q:"+q);

//		String tk = "419886.9854";
		String tkk = getTKK();
		String tk = getTK(q);
		System.out.println(tk);
		String url = "http://translate.google.cn/translate_a/single?client=t&sl=auto&tl=zh-CN&hl=zh-CN&dt=at&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss&dt=t&ie=UTF-8&oe=UTF-8&source=btn&ssel=0&tsel=0&kc=1&tk="
				+ tk + "&q=" + q;
//		String resp = HttpUtil.sendGetRequest(url);
//		System.out.println(resp);
//		System.out.println(tkk);
//		System.out.println(tk);
//		System.out.println("694560.841561");
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
							r = Long.parseLong(sss.trim());
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

	/**
	 * 计算TK值
	 * r + "." + (a + b);  
	 * 413306.2548656634
	 * @return
	 * @throws Exception
	 */
	private static String getTK(String q) throws Exception{
		/*
		for (var e = getTKK().split("."), h = Number(e[0]) || 0, g = [], d = 0, f = 0; f < a.length; f++) {  
	        var c = a.charCodeAt(f);  
	        128 > c ? 
	        	g[d++] = c
	        	 : 
	        	 (
		        	 2048 > c ? 
		        	 	g[d++] = c >> 6 | 192
		        	 	 : 
		        	 	 (
		        	 	 	55296 == (c & 64512) && f + 1 < a.length && 56320 == (a.charCodeAt(f + 1) & 64512) ?
		        	 	 	 (
		        	 	 	 	c = 65536 + ((c & 1023) << 10) + (a.charCodeAt(++f) & 1023), g[d++] = c >> 18 | 240, g[d++] = c >> 12 & 63 | 128
		        	 	 	 ) 
		        	 	 	 :
		        	 	 	 g[d++] = c >> 12 | 224, g[d++] = c >> 6 & 63 | 128
		        	 	 ), g[d++] = c & 63 | 128
		         )  
	    }  
	    a = h;  
	    for (d = 0; d < g.length; d++) a += g[d], a = b(a, "+-a^+6");  
	    a = b(a, "+-3^+b+-f");  
	    a ^= Number(e[1]) || 0;  
	    0 > a && (a = (a & 2147483647) + 2147483648);  
	    a %= 1E6;  
	    return a.toString() + "." + (a ^ h);
	    */
		long h = r;
		long[] g = new long[1000];
		int d = 0;
		int f = 0;
		for(;f<q.length();f++){
			int c = q.codePointAt(f);
			if(128>c){
				g[d++] = c;
			}else{
				if(2048 > c){
					g[d++] = c >> 6 | 192;
				}else{
					/*
					 * (
		        	 	 	55296 == (c & 64512) && f + 1 < a.length && 56320 == (a.charCodeAt(f + 1) & 64512) ?
		        	 	 	 (
		        	 	 	 	c = 65536 + ((c & 1023) << 10) + (a.charCodeAt(++f) & 1023), g[d++] = c >> 18 | 240, g[d++] = c >> 12 & 63 | 128
		        	 	 	 ) 
		        	 	 	 :
		        	 	 	 g[d++] = c >> 12 | 224, g[d++] = c >> 6 & 63 | 128
		        	 	 ), g[d++] = c & 63 | 128
					 */
					if(55296 == (c & 64512) && f + 1 < q.length() && 56320 == (q.codePointAt(f + 1) & 64512)){
						c = 65536 + ((c & 1023) << 10) + (q.codePointAt(++f) & 1023);
						g[d++] = c >> 18 | 240;
						g[d++] = c >> 12 & 63 | 128;
					}else{
						g[d++] = c >> 12 | 224;
						g[d++] = c >> 6 & 63 | 128;
					}
					 g[d++] = c & 63 | 128;
				}
			}
		}
		/**
		 *   a = h;  
	    for (d = 0; d < g.length; d++) a += g[d], a = b(a, "+-a^+6");  
	    a = b(a, "+-3^+b+-f");  
	    a ^= Number(e[1]) || 0;  
	    0 > a && (a = (a & 2147483647) + 2147483648);  
	    a %= 1E6;  
	    return a.toString() + "." + (a ^ h);
		 */
		long ah = h;
//		System.out.println(g.length);
		for(d=0;d<g.length;d++){
			if(g[d]==0)break;
			ah += g[d];
			ah = b(ah, "+-a^+6");
		}
		ah = b(ah, "+-3^+b+-f");  
	    ah ^= (a + b);  
//	    0 > ah && (ah = (a & 2147483647) + 2147483648);  
	    ah %= 1E6;  
		return ah + "." + (a ^ h);
	}

	/*
	  for (var d = 0; d < b.length - 2; d += 3) {  
	      var c = b.charAt(d + 2),  
	          c = "a" <= c ? c.charCodeAt(0) - 87 : Number(c),  
	          c = "+" == b.charAt(d + 1) ? a >>> c : a << c;  
	      a = "+" == b.charAt(d) ? a + c & 4294967295 : a ^ c  
	  }  
	  return a  
	} 
	*/ 
	private static long b(long a ,String b){
		for(int d=0;d<b.length()-2;d+=3){
			long c = b.charAt(d+2);
			c = 'a' <= c ? c - 87 : c;  
			a = '+' == b.charAt(d) ? a + c & 4294967295L : a ^ c;  
		}
		System.out.println(a);
		return a;
	}
}
