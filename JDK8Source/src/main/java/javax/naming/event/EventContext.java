/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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

import javax.naming.Name;
import javax.naming.Context;
import javax.naming.NamingException;


/**
 * Contains methods for registering/deregistering listeners to be notified of
 * events fired when objects named in a context changes.
 *
 *<h1>Target</h1>
 * The name parameter in the <tt>addNamingListener()</tt> methods is referred
 * to as the <em>target</em>. The target, along with the scope, identify
 * the object(s) that the listener is interested in.
 * It is possible to register interest in a target that does not exist, but
 * there might be limitations in the extent to which this can be
 * supported by the service provider and underlying protocol/service.
 *<p>
 * If a service only supports registration for existing
 * targets, an attempt to register for a nonexistent target
 * results in a <tt>NameNotFoundException</tt> being thrown as early as possible,
 * preferably at the time <tt>addNamingListener()</tt> is called, or if that is
 * not possible, the listener will receive the exception through the
 * <tt>NamingExceptionEvent</tt>.
 *<p>
 * Also, for service providers that only support registration for existing
 * targets, when the target that a listener has registered for is
 * subsequently removed from the namespace, the listener is notified
 * via a <tt>NamingExceptionEvent</tt> (containing a
 *<tt>NameNotFoundException</tt>).
 *<p>
 * An application can use the method <tt>targetMustExist()</tt> to check
 * whether a <tt>EventContext</tt> supports registration
 * of nonexistent targets.
 *
 *<h1>Event Source</h1>
 * The <tt>EventContext</tt> instance on which you invoke the
 * registration methods is the <em>event source</em> of the events that are
 * (potentially) generated.
 * The source is <em>not necessarily</em> the object named by the target.
 * Only when the target is the empty name is the object named by the target
 * the source.
 * In other words, the target,
 * along with the scope parameter, are used to identify
 * the object(s) that the listener is interested in, but the event source
 * is the <tt>EventContext</tt> instance with which the listener
 * has registered.
 *<p>
 * For example, suppose a listener makes the following registration:
 *<blockquote><pre>
 *      NamespaceChangeListener listener = ...;
 *      src.addNamingListener("x", SUBTREE_SCOPE, listener);
 *</pre></blockquote>
 * When an object named "x/y" is subsequently deleted, the corresponding
 * <tt>NamingEvent</tt> (<tt>evt</tt>)  must contain:
 *<blockquote><pre>
 *      evt.getEventContext() == src
 *      evt.getOldBinding().getName().equals("x/y")
 *</pre></blockquote>
 *<p>
 * Furthermore, listener registration/deregistration is with
 * the <tt>EventContext</tt>
 * <em>instance</em>, and not with the corresponding object in the namespace.
 * If the program intends at some point to remove a listener, then it needs to
 * keep a reference to the <tt>EventContext</tt> instance on
 * which it invoked <tt>addNamingListener()</tt> (just as
 * it needs to keep a reference to the listener in order to remove it
 * later). It cannot expect to do a <tt>lookup()</tt> and get another instance of
 * a <tt>EventContext</tt> on which to perform the deregistration.
 *<h1>Lifetime of Registration</h1>
 * A registered listener becomes deregistered when:
 *<ul>
 *<li>It is removed using <tt>removeNamingListener()</tt>.
 *<li>An exception is thrown while collecting information about the events.
 *  That is, when the listener receives a <tt>NamingExceptionEvent</tt>.
 *<li><tt>Context.close()</tt> is invoked on the <tt>EventContext</tt>
 * instance with which it has registered.
 </ul>
 * Until that point, a <tt>EventContext</tt> instance that has outstanding
 * listeners will continue to exist and be maintained by the service provider.
 *
 *<h1>Listener Implementations</h1>
 * The registration/deregistration methods accept an instance of
 * <tt>NamingListener</tt>. There are subinterfaces of <tt>NamingListener</tt>
 * for different of event types of <tt>NamingEvent</tt>.
 * For example, the <tt>ObjectChangeListener</tt>
 * interface is for the <tt>NamingEvent.OBJECT_CHANGED</tt> event type.
 * To register interest in multiple event types, the listener implementation
 * should implement multiple <tt>NamingListener</tt> subinterfaces and use a
 * single invocation of <tt>addNamingListener()</tt>.
 * In addition to reducing the number of method calls and possibly the code size
 * of the listeners, this allows some service providers to optimize the
 * registration.
 *
 *<h1>Threading Issues</h1>
 *
 * Like <tt>Context</tt> instances in general, instances of
 * <tt>EventContext</tt> are not guaranteed to be thread-safe.
 * Care must be taken when multiple threads are accessing the same
 * <tt>EventContext</tt> concurrently.
 * See the
 * <a href=package-summary.html#THREADING>package description</a>
 * for more information on threading issues.
 *
 * <p>
 *  包含注册/取消注册侦听器的方法,以通知在上下文中命名的对象更改时触发的事件。
 * 
 *  h1>目标</h1> <tt> addNamingListener()</tt>方法中的名称参数称为<em>目标</em>。目标与范围一起识别听众感兴趣的对象。
 * 可以在不存在的目标中注册兴趣,但是在可以由其支持的程度上可能存在限制服务提供商和底层协议/服务。
 * p>
 *  如果服务仅支持注册现有目标,尝试注册不存在的目标会导致尽早抛出<tt> NameNotFoundException </tt>,最好在<tt> addNamingListener()</tt>被调用,
 * 或者如果不可能,监听器将通过<tt> NamingExceptionEvent </tt>接收异常。
 * p>
 *  此外,对于仅支持注册现有目标的服务提供者,当监听器已注册的目标随后从命名空间被删除时,通过<tt> NamingExceptionEvent </tt>(包含tt> NameNotFoundExcep
 * tion < / tt>)。
 * p>
 *  应用程序可以使用方法<tt> targetMustExist()</tt>来检查<tt> EventContext </tt>是否支持注册不存在的目标。
 * 
 * h1>事件源</h1>您调用注册方法的<tt> EventContext </tt>实例是(潜在地)生成的事件的<em>事件源</em>。源是<em>不一定</em>目标命名的对象。
 * 只有当目标是空名称时,才是目标命名的对象源。换句话说,目标与scope参数一起用于标识监听器感兴趣的对象,但事件源是监听器已注册的<tt> EventContext </tt>实例。
 * p>
 *  例如,假设侦听器进行以下注册：blockquote> <pre> NamespaceChangeListener listener = ...; src.addNamingListener("x",S
 * UBTREE_SCOPE,listener); / pre> </blockquote>当随后删除名为"x / y"的对象时,相应的<tt> NamingEvent </tt>(<tt> evt </tt>
 * )必须包含：blockquote> <pre> evt。
 *  getEventContext()== src evt.getOldBinding()。getName()。equals("x / y")/ pre> </blockquote>。
 * p>
 * 此外,侦听器注册/注销是与<tt> EventContext </tt> <em>实例</em>,而不是与命名空间中的相应对象。
 * 如果程序打算在某个时候删除一个监听器,那么它需要保存一个对它调用的<tt> EventContext </tt>实例的引用<tt> addNamingListener()</tt>保持对侦听器的引用,以
 * 便以后删除它)。
 * 此外,侦听器注册/注销是与<tt> EventContext </tt> <em>实例</em>,而不是与命名空间中的相应对象。
 * 它不能期望执行<tt> lookup()</tt>并获取执行注销的<tt> EventContext </tt>的另一个实例。 h1>注册生命周期</h1>注册的监听器在以下情况下注销：。
 * ul>
 *  li>它使用<tt> removeNamingListener()</tt>删除。 li>在收集有关事件的信息时抛出异常。
 * 也就是说,当侦听器接收到<tt> NamingExceptionEvent </tt>时。
 *  li> <tt> Context.close()</tt>在它注册的<tt> EventContext </tt>实例上被调用。
 * </ul>
 *  在此之前,具有未完成监听器的<tt> EventContext </tt>实例将继续存在并由服务提供商维护。
 * 
 * h1>监听器实现</h1>注册/注销方法接受<tt> NamingListener </tt>的实例。
 * 对于<tt> NamingEvent </tt>的不同事件类型,存在<tt> NamingListener </tt>的子接口。
 * 例如,<tt> ObjectChangeListener </tt>接口用于<tt> NamingEvent.OBJECT_CHANGED </tt>事件类型。
 * 要注册对多种事件类型的兴趣,监听器实现应该实现多个<tt> NamingListener </tt>子接口,并使用<tt> addNamingListener()</tt>的单个调用。
 * 除了减少方法调用的数量和可能的收听者的代码大小之外,这允许一些服务提供商优化注册。
 * 
 *  h1>线程问题</h1>
 * 
 *  像<tt> Context </tt>实例一般来说,<tt> EventContext </tt>的实例不能保证是线程安全的。
 * 当多个线程同时访问同一<tt> EventContext </tt>时,必须小心。
 * 有关线程问题的详细信息,请参见<a href=package-summary.html#THREADING>包描述</a>。
 * 
 * 
 * @author Rosanna Lee
 * @author Scott Seligman
 * @since 1.3
 */

