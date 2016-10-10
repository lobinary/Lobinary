package com.lobinary.java.jdk.jdk16.jmx.一个没明白的程序;

public class ServerMonitor implements ServerMonitorMBean { 

    private final ServerImpl target; 
    public ServerMonitor(ServerImpl target){ 
        this.target = target; 
    } 
    
    @Override
    public long getUpTime(){ 
        return System.currentTimeMillis() - target.startTime; 
    } 
 }