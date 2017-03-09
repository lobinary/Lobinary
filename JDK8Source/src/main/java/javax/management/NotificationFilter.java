/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2003, Oracle and/or its affiliates. All rights reserved.
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
 * To be implemented by a any class acting as a notification filter.
 * It allows a registered notification listener to filter the notifications of interest.
 *
 * <p>
 *  要由充当通知过滤器的任何类实现。它允许注册的通知侦听器过滤感兴趣的通知。
 * 
 * 
 * @since 1.5
 */
public interface NotificationFilter extends java.io.Serializable {

    /**
     * Invoked before sending the specified notification to the listener.
     *
     * <p>
     *  在将指定的通知发送到侦听器之前调用。
     * 
     * @param notification The notification to be sent.
     * @return <CODE>true</CODE> if the notification has to be sent to the listener, <CODE>false</CODE> otherwise.
     */
    public boolean isNotificationEnabled(Notification notification);
}
