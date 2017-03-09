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

package java.util;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A facility for threads to schedule tasks for future execution in a
 * background thread.  Tasks may be scheduled for one-time execution, or for
 * repeated execution at regular intervals.
 *
 * <p>Corresponding to each <tt>Timer</tt> object is a single background
 * thread that is used to execute all of the timer's tasks, sequentially.
 * Timer tasks should complete quickly.  If a timer task takes excessive time
 * to complete, it "hogs" the timer's task execution thread.  This can, in
 * turn, delay the execution of subsequent tasks, which may "bunch up" and
 * execute in rapid succession when (and if) the offending task finally
 * completes.
 *
 * <p>After the last live reference to a <tt>Timer</tt> object goes away
 * <i>and</i> all outstanding tasks have completed execution, the timer's task
 * execution thread terminates gracefully (and becomes subject to garbage
 * collection).  However, this can take arbitrarily long to occur.  By
 * default, the task execution thread does not run as a <i>daemon thread</i>,
 * so it is capable of keeping an application from terminating.  If a caller
 * wants to terminate a timer's task execution thread rapidly, the caller
 * should invoke the timer's <tt>cancel</tt> method.
 *
 * <p>If the timer's task execution thread terminates unexpectedly, for
 * example, because its <tt>stop</tt> method is invoked, any further
 * attempt to schedule a task on the timer will result in an
 * <tt>IllegalStateException</tt>, as if the timer's <tt>cancel</tt>
 * method had been invoked.
 *
 * <p>This class is thread-safe: multiple threads can share a single
 * <tt>Timer</tt> object without the need for external synchronization.
 *
 * <p>This class does <i>not</i> offer real-time guarantees: it schedules
 * tasks using the <tt>Object.wait(long)</tt> method.
 *
 * <p>Java 5.0 introduced the {@code java.util.concurrent} package and
 * one of the concurrency utilities therein is the {@link
 * java.util.concurrent.ScheduledThreadPoolExecutor
 * ScheduledThreadPoolExecutor} which is a thread pool for repeatedly
 * executing tasks at a given rate or delay.  It is effectively a more
 * versatile replacement for the {@code Timer}/{@code TimerTask}
 * combination, as it allows multiple service threads, accepts various
 * time units, and doesn't require subclassing {@code TimerTask} (just
 * implement {@code Runnable}).  Configuring {@code
 * ScheduledThreadPoolExecutor} with one thread makes it equivalent to
 * {@code Timer}.
 *
 * <p>Implementation note: This class scales to large numbers of concurrently
 * scheduled tasks (thousands should present no problem).  Internally,
 * it uses a binary heap to represent its task queue, so the cost to schedule
 * a task is O(log n), where n is the number of concurrently scheduled tasks.
 *
 * <p>Implementation note: All constructors start a timer thread.
 *
 * <p>
 *  线程调度任务以便将来在后台线程中执行的工具。可以将任务调度为一次性执行,或者以固定间隔重复执行。
 * 
 *  <p>与每个<tt> Timer </tt>对象相对应的是单个后台线程,用于顺序执行所有计时器的任务。计时器任务应该快速完成。如果计时器任务花费过多的时间来完成,则它"计数"计时器的任务执行线程。
 * 这又可以延迟后续任务的执行,这些任务可能"成堆"并且在该违规任务(并且如果)最终完成时快速连续地执行。
 * 
 *  <p>在对<tt> Timer </tt>对象的最后一次实时引用消失<i>和</i>所有未完成的任务已完成执行后,计时器的任务执行线程会正常终止(并受到垃圾回收)。然而,这可能需要任意长的时间。
 * 默认情况下,任务执行线程不作为<i>守护线程</i>运行,因此它能够阻止应用程序终止。如果调用者想要快速终止定时器的任务执行线程,调用者应该调用定时器的<tt> cancel </tt>方法。
 * 
 * <p>如果定时器的任务执行线程意外终止,例如因为<tt>停止</tt>方法被调用,任何进一步尝试在定时器上调度任务将导致<tt> IllegalStateException </tt >,如同定时器的<tt>
 *  cancel </tt>方法已被调用。
 * 
 *  <p>此类是线程安全的：多个线程可以共享一个<tt> Timer </tt>对象,而无需外部同步。
 * 
 *  <p>此类不会</i>提供实时保证：它使用<tt> Object.wait(long)</tt>方法计划任务。
 * 
 *  <p> Java 5.0引入了{@code java.util.concurrent}包,其中的一个并发实用程序是{@link java.util.concurrent.ScheduledThreadPoolExecutor ScheduledThreadPoolExecutor}
 * ,它是一个线程池,用于在给定的速率或延迟。
 * 它实际上是一个更通用的替代{@code Timer} / {@ code TimerTask}组合,因为它允许多个服务线程,接受各种时间单位,不需要子类{@code TimerTask}(只是实现{@代码Runnable}
 * )。
 * 用一个线程配置{@code ScheduledThreadPoolExecutor}使它等同于{@code Timer}。
 * 
 *  <p>实现注意：这个类可以扩展到大量的并行计划任务(千个应该没有问题)。在内部,它使用二进制堆来表示其任务队列,因此调度任务的成本是O(log n),其中n是并发计划任务的数量。
 * 
 * <p>实现注意：所有构造函数都启动计时器线程。
 * 
 * 
 * @author  Josh Bloch
 * @see     TimerTask
 * @see     Object#wait(long)
 * @since   1.3
 */

