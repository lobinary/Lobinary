/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.awt.event;

import sun.awt.AWTAccessor;

import java.awt.ActiveEvent;
import java.awt.AWTEvent;

/**
 * An event which executes the <code>run()</code> method on a <code>Runnable
 * </code> when dispatched by the AWT event dispatcher thread. This class can
 * be used as a reference implementation of <code>ActiveEvent</code> rather
 * than declaring a new class and defining <code>dispatch()</code>.<p>
 *
 * Instances of this class are placed on the <code>EventQueue</code> by calls
 * to <code>invokeLater</code> and <code>invokeAndWait</code>. Client code
 * can use this fact to write replacement functions for <code>invokeLater
 * </code> and <code>invokeAndWait</code> without writing special-case code
 * in any <code>AWTEventListener</code> objects.
 * <p>
 * An unspecified behavior will be caused if the {@code id} parameter
 * of any particular {@code InvocationEvent} instance is not
 * in the range from {@code INVOCATION_FIRST} to {@code INVOCATION_LAST}.
 *
 * <p>
 *  当由AWT事件分派器线程分派时,在<code> Runnable </code>上执行<code> run()</code>方法的事件。
 * 这个类可以用作<code> ActiveEvent </code>的引用实现,而不是声明一个新类并定义<code> dispatch()</code>。<p>。
 * 
 *  此类的实例通过调用<code> invokeLater </code>和<code> invokeAndWait </code>而放置在<code> EventQueue </code>上。
 * 客户端代码可以使用此事实为<code> invokeLater </code>和<code> invokeAndWait </code>写入替换函数,而不在任何<code> AWTEventListen
 * er </code>对象中编写特殊情况代码。
 *  此类的实例通过调用<code> invokeLater </code>和<code> invokeAndWait </code>而放置在<code> EventQueue </code>上。
 * <p>
 *  如果任何特定{@code InvocationEvent}实例的{@code id}参数不在{@code INVOCATION_FIRST}到{@code INVOCATION_LAST}的范围内,则
 * 会导致未指定的行为。
 * 
 * 
 * @author      Fred Ecks
 * @author      David Mendenhall
 *
 * @see         java.awt.ActiveEvent
 * @see         java.awt.EventQueue#invokeLater
 * @see         java.awt.EventQueue#invokeAndWait
 * @see         AWTEventListener
 *
 * @since       1.2
 */
public class InvocationEvent extends AWTEvent implements ActiveEvent {

    static {
        AWTAccessor.setInvocationEventAccessor(new AWTAccessor.InvocationEventAccessor() {
            @Override
            public void dispose(InvocationEvent invocationEvent) {
                invocationEvent.finishedDispatching(false);
            }
        });
    }

    /**
     * Marks the first integer id for the range of invocation event ids.
     * <p>
     *  标记调用事件标识范围的第一个整数ID。
     * 
     */
    public static final int INVOCATION_FIRST = 1200;

    /**
     * The default id for all InvocationEvents.
     * <p>
     *  所有InvocationEvents的默认ID。
     * 
     */
    public static final int INVOCATION_DEFAULT = INVOCATION_FIRST;

    /**
     * Marks the last integer id for the range of invocation event ids.
     * <p>
     *  标记调用事件标识范围的最后一个整数ID。
     * 
     */
    public static final int INVOCATION_LAST = INVOCATION_DEFAULT;

    /**
     * The Runnable whose run() method will be called.
     * <p>
     *  Runnable的run()方法将被调用。
     * 
     */
    protected Runnable runnable;

    /**
     * The (potentially null) Object whose notifyAll() method will be called
     * immediately after the Runnable.run() method has returned or thrown an exception
     * or after the event was disposed.
     *
     * <p>
     *  (可能为null)对象的notifyAll()方法将在Runnable.run()方法返回或抛出异常之后立即被调用,或者事件被处理后。
     * 
     * 
     * @see #isDispatched
     */
    protected volatile Object notifier;

