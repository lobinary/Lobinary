package com.boce.test.设计模式.服务定位器模式;

import com.boce.test.设计模式.服务定位器模式.service.Service;
import com.boce.test.设计模式.服务定位器模式.service.impl.ServiceA;
import com.boce.test.设计模式.服务定位器模式.service.impl.ServiceB;

public class InitialContext {
	
	public Service getService(String serviceName){
		if(serviceName.equalsIgnoreCase("SERVICEA")){
			return new ServiceA();
		}else if(serviceName.equalsIgnoreCase("SERVICEB")){
			return new ServiceB();
		}else{
			return null;
		}
	}

}
