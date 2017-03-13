/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.lang.management;

import java.util.Map;

/**
 * The management interface for the thread system of
 * the Java virtual machine.
 *
 * <p> A Java virtual machine has a single instance of the implementation
 * class of this interface.  This instance implementing this interface is
 * an <a href="ManagementFactory.html#MXBean">MXBean</a>
 * that can be obtained by calling
 * the {@link ManagementFactory#getThreadMXBean} method or
 * from the {@link ManagementFactory#getPlatformMBeanServer
 * platform <tt>MBeanServer</tt>} method.
 *
 * <p>The <tt>ObjectName</tt> for uniquely identifying the MXBean for
 * the thread system within an MBeanServer is:
 * <blockquote>
 *    {@link ManagementFactory#THREAD_MXBEAN_NAME
 *           <tt>java.lang:type=Threading</tt>}
 * </blockquote>
 *
 * It can be obtained by calling the
 * {@link PlatformManagedObject#getObjectName} method.
 *
 * <h3>Thread ID</h3>
 * Thread ID is a positive long value returned by calling the
 * {@link java.lang.Thread#getId} method for a thread.
 * The thread ID is unique during its lifetime.  When a thread
 * is terminated, this thread ID may be reused.
 *
 * <p> Some methods in this interface take a thread ID or an array
 * of thread IDs as the input parameter and return per-thread information.
 *
 * <h3>Thread CPU time</h3>
 * A Java virtual machine implementation may support measuring
 * the CPU time for the current thread, for any thread, or for no threads.
 *
 * <p>
 * The {@link #isThreadCpuTimeSupported} method can be used to determine
 * if a Java virtual machine supports measuring of the CPU time for any
 * thread.  The {@link #isCurrentThreadCpuTimeSupported} method can
 * be used to determine if a Java virtual machine supports measuring of
 * the CPU time for the current  thread.
 * A Java virtual machine implementation that supports CPU time measurement
 * for any thread will also support that for the current thread.
 *
 * <p> The CPU time provided by this interface has nanosecond precision
 * but not necessarily nanosecond accuracy.
 *
 * <p>
 * A Java virtual machine may disable CPU time measurement
 * by default.
 * The {@link #isThreadCpuTimeEnabled} and {@link #setThreadCpuTimeEnabled}
 * methods can be used to test if CPU time measurement is enabled
 * and to enable/disable this support respectively.
 * Enabling thread CPU measurement could be expensive in some
 * Java virtual machine implementations.
 *
 * <h3>Thread Contention Monitoring</h3>
 * Some Java virtual machines may support thread contention monitoring.
 * When thread contention monitoring is enabled, the accumulated elapsed
 * time that the thread has blocked for synchronization or waited for
 * notification will be collected and returned in the
 * <a href="ThreadInfo.html#SyncStats"><tt>ThreadInfo</tt></a> object.
 * <p>
 * The {@link #isThreadContentionMonitoringSupported} method can be used to
 * determine if a Java virtual machine supports thread contention monitoring.
 * The thread contention monitoring is disabled by default.  The
 * {@link #setThreadContentionMonitoringEnabled} method can be used to enable
 * thread contention monitoring.
 *
 * <h3>Synchronization Information and Deadlock Detection</h3>
 * Some Java virtual machines may support monitoring of
 * {@linkplain #isObjectMonitorUsageSupported object monitor usage} and
 * {@linkplain #isSynchronizerUsageSupported ownable synchronizer usage}.
 * The {@link #getThreadInfo(long[], boolean, boolean)} and
 * {@link #dumpAllThreads} methods can be used to obtain the thread stack trace
 * and synchronization information including which
 * {@linkplain LockInfo <i>lock</i>} a thread is blocked to
 * acquire or waiting on and which locks the thread currently owns.
 * <p>
 * The <tt>ThreadMXBean</tt> interface provides the
 * {@link #findMonitorDeadlockedThreads} and
 * {@link #findDeadlockedThreads} methods to find deadlocks in
 * the running application.
 *
 * <p>
 *  Java虚拟机的线程系统的管理接口
 * 
 *  <p> Java虚拟机具有此接口的实现类的单个实例实现此接口的实例是<a href=\"ManagementFactoryhtml#MXBean\"> MXBean </a>,可通过调用{@link ManagementFactory#getThreadMXBean}
 * 方法或{@link ManagementFactory#getPlatformMBeanServer platform <tt> MBeanServer </tt>}方法。
 * 
 *  <p>用于唯一标识MBeanServer中的线程系统的MXBean的<tt> ObjectName </tt>是：
 * <blockquote>
 *  {@link ManagementFactory#THREAD_MXBEAN_NAME <tt> javalang：type = Threading </tt>}
 * </blockquote>
 * 
 * 它可以通过调用{@link PlatformManagedObject#getObjectName}方法获得
 * 
 *  <h3>线程ID </h3>线程ID是通过调用线程的{@link javalangThread#getId}方法返回的正长整数值线程ID在其生命周期内是唯一的当线程终止时,此线程ID可能重复使用
 * 
 *  <p>此接口中的一些方法将线程ID或线程ID数组作为输入参数并返回每线程信息
 * 
 *  <h3>线程CPU时间</h3> Java虚拟机实现可以支持测量当前线程,任何线程或无线程的CPU时间
 * 
 * <p>
 * {@link #isThreadCpuTimeSupported}方法可用于确定Java虚拟机是否支持测量任何线程的CPU时间{@link #isCurrentThreadCpuTimeSupported}
 * 方法可用于确定Java虚拟机是否支持CPU的测量当前线程的时间支持任何线程的CPU时间测量的Java虚拟机实现也将支持当前线程的时间。
 * 
 *  <p>此接口提供的CPU时间具有纳秒精度,但不一定是纳秒精度
 * 
 * <p>
 * Java虚拟机可以默认禁用CPU时间测量。
 * {@link #isThreadCpuTimeEnabled}和{@link #setThreadCpuTimeEnabled}方法可用于测试是否启用CPU时间测量,并分别启用/禁用此支持。
 * 启用线程CPU测量在一些Java虚拟机实现中是昂贵的。
 * 
 *  <h3>线程争用监视</h3>某些Java虚拟机可能支持线程争用监视当启用线程争用监视时,线程阻止同步或等待通知的累积经过时间将被收集并返回<a href ="ThreadInfohtml#SyncStats">
 *  <tt> ThreadInfo </tt> </a>对象。
 * <p>
 * {@link #isThreadContentionMonitoringSupported}方法可用于确定Java虚拟机是否支持线程争用监视线程争用监视默认情况下禁用{@link #setThreadContentionMonitoringEnabled}
 * 方法可用于启用线程争用监视。
 * 
 * <h3>同步信息和死锁检测</h3>一些Java虚拟机可能支持监视{@linkplain #isObjectMonitorUsageSupported对象监视器使用情况}和{@linkplain #isSynchronizerUsageSupported拥有的同步器使用情况}
 *  {@link #getThreadInfo(long [布尔,布尔)}和{@link #dumpAllThreads}方法可用于获得线程堆栈跟踪和同步信息,包括哪个{@linkplain LockInfo </i>}
 * 锁定线程以获取或等待,以及它锁定线程当前拥有。
 * <p>
 *  <tt> ThreadMXBean </tt>接口提供{@link #findMonitorDeadlockedThreads}和{@link #findDeadlockedThreads}方法,以在
 * 运行的应用程序中找到死锁。
 * 
 * 
 * @see ManagementFactory#getPlatformMXBeans(Class)
 * @see <a href="../../../javax/management/package-summary.html">
 *      JMX Specification.</a>
 * @see <a href="package-summary.html#examples">
 *      Ways to Access MXBeans</a>
 *
 * @author  Mandy Chung
 * @since   1.5
 */