    /**
     * The (potentially null) Runnable whose run() method will be called
     * immediately after the event was dispatched or disposed.
     *
     * <p>
     *  (可能为空)Runnable的run()方法将在事件被分派或处理后立即被调用。
     * 
     * 
     * @see #isDispatched
     * @since 1.8
     */
    private final Runnable listener;

    /**
     * Indicates whether the <code>run()</code> method of the <code>runnable</code>
     * was executed or not.
     *
     * <p>
     * 指示<code> runnable </code>的<code> run()</code>方法是否已执行。
     * 
     * 
     * @see #isDispatched
     * @since 1.7
     */
    private volatile boolean dispatched = false;

    /**
     * Set to true if dispatch() catches Throwable and stores it in the
     * exception instance variable. If false, Throwables are propagated up
     * to the EventDispatchThread's dispatch loop.
     * <p>
     *  如果dispatch()捕获Throwable并将其存储在异常实例变量中,则设置为true。如果为false,Throwables传播到EventDispatchThread的分派循环。
     * 
     */
    protected boolean catchExceptions;

    /**
     * The (potentially null) Exception thrown during execution of the
     * Runnable.run() method. This variable will also be null if a particular
     * instance does not catch exceptions.
     * <p>
     *  在执行Runnable.run()方法期间抛出的(可能为null)异常。如果特定实例不捕获异常,此变量也将为null。
     * 
     */
    private Exception exception = null;

    /**
     * The (potentially null) Throwable thrown during execution of the
     * Runnable.run() method. This variable will also be null if a particular
     * instance does not catch exceptions.
     * <p>
     *  在执行Runnable.run()方法期间抛出的(可能为空)Throwable。如果特定实例不捕获异常,此变量也将为null。
     * 
     */
    private Throwable throwable = null;

    /**
     * The timestamp of when this event occurred.
     *
     * <p>
     *  此事件发生的时间戳。
     * 
     * 
     * @serial
     * @see #getWhen
     */
    private long when;

    /*
     * JDK 1.1 serialVersionUID.
     * <p>
     *  JDK 1.1 serialVersionUID。
     * 
     */
    private static final long serialVersionUID = 436056344909459450L;

    /**
     * Constructs an <code>InvocationEvent</code> with the specified
     * source which will execute the runnable's <code>run</code>
     * method when dispatched.
     * <p>This is a convenience constructor.  An invocation of the form
     * <tt>InvocationEvent(source, runnable)</tt>
     * behaves in exactly the same way as the invocation of
     * <tt>{@link #InvocationEvent(Object, Runnable, Object, boolean) InvocationEvent}(source, runnable, null, false)</tt>.
     * <p> This method throws an <code>IllegalArgumentException</code>
     * if <code>source</code> is <code>null</code>.
     *
     * <p>
     *  构造一个具有指定源的<code> InvocationEvent </code>,它将在分派时执行runnable的<code> run </code>方法。 <p>这是一个方便的构造函数。
     * 调用形式<tt> InvocationEvent(source,runnable)</tt>的行为与调用<tt>完全相同{@ link #InvocationEvent(Object,Runnable,Object,boolean)InvocationEvent}
     *  runnable,null,false)</tt>。
     *  构造一个具有指定源的<code> InvocationEvent </code>,它将在分派时执行runnable的<code> run </code>方法。 <p>这是一个方便的构造函数。
     *  <p>如果<code> source </code>是<code> null </code>,此方法会抛出<code> IllegalArgumentException </code>。
     * 
     * 
     * @param source    The <code>Object</code> that originated the event
     * @param runnable  The <code>Runnable</code> whose <code>run</code>
     *                  method will be executed
     * @throws IllegalArgumentException if <code>source</code> is null
     *
     * @see #getSource()
     * @see #InvocationEvent(Object, Runnable, Object, boolean)
     */
    public InvocationEvent(Object source, Runnable runnable) {
        this(source, INVOCATION_DEFAULT, runnable, null, null, false);
    }

