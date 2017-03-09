/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, 2003, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.spi.protocol;

import com.sun.corba.se.spi.resolver.Resolver ;

/** InitialServerRequestDispatcher is a specialized version of a ServerRequestDispatcher
 * that provides an initialization method.  This delegate is used
 * to implement bootstrapping of initial object references.
 * <p>
 *  提供了一种初始化方法。此委托用于实现初始对象引用的引导。
 * 
 */
public interface InitialServerRequestDispatcher
    extends CorbaServerRequestDispatcher
{
    /** Plug in the resolver that this InitialServerRequestDispatcher should
     * use in order to lookup or list initial name to object reference
     * bindings.
     * <p>
     *  使用以便查找或列出初始名称到对象引用绑定。
     */
    void init( Resolver resolver ) ;
}
