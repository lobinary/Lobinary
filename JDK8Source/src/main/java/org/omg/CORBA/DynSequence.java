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

/**
 * The representation of a <code>DynAny</code> object that is associated
 * with an IDL sequence.
 * <p>
 *  与IDL序列相关联的<code> DynAny </code>对象的表示。
 * 
 * 
 * @deprecated Use the new <a href="../DynamicAny/DynSequence.html">DynSequence</a> instead
 */
@Deprecated
public interface DynSequence extends org.omg.CORBA.Object, org.omg.CORBA.DynAny
{

    /**
     * Returns the length of the sequence represented by this
     * <code>DynFixed</code> object.
     *
     * <p>
     *  返回由此<code> DynFixed </code>对象表示的序列的长度。
     * 
     * 
     * @return the length of the sequence
     */
    public int length();

    /**
     * Sets the length of the sequence represented by this
     * <code>DynFixed</code> object to the given argument.
     *
     * <p>
     *  将由此<code> DynFixed </code>对象表示的序列的长度设置为给定的参数。
     * 
     * 
     * @param arg the length of the sequence
     */
    public void length(int arg);

    /**
     * Returns the value of every element in this sequence.
     *
     * <p>
     *  返回此序列中每个元素的值。
     * 
     * 
     * @return an array of <code>Any</code> objects containing the values in
         *         the sequence
         * @see #set_elements
     */
    public org.omg.CORBA.Any[] get_elements();

    /**
     * Sets the values of all elements in this sequence with the given
         * array.
     *
     * <p>
     *  使用给定的数组设置此序列中所有元素的值。
     * 
     * @param value the array of <code>Any</code> objects to be set
     * @exception InvalidSeq if the array of values is bad
         * @see #get_elements
     */
    public void set_elements(org.omg.CORBA.Any[] value)
        throws org.omg.CORBA.DynAnyPackage.InvalidSeq;
}