public interface ThreadMXBean extends PlatformManagedObject {
    /**
     * Returns the current number of live threads including both
     * daemon and non-daemon threads.
     *
     * <p>
     * 返回包括守护程序线程和非守护程序线程的当前活动线程数
     * 
     * 
     * @return the current number of live threads.
     */
    public int getThreadCount();

    /**
     * Returns the peak live thread count since the Java virtual machine
     * started or peak was reset.
     *
     * <p>
     *  返回自Java虚拟机启动或峰值复位后的峰值活线程计数
     * 
     * 
     * @return the peak live thread count.
     */
    public int getPeakThreadCount();

    /**
     * Returns the total number of threads created and also started
     * since the Java virtual machine started.
     *
     * <p>
     *  返回自Java虚拟机启动以来已创建并启动的线程总数
     * 
     * 
     * @return the total number of threads started.
     */
    public long getTotalStartedThreadCount();

    /**
     * Returns the current number of live daemon threads.
     *
     * <p>
     *  返回当前的守护进程线程数
     * 
     * 
     * @return the current number of live daemon threads.
     */
    public int getDaemonThreadCount();

    /**
     * Returns all live thread IDs.
     * Some threads included in the returned array
     * may have been terminated when this method returns.
     *
     * <p>
     *  返回所有活动线程ID返回数组中包含的某些线程可能在此方法返回时已终止
     * 
     * 
     * @return an array of <tt>long</tt>, each is a thread ID.
     *
     * @throws java.lang.SecurityException if a security manager
     *         exists and the caller does not have
     *         ManagementPermission("monitor").
     */
    public long[] getAllThreadIds();

    /**
     * Returns the thread info for a thread of the specified
     * <tt>id</tt> with no stack trace.
     * This method is equivalent to calling:
     * <blockquote>
     *   {@link #getThreadInfo(long, int) getThreadInfo(id, 0);}
     * </blockquote>
     *
     * <p>
     * This method returns a <tt>ThreadInfo</tt> object representing
     * the thread information for the thread of the specified ID.
     * The stack trace, locked monitors, and locked synchronizers
     * in the returned <tt>ThreadInfo</tt> object will
     * be empty.
     *
     * If a thread of the given ID is not alive or does not exist,
     * this method will return <tt>null</tt>.  A thread is alive if
     * it has been started and has not yet died.
     *
     * <p>
     * <b>MBeanServer access</b>:<br>
     * The mapped type of <tt>ThreadInfo</tt> is
     * <tt>CompositeData</tt> with attributes as specified in the
     * {@link ThreadInfo#from ThreadInfo.from} method.
     *
     * <p>
     *  返回指定<tt> id </tt>的线程的线程信息,没有堆栈跟踪此方法等效于调用：
     * <blockquote>
     *  {@link #getThreadInfo(long,int)getThreadInfo(id,0);}
     * </blockquote>
     * 
     * <p>
     * 此方法返回表示指定ID的线程的线程信息的<tt> ThreadInfo </tt>对象。返回的<tt> ThreadInfo </tt>对象中的堆栈跟踪,锁定监视器和锁定同步器将为空
     * 
     *  如果给定ID的线程不存活或不存在,则此方法将返回<tt> null </tt>如果线程已启动且尚未死亡,则线程仍然存活
     * 
     * <p>
     *  <b> MBeanServer访问</b>：<br> <tt> ThreadInfo </tt>的映射类型是具有在{@link ThreadInfo#from ThreadInfofrom}方法中指定
     * 的属性的<tt> CompositeData </tt>。
     * 
     * 
     * @param id the thread ID of the thread. Must be positive.
     *
     * @return a {@link ThreadInfo} object for the thread of the given ID
     * with no stack trace, no locked monitor and no synchronizer info;
     * <tt>null</tt> if the thread of the given ID is not alive or
     * it does not exist.
     *
     * @throws IllegalArgumentException if {@code id <= 0}.
     * @throws java.lang.SecurityException if a security manager
     *         exists and the caller does not have
     *         ManagementPermission("monitor").
     */
    public ThreadInfo getThreadInfo(long id);

