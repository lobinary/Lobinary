/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, 2007, Oracle and/or its affiliates. All rights reserved.
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
 *<p>When an MBean emits a notification, it considers each listener that has been
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
 * <p>This interface should be used by new code in preference to the
 * {@link NotificationBroadcaster} interface.</p>
 *
 * <p>Implementations of this interface and of {@code NotificationBroadcaster}
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
 *  p>当MBean发出通知时,它会考虑已添加{@link #addNotificationListener addNotificationListener}的每个侦听器,随后不会使用{@link #removeNotificationListener removeNotificationListener}
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
 * @since 1.5
 */
public interface NotificationEmitter extends NotificationBroadcaster {
    /**
     * <p>Removes a listener from this MBean.  The MBean must have a
     * listener that exactly matches the given <code>listener</code>,
     * <code>filter</code>, and <code>handback</code> parameters.  If
     * there is more than one such listener, only one is removed.</p>
     *
     * <p>The <code>filter</code> and <code>handback</code> parameters
     * may be null if and only if they are null in a listener to be
     * removed.</p>
     *
     * <p>
     * 
     * <p>如果过滤器或侦听器的方法调用抛出{@link Exception},那么该异常不应阻止其他侦听器被调用。
     * 但是,如果方法调用抛出一个{@link错误},那么建议通知的处理停止在那一点,如果可能传播{@code错误}到通知的发件人,这应该完成。</p>。
     * 
     *  <p>此接口应由新代码优先于{@link NotificationBroadcaster}接口使用。</p>
     * 
     *  <p>此接口和{@code NotificationBroadcaster}的实现应小心同步。特别地,实现在调用侦听器时保持任何锁并不是个好主意。
     * 为了处理在分派通知时监听器列表可能改变的可能性,一个好的策略是对此列表使用{@link CopyOnWriteArrayList}。
     * 
     * @param listener A listener that was previously added to this
     * MBean.
     * @param filter The filter that was specified when the listener
     * was added.
     * @param handback The handback that was specified when the listener was
     * added.
     *
     * @exception ListenerNotFoundException The listener is not
     * registered with the MBean, or it is not registered with the
     * given filter and handback.
     */
    public void removeNotificationListener(NotificationListener listener,
                                           NotificationFilter filter,
                                           Object handback)
            throws ListenerNotFoundException;
}
