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
 * Written by Doug Lea and Josh Bloch with assistance from members of
 * JCP JSR-166 Expert Group and released to the public domain, as explained
 * at http://creativecommons.org/publicdomain/zero/1.0/
 * <p>
 *  由Doug Lea和Josh Bloch在JCP JSR-166专家组的成员的帮助下撰写,并发布到公共领域,如http：// creativecommonsorg / publicdomain / z
 * ero / 10 /。
 * 
 */

package java.util;

/**
 * A linear collection that supports element insertion and removal at
 * both ends.  The name <i>deque</i> is short for "double ended queue"
 * and is usually pronounced "deck".  Most {@code Deque}
 * implementations place no fixed limits on the number of elements
 * they may contain, but this interface supports capacity-restricted
 * deques as well as those with no fixed size limit.
 *
 * <p>This interface defines methods to access the elements at both
 * ends of the deque.  Methods are provided to insert, remove, and
 * examine the element.  Each of these methods exists in two forms:
 * one throws an exception if the operation fails, the other returns a
 * special value (either {@code null} or {@code false}, depending on
 * the operation).  The latter form of the insert operation is
 * designed specifically for use with capacity-restricted
 * {@code Deque} implementations; in most implementations, insert
 * operations cannot fail.
 *
 * <p>The twelve methods described above are summarized in the
 * following table:
 *
 * <table BORDER CELLPADDING=3 CELLSPACING=1>
 * <caption>Summary of Deque methods</caption>
 *  <tr>
 *    <td></td>
 *    <td ALIGN=CENTER COLSPAN = 2> <b>First Element (Head)</b></td>
 *    <td ALIGN=CENTER COLSPAN = 2> <b>Last Element (Tail)</b></td>
 *  </tr>
 *  <tr>
 *    <td></td>
 *    <td ALIGN=CENTER><em>Throws exception</em></td>
 *    <td ALIGN=CENTER><em>Special value</em></td>
 *    <td ALIGN=CENTER><em>Throws exception</em></td>
 *    <td ALIGN=CENTER><em>Special value</em></td>
 *  </tr>
 *  <tr>
 *    <td><b>Insert</b></td>
 *    <td>{@link Deque#addFirst addFirst(e)}</td>
 *    <td>{@link Deque#offerFirst offerFirst(e)}</td>
 *    <td>{@link Deque#addLast addLast(e)}</td>
 *    <td>{@link Deque#offerLast offerLast(e)}</td>
 *  </tr>
 *  <tr>
 *    <td><b>Remove</b></td>
 *    <td>{@link Deque#removeFirst removeFirst()}</td>
 *    <td>{@link Deque#pollFirst pollFirst()}</td>
 *    <td>{@link Deque#removeLast removeLast()}</td>
 *    <td>{@link Deque#pollLast pollLast()}</td>
 *  </tr>
 *  <tr>
 *    <td><b>Examine</b></td>
 *    <td>{@link Deque#getFirst getFirst()}</td>
 *    <td>{@link Deque#peekFirst peekFirst()}</td>
 *    <td>{@link Deque#getLast getLast()}</td>
 *    <td>{@link Deque#peekLast peekLast()}</td>
 *  </tr>
 * </table>
 *
 * <p>This interface extends the {@link Queue} interface.  When a deque is
 * used as a queue, FIFO (First-In-First-Out) behavior results.  Elements are
 * added at the end of the deque and removed from the beginning.  The methods
 * inherited from the {@code Queue} interface are precisely equivalent to
 * {@code Deque} methods as indicated in the following table:
 *
 * <table BORDER CELLPADDING=3 CELLSPACING=1>
 * <caption>Comparison of Queue and Deque methods</caption>
 *  <tr>
 *    <td ALIGN=CENTER> <b>{@code Queue} Method</b></td>
 *    <td ALIGN=CENTER> <b>Equivalent {@code Deque} Method</b></td>
 *  </tr>
 *  <tr>
 *    <td>{@link java.util.Queue#add add(e)}</td>
 *    <td>{@link #addLast addLast(e)}</td>
 *  </tr>
 *  <tr>
 *    <td>{@link java.util.Queue#offer offer(e)}</td>
 *    <td>{@link #offerLast offerLast(e)}</td>
 *  </tr>
 *  <tr>
 *    <td>{@link java.util.Queue#remove remove()}</td>
 *    <td>{@link #removeFirst removeFirst()}</td>
 *  </tr>
 *  <tr>
 *    <td>{@link java.util.Queue#poll poll()}</td>
 *    <td>{@link #pollFirst pollFirst()}</td>
 *  </tr>
 *  <tr>
 *    <td>{@link java.util.Queue#element element()}</td>
 *    <td>{@link #getFirst getFirst()}</td>
 *  </tr>
 *  <tr>
 *    <td>{@link java.util.Queue#peek peek()}</td>
 *    <td>{@link #peek peekFirst()}</td>
 *  </tr>
 * </table>
 *
 * <p>Deques can also be used as LIFO (Last-In-First-Out) stacks.  This
 * interface should be used in preference to the legacy {@link Stack} class.
 * When a deque is used as a stack, elements are pushed and popped from the
 * beginning of the deque.  Stack methods are precisely equivalent to
 * {@code Deque} methods as indicated in the table below:
 *
 * <table BORDER CELLPADDING=3 CELLSPACING=1>
 * <caption>Comparison of Stack and Deque methods</caption>
 *  <tr>
 *    <td ALIGN=CENTER> <b>Stack Method</b></td>
 *    <td ALIGN=CENTER> <b>Equivalent {@code Deque} Method</b></td>
 *  </tr>
 *  <tr>
 *    <td>{@link #push push(e)}</td>
 *    <td>{@link #addFirst addFirst(e)}</td>
 *  </tr>
 *  <tr>
 *    <td>{@link #pop pop()}</td>
 *    <td>{@link #removeFirst removeFirst()}</td>
 *  </tr>
 *  <tr>
 *    <td>{@link #peek peek()}</td>
 *    <td>{@link #peekFirst peekFirst()}</td>
 *  </tr>
 * </table>
 *
 * <p>Note that the {@link #peek peek} method works equally well when
 * a deque is used as a queue or a stack; in either case, elements are
 * drawn from the beginning of the deque.
 *
 * <p>This interface provides two methods to remove interior
 * elements, {@link #removeFirstOccurrence removeFirstOccurrence} and
 * {@link #removeLastOccurrence removeLastOccurrence}.
 *
 * <p>Unlike the {@link List} interface, this interface does not
 * provide support for indexed access to elements.
 *
 * <p>While {@code Deque} implementations are not strictly required
 * to prohibit the insertion of null elements, they are strongly
 * encouraged to do so.  Users of any {@code Deque} implementations
 * that do allow null elements are strongly encouraged <i>not</i> to
 * take advantage of the ability to insert nulls.  This is so because
 * {@code null} is used as a special return value by various methods
 * to indicated that the deque is empty.
 *
 * <p>{@code Deque} implementations generally do not define
 * element-based versions of the {@code equals} and {@code hashCode}
 * methods, but instead inherit the identity-based versions from class
 * {@code Object}.
 *
 * <p>This interface is a member of the <a
 * href="{@docRoot}/../technotes/guides/collections/index.html"> Java Collections
 * Framework</a>.
 *
 * <p>
 *  支持在两端插入和删除元素的线性集合名称<i> deque </i>是"双端队列"的缩写,通常发音为"deck"。
 * 大多数{@code Deque}实现不会对它们可能包含的元素数量,但此接口支持容量限制的规则以及没有固定大小限制的那些。
 * 
 * <p>此接口定义访问deque两端元素的方法。提供插入,删除和检查元素的方法。
 * 每种方法都有两种形式：一种在操作失败时抛出异常,另一种返回一个特殊值({@code null}或{@code false},这取决于操作)插入操作的后一种形式是专为使用容量限制的{@code Deque}
 * 实现而设计的;在大多数实现中,插入操作不会失败。
 * <p>此接口定义访问deque两端元素的方法。提供插入,删除和检查元素的方法。
 * 
 *  上述十二种方法总结在下表中：
 * 
 * <table BORDER CELLPADDING=3 CELLSPACING=1>
 *  <caption> Deque方法摘要</caption>
 * <tr>
 *  <td> </td> <td ALIGN = CENTER COLSPAN = 2> <b>第一个元素(头)</b> </td> <td ALIGN = CENTER COLSPAN = 2> <b>
 *  / b> </td>。
 * </tr>
 * <tr>
 * <td> </td> <td ALIGN = CENTER> <em>抛出异常</em> </td> <td ALIGN = CENTER> <em>特殊值</em> </td> <td ALIGN = >
 *  <em>抛出异常</em> </td> <td ALIGN = CENTER> <em>特殊值</em> </td>。
 * </tr>
 * <tr>
 *  <td> <b>插入</b> </td> <td> {@ link Deque#addFirst addFirst(e)} </td> <td> {@ link Deque#offerFirst offerFirst(e)}
 *  </td > <td> {@ link Deque#addLast addLast(e)} </td> <td> {@ link Deque#offerLast offerLast(e)} </td>
 * 。
 * </tr>
 * <tr>
 *  <td> <b>删除</b> </td> <td> {@ link Deque#removeFirst removeFirst()} </td> <td> {@ link Deque#pollFirst pollFirst()}
 *  </td> < td> {@ link Deque#removeLast removeLast()} </td> <td> {@ link Deque#pollLast pollLast()} </td>
 * 。
 * </tr>
 * <tr>
 * <td> <b>检查</b> </td> <td> {@ link Deque#getFirst getFirst()} </td> <td> {@ link Deque#peekFirst peekFirst()}
 *  </td> < td> {@ link Deque#getLast getLast()} </td> <td> {@ link Deque#peekLast peekLast()} </td>。
 * </tr>
 * </table>
 * 
 *  <p>此接口扩展了{@link Queue}接口当deque用作队列时,FIFO(先进先出)行为结果元素在deque结束时添加并从头开始删除方法继承自{@code Queue}接口,与下表中所示的{@code Deque}
 * 方法完全相同：。
 * 
 * <table BORDER CELLPADDING=3 CELLSPACING=1>
 *  <caption>比较队列和deque方法</caption>
 * <tr>
 *  <td ALIGN = CENTER> <b> {@ code Queue}方法</b> </td> <td ALIGN = CENTER> <b>等效的{@code Deque}方法</b>
 * </tr>
 * <tr>
 * <td> {@ link javautilQueue#add add(e)} </td> <td> {@ link #addLast addLast(e)} </td>
 * </tr>
 * <tr>
 *  <td> {@ link javautilQueue#offer offer(e)} </td> <td> {@ link #offer最新优惠(e)} </td>
 * </tr>
 * <tr>
 *  <td> {@ link javumeilQueue#remove remove()} </td> <td> {@ link #removeFirst removeFirst()} </td>
 * </tr>
 * <tr>
 *  <td> {@ link javapolQueue#poll poll()} </td> <td> {@ link #pollFirst pollFirst()} </td>
 * </tr>
 * <tr>
 *  <td> {@ link javgetilQueue#element element()} </td> <td> {@ link #getFirst getFirst()} </td>
 * </tr>
 * <tr>
 *  <td> {@ link java.langue#peek peek()} </td> <td> {@ link #peek peekFirst()} </td>
 * </tr>
 * </table>
 * 
 * <p> Deques也可以用作LIFO(后进先出)堆栈此接口应优先于旧的{@link Stack}类使用当deque用作堆栈时,元素被推送并弹出从deque的开始堆栈方法正好相当于{@code Deque}
 * 方法,如下表所示：。
 * 
 * <table BORDER CELLPADDING=3 CELLSPACING=1>
 *  <caption> Stack and Deque方法的比较</caption>
 * <tr>
 *  <td ALIGN = CENTER> <b>堆栈方法</b> </td> <td ALIGN = CENTER> <b>等效{@code Deque}方法</b> </td>
 * </tr>
 * <tr>
 *  <td> {@ link #push push(e)} </td> <td> {@ link #addFirst addFirst(e)} </td>
 * </tr>
 * <tr>
 *  <td> {@ link #pop pop()} </td> <td> {@ link #removeFirst removeFirst()} </td>
 * </tr>
 * <tr>
 *  <td> {@ link #peek peek()} </td> <td> {@ link #peekFirst peekFirst()} </td>
 * </tr>
 * </table>
 * 
 * <p>请注意,当将deque用作队列或堆栈时,{@link #peek peek}方法同样适用;在任何情况下,元素都从deque的开始绘制
 * 
 *  <p>此接口提供了两种方法来删除内部元素：{@link #removeFirstOccurrence removeFirstOccurrence}和{@link #removeLastOccurrence removeLastOccurrence}
 * 。
 * 
 *  <p>与{@link List}接口不同,此接口不支持对元素的索引访问
 * 
 * <p>尽管{@code Deque}实现不是严格要求禁止插入空元素,强烈建议他们这样做强烈鼓励任何允许空元素的{@code Deque}实现的用户<i>不</i>利用插入null的能力这是因为{@code null}
 * 被用作各种方法的特殊返回值,表示deque是空的。
 * 
 *  </p> {@ code Deque}实现通常不定义{@code equals}和{@code hashCode}方法的基于元素的版本,而是继承类{@code Object}中的基于身份的版本
 * 
 *  <p>此界面是<a href=\"{@docRoot}//technotes/guides/collections/indexhtml\"> Java集合框架</a>的成员
 * 
 * 
 * @author Doug Lea
 * @author Josh Bloch
 * @since  1.6
 * @param <E> the type of elements held in this collection
 */
