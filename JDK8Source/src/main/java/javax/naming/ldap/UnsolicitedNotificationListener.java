/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2010, Oracle and/or its affiliates. All rights reserved.
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

import javax.naming.event.NamingListener;

/**
 * This interface is for handling <tt>UnsolicitedNotificationEvent</tt>.
 * "Unsolicited notification" is defined in
 * <A HREF="http://www.ietf.org/rfc/rfc2251.txt">RFC 2251</A>.
 * It allows the server to send unsolicited notifications to the client.
 * A <tt>UnsolicitedNotificationListener</tt> must:
 *<ol>
 * <li>Implement this interface and its method
 * <li>Implement <tt>NamingListener.namingExceptionThrown()</tt> so
 * that it will be notified of exceptions thrown while attempting to
 * collect unsolicited notification events.
 * <li>Register with the context using one of the <tt>addNamingListener()</tt>
 * methods from <tt>EventContext</tt> or <tt>EventDirContext</tt>.
 * Only the <tt>NamingListener</tt> argument of these methods are applicable;
 * the rest are ignored for a <tt>UnsolicitedNotificationListener</tt>.
 * (These arguments might be applicable to the listener if it implements
 * other listener interfaces).
 *</ol>
 *
 * <p>
 *  此接口用于处理<tt> UnsolicitedNotificationEvent </tt>。
 *  "非请求通知"在<A HREF="http://www.ietf.org/rfc/rfc2251.txt"> RFC 2251 </A>中定义。它允许服务器向客户端发送未经请求的通知。
 *  A <tt> UnsolicitedNotificationListener </tt>必须：。
 * ol>
 *  <li>实施此界面及其方法<li>实现<tt> NamingListener.namingExceptionThrown()</tt>,以便在尝试收集未经请求的通知事件时通知其抛出的异常。
 *  <li>使用<tt> EventContext </tt>或<tt> EventDirContext </tt>中的<tt> addNamingListener()</tt>方法之一注册上下文。
 * 只有这些方法的<tt> NamingListener </tt>参数适用;对于<tt> UnsolicitedNotificationListener </tt>,其余部分将被忽略。
 * 
 * @author Rosanna Lee
 * @author Scott Seligman
 * @author Vincent Ryan
 *
 * @see UnsolicitedNotificationEvent
 * @see UnsolicitedNotification
 * @see javax.naming.event.EventContext#addNamingListener
 * @see javax.naming.event.EventDirContext#addNamingListener
 * @see javax.naming.event.EventContext#removeNamingListener
 * @since 1.3
 */
public interface UnsolicitedNotificationListener extends NamingListener {

    /**
     * Called when an unsolicited notification has been received.
     *
     * <p>
     *  (这些参数可能适用于侦听器,如果它实现其他侦听器接口)。
     * /ol>
     * 
     * 
     * @param evt The non-null UnsolicitedNotificationEvent
     */
     void notificationReceived(UnsolicitedNotificationEvent evt);
}
