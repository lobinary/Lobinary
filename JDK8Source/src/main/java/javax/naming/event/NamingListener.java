/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, Oracle and/or its affiliates. All rights reserved.
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

package javax.naming.event;

/**
  * This interface is the root of listener interfaces that
  * handle <tt>NamingEvent</tt>s.
  * It does not make sense for a listener to implement just this interface.
  * A listener typically implements a subinterface of <tt>NamingListener</tt>,
  * such as <tt>ObjectChangeListener</tt> or <tt>NamespaceChangeListener</tt>.
  *<p>
  * This interface contains a single method, <tt>namingExceptionThrown()</tt>,
  * that must be implemented so that the listener can be notified of
  * exceptions that are thrown (by the service provider) while gathering
  * information about the events that they're interested in.
  * When this method is invoked, the listener has been automatically deregistered
  * from the <tt>EventContext</tt> with which it has registered.
  *<p>
  * For example, suppose a listener implements <tt>ObjectChangeListener</tt> and
  * registers with a <tt>EventContext</tt>.
  * Then, if the connection to the server is subsequently broken,
  * the listener will receive a <tt>NamingExceptionEvent</tt> and may
  * take some corrective action, such as notifying the user of the application.
  *
  * <p>
  *  此接口是处理<tt> NamingEvent </tt>的侦听器接口的根。监听器实现这个接口没有意义。
  * 侦听器通常实现<tt> NamingListener </tt>的子接口,例如<tt> ObjectChangeListener </tt>或<tt> NamespaceChangeListener </tt>
  * 。
  *  此接口是处理<tt> NamingEvent </tt>的侦听器接口的根。监听器实现这个接口没有意义。
  * p>
  *  此接口包含一个方法,<tt> namingExceptionThrown()</tt>,必须实现,以便侦听器可以通知抛出的异常(由服务提供商),同时收集他们的事件的信息当调用此方法时,侦听器已从其已注
  * 册的<tt> EventContext </tt>中自动注销。
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  *
  * @see NamingEvent
  * @see NamingExceptionEvent
  * @see EventContext
  * @see EventDirContext
  * @since 1.3
  */
public interface NamingListener extends java.util.EventListener {
    /**
     * Called when a naming exception is thrown while attempting
     * to fire a <tt>NamingEvent</tt>.
     *
     * <p>
     * p>
     *  例如,假设侦听器实现<tt> ObjectChangeListener </tt>并向<tt> EventContext </tt>注册。
     * 然后,如果与服务器的连接随后断开,则侦听器将接收到<tt> NamingExceptionEvent </tt>,并可能采取一些纠正措施,例如通知应用程序的用户。
     * 
     * 
     * @param evt The nonnull event.
     */
    void namingExceptionThrown(NamingExceptionEvent evt);
}
