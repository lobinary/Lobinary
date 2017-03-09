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
 * Exception thrown when a blocking operation times out.  Blocking
 * operations for which a timeout is specified need a means to
 * indicate that the timeout has occurred. For many such operations it
 * is possible to return a value that indicates timeout; when that is
 * not possible or desirable then {@code TimeoutException} should be
 * declared and thrown.
 *
 * <p>
 *  阻塞操作超时时抛出异常。指定超时的阻塞操作需要指示已发生超时的方法。
 * 对于许多这样的操作,可以返回指示超时的值;当这不可能或不可取时,那么{@code TimeoutException}应该被声明和抛出。
 * 
 * 
 * @since 1.5
 * @author Doug Lea
 */
public class TimeoutException extends Exception {
    private static final long serialVersionUID = 1900926677490660714L;

    /**
     * Constructs a {@code TimeoutException} with no specified detail
     * message.
     * <p>
     *  构造一个没有指定详细消息的{@code TimeoutException}。
     * 
     */
    public TimeoutException() {}

    /**
     * Constructs a {@code TimeoutException} with the specified detail
     * message.
     *
     * <p>
     *  构造具有指定详细消息的{@code TimeoutException}。
     * 
     * @param message the detail message
     */
    public TimeoutException(String message) {
        super(message);
    }
}
