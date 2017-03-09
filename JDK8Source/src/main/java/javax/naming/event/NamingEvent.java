/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2000, Oracle and/or its affiliates. All rights reserved.
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

import javax.naming.Binding;

/**
  * This class represents an event fired by a naming/directory service.
  *<p>
  * The <tt>NamingEvent</tt>'s state consists of
  * <ul>
  * <li>The event source: the <tt>EventContext</tt> which fired this event.
  * <li>The event type.
  * <li>The new binding: information about the object after the change.
  * <li>The old binding: information about the object before the change.
  * <li>Change information: information about the change
  * that triggered this event; usually service provider-specific or server-specific
  * information.
  * </ul>
  * <p>
  * Note that the event source is always the same <tt>EventContext</tt>
  * <em>instance</em>  that the listener has registered with.
  * Furthermore, the names of the bindings in
  * the <tt>NamingEvent</tt> are always relative to that instance.
  * For example, suppose a listener makes the following registration:
  *<blockquote><pre>
  *     NamespaceChangeListener listener = ...;
  *     src.addNamingListener("x", SUBTREE_SCOPE, listener);
  *</pre></blockquote>
  * When an object named "x/y" is subsequently deleted, the corresponding
  * <tt>NamingEvent</tt> (<tt>evt</tt>) must contain:
  *<blockquote><pre>
  *     evt.getEventContext() == src
  *     evt.getOldBinding().getName().equals("x/y")
  *</pre></blockquote>
  *
  * Care must be taken when multiple threads are accessing the same
  * <tt>EventContext</tt> concurrently.
  * See the
  * <a href=package-summary.html#THREADING>package description</a>
  * for more information on threading issues.
  *
  * <p>
  *  此类表示由命名/目录服务触发的事件。
  * p>
  *  <tt> NamingEvent </tt>的状态包括
  * <ul>
  *  <li>事件来源：触发此事件的<tt> EventContext </tt>。 <li>事件类型。 <li>新绑定：有关更改后对象的信息。 <li>旧的绑定：有关更改之前的对象的信息。
  *  <li>更改信息：有关触发此事件的更改的信息;通常是服务提供商特定或服务器特定的信息。
  * </ul>
  * <p>
  *  请注意,事件源始终与侦听器注册的<tt> EventContext </tt> <em>实例</em>相同。此外,<tt> NamingEvent </tt>中绑定的名称始终与该实例相关。
  * 例如,假设侦听器进行以下注册：blockquote> <pre> NamespaceChangeListener listener = ...; src.addNamingListener("x",SU
  * BTREE_SCOPE,listener); / pre> </blockquote>当随后删除名为"x / y"的对象时,相应的<tt> NamingEvent </tt>(<tt> evt </tt>
  * )必须包含：blockquote> <pre> evt。
  *  请注意,事件源始终与侦听器注册的<tt> EventContext </tt> <em>实例</em>相同。此外,<tt> NamingEvent </tt>中绑定的名称始终与该实例相关。
  *  getEventContext()== src evt.getOldBinding()。getName()。equals("x / y")/ pre> </blockquote>。
  * 
  * 当多个线程同时访问同一<tt> EventContext </tt>时,必须小心。
  * 有关线程问题的详细信息,请参见<a href=package-summary.html#THREADING>包描述</a>。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  *
  * @see NamingListener
  * @see EventContext
  * @since 1.3
  */
public class NamingEvent extends java.util.EventObject {
    /**
     * Naming event type for indicating that a new object has been added.
     * The value of this constant is <tt>0</tt>.
     * <p>
     *  用于指示已添加新对象的命名事件类型。此常数的值为<tt> 0 </tt>。
     * 
     */
    public static final int OBJECT_ADDED = 0;

    /**
     * Naming event type for indicating that an object has been removed.
     * The value of this constant is <tt>1</tt>.
     * <p>
     *  用于指示对象已删除的命名事件类型。此常数的值为<tt> 1 </tt>。
     * 
     */
    public static final int OBJECT_REMOVED = 1;

