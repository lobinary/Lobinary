/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2006, Oracle and/or its affiliates. All rights reserved.
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
 * Represents a field in a java class.
 *
 * <p>
 *  表示java类中的字段。
 * 
 * 
 * @see MemberDoc
 *
 * @since 1.2
 * @author Robert Field
 */
public interface FieldDoc extends MemberDoc {

    /**
     * Get type of this field.
     * <p>
     *  获取此字段的类型。
     * 
     */
    Type type();

    /**
     * Return true if this field is transient
     * <p>
     *  如果此字段为瞬态,则返回true
     * 
     */
    boolean isTransient();

    /**
     * Return true if this field is volatile
     * <p>
     *  如果此字段为volatile,则返回true
     * 
     */
    boolean isVolatile();

    /**
     * Return the serialField tags in this FieldDoc item.
     *
     * <p>
     *  返回此FieldDoc项中的serialField标记。
     * 
     * 
     * @return an array of <tt>SerialFieldTag</tt> objects containing
     *         all <code>@serialField</code> tags.
     */
    SerialFieldTag[] serialFieldTags();

    /**
     * Get the value of a constant field.
     *
     * <p>
     *  获取常量字段的值。
     * 
     * 
     * @return the value of a constant field. The value is
     * automatically wrapped in an object if it has a primitive type.
     * If the field is not constant, returns null.
     */
    Object constantValue();

    /**
     * Get the value of a constant field.
     *
     * <p>
     *  获取常量字段的值。
     * 
     * @return the text of a Java language expression whose value
     * is the value of the constant. The expression uses no identifiers
     * other than primitive literals. If the field is
     * not constant, returns null.
     */
    String constantValueExpression();
}
