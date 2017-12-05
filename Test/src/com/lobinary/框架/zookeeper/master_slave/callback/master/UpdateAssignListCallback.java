package com.lobinary.框架.zookeeper.master_slave.callback.master;

import java.util.List;

import org.apache.zookeeper.AsyncCallback.ChildrenCallback;
import org.apache.zookeeper.KeeperException.Code;

import com.lobinary.工具类.JU;
import com.lobinary.框架.zookeeper.master_slave.server.Server;

/**
 * 检查worker工作状态，对没任务的分配任务
 * 
 * @author bin.lv
 * @since create by bin.lv 2017年12月1日 下午6:26:19
 *
 */
public class UpdateAssignListCallback implements ChildrenCallback {

    private Server server;

    public UpdateAssignListCallback(Server server) {
        super();
        this.server = server;
    }

    @Override
    public void processResult(int rc, String path, Object ctx, List<String> children) {
        switch (Code.get(rc)) { 
        case NONODE :
            System.out.println(server+"Assign中没有分配任务记录");
            break;
        case OK:
            if(children.size()==0){
                System.out.println(server+"Assign中没有分配任务记录");
                break;
            }
            System.out.println(server+"获取到最新任务分配记录"+JU.toJSONString(children));
            for(String workerName:children){
                server.getMasterService().addAssign(workerName);
            }
            break;
        default:
            System.out.println(server+this.getClass().getSimpleName()+"存在未处理码："+rc);
        }
    }

}
