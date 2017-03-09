/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, Oracle and/or its affiliates. All rights reserved.
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

import javax.management.DynamicMBean;
import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * A dynamic MBean that wraps an underlying resource.  A version of this
 * interface might eventually appear in the public JMX API.
 *
 * <p>
 *  封装基础资源的动态MBean。此接口的版本可能最终出现在公共JMX API中。
 * 
 * 
 * @since 1.6
 */
public interface DynamicMBean2 extends DynamicMBean {
    /**
     * The resource corresponding to this MBean.  This is the object whose
     * class name should be reflected by the MBean's
     * getMBeanInfo().getClassName() for example.  For a "plain"
     * DynamicMBean it will be "this".  For an MBean that wraps another
     * object, like javax.management.StandardMBean, it will be the wrapped
     * object.
     * <p>
     *  与此MBean对应的资源。这是一个对象,其类名应该由MBean的getMBeanInfo()。getClassName()来反映。对于"plain"DynamicMBean,它将是"this"。
     * 对于包装另一个对象(如javax.management.StandardMBean)的MBean,它将是包装的对象。
     * 
     */
    public Object getResource();

    /**
     * The name of this MBean's class, as used by permission checks.
     * This is typically equal to getResource().getClass().getName().
     * This method is typically faster, sometimes much faster,
     * than getMBeanInfo().getClassName(), but should return the same
     * result.
     * <p>
     *  此MBean的类的名称,由权限检查使用。这通常等于getResource()。getClass()。getName()。此方法通常比getMBeanInfo()。
     * getClassName()更快,有时快得多,但应该返回相同的结果。
     * 
     */
    public String getClassName();

    /**
     * Additional registration hook.  This method is called after
     * {@link javax.management.MBeanRegistration#preRegister preRegister}.
     * Unlike that method, if it throws an exception and the MBean implements
     * {@code MBeanRegistration}, then {@link
     * javax.management.MBeanRegistration#postRegister postRegister(false)}
     * will be called on the MBean.  This is the behavior that the MBean
     * expects for a problem that does not come from its own preRegister
     * method.
     * <p>
     *  附加注册钩。此方法在{@link javax.management.MBeanRegistration#preRegister preRegister}之后调用。
     * 与该方法不同,如果它抛出一个异常,并且MBean实现{@code MBeanRegistration},那么将在MBean上调用{@link javax.management.MBeanRegistration#postRegister postRegister(false)}
     * 。
     *  附加注册钩。此方法在{@link javax.management.MBeanRegistration#preRegister preRegister}之后调用。
     * 这是MBean期望的问题的行为,它不是来自于它自己的preRegister方法。
     */
    public void preRegister2(MBeanServer mbs, ObjectName name)
            throws Exception;

    /**
     * Additional registration hook.  This method is called if preRegister
     * and preRegister2 succeed, but then the MBean cannot be registered
     * (for example because there is already another MBean of the same name).
     * <p>
     * 
     */
    public void registerFailed();
}
