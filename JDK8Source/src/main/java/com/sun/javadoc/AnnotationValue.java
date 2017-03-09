/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2004, Oracle and/or its affiliates. All rights reserved.
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
 * Represents a value of an annotation type element.
 *
 * <p>
 *  表示注释类型元素的值。
 * 
 * 
 * @author Scott Seligman
 * @since 1.5
 */
public interface AnnotationValue {

    /**
     * Returns the value.
     * The type of the returned object is one of the following:
     * <ul><li> a wrapper class for a primitive type
     *     <li> <code>String</code>
     *     <li> <code>Type</code> (representing a class literal)
     *     <li> <code>FieldDoc</code> (representing an enum constant)
     *     <li> <code>AnnotationDesc</code>
     *     <li> <code>AnnotationValue[]</code>
     * </ul>
     *
     * <p>
     *  返回值。
     * 返回对象的类型是下列之一：<ul> <li> <li> <code> String </code> <li> <code>类型</code>的封装类类字面值)<li> <code> FieldDoc </code>
     * (表示枚举常量)<li> <code> AnnotationDesc </code> <li> <code> AnnotationValue [] </code>。
     *  返回值。
     * </ul>
     * 
     * @return the value.
     */
    Object value();

    /**
     * Returns a string representation of the value.
     *
     * <p>
     * 
     * 
     * @return the text of a Java language annotation value expression
     *          whose value is the value of this element.
     */
    String toString();
}
