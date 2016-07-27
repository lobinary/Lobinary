package com.lobinary.test.normal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <pre>
 * 
 * </pre>
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年5月21日 下午2:41:51
 * @version V1.0.0 描述 : 创建文件反射000000寻找父类方法执行
 * 
 *         
 * 
 */
public class 反射寻找父类方法执行 {
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		接口 o = new 实现类();
		Method method = o.getClass().getMethod("out");
		String s = (String) method.invoke(o);
		System.out.println(s);
	}

}
