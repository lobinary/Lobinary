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

import java.beans.beancontext.BeanContextChild;
import java.beans.beancontext.BeanContextEvent;

import java.beans.beancontext.BeanContextServices;

import java.util.Iterator;

/**
 * <p>
 * This event type is used by the BeanContextServicesListener in order to
 * identify the service being registered.
 * </p>
 * <p>
 * <p>
 *  此事件类型由BeanContextServicesListener使用,以便标识正在注册的服务。
 * </p>
 */

public class BeanContextServiceAvailableEvent extends BeanContextEvent {
    private static final long serialVersionUID = -5333985775656400778L;

    /**
     * Construct a <code>BeanContextAvailableServiceEvent</code>.
     * <p>
     *  构造<code> BeanContextAvailableServiceEvent </code>。
     * 
     * 
     * @param bcs The context in which the service has become available
     * @param sc A <code>Class</code> reference to the newly available service
     */
    public BeanContextServiceAvailableEvent(BeanContextServices bcs, Class sc) {
        super((BeanContext)bcs);

        serviceClass = sc;
    }

    /**
     * Gets the source as a reference of type <code>BeanContextServices</code>.
     * <p>
     *  获取源作为<code> BeanContextServices </code>类型的引用。
     * 
     * 
     * @return The context in which the service has become available
     */
    public BeanContextServices getSourceAsBeanContextServices() {
        return (BeanContextServices)getBeanContext();
    }

    /**
     * Gets the service class that is the subject of this notification.
     * <p>
     *  获取作为此通知主题的服务类。
     * 
     * 
     * @return A <code>Class</code> reference to the newly available service
     */
    public Class getServiceClass() { return serviceClass; }

    /**
     * Gets the list of service dependent selectors.
     * <p>
     *  获取服务相关选择器的列表。
     * 
     * 
     * @return the current selectors available from the service
     */
    public Iterator getCurrentServiceSelectors() {
        return ((BeanContextServices)getSource()).getCurrentServiceSelectors(serviceClass);
    }

    /*
     * fields
     * <p>
     *  字段
     * 
     */

    /**
     * A <code>Class</code> reference to the newly available service
     * <p>
     *  对新可用服务的<code> Class </code>引用
     */
    protected Class                      serviceClass;
}
