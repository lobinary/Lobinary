/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package com.sun.corba.se.spi.orbutil.threadpool;

public interface WorkQueue
{

    /**
    * This method is used to add work to the WorkQueue
    * <p>
    *  此方法用于向WorkQueue添加工作
    * 
    */
    public void addWork(Work aWorkItem);

    /**
    * This method will return the name of the WorkQueue.
    * <p>
    *  此方法将返回WorkQueue的名称。
    * 
    */
    public String getName();

    /**
    * Returns the total number of Work items added to the Queue.
    * <p>
    *  返回添加到队列中的工作项的总数。
    * 
    */
    public long totalWorkItemsAdded();

    /**
    * Returns the total number of Work items in the Queue to be processed.
    * <p>
    *  返回要处理的队列中的工作项的总数。
    * 
    */
    public int workItemsInQueue();

    /**
    * Returns the average time a work item is waiting in the queue before
    * getting processed.
    * <p>
    *  返回工作项在处理之前在队列中等待的平均时间。
    * 
    */
    public long averageTimeInQueue();

    /**
     * Set the ThreadPool instance servicing this WorkQueue
     * <p>
     *  设置维护此WorkQueue的ThreadPool实例
     * 
     */
    public void setThreadPool(ThreadPool aThreadPool);

    /**
     * Get the ThreadPool instance servicing this WorkQueue
     * <p>
     *  获取ThreadPool实例服务此WorkQueue
     */
    public ThreadPool getThreadPool();
}

// End of file.
