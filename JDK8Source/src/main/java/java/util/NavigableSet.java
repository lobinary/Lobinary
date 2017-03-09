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
 * Written by Doug Lea and Josh Bloch with assistance from members of JCP
 * JSR-166 Expert Group and released to the public domain, as explained at
 * http://creativecommons.org/publicdomain/zero/1.0/
 * <p>
 *  由Doug Lea和Josh Bloch在JCP JSR-166专家组的成员的帮助下撰写,并发布到公共领域,如http://creativecommons.org/publicdomain/zero/
 * 1.0/。
 * 
 */

package java.util;

/**
 * A {@link SortedSet} extended with navigation methods reporting
 * closest matches for given search targets. Methods {@code lower},
 * {@code floor}, {@code ceiling}, and {@code higher} return elements
 * respectively less than, less than or equal, greater than or equal,
 * and greater than a given element, returning {@code null} if there
 * is no such element.  A {@code NavigableSet} may be accessed and
 * traversed in either ascending or descending order.  The {@code
 * descendingSet} method returns a view of the set with the senses of
 * all relational and directional methods inverted. The performance of
 * ascending operations and views is likely to be faster than that of
 * descending ones.  This interface additionally defines methods
 * {@code pollFirst} and {@code pollLast} that return and remove the
 * lowest and highest element, if one exists, else returning {@code
 * null}.  Methods {@code subSet}, {@code headSet},
 * and {@code tailSet} differ from the like-named {@code
 * SortedSet} methods in accepting additional arguments describing
 * whether lower and upper bounds are inclusive versus exclusive.
 * Subsets of any {@code NavigableSet} must implement the {@code
 * NavigableSet} interface.
 *
 * <p> The return values of navigation methods may be ambiguous in
 * implementations that permit {@code null} elements. However, even
 * in this case the result can be disambiguated by checking
 * {@code contains(null)}. To avoid such issues, implementations of
 * this interface are encouraged to <em>not</em> permit insertion of
 * {@code null} elements. (Note that sorted sets of {@link
 * Comparable} elements intrinsically do not permit {@code null}.)
 *
 * <p>Methods
 * {@link #subSet(Object, Object) subSet(E, E)},
 * {@link #headSet(Object) headSet(E)}, and
 * {@link #tailSet(Object) tailSet(E)}
 * are specified to return {@code SortedSet} to allow existing
 * implementations of {@code SortedSet} to be compatibly retrofitted to
 * implement {@code NavigableSet}, but extensions and implementations
 * of this interface are encouraged to override these methods to return
 * {@code NavigableSet}.
 *
 * <p>This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * <p>
 *  {@link SortedSet}扩展了导航方法,报告了给定搜索目标的最接近的匹配项。
 * 方法{@code lower},{@code floor},{@code ceiling}和{@code higher}返回元素分别小于,小于或等于,大于或等于和大于给定元素, @code null}如
 * 果没有这样的元素。
 *  {@link SortedSet}扩展了导航方法,报告了给定搜索目标的最接近的匹配项。可以按升序或降序访问和遍历{@code NavigableSet}。
 *  {@code descendingSet}方法返回集合的视图,所有关系和方向方法的意义都反转。上升操作和视图的性能可能比下降操作和视图的性能更快。
 * 此接口还定义返回和删除最低和最高元素(如果存在)的方法{@code pollFirst}和{@code pollLast},否则返回{@code null}。
 * 方法{@code subSet},{@code headSet}和{@code tailSet}与类似命名的{@code SortedSet}方法在接受描述下限和上限是否是包含性与否的额外参数方面有所不
 * 同。
 * 此接口还定义返回和删除最低和最高元素(如果存在)的方法{@code pollFirst}和{@code pollLast},否则返回{@code null}。
 * 任何{@code NavigableSet}的子集必须实现{@code NavigableSet}接口。
 * 
 * <p>导航方法的返回值在允许{@code null}元素的实现中可能是不明确的。然而,即使在这种情况下,可以通过检查{@code contains(null)}来对结果进行消歧。
 * 为了避免这样的问题,鼓励该接口的实现</em> </em>允许插入{@code null}元素。 (注意,{@ link Comparable}元素的排序集本质上不允许{@code null}。)。
 * 
 *  <p>方法{@link #subSet(Object,Object)subSet(E,E)},{@link #headSet(Object)headSet(E)}和{@link #tailSet(Object)tailSet被指定为返回{@code SortedSet}
 * 以允许现有的{@code SortedSet}实现被兼容地改进以实现{@code NavigableSet},但是鼓励这个接口的扩展和实现来覆盖这些方法以返回{@code NavigableSet }。
 * 
 *  <p>此接口是的成员
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 *  Java集合框架</a>。
 * 
 * 
 * @author Doug Lea
 * @author Josh Bloch
 * @param <E> the type of elements maintained by this set
 * @since 1.6
 */
