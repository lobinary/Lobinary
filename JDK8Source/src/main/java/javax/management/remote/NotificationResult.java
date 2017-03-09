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

/**
 * <p>Result of a query for buffered notifications.  Notifications in
 * a notification buffer have positive, monotonically increasing
 * sequence numbers.  The result of a notification query contains the
 * following elements:</p>
 *
 * <ul>
 *
 * <li>The sequence number of the earliest notification still in
 * the buffer.
 *
 * <li>The sequence number of the next notification available for
 * querying.  This will be the starting sequence number for the next
 * notification query.
 *
 * <li>An array of (Notification,listenerID) pairs corresponding to
 * the returned notifications and the listeners they correspond to.
 *
 * </ul>
 *
 * <p>It is possible for the <code>nextSequenceNumber</code> to be less
 * than the <code>earliestSequenceNumber</code>.  This signifies that
 * notifications between the two might have been lost.</p>
 *
 * <p>
 *  <p>缓冲通知的查询结果。通知缓冲区中的通知具有正的,单调递增的序列号。通知查询的结果包含以下元素：</p>
 * 
 * <ul>
 * 
 *  <li>缓冲区中最早通知的序列号。
 * 
 *  <li>可用于查询的下一个通知的序列号。这将是下一个通知查询的开始序列号。
 * 
 *  <li>与所返回的通知及其对应的侦听器对应的(Notification,listenerID)对数组。
 * 
 * </ul>
 * 
 *  <p> <code> nextSequenceNumber </code>可能小于<code> earliestSequenceNumber </code>。这表示两者之间的通知可能已丢失。
 * </p>。
 * 
 * 
 * @since 1.5
 */
public class NotificationResult implements Serializable {

    private static final long serialVersionUID = 1191800228721395279L;

    /**
     * <p>Constructs a notification query result.</p>
     *
     * <p>
     *  <p>构造通知查询结果。</p>
     * 
     * 
     * @param earliestSequenceNumber the sequence number of the
     * earliest notification still in the buffer.
     * @param nextSequenceNumber the sequence number of the next
     * notification available for querying.
     * @param targetedNotifications the notifications resulting from
     * the query, and the listeners they correspond to.  This array
     * can be empty.
     *
     * @exception IllegalArgumentException if
     * <code>targetedNotifications</code> is null or if
     * <code>earliestSequenceNumber</code> or
     * <code>nextSequenceNumber</code> is negative.
     */
    public NotificationResult(long earliestSequenceNumber,
                              long nextSequenceNumber,
                              TargetedNotification[] targetedNotifications) {
        validate(targetedNotifications, earliestSequenceNumber, nextSequenceNumber);
        this.earliestSequenceNumber = earliestSequenceNumber;
        this.nextSequenceNumber = nextSequenceNumber;
        this.targetedNotifications = (targetedNotifications.length == 0 ? targetedNotifications : targetedNotifications.clone());
    }

    /**
     * Returns the sequence number of the earliest notification still
     * in the buffer.
     *
     * <p>
     *  返回缓冲区中最早的通知的序列号。
     * 
     * 
     * @return the sequence number of the earliest notification still
     * in the buffer.
     */
    public long getEarliestSequenceNumber() {
        return earliestSequenceNumber;
    }

    /**
     * Returns the sequence number of the next notification available
     * for querying.
     *
     * <p>
     *  返回可用于查询的下一个通知的序列号。
     * 
     * 
     * @return the sequence number of the next notification available
     * for querying.
     */
    public long getNextSequenceNumber() {
        return nextSequenceNumber;
    }

    /**
     * Returns the notifications resulting from the query, and the
     * listeners they correspond to.
     *
     * <p>
     *  返回查询生成的通知以及它们对应的侦听器。
     * 
     * 
     * @return the notifications resulting from the query, and the
     * listeners they correspond to.  This array can be empty.
     */
    public TargetedNotification[] getTargetedNotifications() {
        return targetedNotifications.length == 0 ? targetedNotifications : targetedNotifications.clone();
    }

    /**
     * Returns a string representation of the object.  The result
     * should be a concise but informative representation that is easy
     * for a person to read.
     *
     * <p>
     *  返回对象的字符串表示形式。结果应该是一个简单但翔实的表示,是一个人容易阅读。
     * 
     * 
     * @return a string representation of the object.
     */
    public String toString() {
        return "NotificationResult: earliest=" + getEarliestSequenceNumber() +
            "; next=" + getNextSequenceNumber() + "; nnotifs=" +
            getTargetedNotifications().length;
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        try {
            validate(
                this.targetedNotifications,
                this.earliestSequenceNumber,
                this.nextSequenceNumber
            );

            this.targetedNotifications = this.targetedNotifications.length == 0 ?
                                            this.targetedNotifications :
                                            this.targetedNotifications.clone();
        } catch (IllegalArgumentException e) {
            throw new InvalidObjectException(e.getMessage());
        }
    }

    private long earliestSequenceNumber;
    private long nextSequenceNumber;
    private TargetedNotification[] targetedNotifications;

    private static void validate(TargetedNotification[] targetedNotifications,
                                 long earliestSequenceNumber,
                                 long nextSequenceNumber)
        throws IllegalArgumentException {
        if (targetedNotifications == null) {
            final String msg = "Notifications null";
            throw new IllegalArgumentException(msg);
        }

        if (earliestSequenceNumber < 0 || nextSequenceNumber < 0)
            throw new IllegalArgumentException("Bad sequence numbers");
        /* We used to check nextSequenceNumber >= earliestSequenceNumber
           here.  But in fact the opposite can legitimately be true if
        /* <p>
        /*  这里。但事实上,相反的情况可以合法地是真的如果
        /* 
           notifications have been lost.  */
    }
}
