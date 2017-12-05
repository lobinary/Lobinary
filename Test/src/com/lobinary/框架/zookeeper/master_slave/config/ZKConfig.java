package com.lobinary.框架.zookeeper.master_slave.config;

/**
 * 关于ZK的配置
 * @author bin.lv
 * @since create by bin.lv 2017年12月1日 下午2:44:42
 *
 */
public class ZKConfig {
    
    /**
     * ZK连接地址
     */
    public final static String CONNECTION_STRING = "127.0.0.1:2181";
    
    /**
     * ZK会话超时时间
     */
    public static final int SESSION_TIMEOUT = 3000;

    /**
     * 主节点位置
     */
    public static final String MASTER_PATH = "/master_slave/master";
    /**
     * 主节点父位置
     */
    public static final String MASTER_PARENT_PATH = "/master_slave";

    /**
     * 工作者路径
     */
    public static final String WORKER_PATH = "/master_slave/worker";

    /**
     * 任务分配路径
     */
    public static final String ASSIGN_PATH = "/master_slave/assign";

    /**
     * 当前的任务处理进度号，用来获取下一个任务
     */
    public static final String CURRENT_TASK_NUM = "/master_slave/assign/current_task_num";

    /**
     * 任务列表路径
     */
    public static final String TASKS_PATH = "/master_slave/tasks";
    

}
