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
 * Thrown when the Java Virtual Machine cannot allocate an object
 * because it is out of memory, and no more memory could be made
 * available by the garbage collector.
 *
 * {@code OutOfMemoryError} objects may be constructed by the virtual
 * machine as if {@linkplain Throwable#Throwable(String, Throwable,
 * boolean, boolean) suppression were disabled and/or the stack trace was not
 * writable}.
 *
 * <p>
 *  当Java虚拟机无法分配对象,因为它内存不足,并且没有更多的内存可以由垃圾收集器提供时抛出。
 * 
 *  {@code OutOfMemoryError}对象可以由虚拟机构造,如同禁用{@linkplain Throwable#Throwable(String,Throwable,boolean,boolean)抑制和/或堆栈跟踪不可写}
 * 。
 * 
 * 
 * @author  unascribed
 * @since   JDK1.0
 */
public class OutOfMemoryError extends VirtualMachineError {
    private static final long serialVersionUID = 8228564086184010517L;

    /**
     * Constructs an {@code OutOfMemoryError} with no detail message.
     * <p>
     *  构造一个没有详细消息的{@code OutOfMemoryError}。
     * 
     */
    public OutOfMemoryError() {
        super();
    }

    /**
     * Constructs an {@code OutOfMemoryError} with the specified
     * detail message.
     *
     * <p>
     *  使用指定的详细消息构造{@code OutOfMemoryError}。
     * 
     * @param   s   the detail message.
     */
    public OutOfMemoryError(String s) {
        super(s);
    }
}
