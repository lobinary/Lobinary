package com.lobinary.框架.zookeeper.master_slave.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Op;
import org.apache.zookeeper.ZooDefs;

import com.lobinary.框架.zookeeper.master_slave.callback.master.AssignTaskCallback;
import com.lobinary.框架.zookeeper.master_slave.callback.master.ElectMasterCallBack;
import com.lobinary.框架.zookeeper.master_slave.callback.master.GetMasterDataCallback;
import com.lobinary.框架.zookeeper.master_slave.callback.master.GetTaskDataCallback;
import com.lobinary.框架.zookeeper.master_slave.callback.master.GetTaskNumCallback;
import com.lobinary.框架.zookeeper.master_slave.callback.master.TaskExistsCallback;
import com.lobinary.框架.zookeeper.master_slave.callback.master.UpdateAssignListCallback;
import com.lobinary.框架.zookeeper.master_slave.callback.master.UpdateAssignStatusCallback;
import com.lobinary.框架.zookeeper.master_slave.callback.master.UpdateWorksCallback;
import com.lobinary.框架.zookeeper.master_slave.config.ZKConfig;
import com.lobinary.框架.zookeeper.master_slave.enums.TaskStatusEnum;
import com.lobinary.框架.zookeeper.master_slave.model.TaskData;
import com.lobinary.框架.zookeeper.master_slave.watcher.master.AssignStatusChangeWatcher;
import com.lobinary.框架.zookeeper.master_slave.watcher.master.MasterChangeWatcher;
import com.lobinary.框架.zookeeper.master_slave.watcher.master.TasksExistsWatcher;
import com.lobinary.框架.zookeeper.master_slave.watcher.master.WorkerConnectionChangeWatcher;

public class MasterService {
    
    private Server server;
    private Set<String> workers = new HashSet<String>();
    private Map<String,TaskStatusEnum> workersStatusCache = new HashMap<String,TaskStatusEnum>();
    private int currentTaskNum = -1;
    private int currentTaskVersion = -1;

    
    public MasterService(Server server) {
        super();
        this.server = server;
    }

    /**
     * 查看主节点是否存在并有数据
     *      存在，监控是否存在
     *      不存在，抢节点
     *      
     *  标记① 获取数据并放监控点，
     *          如果存在就不管了，
     *          如果不存在就抢主节点，
     *              如果主节点抢到了，会触发前边放置的监控
     *              如果主节点没抢到，还要获取数据并放置监控，TO标记①
     * 
     * @since add by bin.lv 2017年12月1日 下午3:22:26
     */
    public void checkMaster() {
        System.out.println(server+"准备检查主节点状态");
        server.getClient().getData(ZKConfig.MASTER_PATH, new MasterChangeWatcher(server), new GetMasterDataCallback(server), null);
    }

    /**
     * 抢主节点
     * 
     * @since add by bin.lv 2017年12月1日 下午6:20:27
     */
    public void electMaster() {
        System.out.println(server+"准备抢主节点");
        server.getClient().create(ZKConfig.MASTER_PATH, server.getServerId().getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, new ElectMasterCallBack(server), null);
    }
    
    /**
     * Master开始运行
     * 
     * @since add by bin.lv 2017年12月5日 上午11:12:30
     */
    public void startMaster(){
        updateWorker();
        updateAssignList();
    }

    /**
     * 更新工人的数据，将依次对工人进行判断是否有工作并监听他们的工作状态
     * 
     * @since add by bin.lv 2017年12月1日 下午6:28:02
     */
    public void updateWorker(){
        System.out.println(server+"准备更新worker的工作状态");
        server.getClient().getChildren(ZKConfig.WORKER_PATH, new WorkerConnectionChangeWatcher(server), new UpdateWorksCallback(server), null);
    }

    /**
     * 更新Assign的分配任务的子节点，并监听每个分配任务的状态
     * 
     * @since add by bin.lv 2017年12月4日 上午10:49:46
     */
    public void updateAssignList() {
        server.getClient().getChildren(ZKConfig.ASSIGN_PATH,false,new UpdateAssignListCallback(server),null);
    }

    /**
     * 抓取zk服务器上最新的分配任务数据，并根据数据，来决定如何处理
     * 
     * @since add by bin.lv 2017年12月4日 下午12:00:04
     * @param workerName
     */
    public void updateAssignStatus(String workerName){
        server.getClient().getData(ZKConfig.ASSIGN_PATH+"/"+workerName,new AssignStatusChangeWatcher(server),new UpdateAssignStatusCallback(server),workerName);
    }