public interface Deque<E> extends Queue<E> {
    /**
     * Inserts the specified element at the front of this deque if it is
     * possible to do so immediately without violating capacity restrictions,
     * throwing an {@code IllegalStateException} if no space is currently
     * available.  When using a capacity-restricted deque, it is generally
     * preferable to use method {@link #offerFirst}.
     *
     * <p>
     * 如果可以在不违反容量限制的情况下立即这样做,则在此deque的前面插入指定的元素,如果当前没有空间,则抛出{@code IllegalStateException}当使用容量限制的deque时,通常优选
     * 使用方法{@link #offerFirst}。
     * 
     * 
     * @param e the element to add
     * @throws IllegalStateException if the element cannot be added at this
     *         time due to capacity restrictions
     * @throws ClassCastException if the class of the specified element
     *         prevents it from being added to this deque
     * @throws NullPointerException if the specified element is null and this
     *         deque does not permit null elements
     * @throws IllegalArgumentException if some property of the specified
     *         element prevents it from being added to this deque
     */
    void addFirst(E e);

    /**
     * Inserts the specified element at the end of this deque if it is
     * possible to do so immediately without violating capacity restrictions,
     * throwing an {@code IllegalStateException} if no space is currently
     * available.  When using a capacity-restricted deque, it is generally
     * preferable to use method {@link #offerLast}.
     *
     * <p>This method is equivalent to {@link #add}.
     *
     * <p>
     *  在此deque的结尾插入指定的元素,如果可能在不违反容量限制的情况下立即这样做,则在没有空间可用时抛出{@code IllegalStateException}当使用容量限制的deque时,通常优选使
     * 用方法{@link #offerLast}。
     * 
     *  <p>此方法相当于{@link #add}
     * 
     * 
     * @param e the element to add
     * @throws IllegalStateException if the element cannot be added at this
     *         time due to capacity restrictions
     * @throws ClassCastException if the class of the specified element
     *         prevents it from being added to this deque
     * @throws NullPointerException if the specified element is null and this
     *         deque does not permit null elements
     * @throws IllegalArgumentException if some property of the specified
     *         element prevents it from being added to this deque
     */
    void addLast(E e);