public class Timer {
    /**
     * The timer task queue.  This data structure is shared with the timer
     * thread.  The timer produces tasks, via its various schedule calls,
     * and the timer thread consumes, executing timer tasks as appropriate,
     * and removing them from the queue when they're obsolete.
     * <p>
     *  计时器任务队列。此数据结构与定时器线程共享。定时器通过其各种调度调用产生任务,定时器线程消耗,根据需要执行定时器任务,并且当它们过时时将其从队列中移除。
     * 
     */
    private final TaskQueue queue = new TaskQueue();

    /**
     * The timer thread.
     * <p>
     *  定时器线程。
     * 
     */
    private final TimerThread thread = new TimerThread(queue);

    /**
     * This object causes the timer's task execution thread to exit
     * gracefully when there are no live references to the Timer object and no
     * tasks in the timer queue.  It is used in preference to a finalizer on
     * Timer as such a finalizer would be susceptible to a subclass's
     * finalizer forgetting to call it.
     * <p>
     *  当没有对Timer对象的实时引用和定时器队列中的任何任务时,此对象使定时器的任务执行线程正常退出。它用于优先于定时器上的终结器,因为这样的终结器将容易受到子类的终结器忘记调用它。
     * 
     */
    private final Object threadReaper = new Object() {
        protected void finalize() throws Throwable {
            synchronized(queue) {
                thread.newTasksMayBeScheduled = false;
                queue.notify(); // In case queue is empty.
            }
        }
    };

    /**
     * This ID is used to generate thread names.
     * <p>
     *  此ID用于生成线程名称。
     * 
     */
    private final static AtomicInteger nextSerialNumber = new AtomicInteger(0);
    private static int serialNumber() {
        return nextSerialNumber.getAndIncrement();
    }

    /**
     * Creates a new timer.  The associated thread does <i>not</i>
     * {@linkplain Thread#setDaemon run as a daemon}.
     * <p>
     *  创建新的计时器。相关联的线程</i>不会</i> {@linkplain线程#setDaemon作为守护程序运行}。
     * 
     */
    public Timer() {
        this("Timer-" + serialNumber());
    }

    /**
     * Creates a new timer whose associated thread may be specified to
     * {@linkplain Thread#setDaemon run as a daemon}.
     * A daemon thread is called for if the timer will be used to
     * schedule repeating "maintenance activities", which must be
     * performed as long as the application is running, but should not
     * prolong the lifetime of the application.
     *
     * <p>
     *  创建一个新的计时器,其相关的线程可以被指定为{@linkplain Thread#setDaemon run as a daemon}。
     * 如果定时器将被用于调度重复的"维护活动",则必须使用守护线程,该维护活动必须在应用程序运行时执行,但不应该延长应用程序的生命周期。
     * 
     * 
     * @param isDaemon true if the associated thread should run as a daemon.
     */
    public Timer(boolean isDaemon) {
        this("Timer-" + serialNumber(), isDaemon);
    }

    /**
     * Creates a new timer whose associated thread has the specified name.
     * The associated thread does <i>not</i>
     * {@linkplain Thread#setDaemon run as a daemon}.
     *
     * <p>
     *  创建一个新的计时器,其相关联的线程具有指定的名称。相关联的线程</i>不会</i> {@linkplain线程#setDaemon作为守护程序运行}。
     * 
     * 
     * @param name the name of the associated thread
     * @throws NullPointerException if {@code name} is null
     * @since 1.5
     */
    public Timer(String name) {
        thread.setName(name);
        thread.start();
    }

    /**
     * Creates a new timer whose associated thread has the specified name,
     * and may be specified to
     * {@linkplain Thread#setDaemon run as a daemon}.
     *
     * <p>
     *  创建一个新的计时器,其相关联的线程具有指定的名称,并且可以指定为{@linkplain Thread#setDaemon作为守护程序运行}。
     * 
     * 
     * @param name the name of the associated thread
     * @param isDaemon true if the associated thread should run as a daemon
     * @throws NullPointerException if {@code name} is null
     * @since 1.5
     */
    public Timer(String name, boolean isDaemon) {
        thread.setName(name);
        thread.setDaemon(isDaemon);
        thread.start();
    }

