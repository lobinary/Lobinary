package com.lobinary.android.platform.pojo.bean;

import java.io.Serializable;

public class ButtonListenerBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 884226475239009750L;
	
	public String text;
	public String logoPath;
	public String methodName;
	
	public ButtonListenerBean(String text, String logoPath, String methodName) {
		super();
		this.text = text;
		this.logoPath = logoPath;
		this.methodName = methodName;
	}

}