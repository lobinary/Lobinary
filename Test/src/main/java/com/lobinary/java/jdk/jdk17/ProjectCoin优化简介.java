package com.lobinary.java.jdk.jdk17;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.lobinary.工具类.O;

/**
 * 
 * <pre>
 * 小的语言改进（Project Coin）
 * 	https://my.oschina.net/mushui/blog/148250
 * </pre>
 *
 * @ClassName: ProjectCoin优化简介
 * @author 919515134@qq.com
 * @date 2016年10月10日 下午3:57:49
 * @version V1.0.0
 */
@SuppressWarnings({"unused","null"})
public class ProjectCoin优化简介 {
	
	public static void main(String[] args) {
		O.f("switch支持字符串匹配");
		switch支持字符串匹配();
		O.f("定义二进制数字");
		定义二进制数字();
		O.f("数字分割");
		数字分割();
		O.f("多异常捕获");
		多异常捕获();
//		O.f("Try-with-resources (TWR)");
//		TWR();//XXX 请自行查看
		O.f("简化泛型");
		简化泛型();
		O.f(null);
	}

	private static void 简化泛型() {
		Map<Integer, Map<String, String>> usersLists = new HashMap<Integer, Map<String, String>>();
		
		//可以简化为
		Map<Integer, Map<String, String>> usersLists2 = new HashMap<>();
		
		System.out.println("Map<Integer, Map<String, String>> usersLists = new HashMap<Integer, Map<String, String>>();");
		System.out.println("可以简化为");
		System.out.println("Map<Integer, Map<String, String>> usersLists2 = new HashMap<>();");
	}

	private static void TWR() {
//		而使用try-with-resources语法，则可以简化为：
		URL url = null;
		try (OutputStream out = new FileOutputStream(""); InputStream is = url.openStream()) {
		    byte[] buf = new byte[4096];
		    int len;
		    while ((len = is.read(buf)) > 0) {
		        out.write(buf, 0, len);
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		但是使用try-with-resources的时候还是由可能造成资源没有关闭，比如在try()中有错误时，比如：


		try ( ObjectInputStream in = new ObjectInputStream(new 
		      FileInputStream("someFile.bin")) ) {
//		 ...
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		比如文件存在，但却不是写入的对象序列，因此会造成不正常打开，此时ObjectInputStream不能正确初始化，且不会关闭，因此正确的方式是分开资源变量：

		try ( FileInputStream fin = new FileInputStream("someFile.bin");
		         ObjectInputStream in = new ObjectInputStream(fin) ) {
//		 ...
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void 多异常捕获() {
		String l = null;
		try {
			l.toString();
		} catch (NullPointerException | OutOfMemoryError e) {
			O.show("} catch (NullPointerException | OutOfMemoryError e) {",e);
		}
	}

	private static void 数字分割() {
		long l = 111_222_333;
		O.show("long l = 111_222_333;",l);
	}

	private static void 定义二进制数字() {
		
		int a = Integer.parseInt("10", 2);
		System.out.println("原二进制定义[int a = Integer.parseInt(\"10\", 2);]\t==>\t"+a);
		
		a = 0b10;
		System.out.println("新二进制定义[int a = 0b10;]\t\t\t==>\t"+a);
	}

	private static void switch支持字符串匹配() {
		System.out.print("switch支持字符串的匹配：");
		String s = "a";
		switch (s) {
		case "a":
			System.out.println("对应a的执行语句");
			break;
		default:
			System.out.println("默认执行语句");
			break;
		}
	}

}
