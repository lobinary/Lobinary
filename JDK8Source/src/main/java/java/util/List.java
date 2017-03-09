/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2014, Oracle and/or its affiliates. All rights reserved.
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

import java.util.function.UnaryOperator;

/**
 * An ordered collection (also known as a <i>sequence</i>).  The user of this
 * interface has precise control over where in the list each element is
 * inserted.  The user can access elements by their integer index (position in
 * the list), and search for elements in the list.<p>
 *
 * Unlike sets, lists typically allow duplicate elements.  More formally,
 * lists typically allow pairs of elements <tt>e1</tt> and <tt>e2</tt>
 * such that <tt>e1.equals(e2)</tt>, and they typically allow multiple
 * null elements if they allow null elements at all.  It is not inconceivable
 * that someone might wish to implement a list that prohibits duplicates, by
 * throwing runtime exceptions when the user attempts to insert them, but we
 * expect this usage to be rare.<p>
 *
 * The <tt>List</tt> interface places additional stipulations, beyond those
 * specified in the <tt>Collection</tt> interface, on the contracts of the
 * <tt>iterator</tt>, <tt>add</tt>, <tt>remove</tt>, <tt>equals</tt>, and
 * <tt>hashCode</tt> methods.  Declarations for other inherited methods are
 * also included here for convenience.<p>
 *
 * The <tt>List</tt> interface provides four methods for positional (indexed)
 * access to list elements.  Lists (like Java arrays) are zero based.  Note
 * that these operations may execute in time proportional to the index value
 * for some implementations (the <tt>LinkedList</tt> class, for
 * example). Thus, iterating over the elements in a list is typically
 * preferable to indexing through it if the caller does not know the
 * implementation.<p>
 *
 * The <tt>List</tt> interface provides a special iterator, called a
 * <tt>ListIterator</tt>, that allows element insertion and replacement, and
 * bidirectional access in addition to the normal operations that the
 * <tt>Iterator</tt> interface provides.  A method is provided to obtain a
 * list iterator that starts at a specified position in the list.<p>
 *
 * The <tt>List</tt> interface provides two methods to search for a specified
 * object.  From a performance standpoint, these methods should be used with
 * caution.  In many implementations they will perform costly linear
 * searches.<p>
 *
 * The <tt>List</tt> interface provides two methods to efficiently insert and
 * remove multiple elements at an arbitrary point in the list.<p>
 *
 * Note: While it is permissible for lists to contain themselves as elements,
 * extreme caution is advised: the <tt>equals</tt> and <tt>hashCode</tt>
 * methods are no longer well defined on such a list.
 *
 * <p>Some list implementations have restrictions on the elements that
 * they may contain.  For example, some implementations prohibit null elements,
 * and some have restrictions on the types of their elements.  Attempting to
 * add an ineligible element throws an unchecked exception, typically
 * <tt>NullPointerException</tt> or <tt>ClassCastException</tt>.  Attempting
 * to query the presence of an ineligible element may throw an exception,
 * or it may simply return false; some implementations will exhibit the former
 * behavior and some will exhibit the latter.  More generally, attempting an
 * operation on an ineligible element whose completion would not result in
 * the insertion of an ineligible element into the list may throw an
 * exception or it may succeed, at the option of the implementation.
 * Such exceptions are marked as "optional" in the specification for this
 * interface.
 *
 * <p>This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * <p>
 *  有序集合(也称为<i>序列</i>)。此接口的用户可以精确控制列表中每个元素的插入位置。用户可以通过整数索引(列表中的位置)访问元素,并搜索列表中的元素。<p>
 * 
 *  与集合不同,列表通常允许重复元素。
 * 更正式地,列表通常允许元素对<tt> e1 </tt>和<tt> e2 </tt>使得<tt> e1.equals(e2)</tt>,并且它们通常允许多个空元素if它们允许空元素。
 * 有人可能希望实现一个禁止重复的列表,通过在用户尝试插入它们时抛出运行时异常,但我们预期这种使用是罕见的,这是不可想象的。
 * 
 *  除了<tt>集合</tt>界面中指定的<tt>列表</tt>界面,<tt>列表</tt>界面对<tt>迭代器</tt>,<tt>添加</tt >,<tt> remove </tt>,<tt> equa
 * ls </tt>和<tt> hashCode </tt>为方便起见,此处还包括其他继承方法的声明。
 * <p>。
 * 
 * <tt> List </tt>界面提供了四种位置(索引)访问列表元素的方法。列表(如Java数组)基于零。
 * 注意,这些操作可以在与某些实现(例如,<tt> LinkedList </tt>类)的索引值成比例的时间内执行。因此,如果调用者不知道实现,则遍历列表中的元素通常优于对其进行索引。<p>。
 * 
 *  <tt> List </tt>接口提供了一个特殊的迭代器,称为<tt> ListIterator </tt>,允许元素插入和替换,以及双向访问,除了正常操作,<tt> tt>接口提供。
 * 提供一种方法以获得在列表中的指定位置处开始的列表迭代器。
 * 
 *  <tt> List </tt>界面提供了两种方法来搜索指定的对象。从性能的角度来看,这些方法应谨慎使用。在许多实现中,它们将执行昂贵的线性搜索
 * 
 *  <tt> List </tt>界面提供了两种方法,可以在列表中的任意点有效插入和删除多个元素。<p>
 * 
 *  注意：虽然允许列表将自身作为元素,但建议注意：<tt> equals </tt>和<tt> hashCode </tt>方法在这样的列表中不再被很好地定义。
 * 
 * <p>一些列表实现对它们可能包含的元素有限制。例如,一些实现禁止空元素,并且一些实现对它们的元素的类型具有限制。
 * 尝试添加不合格元素会抛出未检查的异常,通常为<tt> NullPointerException </tt>或<tt> ClassCastException </tt>。
 * 尝试查询不合格元素的存在可能会抛出异常,或者它可能只是返回false;一些实现将展示前一行为,一些将展示后者。
 * 更一般地,尝试对不合格元素的操作可以抛出异常,或者其可以成功,在执行的选择时,其完成不会导致不合格元素插入列表中。这种异常在此接口的规范中标记为"可选"。
 * 
 *  <p>此接口是的成员
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 *  Java集合框架</a>。
 * 
 * 
 * @param <E> the type of elements in this list
 *
 * @author  Josh Bloch
 * @author  Neal Gafter
 * @see Collection
 * @see Set
 * @see ArrayList
 * @see LinkedList
 * @see Vector
 * @see Arrays#asList(Object[])
 * @see Collections#nCopies(int, Object)
 * @see Collections#EMPTY_LIST
 * @see AbstractList
 * @see AbstractSequentialList
 * @since 1.2
 */