    /**
     * 通过最近的分配的任务数据，进行处理
     * 完成则重新分配任务，未完成则更新本地分配任务缓存
     * 
     * @since add by bin.lv 2017年12月4日 上午11:59:40
     * @param taskData
     */
    public void updateAssignStatus(TaskData taskData){
        if(workers.contains(taskData.getWorkerName())){
            if(TaskStatusEnum.FINISHED==taskData.getStatus()){
                assignTask(taskData.getWorkerName());
                return ;
            }else{
                System.out.println(server+"任务未执行完毕，等待其执行完毕后再分配任务"+taskData);
            }
        }else{
            reassignTask(taskData);
        }
        workersStatusCache.put(taskData.getWorkerName(), taskData.getStatus());
    }

    /**
     * 向worker分配任务到assign中
     *  抓取一条任务，如果任务列表为空则放置监听，等待最新的任务，这个时候会阻塞整个任务进程的执行，所有的watcher和callback都会被阻塞，都不进行执行
     * 
     * @since add by bin.lv 2017年12月4日 上午11:52:38
     * @param workerName
     */
    public void assignTask(String workerName){
        if(currentTaskNum==-1){
            server.getClient().getData(ZKConfig.CURRENT_TASK_NUM, false, new GetTaskNumCallback(server), workerName);
        }else{
            assignTask(workerName,currentTaskNum);
        }
    }
    
    /**
     * 根据当前任务号，和工作者名称，分配任务
     * 
     * @since add by bin.lv 2017年12月4日 下午3:38:52
     * @param workerName
     * @param currentTaskNum
     */
    public void assignTask(String workerName,int currentTaskNum) {
        server.getClient().getData(getTaskName(currentTaskNum),false,new GetTaskDataCallback(server),workerName);
    }
    
    /**
     * 当task列表没有任务时，增加监听，等待新任务，重新分配
     * 
     * @since add by bin.lv 2017年12月4日 下午3:56:07
     */
    public void waitNewTask() {
        server.getClient().exists(getTaskName(currentTaskNum),new TasksExistsWatcher(server),new TaskExistsCallback(server),null);
    }

    /**
     * 获取最新的任务分配给对应的工作者
     *  1.当前任务数+1,
     *  2.将当前任务标记为处理中
     *  3.更改Assign任务
     *  4.放置assign监听
     * 
     * @since add by bin.lv 2017年12月4日 下午4:02:29
     * @param ctx
     * @param taskData
     */
    public void assignTask(TaskData taskData) {
        List<Op> ops = new ArrayList<Op>();
        ops.add(Op.setData(ZKConfig.CURRENT_TASK_NUM, String.valueOf(currentTaskNum).getBytes(), currentTaskVersion));
        ops.add(Op.setData(taskData.getTaskPath(), taskData.getTaskStr().getBytes(), taskData.getTaskVersion()));
        ops.add(Op.setData(taskData.getAssignPath(), taskData.getTaskStr().getBytes(), taskData.getTaskVersion()));
        server.getClient().multi(ops, new AssignTaskCallback(server), taskData);
    }
    
    /**
     * 重新分配任务
     * 
     * @since add by bin.lv 2017年12月4日 下午2:37:18
     * @param taskData
     */
    private void reassignTask(TaskData taskData) {
        // TODO Auto-generated method stub
    }

    /**
     * 根据工作者名称重新分配任务
     * 找到这个工作者的名字，并重新分配他的assign中的工作
     * 
     * @since add by bin.lv 2017年12月5日 上午11:00:30
     * @param wnm
     */
    private void reassignTask(String wnm) {
        // TODO Auto-generated method stub
    }
    
    /**
     * 移除工作者
     * 
     * @since add by bin.lv 2017年12月5日 上午11:05:52
     * @param workerName
     */
    public void removeWorker(String workerName){
        if(!workers.contains(workerName)){
            throw new RuntimeException(server+"工作者名称有问题："+workerName);
        }
        reassignTask(workerName);
        workers.remove(workerName);
    }

    /**
     * 更新工作者列表
     * 
     * @since add by bin.lv 2017年12月5日 上午11:04:24
     * @param children
     */
    public void addWorkers(List<String> children){
        for(String wnm:workers){
            boolean has = false;
            for(String wn:children) {
                if(wnm.equals(wn)){
                    has = true;
                    break;
                }
            }
            if(!has){
                removeWorker(wnm);
            }
        }
        for(String wn:children){
            workers.add(wn);
        }
    }

    /**
     * 更新现有的任务
     * 
     * @since add by bin.lv 2017年12月5日 上午11:14:39
     * @param workerName
     */
    public void addAssign(String workerName) {
        if(!workersStatusCache.containsKey(workerName)){
            workersStatusCache.put(workerName, null);
        }
    }
    
    public String getTaskName(int taskNum){
        return ZKConfig.TASKS_PATH + taskNum;
    }

    public void setCurrentTaskData(int currentTaskNum,int currentTaskVersion){
        this.currentTaskNum = currentTaskNum;
        this.currentTaskVersion = currentTaskVersion;
    }

    public int getCurrentTaskNum() {
        return currentTaskNum;
    }
}
