/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2012, Oracle and/or its affiliates. All rights reserved.
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

import java.io.Closeable;

/** This interface defines a thread pool execution service.  The ORB uses this
 * interface, which preceeds the JDK 5 ExecutorService.  Note that the close
 * method must be called in order to reclaim thread resources.
 * <p>
 *  接口,它先于JDK 5 ExecutorService。注意,必须调用close方法才能回收线程资源。
 * 
 */
public interface ThreadPool extends Closeable
{

    /**
    * This method will return any instance of the WorkQueue. If the ThreadPool
    * instance only services one WorkQueue then that WorkQueue instance will
    * be returned. If there are more than one WorkQueues serviced by this
    * ThreadPool, then this method would return a WorkQueue based on the
    * implementation of the class that implements this interface. For PE 8.0 we
    * would return a WorkQueue in a roundrobin fashion everytime this method
    * is called. In the future we could allow pluggability of  Policy objects for this.
    * <p>
    *  此方法将返回WorkQueue的任何实例。如果ThreadPool实例只服务一个WorkQueue,那么将返回该WorkQueue实例。
    * 如果有多个WorkQueue由此ThreadPool服务,则此方法将返回一个WorkQueue基于实现此接口的类的实现。
    * 对于PE 8.0,每次调用此方法时,都会以roundrobin方式返回WorkQueue。在将来,我们可以允许Policy对象的可插入性。
    * 
    */
    public WorkQueue getAnyWorkQueue();

    /**
    * This method will return an instance of the of the WorkQueue given a queueId.
    * This will be useful in situations where there are more than one WorkQueues
    * managed by the ThreadPool and the user of the ThreadPool wants to always use
    * the same WorkQueue for doing the Work.
    * If the number of WorkQueues in the ThreadPool are 10, then queueIds will go
    * from 0-9
    *
    * <p>
    *  此方法将返回给定queueId的WorkQueue的实例。这在ThreadPool管理多个WorkQueue并且ThreadPool的用户希望始终使用相同的WorkQueue执行Work时非常有用。
    * 如果ThreadPool中的WorkQueues的数量是10,那么queueIds将从0-9。
    * 
    * 
    * @throws NoSuchWorkQueueException thrown when queueId passed is invalid
    */
    public WorkQueue getWorkQueue(int queueId) throws NoSuchWorkQueueException;

    /**
    * This method will return the number of WorkQueues serviced by the threadpool.
    * <p>
    *  此方法将返回线程池服务的WorkQueues数。
    * 
    */
    public int numberOfWorkQueues();

    /**
    * This method will return the minimum number of threads maintained by the threadpool.
    * <p>
    *  此方法将返回线程池维护的最小线程数。
    * 
    */
    public int minimumNumberOfThreads();

    /**
    * This method will return the maximum number of threads in the threadpool at any
    * point in time, for the life of the threadpool
    * <p>
    *  此方法将返回线程池中在任何时间点的线程池的最大线程数,对于线程池的生命周期
    * 
    */
    public int maximumNumberOfThreads();

    /**
    * This method will return the time in milliseconds when idle threads in the threadpool are
    * removed.
    * <p>
    * 此方法将返回删除线程池中的空闲线程的时间(以毫秒为单位)。
    * 
    */
    public long idleTimeoutForThreads();

    /**
    * This method will return the current number of threads in the threadpool. This method
    * returns a value which is not synchronized.
    * <p>
    *  此方法将返回线程池中当前的线程数。此方法返回未同步的值。
    * 
    */
    public int currentNumberOfThreads();

    /**
    * This method will return the number of available threads in the threadpool which are
     * waiting for work. This method returns a value which is not synchronized.
     * <p>
     *  此方法将返回线程池中正在等待工作的可用线程数。此方法返回未同步的值。
     * 
    */
    public int numberOfAvailableThreads();

    /**
    * This method will return the number of busy threads in the threadpool
    * This method returns a value which is not synchronized.
    * <p>
    *  此方法将返回线程池中的忙线程数此方法返回未同步的值。
    * 
    */
    public int numberOfBusyThreads();

    /**
    * This method returns the number of Work items processed by the threadpool
    * <p>
    *  此方法返回线程池处理的工作项数
    * 
    */
    public long currentProcessedCount();

     /**
     * This method returns the average elapsed time taken to complete a Work
     * item.
     * <p>
     *  此方法返回完成工作项所花费的平均耗用时间。
     * 
     */
    public long averageWorkCompletionTime();

    /**
    * This method will return the name of the threadpool.
    * <p>
    *  此方法将返回线程池的名称。
    */
    public String getName();

}

// End of file.