public interface List<E> extends Collection<E> {
    // Query Operations

    /**
     * Returns the number of elements in this list.  If this list contains
     * more than <tt>Integer.MAX_VALUE</tt> elements, returns
     * <tt>Integer.MAX_VALUE</tt>.
     *
     * <p>
     *  返回此列表中的元素数。如果此列表包含的元素超过<tt> Integer.MAX_VALUE </tt>,则返回<tt> Integer.MAX_VALUE </tt>。
     * 
     * 
     * @return the number of elements in this list
     */
    int size();

    /**
     * Returns <tt>true</tt> if this list contains no elements.
     *
     * <p>
     *  如果此列表不包含元素,则返回<tt> true </tt>。
     * 
     * 
     * @return <tt>true</tt> if this list contains no elements
     */
    boolean isEmpty();

    /**
     * Returns <tt>true</tt> if this list contains the specified element.
     * More formally, returns <tt>true</tt> if and only if this list contains
     * at least one element <tt>e</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
     *
     * <p>
     *  如果此列表包含指定的元素,则返回<tt> true </tt>。
     * 更正式地,如果且仅当此列表包含至少一个元素<tt> e </tt>,使得<tt>(o == null&nbsp;?&nbsp; e == null&nbsp; ：&nbsp; o.equals(e))</tt>
     * 。
     *  如果此列表包含指定的元素,则返回<tt> true </tt>。
     * 
     * 
     * @param o element whose presence in this list is to be tested
     * @return <tt>true</tt> if this list contains the specified element
     * @throws ClassCastException if the type of the specified element
     *         is incompatible with this list
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null and this
     *         list does not permit null elements
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     */
    boolean contains(Object o);

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     *
     * <p>
     *  以正确的顺序返回此列表中的元素的迭代器。
     * 
     * 
     * @return an iterator over the elements in this list in proper sequence
     */
    Iterator<E> iterator();

    /**
     * Returns an array containing all of the elements in this list in proper
     * sequence (from first to last element).
     *
     * <p>The returned array will be "safe" in that no references to it are
     * maintained by this list.  (In other words, this method must
     * allocate a new array even if this list is backed by an array).
     * The caller is thus free to modify the returned array.
     *
     * <p>This method acts as bridge between array-based and collection-based
     * APIs.
     *
     * <p>
     * 以正确的顺序返回包含此列表中所有元素的数组(从第一个元素到最后一个元素)。
     * 
     *  <p>返回的数组将是"安全的",因为没有对它的引用由此列表维护。 (换句话说,这个方法必须分配一个新数组,即使这个列表是由数组支持的)。因此调用者可以自由地修改返回的数组。
     * 
     *  <p>此方法充当基于阵列和基于集合的API之间的桥梁。
     * 
     * 
     * @return an array containing all of the elements in this list in proper
     *         sequence
     * @see Arrays#asList(Object[])
     */
    Object[] toArray();

