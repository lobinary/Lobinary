/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * <em>Functional interfaces</em> provide target types for lambda expressions
 * and method references.  Each functional interface has a single abstract
 * method, called the <em>functional method</em> for that functional interface,
 * to which the lambda expression's parameter and return types are matched or
 * adapted.  Functional interfaces can provide a target type in multiple
 * contexts, such as assignment context, method invocation, or cast context:
 *
 * <pre>{@code
 *     // Assignment context
 *     Predicate<String> p = String::isEmpty;
 *
 *     // Method invocation context
 *     stream.filter(e -> e.getSize() > 10)...
 *
 *     // Cast context
 *     stream.map((ToIntFunction) e -> e.getSize())...
 * }</pre>
 *
 * <p>The interfaces in this package are general purpose functional interfaces
 * used by the JDK, and are available to be used by user code as well.  While
 * they do not identify a complete set of function shapes to which lambda
 * expressions might be adapted, they provide enough to cover common
 * requirements. Other functional interfaces provided for specific purposes,
 * such as {@link java.io.FileFilter}, are defined in the packages where they
 * are used.
 *
 * <p>The interfaces in this package are annotated with
 * {@link java.lang.FunctionalInterface}. This annotation is not a requirement
 * for the compiler to recognize an interface as a functional interface, but
 * merely an aid to capture design intent and enlist the help of the compiler in
 * identifying accidental violations of design intent.
 *
 * <p>Functional interfaces often represent abstract concepts like functions,
 * actions, or predicates.  In documenting functional interfaces, or referring
 * to variables typed as functional interfaces, it is common to refer directly
 * to those abstract concepts, for example using "this function" instead of
 * "the function represented by this object".  When an API method is said to
 * accept or return a functional interface in this manner, such as "applies the
 * provided function to...", this is understood to mean a <i>non-null</i>
 * reference to an object implementing the appropriate functional interface,
 * unless potential nullity is explicitly specified.
 *
 * <p>The functional interfaces in this package follow an extensible naming
 * convention, as follows:
 *
 * <ul>
 *     <li>There are several basic function shapes, including
 *     {@link java.util.function.Function} (unary function from {@code T} to {@code R}),
 *     {@link java.util.function.Consumer} (unary function from {@code T} to {@code void}),
 *     {@link java.util.function.Predicate} (unary function from {@code T} to {@code boolean}),
 *     and {@link java.util.function.Supplier} (nilary function to {@code R}).
 *     </li>
 *
 *     <li>Function shapes have a natural arity based on how they are most
 *     commonly used.  The basic shapes can be modified by an arity prefix to
 *     indicate a different arity, such as
 *     {@link java.util.function.BiFunction} (binary function from {@code T} and
 *     {@code U} to {@code R}).
 *     </li>
 *
 *     <li>There are additional derived function shapes which extend the basic
 *     function shapes, including {@link java.util.function.UnaryOperator}
 *     (extends {@code Function}) and {@link java.util.function.BinaryOperator}
 *     (extends {@code BiFunction}).
 *     </li>
 *
 *     <li>Type parameters of functional interfaces can be specialized to
 *     primitives with additional type prefixes.  To specialize the return type
 *     for a type that has both generic return type and generic arguments, we
 *     prefix {@code ToXxx}, as in {@link java.util.function.ToIntFunction}.
 *     Otherwise, type arguments are specialized left-to-right, as in
 *     {@link java.util.function.DoubleConsumer}
 *     or {@link java.util.function.ObjIntConsumer}.
 *     (The type prefix {@code Obj} is used to indicate that we don't want to
 *     specialize this parameter, but want to move on to the next parameter,
 *     as in {@link java.util.function.ObjIntConsumer}.)
 *     These schemes can be combined, as in {@code IntToDoubleFunction}.
 *     </li>
 *
 *     <li>If there are specialization prefixes for all arguments, the arity
 *     prefix may be left out (as in {@link java.util.function.ObjIntConsumer}).
 *     </li>
 * </ul>
 *
 * <p>
 *  <em>功能接口</em>提供lambda表达式和方法引用的目标类型。每个功能接口都有一个抽象方法,称为该函数接口的<em>功能方法</em>,其中lambda表达式的参数和返回类型匹配或调整。
 * 功能接口可以在多个上下文中提供目标类型,例如赋值上下文,方法调用或转换上下文：。
 * 
 *  <pre> {@ code //赋值上下文谓词<String> p = String :: isEmpty;
 * 
 *  //方法调用上下文stream.filter(e  - > e.getSize()> 10)...
 * 
 *  // Cast context stream.map((ToIntFunction)e  - > e.getSize())...} </pre>
 * 
 *  <p>此包中的接口是JDK使用的通用功能接口,也可供用户代码使用。虽然它们没有识别一组完整的函数形状,lambda表达式可能适用于它们,但是它们提供足以覆盖公共需求的函数形状。
 * 为特定目的提供的其他功能接口(如{@link java.io.FileFilter})在使用它们的包中定义。
 * 
 * <p>此包中的接口使用{@link java.lang.FunctionalInterface}注释。
 * 这个注释不是编译器将接口识别为功能接口的要求,而仅仅是帮助捕获设计意图,并帮助编译器识别意外违反设计意图的帮助。
 * 
 *  <p>功能接口通常代表抽象概念,如函数,动作或谓词。在记录功能接口或引用被称为功能接口的变量时,通常直接引用那些抽象概念,例如使用"该函数"而不是"由该对象表示的函数"。
 * 当API方法被称为以这种方式接受或返回功能接口时,诸如"将所提供的函数应用于...",这被理解为是指对对象的非空的引用实现适当的功能接口,除非明确指定潜在的无效性。
 * 
 *  <p>此软件包中的功能接口遵循可扩展命名约定,如下所示：
 * 
 * <ul>
 *  <li>有几个基本的函数形状,包括{@link java.util.function.Function}(从{@code T}到{@code R}的一元函数),{@link java.util.function.Consumer}
 *  (从{@code T}到{@code void}的一元函数),{@link java.util.function.Predicate}(从{@code T}到{@code boolean}的一元函数)
 * 和{@link java .util.function.Supplier}(nilary函数到{@code R})。
 * 
 * @see java.lang.FunctionalInterface
 * @since 1.8
 */
package java.util.function;
