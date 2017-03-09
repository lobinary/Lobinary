/***** Lobxxx Translate Finished ******/
// NPCTE fix for bugId 4510777, esc 532372, MR October 2001
// file TaskServer.java created for this bug fix

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
 * This interface is implemented by objects that are able to execute
 * tasks. Whether the task is executed in the client thread or in another
 * thread depends on the TaskServer implementation.
 *
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  此接口由能够执行任务的对象实现。任务是在客户端线程中执行还是在另一个线程中执行取决于TaskServer实现。
 * 
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 * 
 * @see com.sun.jmx.snmp.tasks.Task
 *
 * @since 1.5
 **/
public interface TaskServer {
    /**
     * Submit a task to be executed.
     * Once a task is submitted, it is guaranteed that either
     * {@link com.sun.jmx.snmp.tasks.Task#run() task.run()} or
     * {@link com.sun.jmx.snmp.tasks.Task#cancel() task.cancel()} will be called.
     * <p>Whether the task is executed in the client thread (e.g.
     * <code>public void submitTask(Task task) { task.run(); }</code>) or in
     * another thread (e.g. <code>
     * public void submitTask(Task task) { new Thrad(task).start(); }</code>)
     * depends on the TaskServer implementation.
     * <p>
     *  提交要执行的任务。
     * 提交任务后,将确保{@link com.sun.jmx.snmp.tasks.Task#run()task.run()}或{@link com.sun.jmx.snmp.tasks.Task #cancel()task.cancel()}
     * 将被调用。
     *  提交要执行的任务。
     * 
     * @param task The task to be executed.
     **/
    public void submitTask(Task task);
}