    /**
     * Returns an array containing all of the elements in this list in
     * proper sequence (from first to last element); the runtime type of
     * the returned array is that of the specified array.  If the list fits
     * in the specified array, it is returned therein.  Otherwise, a new
     * array is allocated with the runtime type of the specified array and
     * the size of this list.
     *
     * <p>If the list fits in the specified array with room to spare (i.e.,
     * the array has more elements than the list), the element in the array
     * immediately following the end of the list is set to <tt>null</tt>.
     * (This is useful in determining the length of the list <i>only</i> if
     * the caller knows that the list does not contain any null elements.)
     *
     * <p>Like the {@link #toArray()} method, this method acts as bridge between
     * array-based and collection-based APIs.  Further, this method allows
     * precise control over the runtime type of the output array, and may,
     * under certain circumstances, be used to save allocation costs.
     *
     * <p>Suppose <tt>x</tt> is a list known to contain only strings.
     * The following code can be used to dump the list into a newly
     * allocated array of <tt>String</tt>:
     *
     * <pre>{@code
     *     String[] y = x.toArray(new String[0]);
     * }</pre>
     *
     * Note that <tt>toArray(new Object[0])</tt> is identical in function to
     * <tt>toArray()</tt>.
     *
     * <p>
     *  返回一个包含正确顺序(从第一个元素到最后一个元素)的列表中所有元素的数组。返回的数组的运行时类型是指定数组的运行时类型。如果列表适合指定的数组,则返回其中。
     * 否则,将使用指定数组的运行时类型和此列表的大小分配新数组。
     * 
     *  <p>如果列表符合指定的空余空间(即,数组具有比列表更多的元素),则紧接列表结尾的数组中的元素将设置为<tt> null </tt >。
     *  (如果调用者知道列表不包含任何空元素,则这在确定列表</i>的长度时非常有用。)。
     * 
     *  <p>与{@link #toArray()}方法类似,此方法充当基于数组和基于集合的API之间的桥梁。此外,该方法允许对输出阵列的运行时类型的精确控制,并且在某些情况下可以用于节省分配成本。
     * 
     * <p>假设<tt> x </tt>是一个已知只包含字符串的列表。以下代码可用于将列表转储到新分配的<tt> String </tt>数组中：
     * 
     *  <pre> {@ code String [] y = x.toArray(new String [0]); } </pre>
     * 
     *  注意,<tt> toArray(new Object [0])</tt>在功能上与<tt> toArray()</tt>相同。
     * 
     * 
     * @param a the array into which the elements of this list are to
     *          be stored, if it is big enough; otherwise, a new array of the
     *          same runtime type is allocated for this purpose.
     * @return an array containing the elements of this list
     * @throws ArrayStoreException if the runtime type of the specified array
     *         is not a supertype of the runtime type of every element in
     *         this list
     * @throws NullPointerException if the specified array is null
     */
    <T> T[] toArray(T[] a);


    // Modification Operations

    /**
     * Appends the specified element to the end of this list (optional
     * operation).
     *
     * <p>Lists that support this operation may place limitations on what
     * elements may be added to this list.  In particular, some
     * lists will refuse to add null elements, and others will impose
     * restrictions on the type of elements that may be added.  List
     * classes should clearly specify in their documentation any restrictions
     * on what elements may be added.
     *
     * <p>
     *  将指定的元素追加到此列表的末尾(可选操作)。
     * 
     *  <p>支持此操作的列表可能会对可能添加到此列表的元素设置限制。特别地,一些列表将拒绝添加空元素,并且其他列表将对可以添加的元素的类型施加限制。
     * 列表类应该在其文档中清楚地指定对可以添加什么元素的任何限制。
     * 
     * 
     * @param e element to be appended to this list
     * @return <tt>true</tt> (as specified by {@link Collection#add})
     * @throws UnsupportedOperationException if the <tt>add</tt> operation
     *         is not supported by this list
     * @throws ClassCastException if the class of the specified element
     *         prevents it from being added to this list
     * @throws NullPointerException if the specified element is null and this
     *         list does not permit null elements
     * @throws IllegalArgumentException if some property of this element
     *         prevents it from being added to this list
     */
    boolean add(E e);

    /**
     * Removes the first occurrence of the specified element from this list,
     * if it is present (optional operation).  If this list does not contain
     * the element, it is unchanged.  More formally, removes the element with
     * the lowest index <tt>i</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>
     * (if such an element exists).  Returns <tt>true</tt> if this list
     * contained the specified element (or equivalently, if this list changed
     * as a result of the call).
     *
     * <p>
     *  从该列表中删除指定元素的第一次出现(如果存在)(可选操作)。如果此列表不包含元素,则不会更改。
     * 更正式地,删除最低索引<tt> i </tt>的元素,使得<tt>(o == null&nbsp;?&nbsp; get(i)== null&nbsp;：& )))</tt>(如果这样的元素存在)。
     * 如果此列表包含指定的元素(或等效地,如果此列表作为调用的结果而更改),则返回<tt> true </tt>。
     * 
     * 
     * @param o element to be removed from this list, if present
     * @return <tt>true</tt> if this list contained the specified element
     * @throws ClassCastException if the type of the specified element
     *         is incompatible with this list
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null and this
     *         list does not permit null elements
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws UnsupportedOperationException if the <tt>remove</tt> operation
     *         is not supported by this list
     */
    boolean remove(Object o);


    // Bulk Modification Operations

