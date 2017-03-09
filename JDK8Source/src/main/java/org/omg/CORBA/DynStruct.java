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
 *  with an IDL struct.
 * <p>
 *  与IDL结构相关联的<code> DynAny </code>对象的表示形式。
 * 
 * 
 * @deprecated Use the new <a href="../DynamicAny/DynStruct.html">DynStruct</a> instead
 */
@Deprecated
public interface DynStruct extends org.omg.CORBA.Object, org.omg.CORBA.DynAny
{
    /**
     * During a traversal, returns the name of the current member.
     *
     * <p>
     *  在遍历期间,返回当前成员的名称。
     * 
     * 
     * @return the string name of the current member
     */
    public String current_member_name();

    /**
     * Returns the <code>TCKind</code> object that describes the kind of
         * the current member.
     *
     * <p>
     *  返回描述当前成员类型的<code> TCKind </code>对象。
     * 
     * 
     * @return the <code>TCKind</code> object that describes the current member
     */
    public org.omg.CORBA.TCKind current_member_kind();

    /**
     * Returns an array containing all the members of the stored struct.
     *
     * <p>
     *  返回一个包含存储结构的所有成员的数组。
     * 
     * 
     * @return the array of name-value pairs
         * @see #set_members
     */
    public org.omg.CORBA.NameValuePair[] get_members();

    /**
     * Set the members of the struct.
     *
     * <p>
     *  设置结构体的成员。
     * 
     * @param value the array of name-value pairs.
         * @throws org.omg.CORBA.DynAnyPackage.InvalidSeq if the given argument
         *         is invalid
         * @see #get_members
     */
    public void set_members(org.omg.CORBA.NameValuePair[] value)
        throws org.omg.CORBA.DynAnyPackage.InvalidSeq;
}
