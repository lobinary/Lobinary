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
 *  Represents a <code>DynAny</code> object that is associated
 *  with an IDL fixed type.
 * <p>
 *  表示与IDL固定类型相关联的<code> DynAny </code>对象。
 * 
 * 
 * @deprecated Use the new <a href="../DynamicAny/DynFixed.html">DynFixed</a> instead
 */
@Deprecated
public interface DynFixed extends org.omg.CORBA.Object, org.omg.CORBA.DynAny
{
    /**
     * Returns the value of the fixed type represented in this
     * <code>DynFixed</code> object.
     *
     * <p>
     *  返回此<code> DynFixed </code>对象中表示的固定类型的值。
     * 
     * 
     * @return the value as a byte array
         * @see #set_value
     */
    public byte[] get_value();

    /**
     * Sets the given fixed type instance as the value for this
     * <code>DynFixed</code> object.
     *
     * <p>
     *  将给定的固定类型实例设置为此<code> DynFixed </code>对象的值。
     * 
     * @param val the value of the fixed type as a byte array
         * @throws org.omg.CORBA.DynAnyPackage.InvalidValue if the given
         *         argument is bad
         * @see #get_value
     */
    public void set_value(byte[] val)
        throws org.omg.CORBA.DynAnyPackage.InvalidValue;
}
