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

import javax.management.openmbean.CompositeData;
import sun.management.ManagementFactoryHelper;
import sun.management.ThreadInfoCompositeData;
import static java.lang.Thread.State.*;

/**
 * Thread information. <tt>ThreadInfo</tt> contains the information
 * about a thread including:
 * <h3>General thread information</h3>
 * <ul>
 *   <li>Thread ID.</li>
 *   <li>Name of the thread.</li>
 * </ul>
 *
 * <h3>Execution information</h3>
 * <ul>
 *   <li>Thread state.</li>
 *   <li>The object upon which the thread is blocked due to:
 *       <ul>
 *       <li>waiting to enter a synchronization block/method, or</li>
 *       <li>waiting to be notified in a {@link Object#wait Object.wait} method,
 *           or</li>
 *       <li>parking due to a {@link java.util.concurrent.locks.LockSupport#park
 *           LockSupport.park} call.</li>
 *       </ul>
 *   </li>
 *   <li>The ID of the thread that owns the object
 *       that the thread is blocked.</li>
 *   <li>Stack trace of the thread.</li>
 *   <li>List of object monitors locked by the thread.</li>
 *   <li>List of <a href="LockInfo.html#OwnableSynchronizer">
 *       ownable synchronizers</a> locked by the thread.</li>
 * </ul>
 *
 * <h4><a name="SyncStats">Synchronization Statistics</a></h4>
 * <ul>
 *   <li>The number of times that the thread has blocked for
 *       synchronization or waited for notification.</li>
 *   <li>The accumulated elapsed time that the thread has blocked
 *       for synchronization or waited for notification
 *       since {@link ThreadMXBean#setThreadContentionMonitoringEnabled
 *       thread contention monitoring}
 *       was enabled. Some Java virtual machine implementation
 *       may not support this.  The
 *       {@link ThreadMXBean#isThreadContentionMonitoringSupported()}
 *       method can be used to determine if a Java virtual machine
 *       supports this.</li>
 * </ul>
 *
 * <p>This thread information class is designed for use in monitoring of
 * the system, not for synchronization control.
 *
 * <h4>MXBean Mapping</h4>
 * <tt>ThreadInfo</tt> is mapped to a {@link CompositeData CompositeData}
 * with attributes as specified in
 * the {@link #from from} method.
 *
 * <p>
 *  线程信息<tt> ThreadInfo </tt>包含有关线程的信息,包括：<h3>常规线程信息</h3>
 * <ul>
 *  <li>主题ID </li> <li>主题名称</li>
 * </ul>
 * 
 *  <h3>执行信息</h3>
 * <ul>
 *  <li>线程状态</li> <li>由于以下原因,线程被阻止的对象：
 * <ul>
 *  <li>等待输入同步块/方法,或</li> <li>等待在{@link Object#wait Objectwait}方法中通知您,或</li> <li>由于{链接javautilconcurrentlocksLockSupport#park LockSupportpark}
 * 调用</li>。
 * </ul>
 * </li>
 * <li>拥有线程被阻止的对象的线程ID </li> <li>线程的堆栈跟踪</li> <li>线程锁定的对象监视器列表</li> <li >线程锁定的<a href=\"LockInfohtml#OwnableSynchronizer\">
 * 可拥有的同步器</a>列表</li>。
 * </ul>
 * 
 *  <h4> <a name=\"SyncStats\">同步统计信息</a> </h4>
 * <ul>
 * <li>线程阻止同步或等待通知的次数</li> <li>自从{@link ThreadMXBean#setThreadContentionMonitoringEnabled线程争用监控(线程争用监控)以来,线程阻止同步或等待通知的累计经过时间}
 * 被启用某些Java虚拟机实现可能不支持此{@link ThreadMXBean#isThreadContentionMonitoringSupported()}方法可用于确定Java虚拟机是否支持此</li>
 * 。
 * </ul>
 * 
 *  <p>此线程信息类设计用于监视系统,而不是用于同步控制
 * 
 * <h4> MXBean映射</h4> <tt> ThreadInfo </tt>映射到{@link CompositeData CompositeData},其属性如{@link #from from}
 * 方法中指定。
 * 
 * 
 * @see ThreadMXBean#getThreadInfo
 * @see ThreadMXBean#dumpAllThreads
 *
 * @author  Mandy Chung
 * @since   1.5
 */

public class ThreadInfo {
    private String       threadName;
    private long         threadId;
    private long         blockedTime;
    private long         blockedCount;
    private long         waitedTime;
    private long         waitedCount;
    private LockInfo     lock;
    private String       lockName;
    private long         lockOwnerId;
    private String       lockOwnerName;
    private boolean      inNative;
    private boolean      suspended;
    private Thread.State threadState;
    private StackTraceElement[] stackTrace;
    private MonitorInfo[]       lockedMonitors;
    private LockInfo[]          lockedSynchronizers;

    private static MonitorInfo[] EMPTY_MONITORS = new MonitorInfo[0];
    private static LockInfo[] EMPTY_SYNCS = new LockInfo[0];

