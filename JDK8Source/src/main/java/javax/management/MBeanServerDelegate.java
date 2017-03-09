/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2008, Oracle and/or its affiliates. All rights reserved.
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

import com.sun.jmx.defaults.JmxProperties;
import com.sun.jmx.defaults.ServiceName;
import com.sun.jmx.mbeanserver.Util;

/**
 * Represents  the MBean server from the management point of view.
 * The MBeanServerDelegate MBean emits the MBeanServerNotifications when
 * an MBean is registered/unregistered in the MBean server.
 *
 * <p>
 *  从管理角度表示MBean服务器。当MBean在MBean服务器中注册/注销时,MBeanServerDelegate MBean会发出MBeanServer通知。
 * 
 * 
 * @since 1.5
 */
public class MBeanServerDelegate implements MBeanServerDelegateMBean,
                                            NotificationEmitter   {

    /** The MBean server agent identification.*/
    private String mbeanServerId ;

    /** The NotificationBroadcasterSupport object that sends the
    /* <p>
    /* 
        notifications */
    private final NotificationBroadcasterSupport broadcaster;

    private static long oldStamp = 0;
    private final long stamp;
    private long sequenceNumber = 1;

    private static final MBeanNotificationInfo[] notifsInfo;

    static {
        final String[] types  = {
            MBeanServerNotification.UNREGISTRATION_NOTIFICATION,
            MBeanServerNotification.REGISTRATION_NOTIFICATION
        };
        notifsInfo = new MBeanNotificationInfo[1];
        notifsInfo[0] =
            new MBeanNotificationInfo(types,
                    "javax.management.MBeanServerNotification",
                    "Notifications sent by the MBeanServerDelegate MBean");
    }

    /**
     * Create a MBeanServerDelegate object.
     * <p>
     *  创建MBeanServerDelegate对象。
     * 
     */
    public MBeanServerDelegate () {
        stamp = getStamp();
        broadcaster = new NotificationBroadcasterSupport() ;
    }


    /**
     * Returns the MBean server agent identity.
     *
     * <p>
     *  返回MBean服务器代理标识。
     * 
     * 
     * @return the identity.
     */
    public synchronized String getMBeanServerId() {
        if (mbeanServerId == null) {
            String localHost;
            try {
                localHost = java.net.InetAddress.getLocalHost().getHostName();
            } catch (java.net.UnknownHostException e) {
                JmxProperties.MISC_LOGGER.finest("Can't get local host name, " +
                        "using \"localhost\" instead. Cause is: "+e);
                localHost = "localhost";
            }
            mbeanServerId = localHost + "_" + stamp;
        }
        return mbeanServerId;
    }

    /**
     * Returns the full name of the JMX specification implemented
     * by this product.
     *
     * <p>
     *  返回此产品实现的JMX规范的全名。
     * 
     * 
     * @return the specification name.
     */
    public String getSpecificationName() {
        return ServiceName.JMX_SPEC_NAME;
    }

    /**
     * Returns the version of the JMX specification implemented
     * by this product.
     *
     * <p>
     *  返回此产品实现的JMX规范的版本。
     * 
     * 
     * @return the specification version.
     */
    public String getSpecificationVersion() {
        return ServiceName.JMX_SPEC_VERSION;
    }

    /**
     * Returns the vendor of the JMX specification implemented
     * by this product.
     *
     * <p>
     *  返回此产品实现的JMX规范的供应商。
     * 
     * 
     * @return the specification vendor.
     */
    public String getSpecificationVendor() {
        return ServiceName.JMX_SPEC_VENDOR;
    }

    /**
     * Returns the JMX implementation name (the name of this product).
     *
     * <p>
     *  返回JMX实现名称(此产品的名称)。
     * 
     * 
     * @return the implementation name.
     */
    public String getImplementationName() {
        return ServiceName.JMX_IMPL_NAME;
    }

    /**
     * Returns the JMX implementation version (the version of this product).
     *
     * <p>
     *  返回JMX实现版本(此产品的版本)。
     * 
     * 
     * @return the implementation version.
     */
    public String getImplementationVersion() {
        try {
            return System.getProperty("java.runtime.version");
        } catch (SecurityException e) {
            return "";
        }
    }

    /**
     * Returns the JMX implementation vendor (the vendor of this product).
     *
     * <p>
     *  返回JMX实现供应商(此产品的供应商)。
     * 
     * 
     * @return the implementation vendor.
     */
    public String getImplementationVendor()  {
        return ServiceName.JMX_IMPL_VENDOR;
    }

    // From NotificationEmitter extends NotificationBroacaster
    //
    public MBeanNotificationInfo[] getNotificationInfo() {
        final int len = MBeanServerDelegate.notifsInfo.length;
        final MBeanNotificationInfo[] infos =
        new MBeanNotificationInfo[len];
        System.arraycopy(MBeanServerDelegate.notifsInfo,0,infos,0,len);
        return infos;
    }

    // From NotificationEmitter extends NotificationBroacaster
    //
    public synchronized
        void addNotificationListener(NotificationListener listener,
                                     NotificationFilter filter,
                                     Object handback)
        throws IllegalArgumentException {
        broadcaster.addNotificationListener(listener,filter,handback) ;
    }

    // From NotificationEmitter extends NotificationBroacaster
    //
    public synchronized
        void removeNotificationListener(NotificationListener listener,
                                        NotificationFilter filter,
                                        Object handback)
        throws ListenerNotFoundException {
        broadcaster.removeNotificationListener(listener,filter,handback) ;
    }

    // From NotificationEmitter extends NotificationBroacaster
    //
    public synchronized
        void removeNotificationListener(NotificationListener listener)
        throws ListenerNotFoundException {
        broadcaster.removeNotificationListener(listener) ;
    }

    /**
     * Enables the MBean server to send a notification.
     * If the passed <var>notification</var> has a sequence number lesser
     * or equal to 0, then replace it with the delegate's own sequence
     * number.
     * <p>
     *  使MBean服务器能够发送通知。如果传递的<var>通知</var>具有小于或等于0的序列号,则将其替换为委托自己的序列号。
     * 
     * 
     * @param notification The notification to send.
     *
     */
    public void sendNotification(Notification notification) {
        if (notification.getSequenceNumber() < 1) {
            synchronized (this) {
                notification.setSequenceNumber(this.sequenceNumber++);
            }
        }
        broadcaster.sendNotification(notification);
    }

    /**
     * Defines the default ObjectName of the MBeanServerDelegate.
     *
     * <p>
     *  定义MBeanServerDelegate的默认ObjectName。
     * 
     * 
     * @since 1.6
     */
    public static final ObjectName DELEGATE_NAME =
            Util.newObjectName("JMImplementation:type=MBeanServerDelegate");

    /* Return a timestamp that is monotonically increasing even if
       System.currentTimeMillis() isn't (for example, if you call this
       constructor more than once in the same millisecond, or if the
       clock always returns the same value).  This means that the ids
       for a given JVM will always be distinact, though there is no
    /* <p>
    /*  System.currentTimeMillis()不是(例如,如果你在同一毫秒内多次调用此构造函数,或者时钟总是返回相同的值)。这意味着给定的JVM的id将总是相同的,虽然没有
    /* 
       such guarantee for two different JVMs.  */
    private static synchronized long getStamp() {
        long s = System.currentTimeMillis();
        if (oldStamp >= s) {
            s = oldStamp + 1;
        }
        oldStamp = s;
        return s;
    }
}
