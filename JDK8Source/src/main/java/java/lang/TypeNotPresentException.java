/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2008, Oracle and/or its affiliates. All rights reserved.
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

/**
 * Thrown when an application tries to access a type using a string
 * representing the type's name, but no definition for the type with
 * the specified name can be found.   This exception differs from
 * {@link ClassNotFoundException} in that <tt>ClassNotFoundException</tt> is a
 * checked exception, whereas this exception is unchecked.
 *
 * <p>Note that this exception may be used when undefined type variables
 * are accessed as well as when types (e.g., classes, interfaces or
 * annotation types) are loaded.
 * In particular, this exception can be thrown by the {@linkplain
 * java.lang.reflect.AnnotatedElement API used to read annotations
 * reflectively}.
 *
 * <p>
 *  当应用程序尝试使用表示类型名称的字符串访问类型时抛出,但不能找到具有指定名称的类型的定义。
 * 此异常与{@link ClassNotFoundException}不同,因为<tt> ClassNotFoundException </tt>是一个已检查的异常,而此异常未选中。
 * 
 *  <p>请注意,当访问未定义的类型变量以及加载类型(例如类,接口或注释类型)时,可以使用此异常。
 * 特别是,这个异常可以由{@linkplain java.lang.reflect.AnnotatedElement API用来反映读取注释}抛出。
 * 
 * 
 * @author  Josh Bloch
 * @see     java.lang.reflect.AnnotatedElement
 * @since 1.5
 */
public class TypeNotPresentException extends RuntimeException {
    private static final long serialVersionUID = -5101214195716534496L;

    private String typeName;

    /**
     * Constructs a <tt>TypeNotPresentException</tt> for the named type
     * with the specified cause.
     *
     * <p>
     * 
     * @param typeName the fully qualified name of the unavailable type
     * @param cause the exception that was thrown when the system attempted to
     *    load the named type, or <tt>null</tt> if unavailable or inapplicable
     */
    public TypeNotPresentException(String typeName, Throwable cause) {
        super("Type " + typeName + " not present", cause);
        this.typeName = typeName;
    }

    /**
     * Returns the fully qualified name of the unavailable type.
     *
     * <p>
     *  为具有指定原因的命名类型构造<tt> TypeNotPresentException </tt>。
     * 
     * 
     * @return the fully qualified name of the unavailable type
     */
    public String typeName() { return typeName;}
}