    /**
     * Returns <tt>true</tt> if this list contains all of the elements of the
     * specified collection.
     *
     * <p>
     *  如果此列表包含指定集合的​​所有元素,则返回<tt> true </tt>。
     * 
     * 
     * @param  c collection to be checked for containment in this list
     * @return <tt>true</tt> if this list contains all of the elements of the
     *         specified collection
     * @throws ClassCastException if the types of one or more elements
     *         in the specified collection are incompatible with this
     *         list
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified collection contains one
     *         or more null elements and this list does not permit null
     *         elements
     *         (<a href="Collection.html#optional-restrictions">optional</a>),
     *         or if the specified collection is null
     * @see #contains(Object)
     */
    boolean containsAll(Collection<?> c);

    /**
     * Appends all of the elements in the specified collection to the end of
     * this list, in the order that they are returned by the specified
     * collection's iterator (optional operation).  The behavior of this
     * operation is undefined if the specified collection is modified while
     * the operation is in progress.  (Note that this will occur if the
     * specified collection is this list, and it's nonempty.)
     *
     * <p>
     * 将指定集合中的所有元素以指定集合的​​迭代器(可选操作)返回的顺序追加到此列表的末尾。如果在操作正在进行时修改指定的集合,则此操作的行为是未定义的。
     *  (请注意,如果指定的集合是此列表,并且它是非空的,则会发生这种情况。)。
     * 
     * 
     * @param c collection containing elements to be added to this list
     * @return <tt>true</tt> if this list changed as a result of the call
     * @throws UnsupportedOperationException if the <tt>addAll</tt> operation
     *         is not supported by this list
     * @throws ClassCastException if the class of an element of the specified
     *         collection prevents it from being added to this list
     * @throws NullPointerException if the specified collection contains one
     *         or more null elements and this list does not permit null
     *         elements, or if the specified collection is null
     * @throws IllegalArgumentException if some property of an element of the
     *         specified collection prevents it from being added to this list
     * @see #add(Object)
     */
    boolean addAll(Collection<? extends E> c);

    /**
     * Inserts all of the elements in the specified collection into this
     * list at the specified position (optional operation).  Shifts the
     * element currently at that position (if any) and any subsequent
     * elements to the right (increases their indices).  The new elements
     * will appear in this list in the order that they are returned by the
     * specified collection's iterator.  The behavior of this operation is
     * undefined if the specified collection is modified while the
     * operation is in progress.  (Note that this will occur if the specified
     * collection is this list, and it's nonempty.)
     *
     * <p>
     *  将指定集合中的所有元素插入到此列表的指定位置(可选操作)。将当前在该位置的元素(如果有)和任何后续元素向右移动(增加其索引)。新元素将按照它们由指定集合的​​迭代器返回的顺序显示在此列表中。
     * 如果在操作正在进行时修改指定的集合,则此操作的行为是未定义的。 (请注意,如果指定的集合是此列表,并且它是非空的,则会发生这种情况。)。
     * 
     * 
     * @param index index at which to insert the first element from the
     *              specified collection
     * @param c collection containing elements to be added to this list
     * @return <tt>true</tt> if this list changed as a result of the call
     * @throws UnsupportedOperationException if the <tt>addAll</tt> operation
     *         is not supported by this list
     * @throws ClassCastException if the class of an element of the specified
     *         collection prevents it from being added to this list
     * @throws NullPointerException if the specified collection contains one
     *         or more null elements and this list does not permit null
     *         elements, or if the specified collection is null
     * @throws IllegalArgumentException if some property of an element of the
     *         specified collection prevents it from being added to this list
     * @throws IndexOutOfBoundsException if the index is out of range
     *         (<tt>index &lt; 0 || index &gt; size()</tt>)
     */
    boolean addAll(int index, Collection<? extends E> c);

    /**
     * Removes from this list all of its elements that are contained in the
     * specified collection (optional operation).
     *
     * <p>
     *  从此列表中删除包含在指定集合中的所有元素(可选操作)。
     * 
     * 
     * @param c collection containing elements to be removed from this list
     * @return <tt>true</tt> if this list changed as a result of the call
     * @throws UnsupportedOperationException if the <tt>removeAll</tt> operation
     *         is not supported by this list
     * @throws ClassCastException if the class of an element of this list
     *         is incompatible with the specified collection
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if this list contains a null element and the
     *         specified collection does not permit null elements
     *         (<a href="Collection.html#optional-restrictions">optional</a>),
     *         or if the specified collection is null
     * @see #remove(Object)
     * @see #contains(Object)
     */
    boolean removeAll(Collection<?> c);

    /**
     * Retains only the elements in this list that are contained in the
     * specified collection (optional operation).  In other words, removes
     * from this list all of its elements that are not contained in the
     * specified collection.
     *
     * <p>
     *  仅保留此列表中包含在指定集合中的元素(可选操作)。换句话说,从此列表中删除未包含在指定集合中的所有元素。
     * 
     * 
     * @param c collection containing elements to be retained in this list
     * @return <tt>true</tt> if this list changed as a result of the call
     * @throws UnsupportedOperationException if the <tt>retainAll</tt> operation
     *         is not supported by this list
     * @throws ClassCastException if the class of an element of this list
     *         is incompatible with the specified collection
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if this list contains a null element and the
     *         specified collection does not permit null elements
     *         (<a href="Collection.html#optional-restrictions">optional</a>),
     *         or if the specified collection is null
     * @see #remove(Object)
     * @see #contains(Object)
     */
    boolean retainAll(Collection<?> c);

