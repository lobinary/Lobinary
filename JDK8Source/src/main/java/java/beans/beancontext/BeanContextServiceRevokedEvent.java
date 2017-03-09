/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2009, Oracle and/or its affiliates. All rights reserved.
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

import java.beans.beancontext.BeanContextEvent;

import java.beans.beancontext.BeanContextServices;

/**
 * <p>
 * This event type is used by the
 * <code>BeanContextServiceRevokedListener</code> in order to
 * identify the service being revoked.
 * </p>
 * <p>
 * <p>
 *  此事件类型由<code> BeanContextServiceRevokedListener </code>使用,以标识正在撤销的服务。
 * </p>
 */
public class BeanContextServiceRevokedEvent extends BeanContextEvent {
    private static final long serialVersionUID = -1295543154724961754L;

    /**
     * Construct a <code>BeanContextServiceEvent</code>.
     * <p>
     *  构造一个<code> BeanContextServiceEvent </code>。
     * 
     * 
     * @param bcs the <code>BeanContextServices</code>
     * from which this service is being revoked
     * @param sc the service that is being revoked
     * @param invalidate <code>true</code> for immediate revocation
     */
    public BeanContextServiceRevokedEvent(BeanContextServices bcs, Class sc, boolean invalidate) {
        super((BeanContext)bcs);

        serviceClass    = sc;
        invalidateRefs  = invalidate;
    }

    /**
     * Gets the source as a reference of type <code>BeanContextServices</code>
     * <p>
     *  获取源作为<code> BeanContextServices </code>类型的引用
     * 
     * 
     * @return the <code>BeanContextServices</code> from which
     * this service is being revoked
     */
    public BeanContextServices getSourceAsBeanContextServices() {
        return (BeanContextServices)getBeanContext();
    }

    /**
     * Gets the service class that is the subject of this notification
     * <p>
     *  获取作为此通知主题的服务类
     * 
     * 
     * @return A <code>Class</code> reference to the
     * service that is being revoked
     */
    public Class getServiceClass() { return serviceClass; }

    /**
     * Checks this event to determine whether or not
     * the service being revoked is of a particular class.
     * <p>
     *  检查此事件以确定正被撤销的服务是否是特定类。
     * 
     * 
     * @param service the service of interest (should be non-null)
     * @return <code>true</code> if the service being revoked is of the
     * same class as the specified service
     */
    public boolean isServiceClass(Class service) {
        return serviceClass.equals(service);
    }

    /**
     * Reports if the current service is being forcibly revoked,
     * in which case the references are now invalidated and unusable.
     * <p>
     *  报告当前服务是否被强制撤销,在这种情况下,引用现在无效并且不可用。
     * 
     * 
     * @return <code>true</code> if current service is being forcibly revoked
     */
    public boolean isCurrentServiceInvalidNow() { return invalidateRefs; }

    /**
     * fields
     * <p>
     *  字段
     * 
     */

    /**
     * A <code>Class</code> reference to the service that is being revoked.
     * <p>
     *  对正被撤消的服务的<code> Class </code>引用。
     */
    protected Class                      serviceClass;
    private   boolean                    invalidateRefs;
}
