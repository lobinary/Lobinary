package com.lobinary.框架.zookeeper.master_slave.test;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import com.lobinary.工具类.JU;
import com.lobinary.框架.zookeeper.master_slave.Start;

public class TestWatcher implements Watcher{
    
    ZooKeeper client;
    public TestWatcher(ZooKeeper client) {
        super();
        this.client = client;
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println(JU.toJSONString(event));
        Start.getData(client);
    }
    

}
