/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2008, Oracle and/or its affiliates. All rights reserved.
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
import static javax.management.monitor.Monitor.NumericalType.*;
import static javax.management.monitor.MonitorNotification.*;

/**
 * Defines a monitor MBean designed to observe the values of a counter
 * attribute.
 *
 * <P> A counter monitor sends a {@link
 * MonitorNotification#THRESHOLD_VALUE_EXCEEDED threshold
 * notification} when the value of the counter reaches or exceeds a
 * threshold known as the comparison level.  The notify flag must be
 * set to <CODE>true</CODE>.
 *
 * <P> In addition, an offset mechanism enables particular counting
 * intervals to be detected.  If the offset value is not zero,
 * whenever the threshold is triggered by the counter value reaching a
 * comparison level, that comparison level is incremented by the
 * offset value.  This is regarded as taking place instantaneously,
 * that is, before the count is incremented.  Thus, for each level,
 * the threshold triggers an event notification every time the count
 * increases by an interval equal to the offset value.
 *
 * <P> If the counter can wrap around its maximum value, the modulus
 * needs to be specified.  The modulus is the value at which the
 * counter is reset to zero.
 *
 * <P> If the counter difference mode is used, the value of the
 * derived gauge is calculated as the difference between the observed
 * counter values for two successive observations.  If this difference
 * is negative, the value of the derived gauge is incremented by the
 * value of the modulus.  The derived gauge value (V[t]) is calculated
 * using the following method:
 *
 * <UL>
 * <LI>if (counter[t] - counter[t-GP]) is positive then
 * V[t] = counter[t] - counter[t-GP]
 * <LI>if (counter[t] - counter[t-GP]) is negative then
 * V[t] = counter[t] - counter[t-GP] + MODULUS
 * </UL>
 *
 * This implementation of the counter monitor requires the observed
 * attribute to be of the type integer (<CODE>Byte</CODE>,
 * <CODE>Integer</CODE>, <CODE>Short</CODE>, <CODE>Long</CODE>).
 *
 *
 * <p>
 *  定义一个监视器MBean,用于观察计数器属性的值
 * 
 *  <P>计数器监视器在计数器的值达到或超过称为比较级别的阈值时发送{@link MonitorNotification#THRESHOLD_VALUE_EXCEEDED阈值通知}。
 * 通知标志必须设置为<CODE> true </CODE>。
 * 
 * 此外,偏移机制使得能够检测特定的计数间隔。如果偏移值不为​​零,每当阈值被计数器值达到比较电平触发时,该比较电平增加偏移值。这被视为作为瞬时发生,即,在计数递增之前。
 * 因此,对于每个级别,阈值在每次计数增加等于偏移值的间隔时触发事件通知。
 * 
 *  <P>如果计数器可以包围其最大值,则需要指定模数。模数是计数器复位为零时的值
 * 
 * <P>如果使用计数器差值模式,则导出的计量器的值被计算为两个连续观察值的观察计数器值之间的差值。如果该差值为负,则导出的计量器的值增加模量导出的计量值(V [t])使用以下方法计算：
 * 
 * <UL>
 *  如果(计数器[t]  - 计数器[t-GP])为正则那么计数器[t]计数器[t-GP] GP])为负,则V [t] =计数器[t]  - 计数器[t-GP] + MODULUS
 * </UL>
 * 
 *  这种计数器监视器的实现需要观察属性是整数类型(<CODE>字节</CODE>,<CODE>整数</CODE>,<CODE> Short </CODE> CODE>)
 * 
 * 
 * @since 1.5
 */
public class CounterMonitor extends Monitor implements CounterMonitorMBean {

    /*
     * ------------------------------------------
     *  PACKAGE CLASSES
     * ------------------------------------------
     * <p>
     * ------------------------------------------包装类------------------------------------
     * 
     */

    static class CounterMonitorObservedObject extends ObservedObject {

        public CounterMonitorObservedObject(ObjectName observedObject) {
            super(observedObject);
        }

