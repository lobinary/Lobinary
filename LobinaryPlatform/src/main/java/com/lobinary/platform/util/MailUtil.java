package com.lobinary.platform.util;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailUtil {
	static Logger logger = LoggerFactory.getLogger(MailUtil.class);

	public static void main(String[] args) {
		try {
			MailUtil.sendMail("919515134@qq.com", "欢迎您加入疯子团队",
					"欢迎您加入疯子团队，这个是疯子团队系统邮箱		http:www.lobinary.com");
		} catch (Exception e) {
			System.out.println("发送失败");
		}
	}

	private static String host = "smtp.sina.com";// smtp.qq.com
	private static String mail_head_name = "CrazyTeam";
	private static String mail_head_value = "CrazyTeam";
	private static String mail_from = "crazyteamsystem@sina.com";
	private static String personalName = "疯子团队系统";
	static Authenticator auth;
	
	static{
		if(auth==null){
			auth = new MailUtil.Email_Autherticator();// 进行邮件服务用户认证
		}
	}

	/**
	 * 通过系统多人发送
	 * 
	 * @param headName
	 * @param headValue
	 * @param mailToList
	 * @param mailSubject
	 * @param mailBody
	 * @throws SendFailedException
	 */
	public static void sendMail(List<String> mailToList, String mailSubject,
			String mailBody) throws SendFailedException {
		for (String mailTo : mailToList) {
			try {
				sendMail(mailTo, mailSubject, mailBody);
			} catch (Exception e) {
				logger.info("发送给" + mailTo + "的邮件发送失败，准备发送下一位");
			}
		}
	}

	/**
	 * 通过系统单人发送
	 * 
	 * @param headName
	 * @param headValue
	 * @param mailTo
	 * @param mailSubject
	 * @param mailBody
	 * @throws SendFailedException
	 */
	public static void sendMail(String mailTo, String mailSubject, String mailBody)
			throws SendFailedException {
		try {
			Properties props = new Properties();// 获取系统环境
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.auth", "true");
			Session session = Session.getDefaultInstance(props, auth);
			// 设置session,和邮件服务器进行通讯
			MimeMessage message = new MimeMessage(session);
			message.setContent("Hello", "text/plain");// 设置邮件格式
			message.setSubject(mailSubject);// 设置邮件主题
			message.setText(mailBody);// 设置邮件内容
			message.setHeader(mail_head_name, mail_head_value);// 设置邮件标题
			message.setSentDate(new Date());// 设置邮件发送时期
			Address address = new InternetAddress(mail_from, personalName);
			message.setFrom(address);// 设置邮件发送者的地址
			Address toaddress = new InternetAddress(mailTo);// 设置邮件接收者的地址
			message.addRecipient(Message.RecipientType.TO, toaddress);
			Transport.send(message);
			logger.info("Send Mail Ok!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// return flag;
	}

	static class Email_Autherticator extends Authenticator {
		/*
		 * 您获得邮箱帐号：CrazyTeamPublic@qq.com 对应QQ号码：3394952184
		 */
		String username = "crazyteamsystem@sina.com";
		String password = "CrazyTeam";

		public Email_Autherticator() {
			super();
		}

		public Email_Autherticator(String user, String pwd) {
			super();
			username = user;
			password = pwd;
		}

		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
		}
	}
}