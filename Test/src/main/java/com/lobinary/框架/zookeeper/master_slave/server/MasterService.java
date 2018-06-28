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
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lobinary.框架.zookeeper.master_slave.callback.master.AddTaskCallback;
import com.lobinary.框架.zookeeper.master_slave.callback.master.AssignTaskCallback;
import com.lobinary.框架.zookeeper.master_slave.callback.master.ElectMasterCallBack;
import com.lobinary.框架.zookeeper.master_slave.callback.master.GetAssignDataAndRetryTaskCallback;
import com.lobinary.框架.zookeeper.master_slave.callback.master.GetMasterDataCallback;
import com.lobinary.框架.zookeeper.master_slave.callback.master.GetTaskDataAndCreateAssignCallback;
import com.lobinary.框架.zookeeper.master_slave.callback.master.GetTaskDataCallback;
import com.lobinary.框架.zookeeper.master_slave.callback.master.GetTaskNumCallback;
import com.lobinary.框架.zookeeper.master_slave.callback.master.TaskExistsAndCreateAssignCallback;
import com.lobinary.框架.zookeeper.master_slave.callback.master.TaskExistsCallback;
import com.lobinary.框架.zookeeper.master_slave.callback.master.UpdateAssignListCallback;
import com.lobinary.框架.zookeeper.master_slave.callback.master.UpdateAssignStatusCallback;
import com.lobinary.框架.zookeeper.master_slave.callback.master.UpdateOriTaskAndDeleteAssignCallback;
import com.lobinary.框架.zookeeper.master_slave.callback.master.UpdateOriTaskCallback;
import com.lobinary.框架.zookeeper.master_slave.callback.master.UpdateWorksCallback;
import com.lobinary.框架.zookeeper.master_slave.config.ZKConfig;
import com.lobinary.框架.zookeeper.master_slave.enums.TaskStatusEnum;
import com.lobinary.框架.zookeeper.master_slave.model.TaskData;
import com.lobinary.框架.zookeeper.master_slave.util.ZU;
import com.lobinary.框架.zookeeper.master_slave.watcher.master.AssignStatusChangeWatcher;
import com.lobinary.框架.zookeeper.master_slave.watcher.master.MasterChangeWatcher;
import com.lobinary.框架.zookeeper.master_slave.watcher.master.TasksExistsAndCreateAssignWatcher;
import com.lobinary.框架.zookeeper.master_slave.watcher.master.TasksExistsWatcher;
import com.lobinary.框架.zookeeper.master_slave.watcher.master.WorkerConnectionChangeWatcher;

public class MasterService {

    private final static Logger logger = LoggerFactory.getLogger(MasterService.class);

    private Server server;
    private ZooKeeper client;
    private Set<String> workers = new HashSet<String>();
    private Map<String, TaskData> assignCache = new HashMap<String, TaskData>();
    private int currentTaskNum = -1;
    private int currentTaskVersion = -1;

    public MasterService(Server server) {
        super();
        this.server = server;
        this.client = server.getClient();
    }

    /**
     * 查看主节点是否存在并有数据 存在，监控是否存在 不存在，抢节点
     * 
     * 标记① 获取数据并放监控点， 如果存在就不管了， 如果不存在就抢主节点， 如果主节点抢到了，会触发前边放置的监控
     * 如果主节点没抢到，还要获取数据并放置监控，TO标记①
     * 
     * @since add by bin.lv 2017年12月1日 下午3:22:26
     */
    public void checkMaster() {
        logger.info(server + "准备检查主节点状态");
        client.getData(ZKConfig.MASTER_PATH, new MasterChangeWatcher(server), new GetMasterDataCallback(server), null);
    }

    /**
     * 抢主节点
     * 
     * @since add by bin.lv 2017年12月1日 下午6:20:27
     */
    public void electMaster() {
        logger.info(server + "准备抢主节点");
        client.create(ZKConfig.MASTER_PATH, server.getServerId().getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, new ElectMasterCallBack(server), null);
    }

    /**
     * Master开始运行
     * 
     * @since add by bin.lv 2017年12月5日 上午11:12:30
     */
    public void startMaster() {
        updateCurrentTaskNum();
        updateWorker();
    }

    /**
     * 更新当前任务号
     * 
     * @since add by bin.lv 2017年12月5日 下午3:37:19
     */
    public void updateCurrentTaskNum() {
        client.getData(ZKConfig.CURRENT_TASK_NUM, false, new GetTaskNumCallback(server), null);
    }

    /**
     * 更新工人的数据，将依次对工人进行判断是否有工作并监听他们的工作状态
     * 
     * @since add by bin.lv 2017年12月1日 下午6:28:02
     */
    public void updateWorker() {
        logger.info(server + "准备更新worker的工作状态");
        client.getChildren(ZKConfig.WORKER_PATH, new WorkerConnectionChangeWatcher(server), new UpdateWorksCallback(server), null);
    }

