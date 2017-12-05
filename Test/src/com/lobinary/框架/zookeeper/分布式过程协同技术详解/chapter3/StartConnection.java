package com.lobinary.框架.zookeeper.分布式过程协同技术详解.chapter3;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooKeeper.States;

import com.lobinary.java.多线程.TU;

public class StartConnection {
    
    private static String connectString = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";//zk服务器集群地址或单个服务器地址
    private static int sessionTimeout = 15000;//超时毫秒数
    private static Watcher watcher = new LWatcher();//监听器
    private static ZooKeeper zk;
    
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        startConnection();
        zk.create("/L/Task-", "xxx".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
        System.out.println("----------------------------------------");
        byte[] data = zk.getData("/", false, null);
        System.out.println("----------------------------------------");
        System.out.println(new String(data));
        System.out.println("----------------------------------------");
        List<String> children = zk.getChildren("/L", false);
        for(String c:children){
            System.out.println(c);
        }
        System.out.println("----------------------------------------");
        zk.close();
    }

    /**
     * 之所以此处进行循环判断，是因为有些时候，某些集群里边有已经挂了的服务器，导致第一次连接之后，实际服务器并不是处于连接状态，他需要过一段时间才能连上，所以当直接调用zk进行操作时，可能就会报错，所以此处循环，一直到处于成功才算启动成功
     * 
     * @since add by bin.lv 2017年11月24日 下午2:37:39
     * @throws IOException
     */
    public static void startConnection() throws IOException{
        zk = new ZooKeeper(connectString, sessionTimeout, watcher);
        while(States.CONNECTED!=zk.getState()){
            System.out.println("zk连接"+zk+"失败，正在重连...");
            TU.s(1000);
        }
        System.out.println("zk连接成功"+zk);
    }

    
    public static void ttttt(){
        
    }
}
