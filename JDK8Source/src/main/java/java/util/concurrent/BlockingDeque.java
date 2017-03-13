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
 *  由Doug Lea在JCP JSR-166专家组成员的帮助下撰写,并发布到公共领域,如http：// creativecommonsorg / publicdomain / zero / 10 /
 * 
 */

package java.util.concurrent;
import java.util.*;

/**
 * A {@link Deque} that additionally supports blocking operations that wait
 * for the deque to become non-empty when retrieving an element, and wait for
 * space to become available in the deque when storing an element.
 *
 * <p>{@code BlockingDeque} methods come in four forms, with different ways
 * of handling operations that cannot be satisfied immediately, but may be
 * satisfied at some point in the future:
 * one throws an exception, the second returns a special value (either
 * {@code null} or {@code false}, depending on the operation), the third
 * blocks the current thread indefinitely until the operation can succeed,
 * and the fourth blocks for only a given maximum time limit before giving
 * up.  These methods are summarized in the following table:
 *
 * <table BORDER CELLPADDING=3 CELLSPACING=1>
 * <caption>Summary of BlockingDeque methods</caption>
 *  <tr>
 *    <td ALIGN=CENTER COLSPAN = 5> <b>First Element (Head)</b></td>
 *  </tr>
 *  <tr>
 *    <td></td>
 *    <td ALIGN=CENTER><em>Throws exception</em></td>
 *    <td ALIGN=CENTER><em>Special value</em></td>
 *    <td ALIGN=CENTER><em>Blocks</em></td>
 *    <td ALIGN=CENTER><em>Times out</em></td>
 *  </tr>
 *  <tr>
 *    <td><b>Insert</b></td>
 *    <td>{@link #addFirst addFirst(e)}</td>
 *    <td>{@link #offerFirst(Object) offerFirst(e)}</td>
 *    <td>{@link #putFirst putFirst(e)}</td>
 *    <td>{@link #offerFirst(Object, long, TimeUnit) offerFirst(e, time, unit)}</td>
 *  </tr>
 *  <tr>
 *    <td><b>Remove</b></td>
 *    <td>{@link #removeFirst removeFirst()}</td>
 *    <td>{@link #pollFirst pollFirst()}</td>
 *    <td>{@link #takeFirst takeFirst()}</td>
 *    <td>{@link #pollFirst(long, TimeUnit) pollFirst(time, unit)}</td>
 *  </tr>
 *  <tr>
 *    <td><b>Examine</b></td>
 *    <td>{@link #getFirst getFirst()}</td>
 *    <td>{@link #peekFirst peekFirst()}</td>
 *    <td><em>not applicable</em></td>
 *    <td><em>not applicable</em></td>
 *  </tr>
 *  <tr>
 *    <td ALIGN=CENTER COLSPAN = 5> <b>Last Element (Tail)</b></td>
 *  </tr>
 *  <tr>
 *    <td></td>
 *    <td ALIGN=CENTER><em>Throws exception</em></td>
 *    <td ALIGN=CENTER><em>Special value</em></td>
 *    <td ALIGN=CENTER><em>Blocks</em></td>
 *    <td ALIGN=CENTER><em>Times out</em></td>
 *  </tr>
 *  <tr>
 *    <td><b>Insert</b></td>
 *    <td>{@link #addLast addLast(e)}</td>
 *    <td>{@link #offerLast(Object) offerLast(e)}</td>
 *    <td>{@link #putLast putLast(e)}</td>
 *    <td>{@link #offerLast(Object, long, TimeUnit) offerLast(e, time, unit)}</td>
 *  </tr>
 *  <tr>
 *    <td><b>Remove</b></td>
 *    <td>{@link #removeLast() removeLast()}</td>
 *    <td>{@link #pollLast() pollLast()}</td>
 *    <td>{@link #takeLast takeLast()}</td>
 *    <td>{@link #pollLast(long, TimeUnit) pollLast(time, unit)}</td>
 *  </tr>
 *  <tr>
 *    <td><b>Examine</b></td>
 *    <td>{@link #getLast getLast()}</td>
 *    <td>{@link #peekLast peekLast()}</td>
 *    <td><em>not applicable</em></td>
 *    <td><em>not applicable</em></td>
 *  </tr>
 * </table>
 *
 * <p>Like any {@link BlockingQueue}, a {@code BlockingDeque} is thread safe,
 * does not permit null elements, and may (or may not) be
 * capacity-constrained.
 *
 * <p>A {@code BlockingDeque} implementation may be used directly as a FIFO
 * {@code BlockingQueue}. The methods inherited from the
 * {@code BlockingQueue} interface are precisely equivalent to
 * {@code BlockingDeque} methods as indicated in the following table:
 *
 * <table BORDER CELLPADDING=3 CELLSPACING=1>
 * <caption>Comparison of BlockingQueue and BlockingDeque methods</caption>
 *  <tr>
 *    <td ALIGN=CENTER> <b>{@code BlockingQueue} Method</b></td>
 *    <td ALIGN=CENTER> <b>Equivalent {@code BlockingDeque} Method</b></td>
 *  </tr>
 *  <tr>
 *    <td ALIGN=CENTER COLSPAN = 2> <b>Insert</b></td>
 *  </tr>
 *  <tr>
 *    <td>{@link #add(Object) add(e)}</td>
 *    <td>{@link #addLast(Object) addLast(e)}</td>
 *  </tr>
 *  <tr>
 *    <td>{@link #offer(Object) offer(e)}</td>
 *    <td>{@link #offerLast(Object) offerLast(e)}</td>
 *  </tr>
 *  <tr>
 *    <td>{@link #put(Object) put(e)}</td>
 *    <td>{@link #putLast(Object) putLast(e)}</td>
 *  </tr>
 *  <tr>
 *    <td>{@link #offer(Object, long, TimeUnit) offer(e, time, unit)}</td>
 *    <td>{@link #offerLast(Object, long, TimeUnit) offerLast(e, time, unit)}</td>
 *  </tr>
 *  <tr>
 *    <td ALIGN=CENTER COLSPAN = 2> <b>Remove</b></td>
 *  </tr>
 *  <tr>
 *    <td>{@link #remove() remove()}</td>
 *    <td>{@link #removeFirst() removeFirst()}</td>
 *  </tr>
 *  <tr>
 *    <td>{@link #poll() poll()}</td>
 *    <td>{@link #pollFirst() pollFirst()}</td>
 *  </tr>
 *  <tr>
 *    <td>{@link #take() take()}</td>
 *    <td>{@link #takeFirst() takeFirst()}</td>
 *  </tr>
 *  <tr>
 *    <td>{@link #poll(long, TimeUnit) poll(time, unit)}</td>
 *    <td>{@link #pollFirst(long, TimeUnit) pollFirst(time, unit)}</td>
 *  </tr>
 *  <tr>
 *    <td ALIGN=CENTER COLSPAN = 2> <b>Examine</b></td>
 *  </tr>
 *  <tr>
 *    <td>{@link #element() element()}</td>
 *    <td>{@link #getFirst() getFirst()}</td>
 *  </tr>
 *  <tr>
 *    <td>{@link #peek() peek()}</td>
 *    <td>{@link #peekFirst() peekFirst()}</td>
 *  </tr>
 * </table>
 *
 * <p>Memory consistency effects: As with other concurrent
 * collections, actions in a thread prior to placing an object into a
 * {@code BlockingDeque}
 * <a href="package-summary.html#MemoryVisibility"><i>happen-before</i></a>
 * actions subsequent to the access or removal of that element from
 * the {@code BlockingDeque} in another thread.
 *
 * <p>This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * <p>
 *  {@link Deque}另外支持阻塞操作,在检索元素时等待deque变为非空,并在存储元素时等待空间在deque中可用
 * 
 * <p> {@ code BlockingDeque}方法有四种形式,具有不能立即满足的处理操作的不同方式,但可以在将来的某个时间满足：一个抛出异常,第二个返回一个特殊值{@code null}或{@code false}
 * ,取决于操作),第三个块无限期地阻塞当前线程,直到操作可以成功,第四个块只在给定的最大时间限制之前放弃这些方法总结在下表：。
 * 
 * <table BORDER CELLPADDING=3 CELLSPACING=1>
 *  <caption> BlockingDeque方法的摘要</caption>
 * <tr>
 *  <td ALIGN = CENTER COLSPAN = 5> <b>第一个元素(头)</b> </td>
 * </tr>
 * <tr>
 * <td> </td> <td ALIGN = CENTER> <em>抛出异常</em> </td> <td ALIGN = CENTER> <em>特殊值</em> </td> <td ALIGN = >
 *  <em>阻止</em> </td> <td ALIGN = CENTER> <em>超时</em> </td>。
 * </tr>
 * <tr>
 *  <td> <b>插入</b> </td> <td> {@ link #addFirst addFirst(e)} </td> <td> {@ link #offerFirst(Object)offerFirst(e) td> <td> {@ link #putFirst putFirst(e)}
 *  </td> <td> {@ link #offerFirst(Object,long,TimeUnit)offerFirst(e,time,unit)} </td>。
 * </tr>
 * <tr>
 *  <td> <b>删除</b> </td> <td> {@ link #removeFirst removeFirst()} </td> <td> {@ link #pollFirst pollFirst()}
 *  </td> <td> {@link #takeFirst takeFirst()} </td> <td> {@ link #pollFirst(long,TimeUnit)pollFirst(time,unit)}
 *  </td>。
 * </tr>
 * <tr>
 *  <td> <b>检查</b> </td> <td> {@ link #getFirst getFirst()} </td> <td> {@ link #peekFirst peekFirst()} </td>
 *  <td> <em>不适用</em> </td> <td> <em>不适用</em> </td>。
 * </tr>
 * <tr>
 * <td ALIGN = CENTER COLSPAN = 5> <b>最后元素(尾)</b> </td>
 * </tr>
 * <tr>
 *  <td> </td> <td ALIGN = CENTER> <em>抛出异常</em> </td> <td ALIGN = CENTER> <em>特殊值</em> </td> <td ALIGN = >
 *  <em>阻止</em> </td> <td ALIGN = CENTER> <em>超时</em> </td>。
 * </tr>
 * <tr>
 *  <td> <b>插入</b> </td> <td> {@ link #addLast addLast(e)} </td> <td> {@ link #offerLast(Object)offerLast td> <td> {@ link #putLast putLast(e)}
 *  </td> <td> {@ link #offerLast(Object,long,TimeUnit)offerLast(e,time,unit)} </td>。
 * </tr>
 * <tr>
 *  <td> <b>删除</b> </td> <td> {@ link #removeLast()removeLast()} </td> <td> {@ link #pollLast()pollLast()}
 *  </td > <td> {@ link #takeLast takeLast()} </td> <td> {@ link #pollLast(long,TimeUnit)pollLast(time,unit)}
 *  </td>。
 * </tr>
 * <tr>
 * <td> <b>检查</b> </td> <td> {@ link #getLast getLast()} </td> <td> {@ link #peekLast peekLast()} </td> 
 * <td> <em>不适用</em> </td> <td> <em>不适用</em> </td>。
 * </tr>
 * </table>
 * 
 *  <p>与任何{@link BlockingQueue}一样,{@code BlockingDeque}是线程安全的,不允许空元素,并且可能(或可能不)容量受限
 * 
 *  <@> {@code BlockingDeque}实现可以直接用作FIFO {@code BlockingQueue}从{@code BlockingQueue}接口继承的方法与下表中所示的{@code BlockingDeque}
 * 方法完全相同：。
 * 
 * <table BORDER CELLPADDING=3 CELLSPACING=1>
 *  <caption> BlockingQueue和BlockingDeque方法的比较</caption>
 * <tr>
 *  <td ALIGN = CENTER> <b> {@ code BlockingQueue}方法</b> </td> <td ALIGN = CENTER> <b>等效的{@code BlockingDeque}
 * 方法</b>。
 * </tr>
 * <tr>
 * <td ALIGN = CENTER COLSPAN = 2> <b>插入</b> </td>
 * </tr>
 * <tr>
 *  <td> {@ link #add(Object)add(e)} </td> <td> {@ link #addLast(Object)addLast(e)} </td>
 * </tr>
 * <tr>
 *  <td> {@ link #offer(Object)offer(e)} </td> <td> {@ link #offerLast(Object)offerLast(e)} </td>
 * </tr>
 * <tr>
 *  <td> {@ link #put(Object)put(e)} </td> <td> {@ link #putLast(Object)putLast(e)} </td>
 * </tr>
 * <tr>
 *  <td> {@ link #offer(Object,long,TimeUnit)offer(e,time,unit)} </td> <td> {@ link #offerLast(Object,long,TimeUnit)offerLast(e,time,unit )}
 *  </td>。
 * </tr>
 * <tr>
 *  <td ALIGN = CENTER COLSPAN = 2> <b>删除</b> </td>
 * </tr>
 * <tr>
 *  <td> {@ link #remove()remove()} </td> <td> {@ link #removeFirst()removeFirst()} </td>
 * </tr>
 * <tr>
 *  <td> {@ link #poll()poll()} </td> <td> {@ link #pollFirst()pollFirst()} </td>
 * </tr>
 * <tr>
 *  <td> {@ link #take()take()} </td> <td> {@ link #takeFirst()takeFirst()} </td>
 * </tr>
 * <tr>
 * <td> {@ link #poll(long,TimeUnit)poll(time,unit)} </td> <td> {@ link #pollFirst(long,TimeUnit)pollFirst(time,unit)}
 *  </td>。
 * </tr>
 * <tr>
 *  <td ALIGN = CENTER COLSPAN = 2> <b>检查</b> </td>
 * </tr>
 * <tr>
 *  <td> {@ link #element()element()} </td> <td> {@ link #getFirst()getFirst()} </td>
 * </tr>
 * <tr>
 *  <td> {@ link #peek()peek()} </td> <td> {@ link #peekFirst()peekFirst()} </td>
 * </tr>
 * </table>
 * 
 *  <p>内存一致性效果：与其他并发集合一样,在将对象置于{@code BlockingDeque} <a href=\"package-summaryhtml#MemoryVisibility\"> <i>
 * 发生之前</i > </a>从另一个线程中的{@code BlockingDeque}访问或删除该元素后的操作。
 * 
 *  <p>此接口是的成员
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 *  Java集合框架</a>
 * 
 * 
 * @since 1.6
 * @author Doug Lea
 * @param <E> the type of elements held in this collection
 */
