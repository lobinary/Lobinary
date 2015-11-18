package com.boce.test;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 
 * <pre>
 * 请求规则类
 * </pre>
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年3月24日 下午4:16:07
 * @version V1.0.0 描述 : 创建文件RequestRule
 * 
 *         
 *
 */
public class RequestRule implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7946835389430422112L;

	/**
	 * 请求URL
	 */
	private List<String> requestUrl;
	
	/**
	 * 是否对URL做匹配
	 */
	private boolean isMatchRequestUrl;
	
	/**
	 * 请求的参数
	 */
	private List<String> requestParam;
	
	/**
	 * 是否匹配请求参数
	 */
	private boolean isMatchRequestParam;
	
	/**
	 * 报文型匹配参数
	 */
	private String msgRequestParam;
	
	/**
	 * 是否匹配报文型匹配参数
	 */
	private boolean isMatchMsgRequestParam;
	
	/**
	 * 返回报文内容
	 */
	private String responseData;

	/**
	 * 装配参数-<参数名,参数抓取规则>
	 */
	private Map<String,String> assembleParamMap;

	public List<String> getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(List<String> requestUrl) {
		this.requestUrl = requestUrl;
	}

	public boolean isMatchRequestUrl() {
		return isMatchRequestUrl;
	}

	public void setMatchRequestUrl(boolean isMatchRequestUrl) {
		this.isMatchRequestUrl = isMatchRequestUrl;
	}

	public List<String> getRequestParam() {
		return requestParam;
	}

	public void setRequestParam(List<String> requestParam) {
		this.requestParam = requestParam;
	}

	public boolean isMatchRequestParam() {
		return isMatchRequestParam;
	}

	public void setMatchRequestParam(boolean isMatchRequestParam) {
		this.isMatchRequestParam = isMatchRequestParam;
	}

	public String getMsgRequestParam() {
		return msgRequestParam;
	}

	public void setMsgRequestParam(String msgRequestParam) {
		this.msgRequestParam = msgRequestParam;
	}

	public boolean isMatchMsgRequestParam() {
		return isMatchMsgRequestParam;
	}

	public void setMatchMsgRequestParam(boolean isMatchMsgRequestParam) {
		this.isMatchMsgRequestParam = isMatchMsgRequestParam;
	}

	public String getResponseData() {
		return responseData;
	}

	public void setResponseData(String responseData) {
		this.responseData = responseData;
	}

	public Map<String, String> getAssembleParamMap() {
		return assembleParamMap;
	}

	public void setAssembleParamMap(Map<String, String> assembleParamMap) {
		this.assembleParamMap = assembleParamMap;
	}

}
