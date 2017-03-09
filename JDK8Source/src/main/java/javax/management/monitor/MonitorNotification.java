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
 * Provides definitions of the notifications sent by monitor MBeans.
 * <P>
 * The notification source and a set of parameters concerning the monitor MBean's state
 * need to be specified when creating a new object of this class.
 *
 * The list of notifications fired by the monitor MBeans is the following:
 *
 * <UL>
 * <LI>Common to all kind of monitors:
 *     <UL>
 *     <LI>The observed object is not registered in the MBean server.
 *     <LI>The observed attribute is not contained in the observed object.
 *     <LI>The type of the observed attribute is not correct.
 *     <LI>Any exception (except the cases described above) occurs when trying to get the value of the observed attribute.
 *     </UL>
 * <LI>Common to the counter and the gauge monitors:
 *     <UL>
 *     <LI>The threshold high or threshold low are not of the same type as the gauge (gauge monitors).
 *     <LI>The threshold or the offset or the modulus are not of the same type as the counter (counter monitors).
 *     </UL>
 * <LI>Counter monitors only:
 *     <UL>
 *     <LI>The observed attribute has reached the threshold value.
 *     </UL>
 * <LI>Gauge monitors only:
 *     <UL>
 *     <LI>The observed attribute has exceeded the threshold high value.
 *     <LI>The observed attribute has exceeded the threshold low value.
 *     </UL>
 * <LI>String monitors only:
 *     <UL>
 *     <LI>The observed attribute has matched the "string to compare" value.
 *     <LI>The observed attribute has differed from the "string to compare" value.
 *     </UL>
 * </UL>
 *
 *
 * <p>
 *  提供由monitor MBean发送的通知的定义。
 * <P>
 *  在创建此类的新对象时,需要指定通知源和关于监视器MBean状态的一组参数。
 * 
 *  监视器MBeans触发的通知列表如下：
 * 
 * <UL>
 *  <LI>所有类型的显示器共有：
 * <UL>
 *  <LI>观察到的对象未在MBean服务器中注册。 <LI>观察到的属性不包含在观察对象中。 <LI>观察属性的类型不正确。 <LI>当尝试获取观察属性的值时,发生任何异常(上述情况除外)。
 * </UL>
 *  <LI>计数器和计量表监视器的通用：
 * <UL>
 *  <LI>阈值高或阈值低与计量器(计量器监视器)不是相同的类型。 <LI>阈值或偏移量或模数与计数器(计数器监视器)的类型不同。
 * </UL>
 *  <LI>仅计数器监视器：
 * <UL>
 *  <LI>观察属性已达到阈值。
 * </UL>
 *  <LI>仅限仪表显示器：
 * <UL>
 *  <LI>观察属性已超过阈值高值。 <LI>观察属性已超过阈值低值。
 * </UL>
 *  <LI>仅限字符串监视器：
 * <UL>
 *  <LI>观察属性与"要比较的字符串"值匹配。 <LI>观察属性与"要比较的字符串"值不同。
 * </UL>
 * </UL>
 * 
 * 
 * @since 1.5
 */
public class MonitorNotification extends javax.management.Notification {


    /*
     * ------------------------------------------
     *  PUBLIC VARIABLES
     * ------------------------------------------
     * <p>
     * ------------------------------------------公共变量------ ------------------------------------
     * 
     */

    /**
     * Notification type denoting that the observed object is not registered in the MBean server.
     * This notification is fired by all kinds of monitors.
     * <BR>The value of this notification type is <CODE>jmx.monitor.error.mbean</CODE>.
     * <p>
     *  通知类型,表示观察到的对象未在MBean服务器中注册。此通知由各种监视器触发。 <BR>此通知类型的值为<CODE> jmx.monitor.error.mbean </CODE>。
     * 
     */
    public static final String OBSERVED_OBJECT_ERROR = "jmx.monitor.error.mbean";

