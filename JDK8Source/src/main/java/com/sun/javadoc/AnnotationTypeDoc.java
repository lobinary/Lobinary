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
 * Represents an annotation type.
 *
 * <p>
 *  表示注释类型。
 * 
 * 
 * @author Scott Seligman
 * @since 1.5
 */
public interface AnnotationTypeDoc extends ClassDoc {

    /**
     * Returns the elements of this annotation type.
     * Returns an empty array if there are none.
     *
     * <p>
     *  返回此注记类型的元素。如果没有,返回一个空数组。
     * 
     * @return the elements of this annotation type.
     */
    AnnotationTypeElementDoc[] elements();
}
