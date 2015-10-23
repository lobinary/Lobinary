package com.lobinary.android.common.pojo.communication;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 *	<pre> 
 *	报文标题
 * 	</pre>
 * @author lobinary 
 * @since 2015年10月16日 下午10:38:02
 * @version V1.0.0 描述 : 创建该文件
 *
 */
public class MessageTitle implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4973819743094088838L;

	/**
	 * 版本
	 */
	private String version = "1.0.0";
	
	/**
	 * 发送客户端ID
	 */
	private String sendClientId = "APCTest0001";

	/**
	 * 发送客户端名称
	 */
	private String sendClientName = "APCTestClientName";
	
	/**
	 * 发送客户端IP
	 */
	private String sendClientIp = "127.0.0.1";
	
	/**
	 * 发送时间
	 */
	private Date sendTime;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSendClientId() {
		return sendClientId;
	}

	public void setSendClientId(String sendClientId) {
		this.sendClientId = sendClientId;
	}

	public String getSendClientName() {
		return sendClientName;
	}

	public void setSendClientName(String sendClientName) {
		this.sendClientName = sendClientName;
	}

	public String getSendClientIp() {
		return sendClientIp;
	}

	public void setSendClientIp(String sendClientIp) {
		this.sendClientIp = sendClientIp;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	
	
}
