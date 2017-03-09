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

package org.omg.CORBA;

/** Defines the code used to represent an Abstract interface in
* a typecode.
* This is one of the possible results of the <code>type_modified</code>
* method on the <code>TypeCode</code> interface.
* <p>
*  类型代码。这是<code> TypeCode </code>接口上的<code> type_modified </code>方法的可能结果之一。
* 
* 
* @see org.omg.CORBA.TypeCode
*/
public interface VM_ABSTRACT {
    /** The value representing an abstract interface value type in
    * a typecode.
    * <p>
    *  类型代码。
    */
    final short value = (short) (2L);
}
