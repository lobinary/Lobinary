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

package javax.management;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;

import com.sun.jmx.remote.util.ClassLogger;

/**
 * <p>Provides an implementation of {@link
 * javax.management.NotificationEmitter NotificationEmitter}
 * interface.  This can be used as the super class of an MBean that
 * sends notifications.</p>
 *
 * <p>By default, the notification dispatch model is synchronous.
 * That is, when a thread calls sendNotification, the
 * <code>NotificationListener.handleNotification</code> method of each listener
 * is called within that thread. You can override this default
 * by overriding <code>handleNotification</code> in a subclass, or by passing an
 * Executor to the constructor.</p>
 *
 * <p>If the method call of a filter or listener throws an {@link Exception},
 * then that exception does not prevent other listeners from being invoked.  However,
 * if the method call of a filter or of {@code Executor.execute} or of
 * {@code handleNotification} (when no {@code Excecutor} is specified) throws an
 * {@link Error}, then that {@code Error} is propagated to the caller of
 * {@link #sendNotification sendNotification}.</p>
 *
 * <p>Remote listeners added using the JMX Remote API (see JMXConnector) are not
 * usually called synchronously.  That is, when sendNotification returns, it is
 * not guaranteed that any remote listeners have yet received the notification.</p>
 *
 * <p>
 *  <p>提供{@link javax.management.NotificationEmitter NotificationEmitter}接口的实现。这可以用作发送通知的MBean的超类。</p>
 * 
 *  <p>默认情况下,通知调度模型是同步的。
 * 也就是说,当线程调用sendNotification时,在该线程内调用每个侦听器的<code> NotificationListener.handleNotification </code>方法。
 * 您可以通过在子类中覆盖<code> handleNotification </code>来覆盖此默认值,或者通过将Executor传递给构造函数。</p>。
 * 
 *  <p>如果过滤器或侦听器的方法调用引发{@link Exception},那么该异常不会阻止调用其他侦听器。
 * 但是,如果调用过滤器或{@code Executor.execute}或{@code handleNotification}(当没有指定{@code Excecutor}时)会抛出一个{@link Error}
 * ,那么{@code Error }传播给{@link #sendNotification sendNotification}的调用者。
 *  <p>如果过滤器或侦听器的方法调用引发{@link Exception},那么该异常不会阻止调用其他侦听器。</p>。
 * 
 *  <p>使用JMX Remote API添加的远程侦听器(请参阅JMXConnector)通常不会同步调用。也就是说,当sendNotification返回时,不能保证任何远程侦听器都已收到通知。
 * </p>。
 * 
 * 
 * @since 1.5
 */
public class NotificationBroadcasterSupport implements NotificationEmitter {
    /**
     * Constructs a NotificationBroadcasterSupport where each listener is invoked by the
     * thread sending the notification. This constructor is equivalent to
     * {@link NotificationBroadcasterSupport#NotificationBroadcasterSupport(Executor,
     * MBeanNotificationInfo[] info) NotificationBroadcasterSupport(null, null)}.
     * <p>
     * 构造NotificationBroadcasterSupport,其中每个侦听器由发送通知的线程调用。
     * 此构造函数等效于{@link NotificationBroadcasterSupport#NotificationBroadcasterSupport(Executor,MBeanNotificationInfo [] info)NotificationBroadcasterSupport(null,null)}
     * 。
     * 构造NotificationBroadcasterSupport,其中每个侦听器由发送通知的线程调用。
     * 
     */
    public NotificationBroadcasterSupport() {
        this(null, (MBeanNotificationInfo[]) null);
    }

