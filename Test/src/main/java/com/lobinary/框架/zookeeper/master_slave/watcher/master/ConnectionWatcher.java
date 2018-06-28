package com.lobinary.框架.zookeeper.master_slave.watcher.master;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 连接监听
 * @author bin.lv
 * @since create by bin.lv 2017年12月1日 下午2:52:14
 *
 */
public class ConnectionWatcher implements Watcher{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void process(WatchedEvent event) {
        logger.info("ZK连接事件监听器："+event);
    }

}
