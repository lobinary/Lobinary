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

package java.lang.annotation;

/**
 * The constants of this enumerated type provide a simple classification of the
 * syntactic locations where annotations may appear in a Java program. These
 * constants are used in {@link Target java.lang.annotation.Target}
 * meta-annotations to specify where it is legal to write annotations of a
 * given type.
 *
 * <p>The syntactic locations where annotations may appear are split into
 * <em>declaration contexts</em> , where annotations apply to declarations, and
 * <em>type contexts</em> , where annotations apply to types used in
 * declarations and expressions.
 *
 * <p>The constants {@link #ANNOTATION_TYPE} , {@link #CONSTRUCTOR} , {@link
 * #FIELD} , {@link #LOCAL_VARIABLE} , {@link #METHOD} , {@link #PACKAGE} ,
 * {@link #PARAMETER} , {@link #TYPE} , and {@link #TYPE_PARAMETER} correspond
 * to the declaration contexts in JLS 9.6.4.1.
 *
 * <p>For example, an annotation whose type is meta-annotated with
 * {@code @Target(ElementType.FIELD)} may only be written as a modifier for a
 * field declaration.
 *
 * <p>The constant {@link #TYPE_USE} corresponds to the 15 type contexts in JLS
 * 4.11, as well as to two declaration contexts: type declarations (including
 * annotation type declarations) and type parameter declarations.
 *
 * <p>For example, an annotation whose type is meta-annotated with
 * {@code @Target(ElementType.TYPE_USE)} may be written on the type of a field
 * (or within the type of the field, if it is a nested, parameterized, or array
 * type), and may also appear as a modifier for, say, a class declaration.
 *
 * <p>The {@code TYPE_USE} constant includes type declarations and type
 * parameter declarations as a convenience for designers of type checkers which
 * give semantics to annotation types. For example, if the annotation type
 * {@code NonNull} is meta-annotated with
 * {@code @Target(ElementType.TYPE_USE)}, then {@code @NonNull}
 * {@code class C {...}} could be treated by a type checker as indicating that
 * all variables of class {@code C} are non-null, while still allowing
 * variables of other classes to be non-null or not non-null based on whether
 * {@code @NonNull} appears at the variable's declaration.
 *
 * <p>
 *  这个枚举类型的常量提供了注释可能出现在Java程序中的语法位置的简单分类。
 * 这些常量用于{@link Target java.lang.annotation.Target}元注释中,以指定写入给定类型的注释的合法位置。
 * 
 *  <p>注释可能出现的语法位置分为<em>声明上下文</em>和批注应用于声明的类型,<em>类型上下文</em> 。
 * 
 *  <p>常数{@link #ANNOTATION_TYPE},{@link #CONSTRUCTOR},{@link #FIELD},{@link #LOCAL_VARIABLE},{@link #METHOD}
 * ,{@link #PACKAGE},{@link #PARAMETER},{@link #TYPE}和{@link #TYPE_PARAMETER}对应于JLS 9.6.4.1中的声明上下文。
 * 
 *  <p>例如,类型为元注释为{@code @Target(ElementType.FIELD)}的注释只能写为字段声明的修饰符。
 * 
 *  <p>常量{@link #TYPE_USE}对应于JLS 4.11中的15个类型上下文,以及两个声明上下文：类型声明(包括注释类型声明)和类型参数声明。
 * 
 * <p>例如,类型为元注释为{@code @Target(ElementType.TYPE_USE)}的注释可以写在字段的类型上(或在字段类型内,如果它是嵌套的,参数化或数组类型),并且也可以出现作为例如
 * 
 * @author  Joshua Bloch
 * @since 1.5
 * @jls 9.6.4.1 @Target
 * @jls 4.1 The Kinds of Types and Values
 */
public enum ElementType {
    /** Class, interface (including annotation type), or enum declaration */
    TYPE,

    /** Field declaration (includes enum constants) */
    FIELD,

    /** Method declaration */
    METHOD,

    /** Formal parameter declaration */
    PARAMETER,

    /** Constructor declaration */
    CONSTRUCTOR,

    /** Local variable declaration */
    LOCAL_VARIABLE,

    /** Annotation type declaration */
    ANNOTATION_TYPE,

    /** Package declaration */
    PACKAGE,

    /**
     * Type parameter declaration
     *
     * <p>
     * 类声明的修饰符。
     * 
     *  <p> {@code TYPE_USE}常量包括类型声明和类型参数声明,以方便对类型检查器的设计者提供注释类型的语义。
     * 例如,如果注释类型{@code NonNull}使用{@code @Target(ElementType.TYPE_USE)}进行元注释,则可以处理{@code @NonNull} {@code class C {...}
     * }通过类型检查器来指示类{@code C}的所有变量都是非空的,同时仍然允许其他类的变量是非空的或不是非空的,这取决于{@code @NonNull}是否出现在变量的声明。
     *  <p> {@code TYPE_USE}常量包括类型声明和类型参数声明,以方便对类型检查器的设计者提供注释类型的语义。
     * 
     * 
     * @since 1.8
     */
    TYPE_PARAMETER,

    /**
     * Use of a type
     *
     * <p>
     * 
     * @since 1.8
     */
    TYPE_USE
}
