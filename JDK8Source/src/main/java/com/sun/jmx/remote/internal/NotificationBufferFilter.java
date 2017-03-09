/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
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

import java.util.List;
import javax.management.Notification;
import javax.management.ObjectName;
import javax.management.remote.TargetedNotification;

public interface NotificationBufferFilter {
    /**
     * Add the given notification coming from the given MBean to the list
     * iff it matches this filter's rules.
     * <p>
     *  如果匹配此过滤器的规则,将来自给定MBean的给定通知添加到列表。
     */
    public void apply(List<TargetedNotification> targetedNotifs,
            ObjectName source, Notification notif);
}
