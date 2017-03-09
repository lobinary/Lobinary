/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * Represents an annotation.
 * An annotation associates a value with each element of an annotation type.
 *
 * <p>
 *  表示注释。注释将值与注释类型的每个元素相关联。
 * 
 * 
 * @author Scott Seligman
 * @since 1.5
 */
public interface AnnotationDesc {

    /**
     * Returns the annotation type of this annotation.
     *
     * <p>
     *  返回此注释的注释类型。
     * 
     * 
     * @return the annotation type of this annotation.
     */
    AnnotationTypeDoc annotationType();

    /**
     * Returns this annotation's elements and their values.
     * Only those explicitly present in the annotation are
     * included, not those assuming their default values.
     * Returns an empty array if there are none.
     *
     * <p>
     *  返回此注释的元素及其值。仅包括明确存在于注释中的那些,而不是那些假定其默认值的那些。如果没有,返回一个空数组。
     * 
     * 
     * @return this annotation's elements and their values.
     */
    ElementValuePair[] elementValues();

    /**
     * Check for the synthesized bit on the annotation.
     *
     * <p>
     *  检查注释上的合成位。
     * 
     * 
     * @return true if the annotation is synthesized.
     */
    boolean isSynthesized();

    /**
     * Represents an association between an annotation type element
     * and one of its values.
     *
     * <p>
     *  表示注释类型元素与其值之间的关联。
     * 
     * 
     * @author Scott Seligman
     * @since 1.5
     */
    public interface ElementValuePair {

        /**
         * Returns the annotation type element.
         *
         * <p>
         *  返回注释类型元素。
         * 
         * 
         * @return the annotation type element.
         */
        AnnotationTypeElementDoc element();

        /**
         * Returns the value associated with the annotation type element.
         *
         * <p>
         *  返回与注记类型元素关联的值。
         * 
         * @return the value associated with the annotation type element.
         */
        AnnotationValue value();
    }
}
