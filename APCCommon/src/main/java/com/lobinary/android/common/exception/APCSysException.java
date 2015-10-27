package com.lobinary.android.common.exception;

import com.lobinary.android.common.constants.CodeDescConstants;

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
	
	private String errCode;//内部错误码
	private String errMessage;//内部错误描述
	private String errDesc;//错误描述
	private Throwable orignalException;//原始异常

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
		this.errMessage = msg.getDesc();
		this.errDesc = descRemark;
	}
	
	public APCSysException(CodeDescConstants msg,String descRemark, Throwable e) {
		super(e);
		this.orignalException = e;
		this.errCode = msg.getCode();
		this.errMessage = msg.getDesc();
		this.errDesc = descRemark;
	}
	
	public APCSysException(String _errCode, String _errMessage, Throwable e) {
		super(e);
		this.orignalException = e;
		this.errCode = _errCode;
		this.errMessage = _errMessage;
	}

	public APCSysException(CodeDescConstants msg, Throwable e) {
		super(e);
		this.errCode = msg.getCode();
		this.errMessage = msg.getDesc();
		this.orignalException = e;
	}
	
	public String getErrCode() {
		return this.errCode;
	}

	@Override
	public String getMessage() {
		return this.errMessage;
	}
	
	public String toString() {
		return  "错误编码[" + this.errCode + "],错误信息[" + this.errMessage + "],错误描述[" + this.errDesc + "]," +
				(this.orignalException==null?"":"java异常["+this.orignalException.getMessage())+"]";
	}

	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.android.common.exception.APCSysException#orignalException
	 * @return the orignalException
	 */
	public Throwable getOrignalException() {
		return orignalException;
	}

	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.android.common.exception.APCSysException#orignalException
	 * @param orignalException the orignalException to set
	 */
	public void setOrignalException(Throwable orignalException) {
		this.orignalException = orignalException;
	}

	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.android.common.exception.APCSysException#errMessage
	 * @return the errMessage
	 */
	public String getErrMessage() {
		return errMessage;
	}

	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.android.common.exception.APCSysException#errMessage
	 * @param errMessage the errMessage to set
	 */
	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}

	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.android.common.exception.APCSysException#errDesc
	 * @return the errDesc
	 */
	public String getErrDesc() {
		return errDesc;
	}

	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.android.common.exception.APCSysException#errDesc
	 * @param errDesc the errDesc to set
	 */
	public void setErrDesc(String errDesc) {
		this.errDesc = errDesc;
	}

	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.android.common.exception.APCSysException#errCode
	 * @param errCode the errCode to set
	 */
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

}