    /**
     * Inserts the specified element at the front of this deque unless it would
     * violate capacity restrictions.  When using a capacity-restricted deque,
     * this method is generally preferable to the {@link #addFirst} method,
     * which can fail to insert an element only by throwing an exception.
     *
     * <p>
     * 在此deque前插入指定的元素,除非它违反容量限制当使用容量限制的deque时,此方法通常优先于{@link #addFirst}方法,该方法可能无法仅插入一个元素例外
     * 
     * 
     * @param e the element to add
     * @return {@code true} if the element was added to this deque, else
     *         {@code false}
     * @throws ClassCastException if the class of the specified element
     *         prevents it from being added to this deque
     * @throws NullPointerException if the specified element is null and this
     *         deque does not permit null elements
     * @throws IllegalArgumentException if some property of the specified
     *         element prevents it from being added to this deque
     */
    boolean offerFirst(E e);

    /**
     * Inserts the specified element at the end of this deque unless it would
     * violate capacity restrictions.  When using a capacity-restricted deque,
     * this method is generally preferable to the {@link #addLast} method,
     * which can fail to insert an element only by throwing an exception.
     *
     * <p>
     *  在此deque的末尾插入指定的元素,除非它违反容量限制当使用容量限制的deque时,此方法通常优先于{@link #addLast}方法,该方法只能通过抛出一个例外
     * 
     * 
     * @param e the element to add
     * @return {@code true} if the element was added to this deque, else
     *         {@code false}
     * @throws ClassCastException if the class of the specified element
     *         prevents it from being added to this deque
     * @throws NullPointerException if the specified element is null and this
     *         deque does not permit null elements
     * @throws IllegalArgumentException if some property of the specified
     *         element prevents it from being added to this deque
     */
    boolean offerLast(E e);

