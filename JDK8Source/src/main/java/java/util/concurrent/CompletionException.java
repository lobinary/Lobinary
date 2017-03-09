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
 * Exception thrown when an error or other exception is encountered
 * in the course of completing a result or task.
 *
 * <p>
 *  在完成结果或任务的过程中遇到错误或其他异常时抛出异常。
 * 
 * 
 * @since 1.8
 * @author Doug Lea
 */
public class CompletionException extends RuntimeException {
    private static final long serialVersionUID = 7830266012832686185L;

    /**
     * Constructs a {@code CompletionException} with no detail message.
     * The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause(Throwable) initCause}.
     * <p>
     *  构造一个没有详细消息的{@code CompletionException}。原因未初始化,并可能随后通过调用{@link #initCause(Throwable)initCause}初始化。
     * 
     */
    protected CompletionException() { }

    /**
     * Constructs a {@code CompletionException} with the specified detail
     * message. The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause(Throwable) initCause}.
     *
     * <p>
     *  构造具有指定详细消息的{@code CompletionException}。原因未初始化,并可能随后通过调用{@link #initCause(Throwable)initCause}初始化。
     * 
     * 
     * @param message the detail message
     */
    protected CompletionException(String message) {
        super(message);
    }

    /**
     * Constructs a {@code CompletionException} with the specified detail
     * message and cause.
     *
     * <p>
     *  构造具有指定的详细消息和原因的{@code CompletionException}。
     * 
     * 
     * @param  message the detail message
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method)
     */
    public CompletionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a {@code CompletionException} with the specified cause.
     * The detail message is set to {@code (cause == null ? null :
     * cause.toString())} (which typically contains the class and
     * detail message of {@code cause}).
     *
     * <p>
     *  构造具有指定原因的{@code CompletionException}。
     * 详细消息设置为{@code(cause == null?null：cause.toString())}(通常包含{@code cause}的类和详细消息)。
     * 
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method)
     */
    public CompletionException(Throwable cause) {
        super(cause);
    }
}
