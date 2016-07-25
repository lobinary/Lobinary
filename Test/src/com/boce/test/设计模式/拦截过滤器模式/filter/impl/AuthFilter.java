package com.boce.test.设计模式.拦截过滤器模式.filter.impl;

import com.boce.test.设计模式.拦截过滤器模式.filter.Filter;


public class AuthFilter implements Filter{

	@Override
	public void doFilter(String request) {
		System.out.println("网址"+request+"授权成功");
	}

}
