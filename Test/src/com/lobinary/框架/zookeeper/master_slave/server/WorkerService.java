package com.lobinary.框架.zookeeper.master_slave.server;

import org.apache.zookeeper.ZooKeeper;

/**
 * 作为工作者的工作
 * @author bin.lv
 * @since create by bin.lv 2017年12月5日 上午10:40:35
 *
 */
public class WorkerService {
    
    private Server server;
    private ZooKeeper client;
    private String workerName;
    
    public WorkerService(Server server) {
        super();
        this.server = server;
        this.workerName = "Worker"+server.getServerId();
        this.client = server.getClient();
    }

    public void findWork() {
//        client.
    }

}