    /**
     * Retrieves and removes the first element of this deque.  This method
     * differs from {@link #pollFirst pollFirst} only in that it throws an
     * exception if this deque is empty.
     *
     * <p>
     *  检索和删除此deque的第一个元素此方法与{@link #pollFirst pollFirst}的区别仅在于,如果此deque为空,则它会抛出异常
     * 
     * 
     * @return the head of this deque
     * @throws NoSuchElementException if this deque is empty
     */
    E removeFirst();

    /**
     * Retrieves and removes the last element of this deque.  This method
     * differs from {@link #pollLast pollLast} only in that it throws an
     * exception if this deque is empty.
     *
     * <p>
     * 检索并删除此deque的最后一个元素此方法与{@link #pollLast pollLast}的不同之处仅在于,如果此deque为空,它会抛出异常
     * 
     * 
     * @return the tail of this deque
     * @throws NoSuchElementException if this deque is empty
     */
    E removeLast();

    /**
     * Retrieves and removes the first element of this deque,
     * or returns {@code null} if this deque is empty.
     *
     * <p>
     *  检索并删除此deque的第一个元素,如果此deque为空,则返回{@code null}
     * 
     * 
     * @return the head of this deque, or {@code null} if this deque is empty
     */
    E pollFirst();

    /**
     * Retrieves and removes the last element of this deque,
     * or returns {@code null} if this deque is empty.
     *
     * <p>
     *  检索并删除此deque的最后一个元素,如果此deque为空,则返回{@code null}
     * 
     * 
     * @return the tail of this deque, or {@code null} if this deque is empty
     */
    E pollLast();