    /**
     * Schedules the specified task for execution after the specified delay.
     *
     * <p>
     *  在指定的延迟后,调度指定的任务以执行。
     * 
     * 
     * @param task  task to be scheduled.
     * @param delay delay in milliseconds before task is to be executed.
     * @throws IllegalArgumentException if <tt>delay</tt> is negative, or
     *         <tt>delay + System.currentTimeMillis()</tt> is negative.
     * @throws IllegalStateException if task was already scheduled or
     *         cancelled, timer was cancelled, or timer thread terminated.
     * @throws NullPointerException if {@code task} is null
     */
    public void schedule(TimerTask task, long delay) {
        if (delay < 0)
            throw new IllegalArgumentException("Negative delay.");
        sched(task, System.currentTimeMillis()+delay, 0);
    }

    /**
     * Schedules the specified task for execution at the specified time.  If
     * the time is in the past, the task is scheduled for immediate execution.
     *
     * <p>
     * 在指定时间计划要执行的指定任务。如果时间是过去的,则调度任务以立即执行。
     * 
     * 
     * @param task task to be scheduled.
     * @param time time at which task is to be executed.
     * @throws IllegalArgumentException if <tt>time.getTime()</tt> is negative.
     * @throws IllegalStateException if task was already scheduled or
     *         cancelled, timer was cancelled, or timer thread terminated.
     * @throws NullPointerException if {@code task} or {@code time} is null
     */
    public void schedule(TimerTask task, Date time) {
        sched(task, time.getTime(), 0);
    }

    /**
     * Schedules the specified task for repeated <i>fixed-delay execution</i>,
     * beginning after the specified delay.  Subsequent executions take place
     * at approximately regular intervals separated by the specified period.
     *
     * <p>In fixed-delay execution, each execution is scheduled relative to
     * the actual execution time of the previous execution.  If an execution
     * is delayed for any reason (such as garbage collection or other
     * background activity), subsequent executions will be delayed as well.
     * In the long run, the frequency of execution will generally be slightly
     * lower than the reciprocal of the specified period (assuming the system
     * clock underlying <tt>Object.wait(long)</tt> is accurate).
     *
     * <p>Fixed-delay execution is appropriate for recurring activities
     * that require "smoothness."  In other words, it is appropriate for
     * activities where it is more important to keep the frequency accurate
     * in the short run than in the long run.  This includes most animation
     * tasks, such as blinking a cursor at regular intervals.  It also includes
     * tasks wherein regular activity is performed in response to human
     * input, such as automatically repeating a character as long as a key
     * is held down.
     *
     * <p>
     *  在指定的延迟后开始,为重复的<i>固定延迟执行</i>调度指定的任务。后续执行以大约固定的时间间隔分开指定的时间段。
     * 
     *  <p>在固定延迟执行中,相对于前一个执行的实际执行时间,调度每个执行。如果执行由于任何原因(例如垃圾回收或其他后台活动)而延迟,后续执行也将被延迟。
     * 从长远来看,执行频率通常会略低于指定周期的倒数(假设底层<tt> Object.wait(long)</tt>的系统时钟是准确的)。
     * 
     *  <p>固定延迟执行适用于需要"平滑"的重复活动。换句话说,它适用于在短期内保持频率比长期保持更为重要的活动。这包括大多数动画任务,例如以固定间隔闪烁光标。
     * 它还包括其中响应于人类输入执行常规活动的任务,例如只要按住键就自动重复字符。
     * 
     * 
     * @param task   task to be scheduled.
     * @param delay  delay in milliseconds before task is to be executed.
     * @param period time in milliseconds between successive task executions.
     * @throws IllegalArgumentException if {@code delay < 0}, or
     *         {@code delay + System.currentTimeMillis() < 0}, or
     *         {@code period <= 0}
     * @throws IllegalStateException if task was already scheduled or
     *         cancelled, timer was cancelled, or timer thread terminated.
     * @throws NullPointerException if {@code task} is null
     */
    public void schedule(TimerTask task, long delay, long period) {
        if (delay < 0)
            throw new IllegalArgumentException("Negative delay.");
        if (period <= 0)
            throw new IllegalArgumentException("Non-positive period.");
        sched(task, System.currentTimeMillis()+delay, -period);
    }

