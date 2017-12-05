package com.lobinary.框架.zookeeper.master_slave.callback.master;

import org.apache.zookeeper.AsyncCallback.DataCallback;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.data.Stat;

import com.lobinary.框架.zookeeper.master_slave.model.TaskData;
import com.lobinary.框架.zookeeper.master_slave.server.Server;

/**
 * 查看任务状态
 * @author bin.lv
 * @since create by bin.lv 2017年12月4日 上午11:28:47
 *
 */
public class UpdateAssignStatusCallback implements DataCallback{

    private Server server;
    
    public UpdateAssignStatusCallback(Server server) {
        super();
        this.server = server;
    }

    @Override
    public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
        switch (Code.get(rc)) { 
        case CONNECTIONLOSS:
            server.getMasterService().updateAssignStatus(path);
            break;
        case NONODE:
            System.out.println(server+"[安全警告]发现任务分配的节点被删除");
            break;
        case OK:
            TaskData taskData = new TaskData((String)ctx,data);
            server.getMasterService().updateAssignStatus(taskData);
            break;
        default:
            System.out.println(server+this.getClass().getSimpleName()+"存在未处理码："+rc);
        }
    }

}
