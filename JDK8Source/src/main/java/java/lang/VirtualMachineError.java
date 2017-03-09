/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2011, Oracle and/or its affiliates. All rights reserved.
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
 * Thrown to indicate that the Java Virtual Machine is broken or has
 * run out of resources necessary for it to continue operating.
 *
 *
 * <p>
 *  抛出以指示Java虚拟机已损坏或已用尽其资源继续操作所需的资源。
 * 
 * 
 * @author  Frank Yellin
 * @since   JDK1.0
 */
abstract public class VirtualMachineError extends Error {
    private static final long serialVersionUID = 4161983926571568670L;

    /**
     * Constructs a <code>VirtualMachineError</code> with no detail message.
     * <p>
     *  构造一个没有详细消息的<code> VirtualMachineError </code>。
     * 
     */
    public VirtualMachineError() {
        super();
    }

    /**
     * Constructs a <code>VirtualMachineError</code> with the specified
     * detail message.
     *
     * <p>
     *  用指定的详细消息构造一个<code> VirtualMachineError </code>。
     * 
     * 
     * @param   message   the detail message.
     */
    public VirtualMachineError(String message) {
        super(message);
    }

    /**
     * Constructs a {@code VirtualMachineError} with the specified
     * detail message and cause.  <p>Note that the detail message
     * associated with {@code cause} is <i>not</i> automatically
     * incorporated in this error's detail message.
     *
     * <p>
     *  构造具有指定的详细消息和原因的{@code VirtualMachineError}。 <p>请注意,与{@code cause}相关联的详细信息</i>不会自动并入此错误的详细信息中。
     * 
     * 
     * @param  message the detail message (which is saved for later retrieval
     *         by the {@link #getMessage()} method).
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A {@code null} value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     * @since  1.8
     */
    public VirtualMachineError(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs an a {@code VirtualMachineError} with the specified
     * cause and a detail message of {@code (cause==null ? null :
     * cause.toString())} (which typically contains the class and
     * detail message of {@code cause}).
     *
     * <p>
     *  构造具有指定原因的{@code VirtualMachineError}和{@code(cause == null?null：cause.toString())}的详细消息(通常包含{@code cause}
     * 的类和详细消息) )。
     * 
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A {@code null} value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     * @since  1.8
     */
    public VirtualMachineError(Throwable cause) {
        super(cause);
    }
}
