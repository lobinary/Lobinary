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
 * Thrown if an application tries to access or modify a specified
 * field of an object, and that object no longer has that field.
 * <p>
 * Normally, this error is caught by the compiler; this error can
 * only occur at run time if the definition of a class has
 * incompatibly changed.
 *
 * <p>
 *  如果应用程序尝试访问或修改对象的指定字段,并且该对象不再具有该字段,则抛出。
 * <p>
 *  通常,这个错误被编译器捕获;此错误只能在运行时发生,如果类的定义不兼容地更改。
 * 
 * 
 * @author  unascribed
 * @since   JDK1.0
 */
public
class NoSuchFieldError extends IncompatibleClassChangeError {
    private static final long serialVersionUID = -3456430195886129035L;

    /**
     * Constructs a <code>NoSuchFieldError</code> with no detail message.
     * <p>
     *  构造一个没有详细消息的<code> NoSuchFieldError </code>。
     * 
     */
    public NoSuchFieldError() {
        super();
    }

    /**
     * Constructs a <code>NoSuchFieldError</code> with the specified
     * detail message.
     *
     * <p>
     *  用指定的详细消息构造一个<code> NoSuchFieldError </code>。
     * 
     * @param   s   the detail message.
     */
    public NoSuchFieldError(String s) {
        super(s);
    }
}
