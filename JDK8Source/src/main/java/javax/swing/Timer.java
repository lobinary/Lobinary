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



package javax.swing;



import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.io.*;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;
import javax.swing.event.EventListenerList;



/**
 * Fires one or more {@code ActionEvent}s at specified
 * intervals. An example use is an animation object that uses a
 * <code>Timer</code> as the trigger for drawing its frames.
 *<p>
 * Setting up a timer
 * involves creating a <code>Timer</code> object,
 * registering one or more action listeners on it,
 * and starting the timer using
 * the <code>start</code> method.
 * For example,
 * the following code creates and starts a timer
 * that fires an action event once per second
 * (as specified by the first argument to the <code>Timer</code> constructor).
 * The second argument to the <code>Timer</code> constructor
 * specifies a listener to receive the timer's action events.
 *
 *<pre>
 *  int delay = 1000; //milliseconds
 *  ActionListener taskPerformer = new ActionListener() {
 *      public void actionPerformed(ActionEvent evt) {
 *          <em>//...Perform a task...</em>
 *      }
 *  };
 *  new Timer(delay, taskPerformer).start();</pre>
 *
 * <p>
 * {@code Timers} are constructed by specifying both a delay parameter
 * and an {@code ActionListener}. The delay parameter is used
 * to set both the initial delay and the delay between event
 * firing, in milliseconds. Once the timer has been started,
 * it waits for the initial delay before firing its
 * first <code>ActionEvent</code> to registered listeners.
 * After this first event, it continues to fire events
 * every time the between-event delay has elapsed, until it
 * is stopped.
 * <p>
 * After construction, the initial delay and the between-event
 * delay can be changed independently, and additional
 * <code>ActionListeners</code> may be added.
 * <p>
 * If you want the timer to fire only the first time and then stop,
 * invoke <code>setRepeats(false)</code> on the timer.
 * <p>
 * Although all <code>Timer</code>s perform their waiting
 * using a single, shared thread
 * (created by the first <code>Timer</code> object that executes),
 * the action event handlers for <code>Timer</code>s
 * execute on another thread -- the event-dispatching thread.
 * This means that the action handlers for <code>Timer</code>s
 * can safely perform operations on Swing components.
 * However, it also means that the handlers must execute quickly
 * to keep the GUI responsive.
 *
 * <p>
 * In v 1.3, another <code>Timer</code> class was added
 * to the Java platform: <code>java.util.Timer</code>.
 * Both it and <code>javax.swing.Timer</code>
 * provide the same basic functionality,
 * but <code>java.util.Timer</code>
 * is more general and has more features.
 * The <code>javax.swing.Timer</code> has two features
 * that can make it a little easier to use with GUIs.
 * First, its event handling metaphor is familiar to GUI programmers
 * and can make dealing with the event-dispatching thread
 * a bit simpler.
 * Second, its
 * automatic thread sharing means that you don't have to
 * take special steps to avoid spawning
 * too many threads.
 * Instead, your timer uses the same thread
 * used to make cursors blink,
 * tool tips appear,
 * and so on.
 *
 * <p>
 * You can find further documentation
 * and several examples of using timers by visiting
 * <a href="https://docs.oracle.com/javase/tutorial/uiswing/misc/timer.html"
 * target = "_top">How to Use Timers</a>,
 * a section in <em>The Java Tutorial.</em>
 * For more examples and help in choosing between
 * this <code>Timer</code> class and
 * <code>java.util.Timer</code>,
 * see
 * <a href="http://java.sun.com/products/jfc/tsc/articles/timer/"
 * target="_top">Using Timers in Swing Applications</a>,
 * an article in <em>The Swing Connection.</em>
 * <p>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running
 * the same version of Swing.  As of 1.4, support for long term storage
 * of all JavaBeans&trade;
 * has been added to the <code>java.beans</code> package.
 * Please see {@link java.beans.XMLEncoder}.
 *
 * <p>
 *  以指定的间隔触发一个或多个{@code ActionEvent}。一个使用示例是使用<code> Timer </code>作为绘制其帧的触发器的动画对象。
 * p>
 *  设置定时器包括创建一个<code> Timer </code>对象,在其上注册一个或多个动作监听器,并使用<code> start </code>方法启动定时器。
 * 例如,以下代码创建并启动一个定时器,每秒触发一次动作事件(由<code> Timer </code>构造函数的第一个参数指定)。
 *  <code> Timer </code>构造函数的第二个参数指定一个接收定时器动作事件的监听器。
 * 
 * pre>
 *  int delay = 1000; // milliseconds ActionListener taskPerformer = new ActionListener(){public void actionPerformed(ActionEvent evt){</em> // ... Perform an task ... </em>}
 * }; new Timer(delay,taskPerformer).start(); </pre>。
 * 
 * <p>
 *  {@code Timers}是通过指定delay参数和{@code ActionListener}构造的。 delay参数用于设置初始延迟和事件触发之间的延迟(以毫秒为单位)。
 * 一旦定时器已经启动,它在向注册的侦听器触发其第一<code> ActionEvent </code>之前等待初始延迟。在第一个事件之后,它每次事件间延迟过去后继续触发事件,直到它停止。
 * <p>
 * 在构造之后,可以独立地改变初始延迟和事件间延迟,并且可以添加附加的<code> ActionListeners </code>。
 * <p>
 *  如果你想让定时器第一次触发然后停止,调用定时器上的<code> setRepeats(false)</code>。
 * <p>
 *  虽然所有<code> Timer </code>使用单个共享线程(由执行的第一个<code> Timer </code>对象创建)执行它们的等待,但是<code> Timer </code >在另一个
 * 线程上执行 - 事件调度线程。
 * 这意味着<code> Timer </code>的动作处理程序可以安全地对Swing组件执行操作。然而,这也意味着处理程序必须快速执行以保持GUI响应。
 * 
 * <p>
 *  在v 1.3中,另一个<code> Timer </code>类被添加到Java平台：<code> java.util.Timer </code>。
 * 它和<code> javax.swing.Timer </code>提供相同的基本功能,但<code> java.util.Timer </code>是更通用的,并有更多的功能。
 *  <code> javax.swing.Timer </code>有两个特性,可以使它更容易使用GUI。首先,它的事件处理隐喻是GUI程序员熟悉的,并且可以使事件调度线程更简单一些。
 * 其次,它的自动线程共享意味着你不必采取特殊的步骤,以避免产生太多的线程。相反,您的计时器使用相同的线程用于使光标闪烁,工具提示出现,等等。
 * 
 * <p>
 * 有关使用计时器的更多文档和几个示例,请访问<a href="https://docs.oracle.com/javase/tutorial/uiswing/misc/timer.html" target= "_top">
 * 如何使用计时器有关更多示例和帮助,请选择<code> Timer </code>类和<code> java.util.Timer </code>之间的</em> ,请参阅<a href="http://java.sun.com/products/jfc/tsc/articles/timer/" target="_top">
 * 在Swing应用程序中使用计时器</a>,<em>摇摆连接。
 * </em>。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @see java.util.Timer <code>java.util.Timer</code>
 *
 *
 * @author Dave Moore
 */
