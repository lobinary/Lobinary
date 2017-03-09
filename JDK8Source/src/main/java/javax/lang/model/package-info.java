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
 * Classes and hierarchies of packages used to model the Java
 * programming language.
 *
 * The members of this package and its subpackages are for use in
 * language modeling and language processing tasks and APIs including,
 * but not limited to, the {@linkplain javax.annotation.processing
 * annotation processing} framework.
 *
 * <p> This language model follows a <i>mirror</i>-based design; see
 *
 * <blockquote>
 * Gilad Bracha and David Ungar. <i>Mirrors: Design Principles for
 * Meta-level Facilities of Object-Oriented Programming Languages</i>.
 * In Proc. of the ACM Conf. on Object-Oriented Programming, Systems,
 * Languages and Applications, October 2004.
 * </blockquote>
 *
 * In particular, the model makes a distinction between static
 * language constructs, like the {@linkplain javax.lang.model.element
 * element} representing {@code java.util.Set}, and the family of
 * {@linkplain javax.lang.model.type types} that may be associated
 * with an element, like the raw type {@code java.util.Set}, {@code
 * java.util.Set<String>}, and {@code java.util.Set<T>}.
 *
 * <p> Unless otherwise specified, methods in this package will throw
 * a {@code NullPointerException} if given a {@code null} argument.
 *
 * <p>
 *  用于对Java编程语言建模的包的类和层次结构。
 * 
 *  此包及其子包的成员用于语言建模和语言处理任务和API,包括但不限于{@linkplain javax.annotation.processing注释处理}框架。
 * 
 *  <p>这种语言模型遵循基于<i>镜像</i>的设计;看到
 * 
 * <blockquote>
 *  吉拉德Bracha和大卫Ungar。 <i>镜像：面向对象的编程语言的元级设施的设计原理</i>。在Proc。的ACM Conf。
 * 
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @since 1.6
 */

package javax.lang.model;
