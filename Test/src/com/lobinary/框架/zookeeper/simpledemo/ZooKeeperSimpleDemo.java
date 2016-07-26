package com.lobinary.框架.zookeeper.simpledemo;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;

public class ZooKeeperSimpleDemo extends AbstractZooKeeper {

	private static Logger log = Logger.getLogger(ZooKeeperSimpleDemo.class);

	public static void main(String[] args) {
		try {
			ZooKeeperSimpleDemo zkoperator = new ZooKeeperSimpleDemo();
			zkoperator.connect("127.0.0.1");

			
			byte[] data = new byte[] { 'a', 'b', 'c', 'd' };
			log.info("==================");
			if(zkoperator.zooKeeper.exists("/root", false) == null){
				 zkoperator.create("/root",null);
				 System.out.println(Arrays.toString(zkoperator.getData("/root")));
			}
			//
			// zkoperator.create("/root/child1",data);
			// System.out.println(Arrays.toString(zkoperator.getData("/root/child1")));
			//
			// zkoperator.create("/root/child2",data);
			// System.out.println(Arrays.toString(zkoperator.getData("/root/child2")));

//			zkoperator.zooKeeper.delete("/root/child3", -1);

			if(zkoperator.zooKeeper.exists("/root/child3", false) == null){
				String 变量值 = "变量值";
				zkoperator.create("/root/child3", 变量值.getBytes());
			}
			
			log.debug("获取设置的信息：" + new String(zkoperator.getData("/root/child3")));

			System.out.println("节点孩子信息:");
			zkoperator.getChild("/root");

			zkoperator.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * <b>function:</b>创建持久态的znode,比支持多层创建.比如在创建/parent/child的情况下,无/parent.无法通过
	 *
	 * @author cuiran
	 * @createDate 2013-01-16 15:08:38
	 * @param path
	 * @param data
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public void create(String path, byte[] data) throws KeeperException, InterruptedException {
		/**
		 * 此处采用的是CreateMode是PERSISTENT 表示The znode will not be automatically
		 * deleted upon client's disconnect. EPHEMERAL 表示The znode will be
		 * deleted upon the client's disconnect.
		 */
		this.zooKeeper.create(path, data, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
	}

	/**
	 * 
	 * <b>function:</b>获取节点信息
	 *
	 * @author cuiran
	 * @createDate 2013-01-16 15:17:22
	 * @param path
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public void getChild(String path) throws KeeperException, InterruptedException {
		try {
			List<String> list = this.zooKeeper.getChildren(path, false);
			if (list.isEmpty()) {
				log.debug(path + "中没有节点");
			} else {
				log.debug(path + "中存在节点");
				for (String child : list) {
					log.debug("节点为：" + child);
				}
			}
		} catch (KeeperException.NoNodeException e) {
			// TODO: handle exception
			throw e;

		}
	}

	public byte[] getData(String path) throws KeeperException, InterruptedException {
		return this.zooKeeper.getData(path, false, null);
	}

}