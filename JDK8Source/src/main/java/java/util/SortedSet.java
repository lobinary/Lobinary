/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * A {@link Set} that further provides a <i>total ordering</i> on its elements.
 * The elements are ordered using their {@linkplain Comparable natural
 * ordering}, or by a {@link Comparator} typically provided at sorted
 * set creation time.  The set's iterator will traverse the set in
 * ascending element order. Several additional operations are provided
 * to take advantage of the ordering.  (This interface is the set
 * analogue of {@link SortedMap}.)
 *
 * <p>All elements inserted into a sorted set must implement the <tt>Comparable</tt>
 * interface (or be accepted by the specified comparator).  Furthermore, all
 * such elements must be <i>mutually comparable</i>: <tt>e1.compareTo(e2)</tt>
 * (or <tt>comparator.compare(e1, e2)</tt>) must not throw a
 * <tt>ClassCastException</tt> for any elements <tt>e1</tt> and <tt>e2</tt> in
 * the sorted set.  Attempts to violate this restriction will cause the
 * offending method or constructor invocation to throw a
 * <tt>ClassCastException</tt>.
 *
 * <p>Note that the ordering maintained by a sorted set (whether or not an
 * explicit comparator is provided) must be <i>consistent with equals</i> if
 * the sorted set is to correctly implement the <tt>Set</tt> interface.  (See
 * the <tt>Comparable</tt> interface or <tt>Comparator</tt> interface for a
 * precise definition of <i>consistent with equals</i>.)  This is so because
 * the <tt>Set</tt> interface is defined in terms of the <tt>equals</tt>
 * operation, but a sorted set performs all element comparisons using its
 * <tt>compareTo</tt> (or <tt>compare</tt>) method, so two elements that are
 * deemed equal by this method are, from the standpoint of the sorted set,
 * equal.  The behavior of a sorted set <i>is</i> well-defined even if its
 * ordering is inconsistent with equals; it just fails to obey the general
 * contract of the <tt>Set</tt> interface.
 *
 * <p>All general-purpose sorted set implementation classes should
 * provide four "standard" constructors: 1) A void (no arguments)
 * constructor, which creates an empty sorted set sorted according to
 * the natural ordering of its elements.  2) A constructor with a
 * single argument of type <tt>Comparator</tt>, which creates an empty
 * sorted set sorted according to the specified comparator.  3) A
 * constructor with a single argument of type <tt>Collection</tt>,
 * which creates a new sorted set with the same elements as its
 * argument, sorted according to the natural ordering of the elements.
 * 4) A constructor with a single argument of type <tt>SortedSet</tt>,
 * which creates a new sorted set with the same elements and the same
 * ordering as the input sorted set.  There is no way to enforce this
 * recommendation, as interfaces cannot contain constructors.
 *
 * <p>Note: several methods return subsets with restricted ranges.
 * Such ranges are <i>half-open</i>, that is, they include their low
 * endpoint but not their high endpoint (where applicable).
 * If you need a <i>closed range</i> (which includes both endpoints), and
 * the element type allows for calculation of the successor of a given
 * value, merely request the subrange from <tt>lowEndpoint</tt> to
 * <tt>successor(highEndpoint)</tt>.  For example, suppose that <tt>s</tt>
 * is a sorted set of strings.  The following idiom obtains a view
 * containing all of the strings in <tt>s</tt> from <tt>low</tt> to
 * <tt>high</tt>, inclusive:<pre>
 *   SortedSet&lt;String&gt; sub = s.subSet(low, high+"\0");</pre>
 *
 * A similar technique can be used to generate an <i>open range</i> (which
 * contains neither endpoint).  The following idiom obtains a view
 * containing all of the Strings in <tt>s</tt> from <tt>low</tt> to
 * <tt>high</tt>, exclusive:<pre>
 *   SortedSet&lt;String&gt; sub = s.subSet(low+"\0", high);</pre>
 *
 * <p>This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * <p>
 *  {@link Set},进一步在其元素上提供<i>总排序</i>。
 * 元素使用它们的{@linkplain Comparable natural ordering}或者通常在排序集创建时提供的{@link Comparator}进行排序。
 * 集合的迭代器将以升序元素顺序遍历集合。提供若干附加操作以利用排序。 (此接口是{@link SortedMap}的类似设置。)。
 * 
 *  <p>插入到排序集中的所有元素都必须实现<tt>可比较</tt>接口(或被指定的比较器接受)。
 * 此外,所有这些元素必须<i>相互比较</i>：<tt> e1.compareTo(e2)</tt>(或<tt> comparator.compare(e1,e2)</tt>)对排序集中的任何元素<tt>
 *  e1 </tt>和<tt> e2 </tt>抛出<tt> ClassCastException </tt>。
 *  <p>插入到排序集中的所有元素都必须实现<tt>可比较</tt>接口(或被指定的比较器接受)。
 * 尝试违反此限制会导致冒犯方法或构造函数调用抛出<tt> ClassCastException </tt>。
 * 
 * <p>请注意,如果排序集合要正确实现<tt> Set </tt,则由排序集合(不管是否提供显式比较器)维护的排序必须与equals </i> >接口。
 *  (请参阅<tt>可比较的</tt>界面或<tt>比较器</tt>界面以获得与等于</i>一致的<i>的精确定义。
 * )这是因为<tt> tt>接口是根据<tt> equals </tt>操作定义的,但是排序集合使用其<tt> compareTo </tt>(或<tt>比较</tt>)方法执行所有元素比较,因此从排序集
 * 合的角度看,通过该方法认为相等的两个元素相等。
 *  (请参阅<tt>可比较的</tt>界面或<tt>比较器</tt>界面以获得与等于</i>一致的<i>的精确定义。
 * 排序集合<i>的行为是良好定义的,即使其排序与equals不一致;它只是不能服从<tt> Set </tt>接口的一般合同。
 * 
 * <p>所有通用排序集实现类都应该提供四个"标准"构造函数：1)一个void(无参数)构造函数,它根据元素的自然排序创建一个空的排序集合。
 *  2)具有类型<tt> Comparator </tt>的单个参数的构造函数,它根据指定的比较器创建一个空的排序集合。
 *  3)具有类型<tt> Collection </tt>的单个参数的构造函数,它根据元素的自然排序创建具有与其参数相同的元素的新的排序集合。
 *  4)具有类型为<tt> SortedSet </tt>的单个参数的构造函数,其创建具有与输入排序集合相同的元素和相同排序的新的排序集合。没有办法强制这个建议,因为接口不能包含构造函数。
 * 
 * <p>注意：有几种方法返回限制范围的子集。这样的范围是半开放的,即它们包括它们的低端点,但不包括它们的高端点(如果适用)。
 * 如果您需要<i>封闭范围</i>(包括两个端点),且元素类型允许计算给定值的后继,只需请求子范围从<tt> lowEndpoint </tt>到< tt> successor(highEndpoint)
 * </tt>。
 * <p>注意：有几种方法返回限制范围的子集。这样的范围是半开放的,即它们包括它们的低端点,但不包括它们的高端点(如果适用)。例如,假设<tt> s </tt>是一组有序的字符串。
 * 以下成语获得包含<tt> s </tt>中从<tt>低</tt>到<tt>高</tt>的所有字符串的视图,包括：<pre> SortedSet&lt; String&gt; sub = s.subSet
 * (low,high +"\ 0"); </pre>。
 * <p>注意：有几种方法返回限制范围的子集。这样的范围是半开放的,即它们包括它们的低端点,但不包括它们的高端点(如果适用)。例如,假设<tt> s </tt>是一组有序的字符串。
 * 
 *  可以使用类似的技术来生成开放范围</i>(其不包含端点)。
 * 以下成语获得包含<tt> s </tt>中从<tt>低</tt>到<tt>高</tt>的所有字符串的视图：exclusive：<pre> SortedSet&lt; String&gt; sub = s
 * .subSet(low +"\ 0",high); </pre>。
 *  可以使用类似的技术来生成开放范围</i>(其不包含端点)。
 * 
 *  <p>此接口是的成员
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 *  Java集合框架</a>。
 * 
 * 
 * @param <E> the type of elements maintained by this set
 *
 * @author  Josh Bloch
 * @see Set
 * @see TreeSet
 * @see SortedMap
 * @see Collection
 * @see Comparable
 * @see Comparator
 * @see ClassCastException
 * @since 1.2
 */

