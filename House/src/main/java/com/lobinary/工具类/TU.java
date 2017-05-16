package com.lobinary.工具类;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.l.web.house.dto.房屋统计信息;
import com.l.web.house.service.catchsystem.impl.LinkedHouseImpl;
import com.l.web.house.util.CreateChartServiceImpl;
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
		List<房屋统计信息> 查询房屋价格走势根据批次号 = 链家房屋信息捕获.查询房屋价格走势根据批次号("20170503000000");
		String 创建链家价格走势图文件 = CreateChartServiceImpl.创建链家价格走势图(查询房屋价格走势根据批次号);
		List<房屋统计信息> 查询批次号价格变动数据 = 链家房屋信息捕获.查询批次号价格变动数据(null);
		StringBuilder sb = new StringBuilder();
		sb.append("<br>");
		sb.append("<table border=\"1\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse:collapse; border-color:#000000\">");
		sb.append("<th>序号</th>");
		sb.append("<th>网址</th>");
		sb.append("<th>当前价格</th>");
		sb.append("<th>户型</th>");
		sb.append("<th>实用面积</th>");
		sb.append("<th>建筑类型</th>");
		sb.append("<th>所在区县</th>");
		sb.append("<th>所在小区</th>");
		sb.append("<th>朝向</th>");
		sb.append("<th>产权年限</th>");
		sb.append("<th>差距价格</th>");
		sb.append("<th>房屋基本信息id</th>");
		sb.append("<th>房屋本批次号</th>");
		sb.append("<th>房屋上一批次号</th>");
		sb.append("<th>原价格</th>");
		int i=0;
		for (房屋统计信息 f : 查询批次号价格变动数据) {
			i++;
			sb.append("<tr>");
			sb.append("<td>"+i+"</td>");
			sb.append("<td><a href='"+f.get网址()+"' >"+f.get网址()+"</a></td>");
			sb.append("<td>"+f.get房屋本批次价格()+"</td>");
			sb.append("<td>"+f.get户型()+"</td>");
			sb.append("<td>"+f.get实用面积()+"</td>");
			sb.append("<td>"+f.get建筑类型()+"</td>");
			sb.append("<td>"+f.get所在区县()+"</td>");
			sb.append("<td>"+f.get所在小区()+"</td>");
			sb.append("<td>"+f.get朝向()+"</td>");
			sb.append("<td>"+f.get产权年限()+"</td>");
			sb.append("<td>"+f.get差距价格()+"</td>");
			sb.append("<td>"+f.get房屋基本信息id()+"</td>");
			sb.append("<td>"+f.get房屋本批次号()+"</td>");
			sb.append("<td>"+f.get房屋上一批次号()+"</td>");
			sb.append("<td>"+f.get房屋上批次价格()+"</td>");
			sb.append("</tr>");
		}
		sb.append("</table>");
		AttchAndImgMail.sendEmail("房屋捕获系统通知", "捕获房屋数据完成,统计数据如下<br>"+查询捕获房屋统计信息.toString()+"<br>近日房屋涨跌数量走势图如下:<br>{image}"+sb.toString(), 创建链家价格走势图文件, null);
//		MU.sendMail("房屋捕获系统通知", "捕获房屋数据完成,统计数据如下<br>"+查询捕获房屋统计信息.toString(), "", "919515134@qq.com","ljrxxx@aliyun.com");
	}

}
