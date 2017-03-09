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

/** Defines the code used to represent a truncatable value type in
* a typecode. A value type is truncatable if it inherits "safely"
* from another value type, which means it can be cast to a more
* general inherited type.
* This is one of the possible results of the <code>type_modifier</code>
* method on the <code>TypeCode</code> interface.
* <p>
*  类型代码。如果值类型从另一个值类型继承"安全",那么值类型是可截断的,这意味着它可以转换为更一般的继承类型。
* 这是<code> TypeCode </code>接口上的<code> type_modifier </code>方法的可能结果之一。
* 
* 
* @see org.omg.CORBA.TypeCode
*/
public interface VM_TRUNCATABLE {
    /** The value representing a truncatable value type in
    * a typecode.
    * <p>
    */
    final short value = (short) (3L);
}
