package com.boce.model;

import com.boce.util.RecFileUtil;

public class RecFileModel {
	
	
	private String type;
	private boolean isValid;
	private String orderNo;
	private String serialNo;
	private int amount;//单位分
	
	/**
	 * type为 pay或者refund
	 * @param type
	 * @param orderNo
	 * @param serialNo
	 * @param amount
	 */
	public RecFileModel(String type, boolean isValid,String orderNo, String serialNo, int amount) {
		super();
		this.type = type;
		this.isValid = isValid;
		this.orderNo = orderNo.trim();
		this.serialNo = serialNo.trim();
		this.amount = amount;
		RecFileUtil.outDataLog(type,isValid,orderNo,serialNo,amount);//输出日志
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	
}