    /**
     * Constructs a NotificationBroadcasterSupport where each listener is invoked using
     * the given {@link java.util.concurrent.Executor}. When {@link #sendNotification
     * sendNotification} is called, a listener is selected if it was added with a null
     * {@link NotificationFilter}, or if {@link NotificationFilter#isNotificationEnabled
     * isNotificationEnabled} returns true for the notification being sent. The call to
     * <code>NotificationFilter.isNotificationEnabled</code> takes place in the thread
     * that called <code>sendNotification</code>. Then, for each selected listener,
     * {@link Executor#execute executor.execute} is called with a command
     * that calls the <code>handleNotification</code> method.
     * This constructor is equivalent to
     * {@link NotificationBroadcasterSupport#NotificationBroadcasterSupport(Executor,
     * MBeanNotificationInfo[] info) NotificationBroadcasterSupport(executor, null)}.
     * <p>
     *  构造一个NotificationBroadcasterSupport,其中使用给定的{@link java.util.concurrent.Executor}调用每个侦听器。
     * 调用{@link #sendNotification sendNotification}时,如果添加了一个空{@link NotificationFilter},或者如果{@link NotificationFilter#isNotificationEnabled isNotificationEnabled}
     * 对于正在发送的通知返回true,则选择侦听器。
     *  构造一个NotificationBroadcasterSupport,其中使用给定的{@link java.util.concurrent.Executor}调用每个侦听器。
     * 对<code> NotificationFilter.isNotificationEnabled </code>的调用发生在调用<code> sendNotification </code>的线程中。
     * 然后,对于每个选择的侦听器,使用调用<code> handleNotification </code>方法的命令调用{@link Executor#execute executor.execute}。
     * 这个构造函数等同于{@link NotificationBroadcasterSupport#NotificationBroadcasterSupport(Executor,MBeanNotificationInfo [] info)NotificationBroadcasterSupport(executor,null)}
     * 。
     * 然后,对于每个选择的侦听器,使用调用<code> handleNotification </code>方法的命令调用{@link Executor#execute executor.execute}。
     * 
     * 
     * @param executor an executor used by the method <code>sendNotification</code> to
     * send each notification. If it is null, the thread calling <code>sendNotification</code>
     * will invoke the <code>handleNotification</code> method itself.
     * @since 1.6
     */
    public NotificationBroadcasterSupport(Executor executor) {
        this(executor, (MBeanNotificationInfo[]) null);
    }

    /**
     * <p>Constructs a NotificationBroadcasterSupport with information
     * about the notifications that may be sent.  Each listener is
     * invoked by the thread sending the notification.  This
     * constructor is equivalent to {@link
     * NotificationBroadcasterSupport#NotificationBroadcasterSupport(Executor,
     * MBeanNotificationInfo[] info)
     * NotificationBroadcasterSupport(null, info)}.</p>
     *
     * <p>If the <code>info</code> array is not empty, then it is
     * cloned by the constructor as if by {@code info.clone()}, and
     * each call to {@link #getNotificationInfo()} returns a new
     * clone.</p>
     *
     * <p>
     * <p>使用有关可能发送的通知的信息构造NotificationBroadcasterSupport。每个监听器由发送通知的线程调用。
     * 此构造函数等效于{@link NotificationBroadcasterSupport#NotificationBroadcasterSupport(Executor,MBeanNotificationInfo [] info)NotificationBroadcasterSupport(null,info)}
     * 。
     * <p>使用有关可能发送的通知的信息构造NotificationBroadcasterSupport。每个监听器由发送通知的线程调用。</p>。
     * 
     *  <p>如果<code> info </code>数组不为空,那么它将被构造函数克隆,如同通过{@code info.clone()},并且每次调用{@link #getNotificationInfo返回一个新的克隆。
     * </p>。
     * 
     * 
     * @param info an array indicating, for each notification this
     * MBean may send, the name of the Java class of the notification
     * and the notification type.  Can be null, which is equivalent to
     * an empty array.
     *
     * @since 1.6
     */
    public NotificationBroadcasterSupport(MBeanNotificationInfo... info) {
        this(null, info);
    }

