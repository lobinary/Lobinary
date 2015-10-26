package com.lobinary.android.platform.util;

import android.os.Handler;
import android.os.Message;

import com.lobinary.android.common.util.log.LogUtilInterface;

/**
 * 安卓日志工具类
 * @author Lobinary
 *
 */
public class AndroidLogUtil implements LogUtilInterface{

	private static Handler logHandler;
	
	@Override
	public boolean out(String log) {
		Message msg = new Message();  
		msg.obj = log;
		logHandler.sendMessage(msg);  
		return true;
	}
	

	/* (non-Javadoc)
	 * @see com.lobinary.android.common.util.log.LogUtil#out(java.lang.String)
	 */
	public static Handler getLogHandler() {
		return logHandler;
	}

	public static void setLogHandler(Handler logHandler) {
		AndroidLogUtil.logHandler = logHandler;
	}
	
}