    /**
     * 更新Assign的分配任务的子节点，并监听每个分配任务的状态
     * 
     * @since add by bin.lv 2017年12月4日 上午10:49:46
     */
    public void updateAssignList() {
        logger.info("更新Assign的分配任务的子节点，并监听每个分配任务的状态");
        client.getChildren(ZKConfig.ASSIGN_PATH, false, new UpdateAssignListCallback(server), null);
    }

    /**
     * 抓取zk服务器上最新的分配任务数据，并根据数据，来决定如何处理
     * 
     * @since add by bin.lv 2017年12月4日 下午12:00:04
     * @param workerName
     */
    public void updateAssignStatus(String workerName) {
        if (assignCache.containsKey(workerName)) {
            logger.info(workerName + "工作已经被监听，不再挂载监听######################################");
        } else {
            logger.info("准备检查" + workerName + "工作状态");
            client.getData(ZKConfig.ASSIGN_PATH + "/" + workerName, new AssignStatusChangeWatcher(workerName, server), new UpdateAssignStatusCallback(server),
                    workerName);
        }
    }

    /**
     * 通过最近的分配的任务数据，进行处理 完成则重新分配任务，未完成则更新本地分配任务缓存
     * 
     * @since add by bin.lv 2017年12月4日 上午11:59:40
     * @param taskData
     */
    public void updateAssignStatus(TaskData taskData) {
        assignCache.put(taskData.getWorkerName(), taskData);
        if (workers.contains(taskData.getWorkerName())) {
            if (TaskStatusEnum.FINISHED == taskData.getStatus()) {
                logger.info("检查到：" + taskData.getWorkerName() + "工作已经完成：" + taskData);
                updateOriTask(taskData);
                assignTask(taskData.getWorkerName(), taskData.getAssignVersion());
                return;
            } else {
                logger.info(server + "任务未执行完毕，等待其执行完毕后再分配任务" + taskData);
            }
        } else {
            logger.info("检查到：" + taskData.getWorkerName() + "不存在与其对应的工作者，将重新回滚任务" + taskData);
            reassignTask(taskData);
        }
    }

    /**
     * 根据当前任务号，和工作者名称，分配任务
     * 
     * @since add by bin.lv 2017年12月4日 下午3:38:52
     * @param workerName
     * @param currentTaskNum
     */
    public void assignTask(String workerName, int assignVersion) {
        logger.info("准备给" + workerName + "分配任务,准备抓取任务：" + getTaskName(currentTaskNum));
        client.getData(getTaskName(currentTaskNum), false, new GetTaskDataCallback(server, assignVersion), workerName);
    }

    /**
     * 获取最新的任务分配给对应的工作者 1.当前任务数+1, 2.将当前任务标记为处理中 3.更改Assign任务 4.放置assign监听
     * 
     * @since add by bin.lv 2017年12月4日 下午4:02:29
     * @param ctx
     * @param taskData
     */
    public void assignTask(TaskData taskData) {
        List<Op> ops = new ArrayList<Op>();
        ops.add(Op.setData(ZKConfig.CURRENT_TASK_NUM, String.valueOf(currentTaskNum + 1).getBytes(), currentTaskVersion));
        ops.add(Op.setData(taskData.getAssignPath(), taskData.getTaskStr().getBytes(), taskData.getAssignVersion()));
        ops.add(Op.setData(taskData.getTaskPath(), taskData.getTaskStr(TaskStatusEnum.PROCESS).getBytes(), taskData.getOriVersion()));
        client.multi(ops, new AssignTaskCallback(server), taskData);
    }

    /**
     * 重新分配任务
     * 
     * @since add by bin.lv 2017年12月4日 下午2:37:18
     * @param taskData
     */
    public void reassignTask(TaskData taskData) {
        if (TaskStatusEnum.FINISHED == taskData.getStatus()) {
            logger.info("任务执行完成，不予重新分配，更新原始任务信息" + taskData);
            List<Op> ops = new ArrayList<Op>();
            ops.add(Op.setData(taskData.getTaskPath(), taskData.getTaskStr().getBytes(), -1));
            ops.add(Op.delete(taskData.getAssignPath(), taskData.getAssignVersion()));
            client.multi(ops, new UpdateOriTaskAndDeleteAssignCallback(server), taskData);
        } else {
            logger.info("任务未完成，准备重新分发到tasks池中");
            List<Op> ops = new ArrayList<Op>();
            ops.add(Op.create(ZKConfig.TASKS_PATH + "/task", taskData.getTaskStr().getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL));
            ops.add(Op.delete(taskData.getAssignPath(), taskData.getAssignVersion()));
            client.multi(ops, new AddTaskCallback(server), taskData);
        }
    }

    /**
     * 将原始任务更新状态
     * 
     * @since add by bin.lv 2017年12月6日 下午3:06:09
     * @param taskData
     */
    public void updateOriTask(TaskData taskData) {
        logger.info("准备更新原始任务状态：" + taskData);
        client.setData(taskData.getTaskPath(), taskData.getTaskStr().getBytes(), -1, new UpdateOriTaskCallback(server), taskData);
    }

