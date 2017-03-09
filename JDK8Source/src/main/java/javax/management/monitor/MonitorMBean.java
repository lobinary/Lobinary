/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2007, Oracle and/or its affiliates. All rights reserved.
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

package javax.management.monitor;

// jmx imports
//
import javax.management.ObjectName;

/**
 * Exposes the remote management interface of monitor MBeans.
 *
 *
 * <p>
 *  显示监视MBean的远程管理接口。
 * 
 * 
 * @since 1.5
 */
public interface MonitorMBean {

    /**
     * Starts the monitor.
     * <p>
     *  启动显示器。
     * 
     */
    public void start();

    /**
     * Stops the monitor.
     * <p>
     *  停止显示器。
     * 
     */
    public void stop();

    // GETTERS AND SETTERS
    //--------------------

    /**
     * Adds the specified object in the set of observed MBeans.
     *
     * <p>
     *  在观察的MBean集合中添加指定的对象。
     * 
     * 
     * @param object The object to observe.
     * @exception java.lang.IllegalArgumentException the specified object is null.
     *
     */
    public void addObservedObject(ObjectName object) throws java.lang.IllegalArgumentException;

    /**
     * Removes the specified object from the set of observed MBeans.
     *
     * <p>
     *  从观察的MBean集中删除指定的对象。
     * 
     * 
     * @param object The object to remove.
     *
     */
    public void removeObservedObject(ObjectName object);

    /**
     * Tests whether the specified object is in the set of observed MBeans.
     *
     * <p>
     *  测试指定的对象是否在观察的MBean集合中。
     * 
     * 
     * @param object The object to check.
     * @return <CODE>true</CODE> if the specified object is in the set, <CODE>false</CODE> otherwise.
     *
     */
    public boolean containsObservedObject(ObjectName object);

    /**
     * Returns an array containing the objects being observed.
     *
     * <p>
     *  返回包含所观察对象的数组。
     * 
     * 
     * @return The objects being observed.
     *
     */
    public ObjectName[] getObservedObjects();

    /**
     * Gets the object name of the object being observed.
     *
     * <p>
     *  获取正在观察的对象的对象名称。
     * 
     * 
     * @return The object being observed.
     *
     * @see #setObservedObject
     *
     * @deprecated As of JMX 1.2, replaced by {@link #getObservedObjects}
     */
    @Deprecated
    public ObjectName getObservedObject();

    /**
     * Sets the object to observe identified by its object name.
     *
     * <p>
     *  将要被观察的对象设置为由其对象名称标识。
     * 
     * 
     * @param object The object to observe.
     *
     * @see #getObservedObject
     *
     * @deprecated As of JMX 1.2, replaced by {@link #addObservedObject}
     */
    @Deprecated
    public void setObservedObject(ObjectName object);

    /**
     * Gets the attribute being observed.
     *
     * <p>
     *  获取所观察的属性。
     * 
     * 
     * @return The attribute being observed.
     *
     * @see #setObservedAttribute
     */
    public String getObservedAttribute();

    /**
     * Sets the attribute to observe.
     *
     * <p>
     *  将属性设置为observe。
     * 
     * 
     * @param attribute The attribute to observe.
     *
     * @see #getObservedAttribute
     */
    public void setObservedAttribute(String attribute);

    /**
     * Gets the granularity period (in milliseconds).
     *
     * <p>
     *  获取粒度周期(以毫秒为单位)。
     * 
     * 
     * @return The granularity period.
     *
     * @see #setGranularityPeriod
     */
    public long getGranularityPeriod();

    /**
     * Sets the granularity period (in milliseconds).
     *
     * <p>
     *  设置粒度周期(以毫秒为单位)。
     * 
     * 
     * @param period The granularity period.
     * @exception java.lang.IllegalArgumentException The granularity
     * period is less than or equal to zero.
     *
     * @see #getGranularityPeriod
     */
    public void setGranularityPeriod(long period) throws java.lang.IllegalArgumentException;

    /**
     * Tests if the monitor MBean is active.
     * A monitor MBean is marked active when the {@link #start start} method is called.
     * It becomes inactive when the {@link #stop stop} method is called.
     *
     * <p>
     *  测试监视器MBean是否处于活动状态。当调用{@link #start start}方法时,监视器MBean被标记为活动。当调用{@link #stop stop}方法时,它将变为无效。
     * 
     * @return <CODE>true</CODE> if the monitor MBean is active, <CODE>false</CODE> otherwise.
     */
    public boolean isActive();
}