    /**
     * Constructor of ThreadInfo created by the JVM
     *
     * <p>
     *  由JVM创建的ThreadInfo的构造方法
     * 
     * 
     * @param t             Thread
     * @param state         Thread state
     * @param lockObj       Object on which the thread is blocked
     * @param lockOwner     the thread holding the lock
     * @param blockedCount  Number of times blocked to enter a lock
     * @param blockedTime   Approx time blocked to enter a lock
     * @param waitedCount   Number of times waited on a lock
     * @param waitedTime    Approx time waited on a lock
     * @param stackTrace    Thread stack trace
     */
    private ThreadInfo(Thread t, int state, Object lockObj, Thread lockOwner,
                       long blockedCount, long blockedTime,
                       long waitedCount, long waitedTime,
                       StackTraceElement[] stackTrace) {
        initialize(t, state, lockObj, lockOwner,
                   blockedCount, blockedTime,
                   waitedCount, waitedTime, stackTrace,
                   EMPTY_MONITORS, EMPTY_SYNCS);
    }

    /**
     * Constructor of ThreadInfo created by the JVM
     * for {@link ThreadMXBean#getThreadInfo(long[],boolean,boolean)}
     * and {@link ThreadMXBean#dumpAllThreads}
     *
     * <p>
     *  由JVM为{@link ThreadMXBean#getThreadInfo(long [],boolean,boolean)}和{@link ThreadMXBean#dumpAllThreads}
     * 创建的ThreadInfo的构造方法。
     * 
     * 
     * @param t             Thread
     * @param state         Thread state
     * @param lockObj       Object on which the thread is blocked
     * @param lockOwner     the thread holding the lock
     * @param blockedCount  Number of times blocked to enter a lock
     * @param blockedTime   Approx time blocked to enter a lock
     * @param waitedCount   Number of times waited on a lock
     * @param waitedTime    Approx time waited on a lock
     * @param stackTrace    Thread stack trace
     * @param monitors      List of locked monitors
     * @param stackDepths   List of stack depths
     * @param synchronizers List of locked synchronizers
     */
    private ThreadInfo(Thread t, int state, Object lockObj, Thread lockOwner,
                       long blockedCount, long blockedTime,
                       long waitedCount, long waitedTime,
                       StackTraceElement[] stackTrace,
                       Object[] monitors,
                       int[] stackDepths,
                       Object[] synchronizers) {
        int numMonitors = (monitors == null ? 0 : monitors.length);
        MonitorInfo[] lockedMonitors;
        if (numMonitors == 0) {
            lockedMonitors = EMPTY_MONITORS;
        } else {
            lockedMonitors = new MonitorInfo[numMonitors];
            for (int i = 0; i < numMonitors; i++) {
                Object lock = monitors[i];
                String className = lock.getClass().getName();
                int identityHashCode = System.identityHashCode(lock);
                int depth = stackDepths[i];
                StackTraceElement ste = (depth >= 0 ? stackTrace[depth]
                                                    : null);
                lockedMonitors[i] = new MonitorInfo(className,
                                                    identityHashCode,
                                                    depth,
                                                    ste);
            }
        }

        int numSyncs = (synchronizers == null ? 0 : synchronizers.length);
        LockInfo[] lockedSynchronizers;
        if (numSyncs == 0) {
            lockedSynchronizers = EMPTY_SYNCS;
        } else {
            lockedSynchronizers = new LockInfo[numSyncs];
            for (int i = 0; i < numSyncs; i++) {
                Object lock = synchronizers[i];
                String className = lock.getClass().getName();
                int identityHashCode = System.identityHashCode(lock);
                lockedSynchronizers[i] = new LockInfo(className,
                                                      identityHashCode);
            }
        }

        initialize(t, state, lockObj, lockOwner,
                   blockedCount, blockedTime,
                   waitedCount, waitedTime, stackTrace,
                   lockedMonitors, lockedSynchronizers);
    }

    /**
     * Initialize ThreadInfo object
     *
     * <p>
     *  Initialize ThreadInfo对象
     * 
     * 
     * @param t             Thread
     * @param state         Thread state
     * @param lockObj       Object on which the thread is blocked
     * @param lockOwner     the thread holding the lock
     * @param blockedCount  Number of times blocked to enter a lock
     * @param blockedTime   Approx time blocked to enter a lock
     * @param waitedCount   Number of times waited on a lock
     * @param waitedTime    Approx time waited on a lock
     * @param stackTrace    Thread stack trace
     * @param lockedMonitors List of locked monitors
     * @param lockedSynchronizers List of locked synchronizers
     */
    private void initialize(Thread t, int state, Object lockObj, Thread lockOwner,
                            long blockedCount, long blockedTime,
                            long waitedCount, long waitedTime,
                            StackTraceElement[] stackTrace,
                            MonitorInfo[] lockedMonitors,
                            LockInfo[] lockedSynchronizers) {
        this.threadId = t.getId();
        this.threadName = t.getName();
        this.threadState = ManagementFactoryHelper.toThreadState(state);
        this.suspended = ManagementFactoryHelper.isThreadSuspended(state);
        this.inNative = ManagementFactoryHelper.isThreadRunningNative(state);
        this.blockedCount = blockedCount;
        this.blockedTime = blockedTime;
        this.waitedCount = waitedCount;
        this.waitedTime = waitedTime;

        if (lockObj == null) {
            this.lock = null;
            this.lockName = null;
        } else {
            this.lock = new LockInfo(lockObj);
            this.lockName =
                lock.getClassName() + '@' +
                    Integer.toHexString(lock.getIdentityHashCode());
        }
        if (lockOwner == null) {
            this.lockOwnerId = -1;
            this.lockOwnerName = null;
        } else {
            this.lockOwnerId = lockOwner.getId();
            this.lockOwnerName = lockOwner.getName();
        }
        if (stackTrace == null) {
            this.stackTrace = NO_STACK_TRACE;
        } else {
            this.stackTrace = stackTrace;
        }
        this.lockedMonitors = lockedMonitors;
        this.lockedSynchronizers = lockedSynchronizers;
    }

