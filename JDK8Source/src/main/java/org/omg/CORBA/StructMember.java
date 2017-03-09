/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2001, Oracle and/or its affiliates. All rights reserved.
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
 * File: ./org/omg/CORBA/StructMember.java
 * From: ./ir.idl
 * Date: Fri Aug 28 16:03:31 1998
 *   By: idltojava Java IDL 1.2 Aug 11 1998 02:00:18
 * <p>
 *  文件：./org/omg/CORBA/StructMember.java From：./ir.idl日期：Fri Aug 28 16:03:31 1998发布者：idltojava Java IDL 
 * 1.2 Aug 11 1998 02:00:18。
 * 
 */

package org.omg.CORBA;

/**
 * Describes a member of an IDL <code>struct</code> in the
 * Interface Repository, including
 * the  name of the <code>struct</code> member, the type of
 * the <code>struct</code> member, and
 * the typedef that represents the IDL type of the
 * <code>struct</code> member
 * described the <code>struct</code> member object.
 * <p>
 *  描述Interface Repository中的IDL <code> struct </code>的成员,包括<code> struct </code>成员的名称,<code> struct </code>
 * 成员的类型, typedef表示<code> struct </code>成员的IDL类型描述了<code> struct </code>成员对象。
 * 
 */
public final class StructMember implements org.omg.CORBA.portable.IDLEntity {

    //  instance variables

    /**
     * The name of the struct member described by
     * this <code>StructMember</code> object.
     * <p>
     *  由此<code> StructMember </code>对象描述的结构成员的名称。
     * 
     * 
     * @serial
     */
    public String name;

    /**
     * The type of the struct member described by
     * this <code>StructMember</code> object.
     * <p>
     *  由此<code> StructMember </code>对象描述的结构成员的类型。
     * 
     * 
     * @serial
     */
    public org.omg.CORBA.TypeCode type;

    /**
     * The typedef that represents the IDL type of the struct member described by
     * this <code>StructMember</code> object.
     * <p>
     *  typedef表示由此<code> StructMember </code>对象描述的struct成员的IDL类型。
     * 
     * 
     * @serial
     */
    public org.omg.CORBA.IDLType type_def;
    //  constructors

    /**
     * Constructs a default <code>StructMember</code> object.
     * <p>
     *  构造一个默认的<code> StructMember </code>对象。
     * 
     */
    public StructMember() { }

    /**
     * Constructs a <code>StructMember</code> object initialized with the
     * given values.
     * <p>
     *  构造使用给定值初始化的<code> StructMember </code>对象。
     * 
     * @param __name a <code>String</code> object with the name of the struct
     *        member
     * @param __type a <code>TypeCode</code> object describing the type of the struct
     *        member
     * @param __type_def an <code>IDLType</code> object representing the IDL type
     *        of the struct member
     */
    public StructMember(String __name, org.omg.CORBA.TypeCode __type, org.omg.CORBA.IDLType __type_def) {
        name = __name;
        type = __type;
        type_def = __type_def;
    }
}
