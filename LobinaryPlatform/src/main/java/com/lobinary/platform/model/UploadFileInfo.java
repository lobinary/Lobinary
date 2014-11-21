package com.lobinary.platform.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="RECEIVER_UPLOAD_FILE_INFO")
public class UploadFileInfo implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3309298344570369971L;
	
	/**
	 * ID
	 */
	private int id;
	/**
	 * 发送方
	 */
	private String sender;
	/**
	 * 接收方
	 */
	private String receiver;
	/**
	 * 文件种类
	 */
	private String fileType;
	/**
	 * 文件位置
	 */
	private String fileLocation;
	/**
	 * 发送时间
	 */
	private Date sendDate;
	
	

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

}
