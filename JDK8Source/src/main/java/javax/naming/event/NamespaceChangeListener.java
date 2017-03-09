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
  * Specifies the methods that a listener interested in namespace changes
  * must implement.
  * Specifically, the listener is interested in <tt>NamingEvent</tt>s
  * with event types of <tt>OBJECT_ADDED</TT>, <TT>OBJECT_RENAMED</TT>, or
  * <TT>OBJECT_REMOVED</TT>.
  *<p>
  * Such a listener must:
  *<ol>
  *<li>Implement this interface and its methods.
  *<li>Implement <tt>NamingListener.namingExceptionThrown()</tt> so that
  * it will be notified of exceptions thrown while attempting to
  * collect information about the events.
  *<li>Register with the source using the source's <tt>addNamingListener()</tt>
  *    method.
  *</ol>
  * A listener that wants to be notified of <tt>OBJECT_CHANGED</tt> event types
  * should also implement the <tt>ObjectChangeListener</tt>
  * interface.
  *
  * <p>
  *  指定对命名空间更改感兴趣的侦听器必须实现的方法。
  * 具体来说,监听器对<tt> OBJECT_ADDED </TT>,<TT> OBJECT_RENAMED </TT>或<TT> OBJECT_REMOVED </TT>的事件类型感兴趣于<tt> Nam
  * ingEvent </tt>。
  *  指定对命名空间更改感兴趣的侦听器必须实现的方法。
  * p>
  *  这样的监听器必须：
  * ol>
  *  li>实现此接口及其方法。 li>实现<tt> NamingListener.namingExceptionThrown()</tt>,以便在尝试收集有关事件的信息时通知抛出的异常。
  *  li>使用源的<tt> addNamingListener()</tt>方法向源注册。
  * /ol>
  *  想要通知<tt> OBJECT_CHANGED </tt>事件类型的侦听器也应实现<tt> ObjectChangeListener </tt>接口。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  *
  * @see NamingEvent
  * @see ObjectChangeListener
  * @see EventContext
  * @see EventDirContext
  * @since 1.3
  */
public interface NamespaceChangeListener extends NamingListener {

    /**
     * Called when an object has been added.
     *<p>
     * The binding of the newly added object can be obtained using
     * <tt>evt.getNewBinding()</tt>.
     * <p>
     *  在添加对象时调用。
     * p>
     *  新添加的对象的绑定可以使用<tt> evt.getNewBinding()</tt>获得。
     * 
     * 
     * @param evt The nonnull event.
     * @see NamingEvent#OBJECT_ADDED
     */
    void objectAdded(NamingEvent evt);

    /**
     * Called when an object has been removed.
     *<p>
     * The binding of the newly removed object can be obtained using
     * <tt>evt.getOldBinding()</tt>.
     * <p>
     *  当对象已删除时调用。
     * p>
     *  可以使用<tt> evt.getOldBinding()</tt>来获取新删除的对象的绑定。
     * 
     * 
     * @param evt The nonnull event.
     * @see NamingEvent#OBJECT_REMOVED
     */
    void objectRemoved(NamingEvent evt);

    /**
     * Called when an object has been renamed.
     *<p>
     * The binding of the renamed object can be obtained using
     * <tt>evt.getNewBinding()</tt>. Its old binding (before the rename)
     * can be obtained using <tt>evt.getOldBinding()</tt>.
     * One of these may be null if the old/new binding was outside the
     * scope in which the listener has registered interest.
     * <p>
     *  当对象已重命名时调用。
     * p>
     *  可以使用<tt> evt.getNewBinding()</tt>来获取重命名的对象的绑定。它的旧绑定(重命名之前)可以使用<tt> evt.getOldBinding()</tt>获取。
     * 
     * @param evt The nonnull event.
     * @see NamingEvent#OBJECT_RENAMED
     */
    void objectRenamed(NamingEvent evt);
}