public interface NavigableSet<E> extends SortedSet<E> {
    /**
     * Returns the greatest element in this set strictly less than the
     * given element, or {@code null} if there is no such element.
     *
     * <p>
     *  返回此集合中最大的元素严格小于给定元素,或{@code null}(如果没有这样的元素)。
     * 
     * 
     * @param e the value to match
     * @return the greatest element less than {@code e},
     *         or {@code null} if there is no such element
     * @throws ClassCastException if the specified element cannot be
     *         compared with the elements currently in the set
     * @throws NullPointerException if the specified element is null
     *         and this set does not permit null elements
     */
    E lower(E e);

    /**
     * Returns the greatest element in this set less than or equal to
     * the given element, or {@code null} if there is no such element.
     *
     * <p>
     *  返回此集合中最小的元素小于或等于给定元素,或{@code null}(如果没有这样的元素)。
     * 
     * 
     * @param e the value to match
     * @return the greatest element less than or equal to {@code e},
     *         or {@code null} if there is no such element
     * @throws ClassCastException if the specified element cannot be
     *         compared with the elements currently in the set
     * @throws NullPointerException if the specified element is null
     *         and this set does not permit null elements
     */
    E floor(E e);

    /**
     * Returns the least element in this set greater than or equal to
     * the given element, or {@code null} if there is no such element.
     *
     * <p>
     *  返回此集合中大于或等于给定元素的最小元素,如果没有这样的元素,则返回{@code null}。
     * 
     * 
     * @param e the value to match
     * @return the least element greater than or equal to {@code e},
     *         or {@code null} if there is no such element
     * @throws ClassCastException if the specified element cannot be
     *         compared with the elements currently in the set
     * @throws NullPointerException if the specified element is null
     *         and this set does not permit null elements
     */
    E ceiling(E e);

    /**
     * Returns the least element in this set strictly greater than the
     * given element, or {@code null} if there is no such element.
     *
     * <p>
     *  返回此集合中的最小元素严格大于给定元素,如果没有此元素,则返回{@code null}。
     * 
     * 
     * @param e the value to match
     * @return the least element greater than {@code e},
     *         or {@code null} if there is no such element
     * @throws ClassCastException if the specified element cannot be
     *         compared with the elements currently in the set
     * @throws NullPointerException if the specified element is null
     *         and this set does not permit null elements
     */
    E higher(E e);

    /**
     * Retrieves and removes the first (lowest) element,
     * or returns {@code null} if this set is empty.
     *
     * <p>
     * 检索和删除第一个(最低)元素,如果此集合为空,则返回{@code null}。
     * 
     * 
     * @return the first element, or {@code null} if this set is empty
     */
    E pollFirst();

    /**
     * Retrieves and removes the last (highest) element,
     * or returns {@code null} if this set is empty.
     *
     * <p>
     *  检索并删除最后一个(最高)元素,如果此集合为空,则返回{@code null}。
     * 
     * 
     * @return the last element, or {@code null} if this set is empty
     */
    E pollLast();

    /**
     * Returns an iterator over the elements in this set, in ascending order.
     *
     * <p>
     *  以升序返回此集合中的元素的迭代器。
     * 
     * 
     * @return an iterator over the elements in this set, in ascending order
     */
    Iterator<E> iterator();

