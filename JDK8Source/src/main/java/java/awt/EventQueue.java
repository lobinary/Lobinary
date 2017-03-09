/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2014, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.event.*;

import java.awt.peer.ComponentPeer;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;

import java.security.AccessController;
import java.security.PrivilegedAction;

import java.util.EmptyStackException;

import sun.awt.*;
import sun.awt.dnd.SunDropTargetEvent;
import sun.util.logging.PlatformLogger;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.atomic.AtomicInteger;

import java.security.AccessControlContext;

import sun.misc.SharedSecrets;
import sun.misc.JavaSecurityAccess;

/**
 * <code>EventQueue</code> is a platform-independent class
 * that queues events, both from the underlying peer classes
 * and from trusted application classes.
 * <p>
 * It encapsulates asynchronous event dispatch machinery which
 * extracts events from the queue and dispatches them by calling
 * {@link #dispatchEvent(AWTEvent) dispatchEvent(AWTEvent)} method
 * on this <code>EventQueue</code> with the event to be dispatched
 * as an argument.  The particular behavior of this machinery is
 * implementation-dependent.  The only requirements are that events
 * which were actually enqueued to this queue (note that events
 * being posted to the <code>EventQueue</code> can be coalesced)
 * are dispatched:
 * <dl>
 *   <dt> Sequentially.
 *   <dd> That is, it is not permitted that several events from
 *        this queue are dispatched simultaneously.
 *   <dt> In the same order as they are enqueued.
 *   <dd> That is, if <code>AWTEvent</code>&nbsp;A is enqueued
 *        to the <code>EventQueue</code> before
 *        <code>AWTEvent</code>&nbsp;B then event B will not be
 *        dispatched before event A.
 * </dl>
 * <p>
 * Some browsers partition applets in different code bases into
 * separate contexts, and establish walls between these contexts.
 * In such a scenario, there will be one <code>EventQueue</code>
 * per context. Other browsers place all applets into the same
 * context, implying that there will be only a single, global
 * <code>EventQueue</code> for all applets. This behavior is
 * implementation-dependent.  Consult your browser's documentation
 * for more information.
 * <p>
 * For information on the threading issues of the event dispatch
 * machinery, see <a href="doc-files/AWTThreadIssues.html#Autoshutdown"
 * >AWT Threading Issues</a>.
 *
 * <p>
 *  <code> EventQueue </code>是一个与平台无关的类,它对来自底层对等体类和可信应用程序类的事件进行排队。
 * <p>
 *  它封装了异步事件分派机制,它从队列中提取事件并通过在这个<code> EventQueue </code>上调用{@link #dispatchEvent(AWTEvent)dispatchEvent(AWTEvent)}
 * 方法来调度它们,并将事件作为参数。
 * 这个机制的特定行为是依赖于实现的。唯一的要求是实际上入队列到这个队列的事件(注意事件发布到<code> EventQueue </code>可以合并)调度：。
 * <dl>
 *  <dt>按顺序。 <dd>也就是说,不允许同时调度来自此队列的几个事件。 <dt>按照它们排队的顺序。
 *  <dd>也就是说,如果<code> AWTEvent </code>&amp; A在<code> AWTEvent </code>&nbsp; B之前入队到<code> EventQueue </code>
 * ,那么事件B将不会在事件A.。
 *  <dt>按顺序。 <dd>也就是说,不允许同时调度来自此队列的几个事件。 <dt>按照它们排队的顺序。
 * </dl>
 * <p>
 * 一些浏览器将不同代码库中的applet分割成单独的上下文,并在这些上下文之间建立壁。在这种情况下,每个上下文将有一个<code> EventQueue </code>。
 * 其他浏览器将所有applet放在同一上下文中,这意味着对于所有applet只有一个,全局的<code> EventQueue </code>。此行为是实现相关的。有关详细信息,请参阅浏览器的文档。
 * <p>
 *  有关事件调度​​机制的线程问题的信息,请参见<a href="doc-files/AWTThreadIssues.html#Autoshutdown"> AWT线程问题</a>。
 * 
 * 
 * @author Thomas Ball
 * @author Fred Ecks
 * @author David Mendenhall
 *
 * @since       1.1
 */
public class EventQueue {
    private static final AtomicInteger threadInitNumber = new AtomicInteger(0);

    private static final int LOW_PRIORITY = 0;
    private static final int NORM_PRIORITY = 1;
    private static final int HIGH_PRIORITY = 2;
    private static final int ULTIMATE_PRIORITY = 3;

    private static final int NUM_PRIORITIES = ULTIMATE_PRIORITY + 1;

    /*
     * We maintain one Queue for each priority that the EventQueue supports.
     * That is, the EventQueue object is actually implemented as
     * NUM_PRIORITIES queues and all Events on a particular internal Queue
     * have identical priority. Events are pulled off the EventQueue starting
     * with the Queue of highest priority. We progress in decreasing order
     * across all Queues.
     * <p>
     *  我们为EventQueue支持的每个优先级维护一个队列。也就是说,Eve​​ntQueue对象实际上实现为NUM_PRIORITIES个队列,特定内部队列上的所有事件具有相同的优先级。
     * 事件被从最高优先级的队列开始的EventQueue。我们在所有队列中以递减顺序前进。
     * 
     */
    private Queue[] queues = new Queue[NUM_PRIORITIES];

    /*
     * The next EventQueue on the stack, or null if this EventQueue is
     * on the top of the stack.  If nextQueue is non-null, requests to post
     * an event are forwarded to nextQueue.
     * <p>
     *  堆栈上的下一个EventQueue,如果此EventQueue在堆栈的顶部,则为null。如果nextQueue不为null,则发送事件的请求将转发到nextQueue。
     * 
     */
    private EventQueue nextQueue;

    /*
     * The previous EventQueue on the stack, or null if this is the
     * "base" EventQueue.
     * <p>
     *  堆栈上的前一个EventQueue,如果这是"基本"EventQueue,则为null。
     * 
     */
    private EventQueue previousQueue;

    /*
     * A single lock to synchronize the push()/pop() and related operations with
     * all the EventQueues from the AppContext. Synchronization on any particular
     * event queue(s) is not enough: we should lock the whole stack.
     * <p>
     *  单个锁同步push()/ pop()和相关的操作与所有的EventQueues从AppContext。任何特定事件队列上的同步是不够的：我们应该锁定整个堆栈。
     * 
     */
    private final Lock pushPopLock;
    private final Condition pushPopCond;

    /*
     * Dummy runnable to wake up EDT from getNextEvent() after
     push/pop is performed
     * <p>
     * Dummy runnable在push / pop执行后从getNextEvent()唤醒EDT
     * 
     */
    private final static Runnable dummyRunnable = new Runnable() {
        public void run() {
        }
    };

    private EventDispatchThread dispatchThread;