    /**
     * Returns the thread info for each thread
     * whose ID is in the input array <tt>ids</tt> with no stack trace.
     * This method is equivalent to calling:
     * <blockquote><pre>
     *   {@link #getThreadInfo(long[], int) getThreadInfo}(ids, 0);
     * </pre></blockquote>
     *
     * <p>
     * This method returns an array of the <tt>ThreadInfo</tt> objects.
     * The stack trace, locked monitors, and locked synchronizers
     * in each <tt>ThreadInfo</tt> object will be empty.
     *
     * If a thread of a given ID is not alive or does not exist,
     * the corresponding element in the returned array will
     * contain <tt>null</tt>.  A thread is alive if
     * it has been started and has not yet died.
     *
     * <p>
     * <b>MBeanServer access</b>:<br>
     * The mapped type of <tt>ThreadInfo</tt> is
     * <tt>CompositeData</tt> with attributes as specified in the
     * {@link ThreadInfo#from ThreadInfo.from} method.
     *
     * <p>
     * 返回其ID在输入数组中的每个线程的线程信息<tt> ids </tt>没有堆栈跟踪此方法等效于调用：<blockquote> <pre> {@link #getThreadInfo(long [],int )getThreadInfo}
     * (ids,0); </pre> </blockquote>。
     * 
     * <p>
     *  此方法返回<tt> ThreadInfo </tt>对象的数组每个<tt> ThreadInfo </tt>对象中的堆栈跟踪,锁定监视器和锁定同步器将为空
     * 
     *  如果给定ID的线程不存活或不存在,则返回数组中的相应元素将包含<tt> null </tt>如果线程已经启动并且尚未死亡
     * 
     * <p>
     *  <b> MBeanServer访问</b>：<br> <tt> ThreadInfo </tt>的映射类型是具有在{@link ThreadInfo#from ThreadInfofrom}方法中指定
     * 的属性的<tt> CompositeData </tt>。
     * 
     * 
     * @param ids an array of thread IDs.
     * @return an array of the {@link ThreadInfo} objects, each containing
     * information about a thread whose ID is in the corresponding
     * element of the input array of IDs
     * with no stack trace, no locked monitor and no synchronizer info.
     *
     * @throws IllegalArgumentException if any element in the input array
     *         <tt>ids</tt> is {@code <= 0}.
     * @throws java.lang.SecurityException if a security manager
     *         exists and the caller does not have
     *         ManagementPermission("monitor").
     */
    public ThreadInfo[] getThreadInfo(long[] ids);

    /**
     * Returns a thread info for a thread of the specified <tt>id</tt>,
     * with stack trace of a specified number of stack trace elements.
     * The <tt>maxDepth</tt> parameter indicates the maximum number of
     * {@link StackTraceElement} to be retrieved from the stack trace.
     * If <tt>maxDepth == Integer.MAX_VALUE</tt>, the entire stack trace of
     * the thread will be dumped.
     * If <tt>maxDepth == 0</tt>, no stack trace of the thread
     * will be dumped.
     * This method does not obtain the locked monitors and locked
     * synchronizers of the thread.
     * <p>
     * When the Java virtual machine has no stack trace information
     * about a thread or <tt>maxDepth == 0</tt>,
     * the stack trace in the
     * <tt>ThreadInfo</tt> object will be an empty array of
     * <tt>StackTraceElement</tt>.
     *
     * <p>
     * If a thread of the given ID is not alive or does not exist,
     * this method will return <tt>null</tt>.  A thread is alive if
     * it has been started and has not yet died.
     *
     * <p>
     * <b>MBeanServer access</b>:<br>
     * The mapped type of <tt>ThreadInfo</tt> is
     * <tt>CompositeData</tt> with attributes as specified in the
     * {@link ThreadInfo#from ThreadInfo.from} method.
     *
     * <p>
     * 返回指定<tt> id </tt>的线程的线程信息,堆栈跟踪指定数量的堆栈跟踪元素<tt> maxDepth </tt>参数指示{@link StackTraceElement}要从堆栈跟踪中检索如果<tt>
     *  maxDepth == IntegerMAX_VALUE </tt>,线程的整个堆栈跟踪将被转储如果<tt> maxDepth == 0 </tt>,线程的堆栈跟踪将不会被转储此方法不会获取锁定的监视
     * 器和锁定的线程同步器。
     * <p>
     *  当Java虚拟机没有关于线程或<tt> maxDepth == 0 </tt>的堆栈跟踪信息时,<tt> ThreadInfo </tt>对象中的堆栈跟踪将是一个<tt> StackTraceElem
     * ent </tt>。
     * 
     * <p>
     * 如果给定ID的线程不存活或不存在,则此方法将返回<tt> null </tt>如果线程已启动且尚未死亡,则线程仍然存活
     * 
     * <p>
     *  <b> MBeanServer访问</b>：<br> <tt> ThreadInfo </tt>的映射类型是具有在{@link ThreadInfo#from ThreadInfofrom}方法中指定
     * 的属性的<tt> CompositeData </tt>。
     * 
     * 
     * @param id the thread ID of the thread. Must be positive.
     * @param maxDepth the maximum number of entries in the stack trace
     * to be dumped. <tt>Integer.MAX_VALUE</tt> could be used to request
     * the entire stack to be dumped.
     *
     * @return a {@link ThreadInfo} of the thread of the given ID
     * with no locked monitor and synchronizer info.
     * <tt>null</tt> if the thread of the given ID is not alive or
     * it does not exist.
     *
     * @throws IllegalArgumentException if {@code id <= 0}.
     * @throws IllegalArgumentException if <tt>maxDepth is negative</tt>.
     * @throws java.lang.SecurityException if a security manager
     *         exists and the caller does not have
     *         ManagementPermission("monitor").
     *
     */
    public ThreadInfo getThreadInfo(long id, int maxDepth);

