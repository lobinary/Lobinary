/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2002, Oracle and/or its affiliates. All rights reserved.
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

package java.awt;

/**
 * An interface for events that know how to dispatch themselves.
 * By implementing this interface an event can be placed upon the event
 * queue and its <code>dispatch()</code> method will be called when the event
 * is dispatched, using the <code>EventDispatchThread</code>.
 * <p>
 * This is a very useful mechanism for avoiding deadlocks. If
 * a thread is executing in a critical section (i.e., it has entered
 * one or more monitors), calling other synchronized code may
 * cause deadlocks. To avoid the potential deadlocks, an
 * <code>ActiveEvent</code> can be created to run the second section of
 * code at later time. If there is contention on the monitor,
 * the second thread will simply block until the first thread
 * has finished its work and exited its monitors.
 * <p>
 * For security reasons, it is often desirable to use an <code>ActiveEvent</code>
 * to avoid calling untrusted code from a critical thread. For
 * instance, peer implementations can use this facility to avoid
 * making calls into user code from a system thread. Doing so avoids
 * potential deadlocks and denial-of-service attacks.
 *
 * <p>
 *  一个知道如何分派自己的事件的接口。
 * 通过实现这个接口,可以使用<code> EventDispatchThread </code>来将一个事件放置在事件队列上,并且在调度事件时调用<code> dispatch()</code>方法。
 * <p>
 *  这是一个非常有用的机制,用于避免死锁。如果线程在关键部分中执行(即它已经进入一个或多个监视器),则调用其它同步代码可能导致死锁。
 * 为了避免潜在的死锁,可以创建一个<code> ActiveEvent </code>,以便以后运行第二部分代码。
 * 如果在监视器上存在争用,则第二个线程将简单地阻塞,直到第一个线程完成其工作并退出其监视器。
 * <p>
 * 
 * @author  Timothy Prinzing
 * @since   1.2
 */
public interface ActiveEvent {

    /**
     * Dispatch the event to its target, listeners of the events source,
     * or do whatever it is this event is supposed to do.
     * <p>
     *  出于安全原因,通常希望使用<code> ActiveEvent </code>来避免从关键线程调用不受信任的代码。例如,对等实现可以使用此功能来避免从系统线程调用用户代码。
     * 这样做可以避免潜在的死锁和拒绝服务攻击。
     * 
     */
    public void dispatch();
}
