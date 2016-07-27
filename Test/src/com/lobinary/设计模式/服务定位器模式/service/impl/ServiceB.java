package com.lobinary.设计模式.服务定位器模式.service.impl;

import com.lobinary.设计模式.服务定位器模式.service.Service;

public class ServiceB implements Service {

	@Override
	public void execute() {
		System.out.println("ServiceB executing");
	}

	@Override
	public String getName() {
		return "ServiceB";
	}

}
