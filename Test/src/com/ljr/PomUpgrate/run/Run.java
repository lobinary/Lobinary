package com.ljr.PomUpgrate.run;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ljr.PomUpgrate.FileUtil;
import com.ljr.PomUpgrate.HttpUtils;
import com.ljr.PomUpgrate.dto.Dependency;

public class Run {

	private static final String nexus路径 = "http://172.17.102.5:8080";
	private static final String 父pom路径 = "cmd /c mvn clean package -f D:\\tool\\Eclipse\\YeepayWorkSpace\\mp\\pom.xml";
	
	public static void main(String[] args) {
		for (int i = 0; i < 1; i++) {
			run();
		}
	}

	public static void run() {
		System.out.println("hello");
		Runtime runtime = Runtime.getRuntime();
		try {
			Process 处理线程 = runtime.exec(父pom路径);
			// 取得命令结果的输出流
			InputStream fis = 处理线程.getInputStream();
			// 用一个读输出流类去读
			InputStreamReader isr = new InputStreamReader(fis, "gbk");
			// 用缓冲器读行
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			// 直到读完为止
			Map<String, String> 错误map = new HashMap<String, String>();
			while ((line = br.readLine()) != null) {
				System.out.println(line);
//				line = "[ERROR] /D:/tool/Eclipse/YeepayWorkSpace/mp/ms/src/main/java/ms/run.java:[3,34]程序包org.apache.zookeeper不存在";
				line = "[ERROR] /D:/tool/Eclipse/YeepayWorkSpace/mp/ms/src/main/java/com/csii/ecif/basicservice/account/impl/AccountServiceImpl.java:[743,80] 找不到符号";
				
				if (line != null && line.contains("[ERROR]") && line.contains("程序包") && line.contains("不存在")) {
					String 截取的丢失包名 = line.substring(line.indexOf("程序包") + 3, line.indexOf("不存在"));
					String 截取的丢失包项目pom路径 = line.substring(line.indexOf("[ERROR]") + 9, line.indexOf("src")) + "pom.xml";
					错误map.put(截取的丢失包名, 截取的丢失包项目pom路径);
				}
//				[ERROR] /D:/zx20160926/com.csii.ecif.basicservice.account.impl/src/main/java/com/csii/ecif/basicservice/account/impl/AccountServiceImpl.java:[15,34] 找不到符号
//				[ERROR] 符号:   类 ECIFUtil
//				[ERROR] 位置: 程序包 com.csii.ecif.basicservice
				if (line != null && line.contains("[ERROR]") && line.contains("找不到符号")) {
					String 截取的丢失包项目pom路径 = line.substring(line.indexOf("[ERROR]") + 9, line.indexOf("src")) + "pom.xml";
					line = br.readLine();
//					line = "[ERROR] 符号:   类 ECIFUtil";
					String 截取的丢失类名 = line.substring(16);
					line = br.readLine();
//					line = "[ERROR] 位置: 程序包 com.csii.ecif.basicservice";
					String 截取的丢失类包名 = line.substring(16);
					错误map.put(截取的丢失类包名+"." + 截取的丢失类名, 截取的丢失包项目pom路径);
				}
			}
			
			//测试数据
			错误map.put("org.beetl", "D:/tool/Eclipse/YeepayWorkSpace/mp/ms/pom.xml");
			
			
			System.out.println("==================mvn 命令执行结束，分析错误数据结束，分析结果如下==================================================================================================================");
			for (String 丢失的包名 : 错误map.keySet()) {
				System.out.println("****************分析记录********************************************************************************************");
				String 丢失包POM所在位置 = 错误map.get(丢失的包名);
				System.out.println("丢失的包名：" + 丢失的包名 + ",丢失包对应的pom位置：" + 丢失包POM所在位置);

				List<Dependency> 通过nexus捕获包含该类的依赖包 = 通过nexus捕获包含该类的依赖包(丢失的包名);
				Dependency 筛选出来的依赖包 = 筛选依赖包(通过nexus捕获包含该类的依赖包, 丢失的包名);
				if (筛选出来的依赖包 == null) {
					System.out.println("未发现可以植入的依赖包，请手动修复该问题后 重新执行本程序");
					System.exit(1);
				} else {
					System.out.println("筛选出来的依赖包为：" + 筛选出来的依赖包);
				}
				File pom文件 = new File(丢失包POM所在位置);
				List<String> pom文件List = FileUtil.readLine2List(pom文件);
				boolean pom中包含该依赖 = false;
				for (String pom文件每行 : pom文件List) {
					if (pom文件每行.contains(筛选出来的依赖包.注释())) {
						pom中包含该依赖 = true;
					}
				}
				if (!pom中包含该依赖) {
					File 备份pom = new File(丢失包POM所在位置 + System.currentTimeMillis());
					for (int i = 0; i < pom文件List.size(); i++) {
						String lineStr = pom文件List.get(i);
						if (lineStr.contains("<dependencies>")) {
							FileUtil.copyFile(pom文件, 备份pom);
							pom文件List.set(i, lineStr+"\n"+筛选出来的依赖包.pom("<!--  "+丢失的包名+"  -->"));
							break;
						}
						
					}
					StringBuilder sb = new StringBuilder();
					for (String s : pom文件List) {
						sb.append(s).append("\n");
					}
					pom文件.delete();
					pom文件.createNewFile();
					FileUtil.insertStr2File(sb.toString(), pom文件);
					System.out.println("依赖包"+筛选出来的依赖包+"向pom文件"+pom文件.getAbsolutePath()+"添加完毕");
				}else{
					System.out.println("该依赖包"+筛选出来的依赖包+"已经存在于pom文件"+pom文件.getAbsolutePath()+"中");
					
				}
				System.out.println("****************分析记录********************************************************************************************");
			}
		} catch (Exception e) {
			System.out.println("执行命令异常");
			e.printStackTrace();
		}
	}

