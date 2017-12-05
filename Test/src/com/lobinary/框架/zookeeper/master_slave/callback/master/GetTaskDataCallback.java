package com.lobinary.框架.zookeeper.master_slave.callback.master;

import org.apache.zookeeper.AsyncCallback.DataCallback;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.data.Stat;

import com.lobinary.框架.zookeeper.master_slave.model.TaskData;
import com.lobinary.框架.zookeeper.master_slave.server.Server;

/**
 * 抓取任务并分配
 * @author bin.lv
 * @since create by bin.lv 2017年12月4日 下午3:53:28
 *
 */
public class GetTaskDataCallback implements DataCallback{

    private Server server;
    
    public GetTaskDataCallback(Server server) {
        super();
        this.server = server;
    }

    @Override
    public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
        switch (Code.get(rc)) { 
        case CONNECTIONLOSS:
            server.getMasterService().assignTask((String)ctx);
            break;
        case NONODE:
            server.getMasterService().waitNewTask();
            break;
        case OK:
            TaskData taskData = new TaskData((String)ctx, data);
            taskData.setWorkerName((String)ctx);
            server.getMasterService().assignTask(taskData);
            break;
        default:
            System.out.println(server+this.getClass().getSimpleName()+"存在未处理码："+rc);
        }
    }

}