    /**
     * Schedules the specified task for repeated <i>fixed-delay execution</i>,
     * beginning at the specified time. Subsequent executions take place at
     * approximately regular intervals, separated by the specified period.
     *
     * <p>In fixed-delay execution, each execution is scheduled relative to
     * the actual execution time of the previous execution.  If an execution
     * is delayed for any reason (such as garbage collection or other
     * background activity), subsequent executions will be delayed as well.
     * In the long run, the frequency of execution will generally be slightly
     * lower than the reciprocal of the specified period (assuming the system
     * clock underlying <tt>Object.wait(long)</tt> is accurate).  As a
     * consequence of the above, if the scheduled first time is in the past,
     * it is scheduled for immediate execution.
     *
     * <p>Fixed-delay execution is appropriate for recurring activities
     * that require "smoothness."  In other words, it is appropriate for
     * activities where it is more important to keep the frequency accurate
     * in the short run than in the long run.  This includes most animation
     * tasks, such as blinking a cursor at regular intervals.  It also includes
     * tasks wherein regular activity is performed in response to human
     * input, such as automatically repeating a character as long as a key
     * is held down.
     *
     * <p>
     * 从指定时间开始,为指定的任务重复<i>固定延迟执行</i>。后续执行以大约固定的时间间隔进行,以指定的时间间隔分隔。
     * 
     *  <p>在固定延迟执行中,相对于前一个执行的实际执行时间,调度每个执行。如果执行由于任何原因(例如垃圾回收或其他后台活动)而延迟,后续执行也将被延迟。
     * 从长远来看,执行频率通常会略低于指定周期的倒数(假设底层<tt> Object.wait(long)</tt>的系统时钟是准确的)。作为上述的结果,如果调度的第一时间是过去的,则调度它立即执行。
     * 
     *  <p>固定延迟执行适用于需要"平滑"的重复活动。换句话说,它适用于在短期内保持频率比长期保持更为重要的活动。这包括大多数动画任务,例如以固定间隔闪烁光标。
     * 它还包括其中响应于人类输入执行常规活动的任务,例如只要按住键就自动重复字符。
     * 
     * 
     * @param task   task to be scheduled.
     * @param firstTime First time at which task is to be executed.
     * @param period time in milliseconds between successive task executions.
     * @throws IllegalArgumentException if {@code firstTime.getTime() < 0}, or
     *         {@code period <= 0}
     * @throws IllegalStateException if task was already scheduled or
     *         cancelled, timer was cancelled, or timer thread terminated.
     * @throws NullPointerException if {@code task} or {@code firstTime} is null
     */
    public void schedule(TimerTask task, Date firstTime, long period) {
        if (period <= 0)
            throw new IllegalArgumentException("Non-positive period.");
        sched(task, firstTime.getTime(), -period);
    }

    /**
     * Schedules the specified task for repeated <i>fixed-rate execution</i>,
     * beginning after the specified delay.  Subsequent executions take place
     * at approximately regular intervals, separated by the specified period.
     *
     * <p>In fixed-rate execution, each execution is scheduled relative to the
     * scheduled execution time of the initial execution.  If an execution is
     * delayed for any reason (such as garbage collection or other background
     * activity), two or more executions will occur in rapid succession to
     * "catch up."  In the long run, the frequency of execution will be
     * exactly the reciprocal of the specified period (assuming the system
     * clock underlying <tt>Object.wait(long)</tt> is accurate).
     *
     * <p>Fixed-rate execution is appropriate for recurring activities that
     * are sensitive to <i>absolute</i> time, such as ringing a chime every
     * hour on the hour, or running scheduled maintenance every day at a
     * particular time.  It is also appropriate for recurring activities
     * where the total time to perform a fixed number of executions is
     * important, such as a countdown timer that ticks once every second for
     * ten seconds.  Finally, fixed-rate execution is appropriate for
     * scheduling multiple repeating timer tasks that must remain synchronized
     * with respect to one another.
     *
     * <p>
     * 在指定的延迟后开始,为重复的<i>固定速率执行</i>调度指定的任务。后续执行以大约固定的时间间隔进行,以指定的时间间隔分隔。
     * 
     *  <p>在固定速率执行中,相对于初始执行的预定执行时间,调度每个执行。如果执行由于任何原因(例如垃圾收集或其他后台活动)而延迟,则两个或更多个执行将快速连续地发生以"赶上"。
     * 从长远看,执行的频率将恰好是指定周期的倒数(假设底层<tt> Object.wait(long)</tt>的系统时钟是准确的)。
     * 
     *  <p>固定费率执行适用于对<i>绝对时间敏感的周期性活动,例如每小时每小时响一个钟声,或在特定时间每天运行定期维护。
     * 它也适用于重复活动,其中执行固定数量的执行的总时间是重要的,例如每秒钟每秒钟进行10秒钟的倒计时定时器。最后,固定速率执行适用于调度必须保持彼此同步的多个重复定时器任务。
     * 
     * 
     * @param task   task to be scheduled.
     * @param delay  delay in milliseconds before task is to be executed.
     * @param period time in milliseconds between successive task executions.
     * @throws IllegalArgumentException if {@code delay < 0}, or
     *         {@code delay + System.currentTimeMillis() < 0}, or
     *         {@code period <= 0}
     * @throws IllegalStateException if task was already scheduled or
     *         cancelled, timer was cancelled, or timer thread terminated.
     * @throws NullPointerException if {@code task} is null
     */
    public void scheduleAtFixedRate(TimerTask task, long delay, long period) {
        if (delay < 0)
            throw new IllegalArgumentException("Negative delay.");
        if (period <= 0)
            throw new IllegalArgumentException("Non-positive period.");
        sched(task, System.currentTimeMillis()+delay, period);
    }

