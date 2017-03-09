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
 * The <code>DynUnion</code> interface represents a <code>DynAny</code> object
 * that is associated with an IDL union.
 * Union values can be traversed using the operations defined in <code>DynAny</code>.
 * The first component in the union corresponds to the discriminator;
 * the second corresponds to the actual value of the union.
 * Calling the method <code>next()</code> twice allows you to access both components.
 * <p>
 *  <code> DynUnion </code>接口表示与IDL联合相关联的<code> DynAny </code>对象。可以使用<code> DynAny </code>中定义的操作遍历联合值。
 * 联合中的第一个分量对应于鉴别器;第二个对应于联合的实际值。调用方法<code> next()</code>两次允许您访问这两个组件。
 * 
 * 
 * @deprecated Use the new <a href="../DynamicAny/DynUnion.html">DynUnion</a> instead
 */
@Deprecated
public interface DynUnion extends org.omg.CORBA.Object, org.omg.CORBA.DynAny
{
    /**
     * Determines whether the discriminator associated with this union has been assigned
     * a valid default value.
     * <p>
     *  确定与此联合关联的鉴别器是否已分配有效的默认值。
     * 
     * 
     * @return <code>true</code> if the discriminator has a default value;
     * <code>false</code> otherwise
     */
    public boolean set_as_default();

    /**
    * Determines whether the discriminator associated with this union gets assigned
    * a valid default value.
    * <p>
    *  确定与此联合关联的鉴别器是否被分配有效的默认值。
    * 
    * 
    * @param arg <code>true</code> if the discriminator gets assigned a default value
    */
    public void set_as_default(boolean arg);

    /**
    * Returns a DynAny object reference that must be narrowed to the type
    * of the discriminator in order to insert/get the discriminator value.
    * <p>
    *  返回一个DynAny对象引用,必须缩小到该鉴别器的类型,以插入/获取鉴别器值。
    * 
    * 
    * @return a <code>DynAny</code> object reference representing the discriminator value
    */
    public org.omg.CORBA.DynAny discriminator();

    /**
    * Returns the TCKind object associated with the discriminator of this union.
    * <p>
    *  返回与此联合的鉴别符关联的TCKind对象。
    * 
    * 
    * @return the <code>TCKind</code> object associated with the discriminator of this union
    */
    public org.omg.CORBA.TCKind discriminator_kind();

    /**
    * Returns a DynAny object reference that is used in order to insert/get
    * a member of this union.
    * <p>
    *  返回一个DynAny对象引用,用于插入/获取此联合的成员。
    * 
    * 
    * @return the <code>DynAny</code> object representing a member of this union
    */
    public org.omg.CORBA.DynAny member();

    /**
    * Allows for the inspection of the name of this union member
    * without checking the value of the discriminator.
    * <p>
    *  允许检查此联合成员的名称,而不检查鉴别器的值。
    * 
    * 
    * @return the name of this union member
    */
    public String member_name();

    /**
    * Allows for the assignment of the name of this union member.
    * <p>
    *  允许指定此联合成员的名称。
    * 
    * 
    * @param arg the new name of this union member
    */
    public void member_name(String arg);

    /**
    * Returns the TCKind associated with the member of this union.
    * <p>
    *  返回与此联合成员关联的TCKind。
    * 
    * @return the <code>TCKind</code> object associated with the member of this union
    */
    public org.omg.CORBA.TCKind member_kind();
}