        public final synchronized Number getThreshold() {
            return threshold;
        }
        public final synchronized void setThreshold(Number threshold) {
            this.threshold = threshold;
        }
        public final synchronized Number getPreviousScanCounter() {
            return previousScanCounter;
        }
        public final synchronized void setPreviousScanCounter(
                                                  Number previousScanCounter) {
            this.previousScanCounter = previousScanCounter;
        }
        public final synchronized boolean getModulusExceeded() {
            return modulusExceeded;
        }
        public final synchronized void setModulusExceeded(
                                                 boolean modulusExceeded) {
            this.modulusExceeded = modulusExceeded;
        }
        public final synchronized Number getDerivedGaugeExceeded() {
            return derivedGaugeExceeded;
        }
        public final synchronized void setDerivedGaugeExceeded(
                                                 Number derivedGaugeExceeded) {
            this.derivedGaugeExceeded = derivedGaugeExceeded;
        }
        public final synchronized boolean getDerivedGaugeValid() {
            return derivedGaugeValid;
        }
        public final synchronized void setDerivedGaugeValid(
                                                 boolean derivedGaugeValid) {
            this.derivedGaugeValid = derivedGaugeValid;
        }
        public final synchronized boolean getEventAlreadyNotified() {
            return eventAlreadyNotified;
        }
        public final synchronized void setEventAlreadyNotified(
                                               boolean eventAlreadyNotified) {
            this.eventAlreadyNotified = eventAlreadyNotified;
        }
        public final synchronized NumericalType getType() {
            return type;
        }
        public final synchronized void setType(NumericalType type) {
            this.type = type;
        }

        private Number threshold;
        private Number previousScanCounter;
        private boolean modulusExceeded;
        private Number derivedGaugeExceeded;
        private boolean derivedGaugeValid;
        private boolean eventAlreadyNotified;
        private NumericalType type;
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
     * Counter modulus.
     * <BR>The default value is a null Integer object.
     * <p>
     *  计数器模数<BR>默认值为null整数对象
     * 
     */
    private Number modulus = INTEGER_ZERO;

    /**
     * Counter offset.
     * <BR>The default value is a null Integer object.
     * <p>
     *  计数器偏移量<BR>默认值为null整数对象
     * 
     */
    private Number offset = INTEGER_ZERO;

    /**
     * Flag indicating if the counter monitor notifies when exceeding
     * the threshold.  The default value is set to
     * <CODE>false</CODE>.
     * <p>
     *  指示计数器监视器在超过阈值时通知的标志默认值设置为<CODE> false </CODE>
     * 
     */
    private boolean notify = false;

    /**
     * Flag indicating if the counter difference mode is used.  If the
     * counter difference mode is used, the derived gauge is the
     * difference between two consecutive observed values.  Otherwise,
     * the derived gauge is directly the value of the observed
     * attribute.  The default value is set to <CODE>false</CODE>.
     * <p>
     *  表示是否使用计数器差值模式的标志如果使用计数器差值模式,导出的量表是两个连续观测值之间的差异。否则,导出的量表直接是观测属性的值。默认值设置为<CODE> false </CODE>
     * 
     */
    private boolean differenceMode = false;

    /**
     * Initial counter threshold.  This value is used to initialize
     * the threshold when a new object is added to the list and reset
     * the threshold to its initial value each time the counter
     * resets.
     * <p>
     * 初始计数器阈值此值用于在将新对象添加到列表时初始化阈值,并在每次计数器重置时将阈值重置为其初始值
     * 
     */
    private Number initThreshold = INTEGER_ZERO;

    private static final String[] types = {
        RUNTIME_ERROR,
        OBSERVED_OBJECT_ERROR,
        OBSERVED_ATTRIBUTE_ERROR,
        OBSERVED_ATTRIBUTE_TYPE_ERROR,
        THRESHOLD_ERROR,
        THRESHOLD_VALUE_EXCEEDED
    };