    /**
     * Schedules the specified task for repeated <i>fixed-rate execution</i>,
     * beginning at the specified time. Subsequent executions take place at
     * approximately regular intervals, separated by the specified period.
     *
     * <p>In fixed-rate execution, each execution is scheduled relative to the
     * scheduled execution time of the initial execution.  If an execution is
     * delayed for any reason (such as garbage collection or other background
     * activity), two or more executions will occur in rapid succession to
     * "catch up."  In the long run, the frequency of execution will be
     * exactly the reciprocal of the specified period (assuming the system
     * clock underlying <tt>Object.wait(long)</tt> is accurate).  As a
     * consequence of the above, if the scheduled first time is in the past,
     * then any "missed" executions will be scheduled for immediate "catch up"
     * execution.
     *
     * <p>Fixed-rate execution is appropriate for recurring activities that
     * are sensitive to <i>absolute</i> time, such as ringing a chime every
     * hour on the hour, or running scheduled maintenance every day at a
     * particular time.  It is also appropriate for recurring activities
     * where the total time to perform a fixed number of executions is
     * important, such as a countdown timer that ticks once every second for
     * ten seconds.  Finally, fixed-rate execution is appropriate for
     * scheduling multiple repeating timer tasks that must remain synchronized
     * with respect to one another.
     *
     * <p>
     *  从指定的时间开始,为重复的<i>固定速率执行</i>调度指定的任务。后续执行以大约固定的时间间隔进行,以指定的时间间隔分隔。
     * 
     * <p>在固定速率执行中,相对于初始执行的预定执行时间,调度每个执行。如果执行由于任何原因(例如垃圾收集或其他后台活动)而延迟,则两个或更多个执行将快速连续地发生以"赶上"。
     * 从长远看,执行的频率将恰好是指定周期的倒数(假设底层<tt> Object.wait(long)</tt>的系统时钟是准确的)。
     * 作为上述的结果,如果调度的第一时间是过去的,则将调度任何"未命中"执行以立即"追赶"执行。
     * 
     *  <p>固定费率执行适用于对<i>绝对时间敏感的周期性活动,例如每小时每小时响一个钟声,或在特定时间每天运行定期维护。
     * 它也适用于重复活动,其中执行固定数量的执行的总时间是重要的,例如每秒钟每秒钟进行10秒钟的倒计时定时器。最后,固定速率执行适用于调度必须保持彼此同步的多个重复定时器任务。
     * 
     * 
     * @param task   task to be scheduled.
     * @param firstTime First time at which task is to be executed.
     * @param period time in milliseconds between successive task executions.
     * @throws IllegalArgumentException if {@code firstTime.getTime() < 0} or
     *         {@code period <= 0}
     * @throws IllegalStateException if task was already scheduled or
     *         cancelled, timer was cancelled, or timer thread terminated.
     * @throws NullPointerException if {@code task} or {@code firstTime} is null
     */
    public void scheduleAtFixedRate(TimerTask task, Date firstTime,
                                    long period) {
        if (period <= 0)
            throw new IllegalArgumentException("Non-positive period.");
        sched(task, firstTime.getTime(), period);
    }

    /**
     * Schedule the specified timer task for execution at the specified
     * time with the specified period, in milliseconds.  If period is
     * positive, the task is scheduled for repeated execution; if period is
     * zero, the task is scheduled for one-time execution. Time is specified
     * in Date.getTime() format.  This method checks timer state, task state,
     * and initial execution time, but not period.
     *
     * <p>
     * 在指定的时间以指定的时间段(以毫秒为单位)计划要执行的指定计时器任务。如果周期为正,则任务被安排重复执行;如果周期为零,则将任务调度为一次性执行。时间以Date.getTime()格式指定。
     * 此方法检查计时器状态,任务状态和初始执行时间,但不检查周期。
     * 
     * 
     * @throws IllegalArgumentException if <tt>time</tt> is negative.
     * @throws IllegalStateException if task was already scheduled or
     *         cancelled, timer was cancelled, or timer thread terminated.
     * @throws NullPointerException if {@code task} is null
     */
    private void sched(TimerTask task, long time, long period) {
        if (time < 0)
            throw new IllegalArgumentException("Illegal execution time.");

        // Constrain value of period sufficiently to prevent numeric
        // overflow while still being effectively infinitely large.
        if (Math.abs(period) > (Long.MAX_VALUE >> 1))
            period >>= 1;

        synchronized(queue) {
            if (!thread.newTasksMayBeScheduled)
                throw new IllegalStateException("Timer already cancelled.");

            synchronized(task.lock) {
                if (task.state != TimerTask.VIRGIN)
                    throw new IllegalStateException(
                        "Task already scheduled or cancelled");
                task.nextExecutionTime = time;
                task.period = period;
                task.state = TimerTask.SCHEDULED;
            }

            queue.add(task);
            if (queue.getMin() == task)
                queue.notify();
        }
    }

