/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2000, Oracle and/or its affiliates. All rights reserved.
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
/*
 * File: ./org/omg/CORBA/UnionMember.java
 * From: ./ir.idl
 * Date: Fri Aug 28 16:03:31 1998
 *   By: idltojava Java IDL 1.2 Aug 11 1998 02:00:18
 * <p>
 *  文件：./org/omg/CORBA/UnionMember.java From：./ir.idl日期：Fri Aug 28 16:03:31 1998发布者：idltojava Java IDL 1
 * .2 Aug 11 1998 02:00:18。
 * 
 */

package org.omg.CORBA;

/**
 * A description in the Interface Repository of a member of an IDL union.
 * <p>
 *  接口存储库中IDL联合成员的说明。
 * 
 */
public final class UnionMember implements org.omg.CORBA.portable.IDLEntity {
    //  instance variables

    /**
     * The name of the union member described by this
     * <code>UnionMember</code> object.
     * <p>
     *  由此<code> UnionMember </code>对象描述的联合成员的名称。
     * 
     * 
     * @serial
     */
    public String name;

    /**
     * The label of the union member described by this
     * <code>UnionMember</code> object.
     * <p>
     *  由此<code> UnionMember </code>对象描述的联合成员的标签。
     * 
     * 
     * @serial
     */
    public org.omg.CORBA.Any label;

    /**
     * The type of the union member described by this
     * <code>UnionMember</code> object.
     * <p>
     *  由此<code> UnionMember </code>对象描述的联合成员的类型。
     * 
     * 
     * @serial
     */
    public org.omg.CORBA.TypeCode type;

    /**
     * The typedef that represents the IDL type of the union member described by this
     * <code>UnionMember</code> object.
     * <p>
     *  typedef表示由此<code> UnionMember </code>对象描述的联合成员的IDL类型。
     * 
     * 
     * @serial
     */
    public org.omg.CORBA.IDLType type_def;

    //  constructors

    /**
     * Constructs a new <code>UnionMember</code> object with its fields initialized
     * to null.
     * <p>
     *  构造一个新的<code> UnionMember </code>对象,其字段初始化为null。
     * 
     */
    public UnionMember() { }

    /**
     * Constructs a new <code>UnionMember</code> object with its fields initialized
     * to the given values.
     *
     * <p>
     *  构造一个新的<code> UnionMember </code>对象,其字段已初始化为给定的值。
     * 
     * @param __name a <code>String</code> object with the name of this
     *        <code>UnionMember</code> object
     * @param __label an <code>Any</code> object with the label of this
     *        <code>UnionMember</code> object
     * @param __type a <code>TypeCode</code> object describing the type of this
     *        <code>UnionMember</code> object
     * @param __type_def an <code>IDLType</code> object that represents the
     *        IDL type of this <code>UnionMember</code> object
     */
    public UnionMember(String __name, org.omg.CORBA.Any __label, org.omg.CORBA.TypeCode __type, org.omg.CORBA.IDLType __type_def) {
        name = __name;
        label = __label;
        type = __type;
        type_def = __type_def;
    }
}
