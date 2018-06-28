package com.lobinary.框架.zookeeper.分布式过程协同技术详解.chapter3;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

public class LWatcher implements Watcher{

    @Override
    public void process(WatchedEvent event) {
        System.out.println("LWATCHER 收到事件 :" + event.toString());
    }

}