    /*
     * Constructs a <tt>ThreadInfo</tt> object from a
     * {@link CompositeData CompositeData}.
     * <p>
     *  从{@link CompositeData CompositeData}构造一个<tt> ThreadInfo </tt>对象,
     * 
     */
    private ThreadInfo(CompositeData cd) {
        ThreadInfoCompositeData ticd = ThreadInfoCompositeData.getInstance(cd);

        threadId = ticd.threadId();
        threadName = ticd.threadName();
        blockedTime = ticd.blockedTime();
        blockedCount = ticd.blockedCount();
        waitedTime = ticd.waitedTime();
        waitedCount = ticd.waitedCount();
        lockName = ticd.lockName();
        lockOwnerId = ticd.lockOwnerId();
        lockOwnerName = ticd.lockOwnerName();
        threadState = ticd.threadState();
        suspended = ticd.suspended();
        inNative = ticd.inNative();
        stackTrace = ticd.stackTrace();

        // 6.0 attributes
        if (ticd.isCurrentVersion()) {
            lock = ticd.lockInfo();
            lockedMonitors = ticd.lockedMonitors();
            lockedSynchronizers = ticd.lockedSynchronizers();
        } else {
            // lockInfo is a new attribute added in 1.6 ThreadInfo
            // If cd is a 5.0 version, construct the LockInfo object
            //  from the lockName value.
            if (lockName != null) {
                String result[] = lockName.split("@");
                if (result.length == 2) {
                    int identityHashCode = Integer.parseInt(result[1], 16);
                    lock = new LockInfo(result[0], identityHashCode);
                } else {
                    assert result.length == 2;
                    lock = null;
                }
            } else {
                lock = null;
            }
            lockedMonitors = EMPTY_MONITORS;
            lockedSynchronizers = EMPTY_SYNCS;
        }
    }

    /**
     * Returns the ID of the thread associated with this <tt>ThreadInfo</tt>.
     *
     * <p>
     *  返回与此<tt> ThreadInfo </tt>相关联的线程的ID
     * 
     * 
     * @return the ID of the associated thread.
     */
    public long getThreadId() {
        return threadId;
    }

    /**
     * Returns the name of the thread associated with this <tt>ThreadInfo</tt>.
     *
     * <p>
     *  返回与此<tt> ThreadInfo </tt>相关联的线程的名称
     * 
     * 
     * @return the name of the associated thread.
     */
    public String getThreadName() {
        return threadName;
    }

    /**
     * Returns the state of the thread associated with this <tt>ThreadInfo</tt>.
     *
     * <p>
     *  返回与此<tt> ThreadInfo </tt>相关联的线程的状态
     * 
     * 
     * @return <tt>Thread.State</tt> of the associated thread.
     */
    public Thread.State getThreadState() {
         return threadState;
    }

    /**
     * Returns the approximate accumulated elapsed time (in milliseconds)
     * that the thread associated with this <tt>ThreadInfo</tt>
     * has blocked to enter or reenter a monitor
     * since thread contention monitoring is enabled.
     * I.e. the total accumulated time the thread has been in the
     * {@link java.lang.Thread.State#BLOCKED BLOCKED} state since thread
     * contention monitoring was last enabled.
     * This method returns <tt>-1</tt> if thread contention monitoring
     * is disabled.
     *
     * <p>The Java virtual machine may measure the time with a high
     * resolution timer.  This statistic is reset when
     * the thread contention monitoring is reenabled.
     *
     * <p>
     * 返回与此<tt> ThreadInfo </tt>关联的线程阻塞进入或重新输入监视器的近似累计经过时间(以毫秒为单位),因为线程争用监视已启用Ie线程已进入{ @link javalangThreadState#BLOCKED BLOCKED}
     * 状态,因为上次启用线程争用监视此方法返回<tt> -1 </tt>如果禁用线程争用监视。
     * 
     *  <p> Java虚拟机可以使用高分辨率定时器测量时间当线程争用监视重新启用时,此统计信息会重置
     * 
     * 
     * @return the approximate accumulated elapsed time in milliseconds
     * that a thread entered the <tt>BLOCKED</tt> state;
     * <tt>-1</tt> if thread contention monitoring is disabled.
     *
     * @throws java.lang.UnsupportedOperationException if the Java
     * virtual machine does not support this operation.
     *
     * @see ThreadMXBean#isThreadContentionMonitoringSupported
     * @see ThreadMXBean#setThreadContentionMonitoringEnabled
     */
    public long getBlockedTime() {
        return blockedTime;
    }

