/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2004, Oracle and/or its affiliates. All rights reserved.
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


/** Represents a <tt>DynAny</tt> object associated
 *  with an array.
 * <p>
 *  与数组。
 * 
 * 
 * @deprecated Use the new <a href="../DynamicAny/DynArray.html">DynArray</a> instead
 */
@Deprecated
public interface DynArray extends org.omg.CORBA.Object, org.omg.CORBA.DynAny
{
    /**
     * Returns the value of all the elements of this array.
     *
     * <p>
     *  返回此数组的所有元素的值。
     * 
     * 
     * @return the array of <code>Any</code> objects that is the value
         *         for this <code>DynArray</code> object
         * @see #set_elements
     */
    public org.omg.CORBA.Any[] get_elements();

    /**
     * Sets the value of this
     * <code>DynArray</code> object to the given array.
     *
     * <p>
     *  将<code> DynArray </code>对象的值设置为给定数组。
     * 
     * @param value the array of <code>Any</code> objects
     * @exception InvalidSeq if the sequence is bad
         * @see #get_elements
     */
    public void set_elements(org.omg.CORBA.Any[] value)
        throws org.omg.CORBA.DynAnyPackage.InvalidSeq;
}
