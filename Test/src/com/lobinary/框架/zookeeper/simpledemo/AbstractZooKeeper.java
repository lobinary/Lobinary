package com.lobinary.框架.zookeeper.simpledemo;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;


/** 
* TODO 
* @author cuiran 
* @version TODO 
*/  
public class AbstractZooKeeper implements Watcher {  
  private static Log log = LogFactory.getLog(AbstractZooKeeper.class.getName());  

  //缓存时间  
   private static final int SESSION_TIME   = 2000;     
   protected ZooKeeper zooKeeper;  
   protected CountDownLatch countDownLatch=new CountDownLatch(1);  

   public void connect(String hosts) throws IOException, InterruptedException{     
          zooKeeper = new ZooKeeper(hosts,SESSION_TIME,this);     
          countDownLatch.await();     
    }     

  /* (non-Javadoc) 
   * @see org.apache.zookeeper.Watcher#process(org.apache.zookeeper.WatchedEvent) 
   */  
  @Override  
  public void process(WatchedEvent event) {  
      // TODO Auto-generated method stub  
      if(event.getState()==KeeperState.SyncConnected){  
          countDownLatch.countDown();  
      }  
  }  
    
  public void close() throws InterruptedException{     
      zooKeeper.close();     
  }    
}  