package com.lobinary.platform.dao;

import java.util.List;

import org.springframework.stereotype.Component;


/**
 * 信息处理DAO
 * 
 * @author Lobianry
 * @version v2.0
 * @since 2014年10月11日13:40:08
 *
 */
@Component("messageDAO")
public class MessageDAO extends BaseDAO {
	
	public List<Long> getSendMessgeIdListBySendStatus(int sendStatus){
		@SuppressWarnings("unchecked")
		List<Long> list = (List<Long>) hibernateTemplate.find("select message.receiverId from InteractionMessage message where message.sendStatus=?", sendStatus);
		return list;
	}
	
}
