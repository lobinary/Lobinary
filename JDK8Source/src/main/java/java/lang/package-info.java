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
 * Provides classes that are fundamental to the design of the Java
 * programming language. The most important classes are {@code
 * Object}, which is the root of the class hierarchy, and {@code
 * Class}, instances of which represent classes at run time.
 *
 * <p>Frequently it is necessary to represent a value of primitive
 * type as if it were an object. The wrapper classes {@code Boolean},
 * {@code Character}, {@code Integer}, {@code Long}, {@code Float},
 * and {@code Double} serve this purpose.  An object of type {@code
 * Double}, for example, contains a field whose type is double,
 * representing that value in such a way that a reference to it can be
 * stored in a variable of reference type.  These classes also provide
 * a number of methods for converting among primitive values, as well
 * as supporting such standard methods as equals and hashCode.  The
 * {@code Void} class is a non-instantiable class that holds a
 * reference to a {@code Class} object representing the type void.
 *
 * <p>The class {@code Math} provides commonly used mathematical
 * functions such as sine, cosine, and square root. The classes {@code
 * String}, {@code StringBuffer}, and {@code StringBuilder} similarly
 * provide commonly used operations on character strings.
 *
 * <p>Classes {@code ClassLoader}, {@code Process}, {@code
 * ProcessBuilder}, {@code Runtime}, {@code SecurityManager}, and
 * {@code System} provide "system operations" that manage the dynamic
 * loading of classes, creation of external processes, host
 * environment inquiries such as the time of day, and enforcement of
 * security policies.
 *
 * <p>Class {@code Throwable} encompasses objects that may be thrown
 * by the {@code throw} statement. Subclasses of {@code Throwable}
 * represent errors and exceptions.
 *
 * <a name="charenc"></a>
 * <h3>Character Encodings</h3>
 *
 * The specification of the {@link java.nio.charset.Charset
 * java.nio.charset.Charset} class describes the naming conventions
 * for character encodings as well as the set of standard encodings
 * that must be supported by every implementation of the Java
 * platform.
 *
 * <p>
 *  提供对Java编程语言的设计至关重要的类。最重要的类是{@code Object},它是类层次结构的根,和{@code Class},其实例在运行时表示类。
 * 
 *  <p>通常,必须表示原始类型的值,如同它是一个对象一样。
 * 包装器类{@code Boolean},{@code Character},{@code Integer},{@code Long},{@code Float}和{@code Double}用于此目的。
 *  <p>通常,必须表示原始类型的值,如同它是一个对象一样。例如,类型为{@code Double}的对象包含类型为double的字段,以这样的方式表示该值,即对其的引用可以存储在引用类型的变量中。
 * 这些类还提供了许多用于在原始值之间转换的方法,以及支持诸如equals和hashCode的标准方法。
 *  {@code Void}类是一个不可实例化的类,它保存对表示类型void的{@code Class}对象的引用。
 * 
 *  <p>类{@code Math}提供常用的数学函数,如正弦,余弦和平方根。
 * 类{@code String},{@code StringBuffer}和{@code StringBuilder}类似地提供了对字符串的常用操作。
 * 
 * @since JDK1.0
 */
package java.lang;
