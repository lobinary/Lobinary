package com.lobinary.框架.zookeeper.master_slave.watcher.worker;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lobinary.工具类.JU;
import com.lobinary.框架.zookeeper.master_slave.server.Server;

/**
 * 获取assign task数据的监听
 * @author bin.lv
 * @since create by bin.lv 2017年12月1日 下午3:30:13
 *
 */
public class WaitTaskWatcher implements Watcher{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Server server;
    
    public WaitTaskWatcher(Server server) {
        super();
        this.server = server;
    }

    @Override
    public void process(WatchedEvent event) {
        if(EventType.NodeDataChanged==event.getType()){
            logger.info("任务监听器发现任务数据被更改,准备更新任务"+JU.toJSONString(event));
            server.getWorkerService().updateTask();
        }else{
            logger.info(server+this.getClass().getSimpleName()+"发现未捕获的事件类型["+event+"]");
        }
    }

}
