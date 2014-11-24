package com.lobinary.platform.jobs;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lobinary.platform.constant.Constants;
import com.lobinary.platform.service.MessageService;

/**
 * 
 * 定时任务-消息扫描器-定时更新待发消息列表-防止数据异常
 * 
 * 	
 * 
 * @author lvbin 
 * @since 2014年11月21日 下午4:54:40
 * @version V1.0.0 Description : Create this file
 * 
 *         
 *
 */
@Component("messageScanner")
public class MessageScanner {
	
	private Logger logger = LoggerFactory.getLogger(MessageScanner.class);
	
	/**
	 * 待发消息列表中接收方的id
	 */
	private static Set<Long> messageReceiverIdSet = new HashSet<Long>();
	
	private MessageService messageService;
	
	/**
	 * 扫描信息表-定时任务
	 */
	public void scanMessage(){
		logger.info("消息扫描器[定时任务]-准备更新待发消息列表-任务开始");
		messageReceiverIdSet = messageService.getSendMessgeIdSetBySendStatus(Constants.SEND_STATUS_INITIAL);
		logger.info("消息扫描器[定时任务]-准备更新待发消息列表-任务结束");
	}

	public static Set<Long> getMessageReceiverIdSet() {
		return messageReceiverIdSet;
	}

	public static void setMessageReceiverIdSet(Set<Long> messageReceiverIdSet) {
		MessageScanner.messageReceiverIdSet = messageReceiverIdSet;
	}

	public MessageService getMessageService() {
		return messageService;
	}

	@Resource(name="messageService")
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}


}
