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
 * Indicates the contexts in which an annotation type is applicable. The
 * declaration contexts and type contexts in which an annotation type may be
 * applicable are specified in JLS 9.6.4.1, and denoted in source code by enum
 * constants of {@link ElementType java.lang.annotation.ElementType}.
 *
 * <p>If an {@code @Target} meta-annotation is not present on an annotation type
 * {@code T} , then an annotation of type {@code T} may be written as a
 * modifier for any declaration except a type parameter declaration.
 *
 * <p>If an {@code @Target} meta-annotation is present, the compiler will enforce
 * the usage restrictions indicated by {@code ElementType}
 * enum constants, in line with JLS 9.7.4.
 *
 * <p>For example, this {@code @Target} meta-annotation indicates that the
 * declared type is itself a meta-annotation type.  It can only be used on
 * annotation type declarations:
 * <pre>
 *    &#064;Target(ElementType.ANNOTATION_TYPE)
 *    public &#064;interface MetaAnnotationType {
 *        ...
 *    }
 * </pre>
 *
 * <p>This {@code @Target} meta-annotation indicates that the declared type is
 * intended solely for use as a member type in complex annotation type
 * declarations.  It cannot be used to annotate anything directly:
 * <pre>
 *    &#064;Target({})
 *    public &#064;interface MemberType {
 *        ...
 *    }
 * </pre>
 *
 * <p>It is a compile-time error for a single {@code ElementType} constant to
 * appear more than once in an {@code @Target} annotation.  For example, the
 * following {@code @Target} meta-annotation is illegal:
 * <pre>
 *    &#064;Target({ElementType.FIELD, ElementType.METHOD, ElementType.FIELD})
 *    public &#064;interface Bogus {
 *        ...
 *    }
 * </pre>
 *
 * <p>
 *  指示注释类型适用的上下文。
 * 可以应用注释类型的声明上下文和类型上下文在JLS 9.6.4.1中指定,并且在源代码中由{@link ElementType java.lang.annotation.ElementType}的枚举常量
 * 表示。
 *  指示注释类型适用的上下文。
 * 
 *  <p>如果注释类型{@code T}上不存在{@code @Target}元注释,那么类型为{@code T}的注释可以写为除类型参数之外的任何声明的修饰符宣言。
 * 
 *  <p>如果存在{@code @Target}元注释,编译器将根据JLS 9.7.4强制执行由{@code ElementType}枚举常量指示的使用限制。
 * 
 *  <p>例如,此{@code @Target}元注释表示声明的类型本身是元注释类型。它只能用于注释类型声明：
 * <pre>
 *  @Target(ElementType.ANNOTATION_TYPE)public @interface MetaAnnotationType {...}
 * </pre>
 * 
 * 
 * @since 1.5
 * @jls 9.6.4.1 @Target
 * @jls 9.7.4 Where Annotations May Appear
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Target {
    /**
     * Returns an array of the kinds of elements an annotation type
     * can be applied to.
     * <p>
     *  <p>此{@code @Target}元注释表示声明的类型仅用于在复杂注释类型声明中用作成员类型。它不能用于直接注释任何东西：
     * <pre>
     *  @Target({})public @interface MemberType {...}
     * </pre>
     * 
     * <p>这是一个编译时错误,一个{@code ElementType}常数在{@code @Target}注释中多次出现。例如,以下{@code @Target}元注释是非法的：
     * <pre>
     * 
     * @return an array of the kinds of elements an annotation type
     * can be applied to
     */
    ElementType[] value();
}
