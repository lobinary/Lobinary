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


public interface ThreadPoolChooser
{
    /**
     * This method is used to return an instance of ThreadPool based on the
     * strategy/policy implemented in the ThreadPoolChooser from the set of
     * ThreadPools allowed to be used by the ORB. Typically, the set of
     * ThreadPools would be specified by passing the threadpool-ids
     * configured in the ORB element of the domain.xml of the appserver.
     * <p>
     *  此方法用于返回ThreadPool的实例,该实例基于ThreadPoolChooser中实施的策略/策略,该实例由允许由ORB使用的ThreadPools集合构成。
     * 通常,通过传递在appserver的domain.xml的ORB元素中配置的threadpool-id来指定ThreadPools集合。
     * 
     */
    public ThreadPool getThreadPool();

    /**
     * This method is used to return an instance of ThreadPool that is obtained
     * by using the id argument passed to it. This method will be used in
     * situations where the threadpool id is known to the caller e.g. by the
     * connection object or looking at the high order bits of the request id
     * <p>
     *  此方法用于返回通过使用传递给它的id参数获得的ThreadPool的实例。该方法将用于其中线程池id对于调用者是已知的情况。通过连接对象或查看请求id的高阶位
     * 
     */
    public ThreadPool getThreadPool(int id);

    /**
     * This method is a convenience method to see what threadpool-ids are being
     * used by the ThreadPoolChooser
     * <p>
     *  此方法是一个方便的方法,以查看ThreadPoolChooser正在使用的线程池标识
     */
    public String[] getThreadPoolIds();
}
