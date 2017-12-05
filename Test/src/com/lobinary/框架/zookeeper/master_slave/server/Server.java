package com.lobinary.框架.zookeeper.master_slave.server;

import java.io.IOException;
import java.util.Random;

import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooKeeper.States;

import com.lobinary.java.多线程.TU;
import com.lobinary.框架.zookeeper.master_slave.config.ZKConfig;
import com.lobinary.框架.zookeeper.master_slave.watcher.master.ConnectionWatcher;

/**
 * 对服务器的相关业务操作
 * @author bin.lv
 * @since create by bin.lv 2017年12月1日 下午3:09:54
 *
 */
public class Server {
    
    /**
     * 服务器ID，用于抢主节点时来识别是不是自己
     */
    private String serverId;
    private boolean isMaster = false;
    private ZooKeeper client;
    private MasterService masterService;
    private WorkerService workerService;
    
    
    /**
     * 启动服务器
     * @throws IOException 
     * 
     * @since add by bin.lv 2017年12月1日 下午2:44:15
     */
    public void startServer() throws IOException {
        initServer();
        masterService.checkMaster();
    }
    
    public void stopServer() throws InterruptedException{
        client.close();
    }

    /**
     * 初始化server
     * 
     * @since add by bin.lv 2017年12月1日 下午3:16:40
     * @throws IOException
     */
    private void initServer() throws IOException {
        this.serverId = ""+new Random().nextInt(100);
        initClient();
        this.masterService = new MasterService(this);
        this.workerService = new WorkerService(this);
    }

    /**
     * 获取ZK客户端，防止多客户端连接到某台已经死了的服务器，导致最初的命令调用失败
     * 
     * @since add by bin.lv 2017年12月1日 下午2:56:17
     * @return
     * @throws IOException
     */
    public ZooKeeper initClient() throws IOException {
        ZooKeeper client = new ZooKeeper(ZKConfig.CONNECTION_STRING, ZKConfig.SESSION_TIMEOUT, new ConnectionWatcher());
        TU.s(100);
        States state = client.getState();
        while(States.CONNECTED!=state){
            System.out.println("zk连接"+client+"失败，当前状态"+state+"正在重连...");
            TU.s(500);
            state = client.getState();
        }
        System.out.println("成功连接到ZK服务器");
        this.client = client;
        return client;
    }
    
    public void setMaster(boolean isMaster) {
        this.isMaster = isMaster;
        if(isMaster){
            System.out.println(this+serverId+"抢到主节点啦，(*^▽^*)");
            masterService.startMaster();
        }else{
            System.out.println(this+serverId+"没有抢到主节点，┭┮﹏┭┮");
//            workerService.registerWorker();
        }
    }

    @Override
    public String toString() {
        return "Server [" + serverId + "]:";
    }


    public String getServerId() {
        return serverId;
    }

    public MasterService getMasterService() {
        return masterService;
    }

    public WorkerService getWorkerService() {
        return workerService;
    }

    public ZooKeeper getClient() {
        return client;
    }

    public boolean isMaster() {
        return isMaster;
    }

    

}
