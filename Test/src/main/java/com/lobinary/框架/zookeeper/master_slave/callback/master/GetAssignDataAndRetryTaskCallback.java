package com.lobinary.框架.zookeeper.master_slave.callback.master;

import org.apache.zookeeper.AsyncCallback.DataCallback;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lobinary.框架.zookeeper.master_slave.model.TaskData;
import com.lobinary.框架.zookeeper.master_slave.server.Server;

/**
 * 获取主节点数据
 * @author bin.lv
 * @since create by bin.lv 2017年12月1日 下午3:34:18
 *
 */
public class GetAssignDataAndRetryTaskCallback implements DataCallback{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Server server;
    
    public GetAssignDataAndRetryTaskCallback(Server server) {
        super();
        this.server = server;
    }

    @Override
    public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
        switch (Code.get(rc)) { 
        case CONNECTIONLOSS:
            server.getMasterService().reassignTask((String)ctx);
            break;
        case NONODE:
            throw new RuntimeException("获取Assgin任务数据失败,不知道谁给删除了,assignData:"+path);
        case OK:
            if(stat==null){
                throw new RuntimeException("获取Assgin任务数据失败,不知道谁给删除了,assignData:"+path);
            }else{
                TaskData taskData = new TaskData((String)ctx,stat.getVersion(), data);
                server.getMasterService().reassignTask(taskData);
            }
            break;
        default:
            throw new RuntimeException(server+this.getClass().getSimpleName()+"存在未处理码："+rc);
        }
    }

}
