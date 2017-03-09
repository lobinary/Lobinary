/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2006, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.jmx.remote.internal;

import java.util.Set;
import javax.management.remote.NotificationResult;
import javax.management.remote.TargetedNotification;

/** A buffer of notifications received from an MBean server. */
public interface NotificationBuffer {
    /**
     * <p>Fetch notifications that match the given listeners.</p>
     *
     * <p>The operation only considers notifications with a sequence
     * number at least <code>startSequenceNumber</code>.  It will take
     * no longer than <code>timeout</code>, and will return no more
     * than <code>maxNotifications</code> different notifications.</p>
     *
     * <p>If there are no notifications matching the criteria, the
     * operation will block until one arrives, subject to the
     * timeout.</p>
     *
     * <p>
     *  <p>提取符合指定倾听者的通知。</p>
     * 
     *  <p>此操作只考虑序号至少为<code> startSequenceNumber </code>的通知。
     * 它不会超过<code> timeout </code>,并且只会返回不超过<code> maxNotifications </code>的通知。</p>。
     * 
     * 
     * @param filter an object that will add notifications to a
     * {@code List<TargetedNotification>} if they match the current
     * listeners with their filters.
     * @param startSequenceNumber the first sequence number to
     * consider.
     * @param timeout the maximum time to wait.  May be 0 to indicate
     * not to wait if there are no notifications.
     * @param maxNotifications the maximum number of notifications to
     * return.  May be 0 to indicate a wait for eligible notifications
     * that will return a usable <code>nextSequenceNumber</code>.  The
     * {@link TargetedNotification} array in the returned {@link
     * NotificationResult} may contain more than this number of
     * elements but will not contain more than this number of
     * different notifications.
     */
    public NotificationResult
        fetchNotifications(NotificationBufferFilter filter,
                           long startSequenceNumber,
                           long timeout,
                           int maxNotifications)
            throws InterruptedException;

    /**
     * <p>Discard this buffer.</p>
     * <p>
     *  <p>如果没有符合条件的通知,则操作将阻止,直到到达为止,但会超时。</p>
     * 
     */
    public void dispose();
}
