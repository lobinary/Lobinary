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
 * Exposes the remote management interface of the gauge monitor MBean.
 *
 *
 * <p>
 *  露出仪表监视器MBean的远程管理接口。
 * 
 * 
 * @since 1.5
 */
public interface GaugeMonitorMBean extends MonitorMBean {

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
     * Gets the high threshold value.
     *
     * <p>
     *  获取高阈值。
     * 
     * 
     * @return The high threshold value.
     */
    public Number getHighThreshold();

    /**
     * Gets the low threshold value.
     *
     * <p>
     *  获取低阈值。
     * 
     * 
     * @return The low threshold value.
     */
    public Number getLowThreshold();

    /**
     * Sets the high and the low threshold values.
     *
     * <p>
     *  设置高阈值和低阈值。
     * 
     * 
     * @param highValue The high threshold value.
     * @param lowValue The low threshold value.
     * @exception java.lang.IllegalArgumentException The specified high/low threshold is null
     * or the low threshold is greater than the high threshold
     * or the high threshold and the low threshold are not of the same type.
     */
    public void setThresholds(Number highValue, Number lowValue) throws java.lang.IllegalArgumentException;

    /**
     * Gets the high notification's on/off switch value.
     *
     * <p>
     *  获取高通知的开/关切换值。
     * 
     * 
     * @return <CODE>true</CODE> if the gauge monitor notifies when
     * exceeding the high threshold, <CODE>false</CODE> otherwise.
     *
     * @see #setNotifyHigh
     */
    public boolean getNotifyHigh();

    /**
     * Sets the high notification's on/off switch value.
     *
     * <p>
     *  设置高通知的开/关切换值。
     * 
     * 
     * @param value The high notification's on/off switch value.
     *
     * @see #getNotifyHigh
     */
    public void setNotifyHigh(boolean value);

    /**
     * Gets the low notification's on/off switch value.
     *
     * <p>
     *  获取低通知的开/关切换值。
     * 
     * 
     * @return <CODE>true</CODE> if the gauge monitor notifies when
     * exceeding the low threshold, <CODE>false</CODE> otherwise.
     *
     * @see #setNotifyLow
     */
    public boolean getNotifyLow();

    /**
     * Sets the low notification's on/off switch value.
     *
     * <p>
     *  设置低通知的开/关切换值。
     * 
     * 
     * @param value The low notification's on/off switch value.
     *
     * @see #getNotifyLow
     */
    public void setNotifyLow(boolean value);

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