    /**
     * Replaces each element of this list with the result of applying the
     * operator to that element.  Errors or runtime exceptions thrown by
     * the operator are relayed to the caller.
     *
     * @implSpec
     * The default implementation is equivalent to, for this {@code list}:
     * <pre>{@code
     *     final ListIterator<E> li = list.listIterator();
     *     while (li.hasNext()) {
     *         li.set(operator.apply(li.next()));
     *     }
     * }</pre>
     *
     * If the list's list-iterator does not support the {@code set} operation
     * then an {@code UnsupportedOperationException} will be thrown when
     * replacing the first element.
     *
     * <p>
     *  用将运算符应用于该列表的结果替换此列表中的每个元素。操作员抛出的错误或运行时异常被中继到调用者。
     * 
     * @implSpec对于这个{@code list},默认实现是等价的：<pre> {@ code final ListIterator <E> li = list.listIterator(); while(li.hasNext()){li.set(operator.apply(li.next())); }
     * } </pre>。
     * 
     *  如果列表的列表迭代器不支持{@code set}操作,那么在替换第一个元素时将抛出{@code UnsupportedOperationException}。
     * 
     * 
     * @param operator the operator to apply to each element
     * @throws UnsupportedOperationException if this list is unmodifiable.
     *         Implementations may throw this exception if an element
     *         cannot be replaced or if, in general, modification is not
     *         supported
     * @throws NullPointerException if the specified operator is null or
     *         if the operator result is a null value and this list does
     *         not permit null elements
     *         (<a href="Collection.html#optional-restrictions">optional</a>)
     * @since 1.8
     */
    default void replaceAll(UnaryOperator<E> operator) {
        Objects.requireNonNull(operator);
        final ListIterator<E> li = this.listIterator();
        while (li.hasNext()) {
            li.set(operator.apply(li.next()));
        }
    }

    /**
     * Sorts this list according to the order induced by the specified
     * {@link Comparator}.
     *
     * <p>All elements in this list must be <i>mutually comparable</i> using the
     * specified comparator (that is, {@code c.compare(e1, e2)} must not throw
     * a {@code ClassCastException} for any elements {@code e1} and {@code e2}
     * in the list).
     *
     * <p>If the specified comparator is {@code null} then all elements in this
     * list must implement the {@link Comparable} interface and the elements'
     * {@linkplain Comparable natural ordering} should be used.
     *
     * <p>This list must be modifiable, but need not be resizable.
     *
     * @implSpec
     * The default implementation obtains an array containing all elements in
     * this list, sorts the array, and iterates over this list resetting each
     * element from the corresponding position in the array. (This avoids the
     * n<sup>2</sup> log(n) performance that would result from attempting
     * to sort a linked list in place.)
     *
     * @implNote
     * This implementation is a stable, adaptive, iterative mergesort that
     * requires far fewer than n lg(n) comparisons when the input array is
     * partially sorted, while offering the performance of a traditional
     * mergesort when the input array is randomly ordered.  If the input array
     * is nearly sorted, the implementation requires approximately n
     * comparisons.  Temporary storage requirements vary from a small constant
     * for nearly sorted input arrays to n/2 object references for randomly
     * ordered input arrays.
     *
     * <p>The implementation takes equal advantage of ascending and
     * descending order in its input array, and can take advantage of
     * ascending and descending order in different parts of the same
     * input array.  It is well-suited to merging two or more sorted arrays:
     * simply concatenate the arrays and sort the resulting array.
     *
     * <p>The implementation was adapted from Tim Peters's list sort for Python
     * (<a href="http://svn.python.org/projects/python/trunk/Objects/listsort.txt">
     * TimSort</a>).  It uses techniques from Peter McIlroy's "Optimistic
     * Sorting and Information Theoretic Complexity", in Proceedings of the
     * Fourth Annual ACM-SIAM Symposium on Discrete Algorithms, pp 467-474,
     * January 1993.
     *
     * <p>
     *  根据指定的{@link比较器}引起的顺序对此列表进行排序。
     * 
     *  <p>此列表中的所有元素必须<i>相互比较</i>(使用指定的比较器)(即{@code c.compare(e1,e2)}不得为任何{@code ClassCastException}元素{@code e1}
     * 和{@code e2})。
     * 
     *  <p>如果指定的比较器是{@code null},则此列表中的所有元素都必须实现{@link Comparable}接口,并且应使用元素的{@linkplain Comparable natural ordering}
     * 。
     * 
     *  <p>此列表必须可修改,但不必可调整大小。
     * 
     *  @implSpec默认实现获得一个包含此列表中所有元素的数组,对数组进行排序,并重复该列表,重置数组中相应位置的每个元素。
     *  (这避免了由于试图对链表进行排序而导致的n <sup> 2 </sup> log(n)性能)。
     * 
     * @implNote这个实现是一个稳定的,自适应的迭代合并集,当输入数组被部分排序时,需要远小于n lg(n)的比较,同时在输入数组随机排序时提供传统合并集的性能。
     * 如果输入数组接近排序,实现需要大约n个比较。临时存储要求从随机排序的输入数组的小常数到随机排序的输入数组的n / 2对象引用。
     * 
     *  <p>该实现在其输入数组中具有升序和降序的同等优点,并且可以利用同一输入数组的不同部分中的升序和降序。它非常适合合并两个或多个排序的数组：简单地连接数组并对结果数组进行排序。
     * 
     *  <p>实施方案改编自Tim Peters的Python列表排序(<a href="http://svn.python.org/projects/python/trunk/Objects/listsort.txt">
     *  TimSort </a>)。
     * 它使用来自Peter McIlroy的"Optimistic Sorting and Information Theoretic Complexity",Proceedings of the Fourt
     * h Annual ACM-SIAM Symposium on Discrete Algorithms,pp 467-474,1993年1月的技术。
     * 
     * 
     * @param c the {@code Comparator} used to compare list elements.
     *          A {@code null} value indicates that the elements'
     *          {@linkplain Comparable natural ordering} should be used
     * @throws ClassCastException if the list contains elements that are not
     *         <i>mutually comparable</i> using the specified comparator
     * @throws UnsupportedOperationException if the list's list-iterator does
     *         not support the {@code set} operation
     * @throws IllegalArgumentException
     *         (<a href="Collection.html#optional-restrictions">optional</a>)
     *         if the comparator is found to violate the {@link Comparator}
     *         contract
     * @since 1.8
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    default void sort(Comparator<? super E> c) {
        Object[] a = this.toArray();
        Arrays.sort(a, (Comparator) c);
        ListIterator<E> i = this.listIterator();
        for (Object e : a) {
            i.next();
            i.set((E) e);
        }
    }

    /**
     * Removes all of the elements from this list (optional operation).
     * The list will be empty after this call returns.
     *
     * <p>
     *  从此列表中删除所有元素(可选操作)。此调用返回后,列表将为空。
     * 
     * 
     * @throws UnsupportedOperationException if the <tt>clear</tt> operation
     *         is not supported by this list
     */
    void clear();