    /**
     * Returns a reverse order view of the elements contained in this set.
     * The descending set is backed by this set, so changes to the set are
     * reflected in the descending set, and vice-versa.  If either set is
     * modified while an iteration over either set is in progress (except
     * through the iterator's own {@code remove} operation), the results of
     * the iteration are undefined.
     *
     * <p>The returned set has an ordering equivalent to
     * <tt>{@link Collections#reverseOrder(Comparator) Collections.reverseOrder}(comparator())</tt>.
     * The expression {@code s.descendingSet().descendingSet()} returns a
     * view of {@code s} essentially equivalent to {@code s}.
     *
     * <p>
     *  返回此集合中包含的元素的逆序视图。下降集由该集支持,因此对集的更改反映在下降集中,反之亦然。
     * 如果在对任一集合的迭代正在进行时修改了任一集合(除非通过迭代器自己的{@code remove}操作),迭代的结果是未定义的。
     * 
     *  <p>返回的集合的顺序等同于<tt> {@ link Collections#reverseOrder(Comparator)Collections.reverseOrder}(comparator(
     * ))</tt>。
     * 表达式{@code s.descendingSet()。descendingSet()}返回基本上等同于{@code s}的{@code s}的视图。
     * 
     * 
     * @return a reverse order view of this set
     */
    NavigableSet<E> descendingSet();

    /**
     * Returns an iterator over the elements in this set, in descending order.
     * Equivalent in effect to {@code descendingSet().iterator()}.
     *
     * <p>
     *  以此降序的方式返回此集合中的元素的迭代器。等效于{@code descendingSet()。iterator()}。
     * 
     * 
     * @return an iterator over the elements in this set, in descending order
     */
    Iterator<E> descendingIterator();

    /**
     * Returns a view of the portion of this set whose elements range from
     * {@code fromElement} to {@code toElement}.  If {@code fromElement} and
     * {@code toElement} are equal, the returned set is empty unless {@code
     * fromInclusive} and {@code toInclusive} are both true.  The returned set
     * is backed by this set, so changes in the returned set are reflected in
     * this set, and vice-versa.  The returned set supports all optional set
     * operations that this set supports.
     *
     * <p>The returned set will throw an {@code IllegalArgumentException}
     * on an attempt to insert an element outside its range.
     *
     * <p>
     *  返回此集合中元素范围从{@code fromElement}到{@code toElement}的部分的视图。
     * 如果{@code fromElement}和{@code toElement}相等,返回的集合为空,除非{@code fromInclusive}和{@code toInclusive}都为true。
     * 返回的集合由此集合支持,因此返回集合中的更改反映在此集合中,反之亦然。返回的集合支持该集合支持的所有可选集合操作。
     * 
     * <p>返回的集合将尝试在其范围之外插入元素时抛出{@code IllegalArgumentException}。
     * 
     * 
     * @param fromElement low endpoint of the returned set
     * @param fromInclusive {@code true} if the low endpoint
     *        is to be included in the returned view
     * @param toElement high endpoint of the returned set
     * @param toInclusive {@code true} if the high endpoint
     *        is to be included in the returned view
     * @return a view of the portion of this set whose elements range from
     *         {@code fromElement}, inclusive, to {@code toElement}, exclusive
     * @throws ClassCastException if {@code fromElement} and
     *         {@code toElement} cannot be compared to one another using this
     *         set's comparator (or, if the set has no comparator, using
     *         natural ordering).  Implementations may, but are not required
     *         to, throw this exception if {@code fromElement} or
     *         {@code toElement} cannot be compared to elements currently in
     *         the set.
     * @throws NullPointerException if {@code fromElement} or
     *         {@code toElement} is null and this set does
     *         not permit null elements
     * @throws IllegalArgumentException if {@code fromElement} is
     *         greater than {@code toElement}; or if this set itself
     *         has a restricted range, and {@code fromElement} or
     *         {@code toElement} lies outside the bounds of the range.
     */
    NavigableSet<E> subSet(E fromElement, boolean fromInclusive,
                           E toElement,   boolean toInclusive);

