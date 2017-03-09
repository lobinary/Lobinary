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

package javax.naming.event;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;

/**
 * Contains methods for registering listeners to be notified
 * of events fired when objects named in a directory context changes.
 *<p>
 * The methods in this interface support identification of objects by
 * <A HREF="http://www.ietf.org/rfc/rfc2254.txt">RFC 2254</a>
 * search filters.
 *
 *<P>Using the search filter, it is possible to register interest in objects
 * that do not exist at the time of registration but later come into existence and
 * satisfy the filter.  However, there might be limitations in the extent
 * to which this can be supported by the service provider and underlying
 * protocol/service.  If the caller submits a filter that cannot be
 * supported in this way, <tt>addNamingListener()</tt> throws an
 * <tt>InvalidSearchFilterException</tt>.
 *<p>
 * See <tt>EventContext</tt> for a description of event source
 * and target, and information about listener registration/deregistration
 * that are also applicable to methods in this interface.
 * See the
 * <a href=package-summary.html#THREADING>package description</a>
 * for information on threading issues.
 *<p>
 * A <tt>SearchControls</tt> or array object
 * passed as a parameter to any method is owned by the caller.
 * The service provider will not modify the object or keep a reference to it.
 *
 * <p>
 *  包含用于注册侦听器的方法,以通知在目录上下文中命名的对象更改时触发的事件。
 * p>
 *  此界面中的方法支持通过<A HREF="http://www.ietf.org/rfc/rfc2254.txt"> RFC 2254 </a>搜索过滤器识别对象。
 * 
 *  P>使用搜索过滤器,可以登记对在注册时不存在但后来存在并满足过滤器的对象的兴趣。然而,在服务提供商和底层协议/服务可以支持的程度上可能存在限制。
 * 如果调用者提交的筛选器无法以这种方式支持,则<tt> addNamingListener()</tt>会抛出一个<tt> InvalidSearchFilterException </tt>。
 * p>
 *  有关事件源和目标的说明以及有关侦听器注册/取消注册的信息,请参阅<tt> EventContext </tt>,这些信息也适用于此接口中的方法。
 * 有关线程问题的信息,请参见<a href=package-summary.html#THREADING>软件包说明</a>。
 * p>
 *  作为参数传递给任何方法的<tt> SearchControls </tt>或数组对象由调用者拥有。服务提供程序不会修改对象或保留对它的引用。
 * 
 * 
 * @author Rosanna Lee
 * @author Scott Seligman
 * @since 1.3
 */

public interface EventDirContext extends EventContext, DirContext {
    /**
     * Adds a listener for receiving naming events fired
     * when objects identified by the search filter <tt>filter</tt> at
     * the object named by target are modified.
     * <p>
     * The scope, returningObj flag, and returningAttributes flag from
     * the search controls <tt>ctls</tt> are used to control the selection
     * of objects that the listener is interested in,
     * and determines what information is returned in the eventual
     * <tt>NamingEvent</tt> object. Note that the requested
     * information to be returned might not be present in the <tt>NamingEvent</tt>
     * object if they are unavailable or could not be obtained by the
     * service provider or service.
     *
     * <p>
     * 添加用于接收由搜索过滤器<tt>过滤器</tt>标识的对象(由target命名的对象)时触发的命名事件的侦听器。
     * <p>
     *  来自搜索控件<tt> ctls </tt>的范围,returningObj标志和returningAttributes标志用于控制对侦听器感兴趣的对象的选择,并确定在最终的<tt> NamingEve
     * nt </tt> / tt>对象。
     * 请注意,要返回的请求信息可能不存在于<tt> NamingEvent </tt>对象中,如果它们不可用或无法由服务提供商或服务获取。
     * 
     * 
     * @param target The nonnull name of the object resolved relative to this context.
     * @param filter The nonnull string filter (see RFC2254).
     * @param ctls   The possibly null search controls. If null, the default
     *        search controls are used.
     * @param l  The nonnull listener.
     * @exception NamingException If a problem was encountered while
     * adding the listener.
     * @see EventContext#removeNamingListener
     * @see javax.naming.directory.DirContext#search(javax.naming.Name, java.lang.String, javax.naming.directory.SearchControls)
     */
    void addNamingListener(Name target, String filter, SearchControls ctls,
        NamingListener l) throws NamingException;

