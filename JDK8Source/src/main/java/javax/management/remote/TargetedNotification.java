/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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


package javax.management.remote;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import javax.management.Notification;

/**
 * <p>A (Notification, Listener ID) pair.</p>
 * <p>This class is used to associate an emitted notification
 *    with the listener ID to which it is targeted.</p>
 *
 * <p>
 *  <p> A(通知,侦听器ID)对。</p> <p>此类用于将发出的通知与其定位的侦听器ID相关联。</p>
 * 
 * 
 * @since 1.5
 */
public class TargetedNotification implements Serializable {

    private static final long serialVersionUID = 7676132089779300926L;

// If we replace Integer with int...
//     /**
//      * <p>Constructs a <code>TargetedNotification</code> object.  The
//      * object contains a pair (Notification, Listener ID).
//      * The Listener ID identifies the client listener to which that
//      * notification is targeted. The client listener ID is one
//      * previously returned by the connector server in response to an
//      * <code>addNotificationListener</code> request.</p>
//      * <p>
//      *  // * <p>构造一个<code> TargetedNotification </code>对象。 // *对象包含一个对(Notification,Listener ID)。
//      *  // *侦听器ID标识// *通知所针对的客户端侦听器。
//      * 客户端侦听器ID是连接器服务器响应于// * <code> addNotificationListener </code>请求而先前返回的一个// *。</p>。
//      * 
//      * 
//      * @param notification Notification emitted from the MBean server.
//      * @param listenerID   The ID of the listener to which this
//      *        notification is targeted.
//      */
//     public TargetedNotification(Notification notification,
//                              int listenerID) {
//      this.notif = notification;
//      this.id = listenerID;
//     }

    /**
     * <p>Constructs a <code>TargetedNotification</code> object.  The
     * object contains a pair (Notification, Listener ID).
     * The Listener ID identifies the client listener to which that
     * notification is targeted. The client listener ID is one
     * previously returned by the connector server in response to an
     * <code>addNotificationListener</code> request.</p>
     * <p>
     *  <p>构造一个<code> TargetedNotification </code>对象。对象包含一个对(通知,侦听器ID)。侦听器ID标识该通知所针对的客户端侦听器。
     * 客户端侦听器ID是由连接器服务器先前为响应<code> addNotificationListener </code>请求而返回的一个。</p>。
     * 
     * 
     * @param notification Notification emitted from the MBean server.
     * @param listenerID   The ID of the listener to which this
     *        notification is targeted.
     * @exception IllegalArgumentException if the <var>listenerID</var>
     *        or <var>notification</var> is null.
     */
    public TargetedNotification(Notification notification,
                                Integer listenerID) {
        validate(notification, listenerID);
        // If we replace integer with int...
        // this(notification,intValue(listenerID));
        this.notif = notification;
        this.id = listenerID;
    }

    /**
     * <p>The emitted notification.</p>
     *
     * <p>
     *  <p>发出的通知。</p>
     * 
     * 
     * @return The notification.
     */
    public Notification getNotification() {
        return notif;
    }

    /**
     * <p>The ID of the listener to which the notification is
     *    targeted.</p>
     *
     * <p>
     *  <p>通知所针对的侦听器的ID。</p>
     * 
     * 
     * @return The listener ID.
     */
    public Integer getListenerID() {
        return id;
    }

    /**
     * Returns a textual representation of this Targeted Notification.
     *
     * <p>
     *  返回此定位通知的文本表示。
     * 
     * 
     * @return a String representation of this Targeted Notification.
     **/
    public String toString() {
        return "{" + notif + ", " + id + "}";
    }

    /**
    /* <p>
    /* 
     * @serial A notification to transmit to the other side.
     * @see #getNotification()
     **/
    private Notification notif;
    /**
    /* <p>
    /* 
     * @serial The ID of the listener to which the notification is
     *         targeted.
     * @see #getListenerID()
     **/
    private Integer id;
    //private final int id;

// Needed if we use int instead of Integer...
//     private static int intValue(Integer id) {
//      if (id == null) throw new
//          IllegalArgumentException("Invalid listener ID: null");
//      return id.intValue();
//     }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        try {
            validate(this.notif, this.id);
        } catch (IllegalArgumentException e) {
            throw new InvalidObjectException(e.getMessage());
        }
    }

    private static void validate(Notification notif, Integer id) throws IllegalArgumentException {
        if (notif == null) {
            throw new IllegalArgumentException("Invalid notification: null");
        }
        if (id == null) {
            throw new IllegalArgumentException("Invalid listener ID: null");
        }
    }
}
