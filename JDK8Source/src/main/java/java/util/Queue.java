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

package java.util;

/**
 * A collection designed for holding elements prior to processing.
 * Besides basic {@link java.util.Collection Collection} operations,
 * queues provide additional insertion, extraction, and inspection
 * operations.  Each of these methods exists in two forms: one throws
 * an exception if the operation fails, the other returns a special
 * value (either {@code null} or {@code false}, depending on the
 * operation).  The latter form of the insert operation is designed
 * specifically for use with capacity-restricted {@code Queue}
 * implementations; in most implementations, insert operations cannot
 * fail.
 *
 * <table BORDER CELLPADDING=3 CELLSPACING=1>
 * <caption>Summary of Queue methods</caption>
 *  <tr>
 *    <td></td>
 *    <td ALIGN=CENTER><em>Throws exception</em></td>
 *    <td ALIGN=CENTER><em>Returns special value</em></td>
 *  </tr>
 *  <tr>
 *    <td><b>Insert</b></td>
 *    <td>{@link Queue#add add(e)}</td>
 *    <td>{@link Queue#offer offer(e)}</td>
 *  </tr>
 *  <tr>
 *    <td><b>Remove</b></td>
 *    <td>{@link Queue#remove remove()}</td>
 *    <td>{@link Queue#poll poll()}</td>
 *  </tr>
 *  <tr>
 *    <td><b>Examine</b></td>
 *    <td>{@link Queue#element element()}</td>
 *    <td>{@link Queue#peek peek()}</td>
 *  </tr>
 * </table>
 *
 * <p>Queues typically, but do not necessarily, order elements in a
 * FIFO (first-in-first-out) manner.  Among the exceptions are
 * priority queues, which order elements according to a supplied
 * comparator, or the elements' natural ordering, and LIFO queues (or
 * stacks) which order the elements LIFO (last-in-first-out).
 * Whatever the ordering used, the <em>head</em> of the queue is that
 * element which would be removed by a call to {@link #remove() } or
 * {@link #poll()}.  In a FIFO queue, all new elements are inserted at
 * the <em>tail</em> of the queue. Other kinds of queues may use
 * different placement rules.  Every {@code Queue} implementation
 * must specify its ordering properties.
 *
 * <p>The {@link #offer offer} method inserts an element if possible,
 * otherwise returning {@code false}.  This differs from the {@link
 * java.util.Collection#add Collection.add} method, which can fail to
 * add an element only by throwing an unchecked exception.  The
 * {@code offer} method is designed for use when failure is a normal,
 * rather than exceptional occurrence, for example, in fixed-capacity
 * (or &quot;bounded&quot;) queues.
 *
 * <p>The {@link #remove()} and {@link #poll()} methods remove and
 * return the head of the queue.
 * Exactly which element is removed from the queue is a
 * function of the queue's ordering policy, which differs from
 * implementation to implementation. The {@code remove()} and
 * {@code poll()} methods differ only in their behavior when the
 * queue is empty: the {@code remove()} method throws an exception,
 * while the {@code poll()} method returns {@code null}.
 *
 * <p>The {@link #element()} and {@link #peek()} methods return, but do
 * not remove, the head of the queue.
 *
 * <p>The {@code Queue} interface does not define the <i>blocking queue
 * methods</i>, which are common in concurrent programming.  These methods,
 * which wait for elements to appear or for space to become available, are
 * defined in the {@link java.util.concurrent.BlockingQueue} interface, which
 * extends this interface.
 *
 * <p>{@code Queue} implementations generally do not allow insertion
 * of {@code null} elements, although some implementations, such as
 * {@link LinkedList}, do not prohibit insertion of {@code null}.
 * Even in the implementations that permit it, {@code null} should
 * not be inserted into a {@code Queue}, as {@code null} is also
 * used as a special return value by the {@code poll} method to
 * indicate that the queue contains no elements.
 *
 * <p>{@code Queue} implementations generally do not define
 * element-based versions of methods {@code equals} and
 * {@code hashCode} but instead inherit the identity based versions
 * from class {@code Object}, because element-based equality is not
 * always well-defined for queues with the same elements but different
 * ordering properties.
 *
 *
 * <p>This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * <p>
 *  集合设计用于在处理之前保持元素。除了基本的{@link java.util.Collection Collection}操作,队列还提供额外的插入,提取和检查操作。
 * 这些方法中的每一种都以两种形式存在：一个在操作失败时抛出异常,另一个返回特殊值(根据操作,{@code null}或{@code false})。
 * 插入操作的后一种形式是专为使用容量限制的{@code Queue}实现而设计的;在大多数实现中,插入操作不会失败。
 * 
 * <table BORDER CELLPADDING=3 CELLSPACING=1>
 *  <caption>队列方法摘要</caption>
 * <tr>
 *  <td> </td> <td ALIGN = CENTER> </em> </td> <td ALIGN = CENTER> <em>返回特殊值</em>
 * </tr>
 * <tr>
 *  <td> <b>插入</b> </td> <td> {@ link queue#add add(e)} </td> <td> {@ link Queue#offer offer(e)} </td >。
 * </tr>
 * <tr>
 *  <td> <b>删除</b> </td> <td> {@ link Queue#remove remove()} </td> <td> {@ link Queue#poll poll()} </td>
 * 。
 * </tr>
 * <tr>
 *  <td> <b>检查</b> </td> <td> {@ link Queue#element element()} </td> <td> {@ link Queue#peek peek()} </td>
 * 。
 * </tr>
 * </table>
 * 
 * <p>队列通常(但不一定)以FIFO(先进先出)方式对元素排序。在例外中,优先级队列,其根据提供的比较器或元素的自然排序的顺序元素,以及排序元素LIFO(后进先出)的LIFO队列(或堆栈)。
 * 无论使用什么排序,队列的<em>头</em>都是通过调用{@link #remove()}或{@link #poll()}删除的元素。在FIFO队列中,所有新元素都插入到队列的<em>尾</em>。
 * 其他类型的队列可以使用不同的布局规则。每个{@code Queue}实施必须指定其排序属性。
 * 
 *  <p> {@link #offer offer}方法尽可能插入元素,否则返回{@code false}。
 * 这与{@link java.util.Collection#add Collection.add}方法不同,后者可能无法仅通过抛出未检查的异常来添加元素。
 *  {@code offer}方法被设计用于当故障是例如在固定容量(或"有界")队列中的正常而不是异常事件时使用。
 * 
 * <p> {@link #remove()}和{@link #poll()}方法移除并返回队列头。从队列中删除哪个元素是队列的排序策略的函数,其从实现到实现是不同的。
 *  {@code remove()}和{@code poll()}方法的区别仅在于队列为空时的行为：{@code remove()}方法引发异常,而{@code poll方法返回{@code null}。
 * <p> {@link #remove()}和{@link #poll()}方法移除并返回队列头。从队列中删除哪个元素是队列的排序策略的函数,其从实现到实现是不同的。
 * 
 * 
 * @see java.util.Collection
 * @see LinkedList
 * @see PriorityQueue
 * @see java.util.concurrent.LinkedBlockingQueue
 * @see java.util.concurrent.BlockingQueue
 * @see java.util.concurrent.ArrayBlockingQueue
 * @see java.util.concurrent.LinkedBlockingQueue
 * @see java.util.concurrent.PriorityBlockingQueue
 * @since 1.5
 * @author Doug Lea
 * @param <E> the type of elements held in this collection
 */
