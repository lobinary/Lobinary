package com.lobinary.设计模式.服务定位器模式;

import java.util.HashMap;
import java.util.Map;

import com.lobinary.设计模式.服务定位器模式.service.Service;

public class Cache {
	
	Map<String,Service> serviceMap = new HashMap<String,Service>();
	
	public Service getService(String serviceName){
		return serviceMap.get(serviceName.toUpperCase());
	}
	
	public void addService(Service service){
		serviceMap.put(service.getName().toUpperCase(), service);
	}

}
