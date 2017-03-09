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
 * Represents an element of an annotation type.
 *
 * <p>
 *  表示注释类型的元素。
 * 
 * 
 * @author Scott Seligman
 * @since 1.5
 */
public interface AnnotationTypeElementDoc extends MethodDoc {

    /**
     * Returns the default value of this element.
     * Returns null if this element has no default.
     *
     * <p>
     *  返回此元素的默认值。如果此元素没有默认值,则返回null。
     * 
     * @return the default value of this element.
     */
    AnnotationValue defaultValue();
}
