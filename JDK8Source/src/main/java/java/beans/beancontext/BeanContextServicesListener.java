/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 1999, Oracle and/or its affiliates. All rights reserved.
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

import java.beans.beancontext.BeanContextServiceAvailableEvent;
import java.beans.beancontext.BeanContextServiceRevokedEvent;
import java.beans.beancontext.BeanContextServiceRevokedListener;

/**
 * The listener interface for receiving
 * <code>BeanContextServiceAvailableEvent</code> objects.
 * A class that is interested in processing a
 * <code>BeanContextServiceAvailableEvent</code> implements this interface.
 * <p>
 *  用于接收<code> BeanContextServiceAvailableEvent </code>对象的侦听器接口。
 * 一个有兴趣处理<code> BeanContextServiceAvailableEvent </code>的类实现了这个接口。
 * 
 */
public interface BeanContextServicesListener extends BeanContextServiceRevokedListener {

    /**
     * The service named has been registered. getService requests for
     * this service may now be made.
     * <p>
     * 
     * @param bcsae the <code>BeanContextServiceAvailableEvent</code>
     */
    void serviceAvailable(BeanContextServiceAvailableEvent bcsae);
}
