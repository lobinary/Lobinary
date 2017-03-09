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


/**
 * Represents a notification emitted by the MBean Server through the MBeanServerDelegate MBean.
 * The MBean Server emits the following types of notifications: MBean registration, MBean
 * unregistration.
 * <P>
 * To receive MBeanServerNotifications, you need to register a listener with
 * the {@link MBeanServerDelegate MBeanServerDelegate} MBean
 * that represents the MBeanServer. The ObjectName of the MBeanServerDelegate is
 * {@link MBeanServerDelegate#DELEGATE_NAME}, which is
 * <CODE>JMImplementation:type=MBeanServerDelegate</CODE>.
 *
 * <p>The following code prints a message every time an MBean is registered
 * or unregistered in the MBean Server {@code mbeanServer}:</p>
 *
 * <pre>
 * private static final NotificationListener printListener = new NotificationListener() {
 *     public void handleNotification(Notification n, Object handback) {
 *         if (!(n instanceof MBeanServerNotification)) {
 *             System.out.println("Ignored notification of class " + n.getClass().getName());
 *             return;
 *         }
 *         MBeanServerNotification mbsn = (MBeanServerNotification) n;
 *         String what;
 *         if (n.getType().equals(MBeanServerNotification.REGISTRATION_NOTIFICATION))
 *             what = "MBean registered";
 *         else if (n.getType().equals(MBeanServerNotification.UNREGISTRATION_NOTIFICATION))
 *             what = "MBean unregistered";
 *         else
 *             what = "Unknown type " + n.getType();
 *         System.out.println("Received MBean Server notification: " + what + ": " +
 *                 mbsn.getMBeanName());
 *     }
 * };
 *
 * ...
 *     mbeanServer.addNotificationListener(
 *             MBeanServerDelegate.DELEGATE_NAME, printListener, null, null);
 * </pre>
 *
 * <p id="group">
 * An MBean which is not an {@link MBeanServerDelegate} may also emit
 * MBeanServerNotifications. In particular, there is a convention for
 * MBeans to emit an MBeanServerNotification for a group of MBeans.</p>
 *
 * <p>An MBeanServerNotification emitted to denote the registration or
 * unregistration of a group of MBeans has the following characteristics:
 * <ul><li>Its {@linkplain Notification#getType() notification type} is
 *     {@code "JMX.mbean.registered.group"} or
 *     {@code "JMX.mbean.unregistered.group"}, which can also be written {@link
 *     MBeanServerNotification#REGISTRATION_NOTIFICATION}{@code + ".group"} or
 *     {@link
 *     MBeanServerNotification#UNREGISTRATION_NOTIFICATION}{@code + ".group"}.
 * </li>
 * <li>Its {@linkplain #getMBeanName() MBean name} is an ObjectName pattern
 *     that selects the set (or a superset) of the MBeans being registered
 *     or unregistered</li>
 * <li>Its {@linkplain Notification#getUserData() user data} can optionally
 *     be set to an array of ObjectNames containing the names of all MBeans
 *     being registered or unregistered.</li>
 * </ul>
 *
 * <p>
 * MBeans which emit these group registration/unregistration notifications will
 * declare them in their {@link MBeanInfo#getNotifications()
 * MBeanNotificationInfo}.
 * </p>
 *
 * <p>
 *  表示MBean Server通过MBeanServerDelegate MBean发出的通知。 MBean服务器发出以下类型的通知：MBean注册,MBean注销。
 * <P>
 *  要接收MBeanServer通知,需要使用表示MBeanServer的{@link MBeanServerDelegate MBeanServerDelegate} MBean注册侦听器。
 *  MBeanServerDelegate的ObjectName是{@link MBeanServerDelegate#DELEGATE_NAME},即<CODE> JMImplementation：ty
 * pe = MBeanServerDelegate </CODE>。
 *  要接收MBeanServer通知,需要使用表示MBeanServer的{@link MBeanServerDelegate MBeanServerDelegate} MBean注册侦听器。
 * 
 *  <p>以下代码每次在MBean Server {@code mbeanServer}中注册或取消注册MBean时都会打印一条消息：</p>
 * 
 * <pre>
 * private void final NotificationListener printListener = new NotificationListener(){public void handleNotification(Notification n,Object handback){if(！instanceof MBeanServerNotification)){System.out.println("类忽略通知"+ n.getClass .getName());返回; }
 *  MBeanServerNotification mbsn =(MBeanServerNotification)n;字符串什么? if(n.getType()。
 * equals(MBeanServerNotification.REGISTRATION_NOTIFICATION))what ="MBean registered"; else if(n.getType
 * ()。
 * equals(MBeanServerNotification.UNREGISTRATION_NOTIFICATION))what ="MBean unregistered"; else what ="未
 * 知类型"+ n.getType(); System.out.println("Received MBean Server notification："+ what +"："+ mbsn.getMBean
 * Name()); }};。
 * 
 *  ... mbeanServer.addNotificationListener(MBeanServerDelegate.DELEGATE_NAME,printListener,null,null);。
 * </pre>
 * 
 * <p id="group">
 *  不是{@link MBeanServerDelegate}的MBean也可以发出MBeanServer通知。
 * 特别是,有一个约定MBean为一组MBean发出MBeanServerNotification。</p>。
 * 
 * 
 * @since 1.5
 */