    /**
     * Returns the total number of times that
     * the thread associated with this <tt>ThreadInfo</tt>
     * blocked to enter or reenter a monitor.
     * I.e. the number of times a thread has been in the
     * {@link java.lang.Thread.State#BLOCKED BLOCKED} state.
     *
     * <p>
     * 返回与此<tt> ThreadInfo </tt>关联的线程阻止进入或重新输入监视器的总次数Ie线程已处于{@link javalangThreadState#BLOCKED BLOCKED}状态的次数
     * 。
     * 
     * 
     * @return the total number of times that the thread
     * entered the <tt>BLOCKED</tt> state.
     */
    public long getBlockedCount() {
        return blockedCount;
    }

    /**
     * Returns the approximate accumulated elapsed time (in milliseconds)
     * that the thread associated with this <tt>ThreadInfo</tt>
     * has waited for notification
     * since thread contention monitoring is enabled.
     * I.e. the total accumulated time the thread has been in the
     * {@link java.lang.Thread.State#WAITING WAITING}
     * or {@link java.lang.Thread.State#TIMED_WAITING TIMED_WAITING} state
     * since thread contention monitoring is enabled.
     * This method returns <tt>-1</tt> if thread contention monitoring
     * is disabled.
     *
     * <p>The Java virtual machine may measure the time with a high
     * resolution timer.  This statistic is reset when
     * the thread contention monitoring is reenabled.
     *
     * <p>
     *  返回与此<tt> ThreadInfo </tt>相关联的线程在启用线程争用监视后等待通知的大致累积时间(以毫秒为单位)Ie线程已在{@link javalangThreadState# WAITING WAITING}
     * 或{@link javalangThreadState#TIMED_WAITING TIMED_WAITING}状态,因为线程争用监视已启用如果线程争用监视已禁用,此方法返回<tt> -1 </tt>。
     * 
     * <p> Java虚拟机可以使用高分辨率定时器测量时间当线程争用监视重新启用时,此统计信息会重置
     * 
     * 
     * @return the approximate accumulated elapsed time in milliseconds
     * that a thread has been in the <tt>WAITING</tt> or
     * <tt>TIMED_WAITING</tt> state;
     * <tt>-1</tt> if thread contention monitoring is disabled.
     *
     * @throws java.lang.UnsupportedOperationException if the Java
     * virtual machine does not support this operation.
     *
     * @see ThreadMXBean#isThreadContentionMonitoringSupported
     * @see ThreadMXBean#setThreadContentionMonitoringEnabled
     */
    public long getWaitedTime() {
        return waitedTime;
    }

    /**
     * Returns the total number of times that
     * the thread associated with this <tt>ThreadInfo</tt>
     * waited for notification.
     * I.e. the number of times that a thread has been
     * in the {@link java.lang.Thread.State#WAITING WAITING}
     * or {@link java.lang.Thread.State#TIMED_WAITING TIMED_WAITING} state.
     *
     * <p>
     *  返回与此<tt> ThreadInfo </tt>等待通知相关联的线程的总次数,即线程已进入{@link javalangThreadState#WAITING WAITING}或{@link javalangThreadState#TIMED_WAITING TIMED_WAITING}
     * 状态。
     * 
     * 
     * @return the total number of times that the thread
     * was in the <tt>WAITING</tt> or <tt>TIMED_WAITING</tt> state.
     */
    public long getWaitedCount() {
        return waitedCount;
    }

    /**
     * Returns the <tt>LockInfo</tt> of an object for which
     * the thread associated with this <tt>ThreadInfo</tt>
     * is blocked waiting.
     * A thread can be blocked waiting for one of the following:
     * <ul>
     * <li>an object monitor to be acquired for entering or reentering
     *     a synchronization block/method.
     *     <br>The thread is in the {@link java.lang.Thread.State#BLOCKED BLOCKED}
     *     state waiting to enter the <tt>synchronized</tt> statement
     *     or method.
     *     <p></li>
     * <li>an object monitor to be notified by another thread.
     *     <br>The thread is in the {@link java.lang.Thread.State#WAITING WAITING}
     *     or {@link java.lang.Thread.State#TIMED_WAITING TIMED_WAITING} state
     *     due to a call to the {@link Object#wait Object.wait} method.
     *     <p></li>
     * <li>a synchronization object responsible for the thread parking.
     *     <br>The thread is in the {@link java.lang.Thread.State#WAITING WAITING}
     *     or {@link java.lang.Thread.State#TIMED_WAITING TIMED_WAITING} state
     *     due to a call to the
     *     {@link java.util.concurrent.locks.LockSupport#park(Object)
     *     LockSupport.park} method.  The synchronization object
     *     is the object returned from
     *     {@link java.util.concurrent.locks.LockSupport#getBlocker
     *     LockSupport.getBlocker} method. Typically it is an
     *     <a href="LockInfo.html#OwnableSynchronizer"> ownable synchronizer</a>
     *     or a {@link java.util.concurrent.locks.Condition Condition}.</li>
     * </ul>
     *
     * <p>This method returns <tt>null</tt> if the thread is not in any of
     * the above conditions.
     *
     * <p>
     *  返回与此<tt> ThreadInfo </tt>相关联的线程被阻止等待的对象的<tt> LockInfo </tt>可阻止线程等待以下操作之一：
     * <ul>
     * <li>要获取的用于进入或重新进入同步块/方法的对象监视器<br>线程处于{@link javalangThreadState#BLOCKED BLOCKED}状态,等待输入<tt> synchroni
     * zed </tt>语句或方法<p> </li> <li>要由另一个线程通知的对象监视器<br>该线程处于{@link javalangThreadState#WAITING WAITING}或{@link javalangThreadState#TIMED_WAITING TIMED_WAITING}
     * 状态,负责线程驻留的同步对象{@link Object#wait Objectwait}方法<p> </li> <li>线程位于{@link javalangThreadState#WAITING WAITING}
     * 或{@link javalangThreadState# TIMED_WAITING TIMED_WAITING}状态,因为调用了{@link javautilconcurrentlocksLockSupport#park(Object)LockSupportpark}
     * 方法同步对象是从{@link javautilconcurrentlocksLockSupport#getBlocker LockSupportgetBlocker}方法返回的对象。
     * 通常它是<a href=\"LockInfohtml#OwnableSynchronizer\">可拥有的同步器</a>或{@link javautilconcurrentlocksCondition Condition}
     *  </li>。
     * </ul>
     * 
     * <p>如果线程不在上述任何条件中,此方法返回<tt> null </tt>
     * 
     * 
     * @return <tt>LockInfo</tt> of an object for which the thread
     *         is blocked waiting if any; <tt>null</tt> otherwise.
     * @since 1.6
     */
    public LockInfo getLockInfo() {
        return lock;
    }

