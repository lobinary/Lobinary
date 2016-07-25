package com.boce.test.设计模式.拦截过滤器模式.filter;

import java.util.ArrayList;
import java.util.List;

public class FilterChain {
	
	List<Filter> filterChain = new ArrayList<Filter>();
	TargetJSP targetJSP = new TargetJSP(); 
	
	public void addFilter(Filter filter){
		filterChain.add(filter);
	}
	
	public void execute(String request){
		for (Filter filter : filterChain) {
			filter.doFilter(request);
		}
		targetJSP.show(request);
	}

}
