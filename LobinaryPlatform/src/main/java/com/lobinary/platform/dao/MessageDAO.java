package com.lobinary.platform.dao;

import org.springframework.stereotype.Component;

import com.lobinary.platform.model.DatabaseParameter;
import com.lobinary.platform.model.Message;
import com.lobinary.platform.util.LogUtil;


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

	public void deleteMessageByID(int id) {
		try {
			delete(Message.class, new DatabaseParameter("id", id));
		} catch (Exception e) {
			LogUtil.logException(e);
		}
	}

}
