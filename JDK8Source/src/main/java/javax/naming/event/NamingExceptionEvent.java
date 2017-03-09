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

import javax.naming.NamingException;

/**
  * This class represents an event fired when the procedures/processes
  * used to collect information for notifying listeners of
  * <tt>NamingEvent</tt>s threw a <tt>NamingException</tt>.
  * This can happen, for example, if the server which the listener is using
  * aborts subsequent to the <tt>addNamingListener()</tt> call.
  *
  * <p>
  *  此类表示当用于收集用于通知<tt> NamingEvent </tt>的侦听器的信息的过程/进程抛出<tt> NamingException </tt>时触发的事件。
  * 例如,如果侦听器正在使用的服务器在<tt> addNamingListener()</tt>调用之后中止,则可能会发生这种情况。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  *
  * @see NamingListener#namingExceptionThrown
  * @see EventContext
  * @since 1.3
  */

public class NamingExceptionEvent extends java.util.EventObject {
    /**
     * Contains the exception that was thrown
     * <p>
     *  包含抛出的异常
     * 
     * 
     * @serial
     */
    private NamingException exception;

    /**
     * Constructs an instance of <tt>NamingExceptionEvent</tt> using
     * the context in which the <tt>NamingException</tt> was thrown and the exception
     * that was thrown.
     *
     * <p>
     *  使用引发<tt> NamingException </tt>的上下文和抛出的异常构造<tt> NamingExceptionEvent </tt>的实例。
     * 
     * 
     * @param source The non-null context in which the exception was thrown.
     * @param exc    The non-null <tt>NamingException</tt> that was thrown.
     *
     */
    public NamingExceptionEvent(EventContext source, NamingException exc) {
        super(source);
        exception = exc;
    }

    /**
     * Retrieves the exception that was thrown.
     * <p>
     *  检索抛出的异常。
     * 
     * 
     * @return The exception that was thrown.
     */
    public NamingException getException() {
        return exception;
    }

    /**
     * Retrieves the <tt>EventContext</tt> that fired this event.
     * This returns the same object as <tt>EventObject.getSource()</tt>.
     * <p>
     *  检索触发此事件的<tt> EventContext </tt>。这将返回与<tt> EventObject.getSource()</tt>相同的对象。
     * 
     * 
     * @return The non-null <tt>EventContext</tt> that fired this event.
     */
    public EventContext getEventContext() {
        return (EventContext)getSource();
    }

    /**
     * Invokes the <tt>namingExceptionThrown()</tt> method on
     * a listener using this event.
     * <p>
     *  使用此事件在侦听器上调用<tt> namingExceptionThrown()</tt>方法。
     * 
     * @param listener The non-null naming listener on which to invoke
     * the method.
     */
    public void dispatch(NamingListener listener) {
        listener.namingExceptionThrown(this);
    }

    private static final long serialVersionUID = -4877678086134736336L;
}
