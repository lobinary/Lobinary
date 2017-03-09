/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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
/*
/* <p>
/* 
 * @author    IBM Corp.
 *
 * Copyright IBM Corp. 1999-2000.  All rights reserved.
 */

package javax.management;

import javax.management.MBeanException;
import javax.management.RuntimeOperationsException;
import javax.management.InstanceNotFoundException;

/**
 *  This class is the interface to be implemented by MBeans that are meant to be
 *  persistent.  MBeans supporting this interface should call the load method during
 *  construction in order to prime the MBean from the persistent store.
 *  In the case of a ModelMBean, the store method should be called by the MBeanServer based on the descriptors in
 *  the ModelMBean or by the MBean itself during normal processing of the ModelMBean.
 *
 * <p>
 *  这个类是由MBeans实现的接口,意味着持久化。支持此接口的MBean在构建期间应调用load方法,以便从持久存储中提取MBean。
 * 在ModelMBean的情况下,存储方法应由MBeanServer基于ModelMBean中的描述符或由MBean本身在ModelMBean的正常处理期间调用。
 * 
 * 
 * @since 1.5
 */
public interface PersistentMBean {


    /**
     * Instantiates thisMBean instance with the data found for
     * the MBean in the persistent store.  The data loaded could include
     * attribute and operation values.
     *
     * This method should be called during construction or initialization of this instance,
     * and before the MBean is registered with the MBeanServer.
     *
     * <p>
     *  使用在持久存储中找到的MBean的数据实例化此MBean实例。加载的数据可以包括属性和操作值。
     * 
     *  在构建或初始化此实例期间,以及在向MBeanServer注册MBean之前,应调用此方法。
     * 
     * 
     * @exception MBeanException Wraps another exception or persistence is not supported
     * @exception RuntimeOperationsException Wraps exceptions from the persistence mechanism
     * @exception InstanceNotFoundException Could not find or load this MBean from persistent
     *                                      storage
     */
    public void load()
    throws MBeanException, RuntimeOperationsException, InstanceNotFoundException;

    /**
     * Captures the current state of this MBean instance and
     * writes it out to the persistent store.  The state stored could include
     * attribute and operation values. If one of these methods of persistence is
     * not supported a "serviceNotFound" exception will be thrown.
     * <P>
     * Persistence policy from the MBean and attribute descriptor is used to guide execution
     * of this method. The MBean should be stored if 'persistPolicy' field is:
     * <PRE>{@literal  != "never"
     *   = "always"
     *   = "onTimer" and now > 'lastPersistTime' + 'persistPeriod'
     *   = "NoMoreOftenThan" and now > 'lastPersistTime' + 'persistPeriod'
     *   = "onUnregister"
     * }</PRE>
     * <p>
     * Do not store the MBean if 'persistPolicy' field is:
     * <PRE>{@literal
     *    = "never"
     *    = "onUpdate"
     *    = "onTimer" && now < 'lastPersistTime' + 'persistPeriod'
     * }</PRE>
     *
     * <p>
     *  捕获此MBean实例的当前状态,并将其写入持久存储。所存储的状态可以包括属性和操作值。如果不支持这些持久化方法之一,将抛出"serviceNotFound"异常。
     * <P>
     *  来自MBean和属性描述符的持久性策略用于指导此方法的执行。
     * 如果'persistPolicy'字段是：<PRE> {@ literal！="never"="always"="onTimer"and now>'lastPersistTime'+'persistPeriod'="NoMoreOftenThan"and now>'lastPersistTime '+'persistPeriod'="onUnregister"}
     *  </PRE>。
     * 
     * @exception MBeanException Wraps another exception or persistence is not supported
     * @exception RuntimeOperationsException Wraps exceptions from the persistence mechanism
     * @exception InstanceNotFoundException Could not find/access the persistent store
     */
    public void store()
    throws MBeanException, RuntimeOperationsException, InstanceNotFoundException;

}
