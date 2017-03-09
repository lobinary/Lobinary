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
 * Exception thrown when a thread tries to wait upon a barrier that is
 * in a broken state, or which enters the broken state while the thread
 * is waiting.
 *
 * <p>
 *  当线程尝试等待处于断开状态的障碍时,或者当线程正在等待时进入断开状态的异常时抛出异常。
 * 
 * 
 * @see CyclicBarrier
 *
 * @since 1.5
 * @author Doug Lea
 */
public class BrokenBarrierException extends Exception {
    private static final long serialVersionUID = 7117394618823254244L;

    /**
     * Constructs a {@code BrokenBarrierException} with no specified detail
     * message.
     * <p>
     *  构造一个没有指定详细消息的{@code BrokenBarrierException}。
     * 
     */
    public BrokenBarrierException() {}

    /**
     * Constructs a {@code BrokenBarrierException} with the specified
     * detail message.
     *
     * <p>
     *  使用指定的详细消息构造{@code BrokenBarrierException}。
     * 
     * @param message the detail message
     */
    public BrokenBarrierException(String message) {
        super(message);
    }
}