    /**
     * 当task列表没有任务时，增加监听，等待新任务，重新分配
     * 
     * @since add by bin.lv 2017年12月4日 下午3:56:07
     */
    public void waitNewTask() {
        logger.info("暂时没有任务分配，等待新任务");
        client.exists(getTaskName(currentTaskNum), new TasksExistsWatcher(server), new TaskExistsCallback(server), null);
    }

    /**
     * 当task列表没有任务时，增加监听，等待新任务，重新分配
     * 
     * @since add by bin.lv 2017年12月4日 下午3:56:07
     */
    public void waitNewTaskAndCreateAssign(String workerName) {
        logger.info("开始监测任务" + getTaskName(currentTaskNum) + "变动");
        client.exists(getTaskName(currentTaskNum), TasksExistsAndCreateAssignWatcher.getTaskExistWatcher(getTaskName(currentTaskNum), server, workerName),
                new TaskExistsAndCreateAssignCallback(server), workerName);
    }

    /**
     * 根据工作者名称重新分配任务 找到这个工作者的名字，并重新分配他的assign中的工作
     * 
     * @since add by bin.lv 2017年12月5日 上午11:00:30
     * @param wnm
     */
    public void reassignTask(String workerName) {
        client.getData(getAssignPath(workerName), false, new GetAssignDataAndRetryTaskCallback(server), workerName);
    }

    /**
     * 移除工作者
     * 
     * @since add by bin.lv 2017年12月5日 上午11:05:52
     * @param workerName
     */
    public void removeWorker(String workerName) {
        if (!workers.contains(workerName)) {
            throw new RuntimeException(server + "工作者名称有问题：" + workerName);
        }
        logger.info("查找出没有工作者的Assign记录：" + workerName + "，将Assign任务回归到任务池");
        reassignTask(workerName);
        workers.remove(workerName);
    }

    /**
     * 更新工作者列表
     * 
     * @since add by bin.lv 2017年12月5日 上午11:04:24
     * @param children
     */
    public void addWorkers(List<String> children) {
        if (children.size() == 0) {
            logger.info(server + "未发现可以工作的worker，等待监听到新的worker再分配任务");
        }
        for (String wnm : workers) {
            boolean has = false;
            for (String wn : children) {
                if (wnm.equals(wn)) {
                    has = true;
                    break;
                }
            }
            if (!has) {
                removeWorker(wnm);
            }
        }
        for (String wn : children) {
            if (!workers.contains(wn)) {
                workers.add(wn);
                createAssign(wn);
            }
        }
        updateAssignList();
    }

    /**
     * 第一次创建Assign任务
     * 
     * @since add by bin.lv 2017年12月5日 下午2:54:54
     * @param wn
     */
    public void createAssign(String workerName) {
        logger.info("发现新工作者：" + workerName + ",准备对其创建Assign型分配任务");
        client.getData(getTaskName(currentTaskNum), false, new GetTaskDataAndCreateAssignCallback(server), workerName);
    }

    /**
     * 第一次创建Assign任务
     * 
     * @since add by bin.lv 2017年12月5日 下午2:54:54
     * @param wn
     */
    public void createAssign(TaskData taskData) {
        List<Op> ops = new ArrayList<Op>();
        ops.add(Op.setData(ZKConfig.CURRENT_TASK_NUM, String.valueOf(currentTaskNum + 1).getBytes(), currentTaskVersion));
        ops.add(Op.create(taskData.getAssignPath(), taskData.getTaskStr().getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT));
        ops.add(Op.setData(taskData.getTaskPath(), taskData.getTaskStr(TaskStatusEnum.PROCESS).getBytes(), taskData.getOriVersion()));
        client.multi(ops, new AssignTaskCallback(server), taskData);
    }

    /**
     * 获取Task名称
     * 
     * @since add by bin.lv 2017年12月6日 下午1:53:54
     * @param taskNum
     * @return
     */
    public String getTaskName(int taskNum) {
        // Worker0000000011
        return ZKConfig.TASKS_PATH + "/task" + ZU.beforeFillZero(taskNum, 10);
    }

    public void setCurrentTaskData(int currentTaskNum, int currentTaskVersion) {
        this.currentTaskNum = currentTaskNum;
        this.currentTaskVersion = currentTaskVersion;
        logger.info("准备更新当前任务序号：" + currentTaskNum + ",当前任务序号版本：" + currentTaskVersion);
    }

    public int getCurrentTaskNum() {
        return currentTaskNum;
    }

    public String getAssignPath(String workerName) {
        return ZKConfig.ASSIGN_PATH + "/" + workerName;
    }

    /**
     * 移除watcher
     * 
     * @since add by bin.lv 2017年12月8日 下午5:48:51
     * @param workerName
     */
    public void removeAssignWatcher(String workerName) {
        assignCache.remove(workerName);
    }

    /**
     * 找到没工作的工作者
     * 
     * @since add by bin.lv 2017年12月8日 下午6:31:37
     */
    public void findWaitTaskWorker() {
        for (String workerName : assignCache.keySet()) {
            TaskData taskData = assignCache.get(workerName);
            if (TaskStatusEnum.FINISHED == taskData.getStatus()) {
                assignTask(taskData.getWorkerName(), taskData.getAssignVersion());
            }
        }
    }
}
