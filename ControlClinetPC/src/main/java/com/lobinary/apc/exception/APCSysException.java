package com.lobinary.apc.exception;

import com.lobinary.apc.constants.CodeDescConstants;

/**
 * 
* 项目名称：FAPCommon  
* 类名称：FapSysException   
* 类描述：系统异常类
* 创建人：范世晔   
* 创建时间：2014-12-11 下午4:23:43      
* @version    
*
 */
public class APCSysException extends RuntimeException {

	private static final long serialVersionUID = 4469004050656405136L;
	private String errCode = "";
	private String errMessage = "";

	public APCSysException(String _errCode, String _errMessage) {
		this.errCode = _errCode;
		this.errMessage = _errMessage;
	}

	public APCSysException(CodeDescConstants msg) {
		this.errCode = msg.getCode();
		this.errMessage = msg.getDesc();
	}
	
	public APCSysException(CodeDescConstants msg,String descRemark) {
		this.errCode = msg.getCode();
		this.errMessage = msg.getDesc() + "[" + descRemark + "]";
	}

	public APCSysException(String _errCode, String _errMessage, Throwable e) {
		super(e);
		this.errCode = _errCode;
		this.errMessage = _errMessage;
	}

	public APCSysException(CodeDescConstants msg, Throwable e) {
		super(e);
		this.errCode = msg.getCode();
		this.errMessage = msg.getDesc();
	}
	
	public String getErrCode() {
		return this.errCode;
	}

	@Override
	public String getMessage() {
		return this.errMessage;
	}
	
	public String toString() {
		return  "[" + this.errCode + "]" + this.errMessage;
	}

}
