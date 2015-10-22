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
package com.lobinary.apc.util.log;

import com.lobinary.android.common.util.log.LogUtilInterface;
import com.lobinary.apc.windows.ControlClientPCWindows;

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
public class WindowsLogUtil implements LogUtilInterface{
	
	/* (non-Javadoc)
	 * @see com.lobinary.android.common.util.log.LogUtil#out(java.lang.String)
	 */
	@Override
	public boolean out(String log) {
		String oldText = ControlClientPCWindows.logTextArea.getText();
		ControlClientPCWindows.logTextArea.setText(oldText+"\n"+log);
		return true;
	}

}
