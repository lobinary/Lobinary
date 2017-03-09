/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.beans.beancontext;

import java.util.Iterator;

import java.util.TooManyListenersException;

import java.beans.beancontext.BeanContext;

import java.beans.beancontext.BeanContextServiceProvider;

import java.beans.beancontext.BeanContextServicesListener;


/**
 * <p>
 * The BeanContextServices interface provides a mechanism for a BeanContext
 * to expose generic "services" to the BeanContextChild objects within.
 * </p>
 * <p>
 * <p>
 *  BeanContextServices接口为BeanContext提供了一种机制,用于向其中的BeanContextChild对象公开通用"服务"。
 * </p>
 */
public interface BeanContextServices extends BeanContext, BeanContextServicesListener {

    /**
     * Adds a service to this BeanContext.
     * <code>BeanContextServiceProvider</code>s call this method
     * to register a particular service with this context.
     * If the service has not previously been added, the
     * <code>BeanContextServices</code> associates
     * the service with the <code>BeanContextServiceProvider</code> and
     * fires a <code>BeanContextServiceAvailableEvent</code> to all
     * currently registered <code>BeanContextServicesListeners</code>.
     * The method then returns <code>true</code>, indicating that
     * the addition of the service was successful.
     * If the given service has already been added, this method
     * simply returns <code>false</code>.
     * <p>
     *  向此BeanContext添加服务。 <code> BeanContextServiceProvider </code>调用此方法来注册具有此上下文的特定服务。
     * 如果服务之前未被添加,则<code> BeanContextServices </code>将服务与<code> BeanContextServiceProvider </code>关联,并向所有当前注
     * 册的<code> BeanContextServicesListeners发出<code> BeanContextServiceAvailableEvent </code> </code>。
     *  向此BeanContext添加服务。 <code> BeanContextServiceProvider </code>调用此方法来注册具有此上下文的特定服务。
     * 然后,该方法返回<code> true </code>,表示添加的服务成功。如果给定的服务已经被添加,该方法简单地返回<code> false </code>。
     * 
     * 
     * @param serviceClass     the service to add
     * @param serviceProvider  the <code>BeanContextServiceProvider</code>
     * associated with the service
     * @return true if the service was successful added, false otherwise
     */
    boolean addService(Class serviceClass, BeanContextServiceProvider serviceProvider);

    /**
     * BeanContextServiceProviders wishing to remove
     * a currently registered service from this context
     * may do so via invocation of this method. Upon revocation of
     * the service, the <code>BeanContextServices</code> fires a
     * <code>BeanContextServiceRevokedEvent</code> to its
     * list of currently registered
     * <code>BeanContextServiceRevokedListeners</code> and
     * <code>BeanContextServicesListeners</code>.
     * <p>
     *  希望从该上下文中删除当前注册的服务的BeanContextServiceProviders可以通过调用该方法来这样做。
     * 当撤销服务时,<code> BeanContextServices </code>向其当前注册的<code> BeanContextServiceRevokedListeners </code>和<code>
     *  BeanContextServicesListeners </code>的列表发出<code> BeanContextServiceRevokedEvent </code>。
     *  希望从该上下文中删除当前注册的服务的BeanContextServiceProviders可以通过调用该方法来这样做。
     * 
     * 
     * @param serviceClass the service to revoke from this BeanContextServices
     * @param serviceProvider the BeanContextServiceProvider associated with
     * this particular service that is being revoked
     * @param revokeCurrentServicesNow a value of <code>true</code>
     * indicates an exceptional circumstance where the
     * <code>BeanContextServiceProvider</code> or
     * <code>BeanContextServices</code> wishes to immediately
     * terminate service to all currently outstanding references
     * to the specified service.
     */
    void revokeService(Class serviceClass, BeanContextServiceProvider serviceProvider, boolean revokeCurrentServicesNow);

    /**
     * Reports whether or not a given service is
     * currently available from this context.
     * <p>
     *  报告给定服务是否当前可从此上下文中获取。
     * 
     * 
     * @param serviceClass the service in question
     * @return true if the service is available
     */
    boolean hasService(Class serviceClass);

    /**
     * A <code>BeanContextChild</code>, or any arbitrary object
     * associated with a <code>BeanContextChild</code>, may obtain
     * a reference to a currently registered service from its
     * nesting <code>BeanContextServices</code>
     * via invocation of this method. When invoked, this method
     * gets the service by calling the getService() method on the
     * underlying <code>BeanContextServiceProvider</code>.
     * <p>
     * <code> BeanContextChild </code>或与<code> BeanContextChild </code>相关联的任何任何对象可以通过调用这些对象来从其嵌套<code> BeanC
     * ontextServices </code>获得对当前注册服务的引用方法。
     * 当被调用时,此方法通过调用底层<code> BeanContextServiceProvider </code>上的getService()方法来获取服务。
     * 
     * 
     * @param child the <code>BeanContextChild</code>
     * associated with this request
     * @param requestor the object requesting the service
     * @param serviceClass class of the requested service
     * @param serviceSelector the service dependent parameter
     * @param bcsrl the
     * <code>BeanContextServiceRevokedListener</code> to notify
     * if the service should later become revoked
     * @throws TooManyListenersException if there are too many listeners
     * @return a reference to this context's named
     * Service as requested or <code>null</code>
     */
    Object getService(BeanContextChild child, Object requestor, Class serviceClass, Object serviceSelector, BeanContextServiceRevokedListener bcsrl) throws TooManyListenersException;

    /**
     * Releases a <code>BeanContextChild</code>'s
     * (or any arbitrary object associated with a BeanContextChild)
     * reference to the specified service by calling releaseService()
     * on the underlying <code>BeanContextServiceProvider</code>.
     * <p>
     *  通过在底层<code> BeanContextServiceProvider </code>上调用releaseService(),释放与指定服务相关的<code> BeanContextChild 
     * </code>(或任何与BeanContextChild关联的任何对象)。
     * 
     * 
     * @param child the <code>BeanContextChild</code>
     * @param requestor the requestor
     * @param service the service
     */
    void releaseService(BeanContextChild child, Object requestor, Object service);

    /**
     * Gets the currently available services for this context.
     * <p>
     *  获取此上下文的当前可用服务。
     * 
     * 
     * @return an <code>Iterator</code> consisting of the
     * currently available services
     */
    Iterator getCurrentServiceClasses();

    /**
     * Gets the list of service dependent service parameters
     * (Service Selectors) for the specified service, by
     * calling getCurrentServiceSelectors() on the
     * underlying BeanContextServiceProvider.
     * <p>
     *  通过在底层BeanContextServiceProvider上调用getCurrentServiceSelectors(),获取指定服务的服务相关服务参数(服务选择器)的列表。
     * 
     * 
     * @param serviceClass the specified service
     * @return the currently available service selectors
     * for the named serviceClass
     */
    Iterator getCurrentServiceSelectors(Class serviceClass);

    /**
     * Adds a <code>BeanContextServicesListener</code> to this BeanContext
     * <p>
     *  向此BeanContext添加<code> BeanContextServicesListener </code>
     * 
     * 
     * @param bcsl the <code>BeanContextServicesListener</code> to add
     */
    void addBeanContextServicesListener(BeanContextServicesListener bcsl);

    /**
     * Removes a <code>BeanContextServicesListener</code>
     * from this <code>BeanContext</code>
     * <p>
     *  从<code> BeanContext </code>中删除<code> BeanContextServicesListener </code>
     * 
     * @param bcsl the <code>BeanContextServicesListener</code>
     * to remove from this context
     */
    void removeBeanContextServicesListener(BeanContextServicesListener bcsl);
}
