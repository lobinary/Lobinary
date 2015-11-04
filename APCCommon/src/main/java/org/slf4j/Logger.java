package org.slf4j;



import com.lobinary.android.common.util.PropertiesUtil;
import com.lobinary.android.common.util.date.DateUtil;
import com.lobinary.android.common.util.log.LogUtil;

public class Logger {
	
	private static int level = 0;//debug 0 info 1 error 2

	public final static int DEBUG = 0;
	public final static int INFO = 1;
	public final static int ERROR = 2;
	
	public static int getLevel() {
		return level;
	}

	public static void setLevel(int level) {
		Logger.level = level;
	}

	public Class<?> clazz;
	
	private String getPreStr(){
		return DateUtil.getCurrDateTime("[HH:MM:ss SSS] ");
	}
	
	private String getEndStr(){
		return " [" + clazz.getSimpleName() + "]";
	}
	
	public void debug(String log){
		if(level<=0)
		outLog(log);
	}

	public void info(String log){
		if(level<=1)
		outLog(log);
	}
	public void error(String log){
		if(level<=2)
		outLog(log);
	}


	public void debug(String log,Throwable t){
		if(level<=0)
		outExceptionLog(log,t);
	}
	public void info(String log,Throwable t){
		if(level<=1)
		outExceptionLog(log,t);
	}
	public void error(String log,Throwable t){
		if(level<=2)
		outExceptionLog(log,t);
	}
	

	private void outLog(String log) {
		LogUtil.out(getPreStr()+log+getEndStr());
		System.out.println(getPreStr()+log+getEndStr());
	}

	private void outExceptionLog(String log,Throwable t) {
		LogUtil.out(getPreStr()+log+getEndStr()+t.getMessage());
		System.out.println(getPreStr()+log+getEndStr()+t.getMessage());
		t.printStackTrace();
	}
}