public interface BlockingDeque<E> extends BlockingQueue<E>, Deque<E> {
    /*
     * We have "diamond" multiple interface inheritance here, and that
     * introduces ambiguities.  Methods might end up with different
     * specs depending on the branch chosen by javadoc.  Thus a lot of
     * methods specs here are copied from superinterfaces.
     * <p>
     * 我们在这里有"菱形"多接口继承,并且引入歧义根据javadoc选择的分支,方法最终可能会有不同的规范。因此,这里的很多方法规范都是从超级接口
     * 
     */

    /**
     * Inserts the specified element at the front of this deque if it is
     * possible to do so immediately without violating capacity restrictions,
     * throwing an {@code IllegalStateException} if no space is currently
     * available.  When using a capacity-restricted deque, it is generally
     * preferable to use {@link #offerFirst(Object) offerFirst}.
     *
     * <p>
     *  如果可以在不违反容量限制的情况下立即这样做,则在此deque的前面插入指定的元素,如果当前没有空间,则抛出{@code IllegalStateException}当使用容量限制的deque时,通常优
     * 选使用{@link #offerFirst(Object)offerFirst}。
     * 
     * 
     * @param e the element to add
     * @throws IllegalStateException {@inheritDoc}
     * @throws ClassCastException {@inheritDoc}
     * @throws NullPointerException if the specified element is null
     * @throws IllegalArgumentException {@inheritDoc}
     */
    void addFirst(E e);

