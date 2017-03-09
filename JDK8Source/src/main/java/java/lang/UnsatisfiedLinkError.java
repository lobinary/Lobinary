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
 * Thrown if the Java Virtual Machine cannot find an appropriate
 * native-language definition of a method declared <code>native</code>.
 *
 * <p>
 *  如果Java虚拟机找不到声明为<code> native </code>的方法的合适的本地语言定义,则抛出此异常。
 * 
 * 
 * @author unascribed
 * @see     java.lang.Runtime
 * @since   JDK1.0
 */
public
class UnsatisfiedLinkError extends LinkageError {
    private static final long serialVersionUID = -4019343241616879428L;

    /**
     * Constructs an <code>UnsatisfiedLinkError</code> with no detail message.
     * <p>
     *  构造一个没有详细消息的<code> UnsatisfiedLinkError </code>。
     * 
     */
    public UnsatisfiedLinkError() {
        super();
    }

    /**
     * Constructs an <code>UnsatisfiedLinkError</code> with the
     * specified detail message.
     *
     * <p>
     *  使用指定的详细消息构造<code> UnsatisfiedLinkError </code>。
     * 
     * @param   s   the detail message.
     */
    public UnsatisfiedLinkError(String s) {
        super(s);
    }
}
