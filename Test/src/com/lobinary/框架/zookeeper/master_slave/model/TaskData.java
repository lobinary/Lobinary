package com.lobinary.框架.zookeeper.master_slave.model;

import com.lobinary.框架.zookeeper.master_slave.enums.TaskStatusEnum;

public class TaskData {
    
    private String workerName;
    private String taskPath;
    private int taskVersion;
    private TaskStatusEnum status;

    public TaskData(String workerName, byte[] data) {
        String taskDataStr = new String(data);
        System.out.println(taskDataStr);
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


    @Override
    public String toString() {
        return "Task [workerName=" + workerName + ", status=" + status + "]";
    }
    
    public String getTaskStr(){
        return "this is test str";
    }


    public String getTaskPath() {
        return taskPath;
    }


    public void setTaskPath(String taskPath) {
        this.taskPath = taskPath;
    }


    public int getTaskVersion() {
        return taskVersion;
    }


    public void setTaskVersion(int taskVersion) {
        this.taskVersion = taskVersion;
    }

    /**
     * 获取该任务的分配路径
     * 
     * @since add by bin.lv 2017年12月4日 下午4:36:54
     * @return
     */
    public String getAssignPath() {
        return null;
    }

}
