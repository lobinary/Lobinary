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

import java.util.Collection;
import java.util.Queue;

/**
 * A {@link java.util.Queue} that additionally supports operations
 * that wait for the queue to become non-empty when retrieving an
 * element, and wait for space to become available in the queue when
 * storing an element.
 *
 * <p>{@code BlockingQueue} methods come in four forms, with different ways
 * of handling operations that cannot be satisfied immediately, but may be
 * satisfied at some point in the future:
 * one throws an exception, the second returns a special value (either
 * {@code null} or {@code false}, depending on the operation), the third
 * blocks the current thread indefinitely until the operation can succeed,
 * and the fourth blocks for only a given maximum time limit before giving
 * up.  These methods are summarized in the following table:
 *
 * <table BORDER CELLPADDING=3 CELLSPACING=1>
 * <caption>Summary of BlockingQueue methods</caption>
 *  <tr>
 *    <td></td>
 *    <td ALIGN=CENTER><em>Throws exception</em></td>
 *    <td ALIGN=CENTER><em>Special value</em></td>
 *    <td ALIGN=CENTER><em>Blocks</em></td>
 *    <td ALIGN=CENTER><em>Times out</em></td>
 *  </tr>
 *  <tr>
 *    <td><b>Insert</b></td>
 *    <td>{@link #add add(e)}</td>
 *    <td>{@link #offer offer(e)}</td>
 *    <td>{@link #put put(e)}</td>
 *    <td>{@link #offer(Object, long, TimeUnit) offer(e, time, unit)}</td>
 *  </tr>
 *  <tr>
 *    <td><b>Remove</b></td>
 *    <td>{@link #remove remove()}</td>
 *    <td>{@link #poll poll()}</td>
 *    <td>{@link #take take()}</td>
 *    <td>{@link #poll(long, TimeUnit) poll(time, unit)}</td>
 *  </tr>
 *  <tr>
 *    <td><b>Examine</b></td>
 *    <td>{@link #element element()}</td>
 *    <td>{@link #peek peek()}</td>
 *    <td><em>not applicable</em></td>
 *    <td><em>not applicable</em></td>
 *  </tr>
 * </table>
 *
 * <p>A {@code BlockingQueue} does not accept {@code null} elements.
 * Implementations throw {@code NullPointerException} on attempts
 * to {@code add}, {@code put} or {@code offer} a {@code null}.  A
 * {@code null} is used as a sentinel value to indicate failure of
 * {@code poll} operations.
 *
 * <p>A {@code BlockingQueue} may be capacity bounded. At any given
 * time it may have a {@code remainingCapacity} beyond which no
 * additional elements can be {@code put} without blocking.
 * A {@code BlockingQueue} without any intrinsic capacity constraints always
 * reports a remaining capacity of {@code Integer.MAX_VALUE}.
 *
 * <p>{@code BlockingQueue} implementations are designed to be used
 * primarily for producer-consumer queues, but additionally support
 * the {@link java.util.Collection} interface.  So, for example, it is
 * possible to remove an arbitrary element from a queue using
 * {@code remove(x)}. However, such operations are in general
 * <em>not</em> performed very efficiently, and are intended for only
 * occasional use, such as when a queued message is cancelled.
 *
 * <p>{@code BlockingQueue} implementations are thread-safe.  All
 * queuing methods achieve their effects atomically using internal
 * locks or other forms of concurrency control. However, the
 * <em>bulk</em> Collection operations {@code addAll},
 * {@code containsAll}, {@code retainAll} and {@code removeAll} are
 * <em>not</em> necessarily performed atomically unless specified
 * otherwise in an implementation. So it is possible, for example, for
 * {@code addAll(c)} to fail (throwing an exception) after adding
 * only some of the elements in {@code c}.
 *
 * <p>A {@code BlockingQueue} does <em>not</em> intrinsically support
 * any kind of &quot;close&quot; or &quot;shutdown&quot; operation to
 * indicate that no more items will be added.  The needs and usage of
 * such features tend to be implementation-dependent. For example, a
 * common tactic is for producers to insert special
 * <em>end-of-stream</em> or <em>poison</em> objects, that are
 * interpreted accordingly when taken by consumers.
 *
 * <p>
 * Usage example, based on a typical producer-consumer scenario.
 * Note that a {@code BlockingQueue} can safely be used with multiple
 * producers and multiple consumers.
 *  <pre> {@code
 * class Producer implements Runnable {
 *   private final BlockingQueue queue;
 *   Producer(BlockingQueue q) { queue = q; }
 *   public void run() {
 *     try {
 *       while (true) { queue.put(produce()); }
 *     } catch (InterruptedException ex) { ... handle ...}
 *   }
 *   Object produce() { ... }
 * }
 *
 * class Consumer implements Runnable {
 *   private final BlockingQueue queue;
 *   Consumer(BlockingQueue q) { queue = q; }
 *   public void run() {
 *     try {
 *       while (true) { consume(queue.take()); }
 *     } catch (InterruptedException ex) { ... handle ...}
 *   }
 *   void consume(Object x) { ... }
 * }
 *
 * class Setup {
 *   void main() {
 *     BlockingQueue q = new SomeQueueImplementation();
 *     Producer p = new Producer(q);
 *     Consumer c1 = new Consumer(q);
 *     Consumer c2 = new Consumer(q);
 *     new Thread(p).start();
 *     new Thread(c1).start();
 *     new Thread(c2).start();
 *   }
 * }}</pre>
 *
 * <p>Memory consistency effects: As with other concurrent
 * collections, actions in a thread prior to placing an object into a
 * {@code BlockingQueue}
 * <a href="package-summary.html#MemoryVisibility"><i>happen-before</i></a>
 * actions subsequent to the access or removal of that element from
 * the {@code BlockingQueue} in another thread.
 *
 * <p>This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * <p>
 *  {@link java.util.Queue}还支持在检索元素时等待队列变为非空的操作,并在存储元素时等待队列中的空间可用。
 * 
 *  <p> {@ code BlockingQueue}方法有四种形式,有不同的处理操作的方式,不能立即满足,但可能在未来的某个时刻满足：一个抛出异常,第二个返回一个特殊值{@code null}或{@code false}
 * ,取决于操作),第三个块无限期地阻塞当前线程,直到操作可以成功,第四个块仅在给定的最大时间限制之前放弃。
 * 这些方法总结在下表中：。
 * 
 * <table BORDER CELLPADDING=3 CELLSPACING=1>
 *  <caption> BlockingQueue方法的摘要</caption>
 * <tr>
 *  <td> </td> <td ALIGN = CENTER> <em>抛出异常</em> </td> <td ALIGN = CENTER> <em>特殊值</em> </td> <td ALIGN = >
 *  <em>阻止</em> </td> <td ALIGN = CENTER> <em>超时</em> </td>。
 * </tr>
 * <tr>
 *  <td> <b>插入</b> </td> <td> {@ link #add add(e)} </td> <td> {@ link #offer offer(e)} </td> < td> {@ link #put put(e)}
 *  </td> <td> {@ link #offer(Object,long,TimeUnit)offer(e,time,unit)} </td>。
 * </tr>
 * <tr>
 * <td> <b>删除</b> </td> <td> {@ link #remove remove()} </td> <td> {@ link #poll poll()} </td> <td> {@link #take take()}
 *  </td> <td> {@ link #poll(long,TimeUnit)poll(time,unit)} </td>。
 * </tr>
 * <tr>
 *  <td> <b>检查</b> </td> <td> {@ link #element element()} </td> <td> {@ link #peek peek()} </td> <td> <em>
 * 不适用</em> </td> <td> <em>不适用</em> </td>。
 * </tr>
 * </table>
 * 
 *  <p> {@code BlockingQueue}不接受{@code null}元素。
 * 实施在{@code add},{@code put}或{@code offer} {@code null}尝试时抛出{@code NullPointerException}。
 *  {@code null}用作哨兵值,表示{@code poll}操作失败。
 * 
 *  <p> {@code BlockingQueue}可能有容量限制。
 * 在任何给定的时间,它可以有一个{@code remainingCapacity},超过这个没有额外的元素可以{@code put}没有阻塞。
 * 没有任何内在容量限制的{@code BlockingQueue}总是报告{@code Integer.MAX_VALUE}的剩余容量。
 * 
 *  <p> {@ code BlockingQueue}实现旨在主要用于生产者 - 消费者队列,但另外还支持{@link java.util.Collection}接口。
 * 因此,例如,可以使用{@code remove(x)}从队列中删除任意元素。然而,这样的操作通常不是非常有效地执行,并且仅用于偶尔使用,例如当排队的消息被取消时。
 * 
 * <p> {@ code BlockingQueue}实现是线程安全的。所有排队方法使用内部锁或其他形式的并发控制原子地实现其效果。
 * 但是,<em> </em>收集操作{@code addAll},{@code containsAll},{@code retainAll}和{@code removeAll}否则在实现中。
 * 因此,例如,在{@code c}中只添加一些元素后,{@code addAll(c)}可能失败(抛出异常)。
 * 
 *  <p> {@code BlockingQueue}不会<em> </em>本质上支持任何类型的"关闭"或"关闭"操作以指示不再添加项目。这些特征的需要和使用倾向于取决于实现。
 * 例如,一个常见的策略是生产者插入特殊的<end> of-stream </em>或<em>中毒</em>对象,这些对象在消费者接受时被相应地解释。
 * 
 * <p>
 *  使用示例,基于典型的生产者 - 消费者场景。请注意,{@code BlockingQueue}可以安全地用于多个制作者和多个用户。
 *  <pre> {@code class Producer implements Runnable {private final BlockingQueue queue;生产者(BlockingQueue q){queue = q; }
 *  public void run(){try {while(true){queue.put(produce()); }} catch(InterruptedException ex){... handle ...}
 * } Object produce(){...}}。
 *  使用示例,基于典型的生产者 - 消费者场景。请注意,{@code BlockingQueue}可以安全地用于多个制作者和多个用户。
 * 
 * 类Consumer实现Runnable {private final BlockingQueue queue; Consumer(BlockingQueue q){queue = q; } public
 *  void run(){try {while(true){consume(queue.take()); }} catch(InterruptedException ex){... handle ...}
 * 
 * @since 1.5
 * @author Doug Lea
 * @param <E> the type of elements held in this collection
 */
