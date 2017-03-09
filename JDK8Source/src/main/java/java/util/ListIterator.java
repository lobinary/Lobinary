/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2011, Oracle and/or its affiliates. All rights reserved.
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

package java.util;

/**
 * An iterator for lists that allows the programmer
 * to traverse the list in either direction, modify
 * the list during iteration, and obtain the iterator's
 * current position in the list. A {@code ListIterator}
 * has no current element; its <I>cursor position</I> always
 * lies between the element that would be returned by a call
 * to {@code previous()} and the element that would be
 * returned by a call to {@code next()}.
 * An iterator for a list of length {@code n} has {@code n+1} possible
 * cursor positions, as illustrated by the carets ({@code ^}) below:
 * <PRE>
 *                      Element(0)   Element(1)   Element(2)   ... Element(n-1)
 * cursor positions:  ^            ^            ^            ^                  ^
 * </PRE>
 * Note that the {@link #remove} and {@link #set(Object)} methods are
 * <i>not</i> defined in terms of the cursor position;  they are defined to
 * operate on the last element returned by a call to {@link #next} or
 * {@link #previous()}.
 *
 * <p>This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * <p>
 *  列表的迭代器允许程序员在任一方向上遍历列表,在迭代期间修改列表,并获得列表中的迭代器的当前位置。
 *  {@code ListIterator}没有当前元素;它的<I>光标位置</I>总是位于通过调用{@code previous()}返回的元素和通过调用{@code next()}返回的元素之间。
 * 长度为{@code n}的列表的迭代器具有{@code n + 1}个可能的游标位置,如下面的插入符号({@code ^})所示：。
 * <PRE>
 *  元素(0)元素(1)元素(2)...元素(n-1)光标位置：^ ^ ^ ^ ^
 * </PRE>
 *  请注意,{@ link #remove}和{@link #set(Object)}方法不是</i>按光标位置定义;它们被定义为对调用{@link #next}或{@link #previous()}返
 * 回的最后一个元素进行操作。
 * 
 *  <p>此接口是的成员
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 *  Java集合框架</a>。
 * 
 * 
 * @author  Josh Bloch
 * @see Collection
 * @see List
 * @see Iterator
 * @see Enumeration
 * @see List#listIterator()
 * @since   1.2
 */
public interface ListIterator<E> extends Iterator<E> {
    // Query Operations

    /**
     * Returns {@code true} if this list iterator has more elements when
     * traversing the list in the forward direction. (In other words,
     * returns {@code true} if {@link #next} would return an element rather
     * than throwing an exception.)
     *
     * <p>
     *  如果此列表迭代器在向前方向上遍历列表时具有更多元素,则返回{@code true}。 (换句话说,如果{@link #next}返回一个元素而不是抛出异常,则返回{@code true}。)
     * 
     * 
     * @return {@code true} if the list iterator has more elements when
     *         traversing the list in the forward direction
     */
    boolean hasNext();

    /**
     * Returns the next element in the list and advances the cursor position.
     * This method may be called repeatedly to iterate through the list,
     * or intermixed with calls to {@link #previous} to go back and forth.
     * (Note that alternating calls to {@code next} and {@code previous}
     * will return the same element repeatedly.)
     *
     * <p>
     * 返回列表中的下一个元素并前进光标位置。可以重复调用此方法以遍历列表,或与对{@link #previous}的调用混合来回传递。
     *  (请注意,交替调用{@code next}和{@code previous}会重复返回相同的元素。)。
     * 
     * 
     * @return the next element in the list
     * @throws NoSuchElementException if the iteration has no next element
     */
    E next();

    /**
     * Returns {@code true} if this list iterator has more elements when
     * traversing the list in the reverse direction.  (In other words,
     * returns {@code true} if {@link #previous} would return an element
     * rather than throwing an exception.)
     *
     * <p>
     *  如果此列表迭代器在以相反方向遍历列表时具有更多元素,则返回{@code true}。 (换句话说,如果{@link #previous}返回一个元素而不是抛出异常,则返回{@code true}。
     * )。
     * 
     * 
     * @return {@code true} if the list iterator has more elements when
     *         traversing the list in the reverse direction
     */
    boolean hasPrevious();

    /**
     * Returns the previous element in the list and moves the cursor
     * position backwards.  This method may be called repeatedly to
     * iterate through the list backwards, or intermixed with calls to
     * {@link #next} to go back and forth.  (Note that alternating calls
     * to {@code next} and {@code previous} will return the same
     * element repeatedly.)
     *
     * <p>
     *  返回列表中的上一个元素,并向后移动光标位置。可以重复调用此方法以向后遍历列表,或与对{@link #next}的调用混合来回。
     *  (请注意,交替调用{@code next}和{@code previous}会重复返回相同的元素。)。
     * 
     * 
     * @return the previous element in the list
     * @throws NoSuchElementException if the iteration has no previous
     *         element
     */
    E previous();

