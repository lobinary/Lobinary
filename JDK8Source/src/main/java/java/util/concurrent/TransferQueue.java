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
 * A {@link BlockingQueue} in which producers may wait for consumers
 * to receive elements.  A {@code TransferQueue} may be useful for
 * example in message passing applications in which producers
 * sometimes (using method {@link #transfer}) await receipt of
 * elements by consumers invoking {@code take} or {@code poll}, while
 * at other times enqueue elements (via method {@code put}) without
 * waiting for receipt.
 * {@linkplain #tryTransfer(Object) Non-blocking} and
 * {@linkplain #tryTransfer(Object,long,TimeUnit) time-out} versions of
 * {@code tryTransfer} are also available.
 * A {@code TransferQueue} may also be queried, via {@link
 * #hasWaitingConsumer}, whether there are any threads waiting for
 * items, which is a converse analogy to a {@code peek} operation.
 *
 * <p>Like other blocking queues, a {@code TransferQueue} may be
 * capacity bounded.  If so, an attempted transfer operation may
 * initially block waiting for available space, and/or subsequently
 * block waiting for reception by a consumer.  Note that in a queue
 * with zero capacity, such as {@link SynchronousQueue}, {@code put}
 * and {@code transfer} are effectively synonymous.
 *
 * <p>This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * <p>
 *  {@link BlockingQueue},其中生产者可以等待消费者接收元素。
 *  {@code TransferQueue}例如在消息传递应用中是有用的,其中生产者有时(使用方法{@link #transfer})等待消费者调用{@code take}或{@code poll}的元
 * 素的接收,而在其他时候入队元素(通过方法{@code put})而不等待收据。
 *  {@link BlockingQueue},其中生产者可以等待消费者接收元素。
 *  {@linkplain #tryTransfer(Object)Non-blocking}和{@linkplain #tryTransfer(Object,long,TimeUnit)超时}版本的{@code tryTransfer}
 * 也可用。
 *  {@link BlockingQueue},其中生产者可以等待消费者接收元素。
 * 也可以通过{@link #hasWaitingConsumer}查询{@code TransferQueue},是否有任何线程正在等待项目,这与{@code peek}操作的相反类比。
 * 
 *  <p>与其他阻塞队列一样,{@code TransferQueue}可能具有容量限制。如果是,则尝试的传送操作可以最初阻止等待可用空间,和/或随后阻止等待消费者的接收。
 * 注意,在具有零容量的队列中,例如{@link SynchronousQueue},{@code put}和{@code transfer}是有效的同义词。
 * 
 *  <p>此接口是的成员
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 *  Java集合框架</a>。
 * 
 * 
 * @since 1.7
 * @author Doug Lea
 * @param <E> the type of elements held in this collection
 */
public interface TransferQueue<E> extends BlockingQueue<E> {
    /**
     * Transfers the element to a waiting consumer immediately, if possible.
     *
     * <p>More precisely, transfers the specified element immediately
     * if there exists a consumer already waiting to receive it (in
     * {@link #take} or timed {@link #poll(long,TimeUnit) poll}),
     * otherwise returning {@code false} without enqueuing the element.
     *
     * <p>
     * 如果可能,立即将元素传输给等待的消费者。
     * 
     *  <p>更准确地说,如果存在已经等待接收它的消费者(在{@link #take}或定时{@link #poll(long,TimeUnit)poll}),则立即传输指定的元素,否则返回{@code false}
     * ,而不将元素排队。
     * 
     * 
     * @param e the element to transfer
     * @return {@code true} if the element was transferred, else
     *         {@code false}
     * @throws ClassCastException if the class of the specified element
     *         prevents it from being added to this queue
     * @throws NullPointerException if the specified element is null
     * @throws IllegalArgumentException if some property of the specified
     *         element prevents it from being added to this queue
     */
    boolean tryTransfer(E e);

    /**
     * Transfers the element to a consumer, waiting if necessary to do so.
     *
     * <p>More precisely, transfers the specified element immediately
     * if there exists a consumer already waiting to receive it (in
     * {@link #take} or timed {@link #poll(long,TimeUnit) poll}),
     * else waits until the element is received by a consumer.
     *
     * <p>
     *  将元素传输给消费者,如果必要,请等待。
     * 
     *  <p>更精确地说,如果存在已经等待接收它的消费者(在{@link #take}或定时{@link #poll(long,TimeUnit)poll}),则立即传输指定的元素,否则等待直到元素由消费者接
     * 收。
     * 
     * 
     * @param e the element to transfer
     * @throws InterruptedException if interrupted while waiting,
     *         in which case the element is not left enqueued
     * @throws ClassCastException if the class of the specified element
     *         prevents it from being added to this queue
     * @throws NullPointerException if the specified element is null
     * @throws IllegalArgumentException if some property of the specified
     *         element prevents it from being added to this queue
     */
    void transfer(E e) throws InterruptedException;

    /**
     * Transfers the element to a consumer if it is possible to do so
     * before the timeout elapses.
     *
     * <p>More precisely, transfers the specified element immediately
     * if there exists a consumer already waiting to receive it (in
     * {@link #take} or timed {@link #poll(long,TimeUnit) poll}),
     * else waits until the element is received by a consumer,
     * returning {@code false} if the specified wait time elapses
     * before the element can be transferred.
     *
     * <p>
     *  如果在超时时间之前可以这样做,将元素传递给消费者。
     * 
     *  <p>更精确地说,如果存在已经等待接收它的消费者(在{@link #take}或定时{@link #poll(long,TimeUnit)poll}),则立即传输指定的元素,否则等待直到元素由消费者接
     * 收,如果在元素可以传输之前经过了指定的等待时间,则返回{@code false}。
     * 
     * 
     * @param e the element to transfer
     * @param timeout how long to wait before giving up, in units of
     *        {@code unit}
     * @param unit a {@code TimeUnit} determining how to interpret the
     *        {@code timeout} parameter
     * @return {@code true} if successful, or {@code false} if
     *         the specified waiting time elapses before completion,
     *         in which case the element is not left enqueued
     * @throws InterruptedException if interrupted while waiting,
     *         in which case the element is not left enqueued
     * @throws ClassCastException if the class of the specified element
     *         prevents it from being added to this queue
     * @throws NullPointerException if the specified element is null
     * @throws IllegalArgumentException if some property of the specified
     *         element prevents it from being added to this queue
     */
    boolean tryTransfer(E e, long timeout, TimeUnit unit)
        throws InterruptedException;

    /**
     * Returns {@code true} if there is at least one consumer waiting
     * to receive an element via {@link #take} or
     * timed {@link #poll(long,TimeUnit) poll}.
     * The return value represents a momentary state of affairs.
     *
     * <p>
     *  如果至少有一个使用者等待通过{@link #take}或定时{@link #poll(long,TimeUnit)投票}接收元素,则返回{@code true}。返回值表示事务的瞬时状态。
     * 
     * 
     * @return {@code true} if there is at least one waiting consumer
     */
    boolean hasWaitingConsumer();

    /**
     * Returns an estimate of the number of consumers waiting to
     * receive elements via {@link #take} or timed
     * {@link #poll(long,TimeUnit) poll}.  The return value is an
     * approximation of a momentary state of affairs, that may be
     * inaccurate if consumers have completed or given up waiting.
     * The value may be useful for monitoring and heuristics, but
     * not for synchronization control.  Implementations of this
     * method are likely to be noticeably slower than those for
     * {@link #hasWaitingConsumer}.
     *
     * <p>
     * 返回等待通过{@link #take}或定时{@link #poll(long,TimeUnit)投票}接收元素的消费者数量的估计值。返回值是瞬时状态的近似值,如果消费者完成或放弃等待,则可能不准确。
     * 该值可能对监视和启发式有用,但不适用于同步控制。此方法的实现可能明显慢于{@link #hasWaitingConsumer}的实现。
     * 
     * @return the number of consumers waiting to receive elements
     */
    int getWaitingConsumerCount();
}
