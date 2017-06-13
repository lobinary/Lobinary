package com.lobinary.工具类;


import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
public class AttchAndImgMail{

	private static String DEFAULT_MAIL_S = "smtp.qq.com";
	
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
	
	public static void sendEmail(String title,String mailInfo,String imageStr,String attachmentFile) throws AddressException, MessagingException, UnsupportedEncodingException{
        /*
     * 在 JavaMail 中，可以通过 extends Authenticator 抽象类，在子类中覆盖父类中的 getPasswordAuthentication() 
     * 方法，就可以实现以不同的方式来进行登录邮箱时的用户身份认证。JavaMail 中的这种设计是使用了策略模式（Strategy
     * */ 
       MimeMessage message = new MimeMessage(Session.getInstance(getDefaultProps(),new Authenticator() {
              public PasswordAuthentication getPasswordAuthentication() {
                  return new PasswordAuthentication("936231734@qq.com", "mfpmhndcfgeybaig");
                 }
               }));
       //设置邮件的属性
       //设置邮件的发件人
       message.setFrom(new InternetAddress("936231734@qq.com"));
       //设置邮件的收件人   cc表示抄送   bcc 表示暗送
       message.setRecipient(Message.RecipientType.TO, new InternetAddress("919515134@qq.com"));
       message.setRecipient(Message.RecipientType.CC, new InternetAddress("ljrxxx@aliyun.com"));
       //设置邮件的主题
       message.setSubject(title);      
       //创建邮件的正文
       MimeBodyPart text = new MimeBodyPart();
       // setContent(“邮件的正文内容”,”设置邮件内容的编码方式”)
       
       text.setContent(mailInfo.replace("{image}", "<img src='cid:a'>"),"text/html;charset=gb2312");
       if(imageStr!=null){
           //创建图片
           MimeBodyPart img = new MimeBodyPart();
           
	       /*JavaMail API不限制信息只为文本,任何形式的信息都可能作茧自缚MimeMessage的一部分.
	        * 除了文本信息,作为文件附件包含在电子邮件信息的一部分是很普遍的.
	        * JavaMail API通过使用DataHandler对象,提供一个允许我们包含非文本BodyPart对象的简便方法.*/
	       DataHandler dh = new DataHandler(new FileDataSource(imageStr));
	       img.setDataHandler(dh);
	       //创建图片的一个表示用于显示在邮件中显示
	       img.setContentID("a");
	      
	       //关系   正文和图片的
	       MimeMultipart mm = new MimeMultipart();
	       mm.addBodyPart(text);
	       mm.addBodyPart(img);
	       mm.setSubType("related");//设置正文与图片之间的关系
	       //图班与正文的 body
	       MimeBodyPart all = new MimeBodyPart();
	       all.setContent(mm);
	       //附件与正文（text 和 img）的关系
	       MimeMultipart mm2 = new MimeMultipart();
	       mm2.addBodyPart(all);
	       mm2.setSubType("mixed");//设置正文与附件之间的关系
	       if(attachmentFile!=null){
	           //创建附件
	           MimeBodyPart attch = new MimeBodyPart();
	           DataHandler dh1 = new DataHandler(new FileDataSource(attachmentFile));
	           attch.setDataHandler(dh1);
	           String filename1 = dh1.getName();
	            // MimeUtility 是一个工具类，encodeText（）用于处理附件字，防止中文乱码问题
	           attch.setFileName(MimeUtility.encodeText(filename1));
	           mm2.addBodyPart(attch);
	       }
	       
	       message.setContent(mm2);
       }
       
       message.saveChanges(); //保存修改
       Transport.send(message);//发送邮件
      
    
		
	}
    //JavaMail需要Properties来创建一个session对象。它将寻找字符串"mail.smtp.host"，属性值就是发送邮件的主机.
    public static void main(String[] args) throws Exception{
        /*
     * 在 JavaMail 中，可以通过 extends Authenticator 抽象类，在子类中覆盖父类中的 getPasswordAuthentication() 
     * 方法，就可以实现以不同的方式来进行登录邮箱时的用户身份认证。JavaMail 中的这种设计是使用了策略模式（Strategy
     * */ 
       MimeMessage message = new MimeMessage(Session.getInstance(getDefaultProps(),new Authenticator() {
              public PasswordAuthentication getPasswordAuthentication() {
                  return new PasswordAuthentication("936231734@qq.com", "mfpmhndcfgeybaig");
                 }
               }));
       //设置邮件的属性
       //设置邮件的发件人
       message.setFrom(new InternetAddress("936231734@qq.com"));
       //设置邮件的收件人   cc表示抄送   bcc 表示暗送
       message.setRecipient(Message.RecipientType.TO, new InternetAddress("919515134@qq.com"));
       //设置邮件的主题
       message.setSubject("世界上最复杂的邮件有附件和图片");      
       //创建邮件的正文
       MimeBodyPart text = new MimeBodyPart();
       // setContent(“邮件的正文内容”,”设置邮件内容的编码方式”)
       text.setContent("世界上最复杂的邮件<img src='cid:a'>","text/html;charset=gb2312");
       //创建图片
       MimeBodyPart img = new MimeBodyPart();
       /*JavaMail API不限制信息只为文本,任何形式的信息都可能作茧自缚MimeMessage的一部分.
        * 除了文本信息,作为文件附件包含在电子邮件信息的一部分是很普遍的.
        * JavaMail API通过使用DataHandler对象,提供一个允许我们包含非文本BodyPart对象的简便方法.*/
       DataHandler dh = new DataHandler(new FileDataSource("D:/apps/house/img/2.jpg"));
       img.setDataHandler(dh);
       //创建图片的一个表示用于显示在邮件中显示
       img.setContentID("a");
      
       //创建附件
       MimeBodyPart attch = new MimeBodyPart();
       DataHandler dh1 = new DataHandler(new FileDataSource("D:/apps/house/img/fj.jpg"));
       attch.setDataHandler(dh1);
       String filename1 = dh1.getName();
        // MimeUtility 是一个工具类，encodeText（）用于处理附件字，防止中文乱码问题
       attch.setFileName(MimeUtility.encodeText(filename1));
       //关系   正文和图片的
       MimeMultipart mm = new MimeMultipart();
       mm.addBodyPart(text);
       mm.addBodyPart(img);
       mm.setSubType("related");//设置正文与图片之间的关系
       //图班与正文的 body
       MimeBodyPart all = new MimeBodyPart();
       all.setContent(mm);
       //附件与正文（text 和 img）的关系
       MimeMultipart mm2 = new MimeMultipart();
       mm2.addBodyPart(all);
       mm2.addBodyPart(attch);
       mm2.setSubType("mixed");//设置正文与附件之间的关系
      
       message.setContent(mm2);
       message.saveChanges(); //保存修改
       Transport.send(message);//发送邮件
      
    }
}