    /**
     * Returns the {@link LockInfo#toString string representation}
     * of an object for which the thread associated with this
     * <tt>ThreadInfo</tt> is blocked waiting.
     * This method is equivalent to calling:
     * <blockquote>
     * <pre>
     * getLockInfo().toString()
     * </pre></blockquote>
     *
     * <p>This method will return <tt>null</tt> if this thread is not blocked
     * waiting for any object or if the object is not owned by any thread.
     *
     * <p>
     *  返回与此<tt> ThreadInfo </tt>相关联的线程被阻止等待的对象的{@link LockInfo#toString字符串表示形式}此方法等效于调用：
     * <blockquote>
     * <pre>
     *  getLockInfo()toString()</pre> </blockquote>
     * 
     *  <p>如果此线程未阻塞等待任何对象,或该对象不由任何线程拥有,则此方法将返回<tt> null </tt>
     * 
     * 
     * @return the string representation of the object on which
     * the thread is blocked if any;
     * <tt>null</tt> otherwise.
     *
     * @see #getLockInfo
     */
    public String getLockName() {
        return lockName;
    }

    /**
     * Returns the ID of the thread which owns the object
     * for which the thread associated with this <tt>ThreadInfo</tt>
     * is blocked waiting.
     * This method will return <tt>-1</tt> if this thread is not blocked
     * waiting for any object or if the object is not owned by any thread.
     *
     * <p>
     *  返回拥有与此<tt> ThreadInfo </tt>相关联的线程被阻塞的对象的线程的ID此方法将返回<tt> -1 </tt>如果此线程未阻塞等待任何对象或者该对象不由任何线程拥有
     * 
     * 
     * @return the thread ID of the owner thread of the object
     * this thread is blocked on;
     * <tt>-1</tt> if this thread is not blocked
     * or if the object is not owned by any thread.
     *
     * @see #getLockInfo
     */
    public long getLockOwnerId() {
        return lockOwnerId;
    }

    /**
     * Returns the name of the thread which owns the object
     * for which the thread associated with this <tt>ThreadInfo</tt>
     * is blocked waiting.
     * This method will return <tt>null</tt> if this thread is not blocked
     * waiting for any object or if the object is not owned by any thread.
     *
     * <p>
     * 返回拥有与此<tt> ThreadInfo </tt>关联的线程被阻塞的对象的线程的名称此方法将返回<tt> null </tt>如果此线程未被阻塞等待任何对象或者如果对象不由任何线程拥有
     * 
     * 
     * @return the name of the thread that owns the object
     * this thread is blocked on;
     * <tt>null</tt> if this thread is not blocked
     * or if the object is not owned by any thread.
     *
     * @see #getLockInfo
     */
    public String getLockOwnerName() {
        return lockOwnerName;
    }