    /**
     * Adds a listener for receiving naming events fired when
     * objects identified by the search filter <tt>filter</tt> at the
     * object named by the string target name are modified.
     * See the overload that accepts a <tt>Name</tt> for details of
     * how this method behaves.
     *
     * <p>
     *  添加用于接收由搜索过滤器<tt>过滤器</tt>在由字符串目标名称指定的对象标识的对象时触发的命名事件的侦听器。有关此方法行为的详细信息,请参阅接受<tt>名称</tt>的重载。
     * 
     * 
     * @param target The nonnull string name of the object resolved relative to this context.
     * @param filter The nonnull string filter (see RFC2254).
     * @param ctls   The possibly null search controls. If null, the default
     *        search controls is used.
     * @param l  The nonnull listener.
     * @exception NamingException If a problem was encountered while
     * adding the listener.
     * @see EventContext#removeNamingListener
     * @see javax.naming.directory.DirContext#search(java.lang.String, java.lang.String, javax.naming.directory.SearchControls)
     */
    void addNamingListener(String target, String filter, SearchControls ctls,
        NamingListener l) throws NamingException;

    /**
     * Adds a listener for receiving naming events fired
     * when objects identified by the search filter <tt>filter</tt> and
     * filter arguments at the object named by the target are modified.
     * The scope, returningObj flag, and returningAttributes flag from
     * the search controls <tt>ctls</tt> are used to control the selection
     * of objects that the listener is interested in,
     * and determines what information is returned in the eventual
     * <tt>NamingEvent</tt> object.  Note that the requested
     * information to be returned might not be present in the <tt>NamingEvent</tt>
     * object if they are unavailable or could not be obtained by the
     * service provider or service.
     *
     * <p>
     * 添加用于接收由搜索过滤器<tt>过滤器</tt>标识的对象和由目标指定的对象的过滤器参数进行修改时触发的命名事件的侦听器。
     * 来自搜索控件<tt> ctls </tt>的范围,returningObj标志和returningAttributes标志用于控制对侦听器感兴趣的对象的选择,并确定在最终的<tt> NamingEven
     * t </tt> / tt>对象。
     * 添加用于接收由搜索过滤器<tt>过滤器</tt>标识的对象和由目标指定的对象的过滤器参数进行修改时触发的命名事件的侦听器。
     * 请注意,要返回的请求信息可能不存在于<tt> NamingEvent </tt>对象中,如果它们不可用或无法由服务提供商或服务获取。
     * 
     * @param target The nonnull name of the object resolved relative to this context.
     * @param filter The nonnull string filter (see RFC2254).
     * @param filterArgs The possibly null array of arguments for the filter.
     * @param ctls   The possibly null search controls. If null, the default
     *        search controls are used.
     * @param l  The nonnull listener.
     * @exception NamingException If a problem was encountered while
     * adding the listener.
     * @see EventContext#removeNamingListener
     * @see javax.naming.directory.DirContext#search(javax.naming.Name, java.lang.String, java.lang.Object[], javax.naming.directory.SearchControls)
     */
    void addNamingListener(Name target, String filter, Object[] filterArgs,
        SearchControls ctls, NamingListener l) throws NamingException;

    /**
     * Adds a listener for receiving naming events fired when
     * objects identified by the search filter <tt>filter</tt>
     * and filter arguments at the
     * object named by the string target name are modified.
     * See the overload that accepts a <tt>Name</tt> for details of
     * how this method behaves.
     *
     * <p>
     * 
     * 
     * @param target The nonnull string name of the object resolved relative to this context.
     * @param filter The nonnull string filter (see RFC2254).
     * @param filterArgs The possibly null array of arguments for the filter.
     * @param ctls   The possibly null search controls. If null, the default
     *        search controls is used.
     * @param l  The nonnull listener.
     * @exception NamingException If a problem was encountered while
     * adding the listener.
     * @see EventContext#removeNamingListener
     * @see javax.naming.directory.DirContext#search(java.lang.String, java.lang.String, java.lang.Object[], javax.naming.directory.SearchControls)      */
    void addNamingListener(String target, String filter, Object[] filterArgs,
        SearchControls ctls, NamingListener l) throws NamingException;
}