    private final ThreadGroup threadGroup =
        Thread.currentThread().getThreadGroup();
    private final ClassLoader classLoader =
        Thread.currentThread().getContextClassLoader();

    /*
     * The time stamp of the last dispatched InputEvent or ActionEvent.
     * <p>
     *  最后一个分派的InputEvent或ActionEvent的时间戳。
     * 
     */
    private long mostRecentEventTime = System.currentTimeMillis();

    /*
     * The time stamp of the last KeyEvent .
     * <p>
     *  最后一个KeyEvent的时间戳。
     * 
     */
    private long mostRecentKeyEventTime = System.currentTimeMillis();

    /**
     * The modifiers field of the current event, if the current event is an
     * InputEvent or ActionEvent.
     * <p>
     *  当前事件的修饰符字段,如果当前事件是InputEvent或ActionEvent。
     * 
     */
    private WeakReference<AWTEvent> currentEvent;

    /*
     * Non-zero if a thread is waiting in getNextEvent(int) for an event of
     * a particular ID to be posted to the queue.
     * <p>
     *  非零,如果线程在getNextEvent(int)中等待特定ID的事件发布到队列。
     * 
     */
    private volatile int waitForID;

    /*
     * AppContext corresponding to the queue.
     * <p>
     *  AppContext对应的队列。
     * 
     */
    private final AppContext appContext;

    private final String name = "AWT-EventQueue-" + threadInitNumber.getAndIncrement();

    private FwDispatcher fwDispatcher;

    private static volatile PlatformLogger eventLog;

    private static final PlatformLogger getEventLog() {
        if(eventLog == null) {
            eventLog = PlatformLogger.getLogger("java.awt.event.EventQueue");
        }
        return eventLog;
    }

    static {
        AWTAccessor.setEventQueueAccessor(
            new AWTAccessor.EventQueueAccessor() {
                public Thread getDispatchThread(EventQueue eventQueue) {
                    return eventQueue.getDispatchThread();
                }
                public boolean isDispatchThreadImpl(EventQueue eventQueue) {
                    return eventQueue.isDispatchThreadImpl();
                }
                public void removeSourceEvents(EventQueue eventQueue,
                                               Object source,
                                               boolean removeAllEvents)
                {
                    eventQueue.removeSourceEvents(source, removeAllEvents);
                }
                public boolean noEvents(EventQueue eventQueue) {
                    return eventQueue.noEvents();
                }
                public void wakeup(EventQueue eventQueue, boolean isShutdown) {
                    eventQueue.wakeup(isShutdown);
                }
                public void invokeAndWait(Object source, Runnable r)
                    throws InterruptedException, InvocationTargetException
                {
                    EventQueue.invokeAndWait(source, r);
                }
                public void setFwDispatcher(EventQueue eventQueue,
                                            FwDispatcher dispatcher) {
                    eventQueue.setFwDispatcher(dispatcher);
                }

                @Override
                public long getMostRecentEventTime(EventQueue eventQueue) {
                    return eventQueue.getMostRecentEventTimeImpl();
                }
            });
    }

    public EventQueue() {
        for (int i = 0; i < NUM_PRIORITIES; i++) {
            queues[i] = new Queue();
        }
        /*
         * NOTE: if you ever have to start the associated event dispatch
         * thread at this point, be aware of the following problem:
         * If this EventQueue instance is created in
         * SunToolkit.createNewAppContext() the started dispatch thread
         * may call AppContext.getAppContext() before createNewAppContext()
         * completes thus causing mess in thread group to appcontext mapping.
         * <p>
         *  注意：如果此时必须启动关联的事件分派线程,请注意以下问题：如果在SunToolkit.createNewAppContext()中创建此EventQueue实例,则启动的分派线程可以在createNe
         * wAppContext()之前调用AppContext.getAppContext )完成,从而导致线程组中的混乱到appcontext映射。
         * 
         */

        appContext = AppContext.getAppContext();
        pushPopLock = (Lock)appContext.get(AppContext.EVENT_QUEUE_LOCK_KEY);
        pushPopCond = (Condition)appContext.get(AppContext.EVENT_QUEUE_COND_KEY);
    }

    /**
     * Posts a 1.1-style event to the <code>EventQueue</code>.
     * If there is an existing event on the queue with the same ID
     * and event source, the source <code>Component</code>'s
     * <code>coalesceEvents</code> method will be called.
     *
     * <p>
     *  将1.1样式的事件发布到<code> EventQueue </code>。
     * 如果队列上存在具有相同ID和事件源的现有事件,则会调用源<code> Component </code>的<code> coalesceEvents </code>方法。
     * 
     * 
     * @param theEvent an instance of <code>java.awt.AWTEvent</code>,
     *          or a subclass of it
     * @throws NullPointerException if <code>theEvent</code> is <code>null</code>
     */
    public void postEvent(AWTEvent theEvent) {
        SunToolkit.flushPendingEvents(appContext);
        postEventPrivate(theEvent);
    }

    /**
     * Posts a 1.1-style event to the <code>EventQueue</code>.
     * If there is an existing event on the queue with the same ID
     * and event source, the source <code>Component</code>'s
     * <code>coalesceEvents</code> method will be called.
     *
     * <p>
     *  将1.1样式的事件发布到<code> EventQueue </code>。
     * 如果队列上存在具有相同ID和事件源的现有事件,则会调用源<code> Component </code>的<code> coalesceEvents </code>方法。
     * 
     * 
     * @param theEvent an instance of <code>java.awt.AWTEvent</code>,
     *          or a subclass of it
     */
    private final void postEventPrivate(AWTEvent theEvent) {
        theEvent.isPosted = true;
        pushPopLock.lock();
        try {
            if (nextQueue != null) {
                // Forward the event to the top of EventQueue stack
                nextQueue.postEventPrivate(theEvent);
                return;
            }
            if (dispatchThread == null) {
                if (theEvent.getSource() == AWTAutoShutdown.getInstance()) {
                    return;
                } else {
                    initDispatchThread();
                }
            }
            postEvent(theEvent, getPriority(theEvent));
        } finally {
            pushPopLock.unlock();
        }
    }

    private static int getPriority(AWTEvent theEvent) {
        if (theEvent instanceof PeerEvent) {
            PeerEvent peerEvent = (PeerEvent)theEvent;
            if ((peerEvent.getFlags() & PeerEvent.ULTIMATE_PRIORITY_EVENT) != 0) {
                return ULTIMATE_PRIORITY;
            }
            if ((peerEvent.getFlags() & PeerEvent.PRIORITY_EVENT) != 0) {
                return HIGH_PRIORITY;
            }
            if ((peerEvent.getFlags() & PeerEvent.LOW_PRIORITY_EVENT) != 0) {
                return LOW_PRIORITY;
            }
        }
        int id = theEvent.getID();
        if ((id >= PaintEvent.PAINT_FIRST) && (id <= PaintEvent.PAINT_LAST)) {
            return LOW_PRIORITY;
        }
        return NORM_PRIORITY;
    }