@SuppressWarnings("serial")
public class Timer implements Serializable
{
    /*
     * NOTE: all fields need to be handled in readResolve
     * <p>
     *  注意：所有字段都需要在readResolve中处理
     * 
     */

    protected EventListenerList listenerList = new EventListenerList();

    // The following field strives to maintain the following:
    //    If coalesce is true, only allow one Runnable to be queued on the
    //    EventQueue and be pending (ie in the process of notifying the
    //    ActionListener). If we didn't do this it would allow for a
    //    situation where the app is taking too long to process the
    //    actionPerformed, and thus we'ld end up queing a bunch of Runnables
    //    and the app would never return: not good. This of course implies
    //    you can get dropped events, but such is life.
    // notify is used to indicate if the ActionListener can be notified, when
    // the Runnable is processed if this is true it will notify the listeners.
    // notify is set to true when the Timer fires and the Runnable is queued.
    // It will be set to false after notifying the listeners (if coalesce is
    // true) or if the developer invokes stop.
    private transient final AtomicBoolean notify = new AtomicBoolean(false);

    private volatile int     initialDelay, delay;
    private volatile boolean repeats = true, coalesce = true;

    private transient final Runnable doPostEvent;

    private static volatile boolean logTimers;

    private transient final Lock lock = new ReentrantLock();