    /**
     * Returns the thread info for each thread
     * whose ID is in the input array <tt>ids</tt>,
     * with stack trace of a specified number of stack trace elements.
     * The <tt>maxDepth</tt> parameter indicates the maximum number of
     * {@link StackTraceElement} to be retrieved from the stack trace.
     * If <tt>maxDepth == Integer.MAX_VALUE</tt>, the entire stack trace of
     * the thread will be dumped.
     * If <tt>maxDepth == 0</tt>, no stack trace of the thread
     * will be dumped.
     * This method does not obtain the locked monitors and locked
     * synchronizers of the threads.
     * <p>
     * When the Java virtual machine has no stack trace information
     * about a thread or <tt>maxDepth == 0</tt>,
     * the stack trace in the
     * <tt>ThreadInfo</tt> object will be an empty array of
     * <tt>StackTraceElement</tt>.
     * <p>
     * This method returns an array of the <tt>ThreadInfo</tt> objects,
     * each is the thread information about the thread with the same index
     * as in the <tt>ids</tt> array.
     * If a thread of the given ID is not alive or does not exist,
     * <tt>null</tt> will be set in the corresponding element
     * in the returned array.  A thread is alive if
     * it has been started and has not yet died.
     *
     * <p>
     * <b>MBeanServer access</b>:<br>
     * The mapped type of <tt>ThreadInfo</tt> is
     * <tt>CompositeData</tt> with attributes as specified in the
     * {@link ThreadInfo#from ThreadInfo.from} method.
     *
     * <p>
     * 返回其ID在输入数组<tt> ids </tt>中的每个线程的线程信息,堆栈跟踪具有指定数量的堆栈跟踪元素<tt> maxDepth </tt>参数指示最大数目{ @link StackTraceElement}
     * 从堆栈跟踪中检索如果<tt> maxDepth == IntegerMAX_VALUE </tt>,线程的整个堆栈跟踪将被转储如果<tt> maxDepth == 0 </tt>,没有堆栈跟踪的线程将被
     * 转储此方法不会获得锁定的监视器和线程的锁定同步器。
     * <p>
     *  当Java虚拟机没有关于线程或<tt> maxDepth == 0 </tt>的堆栈跟踪信息时,<tt> ThreadInfo </tt>对象中的堆栈跟踪将是一个<tt> StackTraceElem
     * ent </tt>。
     * <p>
     * 此方法返回<tt> ThreadInfo </tt>对象的数组,每个对象都是与<tt> ids </tt>数组中索引相同的线程的线程信息如果给定ID的线程不是活动或不存在,<tt> null </tt>
     * 将在返回数组中的相应元素中设置如果线程已启动且尚未死亡,则线程仍然活动。
     * 
     * <p>
     *  <b> MBeanServer访问</b>：<br> <tt> ThreadInfo </tt>的映射类型是具有在{@link ThreadInfo#from ThreadInfofrom}方法中指定
     * 的属性的<tt> CompositeData </tt>。
     * 
     * 
     * @param ids an array of thread IDs
     * @param maxDepth the maximum number of entries in the stack trace
     * to be dumped. <tt>Integer.MAX_VALUE</tt> could be used to request
     * the entire stack to be dumped.
     *
     * @return an array of the {@link ThreadInfo} objects, each containing
     * information about a thread whose ID is in the corresponding
     * element of the input array of IDs with no locked monitor and
     * synchronizer info.
     *
     * @throws IllegalArgumentException if <tt>maxDepth is negative</tt>.
     * @throws IllegalArgumentException if any element in the input array
     *      <tt>ids</tt> is {@code <= 0}.
     * @throws java.lang.SecurityException if a security manager
     *         exists and the caller does not have
     *         ManagementPermission("monitor").
     *
     */
    public ThreadInfo[] getThreadInfo(long[] ids, int maxDepth);

    /**
     * Tests if the Java virtual machine supports thread contention monitoring.
     *
     * <p>
     *  测试Java虚拟机是否支持线程争用监视
     * 
     * 
     * @return
     *   <tt>true</tt>
     *     if the Java virtual machine supports thread contention monitoring;
     *   <tt>false</tt> otherwise.
     */
    public boolean isThreadContentionMonitoringSupported();

    /**
     * Tests if thread contention monitoring is enabled.
     *
     * <p>
     *  测试是否启用线程争用监视
     * 
     * 
     * @return <tt>true</tt> if thread contention monitoring is enabled;
     *         <tt>false</tt> otherwise.
     *
     * @throws java.lang.UnsupportedOperationException if the Java virtual
     * machine does not support thread contention monitoring.
     *
     * @see #isThreadContentionMonitoringSupported
     */
    public boolean isThreadContentionMonitoringEnabled();

    /**
     * Enables or disables thread contention monitoring.
     * Thread contention monitoring is disabled by default.
     *
     * <p>
     *  启用或禁用线程争用监视默认情况下禁用线程争用监视
     * 
     * 
     * @param enable <tt>true</tt> to enable;
     *               <tt>false</tt> to disable.
     *
     * @throws java.lang.UnsupportedOperationException if the Java
     * virtual machine does not support thread contention monitoring.
     *
     * @throws java.lang.SecurityException if a security manager
     *         exists and the caller does not have
     *         ManagementPermission("control").
     *
     * @see #isThreadContentionMonitoringSupported
     */
    public void setThreadContentionMonitoringEnabled(boolean enable);

