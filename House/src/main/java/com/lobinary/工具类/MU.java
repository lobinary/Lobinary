package com.lobinary.工具类;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.sun.mail.smtp.SMTPTransport;

/**
 * MailUtil 邮件工具类
 * 
 * 使用需要引用Pom的dependency
 * 
  <!-- https://mvnrepository.com/artifact/javax.mail/javax.mail-api -->
  <dependency> <groupId>javax.mail</groupId>
  <artifactId>javax.mail-api</artifactId> <version>1.5.6</version>
  </dependency>
  
  <!-- https://mvnrepository.com/artifact/javax.mail/mail --> <dependency>
  <groupId>javax.mail</groupId> <artifactId>mail</artifactId>
  <version>1.4.7</version> </dependency>
 * 
 * @author Lobinary
 *
 */
public class MU {

	private static final String DEFAULT_TITLE = "信息通知";
	// 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般(只是一般, 绝非绝对)格式为: smtp.xxx.com
	// 网易163邮箱的 SMTP 服务器地址为: smtp.163.com
	private static String DEFAULT_MAIL_S = "smtp.qq.com";
	// 发件人的 邮箱 和 密码（替换为自己的邮箱和密码）
	// PS: 某些邮箱服务器为了增加邮箱本身密码的安全性，给 SMTP 客户端设置了独立密码（有的邮箱称为“授权码”）,
	// 对于开启了独立密码的邮箱, 这里的邮箱密码必需使用这个独立密码（授权码）。
	private static String DEFAULT_MAIL_U = "936231734@qq.com";
	private static String DEFAULT_MAIL_P = "mfpmhndcfgeybaig";

	// 收件人邮箱（替换为自己知道的有效邮箱）
//	private static String receiveMailAccount = "lobinary@qq.com";

	public static void main(String[] args) throws Exception {
		sendImageMail("Doncat消息通知", "欢迎使用<a href=\"www.baidu.com\" >doncat</a>消息通知服务", "Doncat 通知系统", "lobinary@qq.com","ljrxxx@aliyun.com");
	}

	public static boolean sendMail(String title, String mailInfo, String senderDisplayName,String receiveMail,String ccMail) throws UnsupportedEncodingException, MessagingException {
		Map<String, String> receiveMailMap = new HashMap<String,String>();
		receiveMailMap.put(receiveMail, "收件方");
		Map<String, String> ccMailMap = new HashMap<String,String>();
		ccMailMap.put(ccMail, "收件方");
		return sendMail(null, title, mailInfo, null, null,senderDisplayName, receiveMailMap , ccMailMap, null);
	}
	
	public static boolean sendImageMail(String title, String mailInfo, String senderDisplayName,String receiveMail,String ccMail) throws UnsupportedEncodingException, MessagingException {
		Map<String, String> receiveMailMap = new HashMap<String,String>();
		receiveMailMap.put(receiveMail, "收件方");
		Map<String, String> ccMailMap = new HashMap<String,String>();
		ccMailMap.put(ccMail, "收件方");
		return sendImageMail(null, title, mailInfo, null, null,senderDisplayName, receiveMailMap , ccMailMap, null);
	}

	/**
	 * 发送邮件
	 * @param props 邮件服务器配置，若为null，则通过默认邮箱发送
	 * @param title 标题 默认为 DEFAULT_TITLE=信息通知
	 * @param mailInfo 邮件信息实体字符串 支持html
	 * @param sendMail 发送方邮件
	 * @param password 发送方密码
	 * @param senderDisplayName 发送方展示名称 默认为发送邮件
	 * @param receiveMailMap 收件邮件Map key为收件邮件地址:example@qq.com value:测试邮件
	 * @param ccMailMap 抄送邮件Map 
	 * @param bccMailMap 密文邮件
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws MessagingException
	 */
	public static boolean sendMail(Properties props, String title, String mailInfo, String sendMail, String password, String senderDisplayName,
			Map<String, String> receiveMailMap, Map<String, String> ccMailMap, Map<String, String> bccMailMap) throws UnsupportedEncodingException, MessagingException {
		// 构造回话
		Session session = getSession(props);
		// 创建一封邮件
		MimeMessage message = constructionMailMessage(title, mailInfo, sendMail, senderDisplayName, receiveMailMap, ccMailMap, bccMailMap, session);
		// 发送邮件
		send2MailServer(session, message,sendMail,password);
		return true;

	}