    /**
     * Inserts the specified element at the end of this deque if it is
     * possible to do so immediately without violating capacity restrictions,
     * throwing an {@code IllegalStateException} if no space is currently
     * available.  When using a capacity-restricted deque, it is generally
     * preferable to use {@link #offerLast(Object) offerLast}.
     *
     * <p>
     * 在此deque的结尾插入指定的元素,如果可能在不违反容量限制的情况下立即这样做,则在没有空间可用时抛出{@code IllegalStateException}当使用容量限制的deque时,通常优选使用
     * {@link #offerLast(Object)offerLast}。
     * 
     * 
     * @param e the element to add
     * @throws IllegalStateException {@inheritDoc}
     * @throws ClassCastException {@inheritDoc}
     * @throws NullPointerException if the specified element is null
     * @throws IllegalArgumentException {@inheritDoc}
     */
    void addLast(E e);

    /**
     * Inserts the specified element at the front of this deque if it is
     * possible to do so immediately without violating capacity restrictions,
     * returning {@code true} upon success and {@code false} if no space is
     * currently available.
     * When using a capacity-restricted deque, this method is generally
     * preferable to the {@link #addFirst(Object) addFirst} method, which can
     * fail to insert an element only by throwing an exception.
     *
     * <p>
     *  如果可以在不违反容量限制的情况下立即这样做,则在此deque的前面插入指定的元素,成功时返回{@code true},如果当前没有空间则返回{@code false}当使用容量限制的deque ,这种
     * 方法通常优先于{@link #addFirst(Object)addFirst}方法,它可能无法仅通过抛出异常来插入元素。
     * 
     * 
     * @param e the element to add
     * @throws ClassCastException {@inheritDoc}
     * @throws NullPointerException if the specified element is null
     * @throws IllegalArgumentException {@inheritDoc}
     */
    boolean offerFirst(E e);

