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
public class TasksExistsAndCreateAssignWatcher implements Watcher{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Server server;
    private String workerName;
    private static String currentTaskPath;
    
    private TasksExistsAndCreateAssignWatcher(Server server,String workerName) {
        super();
        this.server = server;
        this.workerName = workerName;
    }

    @Override
    public void process(WatchedEvent event) {
        if(EventType.NodeCreated==event.getType()){
            logger.info("监听到任务被创建"+event.getPath());
            server.getMasterService().createAssign(workerName);
        }
    }

    public static Watcher getTaskExistWatcher(String taskPath,Server server, String workerName) {
        if(!taskPath.equals(currentTaskPath)){
            currentTaskPath = taskPath;
            TasksExistsAndCreateAssignWatcher w = new TasksExistsAndCreateAssignWatcher(server, workerName);
            return w;
        }
        return null;
    }

}
