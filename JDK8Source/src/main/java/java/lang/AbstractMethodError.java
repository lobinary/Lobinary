/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1994, 2008, Oracle and/or its affiliates. All rights reserved.
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
 * Thrown when an application tries to call an abstract method.
 * Normally, this error is caught by the compiler; this error can
 * only occur at run time if the definition of some class has
 * incompatibly changed since the currently executing method was last
 * compiled.
 *
 * <p>
 *  当应用程序尝试调用抽象方法时抛出。通常,这个错误被编译器捕获;此错误只能在运行时发生,如果某个类的定义自上次编译当前执行的方法以来不兼容地更改。
 * 
 * 
 * @author  unascribed
 * @since   JDK1.0
 */
public
class AbstractMethodError extends IncompatibleClassChangeError {
    private static final long serialVersionUID = -1654391082989018462L;

    /**
     * Constructs an <code>AbstractMethodError</code> with no detail  message.
     * <p>
     *  构造一个没有详细消息的<code> AbstractMethodError </code>。
     * 
     */
    public AbstractMethodError() {
        super();
    }

    /**
     * Constructs an <code>AbstractMethodError</code> with the specified
     * detail message.
     *
     * <p>
     *  使用指定的详细消息构造一个<code> AbstractMethodError </code>。
     * 
     * @param   s   the detail message.
     */
    public AbstractMethodError(String s) {
        super(s);
    }
}