    /**
     * Posts the event to the internal Queue of specified priority,
     * coalescing as appropriate.
     *
     * <p>
     *  将事件发布到指定优先级的内部队列,根据情况合并。
     * 
     * 
     * @param theEvent an instance of <code>java.awt.AWTEvent</code>,
     *          or a subclass of it
     * @param priority  the desired priority of the event
     */
    private void postEvent(AWTEvent theEvent, int priority) {
        if (coalesceEvent(theEvent, priority)) {
            return;
        }

        EventQueueItem newItem = new EventQueueItem(theEvent);

        cacheEQItem(newItem);

        boolean notifyID = (theEvent.getID() == this.waitForID);

        if (queues[priority].head == null) {
            boolean shouldNotify = noEvents();
            queues[priority].head = queues[priority].tail = newItem;

            if (shouldNotify) {
                if (theEvent.getSource() != AWTAutoShutdown.getInstance()) {
                    AWTAutoShutdown.getInstance().notifyThreadBusy(dispatchThread);
                }
                pushPopCond.signalAll();
            } else if (notifyID) {
                pushPopCond.signalAll();
            }
        } else {
            // The event was not coalesced or has non-Component source.
            // Insert it at the end of the appropriate Queue.
            queues[priority].tail.next = newItem;
            queues[priority].tail = newItem;
            if (notifyID) {
                pushPopCond.signalAll();
            }
        }
    }

    private boolean coalescePaintEvent(PaintEvent e) {
        ComponentPeer sourcePeer = ((Component)e.getSource()).peer;
        if (sourcePeer != null) {
            sourcePeer.coalescePaintEvent(e);
        }
        EventQueueItem[] cache = ((Component)e.getSource()).eventCache;
        if (cache == null) {
            return false;
        }
        int index = eventToCacheIndex(e);

        if (index != -1 && cache[index] != null) {
            PaintEvent merged = mergePaintEvents(e, (PaintEvent)cache[index].event);
            if (merged != null) {
                cache[index].event = merged;
                return true;
            }
        }
        return false;
    }

    private PaintEvent mergePaintEvents(PaintEvent a, PaintEvent b) {
        Rectangle aRect = a.getUpdateRect();
        Rectangle bRect = b.getUpdateRect();
        if (bRect.contains(aRect)) {
            return b;
        }
        if (aRect.contains(bRect)) {
            return a;
        }
        return null;
    }

    private boolean coalesceMouseEvent(MouseEvent e) {
        EventQueueItem[] cache = ((Component)e.getSource()).eventCache;
        if (cache == null) {
            return false;
        }
        int index = eventToCacheIndex(e);
        if (index != -1 && cache[index] != null) {
            cache[index].event = e;
            return true;
        }
        return false;
    }

    private boolean coalescePeerEvent(PeerEvent e) {
        EventQueueItem[] cache = ((Component)e.getSource()).eventCache;
        if (cache == null) {
            return false;
        }
        int index = eventToCacheIndex(e);
        if (index != -1 && cache[index] != null) {
            e = e.coalesceEvents((PeerEvent)cache[index].event);
            if (e != null) {
                cache[index].event = e;
                return true;
            } else {
                cache[index] = null;
            }
        }
        return false;
    }

