/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2004, 2008, Oracle and/or its affiliates. All rights reserved.
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

package java.lang.instrument;

/**
 * Thrown by an implementation of
 * {@link java.lang.instrument.Instrumentation#redefineClasses Instrumentation.redefineClasses}
 * when one of the specified classes cannot be modified.
 *
 * <p>
 *  当某个指定的类无法修改时,由{@link java.lang.instrument.Instrumentation#redefineClasses Instrumentation.redefineClasses}
 * 的实现引发。
 * 
 * 
 * @see     java.lang.instrument.Instrumentation#redefineClasses
 * @since   1.5
 */
public class UnmodifiableClassException extends Exception {
    private static final long serialVersionUID = 1716652643585309178L;

    /**
     * Constructs an <code>UnmodifiableClassException</code> with no
     * detail message.
     * <p>
     *  构造一个没有详细消息的<code> UnmodifiableClassException </code>。
     * 
     */
    public
    UnmodifiableClassException() {
        super();
    }

    /**
     * Constructs an <code>UnmodifiableClassException</code> with the
     * specified detail message.
     *
     * <p>
     *  构造具有指定详细消息的<code> UnmodifiableClassException </code>。
     * 
     * @param   s   the detail message.
     */
    public
    UnmodifiableClassException(String s) {
        super(s);
    }
}