    /**
     * Terminates this timer, discarding any currently scheduled tasks.
     * Does not interfere with a currently executing task (if it exists).
     * Once a timer has been terminated, its execution thread terminates
     * gracefully, and no more tasks may be scheduled on it.
     *
     * <p>Note that calling this method from within the run method of a
     * timer task that was invoked by this timer absolutely guarantees that
     * the ongoing task execution is the last task execution that will ever
     * be performed by this timer.
     *
     * <p>This method may be called repeatedly; the second and subsequent
     * calls have no effect.
     * <p>
     *  终止此计时器,丢弃任何当前计划的任务。不干扰当前正在执行的任务(如果存在)。一旦定时器被终止,它的执行线程被正常终止,并且不能在它上面安排更多的任务。
     * 
     *  <p>请注意,从此计时器调用的计时器任务的run方法中调用此方法绝对保证正在进行的任务执行是此计时器将执行的最后一个任务执行。
     * 
     *  <p>此方法可以重复调用;第二个和后续呼叫没有效果。
     * 
     */
    public void cancel() {
        synchronized(queue) {
            thread.newTasksMayBeScheduled = false;
            queue.clear();
            queue.notify();  // In case queue was already empty.
        }
    }

    /**
     * Removes all cancelled tasks from this timer's task queue.  <i>Calling
     * this method has no effect on the behavior of the timer</i>, but
     * eliminates the references to the cancelled tasks from the queue.
     * If there are no external references to these tasks, they become
     * eligible for garbage collection.
     *
     * <p>Most programs will have no need to call this method.
     * It is designed for use by the rare application that cancels a large
     * number of tasks.  Calling this method trades time for space: the
     * runtime of the method may be proportional to n + c log n, where n
     * is the number of tasks in the queue and c is the number of cancelled
     * tasks.
     *
     * <p>Note that it is permissible to call this method from within a
     * a task scheduled on this timer.
     *
     * <p>
     *  从此计时器的任务队列中删除所有已取消的任务。 <i>调用此方法对计时器</i>的行为没有影响,但会从队列中删除对已取消任务的引用。如果没有对这些任务的外部引用,它们将成为垃圾收集的资格。
     * 
     * <p>大多数程序不需要调用此方法。它被设计为由消除大量任务的罕见应用程序使用。调用此方法会占用空间时间：方法的运行时间可能与n + c log n成正比,其中n是队列中的任务数量,c是取消任务的数量。
     * 
     *  <p>请注意,允许从此计时器上调度的任务中调用此方法。
     * 
     * 
     * @return the number of tasks removed from the queue.
     * @since 1.5
     */
     public int purge() {
         int result = 0;

         synchronized(queue) {
             for (int i = queue.size(); i > 0; i--) {
                 if (queue.get(i).state == TimerTask.CANCELLED) {
                     queue.quickRemove(i);
                     result++;
                 }
             }

             if (result != 0)
                 queue.heapify();
         }

         return result;
     }
}

/**
 * This "helper class" implements the timer's task execution thread, which
 * waits for tasks on the timer queue, executions them when they fire,
 * reschedules repeating tasks, and removes cancelled tasks and spent
 * non-repeating tasks from the queue.
 * <p>
 *  这个"帮助类"实现定时器的任务执行线程,该线程等待定时器队列上的任务,当它们触发时执行它们,重新调度重复任务,并且从队列中移除取消的任务和花费非重复任务。
 * 
 */
class TimerThread extends Thread {
    /**
     * This flag is set to false by the reaper to inform us that there
     * are no more live references to our Timer object.  Once this flag
     * is true and there are no more tasks in our queue, there is no
     * work left for us to do, so we terminate gracefully.  Note that
     * this field is protected by queue's monitor!
     * <p>
     *  这个标志被收集器设置为false,通知我们没有更多的对我们的Timer对象的实时引用。一旦这个标志是真的,我们的队列中没有更多的任务,我们没有剩下的工作,所以我们终止了。
     * 请注意,此字段受队列的监视器保护！。
     * 
     */
    boolean newTasksMayBeScheduled = true;