    private static final MBeanNotificationInfo[] notifsInfo = {
        new MBeanNotificationInfo(
            types,
            "javax.management.monitor.MonitorNotification",
            "Notifications sent by the CounterMonitor MBean")
    };

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
     *  默认构造函数
     * 
     */
    public CounterMonitor() {
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
     * Starts the counter monitor.
     * <p>
     *  启动计数器监视器
     * 
     */
    public synchronized void start() {
        if (isActive()) {
            MONITOR_LOGGER.logp(Level.FINER, CounterMonitor.class.getName(),
                    "start", "the monitor is already active");
            return;
        }
        // Reset values.
        //
        for (ObservedObject o : observedObjects) {
            final CounterMonitorObservedObject cmo =
                (CounterMonitorObservedObject) o;
            cmo.setThreshold(initThreshold);
            cmo.setModulusExceeded(false);
            cmo.setEventAlreadyNotified(false);
            cmo.setPreviousScanCounter(null);
        }
        doStart();
    }

    /**
     * Stops the counter monitor.
     * <p>
     *  停止计数器监视器
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
     *  获取指定对象的派生尺寸(如果此对象包含在观察MBean的集合中),否则返回<code> null </code>
     * 
     * 
     * @param object the name of the object whose derived gauge is to
     * be returned.
     *
     * @return The derived gauge of the specified object.
     *
     */
    @Override
    public synchronized Number getDerivedGauge(ObjectName object) {
        return (Number) super.getDerivedGauge(object);
    }

    /**
     * Gets the derived gauge timestamp of the specified object, if
     * this object is contained in the set of observed MBeans, or
     * <code>0</code> otherwise.
     *
     * <p>
     *  获取指定对象的派生标尺时间戳(如果此对象包含在观察MBean的集合中),否则为<code> 0 </code>
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
     * Gets the current threshold value of the specified object, if
     * this object is contained in the set of observed MBeans, or
     * <code>null</code> otherwise.
     *
     * <p>
     * 获取指定对象的当前阈值(如果此对象包含在观察到的MBean的集合中),否则返回<code> null </code>
     * 
     * 
     * @param object the name of the object whose threshold is to be
     * returned.
     *
     * @return The threshold value of the specified object.
     *
     */
    public synchronized Number getThreshold(ObjectName object) {
        final CounterMonitorObservedObject o =
            (CounterMonitorObservedObject) getObservedObject(object);
        if (o == null)
            return null;

        // If the counter that is monitored rolls over when it reaches a
        // maximum value, then the modulus value needs to be set to that
        // maximum value. The threshold will then also roll over whenever
        // it strictly exceeds the modulus value. When the threshold rolls
        // over, it is reset to the value that was specified through the
        // latest call to the monitor's setInitThreshold method, before
        // any offsets were applied.
        //
        if (offset.longValue() > 0L &&
            modulus.longValue() > 0L &&
            o.getThreshold().longValue() > modulus.longValue()) {
            return initThreshold;
        } else {
            return o.getThreshold();
        }
    }

    /**
     * Gets the initial threshold value common to all observed objects.
     *
     * <p>
     *  获取所有观察到的对象共有的初始阈值
     * 
     * 
     * @return The initial threshold.
     *
     * @see #setInitThreshold
     *
     */
    public synchronized Number getInitThreshold() {
        return initThreshold;
    }

    /**
     * Sets the initial threshold value common to all observed objects.
     *
     * <BR>The current threshold of every object in the set of
     * observed MBeans is updated consequently.
     *
     * <p>
     *  设置所有观察对象的公共初始阈值
     * 
     *  <BR>因此更新观察MBeans集合中每个对象的当前阈值
     * 
     * 
     * @param value The initial threshold value.
     *
     * @exception IllegalArgumentException The specified
     * threshold is null or the threshold value is less than zero.
     *
     * @see #getInitThreshold
     *
     */
    public synchronized void setInitThreshold(Number value)
        throws IllegalArgumentException {

        if (value == null) {
            throw new IllegalArgumentException("Null threshold");
        }
        if (value.longValue() < 0L) {
            throw new IllegalArgumentException("Negative threshold");
        }

        if (initThreshold.equals(value))
            return;
        initThreshold = value;

        // Reset values.
        //
        int index = 0;
        for (ObservedObject o : observedObjects) {
            resetAlreadyNotified(o, index++, THRESHOLD_ERROR_NOTIFIED);
            final CounterMonitorObservedObject cmo =
                (CounterMonitorObservedObject) o;
            cmo.setThreshold(value);
            cmo.setModulusExceeded(false);
            cmo.setEventAlreadyNotified(false);
        }
    }

    /**
     * Returns the derived gauge of the first object in the set of
     * observed MBeans.
     *
     * <p>
     *  返回观测MBean集合中第一个对象的导出标准
     * 
     * 
     * @return The derived gauge.
     *
     * @deprecated As of JMX 1.2, replaced by
     * {@link #getDerivedGauge(ObjectName)}
     */
    @Deprecated
    public synchronized Number getDerivedGauge() {
        if (observedObjects.isEmpty()) {
            return null;
        } else {
            return (Number) observedObjects.get(0).getDerivedGauge();
        }
    }

    /**
     * Gets the derived gauge timestamp of the first object in the set
     * of observed MBeans.
     *
     * <p>
     *  获取观察到的MBean集合中的第一个对象的导出度量标记时间戳
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
     * Gets the threshold value of the first object in the set of
     * observed MBeans.
     *
     * <p>
     *  获取观察MBean集合中第一个对象的阈值
     * 
     * 
     * @return The threshold value.
     *
     * @see #setThreshold
     *
     * @deprecated As of JMX 1.2, replaced by {@link #getThreshold(ObjectName)}
     */
    @Deprecated
    public synchronized Number getThreshold() {
        return getThreshold(getObservedObject());
    }

    /**
     * Sets the initial threshold value.
     *
     * <p>
     *  设置初始阈值
     * 
     * 
     * @param value The initial threshold value.
     *
     * @exception IllegalArgumentException The specified threshold is
     * null or the threshold value is less than zero.
     *
     * @see #getThreshold()
     *
     * @deprecated As of JMX 1.2, replaced by {@link #setInitThreshold}
     */
    @Deprecated
    public synchronized void setThreshold(Number value)
        throws IllegalArgumentException {
        setInitThreshold(value);
    }

    /**
     * Gets the offset value common to all observed MBeans.
     *
     * <p>
     *  获取所有观察到的MBean公用的偏移值
     * 
     * 
     * @return The offset value.
     *
     * @see #setOffset
     */
    public synchronized Number getOffset() {
        return offset;
    }

    /**
     * Sets the offset value common to all observed MBeans.
     *
     * <p>
     *  设置所有观察到的MBean的公共偏移值
     * 
     * 
     * @param value The offset value.
     *
     * @exception IllegalArgumentException The specified
     * offset is null or the offset value is less than zero.
     *
     * @see #getOffset
     */
    public synchronized void setOffset(Number value)
        throws IllegalArgumentException {

        if (value == null) {
            throw new IllegalArgumentException("Null offset");
        }
        if (value.longValue() < 0L) {
            throw new IllegalArgumentException("Negative offset");
        }

        if (offset.equals(value))
            return;
        offset = value;

        int index = 0;
        for (ObservedObject o : observedObjects) {
            resetAlreadyNotified(o, index++, THRESHOLD_ERROR_NOTIFIED);
        }
    }

    /**
     * Gets the modulus value common to all observed MBeans.
     *
     * <p>
     * 获取所有观察到的MBeans共有的模量值
     * 
     * 
     * @see #setModulus
     *
     * @return The modulus value.
     */
    public synchronized Number getModulus() {
        return modulus;
    }

    /**
     * Sets the modulus value common to all observed MBeans.
     *
     * <p>
     *  设置所有观察到的MBean公用的模值
     * 
     * 
     * @param value The modulus value.
     *
     * @exception IllegalArgumentException The specified
     * modulus is null or the modulus value is less than zero.
     *
     * @see #getModulus
     */
    public synchronized void setModulus(Number value)
        throws IllegalArgumentException {

        if (value == null) {
            throw new IllegalArgumentException("Null modulus");
        }
        if (value.longValue() < 0L) {
            throw new IllegalArgumentException("Negative modulus");
        }

        if (modulus.equals(value))
            return;
        modulus = value;

        // Reset values.
        //
        int index = 0;
        for (ObservedObject o : observedObjects) {
            resetAlreadyNotified(o, index++, THRESHOLD_ERROR_NOTIFIED);
            final CounterMonitorObservedObject cmo =
                (CounterMonitorObservedObject) o;
            cmo.setModulusExceeded(false);
        }
    }

    /**
     * Gets the notification's on/off switch value common to all
     * observed MBeans.
     *
     * <p>
     *  获取所有观察到的MBean公用的通知的开/关切换值
     * 
     * 
     * @return <CODE>true</CODE> if the counter monitor notifies when
     * exceeding the threshold, <CODE>false</CODE> otherwise.
     *
     * @see #setNotify
     */
    public synchronized boolean getNotify() {
        return notify;
    }

    /**
     * Sets the notification's on/off switch value common to all
     * observed MBeans.
     *
     * <p>
     *  将通知的开/关切换值设置为所有观察到的MBean的公共
     * 
     * 
     * @param value The notification's on/off switch value.
     *
     * @see #getNotify
     */
    public synchronized void setNotify(boolean value) {
        if (notify == value)
            return;
        notify = value;
    }

    /**
     * Gets the difference mode flag value common to all observed MBeans.
     *
     * <p>
     *  获取所有观察到的MBean公用的差模式标志值
     * 
     * 
     * @return <CODE>true</CODE> if the difference mode is used,
     * <CODE>false</CODE> otherwise.
     *
     * @see #setDifferenceMode
     */
    public synchronized boolean getDifferenceMode() {
        return differenceMode;
    }

    /**
     * Sets the difference mode flag value common to all observed MBeans.
     *
     * <p>
     *  将差值模式标志值设置为所有观察到的MBean的公共值
     * 
     * 
     * @param value The difference mode flag value.
     *
     * @see #getDifferenceMode
     */
    public synchronized void setDifferenceMode(boolean value) {
        if (differenceMode == value)
            return;
        differenceMode = value;

        // Reset values.
        //
        for (ObservedObject o : observedObjects) {
            final CounterMonitorObservedObject cmo =
                (CounterMonitorObservedObject) o;
            cmo.setThreshold(initThreshold);
            cmo.setModulusExceeded(false);
            cmo.setEventAlreadyNotified(false);
            cmo.setPreviousScanCounter(null);
        }
    }

    /**
     * Returns a <CODE>NotificationInfo</CODE> object containing the
     * name of the Java class of the notification and the notification
     * types sent by the counter monitor.
     * <p>
     *  返回一个<CODE> NotificationInfo </CODE>对象,其中包含通知Java类的名称和计数器监视器发送的通知类型
     * 
     */
    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        return notifsInfo.clone();
    }