    /*
     * Should avoid of calling this method by any means
     * as it's working time is dependant on EQ length.
     * In the wors case this method alone can slow down the entire application
     * 10 times by stalling the Event processing.
     * Only here by backward compatibility reasons.
     * <p>
     * 应避免通过任何方式调用此方法,因为它的工作时间取决于EQ长度。在糟糕的情况下,这种方法单独可以通过停止事件处理减慢整个应用程序10次。只有在这里通过向后兼容的原因。
     * 
     */
    private boolean coalesceOtherEvent(AWTEvent e, int priority) {
        int id = e.getID();
        Component source = (Component)e.getSource();
        for (EventQueueItem entry = queues[priority].head;
            entry != null; entry = entry.next)
        {
            // Give Component.coalesceEvents a chance
            if (entry.event.getSource() == source && entry.event.getID() == id) {
                AWTEvent coalescedEvent = source.coalesceEvents(
                    entry.event, e);
                if (coalescedEvent != null) {
                    entry.event = coalescedEvent;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean coalesceEvent(AWTEvent e, int priority) {
        if (!(e.getSource() instanceof Component)) {
            return false;
        }
        if (e instanceof PeerEvent) {
            return coalescePeerEvent((PeerEvent)e);
        }
        // The worst case
        if (((Component)e.getSource()).isCoalescingEnabled()
            && coalesceOtherEvent(e, priority))
        {
            return true;
        }
        if (e instanceof PaintEvent) {
            return coalescePaintEvent((PaintEvent)e);
        }
        if (e instanceof MouseEvent) {
            return coalesceMouseEvent((MouseEvent)e);
        }
        return false;
    }

    private void cacheEQItem(EventQueueItem entry) {
        int index = eventToCacheIndex(entry.event);
        if (index != -1 && entry.event.getSource() instanceof Component) {
            Component source = (Component)entry.event.getSource();
            if (source.eventCache == null) {
                source.eventCache = new EventQueueItem[CACHE_LENGTH];
            }
            source.eventCache[index] = entry;
        }
    }

    private void uncacheEQItem(EventQueueItem entry) {
        int index = eventToCacheIndex(entry.event);
        if (index != -1 && entry.event.getSource() instanceof Component) {
            Component source = (Component)entry.event.getSource();
            if (source.eventCache == null) {
                return;
            }
            source.eventCache[index] = null;
        }
    }

    private static final int PAINT = 0;
    private static final int UPDATE = 1;
    private static final int MOVE = 2;
    private static final int DRAG = 3;
    private static final int PEER = 4;
    private static final int CACHE_LENGTH = 5;

    private static int eventToCacheIndex(AWTEvent e) {
        switch(e.getID()) {
        case PaintEvent.PAINT:
            return PAINT;
        case PaintEvent.UPDATE:
            return UPDATE;
        case MouseEvent.MOUSE_MOVED:
            return MOVE;
        case MouseEvent.MOUSE_DRAGGED:
            // Return -1 for SunDropTargetEvent since they are usually synchronous
            // and we don't want to skip them by coalescing with MouseEvent or other drag events
            return e instanceof SunDropTargetEvent ? -1 : DRAG;
        default:
            return e instanceof PeerEvent ? PEER : -1;
        }
    }

    /**
     * Returns whether an event is pending on any of the separate
     * Queues.
     * <p>
     *  返回任何单独队列中的事件是否正在等待。
     * 
     * 
     * @return whether an event is pending on any of the separate Queues
     */
    private boolean noEvents() {
        for (int i = 0; i < NUM_PRIORITIES; i++) {
            if (queues[i].head != null) {
                return false;
            }
        }

        return true;
    }

    /**
     * Removes an event from the <code>EventQueue</code> and
     * returns it.  This method will block until an event has
     * been posted by another thread.
     * <p>
     *  从<code> EventQueue </code>中删除事件并返回。此方法将阻塞,直到另一个线程发布了一个事件。
     * 
     * 
     * @return the next <code>AWTEvent</code>
     * @exception InterruptedException
     *            if any thread has interrupted this thread
     */
    public AWTEvent getNextEvent() throws InterruptedException {
        do {
            /*
             * SunToolkit.flushPendingEvents must be called outside
             * of the synchronized block to avoid deadlock when
             * event queues are nested with push()/pop().
             * <p>
             *  SunToolkit.flushPendingEvents必须在同步块之外调用,以避免在事件队列嵌套push()/ pop()时发生死锁。
             * 
             */
            SunToolkit.flushPendingEvents(appContext);
            pushPopLock.lock();
            try {
                AWTEvent event = getNextEventPrivate();
                if (event != null) {
                    return event;
                }
                AWTAutoShutdown.getInstance().notifyThreadFree(dispatchThread);
                pushPopCond.await();
            } finally {
                pushPopLock.unlock();
            }
        } while(true);
    }

    /*
     * Must be called under the lock. Doesn't call flushPendingEvents()
     * <p>
     *  必须在锁下面调用。不调用flushPendingEvents()
     * 
     */
    AWTEvent getNextEventPrivate() throws InterruptedException {
        for (int i = NUM_PRIORITIES - 1; i >= 0; i--) {
            if (queues[i].head != null) {
                EventQueueItem entry = queues[i].head;
                queues[i].head = entry.next;
                if (entry.next == null) {
                    queues[i].tail = null;
                }
                uncacheEQItem(entry);
                return entry.event;
            }
        }
        return null;
    }

    AWTEvent getNextEvent(int id) throws InterruptedException {
        do {
            /*
             * SunToolkit.flushPendingEvents must be called outside
             * of the synchronized block to avoid deadlock when
             * event queues are nested with push()/pop().
             * <p>
             *  SunToolkit.flushPendingEvents必须在同步块之外调用,以避免在事件队列嵌套push()/ pop()时发生死锁。
             * 
             */
            SunToolkit.flushPendingEvents(appContext);
            pushPopLock.lock();
            try {
                for (int i = 0; i < NUM_PRIORITIES; i++) {
                    for (EventQueueItem entry = queues[i].head, prev = null;
                         entry != null; prev = entry, entry = entry.next)
                    {
                        if (entry.event.getID() == id) {
                            if (prev == null) {
                                queues[i].head = entry.next;
                            } else {
                                prev.next = entry.next;
                            }
                            if (queues[i].tail == entry) {
                                queues[i].tail = prev;
                            }
                            uncacheEQItem(entry);
                            return entry.event;
                        }
                    }
                }
                waitForID = id;
                pushPopCond.await();
                waitForID = 0;
            } finally {
                pushPopLock.unlock();
            }
        } while(true);
    }

    /**
     * Returns the first event on the <code>EventQueue</code>
     * without removing it.
     * <p>
     *  返回<code> EventQueue </code>上的第一个事件,而不删除它。
     * 
     * 
     * @return the first event
     */
    public AWTEvent peekEvent() {
        pushPopLock.lock();
        try {
            for (int i = NUM_PRIORITIES - 1; i >= 0; i--) {
                if (queues[i].head != null) {
                    return queues[i].head.event;
                }
            }
        } finally {
            pushPopLock.unlock();
        }

        return null;
    }

    /**
     * Returns the first event with the specified id, if any.
     * <p>
     *  返回具有指定ID的第一个事件(如果有)。
     * 
     * 
     * @param id the id of the type of event desired
     * @return the first event of the specified id or <code>null</code>
     *    if there is no such event
     */
    public AWTEvent peekEvent(int id) {
        pushPopLock.lock();
        try {
            for (int i = NUM_PRIORITIES - 1; i >= 0; i--) {
                EventQueueItem q = queues[i].head;
                for (; q != null; q = q.next) {
                    if (q.event.getID() == id) {
                        return q.event;
                    }
                }
            }
        } finally {
            pushPopLock.unlock();
        }

        return null;
    }

    private static final JavaSecurityAccess javaSecurityAccess =
        SharedSecrets.getJavaSecurityAccess();

    /**
     * Dispatches an event. The manner in which the event is
     * dispatched depends upon the type of the event and the
     * type of the event's source object:
     *
     * <table border=1 summary="Event types, source types, and dispatch methods">
     * <tr>
     *     <th>Event Type</th>
     *     <th>Source Type</th>
     *     <th>Dispatched To</th>
     * </tr>
     * <tr>
     *     <td>ActiveEvent</td>
     *     <td>Any</td>
     *     <td>event.dispatch()</td>
     * </tr>
     * <tr>
     *     <td>Other</td>
     *     <td>Component</td>
     *     <td>source.dispatchEvent(AWTEvent)</td>
     * </tr>
     * <tr>
     *     <td>Other</td>
     *     <td>MenuComponent</td>
     *     <td>source.dispatchEvent(AWTEvent)</td>
     * </tr>
     * <tr>
     *     <td>Other</td>
     *     <td>Other</td>
     *     <td>No action (ignored)</td>
     * </tr>
     * </table>
     * <p>
     * <p>
     *  分派事件。事件的分派方式取决于事件的类型和事件的源对象的类型：
     * 
     * <table border=1 summary="Event types, source types, and dispatch methods">
     * <tr>
     *  <th>事件类型</th> <th>源类型</th> <th>已分派到</th>
     * </tr>
     * <tr>
     *  <td> ActiveEvent </td> <td>任何</td> <td> event.dispatch()</td>
     * </tr>
     * <tr>
     *  <td>其他</td> <td>组件</td> <td> source.dispatchEvent(AWTEvent)</td>
     * </tr>
     * <tr>
     *  <td>其他</td> <td> MenuComponent </td> <td> source.dispatchEvent(AWTEvent)</td>
     * </tr>
     * <tr>
     *  <td>其他</td> <td>其他</td> <td>无操作(忽略)</td>
     * </tr>
     * </table>
     * <p>
     * 
     * @param event an instance of <code>java.awt.AWTEvent</code>,
     *          or a subclass of it
     * @throws NullPointerException if <code>event</code> is <code>null</code>
     * @since           1.2
     */
    protected void dispatchEvent(final AWTEvent event) {
        final Object src = event.getSource();
        final PrivilegedAction<Void> action = new PrivilegedAction<Void>() {
            public Void run() {
                // In case fwDispatcher is installed and we're already on the
                // dispatch thread (e.g. performing DefaultKeyboardFocusManager.sendMessage),
                // dispatch the event straight away.
                if (fwDispatcher == null || isDispatchThreadImpl()) {
                    dispatchEventImpl(event, src);
                } else {
                    fwDispatcher.scheduleDispatch(new Runnable() {
                        @Override
                        public void run() {
                            dispatchEventImpl(event, src);
                        }
                    });
                }
                return null;
            }
        };

        final AccessControlContext stack = AccessController.getContext();
        final AccessControlContext srcAcc = getAccessControlContextFrom(src);
        final AccessControlContext eventAcc = event.getAccessControlContext();
        if (srcAcc == null) {
            javaSecurityAccess.doIntersectionPrivilege(action, stack, eventAcc);
        } else {
            javaSecurityAccess.doIntersectionPrivilege(
                new PrivilegedAction<Void>() {
                    public Void run() {
                        javaSecurityAccess.doIntersectionPrivilege(action, eventAcc);
                        return null;
                    }
                }, stack, srcAcc);
        }
    }

    private static AccessControlContext getAccessControlContextFrom(Object src) {
        return src instanceof Component ?
            ((Component)src).getAccessControlContext() :
            src instanceof MenuComponent ?
                ((MenuComponent)src).getAccessControlContext() :
                src instanceof TrayIcon ?
                    ((TrayIcon)src).getAccessControlContext() :
                    null;
    }

    /**
     * Called from dispatchEvent() under a correct AccessControlContext
     * <p>
     * 从dispatchEvent()在正确的AccessControlContext下调用
     * 
     */
    private void dispatchEventImpl(final AWTEvent event, final Object src) {
        event.isPosted = true;
        if (event instanceof ActiveEvent) {
            // This could become the sole method of dispatching in time.
            setCurrentEventAndMostRecentTimeImpl(event);
            ((ActiveEvent)event).dispatch();
        } else if (src instanceof Component) {
            ((Component)src).dispatchEvent(event);
            event.dispatched();
        } else if (src instanceof MenuComponent) {
            ((MenuComponent)src).dispatchEvent(event);
        } else if (src instanceof TrayIcon) {
            ((TrayIcon)src).dispatchEvent(event);
        } else if (src instanceof AWTAutoShutdown) {
            if (noEvents()) {
                dispatchThread.stopDispatching();
            }
        } else {
            if (getEventLog().isLoggable(PlatformLogger.Level.FINE)) {
                getEventLog().fine("Unable to dispatch event: " + event);
            }
        }
    }

    /**
     * Returns the timestamp of the most recent event that had a timestamp, and
     * that was dispatched from the <code>EventQueue</code> associated with the
     * calling thread. If an event with a timestamp is currently being
     * dispatched, its timestamp will be returned. If no events have yet
     * been dispatched, the EventQueue's initialization time will be
     * returned instead.In the current version of
     * the JDK, only <code>InputEvent</code>s,
     * <code>ActionEvent</code>s, and <code>InvocationEvent</code>s have
     * timestamps; however, future versions of the JDK may add timestamps to
     * additional event types. Note that this method should only be invoked
     * from an application's {@link #isDispatchThread event dispatching thread}.
     * If this method is
     * invoked from another thread, the current system time (as reported by
     * <code>System.currentTimeMillis()</code>) will be returned instead.
     *
     * <p>
     *  返回具有时间戳的最近事件的时间戳,该事件从与调用线程相关联的<code> EventQueue </code>分派。如果当前正在分派具有时间戳的事件,则将返回其时间戳。
     * 如果没有事件被分派,则将返回EventQueue的初始化时间。
     * 在当前版本的JDK中,只有<code> InputEvent </code>,<code> ActionEvent </code> InvocationEvent </code>有时间戳;但是,JDK的
     * 未来版本可能会向其他事件类型添加时间戳。
     * 如果没有事件被分派,则将返回EventQueue的初始化时间。请注意,此方法只应从应用程序的{@link #isDispatchThread事件分派线程}调用。
     * 如果从另一个线程调用此方法,则将返回当前系统时间(由<code> System.currentTimeMillis()</code>)报告。
     * 
     * 
     * @return the timestamp of the last <code>InputEvent</code>,
     *         <code>ActionEvent</code>, or <code>InvocationEvent</code> to be
     *         dispatched, or <code>System.currentTimeMillis()</code> if this
     *         method is invoked on a thread other than an event dispatching
     *         thread
     * @see java.awt.event.InputEvent#getWhen
     * @see java.awt.event.ActionEvent#getWhen
     * @see java.awt.event.InvocationEvent#getWhen
     * @see #isDispatchThread
     *
     * @since 1.4
     */
    public static long getMostRecentEventTime() {
        return Toolkit.getEventQueue().getMostRecentEventTimeImpl();
    }
    private long getMostRecentEventTimeImpl() {
        pushPopLock.lock();
        try {
            return (Thread.currentThread() == dispatchThread)
                ? mostRecentEventTime
                : System.currentTimeMillis();
        } finally {
            pushPopLock.unlock();
        }
    }

    /**
    /* <p>
    /* 
     * @return most recent event time on all threads.
     */
    long getMostRecentEventTimeEx() {
        pushPopLock.lock();
        try {
            return mostRecentEventTime;
        } finally {
            pushPopLock.unlock();
        }
    }

    /**
     * Returns the the event currently being dispatched by the
     * <code>EventQueue</code> associated with the calling thread. This is
     * useful if a method needs access to the event, but was not designed to
     * receive a reference to it as an argument. Note that this method should
     * only be invoked from an application's event dispatching thread. If this
     * method is invoked from another thread, null will be returned.
     *
     * <p>
     *  返回与调用线程相关联的<code> EventQueue </code>当前调度的事件。如果方法需要访问事件,但不是设计为接收对它的引用作为参数,这是有用的。
     * 注意,这个方法只能从应用程序的事件分派线程调用​​。如果从另一个线程调用此方法,将返回null。
     * 
     * 
     * @return the event currently being dispatched, or null if this method is
     *         invoked on a thread other than an event dispatching thread
     * @since 1.4
     */
    public static AWTEvent getCurrentEvent() {
        return Toolkit.getEventQueue().getCurrentEventImpl();
    }
    private AWTEvent getCurrentEventImpl() {
        pushPopLock.lock();
        try {
                return (Thread.currentThread() == dispatchThread)
                ? currentEvent.get()
                : null;
        } finally {
            pushPopLock.unlock();
        }
    }

    /**
     * Replaces the existing <code>EventQueue</code> with the specified one.
     * Any pending events are transferred to the new <code>EventQueue</code>
     * for processing by it.
     *
     * <p>
     *  用指定的<code> EventQueue </code>替换现有的<code> EventQueue </code>。
     * 任何挂起的事件都会传输到新的<code> EventQueue </code>中,以便进行处理。
     * 
     * 
     * @param newEventQueue an <code>EventQueue</code>
     *          (or subclass thereof) instance to be use
     * @see      java.awt.EventQueue#pop
     * @throws NullPointerException if <code>newEventQueue</code> is <code>null</code>
     * @since           1.2
     */
    public void push(EventQueue newEventQueue) {
        if (getEventLog().isLoggable(PlatformLogger.Level.FINE)) {
            getEventLog().fine("EventQueue.push(" + newEventQueue + ")");
        }

        pushPopLock.lock();
        try {
            EventQueue topQueue = this;
            while (topQueue.nextQueue != null) {
                topQueue = topQueue.nextQueue;
            }
            if (topQueue.fwDispatcher != null) {
                throw new RuntimeException("push() to queue with fwDispatcher");
            }
            if ((topQueue.dispatchThread != null) &&
                (topQueue.dispatchThread.getEventQueue() == this))
            {
                newEventQueue.dispatchThread = topQueue.dispatchThread;
                topQueue.dispatchThread.setEventQueue(newEventQueue);
            }

            // Transfer all events forward to new EventQueue.
            while (topQueue.peekEvent() != null) {
                try {
                    // Use getNextEventPrivate() as it doesn't call flushPendingEvents()
                    newEventQueue.postEventPrivate(topQueue.getNextEventPrivate());
                } catch (InterruptedException ie) {
                    if (getEventLog().isLoggable(PlatformLogger.Level.FINE)) {
                        getEventLog().fine("Interrupted push", ie);
                    }
                }
            }

            // Wake up EDT waiting in getNextEvent(), so it can
            // pick up a new EventQueue. Post the waking event before
            // topQueue.nextQueue is assigned, otherwise the event would
            // go newEventQueue
            topQueue.postEventPrivate(new InvocationEvent(topQueue, dummyRunnable));

            newEventQueue.previousQueue = topQueue;
            topQueue.nextQueue = newEventQueue;

            if (appContext.get(AppContext.EVENT_QUEUE_KEY) == topQueue) {
                appContext.put(AppContext.EVENT_QUEUE_KEY, newEventQueue);
            }

            pushPopCond.signalAll();
        } finally {
            pushPopLock.unlock();
        }
    }

    /**
     * Stops dispatching events using this <code>EventQueue</code>.
     * Any pending events are transferred to the previous
     * <code>EventQueue</code> for processing.
     * <p>
     * Warning: To avoid deadlock, do not declare this method
     * synchronized in a subclass.
     *
     * <p>
     * 使用此<> EventQueue </code>停止调度事件。任何挂起的事件都会被转移到上一个<code> EventQueue </code>中进行处理。
     * <p>
     *  警告：为了避免死锁,请不要在子类中声明此方法同步。
     * 
     * 
     * @exception EmptyStackException if no previous push was made
     *  on this <code>EventQueue</code>
     * @see      java.awt.EventQueue#push
     * @since           1.2
     */
    protected void pop() throws EmptyStackException {
        if (getEventLog().isLoggable(PlatformLogger.Level.FINE)) {
            getEventLog().fine("EventQueue.pop(" + this + ")");
        }

        pushPopLock.lock();
        try {
            EventQueue topQueue = this;
            while (topQueue.nextQueue != null) {
                topQueue = topQueue.nextQueue;
            }
            EventQueue prevQueue = topQueue.previousQueue;
            if (prevQueue == null) {
                throw new EmptyStackException();
            }

            topQueue.previousQueue = null;
            prevQueue.nextQueue = null;

            // Transfer all events back to previous EventQueue.
            while (topQueue.peekEvent() != null) {
                try {
                    prevQueue.postEventPrivate(topQueue.getNextEventPrivate());
                } catch (InterruptedException ie) {
                    if (getEventLog().isLoggable(PlatformLogger.Level.FINE)) {
                        getEventLog().fine("Interrupted pop", ie);
                    }
                }
            }

            if ((topQueue.dispatchThread != null) &&
                (topQueue.dispatchThread.getEventQueue() == this))
            {
                prevQueue.dispatchThread = topQueue.dispatchThread;
                topQueue.dispatchThread.setEventQueue(prevQueue);
            }

            if (appContext.get(AppContext.EVENT_QUEUE_KEY) == this) {
                appContext.put(AppContext.EVENT_QUEUE_KEY, prevQueue);
            }

            // Wake up EDT waiting in getNextEvent(), so it can
            // pick up a new EventQueue
            topQueue.postEventPrivate(new InvocationEvent(topQueue, dummyRunnable));

            pushPopCond.signalAll();
        } finally {
            pushPopLock.unlock();
        }
    }

    /**
     * Creates a new {@code secondary loop} associated with this
     * event queue. Use the {@link SecondaryLoop#enter} and
     * {@link SecondaryLoop#exit} methods to start and stop the
     * event loop and dispatch the events from this queue.
     *
     * <p>
     *  创建与此事件队列关联的新{@code secondary loop}。
     * 使用{@link SecondaryLoop#enter}和{@link SecondaryLoop#exit}方法来启动和停止事件循环,并从此队列中分派事件。
     * 
     * 
     * @return secondaryLoop A new secondary loop object, which can
     *                       be used to launch a new nested event
     *                       loop and dispatch events from this queue
     *
     * @see SecondaryLoop#enter
     * @see SecondaryLoop#exit
     *
     * @since 1.7
     */
    public SecondaryLoop createSecondaryLoop() {
        return createSecondaryLoop(null, null, 0);
    }

    SecondaryLoop createSecondaryLoop(Conditional cond, EventFilter filter, long interval) {
        pushPopLock.lock();
        try {
            if (nextQueue != null) {
                // Forward the request to the top of EventQueue stack
                return nextQueue.createSecondaryLoop(cond, filter, interval);
            }
            if (fwDispatcher != null) {
                return fwDispatcher.createSecondaryLoop();
            }
            if (dispatchThread == null) {
                initDispatchThread();
            }
            return new WaitDispatchSupport(dispatchThread, cond, filter, interval);
        } finally {
            pushPopLock.unlock();
        }
    }

    /**
     * Returns true if the calling thread is
     * {@link Toolkit#getSystemEventQueue the current AWT EventQueue}'s
     * dispatch thread. Use this method to ensure that a particular
     * task is being executed (or not being) there.
     * <p>
     * Note: use the {@link #invokeLater} or {@link #invokeAndWait}
     * methods to execute a task in
     * {@link Toolkit#getSystemEventQueue the current AWT EventQueue}'s
     * dispatch thread.
     * <p>
     *
     * <p>
     *  如果调用线程是{@link Toolkit#getSystemEventQueue当前AWT EventQueue}的调度线程,则返回true。使用此方法可确保特定任务正在执行(或不在)。
     * <p>
     *  注意：使用{@link #invokeLater}或{@link #invokeAndWait}方法在{@link Toolkit#getSystemEventQueue当前AWT EventQueue}
     * 的调度线程中执行任务。
     * <p>
     * 
     * 
     * @return true if running in
     * {@link Toolkit#getSystemEventQueue the current AWT EventQueue}'s
     * dispatch thread
     * @see             #invokeLater
     * @see             #invokeAndWait
     * @see             Toolkit#getSystemEventQueue
     * @since           1.2
     */
    public static boolean isDispatchThread() {
        EventQueue eq = Toolkit.getEventQueue();
        return eq.isDispatchThreadImpl();
    }

    final boolean isDispatchThreadImpl() {
        EventQueue eq = this;
        pushPopLock.lock();
        try {
            EventQueue next = eq.nextQueue;
            while (next != null) {
                eq = next;
                next = eq.nextQueue;
            }
            if (eq.fwDispatcher != null) {
                return eq.fwDispatcher.isDispatchThread();
            }
            return (Thread.currentThread() == eq.dispatchThread);
        } finally {
            pushPopLock.unlock();
        }
    }

    final void initDispatchThread() {
        pushPopLock.lock();
        try {
            if (dispatchThread == null && !threadGroup.isDestroyed() && !appContext.isDisposed()) {
                dispatchThread = AccessController.doPrivileged(
                    new PrivilegedAction<EventDispatchThread>() {
                        public EventDispatchThread run() {
                            EventDispatchThread t =
                                new EventDispatchThread(threadGroup,
                                                        name,
                                                        EventQueue.this);
                            t.setContextClassLoader(classLoader);
                            t.setPriority(Thread.NORM_PRIORITY + 1);
                            t.setDaemon(false);
                            AWTAutoShutdown.getInstance().notifyThreadBusy(t);
                            return t;
                        }
                    }
                );
                dispatchThread.start();
            }
        } finally {
            pushPopLock.unlock();
        }
    }

    final void detachDispatchThread(EventDispatchThread edt) {
        /*
         * Minimize discard possibility for non-posted events
         * <p>
         *  最小化非投递事件的丢弃可能性
         * 
         */
        SunToolkit.flushPendingEvents(appContext);
        /*
         * This synchronized block is to secure that the event dispatch
         * thread won't die in the middle of posting a new event to the
         * associated event queue. It is important because we notify
         * that the event dispatch thread is busy after posting a new event
         * to its queue, so the EventQueue.dispatchThread reference must
         * be valid at that point.
         * <p>
         *  这个同步块是为了保证事件分派线程不会在将新事件发布到相关联的事件队列的中间死亡。
         * 这很重要,因为我们通知事件分派线程在将新事件发布到其队列后处于繁忙状态,因此EventQueue.dispatchThread引用必须在此时有效。
         * 
         */
        pushPopLock.lock();
        try {
            if (edt == dispatchThread) {
                dispatchThread = null;
            }
            AWTAutoShutdown.getInstance().notifyThreadFree(edt);
            /*
             * Event was posted after EDT events pumping had stopped, so start
             * another EDT to handle this event
             * <p>
             *  事件在EDT事件抽取停止后发布,因此启动另一个EDT来处理此事件
             * 
             */
            if (peekEvent() != null) {
                initDispatchThread();
            }
        } finally {
            pushPopLock.unlock();
        }
    }

    /*
     * Gets the <code>EventDispatchThread</code> for this
     * <code>EventQueue</code>.
     * <p>
     *  获取此<code> EventQueue </code>的<code> EventDispatchThread </code>。
     * 
     * 
     * @return the event dispatch thread associated with this event queue
     *         or <code>null</code> if this event queue doesn't have a
     *         working thread associated with it
     * @see    java.awt.EventQueue#initDispatchThread
     * @see    java.awt.EventQueue#detachDispatchThread
     */
    final EventDispatchThread getDispatchThread() {
        pushPopLock.lock();
        try {
            return dispatchThread;
        } finally {
            pushPopLock.unlock();
        }
    }

    /*
     * Removes any pending events for the specified source object.
     * If removeAllEvents parameter is <code>true</code> then all
     * events for the specified source object are removed, if it
     * is <code>false</code> then <code>SequencedEvent</code>, <code>SentEvent</code>,
     * <code>FocusEvent</code>, <code>WindowEvent</code>, <code>KeyEvent</code>,
     * and <code>InputMethodEvent</code> are kept in the queue, but all other
     * events are removed.
     *
     * This method is normally called by the source's
     * <code>removeNotify</code> method.
     * <p>
     * 删除指定源对象的任何挂起事件。
     * 如果removeAllEvents参数为<code> true </code>,则除去指定源对象的所有事件,如果<code> false </code>,则<code> SequencedEvent </code>
     * ,<code> SentEvent <代码>,<code> FocusEvent </code>,<code> WindowEvent </code>,<code> KeyEvent </code>和<code>
     *  InputMethodEvent </code>,但所有其他事件删除。
     * 删除指定源对象的任何挂起事件。
     * 
     *  此方法通常由源的<code> removeNotify </code>方法调用。
     * 
     */
    final void removeSourceEvents(Object source, boolean removeAllEvents) {
        SunToolkit.flushPendingEvents(appContext);
        pushPopLock.lock();
        try {
            for (int i = 0; i < NUM_PRIORITIES; i++) {
                EventQueueItem entry = queues[i].head;
                EventQueueItem prev = null;
                while (entry != null) {
                    if ((entry.event.getSource() == source)
                        && (removeAllEvents
                            || ! (entry.event instanceof SequencedEvent
                                  || entry.event instanceof SentEvent
                                  || entry.event instanceof FocusEvent
                                  || entry.event instanceof WindowEvent
                                  || entry.event instanceof KeyEvent
                                  || entry.event instanceof InputMethodEvent)))
                    {
                        if (entry.event instanceof SequencedEvent) {
                            ((SequencedEvent)entry.event).dispose();
                        }
                        if (entry.event instanceof SentEvent) {
                            ((SentEvent)entry.event).dispose();
                        }
                        if (entry.event instanceof InvocationEvent) {
                            AWTAccessor.getInvocationEventAccessor()
                                    .dispose((InvocationEvent)entry.event);
                        }
                        if (prev == null) {
                            queues[i].head = entry.next;
                        } else {
                            prev.next = entry.next;
                        }
                        uncacheEQItem(entry);
                    } else {
                        prev = entry;
                    }
                    entry = entry.next;
                }
                queues[i].tail = prev;
            }
        } finally {
            pushPopLock.unlock();
        }
    }

    synchronized long getMostRecentKeyEventTime() {
        pushPopLock.lock();
        try {
            return mostRecentKeyEventTime;
        } finally {
            pushPopLock.unlock();
        }
    }

    static void setCurrentEventAndMostRecentTime(AWTEvent e) {
        Toolkit.getEventQueue().setCurrentEventAndMostRecentTimeImpl(e);
    }
    private void setCurrentEventAndMostRecentTimeImpl(AWTEvent e) {
        pushPopLock.lock();
        try {
            if (Thread.currentThread() != dispatchThread) {
                return;
            }

            currentEvent = new WeakReference<>(e);

            // This series of 'instanceof' checks should be replaced with a
            // polymorphic type (for example, an interface which declares a
            // getWhen() method). However, this would require us to make such
            // a type public, or to place it in sun.awt. Both of these approaches
            // have been frowned upon. So for now, we hack.
            //
            // In tiger, we will probably give timestamps to all events, so this
            // will no longer be an issue.
            long mostRecentEventTime2 = Long.MIN_VALUE;
            if (e instanceof InputEvent) {
                InputEvent ie = (InputEvent)e;
                mostRecentEventTime2 = ie.getWhen();
                if (e instanceof KeyEvent) {
                    mostRecentKeyEventTime = ie.getWhen();
                }
            } else if (e instanceof InputMethodEvent) {
                InputMethodEvent ime = (InputMethodEvent)e;
                mostRecentEventTime2 = ime.getWhen();
            } else if (e instanceof ActionEvent) {
                ActionEvent ae = (ActionEvent)e;
                mostRecentEventTime2 = ae.getWhen();
            } else if (e instanceof InvocationEvent) {
                InvocationEvent ie = (InvocationEvent)e;
                mostRecentEventTime2 = ie.getWhen();
            }
            mostRecentEventTime = Math.max(mostRecentEventTime, mostRecentEventTime2);
        } finally {
            pushPopLock.unlock();
        }
    }

    /**
     * Causes <code>runnable</code> to have its <code>run</code>
     * method called in the {@link #isDispatchThread dispatch thread} of
     * {@link Toolkit#getSystemEventQueue the system EventQueue}.
     * This will happen after all pending events are processed.
     *
     * <p>
     *  导致<code> runnable </code>在{@link Toolkit#getSystemEventQueue系统EventQueue}的{@link #isDispatchThread dispatch线程}
     * 中调用其<code>运行</code>方法。
     * 这将在处理所有待处理事件后发生。
     * 
     * 
     * @param runnable  the <code>Runnable</code> whose <code>run</code>
     *                  method should be executed
     *                  asynchronously in the
     *                  {@link #isDispatchThread event dispatch thread}
     *                  of {@link Toolkit#getSystemEventQueue the system EventQueue}
     * @see             #invokeAndWait
     * @see             Toolkit#getSystemEventQueue
     * @see             #isDispatchThread
     * @since           1.2
     */
    public static void invokeLater(Runnable runnable) {
        Toolkit.getEventQueue().postEvent(
            new InvocationEvent(Toolkit.getDefaultToolkit(), runnable));
    }

    /**
     * Causes <code>runnable</code> to have its <code>run</code>
     * method called in the {@link #isDispatchThread dispatch thread} of
     * {@link Toolkit#getSystemEventQueue the system EventQueue}.
     * This will happen after all pending events are processed.
     * The call blocks until this has happened.  This method
     * will throw an Error if called from the
     * {@link #isDispatchThread event dispatcher thread}.
     *
     * <p>
     *  导致<code> runnable </code>在{@link Toolkit#getSystemEventQueue系统EventQueue}的{@link #isDispatchThread dispatch线程}
     * 中调用其<code>运行</code>方法。
     * 这将在处理所有待处理事件后发生。呼叫阻塞,直到这发生。如果从{@link #isDispatchThread事件分派器线程}调用,此方法将抛出错误。
     * 
     * 
     * @param runnable  the <code>Runnable</code> whose <code>run</code>
     *                  method should be executed
     *                  synchronously in the
     *                  {@link #isDispatchThread event dispatch thread}
     *                  of {@link Toolkit#getSystemEventQueue the system EventQueue}
     * @exception       InterruptedException  if any thread has
     *                  interrupted this thread
     * @exception       InvocationTargetException  if an throwable is thrown
     *                  when running <code>runnable</code>
     * @see             #invokeLater
     * @see             Toolkit#getSystemEventQueue
     * @see             #isDispatchThread
     * @since           1.2
     */
    public static void invokeAndWait(Runnable runnable)
        throws InterruptedException, InvocationTargetException
    {
        invokeAndWait(Toolkit.getDefaultToolkit(), runnable);
    }

    static void invokeAndWait(Object source, Runnable runnable)
        throws InterruptedException, InvocationTargetException
    {
        if (EventQueue.isDispatchThread()) {
            throw new Error("Cannot call invokeAndWait from the event dispatcher thread");
        }

        class AWTInvocationLock {}
        Object lock = new AWTInvocationLock();

        InvocationEvent event =
            new InvocationEvent(source, runnable, lock, true);

        synchronized (lock) {
            Toolkit.getEventQueue().postEvent(event);
            while (!event.isDispatched()) {
                lock.wait();
            }
        }

        Throwable eventThrowable = event.getThrowable();
        if (eventThrowable != null) {
            throw new InvocationTargetException(eventThrowable);
        }
    }

    /*
     * Called from PostEventQueue.postEvent to notify that a new event
     * appeared. First it proceeds to the EventQueue on the top of the
     * stack, then notifies the associated dispatch thread if it exists
     * or starts a new one otherwise.
     * <p>
     *  从PostEventQueue.postEvent调用以通知新事件出现。首先它进行到堆栈顶部的EventQueue,然后通知相关的分派线程如果它存在,否则启动一个新的。
     * 
     */
    private void wakeup(boolean isShutdown) {
        pushPopLock.lock();
        try {
            if (nextQueue != null) {
                // Forward call to the top of EventQueue stack.
                nextQueue.wakeup(isShutdown);
            } else if (dispatchThread != null) {
                pushPopCond.signalAll();
            } else if (!isShutdown) {
                initDispatchThread();
            }
        } finally {
            pushPopLock.unlock();
        }
    }

    // The method is used by AWTAccessor for javafx/AWT single threaded mode.
    private void setFwDispatcher(FwDispatcher dispatcher) {
        if (nextQueue != null) {
            nextQueue.setFwDispatcher(dispatcher);
        } else {
            fwDispatcher = dispatcher;
        }
    }
}

/**
 * The Queue object holds pointers to the beginning and end of one internal
 * queue. An EventQueue object is composed of multiple internal Queues, one
 * for each priority supported by the EventQueue. All Events on a particular
 * internal Queue have identical priority.
 * <p>
 * Queue对象保存指向一个内部队列的开始和结束的指针。 EventQueue对象由多个内部队列组成,一个用于EventQueue支持的每个优先级。特定内部队列上的所有事件具有相同的优先级。
 */
class Queue {
    EventQueueItem head;
    EventQueueItem tail;
}
