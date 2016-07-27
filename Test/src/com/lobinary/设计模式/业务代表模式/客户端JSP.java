package com.lobinary.设计模式.业务代表模式;

public class 客户端JSP {
	
	private 业务代表 业务代表;
	
	public 客户端JSP(com.lobinary.设计模式.业务代表模式.业务代表 业务代表) {
		super();
		this.业务代表 = 业务代表;
	}

	public void 执行业务(){
		业务代表.执行业务();
	}

}
