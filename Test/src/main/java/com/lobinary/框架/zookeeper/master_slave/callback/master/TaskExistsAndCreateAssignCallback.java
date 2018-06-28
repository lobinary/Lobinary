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
public class TaskExistsAndCreateAssignCallback implements StatCallback{
    @SuppressWarnings("unused")
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Server server;
    
    public TaskExistsAndCreateAssignCallback(Server server) {
        super();
        this.server = server;
    }

    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {
        switch (Code.get(rc)) { 
        case CONNECTIONLOSS:
            server.getMasterService().waitNewTaskAndCreateAssign((String)ctx);
            break;
        case NONODE:
            break;
        case OK:
            if(stat!=null){
                logger.info("发现任务"+path+"已经存在，准备开始向"+ctx+"创建Assign型分配任务");
                server.getMasterService().createAssign((String)ctx);
            }else{
                logger.info(path+"暂时不存在，等待Watcher监控");
            }
            break;
        default:
            throw new RuntimeException(server+this.getClass().getSimpleName()+"存在未处理码："+rc);
        }
    }

}
