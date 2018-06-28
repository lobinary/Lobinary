package com.lobinary.框架.zookeeper.master_slave.callback.master;

import org.apache.zookeeper.AsyncCallback.DataCallback;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lobinary.框架.zookeeper.master_slave.model.TaskData;
import com.lobinary.框架.zookeeper.master_slave.server.Server;

/**
 * 抓取任务并分配
 * @author bin.lv
 * @since create by bin.lv 2017年12月4日 下午3:53:28
 *
 */
public class GetTaskDataAndCreateAssignCallback implements DataCallback{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Server server;
    
    public GetTaskDataAndCreateAssignCallback(Server server) {
        super();
        this.server = server;
    }

    @Override
    public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
        switch (Code.get(rc)) { 
        case CONNECTIONLOSS:
            server.getMasterService().createAssign((String)ctx);
            break;
        case NONODE:
            logger.info("发现任务"+path+"并未创建");;
            server.getMasterService().waitNewTaskAndCreateAssign((String)ctx);
            break;
        case OK:
            if(stat==null){
                logger.info("当前线程池中没有任务，准备等待新任务");
                server.getMasterService().waitNewTaskAndCreateAssign((String)ctx);
            }else{
                logger.info("发现任务"+path+"已经被创建，准备开始创建Assign并分派任务给"+ctx);
                TaskData taskData = new TaskData((String)ctx,  path,stat.getVersion(),data);
                server.getMasterService().createAssign(taskData);
            }
            break;
        default:
            throw new RuntimeException(server+this.getClass().getSimpleName()+"存在未处理码："+rc);
        }
    }

}
