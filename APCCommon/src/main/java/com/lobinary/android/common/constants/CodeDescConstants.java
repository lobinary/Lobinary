package com.lobinary.android.common.constants;

/**
 * 
 *	<pre> 
 *	状态码常量类
 *
 *	状态码分配规则：
 *	123456 状态码长度为6位
 *	1代表所属类别
 *	23代表所属子类别 [00为公共 ]
 *	4代表状态码属性    [1.状态型 4.异常型]
 *	56代表子变量
 *
 * 	</pre>
 * @author lobinary 
 * @since 2015年10月11日 下午10:16:54
 * @version V1.0.0 描述 : 创建该文件
 *
 */
public enum CodeDescConstants {

	//1***** 公共状态码

	//1004** 公用公共状态码
	/** "100400","系统异常" */
	PUB_SYSTEM_EXCEPTION("100400","系统异常"),

	//1011** 公用状态码
	/** "101100","处理中" */
	PUB_DEALING("101100","处理中"),
	/** "101101","处理成功" */
	PUB_DEAL_SUCCESS("101101","处理成功"),
	/** "101102","处理失败" */
	PUB_DEAL_FAIL("101102","处理失败"), 
	/** "101103","提交成功" */
	PUB_SUBMIT_SUCCESS("101103","提交成功"), 
	/** "101104","提交失败" */
	PUB_SUBMIT_FAIL("101104","提交失败"), 
	/** "101105","上传成功" */
	PUB_UPLOAD_SUCCESS("101105","上传成功"), 
	/** "101106","上传失败" */
	PUB_UPLOAD_FAIL("101106","上传失败"), 
	
	//2***** 业务型状态码

	//2001** 业务公共 状态码
	/** "200101","业务执行成功" */
	SERVICE_EXECUTE_SUCCESS("200101","业务执行成功"),
	
	//2004** 业务公共 异常码
	/** "200401","无此业务" */
	SERVICE_NULL_BUSINESS("200401","无此业务"),

	//2014** 接受报文 异常码
	/** "201401","错误报文类型" */
	SERVICE_MESSAGE_ERROR_MESSAGE_TYPE("201401","错误报文类型"),
	/** "201402","错误报文字符串" */
	SERVICE_MESSAGE_ERROR_MESSAGE_STRING("201402","错误报文字符串"),
	
	//2024** 发送报文 异常码
	/** "202401","发送报文失败" */
	SERVICE_MESSAGE_SEND_FAIL("202401","发送报文失败"),
	
	//3***** 远程信息交互状态码
	
	//4***** 数据库状态码
	
	//5***** 
	
	//9***** 工具类状态码
	
	//901*** 文件类状态码
	/** "901400","文件工具类异常" */
	FILE_EXCEPTION("901400","文件工具类异常"),
	
	//90140* ~ 90144*	找文件型异常
	/** "901401","读取文件内容发生未知错误" */
	FILE_READ_EXCEPTION("901401","读取文件内容发生未知错误"),
	/** "901402","文件未找到" */
	FILE_READ_UNFOUNT("901402","文件未找到"),
	
	//90145* ~ 90149* 写文件型异常
	
	/** "901450","写入文件内容发生未知错误" */
	FILE_WRITE_EXCEPTION("901450","写入文件内容发生未知错误"),
	
	//902*** 日期类状态码
	
	/** "902400","日期工具类异常" */
	DATE_EXCEPTION("902400","日期工具类异常"),
	
	//903*** 字符串类状态码
	
	//904*** 数字类状态码
	
	//905*** 网络类状态码

	/** "905400","网络工具类异常" */
	NET_EXCEPTION("905400","网络工具类异常"),
	
	/** "905401","获取本地IP异常" */
	NET_GET_LOCAL_IP_EXCEPTION("905401","获取本地IP异常"),
	NET_GET_LOCAL_LAN_IP_EXCEPTION("905401","获取本地局域网IP异常")
	
	;
	

	private String code;  //响应码
	private String desc;   //响应描述
	private CodeDescConstants(String code,String desc){
		this.code=code;
		this.desc=desc;
	}
	public String getCode(){
		return code;
	}
	public String getDesc(){
		return desc;
	}

}
