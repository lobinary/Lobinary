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
 * {@code AnnotatedParameterizedType} represents the potentially annotated use
 * of a parameterized type, whose type arguments may themselves represent
 * annotated uses of types.
 *
 * <p>
 *  {@code AnnotatedParameterizedType}表示参数化类型的潜在注释使用,其类型参数本身可以表示类型的注释使用。
 * 
 * 
 * @since 1.8
 */
public interface AnnotatedParameterizedType extends AnnotatedType {

    /**
     * Returns the potentially annotated actual type arguments of this parameterized type.
     *
     * <p>
     *  返回此参数化类型的可能带注释的实际类型参数。
     * 
     * @return the potentially annotated actual type arguments of this parameterized type
     */
    AnnotatedType[] getAnnotatedActualTypeArguments();
}