	private static Dependency 筛选依赖包(List<Dependency> 通过nexus捕获包含该类的依赖包, String 筛选类名) {
		Dependency 默认依赖包 = 通过nexus捕获包含该类的依赖包 == null ? null : 通过nexus捕获包含该类的依赖包.get(0);
		System.out.println(通过nexus捕获包含该类的依赖包.size());
		for (Dependency 依赖包 : 通过nexus捕获包含该类的依赖包) {
			if (依赖包.getGroupId().contains("com.hrbb.zxqd")) {
				return 依赖包;
			}
		}
		boolean csii的第一个依赖包被装配 = false;
		for (Dependency 依赖包 : 通过nexus捕获包含该类的依赖包) {
			if (依赖包.getGroupId().contains("com.csii")) {
				if (依赖包.getVersion().matches("^\\d+.\\d+.\\d+.\\d+$")) {// 如果版本是数字
					return 依赖包;
				}
				if (!csii的第一个依赖包被装配) {// 装配第一个依赖包
					默认依赖包 = 依赖包;
					csii的第一个依赖包被装配 = true;// 标记为已装配
				}
			}
		}

		for (Dependency 依赖包 : 通过nexus捕获包含该类的依赖包) {// 都没有筛选版本号是数字的
			if (依赖包.getVersion().matches("^\\d+.\\d+.\\d+.\\d+$")) {// 如果版本是数字
				return 依赖包;
			}
		}
		return 默认依赖包;
	}

	private static List<Dependency> 通过nexus捕获包含该类的依赖包(String 筛选类名) {
		String nexus返回字符串 = HttpUtils.doGet(nexus路径+"/nexus/service/local/lucene/search?_dc=1475159084572&cn=" + 筛选类名);
		// System.out.println("########################nexus返回字符串#######################################################################");
		// System.out.println(nexus返回字符串);
		// System.out.println("#####################nexus返回字符串###########################################################################");
		if (!nexus返回字符串.contains("<data>")) {// 如果不存在，需要人工处理
			System.out.println("nexus没有查到包为：" + 筛选类名 + "的数据");
			return null;
		}
		String data标签 = "<data>";
		String 截取data字符串 = nexus返回字符串.substring(nexus返回字符串.indexOf(data标签) + data标签.length(), nexus返回字符串.indexOf("</data>"));
		// System.out.println("====================================================================================");
		// System.out.println(截取data字符串);
		String[] artifact数组 = 截取data字符串.split("<artifact>");

		List<Dependency> dlist = new ArrayList<Dependency>();
		for (String 每个artifact : artifact数组) {
			if (每个artifact == null || 每个artifact.length() < 10)
				continue;
			Dependency d = new Dependency(每个artifact);
			dlist.add(d);
			// System.out.println(d);
			// System.out.println("======================================================================");
		}
		return dlist;
	}

}
