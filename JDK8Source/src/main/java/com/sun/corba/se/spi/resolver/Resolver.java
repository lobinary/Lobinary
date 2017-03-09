/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.spi.resolver ;

/** Resolver defines the operations needed to support ORB operations for
 * resolve_initial_references and list_initial_services.
 * <p>
 *  resolve_initial_references和list_initial_services。
 * 
 */
public interface Resolver {
    /** Look up the name using this resolver and return the CORBA object
     * reference bound to this name, if any.  Returns null if no object
     * is bound to the name.
     * <p>
     *  引用绑定到此名称(如果有)。如果没有对象绑定到名称,则返回null。
     * 
     */
    org.omg.CORBA.Object resolve( String name ) ;

    /** Return the entire collection of names that are currently bound
     * by this resolver.  Resulting collection contains only strings for
     * which resolve does not return null.  Some resolvers may not support
     * this method, in which case they return an empty set.
     * <p>
     *  这个解析器。结果集合仅包含其解析不返回null的字符串。一些解析器可能不支持此方法,在这种情况下,它们返回一个空集。
     */
    java.util.Set list() ;
}
