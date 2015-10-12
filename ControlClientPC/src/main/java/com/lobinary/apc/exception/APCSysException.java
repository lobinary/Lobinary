package com.lobinary.apc.exception;

import com.lobinary.apc.constants.CodeDescConstants;

/**
 * 
 * <pre>
 * 异常类
 * </pre>
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年10月12日 下午1:02:52
 * @version V1.0.0 描述 : 创建文件APCSysException
 * 
 *         
 *
 */
public class APCSysException extends RuntimeException {

	private static final long serialVersionUID = 4469004050656405136L;
	private String errCode;
	private String errMessage;

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
