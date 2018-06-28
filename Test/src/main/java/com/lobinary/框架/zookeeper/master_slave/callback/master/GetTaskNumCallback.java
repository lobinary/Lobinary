package com.lobinary.框架.zookeeper.master_slave.callback.master;

import org.apache.zookeeper.AsyncCallback.DataCallback;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lobinary.框架.zookeeper.master_slave.config.ZKConfig;
import com.lobinary.框架.zookeeper.master_slave.server.Server;

/**
 * 获取当前执行的任务数
 * @author bin.lv
 * @since create by bin.lv 2017年12月4日 下午2:56:12
 *
 */
public class GetTaskNumCallback implements DataCallback{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Server server;
    
    public GetTaskNumCallback(Server server) {
        super();
        this.server = server;
    }

    @Override
    public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
        switch (Code.get(rc)) { 
        case CONNECTIONLOSS:
            server.getMasterService().updateCurrentTaskNum();
            break;
        case NONODE:
            logger.info(server+"厉害了，"+ZKConfig.CURRENT_TASK_NUM+"节点不存在，数据架构有问题，赶紧停下来服务器，看看咋回事吧");
            System.exit(0);
            break;
        case OK:
            if(stat==null){
                logger.info(server+"厉害了，"+ZKConfig.CURRENT_TASK_NUM+"节点不存在，数据架构有问题，赶紧停下来服务器，看看咋回事吧");
                System.exit(0);
            }else{
                String currentTaskNumStr = new String(data);
                int currentTaskNum = Integer.parseInt(currentTaskNumStr);
                server.getMasterService().setCurrentTaskData(currentTaskNum,stat.getVersion());
            }
            break;
        default:
            throw new RuntimeException(server+this.getClass().getSimpleName()+"存在未处理码："+rc);
        }
    }

}
