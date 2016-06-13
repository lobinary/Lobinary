package com.lobinary.platform.dao.hibernate;

import org.springframework.stereotype.Component;

import com.lobinary.platform.model.DatabaseParameter;
import com.lobinary.platform.model.db.ReceiverMessage;
import com.lobinary.platform.util.LogUtil;


/**
 * 
 * 信息接收器接受信息记录DAO层
 * 
 * @author lvbin 
 * @since 2014年11月24日 上午11:16:23
 * @version V1.0.0 Description : Create this file
 * 
 *         
 *
 */
@Component("receiverMessageDAO")
public class ReceiverMessageDAO extends BaseDAO {

	public void deleteMessageByID(long id) {
		try {
			delete(ReceiverMessage.class, new DatabaseParameter("id", id));
		} catch (Exception e) {
			LogUtil.logException(e);
		}
	}

}