    /**
     * Constructs an <code>InvocationEvent</code> with the specified
     * source which will execute the runnable's <code>run</code>
     * method when dispatched.  If notifier is non-<code>null</code>,
     * <code>notifyAll()</code> will be called on it
     * immediately after <code>run</code> has returned or thrown an exception.
     * <p>An invocation of the form <tt>InvocationEvent(source,
     * runnable, notifier, catchThrowables)</tt>
     * behaves in exactly the same way as the invocation of
     * <tt>{@link #InvocationEvent(Object, int, Runnable, Object, boolean) InvocationEvent}(source, InvocationEvent.INVOCATION_DEFAULT, runnable, notifier, catchThrowables)</tt>.
     * <p>This method throws an <code>IllegalArgumentException</code>
     * if <code>source</code> is <code>null</code>.
     *
     * <p>
     * 构造一个具有指定源的<code> InvocationEvent </code>,它将在分派时执行runnable的<code> run </code>方法。
     * 如果通知程序是非<code> null </code>,则<code> run </code>返回或抛出异常后,将立即调用notifyAll()</code>。
     *  <p>调用形式<tt> InvocationEvent(source,runnable,notifier,catchThrowables)</tt>的行为与调用<tt>完全相同{@ link #InvocationEvent(Object,int,Runnable, Object,boolean)InvocationEvent}
     * (source,InvocationEvent.INVOCATION_DEFAULT,runnable,notifier,catchThrowables)</tt>。
     * 如果通知程序是非<code> null </code>,则<code> run </code>返回或抛出异常后,将立即调用notifyAll()</code>。
     *  <p>如果<code> source </code>是<code> null </code>,此方法会抛出<code> IllegalArgumentException </code>。
     * 
     * 
     * @param source            The <code>Object</code> that originated
     *                          the event
     * @param runnable          The <code>Runnable</code> whose
     *                          <code>run</code> method will be
     *                          executed
     * @param notifier          The {@code Object} whose <code>notifyAll</code>
     *                          method will be called after
     *                          <code>Runnable.run</code> has returned or
     *                          thrown an exception or after the event was
     *                          disposed
     * @param catchThrowables   Specifies whether <code>dispatch</code>
     *                          should catch Throwable when executing
     *                          the <code>Runnable</code>'s <code>run</code>
     *                          method, or should instead propagate those
     *                          Throwables to the EventDispatchThread's
     *                          dispatch loop
     * @throws IllegalArgumentException if <code>source</code> is null
     *
     * @see #getSource()
     * @see     #InvocationEvent(Object, int, Runnable, Object, boolean)
     */
    public InvocationEvent(Object source, Runnable runnable, Object notifier,
                           boolean catchThrowables) {
        this(source, INVOCATION_DEFAULT, runnable, notifier, null, catchThrowables);
    }

    /**
     * Constructs an <code>InvocationEvent</code> with the specified
     * source which will execute the runnable's <code>run</code>
     * method when dispatched.  If listener is non-<code>null</code>,
     * <code>listener.run()</code> will be called immediately after
     * <code>run</code> has returned, thrown an exception or the event
     * was disposed.
     * <p>This method throws an <code>IllegalArgumentException</code>
     * if <code>source</code> is <code>null</code>.
     *
     * <p>
     *  构造一个具有指定源的<code> InvocationEvent </code>,它将在分派时执行runnable的<code> run </code>方法。
     * 如果侦听器是非<code> null </code>,<code> listener.run()</code>将在<code> run </code>返回后抛出异常或事件被处理。
     *  <p>如果<code> source </code>是<code> null </code>,此方法会抛出<code> IllegalArgumentException </code>。
     * 
     * 
     * @param source            The <code>Object</code> that originated
     *                          the event
     * @param runnable          The <code>Runnable</code> whose
     *                          <code>run</code> method will be
     *                          executed
     * @param listener          The <code>Runnable</code>Runnable whose
     *                          <code>run()</code> method will be called
     *                          after the {@code InvocationEvent}
     *                          was dispatched or disposed
     * @param catchThrowables   Specifies whether <code>dispatch</code>
     *                          should catch Throwable when executing
     *                          the <code>Runnable</code>'s <code>run</code>
     *                          method, or should instead propagate those
     *                          Throwables to the EventDispatchThread's
     *                          dispatch loop
     * @throws IllegalArgumentException if <code>source</code> is null
     */
    public InvocationEvent(Object source, Runnable runnable, Runnable listener,
                           boolean catchThrowables)  {
        this(source, INVOCATION_DEFAULT, runnable, null, listener, catchThrowables);
    }

