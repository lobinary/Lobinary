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
 * Thrown if an application tries to call a specified method of a
 * class (either static or instance), and that class no longer has a
 * definition of that method.
 * <p>
 * Normally, this error is caught by the compiler; this error can
 * only occur at run time if the definition of a class has
 * incompatibly changed.
 *
 * <p>
 *  如果应用程序尝试调用类(静态或实例)的指定方法,并且该类不再具有该方法的定义,则抛出。
 * <p>
 *  通常,这个错误被编译器捕获;此错误只能在运行时发生,如果类的定义不兼容地更改。
 * 
 * 
 * @author  unascribed
 * @since   JDK1.0
 */
public
class NoSuchMethodError extends IncompatibleClassChangeError {
    private static final long serialVersionUID = -3765521442372831335L;

    /**
     * Constructs a <code>NoSuchMethodError</code> with no detail message.
     * <p>
     *  构造一个没有详细消息的<code> NoSuchMethodError </code>。
     * 
     */
    public NoSuchMethodError() {
        super();
    }

    /**
     * Constructs a <code>NoSuchMethodError</code> with the
     * specified detail message.
     *
     * <p>
     *  使用指定的详细消息构造<code> NoSuchMethodError </code>。
     * 
     * @param   s   the detail message.
     */
    public NoSuchMethodError(String s) {
        super(s);
    }
}
