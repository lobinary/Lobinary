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
 * Exposes the remote management interface of the string monitor MBean.
 *
 *
 * <p>
 *  显示字符串monitor MBean的远程管理接口。
 * 
 * 
 * @since 1.5
 */
public interface StringMonitorMBean extends MonitorMBean {

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
    public String getDerivedGauge();

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
    public String getDerivedGauge(ObjectName object);

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
     * Gets the string to compare with the observed attribute.
     *
     * <p>
     *  获取要与观察属性进行比较的字符串。
     * 
     * 
     * @return The string value.
     *
     * @see #setStringToCompare
     */
    public String getStringToCompare();

    /**
     * Sets the string to compare with the observed attribute.
     *
     * <p>
     *  设置要与观察属性进行比较的字符串。
     * 
     * 
     * @param value The string value.
     * @exception java.lang.IllegalArgumentException The specified
     * string to compare is null.
     *
     * @see #getStringToCompare
     */
    public void setStringToCompare(String value) throws java.lang.IllegalArgumentException;

    /**
     * Gets the matching notification's on/off switch value.
     *
     * <p>
     *  获取匹配通知的开/关切换值。
     * 
     * 
     * @return <CODE>true</CODE> if the string monitor notifies when
     * matching, <CODE>false</CODE> otherwise.
     *
     * @see #setNotifyMatch
     */
    public boolean getNotifyMatch();

    /**
     * Sets the matching notification's on/off switch value.
     *
     * <p>
     *  设置匹配通知的开/关切换值。
     * 
     * 
     * @param value The matching notification's on/off switch value.
     *
     * @see #getNotifyMatch
     */
    public void setNotifyMatch(boolean value);

    /**
     * Gets the differing notification's on/off switch value.
     *
     * <p>
     *  获取不同的通知的开/关切换值。
     * 
     * 
     * @return <CODE>true</CODE> if the string monitor notifies when
     * differing, <CODE>false</CODE> otherwise.
     *
     * @see #setNotifyDiffer
     */
    public boolean getNotifyDiffer();

    /**
     * Sets the differing notification's on/off switch value.
     *
     * <p>
     *  设置不同的通知的开/关切换值。
     * 
     * @param value The differing notification's on/off switch value.
     *
     * @see #getNotifyDiffer
     */
    public void setNotifyDiffer(boolean value);
}
