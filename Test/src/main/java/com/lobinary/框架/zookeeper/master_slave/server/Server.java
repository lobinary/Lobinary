package com.lobinary.框架.zookeeper.master_slave.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Op;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooKeeper.States;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    
    private final static Logger logger = LoggerFactory.getLogger(Server.class);
    
    /**
     * 服务器ID，用于抢主节点时来识别是不是自己
     */
    private String serverId;
    private Boolean isMaster = null;
    private ZooKeeper client;
    private MasterService masterService;
    private WorkerService workerService;
    
    
    /**
     * 启动服务器
     * @throws IOException 
     * @throws InterruptedException 
     * @throws KeeperException 
     * 
     * @since add by bin.lv 2017年12月1日 下午2:44:15
     */
    public void startServer() throws IOException, KeeperException, InterruptedException {
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
     * @throws InterruptedException 
     * @throws KeeperException 
     */
    private void initServer() throws IOException, KeeperException, InterruptedException {
        this.serverId = ""+new Random().nextInt(100);
        initClient();
        this.masterService = new MasterService(this);
        this.workerService = new WorkerService(this);
        initialFolder();
    }

    private void initialFolder() throws KeeperException, InterruptedException {
        Stat masterSlavePath = client.exists(ZKConfig.MASTER_PARENT_PATH, false);
        if(masterSlavePath==null){
            List<Op> initialFolderOps = new ArrayList<Op>();
            initialFolderOps.add(Op.create(ZKConfig.MASTER_PARENT_PATH, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT));
            initialFolderOps.add(Op.create(ZKConfig.WORKER_PATH, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT));
            initialFolderOps.add(Op.create(ZKConfig.TASKS_PATH, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT));
            initialFolderOps.add(Op.create(ZKConfig.ASSIGN_PATH, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT));
            initialFolderOps.add(Op.create(ZKConfig.CURRENT_TASK_NUM, "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT));
            client.multi(initialFolderOps);
            logger.info(this+"主从关键路径创建完毕");
        }
//        else{
//            logger.info(masterSlavePath);
//            deleteNode(ZKConfig.CURRENT_TASK_NUM);
//            deleteNode(ZKConfig.ASSIGN_PATH);
//            deleteNode(ZKConfig.WORKER_PATH);
//            deleteNode(ZKConfig.TASKS_PATH);
//            deleteNode(ZKConfig.MASTER_PARENT_PATH);
//            logger.info(this+"节点清除完毕");
//            throw new RuntimeException(this+"节点清除完毕");
//        }
    }

    @SuppressWarnings("unused")
    private void deleteNode(String path)  {
        try {
            client.delete(path, -1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            logger.info("",e);
        }
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
            logger.info("zk连接"+client+"失败，当前状态"+state+"正在重连...");
            TU.s(500);
            state = client.getState();
        }
        logger.info("成功连接到ZK服务器");
        this.client = client;
        return client;
    }
    
    public void setMaster(boolean isMaster) {
        if(isMaster){
            if(this.isMaster !=null && this.isMaster==false){
                workerService.shutDownWorker();
            }
            this.isMaster = isMaster;
            logger.info(this+serverId+"抢到主节点啦，(*^▽^*)");
            masterService.startMaster();
        }else{
            logger.info(this+serverId+"没有抢到主节点，┭┮﹏┭┮");
            workerService.registerWorker();
        }
    }

    @Override
    public String toString() {
//        if( null == isMaster || true == isMaster){
//            return "Server [" + serverId + "]:";
//        }else {
//            String workerName = workerService.getWorkerName();
//            if(workerName==null){
//                workerName = serverId;
//            }
//            return "Worker [" + workerName + "]:";
//        }
        return "";
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
