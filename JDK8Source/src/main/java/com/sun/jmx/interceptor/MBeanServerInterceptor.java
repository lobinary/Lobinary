/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, 2008, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.jmx.interceptor;


import java.io.ObjectInputStream;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.OperationsException;
import javax.management.ReflectionException;
import javax.management.loading.ClassLoaderRepository;

/**
 * <p>This interface specifies the behavior to be implemented by an
 * MBean Server Interceptor.  An MBean Server Interceptor has
 * essentially the same interface as an MBean Server.  An MBean Server
 * forwards received requests to its default interceptor, which may
 * handle them itself or forward them to other interceptors.  The
 * default interceptor may be changed via the {@link
 * com.sun.jmx.mbeanserver.SunJmxMBeanServer#setMBeanServerInterceptor}
 * method.</p>
 *
 * <p>The initial default interceptor provides the standard MBean
 * Server behavior.  It handles a collection of named MBeans, each
 * represented by a Java object.  A replacement default interceptor
 * may build on this behavior, for instance by adding logging or
 * security checks, before forwarding requests to the initial default
 * interceptor.  Or, it may route each request to one of a number of
 * sub-interceptors, for instance based on the {@link ObjectName} in
 * the request.</p>
 *
 * <p>An interceptor, default or not, need not implement MBeans as
 * Java objects, in the way that the initial default interceptor does.
 * It may instead implement <em>virtual MBeans</em>, which do not
 * exist as Java objects when they are not in use.  For example, these
 * MBeans could be implemented by forwarding requests to a database,
 * or to a remote MBean server, or by performing system calls to query
 * or modify system resources.</p>
 *
 * <p>
 *  <p>此接口指定要由MBean Server拦截器实施的行为。 MBean Server拦截器具有与MBean Server基本相同的接口。
 *  MBean服务器将接收的请求转发到其默认拦截器,它可以自己处理它们或将它们转发给其他拦截器。
 * 默认拦截器可以通过{@link com.sun.jmx.mbeanserver.SunJmxMBeanServer#setMBeanServerInterceptor}方法更改。</p>。
 * 
 *  <p>初始缺省拦截器提供标准MBean Server行为。它处理命名MBean的集合,每个都由Java对象表示。
 * 替换默认拦截器可以基于此行为,例如通过在将请求转发到初始默认拦截器之前添加日志记录或安全检查。或者,它可以将每个请求路由到多个子拦截器中的一个,例如基于请求中的{@link ObjectName}。
 * </p>。
 * 
 *  <p>一个拦截器,默认或不是,不需要实现MBean作为Java对象,在初始默认拦截器的方式。它可以改为实现<em> virtual MBean </em>,它们在不使用时不作为Java对象存在。
 * 例如,这些MBean可以通过将请求转发到数据库或远程MBean服务器,或通过执行系统调用来查询或修改系统资源来实现。</p>。
 * 
 * 
 * @since 1.5
 */
public interface MBeanServerInterceptor extends MBeanServer {
    /**
     * This method should never be called.
     * Usually hrows UnsupportedOperationException.
     * <p>
     * 不应该调用此方法。通常会抛出UnsupportedOperationException。
     * 
     */
    public Object instantiate(String className)
            throws ReflectionException, MBeanException;
    /**
     * This method should never be called.
     * Usually throws UnsupportedOperationException.
     * <p>
     *  不应该调用此方法。通常会抛出UnsupportedOperationException。
     * 
     */
    public Object instantiate(String className, ObjectName loaderName)
            throws ReflectionException, MBeanException,
            InstanceNotFoundException;
    /**
     * This method should never be called.
     * Usually throws UnsupportedOperationException.
     * <p>
     *  不应该调用此方法。通常会抛出UnsupportedOperationException。
     * 
     */
    public Object instantiate(String className, Object[] params,
            String[] signature) throws ReflectionException, MBeanException;

    /**
     * This method should never be called.
     * Usually throws UnsupportedOperationException.
     * <p>
     *  不应该调用此方法。通常会抛出UnsupportedOperationException。
     * 
     */
    public Object instantiate(String className, ObjectName loaderName,
            Object[] params, String[] signature)
            throws ReflectionException, MBeanException,
            InstanceNotFoundException;

    /**
     * This method should never be called.
     * Usually throws UnsupportedOperationException.
     * <p>
     *  不应该调用此方法。通常会抛出UnsupportedOperationException。
     * 
     */
    @Deprecated
    public ObjectInputStream deserialize(ObjectName name, byte[] data)
            throws InstanceNotFoundException, OperationsException;

    /**
     * This method should never be called.
     * Usually throws UnsupportedOperationException.
     * <p>
     *  不应该调用此方法。通常会抛出UnsupportedOperationException。
     * 
     */
    @Deprecated
    public ObjectInputStream deserialize(String className, byte[] data)
            throws OperationsException, ReflectionException;

    /**
     * This method should never be called.
     * Usually hrows UnsupportedOperationException.
     * <p>
     *  不应该调用此方法。通常会抛出UnsupportedOperationException。
     * 
     */
    @Deprecated
    public ObjectInputStream deserialize(String className,
            ObjectName loaderName, byte[] data)
            throws InstanceNotFoundException, OperationsException,
            ReflectionException;

    /**
     * This method should never be called.
     * Usually throws UnsupportedOperationException.
     * <p>
     *  不应该调用此方法。通常会抛出UnsupportedOperationException。
     */
    public ClassLoaderRepository getClassLoaderRepository();

}

