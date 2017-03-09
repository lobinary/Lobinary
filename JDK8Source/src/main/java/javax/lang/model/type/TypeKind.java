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


/**
 * The kind of a type mirror.
 *
 * <p>Note that it is possible additional type kinds will be added to
 * accommodate new, currently unknown, language structures added to
 * future versions of the Java&trade; programming language.
 *
 * <p>
 *  类型镜子的种类。
 * 
 *  <p>请注意,可能会添加其他类型的类型,以适应添加到未来版本的Java和贸易中的新的,当前未知的语言结构;编程语言。
 * 
 * 
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @see TypeMirror
 * @since 1.6
 */
public enum TypeKind {
    /**
     * The primitive type {@code boolean}.
     * <p>
     *  原始类型{@code boolean}。
     * 
     */
    BOOLEAN,

    /**
     * The primitive type {@code byte}.
     * <p>
     *  原始类型{@code byte}。
     * 
     */
    BYTE,

    /**
     * The primitive type {@code short}.
     * <p>
     *  基本类型{@code short}。
     * 
     */
    SHORT,

    /**
     * The primitive type {@code int}.
     * <p>
     *  原始类型{@code int}。
     * 
     */
    INT,

    /**
     * The primitive type {@code long}.
     * <p>
     *  基本类型{@code long}。
     * 
     */
    LONG,

    /**
     * The primitive type {@code char}.
     * <p>
     *  原始类型{@code char}。
     * 
     */
    CHAR,

    /**
     * The primitive type {@code float}.
     * <p>
     *  原始类型{@code float}。
     * 
     */
    FLOAT,

    /**
     * The primitive type {@code double}.
     * <p>
     *  原始类型{@code double}。
     * 
     */
    DOUBLE,

    /**
     * The pseudo-type corresponding to the keyword {@code void}.
     * <p>
     *  对应于关键字{@code void}的伪类型。
     * 
     * 
     * @see NoType
     */
    VOID,

    /**
     * A pseudo-type used where no actual type is appropriate.
     * <p>
     *  在没有实际类型是合适的情况下使用的伪类型。
     * 
     * 
     * @see NoType
     */
    NONE,

    /**
     * The null type.
     * <p>
     *  null类型。
     * 
     */
    NULL,

    /**
     * An array type.
     * <p>
     *  数组类型。
     * 
     */
    ARRAY,

    /**
     * A class or interface type.
     * <p>
     *  类或接口类型。
     * 
     */
    DECLARED,

    /**
     * A class or interface type that could not be resolved.
     * <p>
     *  无法解析的类或接口类型。
     * 
     */
    ERROR,

    /**
     * A type variable.
     * <p>
     *  一个类型变量。
     * 
     */
    TYPEVAR,

    /**
     * A wildcard type argument.
     * <p>
     *  通配符类型参数。
     * 
     */
    WILDCARD,

    /**
     * A pseudo-type corresponding to a package element.
     * <p>
     *  对应于包元素的伪类型。
     * 
     * 
     * @see NoType
     */
    PACKAGE,

    /**
     * A method, constructor, or initializer.
     * <p>
     *  方法,构造函数或初始化函数。
     * 
     */
    EXECUTABLE,

    /**
     * An implementation-reserved type.
     * This is not the type you are looking for.
     * <p>
     *  实现保留类型。这不是你要找的类型。
     * 
     */
    OTHER,

    /**
      * A union type.
      *
      * <p>
      *  联合类型。
      * 
      * 
      * @since 1.7
      */
    UNION,

    /**
      * An intersection type.
      *
      * <p>
      *  交叉路口类型。
      * 
      * 
      * @since 1.8
      */
    INTERSECTION;

    /**
     * Returns {@code true} if this kind corresponds to a primitive
     * type and {@code false} otherwise.
     * <p>
     *  如果此类对应于原始类型,则返回{@code true},否则返回{@code false}。
     * 
     * @return {@code true} if this kind corresponds to a primitive type
     */
    public boolean isPrimitive() {
        switch(this) {
        case BOOLEAN:
        case BYTE:
        case SHORT:
        case INT:
        case LONG:
        case CHAR:
        case FLOAT:
        case DOUBLE:
            return true;

        default:
            return false;
        }
    }
}