    // This field is maintained by TimerQueue.
    // eventQueued can also be reset by the TimerQueue, but will only ever
    // happen in applet case when TimerQueues thread is destroyed.
    // access to this field is synchronized on getLock() lock.
    transient TimerQueue.DelayedTimer delayedTimer = null;

    private volatile String actionCommand;

    /**
     * Creates a {@code Timer} and initializes both the initial delay and
     * between-event delay to {@code delay} milliseconds. If {@code delay}
     * is less than or equal to zero, the timer fires as soon as it
     * is started. If <code>listener</code> is not <code>null</code>,
     * it's registered as an action listener on the timer.
     *
     * <p>
     *  创建{@code Timer}并初始化初始延迟和事件间延迟到{@code delay}毫秒。如果{@code delay}小于或等于零,则定时器在启动时立即激活。
     * 如果<code> listener </code>不是<code> null </code>,它被注册为计时器上的动作监听器。
     * 
     * 
     * @param delay milliseconds for the initial and between-event delay
     * @param listener  an initial listener; can be <code>null</code>
     *
     * @see #addActionListener
     * @see #setInitialDelay
     * @see #setRepeats
     */
    public Timer(int delay, ActionListener listener) {
        super();
        this.delay = delay;
        this.initialDelay = delay;

        doPostEvent = new DoPostEvent();

        if (listener != null) {
            addActionListener(listener);
        }
    }

    /*
     * The timer's AccessControlContext.
     * <p>
     *  定时器的AccessControlContext。
     * 
     */
     private transient volatile AccessControlContext acc =
            AccessController.getContext();

    /**
      * Returns the acc this timer was constructed with.
      * <p>
      *  返回此计时器构造的acc。
      * 
      */
     final AccessControlContext getAccessControlContext() {
       if (acc == null) {
           throw new SecurityException(
                   "Timer is missing AccessControlContext");
       }
       return acc;
     }

    /**
     * DoPostEvent is a runnable class that fires actionEvents to
     * the listeners on the EventDispatchThread, via invokeLater.
     * <p>
     *  DoPostEvent是一个可运行的类,通过invokeLater将eventEvents触发到EventDispatchThread上的侦听器。
     * 
     * 
     * @see Timer#post
     */
    class DoPostEvent implements Runnable
    {
        public void run() {
            if (logTimers) {
                System.out.println("Timer ringing: " + Timer.this);
            }
            if(notify.get()) {
                fireActionPerformed(new ActionEvent(Timer.this, 0, getActionCommand(),
                                                    System.currentTimeMillis(),
                                                    0));
                if (coalesce) {
                    cancelEvent();
                }
            }
        }

        Timer getTimer() {
            return Timer.this;
        }
    }

    /**
     * Adds an action listener to the <code>Timer</code>.
     *
     * <p>
     * 向<code> Timer </code>添加一个操作监听器。
     * 
     * 
     * @param listener the listener to add
     *
     * @see #Timer
     */
    public void addActionListener(ActionListener listener) {
        listenerList.add(ActionListener.class, listener);
    }


    /**
     * Removes the specified action listener from the <code>Timer</code>.
     *
     * <p>
     *  从<code> Timer </code>中删除指定的操作监听器。
     * 
     * 
     * @param listener the listener to remove
     */
    public void removeActionListener(ActionListener listener) {
        listenerList.remove(ActionListener.class, listener);
    }