    /**
     * <p>Constructs a NotificationBroadcasterSupport with information about the notifications that may be sent,
     * and where each listener is invoked using the given {@link java.util.concurrent.Executor}.</p>
     *
     * <p>When {@link #sendNotification sendNotification} is called, a
     * listener is selected if it was added with a null {@link
     * NotificationFilter}, or if {@link
     * NotificationFilter#isNotificationEnabled isNotificationEnabled}
     * returns true for the notification being sent. The call to
     * <code>NotificationFilter.isNotificationEnabled</code> takes
     * place in the thread that called
     * <code>sendNotification</code>. Then, for each selected
     * listener, {@link Executor#execute executor.execute} is called
     * with a command that calls the <code>handleNotification</code>
     * method.</p>
     *
     * <p>If the <code>info</code> array is not empty, then it is
     * cloned by the constructor as if by {@code info.clone()}, and
     * each call to {@link #getNotificationInfo()} returns a new
     * clone.</p>
     *
     * <p>
     *  <p>构造一个NotificationBroadcasterSupport,其中包含可能发送的通知的信息,以及使用给定的{@link java.util.concurrent.Executor}调用每
     * 个侦听器。
     * </p>。
     * 
     *  <p>调用{@link #sendNotification sendNotification}时,如果添加了一个空的{@link NotificationFilter},或者如果{@link NotificationFilter#isNotificationEnabled isNotificationEnabled}
     * 对于正在发送的通知返回true,则会选择侦听器。
     * 对<code> NotificationFilter.isNotificationEnabled </code>的调用发生在调用<code> sendNotification </code>的线程中。
     * 然后,对于每个选择的侦听器,使用调用<code> handleNotification </code>方法的命令调用{@link Executor#execute executor.execute}。
     * </p>。
     * 
     * <p>如果<code> info </code>数组不为空,那么它将被构造函数克隆,如同通过{@code info.clone()},并且每次调用{@link #getNotificationInfo返回一个新的克隆。
     * </p>。
     * 
     * 
     * @param executor an executor used by the method
     * <code>sendNotification</code> to send each notification. If it
     * is null, the thread calling <code>sendNotification</code> will
     * invoke the <code>handleNotification</code> method itself.
     *
     * @param info an array indicating, for each notification this
     * MBean may send, the name of the Java class of the notification
     * and the notification type.  Can be null, which is equivalent to
     * an empty array.
     *
     * @since 1.6
     */
    public NotificationBroadcasterSupport(Executor executor,
                                          MBeanNotificationInfo... info) {
        this.executor = (executor != null) ? executor : defaultExecutor;

        notifInfo = info == null ? NO_NOTIFICATION_INFO : info.clone();
    }

    /**
     * Adds a listener.
     *
     * <p>
     *  添加侦听器。
     * 
     * 
     * @param listener The listener to receive notifications.
     * @param filter The filter object. If filter is null, no
     * filtering will be performed before handling notifications.
     * @param handback An opaque object to be sent back to the
     * listener when a notification is emitted. This object cannot be
     * used by the Notification broadcaster object. It should be
     * resent unchanged with the notification to the listener.
     *
     * @exception IllegalArgumentException thrown if the listener is null.
     *
     * @see #removeNotificationListener
     */
    public void addNotificationListener(NotificationListener listener,
                                        NotificationFilter filter,
                                        Object handback) {

        if (listener == null) {
            throw new IllegalArgumentException ("Listener can't be null") ;
        }

        listenerList.add(new ListenerInfo(listener, filter, handback));
    }

    public void removeNotificationListener(NotificationListener listener)
            throws ListenerNotFoundException {

        ListenerInfo wildcard = new WildcardListenerInfo(listener);
        boolean removed =
            listenerList.removeAll(Collections.singleton(wildcard));
        if (!removed)
            throw new ListenerNotFoundException("Listener not registered");
    }

    public void removeNotificationListener(NotificationListener listener,
                                           NotificationFilter filter,
                                           Object handback)
            throws ListenerNotFoundException {

        ListenerInfo li = new ListenerInfo(listener, filter, handback);
        boolean removed = listenerList.remove(li);
        if (!removed) {
            throw new ListenerNotFoundException("Listener not registered " +
                                                "(with this filter and " +
                                                "handback)");
            // or perhaps not registered at all
        }
    }

    public MBeanNotificationInfo[] getNotificationInfo() {
        if (notifInfo.length == 0)
            return notifInfo;
        else
            return notifInfo.clone();
    }