public interface SortedSet<E> extends Set<E> {
    /**
     * Returns the comparator used to order the elements in this set,
     * or <tt>null</tt> if this set uses the {@linkplain Comparable
     * natural ordering} of its elements.
     *
     * <p>
     *  返回用于对此集合中的元素排序的比较器,如果此集合使用其元素的{@linkplain Comparable natural ordering},则返回<tt> null </tt>。
     * 
     * 
     * @return the comparator used to order the elements in this set,
     *         or <tt>null</tt> if this set uses the natural ordering
     *         of its elements
     */
    Comparator<? super E> comparator();

    /**
     * Returns a view of the portion of this set whose elements range
     * from <tt>fromElement</tt>, inclusive, to <tt>toElement</tt>,
     * exclusive.  (If <tt>fromElement</tt> and <tt>toElement</tt> are
     * equal, the returned set is empty.)  The returned set is backed
     * by this set, so changes in the returned set are reflected in
     * this set, and vice-versa.  The returned set supports all
     * optional set operations that this set supports.
     *
     * <p>The returned set will throw an <tt>IllegalArgumentException</tt>
     * on an attempt to insert an element outside its range.
     *
     * <p>
     * 传回此集合中元素范围从<tt> fromElement </tt>(含)到<tt> toElement </tt>(排除)的部分的视图。
     *  (如果<tt> fromElement </tt>和<tt>到Element</tt>相等,则返回的集合为空。)返回的集合由此集合支持,因此返回集合中的更改反映在此集合中,反之亦然。
     * 返回的集合支持该集合支持的所有可选集合操作。
     * 
     *  <p>返回的集合会在尝试在其范围之外插入元素时引发<tt> IllegalArgumentException </tt>。
     * 
     * 
     * @param fromElement low endpoint (inclusive) of the returned set
     * @param toElement high endpoint (exclusive) of the returned set
     * @return a view of the portion of this set whose elements range from
     *         <tt>fromElement</tt>, inclusive, to <tt>toElement</tt>, exclusive
     * @throws ClassCastException if <tt>fromElement</tt> and
     *         <tt>toElement</tt> cannot be compared to one another using this
     *         set's comparator (or, if the set has no comparator, using
     *         natural ordering).  Implementations may, but are not required
     *         to, throw this exception if <tt>fromElement</tt> or
     *         <tt>toElement</tt> cannot be compared to elements currently in
     *         the set.
     * @throws NullPointerException if <tt>fromElement</tt> or
     *         <tt>toElement</tt> is null and this set does not permit null
     *         elements
     * @throws IllegalArgumentException if <tt>fromElement</tt> is
     *         greater than <tt>toElement</tt>; or if this set itself
     *         has a restricted range, and <tt>fromElement</tt> or
     *         <tt>toElement</tt> lies outside the bounds of the range
     */
    SortedSet<E> subSet(E fromElement, E toElement);

