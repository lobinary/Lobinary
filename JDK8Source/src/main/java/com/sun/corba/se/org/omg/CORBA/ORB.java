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

package com.sun.corba.se.org.omg.CORBA ;

import org.omg.CORBA.ORBPackage.InvalidName ;

/** This ORB class is defined to provide a home for the
* register_initial_reference operation, which is added by
* portable interceptors and CORBA 2.4.  It is added here
* until there is an official Java language mapping for the
* method.
* <p>
*  register_initial_reference操作,由便携式拦截器和CORBA 2.4添加。它被添加到这里,直到有一个官方Java语言映射的方法。
* 
*/
abstract public class ORB extends org.omg.CORBA_2_3.ORB
{
    /**
     * If this operation is called with an id, <code>"Y"</code>, and an
     * object, <code>YY</code>, then a subsequent call to
     * <code>ORB.resolve_initial_references( "Y" )</code> will
     * return object <code>YY</code>.
     *
     * <p>
     *  如果使用id,<code>"Y"</code>和对象<code> YY </code>调用此操作,则随后调用<code> ORB.resolve_initial_references("Y" / co
     * de>将返回对象<code> YY </code>。
     * 
     * @param id The ID by which the initial reference will be known.
     * @param obj The initial reference itself.
     * @throws InvalidName if this operation is called with an empty string id
     *     or this operation is called with an id that is already registered,
     *     including the default names defined by OMG.
     * @throws BAD_PARAM if the obj parameter is null.
     */
    public void register_initial_reference( String id,
                                            org.omg.CORBA.Object obj )
        throws InvalidName
    {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }
}