    /**
     * Notification type denoting that the observed attribute is not contained in the observed object.
     * This notification is fired by all kinds of monitors.
     * <BR>The value of this notification type is <CODE>jmx.monitor.error.attribute</CODE>.
     * <p>
     *  通知类型,表示观察到的属性不包含在观察对象中。此通知由各种监视器触发。 <BR>此通知类型的值为<CODE> jmx.monitor.error.attribute </CODE>。
     * 
     */
    public static final String OBSERVED_ATTRIBUTE_ERROR = "jmx.monitor.error.attribute";

    /**
     * Notification type denoting that the type of the observed attribute is not correct.
     * This notification is fired by all kinds of monitors.
     * <BR>The value of this notification type is <CODE>jmx.monitor.error.type</CODE>.
     * <p>
     *  通知类型,表示观察属性的类型不正确。此通知由各种监视器触发。 <BR>此通知类型的值为<CODE> jmx.monitor.error.type </CODE>。
     * 
     */
    public static final String OBSERVED_ATTRIBUTE_TYPE_ERROR = "jmx.monitor.error.type";

    /**
     * Notification type denoting that the type of the thresholds, offset or modulus is not correct.
     * This notification is fired by counter and gauge monitors.
     * <BR>The value of this notification type is <CODE>jmx.monitor.error.threshold</CODE>.
     * <p>
     *  通知类型表示阈值,偏移或模数的类型不正确。此通知由计数器和计数器监视器触发。 <BR>此通知类型的值为<CODE> jmx.monitor.error.threshold </CODE>。
     * 
     */
    public static final String THRESHOLD_ERROR = "jmx.monitor.error.threshold";

    /**
     * Notification type denoting that a non-predefined error type has occurred when trying to get the value of the observed attribute.
     * This notification is fired by all kinds of monitors.
     * <BR>The value of this notification type is <CODE>jmx.monitor.error.runtime</CODE>.
     * <p>
     *  通知类型,表示尝试获取观察属性的值时发生了非预定义的错误类型。此通知由各种监视器触发。 <BR>此通知类型的值为<CODE> jmx.monitor.error.runtime </CODE>。
     * 
     */
    public static final String RUNTIME_ERROR = "jmx.monitor.error.runtime";

    /**
     * Notification type denoting that the observed attribute has reached the threshold value.
     * This notification is only fired by counter monitors.
     * <BR>The value of this notification type is <CODE>jmx.monitor.counter.threshold</CODE>.
     * <p>
     * 表示观察属性已达到阈值的通知类型。此通知仅由计数器监视器触发。 <BR>此通知类型的值为<CODE> jmx.monitor.counter.threshold </CODE>。
     * 
     */
    public static final String THRESHOLD_VALUE_EXCEEDED = "jmx.monitor.counter.threshold";

    /**
     * Notification type denoting that the observed attribute has exceeded the threshold high value.
     * This notification is only fired by gauge monitors.
     * <BR>The value of this notification type is <CODE>jmx.monitor.gauge.high</CODE>.
     * <p>
     *  通知类型表示观察属性已超过阈值高值。此通知仅由仪表监视器触发。 <BR>此通知类型的值为<CODE> jmx.monitor.gauge.high </CODE>。
     * 
     */
    public static final String THRESHOLD_HIGH_VALUE_EXCEEDED = "jmx.monitor.gauge.high";

    /**
     * Notification type denoting that the observed attribute has exceeded the threshold low value.
     * This notification is only fired by gauge monitors.
     * <BR>The value of this notification type is <CODE>jmx.monitor.gauge.low</CODE>.
     * <p>
     *  通知类型,表示观察属性已超过阈值低值。此通知仅由仪表监视器触发。 <BR>此通知类型的值为<CODE> jmx.monitor.gauge.low </CODE>。
     * 
     */
    public static final String THRESHOLD_LOW_VALUE_EXCEEDED = "jmx.monitor.gauge.low";

    /**
     * Notification type denoting that the observed attribute has matched the "string to compare" value.
     * This notification is only fired by string monitors.
     * <BR>The value of this notification type is <CODE>jmx.monitor.string.matches</CODE>.
     * <p>
     *  通知类型,表示观察属性与"要比较的字符串"值匹配。此通知仅由字符串监视器触发。 <BR>此通知类型的值为<CODE> jmx.monitor.string.matches </CODE>。
     * 
     */
    public static final String STRING_TO_COMPARE_VALUE_MATCHED = "jmx.monitor.string.matches";