    /**
     * Constructs an <code>InvocationEvent</code> with the specified
     * source and ID which will execute the runnable's <code>run</code>
     * method when dispatched.  If notifier is non-<code>null</code>,
     * <code>notifyAll</code> will be called on it immediately after
     * <code>run</code> has returned or thrown an exception.
     * <p>This method throws an
     * <code>IllegalArgumentException</code> if <code>source</code>
     * is <code>null</code>.
     *
     * <p>
     * 构造具有指定的源和ID的<code> InvocationEvent </code>,它将在分派时执行runnable的<code> run </code>方法。
     * 如果通知程序是非<code> null </code>,则<code> run </code>返回或抛出异常后,将立即调用<code> notifyAll </code>。
     *  <p>如果<code> source </code>是<code> null </code>,此方法会抛出<code> IllegalArgumentException </code>。
     * 
     * 
     * @param source            The <code>Object</code> that originated
     *                          the event
     * @param id     An integer indicating the type of event.
     *                     For information on allowable values, see
     *                     the class description for {@link InvocationEvent}
     * @param runnable          The <code>Runnable</code> whose
     *                          <code>run</code> method will be executed
     * @param notifier          The <code>Object</code> whose <code>notifyAll</code>
     *                          method will be called after
     *                          <code>Runnable.run</code> has returned or
     *                          thrown an exception or after the event was
     *                          disposed
     * @param catchThrowables   Specifies whether <code>dispatch</code>
     *                          should catch Throwable when executing the
     *                          <code>Runnable</code>'s <code>run</code>
     *                          method, or should instead propagate those
     *                          Throwables to the EventDispatchThread's
     *                          dispatch loop
     * @throws IllegalArgumentException if <code>source</code> is null
     * @see #getSource()
     * @see #getID()
     */
    protected InvocationEvent(Object source, int id, Runnable runnable,
                              Object notifier, boolean catchThrowables) {
        this(source, id, runnable, notifier, null, catchThrowables);
    }

    private InvocationEvent(Object source, int id, Runnable runnable,
                            Object notifier, Runnable listener, boolean catchThrowables) {
        super(source, id);
        this.runnable = runnable;
        this.notifier = notifier;
        this.listener = listener;
        this.catchExceptions = catchThrowables;
        this.when = System.currentTimeMillis();
    }
    /**
     * Executes the Runnable's <code>run()</code> method and notifies the
     * notifier (if any) when <code>run()</code> has returned or thrown an exception.
     *
     * <p>
     *  执行Runnable的<code> run()</code>方法,并在<code> run()</code>返回或抛出异常时通知通知程序(如果有)。
     * 
     * 
     * @see #isDispatched
     */
    public void dispatch() {
        try {
            if (catchExceptions) {
                try {
                    runnable.run();
                }
                catch (Throwable t) {
                    if (t instanceof Exception) {
                        exception = (Exception) t;
                    }
                    throwable = t;
                }
            }
            else {
                runnable.run();
            }
        } finally {
            finishedDispatching(true);
        }
    }

    /**
     * Returns any Exception caught while executing the Runnable's <code>run()
     * </code> method.
     *
     * <p>
     *  返回执行Runnable的<code> run()</code>方法时捕获的任何异常。
     * 
     * 
     * @return  A reference to the Exception if one was thrown; null if no
     *          Exception was thrown or if this InvocationEvent does not
     *          catch exceptions
     */
    public Exception getException() {
        return (catchExceptions) ? exception : null;
    }

