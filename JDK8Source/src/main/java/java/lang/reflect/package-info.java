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

/**
 * Provides classes and interfaces for obtaining reflective
 * information about classes and objects.  Reflection allows
 * programmatic access to information about the fields, methods and
 * constructors of loaded classes, and the use of reflected fields,
 * methods, and constructors to operate on their underlying
 * counterparts, within security restrictions.
 *
 * <p>{@code AccessibleObject} allows suppression of access checks if
 * the necessary {@code ReflectPermission} is available.
 *
 * <p>{@code Array} provides static methods to dynamically create and
 * access arrays.
 *
 * <p>Classes in this package, along with {@code java.lang.Class}
 * accommodate applications such as debuggers, interpreters, object
 * inspectors, class browsers, and services such as Object
 * Serialization and JavaBeans that need access to either the public
 * members of a target object (based on its runtime class) or the
 * members declared by a given class.
 *
 * <p>
 *  提供类和接口,用于获取有关类和对象的反射信息。 Reflection允许在安全限制内编程访问关于加载类的字段,方法和构造函数的信息,以及使用反映的字段,方法和构造函数对其底层对象进行操作。
 * 
 *  <p> {@ code AccessibleObject}允许在必要的{@code ReflectPermission}可用时禁止访问检查。
 * 
 *  <p> {@ code Array}提供了静态方法来动态创建和访问数组。
 * 
 * @since JDK1.1
 */
package java.lang.reflect;