    /**
     * Returns the total CPU time for the current thread in nanoseconds.
     * The returned value is of nanoseconds precision but
     * not necessarily nanoseconds accuracy.
     * If the implementation distinguishes between user mode time and system
     * mode time, the returned CPU time is the amount of time that
     * the current thread has executed in user mode or system mode.
     *
     * <p>
     * This is a convenient method for local management use and is
     * equivalent to calling:
     * <blockquote><pre>
     *   {@link #getThreadCpuTime getThreadCpuTime}(Thread.currentThread().getId());
     * </pre></blockquote>
     *
     * <p>
     * 以纳秒为单位返回当前线程的总CPU时间。返回值的精度为纳秒,但不一定为纳秒精度如果实现区分用户模式时间和系统模式时间,则返回的CPU时间是当前线程具有的时间量在用户模式或系统模式下执行
     * 
     * <p>
     *  这是一个方便的本地管理使用方法,相当于调用：<blockquote> <pre> {@link #getThreadCpuTime getThreadCpuTime}(ThreadcurrentThr
     * ead()getId()); </pre> </blockquote>。
     * 
     * 
     * @return the total CPU time for the current thread if CPU time
     * measurement is enabled; <tt>-1</tt> otherwise.
     *
     * @throws java.lang.UnsupportedOperationException if the Java
     * virtual machine does not support CPU time measurement for
     * the current thread.
     *
     * @see #getCurrentThreadUserTime
     * @see #isCurrentThreadCpuTimeSupported
     * @see #isThreadCpuTimeEnabled
     * @see #setThreadCpuTimeEnabled
     */
    public long getCurrentThreadCpuTime();

    /**
     * Returns the CPU time that the current thread has executed
     * in user mode in nanoseconds.
     * The returned value is of nanoseconds precision but
     * not necessarily nanoseconds accuracy.
     *
     * <p>
     * This is a convenient method for local management use and is
     * equivalent to calling:
     * <blockquote><pre>
     *   {@link #getThreadUserTime getThreadUserTime}(Thread.currentThread().getId());
     * </pre></blockquote>
     *
     * <p>
     *  返回当前线程在用户模式下以纳秒为单位执行的CPU时间返回值的精度为纳秒,但不一定为纳秒精度
     * 
     * <p>
     * 这是一个方便的本地管理使用的方法,相当于调用：<blockquote> <pre> {@link #getThreadUserTime getThreadUserTime}(ThreadcurrentT
     * hread()getId()); </pre> </blockquote>。
     * 
     * 
     * @return the user-level CPU time for the current thread if CPU time
     * measurement is enabled; <tt>-1</tt> otherwise.
     *
     * @throws java.lang.UnsupportedOperationException if the Java
     * virtual machine does not support CPU time measurement for
     * the current thread.
     *
     * @see #getCurrentThreadCpuTime
     * @see #isCurrentThreadCpuTimeSupported
     * @see #isThreadCpuTimeEnabled
     * @see #setThreadCpuTimeEnabled
     */
    public long getCurrentThreadUserTime();

    /**
     * Returns the total CPU time for a thread of the specified ID in nanoseconds.
     * The returned value is of nanoseconds precision but
     * not necessarily nanoseconds accuracy.
     * If the implementation distinguishes between user mode time and system
     * mode time, the returned CPU time is the amount of time that
     * the thread has executed in user mode or system mode.
     *
     * <p>
     * If the thread of the specified ID is not alive or does not exist,
     * this method returns <tt>-1</tt>. If CPU time measurement
     * is disabled, this method returns <tt>-1</tt>.
     * A thread is alive if it has been started and has not yet died.
     * <p>
     * If CPU time measurement is enabled after the thread has started,
     * the Java virtual machine implementation may choose any time up to
     * and including the time that the capability is enabled as the point
     * where CPU time measurement starts.
     *
     * <p>
     *  以纳秒为单位返回指定ID的线程的总CPU时间。返回值的精度为纳秒,但不一定为纳秒精度如果实现区分用户模式时间和系统模式时间,则返回的CPU时间是线程已在用户模式或系统模式下执行
     * 
     * <p>
     *  如果指定ID的线程不存在或不存在,此方法返回<tt> -1 </tt>如果禁用CPU时间测量,此方法返回<tt> -1 </tt>线程存活如果它已经开始并且还没有死亡
     * <p>
     * 如果在线程启动后启用CPU时间测量,Java虚拟机实现可以选择任何时间,直到并包括启用能力的时间作为CPU时间测量开始的点
     * 
     * 
     * @param id the thread ID of a thread
     * @return the total CPU time for a thread of the specified ID
     * if the thread of the specified ID exists, the thread is alive,
     * and CPU time measurement is enabled;
     * <tt>-1</tt> otherwise.
     *
     * @throws IllegalArgumentException if {@code id <= 0}.
     * @throws java.lang.UnsupportedOperationException if the Java
     * virtual machine does not support CPU time measurement for
     * other threads.
     *
     * @see #getThreadUserTime
     * @see #isThreadCpuTimeSupported
     * @see #isThreadCpuTimeEnabled
     * @see #setThreadCpuTimeEnabled
     */
    public long getThreadCpuTime(long id);

