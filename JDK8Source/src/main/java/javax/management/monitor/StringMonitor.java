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

import static com.sun.jmx.defaults.JmxProperties.MONITOR_LOGGER;
import java.util.logging.Level;
import javax.management.ObjectName;
import javax.management.MBeanNotificationInfo;
import static javax.management.monitor.MonitorNotification.*;

/**
 * Defines a monitor MBean designed to observe the values of a string
 * attribute.
 * <P>
 * A string monitor sends notifications as follows:
 * <UL>
 * <LI> if the attribute value matches the string to compare value,
 *      a {@link MonitorNotification#STRING_TO_COMPARE_VALUE_MATCHED
 *      match notification} is sent.
 *      The notify match flag must be set to <CODE>true</CODE>.
 *      <BR>Subsequent matchings of the string to compare values do not
 *      cause further notifications unless
 *      the attribute value differs from the string to compare value.
 * <LI> if the attribute value differs from the string to compare value,
 *      a {@link MonitorNotification#STRING_TO_COMPARE_VALUE_DIFFERED
 *      differ notification} is sent.
 *      The notify differ flag must be set to <CODE>true</CODE>.
 *      <BR>Subsequent differences from the string to compare value do
 *      not cause further notifications unless
 *      the attribute value matches the string to compare value.
 * </UL>
 *
 *
 * <p>
 *  定义一个监视器MBean,用于观察字符串属性的值。
 * <P>
 *  字符串监视器发送通知,如下所示：
 * <UL>
 *  <LI>如果属性值与要比较的字符串值匹配,则会发送{@link MonitorNotification#STRING_TO_COMPARE_VALUE_MATCHED匹配通知}。
 * 通知匹配标志必须设置为<CODE> true </CODE>。 <BR>后续匹配字符串以比较值不会导致进一步通知,除非属性值与要比较的字符串值不同。
 *  <LI>如果属性值与要比较的字符串值不同,则会发送{@link MonitorNotification#STRING_TO_COMPARE_VALUE_DIFFERED个差异通知}。
 *  notify differences标志必须设置为<CODE> true </CODE>。 <BR>字符串与比较值的后续差异不会导致进一步通知,除非属性值与要比较的字符串值匹配。
 * </UL>
 * 
 * 
 * @since 1.5
 */
public class StringMonitor extends Monitor implements StringMonitorMBean {

    /*
     * ------------------------------------------
     *  PACKAGE CLASSES
     * ------------------------------------------
     * <p>
     *  ------------------------------------------包装类------------------------------------
     * 
     */

    static class StringMonitorObservedObject extends ObservedObject {

        public StringMonitorObservedObject(ObjectName observedObject) {
            super(observedObject);
        }

        public final synchronized int getStatus() {
            return status;
        }
        public final synchronized void setStatus(int status) {
            this.status = status;
        }

        private int status;
    }

    /*
     * ------------------------------------------
     *  PRIVATE VARIABLES
     * ------------------------------------------
     * <p>
     *  ------------------------------------------私人变数------ ------------------------------------
     * 
     */

    /**
     * String to compare with the observed attribute.
     * <BR>The default value is an empty character sequence.
     * <p>
     *  要与观察属性进行比较的字符串。 <BR>默认值为空字符序列。
     * 
     */
    private String stringToCompare = "";

    /**
     * Flag indicating if the string monitor notifies when matching
     * the string to compare.
     * <BR>The default value is set to <CODE>false</CODE>.
     * <p>
     *  标志指示当匹配要比较的字符串时,字符串监视器是否通知。 <BR>默认值设置为<CODE> false </CODE>。
     * 
     */
    private boolean notifyMatch = false;

    /**
     * Flag indicating if the string monitor notifies when differing
     * from the string to compare.
     * <BR>The default value is set to <CODE>false</CODE>.
     * <p>
     * 指示字符串监视器通知何时与要比较的字符串不同的标志。 <BR>默认值设置为<CODE> false </CODE>。
     * 
     */
    private boolean notifyDiffer = false;

    private static final String[] types = {
        RUNTIME_ERROR,
        OBSERVED_OBJECT_ERROR,
        OBSERVED_ATTRIBUTE_ERROR,
        OBSERVED_ATTRIBUTE_TYPE_ERROR,
        STRING_TO_COMPARE_VALUE_MATCHED,
        STRING_TO_COMPARE_VALUE_DIFFERED
    };

    private static final MBeanNotificationInfo[] notifsInfo = {
        new MBeanNotificationInfo(
            types,
            "javax.management.monitor.MonitorNotification",
            "Notifications sent by the StringMonitor MBean")
    };

    // Flags needed to implement the matching/differing mechanism.
    //
    private static final int MATCHING                   = 0;
    private static final int DIFFERING                  = 1;
    private static final int MATCHING_OR_DIFFERING      = 2;

    /*
     * ------------------------------------------
     *  CONSTRUCTORS
     * ------------------------------------------
     * <p>
     *  ------------------------------------------建筑师------- -----------------------------------
     * 
     */

    /**
     * Default constructor.
     * <p>
     *  默认构造函数。
     * 
     */
    public StringMonitor() {
    }

