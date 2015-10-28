package org.slf4j;



import com.lobinary.android.common.util.date.DateUtil;
import com.lobinary.android.common.util.log.LogUtil;

public class Logger {
	
	public Class<?> clazz;
	
	private String getPreStr(){
		return DateUtil.getCurrDateTime("[HH:MM:ss SSS] ");
	}
	
	private String getEndStr(){
		return " [" + clazz.getSimpleName() + "]";
	}
	
	public void debug(String log){
		outLog(log);
	}

	public void info(String log){
		outLog(log);
	}
	public void error(String log){
		outLog(log);
	}


	public void debug(String log,Throwable t){
		outExceptionLog(log,t);
	}
	public void info(String log,Throwable t){
		outExceptionLog(log,t);
	}
	public void error(String log,Throwable t){
		outExceptionLog(log,t);
	}
	

	private void outLog(String log) {
		LogUtil.out(getPreStr()+log+getEndStr());
		System.out.println(getPreStr()+log+getEndStr());
	}

	private void outExceptionLog(String log,Throwable t) {
		LogUtil.out(getPreStr()+log+getEndStr()+t.getMessage());
		System.out.println(getPreStr()+log+getEndStr()+t.getMessage());
	}
}
