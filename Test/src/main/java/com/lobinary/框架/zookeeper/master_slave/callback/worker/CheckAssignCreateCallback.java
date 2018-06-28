package com.lobinary.框架.zookeeper.master_slave.callback.worker;

import org.apache.zookeeper.AsyncCallback.StatCallback;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lobinary.框架.zookeeper.master_slave.server.Server;

/**
 * 抓取任务并分配
 * @author bin.lv
 * @since create by bin.lv 2017年12月4日 下午3:53:28
 *
 */
public class CheckAssignCreateCallback implements StatCallback{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Server server;
    
    public CheckAssignCreateCallback(Server server) {
        super();
        this.server = server;
    }

    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {
        switch (Code.get(rc)) { 
        case CONNECTIONLOSS:
            server.getWorkerService().waitAssignCreate();
            break;
        case NONODE:
            break;
        case OK:
            if(stat!=null){
                logger.info(server+"Assign节点"+path+"已经被创建");
                server.getWorkerService().updateTask();
            }else{
                logger.info("放置监听的同时检测到Assign节点"+path+"依然还没有被创建");
            }
            break;
        default:
            throw new RuntimeException(server+this.getClass().getSimpleName()+"存在未处理码："+rc);
        }
    }

}
