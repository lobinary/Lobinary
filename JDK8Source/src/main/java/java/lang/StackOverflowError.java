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
 * Thrown when a stack overflow occurs because an application
 * recurses too deeply.
 *
 * <p>
 *  当发生堆栈溢出时抛出,因为应用程序执行得太深。
 * 
 * 
 * @author unascribed
 * @since   JDK1.0
 */
public
class StackOverflowError extends VirtualMachineError {
    private static final long serialVersionUID = 8609175038441759607L;

    /**
     * Constructs a <code>StackOverflowError</code> with no detail message.
     * <p>
     *  构造一个没有详细消息的<code> StackOverflowError </code>。
     * 
     */
    public StackOverflowError() {
        super();
    }

    /**
     * Constructs a <code>StackOverflowError</code> with the specified
     * detail message.
     *
     * <p>
     *  用指定的详细消息构造一个<code> StackOverflowError </code>。
     * 
     * @param   s   the detail message.
     */
    public StackOverflowError(String s) {
        super(s);
    }
}
