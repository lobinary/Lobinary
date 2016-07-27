package com.lobinary.书籍.thinking_in_java.对象入门;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * 
  *
  *	<pre>
  * @Description: TODO
  * </pre>
  * @ClassName: 系统信息
  * @author Comsys-Lobinary
  * @date 2016年6月17日 上午11:23:31
  * @version V1.0.0
 */
public class 系统信息 {
	public static void main(String[] args) {
		System.out.println(new Date());
		Properties p = System.getProperties();
		p.list(System.out);
		System.out.println("--- Memory Usage:");
		Runtime rt = Runtime.getRuntime();
		System.out.println("Total Memory = " + rt.totalMemory() + " Free Memory = " + rt.freeMemory());
		List<File> fl = new ArrayList<File>();
		for (int i = 0; i < 10000; i++) {
			fl.add(new File("c:/HaxLogs.txt"));
		}
		System.out.println("Total Memory = " + rt.totalMemory() + " Free Memory = " + rt.freeMemory());
	}
}