    /**
     * Returns the index of the element that would be returned by a
     * subsequent call to {@link #next}. (Returns list size if the list
     * iterator is at the end of the list.)
     *
     * <p>
     *  返回由{@link #next}的后续调用返回的元素的索引。 (如果列表迭代器位于列表的末尾,则返回列表大小。)
     * 
     * 
     * @return the index of the element that would be returned by a
     *         subsequent call to {@code next}, or list size if the list
     *         iterator is at the end of the list
     */
    int nextIndex();

    /**
     * Returns the index of the element that would be returned by a
     * subsequent call to {@link #previous}. (Returns -1 if the list
     * iterator is at the beginning of the list.)
     *
     * <p>
     *  返回由{@link #previous}的后续调用返回的元素的索引。 (如果列表迭代器位于列表的开头,则返回-1。)
     * 
     * 
     * @return the index of the element that would be returned by a
     *         subsequent call to {@code previous}, or -1 if the list
     *         iterator is at the beginning of the list
     */
    int previousIndex();


    // Modification Operations

    /**
     * Removes from the list the last element that was returned by {@link
     * #next} or {@link #previous} (optional operation).  This call can
     * only be made once per call to {@code next} or {@code previous}.
     * It can be made only if {@link #add} has not been
     * called after the last call to {@code next} or {@code previous}.
     *
     * <p>
     * 从列表中删除{@link #next}或{@link #previous}(可选操作)返回的最后一个元素。此调用只能在每次调用{@code next}或{@code previous}时发出。
     * 只有在最后一次调用{@code next}或{@code previous}后未调用{@link #add}时,才能进行调用。
     * 
     * 
     * @throws UnsupportedOperationException if the {@code remove}
     *         operation is not supported by this list iterator
     * @throws IllegalStateException if neither {@code next} nor
     *         {@code previous} have been called, or {@code remove} or
     *         {@code add} have been called after the last call to
     *         {@code next} or {@code previous}
     */
    void remove();

    /**
     * Replaces the last element returned by {@link #next} or
     * {@link #previous} with the specified element (optional operation).
     * This call can be made only if neither {@link #remove} nor {@link
     * #add} have been called after the last call to {@code next} or
     * {@code previous}.
     *
     * <p>
     *  将{@link #next}或{@link #previous}返回的最后一个元素替换为指定的元素(可选操作)。
     * 只有在最后一次呼叫{@code next}或{@code previous}后,系统才会呼叫{@link #remove}或{@link #add},才能进行这项通话。
     * 
     * 
     * @param e the element with which to replace the last element returned by
     *          {@code next} or {@code previous}
     * @throws UnsupportedOperationException if the {@code set} operation
     *         is not supported by this list iterator
     * @throws ClassCastException if the class of the specified element
     *         prevents it from being added to this list
     * @throws IllegalArgumentException if some aspect of the specified
     *         element prevents it from being added to this list
     * @throws IllegalStateException if neither {@code next} nor
     *         {@code previous} have been called, or {@code remove} or
     *         {@code add} have been called after the last call to
     *         {@code next} or {@code previous}
     */
    void set(E e);

    /**
     * Inserts the specified element into the list (optional operation).
     * The element is inserted immediately before the element that
     * would be returned by {@link #next}, if any, and after the element
     * that would be returned by {@link #previous}, if any.  (If the
     * list contains no elements, the new element becomes the sole element
     * on the list.)  The new element is inserted before the implicit
     * cursor: a subsequent call to {@code next} would be unaffected, and a
     * subsequent call to {@code previous} would return the new element.
     * (This call increases by one the value that would be returned by a
     * call to {@code nextIndex} or {@code previousIndex}.)
     *
     * <p>
     *  将指定的元素插入列表(可选操作)。该元素插入在{@link #next}(如果有)返回的元素之前,以及{@link #previous}返回的元素(如果有)之前。
     *  (如果列表不包含元素,则新元素将成为列表上的唯一元素。)新元素插入隐式游标之前：对{@code next}的后续调用不受影响,并且随后调用{@ code previous}将返回新的元素。
     * 
     * @param e the element to insert
     * @throws UnsupportedOperationException if the {@code add} method is
     *         not supported by this list iterator
     * @throws ClassCastException if the class of the specified element
     *         prevents it from being added to this list
     * @throws IllegalArgumentException if some aspect of this element
     *         prevents it from being added to this list
     */
    void add(E e);
}