    /**
     * Inserts the specified element at the end of this deque if it is
     * possible to do so immediately without violating capacity restrictions,
     * returning {@code true} upon success and {@code false} if no space is
     * currently available.
     * When using a capacity-restricted deque, this method is generally
     * preferable to the {@link #addLast(Object) addLast} method, which can
     * fail to insert an element only by throwing an exception.
     *
     * <p>
     * 如果可以在不违反容量限制的情况下立即这样做,则在该deque的末尾插入指定的元素,成功返回{@code true},如果当前没有空间则返回{@code false}当使用容量限制的deque ,这种方法
     * 通常优于{@link #addLast(Object)addLast}方法,它可能无法仅通过抛出异常来插入元素。
     * 
     * 
     * @param e the element to add
     * @throws ClassCastException {@inheritDoc}
     * @throws NullPointerException if the specified element is null
     * @throws IllegalArgumentException {@inheritDoc}
     */
    boolean offerLast(E e);

    /**
     * Inserts the specified element at the front of this deque,
     * waiting if necessary for space to become available.
     *
     * <p>
     *  将指定的元素插入此出口队列的前面,如果需要,等待空间可用
     * 
     * 
     * @param e the element to add
     * @throws InterruptedException if interrupted while waiting
     * @throws ClassCastException if the class of the specified element
     *         prevents it from being added to this deque
     * @throws NullPointerException if the specified element is null
     * @throws IllegalArgumentException if some property of the specified
     *         element prevents it from being added to this deque
     */
    void putFirst(E e) throws InterruptedException;

