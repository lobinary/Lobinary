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

package javax.lang.model.element;

import java.util.List;
import javax.lang.model.type.*;

/**
 * Represents a method, constructor, or initializer (static or
 * instance) of a class or interface, including annotation type
 * elements.
 *
 * <p>
 *  表示类或接口的方法,构造函数或初始化程序(静态或实例),包括注释类型元素。
 * 
 * 
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @see ExecutableType
 * @since 1.6
 */
public interface ExecutableElement extends Element, Parameterizable {
    /**
     * Returns the formal type parameters of this executable
     * in declaration order.
     *
     * <p>
     *  以声明顺序返回此可执行文件的形式类型参数。
     * 
     * 
     * @return the formal type parameters, or an empty list
     * if there are none
     */
    List<? extends TypeParameterElement> getTypeParameters();

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
     * Returns the formal parameters of this executable.
     * They are returned in declaration order.
     *
     * <p>
     *  返回此可执行文件的形式参数。它们以声明顺序返回。
     * 
     * 
     * @return the formal parameters,
     * or an empty list if there are none
     */
    List<? extends VariableElement> getParameters();

    /**
     * Returns the receiver type of this executable,
     * or {@link javax.lang.model.type.NoType NoType} with
     * kind {@link javax.lang.model.type.TypeKind#NONE NONE}
     * if the executable has no receiver type.
     *
     * An executable which is an instance method, or a constructor of an
     * inner class, has a receiver type derived from the {@linkplain
     * #getEnclosingElement declaring type}.
     *
     * An executable which is a static method, or a constructor of a
     * non-inner class, or an initializer (static or instance), has no
     * receiver type.
     *
     * <p>
     *  如果可执行文件没有接收器类型,则返回此类可执行文件的接收器类型,或{@link javax.lang.model.type.NoType NoType}类型为{@link javax.lang.model.type.TypeKind#NONE NONE}
     * 。
     * 
     *  作为实例方法的可执行文件或内部类的构造函数具有从{@linkplain #getEnclosingElement declaring type}派生的接收器类型。
     * 
     *  作为静态方法的可执行文件,或非内部类的构造函数或初始化器(静态或实例),没有接收器类型。
     * 
     * 
     * @return the receiver type of this executable
     * @since 1.8
     */
    TypeMirror getReceiverType();

    /**
     * Returns {@code true} if this method or constructor accepts a variable
     * number of arguments and returns {@code false} otherwise.
     *
     * <p>
     *  如果此方法或构造函数接受可变数量的参数并返回{@code false},则返回{@code true}。
     * 
     * 
     * @return {@code true} if this method or constructor accepts a variable
     * number of arguments and {@code false} otherwise
     */
    boolean isVarArgs();

    /**
     * Returns {@code true} if this method is a default method and
     * returns {@code false} otherwise.
     *
     * <p>
     *  如果此方法是默认方法,则返回{@code true},否则返回{@code false}。
     * 
     * 
     * @return {@code true} if this method is a default method and
     * {@code false} otherwise
     * @since 1.8
     */
    boolean isDefault();

    /**
     * Returns the exceptions and other throwables listed in this
     * method or constructor's {@code throws} clause in declaration
     * order.
     *
     * <p>
     *  返回此方法中列出的异常和其他throwables或声明顺序中的构造函数的{@code throws}子句。
     * 
     * 
     * @return the exceptions and other throwables listed in the
     * {@code throws} clause, or an empty list if there are none
     */
    List<? extends TypeMirror> getThrownTypes();

    /**
     * Returns the default value if this executable is an annotation
     * type element.  Returns {@code null} if this method is not an
     * annotation type element, or if it is an annotation type element
     * with no default value.
     *
     * <p>
     * 如果此可执行文件是注释类型元素,则返回默认值。如果此方法不是注记类型元素,或者如果它是没有默认值的注记类型元素,则返回{@code null}。
     * 
     * 
     * @return the default value, or {@code null} if none
     */
    AnnotationValue getDefaultValue();

    /**
     * Returns the simple name of a constructor, method, or
     * initializer.  For a constructor, the name {@code "<init>"} is
     * returned, for a static initializer, the name {@code "<clinit>"}
     * is returned, and for an anonymous class or instance
     * initializer, an empty name is returned.
     *
     * <p>
     *  返回构造函数,方法或初始化程序的简单名称。对于构造函数,返回名称{@code"<init>"},对于静态初始化器,将返回名称{@code"<clinit>"},对于匿名类或实例初始化器,回。
     * 
     * @return the simple name of a constructor, method, or
     * initializer
     */
    @Override
    Name getSimpleName();
}
