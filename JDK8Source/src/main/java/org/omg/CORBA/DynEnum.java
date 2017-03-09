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

/** Represents a <tt>DynAny</tt> object  associated
 *  with an IDL enum.
 * <p>
 *  与IDL枚举。
 * 
 * 
 * @deprecated Use the new <a href="../DynamicAny/DynEnum.html">DynEnum</a> instead
 */
@Deprecated
public interface DynEnum extends org.omg.CORBA.Object, org.omg.CORBA.DynAny
{
    /**
     * Return the value of the IDL enum stored in this
     * <code>DynEnum</code> as a string.
     *
     * <p>
     *  将存储在此<code> DynEnum </code>中的IDL枚举的值作为字符串返回。
     * 
     * 
     * @return the stringified value.
     */
    public String value_as_string();

    /**
     * Set a particular enum in this <code>DynEnum</code>.
     *
     * <p>
     *  在此<code> DynEnum </code>中设置特定的枚举。
     * 
     * 
     * @param arg the string corresponding to the value.
     */
    public void value_as_string(String arg);

    /**
     * Return the value of the IDL enum as a Java int.
     *
     * <p>
     *  将IDL枚举的值作为Java int返回。
     * 
     * 
     * @return the integer value.
     */
    public int value_as_ulong();

    /**
     * Set the value of the IDL enum.
     *
     * <p>
     *  设置IDL枚举的值。
     * 
     * @param arg the int value of the enum.
     */
    public void value_as_ulong(int arg);
}