    /**
     * Retrieves, but does not remove, the first element of this deque.
     *
     * This method differs from {@link #peekFirst peekFirst} only in that it
     * throws an exception if this deque is empty.
     *
     * <p>
     *  检索,但不删除,这个deque的第一个元素
     * 
     *  此方法与{@link #peekFirst peekFirst}的不同之处仅在于,如果此deque为空,它会抛出异常
     * 
     * 
     * @return the head of this deque
     * @throws NoSuchElementException if this deque is empty
     */
    E getFirst();

    /**
     * Retrieves, but does not remove, the last element of this deque.
     * This method differs from {@link #peekLast peekLast} only in that it
     * throws an exception if this deque is empty.
     *
     * <p>
     *  检索但不删除此deque的最后一个元素此方法不同于{@link #peekLast peekLast},因为如果此deque为空,它会抛出异常
     * 
     * 
     * @return the tail of this deque
     * @throws NoSuchElementException if this deque is empty
     */
    E getLast();

    /**
     * Retrieves, but does not remove, the first element of this deque,
     * or returns {@code null} if this deque is empty.
     *
     * <p>
     * 检索但不删除此deque的第一个元素,或如果此deque为空,则返回{@code null}
     * 
     * 
     * @return the head of this deque, or {@code null} if this deque is empty
     */
    E peekFirst();

