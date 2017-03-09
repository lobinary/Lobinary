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
  * Specifies the method that a listener of a <tt>NamingEvent</tt>
  * with event type of <tt>OBJECT_CHANGED</tt> must implement.
  *<p>
  * An <tt>OBJECT_CHANGED</tt> event type is fired when (the contents of)
  * an object has changed. This might mean that its attributes have been modified,
  * added, or removed, and/or that the object itself has been replaced.
  * How the object has changed can be determined by examining the
  * <tt>NamingEvent</tt>'s old and new bindings.
  *<p>
  * A listener interested in <tt>OBJECT_CHANGED</tt> event types must:
  *<ol>
  *
  *<li>Implement this interface and its method (<tt>objectChanged()</tt>)
  *<li>Implement <tt>NamingListener.namingExceptionThrown()</tt> so that
  * it will be notified of exceptions thrown while attempting to
  * collect information about the events.
  *<li>Register with the source using the source's <tt>addNamingListener()</tt>
  *    method.
  *</ol>
  * A listener that wants to be notified of namespace change events
  * should also implement the <tt>NamespaceChangeListener</tt>
  * interface.
  *
  * <p>
  *  指定事件类型为<tt> OBJECT_CHANGED </tt>的<tt> NamingEvent </tt>的侦听器必须实现的方法。
  * p>
  *  当对象的(内容)更改时,将触发<tt> OBJECT_CHANGED </tt>事件类型。这可能意味着其属性已被修改,添加或删除,和/或对象本身已被替换。
  * 对象如何更改可以通过检查<tt> NamingEvent </tt>的旧和新绑定来确定。
  * p>
  *  对<tt> OBJECT_CHANGED </tt>事件类型感兴趣的监听器必须：
  * ol>
  * 
  *  li>实现此接口及其方法(<tt> objectChanged()</tt>)li>实现<tt> NamingListener.namingExceptionThrown()</tt>,以便在尝试收集
  * 有关事件。
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  *
  * @see NamingEvent
  * @see NamespaceChangeListener
  * @see EventContext
  * @see EventDirContext
  * @since 1.3
  */
public interface ObjectChangeListener extends NamingListener {

    /**
     * Called when an object has been changed.
     *<p>
     * The binding of the changed object can be obtained using
     * <tt>evt.getNewBinding()</tt>. Its old binding (before the change)
     * can be obtained using <tt>evt.getOldBinding()</tt>.
     * <p>
     *  li>使用源的<tt> addNamingListener()</tt>方法向源注册。
     * /ol>
     *  想要通知命名空间更改事件的侦听器还应实现<tt> NamespaceChangeListener </tt>界面。
     * 
     * 
     * @param evt The nonnull naming event.
     * @see NamingEvent#OBJECT_CHANGED
     */
    void objectChanged(NamingEvent evt);
}
