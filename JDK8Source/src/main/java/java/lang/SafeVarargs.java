/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.lang.annotation.*;

/**
 * A programmer assertion that the body of the annotated method or
 * constructor does not perform potentially unsafe operations on its
 * varargs parameter.  Applying this annotation to a method or
 * constructor suppresses unchecked warnings about a
 * <i>non-reifiable</i> variable arity (vararg) type and suppresses
 * unchecked warnings about parameterized array creation at call
 * sites.
 *
 * <p> In addition to the usage restrictions imposed by its {@link
 * Target @Target} meta-annotation, compilers are required to implement
 * additional usage restrictions on this annotation type; it is a
 * compile-time error if a method or constructor declaration is
 * annotated with a {@code @SafeVarargs} annotation, and either:
 * <ul>
 * <li>  the declaration is a fixed arity method or constructor
 *
 * <li> the declaration is a variable arity method that is neither
 * {@code static} nor {@code final}.
 *
 * </ul>
 *
 * <p> Compilers are encouraged to issue warnings when this annotation
 * type is applied to a method or constructor declaration where:
 *
 * <ul>
 *
 * <li> The variable arity parameter has a reifiable element type,
 * which includes primitive types, {@code Object}, and {@code String}.
 * (The unchecked warnings this annotation type suppresses already do
 * not occur for a reifiable element type.)
 *
 * <li> The body of the method or constructor declaration performs
 * potentially unsafe operations, such as an assignment to an element
 * of the variable arity parameter's array that generates an unchecked
 * warning.  Some unsafe operations do not trigger an unchecked
 * warning.  For example, the aliasing in
 *
 * <blockquote><pre>
 * &#64;SafeVarargs // Not actually safe!
 * static void m(List&lt;String&gt;... stringLists) {
 *   Object[] array = stringLists;
 *   List&lt;Integer&gt; tmpList = Arrays.asList(42);
 *   array[0] = tmpList; // Semantically invalid, but compiles without warnings
 *   String s = stringLists[0].get(0); // Oh no, ClassCastException at runtime!
 * }
 * </pre></blockquote>
 *
 * leads to a {@code ClassCastException} at runtime.
 *
 * <p>Future versions of the platform may mandate compiler errors for
 * such unsafe operations.
 *
 * </ul>
 *
 * <p>
 *  程序员断言注释方法或构造函数的主体不对其varargs参数执行潜在的不安全操作。
 * 将此注释应用于方法或构造函数会抑制关于<i>不可重新引用的</i>变量arity(vararg)类型的未选中警告,并抑制在调用站点上参数化数组创建的未选中警告。
 * 
 *  <p>除了其{@link Target @Target}元注释施加的使用限制之外,编译器还需要对此注释类型实施其他使用限制;如果方法或构造函数声明用{@code @SafeVarargs}注释注释,那
 * 么它是编译时错误,并且：。
 * <ul>
 *  <li>声明是固定的方法或构造函数
 * 
 *  <li>声明是一种变量arity方法,既不是{@code static}也不是{@code final}。
 * 
 * </ul>
 * 
 *  <p>鼓励编译器在将此注释类型应用于方法或构造函数声明时发出警告,其中：
 * 
 * <ul>
 * 
 * @since 1.7
 * @jls 4.7 Reifiable Types
 * @jls 8.4.1 Formal Parameters
 * @jls 9.6.3.7 @SafeVarargs
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD})
public @interface SafeVarargs {}
