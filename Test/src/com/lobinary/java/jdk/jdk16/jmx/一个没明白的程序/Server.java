package com.lobinary.java.jdk.jdk16.jmx.一个没明白的程序;


public class Server { 

	 private long startTime; 
	
	 public Server() { 	 } 
	
	 public int start(){ 
		 startTime = System.currentTimeMillis(); 
		 return 0; 
	 } 
	
	 public long getUpTime(){ 
		 return System.currentTimeMillis() - startTime; 
	 } 
}