    /*
     * ------------------------------------------
     *  PRIVATE METHODS
     * ------------------------------------------
     * <p>
     *  ------------------------------------------私有方法------ ------------------------------------
     * 
     */

    /**
     * Updates the derived gauge attribute of the observed object.
     *
     * <p>
     *  更新观察对象的派生规格属性
     * 
     * 
     * @param scanCounter The value of the observed attribute.
     * @param o The observed object.
     * @return <CODE>true</CODE> if the derived gauge value is valid,
     * <CODE>false</CODE> otherwise.  The derived gauge value is
     * invalid when the differenceMode flag is set to
     * <CODE>true</CODE> and it is the first notification (so we
     * haven't 2 consecutive values to update the derived gauge).
     */
    private synchronized boolean updateDerivedGauge(
        Object scanCounter, CounterMonitorObservedObject o) {

        boolean is_derived_gauge_valid;

        // The counter difference mode is used.
        //
        if (differenceMode) {

            // The previous scan counter has been initialized.
            //
            if (o.getPreviousScanCounter() != null) {
                setDerivedGaugeWithDifference((Number)scanCounter, null, o);

                // If derived gauge is negative it means that the
                // counter has wrapped around and the value of the
                // threshold needs to be reset to its initial value.
                //
                if (((Number)o.getDerivedGauge()).longValue() < 0L) {
                    if (modulus.longValue() > 0L) {
                        setDerivedGaugeWithDifference((Number)scanCounter,
                                                      modulus, o);
                    }
                    o.setThreshold(initThreshold);
                    o.setEventAlreadyNotified(false);
                }
                is_derived_gauge_valid = true;
            }
            // The previous scan counter has not been initialized.
            // We cannot update the derived gauge...
            //
            else {
                is_derived_gauge_valid = false;
            }
            o.setPreviousScanCounter((Number)scanCounter);
        }
        // The counter difference mode is not used.
        //
        else {
            o.setDerivedGauge((Number)scanCounter);
            is_derived_gauge_valid = true;
        }
        return is_derived_gauge_valid;
    }