    /**
     * Returns the stack trace of the thread
     * associated with this <tt>ThreadInfo</tt>.
     * If no stack trace was requested for this thread info, this method
     * will return a zero-length array.
     * If the returned array is of non-zero length then the first element of
     * the array represents the top of the stack, which is the most recent
     * method invocation in the sequence.  The last element of the array
     * represents the bottom of the stack, which is the least recent method
     * invocation in the sequence.
     *
     * <p>Some Java virtual machines may, under some circumstances, omit one
     * or more stack frames from the stack trace.  In the extreme case,
     * a virtual machine that has no stack trace information concerning
     * the thread associated with this <tt>ThreadInfo</tt>
     * is permitted to return a zero-length array from this method.
     *
     * <p>
     *  返回与此<tt> ThreadInfo </tt>相关联的线程的堆栈跟踪。
     * 如果没有为此线程信息请求堆栈跟踪,此方法将返回零长度数组如果返回的数组长度非零,则数组的第一个元素表示堆栈的顶部,这是序列中最近的方法调用数组的最后一个元素表示堆栈的底部,这是序列中最近的方法调用。
     * 
     * <p>在某些情况下,某些Java虚拟机可能会从堆栈跟踪中省略一个或多个堆栈帧。
     * 在极端情况下,没有与此<tt> ThreadInfo </tt相关联的线程没有堆栈跟踪信息的虚拟机>允许从此方法返回零长度数组。
     * 
     * 
     * @return an array of <tt>StackTraceElement</tt> objects of the thread.
     */
    public StackTraceElement[] getStackTrace() {
        return stackTrace;
    }

    /**
     * Tests if the thread associated with this <tt>ThreadInfo</tt>
     * is suspended.  This method returns <tt>true</tt> if
     * {@link Thread#suspend} has been called.
     *
     * <p>
     *  测试与此<tt> ThreadInfo </tt>相关联的线程是否已暂停此方法返回<tt> true </tt>如果{@link Thread#suspend}已调用
     * 
     * 
     * @return <tt>true</tt> if the thread is suspended;
     *         <tt>false</tt> otherwise.
     */
    public boolean isSuspended() {
         return suspended;
    }

    /**
     * Tests if the thread associated with this <tt>ThreadInfo</tt>
     * is executing native code via the Java Native Interface (JNI).
     * The JNI native code does not include
     * the virtual machine support code or the compiled native
     * code generated by the virtual machine.
     *
     * <p>
     *  测试与此<tt> ThreadInfo </tt>相关联的线程是否通过Java本机接口(JNI)执行本机代码JNI本机代码不包括虚拟机支持代码或虚拟机生成的已编译本机代码
     * 
     * 
     * @return <tt>true</tt> if the thread is executing native code;
     *         <tt>false</tt> otherwise.
     */
    public boolean isInNative() {
         return inNative;
    }

