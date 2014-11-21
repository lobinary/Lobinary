package com.lobinary.platform.model.db;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * 交互信息实体
 * 
 * @author lvbin 
 * @since 2014年11月21日 下午3:17:46
 * @version V1.0.0 Description : Create this file
 * 
 *         
 *
 */
@Entity
@Table(name="INTERACTION_MESSAGE_INFO")
public class InteractionMessage implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -870675983052908611L;
	
	/**
	 * ID
	 */
	private int id;
	/**
	 * 发送方ID
	 */
	private long senderId;
	/**
	 * 接收方ID
	 */
	private long receiverId;
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
	
	/**
	 * 接收时间
	 */
	private Date receiveDate;
	
	/**
	 * 状态   0未发送  1已发送 -1发送失败
	 */
	private int status;
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getSenderId() {
		return senderId;
	}

	public void setSenderId(long senderId) {
		this.senderId = senderId;
	}

	public long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(long receiverId) {
		this.receiverId = receiverId;
	}

	public int getMessageType() {
		return messageType;
	}

	public void setMessageType(int messageType) {
		this.messageType = messageType;
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

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
