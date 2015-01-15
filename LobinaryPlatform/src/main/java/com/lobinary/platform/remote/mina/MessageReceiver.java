package com.lobinary.platform.remote.mina;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import javax.annotation.Resource;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;

import com.lobinary.platform.service.MessageService;
import com.lobinary.platform.util.LogUtil;
import com.lobinary.platform.util.PropertiesUtil;

/**
 * 
 * 交互信息接收系统NIO-MINA框架接收器
 * 
 * @author lvbin
 * @since 2014年11月21日 下午3:49:43
 * @version V1.0.0 Description : Create this file
 * 
 * 
 * 
 */
public class MessageReceiver {

	public Logger logger = LogUtil.getLog(MessageReceiver.class);
	private MessageService messageService;

	public boolean receiverMessage() {
		try { 
			// ......负责信息收集并交给service层继续处理
			// ......启动服务器自动启动
			int port = Integer.parseInt(PropertiesUtil.getValue("message_receiver_mina_tcp_port"));
			NioSocketAcceptor acceptor = new NioSocketAcceptor();
			DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
	//		MdcInjectionFilter mdcInjectionFilter = new MdcInjectionFilter();
	//		chain.addLast("mdc", mdcInjectionFilter);
			// Add SSL filter if SSL is enabled.
	//		if (USE_SSL) {
	//			addSSLSupport(chain);
	//		}
			//配置CodecFactory 
			acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8")))); 
	//		LoggingFilter log = new LoggingFilter();         
	//		log.setMessageReceivedLogLevel(LogLevel.INFO);
	//		acceptor.getFilterChain().addLast("logger", log);
			acceptor.setHandler(new MessageReceiverHandler());
			//配置handler         
			acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 30);
			chain.addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory()));
			// Bind acceptor.setHandler(new ChatProtocolHandler());
			acceptor.bind(new InetSocketAddress(port));
			logger.info("");
			messageService.add(null);
			return true;         
		}catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {             
			e.printStackTrace();             
			return false;
		} 
	}

	public MessageService getMessageService() {
		return messageService;
	}

	@Resource(name = "messageService")
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

}
