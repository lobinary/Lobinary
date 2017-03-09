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

public interface ThreadPoolManager extends Closeable
{
    /**
    * This method will return an instance of the threadpool given a threadpoolId,
    * that can be used by any component in the app. server.
    *
    * <p>
    *  此方法将返回线程池的一个实例,给定一个threadpoolId,可以由应用程序中的任何组件使用。服务器。
    * 
    * 
    * @throws NoSuchThreadPoolException thrown when invalid threadpoolId is passed
    * as a parameter
    */
    public ThreadPool getThreadPool(String threadpoolId) throws NoSuchThreadPoolException;

    /**
    * This method will return an instance of the threadpool given a numeric threadpoolId.
    * This method will be used by the ORB to support the functionality of
    * dedicated threadpool for EJB beans
    *
    * <p>
    *  此方法将返回给定数字threadpoolId的线程池的实例。 ORB将使用此方法来支持EJB bean的专用线程池的功能
    * 
    * 
    * @throws NoSuchThreadPoolException thrown when invalidnumericIdForThreadpool is passed
    * as a parameter
    */
    public ThreadPool getThreadPool(int numericIdForThreadpool) throws NoSuchThreadPoolException;

    /**
    * This method is used to return the numeric id of the threadpool, given a String
    * threadpoolId. This is used by the POA interceptors to add the numeric threadpool
    * Id, as a tagged component in the IOR. This is used to provide the functionality of
    * dedicated threadpool for EJB beans
    * <p>
    *  此方法用于返回线程池的数字id,给定String threadpoolId。这由POA拦截器用于添加数字线程池Id,作为IOR中的已标记组件。这用于为EJB bean提供专用线程池的功能
    * 
    */
    public int  getThreadPoolNumericId(String threadpoolId);

    /**
    * Return a String Id for a numericId of a threadpool managed by the threadpool
    * manager
    * <p>
    *  返回由threadpool管理器管理的线程池的numericId的String Id
    * 
    */
    public String getThreadPoolStringId(int numericIdForThreadpool);

    /**
    * Returns the first instance of ThreadPool in the ThreadPoolManager
    * <p>
    *  返回ThreadPoolManager中的第一个ThreadPool实例
    * 
    */
    public ThreadPool getDefaultThreadPool();

    /**
     * Return an instance of ThreadPoolChooser based on the componentId that was
     * passed as argument
     * <p>
     *  基于作为参数传递的componentId返回ThreadPoolChooser的实例
     * 
     */
    public ThreadPoolChooser getThreadPoolChooser(String componentId);

    /**
     * Return an instance of ThreadPoolChooser based on the componentIndex that was
     * passed as argument. This is added for improved performance so that the caller
     * does not have to pay the cost of computing hashcode for the componentId
     * <p>
     *  基于作为参数传递的componentIndex返回ThreadPoolChooser的实例。这是为了提高性能而添加的,以便调用者不必为componentId支付计算散列码的成本
     * 
     */
    public ThreadPoolChooser getThreadPoolChooser(int componentIndex);

    /**
     * Sets a ThreadPoolChooser for a particular componentId in the ThreadPoolManager. This
     * would enable any component to add a ThreadPoolChooser for their specific use
     * <p>
     *  为ThreadPoolManager中的特定componentId设置ThreadPoolChooser。这将使任何组件能够为其特定用途添加ThreadPoolChooser
     * 
     */
    public void setThreadPoolChooser(String componentId, ThreadPoolChooser aThreadPoolChooser);

    /**
     * Gets the numeric index associated with the componentId specified for a
     * ThreadPoolChooser. This method would help the component call the more
     * efficient implementation i.e. getThreadPoolChooser(int componentIndex)
     * <p>
     * 获取与为ThreadPoolChooser指定的componentId关联的数字索引。
     * 这个方法将帮助组件调用更有效的实现,即getThreadPoolChooser(int componentIndex)。
     */
    public int getThreadPoolChooserNumericId(String componentId);
}

// End of file.
