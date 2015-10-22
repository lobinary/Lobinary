package com.lobinary.android.common.pojo.communication;

import java.io.Serializable;
import java.util.Map;

/**
 * 
 *	<pre> 
 *	命令对象实体 包含指令相关信息
 * 	</pre>
 * @author lobinary 
 * @since 2015年10月16日 下午10:54:57
 * @version V1.0.0 描述 : 创建该文件
 *
 */
public class Command implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3761390861627858311L;

	/**
	 * 指令编码
	 */
	private String commandCode;
	
	/**
	 * 指令描述
	 */
	private String commandDesc;
	
	/**
	 * 远程方法名称
	 */
	private String remoteMethodName;
	
	/**
	 * 命令内容Map
	 */
	private Map<String,String> commandContentMap;
	
	/**
	 * 参数对象
	 */
	private CommandParam commandParam;
	
	/**
	 * 延迟执行时间 单位 ： 毫秒
	 */
	private long delayTime;
	
	/**
	 * 授权码
	 */
	private String grantCode;

	public String getCommandCode() {
		return commandCode;
	}

	public void setCommandCode(String commandCode) {
		this.commandCode = commandCode;
	}

	public String getCommandDesc() {
		return commandDesc;
	}

	public void setCommandDesc(String commandDesc) {
		this.commandDesc = commandDesc;
	}

	public long getDelayTime() {
		return delayTime;
	}

	public void setDelayTime(long delayTime) {
		this.delayTime = delayTime;
	}

	public String getGrantCode() {
		return grantCode;
	}

	public void setGrantCode(String grantCode) {
		this.grantCode = grantCode;
	}

	public Map<String, String> getCommandContentMap() {
		return commandContentMap;
	}

	public void setCommandContentMap(Map<String, String> commandContentMap) {
		this.commandContentMap = commandContentMap;
	}

	public String getRemoteMethodName() {
		return remoteMethodName;
	}

	public void setRemoteMethodName(String remoteMethodName) {
		this.remoteMethodName = remoteMethodName;
	}

	public CommandParam getCommandParam() {
		return commandParam;
	}

	public void setCommandParam(CommandParam commandParam) {
		this.commandParam = commandParam;
	}

}
