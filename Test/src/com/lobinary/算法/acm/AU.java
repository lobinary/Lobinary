package com.lobinary.算法.acm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.lobinary.工具类.LUSystemOutPrintStream;
import com.lobinary.工具类.file.FileUtil;

/**
 * ACM工具类
 * 负责检测ACM程序的输入
 * 并对输入的数据的输出的结果进行校验
 * @author Lobinary
 *
 */
public class AU {
	
	static File 输入文件;
	static File 输出文件;
	
	public static void main(String[] args) throws FileNotFoundException {
		
	}

	static {
		try {
			输入文件 = FileUtil.createFile("/apps/acm/input.txt");
			输出文件 = FileUtil.createFile("/apps/acm/output.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public AU() {
		super();
	}

	public static void check() {
		try {
			String folder = AU.class.getResource("").getFile()+获取调用类名().substring(获取调用类名().indexOf("acm")+4).replace(".", "/");
			folder = URLDecoder.decode(folder,"UTF-8");
			String fileLocation = folder.substring(0, folder.lastIndexOf("/"))+"/案例数据/"+获取调用文件名().replace(".java", ".txt");
			File 案例数据 = new File(fileLocation);
//			System.out.println(案例数据.getAbsolutePath());
			System.out.println("--------------------------------------------------------------准备解析输入和输出数据--------------------------------------------------------------");
			List<String> 案例数据List = FileUtil.readLine2List(案例数据);
			boolean 案例输入 = false;
			boolean 案例输出 = false;
			List<String> 输入数据 = new ArrayList<String>();
			List<String> 输出数据 = new ArrayList<String>();
			for (String 案例数据Str : 案例数据List) {
				if("Sample Input".equals(案例数据Str)){
					System.out.println("--------------------------------------------------------------案例输入开始--------------------------------------------------------------");
					案例输入 = true;
					continue;
				}else if("Sample Output".equals(案例数据Str)){
					System.out.println("--------------------------------------------------------------案例输入结束--------------------------------------------------------------");
					System.out.println("--------------------------------------------------------------案例输出开始--------------------------------------------------------------");
					案例输入 = false;
					案例输出 = true;
					continue;
				}else if("Source".equals(案例数据Str)){
					System.out.println("--------------------------------------------------------------案例输出结束--------------------------------------------------------------");
					案例输出 = false;
					continue;
				}
				if(案例输入&&案例数据Str.length()!=0){
					输入数据.add(案例数据Str);
					System.out.println(案例数据Str);
				}
				if(案例输出&&案例数据Str.length()!=0){
					输出数据.add(案例数据Str);
					System.out.println(案例数据Str);
				}
			}
			
			FileUtil.insertList2File(输入数据, 输入文件);

			Scanner cin = new Scanner(new FileInputStream(输入文件));
			System.out.println("--------------------------------------------------------------调用指定方法--------------------------------------------------------------");
			changeSystemOut();
			调用指定方法(cin);
			resetSystemOut();
			System.out.println("--------------------------------------------------------------开始核对数据--------------------------------------------------------------");
			List<String> 输出文件List = FileUtil.readLine2List(输出文件);
			boolean hasError = false;
			for (int j = 0; j < 输出数据.size(); j++) {
				String 理论数据Str = 输出数据.get(j);
				System.out.println("理论数据："+理论数据Str);
				String 输出文件Str = "null";
				if(输出文件List.size()>=j+1){
					输出文件Str = 输出文件List.get(j);
				}
				System.out.println("实际数据："+输出文件Str);
				if(!输出文件Str.equals(理论数据Str)){
					hasError = true;
					System.out.println("****核对数据出错************************************************************************************");
					break;
				}else{
					System.out.println("============================核对正确无误============================");
				}
			}
			if(hasError){
				
			}else{
				System.out.println("##############################################################################################");
				System.out.println("##############################################################################################");
				System.out.println("##############################################################################################");
				System.out.println("###                                                                                       ####");
				System.out.println("###                                                                                       ####");
				System.out.println("###                                       恭喜您执行成功                                                                    ####");
				System.out.println("###                                                                                       ####");
				System.out.println("###                                                                                       ####");
				System.out.println("##############################################################################################");
				System.out.println("##############################################################################################");
				System.out.println("##############################################################################################");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将数据不仅写入到控制台，还要写入到输出文件
	 * @throws FileNotFoundException 
	 */
	private static void changeSystemOut() throws FileNotFoundException {
		LUSystemOutPrintStream out = new LUSystemOutPrintStream(new FileOutputStream(输出文件,false));
		out.setSystemOut(System.out);
		System.setOut(out);
		System.setErr(out);
	}
	
	private static void resetSystemOut(){
		System.setOut(LUSystemOutPrintStream.systemOut);
		System.setErr(LUSystemOutPrintStream.systemOut);
	}

	private static Object 调用指定方法(Object param) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException {
		Class<?> c = Class.forName(获取调用类名());
		Object obj = c.newInstance();
		Method[] method = c.getMethods();
		for (Method m : method) {
			if (m.getName().equals("run")) {
				Object r = m.invoke(obj,param);
				return r;
			}
		}
		return null;
	}
	
	public static String 获取调用类名(){
		StackTraceElement stack[] = Thread.currentThread().getStackTrace();
		StackTraceElement invoker = stack[stack.length - 1];
		String className = invoker.getClassName();
		return className;
	}
	
	public static String 获取调用方法名(){
		StackTraceElement stack[] = Thread.currentThread().getStackTrace();
		StackTraceElement invoker = stack[stack.length - 1];
		String methodName = invoker.getMethodName();
		return methodName;
	}
	
	public static String 获取调用文件名(){
		StackTraceElement stack[] = Thread.currentThread().getStackTrace();
		StackTraceElement invoker = stack[stack.length - 1];
		String fileName = invoker.getFileName();
		return fileName;
	}

}
