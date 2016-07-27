package com.lobinary.设计模式.拦截过滤器模式.filter;


public class FilterManager {
	
	FilterChain filterChain = new FilterChain();
	
	public void setFilter(Filter filter){
		filterChain.addFilter(filter);
	}
	
	public void execute(String request){
		filterChain.execute(request);
	}

}