public interface BlockingQueue<E> extends Queue<E> {
    /**
     * Inserts the specified element into this queue if it is possible to do
     * so immediately without violating capacity restrictions, returning
     * {@code true} upon success and throwing an
     * {@code IllegalStateException} if no space is currently available.
     * When using a capacity-restricted queue, it is generally preferable to
     * use {@link #offer(Object) offer}.
     *
     * <p>
     * } void consume(Object x){...}}。
     * 
     *  class Setup {void main(){BlockingQueue q = new SomeQueueImplementation();生产者p =新生产者(q);消费者c1 =新消费者(q);消费者c2 =新消费者(q);新线程(p).start();新线程(c1).start();新线程(c2).start(); }
     * }} </pre>。
     * 
     *  <p>内存一致性效果：与其他并发集合一样,在将对象置于{@code BlockingQueue} <a href="package-summary.html#MemoryVisibility"> <i>
     * 发生在之前< / i> </a>从另一个线程中的{@code BlockingQueue}中访问或删除该元素后的操作。
     * 
     *  <p>此接口是的成员
     * <a href="{@docRoot}/../technotes/guides/collections/index.html">
     *  Java集合框架</a>。
     * 
     * 
     * @param e the element to add
     * @return {@code true} (as specified by {@link Collection#add})
     * @throws IllegalStateException if the element cannot be added at this
     *         time due to capacity restrictions
     * @throws ClassCastException if the class of the specified element
     *         prevents it from being added to this queue
     * @throws NullPointerException if the specified element is null
     * @throws IllegalArgumentException if some property of the specified
     *         element prevents it from being added to this queue
     */
    boolean add(E e);

