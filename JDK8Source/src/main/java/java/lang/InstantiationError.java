/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
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
 * Thrown when an application tries to use the Java <code>new</code>
 * construct to instantiate an abstract class or an interface.
 * <p>
 * Normally, this error is caught by the compiler; this error can
 * only occur at run time if the definition of a class has
 * incompatibly changed.
 *
 * <p>
 *  当应用程序尝试使用Java <code> new </code>结构来实例化抽象类或接口时抛出。
 * <p>
 *  通常,这个错误被编译器捕获;此错误只能在运行时发生,如果类的定义不兼容地更改。
 * 
 * 
 * @author  unascribed
 * @since   JDK1.0
 */


public
class InstantiationError extends IncompatibleClassChangeError {
    private static final long serialVersionUID = -4885810657349421204L;

    /**
     * Constructs an <code>InstantiationError</code> with no detail  message.
     * <p>
     *  构造一个没有详细消息的<code> InstantiationError </code>。
     * 
     */
    public InstantiationError() {
        super();
    }

    /**
     * Constructs an <code>InstantiationError</code> with the specified
     * detail message.
     *
     * <p>
     *  使用指定的详细消息构造<code> InstantiationError </code>。
     * 
     * @param   s   the detail message.
     */
    public InstantiationError(String s) {
        super(s);
    }
}