    /**
     * Retrieves, but does not remove, the last element of this deque,
     * or returns {@code null} if this deque is empty.
     *
     * <p>
     *  检索但不删除此deque的最后一个元素,或如果此deque为空则返回{@code null}
     * 
     * 
     * @return the tail of this deque, or {@code null} if this deque is empty
     */
    E peekLast();

    /**
     * Removes the first occurrence of the specified element from this deque.
     * If the deque does not contain the element, it is unchanged.
     * More formally, removes the first element {@code e} such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>
     * (if such an element exists).
     * Returns {@code true} if this deque contained the specified element
     * (or equivalently, if this deque changed as a result of the call).
     *
     * <p>
     *  从该deque中删除指定元素的第一次出现如果deque不包含元素,则它不变。
     * 更正式地,删除第一个元素{@code e},使得<tt>(o == null& == null&nbsp;：oequals(e))</tt>(如果这样的元素存在)返回{@code true}如果这个de
     * que包含指定的元素(或者等效地,如果这个deque由于调用而改变)。
     *  从该deque中删除指定元素的第一次出现如果deque不包含元素,则它不变。
     * 
     * 
     * @param o element to be removed from this deque, if present
     * @return {@code true} if an element was removed as a result of this call
     * @throws ClassCastException if the class of the specified element
     *         is incompatible with this deque
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null and this
     *         deque does not permit null elements
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     */
    boolean removeFirstOccurrence(Object o);

    /**
     * Removes the last occurrence of the specified element from this deque.
     * If the deque does not contain the element, it is unchanged.
     * More formally, removes the last element {@code e} such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>
     * (if such an element exists).
     * Returns {@code true} if this deque contained the specified element
     * (or equivalently, if this deque changed as a result of the call).
     *
     * <p>
     * 从该deque中删除指定元素的最后一次出现如果deque不包含元素,则它不变。
     * 更正式地,删除最后一个元素{@code e},使得<tt>(o == null& == null&nbsp;：oequals(e))</tt>(如果这样的元素存在)返回{@code true}如果这个d
     * eque包含指定的元素(或者等效地,如果这个deque由于调用而改变)。
     * 从该deque中删除指定元素的最后一次出现如果deque不包含元素,则它不变。
     * 
     * 
     * @param o element to be removed from this deque, if present
     * @return {@code true} if an element was removed as a result of this call
     * @throws ClassCastException if the class of the specified element
     *         is incompatible with this deque
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null and this
     *         deque does not permit null elements
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     */
    boolean removeLastOccurrence(Object o);

    // *** Queue methods ***