    /**
     * Returns the CPU time that a thread of the specified ID
     * has executed in user mode in nanoseconds.
     * The returned value is of nanoseconds precision but
     * not necessarily nanoseconds accuracy.
     *
     * <p>
     * If the thread of the specified ID is not alive or does not exist,
     * this method returns <tt>-1</tt>. If CPU time measurement
     * is disabled, this method returns <tt>-1</tt>.
     * A thread is alive if it has been started and has not yet died.
     * <p>
     * If CPU time measurement is enabled after the thread has started,
     * the Java virtual machine implementation may choose any time up to
     * and including the time that the capability is enabled as the point
     * where CPU time measurement starts.
     *
     * <p>
     *  返回指定ID的线程在用户模式下以纳秒为单位执行的CPU时间。返回的值为纳秒精度,但不一定为纳秒精度
     * 
     * <p>
     *  如果指定ID的线程不存在或不存在,此方法返回<tt> -1 </tt>如果禁用CPU时间测量,此方法返回<tt> -1 </tt>线程存活如果它已经开始并且还没有死亡
     * <p>
     * 如果在线程启动后启用CPU时间测量,Java虚拟机实现可以选择任何时间,直到并包括启用能力的时间作为CPU时间测量开始的点
     * 
     * 
     * @param id the thread ID of a thread
     * @return the user-level CPU time for a thread of the specified ID
     * if the thread of the specified ID exists, the thread is alive,
     * and CPU time measurement is enabled;
     * <tt>-1</tt> otherwise.
     *
     * @throws IllegalArgumentException if {@code id <= 0}.
     * @throws java.lang.UnsupportedOperationException if the Java
     * virtual machine does not support CPU time measurement for
     * other threads.
     *
     * @see #getThreadCpuTime
     * @see #isThreadCpuTimeSupported
     * @see #isThreadCpuTimeEnabled
     * @see #setThreadCpuTimeEnabled
     */
    public long getThreadUserTime(long id);

    /**
     * Tests if the Java virtual machine implementation supports CPU time
     * measurement for any thread.
     * A Java virtual machine implementation that supports CPU time
     * measurement for any thread will also support CPU time
     * measurement for the current thread.
     *
     * <p>
     *  测试Java虚拟机实现是否支持任何线程的CPU时间测量支持任何线程的CPU时间测量的Java虚拟机实现也将支持当前线程的CPU时间测量
     * 
     * 
     * @return
     *   <tt>true</tt>
     *     if the Java virtual machine supports CPU time
     *     measurement for any thread;
     *   <tt>false</tt> otherwise.
     */
    public boolean isThreadCpuTimeSupported();

    /**
     * Tests if the Java virtual machine supports CPU time
     * measurement for the current thread.
     * This method returns <tt>true</tt> if {@link #isThreadCpuTimeSupported}
     * returns <tt>true</tt>.
     *
     * <p>
     *  测试Java虚拟机是否支持当前线程的CPU时间测量如果{@link #isThreadCpuTimeSupported}返回<tt> true </tt>,此方法返回<tt> true </tt>
     * 
     * 
     * @return
     *   <tt>true</tt>
     *     if the Java virtual machine supports CPU time
     *     measurement for current thread;
     *   <tt>false</tt> otherwise.
     */
    public boolean isCurrentThreadCpuTimeSupported();

    /**
     * Tests if thread CPU time measurement is enabled.
     *
     * <p>
     *  测试是否启用线程CPU时间测量
     * 
     * 
     * @return <tt>true</tt> if thread CPU time measurement is enabled;
     *         <tt>false</tt> otherwise.
     *
     * @throws java.lang.UnsupportedOperationException if the Java virtual
     * machine does not support CPU time measurement for other threads
     * nor for the current thread.
     *
     * @see #isThreadCpuTimeSupported
     * @see #isCurrentThreadCpuTimeSupported
     */
    public boolean isThreadCpuTimeEnabled();

    /**
     * Enables or disables thread CPU time measurement.  The default
     * is platform dependent.
     *
     * <p>
     *  启用或禁用线程CPU时间测量默认值是平台相关的
     * 
     * 
     * @param enable <tt>true</tt> to enable;
     *               <tt>false</tt> to disable.
     *
     * @throws java.lang.UnsupportedOperationException if the Java
     * virtual machine does not support CPU time measurement for
     * any threads nor for the current thread.
     *
     * @throws java.lang.SecurityException if a security manager
     *         exists and the caller does not have
     *         ManagementPermission("control").
     *
     * @see #isThreadCpuTimeSupported
     * @see #isCurrentThreadCpuTimeSupported
     */
    public void setThreadCpuTimeEnabled(boolean enable);

    /**
     * Finds cycles of threads that are in deadlock waiting to acquire
     * object monitors. That is, threads that are blocked waiting to enter a
     * synchronization block or waiting to reenter a synchronization block
     * after an {@link Object#wait Object.wait} call,
     * where each thread owns one monitor while
     * trying to obtain another monitor already held by another thread
     * in a cycle.
     * <p>
     * More formally, a thread is <em>monitor deadlocked</em> if it is
     * part of a cycle in the relation "is waiting for an object monitor
     * owned by".  In the simplest case, thread A is blocked waiting
     * for a monitor owned by thread B, and thread B is blocked waiting
     * for a monitor owned by thread A.
     * <p>
     * This method is designed for troubleshooting use, but not for
     * synchronization control.  It might be an expensive operation.
     * <p>
     * This method finds deadlocks involving only object monitors.
     * To find deadlocks involving both object monitors and
     * <a href="LockInfo.html#OwnableSynchronizer">ownable synchronizers</a>,
     * the {@link #findDeadlockedThreads findDeadlockedThreads} method
     * should be used.
     *
     * <p>
     * 查找处于死锁状态的线程等待获取对象监视器的循环。
     * 即,在{@link Object#wait Objectwait}调用之后阻塞等待进入同步块或等待重新启动同步块的线程,其中每个线程都拥有一个在一个周期中尝试获取另一个线程已经持有的另一个监视器。
     * <p>
     *  更正式地,线程是<em>监视器死锁</em>,如果它是关系"正在等待对象监视器拥有"的循环的一部分。在最简单的情况下,线程A被阻塞等待监视器线程B,并且线程B被阻塞等待线程A拥有的监视器
     * <p>
     *  此方法设计用于故障排除使用,但不是用于同步控制它可能是一个昂贵的操作
     * <p>
     * 此方法发现仅涉及对象监视器的死锁要查找涉及对象监视器和<a href=\"LockInfohtml#OwnableSynchronizer\">可拥有同步器</a>的死锁,应使用{@link #findDeadlockedThreads findDeadlockedThreads}
     * 方法。
     * 
     * 
     * @return an array of IDs of the threads that are monitor
     * deadlocked, if any; <tt>null</tt> otherwise.
     *
     * @throws java.lang.SecurityException if a security manager
     *         exists and the caller does not have
     *         ManagementPermission("monitor").
     *
     * @see #findDeadlockedThreads
     */
    public long[] findMonitorDeadlockedThreads();

