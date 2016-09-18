package com.lobinary.框架.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * 
 * <pre>
 * 使用zookeeper需要现在服务器或者本地启动zookeeper服务
 * 	
 * </pre>
 *
 * @ClassName: Start
 * @author 919515134@qq.com
 * @date 2016年8月23日 下午2:09:05
 * @version V1.0.0
 */
public class Start {


    private static final int TIME_OUT = 3000;
    private static final String HOST = "127.0.0.1:2181";
    public static void main(String[] args) throws Exception{

    	ZooKeeper zookeeper = new ZooKeeper(HOST, TIME_OUT, null);
    	System.out.println("=========创建节点===========");
        if(zookeeper.exists("/test", false) == null)
        {
            zookeeper.create("/test", "znode1".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        System.out.println("=============查看节点是否安装成功===============");
        System.out.println(new String(zookeeper.getData("/test", false, null)));
         
        System.out.println("=========修改节点的数据==========");
        String data = "zNode2";
        zookeeper.setData("/test", data.getBytes(), -1);
         
        System.out.println("========查看修改的节点是否成功=========");
        System.out.println(new String(zookeeper.getData("/test", false, null)));
         
        System.out.println("=======删除节点==========");
        zookeeper.delete("/test", -1);
         
        System.out.println("==========查看节点是否被删除============");
        System.out.println("节点状态：" + zookeeper.exists("/test", false));
         
        zookeeper.close();
    } 
	
}
