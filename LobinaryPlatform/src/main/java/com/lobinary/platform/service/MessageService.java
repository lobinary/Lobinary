package com.lobinary.platform.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lobinary.platform.dao.MessageDAO;
import com.lobinary.platform.model.db.InteractionMessage;

/**
 * 
 * 交互消息业务类
 * 
 * @author lvbin 
 * @since 2014年11月21日 下午5:28:33
 * @version V1.0.0 Description : Create this file
 * 
 *         
 *
 */
@Service("messageService")
public class MessageService {
	
	private MessageDAO messageDAO;
	
	public Set<Long> getSendMessgeIdSetBySendStatus(int sendStatus){
		List<Long> readySendMessageIdList = messageDAO.getSendMessgeIdListBySendStatus(sendStatus);
		Set<Long> resultSet = new HashSet<Long>();
		for(Long id : readySendMessageIdList){
			resultSet.add(id);
		}
		return resultSet;
	}
	
	public boolean add(InteractionMessage interactionMessage){
		return messageDAO.add(interactionMessage);
	}

	public MessageDAO getMessageDAO() {
		return messageDAO;
	}

	@Resource(name="messageDAO")
	public void setMessageDAO(MessageDAO messageDAO) {
		this.messageDAO = messageDAO;
	}

}
