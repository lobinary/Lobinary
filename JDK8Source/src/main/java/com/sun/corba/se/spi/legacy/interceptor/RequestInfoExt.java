/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2003, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.spi.legacy.interceptor;

import com.sun.corba.se.spi.legacy.connection.Connection;

/**
 * This interface is implemented by our implementation of
 * PortableInterceptor.ClientRequestInfo and
 * PortableInterceptor.ServerRequestInfo. <p>
 *
 * <p>
 *  这个接口通过我们的PortableInterceptor.ClientRequestInfo和PortableInterceptor.ServerRequestInfo的实现来实现。 <p>
 * 
 */

public interface RequestInfoExt
{
    /**
    /* <p>
    /* 
     * @return The connection on which the request is made.
     *         The return value will be null when a local transport
     *         is used.
     */
    public Connection connection();
}
