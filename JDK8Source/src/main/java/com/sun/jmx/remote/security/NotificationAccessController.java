/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.jmx.remote.security;

import javax.management.Notification;
import javax.management.ObjectName;
import javax.security.auth.Subject;

/**
 * <p>This interface allows to control remote access to the
 * {@code addNotificationListener} and {@code removeNotificationListener}
 * methods when the notification listener parameter is of type
 * {@code NotificationListener} and also allows to control remote access
 * to the notifications being forwarded to the interested remote listeners.</p>
 *
 * <p>An implementation of this interface can be supplied to a
 * {@code JMXConnectorServer} in the environment map through the
 * {@code com.sun.jmx.remote.notification.access.controller}
 * environment map property.</p>
 *
 * <p>
 *  <p>当通知侦听器参数的类型为{@code NotificationListener}时,此接口可以控制对{@code addNotificationListener}和{@code removeNotificationListener}
 * 方法的远程访问,并且还允许控制对正在转发的通知的远程访问感兴趣的远程侦听器。
 * </p>。
 * 
 *  <p>此接口的实现可通过{@code com.sun.jmx.remote.notification.access.controller}环境映射属性提供给环境映射中的{@code JMXConnectorServer}
 * 。
 * </p>。
 * 
 * 
 * @since 1.6
 */
public interface NotificationAccessController {

    /**
     * This method is called when a remote
     * {@link javax.management.remote.JMXConnector} invokes the method
     * {@link javax.management.MBeanServerConnection#addNotificationListener(ObjectName,NotificationListener,NotificationFilter,Object)}.
     *
     * <p>
     *  当远程{@link javax.management.remote.JMXConnector}调用方法{@link javax.management.MBeanServerConnection#addNotificationListener(ObjectName,NotificationListener,NotificationFilter,Object)}
     * 时,将调用此方法。
     * 
     * 
     * @param connectionId the {@code connectionId} of the remote client
     * adding the listener.
     * @param name the name of the MBean where the listener is to be added.
     * @param subject the authenticated subject representing the remote client.
     *
     * @throws SecurityException if the remote client with the supplied
     * authenticated subject does not have the rights to add a listener
     * to the supplied MBean.
     */
    public void addNotificationListener(String connectionId,
                                        ObjectName name,
                                        Subject subject)
        throws SecurityException;

    /**
     * This method is called when a remote
     * {@link javax.management.remote.JMXConnector} invokes the method
     * {@link javax.management.MBeanServerConnection#removeNotificationListener(ObjectName,NotificationListener)}
     * or the method
     * {@link javax.management.MBeanServerConnection#removeNotificationListener(ObjectName,NotificationListener,NotificationFilter,Object)}.
     *
     * <p>
     *  当远程{@link javax.management.remote.JMXConnector}调用方法{@link javax.management.MBeanServerConnection#removeNotificationListener(ObjectName,NotificationListener)}
     * 或方法{@link javax.management.MBeanServerConnection#removeNotificationListener (ObjectName,NotificationListener,NotificationFilter,Object)}
     * 。
     * 
     * 
     * @param connectionId the {@code connectionId} of the remote client
     * removing the listener.
     * @param name the name of the MBean where the listener is to be removed.
     * @param subject the authenticated subject representing the remote client.
     *
     * @throws SecurityException if the remote client with the supplied
     * authenticated subject does not have the rights to remove a listener
     * from the supplied MBean.
     */
    public void removeNotificationListener(String connectionId,
                                           ObjectName name,
                                           Subject subject)
        throws SecurityException;

    /**
     * This method is called before the
     * {@link javax.management.remote.JMXConnectorServer}
     * forwards the notification to the interested remote
     * listener represented by the authenticated subject.
     *
     * <p>
     * 
     * @param connectionId the {@code connectionId} of the remote client
     * receiving the notification.
     * @param name the name of the MBean forwarding the notification.
     * @param notification the notification to be forwarded to the interested
     * remote listener.
     * @param subject the authenticated subject representing the remote client.
     *
     * @throws SecurityException if the remote client with
     * the supplied authenticated subject does not have the
     * rights to receive the notification.
     */
    public void fetchNotification(String connectionId,
                                  ObjectName name,
                                  Notification notification,
                                  Subject subject)
        throws SecurityException;
}
