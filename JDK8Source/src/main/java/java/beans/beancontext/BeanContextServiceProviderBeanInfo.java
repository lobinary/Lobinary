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

import java.beans.BeanInfo;

/**
 * A BeanContextServiceProvider implementor who wishes to provide explicit
 * information about the services their bean may provide shall implement a
 * BeanInfo class that implements this BeanInfo subinterface and provides
 * explicit information about the methods, properties, events, etc, of their
 * services.
 * <p>
 *  一个BeanContextServiceProvider实现者希望提供关于它们的bean可能提供的服务的显式信息应该实现一个BeanInfo类,它实现这个BeanInfo子接口并提供关于他们的服务的方
 * 法,属性,事件等的显式信息。
 * 
 */

public interface BeanContextServiceProviderBeanInfo extends BeanInfo {

    /**
     * Gets a <code>BeanInfo</code> array, one for each
     * service class or interface statically available
     * from this ServiceProvider.
     * <p>
     * 
     * @return the <code>BeanInfo</code> array
     */
    BeanInfo[] getServicesBeanInfo();
}