    /**
     * Returns a string representation of this thread info.
     * The format of this string depends on the implementation.
     * The returned string will typically include
     * the {@linkplain #getThreadName thread name},
     * the {@linkplain #getThreadId thread ID},
     * its {@linkplain #getThreadState state},
     * and a {@linkplain #getStackTrace stack trace} if any.
     *
     * <p>
     * 返回此线程的字符串表示形式info此字符串的格式取决于实现返回的字符串通常包括{@linkplain #getThreadName线程名},{@linkplain #getThreadId线程ID},其
     * {@linkplain #getThreadState状态}和{@linkplain #getStackTrace stack trace}(如果有的话)。
     * 
     * 
     * @return a string representation of this thread info.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("\"" + getThreadName() + "\"" +
                                             " Id=" + getThreadId() + " " +
                                             getThreadState());
        if (getLockName() != null) {
            sb.append(" on " + getLockName());
        }
        if (getLockOwnerName() != null) {
            sb.append(" owned by \"" + getLockOwnerName() +
                      "\" Id=" + getLockOwnerId());
        }
        if (isSuspended()) {
            sb.append(" (suspended)");
        }
        if (isInNative()) {
            sb.append(" (in native)");
        }
        sb.append('\n');
        int i = 0;
        for (; i < stackTrace.length && i < MAX_FRAMES; i++) {
            StackTraceElement ste = stackTrace[i];
            sb.append("\tat " + ste.toString());
            sb.append('\n');
            if (i == 0 && getLockInfo() != null) {
                Thread.State ts = getThreadState();
                switch (ts) {
                    case BLOCKED:
                        sb.append("\t-  blocked on " + getLockInfo());
                        sb.append('\n');
                        break;
                    case WAITING:
                        sb.append("\t-  waiting on " + getLockInfo());
                        sb.append('\n');
                        break;
                    case TIMED_WAITING:
                        sb.append("\t-  waiting on " + getLockInfo());
                        sb.append('\n');
                        break;
                    default:
                }
            }

            for (MonitorInfo mi : lockedMonitors) {
                if (mi.getLockedStackDepth() == i) {
                    sb.append("\t-  locked " + mi);
                    sb.append('\n');
                }
            }
       }
       if (i < stackTrace.length) {
           sb.append("\t...");
           sb.append('\n');
       }

       LockInfo[] locks = getLockedSynchronizers();
       if (locks.length > 0) {
           sb.append("\n\tNumber of locked synchronizers = " + locks.length);
           sb.append('\n');
           for (LockInfo li : locks) {
               sb.append("\t- " + li);
               sb.append('\n');
           }
       }
       sb.append('\n');
       return sb.toString();
    }
    private static final int MAX_FRAMES = 8;

    /**
     * Returns a <tt>ThreadInfo</tt> object represented by the
     * given <tt>CompositeData</tt>.
     * The given <tt>CompositeData</tt> must contain the following attributes
     * unless otherwise specified below:
     * <blockquote>
     * <table border summary="The attributes and their types the given CompositeData contains">
     * <tr>
     *   <th align=left>Attribute Name</th>
     *   <th align=left>Type</th>
     * </tr>
     * <tr>
     *   <td>threadId</td>
     *   <td><tt>java.lang.Long</tt></td>
     * </tr>
     * <tr>
     *   <td>threadName</td>
     *   <td><tt>java.lang.String</tt></td>
     * </tr>
     * <tr>
     *   <td>threadState</td>
     *   <td><tt>java.lang.String</tt></td>
     * </tr>
     * <tr>
     *   <td>suspended</td>
     *   <td><tt>java.lang.Boolean</tt></td>
     * </tr>
     * <tr>
     *   <td>inNative</td>
     *   <td><tt>java.lang.Boolean</tt></td>
     * </tr>
     * <tr>
     *   <td>blockedCount</td>
     *   <td><tt>java.lang.Long</tt></td>
     * </tr>
     * <tr>
     *   <td>blockedTime</td>
     *   <td><tt>java.lang.Long</tt></td>
     * </tr>
     * <tr>
     *   <td>waitedCount</td>
     *   <td><tt>java.lang.Long</tt></td>
     * </tr>
     * <tr>
     *   <td>waitedTime</td>
     *   <td><tt>java.lang.Long</tt></td>
     * </tr>
     * <tr>
     *   <td>lockInfo</td>
     *   <td><tt>javax.management.openmbean.CompositeData</tt>
     *       - the mapped type for {@link LockInfo} as specified in the
     *         {@link LockInfo#from} method.
     *       <p>
     *       If <tt>cd</tt> does not contain this attribute,
     *       the <tt>LockInfo</tt> object will be constructed from
     *       the value of the <tt>lockName</tt> attribute. </td>
     * </tr>
     * <tr>
     *   <td>lockName</td>
     *   <td><tt>java.lang.String</tt></td>
     * </tr>
     * <tr>
     *   <td>lockOwnerId</td>
     *   <td><tt>java.lang.Long</tt></td>
     * </tr>
     * <tr>
     *   <td>lockOwnerName</td>
     *   <td><tt>java.lang.String</tt></td>
     * </tr>
     * <tr>
     *   <td><a name="StackTrace">stackTrace</a></td>
     *   <td><tt>javax.management.openmbean.CompositeData[]</tt>
     *       <p>
     *       Each element is a <tt>CompositeData</tt> representing
     *       StackTraceElement containing the following attributes:
     *       <blockquote>
     *       <table cellspacing=1 cellpadding=0 summary="The attributes and their types the given CompositeData contains">
     *       <tr>
     *         <th align=left>Attribute Name</th>
     *         <th align=left>Type</th>
     *       </tr>
     *       <tr>
     *         <td>className</td>
     *         <td><tt>java.lang.String</tt></td>
     *       </tr>
     *       <tr>
     *         <td>methodName</td>
     *         <td><tt>java.lang.String</tt></td>
     *       </tr>
     *       <tr>
     *         <td>fileName</td>
     *         <td><tt>java.lang.String</tt></td>
     *       </tr>
     *       <tr>
     *         <td>lineNumber</td>
     *         <td><tt>java.lang.Integer</tt></td>
     *       </tr>
     *       <tr>
     *         <td>nativeMethod</td>
     *         <td><tt>java.lang.Boolean</tt></td>
     *       </tr>
     *       </table>
     *       </blockquote>
     *   </td>
     * </tr>
     * <tr>
     *   <td>lockedMonitors</td>
     *   <td><tt>javax.management.openmbean.CompositeData[]</tt>
     *       whose element type is the mapped type for
     *       {@link MonitorInfo} as specified in the
     *       {@link MonitorInfo#from Monitor.from} method.
     *       <p>
     *       If <tt>cd</tt> does not contain this attribute,
     *       this attribute will be set to an empty array. </td>
     * </tr>
     * <tr>
     *   <td>lockedSynchronizers</td>
     *   <td><tt>javax.management.openmbean.CompositeData[]</tt>
     *       whose element type is the mapped type for
     *       {@link LockInfo} as specified in the {@link LockInfo#from} method.
     *       <p>
     *       If <tt>cd</tt> does not contain this attribute,
     *       this attribute will be set to an empty array. </td>
     * </tr>
     * </table>
     * </blockquote>
     *
     * <p>
     *  返回由给定的<tt> CompositeData </tt>表示的<tt> ThreadInfo </tt>对象给定的<tt> CompositeData </tt>必须包含以下属性,除非另有规定：。
     * <blockquote>
     * <table border summary="The attributes and their types the given CompositeData contains">
     * <tr>
     *  <th align = left>属性名称</th> <th align = left>键入</th>
     * </tr>
     * <tr>
     *  <td> threadId </td> <td> <tt> javalangLong </tt> </td>
     * </tr>
     * <tr>
     *  <td> threadName </td> <td> <tt> javalangString </tt> </td>
     * </tr>
     * <tr>
     *  <td> threadState </td> <td> <tt> javalangString </tt> </td>
     * </tr>
     * <tr>
     * <td>暂停</td> <td> <tt> javalangBoolean </tt> </td>
     * </tr>
     * <tr>
     *  <td> inNative </td> <td> <tt> javalangBoolean </tt> </td>
     * </tr>
     * <tr>
     *  <td> blockedCount </td> <td> <tt> javalangLong </tt> </td>
     * </tr>
     * <tr>
     *  <td> blockedTime </td> <td> <tt> javalangLong </tt> </td>
     * </tr>
     * <tr>
     *  <td> waitedCount </td> <td> <tt> javalangLong </tt> </td>
     * </tr>
     * <tr>
     *  <td> waitedTime </td> <td> <tt> javalangLong </tt> </td>
     * </tr>
     * <tr>
     *  <td> lockInfo </td> <td> <tt> javaxmanagementopenmbeanCompositeData </tt>  - 在{@link LockInfo#from}方
     * 法中指定的{@link LockInfo}的映射类型。
     * <p>
     *  如果<tt> cd </tt>不包含此属性,则<tt> LockInfo </tt>对象将从<tt> lockName </tt>属性的值</td>
     * </tr>
     * <tr>
     *  <td> lockName </td> <td> <tt> javalangString </tt> </td>
     * </tr>
     * <tr>
     *  <td> lockOwnerId </td> <td> <tt> javalangLong </tt> </td>
     * </tr>
     * <tr>
     * <td> lockOwnerName </td> <td> <tt> javalangString </tt> </td>
     * </tr>
     * <tr>
     *  <td> <a name=\"StackTrace\"> stackTrace </a> </td> <td> <tt> javaxmanagementopenmbeanCompositeData [
     * 
     * @param cd <tt>CompositeData</tt> representing a <tt>ThreadInfo</tt>
     *
     * @throws IllegalArgumentException if <tt>cd</tt> does not
     *   represent a <tt>ThreadInfo</tt> with the attributes described
     *   above.
     *
     * @return a <tt>ThreadInfo</tt> object represented
     *         by <tt>cd</tt> if <tt>cd</tt> is not <tt>null</tt>;
     *         <tt>null</tt> otherwise.
     */
    public static ThreadInfo from(CompositeData cd) {
        if (cd == null) {
            return null;
        }

        if (cd instanceof ThreadInfoCompositeData) {
            return ((ThreadInfoCompositeData) cd).getThreadInfo();
        } else {
            return new ThreadInfo(cd);
        }
    }

