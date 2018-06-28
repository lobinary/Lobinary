package com.lobinary.框架.zookeeper.master_slave.callback.worker;

import org.apache.zookeeper.AsyncCallback.StatCallback;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lobinary.工具类.JU;
import com.lobinary.框架.zookeeper.master_slave.model.TaskData;
import com.lobinary.框架.zookeeper.master_slave.server.Server;

/**
 * 更新任务信息
 * @author bin.lv
 * @since create by bin.lv 2017年12月6日 上午10:48:29
 *
 */
public class UpdateTaskCallback implements StatCallback{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Server server;
    private TaskData taskData;


    public UpdateTaskCallback(TaskData taskData, Server server) {
        super();
        this.server = server;
        this.taskData = taskData;
    }


    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {
        switch (Code.get(rc)) { 
        case CONNECTIONLOSS:
            server.getWorkerService().updateTask(taskData);
            break;
        case OK:
            logger.info(server+"更新任务成功，准备等待新任务分配"+JU.toJSONString(stat));
            server.getWorkerService().setProcessingTask(null);
            break;
        default:
            throw new RuntimeException(server+this.getClass().getSimpleName()+"存在未处理码："+rc);
        }
    }

}