    /**
     * Inserts the specified element at the end of this deque,
     * waiting if necessary for space to become available.
     *
     * <p>
     *  在此deque的结尾插入指定的元素,如果必要,等待空间可用
     * 
     * 
     * @param e the element to add
     * @throws InterruptedException if interrupted while waiting
     * @throws ClassCastException if the class of the specified element
     *         prevents it from being added to this deque
     * @throws NullPointerException if the specified element is null
     * @throws IllegalArgumentException if some property of the specified
     *         element prevents it from being added to this deque
     */
    void putLast(E e) throws InterruptedException;

    /**
     * Inserts the specified element at the front of this deque,
     * waiting up to the specified wait time if necessary for space to
     * become available.
     *
     * <p>
     *  将指定的元素插入到该deque的前面,等待指定的等待时间(如果需要),以使空间可用
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
     *         prevents it from being added to this deque
     * @throws NullPointerException if the specified element is null
     * @throws IllegalArgumentException if some property of the specified
     *         element prevents it from being added to this deque
     */
    boolean offerFirst(E e, long timeout, TimeUnit unit)
        throws InterruptedException;

    /**
     * Inserts the specified element at the end of this deque,
     * waiting up to the specified wait time if necessary for space to
     * become available.
     *
     * <p>
     * 在此deque的结尾插入指定的元素,等待指定的等待时间(如果需要),以使空间可用
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
     *         prevents it from being added to this deque
     * @throws NullPointerException if the specified element is null
     * @throws IllegalArgumentException if some property of the specified
     *         element prevents it from being added to this deque
     */
    boolean offerLast(E e, long timeout, TimeUnit unit)
        throws InterruptedException;

    /**
     * Retrieves and removes the first element of this deque, waiting
     * if necessary until an element becomes available.
     *
     * <p>
     *  检索并删除此deque的第一个元素,如果必要,等待直到元素可用
     * 
     * 
     * @return the head of this deque
     * @throws InterruptedException if interrupted while waiting
     */
    E takeFirst() throws InterruptedException;

    /**
     * Retrieves and removes the last element of this deque, waiting
     * if necessary until an element becomes available.
     *
     * <p>
     *  检索并删除此deque的最后一个元素,如果必要,等待元素可用
     * 
     * 
     * @return the tail of this deque
     * @throws InterruptedException if interrupted while waiting
     */
    E takeLast() throws InterruptedException;

    /**
     * Retrieves and removes the first element of this deque, waiting
     * up to the specified wait time if necessary for an element to
     * become available.
     *
     * <p>
     *  检索并删除此deque的第一个元素,等待指定的等待时间(如果需要),以使元素可用
     * 
     * 
     * @param timeout how long to wait before giving up, in units of
     *        {@code unit}
     * @param unit a {@code TimeUnit} determining how to interpret the
     *        {@code timeout} parameter
     * @return the head of this deque, or {@code null} if the specified
     *         waiting time elapses before an element is available
     * @throws InterruptedException if interrupted while waiting
     */
    E pollFirst(long timeout, TimeUnit unit)
        throws InterruptedException;

    /**
     * Retrieves and removes the last element of this deque, waiting
     * up to the specified wait time if necessary for an element to
     * become available.
     *
     * <p>
     *  检索并删除此deque的最后一个元素,等待指定的等待时间(如果必要),以使元素可用
     * 
     * 
     * @param timeout how long to wait before giving up, in units of
     *        {@code unit}
     * @param unit a {@code TimeUnit} determining how to interpret the
     *        {@code timeout} parameter
     * @return the tail of this deque, or {@code null} if the specified
     *         waiting time elapses before an element is available
     * @throws InterruptedException if interrupted while waiting
     */
    E pollLast(long timeout, TimeUnit unit)
        throws InterruptedException;

