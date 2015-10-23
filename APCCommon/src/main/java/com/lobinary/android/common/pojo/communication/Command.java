package com.lobinary.android.common.pojo.communication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
	 * 参数对象
	 */
	private CommandParam commandParam;
	
	/**
	 * 远程方法参数
	 */
	private List<Object> remoteMethodParam;
	
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

	public void setRemoteMethodName(String remoteMethodName) {
		this.remoteMethodName = remoteMethodName;
	}

	public CommandParam getCommandParam() {
		return commandParam;
	}

	public void setCommandParam(CommandParam commandParam) {
		this.commandParam = commandParam;
	}

	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.android.common.pojo.communication.Command#remoteMethodName
	 * @return the remoteMethodName
	 */
	public String getRemoteMethodName() {
		return remoteMethodName;
	}

	/**
	 * <pre>
	 * 向远程方法加入参数列表加入参数中
	 * </pre>
	 *
	 * @param player
	 */
	public Command add(String param) {
		if(remoteMethodParam==null){
			remoteMethodParam = new ArrayList<Object>();
		}
		remoteMethodParam.add(param);
		return this;
	}

	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.android.common.pojo.communication.Command#remoteMethodParam
	 * @return the remoteMethodParam
	 */
	public List<Object> getRemoteMethodParam() {
		return remoteMethodParam;
	}

	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.android.common.pojo.communication.Command#remoteMethodParam
	 * @param remoteMethodParam the remoteMethodParam to set
	 */
	public void setRemoteMethodParam(List<Object> remoteMethodParam) {
		this.remoteMethodParam = remoteMethodParam;
	}

}