    /**
     * Naming event type for indicating that an object has been renamed.
     * Note that some services might fire multiple events for a single
     * logical rename operation. For example, the rename operation might
     * be implemented by adding a binding with the new name and removing
     * the old binding.
     *<p>
     * The old/new binding in <tt>NamingEvent</tt> may be null if the old
     * name or new name is outside of the scope for which the listener
     * has registered.
     *<p>
     * When an interior node in the namespace tree has been renamed, the
     * topmost node which is part of the listener's scope should used to generate
     * a rename event. The extent to which this can be supported is
     * provider-specific. For example, a service might generate rename
     * notifications for all descendants of the changed interior node and the
     * corresponding provider might not be able to prevent those
     * notifications from being propagated to the listeners.
     *<p>
     * The value of this constant is <tt>2</tt>.
     * <p>
     *  用于指示对象已重命名的命名事件类型。请注意,一些服务可能为单个逻辑重命名操作触发多个事件。例如,重命名操作可以通过添加具有新名称的绑定并删除旧绑定来实现。
     * p>
     *  如果旧名称或新名称在侦听器已注册的范围之外,则<tt> NamingEvent </tt>中的旧/新绑定可能为null。
     * p>
     *  当命名空间树中的内部节点已被重命名时,作为监听器范围的一部分的最顶层节点应该用于生成重命名事件。可以支持的程度是特定于提供者。
     * 例如,服务可以为改变的内部节点的所有后代生成重命名通知,并且相应的提供商可能不能防止这些通知被传播到收听者。
     * p>
     *  该常数的值为<tt> 2 </tt>。
     * 
     */
    public static final int OBJECT_RENAMED = 2;

    /**
     * Naming event type for indicating that an object has been changed.
     * The changes might include the object's attributes, or the object itself.
     * Note that some services might fire multiple events for a single
     * modification. For example, the modification might
     * be implemented by first removing the old binding and adding
     * a new binding containing the same name but a different object.
     *<p>
     * The value of this constant is <tt>3</tt>.
     * <p>
     * 用于指示对象已更改的命名事件类型。更改可能包括对象的属性或对象本身。请注意,某些服务可能针对单个修改触发多个事件。例如,可以通过首先移除旧绑定并添加包含相同名称但不同对象的新绑定来实现修改。
     * p>
     *  此常数的值为<tt> 3 </tt>。
     * 
     */
    public static final int OBJECT_CHANGED = 3;

    /**
     * Contains information about the change that generated this event.
     * <p>
     *  包含有关生成此事件的更改的信息。
     * 
     * 
     * @serial
     */
    protected Object changeInfo;

    /**
     * Contains the type of this event.
     * <p>
     *  包含此事件的类型。
     * 
     * 
     * @see #OBJECT_ADDED
     * @see #OBJECT_REMOVED
     * @see #OBJECT_RENAMED
     * @see #OBJECT_CHANGED
     * @serial
     */
    protected int type;

    /**
     * Contains information about the object before the change.
     * <p>
     *  包含有关更改之前的对象的信息。
     * 
     * 
     * @serial
     */
    protected Binding oldBinding;

    /**
     * Contains information about the object after the change.
     * <p>
     *  包含更改后对象的信息。
     * 
     * 
     * @serial
     */
    protected Binding newBinding;

