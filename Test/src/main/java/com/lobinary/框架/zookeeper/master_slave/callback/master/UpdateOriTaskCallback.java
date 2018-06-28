package com.lobinary.框架.zookeeper.master_slave.callback.master;

import org.apache.zookeeper.AsyncCallback.StatCallback;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lobinary.工具类.JU;
import com.lobinary.框架.zookeeper.master_slave.model.TaskData;
import com.lobinary.框架.zookeeper.master_slave.server.Server;

/**
 * 检查worker工作状态，对没任务的分配任务
 * @author bin.lv
 * @since create by bin.lv 2017年12月1日 下午6:26:19
 *
 */
public class UpdateOriTaskCallback implements StatCallback{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Server server;
    
    public UpdateOriTaskCallback(Server server) {
        super();
        this.server = server;
    }


    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {
        switch (Code.get(rc)) { 
        case CONNECTIONLOSS:
            server.getMasterService().updateOriTask((TaskData) ctx);
        case NONODE :
            throw new RuntimeException(server+"未发现原始任务"+ctx);
        case OK:
            logger.info(server+"更新原始任务成功"+JU.toJSONString(stat));
            break;
        default:
            throw new RuntimeException(server+this.getClass().getSimpleName()+"存在未处理码："+rc);
        }
    }

}
