/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2000, Oracle and/or its affiliates. All rights reserved.
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

package javax.naming.ldap;

/**
 * This class represents an event fired in response to an unsolicited
 * notification sent by the LDAP server.
 *
 * <p>
 *  此类表示响应LDAP服务器发送的主动通知而触发的事件。
 * 
 * 
 * @author Rosanna Lee
 * @author Scott Seligman
 * @author Vincent Ryan
 *
 * @see UnsolicitedNotification
 * @see UnsolicitedNotificationListener
 * @see javax.naming.event.EventContext#addNamingListener
 * @see javax.naming.event.EventDirContext#addNamingListener
 * @see javax.naming.event.EventContext#removeNamingListener
 * @since 1.3
 */

public class UnsolicitedNotificationEvent extends java.util.EventObject {
    /**
     * The notification that caused this event to be fired.
     * <p>
     *  导致触发此事件的通知。
     * 
     * 
     * @serial
     */
    private UnsolicitedNotification notice;

    /**
     * Constructs a new instance of <tt>UnsolicitedNotificationEvent</tt>.
     *
     * <p>
     *  构造一个<tt> UnsolicitedNotificationEvent </tt>的新实例。
     * 
     * 
     * @param src The non-null source that fired the event.
     * @param notice The non-null unsolicited notification.
     */
    public UnsolicitedNotificationEvent(Object src,
        UnsolicitedNotification notice) {
        super(src);
        this.notice = notice;
    }


    /**
     * Returns the unsolicited notification.
     * <p>
     *  返回主动通知。
     * 
     * 
     * @return The non-null unsolicited notification that caused this
     * event to be fired.
     */
    public UnsolicitedNotification getNotification() {
        return notice;
    }

    /**
     * Invokes the <tt>notificationReceived()</tt> method on
     * a listener using this event.
     * <p>
     *  使用此事件在侦听器上调用<tt> notificationReceived()</tt>方法。
     * 
     * @param listener The non-null listener on which to invoke
     * <tt>notificationReceived</tt>.
     */
    public void dispatch(UnsolicitedNotificationListener listener) {
        listener.notificationReceived(this);
    }

    private static final long serialVersionUID = -2382603380799883705L;
}