    /**
     * Notification type denoting that the observed attribute has differed from the "string to compare" value.
     * This notification is only fired by string monitors.
     * <BR>The value of this notification type is <CODE>jmx.monitor.string.differs</CODE>.
     * <p>
     *  通知类型,表示观察属性与"要比较的字符串"值不同。此通知仅由字符串监视器触发。 <BR>此通知类型的值为<CODE> jmx.monitor.string.differs </CODE>。
     * 
     */
    public static final String STRING_TO_COMPARE_VALUE_DIFFERED = "jmx.monitor.string.differs";


    /*
     * ------------------------------------------
     *  PRIVATE VARIABLES
     * ------------------------------------------
     * <p>
     *  ------------------------------------------私人变数------ ------------------------------------
     * 
     */

    /* Serial version */
    private static final long serialVersionUID = -4608189663661929204L;

    /**
    /* <p>
    /* 
     * @serial Monitor notification observed object.
     */
    private ObjectName observedObject = null;

    /**
    /* <p>
    /* 
     * @serial Monitor notification observed attribute.
     */
    private String observedAttribute = null;

    /**
    /* <p>
    /* 
     * @serial Monitor notification derived gauge.
     */
    private Object derivedGauge = null;

    /**
    /* <p>
    /* 
     * @serial Monitor notification release mechanism.
     *         This value is used to keep the threshold/string (depending on the
     *         monitor type) that triggered off this notification.
     */
    private Object trigger = null;


    /*
     * ------------------------------------------
     *  CONSTRUCTORS
     * ------------------------------------------
     * <p>
     *  ------------------------------------------建筑师------- -----------------------------------
     * 
     */

    /**
     * Creates a monitor notification object.
     *
     * <p>
     *  创建监视器通知对象。
     * 
     * 
     * @param type The notification type.
     * @param source The notification producer.
     * @param sequenceNumber The notification sequence number within the source object.
     * @param timeStamp The notification emission date.
     * @param msg The notification message.
     * @param obsObj The object observed by the producer of this notification.
     * @param obsAtt The attribute observed by the producer of this notification.
     * @param derGauge The derived gauge.
     * @param trigger The threshold/string (depending on the monitor type) that triggered the notification.
     */
    MonitorNotification(String type, Object source, long sequenceNumber, long timeStamp, String msg,
                               ObjectName obsObj, String obsAtt, Object derGauge, Object trigger) {

        super(type, source, sequenceNumber, timeStamp, msg);
        this.observedObject = obsObj;
        this.observedAttribute = obsAtt;
        this.derivedGauge = derGauge;
        this.trigger = trigger;
    }

    /*
     * ------------------------------------------
     *  PUBLIC METHODS
     * ------------------------------------------
     * <p>
     * ------------------------------------------公共方法------------------------------------
     * 
     */

    // GETTERS AND SETTERS
    //--------------------

    /**
     * Gets the observed object of this monitor notification.
     *
     * <p>
     *  获取此监视器通知的观察对象。
     * 
     * 
     * @return The observed object.
     */
    public ObjectName getObservedObject() {
        return observedObject;
    }

    /**
     * Gets the observed attribute of this monitor notification.
     *
     * <p>
     *  获取此监视器通知的观察属性。
     * 
     * 
     * @return The observed attribute.
     */
    public String getObservedAttribute() {
        return observedAttribute;
    }

    /**
     * Gets the derived gauge of this monitor notification.
     *
     * <p>
     *  获取此监视器通知的派生标尺。
     * 
     * 
     * @return The derived gauge.
     */
    public Object getDerivedGauge() {
        return derivedGauge;
    }

    /**
     * Gets the threshold/string (depending on the monitor type) that triggered off this monitor notification.
     *
     * <p>
     *  获取触发此监视器通知的阈值/字符串(取决于监视器类型)。
     * 
     * @return The trigger.
     */
    public Object getTrigger() {
        return trigger;
    }

}
