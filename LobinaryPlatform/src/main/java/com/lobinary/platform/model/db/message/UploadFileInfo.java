package com.lobinary.platform.model.db.message;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * 上传文件信息实体
 * 
 * @author lvbin 
 * @since 2014年11月21日 下午3:17:53
 * @version V1.0.0 Description : Create this file
 * 
 *         
 *
 */
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
