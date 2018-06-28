package com.lobinary.框架.zookeeper.master_slave.watcher.worker;

import org.apache.zookeeper.AsyncCallback.StringCallback;
import org.apache.zookeeper.KeeperException.Code;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lobinary.框架.zookeeper.master_slave.server.Server;
import com.lobinary.框架.zookeeper.master_slave.util.ZU;

public class RegisterWorker implements StringCallback {

    private Server server;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    public RegisterWorker(Server server) {
        super();
        this.server = server;
    }

    @Override
    public void processResult(int rc, String path, Object ctx, String name) {
        switch (Code.get(rc)) { 
        case CONNECTIONLOSS:
            server.getWorkerService().registerWorker();
            break;
        case OK:
            logger.info(server+"注册Worker成功："+ZU.getLastNodeName(name));
            server.getWorkerService().setWorkerName(ZU.getLastNodeName(name));
            server.getWorkerService().updateTask();
            break;
        case NODEEXISTS:
            throw new RuntimeException("注册Worker失败,NODEEXISTS:"+path+" ---- "+name);
        default:
            throw new RuntimeException(server+this.getClass().getSimpleName()+"存在未处理码："+rc);
        }
    }

}
