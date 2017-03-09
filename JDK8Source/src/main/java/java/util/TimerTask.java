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

package java.util;

/**
 * A task that can be scheduled for one-time or repeated execution by a Timer.
 *
 * <p>
 *  可以由计时器一次性或重复执行的任务。
 * 
 * 
 * @author  Josh Bloch
 * @see     Timer
 * @since   1.3
 */

public abstract class TimerTask implements Runnable {
    /**
     * This object is used to control access to the TimerTask internals.
     * <p>
     *  此对象用于控制对TimerTask内部的访问。
     * 
     */
    final Object lock = new Object();

    /**
     * The state of this task, chosen from the constants below.
     * <p>
     *  这个任务的状态,从下面的常量中选择。
     * 
     */
    int state = VIRGIN;

    /**
     * This task has not yet been scheduled.
     * <p>
     *  此任务尚未安排。
     * 
     */
    static final int VIRGIN = 0;

    /**
     * This task is scheduled for execution.  If it is a non-repeating task,
     * it has not yet been executed.
     * <p>
     *  此任务计划执行。如果它是一个不重复的任务,它还没有被执行。
     * 
     */
    static final int SCHEDULED   = 1;

    /**
     * This non-repeating task has already executed (or is currently
     * executing) and has not been cancelled.
     * <p>
     *  此非重复任务已经执行(或正在执行),并且未被取消。
     * 
     */
    static final int EXECUTED    = 2;

    /**
     * This task has been cancelled (with a call to TimerTask.cancel).
     * <p>
     *  此任务已取消(调用TimerTask.cancel)。
     * 
     */
    static final int CANCELLED   = 3;

    /**
     * Next execution time for this task in the format returned by
     * System.currentTimeMillis, assuming this task is scheduled for execution.
     * For repeating tasks, this field is updated prior to each task execution.
     * <p>
     *  假设此任务已计划执行,则System.currentTimeMillis返回的格式的此任务的下一个执行时间。对于重复任务,此字段在每个任务执行之前更新。
     * 
     */
    long nextExecutionTime;

    /**
     * Period in milliseconds for repeating tasks.  A positive value indicates
     * fixed-rate execution.  A negative value indicates fixed-delay execution.
     * A value of 0 indicates a non-repeating task.
     * <p>
     *  重复任务的时间(以毫秒为单位)。正值表示固定速率执行。负值表示固定延迟执行。值0表示非重复任务。
     * 
     */
    long period = 0;

    /**
     * Creates a new timer task.
     * <p>
     *  创建新的计时器任务。
     * 
     */
    protected TimerTask() {
    }

    /**
     * The action to be performed by this timer task.
     * <p>
     *  此计时器任务要执行的操作。
     * 
     */
    public abstract void run();

    /**
     * Cancels this timer task.  If the task has been scheduled for one-time
     * execution and has not yet run, or has not yet been scheduled, it will
     * never run.  If the task has been scheduled for repeated execution, it
     * will never run again.  (If the task is running when this call occurs,
     * the task will run to completion, but will never run again.)
     *
     * <p>Note that calling this method from within the <tt>run</tt> method of
     * a repeating timer task absolutely guarantees that the timer task will
     * not run again.
     *
     * <p>This method may be called repeatedly; the second and subsequent
     * calls have no effect.
     *
     * <p>
     *  取消此计时器任务。如果任务已被调度为一次性执行,并且尚未运行或尚未被调度,则它将永远不会运行。如果任务已被调度为重复执行,则它将永远不会再次运行。
     *  (如果发生此调用时任务正在运行,任务将运行到完成,但不会再次运行)。
     * 
     * <p>请注意,从重复计时器任务的<tt> run </tt>方法中调用此方法绝对保证计时器任务不会再次运行。
     * 
     *  <p>此方法可以重复调用;第二个和后续呼叫没有效果。
     * 
     * 
     * @return true if this task is scheduled for one-time execution and has
     *         not yet run, or this task is scheduled for repeated execution.
     *         Returns false if the task was scheduled for one-time execution
     *         and has already run, or if the task was never scheduled, or if
     *         the task was already cancelled.  (Loosely speaking, this method
     *         returns <tt>true</tt> if it prevents one or more scheduled
     *         executions from taking place.)
     */
    public boolean cancel() {
        synchronized(lock) {
            boolean result = (state == SCHEDULED);
            state = CANCELLED;
            return result;
        }
    }

    /**
     * Returns the <i>scheduled</i> execution time of the most recent
     * <i>actual</i> execution of this task.  (If this method is invoked
     * while task execution is in progress, the return value is the scheduled
     * execution time of the ongoing task execution.)
     *
     * <p>This method is typically invoked from within a task's run method, to
     * determine whether the current execution of the task is sufficiently
     * timely to warrant performing the scheduled activity:
     * <pre>{@code
     *   public void run() {
     *       if (System.currentTimeMillis() - scheduledExecutionTime() >=
     *           MAX_TARDINESS)
     *               return;  // Too late; skip this execution.
     *       // Perform the task
     *   }
     * }</pre>
     * This method is typically <i>not</i> used in conjunction with
     * <i>fixed-delay execution</i> repeating tasks, as their scheduled
     * execution times are allowed to drift over time, and so are not terribly
     * significant.
     *
     * <p>
     * 
     * @return the time at which the most recent execution of this task was
     *         scheduled to occur, in the format returned by Date.getTime().
     *         The return value is undefined if the task has yet to commence
     *         its first execution.
     * @see Date#getTime()
     */
    public long scheduledExecutionTime() {
        synchronized(lock) {
            return (period < 0 ? nextExecutionTime + period
                               : nextExecutionTime - period);
        }
    }
}
