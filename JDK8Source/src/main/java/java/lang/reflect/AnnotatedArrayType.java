/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.lang.reflect;


/**
 * {@code AnnotatedArrayType} represents the potentially annotated use of an
 * array type, whose component type may itself represent the annotated use of a
 * type.
 *
 * <p>
 *  {@code AnnotatedArrayType}表示数组类型的潜在注释使用,其组件类型本身可以表示类型的注释使用。
 * 
 * 
 * @since 1.8
 */
public interface AnnotatedArrayType extends AnnotatedType {

    /**
     * Returns the potentially annotated generic component type of this array type.
     *
     * <p>
     *  返回此数组类型的可能带注释的通用组件类型。
     * 
     * @return the potentially annotated generic component type of this array type
     */
    AnnotatedType  getAnnotatedGenericComponentType();
}