    /**
     * Inserts the specified element into the queue represented by this deque
     * (in other words, at the tail of this deque) if it is possible to do so
     * immediately without violating capacity restrictions, returning
     * {@code true} upon success and throwing an
     * {@code IllegalStateException} if no space is currently available.
     * When using a capacity-restricted deque, it is generally preferable to
     * use {@link #offer(Object) offer}.
     *
     * <p>This method is equivalent to {@link #addLast}.
     *
     * <p>
     * 将指定的元素插入由此deque表示的队列(换句话说,在此deque的尾部),如果可以立即这样做而不违反容量限制,返回{@code true}成功并抛出一个{@code IllegalStateException}
     * 如果当前没有空间当使用容量限制的deque时,通常优选使用{@link #offer(Object)offer}。
     * 
     *  <p>此方法相当于{@link #addLast}
     * 
     * 
     * @param e the element to add
     * @return {@code true} (as specified by {@link Collection#add})
     * @throws IllegalStateException if the element cannot be added at this
     *         time due to capacity restrictions
     * @throws ClassCastException if the class of the specified element
     *         prevents it from being added to this deque
     * @throws NullPointerException if the specified element is null and this
     *         deque does not permit null elements
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
     * <p>This method is equivalent to {@link #offerLast}.
     *
     * <p>
     * 将指定的元素插入由此deque表示的队列(换句话说,在此deque的尾部),如果可以立即执行而不违反容量限制,则返回成功时的{@code true}和{@code false}如果没有空间当前可用当使用
     * 容量限制的deque时,此方法通常优于{@link #add}方法,它可能无法仅通过抛出异常插入元素。
     * 
     *  <p>此方法相当于{@link #offerLast}
     * 
     * 
     * @param e the element to add
     * @return {@code true} if the element was added to this deque, else
     *         {@code false}
     * @throws ClassCastException if the class of the specified element
     *         prevents it from being added to this deque
     * @throws NullPointerException if the specified element is null and this
     *         deque does not permit null elements
     * @throws IllegalArgumentException if some property of the specified
     *         element prevents it from being added to this deque
     */
    boolean offer(E e);

    /**
     * Retrieves and removes the head of the queue represented by this deque
     * (in other words, the first element of this deque).
     * This method differs from {@link #poll poll} only in that it throws an
     * exception if this deque is empty.
     *
     * <p>This method is equivalent to {@link #removeFirst()}.
     *
     * <p>
     *  检索并删除由此deque表示的队列的头(换句话说,此deque的第一个元素)此方法与{@link #poll poll}不同之处仅在于,如果此deque为空,则会抛出异常
     * 
     *  <p>此方法相当于{@link #removeFirst()}
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
     * 检索并删除由此deque(由此deque的第一个元素)表示的队列的头部,或者如果此deque为空则返回{@code null}
     * 
     *  <p>此方法相当于{@link #pollFirst()}
     * 
     * 
     * @return the first element of this deque, or {@code null} if
     *         this deque is empty
     */
    E poll();

    /**
     * Retrieves, but does not remove, the head of the queue represented by
     * this deque (in other words, the first element of this deque).
     * This method differs from {@link #peek peek} only in that it throws an
     * exception if this deque is empty.
     *
     * <p>This method is equivalent to {@link #getFirst()}.
     *
     * <p>
     *  检索但不删除由此deque表示的队列的头(换句话说,此deque的第一个元素)此方法与{@link #peek peek}不同之处仅在于,如果此deque是空
     * 
     *  <p>此方法相当于{@link #getFirst()}
     * 
     * 
     * @return the head of the queue represented by this deque
     * @throws NoSuchElementException if this deque is empty
     */
    E element();

    /**
     * Retrieves, but does not remove, the head of the queue represented by
     * this deque (in other words, the first element of this deque), or
     * returns {@code null} if this deque is empty.
     *
     * <p>This method is equivalent to {@link #peekFirst()}.
     *
     * <p>
     *  检索但不删除由此deque表示的队列的头(换句话说,此deque的第一个元素),或如果此deque为空则返回{@code null}
     * 
     *  <p>此方法相当于{@link #peekFirst()}
     * 
     * 
     * @return the head of the queue represented by this deque, or
     *         {@code null} if this deque is empty
     */
    E peek();


    // *** Stack methods ***