    /**
     * Our Timer's queue.  We store this reference in preference to
     * a reference to the Timer so the reference graph remains acyclic.
     * Otherwise, the Timer would never be garbage-collected and this
     * thread would never go away.
     * <p>
     *  我们的计时器的队列。我们存储此引用优先于定时器的引用,因此引用图形保持非循环。否则,定时器永远不会被垃圾回收,这个线程永远不会消失。
     * 
     */
    private TaskQueue queue;

    TimerThread(TaskQueue queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            mainLoop();
        } finally {
            // Someone killed this Thread, behave as if Timer cancelled
            synchronized(queue) {
                newTasksMayBeScheduled = false;
                queue.clear();  // Eliminate obsolete references
            }
        }
    }

    /**
     * The main timer loop.  (See class comment.)
     * <p>
     *  主定时器循环。 (见班级评论。)
     * 
     */
    private void mainLoop() {
        while (true) {
            try {
                TimerTask task;
                boolean taskFired;
                synchronized(queue) {
                    // Wait for queue to become non-empty
                    while (queue.isEmpty() && newTasksMayBeScheduled)
                        queue.wait();
                    if (queue.isEmpty())
                        break; // Queue is empty and will forever remain; die

                    // Queue nonempty; look at first evt and do the right thing
                    long currentTime, executionTime;
                    task = queue.getMin();
                    synchronized(task.lock) {
                        if (task.state == TimerTask.CANCELLED) {
                            queue.removeMin();
                            continue;  // No action required, poll queue again
                        }
                        currentTime = System.currentTimeMillis();
                        executionTime = task.nextExecutionTime;
                        if (taskFired = (executionTime<=currentTime)) {
                            if (task.period == 0) { // Non-repeating, remove
                                queue.removeMin();
                                task.state = TimerTask.EXECUTED;
                            } else { // Repeating task, reschedule
                                queue.rescheduleMin(
                                  task.period<0 ? currentTime   - task.period
                                                : executionTime + task.period);
                            }
                        }
                    }
                    if (!taskFired) // Task hasn't yet fired; wait
                        queue.wait(executionTime - currentTime);
                }
                if (taskFired)  // Task fired; run it, holding no locks
                    task.run();
            } catch(InterruptedException e) {
            }
        }
    }
}

/**
 * This class represents a timer task queue: a priority queue of TimerTasks,
 * ordered on nextExecutionTime.  Each Timer object has one of these, which it
 * shares with its TimerThread.  Internally this class uses a heap, which
 * offers log(n) performance for the add, removeMin and rescheduleMin
 * operations, and constant time performance for the getMin operation.
 * <p>
 * 此类表示一个计时器任务队列：TimerTasks的优先级队列,在nextExecutionTime上排序。每个Timer对象都有其中的一个,它与它的TimerThread共享。
 * 在内部,这个类使用一个堆,它为add,removeMin和rescheduleMin操作提供log(n)性能,并为getMin操作提供恒定时间性能。
 * 
 */
class TaskQueue {
    /**
     * Priority queue represented as a balanced binary heap: the two children
     * of queue[n] are queue[2*n] and queue[2*n+1].  The priority queue is
     * ordered on the nextExecutionTime field: The TimerTask with the lowest
     * nextExecutionTime is in queue[1] (assuming the queue is nonempty).  For
     * each node n in the heap, and each descendant of n, d,
     * n.nextExecutionTime <= d.nextExecutionTime.
     * <p>
     *  表示为平衡二进制堆的优先级队列：队列[n]的两个子队列是队列[2 * n]和队列[2 * n + 1]。
     * 优先级队列在nextExecutionTime字段上排序：具有最低nextExecutionTime的TimerTask在队列[1]中(假设队列是非空的)。
     * 对于堆中的每个节点n,以及n,d,n.nextExecutionTime <= d.nextExecutionTime的每个后代。
     * 
     */
    private TimerTask[] queue = new TimerTask[128];

    /**
     * The number of tasks in the priority queue.  (The tasks are stored in
     * queue[1] up to queue[size]).
     * <p>
     *  优先级队列中的任务数。 (任务存储在队列[1]到队列[size])。
     * 
     */
    private int size = 0;

    /**
     * Returns the number of tasks currently on the queue.
     * <p>
     *  返回当前在队列上的任务数。
     * 
     */
    int size() {
        return size;
    }

    /**
     * Adds a new task to the priority queue.
     * <p>
     *  将新任务添加到优先级队列。
     * 
     */
    void add(TimerTask task) {
        // Grow backing store if necessary
        if (size + 1 == queue.length)
            queue = Arrays.copyOf(queue, 2*queue.length);

        queue[++size] = task;
        fixUp(size);
    }

    /**
     * Return the "head task" of the priority queue.  (The head task is an
     * task with the lowest nextExecutionTime.)
     * <p>
     *  返回优先级队列的"头部任务"。 (头任务是具有最低nextExecutionTime的任务。)
     * 
     */
    TimerTask getMin() {
        return queue[1];
    }

