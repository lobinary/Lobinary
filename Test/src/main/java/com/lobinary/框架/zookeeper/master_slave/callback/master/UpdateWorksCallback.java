package com.lobinary.框架.zookeeper.master_slave.callback.master;

import java.util.List;

import org.apache.zookeeper.AsyncCallback.ChildrenCallback;
import org.apache.zookeeper.KeeperException.Code;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lobinary.工具类.JU;
import com.lobinary.框架.zookeeper.master_slave.server.Server;

/**
 * 检查worker工作状态，对没任务的分配任务
 * @author bin.lv
 * @since create by bin.lv 2017年12月1日 下午6:26:19
 *
 */
public class UpdateWorksCallback implements ChildrenCallback{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Server server;
    
    public UpdateWorksCallback(Server server) {
        super();
        this.server = server;
    }

    @Override
    public void processResult(int rc, String path, Object ctx, List<String> children) {
        switch (Code.get(rc)) { 
        case CONNECTIONLOSS:
            server.getMasterService().updateWorker();
        case NONODE :
            logger.info(server+"未发现可以工作的worker，等待监听到工作者再分配任务");
            break;
        case OK:
            logger.info(server+"获取到最新工作者信息"+JU.toJSONString(children));
            server.getMasterService().addWorkers(children);
            break;
        default:
            throw new RuntimeException(server+this.getClass().getSimpleName()+"存在未处理码："+rc);
        }
    }

}
