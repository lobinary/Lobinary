/*
 * @(#)LogUtil.java     V1.0.0      @下午2:40:57
 *
 * 项目名称: ControlClientPC
 *
 * 更改 信息:
 *    作者        				   日期        			描述
 *    ============  	================  =======================================
 *    lobinary       	  2015年10月12日    	创建文件
 *
 */
package com.lobinary.apc.util.common.file;

import org.apache.log4j.Logger;

/**
 * <pre>
 * 通过log4j appender 拦截日志到日志tab中
 * </pre>
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年10月12日 下午2:40:57
 * @version V1.0.0 描述 : 创建文件LogUtil
 * 
 *         
 * 
 */
public class LogUtil {

	/**
	 * <pre>
	 * 
	 * </pre>
	 *
	 * @param class1
	 * @return
	 */
	public static Logger getLogger(Class<FileUtil> class1) {
		
		return Logger.getLogger(FileUtil.class);
	}

}
