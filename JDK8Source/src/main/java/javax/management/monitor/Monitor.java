/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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
import com.sun.jmx.mbeanserver.GetPropertyAction;
import com.sun.jmx.mbeanserver.Introspector;
import java.io.IOException;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanRegistration;
import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;
import javax.management.NotificationBroadcasterSupport;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import static javax.management.monitor.MonitorNotification.*;

/**
 * Defines the part common to all monitor MBeans.
 * A monitor MBean monitors values of an attribute common to a set of observed
 * MBeans. The observed attribute is monitored at intervals specified by the
 * granularity period. A gauge value (derived gauge) is derived from the values
 * of the observed attribute.
 *
 *
 * <p>
 *  定义所有监视器MBean公用的部分。监视器MBean监视对于一组观察到的MBean公共的属性的值。以粒度周期指定的间隔监视所观察的属性。从观察属性的值导出计量值(导出计量)。
 * 
 * 
 * @since 1.5
 */
public abstract class Monitor
    extends NotificationBroadcasterSupport
    implements MonitorMBean, MBeanRegistration {

    /*
     * ------------------------------------------
     *  PACKAGE CLASSES
     * ------------------------------------------
     * <p>
     *  ------------------------------------------包装类------------------------------------
     * 
     */

    static class ObservedObject {

        public ObservedObject(ObjectName observedObject) {
            this.observedObject = observedObject;
        }

        public final ObjectName getObservedObject() {
            return observedObject;
        }
        public final synchronized int getAlreadyNotified() {
            return alreadyNotified;
        }
        public final synchronized void setAlreadyNotified(int alreadyNotified) {
            this.alreadyNotified = alreadyNotified;
        }
        public final synchronized Object getDerivedGauge() {
            return derivedGauge;
        }
        public final synchronized void setDerivedGauge(Object derivedGauge) {
            this.derivedGauge = derivedGauge;
        }
        public final synchronized long getDerivedGaugeTimeStamp() {
            return derivedGaugeTimeStamp;
        }
        public final synchronized void setDerivedGaugeTimeStamp(
                                                 long derivedGaugeTimeStamp) {
            this.derivedGaugeTimeStamp = derivedGaugeTimeStamp;
        }

        private final ObjectName observedObject;
        private int alreadyNotified;
        private Object derivedGauge;
        private long derivedGaugeTimeStamp;
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
     * Attribute to observe.
     * <p>
     *  属性观察。
     * 
     */
    private String observedAttribute;

    /**
     * Monitor granularity period (in milliseconds).
     * The default value is set to 10 seconds.
     * <p>
     *  监视粒度周期(以毫秒为单位)。默认值设置为10秒。
     * 
     */
    private long granularityPeriod = 10000;

    /**
     * Monitor state.
     * The default value is set to <CODE>false</CODE>.
     * <p>
     *  监视状态。默认值设置为<CODE> false </CODE>。
     * 
     */
    private boolean isActive = false;

    /**
     * Monitor sequence number.
     * The default value is set to 0.
     * <p>
     *  监视序列号。默认值设置为0。
     * 
     */
    private final AtomicLong sequenceNumber = new AtomicLong();

    /**
     * Complex type attribute flag.
     * The default value is set to <CODE>false</CODE>.
     * <p>
     *  复杂类型属性标志。默认值设置为<CODE> false </CODE>。
     * 
     */
    private boolean isComplexTypeAttribute = false;

    /**
     * First attribute name extracted from complex type attribute name.
     * <p>
     *  从复杂类型属性名称提取的第一个属性名称。
     * 
     */
    private String firstAttribute;

    /**
     * Remaining attribute names extracted from complex type attribute name.
     * <p>
     *  从复杂类型属性名称中提取的剩余属性名称。
     * 
     */
    private final List<String> remainingAttributes =
        new CopyOnWriteArrayList<String>();

    /**
     * AccessControlContext of the Monitor.start() caller.
     * <p>
     *  Monitor.start()调用者的AccessControlContext。
     * 
     */
    private static final AccessControlContext noPermissionsACC =
            new AccessControlContext(
            new ProtectionDomain[] {new ProtectionDomain(null, null)});
    private volatile AccessControlContext acc = noPermissionsACC;

    /**
     * Scheduler Service.
     * <p>
     *  调度服务。
     * 
     */
    private static final ScheduledExecutorService scheduler =
        Executors.newSingleThreadScheduledExecutor(
            new DaemonThreadFactory("Scheduler"));

    /**
     * Map containing the thread pool executor per thread group.
     * <p>
     *  包含每个线程组的线程池执行程序的映射。
     * 
     */
    private static final Map<ThreadPoolExecutor, Void> executors =
            new WeakHashMap<ThreadPoolExecutor, Void>();

    /**
     * Lock for executors map.
     * <p>
     *  锁定执行器映射。
     * 
     */
    private static final Object executorsLock = new Object();

    /**
     * Maximum Pool Size
     * <p>
     *  最大池大小
     * 
     */
    private static final int maximumPoolSize;
    static {
        final String maximumPoolSizeSysProp = "jmx.x.monitor.maximum.pool.size";
        final String maximumPoolSizeStr = AccessController.doPrivileged(
            new GetPropertyAction(maximumPoolSizeSysProp));
        if (maximumPoolSizeStr == null ||
            maximumPoolSizeStr.trim().length() == 0) {
            maximumPoolSize = 10;
        } else {
            int maximumPoolSizeTmp = 10;
            try {
                maximumPoolSizeTmp = Integer.parseInt(maximumPoolSizeStr);
            } catch (NumberFormatException e) {
                if (MONITOR_LOGGER.isLoggable(Level.FINER)) {
                    MONITOR_LOGGER.logp(Level.FINER, Monitor.class.getName(),
                            "<static initializer>",
                            "Wrong value for " + maximumPoolSizeSysProp +
                            " system property", e);
                    MONITOR_LOGGER.logp(Level.FINER, Monitor.class.getName(),
                            "<static initializer>",
                            maximumPoolSizeSysProp + " defaults to 10");
                }
                maximumPoolSizeTmp = 10;
            }
            if (maximumPoolSizeTmp < 1) {
                maximumPoolSize = 1;
            } else {
                maximumPoolSize = maximumPoolSizeTmp;
            }
        }
    }

    /**
     * Future associated to the current monitor task.
     * <p>
     *  未来与当前监视任务相关联。
     * 
     */
    private Future<?> monitorFuture;

    /**
     * Scheduler task to be executed by the Scheduler Service.
     * <p>
     *  由调度程序服务执行的调度程序任务。
     * 
     */
    private final SchedulerTask schedulerTask = new SchedulerTask();

    /**
     * ScheduledFuture associated to the current scheduler task.
     * <p>
     *  ScheduledFuture关联到当前调度任务。
     * 
     */
    private ScheduledFuture<?> schedulerFuture;

    /*
     * ------------------------------------------
     *  PROTECTED VARIABLES
     * ------------------------------------------
     * <p>
     * ------------------------------------------保护变量------ ------------------------------------
     * 
     */

    /**
     * The amount by which the capacity of the monitor arrays are
     * automatically incremented when their size becomes greater than
     * their capacity.
     * <p>
     *  监视器阵列的容量在其大小大于其容量时自动增加的量。
     * 
     */
    protected final static int capacityIncrement = 16;

    /**
     * The number of valid components in the vector of observed objects.
     *
     * <p>
     *  观察对象向量中的有效分量数。
     * 
     */
    protected int elementCount = 0;

    /**
     * Monitor errors that have already been notified.
     * <p>
     *  监视已经通知的错误。
     * 
     * 
     * @deprecated equivalent to {@link #alreadyNotifieds}[0].
     */
    @Deprecated
    protected int alreadyNotified = 0;

    /**
     * <p>Selected monitor errors that have already been notified.</p>
     *
     * <p>Each element in this array corresponds to an observed object
     * in the vector.  It contains a bit mask of the flags {@link
     * #OBSERVED_OBJECT_ERROR_NOTIFIED} etc, indicating whether the
     * corresponding notification has already been sent for the MBean
     * being monitored.</p>
     *
     * <p>
     *  <p>已经通知的所选监视器错误。</p>
     * 
     *  <p>此数组中的每个元素都对应于向量中的观察对象。
     * 它包含标志{@link #OBSERVED_OBJECT_ERROR_NOTIFIED}等的位掩码,指示是否已经为正在监视的MBean发送相应的通知。</p>。
     * 
     */
    protected int alreadyNotifieds[] = new int[capacityIncrement];

    /**
     * Reference to the MBean server.  This reference is null when the
     * monitor MBean is not registered in an MBean server.  This
     * reference is initialized before the monitor MBean is registered
     * in the MBean server.
     * <p>
     *  引用MBean服务器。当监视器MBean未在MBean服务器中注册时,此引用为null。在将监视器MBean注册到MBean服务器之前,将初始化此引用。
     * 
     * 
     * @see #preRegister(MBeanServer server, ObjectName name)
     */
    protected MBeanServer server;

    // Flags defining possible monitor errors.
    //

    /**
     * This flag is used to reset the {@link #alreadyNotifieds
     * alreadyNotifieds} monitor attribute.
     * <p>
     *  此标志用于重置{@link #alreadyNotifieds alreadyNotifieds}监视器属性。
     * 
     */
    protected static final int RESET_FLAGS_ALREADY_NOTIFIED             = 0;

    /**
     * Flag denoting that a notification has occurred after changing
     * the observed object.  This flag is used to check that the new
     * observed object is registered in the MBean server at the time
     * of the first notification.
     * <p>
     *  表示在更改观察对象后发生通知的标志。该标志用于在第一次通知时检查新的观察对象是否在MBean服务器中注册。
     * 
     */
    protected static final int OBSERVED_OBJECT_ERROR_NOTIFIED           = 1;

    /**
     * Flag denoting that a notification has occurred after changing
     * the observed attribute.  This flag is used to check that the
     * new observed attribute belongs to the observed object at the
     * time of the first notification.
     * <p>
     *  更改观察属性后表示已发生通知的标志。该标志用于在第一次通知时检查新的观察属性属于观察对象。
     * 
     */
    protected static final int OBSERVED_ATTRIBUTE_ERROR_NOTIFIED        = 2;

    /**
     * Flag denoting that a notification has occurred after changing
     * the observed object or the observed attribute.  This flag is
     * used to check that the observed attribute type is correct
     * (depending on the monitor in use) at the time of the first
     * notification.
     * <p>
     * 表示在更改观察对象或观察属性后发生通知的标志。此标志用于在第一次通知时检查观察到的属性类型是否正确(取决于使用的监视器)。
     * 
     */
    protected static final int OBSERVED_ATTRIBUTE_TYPE_ERROR_NOTIFIED   = 4;

    /**
     * Flag denoting that a notification has occurred after changing
     * the observed object or the observed attribute.  This flag is
     * used to notify any exception (except the cases described above)
     * when trying to get the value of the observed attribute at the
     * time of the first notification.
     * <p>
     *  表示在更改观察对象或观察属性后发生通知的标志。此标志用于在尝试在第一次通知时获取观察属性的值时通知任何异常(上述情况除外)。
     * 
     */
    protected static final int RUNTIME_ERROR_NOTIFIED                   = 8;

    /**
     * This field is retained for compatibility but should not be referenced.
     *
     * <p>
     *  此字段为兼容性保留,但不应引用。
     * 
     * 
     * @deprecated No replacement.
     */
    @Deprecated
    protected String dbgTag = Monitor.class.getName();

    /*
     * ------------------------------------------
     *  PACKAGE VARIABLES
     * ------------------------------------------
     * <p>
     *  ------------------------------------------包装变量------ ------------------------------------
     * 
     */

    /**
     * List of ObservedObjects to which the attribute to observe belongs.
     * <p>
     *  要观察的属性所属的ObservedObject的列表。
     * 
     */
    final List<ObservedObject> observedObjects =
        new CopyOnWriteArrayList<ObservedObject>();

    /**
     * Flag denoting that a notification has occurred after changing
     * the threshold. This flag is used to notify any exception
     * related to invalid thresholds settings.
     * <p>
     *  表示在更改阈值后发生通知的标志。此标志用于通知与无效阈值设置相关的任何异常。
     * 
     */
    static final int THRESHOLD_ERROR_NOTIFIED                           = 16;

    /**
     * Enumeration used to keep trace of the derived gauge type
     * in counter and gauge monitors.
     * <p>
     *  用于在计数器和计量表监视器中保持导出的计量器类型的跟踪的枚举。
     * 
     */
    enum NumericalType { BYTE, SHORT, INTEGER, LONG, FLOAT, DOUBLE };

    /**
     * Constant used to initialize all the numeric values.
     * <p>
     *  用于初始化所有数值的常量。
     * 
     */
    static final Integer INTEGER_ZERO = 0;


    /*
     * ------------------------------------------
     *  PUBLIC METHODS
     * ------------------------------------------
     * <p>
     *  ------------------------------------------公共方法------------------------------------
     * 
     */

    /**
     * Allows the monitor MBean to perform any operations it needs
     * before being registered in the MBean server.
     * <P>
     * Initializes the reference to the MBean server.
     *
     * <p>
     *  允许监视器MBean在注册到MBean服务器之前执行其所需的任何操作。
     * <P>
     *  初始化对MBean服务器的引用。
     * 
     * 
     * @param server The MBean server in which the monitor MBean will
     * be registered.
     * @param name The object name of the monitor MBean.
     *
     * @return The name of the monitor MBean registered.
     *
     * @exception Exception
     */
    public ObjectName preRegister(MBeanServer server, ObjectName name)
        throws Exception {

        MONITOR_LOGGER.logp(Level.FINER, Monitor.class.getName(),
                "preRegister(MBeanServer, ObjectName)",
                "initialize the reference on the MBean server");

        this.server = server;
        return name;
    }

    /**
     * Allows the monitor MBean to perform any operations needed after
     * having been registered in the MBean server or after the
     * registration has failed.
     * <P>
     * Not used in this context.
     * <p>
     *  允许监视器MBean在已在MBean服务器中注册或注册失败后执行所需的任何操作。
     * <P>
     * 在此上下文中未使用。
     * 
     */
    public void postRegister(Boolean registrationDone) {
    }

    /**
     * Allows the monitor MBean to perform any operations it needs
     * before being unregistered by the MBean server.
     * <P>
     * Stops the monitor.
     *
     * <p>
     *  允许监视器MBean在MBean服务器取消注册之前执行其所需的任何操作。
     * <P>
     *  停止显示器。
     * 
     * 
     * @exception Exception
     */
    public void preDeregister() throws Exception {

        MONITOR_LOGGER.logp(Level.FINER, Monitor.class.getName(),
                "preDeregister()", "stop the monitor");

        // Stop the Monitor.
        //
        stop();
    }

    /**
     * Allows the monitor MBean to perform any operations needed after
     * having been unregistered by the MBean server.
     * <P>
     * Not used in this context.
     * <p>
     *  允许监视器MBean在MBean服务器取消注册后执行所需的任何操作。
     * <P>
     *  在此上下文中未使用。
     * 
     */
    public void postDeregister() {
    }

    /**
     * Starts the monitor.
     * <p>
     *  启动显示器。
     * 
     */
    public abstract void start();

    /**
     * Stops the monitor.
     * <p>
     *  停止显示器。
     * 
     */
    public abstract void stop();

    // GETTERS AND SETTERS
    //--------------------

    /**
     * Returns the object name of the first object in the set of observed
     * MBeans, or <code>null</code> if there is no such object.
     *
     * <p>
     *  返回观察MBean集合中第一个对象的对象名称,如果没有此类对象,则返回<code> null </code>。
     * 
     * 
     * @return The object being observed.
     *
     * @see #setObservedObject(ObjectName)
     *
     * @deprecated As of JMX 1.2, replaced by {@link #getObservedObjects}
     */
    @Deprecated
    public synchronized ObjectName getObservedObject() {
        if (observedObjects.isEmpty()) {
            return null;
        } else {
            return observedObjects.get(0).getObservedObject();
        }
    }

    /**
     * Removes all objects from the set of observed objects, and then adds the
     * specified object.
     *
     * <p>
     *  从观察对象集中删除所有对象,然后添加指定的对象。
     * 
     * 
     * @param object The object to observe.
     * @exception IllegalArgumentException The specified
     * object is null.
     *
     * @see #getObservedObject()
     *
     * @deprecated As of JMX 1.2, replaced by {@link #addObservedObject}
     */
    @Deprecated
    public synchronized void setObservedObject(ObjectName object)
        throws IllegalArgumentException {
        if (object == null)
            throw new IllegalArgumentException("Null observed object");
        if (observedObjects.size() == 1 && containsObservedObject(object))
            return;
        observedObjects.clear();
        addObservedObject(object);
    }

    /**
     * Adds the specified object in the set of observed MBeans, if this object
     * is not already present.
     *
     * <p>
     *  如果此对象尚不存在,则在观察到的MBean集合中添加指定的对象。
     * 
     * 
     * @param object The object to observe.
     * @exception IllegalArgumentException The specified object is null.
     *
     */
    public synchronized void addObservedObject(ObjectName object)
        throws IllegalArgumentException {

        if (object == null) {
            throw new IllegalArgumentException("Null observed object");
        }

        // Check that the specified object is not already contained.
        //
        if (containsObservedObject(object))
            return;

        // Add the specified object in the list.
        //
        ObservedObject o = createObservedObject(object);
        o.setAlreadyNotified(RESET_FLAGS_ALREADY_NOTIFIED);
        o.setDerivedGauge(INTEGER_ZERO);
        o.setDerivedGaugeTimeStamp(System.currentTimeMillis());
        observedObjects.add(o);

        // Update legacy protected stuff.
        //
        createAlreadyNotified();
    }

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
    public synchronized void removeObservedObject(ObjectName object) {
        // Check for null object.
        //
        if (object == null)
            return;

        final ObservedObject o = getObservedObject(object);
        if (o != null) {
            // Remove the specified object from the list.
            //
            observedObjects.remove(o);
            // Update legacy protected stuff.
            //
            createAlreadyNotified();
        }
    }

    /**
     * Tests whether the specified object is in the set of observed MBeans.
     *
     * <p>
     *  测试指定的对象是否在观察的MBean集合中。
     * 
     * 
     * @param object The object to check.
     * @return <CODE>true</CODE> if the specified object is present,
     * <CODE>false</CODE> otherwise.
     *
     */
    public synchronized boolean containsObservedObject(ObjectName object) {
        return getObservedObject(object) != null;
    }

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
    public synchronized ObjectName[] getObservedObjects() {
        ObjectName[] names = new ObjectName[observedObjects.size()];
        for (int i = 0; i < names.length; i++)
            names[i] = observedObjects.get(i).getObservedObject();
        return names;
    }

    /**
     * Gets the attribute being observed.
     * <BR>The observed attribute is not initialized by default (set to null).
     *
     * <p>
     *  获取所观察的属性。 <BR>默认情况下,观察属性未初始化(设置为null)。
     * 
     * 
     * @return The attribute being observed.
     *
     * @see #setObservedAttribute
     */
    public synchronized String getObservedAttribute() {
        return observedAttribute;
    }

    /**
     * Sets the attribute to observe.
     * <BR>The observed attribute is not initialized by default (set to null).
     *
     * <p>
     *  将属性设置为observe。 <BR>默认情况下,观察属性未初始化(设置为null)。
     * 
     * 
     * @param attribute The attribute to observe.
     * @exception IllegalArgumentException The specified
     * attribute is null.
     *
     * @see #getObservedAttribute
     */
    public void setObservedAttribute(String attribute)
        throws IllegalArgumentException {

        if (attribute == null) {
            throw new IllegalArgumentException("Null observed attribute");
        }

        // Update alreadyNotified array.
        //
        synchronized (this) {
            if (observedAttribute != null &&
                observedAttribute.equals(attribute))
                return;
            observedAttribute = attribute;

            // Reset the complex type attribute information
            // such that it is recalculated again.
            //
            cleanupIsComplexTypeAttribute();

            int index = 0;
            for (ObservedObject o : observedObjects) {
                resetAlreadyNotified(o, index++,
                                     OBSERVED_ATTRIBUTE_ERROR_NOTIFIED |
                                     OBSERVED_ATTRIBUTE_TYPE_ERROR_NOTIFIED);
            }
        }
    }

    /**
     * Gets the granularity period (in milliseconds).
     * <BR>The default value of the granularity period is 10 seconds.
     *
     * <p>
     *  获取粒度周期(以毫秒为单位)。 <BR>粒度周期的默认值为10秒。
     * 
     * 
     * @return The granularity period value.
     *
     * @see #setGranularityPeriod
     */
    public synchronized long getGranularityPeriod() {
        return granularityPeriod;
    }

    /**
     * Sets the granularity period (in milliseconds).
     * <BR>The default value of the granularity period is 10 seconds.
     *
     * <p>
     *  设置粒度周期(以毫秒为单位)。 <BR>粒度周期的默认值为10秒。
     * 
     * 
     * @param period The granularity period value.
     * @exception IllegalArgumentException The granularity
     * period is less than or equal to zero.
     *
     * @see #getGranularityPeriod
     */
    public synchronized void setGranularityPeriod(long period)
        throws IllegalArgumentException {

        if (period <= 0) {
            throw new IllegalArgumentException("Nonpositive granularity " +
                                               "period");
        }

        if (granularityPeriod == period)
            return;
        granularityPeriod = period;

        // Reschedule the scheduler task if the monitor is active.
        //
        if (isActive()) {
            cleanupFutures();
            schedulerFuture = scheduler.schedule(schedulerTask,
                                                 period,
                                                 TimeUnit.MILLISECONDS);
        }
    }

    /**
     * Tests whether the monitor MBean is active.  A monitor MBean is
     * marked active when the {@link #start start} method is called.
     * It becomes inactive when the {@link #stop stop} method is
     * called.
     *
     * <p>
     *  测试监视器MBean是否处于活动状态。当调用{@link #start start}方法时,监视器MBean被标记为活动。当调用{@link #stop stop}方法时,它将变为无效。
     * 
     * 
     * @return <CODE>true</CODE> if the monitor MBean is active,
     * <CODE>false</CODE> otherwise.
     */
    /* This method must be synchronized so that the monitoring thread will
       correctly see modifications to the isActive variable. See the MonitorTask
    /* <p>
    /* 正确地看到对isActive变量的修改。请参阅MonitorTask
    /* 
    /* 
       action executed by the Scheduled Executor Service. */
    public synchronized boolean isActive() {
        return isActive;
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
     * Starts the monitor.
     * <p>
     *  启动显示器。
     * 
     */
    void doStart() {
            MONITOR_LOGGER.logp(Level.FINER, Monitor.class.getName(),
                "doStart()", "start the monitor");

        synchronized (this) {
            if (isActive()) {
                MONITOR_LOGGER.logp(Level.FINER, Monitor.class.getName(),
                        "doStart()", "the monitor is already active");
                return;
            }

            isActive = true;

            // Reset the complex type attribute information
            // such that it is recalculated again.
            //
            cleanupIsComplexTypeAttribute();

            // Cache the AccessControlContext of the Monitor.start() caller.
            // The monitor tasks will be executed within this context.
            //
            acc = AccessController.getContext();

            // Start the scheduler.
            //
            cleanupFutures();
            schedulerTask.setMonitorTask(new MonitorTask());
            schedulerFuture = scheduler.schedule(schedulerTask,
                                                 getGranularityPeriod(),
                                                 TimeUnit.MILLISECONDS);
        }
    }

    /**
     * Stops the monitor.
     * <p>
     *  停止显示器。
     * 
     */
    void doStop() {
        MONITOR_LOGGER.logp(Level.FINER, Monitor.class.getName(),
                "doStop()", "stop the monitor");

        synchronized (this) {
            if (!isActive()) {
                MONITOR_LOGGER.logp(Level.FINER, Monitor.class.getName(),
                        "doStop()", "the monitor is not active");
                return;
            }

            isActive = false;

            // Cancel the scheduler task associated with the
            // scheduler and its associated monitor task.
            //
            cleanupFutures();

            // Reset the AccessControlContext.
            //
            acc = noPermissionsACC;

            // Reset the complex type attribute information
            // such that it is recalculated again.
            //
            cleanupIsComplexTypeAttribute();
        }
    }

    /**
     * Gets the derived gauge of the specified object, if this object is
     * contained in the set of observed MBeans, or <code>null</code> otherwise.
     *
     * <p>
     *  获取指定对象的派生尺寸(如果此对象包含在观察MBean的集合中),否则返回<code> null </code>。
     * 
     * 
     * @param object the name of the object whose derived gauge is to
     * be returned.
     *
     * @return The derived gauge of the specified object.
     *
     * @since 1.6
     */
    synchronized Object getDerivedGauge(ObjectName object) {
        final ObservedObject o = getObservedObject(object);
        return o == null ? null : o.getDerivedGauge();
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
    synchronized long getDerivedGaugeTimeStamp(ObjectName object) {
        final ObservedObject o = getObservedObject(object);
        return o == null ? 0 : o.getDerivedGaugeTimeStamp();
    }

    Object getAttribute(MBeanServerConnection mbsc,
                        ObjectName object,
                        String attribute)
        throws AttributeNotFoundException,
               InstanceNotFoundException,
               MBeanException,
               ReflectionException,
               IOException {
        // Check for "ObservedAttribute" replacement.
        // This could happen if a thread A called setObservedAttribute()
        // while other thread B was in the middle of the monitor() method
        // and received the old observed attribute value.
        //
        final boolean lookupMBeanInfo;
        synchronized (this) {
            if (!isActive())
                throw new IllegalArgumentException(
                    "The monitor has been stopped");
            if (!attribute.equals(getObservedAttribute()))
                throw new IllegalArgumentException(
                    "The observed attribute has been changed");
            lookupMBeanInfo =
                (firstAttribute == null && attribute.indexOf('.') != -1);
        }

        // Look up MBeanInfo if needed
        //
        final MBeanInfo mbi;
        if (lookupMBeanInfo) {
            try {
                mbi = mbsc.getMBeanInfo(object);
            } catch (IntrospectionException e) {
                throw new IllegalArgumentException(e);
            }
        } else {
            mbi = null;
        }

        // Check for complex type attribute
        //
        final String fa;
        synchronized (this) {
            if (!isActive())
                throw new IllegalArgumentException(
                    "The monitor has been stopped");
            if (!attribute.equals(getObservedAttribute()))
                throw new IllegalArgumentException(
                    "The observed attribute has been changed");
            if (firstAttribute == null) {
                if (attribute.indexOf('.') != -1) {
                    MBeanAttributeInfo mbaiArray[] = mbi.getAttributes();
                    for (MBeanAttributeInfo mbai : mbaiArray) {
                        if (attribute.equals(mbai.getName())) {
                            firstAttribute = attribute;
                            break;
                        }
                    }
                    if (firstAttribute == null) {
                        String tokens[] = attribute.split("\\.", -1);
                        firstAttribute = tokens[0];
                        for (int i = 1; i < tokens.length; i++)
                            remainingAttributes.add(tokens[i]);
                        isComplexTypeAttribute = true;
                    }
                } else {
                    firstAttribute = attribute;
                }
            }
            fa = firstAttribute;
        }
        return mbsc.getAttribute(object, fa);
    }

    Comparable<?> getComparableFromAttribute(ObjectName object,
                                             String attribute,
                                             Object value)
        throws AttributeNotFoundException {
        if (isComplexTypeAttribute) {
            Object v = value;
            for (String attr : remainingAttributes)
                v = Introspector.elementFromComplex(v, attr);
            return (Comparable<?>) v;
        } else {
            return (Comparable<?>) value;
        }
    }

    boolean isComparableTypeValid(ObjectName object,
                                  String attribute,
                                  Comparable<?> value) {
        return true;
    }

    String buildErrorNotification(ObjectName object,
                                  String attribute,
                                  Comparable<?> value) {
        return null;
    }

    void onErrorNotification(MonitorNotification notification) {
    }

    Comparable<?> getDerivedGaugeFromComparable(ObjectName object,
                                                String attribute,
                                                Comparable<?> value) {
        return (Comparable<?>) value;
    }

    MonitorNotification buildAlarmNotification(ObjectName object,
                                               String attribute,
                                               Comparable<?> value){
        return null;
    }

    boolean isThresholdTypeValid(ObjectName object,
                                 String attribute,
                                 Comparable<?> value) {
        return true;
    }

    static Class<? extends Number> classForType(NumericalType type) {
        switch (type) {
            case BYTE:
                return Byte.class;
            case SHORT:
                return Short.class;
            case INTEGER:
                return Integer.class;
            case LONG:
                return Long.class;
            case FLOAT:
                return Float.class;
            case DOUBLE:
                return Double.class;
            default:
                throw new IllegalArgumentException(
                    "Unsupported numerical type");
        }
    }

    static boolean isValidForType(Object value, Class<? extends Number> c) {
        return ((value == INTEGER_ZERO) || c.isInstance(value));
    }

    /**
     * Get the specified {@code ObservedObject} if this object is
     * contained in the set of observed MBeans, or {@code null}
     * otherwise.
     *
     * <p>
     *  如果此对象包含在观察MBean的集合中,则获取指定的{@code ObservedObject},否则返回{@code null}。
     * 
     * 
     * @param object the name of the {@code ObservedObject} to retrieve.
     *
     * @return The {@code ObservedObject} associated to the supplied
     * {@code ObjectName}.
     *
     * @since 1.6
     */
    synchronized ObservedObject getObservedObject(ObjectName object) {
        for (ObservedObject o : observedObjects)
            if (o.getObservedObject().equals(object))
                return o;
        return null;
    }

    /**
     * Factory method for ObservedObject creation.
     *
     * <p>
     *  ObservedObject创建的工厂方法。
     * 
     * 
     * @since 1.6
     */
    ObservedObject createObservedObject(ObjectName object) {
        return new ObservedObject(object);
    }

    /**
     * Create the {@link #alreadyNotified} array from
     * the {@code ObservedObject} array list.
     * <p>
     *  从{@code ObservedObject}数组列表中创建{@link #alreadyNotified}数组。
     * 
     */
    synchronized void createAlreadyNotified() {
        // Update elementCount.
        //
        elementCount = observedObjects.size();

        // Update arrays.
        //
        alreadyNotifieds = new int[elementCount];
        for (int i = 0; i < elementCount; i++) {
            alreadyNotifieds[i] = observedObjects.get(i).getAlreadyNotified();
        }
        updateDeprecatedAlreadyNotified();
    }

    /**
     * Update the deprecated {@link #alreadyNotified} field.
     * <p>
     *  更新已弃用的{@link #already已通知}字段。
     * 
     */
    synchronized void updateDeprecatedAlreadyNotified() {
        if (elementCount > 0)
            alreadyNotified = alreadyNotifieds[0];
        else
            alreadyNotified = 0;
    }

    /**
     * Update the {@link #alreadyNotifieds} array element at the given index
     * with the already notified flag in the given {@code ObservedObject}.
     * Ensure the deprecated {@link #alreadyNotified} field is updated
     * if appropriate.
     * <p>
     *  使用给定{@code ObservedObject}中的已通知标志更新给定索引处的{@link #alreadyNotifieds}数组元素。
     * 确保已弃用的{@link #alreadyNotified}字段已更新(如果适用)。
     * 
     */
    synchronized void updateAlreadyNotified(ObservedObject o, int index) {
        alreadyNotifieds[index] = o.getAlreadyNotified();
        if (index == 0)
            updateDeprecatedAlreadyNotified();
    }

    /**
     * Check if the given bits in the given element of {@link #alreadyNotifieds}
     * are set.
     * <p>
     *  检查{@link #alreadyNotifieds}的给定元素中的给定位是否已设置。
     * 
     */
    synchronized boolean isAlreadyNotified(ObservedObject o, int mask) {
        return ((o.getAlreadyNotified() & mask) != 0);
    }

    /**
     * Set the given bits in the given element of {@link #alreadyNotifieds}.
     * Ensure the deprecated {@link #alreadyNotified} field is updated
     * if appropriate.
     * <p>
     *  在{@link #alreadyNotifieds}的给定元素中设置给定的位。确保已弃用的{@link #alreadyNotified}字段已更新(如果适用)。
     * 
     */
    synchronized void setAlreadyNotified(ObservedObject o, int index,
                                         int mask, int an[]) {
        final int i = computeAlreadyNotifiedIndex(o, index, an);
        if (i == -1)
            return;
        o.setAlreadyNotified(o.getAlreadyNotified() | mask);
        updateAlreadyNotified(o, i);
    }

    /**
     * Reset the given bits in the given element of {@link #alreadyNotifieds}.
     * Ensure the deprecated {@link #alreadyNotified} field is updated
     * if appropriate.
     * <p>
     *  重置{@link #alreadyNotifieds}的给定元素中的给定位。确保已弃用的{@link #alreadyNotified}字段已更新(如果适用)。
     * 
     */
    synchronized void resetAlreadyNotified(ObservedObject o,
                                           int index, int mask) {
        o.setAlreadyNotified(o.getAlreadyNotified() & ~mask);
        updateAlreadyNotified(o, index);
    }

    /**
     * Reset all bits in the given element of {@link #alreadyNotifieds}.
     * Ensure the deprecated {@link #alreadyNotified} field is updated
     * if appropriate.
     * <p>
     * 重置{@link #alreadyNotifieds}的给定元素中的所有位。确保已弃用的{@link #alreadyNotified}字段已更新(如果适用)。
     * 
     */
    synchronized void resetAllAlreadyNotified(ObservedObject o,
                                              int index, int an[]) {
        final int i = computeAlreadyNotifiedIndex(o, index, an);
        if (i == -1)
            return;
        o.setAlreadyNotified(RESET_FLAGS_ALREADY_NOTIFIED);
        updateAlreadyNotified(o, index);
    }

    /**
     * Check if the {@link #alreadyNotifieds} array has been modified.
     * If true recompute the index for the given observed object.
     * <p>
     *  检查{@link #alreadyNotifieds}数组是否已修改。如果为true,则重新计算给定观察对象的索引。
     * 
     */
    synchronized int computeAlreadyNotifiedIndex(ObservedObject o,
                                                 int index, int an[]) {
        if (an == alreadyNotifieds) {
            return index;
        } else {
            return observedObjects.indexOf(o);
        }
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
     * This method is used by the monitor MBean to create and send a
     * monitor notification to all the listeners registered for this
     * kind of notification.
     *
     * <p>
     *  监视器MBean使用此方法来创建监视器通知并将其发送到为此类通知注册的所有侦听器。
     * 
     * 
     * @param type The notification type.
     * @param timeStamp The notification emission date.
     * @param msg The notification message.
     * @param derGauge The derived gauge.
     * @param trigger The threshold/string (depending on the monitor
     * type) that triggered off the notification.
     * @param object The ObjectName of the observed object that triggered
     * off the notification.
     * @param onError Flag indicating if this monitor notification is
     * an error notification or an alarm notification.
     */
    private void sendNotification(String type, long timeStamp, String msg,
                                  Object derGauge, Object trigger,
                                  ObjectName object, boolean onError) {
        if (!isActive())
            return;

        if (MONITOR_LOGGER.isLoggable(Level.FINER)) {
            MONITOR_LOGGER.logp(Level.FINER, Monitor.class.getName(),
                    "sendNotification", "send notification: " +
                    "\n\tNotification observed object = " + object +
                    "\n\tNotification observed attribute = " + observedAttribute +
                    "\n\tNotification derived gauge = " + derGauge);
        }

        long seqno = sequenceNumber.getAndIncrement();

        MonitorNotification mn =
            new MonitorNotification(type,
                                    this,
                                    seqno,
                                    timeStamp,
                                    msg,
                                    object,
                                    observedAttribute,
                                    derGauge,
                                    trigger);
        if (onError)
            onErrorNotification(mn);
        sendNotification(mn);
    }

    /**
     * This method is called by the monitor each time
     * the granularity period has been exceeded.
     * <p>
     *  每次超过粒度周期时,监视器会调用此方法。
     * 
     * 
     * @param o The observed object.
     */
    private void monitor(ObservedObject o, int index, int an[]) {

        String attribute;
        String notifType = null;
        String msg = null;
        Object derGauge = null;
        Object trigger = null;
        ObjectName object;
        Comparable<?> value = null;
        MonitorNotification alarm = null;

        if (!isActive())
            return;

        // Check that neither the observed object nor the
        // observed attribute are null.  If the observed
        // object or observed attribute is null, this means
        // that the monitor started before a complete
        // initialization and nothing is done.
        //
        synchronized (this) {
            object = o.getObservedObject();
            attribute = getObservedAttribute();
            if (object == null || attribute == null) {
                return;
            }
        }

        // Check that the observed object is registered in the
        // MBean server and that the observed attribute
        // belongs to the observed object.
        //
        Object attributeValue = null;
        try {
            attributeValue = getAttribute(server, object, attribute);
            if (attributeValue == null)
                if (isAlreadyNotified(
                        o, OBSERVED_ATTRIBUTE_TYPE_ERROR_NOTIFIED))
                    return;
                else {
                    notifType = OBSERVED_ATTRIBUTE_TYPE_ERROR;
                    setAlreadyNotified(
                        o, index, OBSERVED_ATTRIBUTE_TYPE_ERROR_NOTIFIED, an);
                    msg = "The observed attribute value is null.";
                    MONITOR_LOGGER.logp(Level.FINEST, Monitor.class.getName(),
                            "monitor", msg);
                }
        } catch (NullPointerException np_ex) {
            if (isAlreadyNotified(o, RUNTIME_ERROR_NOTIFIED))
                return;
            else {
                notifType = RUNTIME_ERROR;
                setAlreadyNotified(o, index, RUNTIME_ERROR_NOTIFIED, an);
                msg =
                    "The monitor must be registered in the MBean " +
                    "server or an MBeanServerConnection must be " +
                    "explicitly supplied.";
                MONITOR_LOGGER.logp(Level.FINEST, Monitor.class.getName(),
                        "monitor", msg);
                MONITOR_LOGGER.logp(Level.FINEST, Monitor.class.getName(),
                        "monitor", np_ex.toString());
            }
        } catch (InstanceNotFoundException inf_ex) {
            if (isAlreadyNotified(o, OBSERVED_OBJECT_ERROR_NOTIFIED))
                return;
            else {
                notifType = OBSERVED_OBJECT_ERROR;
                setAlreadyNotified(
                    o, index, OBSERVED_OBJECT_ERROR_NOTIFIED, an);
                msg =
                    "The observed object must be accessible in " +
                    "the MBeanServerConnection.";
                MONITOR_LOGGER.logp(Level.FINEST, Monitor.class.getName(),
                        "monitor", msg);
                MONITOR_LOGGER.logp(Level.FINEST, Monitor.class.getName(),
                        "monitor", inf_ex.toString());
            }
        } catch (AttributeNotFoundException anf_ex) {
            if (isAlreadyNotified(o, OBSERVED_ATTRIBUTE_ERROR_NOTIFIED))
                return;
            else {
                notifType = OBSERVED_ATTRIBUTE_ERROR;
                setAlreadyNotified(
                    o, index, OBSERVED_ATTRIBUTE_ERROR_NOTIFIED, an);
                msg =
                    "The observed attribute must be accessible in " +
                    "the observed object.";
                MONITOR_LOGGER.logp(Level.FINEST, Monitor.class.getName(),
                        "monitor", msg);
                MONITOR_LOGGER.logp(Level.FINEST, Monitor.class.getName(),
                        "monitor", anf_ex.toString());
            }
        } catch (MBeanException mb_ex) {
            if (isAlreadyNotified(o, RUNTIME_ERROR_NOTIFIED))
                return;
            else {
                notifType = RUNTIME_ERROR;
                setAlreadyNotified(o, index, RUNTIME_ERROR_NOTIFIED, an);
                msg = mb_ex.getMessage() == null ? "" : mb_ex.getMessage();
                MONITOR_LOGGER.logp(Level.FINEST, Monitor.class.getName(),
                        "monitor", msg);
                MONITOR_LOGGER.logp(Level.FINEST, Monitor.class.getName(),
                        "monitor", mb_ex.toString());
            }
        } catch (ReflectionException ref_ex) {
            if (isAlreadyNotified(o, RUNTIME_ERROR_NOTIFIED)) {
                return;
            } else {
                notifType = RUNTIME_ERROR;
                setAlreadyNotified(o, index, RUNTIME_ERROR_NOTIFIED, an);
                msg = ref_ex.getMessage() == null ? "" : ref_ex.getMessage();
                MONITOR_LOGGER.logp(Level.FINEST, Monitor.class.getName(),
                        "monitor", msg);
                MONITOR_LOGGER.logp(Level.FINEST, Monitor.class.getName(),
                        "monitor", ref_ex.toString());
            }
        } catch (IOException io_ex) {
            if (isAlreadyNotified(o, RUNTIME_ERROR_NOTIFIED))
                return;
            else {
                notifType = RUNTIME_ERROR;
                setAlreadyNotified(o, index, RUNTIME_ERROR_NOTIFIED, an);
                msg = io_ex.getMessage() == null ? "" : io_ex.getMessage();
                MONITOR_LOGGER.logp(Level.FINEST, Monitor.class.getName(),
                        "monitor", msg);
                MONITOR_LOGGER.logp(Level.FINEST, Monitor.class.getName(),
                        "monitor", io_ex.toString());
            }
        } catch (RuntimeException rt_ex) {
            if (isAlreadyNotified(o, RUNTIME_ERROR_NOTIFIED))
                return;
            else {
                notifType = RUNTIME_ERROR;
                setAlreadyNotified(o, index, RUNTIME_ERROR_NOTIFIED, an);
                msg = rt_ex.getMessage() == null ? "" : rt_ex.getMessage();
                MONITOR_LOGGER.logp(Level.FINEST, Monitor.class.getName(),
                        "monitor", msg);
                MONITOR_LOGGER.logp(Level.FINEST, Monitor.class.getName(),
                        "monitor", rt_ex.toString());
            }
        }

        synchronized (this) {

            // Check if the monitor has been stopped.
            //
            if (!isActive())
                return;

            // Check if the observed attribute has been changed.
            //
            // Avoid race condition where mbs.getAttribute() succeeded but
            // another thread replaced the observed attribute meanwhile.
            //
            // Avoid setting computed derived gauge on erroneous attribute.
            //
            if (!attribute.equals(getObservedAttribute()))
                return;

            // Derive a Comparable object from the ObservedAttribute value
            // if the type of the ObservedAttribute value is a complex type.
            //
            if (msg == null) {
                try {
                    value = getComparableFromAttribute(object,
                                                       attribute,
                                                       attributeValue);
                } catch (ClassCastException e) {
                    if (isAlreadyNotified(
                            o, OBSERVED_ATTRIBUTE_TYPE_ERROR_NOTIFIED))
                        return;
                    else {
                        notifType = OBSERVED_ATTRIBUTE_TYPE_ERROR;
                        setAlreadyNotified(o, index,
                            OBSERVED_ATTRIBUTE_TYPE_ERROR_NOTIFIED, an);
                        msg =
                            "The observed attribute value does not " +
                            "implement the Comparable interface.";
                        MONITOR_LOGGER.logp(Level.FINEST,
                                Monitor.class.getName(), "monitor", msg);
                        MONITOR_LOGGER.logp(Level.FINEST,
                                Monitor.class.getName(), "monitor", e.toString());
                    }
                } catch (AttributeNotFoundException e) {
                    if (isAlreadyNotified(o, OBSERVED_ATTRIBUTE_ERROR_NOTIFIED))
                        return;
                    else {
                        notifType = OBSERVED_ATTRIBUTE_ERROR;
                        setAlreadyNotified(
                            o, index, OBSERVED_ATTRIBUTE_ERROR_NOTIFIED, an);
                        msg =
                            "The observed attribute must be accessible in " +
                            "the observed object.";
                        MONITOR_LOGGER.logp(Level.FINEST,
                                Monitor.class.getName(), "monitor", msg);
                        MONITOR_LOGGER.logp(Level.FINEST,
                                Monitor.class.getName(), "monitor", e.toString());
                    }
                } catch (RuntimeException e) {
                    if (isAlreadyNotified(o, RUNTIME_ERROR_NOTIFIED))
                        return;
                    else {
                        notifType = RUNTIME_ERROR;
                        setAlreadyNotified(o, index,
                            RUNTIME_ERROR_NOTIFIED, an);
                        msg = e.getMessage() == null ? "" : e.getMessage();
                        MONITOR_LOGGER.logp(Level.FINEST,
                                Monitor.class.getName(), "monitor", msg);
                        MONITOR_LOGGER.logp(Level.FINEST,
                                Monitor.class.getName(), "monitor", e.toString());
                    }
                }
            }

            // Check that the observed attribute type is supported by this
            // monitor.
            //
            if (msg == null) {
                if (!isComparableTypeValid(object, attribute, value)) {
                    if (isAlreadyNotified(
                            o, OBSERVED_ATTRIBUTE_TYPE_ERROR_NOTIFIED))
                        return;
                    else {
                        notifType = OBSERVED_ATTRIBUTE_TYPE_ERROR;
                        setAlreadyNotified(o, index,
                            OBSERVED_ATTRIBUTE_TYPE_ERROR_NOTIFIED, an);
                        msg = "The observed attribute type is not valid.";
                        MONITOR_LOGGER.logp(Level.FINEST,
                                Monitor.class.getName(), "monitor", msg);
                    }
                }
            }

            // Check that threshold type is supported by this monitor.
            //
            if (msg == null) {
                if (!isThresholdTypeValid(object, attribute, value)) {
                    if (isAlreadyNotified(o, THRESHOLD_ERROR_NOTIFIED))
                        return;
                    else {
                        notifType = THRESHOLD_ERROR;
                        setAlreadyNotified(o, index,
                            THRESHOLD_ERROR_NOTIFIED, an);
                        msg = "The threshold type is not valid.";
                        MONITOR_LOGGER.logp(Level.FINEST,
                                Monitor.class.getName(), "monitor", msg);
                    }
                }
            }

            // Let someone subclassing the monitor to perform additional
            // monitor consistency checks and report errors if necessary.
            //
            if (msg == null) {
                msg = buildErrorNotification(object, attribute, value);
                if (msg != null) {
                    if (isAlreadyNotified(o, RUNTIME_ERROR_NOTIFIED))
                        return;
                    else {
                        notifType = RUNTIME_ERROR;
                        setAlreadyNotified(o, index,
                            RUNTIME_ERROR_NOTIFIED, an);
                        MONITOR_LOGGER.logp(Level.FINEST,
                                Monitor.class.getName(), "monitor", msg);
                    }
                }
            }

            // If no errors were found then clear all error flags and
            // let the monitor decide if a notification must be sent.
            //
            if (msg == null) {
                // Clear all already notified flags.
                //
                resetAllAlreadyNotified(o, index, an);

                // Get derived gauge from comparable value.
                //
                derGauge = getDerivedGaugeFromComparable(object,
                                                         attribute,
                                                         value);

                o.setDerivedGauge(derGauge);
                o.setDerivedGaugeTimeStamp(System.currentTimeMillis());

                // Check if an alarm must be fired.
                //
                alarm = buildAlarmNotification(object,
                                               attribute,
                                               (Comparable<?>) derGauge);
            }

        }

        // Notify monitor errors
        //
        if (msg != null)
            sendNotification(notifType,
                             System.currentTimeMillis(),
                             msg,
                             derGauge,
                             trigger,
                             object,
                             true);

        // Notify monitor alarms
        //
        if (alarm != null && alarm.getType() != null)
            sendNotification(alarm.getType(),
                             System.currentTimeMillis(),
                             alarm.getMessage(),
                             derGauge,
                             alarm.getTrigger(),
                             object,
                             false);
    }

    /**
     * Cleanup the scheduler and monitor tasks futures.
     * <p>
     *  清理调度程序和监视任务未来。
     * 
     */
    private synchronized void cleanupFutures() {
        if (schedulerFuture != null) {
            schedulerFuture.cancel(false);
            schedulerFuture = null;
        }
        if (monitorFuture != null) {
            monitorFuture.cancel(false);
            monitorFuture = null;
        }
    }

    /**
     * Cleanup the "is complex type attribute" info.
     * <p>
     *  清理"是复杂类型属性"信息。
     * 
     */
    private synchronized void cleanupIsComplexTypeAttribute() {
        firstAttribute = null;
        remainingAttributes.clear();
        isComplexTypeAttribute = false;
    }

    /**
     * SchedulerTask nested class: This class implements the Runnable interface.
     *
     * The SchedulerTask is executed periodically with a given fixed delay by
     * the Scheduled Executor Service.
     * <p>
     *  SchedulerTask嵌套类：此类实现Runnable接口。
     * 
     *  SchedulerTask由调度执行器服务以给定的固定延迟周期性地执行。
     * 
     */
    private class SchedulerTask implements Runnable {

        private MonitorTask task;

        /*
         * ------------------------------------------
         *  CONSTRUCTORS
         * ------------------------------------------
         * <p>
         *  ------------------------------------------建筑师------- -----------------------------------
         * 
         */

        public SchedulerTask() {
        }

        /*
         * ------------------------------------------
         *  GETTERS/SETTERS
         * ------------------------------------------
         * <p>
         *  ------------------------------------------ GETTERS / SETTERS ----- ---------------------------------
         * ----。
         * 
         */

        public void setMonitorTask(MonitorTask task) {
            this.task = task;
        }

        /*
         * ------------------------------------------
         *  PUBLIC METHODS
         * ------------------------------------------
         * <p>
         *  ------------------------------------------公共方法------------------------------------
         * 
         */

        public void run() {
            synchronized (Monitor.this) {
                Monitor.this.monitorFuture = task.submit();
            }
        }
    }

    /**
     * MonitorTask nested class: This class implements the Runnable interface.
     *
     * The MonitorTask is executed periodically with a given fixed delay by the
     * Scheduled Executor Service.
     * <p>
     *  MonitorTask嵌套类：此类实现Runnable接口。
     * 
     *  MonitorTask由计划执行器服务以给定的固定延迟周期性地执行。
     * 
     */
    private class MonitorTask implements Runnable {

        private ThreadPoolExecutor executor;

        /*
         * ------------------------------------------
         *  CONSTRUCTORS
         * ------------------------------------------
         * <p>
         *  ------------------------------------------建筑师------- -----------------------------------
         * 
         */

        public MonitorTask() {
            // Find out if there's already an existing executor for the calling
            // thread and reuse it. Otherwise, create a new one and store it in
            // the executors map. If there is a SecurityManager, the group of
            // System.getSecurityManager() is used, else the group of the thread
            // instantiating this MonitorTask, i.e. the group of the thread that
            // calls "Monitor.start()".
            SecurityManager s = System.getSecurityManager();
            ThreadGroup group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
            synchronized (executorsLock) {
                for (ThreadPoolExecutor e : executors.keySet()) {
                    DaemonThreadFactory tf =
                            (DaemonThreadFactory) e.getThreadFactory();
                    ThreadGroup tg = tf.getThreadGroup();
                    if (tg == group) {
                        executor = e;
                        break;
                    }
                }
                if (executor == null) {
                    executor = new ThreadPoolExecutor(
                            maximumPoolSize,
                            maximumPoolSize,
                            60L,
                            TimeUnit.SECONDS,
                            new LinkedBlockingQueue<Runnable>(),
                            new DaemonThreadFactory("ThreadGroup<" +
                            group.getName() + "> Executor", group));
                    executor.allowCoreThreadTimeOut(true);
                    executors.put(executor, null);
                }
            }
        }

        /*
         * ------------------------------------------
         *  PUBLIC METHODS
         * ------------------------------------------
         * <p>
         * ------------------------------------------公共方法------------------------------------
         * 
         */

        public Future<?> submit() {
            return executor.submit(this);
        }

        public void run() {
            final ScheduledFuture<?> sf;
            final AccessControlContext ac;
            synchronized (Monitor.this) {
                sf = Monitor.this.schedulerFuture;
                ac = Monitor.this.acc;
            }
            PrivilegedAction<Void> action = new PrivilegedAction<Void>() {
                public Void run() {
                    if (Monitor.this.isActive()) {
                        final int an[] = alreadyNotifieds;
                        int index = 0;
                        for (ObservedObject o : Monitor.this.observedObjects) {
                            if (Monitor.this.isActive()) {
                                Monitor.this.monitor(o, index++, an);
                            }
                        }
                    }
                    return null;
                }
            };
            if (ac == null) {
                throw new SecurityException("AccessControlContext cannot be null");
            }
            AccessController.doPrivileged(action, ac);
            synchronized (Monitor.this) {
                if (Monitor.this.isActive() &&
                    Monitor.this.schedulerFuture == sf) {
                    Monitor.this.monitorFuture = null;
                    Monitor.this.schedulerFuture =
                        scheduler.schedule(Monitor.this.schedulerTask,
                                           Monitor.this.getGranularityPeriod(),
                                           TimeUnit.MILLISECONDS);
                }
            }
        }
    }

    /**
     * Daemon thread factory used by the monitor executors.
     * <P>
     * This factory creates all new threads used by an Executor in
     * the same ThreadGroup. If there is a SecurityManager, it uses
     * the group of System.getSecurityManager(), else the group of
     * the thread instantiating this DaemonThreadFactory. Each new
     * thread is created as a daemon thread with priority
     * Thread.NORM_PRIORITY. New threads have names accessible via
     * Thread.getName() of "{@literal JMX Monitor <pool-name> Pool [Thread-M]}",
     * where M is the sequence number of the thread created by this
     * factory.
     * <p>
     *  守护线程工厂由监视器执行者使用。
     * <P>
     *  此工厂创建同一线程组中的执行程序使用的所有新线程。
     * 如果有一个SecurityManager,它使用的组System.getSecurityManager(),否则组的线程实例化这个DaemonThreadFactory。
     */
    private static class DaemonThreadFactory implements ThreadFactory {
        final ThreadGroup group;
        final AtomicInteger threadNumber = new AtomicInteger(1);
        final String namePrefix;
        static final String nameSuffix = "]";

        public DaemonThreadFactory(String poolName) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                                  Thread.currentThread().getThreadGroup();
            namePrefix = "JMX Monitor " + poolName + " Pool [Thread-";
        }

        public DaemonThreadFactory(String poolName, ThreadGroup threadGroup) {
            group = threadGroup;
            namePrefix = "JMX Monitor " + poolName + " Pool [Thread-";
        }

        public ThreadGroup getThreadGroup() {
            return group;
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group,
                                  r,
                                  namePrefix +
                                  threadNumber.getAndIncrement() +
                                  nameSuffix,
                                  0);
            t.setDaemon(true);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
}