    /*
     * ------------------------------------------
     *  PUBLIC METHODS
     * ------------------------------------------
     * <p>
     *  ------------------------------------------公共方法------------------------------------
     * 
     */

    /**
     * Starts the string monitor.
     * <p>
     *  启动字符串监视器。
     * 
     */
    public synchronized void start() {
        if (isActive()) {
            MONITOR_LOGGER.logp(Level.FINER, StringMonitor.class.getName(),
                    "start", "the monitor is already active");
            return;
        }
        // Reset values.
        //
        for (ObservedObject o : observedObjects) {
            final StringMonitorObservedObject smo =
                (StringMonitorObservedObject) o;
            smo.setStatus(MATCHING_OR_DIFFERING);
        }
        doStart();
    }

    /**
     * Stops the string monitor.
     * <p>
     *  停止字符串监视器。
     * 
     */
    public synchronized void stop() {
        doStop();
    }

    // GETTERS AND SETTERS
    //--------------------

    /**
     * Gets the derived gauge of the specified object, if this object is
     * contained in the set of observed MBeans, or <code>null</code> otherwise.
     *
     * <p>
     *  获取指定对象的派生尺寸(如果此对象包含在观察MBean的集合中),否则返回<code> null </code>。
     * 
     * 
     * @param object the name of the MBean whose derived gauge is required.
     *
     * @return The derived gauge of the specified object.
     *
     */
    @Override
    public synchronized String getDerivedGauge(ObjectName object) {
        return (String) super.getDerivedGauge(object);
    }

    /**
     * Gets the derived gauge timestamp of the specified object, if
     * this object is contained in the set of observed MBeans, or
     * <code>0</code> otherwise.
     *
     * <p>
     *  获取指定对象的派生标尺时间戳(如果此对象包含在观察MBean的集合中),否则为<code> 0 </code>。
     * 
     * 
     * @param object the name of the object whose derived gauge
     * timestamp is to be returned.
     *
     * @return The derived gauge timestamp of the specified object.
     *
     */
    @Override
    public synchronized long getDerivedGaugeTimeStamp(ObjectName object) {
        return super.getDerivedGaugeTimeStamp(object);
    }

    /**
     * Returns the derived gauge of the first object in the set of
     * observed MBeans.
     *
     * <p>
     *  返回观测MBean集合中第一个对象的导出标准。
     * 
     * 
     * @return The derived gauge.
     *
     * @deprecated As of JMX 1.2, replaced by
     * {@link #getDerivedGauge(ObjectName)}
     */
    @Deprecated
    public synchronized String getDerivedGauge() {
        if (observedObjects.isEmpty()) {
            return null;
        } else {
            return (String) observedObjects.get(0).getDerivedGauge();
        }
    }

    /**
     * Gets the derived gauge timestamp of the first object in the set
     * of observed MBeans.
     *
     * <p>
     *  获取观察到的MBean集合中的第一个对象的导出度量标记时间戳。
     * 
     * 
     * @return The derived gauge timestamp.
     *
     * @deprecated As of JMX 1.2, replaced by
     * {@link #getDerivedGaugeTimeStamp(ObjectName)}
     */
    @Deprecated
    public synchronized long getDerivedGaugeTimeStamp() {
        if (observedObjects.isEmpty()) {
            return 0;
        } else {
            return observedObjects.get(0).getDerivedGaugeTimeStamp();
        }
    }

    /**
     * Gets the string to compare with the observed attribute common
     * to all observed MBeans.
     *
     * <p>
     *  获取要与所有观察到的MBean公用的观察属性进行比较的字符串。
     * 
     * 
     * @return The string value.
     *
     * @see #setStringToCompare
     */
    public synchronized String getStringToCompare() {
        return stringToCompare;
    }

    /**
     * Sets the string to compare with the observed attribute common
     * to all observed MBeans.
     *
     * <p>
     *  设置要与所有观察到的MBean公用的观察属性进行比较的字符串。
     * 
     * 
     * @param value The string value.
     *
     * @exception IllegalArgumentException The specified
     * string to compare is null.
     *
     * @see #getStringToCompare
     */
    public synchronized void setStringToCompare(String value)
        throws IllegalArgumentException {

        if (value == null) {
            throw new IllegalArgumentException("Null string to compare");
        }

        if (stringToCompare.equals(value))
            return;
        stringToCompare = value;

        // Reset values.
        //
        for (ObservedObject o : observedObjects) {
            final StringMonitorObservedObject smo =
                (StringMonitorObservedObject) o;
            smo.setStatus(MATCHING_OR_DIFFERING);
        }
    }

    /**
     * Gets the matching notification's on/off switch value common to
     * all observed MBeans.
     *
     * <p>
     *  获取所有观察到的MBean公用的匹配通知的开/关切换值。
     * 
     * 
     * @return <CODE>true</CODE> if the string monitor notifies when
     * matching the string to compare, <CODE>false</CODE> otherwise.
     *
     * @see #setNotifyMatch
     */
    public synchronized boolean getNotifyMatch() {
        return notifyMatch;
    }

