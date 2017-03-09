/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.lang.model.type;


import java.util.List;

import javax.lang.model.element.ExecutableElement;

/**
 * Represents the type of an executable.  An <i>executable</i>
 * is a method, constructor, or initializer.
 *
 * <p> The executable is
 * represented as when viewed as a method (or constructor or
 * initializer) of some reference type.
 * If that reference type is parameterized, then its actual
 * type arguments are substituted into any types returned by the methods of
 * this interface.
 *
 * <p>
 *  表示可执行文件的类型。 <i>可执行</i>是方法,构造函数或初始化程序。
 * 
 *  <p>当被视为某种引用类型的方法(或构造函数或初始化程序)时,可执行文件被表示。如果该引用类型被参数化,那么它的实际类型参数被替换为由该接口的方法返回的任何类型。
 * 
 * 
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @see ExecutableElement
 * @since 1.6
 */
public interface ExecutableType extends TypeMirror {

    /**
     * Returns the type variables declared by the formal type parameters
     * of this executable.
     *
     * <p>
     *  返回由此可执行文件的形式类型参数声明的类型变量。
     * 
     * 
     * @return the type variables declared by the formal type parameters,
     *          or an empty list if there are none
     */
    List<? extends TypeVariable> getTypeVariables();

    /**
     * Returns the return type of this executable.
     * Returns a {@link NoType} with kind {@link TypeKind#VOID VOID}
     * if this executable is not a method, or is a method that does not
     * return a value.
     *
     * <p>
     *  返回此可执行文件的返回类型。如果此可执行文件不是方法,或者是不返回值的方法,则返回带有类型{@link TypeKind#VOID VOID}的{@link NoType}。
     * 
     * 
     * @return the return type of this executable
     */
    TypeMirror getReturnType();

    /**
     * Returns the types of this executable's formal parameters.
     *
     * <p>
     *  返回此可执行文件的形式参数的类型。
     * 
     * 
     * @return the types of this executable's formal parameters,
     *          or an empty list if there are none
     */
    List<? extends TypeMirror> getParameterTypes();

    /**
     * Returns the receiver type of this executable,
     * or {@link javax.lang.model.type.NoType NoType} with
     * kind {@link javax.lang.model.type.TypeKind#NONE NONE}
     * if the executable has no receiver type.
     *
     * An executable which is an instance method, or a constructor of an
     * inner class, has a receiver type derived from the {@linkplain
     * ExecutableElement#getEnclosingElement declaring type}.
     *
     * An executable which is a static method, or a constructor of a
     * non-inner class, or an initializer (static or instance), has no
     * receiver type.
     *
     * <p>
     *  如果可执行文件没有接收器类型,则返回此类可执行文件的接收器类型,或{@link javax.lang.model.type.NoType NoType}类型为{@link javax.lang.model.type.TypeKind#NONE NONE}
     * 。
     * 
     *  作为实例方法的可执行文件或内部类的构造函数具有从{@linkplain ExecutableElement#getEnclosingElement declaring type}派生的接收器类型。
     * 
     * 
     * @return the receiver type of this executable
     * @since 1.8
     */
    TypeMirror getReceiverType();

    /**
     * Returns the exceptions and other throwables listed in this
     * executable's {@code throws} clause.
     *
     * <p>
     *  作为静态方法的可执行文件,或非内部类的构造函数或初始化器(静态或实例),没有接收器类型。
     * 
     * 
     * @return the exceptions and other throwables listed in this
     *          executable's {@code throws} clause,
     *          or an empty list if there are none.
     */
    List<? extends TypeMirror> getThrownTypes();
}
