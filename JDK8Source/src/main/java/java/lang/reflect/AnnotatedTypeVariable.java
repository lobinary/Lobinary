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
 * {@code AnnotatedTypeVariable} represents the potentially annotated use of a
 * type variable, whose declaration may have bounds which themselves represent
 * annotated uses of types.
 *
 * <p>
 *  {@code AnnotatedTypeVariable}表示类型变量的潜在注释使用,其声明可能具有其本身表示类型的注释使用的边界。
 * 
 * 
 * @since 1.8
 */
public interface AnnotatedTypeVariable extends AnnotatedType {

    /**
     * Returns the potentially annotated bounds of this type variable.
     *
     * <p>
     *  返回此类型变量的可能带注释的边界。
     * 
     * @return the potentially annotated bounds of this type variable
     */
    AnnotatedType[] getAnnotatedBounds();
}