    /**
     * Returns any Throwable caught while executing the Runnable's <code>run()
     * </code> method.
     *
     * <p>
     *  返回执行Runnable的<code> run()</code>方法时捕获的任何Throwable。
     * 
     * 
     * @return  A reference to the Throwable if one was thrown; null if no
     *          Throwable was thrown or if this InvocationEvent does not
     *          catch Throwables
     * @since 1.5
     */
    public Throwable getThrowable() {
        return (catchExceptions) ? throwable : null;
    }

    /**
     * Returns the timestamp of when this event occurred.
     *
     * <p>
     *  返回此事件发生时的时间戳。
     * 
     * 
     * @return this event's timestamp
     * @since 1.4
     */
    public long getWhen() {
        return when;
    }

    /**
     * Returns {@code true} if the event is dispatched or any exception is
     * thrown while dispatching, {@code false} otherwise. The method should
     * be called by a waiting thread that calls the {@code notifier.wait()} method.
     * Since spurious wakeups are possible (as explained in {@link Object#wait()}),
     * this method should be used in a waiting loop to ensure that the event
     * got dispatched:
     * <pre>
     *     while (!event.isDispatched()) {
     *         notifier.wait();
     *     }
     * </pre>
     * If the waiting thread wakes up without dispatching the event,
     * the {@code isDispatched()} method returns {@code false}, and
     * the {@code while} loop executes once more, thus, causing
     * the awakened thread to revert to the waiting mode.
     * <p>
     * If the {@code notifier.notifyAll()} happens before the waiting thread
     * enters the {@code notifier.wait()} method, the {@code while} loop ensures
     * that the waiting thread will not enter the {@code notifier.wait()} method.
     * Otherwise, there is no guarantee that the waiting thread will ever be woken
     * from the wait.
     *
     * <p>
     *  如果分派事件或返回时抛出任何异常,则返回{@code true},否则返回{@code false}。该方法应该由调用{@code notifier.wait()}方法的等待线程调用。
     * 由于虚假唤醒是可能的(如{@link Object#wait()}中所述),此方法应在等待循环中使用,以确保事件已分派：。
     * <pre>
     *  while(！event.isDispatched()){notifier.wait(); }}
     * </pre>
     *  如果等待线程在不调度事件的情况下唤醒,{@code isDispatched()}方法返回{@code false},并且再次执行{@code while}循环,从而使唤醒的线程恢复等待模式。
     * <p>
     * 如果{@code notifier.notifyAll()}发生在等待线程进入{@code notifier.wait()}方法之前,{@code while}循环确保等待线程不会进入{@code notifier。
     * 
     * @return {@code true} if the event has been dispatched, or any exception
     * has been thrown while dispatching, {@code false} otherwise
     * @see #dispatch
     * @see #notifier
     * @see #catchExceptions
     * @since 1.7
     */
    public boolean isDispatched() {
        return dispatched;
    }

    /**
     * Called when the event was dispatched or disposed
     * <p>
     *  wait()}方法。否则,不能保证等待线程将永远从等待唤醒。
     * 
     * 
     * @param dispatched true if the event was dispatched
     *                   false if the event was disposed
     */
    private void finishedDispatching(boolean dispatched) {
        this.dispatched = dispatched;

        if (notifier != null) {
            synchronized (notifier) {
                notifier.notifyAll();
            }
        }

        if (listener != null) {
            listener.run();
        }
    }

    /**
     * Returns a parameter string identifying this event.
     * This method is useful for event-logging and for debugging.
     *
     * <p>
     *  在事件被分派或处置时调用
     * 
     * 
     * @return  A string identifying the event and its attributes
     */
    public String paramString() {
        String typeStr;
        switch(id) {
            case INVOCATION_DEFAULT:
                typeStr = "INVOCATION_DEFAULT";
                break;
            default:
                typeStr = "unknown type";
        }
        return typeStr + ",runnable=" + runnable + ",notifier=" + notifier +
            ",catchExceptions=" + catchExceptions + ",when=" + when;
    }
}
