/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2004, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.spi.orbutil.proxy ;

import java.lang.reflect.InvocationHandler ;

public interface InvocationHandlerFactory
{
    /** Get an InvocationHandler.
    /* <p>
     */
    InvocationHandler getInvocationHandler() ;

    /** Get the interfaces that InvocationHandler instances
     * produced by this InvocationHandlerFactory support.
     * <p>
     *  由此InvocationHandlerFactory支持生成。
     */
    Class[] getProxyInterfaces() ;
}
