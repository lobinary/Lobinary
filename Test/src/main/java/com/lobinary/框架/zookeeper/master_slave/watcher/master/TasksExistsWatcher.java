package com.lobinary.框架.zookeeper.master_slave.watcher.master;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lobinary.工具类.JU;
import com.lobinary.框架.zookeeper.master_slave.server.Server;

/**
 * 获取Master数据的监听
 * @author bin.lv
 * @since create by bin.lv 2017年12月1日 下午3:30:13
 *
 */
public class TasksExistsWatcher implements Watcher{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Server server;
    
    public TasksExistsWatcher(Server server) {
        super();
        this.server = server;
    }

    @Override
    public void process(WatchedEvent event) {
        if(EventType.NodeCreated==event.getType()){
            logger.info("检测到任务"+event.getPath()+"被创建，准备更新任务分配列表");
            server.getMasterService().findWaitTaskWorker();
        }else{
            logger.error("检测到未知的节点事件"+JU.toJSONString(event));
        }
    }

}
