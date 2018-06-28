package com.lobinary.框架.zookeeper.master_slave.callback.master;

import java.util.List;

import org.apache.zookeeper.AsyncCallback.MultiCallback;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.OpResult;
import org.apache.zookeeper.OpResult.ErrorResult;
import org.apache.zookeeper.OpResult.SetDataResult;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lobinary.工具类.JU;
import com.lobinary.框架.zookeeper.master_slave.model.TaskData;
import com.lobinary.框架.zookeeper.master_slave.server.Server;

/**
 * 获取主节点数据
 * @author bin.lv
 * @since create by bin.lv 2017年12月1日 下午3:34:18
 *
 */
public class AssignTaskCallback implements MultiCallback{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Server server;
    
    public AssignTaskCallback(Server server) {
        super();
        this.server = server;
    }

    @Override
    public void processResult(int rc, String path, Object ctx, List<OpResult> opResults) {
        switch (Code.get(rc)) { 
        case CONNECTIONLOSS:
            server.getMasterService().assignTask((TaskData) ctx);
            break;
        case OK:
            for (int j = 0; j < opResults.size(); j++) {
                OpResult result = opResults.get(j);
                logger.info(server+"分配任务事务第"+j+"个执行结果:"+JU.toJSONString(result));
                if(result instanceof ErrorResult){
                    ErrorResult errorResult = (ErrorResult) result;
                    switch (Code.get(errorResult.getErr())) { 
                    case NONODE:
                        logger.info(server+"存在没有的节点类型"+errorResult.getType()+"，第"+j+"个操作");
                        break;
                    case BADVERSION:
                        logger.info(server+this.getClass().getSimpleName()+"存在非法版本,请查明当前原因，第"+j+"个操作");
                        break;
                    default:
                        logger.info(server+this.getClass().getSimpleName()+"I存在未处理码："+errorResult.getErr());
                    }
                }else if(result instanceof SetDataResult){
                    SetDataResult setDataResult = (SetDataResult)result;
                    if(0==j){
                        Stat stat = setDataResult.getStat();
                        server.getMasterService().setCurrentTaskData(server.getMasterService().getCurrentTaskNum()+1, stat.getVersion());
                    }
                }
            }
            server.getMasterService().updateAssignList();
            break;
        default:
            throw new RuntimeException(server+this.getClass().getSimpleName()+"II存在未处理码："+rc);
        }
    }

}
