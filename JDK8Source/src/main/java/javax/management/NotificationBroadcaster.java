/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2005, Oracle and/or its affiliates. All rights reserved.
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

import java.util.concurrent.CopyOnWriteArrayList;  // for Javadoc

/**
 * <p>Interface implemented by an MBean that emits Notifications. It
 * allows a listener to be registered with the MBean as a notification
 * listener.</p>
 *
 * <h3>Notification dispatch</h3>
 *
 * <p>When an MBean emits a notification, it considers each listener that has been
 * added with {@link #addNotificationListener addNotificationListener} and not
 * subsequently removed with {@link #removeNotificationListener removeNotificationListener}.
 * If a filter was provided with that listener, and if the filter's
 * {@link NotificationFilter#isNotificationEnabled isNotificationEnabled} method returns
 * false, the listener is ignored.  Otherwise, the listener's
 * {@link NotificationListener#handleNotification handleNotification} method is called with
 * the notification, as well as the handback object that was provided to
 * {@code addNotificationListener}.</p>
 *
 * <p>If the same listener is added more than once, it is considered as many times as it was
 * added.  It is often useful to add the same listener with different filters or handback
 * objects.</p>
 *
 * <p>Implementations of this interface can differ regarding the thread in which the methods
 * of filters and listeners are called.</p>
 *
 * <p>If the method call of a filter or listener throws an {@link Exception}, then that
 * exception should not prevent other listeners from being invoked.  However, if the method
 * call throws an {@link Error}, then it is recommended that processing of the notification
 * stop at that point, and if it is possible to propagate the {@code Error} to the sender of
 * the notification, this should be done.</p>
 *
 * <p>New code should use the {@link NotificationEmitter} interface
 * instead.</p>
 *
 * <p>Implementations of this interface and of {@code NotificationEmitter}
 * should be careful about synchronization.  In particular, it is not a good
 * idea for an implementation to hold any locks while it is calling a
 * listener.  To deal with the possibility that the list of listeners might
 * change while a notification is being dispatched, a good strategy is to
 * use a {@link CopyOnWriteArrayList} for this list.
 *
 * <p>
 *  <p>由发出通知的MBean实现的接口。它允许将侦听器作为通知侦听器注册到MBean。</p>
 * 
 *  <h3>通知调度</h3>
 * 
 *  <p>当MBean发出通知时,它会考虑已添加{@link #addNotificationListener addNotificationListener}的每个侦听器,随后不会使用{@link #removeNotificationListener removeNotificationListener}
 * 删除。
 * 如果此侦听器提供了过滤器,并且过滤器的{@link NotificationFilter#isNotificationEnabled isNotificationEnabled}方法返回false,则会
 * 忽略侦听器。
 * 否则,将使用通知调用侦听器的{@link NotificationListener#handleNotification handleNotification}方法以及提供给{@code addNotificationListener}
 * 的handback对象。
 * </p>。
 * 
 *  <p>如果同一个收听器被添加了多次,它被认为是添加的次数。使用不同的过滤器或回传对象添加相同的侦听器通常很有用。</p>
 * 
 *  <p>此接口的实现方式可能与调用过滤器和侦听器的方法的线程不同。</p>
 * 
 * <p>如果过滤器或侦听器的方法调用抛出{@link Exception},那么该异常不应阻止其他侦听器被调用。
 * 但是,如果方法调用抛出一个{@link错误},那么建议通知的处理停止在那一点,如果可能传播{@code错误}到通知的发件人,这应该完成。</p>。
 * 
 * @since 1.5
 */
public interface NotificationBroadcaster {

    /**
     * Adds a listener to this MBean.
     *
     * <p>
     * 
     *  <p>新代码应改用{@link NotificationEmitter}接口。</p>
     * 
     *  <p>此接口和{@code NotificationEmitter}的实现应小心同步。特别地,实现在调用侦听器时保持任何锁并不是个好主意。
     * 为了处理在分派通知时监听器列表可能改变的可能性,一个好的策略是对此列表使用{@link CopyOnWriteArrayList}。
     * 
     * 
     * @param listener The listener object which will handle the
     * notifications emitted by the broadcaster.
     * @param filter The filter object. If filter is null, no
     * filtering will be performed before handling notifications.
     * @param handback An opaque object to be sent back to the
     * listener when a notification is emitted. This object cannot be
     * used by the Notification broadcaster object. It should be
     * resent unchanged with the notification to the listener.
     *
     * @exception IllegalArgumentException Listener parameter is null.
     *
     * @see #removeNotificationListener
     */
    public void addNotificationListener(NotificationListener listener,
                                        NotificationFilter filter,
                                        Object handback)
            throws java.lang.IllegalArgumentException;

    /**
     * Removes a listener from this MBean.  If the listener
     * has been registered with different handback objects or
     * notification filters, all entries corresponding to the listener
     * will be removed.
     *
     * <p>
     *  向此MBean添加侦听器。
     * 
     * 
     * @param listener A listener that was previously added to this
     * MBean.
     *
     * @exception ListenerNotFoundException The listener is not
     * registered with the MBean.
     *
     * @see #addNotificationListener
     * @see NotificationEmitter#removeNotificationListener
     */
    public void removeNotificationListener(NotificationListener listener)
            throws ListenerNotFoundException;

    /**
     * <p>Returns an array indicating, for each notification this
     * MBean may send, the name of the Java class of the notification
     * and the notification type.</p>
     *
     * <p>It is not illegal for the MBean to send notifications not
     * described in this array.  However, some clients of the MBean
     * server may depend on the array being complete for their correct
     * functioning.</p>
     *
     * <p>
     *  从此MBean中删除侦听器。如果侦听器已注册了不同的手动对象或通知过滤器,则与侦听器对应的所有条目将被删除。
     * 
     * 
     * @return the array of possible notifications.
     */
    public MBeanNotificationInfo[] getNotificationInfo();
}
