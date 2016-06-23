package com.lobinary.platform.model.db.message;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * 接受信息实体类
 * 
 * @author lvbin 
 * @since 2014年11月21日 下午3:20:23
 * @version V1.0.0 Description : Create this file
 * 
 *         
 *
 */
@Entity
@Table(name="RECEIVER_MESSAGE_INFO")
public class ReceiverMessage implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -870675983052908611L;
	
	/**
	 * ID
	 */
	private long id;
	/**
	 * 发送方
	 */
	private String sender;
	/**
	 * 接收方
	 */
	private String receiver;
	/**
	 * 消息种类
	 */
	private int messageType;
	/**
	 * 信息内容
	 */
	private String messageInfo;
	/**
	 * 发送时间
	 */
	private Date sendDate;
	
	

	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getMessageInfo() {
		return messageInfo;
	}

	public void setMessageInfo(String messageInfo) {
		this.messageInfo = messageInfo;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public int getMessageType() {
		return messageType;
	}

	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

}