    /**
     * Returns a view of the portion of this set whose elements are
     * strictly less than <tt>toElement</tt>.  The returned set is
     * backed by this set, so changes in the returned set are
     * reflected in this set, and vice-versa.  The returned set
     * supports all optional set operations that this set supports.
     *
     * <p>The returned set will throw an <tt>IllegalArgumentException</tt>
     * on an attempt to insert an element outside its range.
     *
     * <p>
     *  返回此元素的元素严格小于<tt> toElement </tt>的部分的视图。返回的集合由此集合支持,因此返回集合中的更改反映在此集合中,反之亦然。返回的集合支持该集合支持的所有可选集合操作。
     * 
     *  <p>返回的集合会在尝试在其范围之外插入元素时引发<tt> IllegalArgumentException </tt>。
     * 
     * 
     * @param toElement high endpoint (exclusive) of the returned set
     * @return a view of the portion of this set whose elements are strictly
     *         less than <tt>toElement</tt>
     * @throws ClassCastException if <tt>toElement</tt> is not compatible
     *         with this set's comparator (or, if the set has no comparator,
     *         if <tt>toElement</tt> does not implement {@link Comparable}).
     *         Implementations may, but are not required to, throw this
     *         exception if <tt>toElement</tt> cannot be compared to elements
     *         currently in the set.
     * @throws NullPointerException if <tt>toElement</tt> is null and
     *         this set does not permit null elements
     * @throws IllegalArgumentException if this set itself has a
     *         restricted range, and <tt>toElement</tt> lies outside the
     *         bounds of the range
     */
    SortedSet<E> headSet(E toElement);

