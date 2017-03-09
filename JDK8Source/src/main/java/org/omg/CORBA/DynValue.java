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
 *  with an IDL value type.
 * <p>
 *  与IDL值类型相关联的<code> DynAny </code>对象的表示形式。
 * 
 * 
 * @deprecated Use the new <a href="../DynamicAny/DynValue.html">DynValue</a> instead
 */
@Deprecated
public interface DynValue extends org.omg.CORBA.Object, org.omg.CORBA.DynAny {

    /**
     * Returns the name of the current member while traversing a
     * <code>DynAny</code> object that represents a Value object.
     *
     * <p>
     *  在遍历代表Value对象的<code> DynAny </code>对象时返回当前成员的名称。
     * 
     * 
     * @return the name of the current member
     */
    String current_member_name();

    /**
     * Returns the <code>TCKind</code> object that describes the current member.
     *
     * <p>
     *  返回描述当前成员的<code> TCKind </code>对象。
     * 
     * 
     * @return the <code>TCKind</code> object corresponding to the current
     * member
     */
    TCKind current_member_kind();

    /**
     * Returns an array containing all the members of the value object
     * stored in this <code>DynValue</code>.
     *
     * <p>
     *  返回包含存储在此<code> DynValue </code>中的值对象的所有成员的数组。
     * 
     * 
     * @return an array of name-value pairs.
         * @see #set_members
     */
    org.omg.CORBA.NameValuePair[] get_members();

    /**
     * Sets the members of the value object this <code>DynValue</code>
     * object represents to the given array of <code>NameValuePair</code>
         * objects.
     *
     * <p>
     *  将此<code> DynValue </code>对象表示的值对象的成员设置为<code> NameValuePair </code>对象的给定数组。
     * 
     * @param value the array of name-value pairs to be set
     * @throws org.omg.CORBA.DynAnyPackage.InvalidSeq
     *         if an inconsistent value is part of the given array
         * @see #get_members
     */
    void set_members(NameValuePair[] value)
        throws org.omg.CORBA.DynAnyPackage.InvalidSeq;
}