    /**
     * Updates the notification attribute of the observed object
     * and notifies the listeners only once if the notify flag
     * is set to <CODE>true</CODE>.
     * <p>
     * 更新观察对象的通知属性,如果通知标志设置为<CODE> true </CODE>,则通知监听器一次。
     * 
     * 
     * @param o The observed object.
     */
    private synchronized MonitorNotification updateNotifications(
        CounterMonitorObservedObject o) {

        MonitorNotification n = null;

        // Send notification if notify is true.
        //
        if (!o.getEventAlreadyNotified()) {
            if (((Number)o.getDerivedGauge()).longValue() >=
                o.getThreshold().longValue()) {
                if (notify) {
                    n = new MonitorNotification(THRESHOLD_VALUE_EXCEEDED,
                                                this,
                                                0,
                                                0,
                                                "",
                                                null,
                                                null,
                                                null,
                                                o.getThreshold());
                }
                if (!differenceMode) {
                    o.setEventAlreadyNotified(true);
                }
            }
        } else {
            if (MONITOR_LOGGER.isLoggable(Level.FINER)) {
                final StringBuilder strb = new StringBuilder()
                .append("The notification:")
                .append("\n\tNotification observed object = ")
                .append(o.getObservedObject())
                .append("\n\tNotification observed attribute = ")
                .append(getObservedAttribute())
                .append("\n\tNotification threshold level = ")
                .append(o.getThreshold())
                .append("\n\tNotification derived gauge = ")
                .append(o.getDerivedGauge())
                .append("\nhas already been sent");
                MONITOR_LOGGER.logp(Level.FINER, CounterMonitor.class.getName(),
                        "updateNotifications", strb.toString());
            }
        }

        return n;
    }