    /**
     * Sets the matching notification's on/off switch value common to
     * all observed MBeans.
     *
     * <p>
     *  设置所有观察到的MBean的通用的匹配通知的开/关切换值。
     * 
     * 
     * @param value The matching notification's on/off switch value.
     *
     * @see #getNotifyMatch
     */
    public synchronized void setNotifyMatch(boolean value) {
        if (notifyMatch == value)
            return;
        notifyMatch = value;
    }

    /**
     * Gets the differing notification's on/off switch value common to
     * all observed MBeans.
     *
     * <p>
     *  获取所有观察到的MBean公用的不同通知的开/关切换值。
     * 
     * 
     * @return <CODE>true</CODE> if the string monitor notifies when
     * differing from the string to compare, <CODE>false</CODE> otherwise.
     *
     * @see #setNotifyDiffer
     */
    public synchronized boolean getNotifyDiffer() {
        return notifyDiffer;
    }

    /**
     * Sets the differing notification's on/off switch value common to
     * all observed MBeans.
     *
     * <p>
     *  设置所有观察到的MBean公用的不同通知的开/关切换值。
     * 
     * 
     * @param value The differing notification's on/off switch value.
     *
     * @see #getNotifyDiffer
     */
    public synchronized void setNotifyDiffer(boolean value) {
        if (notifyDiffer == value)
            return;
        notifyDiffer = value;
    }

    /**
     * Returns a <CODE>NotificationInfo</CODE> object containing the name of
     * the Java class of the notification and the notification types sent by
     * the string monitor.
     * <p>
     * 返回一个<CODE> NotificationInfo </CODE>对象,其中包含通知的Java类的名称以及字符串监视器发送的通知类型。
     * 
     */
    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        return notifsInfo.clone();
    }

    /*
     * ------------------------------------------
     *  PACKAGE METHODS
     * ------------------------------------------
     * <p>
     *  ------------------------------------------包装方法------ ------------------------------------
     * 
     */

    /**
     * Factory method for ObservedObject creation.
     *
     * <p>
     *  ObservedObject创建的工厂方法。
     * 
     * 
     * @since 1.6
     */
    @Override
    ObservedObject createObservedObject(ObjectName object) {
        final StringMonitorObservedObject smo =
            new StringMonitorObservedObject(object);
        smo.setStatus(MATCHING_OR_DIFFERING);
        return smo;
    }

    /**
     * Check that the type of the supplied observed attribute
     * value is one of the value types supported by this monitor.
     * <p>
     *  检查所提供的观察属性值的类型是否为此监视器支持的值类型之一。
     */
    @Override
    synchronized boolean isComparableTypeValid(ObjectName object,
                                               String attribute,
                                               Comparable<?> value) {
        // Check that the observed attribute is of type "String".
        //
        if (value instanceof String) {
            return true;
        }
        return false;
    }

    @Override
    synchronized void onErrorNotification(MonitorNotification notification) {
        final StringMonitorObservedObject o = (StringMonitorObservedObject)
            getObservedObject(notification.getObservedObject());
        if (o == null)
            return;

        // Reset values.
        //
        o.setStatus(MATCHING_OR_DIFFERING);
    }

    @Override
    synchronized MonitorNotification buildAlarmNotification(
                                               ObjectName object,
                                               String attribute,
                                               Comparable<?> value) {
        String type = null;
        String msg = null;
        Object trigger = null;

        final StringMonitorObservedObject o =
            (StringMonitorObservedObject) getObservedObject(object);
        if (o == null)
            return null;

        // Send matching notification if notifyMatch is true.
        // Send differing notification if notifyDiffer is true.
        //
        if (o.getStatus() == MATCHING_OR_DIFFERING) {
            if (o.getDerivedGauge().equals(stringToCompare)) {
                if (notifyMatch) {
                    type = STRING_TO_COMPARE_VALUE_MATCHED;
                    msg = "";
                    trigger = stringToCompare;
                }
                o.setStatus(DIFFERING);
            } else {
                if (notifyDiffer) {
                    type = STRING_TO_COMPARE_VALUE_DIFFERED;
                    msg = "";
                    trigger = stringToCompare;
                }
                o.setStatus(MATCHING);
            }
        } else {
            if (o.getStatus() == MATCHING) {
                if (o.getDerivedGauge().equals(stringToCompare)) {
                    if (notifyMatch) {
                        type = STRING_TO_COMPARE_VALUE_MATCHED;
                        msg = "";
                        trigger = stringToCompare;
                    }
                    o.setStatus(DIFFERING);
                }
            } else if (o.getStatus() == DIFFERING) {
                if (!o.getDerivedGauge().equals(stringToCompare)) {
                    if (notifyDiffer) {
                        type = STRING_TO_COMPARE_VALUE_DIFFERED;
                        msg = "";
                        trigger = stringToCompare;
                    }
                    o.setStatus(MATCHING);
                }
            }
        }

        return new MonitorNotification(type,
                                       this,
                                       0,
                                       0,
                                       msg,
                                       null,
                                       null,
                                       null,
                                       trigger);
    }
}