    /**
     * Inserts the specified element into this queue if it is possible to do
     * so immediately without violating capacity restrictions, returning
     * {@code true} upon success and {@code false} if no space is currently
     * available.  When using a capacity-restricted queue, this method is
     * generally preferable to {@link #add}, which can fail to insert an
     * element only by throwing an exception.
     *
     * <p>
     *  如果可以在不违反容量限制的情况下立即执行此操作,则将指定的元素插入到此队列中,如果成功则返回{@code true},如果当前没有可用空间,则抛出{@code IllegalStateException}
     * 。
     * 当使用容量限制队列时,通常优选使用{@link #offer(Object)offer}。
     * 
     * 
     * @param e the element to add
     * @return {@code true} if the element was added to this queue, else
     *         {@code false}
     * @throws ClassCastException if the class of the specified element
     *         prevents it from being added to this queue
     * @throws NullPointerException if the specified element is null
     * @throws IllegalArgumentException if some property of the specified
     *         element prevents it from being added to this queue
     */
    boolean offer(E e);

    /**
     * Inserts the specified element into this queue, waiting if necessary
     * for space to become available.
     *
     * <p>
     * 如果可以在不违反容量限制的情况下立即执行此操作,则将指定的元素插入到此队列中,如果成功则返回{@code true},如果当前没有可用空间则返回{@code false}。
     * 当使用容量限制队列时,此方法通常优先于{@link #add},这可能无法仅通过抛出异常插入元素。
     * 
     * 
     * @param e the element to add
     * @throws InterruptedException if interrupted while waiting
     * @throws ClassCastException if the class of the specified element
     *         prevents it from being added to this queue
     * @throws NullPointerException if the specified element is null
     * @throws IllegalArgumentException if some property of the specified
     *         element prevents it from being added to this queue
     */
    void put(E e) throws InterruptedException;

