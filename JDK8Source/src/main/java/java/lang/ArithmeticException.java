/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1994, 2011, Oracle and/or its affiliates. All rights reserved.
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
 * Thrown when an exceptional arithmetic condition has occurred. For
 * example, an integer "divide by zero" throws an
 * instance of this class.
 *
 * {@code ArithmeticException} objects may be constructed by the
 * virtual machine as if {@linkplain Throwable#Throwable(String,
 * Throwable, boolean, boolean) suppression were disabled and/or the
 * stack trace was not writable}.
 *
 * <p>
 *  当发生异常算术条件时抛出。例如,整数"divide by zero"抛出此类的实例。
 * 
 *  {@code ArithmeticException}对象可以由虚拟机构造,如同禁用{@linkplain Throwable#Throwable(String,Throwable,boolean,boolean)抑制和/或堆栈跟踪不可写}
 * 。
 * 
 * 
 * @author  unascribed
 * @since   JDK1.0
 */
public class ArithmeticException extends RuntimeException {
    private static final long serialVersionUID = 2256477558314496007L;

    /**
     * Constructs an {@code ArithmeticException} with no detail
     * message.
     * <p>
     *  构造一个没有详细消息的{@code ArithmeticException}。
     * 
     */
    public ArithmeticException() {
        super();
    }

    /**
     * Constructs an {@code ArithmeticException} with the specified
     * detail message.
     *
     * <p>
     *  构造具有指定详细消息的{@code ArithmeticException}。
     * 
     * @param   s   the detail message.
     */
    public ArithmeticException(String s) {
        super(s);
    }
}
