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

package com.sun.corba.se.spi.presentation.rmi ;

import java.lang.reflect.Method ;

/** Translates between methods on an interface and RMI-IIOP encodings
 * of those methods as names.
 * <p>
 *  的方法作为名称。
 * 
 */
public interface IDLNameTranslator
{
    /** Get the interfaces that this IDLNameTranslator describes.
    /* <p>
     */
    Class[] getInterfaces() ;

    /** Get all methods for this remote interface.
     * The methods are returned in a canonical order, that is,
     * they are always in the same order for a particular interface.
     * <p>
     *  这些方法以规范的顺序返回,也就是说,它们对于特定接口而言总是以相同的顺序。
     * 
     */
    Method[] getMethods() ;

    /** Get the method from this IDLNameTranslator's interfaces that
     * corresponds to the mangled name idlName.  Returns null
     * if there is no matching method.
     * <p>
     *  对应于标记名称idlName。如果没有匹配的方法,则返回null。
     * 
     */
    Method getMethod( String idlName )  ;

    /** Get the mangled name that corresponds to the given method
     * on this IDLNameTranslator's interface.  Returns null
     * if there is no matching name.
     * <p>
     *  在此IDLNameTranslator的接口。如果没有匹配的名称,则返回null。
     */
    String getIDLName( Method method )  ;
}