    /**
     * Return the ith task in the priority queue, where i ranges from 1 (the
     * head task, which is returned by getMin) to the number of tasks on the
     * queue, inclusive.
     * <p>
     *  返回优先级队列中的第i个任务,其中i的范围为1(由getMin返回的头任务)到队列上的任务数(含)。
     * 
     */
    TimerTask get(int i) {
        return queue[i];
    }

    /**
     * Remove the head task from the priority queue.
     * <p>
     *  从优先级队列中删除头任务。
     * 
     */
    void removeMin() {
        queue[1] = queue[size];
        queue[size--] = null;  // Drop extra reference to prevent memory leak
        fixDown(1);
    }

    /**
     * Removes the ith element from queue without regard for maintaining
     * the heap invariant.  Recall that queue is one-based, so
     * 1 <= i <= size.
     * <p>
     *  从队列中删除第i个元素,而不考虑保持堆不变。回想一下,队列是基于1的,所以1 <= i <= size。
     * 
     */
    void quickRemove(int i) {
        assert i <= size;

        queue[i] = queue[size];
        queue[size--] = null;  // Drop extra ref to prevent memory leak
    }

    /**
     * Sets the nextExecutionTime associated with the head task to the
     * specified value, and adjusts priority queue accordingly.
     * <p>
     * 将与头任务相关联的nextExecutionTime设置为指定的值,并相应地调整优先级队列。
     * 
     */
    void rescheduleMin(long newTime) {
        queue[1].nextExecutionTime = newTime;
        fixDown(1);
    }

    /**
     * Returns true if the priority queue contains no elements.
     * <p>
     *  如果优先级队列不包含元素,则返回true。
     * 
     */
    boolean isEmpty() {
        return size==0;
    }

    /**
     * Removes all elements from the priority queue.
     * <p>
     *  从优先级队列中删除所有元素。
     * 
     */
    void clear() {
        // Null out task references to prevent memory leak
        for (int i=1; i<=size; i++)
            queue[i] = null;

        size = 0;
    }

    /**
     * Establishes the heap invariant (described above) assuming the heap
     * satisfies the invariant except possibly for the leaf-node indexed by k
     * (which may have a nextExecutionTime less than its parent's).
     *
     * This method functions by "promoting" queue[k] up the hierarchy
     * (by swapping it with its parent) repeatedly until queue[k]'s
     * nextExecutionTime is greater than or equal to that of its parent.
     * <p>
     *  建立堆不变量(如上所述),假设堆满足不变量,除了可能用于由k索引的叶节点(其可能具有小于其父节点的nextExecutionTime)。
     * 
     *  这个方法通过重复地"提升"队列[k]在层次结构中(通过与其父进行交换)来运行,直到queue [k]的nextExecutionTime大于或等于其父值。
     * 
     */
    private void fixUp(int k) {
        while (k > 1) {
            int j = k >> 1;
            if (queue[j].nextExecutionTime <= queue[k].nextExecutionTime)
                break;
            TimerTask tmp = queue[j];  queue[j] = queue[k]; queue[k] = tmp;
            k = j;
        }
    }

    /**
     * Establishes the heap invariant (described above) in the subtree
     * rooted at k, which is assumed to satisfy the heap invariant except
     * possibly for node k itself (which may have a nextExecutionTime greater
     * than its children's).
     *
     * This method functions by "demoting" queue[k] down the hierarchy
     * (by swapping it with its smaller child) repeatedly until queue[k]'s
     * nextExecutionTime is less than or equal to those of its children.
     * <p>
     *  在以k为根的子树中建立堆不变量(如上所述),假设其满足堆不变量,除了可能对节点k本身(其可能具有大于其子节点的nextExecutionTime)。
     * 
     *  该方法通过重复地将层次结构中的"降级"队列[k](通过用其较小的子交换它)来运行,直到队列[k]的nextExecutionTime小于或等于其子节点的nextExecutionTime。
     * 
     */
    private void fixDown(int k) {
        int j;
        while ((j = k << 1) <= size && j > 0) {
            if (j < size &&
                queue[j].nextExecutionTime > queue[j+1].nextExecutionTime)
                j++; // j indexes smallest kid
            if (queue[k].nextExecutionTime <= queue[j].nextExecutionTime)
                break;
            TimerTask tmp = queue[j];  queue[j] = queue[k]; queue[k] = tmp;
            k = j;
        }
    }

    /**
     * Establishes the heap invariant (described above) in the entire tree,
     * assuming nothing about the order of the elements prior to the call.
     * <p>
     */
    void heapify() {
        for (int i = size/2; i >= 1; i--)
            fixDown(i);
    }
}
