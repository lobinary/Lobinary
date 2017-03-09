/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.lang.ref;

import sun.misc.Cleaner;

/**
 * Abstract base class for reference objects.  This class defines the
 * operations common to all reference objects.  Because reference objects are
 * implemented in close cooperation with the garbage collector, this class may
 * not be subclassed directly.
 *
 * <p>
 *  引用对象的抽象基类。此类定义所有引用对象的公共操作。因为引用对象是与垃圾收集器密切合作实现的,所以这个类可能不会被直接子类化。
 * 
 * 
 * @author   Mark Reinhold
 * @since    1.2
 */

public abstract class Reference<T> {

    /* A Reference instance is in one of four possible internal states:
     *
     *     Active: Subject to special treatment by the garbage collector.  Some
     *     time after the collector detects that the reachability of the
     *     referent has changed to the appropriate state, it changes the
     *     instance's state to either Pending or Inactive, depending upon
     *     whether or not the instance was registered with a queue when it was
     *     created.  In the former case it also adds the instance to the
     *     pending-Reference list.  Newly-created instances are Active.
     *
     *     Pending: An element of the pending-Reference list, waiting to be
     *     enqueued by the Reference-handler thread.  Unregistered instances
     *     are never in this state.
     *
     *     Enqueued: An element of the queue with which the instance was
     *     registered when it was created.  When an instance is removed from
     *     its ReferenceQueue, it is made Inactive.  Unregistered instances are
     *     never in this state.
     *
     *     Inactive: Nothing more to do.  Once an instance becomes Inactive its
     *     state will never change again.
     *
     * The state is encoded in the queue and next fields as follows:
     *
     *     Active: queue = ReferenceQueue with which instance is registered, or
     *     ReferenceQueue.NULL if it was not registered with a queue; next =
     *     null.
     *
     *     Pending: queue = ReferenceQueue with which instance is registered;
     *     next = this
     *
     *     Enqueued: queue = ReferenceQueue.ENQUEUED; next = Following instance
     *     in queue, or this if at end of list.
     *
     *     Inactive: queue = ReferenceQueue.NULL; next = this.
     *
     * With this scheme the collector need only examine the next field in order
     * to determine whether a Reference instance requires special treatment: If
     * the next field is null then the instance is active; if it is non-null,
     * then the collector should treat the instance normally.
     *
     * To ensure that a concurrent collector can discover active Reference
     * objects without interfering with application threads that may apply
     * the enqueue() method to those objects, collectors should link
     * discovered objects through the discovered field. The discovered
     * field is also used for linking Reference objects in the pending list.
     * <p>
     *  活动：受到垃圾收集器的特殊处理。收集器检测到引用对象的可达性已更改为适当状态后,它会将实例的状态更改为"待处理"或"非活动",具体取决于实例是否已在队列创建时注册。
     * 在前一种情况下,它还将实例添加到pending-Reference列表。新创建的实例为"活动"。
     * 
     *  Pending：等待引用列表的一个元素,等待引用处理程序线程入队。未注册的实例从不处于此状态。
     * 
     *  Enqueued：实例在创建时注册的队列元素。当一个实例从它的ReferenceQueue中删除时,它被设置为Inactive。未注册的实例从不处于此状态。
     * 
     *  非活动：没什么可做。一旦实例变为非活动状态,其状态将永远不会再更改。
     * 
     *  状态在队列和下一个字段中编码如下：
     * 
     * Active：queue = ReferenceQueue与注册的实例,或ReferenceQueue.NULL如果没有注册队列; next = null。
     * 
     *  Pending：queue = ReferenceQueue,其中注册了实例; next = this
     * 
     *  入队：queue = ReferenceQueue.ENQUEUED; next =队列中的实例,或者如果在列表的末尾。
     * 
     *  不活动：queue = ReferenceQueue.NULL; next = this。
     * 
     *  使用此方案,收集器只需检查下一个字段,以确定引用实例是否需要特殊处理：如果下一个字段为空,则实例处于活动状态;如果它是非null,那么收集器应该正常处理实例。
     * 
     *  为了确保并发收集器可以发现活动引用对象而不干扰可能对这些对象应用enqueue()方法的应用程序线程,收集器应通过发现的字段链接发现的对象。发现的字段还用于链接挂起列表中的引用对象。
     * 
     */

    private T referent;         /* Treated specially by GC */

    volatile ReferenceQueue<? super T> queue;

    /* When active:   NULL
     *     pending:   this
     *    Enqueued:   next reference in queue (or this if last)
     *    Inactive:   this
     * <p>
     *  pending：this Enqueued：队列中的下一个引用(或者如果最后一个引用)Inactive：this
     * 
     */
    @SuppressWarnings("rawtypes")
    Reference next;

    /* When active:   next element in a discovered reference list maintained by GC (or this if last)
     *     pending:   next element in the pending list (or null if last)
     *   otherwise:   NULL
     * <p>
     *  pending：待处理列表中的next元素(如果最后则为null)否则为NULL
     * 
     */
    transient private Reference<T> discovered;  /* used by VM */


