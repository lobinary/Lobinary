package com.lobinary.框架.zookeeper.master_slave.callback.master;

import org.apache.zookeeper.AsyncCallback.DataCallback;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.data.Stat;

import com.lobinary.框架.zookeeper.master_slave.server.Server;

/**
 * 获取主节点数据
 * @author bin.lv
 * @since create by bin.lv 2017年12月1日 下午3:34:18
 *
 */
public class GetMasterDataCallback implements DataCallback{

    private Server server;
    
    public GetMasterDataCallback(Server server) {
        super();
        this.server = server;
    }

    @Override
    public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
        switch (Code.get(rc)) { 
        case CONNECTIONLOSS:
            server.getMasterService().checkMaster();
            break;
        case NONODE:
            server.getMasterService().electMaster();
            break;
        case OK:
            String masterServerId = new String(data);
            if(server.getServerId().equals(masterServerId)){//如果发现我是Master
                server.setMaster(true);
            }else{//发现我不是master，监控它,然而前边我们已经监听了，所以此处不做处理
                server.setMaster(false);
            }
            break;
        default:
            System.out.println(server+this.getClass().getSimpleName()+"存在未处理码："+rc);
        }
    }

}
