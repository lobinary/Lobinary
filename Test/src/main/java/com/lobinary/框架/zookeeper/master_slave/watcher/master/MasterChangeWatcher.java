package com.lobinary.框架.zookeeper.master_slave.watcher.master;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lobinary.框架.zookeeper.master_slave.server.Server;

/**
 * 获取Master数据的监听
 * @author bin.lv
 * @since create by bin.lv 2017年12月1日 下午3:30:13
 *
 */
public class MasterChangeWatcher implements Watcher{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Server server;
    
    public MasterChangeWatcher(Server server) {
        super();
        this.server = server;
    }

    @Override
    public void process(WatchedEvent event) {
        logger.info(server+this.getClass().getSimpleName()+"发现事件["+event+"]");
        if(EventType.NodeDeleted==event.getType()){
            server.getMasterService().electMaster();
        }else if(EventType.NodeCreated==event.getType()){
            server.getMasterService().checkMaster();
        }else{
            logger.info(server+this.getClass().getSimpleName()+"发现未捕获的事件类型["+event+"]");
        }
    }

}
