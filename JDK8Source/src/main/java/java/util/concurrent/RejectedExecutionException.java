/***** Lobxxx Translate Finished ******/
/*
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

/*
 *
 *
 *
 *
 *
 * Written by Doug Lea with assistance from members of JCP JSR-166
 * Expert Group and released to the public domain, as explained at
 * http://creativecommons.org/publicdomain/zero/1.0/
 * <p>
 *  由Doug Lea在JCP JSR-166专家组成员的帮助下撰写,并发布到公共领域,如http://creativecommons.org/publicdomain/zero/1.0/
 * 
 */

package java.util.concurrent;

/**
 * Exception thrown by an {@link Executor} when a task cannot be
 * accepted for execution.
 *
 * <p>
 *  当任务无法被接受执行时由{@link Executor}抛出的异常。
 * 
 * 
 * @since 1.5
 * @author Doug Lea
 */
public class RejectedExecutionException extends RuntimeException {
    private static final long serialVersionUID = -375805702767069545L;

    /**
     * Constructs a {@code RejectedExecutionException} with no detail message.
     * The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause(Throwable) initCause}.
     * <p>
     *  构造一个没有详细消息的{@code RejectedExecutionException}。
     * 原因未初始化,并可能随后通过调用{@link #initCause(Throwable)initCause}初始化。
     * 
     */
    public RejectedExecutionException() { }

    /**
     * Constructs a {@code RejectedExecutionException} with the
     * specified detail message. The cause is not initialized, and may
     * subsequently be initialized by a call to {@link
     * #initCause(Throwable) initCause}.
     *
     * <p>
     *  使用指定的详细消息构造{@code RejectedExecutionException}。
     * 原因未初始化,并可能随后通过调用{@link #initCause(Throwable)initCause}初始化。
     * 
     * 
     * @param message the detail message
     */
    public RejectedExecutionException(String message) {
        super(message);
    }

    /**
     * Constructs a {@code RejectedExecutionException} with the
     * specified detail message and cause.
     *
     * <p>
     *  构造具有指定的详细消息和原因的{@code RejectedExecutionException}。
     * 
     * 
     * @param  message the detail message
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method)
     */
    public RejectedExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a {@code RejectedExecutionException} with the
     * specified cause.  The detail message is set to {@code (cause ==
     * null ? null : cause.toString())} (which typically contains
     * the class and detail message of {@code cause}).
     *
     * <p>
     *  构造具有指定原因的{@code RejectedExecutionException}。
     * 详细消息设置为{@code(cause == null?null：cause.toString())}(通常包含{@code cause}的类和详细消息)。
     * 
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method)
     */
    public RejectedExecutionException(Throwable cause) {
        super(cause);
    }
}