    /**
     * Removes the first occurrence of the specified element from this deque.
     * If the deque does not contain the element, it is unchanged.
     * More formally, removes the first element {@code e} such that
     * {@code o.equals(e)} (if such an element exists).
     * Returns {@code true} if this deque contained the specified element
     * (or equivalently, if this deque changed as a result of the call).
     *
     * <p>
     * 从这个deque中删除指定元素的第一次出现如果deque不包含元素,它不变。
     * 更正式地,删除第一个元素{@code e},使得{@code oequals(e)}(如果这样的元素exists)如果此deque包含指定的元素(或等效地,如果这个deque作为调用的结果而改变),则返
     * 回{@code true}。
     * 从这个deque中删除指定元素的第一次出现如果deque不包含元素,它不变。
     * 
     * 
     * @param o element to be removed from this deque, if present
     * @return {@code true} if an element was removed as a result of this call
     * @throws ClassCastException if the class of the specified element
     *         is incompatible with this deque
     *         (<a href="../Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null
     *         (<a href="../Collection.html#optional-restrictions">optional</a>)
     */
    boolean removeFirstOccurrence(Object o);

    /**
     * Removes the last occurrence of the specified element from this deque.
     * If the deque does not contain the element, it is unchanged.
     * More formally, removes the last element {@code e} such that
     * {@code o.equals(e)} (if such an element exists).
     * Returns {@code true} if this deque contained the specified element
     * (or equivalently, if this deque changed as a result of the call).
     *
     * <p>
     *  从该deque中删除指定元素的最后一次出现如果deque不包含元素,则它不变。
     * 更正式地,删除最后一个元素{@code e},使得{@code oequals(e)}(如果这样的元素exists)如果此deque包含指定的元素(或等效地,如果这个deque作为调用的结果而改变),则
     * 返回{@code true}。
     *  从该deque中删除指定元素的最后一次出现如果deque不包含元素,则它不变。
     * 
     * 
     * @param o element to be removed from this deque, if present
     * @return {@code true} if an element was removed as a result of this call
     * @throws ClassCastException if the class of the specified element
     *         is incompatible with this deque
     *         (<a href="../Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null
     *         (<a href="../Collection.html#optional-restrictions">optional</a>)
     */
    boolean removeLastOccurrence(Object o);

    // *** BlockingQueue methods ***

    /**
     * Inserts the specified element into the queue represented by this deque
     * (in other words, at the tail of this deque) if it is possible to do so
     * immediately without violating capacity restrictions, returning
     * {@code true} upon success and throwing an
     * {@code IllegalStateException} if no space is currently available.
     * When using a capacity-restricted deque, it is generally preferable to
     * use {@link #offer(Object) offer}.
     *
     * <p>This method is equivalent to {@link #addLast(Object) addLast}.
     *
     * <p>
     * 将指定的元素插入由此deque表示的队列(换句话说,在此deque的尾部),如果可以立即这样做而不违反容量限制,返回{@code true}成功并抛出一个{@code IllegalStateException}
     * 如果当前没有空间当使用容量限制的deque时,通常优选使用{@link #offer(Object)offer}。
     * 
     *  <p>此方法等效于{@link #addLast(Object)addLast}
     * 
     * 
     * @param e the element to add
     * @throws IllegalStateException {@inheritDoc}
     * @throws ClassCastException if the class of the specified element
     *         prevents it from being added to this deque
     * @throws NullPointerException if the specified element is null
     * @throws IllegalArgumentException if some property of the specified
     *         element prevents it from being added to this deque
     */
    boolean add(E e);

    /**
     * Inserts the specified element into the queue represented by this deque
     * (in other words, at the tail of this deque) if it is possible to do so
     * immediately without violating capacity restrictions, returning
     * {@code true} upon success and {@code false} if no space is currently
     * available.  When using a capacity-restricted deque, this method is
     * generally preferable to the {@link #add} method, which can fail to
     * insert an element only by throwing an exception.
     *
     * <p>This method is equivalent to {@link #offerLast(Object) offerLast}.
     *
     * <p>
     * 将指定的元素插入由此deque表示的队列(换句话说,在此deque的尾部),如果可以立即执行而不违反容量限制,则返回成功时的{@code true}和{@code false}如果没有空间当前可用当使用
     * 容量限制的deque时,此方法通常优于{@link #add}方法,它可能无法仅通过抛出异常插入元素。
     * 
     *  <p>此方法相当于{@link #offerLast(Object)offerLast}
     * 
     * 
     * @param e the element to add
     * @throws ClassCastException if the class of the specified element
     *         prevents it from being added to this deque
     * @throws NullPointerException if the specified element is null
     * @throws IllegalArgumentException if some property of the specified
     *         element prevents it from being added to this deque
     */
    boolean offer(E e);

