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

package com.sun.javadoc;


/**
 * Represents a type variable.
 * For example, the generic interface {@code List<E>} has a single
 * type variable {@code E}.
 * A type variable may have explicit bounds, as in
 * {@code C<R extends Remote>}.
 *
 * <p>
 *  表示一个类型变量。例如,通用接口{@code List <E>}有一个类型变量{@code E}。类型变量可以具有显式边界,如{@code C <R extends Remote>}中所示。
 * 
 * 
 * @author Scott Seligman
 * @since 1.5
 */
public interface TypeVariable extends Type {

    /**
     * Return the bounds of this type variable.
     * These are the types given by the <i>extends</i> clause.
     * Return an empty array if there are no explicit bounds.
     *
     * <p>
     *  返回此类型变量的边界。这些是由<i> extend </i>子句给出的类型。如果没有显式边界,则返回空数组。
     * 
     * 
     * @return the bounds of this type variable.
     */
    Type[] bounds();

    /**
     * Return the class, interface, method, or constructor within
     * which this type variable is declared.
     *
     * <p>
     *  返回声明此类型变量的类,接口,方法或构造函数。
     * 
     * 
     * @return the class, interface, method, or constructor within
     *         which this type variable is declared.
     */
    ProgramElementDoc owner();

    /**
     * Get the annotations of this program element.
     * Return an empty array if there are none.
     * <p>
     *  获取此程序元素的注释。如果没有,返回一个空数组。
     */
    public AnnotationDesc[] annotations();

}
