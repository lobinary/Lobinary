package com.lobinary.java.jdk.jdk18.lambda;

import com.lobinary.java.jdk.jdk18.FunctionalInterface函数式接口;
import com.lobinary.java.多线程.TU;

/**
 * 
 * <pre>
 * 	https://www.ibm.com/developerworks/cn/java/j-lo-jdk8newfeature/
 * </pre>
 *
 * @ClassName: Lambda表达式
 * @author 919515134@qq.com
 * @date 2016年10月12日 上午10:42:33
 * @version V1.0.0
 */
public class Lambda表达式 {
	
	/**
	 * Lambda表达式一般都是配合FunctionalInterface函数式接口使用
	 * 详情可见 ： {@link FunctionalInterface函数式接口}
	 */
	
	public static void main(String[] args) {
		new Thread(() -> { TU.l("1"); } ).start();
	}

}
