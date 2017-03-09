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
 * Thrown if an application attempts to access or modify a field, or
 * to call a method that it does not have access to.
 * <p>
 * Normally, this error is caught by the compiler; this error can
 * only occur at run time if the definition of a class has
 * incompatibly changed.
 *
 * <p>
 *  如果应用程序尝试访问或修改字段,或调用其无权访问的方法,则抛出。
 * <p>
 *  通常,这个错误被编译器捕获;此错误只能在运行时发生,如果类的定义不兼容地更改。
 * 
 * 
 * @author  unascribed
 * @since   JDK1.0
 */
public class IllegalAccessError extends IncompatibleClassChangeError {
    private static final long serialVersionUID = -8988904074992417891L;

    /**
     * Constructs an <code>IllegalAccessError</code> with no detail message.
     * <p>
     *  构造一个没有详细消息的<code> IllegalAccessError </code>。
     * 
     */
    public IllegalAccessError() {
        super();
    }

    /**
     * Constructs an <code>IllegalAccessError</code> with the specified
     * detail message.
     *
     * <p>
     *  使用指定的详细消息构造<code> IllegalAccessError </code>。
     * 
     * @param   s   the detail message.
     */
    public IllegalAccessError(String s) {
        super(s);
    }
}