    /* Object used to synchronize with the garbage collector.  The collector
     * must acquire this lock at the beginning of each collection cycle.  It is
     * therefore critical that any code holding this lock complete as quickly
     * as possible, allocate no new objects, and avoid calling user code.
     * <p>
     *  必须在每个收集周期开始时获取此锁。因此,保持此锁的任何代码尽可能快地完成,不分配新对象,并避免调用用户代码是至关重要的。
     * 
     */
    static private class Lock { };
    private static Lock lock = new Lock();


    /* List of References waiting to be enqueued.  The collector adds
     * References to this list, while the Reference-handler thread removes
     * them.  This list is protected by the above lock object. The
     * list uses the discovered field to link its elements.
     * <p>
     * 引用此列表,而引用处理程序线程删除它们。此列表受上面的锁对象保护。该列表使用发现的字段链接其元素。
     * 
     */
    private static Reference<Object> pending = null;

    /* High-priority thread to enqueue pending References
    /* <p>
     */
    private static class ReferenceHandler extends Thread {

        ReferenceHandler(ThreadGroup g, String name) {
            super(g, name);
        }

        public void run() {
            for (;;) {
                Reference<Object> r;
                synchronized (lock) {
                    if (pending != null) {
                        r = pending;
                        pending = r.discovered;
                        r.discovered = null;
                    } else {
                        // The waiting on the lock may cause an OOME because it may try to allocate
                        // exception objects, so also catch OOME here to avoid silent exit of the
                        // reference handler thread.
                        //
                        // Explicitly define the order of the two exceptions we catch here
                        // when waiting for the lock.
                        //
                        // We do not want to try to potentially load the InterruptedException class
                        // (which would be done if this was its first use, and InterruptedException
                        // were checked first) in this situation.
                        //
                        // This may lead to the VM not ever trying to load the InterruptedException
                        // class again.
                        try {
                            try {
                                lock.wait();
                            } catch (OutOfMemoryError x) { }
                        } catch (InterruptedException x) { }
                        continue;
                    }
                }

                // Fast path for cleaners
                if (r instanceof Cleaner) {
                    ((Cleaner)r).clean();
                    continue;
                }

                ReferenceQueue<Object> q = r.queue;
                if (q != ReferenceQueue.NULL) q.enqueue(r);
            }
        }
    }

    static {
        ThreadGroup tg = Thread.currentThread().getThreadGroup();
        for (ThreadGroup tgn = tg;
             tgn != null;
             tg = tgn, tgn = tg.getParent());
        Thread handler = new ReferenceHandler(tg, "Reference Handler");
        /* If there were a special system-only priority greater than
         * MAX_PRIORITY, it would be used here
         * <p>
         *  MAX_PRIORITY,它会在这里使用
         * 
         */
        handler.setPriority(Thread.MAX_PRIORITY);
        handler.setDaemon(true);
        handler.start();
    }


    /* -- Referent accessor and setters -- */

    /**
     * Returns this reference object's referent.  If this reference object has
     * been cleared, either by the program or by the garbage collector, then
     * this method returns <code>null</code>.
     *
     * <p>
     *  返回此引用对象的引用。如果此引用对象已被程序或垃圾收集器清除,则此方法返回<code> null </code>。
     * 
     * 
     * @return   The object to which this reference refers, or
     *           <code>null</code> if this reference object has been cleared
     */
    public T get() {
        return this.referent;
    }

    /**
     * Clears this reference object.  Invoking this method will not cause this
     * object to be enqueued.
     *
     * <p> This method is invoked only by Java code; when the garbage collector
     * clears references it does so directly, without invoking this method.
     * <p>
     *  清除此引用对象。调用此方法不会导致此对象被排入队列。
     * 
     *  <p>此方法仅由Java代码调用;当垃圾收集器清除引用时,它直接这样做,而不调用此方法。
     * 
     */
    public void clear() {
        this.referent = null;
    }


    /* -- Queue operations -- */

    /**
     * Tells whether or not this reference object has been enqueued, either by
     * the program or by the garbage collector.  If this reference object was
     * not registered with a queue when it was created, then this method will
     * always return <code>false</code>.
     *
     * <p>
     *  告诉这个引用对象是否已经被程序或垃圾收集器入队。如果此引用对象在创建时未在队列中注册,则此方法将始终返回<code> false </code>。
     * 
     * 
     * @return   <code>true</code> if and only if this reference object has
     *           been enqueued
     */
    public boolean isEnqueued() {
        return (this.queue == ReferenceQueue.ENQUEUED);
    }

    /**
     * Adds this reference object to the queue with which it is registered,
     * if any.
     *
     * <p> This method is invoked only by Java code; when the garbage collector
     * enqueues references it does so directly, without invoking this method.
     *
     * <p>
     *  将此引用对象添加到与其注册的队列(如果有)。
     * 
     * 
     * @return   <code>true</code> if this reference object was successfully
     *           enqueued; <code>false</code> if it was already enqueued or if
     *           it was not registered with a queue when it was created
     */
    public boolean enqueue() {
        return this.queue.enqueue(this);
    }


    /* -- Constructors -- */

    Reference(T referent) {
        this(referent, null);
    }

    Reference(T referent, ReferenceQueue<? super T> queue) {
        this.referent = referent;
        this.queue = (queue == null) ? ReferenceQueue.NULL : queue;
    }

}
