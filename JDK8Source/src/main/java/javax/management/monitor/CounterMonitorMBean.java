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
 * Exposes the remote management interface of the counter monitor MBean.
 *
 *
 * <p>
 *  显示计数器监视器MBean的远程管理接口。
 * 
 * 
 * @since 1.5
 */
public interface CounterMonitorMBean extends MonitorMBean {

    // GETTERS AND SETTERS
    //--------------------

    /**
     * Gets the derived gauge.
     *
     * <p>
     *  获取派生量表。
     * 
     * 
     * @return The derived gauge.
     * @deprecated As of JMX 1.2, replaced by {@link #getDerivedGauge(ObjectName)}
     */
    @Deprecated
    public Number getDerivedGauge();

    /**
     * Gets the derived gauge timestamp.
     *
     * <p>
     *  获取派生的度量时间戳。
     * 
     * 
     * @return The derived gauge timestamp.
     * @deprecated As of JMX 1.2, replaced by {@link #getDerivedGaugeTimeStamp(ObjectName)}
     */
    @Deprecated
    public long getDerivedGaugeTimeStamp();

    /**
     * Gets the threshold value.
     *
     * <p>
     *  获取阈值。
     * 
     * 
     * @return The threshold value.
     *
     * @see #setThreshold(Number)
     *
     * @deprecated As of JMX 1.2, replaced by {@link #getThreshold(ObjectName)}
     */
    @Deprecated
    public Number getThreshold();

    /**
     * Sets the threshold value.
     *
     * <p>
     *  设置阈值。
     * 
     * 
     * @see #getThreshold()
     *
     * @param value The threshold value.
     * @exception java.lang.IllegalArgumentException The specified threshold is null or the threshold value is less than zero.
     * @deprecated As of JMX 1.2, replaced by {@link #setInitThreshold}
     */
    @Deprecated
    public void setThreshold(Number value) throws java.lang.IllegalArgumentException;

    /**
     * Gets the derived gauge for the specified MBean.
     *
     * <p>
     *  获取指定MBean的派生标尺。
     * 
     * 
     * @param object the MBean for which the derived gauge is to be returned
     * @return The derived gauge for the specified MBean if this MBean is in the
     *         set of observed MBeans, or <code>null</code> otherwise.
     *
     */
    public Number getDerivedGauge(ObjectName object);

    /**
     * Gets the derived gauge timestamp for the specified MBean.
     *
     * <p>
     *  获取指定MBean的派生标尺时间戳。
     * 
     * 
     * @param object the MBean for which the derived gauge timestamp is to be returned
     * @return The derived gauge timestamp for the specified MBean if this MBean
     *         is in the set of observed MBeans, or <code>null</code> otherwise.
     *
     */
    public long getDerivedGaugeTimeStamp(ObjectName object);

    /**
     * Gets the threshold value for the specified MBean.
     *
     * <p>
     *  获取指定MBean的阈值。
     * 
     * 
     * @param object the MBean for which the threshold value is to be returned
     * @return The threshold value for the specified MBean if this MBean
     *         is in the set of observed MBeans, or <code>null</code> otherwise.
     *
     * @see #setThreshold
     *
     */
    public Number getThreshold(ObjectName object);

    /**
     * Gets the initial threshold value common to all observed objects.
     *
     * <p>
     *  获取所有观察到的对象共有的初始阈值。
     * 
     * 
     * @return The initial threshold value.
     *
     * @see #setInitThreshold
     *
     */
    public Number getInitThreshold();

    /**
     * Sets the initial threshold value common to all observed MBeans.
     *
     * <p>
     *  设置所有观察到的MBean的公共初始阈值。
     * 
     * 
     * @param value The initial threshold value.
     * @exception java.lang.IllegalArgumentException The specified
     * threshold is null or the threshold value is less than zero.
     *
     * @see #getInitThreshold
     *
     */
    public void setInitThreshold(Number value) throws java.lang.IllegalArgumentException;

    /**
     * Gets the offset value.
     *
     * <p>
     *  获取偏移值。
     * 
     * 
     * @see #setOffset(Number)
     *
     * @return The offset value.
     */
    public Number getOffset();

    /**
     * Sets the offset value.
     *
     * <p>
     *  设置偏移值。
     * 
     * 
     * @param value The offset value.
     * @exception java.lang.IllegalArgumentException The specified
     * offset is null or the offset value is less than zero.
     *
     * @see #getOffset()
     */
    public void setOffset(Number value) throws java.lang.IllegalArgumentException;

    /**
     * Gets the modulus value.
     *
     * <p>
     *  获取模量值。
     * 
     * 
     * @return The modulus value.
     *
     * @see #setModulus
     */
    public Number getModulus();

    /**
     * Sets the modulus value.
     *
     * <p>
     *  设置模量值。
     * 
     * 
     * @param value The modulus value.
     * @exception java.lang.IllegalArgumentException The specified
     * modulus is null or the modulus value is less than zero.
     *
     * @see #getModulus
     */
    public void setModulus(Number value) throws java.lang.IllegalArgumentException;

    /**
     * Gets the notification's on/off switch value.
     *
     * <p>
     *  获取通知的开/关切换值。
     * 
     * 
     * @return <CODE>true</CODE> if the counter monitor notifies when
     * exceeding the threshold, <CODE>false</CODE> otherwise.
     *
     * @see #setNotify
     */
    public boolean getNotify();

    /**
     * Sets the notification's on/off switch value.
     *
     * <p>
     *  设置通知的开/关切换值。
     * 
     * 
     * @param value The notification's on/off switch value.
     *
     * @see #getNotify
     */
    public void setNotify(boolean value);

    /**
     * Gets the difference mode flag value.
     *
     * <p>
     *  获取差模式标志值。
     * 
     * 
     * @return <CODE>true</CODE> if the difference mode is used,
     * <CODE>false</CODE> otherwise.
     *
     * @see #setDifferenceMode
     */
    public boolean getDifferenceMode();

    /**
     * Sets the difference mode flag value.
     *
     * <p>
     *  设置差分模式标志值。
     * 
     * @param value The difference mode flag value.
     *
     * @see #getDifferenceMode
     */
    public void setDifferenceMode(boolean value);
}
