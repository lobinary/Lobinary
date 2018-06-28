package com.lobinary.框架.zookeeper.master_slave.callback.master;

import org.apache.zookeeper.AsyncCallback.StringCallback;
import org.apache.zookeeper.KeeperException.Code;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lobinary.框架.zookeeper.master_slave.config.ZKConfig;
import com.lobinary.框架.zookeeper.master_slave.server.Server;

public class ElectMasterCallBack implements StringCallback {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Server server;
    
    public ElectMasterCallBack(Server server) {
        super();
        this.server = server;
    }

    @Override
    public void processResult(int rc, String path, Object ctx, String name) {
        switch (Code.get(rc)) { 
        case CONNECTIONLOSS:
            server.getMasterService().checkMaster();
            break;
        case OK:
            if(!ZKConfig.MASTER_PATH.equals(name)){
                logger.info(server+"发现创建了主节点不交主节点，名字居然是："+name);
            }
            server.setMaster(true);
            break;
        case NONODE:
            server.getMasterService().electMaster();
            break;
        case NODEEXISTS:
            server.getMasterService().checkMaster();
            break;
        default:
            throw new RuntimeException(server+this.getClass().getSimpleName()+"存在未处理码："+rc);
        }
    }

}