    /**
     * Inserts the specified element into the queue represented by this deque
     * (in other words, at the tail of this deque), waiting if necessary for
     * space to become available.
     *
     * <p>This method is equivalent to {@link #putLast(Object) putLast}.
     *
     * <p>
     *  将指定的元素插入由此deque表示的队列中(换句话说,在此deque的尾部),如果必要,等待空间可用
     * 
     *  <p>此方法等效于{@link #putLast(Object)putLast}
     * 
     * 
     * @param e the element to add
     * @throws InterruptedException {@inheritDoc}
     * @throws ClassCastException if the class of the specified element
     *         prevents it from being added to this deque
     * @throws NullPointerException if the specified element is null
     * @throws IllegalArgumentException if some property of the specified
     *         element prevents it from being added to this deque
     */
    void put(E e) throws InterruptedException;

    /**
     * Inserts the specified element into the queue represented by this deque
     * (in other words, at the tail of this deque), waiting up to the
     * specified wait time if necessary for space to become available.
     *
     * <p>This method is equivalent to
     * {@link #offerLast(Object,long,TimeUnit) offerLast}.
     *
     * <p>
     * 将指定的元素插入由此deque表示的队列中(换句话说,在此deque的尾部),等待指定的等待时间(如果需要),以使空间可用
     * 
     *  <p>此方法相当于{@link #offerLast(Object,long,TimeUnit)offerLast}
     * 
     * 
     * @param e the element to add
     * @return {@code true} if the element was added to this deque, else
     *         {@code false}
     * @throws InterruptedException {@inheritDoc}
     * @throws ClassCastException if the class of the specified element
     *         prevents it from being added to this deque
     * @throws NullPointerException if the specified element is null
     * @throws IllegalArgumentException if some property of the specified
     *         element prevents it from being added to this deque
     */
    boolean offer(E e, long timeout, TimeUnit unit)
        throws InterruptedException;

    /**
     * Retrieves and removes the head of the queue represented by this deque
     * (in other words, the first element of this deque).
     * This method differs from {@link #poll poll} only in that it
     * throws an exception if this deque is empty.
     *
     * <p>This method is equivalent to {@link #removeFirst() removeFirst}.
     *
     * <p>
     *  检索并删除由此deque表示的队列的头(换句话说,此deque的第一个元素)此方法与{@link #poll poll}不同之处仅在于,如果此deque为空,则会抛出异常
     * 
     *  <p>此方法等效于{@link #removeFirst()removeFirst}
     * 
     * 
     * @return the head of the queue represented by this deque
     * @throws NoSuchElementException if this deque is empty
     */
    E remove();

    /**
     * Retrieves and removes the head of the queue represented by this deque
     * (in other words, the first element of this deque), or returns
     * {@code null} if this deque is empty.
     *
     * <p>This method is equivalent to {@link #pollFirst()}.
     *
     * <p>
     *  检索并删除由此deque(由此deque的第一个元素)表示的队列的头部,或者如果此deque为空则返回{@code null}
     * 
     * <p>此方法相当于{@link #pollFirst()}
     * 
     * 
     * @return the head of this deque, or {@code null} if this deque is empty
     */
    E poll();

    /**
     * Retrieves and removes the head of the queue represented by this deque
     * (in other words, the first element of this deque), waiting if
     * necessary until an element becomes available.
     *
     * <p>This method is equivalent to {@link #takeFirst() takeFirst}.
     *
     * <p>
     *  检索并删除由此deque(换句话说,此deque的第一个元素)表示的队列的头部,如果必要,等待直到元素变为可用
     * 
     *  <p>此方法等同于{@link #takeFirst()takeFirst}
     * 
     * 
     * @return the head of this deque
     * @throws InterruptedException if interrupted while waiting
     */
    E take() throws InterruptedException;

    /**
     * Retrieves and removes the head of the queue represented by this deque
     * (in other words, the first element of this deque), waiting up to the
     * specified wait time if necessary for an element to become available.
     *
     * <p>This method is equivalent to
     * {@link #pollFirst(long,TimeUnit) pollFirst}.
     *
     * <p>
     *  检索并删除由该deque表示的队列的头部(换句话说,这个deque的第一个元素),等待指定的等待时间,如果必要,一个元素变得可用
     * 
     *  <p>此方法等效于{@link #pollFirst(long,TimeUnit)pollFirst}
     * 
     * 
     * @return the head of this deque, or {@code null} if the
     *         specified waiting time elapses before an element is available
     * @throws InterruptedException if interrupted while waiting
     */
    E poll(long timeout, TimeUnit unit)
        throws InterruptedException;

