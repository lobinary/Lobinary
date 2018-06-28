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
public class AssignStatusChangeWatcher implements Watcher{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Server server;
    private String workerName;
    

    public AssignStatusChangeWatcher(String workerName, Server server) {
        super();
        this.server = server;
        this.workerName = workerName;
    }

    @Override
    public void process(WatchedEvent event) {
        server.getMasterService().removeAssignWatcher(workerName);
        if(EventType.NodeDataChanged==event.getType()){
            logger.info("Assign节点"+workerName+"数据发生变化，准备更新节点数据状态"+JU.toJSONString(event));
            server.getMasterService().updateAssignStatus(workerName);
        }else if(EventType.NodeDeleted==event.getType()){
            logger.info("Assign节点被删除："+workerName);
        }else{
            throw new RuntimeException("出现未知的事件："+event);
        }
    }

}