    /**
     * Returns an array of all the action listeners registered
     * on this timer.
     *
     * <p>
     *  返回在此计时器上注册的所有操作侦听器的数组。
     * 
     * 
     * @return all of the timer's <code>ActionListener</code>s or an empty
     *         array if no action listeners are currently registered
     *
     * @see #addActionListener
     * @see #removeActionListener
     *
     * @since 1.4
     */
    public ActionListener[] getActionListeners() {
        return listenerList.getListeners(ActionListener.class);
    }


    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.
     *
     * <p>
     *  通知所有已注册有关此事件类型的通知的收件人。
     * 
     * 
     * @param e the action event to fire
     * @see EventListenerList
     */
    protected void fireActionPerformed(ActionEvent e) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();

        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i=listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==ActionListener.class) {
                ((ActionListener)listeners[i+1]).actionPerformed(e);
            }
        }
    }

    /**
     * Returns an array of all the objects currently registered as
     * <code><em>Foo</em>Listener</code>s
     * upon this <code>Timer</code>.
     * <code><em>Foo</em>Listener</code>s
     * are registered using the <code>add<em>Foo</em>Listener</code> method.
     * <p>
     * You can specify the <code>listenerType</code> argument
     * with a class literal, such as <code><em>Foo</em>Listener.class</code>.
     * For example, you can query a <code>Timer</code>
     * instance <code>t</code>
     * for its action listeners
     * with the following code:
     *
     * <pre>ActionListener[] als = (ActionListener[])(t.getListeners(ActionListener.class));</pre>
     *
     * If no such listeners exist,
     * this method returns an empty array.
     *
     * <p>
     *  返回当前在<code> Timer </code>上注册为<code> <em> Foo </em> Listener </code>的所有对象的数组。
     * 使用<code> add <em> </em>侦听器</code>方法注册<code> <em> </em>侦听器</code>。
     * <p>
     *  您可以使用类文字指定<code> listenerType </code>参数,例如<code> <em> Foo </em> Listener.class </code>。
     * 例如,您可以使用以下代码查询<code> Timer </code>实例<code> t </code>的操作侦听器：。
     * 
     *  <pre> ActionListener [] als =(ActionListener [])(t.getListeners(ActionListener.class)); </pre>
     * 
     *  如果不存在此类侦听器,则此方法将返回一个空数组。
     * 
     * 
     * @param listenerType  the type of listeners requested;
     *          this parameter should specify an interface
     *          that descends from <code>java.util.EventListener</code>
     * @return an array of all objects registered as
     *          <code><em>Foo</em>Listener</code>s
     *          on this timer,
     *          or an empty array if no such
     *          listeners have been added
     * @exception ClassCastException if <code>listenerType</code> doesn't
     *          specify a class or interface that implements
     *          <code>java.util.EventListener</code>
     *
     * @see #getActionListeners
     * @see #addActionListener
     * @see #removeActionListener
     *
     * @since 1.3
     */
    public <T extends EventListener> T[] getListeners(Class<T> listenerType) {
        return listenerList.getListeners(listenerType);
    }

    /**
     * Returns the timer queue.
     * <p>
     *  返回定时器队列。
     * 
     */
    private TimerQueue timerQueue() {
        return TimerQueue.sharedInstance();
    }


    /**
     * Enables or disables the timer log. When enabled, a message
     * is posted to <code>System.out</code> whenever the timer goes off.
     *
     * <p>
     *  启用或禁用定时器日志。当启用时,每当定时器关闭时,会向<code> System.out </code>发布一条消息。
     * 
     * 
     * @param flag  <code>true</code> to enable logging
     * @see #getLogTimers
     */
    public static void setLogTimers(boolean flag) {
        logTimers = flag;
    }


    /**
     * Returns <code>true</code> if logging is enabled.
     *
     * <p>
     *  如果启用日志记录,则返回<code> true </code>。
     * 
     * 
     * @return <code>true</code> if logging is enabled; otherwise, false
     * @see #setLogTimers
     */
    public static boolean getLogTimers() {
        return logTimers;
    }


    /**
     * Sets the <code>Timer</code>'s between-event delay, the number of milliseconds
     * between successive action events. This does not affect the initial delay
     * property, which can be set by the {@code setInitialDelay} method.
     *
     * <p>
     *  设置<code> Timer </code>的事件延迟,连续操作事件之间的毫秒数。这不影响初始延迟属性,可以通过{@code setInitialDelay}方法设置。
     * 
     * 
     * @param delay the delay in milliseconds
     * @see #setInitialDelay
     */
    public void setDelay(int delay) {
        if (delay < 0) {
            throw new IllegalArgumentException("Invalid delay: " + delay);
        }
        else {
            this.delay = delay;
        }
    }


    /**
     * Returns the delay, in milliseconds,
     * between firings of action events.
     *
     * <p>
     *  返回操作事件触发之间的延迟(以毫秒为单位)。
     * 
     * 
     * @see #setDelay
     * @see #getInitialDelay
     */
    public int getDelay() {
        return delay;
    }


    /**
     * Sets the <code>Timer</code>'s initial delay, the time
     * in milliseconds to wait after the timer is started
     * before firing the first event. Upon construction, this
     * is set to be the same as the between-event delay,
     * but then its value is independent and remains unaffected
     * by changes to the between-event delay.
     *
     * <p>
     * 设置<code> Timer </code>的初始延迟,在启动第一个事件之前启动计时器后等待的时间(以毫秒为单位)。
     * 在构造时,这被设置为与事件间延迟相同,但是其值是独立的,并且保持不受事件间延迟的改变的影响。
     * 
     * 
     * @param initialDelay the initial delay, in milliseconds
     * @see #setDelay
     */
    public void setInitialDelay(int initialDelay) {
        if (initialDelay < 0) {
            throw new IllegalArgumentException("Invalid initial delay: " +
                                               initialDelay);
        }
        else {
            this.initialDelay = initialDelay;
        }
    }


    /**
     * Returns the <code>Timer</code>'s initial delay.
     *
     * <p>
     *  返回<code> Timer </code>的初始延迟。
     * 
     * 
     * @see #setInitialDelay
     * @see #setDelay
     */
    public int getInitialDelay() {
        return initialDelay;
    }


    /**
     * If <code>flag</code> is <code>false</code>,
     * instructs the <code>Timer</code> to send only one
     * action event to its listeners.
     *
     * <p>
     *  如果<code> flag </code>是<code> false </code>,则指示<code> Timer </code>只向其侦听器发送一个操作事件。
     * 
     * 
     * @param flag specify <code>false</code> to make the timer
     *             stop after sending its first action event
     */
    public void setRepeats(boolean flag) {
        repeats = flag;
    }


    /**
     * Returns <code>true</code> (the default)
     * if the <code>Timer</code> will send
     * an action event
     * to its listeners multiple times.
     *
     * <p>
     *  如果<code> Timer </code>将多次向其侦听器发送一个操作事件,则返回<code> true </code>(默认值)。
     * 
     * 
     * @see #setRepeats
     */
    public boolean isRepeats() {
        return repeats;
    }


    /**
     * Sets whether the <code>Timer</code> coalesces multiple pending
     * <code>ActionEvent</code> firings.
     * A busy application may not be able
     * to keep up with a <code>Timer</code>'s event generation,
     * causing multiple
     * action events to be queued.  When processed,
     * the application sends these events one after the other, causing the
     * <code>Timer</code>'s listeners to receive a sequence of
     * events with no delay between them. Coalescing avoids this situation
     * by reducing multiple pending events to a single event.
     * <code>Timer</code>s
     * coalesce events by default.
     *
     * <p>
     *  设置<code> Timer </code>是否合并多个挂起<code> ActionEvent </code>触发。
     * 繁忙的应用程序可能无法跟上<code> Timer </code>的事件生成,导致多个操作事件排队。
     * 当处理时,应用程序一个接一个地发送这些事件,导致<code> Timer </code>的侦听器接收事件序列,它们之间没有延迟。合并通过将多个挂起事件减少到单个事件来避免这种情况。
     *  <code> Timer </code>的合并事件。
     * 
     * 
     * @param flag specify <code>false</code> to turn off coalescing
     */
    public void setCoalesce(boolean flag) {
        boolean old = coalesce;
        coalesce = flag;
        if (!old && coalesce) {
            // We must do this as otherwise if the Timer once notified
            // in !coalese mode notify will be stuck to true and never
            // become false.
            cancelEvent();
        }
    }


    /**
     * Returns <code>true</code> if the <code>Timer</code> coalesces
     * multiple pending action events.
     *
     * <p>
     *  如果<code> Timer </code>合并多个待处理操作事件,则返回<code> true </code>。
     * 
     * 
     * @see #setCoalesce
     */
    public boolean isCoalesce() {
        return coalesce;
    }


    /**
     * Sets the string that will be delivered as the action command
     * in <code>ActionEvent</code>s fired by this timer.
     * <code>null</code> is an acceptable value.
     *
     * <p>
     *  设置将在此计时器触发的<code> ActionEvent </code>中作为动作命令传递的字符串。 <code> null </code>是可接受的值。
     * 
     * 
     * @param command the action command
     * @since 1.6
     */
    public void setActionCommand(String command) {
        this.actionCommand = command;
    }


    /**
     * Returns the string that will be delivered as the action command
     * in <code>ActionEvent</code>s fired by this timer. May be
     * <code>null</code>, which is also the default.
     *
     * <p>
     * 返回此定时器触发的<code> ActionEvent </code>中作为动作命令传递的字符串。可能是<code> null </code>,这也是默认值。
     * 
     * 
     * @return the action command used in firing events
     * @since 1.6
     */
    public String getActionCommand() {
        return actionCommand;
    }


    /**
     * Starts the <code>Timer</code>,
     * causing it to start sending action events
     * to its listeners.
     *
     * <p>
     *  启动<code> Timer </code>,使其开始向其侦听器发送操作事件。
     * 
     * 
     * @see #stop
     */
     public void start() {
        timerQueue().addTimer(this, getInitialDelay());
    }


    /**
     * Returns <code>true</code> if the <code>Timer</code> is running.
     *
     * <p>
     *  如果<code> Timer </code>正在运行,则返回<code> true </code>。
     * 
     * 
     * @see #start
     */
    public boolean isRunning() {
        return timerQueue().containsTimer(this);
    }


    /**
     * Stops the <code>Timer</code>,
     * causing it to stop sending action events
     * to its listeners.
     *
     * <p>
     *  停止<code> Timer </code>,使其停止向其侦听器发送操作事件。
     * 
     * 
     * @see #start
     */
    public void stop() {
        getLock().lock();
        try {
            cancelEvent();
            timerQueue().removeTimer(this);
        } finally {
            getLock().unlock();
        }
    }


    /**
     * Restarts the <code>Timer</code>,
     * canceling any pending firings and causing
     * it to fire with its initial delay.
     * <p>
     *  重新启动<code> Timer </code>,取消任何挂起的触发,并使其以其初始延迟触发。
     * 
     */
    public void restart() {
        getLock().lock();
        try {
            stop();
            start();
        } finally {
            getLock().unlock();
        }
    }


    /**
     * Resets the internal state to indicate this Timer shouldn't notify
     * any of its listeners. This does not stop a repeatable Timer from
     * firing again, use <code>stop</code> for that.
     * <p>
     *  重置内部状态以指示此定时器不应通知其任何侦听器。这不会阻止可重复定时器再次触发,请使用<code> stop </code>。
     * 
     */
    void cancelEvent() {
        notify.set(false);
    }


    void post() {
         if (notify.compareAndSet(false, true) || !coalesce) {
             AccessController.doPrivileged(new PrivilegedAction<Void>() {
                 public Void run() {
                     SwingUtilities.invokeLater(doPostEvent);
                     return null;
                }
            }, getAccessControlContext());
        }
    }

    Lock getLock() {
        return lock;
    }

    private void readObject(ObjectInputStream in)
        throws ClassNotFoundException, IOException
    {
        this.acc = AccessController.getContext();
        in.defaultReadObject();
    }

    /*
     * We have to use readResolve because we can not initialize final
     * fields for deserialized object otherwise
     * <p>
     *  我们必须使用readResolve,因为我们不能为反序列化的对象初始化final字段
     */
    private Object readResolve() {
        Timer timer = new Timer(getDelay(), null);
        timer.listenerList = listenerList;
        timer.initialDelay = initialDelay;
        timer.delay = delay;
        timer.repeats = repeats;
        timer.coalesce = coalesce;
        timer.actionCommand = actionCommand;
        return timer;
    }
}
