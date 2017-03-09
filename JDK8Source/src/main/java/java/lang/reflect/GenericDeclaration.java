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

package java.lang.reflect;

/**
 * A common interface for all entities that declare type variables.
 *
 * <p>
 *  所有声明类型变量的实体的通用接口。
 * 
 * 
 * @since 1.5
 */
public interface GenericDeclaration extends AnnotatedElement {
    /**
     * Returns an array of {@code TypeVariable} objects that
     * represent the type variables declared by the generic
     * declaration represented by this {@code GenericDeclaration}
     * object, in declaration order.  Returns an array of length 0 if
     * the underlying generic declaration declares no type variables.
     *
     * <p>
     *  返回一个{@code TypeVariable}对象数组,它们以声明顺序表示由此{@code GenericDeclaration}对象表示的通用声明声明的类型变量。
     * 如果底层通用声明没有声明类型变量,则返回长度为0的数组。
     * 
     * @return an array of {@code TypeVariable} objects that represent
     *     the type variables declared by this generic declaration
     * @throws GenericSignatureFormatError if the generic
     *     signature of this generic declaration does not conform to
     *     the format specified in
     *     <cite>The Java&trade; Virtual Machine Specification</cite>
     */
    public TypeVariable<?>[] getTypeParameters();
}