public interface EventContext extends Context {
    /**
     * Constant for expressing interest in events concerning the object named
     * by the target.
     *<p>
     * The value of this constant is <tt>0</tt>.
     * <p>
     */
    public final static int OBJECT_SCOPE = 0;

    /**
     * Constant for expressing interest in events concerning objects
     * in the context named by the target,
     * excluding the context named by the target.
     *<p>
     * The value of this constant is <tt>1</tt>.
     * <p>
     *  用于表示对关于由目标命名的对象的事件的兴趣的常数。
     * p>
     *  此常数的值为<tt> 0 </tt>。
     * 
     */
    public final static int ONELEVEL_SCOPE = 1;

    /**
     * Constant for expressing interest in events concerning objects
     * in the subtree of the object named by the target, including the object
     * named by the target.
     *<p>
     * The value of this constant is <tt>2</tt>.
     * <p>
     *  用于表示对目标命名的上下文中的对象的事件感兴趣的常数,不包括目标命名的上下文。
     * p>
     *  此常数的值为<tt> 1 </tt>。
     * 
     */
    public final static int SUBTREE_SCOPE = 2;


    /**
     * Adds a listener for receiving naming events fired
     * when the object(s) identified by a target and scope changes.
     *
     * The event source of those events is this context. See the
     * class description for a discussion on event source and target.
     * See the descriptions of the constants <tt>OBJECT_SCOPE</tt>,
     * <tt>ONELEVEL_SCOPE</tt>, and <tt>SUBTREE_SCOPE</tt> to see how
     * <tt>scope</tt> affects the registration.
     *<p>
     * <tt>target</tt> needs to name a context only when <tt>scope</tt> is
     * <tt>ONELEVEL_SCOPE</tt>.
     * <tt>target</tt> may name a non-context if <tt>scope</tt> is either
     * <tt>OBJECT_SCOPE</tt> or <tt>SUBTREE_SCOPE</tt>.  Using
     * <tt>SUBTREE_SCOPE</tt> for a non-context might be useful,
     * for example, if the caller does not know in advance whether <tt>target</tt>
     * is a context and just wants to register interest in the (possibly
     * degenerate subtree) rooted at <tt>target</tt>.
     *<p>
     * When the listener is notified of an event, the listener may
     * in invoked in a thread other than the one in which
     * <tt>addNamingListener()</tt> is executed.
     * Care must be taken when multiple threads are accessing the same
     * <tt>EventContext</tt> concurrently.
     * See the
     * <a href=package-summary.html#THREADING>package description</a>
     * for more information on threading issues.
     *
     * <p>
     * 用于表示对与由目标命名的对象的子树中的对象有关的事件感兴趣的常数,包括由目标命名的对象。
     * p>
     *  该常数的值为<tt> 2 </tt>。
     * 
     * 
     * @param target A nonnull name to be resolved relative to this context.
     * @param scope One of <tt>OBJECT_SCOPE</tt>, <tt>ONELEVEL_SCOPE</tt>, or
     * <tt>SUBTREE_SCOPE</tt>.
     * @param l  The nonnull listener.
     * @exception NamingException If a problem was encountered while
     * adding the listener.
     * @see #removeNamingListener
     */
    void addNamingListener(Name target, int scope, NamingListener l)
        throws NamingException;