    // Comparison and hashing

    /**
     * Compares the specified object with this list for equality.  Returns
     * <tt>true</tt> if and only if the specified object is also a list, both
     * lists have the same size, and all corresponding pairs of elements in
     * the two lists are <i>equal</i>.  (Two elements <tt>e1</tt> and
     * <tt>e2</tt> are <i>equal</i> if <tt>(e1==null ? e2==null :
     * e1.equals(e2))</tt>.)  In other words, two lists are defined to be
     * equal if they contain the same elements in the same order.  This
     * definition ensures that the equals method works properly across
     * different implementations of the <tt>List</tt> interface.
     *
     * <p>
     * 将指定的对象与此列表进行比较以确保相等。当且仅当指定的对象也是列表时,返回<tt> true </tt>,两个列表具有相同的大小,并且两个列表中的所有对应的元素对都是<i>等于</i>。
     *  (如果<tt>(e1 == null?e2 == null：e1.equals(e2)),则两个元素<tt> e1 </tt>和<tt> e2 </tt> )</tt>。
     * )换句话说,如果两个列表包含相同顺序的相同元素,则它们被定义为相等。此定义确保equals方法在<tt> List </tt>接口的不同实现中正常工作。
     * 
     * 
     * @param o the object to be compared for equality with this list
     * @return <tt>true</tt> if the specified object is equal to this list
     */
    boolean equals(Object o);

    /**
     * Returns the hash code value for this list.  The hash code of a list
     * is defined to be the result of the following calculation:
     * <pre>{@code
     *     int hashCode = 1;
     *     for (E e : list)
     *         hashCode = 31*hashCode + (e==null ? 0 : e.hashCode());
     * }</pre>
     * This ensures that <tt>list1.equals(list2)</tt> implies that
     * <tt>list1.hashCode()==list2.hashCode()</tt> for any two lists,
     * <tt>list1</tt> and <tt>list2</tt>, as required by the general
     * contract of {@link Object#hashCode}.
     *
     * <p>
     *  返回此列表的哈希码值。
     * 列表的哈希码被定义为以下计算的结果：<pre> {@ code int hashCode = 1; for(E e：list)hashCode = 31 * hashCode +(e == null?0：e.hashCode()); }
     *  </t>这确保对于任何两个列表,<tt> list1.equals(list2)</tt>意味着<tt> list1.hashCode()== list2.hashCode list1 </tt>和<tt>
     *  list2 </tt>,根据{@link Object#hashCode}的一般合同的要求。
     *  返回此列表的哈希码值。
     * 
     * 
     * @return the hash code value for this list
     * @see Object#equals(Object)
     * @see #equals(Object)
     */
    int hashCode();


    // Positional Access Operations

