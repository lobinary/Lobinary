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

package java.lang;

import java.lang.annotation.*;

/**
 * An informative annotation type used to indicate that an interface
 * type declaration is intended to be a <i>functional interface</i> as
 * defined by the Java Language Specification.
 *
 * Conceptually, a functional interface has exactly one abstract
 * method.  Since {@linkplain java.lang.reflect.Method#isDefault()
 * default methods} have an implementation, they are not abstract.  If
 * an interface declares an abstract method overriding one of the
 * public methods of {@code java.lang.Object}, that also does
 * <em>not</em> count toward the interface's abstract method count
 * since any implementation of the interface will have an
 * implementation from {@code java.lang.Object} or elsewhere.
 *
 * <p>Note that instances of functional interfaces can be created with
 * lambda expressions, method references, or constructor references.
 *
 * <p>If a type is annotated with this annotation type, compilers are
 * required to generate an error message unless:
 *
 * <ul>
 * <li> The type is an interface type and not an annotation type, enum, or class.
 * <li> The annotated type satisfies the requirements of a functional interface.
 * </ul>
 *
 * <p>However, the compiler will treat any interface meeting the
 * definition of a functional interface as a functional interface
 * regardless of whether or not a {@code FunctionalInterface}
 * annotation is present on the interface declaration.
 *
 * @jls 4.3.2. The Class Object
 * @jls 9.8 Functional Interfaces
 * @jls 9.4.3 Interface Method Body
 * <p>
 *  用于指示接口类型声明旨在作为由Java语言规范定义的功能接口的信息性注释类型。
 * 
 *  从概念上讲,功能接口只有一种抽象方法。由于{@linkplain java.lang.reflect.Method#isDefault()默认方法}有一个实现,它们不是抽象的。
 * 如果一个接口声明一个抽象方法覆盖了{@code java.lang.Object}的一个公共方法,它也不会计入接口的抽象方法计数,因为接口的任何实现都将来自{@code java.lang.Object}
 * 或其他地方的实现。
 *  从概念上讲,功能接口只有一种抽象方法。由于{@linkplain java.lang.reflect.Method#isDefault()默认方法}有一个实现,它们不是抽象的。
 * 
 *  <p>请注意,可以使用lambda表达式,方法引用或构造函数引用创建函数接口的实例。
 * 
 *  <p>如果类型使用此注释类型注释,则编译器需要生成错误消息,除非：
 * 
 * @since 1.8
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FunctionalInterface {}