    /**
     * Returns a view of the portion of this set whose elements are less than
     * (or equal to, if {@code inclusive} is true) {@code toElement}.  The
     * returned set is backed by this set, so changes in the returned set are
     * reflected in this set, and vice-versa.  The returned set supports all
     * optional set operations that this set supports.
     *
     * <p>The returned set will throw an {@code IllegalArgumentException}
     * on an attempt to insert an element outside its range.
     *
     * <p>
     *  返回此元素的元素小于(或等于,如果{@code inclusive}为true){@code toElement}的部分的视图。返回的集合由此集合支持,因此返回集合中的更改反映在此集合中,反之亦然。
     * 返回的集合支持该集合支持的所有可选集合操作。
     * 
     *  <p>返回的集合将尝试在其范围之外插入元素时抛出{@code IllegalArgumentException}。
     * 
     * 
     * @param toElement high endpoint of the returned set
     * @param inclusive {@code true} if the high endpoint
     *        is to be included in the returned view
     * @return a view of the portion of this set whose elements are less than
     *         (or equal to, if {@code inclusive} is true) {@code toElement}
     * @throws ClassCastException if {@code toElement} is not compatible
     *         with this set's comparator (or, if the set has no comparator,
     *         if {@code toElement} does not implement {@link Comparable}).
     *         Implementations may, but are not required to, throw this
     *         exception if {@code toElement} cannot be compared to elements
     *         currently in the set.
     * @throws NullPointerException if {@code toElement} is null and
     *         this set does not permit null elements
     * @throws IllegalArgumentException if this set itself has a
     *         restricted range, and {@code toElement} lies outside the
     *         bounds of the range
     */
    NavigableSet<E> headSet(E toElement, boolean inclusive);

    /**
     * Returns a view of the portion of this set whose elements are greater
     * than (or equal to, if {@code inclusive} is true) {@code fromElement}.
     * The returned set is backed by this set, so changes in the returned set
     * are reflected in this set, and vice-versa.  The returned set supports
     * all optional set operations that this set supports.
     *
     * <p>The returned set will throw an {@code IllegalArgumentException}
     * on an attempt to insert an element outside its range.
     *
     * <p>
     *  返回此集合的元素大于(或等于,如果{@code inclusive}为true){@code fromElement}的部分的视图。
     * 返回的集合由此集合支持,因此返回集合中的更改反映在此集合中,反之亦然。返回的集合支持该集合支持的所有可选集合操作。
     * 
     *  <p>返回的集合将尝试在其范围之外插入元素时抛出{@code IllegalArgumentException}。
     * 
     * 
     * @param fromElement low endpoint of the returned set
     * @param inclusive {@code true} if the low endpoint
     *        is to be included in the returned view
     * @return a view of the portion of this set whose elements are greater
     *         than or equal to {@code fromElement}
     * @throws ClassCastException if {@code fromElement} is not compatible
     *         with this set's comparator (or, if the set has no comparator,
     *         if {@code fromElement} does not implement {@link Comparable}).
     *         Implementations may, but are not required to, throw this
     *         exception if {@code fromElement} cannot be compared to elements
     *         currently in the set.
     * @throws NullPointerException if {@code fromElement} is null
     *         and this set does not permit null elements
     * @throws IllegalArgumentException if this set itself has a
     *         restricted range, and {@code fromElement} lies outside the
     *         bounds of the range
     */
    NavigableSet<E> tailSet(E fromElement, boolean inclusive);

    /**
     * {@inheritDoc}
     *
     * <p>Equivalent to {@code subSet(fromElement, true, toElement, false)}.
     *
     * <p>
     *  {@inheritDoc}
     * 
     *  <p>等同于{@code subSet(fromElement,true,toElement,false)}。
     * 
     * 
     * @throws ClassCastException       {@inheritDoc}
     * @throws NullPointerException     {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    SortedSet<E> subSet(E fromElement, E toElement);

    /**
     * {@inheritDoc}
     *
     * <p>Equivalent to {@code headSet(toElement, false)}.
     *
     * <p>
     *  {@inheritDoc}
     * 
     *  <p>等同于{@code headSet(toElement,false)}。
     * 
     * 
     * @throws ClassCastException       {@inheritDoc}
     * @throws NullPointerException     {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    SortedSet<E> headSet(E toElement);

    /**
     * {@inheritDoc}
     *
     * <p>Equivalent to {@code tailSet(fromElement, true)}.
     *
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @throws ClassCastException       {@inheritDoc}
     * @throws NullPointerException     {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    SortedSet<E> tailSet(E fromElement);
}