    /**
     * Resets the peak thread count to the current number of
     * live threads.
     *
     * <p>
     *  将峰值线程计数重置为当前活动线程数
     * 
     * 
     * @throws java.lang.SecurityException if a security manager
     *         exists and the caller does not have
     *         ManagementPermission("control").
     *
     * @see #getPeakThreadCount
     * @see #getThreadCount
     */
    public void resetPeakThreadCount();

    /**
     * Finds cycles of threads that are in deadlock waiting to acquire
     * object monitors or
     * <a href="LockInfo.html#OwnableSynchronizer">ownable synchronizers</a>.
     *
     * Threads are <em>deadlocked</em> in a cycle waiting for a lock of
     * these two types if each thread owns one lock while
     * trying to acquire another lock already held
     * by another thread in the cycle.
     * <p>
     * This method is designed for troubleshooting use, but not for
     * synchronization control.  It might be an expensive operation.
     *
     * <p>
     *  查找处于死锁状态的线程周期,等待获取对象监视器或<a href=\"LockInfohtml#OwnableSynchronizer\">可拥有的同步器</a>
     * 
     *  线程在等待这两种类型的锁的循环中被死锁</em>,如果每个线程在尝试获取另一个已经由循环中的另一个线程持有的锁时拥有一个锁
     * <p>
     * 此方法设计用于故障排除使用,但不是用于同步控制它可能是一个昂贵的操作
     * 
     * 
     * @return an array of IDs of the threads that are
     * deadlocked waiting for object monitors or ownable synchronizers, if any;
     * <tt>null</tt> otherwise.
     *
     * @throws java.lang.SecurityException if a security manager
     *         exists and the caller does not have
     *         ManagementPermission("monitor").
     * @throws java.lang.UnsupportedOperationException if the Java virtual
     * machine does not support monitoring of ownable synchronizer usage.
     *
     * @see #isSynchronizerUsageSupported
     * @see #findMonitorDeadlockedThreads
     * @since 1.6
     */
    public long[] findDeadlockedThreads();

    /**
     * Tests if the Java virtual machine supports monitoring of
     * object monitor usage.
     *
     * <p>
     *  测试Java虚拟机是否支持监视对象监视器使用情况
     * 
     * 
     * @return
     *   <tt>true</tt>
     *     if the Java virtual machine supports monitoring of
     *     object monitor usage;
     *   <tt>false</tt> otherwise.
     *
     * @see #dumpAllThreads
     * @since 1.6
     */
    public boolean isObjectMonitorUsageSupported();

    /**
     * Tests if the Java virtual machine supports monitoring of
     * <a href="LockInfo.html#OwnableSynchronizer">
     * ownable synchronizer</a> usage.
     *
     * <p>
     *  测试Java虚拟机是否支持监视
     * <a href="LockInfo.html#OwnableSynchronizer">
     *  拥有同步器</a>使用
     * 
     * 
     * @return
     *   <tt>true</tt>
     *     if the Java virtual machine supports monitoring of ownable
     *     synchronizer usage;
     *   <tt>false</tt> otherwise.
     *
     * @see #dumpAllThreads
     * @since 1.6
     */
    public boolean isSynchronizerUsageSupported();

