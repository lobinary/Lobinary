/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2007, Oracle and/or its affiliates. All rights reserved.
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

package javax.management.remote;

import javax.management.MBeanServer;

/**
 * <p>An object of this class implements the MBeanServer interface and
 * wraps another object that also implements that interface.
 * Typically, an implementation of this interface performs some action
 * in some or all methods of the <code>MBeanServer</code> interface
 * before and/or after forwarding the method to the wrapped object.
 * Examples include security checking and logging.</p>
 *
 * <p>
 *  <p>此类的对象实现MBeanServer接口,并且包装另一个也实现该接口的对象。
 * 通常,此接口的实现在将该方法转发到包装的对象之前和/或之后在<code> MBeanServer </code>接口的一些或所有方法中执行一些动作。示例包括安全检查和日志记录。</p>。
 * 
 * 
 * @since 1.5
 */
public interface MBeanServerForwarder extends MBeanServer {

    /**
     * Returns the MBeanServer object to which requests will be forwarded.
     *
     * <p>
     *  返回请求将转发到的MBeanServer对象。
     * 
     * 
     * @return the MBeanServer object to which requests will be forwarded,
     * or null if there is none.
     *
     * @see #setMBeanServer
     */
    public MBeanServer getMBeanServer();

    /**
     * Sets the MBeanServer object to which requests will be forwarded
     * after treatment by this object.
     *
     * <p>
     *  设置MBeanServer对象,请求在此对象处理后将转发到此对象。
     * 
     * @param mbs the MBeanServer object to which requests will be forwarded.
     *
     * @exception IllegalArgumentException if this object is already
     * forwarding to an MBeanServer object or if <code>mbs</code> is
     * null or if <code>mbs</code> is identical to this object.
     *
     * @see #getMBeanServer
     */
    public void setMBeanServer(MBeanServer mbs);
}
