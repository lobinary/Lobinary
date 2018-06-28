package com.lobinary.框架.zookeeper.master_slave.callback.master;

import java.util.List;

import org.apache.zookeeper.AsyncCallback.MultiCallback;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.OpResult;
import org.apache.zookeeper.OpResult.ErrorResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lobinary.工具类.JU;
import com.lobinary.框架.zookeeper.master_slave.model.TaskData;
import com.lobinary.框架.zookeeper.master_slave.server.Server;

/**
 * 更新原始任务并删除Assign
 * @author bin.lv
 * @since create by bin.lv 2017年12月6日 下午3:12:21
 *
 */
public class UpdateOriTaskAndDeleteAssignCallback implements MultiCallback{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Server server;
    
    public UpdateOriTaskAndDeleteAssignCallback(Server server) {
        super();
        this.server = server;
    }

    @Override
    public void processResult(int rc, String path, Object ctx, List<OpResult> opResults) {
        switch (Code.get(rc)) { 
        case CONNECTIONLOSS:
            server.getMasterService().reassignTask((TaskData) ctx);
            break;
        case OK:
            for (int j = 0; j < opResults.size(); j++) {
                OpResult result = opResults.get(j);
                logger.info(server+"更新原始任务并删除Assign第"+j+"个执行结果:"+JU.toJSONString(result));
                if(result instanceof ErrorResult){
                    ErrorResult errorResult = (ErrorResult) result;
                    switch (Code.get(errorResult.getErr())) { 
                    default:
                        logger.error(server+this.getClass().getSimpleName()+"I存在未处理码："+errorResult.getErr());
                    }
                }
            }
            break;
        default:
            throw new RuntimeException(server+this.getClass().getSimpleName()+"II存在未处理码："+rc);
        }
    }

}
