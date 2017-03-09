/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2008, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.jmx.mbeanserver;

import javax.management.MBeanServer;
import javax.management.MBeanServerDelegate;


/**
 * Extends the MBeanServer interface to
 * provide methods for getting the MetaData and MBeanServerInstantiator
 * objects associated with an MBeanServer.
 *
 * <p>
 *  扩展MBeanServer接口以提供用于获取与MBeanServer关联的MetaData和MBeanServerInstantiator对象的方法。
 * 
 * 
 * @since 1.5
 */
public interface SunJmxMBeanServer
    extends MBeanServer {

    /**
     * Return the MBeanInstantiator associated to this MBeanServer.
     * <p>
     *  返回与此MBeanServer关联的MBeanInstantiator。
     * 
     * 
     * @exception UnsupportedOperationException if
     *            {@link MBeanServerInterceptor}s
     *            are not enabled on this object.
     * @see #interceptorsEnabled
     */
    public MBeanInstantiator getMBeanInstantiator();

    /**
     * Tell whether {@link MBeanServerInterceptor}s are enabled on this
     * object.
     * <p>
     *  判断是否对此对象启用了{@link MBeanServerInterceptor}。
     * 
     * 
     * @return <code>true</code> if {@link MBeanServerInterceptor}s are
     *         enabled.
     * @see #getMBeanServerInterceptor
     * @see #setMBeanServerInterceptor
     * @see #getMBeanInstantiator
     * @see com.sun.jmx.mbeanserver.JmxMBeanServerBuilder
     **/
    public boolean interceptorsEnabled();

    /**
     * Return the MBeanServerInterceptor.
     * <p>
     *  返回MBeanServerInterceptor。
     * 
     * 
     * @exception UnsupportedOperationException if
     *            {@link MBeanServerInterceptor}s
     *            are not enabled on this object.
     * @see #interceptorsEnabled
     **/
    public MBeanServer getMBeanServerInterceptor();

    /**
     * Set the MBeanServerInterceptor.
     * <p>
     *  设置MBeanServerInterceptor。
     * 
     * 
     * @exception UnsupportedOperationException if
     *            {@link MBeanServerInterceptor}s
     *            are not enabled on this object.
     * @see #interceptorsEnabled
     **/
    public void setMBeanServerInterceptor(MBeanServer interceptor);

    /**
     * <p>Return the MBeanServerDelegate representing the MBeanServer.
     * Notifications can be sent from the MBean server delegate using
     * the method {@link MBeanServerDelegate#sendNotification}
     * in the returned object.</p>
     *
     * <p>
     *  <p>返回表示MBeanServer的MBeanServerDelegate。
     * 通知可以使用返回的对象中的{@link MBeanServerDelegate#sendNotification}方法从MBean服务器委托发送。</p>。
     */
    public MBeanServerDelegate getMBeanServerDelegate();

}
