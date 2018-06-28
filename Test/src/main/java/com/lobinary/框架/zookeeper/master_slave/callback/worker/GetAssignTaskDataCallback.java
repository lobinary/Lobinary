package com.lobinary.框架.zookeeper.master_slave.callback.worker;

import org.apache.zookeeper.AsyncCallback.DataCallback;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lobinary.框架.zookeeper.master_slave.enums.TaskStatusEnum;
import com.lobinary.框架.zookeeper.master_slave.model.TaskData;
import com.lobinary.框架.zookeeper.master_slave.server.Server;

/**
 * 获取Assign中的Task数据
 * @author bin.lv
 * @since create by bin.lv 2017年12月1日 下午3:34:18
 *
 */
public class GetAssignTaskDataCallback implements DataCallback{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Server server;
    
    public GetAssignTaskDataCallback(Server server) {
        super();
        this.server = server;
    }

    @Override
    public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
        switch (Code.get(rc)) { 
        case CONNECTIONLOSS:
            server.getWorkerService().updateTask();
            break;
        case NONODE:
            logger.info(server+"Assign还没有被创建，等待Assign创建");
            server.getWorkerService().waitAssignCreate();
            break;
        case OK:
            if(stat==null){
                logger.info(server+"Assign还没有被创建，等待Assign创建");
                server.getWorkerService().waitAssignCreate();
            }else{
                TaskData taskData = new TaskData((String)ctx,stat.getVersion(),data);
                if(TaskStatusEnum.FINISHED!=taskData.getStatus()){
                    server.getWorkerService().processTask(taskData);
                }else{
                    logger.info(server+"发现任务已经完成，等待新任务，当前已完成的任务："+taskData);
                }
            }
            break;
        default:
            throw new RuntimeException(server+this.getClass().getSimpleName()+"存在未处理码："+rc);
        }
    }

}
