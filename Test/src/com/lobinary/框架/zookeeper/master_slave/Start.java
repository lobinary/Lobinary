package com.lobinary.框架.zookeeper.master_slave;

import java.io.IOException;

import org.apache.zookeeper.AsyncCallback.StatCallback;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import com.lobinary.java.多线程.TU;
import com.lobinary.框架.zookeeper.master_slave.server.Server;

public class Start {
    
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        System.out.println("ready to start server");
//        Server s1 = new Server();
//        Server s2 = new Server();
//        s1.startServer();
//        s2.startServer();
//        s1.stopServer();
//        TU.s(3000);
        test();
    }

    @SuppressWarnings("unused")
    private static void test() throws IOException, KeeperException, InterruptedException {
        Server s = new Server();
        s.initClient();
        ZooKeeper client = s.getClient();
        client.exists("/test3", new Watcher() {
            
            @Override
            public void process(WatchedEvent event) {
                System.out.println(event);
            }
        }, new StatCallback() {
            
            @Override
            public void processResult(int rc, String path, Object ctx, Stat stat) {
                System.out.println(stat);
            }
        }, null);
//        try {
//            Stat exists = client.exists("/test3", new Watcher() {
//                @Override
//                public void process(WatchedEvent event) {
//                    System.out.println(event);
//                }
//            });
//            System.out.println(exists);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        
//
//        client.exists("/test", new Watcher() {
//            @Override
//            public void process(WatchedEvent event) {
//                System.out.println("test start");
//                TU.s(10000);
//                System.out.println("test end");
//            }
//        });
//        client.exists("/test2", new Watcher() {
//            @Override
//            public void process(WatchedEvent event) {
//                System.out.println("test2");
//            }
//        });
        TU.s(300000);
//        System.out.println(client);
//        byte[] data = client.getData("/xxx", false, null);
//        System.out.println(new String(data));
//        System.out.println(data[0]);
//        System.out.println(data[1]);
//        System.out.println(data[2]);
    }

}