	public static boolean sendImageMail(Properties props, String title, String mailInfo, String sendMail, String password, String senderDisplayName,
			Map<String, String> receiveMailMap, Map<String, String> ccMailMap, Map<String, String> bccMailMap) throws UnsupportedEncodingException, MessagingException {
		// 构造回话
		Session session = getSession(props);
		// 创建一封邮件
		MimeMessage message = constructionMailMessage(title, mailInfo, sendMail, senderDisplayName, receiveMailMap, ccMailMap, bccMailMap, session);
		
		sendImageMail2MailServer(message, "D:\\apps\\house\\img\\2.jpg", "D:\\apps\\house\\img\\price-trend.png");
		// 发送邮件
		send2MailServer(session, message,sendMail,password);
		return true;

	}

	private static Session getSession(Properties sessionProps) {
		if (sessionProps == null)
			sessionProps = getDefaultProps();
		// 2. 根据配置创建会话对象, 用于和邮件服务器交互
		Session session = Session.getDefaultInstance(getDefaultProps());
		session.setDebug(true); // 设置为debug模式, 可以查看详细的发送 log
		return session;
	}

	private static Properties getDefaultProps() {
		// 1. 创建参数配置, 用于连接邮件服务器的参数配置
		Properties props = new Properties(); // 参数配置
		props.setProperty("mail.transport.protocol", "smtp"); // 使用的协议（JavaMail规范要求）
		props.setProperty("mail.smtp.host", DEFAULT_MAIL_S); // 发件人的邮箱的
																	// SMTP
																	// 服务器地址
		props.setProperty("mail.smtp.auth", "true"); // 需要请求认证
	
		// PS: 某些邮箱服务器要求 SMTP 连接需要使用 SSL 安全认证 (为了提高安全性, 邮箱支持SSL连接, 也可以自己开启),
		// 如果无法连接邮件服务器, 仔细查看控制台打印的 log, 如果有有类似 “连接失败, 要求 SSL 安全连接” 等错误,
		// 打开下面 /* ... */ 之间的注释代码, 开启 SSL 安全连接。
	
		// SMTP 服务器的端口 (非 SSL 连接的端口一般默认为 25, 可以不添加, 如果开启了 SSL 连接,
		// 需要改为对应邮箱的 SMTP 服务器的端口, 具体可查看对应邮箱服务的帮助,
		// QQ邮箱的SMTP(SLL)端口为465或587, 其他邮箱自行去查看)
		final String smtpPort = "465";
		props.setProperty("mail.smtp.port", smtpPort);
		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.socketFactory.port", smtpPort);
		return props;
	}

	/**
	 * 构建邮件数据
	 * @param title
	 * @param mailInfo
	 * @param sendMail
	 * @param senderDisplayName
	 * @param receiveMailMap
	 * @param ccMailMap
	 * @param bccMailMap
	 * @param session
	 * @return
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	private static MimeMessage constructionMailMessage(String title, String mailInfo, String sendMail, String senderDisplayName, Map<String, String> receiveMailMap,
			Map<String, String> ccMailMap, Map<String, String> bccMailMap, Session session) throws MessagingException, UnsupportedEncodingException {
		MimeMessage message = new MimeMessage(session); // 创建邮件对象
		/*
		 * 也可以根据已有的eml邮件文件创建 MimeMessage 对象 MimeMessage message = new
		 * MimeMessage(session, new FileInputStream("MyEmail.eml"));
		 */

		// 2. From: 发件人
		// 其中 InternetAddress 的三个参数分别为: 邮箱, 显示的昵称(只用于显示, 没有特别的要求), 昵称的字符集编码
		// 真正要发送时, 邮箱必须是真实有效的邮箱。
		// message.setFrom(new InternetAddress("936231734@qq.com", "Lobxxx
		// Mail", "UTF-8"));
		if(sendMail==null)sendMail = DEFAULT_MAIL_U;
		if(senderDisplayName==null)senderDisplayName = sendMail;
		message.setFrom(new InternetAddress(sendMail, senderDisplayName, "UTF-8"));

