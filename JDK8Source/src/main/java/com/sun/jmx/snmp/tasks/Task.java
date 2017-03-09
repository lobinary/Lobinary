/***** Lobxxx Translate Finished ******/
// NPCTE fix for bugId 4510777, esc 532372, MR October 2001
// file Task.java created for this bug fix
/*
 * Copyright (c) 2002, 2003, Oracle and/or its affiliates. All rights reserved.
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
package com.sun.jmx.snmp.tasks;

/**
 * This interface is implemented by objects that can be executed
 * by a {@link com.sun.jmx.snmp.tasks.TaskServer}.
 * <p>A <code>Task</code> object implements two methods:
 * <ul><li><code>public void run(): </code> from
 *               {@link java.lang.Runnable}</li>
 * <ul>This method is called by the {@link com.sun.jmx.snmp.tasks.TaskServer}
 *     when the task is executed.</ul>
 * <li><code>public void cancel(): </code></li>
 * <ul>This method is called by the {@link com.sun.jmx.snmp.tasks.TaskServer}
 *     if the <code>TaskServer</code> is stopped before the
 *     <code>Task</code> is executed.</ul>
 * </ul>
 * An implementation of {@link com.sun.jmx.snmp.tasks.TaskServer} shall call
 * either <code>run()</code> or <code>cancel()</code>.
 * Whether the task is executed synchronously in the current
 * thread (when calling <code>TaskServer.submitTask()</code> or in a new
 * thread dedicated to the task, or in a daemon thread, depends on the
 * implementation of the <code>TaskServer</code> through which the task
 * is executed.
 * <p>The implementation of <code>Task</code> must not make any
 * assumption on the implementation of the <code>TaskServer</code> through
 * which it will be executed.
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  此接口由可由{@link com.sun.jmx.snmp.tasks.TaskServer}执行的对象实现。
 *  <p> <code> Task </code>对象实现两种方法：<ul> <li> <code> public void run()：</code> from {@link java.lang.Runnable}
 *  <ul>此方法由{@link com.sun.jmx.snmp.tasks.TaskServer}在执行任务时调用。
 *  此接口由可由{@link com.sun.jmx.snmp.tasks.TaskServer}执行的对象实现。
 * </ul> <li> <code> public void cancel()：</code> </li> <ul>此方法由{@link com.sun.jmx.snmp.tasks.TaskServer}
 * 调用,如果<code> TaskServer </code>在<code> Task </code> </ul>。
 *  此接口由可由{@link com.sun.jmx.snmp.tasks.TaskServer}执行的对象实现。
 * </ul>
 *  {@link com.sun.jmx.snmp.tasks.TaskServer}的实现应调用<code> run()</code>或<code> cancel()</code>。
 * 
 * @see com.sun.jmx.snmp.tasks.TaskServer
 *
 * @since 1.5
 **/
public interface Task extends Runnable {
    /**
     * Cancel the submitted task.
     * The implementation of this method is Task-implementation dependent.
     * It could involve some message logging, or even call the run() method.
     * Note that only one of run() or cancel() will be called - and exactly
     * one.
     * <p>
     * 是否在当前线程中同步执行任务(在调用<code> TaskServer.submitTask()</code>或在专用于任务的新线程中或在守护线程中时)取决于<code> <p> <code> Task
     *  </code>的实现不能对执行任务的<code> TaskServer </code>执行任何假设。
     *  {@link com.sun.jmx.snmp.tasks.TaskServer}的实现应调用<code> run()</code>或<code> cancel()</code>。 。
     * <p> <b>此API是Sun Microsystems内部API,如有更改,恕不另行通知。</b> </p>。
     * 
     **/
    public void cancel();
}
