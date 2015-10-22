package com.lobinary.android.common.util.log;

import com.lobinary.android.common.util.factory.CommonFactory;

/**
 * <pre>
 * 输出日志接口
 * </pre>
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年10月22日 下午1:37:50
 * @version V1.0.0 描述 : 创建文件LogUtil
 * 
 *         
 * 
 */
public class LogUtil {
	
	/**
	 * 日志工具实现类
	 */
	static LogUtilInterface logUtilImpl;
	
	static{
		logUtilImpl = CommonFactory.getLogUtil();
	}
	
	/**
	 * 
	 * <pre>
	 * 输出字符串日志
	 * </pre>
	 *
	 * @return
	 */
	public static boolean out(String log){
		return logUtilImpl.out(log);
	}

}
