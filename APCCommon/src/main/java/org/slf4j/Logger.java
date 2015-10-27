package org.slf4j;


import com.lobinary.android.common.util.log.LogUtil;

public class Logger {
	
	public Class<?> clazz;
	
	private String getPreStr(){
		return clazz.getName()+":";
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
		LogUtil.out(getPreStr()+log);
		System.out.println(getPreStr()+log);
	}

	private void outExceptionLog(String log,Throwable t) {
		LogUtil.out(getPreStr()+log+t.getMessage());
		System.out.println(getPreStr()+log+t.getMessage());
	}
}
