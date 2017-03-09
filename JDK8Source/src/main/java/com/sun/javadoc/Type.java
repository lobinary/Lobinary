/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * Represents a type.  A type can be a class or interface, an
 * invocation (like {@code List<String>}) of a generic class or interface,
 * a type variable, a wildcard type ("<code>?</code>"),
 * or a primitive data type (like <code>char</code>).
 *
 * <p>
 *  表示类型。
 * 类型可以是类或接口,通用类或接口的调用(如{@code List <String>}),类型变量,通配符类型("<code>?</code>")一个原始数据类型(如<code> char </code>
 * )。
 *  表示类型。
 * 
 * 
 * @since 1.2
 * @author Kaiyang Liu (original)
 * @author Robert Field (rewrite)
 * @author Scott Seligman (generics)
 */
public interface Type {

    /**
     * Return unqualified name of type excluding any dimension information.
     * <p>
     * For example, a two dimensional array of String returns
     * "<code>String</code>".
     * <p>
     *  返回不包含任何尺寸信息的类型的非限定名称。
     * <p>
     *  例如,一个二维数组的String返回"<code> String </code>"。
     * 
     */
    String typeName();

    /**
     * Return qualified name of type excluding any dimension information.
     *<p>
     * For example, a two dimensional array of String
     * returns "<code>java.lang.String</code>".
     * <p>
     *  返回不包括任何尺寸信息的类型的限定名称。
     * p>
     *  例如,一个二维数组的String返回"<code> java.lang.String </code>"。
     * 
     */
    String qualifiedTypeName();

    /**
     * Return the simple name of this type excluding any dimension information.
     * This is the unqualified name of the type, except that for nested types
     * only the identifier of the innermost type is included.
     * <p>
     * For example, the class {@code Outer.Inner} returns
     * "<code>Inner</code>".
     *
     * <p>
     *  返回此类型的简单名称,不包括任何尺寸信息。这是类型的非限定名称,除了对于嵌套类型,只包括最内层类型的标识符。
     * <p>
     *  例如,类{@code Outer.Inner}返回"<code> Inner </code>"。
     * 
     * 
     * @since 1.5
     */
    String simpleTypeName();

    /**
     * Return the type's dimension information, as a string.
     * <p>
     * For example, a two dimensional array of String returns
     * "<code>[][]</code>".
     * <p>
     *  返回类型的维度信息,作为字符串。
     * <p>
     *  例如,一个二维数组的String返回"<code> [] [] </code>"。
     * 
     */
    String dimension();

    /**
     * Return a string representation of the type.
     * This includes any dimension information and type arguments.
     * <p>
     * For example, a two dimensional array of String may return
     * "<code>java.lang.String[][]</code>",
     * and the parameterized type {@code List<Integer>} may return
     * "{@code java.util.List<java.lang.Integer>}".
     *
     * <p>
     *  返回类型的字符串表示形式。这包括任何维度信息和类型参数。
     * <p>
     *  例如,String的二维数组可能返回"<code> java.lang.String [] [] </code>",参数化类型{@code List <Integer>}可能返回"{@code java。
     *  util.List <java.lang.Integer>}"。
     * 
     * 
     * @return a string representation of the type.
     */
    String toString();

    /**
     * Return true if this type represents a primitive type.
     *
     * <p>
     *  如果此类型表示基本类型,则返回true。
     * 
     * 
     * @return true if this type represents a primitive type.
     * @since 1.5
     */
    boolean isPrimitive();

    /**
     * Return this type as a <code>ClassDoc</code> if it represents a class
     * or interface.  Array dimensions are ignored.
     * If this type is a <code>ParameterizedType</code>,
     * <code>TypeVariable</code>, or <code>WildcardType</code>, return
     * the <code>ClassDoc</code> of the type's erasure.  If this is an
     * <code>AnnotationTypeDoc</code>, return this as a <code>ClassDoc</code>
     * (but see {@link #asAnnotationTypeDoc()}).
     * If this is a primitive type, return null.
     *
     * <p>
     * 如果它代表一个类或接口,则将此类型返回为<code> ClassDoc </code>。将忽略数组维度。
     * 如果此类型是<code> ParameterizedType </code>,<code> TypeVariable </code>或<code> WildcardType </code>,请返回类型擦
     * 除的<code> ClassDoc </code>。
     * 如果它代表一个类或接口,则将此类型返回为<code> ClassDoc </code>。将忽略数组维度。
     * 如果这是一个<code> AnnotationTypeDoc </code>,将它作为<code> ClassDoc </code>返回(但参见{@link #asAnnotationTypeDoc()}
     * )。
     * 如果它代表一个类或接口,则将此类型返回为<code> ClassDoc </code>。将忽略数组维度。如果这是原始类型,则返回null。
     * 
     * 
     * @return the <code>ClassDoc</code> of this type,
     *         or null if it is a primitive type.
     */
    ClassDoc asClassDoc();

    /**
     * Return this type as a <code>ParameterizedType</code> if it represents
     * an invocation of a generic class or interface.  Array dimensions
     * are ignored.
     *
     * <p>
     *  如果它代表对通用类或接口的调用,则将此类型返回为<code> ParameterizedType </code>。将忽略数组维度。
     * 
     * 
     * @return a <code>ParameterizedType</code> if the type is an
     *         invocation of a generic type, or null if it is not.
     * @since 1.5
     */
    ParameterizedType asParameterizedType();

    /**
     * Return this type as a <code>TypeVariable</code> if it represents
     * a type variable.  Array dimensions are ignored.
     *
     * <p>
     *  如果它代表一个类型变量,则将此类型返回为<code> TypeVariable </code>。将忽略数组维度。
     * 
     * 
     * @return a <code>TypeVariable</code> if the type is a type variable,
     *         or null if it is not.
     * @since 1.5
     */
    TypeVariable asTypeVariable();

    /**
     * Return this type as a <code>WildcardType</code> if it represents
     * a wildcard type.
     *
     * <p>
     *  如果它表示通配符类型,则将此类型返回为<code> WildcardType </code>。
     * 
     * 
     * @return a <code>WildcardType</code> if the type is a wildcard type,
     *         or null if it is not.
     * @since 1.5
     */
    WildcardType asWildcardType();

    /**
     * Returns this type as a <code>AnnotatedType</code> if it represents
     * an annotated type.
     *
     * <p>
     *  如果它表示注释类型,则将此类型返回为<code> AnnotatedType </code>。
     * 
     * 
     * @return a <code>AnnotatedType</code> if the type if an annotated type,
     *         or null if it is not
     * @since 1.8
     */
    AnnotatedType asAnnotatedType();

    /**
     * Return this type as an <code>AnnotationTypeDoc</code> if it represents
     * an annotation type.  Array dimensions are ignored.
     *
     * <p>
     *  如果它代表注释类型,则将此类型返回为<code> AnnotationTypeDoc </code>。将忽略数组维度。
     * 
     * 
     * @return an <code>AnnotationTypeDoc</code> if the type is an annotation
     *         type, or null if it is not.
     * @since 1.5
     */
    AnnotationTypeDoc asAnnotationTypeDoc();

    /**
     * If this type is an array type, return the element type of the
     * array. Otherwise, return null.
     *
     * <p>
     *  如果此类型是数组类型,则返回数组的元素类型。否则,返回null。
     * 
     * @return a <code>Type</code> representing the element type or null.
     * @since 1.8
     */
    Type getElementType();
}
