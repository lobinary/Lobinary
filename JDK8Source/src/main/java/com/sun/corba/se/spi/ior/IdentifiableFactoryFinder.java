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

package com.sun.corba.se.spi.ior;

import org.omg.CORBA_2_3.portable.InputStream ;

/** Interface used to manage a group of related IdentifiableFactory instances.
 * Factories can be registered, and invoked through a create method, which
 * must be implemented to handle the case of no registered factory
 * appropriately.
 * <p>
 *  工厂可以注册,并通过create方法调用,必须实现它来处理没有注册工厂的情况。
 * 
 * 
 * @author Ken Cavanaugh
 */
public interface IdentifiableFactoryFinder
{
    /** If there is a registered factory for id, use it to
     * read an Identifiable from is.  Otherwise create an
     * appropriate generic container, or throw an error.
     * The type of generic container, or error behavior is
     * a property of the implementation.
     * <p>
     *  阅读一个Identifiable from。否则创建一个适当的通用容器,否则抛出一个错误。通用容器的类型或错误行为是实现的属性。
     * 
     */
    Identifiable create(int id, InputStream is);

    /** Register a factory for the given id.
    /* <p>
     */
    void registerFactory( IdentifiableFactory factory ) ;
}
