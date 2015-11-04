package com.lobinary.android.platform.util;

import com.lobinary.android.common.util.log.LogUtilInterface;
import com.lobinary.android.platform.ui.activity.LogActivity;

/**
 * 安卓日志工具类
 * @author Lobinary
 *
 */
public class AndroidLogUtil implements LogUtilInterface{

//	private static Handler logHandler;
	
	@Override
	public boolean out(String log) {
//		if(logHandler!=null){
//			Message msg = new Message();  
//			msg.obj = log;
//			logHandler.sendMessage(msg); 
//		}else{
//			System.out.println("[日志工具暂未初始化]"+log);
//		}
		LogActivity.outLog(log);
		return true;
	}
	

	/* (non-Javadoc)
	 * @see com.lobinary.android.common.util.log.LogUtil#out(java.lang.String)
	 */
//	public static Handler getLogHandler() {
//		return logHandler;
//	}
//
//	public static void setLogHandler(Handler logHandler) {
//		AndroidLogUtil.logHandler = logHandler;
//	}
	
}
