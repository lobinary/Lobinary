/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2010, Oracle and/or its affiliates. All rights reserved.
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
 * Subclasses of {@code LinkageError} indicate that a class has
 * some dependency on another class; however, the latter class has
 * incompatibly changed after the compilation of the former class.
 *
 *
 * <p>
 *  {@code LinkageError}的子类表明一个类对另一个类有一些依赖;然而,后一类在编译前类后不可改变。
 * 
 * 
 * @author  Frank Yellin
 * @since   JDK1.0
 */
public
class LinkageError extends Error {
    private static final long serialVersionUID = 3579600108157160122L;

    /**
     * Constructs a {@code LinkageError} with no detail message.
     * <p>
     *  构造一个没有详细消息的{@code LinkageError}。
     * 
     */
    public LinkageError() {
        super();
    }

    /**
     * Constructs a {@code LinkageError} with the specified detail
     * message.
     *
     * <p>
     *  使用指定的详细消息构造{@code LinkageError}。
     * 
     * 
     * @param   s   the detail message.
     */
    public LinkageError(String s) {
        super(s);
    }

    /**
     * Constructs a {@code LinkageError} with the specified detail
     * message and cause.
     *
     * <p>
     *  构造具有指定的详细消息和原因的{@code LinkageError}。
     * 
     * @param s     the detail message.
     * @param cause the cause, may be {@code null}
     * @since 1.7
     */
    public LinkageError(String s, Throwable cause) {
        super(s, cause);
    }
}