    /**
     * Updates the threshold attribute of the observed object.
     * <p>
     *  更新观察对象的阈值属性
     * 
     * 
     * @param o The observed object.
     */
    private synchronized void updateThreshold(CounterMonitorObservedObject o) {

        // Calculate the new threshold value if the threshold has been
        // exceeded and if the offset value is greater than zero.
        //
        if (((Number)o.getDerivedGauge()).longValue() >=
            o.getThreshold().longValue()) {

            if (offset.longValue() > 0L) {

                // Increment the threshold until its value is greater
                // than the one for the current derived gauge.
                //
                long threshold_value = o.getThreshold().longValue();
                while (((Number)o.getDerivedGauge()).longValue() >=
                       threshold_value) {
                    threshold_value += offset.longValue();
                }

                // Set threshold attribute.
                //
                switch (o.getType()) {
                    case INTEGER:
                        o.setThreshold(Integer.valueOf((int)threshold_value));
                        break;
                    case BYTE:
                        o.setThreshold(Byte.valueOf((byte)threshold_value));
                        break;
                    case SHORT:
                        o.setThreshold(Short.valueOf((short)threshold_value));
                        break;
                    case LONG:
                        o.setThreshold(Long.valueOf(threshold_value));
                        break;
                    default:
                        // Should never occur...
                        MONITOR_LOGGER.logp(Level.FINEST,
                                CounterMonitor.class.getName(),
                                "updateThreshold",
                                "the threshold type is invalid");
                        break;
                }

                // If the counter can wrap around when it reaches
                // its maximum and we are not dealing with counter
                // differences then we need to reset the threshold
                // to its initial value too.
                //
                if (!differenceMode) {
                    if (modulus.longValue() > 0L) {
                        if (o.getThreshold().longValue() >
                            modulus.longValue()) {
                            o.setModulusExceeded(true);
                            o.setDerivedGaugeExceeded(
                                (Number) o.getDerivedGauge());
                        }
                    }
                }

                // Threshold value has been modified so we can notify again.
                //
                o.setEventAlreadyNotified(false);
            } else {
                o.setModulusExceeded(true);
                o.setDerivedGaugeExceeded((Number) o.getDerivedGauge());
            }
        }
    }

