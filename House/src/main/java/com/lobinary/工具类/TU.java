package com.lobinary.工具类;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.mail.MessagingException;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.l.web.house.dto.房屋统计信息;
import com.l.web.house.service.catchsystem.impl.LinkedHouseImpl;
import com.lobinary.工具类.date.DateUtil;

/**
 * 定时器工具类
 * 
 * 负责定时启动以及相关的提示等功能
 * @author Lobinary
 * @see 本工具类使用了下方代码
 * @see com.lobinary.工具类.LU
 * @see com.lobinary.工具类.date.DateUtil
 *
 */
public class TU {
	
	private static final String FILE_LOCATION = "/apps/logs/house/house"+DateUtil.getDate(new Date())+".log";
	private static String appName = "House";
	private static int times = 0; 
	
	public static void main(String[] args) throws UnsupportedEncodingException, MessagingException, InterruptedException {
		startJob(args);
	}
	
	/**
	 * 启动定时启动器
	 * @param args
	 * @throws MessagingException 
	 * @throws UnsupportedEncodingException 
	 * @throws InterruptedException 
	 */
	public static void startJob(String[] args) throws UnsupportedEncodingException, MessagingException, InterruptedException{
		times++;
		if(times>=5)return;
		LU.changeLogFile(FILE_LOCATION);
		
		LU.l("======================"+appName+"定时任务=====执行开始======================");
		System.out.println("A");
		LU.changeSystemOut2Log();
		System.out.println("B");
		try {
			job(args);
		} catch (Exception e) {
			String fullStackTrace = ExceptionUtils.getFullStackTrace(e);
			LU.l("捕获到异常"+e.getMessage());
			e.printStackTrace();
			fullStackTrace = fullStackTrace.replace("\n", "<br>");
			MU.sendMail("房屋捕获系统报警", "捕获房屋系统时发生异常，异常信息如下:<br>"+e.getMessage()+"<br>"+fullStackTrace, "", "919515134@qq.com","ljrxxx@aliyun.com");
			Thread.sleep(5*60*1000);
			startJob(args);
		}
		
		LU.l("======================"+appName+"定时任务=====执行结束======================");
	
	}
	
	private static void job(String[] args) throws Exception{
		LU.changeLogFile(FILE_LOCATION);
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath*:spring/spring-application.xml");
		LinkedHouseImpl 链家房屋信息捕获 = (LinkedHouseImpl) ctx.getBean(LinkedHouseImpl.class);
		链家房屋信息捕获.捕获房屋信息(args);
		房屋统计信息 查询捕获房屋统计信息 = 链家房屋信息捕获.查询捕获房屋统计信息();
		MU.sendMail("房屋捕获系统通知", "捕获房屋数据完成,统计数据如下<br>"+查询捕获房屋统计信息.toString(), "", "919515134@qq.com","ljrxxx@aliyun.com");
	}

}