    /**
     * Retrieves, but does not remove, the head of the queue represented by
     * this deque (in other words, the first element of this deque).
     * This method differs from {@link #peek peek} only in that it throws an
     * exception if this deque is empty.
     *
     * <p>This method is equivalent to {@link #getFirst() getFirst}.
     *
     * <p>
     * 检索但不删除由此deque表示的队列的头(换句话说,此deque的第一个元素)此方法与{@link #peek peek}不同之处仅在于,如果此deque是空
     * 
     *  <p>此方法等效于{@link #getFirst()getFirst}
     * 
     * 
     * @return the head of this deque
     * @throws NoSuchElementException if this deque is empty
     */
    E element();

    /**
     * Retrieves, but does not remove, the head of the queue represented by
     * this deque (in other words, the first element of this deque), or
     * returns {@code null} if this deque is empty.
     *
     * <p>This method is equivalent to {@link #peekFirst() peekFirst}.
     *
     * <p>
     *  检索但不删除由此deque表示的队列的头(换句话说,此deque的第一个元素),或如果此deque为空则返回{@code null}
     * 
     *  <p>此方法相当于{@link #peekFirst()peekFirst}
     * 
     * 
     * @return the head of this deque, or {@code null} if this deque is empty
     */
    E peek();

    /**
     * Removes the first occurrence of the specified element from this deque.
     * If the deque does not contain the element, it is unchanged.
     * More formally, removes the first element {@code e} such that
     * {@code o.equals(e)} (if such an element exists).
     * Returns {@code true} if this deque contained the specified element
     * (or equivalently, if this deque changed as a result of the call).
     *
     * <p>This method is equivalent to
     * {@link #removeFirstOccurrence(Object) removeFirstOccurrence}.
     *
     * <p>
     * 从这个deque中删除指定元素的第一次出现如果deque不包含元素,它不变。
     * 更正式地,删除第一个元素{@code e},使得{@code oequals(e)}(如果这样的元素exists)如果此deque包含指定的元素(或等效地,如果这个deque作为调用的结果而改变),则返
     * 回{@code true}。
     * 从这个deque中删除指定元素的第一次出现如果deque不包含元素,它不变。
     * 
     *  <p>此方法等效于{@link #removeFirstOccurrence(Object)removeFirstOccurrence}
     * 
     * 
     * @param o element to be removed from this deque, if present
     * @return {@code true} if this deque changed as a result of the call
     * @throws ClassCastException if the class of the specified element
     *         is incompatible with this deque
     *         (<a href="../Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null
     *         (<a href="../Collection.html#optional-restrictions">optional</a>)
     */
    boolean remove(Object o);

    /**
     * Returns {@code true} if this deque contains the specified element.
     * More formally, returns {@code true} if and only if this deque contains
     * at least one element {@code e} such that {@code o.equals(e)}.
     *
     * <p>
     *  如果这个deque包含指定的元素,则返回{@code true}更正式地说,当且仅当这个deque包含至少一个元素{@code e},{@code oequals(e)}
     * 
     * 
     * @param o object to be checked for containment in this deque
     * @return {@code true} if this deque contains the specified element
     * @throws ClassCastException if the class of the specified element
     *         is incompatible with this deque
     *         (<a href="../Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null
     *         (<a href="../Collection.html#optional-restrictions">optional</a>)
     */
    public boolean contains(Object o);

    /**
     * Returns the number of elements in this deque.
     *
     * <p>
     *  返回此deque中的元素数
     * 
     * 
     * @return the number of elements in this deque
     */
    public int size();

    /**
     * Returns an iterator over the elements in this deque in proper sequence.
     * The elements will be returned in order from first (head) to last (tail).
     *
     * <p>
     * 以正确顺序返回此deque中的元素的迭代器元素将按从头(头)到尾(尾)的顺序返回,
     * 
     * 
     * @return an iterator over the elements in this deque in proper sequence
     */
    Iterator<E> iterator();

    // *** Stack methods ***

    /**
     * Pushes an element onto the stack represented by this deque (in other
     * words, at the head of this deque) if it is possible to do so
     * immediately without violating capacity restrictions, throwing an
     * {@code IllegalStateException} if no space is currently available.
     *
     * <p>This method is equivalent to {@link #addFirst(Object) addFirst}.
     *
     * <p>
     *  将元素推送到由此deque表示的堆栈(换句话说,在此deque的头部),如果可能在不违反容量限制的情况下立即执行,则抛出{@code IllegalStateException}(如果当前没有空间)。
     * 
     * 
     * @throws IllegalStateException {@inheritDoc}
     * @throws ClassCastException {@inheritDoc}
     * @throws NullPointerException if the specified element is null
     * @throws IllegalArgumentException {@inheritDoc}
     */
    void push(E e);
}
