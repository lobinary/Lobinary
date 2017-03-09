/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2008, Oracle and/or its affiliates. All rights reserved.
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

import java.lang.reflect.Method;

import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

/**
 * Base class for Standard MBeans.
 *
 * <p>
 *  标准MBeans的基类。
 * 
 * 
 * @since 1.6
 */
public class StandardMBeanSupport extends MBeanSupport<Method> {

    /**
     * <p>Construct a Standard MBean that wraps the given resource using the
     * given Standard MBean interface.</p>
     *
     * <p>
     *  <p>使用给定的标准MBean接口构造包装给定资源的标准MBean。</p>
     * 
     * 
     * @param resource the underlying resource for the new MBean.
     * @param mbeanInterfaceType the class or interface to be used to determine
     *       the MBean's management interface.  An interface if this is a
     *       classic Standard MBean; a class if this is a {@code @ManagedResource}.
     * @param <T> a type parameter that allows the compiler to check
     *       that {@code resource} implements {@code mbeanInterfaceType},
     *       provided that {@code mbeanInterfaceType} is a class constant like
     *       {@code SomeMBean.class}.
     * @throws IllegalArgumentException if {@code resource} is null or
     *       if it does not implement the class {@code mbeanInterfaceType} or if
     *       that class is not a valid Standard MBean interface.
     */
    public <T> StandardMBeanSupport(T resource, Class<T> mbeanInterfaceType)
            throws NotCompliantMBeanException {
        super(resource, mbeanInterfaceType);
    }

    @Override
    MBeanIntrospector<Method> getMBeanIntrospector() {
        return StandardMBeanIntrospector.getInstance();
    }

    @Override
    Object getCookie() {
        return null;
    }

    @Override
    public void register(MBeanServer mbs, ObjectName name) {}

    @Override
    public void unregister() {}

    /* Standard MBeans that are NotificationBroadcasters can return a different
     * MBeanNotificationInfo[] every time getMBeanInfo() is called, so we have
     * to reconstruct this MBeanInfo if necessary.
     * <p>
     *  MBeanNotificationInfo []每次调用getMBeanInfo(),所以我们必须重建这个MBeanInfo如果必要。
     */
    @Override
    public MBeanInfo getMBeanInfo() {
        MBeanInfo mbi = super.getMBeanInfo();
        Class<?> resourceClass = getResource().getClass();
        if (StandardMBeanIntrospector.isDefinitelyImmutableInfo(resourceClass))
            return mbi;
        return new MBeanInfo(mbi.getClassName(), mbi.getDescription(),
                mbi.getAttributes(), mbi.getConstructors(),
                mbi.getOperations(),
                MBeanIntrospector.findNotifications(getResource()),
                mbi.getDescriptor());
    }
}
