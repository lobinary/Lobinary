package com.lobinary.框架.zookeeper.master_slave.watcher.master;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import com.lobinary.框架.zookeeper.master_slave.server.Server;

/**
 * 获取worker连接变化
 * @author bin.lv
 * @since create by bin.lv 2017年12月1日 下午6:25:43
 *
 */
public class WorkerConnectionChangeWatcher implements Watcher{

    private Server server;
    
    public WorkerConnectionChangeWatcher(Server server) {
        super();
        this.server = server;
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println(server+this.getClass().getSimpleName()+"发现事件["+event+"]");
        server.getMasterService().updateWorker();
    }

}