public interface Queue<E> extends Collection<E> {
    /**
     * Inserts the specified element into this queue if it is possible to do so
     * immediately without violating capacity restrictions, returning
     * {@code true} upon success and throwing an {@code IllegalStateException}
     * if no space is currently available.
     *
     * <p>
     *  <p> {@link #element()}和{@link #peek()}方法返回,但不删除,队列头。
     * 
     *  <p> {@code Queue}接口未定义<i>阻塞队列方法</i>,这在并发编程中很常见。
     * 这些方法等待元素出现或空间变得可用,在{@link java.util.concurrent.BlockingQueue}接口中定义,这扩展了此接口。
     * 
     *  <p> {@ code Queue}实现通常不允许插入{@code null}元素,但某些实现(例如{@link LinkedList})不会禁止插入{@code null}。
     * 即使在允许它的实现中,{@code null}也不应该插入{@code Queue},因为{@code null}也被{@code poll}方法用作特殊返回值,表示队列不包含任何元素。
     * 
     * <p> {@ code Queue}实现通常不定义方法{@code equals}和{@code hashCode}的基于元素的版本,而是继承类{@code Object}的基于身份的版本,因为基于元素
     * 的等式并不总是为具有相同元素但排序属性不同的队列定义良好。
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
     * @throws NullPointerException if the specified element is null and
     *         this queue does not permit null elements
     * @throws IllegalArgumentException if some property of this element
     *         prevents it from being added to this queue
     */
    boolean add(E e);

    /**
     * Inserts the specified element into this queue if it is possible to do
     * so immediately without violating capacity restrictions.
     * When using a capacity-restricted queue, this method is generally
     * preferable to {@link #add}, which can fail to insert an element only
     * by throwing an exception.
     *
     * <p>
     *  如果可以在不违反容量限制的情况下立即执行此操作,则将指定的元素插入到此队列中,如果成功则返回{@code true},如果当前没有可用空间,则抛出{@code IllegalStateException}
     * 。
     * 
     * 
     * @param e the element to add
     * @return {@code true} if the element was added to this queue, else
     *         {@code false}
     * @throws ClassCastException if the class of the specified element
     *         prevents it from being added to this queue
     * @throws NullPointerException if the specified element is null and
     *         this queue does not permit null elements
     * @throws IllegalArgumentException if some property of this element
     *         prevents it from being added to this queue
     */
    boolean offer(E e);

    /**
     * Retrieves and removes the head of this queue.  This method differs
     * from {@link #poll poll} only in that it throws an exception if this
     * queue is empty.
     *
     * <p>
     *  如果可以在不违反容量限制的情况下立即执行,将指定的元素插入此队列。当使用容量限制队列时,此方法通常优先于{@link #add},这可能无法仅通过抛出异常插入元素。
     * 
     * 
     * @return the head of this queue
     * @throws NoSuchElementException if this queue is empty
     */
    E remove();

    /**
     * Retrieves and removes the head of this queue,
     * or returns {@code null} if this queue is empty.
     *
     * <p>
     *  检索并删除此队列的头。此方法与{@link #poll poll}不同之处仅在于,如果此队列为空,它会抛出异常。
     * 
     * 
     * @return the head of this queue, or {@code null} if this queue is empty
     */
    E poll();

    /**
     * Retrieves, but does not remove, the head of this queue.  This method
     * differs from {@link #peek peek} only in that it throws an exception
     * if this queue is empty.
     *
     * <p>
     *  检索并删除此队列的头部,如果此队列为空,则返回{@code null}。
     * 
     * 
     * @return the head of this queue
     * @throws NoSuchElementException if this queue is empty
     */
    E element();

    /**
     * Retrieves, but does not remove, the head of this queue,
     * or returns {@code null} if this queue is empty.
     *
     * <p>
     *  检索,但不删除,这个队列的头。此方法与{@link #peek peek}的区别仅在于,如果此队列为空,它会抛出异常。
     * 
     * 
     * @return the head of this queue, or {@code null} if this queue is empty
     */
    E peek();
}
