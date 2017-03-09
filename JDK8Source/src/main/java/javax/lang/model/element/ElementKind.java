/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2010, Oracle and/or its affiliates. All rights reserved.
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

/**
 * The {@code kind} of an element.
 *
 * <p>Note that it is possible additional element kinds will be added
 * to accommodate new, currently unknown, language structures added to
 * future versions of the Java&trade; programming language.
 *
 * <p>
 *  元素的{@code kind}。
 * 
 *  <p>请注意,可能会添加其他元素种类,以适应添加到未来版本的Java和贸易中的新的,当前未知的语言结构;编程语言。
 * 
 * 
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @see Element
 * @since 1.6
 */
public enum ElementKind {

    /** A package. */
    PACKAGE,

    // Declared types
    /** An enum type. */
    ENUM,
    /** A class not described by a more specific kind (like {@code ENUM}). */
    CLASS,
    /** An annotation type. */
    ANNOTATION_TYPE,
    /**
     * An interface not described by a more specific kind (like
     * {@code ANNOTATION_TYPE}).
     * <p>
     *  未由更具体类型(如{@code ANNOTATION_TYPE})描述的接口。
     * 
     */
    INTERFACE,

    // Variables
    /** An enum constant. */
    ENUM_CONSTANT,
    /**
     * A field not described by a more specific kind (like
     * {@code ENUM_CONSTANT}).
     * <p>
     *  不是由特定类型(例如{@code ENUM_CONSTANT})描述的字段。
     * 
     */
    FIELD,
    /** A parameter of a method or constructor. */
    PARAMETER,
    /** A local variable. */
    LOCAL_VARIABLE,
    /** A parameter of an exception handler. */
    EXCEPTION_PARAMETER,

    // Executables
    /** A method. */
    METHOD,
    /** A constructor. */
    CONSTRUCTOR,
    /** A static initializer. */
    STATIC_INIT,
    /** An instance initializer. */
    INSTANCE_INIT,

    /** A type parameter. */
    TYPE_PARAMETER,

    /**
     * An implementation-reserved element.  This is not the element
     * you are looking for.
     * <p>
     *  实现保留元素。这不是你要找的元素。
     * 
     */
    OTHER,

    /**
     * A resource variable.
     * <p>
     *  资源变量。
     * 
     * 
     * @since 1.7
     */
    RESOURCE_VARIABLE;


    /**
     * Returns {@code true} if this is a kind of class:
     * either {@code CLASS} or {@code ENUM}.
     *
     * <p>
     *  如果这是一种类,则返回{@code true}：{@code CLASS}或{@code ENUM}。
     * 
     * 
     * @return {@code true} if this is a kind of class
     */
    public boolean isClass() {
        return this == CLASS || this == ENUM;
    }

    /**
     * Returns {@code true} if this is a kind of interface:
     * either {@code INTERFACE} or {@code ANNOTATION_TYPE}.
     *
     * <p>
     *  如果这是一种界面,则返回{@code true}：{@code INTERFACE}或{@code ANNOTATION_TYPE}。
     * 
     * 
     * @return {@code true} if this is a kind of interface
     */
    public boolean isInterface() {
        return this == INTERFACE || this == ANNOTATION_TYPE;
    }

    /**
     * Returns {@code true} if this is a kind of field:
     * either {@code FIELD} or {@code ENUM_CONSTANT}.
     *
     * <p>
     *  如果这是一种字段,则返回{@code true}：{@code FIELD}或{@code ENUM_CONSTANT}。
     * 
     * @return {@code true} if this is a kind of field
     */
    public boolean isField() {
        return this == FIELD || this == ENUM_CONSTANT;
    }
}
