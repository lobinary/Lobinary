/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2006, Oracle and/or its affiliates. All rights reserved.
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
 * Provides interfaces for tools which can be invoked from a program,
 * for example, compilers.
 *
 * <p>These interfaces and classes are required as part of the
 * Java&trade; Platform, Standard Edition (Java SE),
 * but there is no requirement to provide any tools implementing them.
 *
 * <p>Unless explicitly allowed, all methods in this package might
 * throw a {@linkplain java.lang.NullPointerException} if given a
 * {@code null} argument or if given a
 * {@linkplain java.lang.Iterable list or collection} containing
 * {@code null} elements.  Similarly, no method may return
 * {@code null} unless explicitly allowed.
 *
 * <p>This package is the home of the Java programming language compiler framework.  This
 * framework allows clients of the framework to locate and run
 * compilers from programs.  The framework also provides Service
 * Provider Interfaces (SPI) for structured access to diagnostics
 * ({@linkplain javax.tools.DiagnosticListener}) as well as a file
 * abstraction for overriding file access ({@linkplain
 * javax.tools.JavaFileManager} and {@linkplain
 * javax.tools.JavaFileObject}).  See {@linkplain
 * javax.tools.JavaCompiler} for more details on using the SPI.
 *
 * <p>There is no requirement for a compiler at runtime.  However, if
 * a default compiler is provided, it can be located using the
 * {@linkplain javax.tools.ToolProvider}, for example:
 *
 * <p>{@code JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();}
 *
 * <p>It is possible to provide alternative compilers or tools
 * through the {@linkplain java.util.ServiceLoader service provider
 * mechanism}.
 *
 * <p>For example, if {@code com.vendor.VendorJavaCompiler} is a
 * provider of the {@code JavaCompiler} tool then its jar file
 * would contain the file {@code
 * META-INF/services/javax.tools.JavaCompiler}.  This file would
 * contain the single line:
 *
 * <p>{@code com.vendor.VendorJavaCompiler}
 *
 * <p>If the jar file is on the class path, VendorJavaCompiler can be
 * located using code like this:
 *
 * <p>{@code JavaCompiler compiler = ServiceLoader.load(JavaCompiler.class).iterator().next();}
 *
 * <p>
 *  提供可从程序(例如编译器)调用的工具的接口。
 * 
 *  <p>这些接口和类作为Java&trade的一部分是必需的;平台,标准版(Java SE),但没有要求提供任何实现它们的工具。
 * 
 *  <p>除非明确允许,否则此包中的所有方法都可能会抛出一个{@linkplain java.lang.NullPointerException}(如果给定了一个{@code null}参数),或者如果给
 * 定了一个{@linkplain java.lang.Iterable list或collection} {@code null}元素。
 * 类似地,除非明确允许,否则任何方法都不会返回{@code null}。
 * 
 *  <p>这个包是Java编程语言编译器框架的家。这个框架允许框架的客户端从程序中定位和运行编译器。
 * 该框架还提供用于结构化访问诊断的服务提供程序接口(SPI)({@linkplain javax.tools.DiagnosticListener})以及用于覆盖文件访问的文件抽象({@linkplain javax.tools.JavaFileManager}
 * 和{@linkplain javax.tools.JavaFileObject})。
 *  <p>这个包是Java编程语言编译器框架的家。这个框架允许框架的客户端从程序中定位和运行编译器。
 * 有关使用SPI的更多细节,请参见{@linkplain javax.tools.JavaCompiler}。
 * 
 *  <p>在运行时不需要编译器。但是,如果提供了缺省编译器,可以使用{@linkplain javax.tools.ToolProvider}来定位它,例如：
 * 
 * @author Peter von der Ah&eacute;
 * @author Jonathan Gibbons
 * @since 1.6
 */
package javax.tools;