    /**
     * Returns the thread info for each thread
     * whose ID is in the input array <tt>ids</tt>, with stack trace
     * and synchronization information.
     *
     * <p>
     * This method obtains a snapshot of the thread information
     * for each thread including:
     * <ul>
     *    <li>the entire stack trace,</li>
     *    <li>the object monitors currently locked by the thread
     *        if <tt>lockedMonitors</tt> is <tt>true</tt>, and</li>
     *    <li>the <a href="LockInfo.html#OwnableSynchronizer">
     *        ownable synchronizers</a> currently locked by the thread
     *        if <tt>lockedSynchronizers</tt> is <tt>true</tt>.</li>
     * </ul>
     * <p>
     * This method returns an array of the <tt>ThreadInfo</tt> objects,
     * each is the thread information about the thread with the same index
     * as in the <tt>ids</tt> array.
     * If a thread of the given ID is not alive or does not exist,
     * <tt>null</tt> will be set in the corresponding element
     * in the returned array.  A thread is alive if
     * it has been started and has not yet died.
     * <p>
     * If a thread does not lock any object monitor or <tt>lockedMonitors</tt>
     * is <tt>false</tt>, the returned <tt>ThreadInfo</tt> object will have an
     * empty <tt>MonitorInfo</tt> array.  Similarly, if a thread does not
     * lock any synchronizer or <tt>lockedSynchronizers</tt> is <tt>false</tt>,
     * the returned <tt>ThreadInfo</tt> object
     * will have an empty <tt>LockInfo</tt> array.
     *
     * <p>
     * When both <tt>lockedMonitors</tt> and <tt>lockedSynchronizers</tt>
     * parameters are <tt>false</tt>, it is equivalent to calling:
     * <blockquote><pre>
     *     {@link #getThreadInfo(long[], int)  getThreadInfo(ids, Integer.MAX_VALUE)}
     * </pre></blockquote>
     *
     * <p>
     * This method is designed for troubleshooting use, but not for
     * synchronization control.  It might be an expensive operation.
     *
     * <p>
     * <b>MBeanServer access</b>:<br>
     * The mapped type of <tt>ThreadInfo</tt> is
     * <tt>CompositeData</tt> with attributes as specified in the
     * {@link ThreadInfo#from ThreadInfo.from} method.
     *
     * <p>
     *  返回ID在输入数组<tt> ids </tt>中的每个线程的线程信息,具有堆栈跟踪和同步信息
     * 
     * <p>
     *  该方法获得每个线程的线程信息的快照,包括：
     * <ul>
     * <li>整个堆栈跟踪</li> <li>如果<tt> lockedMonitors </tt>为<tt> true </tt>和</li> <li>目前由线程锁定的<a href=\"LockInfohtml#OwnableSynchronizer\">
     * 可拥有同步器</a>(如果<tt> lockedSynchronizers </tt>为<tt> true </tt> </li>。
     * </ul>
     * <p>
     *  此方法返回<tt> ThreadInfo </tt>对象的数组,每个对象都是与<tt> ids </tt>数组中索引相同的线程的线程信息如果给定ID的线程不是活动或不存在,<tt> null </tt>
     * 将在返回数组中的相应元素中设置如果线程已启动且尚未死亡,则线程仍然活动。
     * <p>
     * 如果线程不锁定任何对象监视器或<tt> lockedMonitors </tt>为<tt> false </tt>,则返回的<tt> ThreadInfo </tt>对象将有一个空的<tt> Monit
     * orInfo </tt > array类似地,如果线程不锁定任何同步器或<tt> lockedSynchronizers </tt>为<tt> false </tt>,则返回的<tt> ThreadIn
     * fo </tt>对象将有一个空的<tt> LockInfo </tt>数组。
     * 
     * <p>
     * 
     * @param  ids an array of thread IDs.
     * @param  lockedMonitors if <tt>true</tt>, retrieves all locked monitors.
     * @param  lockedSynchronizers if <tt>true</tt>, retrieves all locked
     *             ownable synchronizers.
     *
     * @return an array of the {@link ThreadInfo} objects, each containing
     * information about a thread whose ID is in the corresponding
     * element of the input array of IDs.
     *
     * @throws java.lang.SecurityException if a security manager
     *         exists and the caller does not have
     *         ManagementPermission("monitor").
     * @throws java.lang.UnsupportedOperationException
     *         <ul>
     *           <li>if <tt>lockedMonitors</tt> is <tt>true</tt> but
     *               the Java virtual machine does not support monitoring
     *               of {@linkplain #isObjectMonitorUsageSupported
     *               object monitor usage}; or</li>
     *           <li>if <tt>lockedSynchronizers</tt> is <tt>true</tt> but
     *               the Java virtual machine does not support monitoring
     *               of {@linkplain #isSynchronizerUsageSupported
     *               ownable synchronizer usage}.</li>
     *         </ul>
     *
     * @see #isObjectMonitorUsageSupported
     * @see #isSynchronizerUsageSupported
     *
     * @since 1.6
     */
    public ThreadInfo[] getThreadInfo(long[] ids, boolean lockedMonitors, boolean lockedSynchronizers);

    /**
     * Returns the thread info for all live threads with stack trace
     * and synchronization information.
     * Some threads included in the returned array
     * may have been terminated when this method returns.
     *
     * <p>
     * This method returns an array of {@link ThreadInfo} objects
     * as specified in the {@link #getThreadInfo(long[], boolean, boolean)}
     * method.
     *
     * <p>
     *  当<tt> lockedMonitors </tt>和<tt> lockedSynchronizers </tt>参数都为<tt> false </tt>时,它等效于调用：<blockquote> <pre>
     *  {@link #getThreadInfo ],int)getThreadInfo(ids,IntegerMAX_VALUE)} </pre> </blockquote>。
     * 
     * <p>
     *  此方法设计用于故障排除使用,但不是用于同步控制它可能是一个昂贵的操作
     * 
     * <p>
     * <b> MBeanServer访问</b>：<br> <tt> ThreadInfo </tt>的映射类型是具有在{@link ThreadInfo#from ThreadInfofrom}方法中指定的
     * 
     * @param  lockedMonitors if <tt>true</tt>, dump all locked monitors.
     * @param  lockedSynchronizers if <tt>true</tt>, dump all locked
     *             ownable synchronizers.
     *
     * @return an array of {@link ThreadInfo} for all live threads.
     *
     * @throws java.lang.SecurityException if a security manager
     *         exists and the caller does not have
     *         ManagementPermission("monitor").
     * @throws java.lang.UnsupportedOperationException
     *         <ul>
     *           <li>if <tt>lockedMonitors</tt> is <tt>true</tt> but
     *               the Java virtual machine does not support monitoring
     *               of {@linkplain #isObjectMonitorUsageSupported
     *               object monitor usage}; or</li>
     *           <li>if <tt>lockedSynchronizers</tt> is <tt>true</tt> but
     *               the Java virtual machine does not support monitoring
     *               of {@linkplain #isSynchronizerUsageSupported
     *               ownable synchronizer usage}.</li>
     *         </ul>
     *
     * @see #isObjectMonitorUsageSupported
     * @see #isSynchronizerUsageSupported
     *
     * @since 1.6
     */
    public ThreadInfo[] dumpAllThreads(boolean lockedMonitors, boolean lockedSynchronizers);
}
