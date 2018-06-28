package com.lobinary.框架.zookeeper.master_slave.client;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooKeeper.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lobinary.java.多线程.TU;
import com.lobinary.框架.zookeeper.master_slave.config.ZKConfig;
import com.lobinary.框架.zookeeper.master_slave.enums.TaskStatusEnum;
import com.lobinary.框架.zookeeper.master_slave.model.TaskData;
import com.lobinary.框架.zookeeper.master_slave.watcher.master.ConnectionWatcher;

public class Client {

    private final static Logger logger = LoggerFactory.getLogger(Client.class);

    public ZooKeeper client;
    
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        Client zk = new Client();
        zk.initClient();
        TaskData td = new TaskData("test"+System.currentTimeMillis(),TaskStatusEnum.INIT);
        System.out.println(td.getTaskStr());
        String name = zk.client.create(ZKConfig.TASKS_PATH+"/task", td.getTaskStr().getBytes(),  ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
        System.out.println("创建任务成功："+name);
//        byte[] data = zk.client.getData("/master_slave/tasks/task0000000001", false, null);
//        System.out.println(new String(data));
    }

    /**
     * 获取ZK客户端，防止多客户端连接到某台已经死了的服务器，导致最初的命令调用失败
     * 
     * @since add by bin.lv 2017年12月1日 下午2:56:17
     * @return
     * 
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
    
}