    /**
     * Returns the element at the specified position in this list.
     *
     * <p>
     *  返回此列表中指定位置的元素。
     * 
     * 
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range
     *         (<tt>index &lt; 0 || index &gt;= size()</tt>)
     */
    E get(int index);

    /**
     * Replaces the element at the specified position in this list with the
     * specified element (optional operation).
     *
     * <p>
     *  用指定的元素替换此列表中指定位置处的元素(可选操作)。
     * 
     * 
     * @param index index of the element to replace
     * @param element element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws UnsupportedOperationException if the <tt>set</tt> operation
     *         is not supported by this list
     * @throws ClassCastException if the class of the specified element
     *         prevents it from being added to this list
     * @throws NullPointerException if the specified element is null and
     *         this list does not permit null elements
     * @throws IllegalArgumentException if some property of the specified
     *         element prevents it from being added to this list
     * @throws IndexOutOfBoundsException if the index is out of range
     *         (<tt>index &lt; 0 || index &gt;= size()</tt>)
     */
    E set(int index, E element);

    /**
     * Inserts the specified element at the specified position in this list
     * (optional operation).  Shifts the element currently at that position
     * (if any) and any subsequent elements to the right (adds one to their
     * indices).
     *
     * <p>
     *  在此列表的指定位置插入指定的元素(可选操作)。将当前在该位置的元素(如果有)和任何后续元素向右移(将一个添加到它们的索引)。
     * 
     * 
     * @param index index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws UnsupportedOperationException if the <tt>add</tt> operation
     *         is not supported by this list
     * @throws ClassCastException if the class of the specified element
     *         prevents it from being added to this list
     * @throws NullPointerException if the specified element is null and
     *         this list does not permit null elements
     * @throws IllegalArgumentException if some property of the specified
     *         element prevents it from being added to this list
     * @throws IndexOutOfBoundsException if the index is out of range
     *         (<tt>index &lt; 0 || index &gt; size()</tt>)
     */
    void add(int index, E element);

    /**
     * Removes the element at the specified position in this list (optional
     * operation).  Shifts any subsequent elements to the left (subtracts one
     * from their indices).  Returns the element that was removed from the
     * list.
     *
     * <p>
     * 删除此列表中指定位置处的元素(可选操作)。将任何后续元素向左移(从它们的索引中减去一个)。返回从列表中删除的元素。
     * 
     * 
     * @param index the index of the element to be removed
     * @return the element previously at the specified position
     * @throws UnsupportedOperationException if the <tt>remove</tt> operation
     *         is not supported by this list
     * @throws IndexOutOfBoundsException if the index is out of range
     *         (<tt>index &lt; 0 || index &gt;= size()</tt>)
     */
    E remove(int index);


    // Search Operations

    /**
     * Returns the index of the first occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     * More formally, returns the lowest index <tt>i</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>,
     * or -1 if there is no such index.
     *
     * <p>
     *  返回此列表中指定元素的第一次出现的索引,如果此列表不包含元素,则返回-1。
     * 更正式地,返回最低索引<tt> i </tt>,使得<tt>(o == null&nbsp;?&nbsp; get(i)== null&nbsp;：&nbsp; o.equals(get(i))) </tt>
     * ,如果没有这样的索引,则为-1。
     *  返回此列表中指定元素的第一次出现的索引,如果此列表不包含元素,则返回-1。
     * 
     * 
     * @param o element to search for
     * @return the index of the first occurrence of the specified element in
     *         this list, or -1 if this list does not contain the element
     * @throws ClassCastException if the type of the specified element
     *         is incompatible with this list
     *         (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null and this
     *         list does not permit null elements
     *         (<a href="Collection.html#optional-restrictions">optional</a>)
     */
    int indexOf(Object o);

    /**
     * Returns the index of the last occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     * More formally, returns the highest index <tt>i</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>,
     * or -1 if there is no such index.
     *
     * <p>
     *  返回此列表中指定元素的最后一次出现的索引,如果此列表不包含元素,则返回-1。
     * 更正式地,返回最高索引<tt> i </tt>,使得<tt>(o == null&nbsp;?&nbsp; get(i)== null&nbsp;：&nbsp; o.equals(get(i))) </tt>
     * ,如果没有这样的索引,则为-1。
     *  返回此列表中指定元素的最后一次出现的索引,如果此列表不包含元素,则返回-1。
     * 
     * 
     * @param o element to search for
     * @return the index of the last occurrence of the specified element in
     *         this list, or -1 if this list does not contain the element
     * @throws ClassCastException if the type of the specified element
     *         is incompatible with this list
     *         (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null and this
     *         list does not permit null elements
     *         (<a href="Collection.html#optional-restrictions">optional</a>)
     */
    int lastIndexOf(Object o);


    // List Iterators

    /**
     * Returns a list iterator over the elements in this list (in proper
     * sequence).
     *
     * <p>
     *  返回此列表中的元素(按正确顺序)的列表迭代器。
     * 
     * 
     * @return a list iterator over the elements in this list (in proper
     *         sequence)
     */
    ListIterator<E> listIterator();