    /**
     * Sends a notification.
     *
     * If an {@code Executor} was specified in the constructor, it will be given one
     * task per selected listener to deliver the notification to that listener.
     *
     * <p>
     *  发送通知。
     * 
     *  如果在构造函数中指定了{@code Executor},那么每个选定的侦听器将被赋予一个任务,以将通知传递给该侦听器。
     * 
     * 
     * @param notification The notification to send.
     */
    public void sendNotification(Notification notification) {

        if (notification == null) {
            return;
        }

        boolean enabled;

        for (ListenerInfo li : listenerList) {
            try {
                enabled = li.filter == null ||
                    li.filter.isNotificationEnabled(notification);
            } catch (Exception e) {
                if (logger.debugOn()) {
                    logger.debug("sendNotification", e);
                }

                continue;
            }

            if (enabled) {
                executor.execute(new SendNotifJob(notification, li));
            }
        }
    }

    /**
     * <p>This method is called by {@link #sendNotification
     * sendNotification} for each listener in order to send the
     * notification to that listener.  It can be overridden in
     * subclasses to change the behavior of notification delivery,
     * for instance to deliver the notification in a separate
     * thread.</p>
     *
     * <p>The default implementation of this method is equivalent to
     * <pre>
     * listener.handleNotification(notif, handback);
     * </pre>
     *
     * <p>
     *  <p>此方法由{@link #sendNotification sendNotification}为每个侦听器调用,以便将通知发送给该侦听器。
     * 它可以在子类中重写以更改通知传递的行为,例如在单独的线程中传递通知。</p>。
     * 
     *  <p>此方法的默认实现等同于
     * 
     * @param listener the listener to which the notification is being
     * delivered.
     * @param notif the notification being delivered to the listener.
     * @param handback the handback object that was supplied when the
     * listener was added.
     *
     */
    protected void handleNotification(NotificationListener listener,
                                      Notification notif, Object handback) {
        listener.handleNotification(notif, handback);
    }

    // private stuff
    private static class ListenerInfo {
        NotificationListener listener;
        NotificationFilter filter;
        Object handback;

        ListenerInfo(NotificationListener listener,
                     NotificationFilter filter,
                     Object handback) {
            this.listener = listener;
            this.filter = filter;
            this.handback = handback;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof ListenerInfo))
                return false;
            ListenerInfo li = (ListenerInfo) o;
            if (li instanceof WildcardListenerInfo)
                return (li.listener == listener);
            else
                return (li.listener == listener && li.filter == filter
                        && li.handback == handback);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(listener);
        }
    }

    private static class WildcardListenerInfo extends ListenerInfo {
        WildcardListenerInfo(NotificationListener listener) {
            super(listener, null, null);
        }

        @Override
        public boolean equals(Object o) {
            assert (!(o instanceof WildcardListenerInfo));
            return o.equals(this);
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }
    }

    private List<ListenerInfo> listenerList =
        new CopyOnWriteArrayList<ListenerInfo>();

    // since 1.6
    private final Executor executor;
    private final MBeanNotificationInfo[] notifInfo;

    private final static Executor defaultExecutor = new Executor() {
            // DirectExecutor using caller thread
            public void execute(Runnable r) {
                r.run();
            }
        };

    private static final MBeanNotificationInfo[] NO_NOTIFICATION_INFO =
        new MBeanNotificationInfo[0];

    private class SendNotifJob implements Runnable {
        public SendNotifJob(Notification notif, ListenerInfo listenerInfo) {
            this.notif = notif;
            this.listenerInfo = listenerInfo;
        }

        public void run() {
            try {
                handleNotification(listenerInfo.listener,
                                   notif, listenerInfo.handback);
            } catch (Exception e) {
                if (logger.debugOn()) {
                    logger.debug("SendNotifJob-run", e);
                }
            }
        }

        private final Notification notif;
        private final ListenerInfo listenerInfo;
    }

    private static final ClassLogger logger =
        new ClassLogger("javax.management", "NotificationBroadcasterSupport");
}