    /**
     * Sets the derived gauge of the specified observed object when the
     * differenceMode flag is set to <CODE>true</CODE>.  Integer types
     * only are allowed.
     *
     * <p>
     *  当differenceMode标志设置为<CODE> true时,设置指定观察对象的派生规格</CODE>仅允许使用整数类型
     * 
     * 
     * @param scanCounter The value of the observed attribute.
     * @param mod The counter modulus value.
     * @param o The observed object.
     */
    private synchronized void setDerivedGaugeWithDifference(
        Number scanCounter, Number mod, CounterMonitorObservedObject o) {
        /* We do the arithmetic using longs here even though the
           result may end up in a smaller type.  Since
           l == (byte)l (mod 256) for any long l,
           (byte) ((byte)l1 + (byte)l2) == (byte) (l1 + l2),
           and likewise for subtraction.  So it's the same as if
        /* <p>
        /*  由于对于任何长l,(字节)((字节)l1 +(字节)l2)==(字节)(l1 + l2)的l ==(字节)l(mod 256)和同样减法所以它是相同的如果
        /* 
        /* 
           we had done the arithmetic in the smaller type.*/

        long derived =
            scanCounter.longValue() - o.getPreviousScanCounter().longValue();
        if (mod != null)
            derived += modulus.longValue();

        switch (o.getType()) {
        case INTEGER: o.setDerivedGauge(Integer.valueOf((int) derived)); break;
        case BYTE: o.setDerivedGauge(Byte.valueOf((byte) derived)); break;
        case SHORT: o.setDerivedGauge(Short.valueOf((short) derived)); break;
        case LONG: o.setDerivedGauge(Long.valueOf(derived)); break;
        default:
            // Should never occur...
            MONITOR_LOGGER.logp(Level.FINEST, CounterMonitor.class.getName(),
                    "setDerivedGaugeWithDifference",
                    "the threshold type is invalid");
            break;
        }
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
     *  ObservedObject创建的工厂方法
     * 
     * 
     * @since 1.6
     */
    @Override
    ObservedObject createObservedObject(ObjectName object) {
        final CounterMonitorObservedObject cmo =
            new CounterMonitorObservedObject(object);
        cmo.setThreshold(initThreshold);
        cmo.setModulusExceeded(false);
        cmo.setEventAlreadyNotified(false);
        cmo.setPreviousScanCounter(null);
        return cmo;
    }

    /**
     * This method globally sets the derived gauge type for the given
     * "object" and "attribute" after checking that the type of the
     * supplied observed attribute value is one of the value types
     * supported by this monitor.
     * <p>
     * 此方法在检查所提供的观察属性值的类型是此监视器支持的值类型之一之后,全局地为给定的"对象"和"属性"设置派生的量规类型
     * 
     */
    @Override
    synchronized boolean isComparableTypeValid(ObjectName object,
                                               String attribute,
                                               Comparable<?> value) {
        final CounterMonitorObservedObject o =
            (CounterMonitorObservedObject) getObservedObject(object);
        if (o == null)
            return false;

        // Check that the observed attribute is of type "Integer".
        //
        if (value instanceof Integer) {
            o.setType(INTEGER);
        } else if (value instanceof Byte) {
            o.setType(BYTE);
        } else if (value instanceof Short) {
            o.setType(SHORT);
        } else if (value instanceof Long) {
            o.setType(LONG);
        } else {
            return false;
        }
        return true;
    }

    @Override
    synchronized Comparable<?> getDerivedGaugeFromComparable(
                                                  ObjectName object,
                                                  String attribute,
                                                  Comparable<?> value) {
        final CounterMonitorObservedObject o =
            (CounterMonitorObservedObject) getObservedObject(object);
        if (o == null)
            return null;

        // Check if counter has wrapped around.
        //
        if (o.getModulusExceeded()) {
            if (((Number)o.getDerivedGauge()).longValue() <
                o.getDerivedGaugeExceeded().longValue()) {
                    o.setThreshold(initThreshold);
                    o.setModulusExceeded(false);
                    o.setEventAlreadyNotified(false);
            }
        }

        // Update the derived gauge attributes and check the
        // validity of the new value. The derived gauge value
        // is invalid when the differenceMode flag is set to
        // true and it is the first notification, i.e. we
        // haven't got 2 consecutive values to update the
        // derived gauge.
        //
        o.setDerivedGaugeValid(updateDerivedGauge(value, o));

        return (Comparable<?>) o.getDerivedGauge();
    }

    @Override
    synchronized void onErrorNotification(MonitorNotification notification) {
        final CounterMonitorObservedObject o = (CounterMonitorObservedObject)
            getObservedObject(notification.getObservedObject());
        if (o == null)
            return;

        // Reset values.
        //
        o.setModulusExceeded(false);
        o.setEventAlreadyNotified(false);
        o.setPreviousScanCounter(null);
    }

    @Override
    synchronized MonitorNotification buildAlarmNotification(
                                               ObjectName object,
                                               String attribute,
                                               Comparable<?> value) {
        final CounterMonitorObservedObject o =
            (CounterMonitorObservedObject) getObservedObject(object);
        if (o == null)
            return null;

        // Notify the listeners and update the threshold if
        // the updated derived gauge value is valid.
        //
        final MonitorNotification alarm;
        if (o.getDerivedGaugeValid()) {
            alarm = updateNotifications(o);
            updateThreshold(o);
        } else {
            alarm = null;
        }
        return alarm;
    }

    /**
     * Tests if the threshold, offset and modulus of the specified observed
     * object are of the same type as the counter. Only integer types are
     * allowed.
     *
     * Note:
     *   If the optional offset or modulus have not been initialized, their
     *   default value is an Integer object with a value equal to zero.
     *
     * <p>
     *  测试指定观察对象的阈值,偏移和模数是否与计数器的类型相同只允许整数类型
     * 
     * 
     * @param object The observed object.
     * @param attribute The observed attribute.
     * @param value The sample value.
     * @return <CODE>true</CODE> if type is the same,
     * <CODE>false</CODE> otherwise.
     */
    @Override
    synchronized boolean isThresholdTypeValid(ObjectName object,
                                              String attribute,
                                              Comparable<?> value) {
        final CounterMonitorObservedObject o =
            (CounterMonitorObservedObject) getObservedObject(object);
        if (o == null)
            return false;

        Class<? extends Number> c = classForType(o.getType());
        return (c.isInstance(o.getThreshold()) &&
                isValidForType(offset, c) &&
                isValidForType(modulus, c));
    }
}