    /**
     * Returns a list iterator over the elements in this list (in proper
     * sequence), starting at the specified position in the list.
     * The specified index indicates the first element that would be
     * returned by an initial call to {@link ListIterator#next next}.
     * An initial call to {@link ListIterator#previous previous} would
     * return the element with the specified index minus one.
     *
     * <p>
     *  对列表中的元素返回一个列表迭代器(以正确的顺序),从列表中指定的位置开始。指定的索引表示由初始调用{@link ListIterator#next next}返回的第一个元素。
     * 对{@link ListIterator#previous previous}的初始调用将返回具有指定索引减1的元素。
     * 
     * 
     * @param index index of the first element to be returned from the
     *        list iterator (by a call to {@link ListIterator#next next})
     * @return a list iterator over the elements in this list (in proper
     *         sequence), starting at the specified position in the list
     * @throws IndexOutOfBoundsException if the index is out of range
     *         ({@code index < 0 || index > size()})
     */
    ListIterator<E> listIterator(int index);

    // View

    /**
     * Returns a view of the portion of this list between the specified
     * <tt>fromIndex</tt>, inclusive, and <tt>toIndex</tt>, exclusive.  (If
     * <tt>fromIndex</tt> and <tt>toIndex</tt> are equal, the returned list is
     * empty.)  The returned list is backed by this list, so non-structural
     * changes in the returned list are reflected in this list, and vice-versa.
     * The returned list supports all of the optional list operations supported
     * by this list.<p>
     *
     * This method eliminates the need for explicit range operations (of
     * the sort that commonly exist for arrays).  Any operation that expects
     * a list can be used as a range operation by passing a subList view
     * instead of a whole list.  For example, the following idiom
     * removes a range of elements from a list:
     * <pre>{@code
     *      list.subList(from, to).clear();
     * }</pre>
     * Similar idioms may be constructed for <tt>indexOf</tt> and
     * <tt>lastIndexOf</tt>, and all of the algorithms in the
     * <tt>Collections</tt> class can be applied to a subList.<p>
     *
     * The semantics of the list returned by this method become undefined if
     * the backing list (i.e., this list) is <i>structurally modified</i> in
     * any way other than via the returned list.  (Structural modifications are
     * those that change the size of this list, or otherwise perturb it in such
     * a fashion that iterations in progress may yield incorrect results.)
     *
     * <p>
     * 返回此列表中指定的<tt> fromIndex </tt>(含)和<tt> toIndex </tt>之间的部分的视图。
     *  (如果<tt> fromIndex </tt>和<tt> toIndex </tt>相等,则返回的列表为空。)返回的列表由此列表支持,因此返回列表中的非结构性更改反映在这个列表,反之亦然。
     * 返回的列表支持此列表支持的所有可选列表操作。<p>。
     * 
     *  此方法消除了对显式范围操作(对于数组通常存在的排序)的需要。任何期望列表的操作都可以通过传递子列表视图而不是整个列表来用作范围操作。
     * 例如,以下惯用法从列表中删除一系列元素：<pre> {@ code list.subList(from,to).clear(); } </pre>可以为<tt> indexOf </tt>和<tt> l
     * astIndexOf </tt>构建类似习语,并且<tt> Collections </tt>类中的所有算法都可以应用于subList。
     *  此方法消除了对显式范围操作(对于数组通常存在的排序)的需要。任何期望列表的操作都可以通过传递子列表视图而不是整个列表来用作范围操作。<p>。
     * 
     *  如果以任何方式而不是经由返回的列表来结构地修改后备列表(即,该列表),则由该方法返回的列表的语义变得未定义。
     *  (结构修改是那些改变这个列表的大小,或者以这样的方式干扰它,即正在进行的迭代可能产生不正确的结果。
     * 
     * @param fromIndex low endpoint (inclusive) of the subList
     * @param toIndex high endpoint (exclusive) of the subList
     * @return a view of the specified range within this list
     * @throws IndexOutOfBoundsException for an illegal endpoint index value
     *         (<tt>fromIndex &lt; 0 || toIndex &gt; size ||
     *         fromIndex &gt; toIndex</tt>)
     */
    List<E> subList(int fromIndex, int toIndex);

    /**
     * Creates a {@link Spliterator} over the elements in this list.
     *
     * <p>The {@code Spliterator} reports {@link Spliterator#SIZED} and
     * {@link Spliterator#ORDERED}.  Implementations should document the
     * reporting of additional characteristic values.
     *
     * @implSpec
     * The default implementation creates a
     * <em><a href="Spliterator.html#binding">late-binding</a></em> spliterator
     * from the list's {@code Iterator}.  The spliterator inherits the
     * <em>fail-fast</em> properties of the list's iterator.
     *
     * @implNote
     * The created {@code Spliterator} additionally reports
     * {@link Spliterator#SUBSIZED}.
     *
     * <p>
     * 
     * 
     * @return a {@code Spliterator} over the elements in this list
     * @since 1.8
     */
    @Override
    default Spliterator<E> spliterator() {
        return Spliterators.spliterator(this, Spliterator.ORDERED);
    }
}
