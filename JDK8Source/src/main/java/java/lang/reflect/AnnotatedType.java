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
 * {@code AnnotatedType} represents the potentially annotated use of a type in
 * the program currently running in this VM. The use may be of any type in the
 * Java programming language, including an array type, a parameterized type, a
 * type variable, or a wildcard type.
 *
 * <p>
 *  {@code AnnotatedType}表示当前在此VM中运行的程序中类型的潜在注释使用。该使用可以是Java编程语言中的任何类型,包括数组类型,参数化类型,类型变量或通配符类型。
 * 
 * 
 * @since 1.8
 */
public interface AnnotatedType extends AnnotatedElement {

    /**
     * Returns the underlying type that this annotated type represents.
     *
     * <p>
     *  返回此带注释类型表示的底层类型。
     * 
     * @return the type this annotated type represents
     */
    public Type getType();
}
