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
 * Facilities for declaring annotation processors and for
 * allowing annotation processors to communicate with an annotation processing
 * tool environment.
 *
 * <p> Unless otherwise specified in a particular implementation, the
 * collections returned by methods in this package should be expected
 * to be unmodifiable by the caller and unsafe for concurrent access.
 *
 * <p> Unless otherwise specified, methods in this package will throw
 * a {@code NullPointerException} if given a {@code null} argument.
 *
 * <p>
 *  用于声明注释处理器和用于允许注释处理器与注释处理工具环境通信的设施。
 * 
 *  <p>除非在特定实现中另有规定,否则由此包中的方法返回的集合应该是调用者不可修改的并且对并发访问不安全。
 * 
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @since 1.6
 */
package javax.annotation.processing;
