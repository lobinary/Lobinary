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
 * Represents an invocation of a generic class or interface.  For example,
 * given the generic interface {@code List<E>}, possible invocations
 * include:
 * <pre>
 *      {@code List<String>}
 *      {@code List<T extends Number>}
 *      {@code List<?>}
 * </pre>
 * A generic inner class {@code Outer<T>.Inner<S>} might be invoked as:
 * <pre>
 *      {@code Outer<Number>.Inner<String>}
 * </pre>
 *
 * <p>
 *  表示对通用类或接口的调用。例如,给定通用接口{@code List <E>},可能的调用包括：
 * <pre>
 *  {@code List <String>} {@code List <T extends Number>} {@code List <?>}
 * </pre>
 *  通用内部类{@code Outer <T> .Inner <S>}可能被调用：
 * <pre>
 *  {@code Outer <Number> .Inner <String>}
 * </pre>
 * 
 * 
 * @author Scott Seligman
 * @since 1.5
 */
public interface ParameterizedType extends Type {

    /**
     * Return the generic class or interface that declared this type.
     *
     * <p>
     *  返回声明此类型的泛型类或接口。
     * 
     * 
     * @return the generic class or interface that declared this type.
     */
    ClassDoc asClassDoc();

    /**
     * Return the actual type arguments of this type.
     * For a generic type that is nested within some other generic type
     * (such as {@code Outer<T>.Inner<S>}),
     * only the type arguments of the innermost type are included.
     *
     * <p>
     *  返回此类型的实际类型参数。对于嵌套在一些其他泛型类型(例如{@code Outer <T> .Inner <S>})中的泛型类型,只包括最内层类型的类型参数。
     * 
     * 
     * @return the actual type arguments of this type.
     */
    Type[] typeArguments();

    /**
     * Return the class type that is a direct supertype of this one.
     * This is the superclass of this type's declaring class,
     * with type arguments substituted in.
     * Return null if this is an interface type.
     *
     * <p> For example, if this parameterized type is
     * {@code java.util.ArrayList<String>}, the result will be
     * {@code java.util.AbstractList<String>}.
     *
     * <p>
     *  返回类型类型,它是这个类型的直接超类型。这是此类型的声明类的超类,其中带有类型参数。如果这是接口类型,则返回null。
     * 
     *  <p>例如,如果此参数化类型是{@code java.util.ArrayList <String>},结果将是{@code java.util.AbstractList <String>}。
     * 
     * 
     * @return the class type that is a direct supertype of this one.
     */
    Type superclassType();

    /**
     * Return the interface types directly implemented by or extended by this
     * parameterized type.
     * These are the interfaces directly implemented or extended
     * by this type's declaring class or interface,
     * with type arguments substituted in.
     * Return an empty array if there are no interfaces.
     *
     * <p> For example, the interface extended by
     * {@code java.util.Set<String>} is {@code java.util.Collection<String>}.
     *
     * <p>
     *  返回由此参数化类型直接实现或扩展的接口类型。这些是通过类型的声明类或接口直接实现或扩展的接口,其中带有类型参数。如果没有接口,则返回一个空数组。
     * 
     *  <p>例如,由{@code java.util.Set <String>}扩展的接口是{@code java.util.Collection <String>}。
     * 
     * 
     * @return the interface types directly implemented by or extended by this
     * parameterized type.
     */
    Type[] interfaceTypes();

    /**
     * Return the type that contains this type as a member.
     * Return null is this is a top-level type.
     *
     * <p> For example, the containing type of
     * {@code AnInterface.Nested<Number>} is the <code>ClassDoc</code>
     * representing {@code AnInterface}, and the containing type of
     * {@code Outer<String>.Inner<Number>} is the
     * <code>ParameterizedType</code> representing {@code Outer<String>}.
     *
     * <p>
     * 返回包含此类型的类型作为成员。返回null是这是一个顶层类型。
     * 
     *  <p>例如,{@code AnInterface.Nested <Number>}的包含类型是表示{@code AnInterface}的<code> ClassDoc </code>,以及包含类型{@code Outer <String>。
     * 
     * @return the type that contains this type as a member.
     */
    Type containingType();
}