    /**
     * Inserts the specified element into this queue, waiting up to the
     * specified wait time if necessary for space to become available.
     *
     * <p>
     *  将指定的元素插入此队列,如果必要,等待空间可用。
     * 
     * 
     * @param e the element to add
     * @param timeout how long to wait before giving up, in units of
     *        {@code unit}
     * @param unit a {@code TimeUnit} determining how to interpret the
     *        {@code timeout} parameter
     * @return {@code true} if successful, or {@code false} if
     *         the specified waiting time elapses before space is available
     * @throws InterruptedException if interrupted while waiting
     * @throws ClassCastException if the class of the specified element
     *         prevents it from being added to this queue
     * @throws NullPointerException if the specified element is null
     * @throws IllegalArgumentException if some property of the specified
     *         element prevents it from being added to this queue
     */
    boolean offer(E e, long timeout, TimeUnit unit)
        throws InterruptedException;

    /**
     * Retrieves and removes the head of this queue, waiting if necessary
     * until an element becomes available.
     *
     * <p>
     *  将指定的元素插入此队列,等待指定的等待时间(如果需要),以使空间可用。
     * 
     * 
     * @return the head of this queue
     * @throws InterruptedException if interrupted while waiting
     */
    E take() throws InterruptedException;

    /**
     * Retrieves and removes the head of this queue, waiting up to the
     * specified wait time if necessary for an element to become available.
     *
     * <p>
     *  检索并删除此队列的头,如果必要,等待元素可用。
     * 
     * 
     * @param timeout how long to wait before giving up, in units of
     *        {@code unit}
     * @param unit a {@code TimeUnit} determining how to interpret the
     *        {@code timeout} parameter
     * @return the head of this queue, or {@code null} if the
     *         specified waiting time elapses before an element is available
     * @throws InterruptedException if interrupted while waiting
     */
    E poll(long timeout, TimeUnit unit)
        throws InterruptedException;

    /**
     * Returns the number of additional elements that this queue can ideally
     * (in the absence of memory or resource constraints) accept without
     * blocking, or {@code Integer.MAX_VALUE} if there is no intrinsic
     * limit.
     *
     * <p>Note that you <em>cannot</em> always tell if an attempt to insert
     * an element will succeed by inspecting {@code remainingCapacity}
     * because it may be the case that another thread is about to
     * insert or remove an element.
     *
     * <p>
     *  检索并删除此队列的头,等待指定的等待时间(如果需要),以使元素可用。
     * 
     * 
     * @return the remaining capacity
     */
    int remainingCapacity();