    /**
     * Constructs an instance of <tt>NamingEvent</tt>.
     *<p>
     * The names in <tt>newBd</tt> and <tt>oldBd</tt> are to be resolved relative
     * to the event source <tt>source</tt>.
     *
     * For an <tt>OBJECT_ADDED</tt> event type, <tt>newBd</tt> must not be null.
     * For an <tt>OBJECT_REMOVED</tt> event type, <tt>oldBd</tt> must not be null.
     * For an <tt>OBJECT_CHANGED</tt> event type,  <tt>newBd</tt> and
     * <tt>oldBd</tt> must not be null. For  an <tt>OBJECT_RENAMED</tt> event type,
     * one of <tt>newBd</tt> or <tt>oldBd</tt> may be null if the new or old
     * binding is outside of the scope for which the listener has registered.
     *
     * <p>
     *  构造<tt> NamingEvent </tt>的实例。
     * p>
     *  <tt> newBd </tt>和<tt> oldBd </tt>中的名称将相对于事件源<tt> source </tt>解析。
     * 
     *  对于<tt> OBJECT_ADDED </tt>事件类型,<tt> newBd </tt>不能为空。
     * 对于<tt> OBJECT_REMOVED </tt>事件类型,<tt> oldBd </tt>不能为空。
     * 对于<tt> OBJECT_CHANGED </tt>事件类型,<tt> newBd </tt>和<tt> oldBd </tt>不能为空。
     * 对于<tt> OBJECT_RENAMED </tt>事件类型,如果新的或旧的绑定超出监听器的范围,则<tt> newBd </tt>或<tt> oldBd </tt>已注册。
     * 
     * 
     * @param source The non-null context that fired this event.
     * @param type The type of the event.
     * @param newBd A possibly null binding before the change. See method description.
     * @param oldBd A possibly null binding after the change. See method description.
     * @param changeInfo A possibly null object containing information about the change.
     * @see #OBJECT_ADDED
     * @see #OBJECT_REMOVED
     * @see #OBJECT_RENAMED
     * @see #OBJECT_CHANGED
     */
    public NamingEvent(EventContext source, int type,
        Binding newBd, Binding oldBd, Object changeInfo) {
        super(source);
        this.type = type;
        oldBinding = oldBd;
        newBinding = newBd;
        this.changeInfo = changeInfo;
    }

    /**
     * Returns the type of this event.
     * <p>
     *  返回此事件的类型。
     * 
     * 
     * @return The type of this event.
     * @see #OBJECT_ADDED
     * @see #OBJECT_REMOVED
     * @see #OBJECT_RENAMED
     * @see #OBJECT_CHANGED
     */
    public int getType() {
        return type;
    }

    /**
     * Retrieves the event source that fired this event.
     * This returns the same object as <tt>EventObject.getSource()</tt>.
     *<p>
     * If the result of this method is used to access the
     * event source, for example, to look up the object or get its attributes,
     * then it needs to be locked  because implementations of <tt>Context</tt>
     * are not guaranteed to be thread-safe
     * (and <tt>EventContext</tt> is a subinterface of <tt>Context</tt>).
     * See the
     * <a href=package-summary.html#THREADING>package description</a>
     * for more information on threading issues.
     *
     * <p>
     *  检索触发此事件的事件源。这将返回与<tt> EventObject.getSource()</tt>相同的对象。
     * p>
     * 如果此方法的结果用于访问事件源,例如,查找对象或获取其属性,则需要锁定它,因为<tt> Context </tt>的实现不能保证是线程-safe(和<tt> EventContext </tt>是<tt>
     * 上下文</tt>的子接口)。
     * 有关线程问题的详细信息,请参见<a href=package-summary.html#THREADING>包描述</a>。
     * 
     * 
     * @return The non-null context that fired this event.
     */
    public EventContext getEventContext() {
        return (EventContext)getSource();
    }

    /**
     * Retrieves the binding of the object before the change.
     *<p>
     * The binding must be nonnull if the object existed before the change
     * relative to the source context (<tt>getEventContext()</tt>).
     * That is, it must be nonnull for <tt>OBJECT_REMOVED</tt> and
     * <tt>OBJECT_CHANGED</tt>.
     * For <tt>OBJECT_RENAMED</tt>, it is null if the object before the rename
     * is outside of the scope for which the listener has registered interest;
     * it is nonnull if the object is inside the scope before the rename.
     *<p>
     * The name in the binding is to be resolved relative
     * to the event source <tt>getEventContext()</tt>.
     * The object returned by <tt>Binding.getObject()</tt> may be null if
     * such information is unavailable.
     *
     * <p>
     *  检索对象在更改之前的绑定。
     * p>
     *  如果对象在相对于源上下文的更改之前存在(<tt> getEventContext()</tt>),则绑定必须为非空。
     * 也就是说,对于<tt> OBJECT_REMOVED </tt>和<tt> OBJECT_CHANGED </tt>,它必须为非空。
     * 对于<tt> OBJECT_RENAMED </tt>,如果重命名之前的对象超出了侦听器已注册的范围,则它为null;如果对象在重命名之前的范围内,则它是非空的。
     * p>
     *  绑定中的名称将相对于事件源<tt> getEventContext()</tt>解析。如果此类信息不可用,则<tt> Binding.getObject()</tt>返回的对象可能为null。
     * 
     * 
     * @return The possibly null binding of the object before the change.
     */
    public Binding getOldBinding() {
        return oldBinding;
    }

