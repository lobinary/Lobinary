package com.lobinary.设计模式.拦截过滤器模式.filter.impl;

import com.lobinary.设计模式.拦截过滤器模式.filter.Filter;


public class EncodingFilter implements Filter{

	@Override
	public void doFilter(String request) {
		System.out.println("网址"+request+"统一解码完毕");
	}

}
