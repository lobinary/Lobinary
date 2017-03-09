/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2006, Oracle and/or its affiliates. All rights reserved.
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
 * Represents a java program element: class, interface, field,
 * constructor, or method.
 * This is an abstract class dealing with information common to
 * these elements.
 *
 * <p>
 *  表示一个java程序元素：类,接口,字段,构造函数或方法。这是一个处理这些元素通用的信息的抽象类。
 * 
 * 
 * @see MemberDoc
 * @see ClassDoc
 *
 * @author Robert Field
 */
public interface ProgramElementDoc extends Doc {

    /**
     * Get the containing class or interface of this program element.
     *
     * <p>
     *  获取此程序元素的包含类或接口。
     * 
     * 
     * @return a ClassDoc for this element's containing class or interface.
     * If this is a top-level class or interface, return null.
     */
    ClassDoc containingClass();

    /**
     * Get the package that this program element is contained in.
     *
     * <p>
     *  获取此程序元素包含的包。
     * 
     * 
     * @return a PackageDoc for this element containing package.
     * If in the unnamed package, this PackageDoc will have the
     * name "".
     */
    PackageDoc containingPackage();

    /**
     * Get the fully qualified name of this program element.
     * For example, for the class <code>java.util.Hashtable</code>,
     * return "java.util.Hashtable".
     * <p>
     * For the method <code>bar()</code> in class <code>Foo</code>
     * in the unnamed package, return "Foo.bar".
     *
     * <p>
     *  获取此程序元素的完全限定名称。例如,对于<code> java.util.Hashtable </code>类,返回"java.util.Hashtable"。
     * <p>
     *  对于未命名包中类<code> Foo </code>中的<code> bar()</code>方法,返回"Foo.bar"。
     * 
     * 
     * @return the qualified name of the program element as a String.
     */
    String qualifiedName();

    /**
     * Get the modifier specifier integer.
     *
     * <p>
     *  获取修饰符指定符整数。
     * 
     * 
     * @see java.lang.reflect.Modifier
     */
    int modifierSpecifier();

    /**
     * Get modifiers string.
     * For example, for:
     * <pre>
     *   public abstract int foo() { ... }
     * </pre>
     * return "public abstract".
     * Annotations are not included.
     * <p>
     *  获取修饰符字符串。例如,对于：
     * <pre>
     *  public abstract int foo(){...}
     * </pre>
     *  返回"public abstract"。不包括注释。
     * 
     */
    String modifiers();

    /**
     * Get the annotations of this program element.
     * Return an empty array if there are none.
     *
     * <p>
     *  获取此程序元素的注释。如果没有,返回一个空数组。
     * 
     * 
     * @return the annotations of this program element.
     * @since 1.5
     */
    AnnotationDesc[] annotations();

    /**
     * Return true if this program element is public.
     * <p>
     *  如果此程序元素是public,则返回true。
     * 
     */
    boolean isPublic();

    /**
     * Return true if this program element is protected.
     * <p>
     *  如果此程序元素受保护,则返回true。
     * 
     */
    boolean isProtected();

    /**
     * Return true if this program element is private.
     * <p>
     *  如果此程序元素是私有的,则返回true。
     * 
     */
    boolean isPrivate();

    /**
     * Return true if this program element is package private.
     * <p>
     *  如果此程序元素是包私有的,则返回true。
     * 
     */
    boolean isPackagePrivate();
    /**
     * Return true if this program element is static.
     * <p>
     *  如果此程序元素是静态的,则返回true。
     * 
     */
    boolean isStatic();

    /**
     * Return true if this program element is final.
     * <p>
     *  如果此程序元素是final,则返回true。
     */
    boolean isFinal();
}