    /**
     * Retrieves the binding of the object after the change.
     *<p>
     * The binding must be nonnull if the object existed after the change
     * relative to the source context (<tt>getEventContext()</tt>).
     * That is, it must be nonnull for <tt>OBJECT_ADDED</tt> and
     * <tt>OBJECT_CHANGED</tt>. For <tt>OBJECT_RENAMED</tt>,
     * it is null if the object after the rename is outside the scope for
     * which the listener registered interest; it is nonnull if the object
     * is inside the scope after the rename.
     *<p>
     * The name in the binding is to be resolved relative
     * to the event source <tt>getEventContext()</tt>.
     * The object returned by <tt>Binding.getObject()</tt> may be null if
     * such information is unavailable.
     *
     * <p>
     *  检索更改后对象的绑定。
     * p>
     * 如果对象在相对于源上下文(<tt> getEventContext()</tt>)的更改后存在,则绑定必须为非空。
     * 也就是说,对于<tt> OBJECT_ADDED </tt>和<tt> OBJECT_CHANGED </tt>,它必须为非空。
     * 对于<tt> OBJECT_RENAMED </tt>,如果重命名后的对象超出了监听器注册的范围,则它为null;如果对象在重命名后的作用域内,则它是非空的。
     * p>
     *  绑定中的名称将相对于事件源<tt> getEventContext()</tt>解析。如果此类信息不可用,则<tt> Binding.getObject()</tt>返回的对象可能为null。
     * 
     * 
     * @return The possibly null binding of the object after the change.
     */
    public Binding getNewBinding() {
        return newBinding;
    }

    /**
     * Retrieves the change information for this event.
     * The value of the change information is service-specific. For example,
     * it could be an ID that identifies the change in a change log on the server.
     *
     * <p>
     *  检索此事件的更改信息。更改信息的值是特定于服务的。例如,它可以是标识服务器上的更改日志中的更改的ID。
     * 
     * 
     * @return The possibly null change information of this event.
     */
    public Object getChangeInfo() {
        return changeInfo;
    }

    /**
     * Invokes the appropriate listener method on this event.
     * The default implementation of
     * this method handles the following event types:
     * <tt>OBJECT_ADDED</TT>, <TT>OBJECT_REMOVED</TT>,
     * <TT>OBJECT_RENAMED</TT>, <TT>OBJECT_CHANGED</TT>.
     *<p>
     * The listener method is executed in the same thread
     * as this method.  See the
     * <a href=package-summary.html#THREADING>package description</a>
     * for more information on threading issues.
     * <p>
     *  在此事件上调用相应的侦听器方法。
     * 此方法的默认实现处理以下事件类型：<tt> OBJECT_ADDED </TT>,<TT> OBJECT_REMOVED </TT>,<TT> OBJECT_RENAMED </TT>,<TT> OBJ
     * ECT_CHANGED </TT>。
     *  在此事件上调用相应的侦听器方法。
     * 
     * @param listener The nonnull listener.
     */
    public void dispatch(NamingListener listener) {
        switch (type) {
        case OBJECT_ADDED:
            ((NamespaceChangeListener)listener).objectAdded(this);
            break;

        case OBJECT_REMOVED:
            ((NamespaceChangeListener)listener).objectRemoved(this);
            break;

        case OBJECT_RENAMED:
            ((NamespaceChangeListener)listener).objectRenamed(this);
            break;

        case OBJECT_CHANGED:
            ((ObjectChangeListener)listener).objectChanged(this);
            break;
        }
    }
    private static final long serialVersionUID = -7126752885365133499L;
}
