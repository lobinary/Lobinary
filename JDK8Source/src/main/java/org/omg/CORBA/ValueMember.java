/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2000, Oracle and/or its affiliates. All rights reserved.
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
 * File: ./org/omg/CORBA/ValueMember.java
 * From: ./ir.idl
 * Date: Fri Aug 28 16:03:31 1998
 *   By: idltojava Java IDL 1.2 Aug 11 1998 02:00:18
 * <p>
 *  文件：./org/omg/CORBA/ValueMember.java From：./ir.idl日期：Fri Aug 28 16:03:31 1998发布者：idltojava Java IDL 1
 * .2 Aug 11 1998 02:00:18。
 * 
 */

package org.omg.CORBA;

/**
 * A description in the Interface Repository of
 * a member of a <code>value</code> object.
 * <p>
 *  Interface Repository中对<code> value </code>对象的成员的描述。
 * 
 */
public final class ValueMember implements org.omg.CORBA.portable.IDLEntity {

    //  instance variables

    /**
     * The name of the <code>value</code> member described by this
     * <code>ValueMember</code> object.
     * <p>
     *  由此<code> ValueMember </code>对象描述的<code>值</code>成员的名称。
     * 
     * 
     * @serial
     */
    public String name;

    /**
     * The repository ID of the <code>value</code> member described by
     * this <code>ValueMember</code> object;
     * <p>
     *  由<code> ValueMember </code>对象描述的<code>值</code>成员的存储库ID;
     * 
     * 
     * @serial
     */
    public String id;

    /**
     * The repository ID of the <code>value</code> in which this member
     * is defined.
     * <p>
     *  定义此成员的<code>值</code>的存储库ID。
     * 
     * 
     * @serial
     */
    public String defined_in;

    /**
     * The version of the <code>value</code> in which this member is defined.
     * <p>
     *  定义此成员的<code>值</code>的版本。
     * 
     * 
     * @serial
     */
    public String version;

    /**
     * The type of of this <code>value</code> member.
     * <p>
     *  此<code>值</code>成员的类型。
     * 
     * 
     * @serial
     */
    public org.omg.CORBA.TypeCode type;

    /**
     * The typedef that represents the IDL type of the <code>value</code>
     * member described by this <code>ValueMember</code> object.
     * <p>
     *  typedef表示由<code> ValueMember </code>对象描述的<code>值</code>成员的IDL类型。
     * 
     * 
     * @serial
     */
    public org.omg.CORBA.IDLType type_def;

    /**
     * The type of access (public, private) for the <code>value</code>
     * member described by this <code>ValueMember</code> object.
     * <p>
     *  由<code> ValueMember </code>对象描述的<code> value </code>成员的访问类型(public,private)。
     * 
     * 
     * @serial
     */
    public short access;
    //  constructors

    /**
     * Constructs a default <code>ValueMember</code> object.
     * <p>
     *  构造一个默认的<code> ValueMember </code>对象。
     * 
     */
    public ValueMember() { }

    /**
     * Constructs a <code>ValueMember</code> object initialized with
     * the given values.
     *
     * <p>
     *  构造使用给定值初始化的<code> ValueMember </code>对象。
     * 
     *@param __name The name of the <code>value</code> member described by this
     * <code>ValueMember</code> object.
     *@param __id The repository ID of the <code>value</code> member described by
     * this <code>ValueMember</code> object;
     *@param __defined_in The repository ID of the <code>value</code> in which this member
     * is defined.
     *@param __version The version of the <code>value</code> in which this member is defined.
     *@param __type The type of of this <code>value</code> member.
     *@param __type_def The typedef that represents the IDL type of the <code>value</code>
     * member described by this <code>ValueMember</code> object.
     *@param __access The type of access (public, private) for the <code>value</code>
     * member described by this <code>ValueMember</code> object.
     */
    public ValueMember(String __name, String __id, String __defined_in, String __version, org.omg.CORBA.TypeCode __type, org.omg.CORBA.IDLType __type_def, short __access) {
        name = __name;
        id = __id;
        defined_in = __defined_in;
        version = __version;
        type = __type;
        type_def = __type_def;
        access = __access;
    }
}
