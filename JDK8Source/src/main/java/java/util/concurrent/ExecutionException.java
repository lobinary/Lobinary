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
 * Exception thrown when attempting to retrieve the result of a task
 * that aborted by throwing an exception. This exception can be
 * inspected using the {@link #getCause()} method.
 *
 * <p>
 *  尝试检索通过抛出异常而中止的任务的结果时抛出异常。可以使用{@link #getCause()}方法检查此异常。
 * 
 * 
 * @see Future
 * @since 1.5
 * @author Doug Lea
 */
public class ExecutionException extends Exception {
    private static final long serialVersionUID = 7830266012832686185L;

    /**
     * Constructs an {@code ExecutionException} with no detail message.
     * The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause(Throwable) initCause}.
     * <p>
     *  构造一个没有详细消息的{@code ExecutionException}。原因未初始化,并可能随后通过调用{@link #initCause(Throwable)initCause}初始化。
     * 
     */
    protected ExecutionException() { }

    /**
     * Constructs an {@code ExecutionException} with the specified detail
     * message. The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause(Throwable) initCause}.
     *
     * <p>
     *  构造具有指定详细消息的{@code ExecutionException}。原因未初始化,并可能随后通过调用{@link #initCause(Throwable)initCause}初始化。
     * 
     * 
     * @param message the detail message
     */
    protected ExecutionException(String message) {
        super(message);
    }

    /**
     * Constructs an {@code ExecutionException} with the specified detail
     * message and cause.
     *
     * <p>
     *  构造具有指定的详细消息和原因的{@code ExecutionException}。
     * 
     * 
     * @param  message the detail message
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method)
     */
    public ExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs an {@code ExecutionException} with the specified cause.
     * The detail message is set to {@code (cause == null ? null :
     * cause.toString())} (which typically contains the class and
     * detail message of {@code cause}).
     *
     * <p>
     *  构造具有指定原因的{@code ExecutionException}。
     * 详细消息设置为{@code(cause == null?null：cause.toString())}(通常包含{@code cause}的类和详细消息)。
     * 
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method)
     */
    public ExecutionException(Throwable cause) {
        super(cause);
    }
}