    /**
     * Pushes an element onto the stack represented by this deque (in other
     * words, at the head of this deque) if it is possible to do so
     * immediately without violating capacity restrictions, throwing an
     * {@code IllegalStateException} if no space is currently available.
     *
     * <p>This method is equivalent to {@link #addFirst}.
     *
     * <p>
     * 将元素推送到由此deque表示的堆栈(换句话说,在此deque的头部),如果可能在不违反容量限制的情况下立即执行,则抛出{@code IllegalStateException}(如果当前没有空间)
     * 
     *  <p>此方法相当于{@link #addFirst}
     * 
     * 
     * @param e the element to push
     * @throws IllegalStateException if the element cannot be added at this
     *         time due to capacity restrictions
     * @throws ClassCastException if the class of the specified element
     *         prevents it from being added to this deque
     * @throws NullPointerException if the specified element is null and this
     *         deque does not permit null elements
     * @throws IllegalArgumentException if some property of the specified
     *         element prevents it from being added to this deque
     */
    void push(E e);

    /**
     * Pops an element from the stack represented by this deque.  In other
     * words, removes and returns the first element of this deque.
     *
     * <p>This method is equivalent to {@link #removeFirst()}.
     *
     * <p>
     *  从此deque表示的堆栈中弹出一个元素换句话说,删除并返回此deque的第一个元素
     * 
     *  <p>此方法相当于{@link #removeFirst()}
     * 
     * 
     * @return the element at the front of this deque (which is the top
     *         of the stack represented by this deque)
     * @throws NoSuchElementException if this deque is empty
     */
    E pop();


    // *** Collection methods ***

    /**
     * Removes the first occurrence of the specified element from this deque.
     * If the deque does not contain the element, it is unchanged.
     * More formally, removes the first element {@code e} such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>
     * (if such an element exists).
     * Returns {@code true} if this deque contained the specified element
     * (or equivalently, if this deque changed as a result of the call).
     *
     * <p>This method is equivalent to {@link #removeFirstOccurrence(Object)}.
     *
     * <p>
     * 从该deque中删除指定元素的第一次出现如果deque不包含元素,则它不变。
     * 更正式地,删除第一个元素{@code e},使得<tt>(o == null& == null&nbsp;：oequals(e))</tt>(如果这样的元素存在)返回{@code true}如果这个de
     * que包含指定的元素(或者等效地,如果这个deque由于调用而改变)。
     * 从该deque中删除指定元素的第一次出现如果deque不包含元素,则它不变。
     * 
     *  <p>此方法等效于{@link #removeFirstOccurrence(Object)}
     * 
     * 
     * @param o element to be removed from this deque, if present
     * @return {@code true} if an element was removed as a result of this call
     * @throws ClassCastException if the class of the specified element
     *         is incompatible with this deque
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null and this
     *         deque does not permit null elements
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     */
    boolean remove(Object o);

    /**
     * Returns {@code true} if this deque contains the specified element.
     * More formally, returns {@code true} if and only if this deque contains
     * at least one element {@code e} such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
     *
     * <p>
     *  如果这个deque包含指定的元素,则返回{@code true}更正式地说,当且仅当这个deque包含至少一个元素{@code e}时,返回{@code true},使得<tt>(o == null&
     * nbsp; &nbsp; e == null&nbsp;：&nbsp; oequals(e))</tt>。
     * 
     * 
     * @param o element whose presence in this deque is to be tested
     * @return {@code true} if this deque contains the specified element
     * @throws ClassCastException if the type of the specified element
     *         is incompatible with this deque
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null and this
     *         deque does not permit null elements
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     */
    boolean contains(Object o);

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

    /**
     * Returns an iterator over the elements in this deque in reverse
     * sequential order.  The elements will be returned in order from
     * last (tail) to first (head).
     *
     * <p>
     *  以相反的顺序返回此deque中的元素上的迭代器元素将按照从last(尾)到first(头)的顺序返回,
     * 
     * @return an iterator over the elements in this deque in reverse
     * sequence
     */
    Iterator<E> descendingIterator();

}