public class MBeanServerNotification extends Notification {


    /* Serial version */
    private static final long serialVersionUID = 2876477500475969677L;
    /**
     * Notification type denoting that an MBean has been registered.
     * Value is "JMX.mbean.registered".
     * <p>
     * <p>发出以表示一组MBean的注册或注销的MBeanServer通知具有以下特征：<ul> <li>其{@linkplain Notification#getType()通知类型}是{@code"JMX.mbean.registered .group"}
     * 或{@code"JMX.mbean.unregistered.group"},也可以写为{@link MBeanServerNotification#REGISTRATION_NOTIFICATION}
     *  {@ code +".group"}或{@link MBeanServerNotification#UNREGISTRATION_NOTIFICATION} code +".group"}。
     * </li>
     *  <li>其{@linkplain #getMBeanName()MBean name}是一个ObjectName模式,用于选择要注册或未注册的MBean的集合(或超集)。
     * <li>其{@linkplain Notification#getUserData用户数据}可以可选地设置为包含正在注册或未注册的所有MBean的名称的ObjectName的数组。</li>。
     * </ul>
     * 
     * <p>
     *  发出这些组注册/注销通知的MBean将在其{@link MBeanInfo#getNotifications()MBeanNotificationInfo}中声明它们。
     * </p>
     * 
     */
    public static final String REGISTRATION_NOTIFICATION =
            "JMX.mbean.registered";
    /**
     * Notification type denoting that an MBean has been unregistered.
     * Value is "JMX.mbean.unregistered".
     * <p>
     *  表示MBean已注册的通知类型。值为"JMX.mbean.registered"。
     * 
     */
    public static final String UNREGISTRATION_NOTIFICATION =
            "JMX.mbean.unregistered";
    /**
    /* <p>
    /*  通知类型,表示MBean已取消注册。值为"JMX.mbean.unregistered"。
    /* 
    /* 
     * @serial The object names of the MBeans concerned by this notification
     */
    private final ObjectName objectName;

    /**
     * Creates an MBeanServerNotification object specifying object names of
     * the MBeans that caused the notification and the specified notification
     * type.
     *
     * <p>
     * 
     * @param type A string denoting the type of the
     * notification. Set it to one these values: {@link
     * #REGISTRATION_NOTIFICATION}, {@link
     * #UNREGISTRATION_NOTIFICATION}.
     * @param source The MBeanServerNotification object responsible
     * for forwarding MBean server notification.
     * @param sequenceNumber A sequence number that can be used to order
     * received notifications.
     * @param objectName The object name of the MBean that caused the
     * notification.
     *
     */
    public MBeanServerNotification(String type, Object source,
            long sequenceNumber, ObjectName objectName) {
        super(type, source, sequenceNumber);
        this.objectName = objectName;
    }

    /**
     * Returns the  object name of the MBean that caused the notification.
     *
     * <p>
     *  创建指定导致通知和指定的通知类型的MBean的对象名称的MBeanServerNotification对象。
     * 
     * 
     * @return the object name of the MBean that caused the notification.
     */
    public ObjectName getMBeanName() {
        return objectName;
    }

    @Override
    public String toString() {
        return super.toString() + "[mbeanName=" + objectName + "]";

    }

 }