    /**
     * Returns an array of {@link MonitorInfo} objects, each of which
     * represents an object monitor currently locked by the thread
     * associated with this <tt>ThreadInfo</tt>.
     * If no locked monitor was requested for this thread info or
     * no monitor is locked by the thread, this method
     * will return a zero-length array.
     *
     * <p>
     * ] </tt>。
     * <p>
     *  每个元素都是一个表示StackTraceElement的<tt> CompositeData </tt>,它包含以下属性：
     * <blockquote>
     * <table cellspacing=1 cellpadding=0 summary="The attributes and their types the given CompositeData contains">
     * <tr>
     *  <th align = left>属性名称</th> <th align = left>键入</th>
     * </tr>
     * <tr>
     *  <td> className </td> <td> <tt> javalangString </tt> </td>
     * </tr>
     * <tr>
     *  <td> methodName </td> <td> <tt> javalangString </tt> </td>
     * </tr>
     * <tr>
     *  <td> fileName </td> <td> <tt> javalangString </tt> </td>
     * </tr>
     * <tr>
     *  <td> lineNumber </td> <td> <tt> javalangInteger </tt> </td>
     * </tr>
     * <tr>
     *  <td> nativeMethod </td> <td> <tt> javalangBoolean </tt> </td>
     * </tr>
     * </table>
     * </blockquote>
     * 
     * @return an array of <tt>MonitorInfo</tt> objects representing
     *         the object monitors locked by the thread.
     *
     * @since 1.6
     */
    public MonitorInfo[] getLockedMonitors() {
        return lockedMonitors;
    }

    /**
     * Returns an array of {@link LockInfo} objects, each of which
     * represents an <a href="LockInfo.html#OwnableSynchronizer">ownable
     * synchronizer</a> currently locked by the thread associated with
     * this <tt>ThreadInfo</tt>.  If no locked synchronizer was
     * requested for this thread info or no synchronizer is locked by
     * the thread, this method will return a zero-length array.
     *
     * <p>
     * </td>
     * </tr>
     * <tr>
     * <td> lockedMonitors </td> <td> <tt> javaxmanagementopenmbeanCompositeData [] </tt>其元素类型是{@link MonitorInfo}
     * 的映射类型,在{@link MonitorInfo#from Monitorfrom}方法中指定。
     * <p>
     *  如果<tt> cd </tt>不包含此属性,则此属性将设置为空数组</td>
     * </tr>
     * <tr>
     *  <td> lockedSynchronizers </td> <td> <tt> javaxmanagementopenmbeanCompositeData [] </tt>其元素类型是{@link LockInfo}
     * 的映射类型,如{@link LockInfo#from}。
     * <p>
     *  如果<tt> cd </tt>不包含此属性,则此属性将设置为空数组</td>
     * 
     * @return an array of <tt>LockInfo</tt> objects representing
     *         the ownable synchronizers locked by the thread.
     *
     * @since 1.6
     */
    public LockInfo[] getLockedSynchronizers() {
        return lockedSynchronizers;
    }

    private static final StackTraceElement[] NO_STACK_TRACE =
        new StackTraceElement[0];
}
