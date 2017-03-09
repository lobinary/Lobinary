/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2012, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.javadoc;

/**
 * Documents a Serializable field defined by an ObjectStreamField.
 * <pre>
 * The class parses and stores the three serialField tag parameters:
 *
 * - field name
 * - field type name
 *      (fully-qualified or visible from the current import context)
 * - description of the valid values for the field

 * </pre>
 * This tag is only allowed in the javadoc for the special member
 * serialPersistentFields.
 *
 * <p>
 *  文档由ObjectStreamField定义的可序列化字段。
 * <pre>
 *  类解析并存储三个serialField标记参数：
 * 
 *   - 字段名称 - 字段类型名称(从当前导入上下文中是完全限定的或可见的) - 字段的有效值的描述
 * 
 * </pre>
 *  此标记仅在特殊成员serialPersistentFields的javadoc中允许。
 * 
 * 
 * @author Joe Fialli
 *
 * @see java.io.ObjectStreamField
 */
public interface SerialFieldTag extends Tag, Comparable<Object> {

    /**
     * Return the serializable field name.
     * <p>
     *  返回可序列化字段名称。
     * 
     */
    public String fieldName();

    /**
     * Return the field type string.
     * <p>
     *  返回字段类型字符串。
     * 
     */
    public String fieldType();

    /**
     * Return the ClassDoc for field type.
     *
     * <p>
     *  返回字段类型的ClassDoc。
     * 
     * 
     * @return null if no ClassDoc for field type is visible from
     *         containingClass context.
     */
    public ClassDoc fieldTypeDoc();

    /**
     * Return the field comment. If there is no serialField comment, return
     * javadoc comment of corresponding FieldDoc.
     * <p>
     *  返回字段注释。如果没有serialField注释,返回对应的FieldDoc的javadoc注释。
     * 
     */
    public String description();

    /**
     * Compares this Object with the specified Object for order.  Returns a
     * negative integer, zero, or a positive integer as this Object is less
     * than, equal to, or greater than the given Object.
     * <p>
     * Included to make SerialFieldTag items java.lang.Comparable.
     *
     * <p>
     *  将此对象与指定的对象进行比较。返回负整数,零或正整数,因为此Object小于,等于或大于给定的Object。
     * <p>
     * 
     * @param   obj the <code>Object</code> to be compared.
     * @return  a negative integer, zero, or a positive integer as this Object
     *          is less than, equal to, or greater than the given Object.
     * @exception ClassCastException the specified Object's type prevents it
     *            from being compared to this Object.
     * @since 1.2
     */
    public int compareTo(Object obj);
}
