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
 * Represents a java package.  Provides access to information
 * about the package, the package's comment and tags, and the
 * classes in the package.
 * <p>
 * Each method whose return type is an array will return an empty
 * array (never null) when there are no objects in the result.
 *
 * <p>
 *  表示一个java包。提供对有关软件包,软件包的注释和标签以及软件包中类的信息的访问。
 * <p>
 *  当结果中没有对象时,每个返回类型为数组的方法将返回一个空数组(从不为空)。
 * 
 * 
 * @since 1.2
 * @author Kaiyang Liu (original)
 * @author Robert Field (rewrite)
 */
public interface PackageDoc extends Doc {

    /**
     * Get all classes and interfaces in the package, filtered to the specified
     * <a href="{@docRoot}/com/sun/javadoc/package-summary.html#included">access
     * modifier option</a>.
     *
     * <p>
     *  获取包中的所有类和接口,并过滤到指定的<a href="{@docRoot}/com/sun/javadoc/package-summary.html#included">访问权限修饰符选项</a>。
     * 
     * 
     * @return       filtered classes and interfaces in this package
     * @param filter Specifying true filters according to the specified access
     *               modifier option.
     *               Specifying false includes all classes and interfaces
     *               regardless of access modifier option.
     * @since 1.4
     */
    ClassDoc[] allClasses(boolean filter);

    /**
     * Get all
     * <a href="{@docRoot}/com/sun/javadoc/package-summary.html#included">included</a>
     * classes and interfaces in the package.  Same as allClasses(true).
     *
     * <p>
     *  获取包中的所有<a href="{@docRoot}/com/sun/javadoc/package-summary.html#included">包含的</a>类和界面。
     * 与allClasses相同(true)。
     * 
     * 
     * @return all included classes and interfaces in this package.
     */
    ClassDoc[] allClasses();

    /**
     * Get included
     * <a href="{@docRoot}/com/sun/javadoc/package-summary.html#class">ordinary</a>
     * classes (that is, exclude exceptions, errors, enums, interfaces, and
     * annotation types)
     * in this package.
     *
     * <p>
     *  包含<a href="{@docRoot}/com/sun/javadoc/package-summary.html#class">普通</a>类(即排除异常,错误,枚举,界面和注释类型)在这个包。
     * 
     * 
     * @return included ordinary classes in this package.
     */
    ClassDoc[] ordinaryClasses();

    /**
     * Get included Exception classes in this package.
     *
     * <p>
     *  获取包含此包中的异常类。
     * 
     * 
     * @return included Exceptions in this package.
     */
    ClassDoc[] exceptions();

    /**
     * Get included Error classes in this package.
     *
     * <p>
     *  获取包含此包中的错误类。
     * 
     * 
     * @return included Errors in this package.
     */
    ClassDoc[] errors();

    /**
     * Get included enum types in this package.
     *
     * <p>
     *  在此软件包中包含枚举类型。
     * 
     * 
     * @return included enum types in this package.
     * @since 1.5
     */
    ClassDoc[] enums();

    /**
     * Get included interfaces in this package, omitting annotation types.
     *
     * <p>
     *  在此包中获取包含的接口,省略注释类型。
     * 
     * 
     * @return included interfaces in this package.
     */
    ClassDoc[] interfaces();

    /**
     * Get included annotation types in this package.
     *
     * <p>
     *  在此包中包含注释类型。
     * 
     * 
     * @return included annotation types in this package.
     * @since 1.5
     */
    AnnotationTypeDoc[] annotationTypes();

    /**
     * Get the annotations of this package.
     * Return an empty array if there are none.
     *
     * <p>
     *  获取此包的注释。如果没有,返回一个空数组。
     * 
     * 
     * @return the annotations of this package.
     * @since 1.5
     */
    AnnotationDesc[] annotations();

    /**
     * Lookup a class or interface within this package.
     *
     * <p>
     *  在此包中查找类或接口。
     * 
     * @return ClassDoc of found class or interface,
     * or null if not found.
     */
    ClassDoc findClass(String className);
}
