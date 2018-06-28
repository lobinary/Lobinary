package com.lobinary.框架.zookeeper.master_slave.callback.master;

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
public class TaskExistsCallback implements StatCallback{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Server server;
    
    public TaskExistsCallback(Server server) {
        super();
        this.server = server;
    }

    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {
        switch (Code.get(rc)) { 
        case CONNECTIONLOSS:
            server.getMasterService().waitNewTask();
            break;
        case NONODE:
            logger.info("检查新任务结果：暂时没有任务分配，等待Watcher发现新任务");
            break;
        case OK:
            if(stat!=null){
                logger.info("发现新任务已经被创建");
                server.getMasterService().updateAssignList();
            }
            break;
        default:
            throw new RuntimeException(server+this.getClass().getSimpleName()+"存在未处理码："+rc);
        }
    }

}
