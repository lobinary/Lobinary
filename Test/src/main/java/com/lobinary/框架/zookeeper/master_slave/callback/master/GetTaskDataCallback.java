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
public class GetTaskDataCallback implements DataCallback{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Server server;
    private int assignVersion;
    
    public GetTaskDataCallback(Server server, int assignVersion) {
        super();
        this.server = server;
        this.assignVersion = assignVersion;
    }

    @Override
    public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
        switch (Code.get(rc)) { 
        case CONNECTIONLOSS:
            server.getMasterService().assignTask((String)ctx,assignVersion);
            break;
        case NONODE:
            logger.info("暂无任务，等待该任务被创建"+path);
            server.getMasterService().waitNewTask();
            break;
        case OK:
            if(stat==null){
                server.getMasterService().waitNewTask();
            }else{
                TaskData taskData = new TaskData((String)ctx,  path,stat.getVersion(),data);
                taskData.setWorkerName((String)ctx);
                taskData.setAssignVersion(assignVersion);
                server.getMasterService().assignTask(taskData);
            }
            break;
        default:
            throw new RuntimeException(server+this.getClass().getSimpleName()+"存在未处理码："+rc);
        }
    }

}