    /**
     * Removes a single instance of the specified element from this queue,
     * if it is present.  More formally, removes an element {@code e} such
     * that {@code o.equals(e)}, if this queue contains one or more such
     * elements.
     * Returns {@code true} if this queue contained the specified element
     * (or equivalently, if this queue changed as a result of the call).
     *
     * <p>
     *  返回此队列可理想地(在没有内存或资源约束的情况下)接受而无需阻塞的附加元素数,如果没有内在限制,则返回{@code Integer.MAX_VALUE}。
     * 
     *  <p>请注意,您<em>不能</em>总是通过检查{@code remainingCapacity}来确定是否尝试插入元素将会成功,因为可能是另一个线程要插入或删除元素。
     * 
     * 
     * @param o element to be removed from this queue, if present
     * @return {@code true} if this queue changed as a result of the call
     * @throws ClassCastException if the class of the specified element
     *         is incompatible with this queue
     *         (<a href="../Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null
     *         (<a href="../Collection.html#optional-restrictions">optional</a>)
     */
    boolean remove(Object o);

    /**
     * Returns {@code true} if this queue contains the specified element.
     * More formally, returns {@code true} if and only if this queue contains
     * at least one element {@code e} such that {@code o.equals(e)}.
     *
     * <p>
     * 从此队列中删除指定元素的单个实例(如果存在)。更正式地,如果此队列包含一个或多个这样的元素,则删除{@code e} {@code o.equals(e)}的元素。
     * 如果此队列包含指定的元素(或等效地,如果此队列作为调用的结果而更改),则返回{@code true}。
     * 
     * 
     * @param o object to be checked for containment in this queue
     * @return {@code true} if this queue contains the specified element
     * @throws ClassCastException if the class of the specified element
     *         is incompatible with this queue
     *         (<a href="../Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null
     *         (<a href="../Collection.html#optional-restrictions">optional</a>)
     */
    public boolean contains(Object o);

    /**
     * Removes all available elements from this queue and adds them
     * to the given collection.  This operation may be more
     * efficient than repeatedly polling this queue.  A failure
     * encountered while attempting to add elements to
     * collection {@code c} may result in elements being in neither,
     * either or both collections when the associated exception is
     * thrown.  Attempts to drain a queue to itself result in
     * {@code IllegalArgumentException}. Further, the behavior of
     * this operation is undefined if the specified collection is
     * modified while the operation is in progress.
     *
     * <p>
     *  如果此队列包含指定的元素,则返回{@code true}。更正式地说,当且仅当此队列包含至少一个{@code e}元素{@code o.equals(e)}时,返回{@code true}。
     * 
     * 
     * @param c the collection to transfer elements into
     * @return the number of elements transferred
     * @throws UnsupportedOperationException if addition of elements
     *         is not supported by the specified collection
     * @throws ClassCastException if the class of an element of this queue
     *         prevents it from being added to the specified collection
     * @throws NullPointerException if the specified collection is null
     * @throws IllegalArgumentException if the specified collection is this
     *         queue, or some property of an element of this queue prevents
     *         it from being added to the specified collection
     */
    int drainTo(Collection<? super E> c);

    /**
     * Removes at most the given number of available elements from
     * this queue and adds them to the given collection.  A failure
     * encountered while attempting to add elements to
     * collection {@code c} may result in elements being in neither,
     * either or both collections when the associated exception is
     * thrown.  Attempts to drain a queue to itself result in
     * {@code IllegalArgumentException}. Further, the behavior of
     * this operation is undefined if the specified collection is
     * modified while the operation is in progress.
     *
     * <p>
     *  从此队列中删除所有可用元素,并将它们添加到给定集合。此操作可能比重复轮询此队列更有效。尝试向集合{@code c}添加元素时遇到的失败可能会导致在抛出相关联的异常时,元素既不在集合中,也不在集合中。
     * 尝试排队一个队列本身导致{@code IllegalArgumentException}。此外,如果在操作正在进行时修改指定的集合,则此操作的行为是未定义的。
     * 
     * 
     * @param c the collection to transfer elements into
     * @param maxElements the maximum number of elements to transfer
     * @return the number of elements transferred
     * @throws UnsupportedOperationException if addition of elements
     *         is not supported by the specified collection
     * @throws ClassCastException if the class of an element of this queue
     *         prevents it from being added to the specified collection
     * @throws NullPointerException if the specified collection is null
     * @throws IllegalArgumentException if the specified collection is this
     *         queue, or some property of an element of this queue prevents
     *         it from being added to the specified collection
     */
    int drainTo(Collection<? super E> c, int maxElements);
}
