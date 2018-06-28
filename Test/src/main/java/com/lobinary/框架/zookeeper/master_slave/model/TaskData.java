package com.lobinary.框架.zookeeper.master_slave.model;

import java.util.HashMap;
import java.util.Map;

import com.lobinary.工具类.JU;
import com.lobinary.框架.zookeeper.master_slave.config.ZKConfig;
import com.lobinary.框架.zookeeper.master_slave.enums.TaskStatusEnum;

public class TaskData {
    
    private String workerName;
    private String taskPath;
    private String data;
    private int oriVersion;
    private int assignVersion;
    private TaskStatusEnum status;

    public TaskData(String data, TaskStatusEnum status) {
        super();
        this.data = data;
        this.status = status;
    }

    public TaskData(String workerName,int assignVersion,byte[] data) {
        String taskDataStr = new String(data);
        this.workerName = workerName;
        this.assignVersion = assignVersion;
        @SuppressWarnings("unchecked")
        Map<String,String> taskData = JU.toBean(taskDataStr, Map.class);
        this.data = taskData.get("data");
        this.status = TaskStatusEnum.valueOf(taskData.get("status"));
        this.taskPath = taskData.get("taskPath");
        this.oriVersion = Integer.parseInt(taskData.get("oriVersion"));
    }

    public TaskData(String workerName,String taskPath,int oriVersion,byte[] data) {
        String taskDataStr = new String(data);
        this.workerName = workerName;
        this.oriVersion = oriVersion;
        this.taskPath = taskPath;
        @SuppressWarnings("unchecked")
        Map<String,String> taskData = JU.toBean(taskDataStr, Map.class);
        this.data = taskData.get("data");
        this.status = TaskStatusEnum.valueOf(taskData.get("status"));
    }

    public TaskStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TaskStatusEnum status) {
        this.status = status;
    }


    public String getWorkerName() {
        return workerName;
    }


    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    
    public String getTaskStr(){
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("data", data);
        paramMap.put("status", status.name());
        paramMap.put("taskPath", taskPath);
        paramMap.put("oriVersion", ""+oriVersion);
        return JU.toJSONString(paramMap);
    }


    public String getTaskPath() {
        return taskPath;
    }


    public void setTaskPath(String taskPath) {
        this.taskPath = taskPath;
    }

    /**
     * 获取该任务的分配路径
     * 
     * @since add by bin.lv 2017年12月4日 下午4:36:54
     * @return
     */
    public String getAssignPath() {
        return ZKConfig.ASSIGN_PATH + "/" + this.workerName;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getOriVersion() {
        return oriVersion;
    }

    public void setOriVersion(int oriVersion) {
        this.oriVersion = oriVersion;
    }

    public int getAssignVersion() {
        return assignVersion;
    }

    public void setAssignVersion(int assignVersion) {
        this.assignVersion = assignVersion;
    }

    @Override
    public String toString() {
        return "TaskData [workerName=" + workerName + ", taskPath=" + taskPath + ", data=" + data + ", oriVersion=" + oriVersion + ", assignVersion=" + assignVersion
                + ", status=" + status + "]";
    }

    public String getTaskStr(TaskStatusEnum st) {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("data", data);
        paramMap.put("status", st);
        paramMap.put("taskPath", taskPath);
        paramMap.put("oriVersion", oriVersion);
        return JU.toJSONString(paramMap);
    }

}
