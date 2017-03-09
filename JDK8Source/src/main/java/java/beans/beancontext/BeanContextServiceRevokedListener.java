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

import java.beans.beancontext.BeanContextServiceRevokedEvent;

import java.util.EventListener;

/**
 *  The listener interface for receiving
 * <code>BeanContextServiceRevokedEvent</code> objects. A class that is
 * interested in processing a <code>BeanContextServiceRevokedEvent</code>
 * implements this interface.
 * <p>
 *  用于接收<code> BeanContextServiceRevokedEvent </code>对象的侦听器接口。
 * 一个有兴趣处理<code> BeanContextServiceRevokedEvent </code>的类实现了这个接口。
 * 
 */
public interface BeanContextServiceRevokedListener extends EventListener {

    /**
     * The service named has been revoked. getService requests for
     * this service will no longer be satisfied.
     * <p>
     * 
     * @param bcsre the <code>BeanContextServiceRevokedEvent</code> received
     * by this listener.
     */
    void serviceRevoked(BeanContextServiceRevokedEvent bcsre);
}