		for (String receiveMail : receiveMailMap.keySet()) {
			// 3. To: 收件人
			message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, receiveMailMap.get(receiveMail), "UTF-8"));
		}
		if(ccMailMap!=null){
			for (String ccMail : ccMailMap.keySet()) {
				// Cc: 抄送（可选）
				message.setRecipient(MimeMessage.RecipientType.CC, new InternetAddress(ccMail, ccMailMap.get(ccMail), "UTF-8"));
			}
		}
		if(bccMailMap!=null){
			for (String bccMail : bccMailMap.keySet()) {
				// Bcc: 密送（可选）
				message.setRecipient(MimeMessage.RecipientType.CC, new InternetAddress(bccMail, bccMailMap.get(bccMail), "UTF-8"));
			}
		}
		// 4. Subject: 邮件主题
		message.setSubject(title==null?DEFAULT_TITLE:title, "UTF-8");

		// 5. Content: 邮件正文（可以使用html标签）
		message.setContent(mailInfo, "text/html;charset=UTF-8");

		// 6. 设置显示的发件时间
		message.setSentDate(new Date());

		// 7. 保存前面的设置
		message.saveChanges();
		return message;
	}
	
	public static MimeMessage sendImageMail2MailServer(MimeMessage mimeMessage,String... imageFiles) throws MessagingException, UnsupportedEncodingException{
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "GB2312");  

        String imageStr = "img";
        StringBuffer html = new StringBuffer();  
        html.append("<html>");  
        html.append("<head>");  
        html.append("<meta http-equiv='Content-Type' content='multipart/alternative; charset=UTF-8'>");  
        html.append("</head>");  
        html.append("<body bgcolor='#ccccff'>");  
        html.append("<center>");  
        html.append("<h1>你好，Doncat</h1>");  
        Map<String,String> imageMap = new HashMap<String, String>();
        int imageId = 1;
        for (String imageFile : imageFiles) {
            String contentId = imageStr+imageId;
            imageMap.put(contentId,imageFile);  
            html.append("<p>"+contentId+":");  
            html.append("<img src='cid:"+contentId+"'>"); 
		}
        html.append("</center>");  
        html.append("</body>");  
        html.append("</html>");  
        helper.setText(html.toString(), true); 
        
        
        for(String keys : imageMap.keySet()){
            FileSystemResource imageResource = new FileSystemResource(new File(imageMap.get(keys)));  
            helper.addInline(keys,imageResource);
        } 
        //添加附件  
        File file = new File("D:/apps/house/img/fj.jpg");  
        helper.addAttachment("fj.jpg",file);  
           
        //添加中文名的，不做下面的处理会出现乱码的。  
        String filename = MimeUtility.encodeText(new String("中文标题附件.txt".getBytes(),"UTF-8"),"UTF-8","B");  
        File f = new File("D:/apps/house/img/中文标题附件.txt");  
        helper.addAttachment(filename,f);  
           
//        helper.setFrom("green006@163.com");  
//        helper.setTo("green006@163.com");  
//        helper.setSubject("spring javamail test");  
           
//        logger.info(mimeMessage.getContentID());  
//        logger.info(mimeMessage.getContent());  
           
          return mimeMessage;
//        mailsender.send(mimeMessage);  
//        logger.info("a mime mail with an attachment has bean sent !");  
	}
	
	private static void send2MailServer(Session session, MimeMessage message,String u,String p) throws MessagingException {

		// 4. 根据 Session 获取邮件传输对象
		Transport transport = session.getTransport();

		// 5. 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
		//
		// PS_01: 成败的判断关键在此一句, 如果连接服务器失败, 都会在控制台输出相应失败原因的 log,
		// 仔细查看失败原因, 有些邮箱服务器会返回错误码或查看错误类型的链接, 根据给出的错误
		// 类型到对应邮件服务器的帮助网站上查看具体失败原因。
		//
		// PS_02: 连接失败的原因通常为以下几点, 仔细检查代码:
		// (1) 邮箱没有开启 SMTP 服务;
		// (2) 邮箱密码错误, 例如某些邮箱开启了独立密码;
		// (3) 邮箱服务器要求必须要使用 SSL 安全连接;
		// (4) 请求过于频繁或其他原因, 被邮件服务器拒绝服务;
		// (5) 如果以上几点都确定无误, 到邮件服务器网站查找帮助。
		//
		// PS_03: 仔细看log, 认真看log, 看懂log, 错误原因都在log已说明。
		if(u==null){
			u = DEFAULT_MAIL_U;
			p = DEFAULT_MAIL_P;
		}
		transport.connect(u, p);

		// 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人,
		// 抄送人, 密送人
		transport.sendMessage(message, message.getAllRecipients());
		// 7. 关闭连接
		transport.close();
	}

}