    /**
     * Adds a listener for receiving naming events fired
     * when the object named by the string target name and scope changes.
     *
     * See the overload that accepts a <tt>Name</tt> for details.
     *
     * <p>
     *  添加用于接收由目标和范围标识的对象更改时触发的命名事件的侦听器。
     * 
     *  这些事件的事件源是这种上下文。有关事件源和目标的讨论,请参阅类描述。
     * 请参阅常量<tt> OBJECT_SCOPE </tt>,<tt> ONELEVEL_SCOPE </tt>和<tt> SUBTREE_SCOPE </tt>的说明,了解<tt>范围</tt>如何影响注
     * 册。
     *  这些事件的事件源是这种上下文。有关事件源和目标的讨论,请参阅类描述。
     * p>
     *  <tt> target </tt>只有在<tt>范围</tt>为<tt> ONELEVEL_SCOPE </tt>时才需要命名上下文。
     * 如果<tt>范围</tt>为<tt> OBJECT_SCOPE </tt>或<tt> SUBTREE_SCOPE </tt>,则<tt> target </tt>可以命名非上下文。
     * 对于非上下文使用<tt> SUBTREE_SCOPE </tt>可能是有用的,例如,如果调用者事先不知道<tt> target </tt>是否为上下文,可能退化子树),其根源在<tt>目标</tt>。
     * p>
     *  当监听器被通知事件时,监听器可以在除了执行<tt> addNamingListener()</tt>的线程之外的线程中调用。
     * 当多个线程同时访问同一<tt> EventContext </tt>时,必须小心。
     * 有关线程问题的详细信息,请参见<a href=package-summary.html#THREADING>包描述</a>。
     * 
     * @param target The nonnull string name of the object resolved relative
     * to this context.
     * @param scope One of <tt>OBJECT_SCOPE</tt>, <tt>ONELEVEL_SCOPE</tt>, or
     * <tt>SUBTREE_SCOPE</tt>.
     * @param l  The nonnull listener.
     * @exception NamingException If a problem was encountered while
     * adding the listener.
     * @see #removeNamingListener
     */
    void addNamingListener(String target, int scope, NamingListener l)
        throws NamingException;

    /**
     * Removes a listener from receiving naming events fired
     * by this <tt>EventContext</tt>.
     * The listener may have registered more than once with this
     * <tt>EventContext</tt>, perhaps with different target/scope arguments.
     * After this method is invoked, the listener will no longer
     * receive events with this <tt>EventContext</tt> instance
     * as the event source (except for those events already in the process of
     * being dispatched).
     * If the listener was not, or is no longer, registered with
     * this <tt>EventContext</tt> instance, this method does not do anything.
     *
     * <p>
     * 
     * 
     * @param l  The nonnull listener.
     * @exception NamingException If a problem was encountered while
     * removing the listener.
     * @see #addNamingListener
     */
    void removeNamingListener(NamingListener l) throws NamingException;

    /**
     * Determines whether a listener can register interest in a target
     * that does not exist.
     *
     * <p>
     * 添加一个侦听器,用于接收由字符串目标名称和范围所指定的对象更改时触发的命名事件。
     * 
     *  有关详细信息,请参阅接受<tt>名称</tt>的重载。
     * 
     * 
     * @return true if the target must exist; false if the target need not exist.
     * @exception NamingException If the context's behavior in this regard cannot
     * be determined.
     */
    boolean targetMustExist() throws NamingException;
}