    /**
     * Returns a view of the portion of this set whose elements are
     * greater than or equal to <tt>fromElement</tt>.  The returned
     * set is backed by this set, so changes in the returned set are
     * reflected in this set, and vice-versa.  The returned set
     * supports all optional set operations that this set supports.
     *
     * <p>The returned set will throw an <tt>IllegalArgumentException</tt>
     * on an attempt to insert an element outside its range.
     *
     * <p>
     *  返回其元素大于或等于<tt> fromElement </tt>的此集合部分的视图。返回的集合由此集合支持,因此返回集合中的更改反映在此集合中,反之亦然。返回的集合支持该集合支持的所有可选集合操作。
     * 
     *  <p>返回的集合会在尝试在其范围之外插入元素时引发<tt> IllegalArgumentException </tt>。
     * 
     * 
     * @param fromElement low endpoint (inclusive) of the returned set
     * @return a view of the portion of this set whose elements are greater
     *         than or equal to <tt>fromElement</tt>
     * @throws ClassCastException if <tt>fromElement</tt> is not compatible
     *         with this set's comparator (or, if the set has no comparator,
     *         if <tt>fromElement</tt> does not implement {@link Comparable}).
     *         Implementations may, but are not required to, throw this
     *         exception if <tt>fromElement</tt> cannot be compared to elements
     *         currently in the set.
     * @throws NullPointerException if <tt>fromElement</tt> is null
     *         and this set does not permit null elements
     * @throws IllegalArgumentException if this set itself has a
     *         restricted range, and <tt>fromElement</tt> lies outside the
     *         bounds of the range
     */
    SortedSet<E> tailSet(E fromElement);

    /**
     * Returns the first (lowest) element currently in this set.
     *
     * <p>
     *  返回此集合中当前的第一个(最低)元素。
     * 
     * 
     * @return the first (lowest) element currently in this set
     * @throws NoSuchElementException if this set is empty
     */
    E first();

    /**
     * Returns the last (highest) element currently in this set.
     *
     * <p>
     * 返回此集合中当前的最后一个(最高)元素。
     * 
     * 
     * @return the last (highest) element currently in this set
     * @throws NoSuchElementException if this set is empty
     */
    E last();

    /**
     * Creates a {@code Spliterator} over the elements in this sorted set.
     *
     * <p>The {@code Spliterator} reports {@link Spliterator#DISTINCT},
     * {@link Spliterator#SORTED} and {@link Spliterator#ORDERED}.
     * Implementations should document the reporting of additional
     * characteristic values.
     *
     * <p>The spliterator's comparator (see
     * {@link java.util.Spliterator#getComparator()}) must be {@code null} if
     * the sorted set's comparator (see {@link #comparator()}) is {@code null}.
     * Otherwise, the spliterator's comparator must be the same as or impose the
     * same total ordering as the sorted set's comparator.
     *
     * @implSpec
     * The default implementation creates a
     * <em><a href="Spliterator.html#binding">late-binding</a></em> spliterator
     * from the sorted set's {@code Iterator}.  The spliterator inherits the
     * <em>fail-fast</em> properties of the set's iterator.  The
     * spliterator's comparator is the same as the sorted set's comparator.
     * <p>
     * The created {@code Spliterator} additionally reports
     * {@link Spliterator#SIZED}.
     *
     * @implNote
     * The created {@code Spliterator} additionally reports
     * {@link Spliterator#SUBSIZED}.
     *
     * <p>
     *  在此排序集中的元素上创建{@code Spliterator}。
     * 
     *  <p> {@code Spliterator}报告{@link Spliterator#DISTINCT},{@link Spliterator#SORTED}和{@link Spliterator#ORDERED}
     * 。
     * 实施应记录附加特性值的报告。
     * 
     *  <p>如果排序集的比较器(参见{@link #comparator()})是{@code null),则分割器的比较器(参见{@link java.util.Spliterator#getComparator()}
     * )必须是{@code null} }。
     * 否则,拼接器的比较器必须与排序集合的比较器相同或具有相同的总排序。
     * 
     * @return a {@code Spliterator} over the elements in this sorted set
     * @since 1.8
     */
    @Override
    default Spliterator<E> spliterator() {
        return new Spliterators.IteratorSpliterator<E>(
                this, Spliterator.DISTINCT | Spliterator.SORTED | Spliterator.ORDERED) {
            @Override
            public Comparator<? super E> getComparator() {
                return SortedSet.this.comparator();
            }
        };
    }
}
