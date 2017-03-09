/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1994, 2006, Oracle and/or its affiliates. All rights reserved.
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

package java.io;

/**
 * Signals that an I/O exception of some sort has occurred. This
 * class is the general class of exceptions produced by failed or
 * interrupted I/O operations.
 *
 * <p>
 *  表示发生了某种I / O异常。此类是由失败或中断的I / O操作产生的异常的一般类。
 * 
 * 
 * @author  unascribed
 * @see     java.io.InputStream
 * @see     java.io.OutputStream
 * @since   JDK1.0
 */
public
class IOException extends Exception {
    static final long serialVersionUID = 7818375828146090155L;

    /**
     * Constructs an {@code IOException} with {@code null}
     * as its error detail message.
     * <p>
     *  使用{@code null}作为其错误详细信息构造{@code IOException}。
     * 
     */
    public IOException() {
        super();
    }

    /**
     * Constructs an {@code IOException} with the specified detail message.
     *
     * <p>
     *  构造具有指定详细消息的{@code IOException}。
     * 
     * 
     * @param message
     *        The detail message (which is saved for later retrieval
     *        by the {@link #getMessage()} method)
     */
    public IOException(String message) {
        super(message);
    }

    /**
     * Constructs an {@code IOException} with the specified detail message
     * and cause.
     *
     * <p> Note that the detail message associated with {@code cause} is
     * <i>not</i> automatically incorporated into this exception's detail
     * message.
     *
     * <p>
     *  构造具有指定的详细消息和原因的{@code IOException}。
     * 
     *  <p>请注意,与{@code cause}相关联的详细信息</i>不会自动并入此例外的详细信息中。
     * 
     * 
     * @param message
     *        The detail message (which is saved for later retrieval
     *        by the {@link #getMessage()} method)
     *
     * @param cause
     *        The cause (which is saved for later retrieval by the
     *        {@link #getCause()} method).  (A null value is permitted,
     *        and indicates that the cause is nonexistent or unknown.)
     *
     * @since 1.6
     */
    public IOException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs an {@code IOException} with the specified cause and a
     * detail message of {@code (cause==null ? null : cause.toString())}
     * (which typically contains the class and detail message of {@code cause}).
     * This constructor is useful for IO exceptions that are little more
     * than wrappers for other throwables.
     *
     * <p>
     *  构造具有指定原因的{@code IOException}和{@code(cause == null?null：cause.toString())}的详细消息(通常包含{@code cause}的类和详
     * 细信息) 。
     * 
     * @param cause
     *        The cause (which is saved for later retrieval by the
     *        {@link #getCause()} method).  (A null value is permitted,
     *        and indicates that the cause is nonexistent or unknown.)
     *
     * @since 1.6
     */
    public IOException(Throwable cause) {
        super(cause);
    }
}
