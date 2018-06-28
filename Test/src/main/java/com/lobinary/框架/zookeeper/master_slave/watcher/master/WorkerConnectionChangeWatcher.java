package com.lobinary.框架.zookeeper.master_slave.watcher.master;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lobinary.工具类.JU;
import com.lobinary.框架.zookeeper.master_slave.server.Server;

/**
 * 获取worker连接变化
 * @author bin.lv
 * @since create by bin.lv 2017年12月1日 下午6:25:43
 *
 */
public class WorkerConnectionChangeWatcher implements Watcher{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Server server;
    
    public WorkerConnectionChangeWatcher(Server server) {
        super();
        this.server = server;
    }

    @Override
    public void process(WatchedEvent event) {
        if(EventType.NodeChildrenChanged==event.getType()){
            logger.info("发现工作者发生变化"+JU.toJSONString(event));
            server.getMasterService().updateWorker();
        }else{
            logger.error("发现未知的工作者变化事件："+JU.toJSONString(event));
        }
    }

}
