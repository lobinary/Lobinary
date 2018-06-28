package com.lobinary.框架.zookeeper.master_slave.server;

import org.apache.zookeeper.AsyncCallback.VoidCallback;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lobinary.java.多线程.TU;
import com.lobinary.框架.zookeeper.master_slave.callback.worker.CheckAssignCreateCallback;
import com.lobinary.框架.zookeeper.master_slave.callback.worker.GetAssignTaskDataCallback;
import com.lobinary.框架.zookeeper.master_slave.callback.worker.UpdateTaskCallback;
import com.lobinary.框架.zookeeper.master_slave.config.ZKConfig;
import com.lobinary.框架.zookeeper.master_slave.enums.TaskStatusEnum;
import com.lobinary.框架.zookeeper.master_slave.model.TaskData;
import com.lobinary.框架.zookeeper.master_slave.watcher.worker.RegisterWorker;
import com.lobinary.框架.zookeeper.master_slave.watcher.worker.WaitAssignCreateWatcher;
import com.lobinary.框架.zookeeper.master_slave.watcher.worker.WaitTaskWatcher;

/**
 * 作为工作者的工作
 * @author bin.lv
 * @since create by bin.lv 2017年12月5日 上午10:40:35
 *
 */
public class WorkerService {
    
    private final static Logger logger = LoggerFactory.getLogger(WorkerService.class);
    
    private Server server;
    private ZooKeeper client;
    private String workerName;
    private volatile boolean isShutDown = false;
    private TaskData processingTask;
    
    public WorkerService(Server server) {
        super();
        this.server = server;
        this.client = server.getClient();
    }

    /**
     * 注册worker
     * 
     * @since add by bin.lv 2017年12月6日 上午10:45:23
     */
    public void registerWorker() {
        client.create(ZKConfig.WORKER_PATH+"/Worker", new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL, new RegisterWorker(server), null);
    }

    /**
     * 等待分配任务
     * 
     * @since add by bin.lv 2017年12月6日 上午10:34:05
     */
    public void updateTask() {
        if(isShutDown){
            logger.info(server+"因已经变成主节点，停止worker工作");
            closeWorker();
            return ;
        }
        logger.info(server+"开始抓取任务");
        client.getData(getWorkerAssign(), new WaitTaskWatcher(server), new GetAssignTaskDataCallback(server), workerName);
    }
    
    /**
     * 等待assign被创建
     * 
     * @since add by bin.lv 2017年12月6日 上午10:45:13
     */
    public void waitAssignCreate(){
        logger.info(server+"开始监听"+getWorkerAssign()+"节点");
        client.exists(getWorkerAssign(), new WaitAssignCreateWatcher(server), new CheckAssignCreateCallback(server), null);
    }

    /**
     * 获取自己的Assign中的路径
     * 
     * @since add by bin.lv 2017年12月6日 上午10:44:59
     * @return
     */
    private String getWorkerAssign() {
        return ZKConfig.ASSIGN_PATH+"/"+workerName;
    }

    /**
     * 处理任务
     * 
     * @since add by bin.lv 2017年12月6日 上午10:41:38
     * @param taskData
     */
    public void processTask(TaskData taskData) {
        if(processingTask!=null&&taskData.getTaskPath().equals(processingTask.getTaskPath())){
            logger.info("该任务正在被处理中，不予处理，taskData："+taskData);
            return ;
        }
        processingTask = taskData;
        logger.info("worker开始处理任务："+taskData);
        TU.s(5000);
        taskData.setStatus(TaskStatusEnum.FINISHED);
        logger.info("worker处理任务结束："+taskData);
        updateTask(taskData);
    }

    /**
     * 更新任务状态
     * 
     * @since add by bin.lv 2017年12月6日 上午10:46:40
     * @param taskData
     */
    public void updateTask(TaskData taskData) {
        client.setData(getWorkerAssign(), taskData.getTaskStr().getBytes(), taskData.getAssignVersion(), new UpdateTaskCallback(taskData,server), null);
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public void shutDownWorker() {
        isShutDown = true;
    }
    
    private void closeWorker(){
        client.delete(ZKConfig.WORKER_PATH+ "/" + workerName,-1,new VoidCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx) {
                switch (Code.get(rc)) { 
                case CONNECTIONLOSS:
                    closeWorker();
                    break;
                case NONODE:
                    logger.info("关闭Worker连接成功");
                    break;
                case OK:
                    logger.info("关闭Worker连接成功");
                    break;
                default:
                    throw new RuntimeException(server+this.getClass().getSimpleName()+"存在未处理码："+rc);
                }
            }
        },null);
    }

    public TaskData getProcessingTask() {
        return processingTask;
    }

    public void setProcessingTask(TaskData processingTask) {
        this.processingTask = processingTask;
    }

}
