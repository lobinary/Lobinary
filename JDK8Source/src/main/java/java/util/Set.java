/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * A collection that contains no duplicate elements.  More formally, sets
 * contain no pair of elements <code>e1</code> and <code>e2</code> such that
 * <code>e1.equals(e2)</code>, and at most one null element.  As implied by
 * its name, this interface models the mathematical <i>set</i> abstraction.
 *
 * <p>The <tt>Set</tt> interface places additional stipulations, beyond those
 * inherited from the <tt>Collection</tt> interface, on the contracts of all
 * constructors and on the contracts of the <tt>add</tt>, <tt>equals</tt> and
 * <tt>hashCode</tt> methods.  Declarations for other inherited methods are
 * also included here for convenience.  (The specifications accompanying these
 * declarations have been tailored to the <tt>Set</tt> interface, but they do
 * not contain any additional stipulations.)
 *
 * <p>The additional stipulation on constructors is, not surprisingly,
 * that all constructors must create a set that contains no duplicate elements
 * (as defined above).
 *
 * <p>Note: Great care must be exercised if mutable objects are used as set
 * elements.  The behavior of a set is not specified if the value of an object
 * is changed in a manner that affects <tt>equals</tt> comparisons while the
 * object is an element in the set.  A special case of this prohibition is
 * that it is not permissible for a set to contain itself as an element.
 *
 * <p>Some set implementations have restrictions on the elements that
 * they may contain.  For example, some implementations prohibit null elements,
 * and some have restrictions on the types of their elements.  Attempting to
 * add an ineligible element throws an unchecked exception, typically
 * <tt>NullPointerException</tt> or <tt>ClassCastException</tt>.  Attempting
 * to query the presence of an ineligible element may throw an exception,
 * or it may simply return false; some implementations will exhibit the former
 * behavior and some will exhibit the latter.  More generally, attempting an
 * operation on an ineligible element whose completion would not result in
 * the insertion of an ineligible element into the set may throw an
 * exception or it may succeed, at the option of the implementation.
 * Such exceptions are marked as "optional" in the specification for this
 * interface.
 *
 * <p>This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * <p>
 *  不包含重复元素的集合。
 * 更正式地,集合不包含<code> e1 </code>和<code> e2 </code>的元素对,使得<code> e1.equals(e2)</code>,并且至多一个空元素。
 * 如其名称所暗示的,该接口对数学<i>集</i>抽象进行建模。
 * 
 *  <p> <tt> Set </tt>界面对所有构造函数的合同以及<tt> add </tt>接口添加了额外的规定,除了继承<tt>集合</tt> tt>,<tt>等于</tt>和<tt> hashCo
 * de </tt>方法。
 * 为了方便起见,这里还包括其他继承方法的声明。 (伴随这些声明的规范是根据<tt>设置</tt>界面定制的,但不包含任何其他规定。)。
 * 
 *  <p>构造函数的附加规定是,并不奇怪,所有构造函数必须创建一个不包含重复元素(如上定义)的集合。
 * 
 *  <p>注意：如果可变对象用作设置元素,则必须非常小心。如果对象的值以影响<tt>等于</tt>比较的方式更改,而对象是集合中的元素,则不指定集合的​​行为。
 * 这种禁止的一个特殊情况是,一个集合不允许包含自身作为一个元素。
 * 
 * <p>某些集合实现对它们可能包含的元素有限制。例如,一些实现禁止空元素,并且一些实现对它们的元素的类型具有限制。
 * 尝试添加不合格元素会抛出未检查的异常,通常为<tt> NullPointerException </tt>或<tt> ClassCastException </tt>。
 * 尝试查询不合格元素的存在可能会抛出异常,或者它可能只是返回false;一些实现将展示前一行为,一些将展示后者。
 * 更一般地,尝试对不合格元素的操作可以抛出异常或者它可以成功,在执行的选择时,其完成不会导致不合格元素插入到集合中。这种异常在此接口的规范中标记为"可选"。
 * 
 *  <p>此接口是的成员
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 *  Java集合框架</a>。
 * 
 * 
 * @param <E> the type of elements maintained by this set
 *
 * @author  Josh Bloch
 * @author  Neal Gafter
 * @see Collection
 * @see List
 * @see SortedSet
 * @see HashSet
 * @see TreeSet
 * @see AbstractSet
 * @see Collections#singleton(java.lang.Object)
 * @see Collections#EMPTY_SET
 * @since 1.2
 */

public interface Set<E> extends Collection<E> {
    // Query Operations

    /**
     * Returns the number of elements in this set (its cardinality).  If this
     * set contains more than <tt>Integer.MAX_VALUE</tt> elements, returns
     * <tt>Integer.MAX_VALUE</tt>.
     *
     * <p>
     *  返回此集合中的元素数(其基数)。如果此集合包含多于<tt> Integer.MAX_VALUE </tt>个元素,则返回<tt> Integer.MAX_VALUE </tt>。
     * 
     * 
     * @return the number of elements in this set (its cardinality)
     */
    int size();

    /**
     * Returns <tt>true</tt> if this set contains no elements.
     *
     * <p>
     *  如果此集合不包含元素,则返回<tt> true </tt>。
     * 
     * 
     * @return <tt>true</tt> if this set contains no elements
     */
    boolean isEmpty();

    /**
     * Returns <tt>true</tt> if this set contains the specified element.
     * More formally, returns <tt>true</tt> if and only if this set
     * contains an element <tt>e</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
     *
     * <p>
     *  如果此集合包含指定的元素,则返回<tt> true </tt>。
     * 更正式地说,如果且仅当此集合包含一个元素<tt> e </tt>,使得<tt>(o == null&nbsp;?&nbsp; e == null&nbsp;：&nbsp; ; o.equals(e))</tt>
     * 。
     *  如果此集合包含指定的元素,则返回<tt> true </tt>。
     * 
     * 
     * @param o element whose presence in this set is to be tested
     * @return <tt>true</tt> if this set contains the specified element
     * @throws ClassCastException if the type of the specified element
     *         is incompatible with this set
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null and this
     *         set does not permit null elements
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     */
    boolean contains(Object o);

    /**
     * Returns an iterator over the elements in this set.  The elements are
     * returned in no particular order (unless this set is an instance of some
     * class that provides a guarantee).
     *
     * <p>
     * 返回此集合中的元素的迭代器。元素不按特定顺序返回(除非此集合是提供保证的某个类的实例)。
     * 
     * 
     * @return an iterator over the elements in this set
     */
    Iterator<E> iterator();

    /**
     * Returns an array containing all of the elements in this set.
     * If this set makes any guarantees as to what order its elements
     * are returned by its iterator, this method must return the
     * elements in the same order.
     *
     * <p>The returned array will be "safe" in that no references to it
     * are maintained by this set.  (In other words, this method must
     * allocate a new array even if this set is backed by an array).
     * The caller is thus free to modify the returned array.
     *
     * <p>This method acts as bridge between array-based and collection-based
     * APIs.
     *
     * <p>
     *  返回一个包含此集合中所有元素的数组。如果这个集合对它的元素被它的迭代器返回的顺序作出任何保证,那么这个方法必须以相同的顺序返回这些元素。
     * 
     *  <p>返回的数组将是"安全的",因为没有对它的引用由此集合维护。 (换句话说,这个方法必须分配一个新数组,即使这个数组支持数组)。因此调用者可以自由地修改返回的数组。
     * 
     *  <p>此方法充当基于阵列和基于集合的API之间的桥梁。
     * 
     * 
     * @return an array containing all the elements in this set
     */
    Object[] toArray();

    /**
     * Returns an array containing all of the elements in this set; the
     * runtime type of the returned array is that of the specified array.
     * If the set fits in the specified array, it is returned therein.
     * Otherwise, a new array is allocated with the runtime type of the
     * specified array and the size of this set.
     *
     * <p>If this set fits in the specified array with room to spare
     * (i.e., the array has more elements than this set), the element in
     * the array immediately following the end of the set is set to
     * <tt>null</tt>.  (This is useful in determining the length of this
     * set <i>only</i> if the caller knows that this set does not contain
     * any null elements.)
     *
     * <p>If this set makes any guarantees as to what order its elements
     * are returned by its iterator, this method must return the elements
     * in the same order.
     *
     * <p>Like the {@link #toArray()} method, this method acts as bridge between
     * array-based and collection-based APIs.  Further, this method allows
     * precise control over the runtime type of the output array, and may,
     * under certain circumstances, be used to save allocation costs.
     *
     * <p>Suppose <tt>x</tt> is a set known to contain only strings.
     * The following code can be used to dump the set into a newly allocated
     * array of <tt>String</tt>:
     *
     * <pre>
     *     String[] y = x.toArray(new String[0]);</pre>
     *
     * Note that <tt>toArray(new Object[0])</tt> is identical in function to
     * <tt>toArray()</tt>.
     *
     * <p>
     *  返回一个包含此集合中所有元素的数组;返回的数组的运行时类型是指定数组的运行时类型。如果集合适合指定的数组,则返回其中。否则,将使用指定数组的运行时类型和此集合的大小分配新数组。
     * 
     *  <p>如果此集合适合具有空余空间的指定数组(即,数组具有比此集合更多的元素),则紧接集合结尾的数组中的元素将设置为<tt> null </tt >。
     *  (如果调用者知道此集合不包含任何空元素,则这在确定此集合的长度</i> </i>时非常有用。)。
     * 
     * <p>如果此集合对其元素由其迭代器返回的顺序作出任何保证,则此方法必须以相同的顺序返回元素。
     * 
     *  <p>与{@link #toArray()}方法类似,此方法充当基于数组和基于集合的API之间的桥梁。此外,该方法允许对输出阵列的运行时类型的精确控制,并且在某些情况下可以用于节省分配成本。
     * 
     *  <p>假设<tt> x </tt>是一个已知只包含字符串的集合。以下代码可用于将集合转储到新分配的<tt> String </tt>数组中：
     * 
     * <pre>
     *  String [] y = x.toArray(new String [0]); </pre>
     * 
     *  注意,<tt> toArray(new Object [0])</tt>在功能上与<tt> toArray()</tt>相同。
     * 
     * 
     * @param a the array into which the elements of this set are to be
     *        stored, if it is big enough; otherwise, a new array of the same
     *        runtime type is allocated for this purpose.
     * @return an array containing all the elements in this set
     * @throws ArrayStoreException if the runtime type of the specified array
     *         is not a supertype of the runtime type of every element in this
     *         set
     * @throws NullPointerException if the specified array is null
     */
    <T> T[] toArray(T[] a);


    // Modification Operations

    /**
     * Adds the specified element to this set if it is not already present
     * (optional operation).  More formally, adds the specified element
     * <tt>e</tt> to this set if the set contains no element <tt>e2</tt>
     * such that
     * <tt>(e==null&nbsp;?&nbsp;e2==null&nbsp;:&nbsp;e.equals(e2))</tt>.
     * If this set already contains the element, the call leaves the set
     * unchanged and returns <tt>false</tt>.  In combination with the
     * restriction on constructors, this ensures that sets never contain
     * duplicate elements.
     *
     * <p>The stipulation above does not imply that sets must accept all
     * elements; sets may refuse to add any particular element, including
     * <tt>null</tt>, and throw an exception, as described in the
     * specification for {@link Collection#add Collection.add}.
     * Individual set implementations should clearly document any
     * restrictions on the elements that they may contain.
     *
     * <p>
     *  如果指定的元素不存在(可选操作),则将该元素添加到此集合。
     * 更正式地,如果集合不包含元素<tt> e2 </tt>,使得<tt>(e == null&nbsp;?&nbsp; e2 == null&nbsp; ;：e.equals(e2))</tt>。
     * 如果此集合已包含元素,则调用使集合保持不变,并返回<tt> false </tt>。结合对构造函数的限制,这确保集合从不包含重复的元素。
     * 
     * <p>上述规定并不意味着集合必须接受所有元素;集合可以拒绝添加任何特定元素,包括<tt> null </tt>,并抛出异常,如{@link Collection#add Collection.add}的
     * 规范中所述。
     * 单个集合实现应清楚地记录对它们可能包含的元素的任何限制。
     * 
     * 
     * @param e element to be added to this set
     * @return <tt>true</tt> if this set did not already contain the specified
     *         element
     * @throws UnsupportedOperationException if the <tt>add</tt> operation
     *         is not supported by this set
     * @throws ClassCastException if the class of the specified element
     *         prevents it from being added to this set
     * @throws NullPointerException if the specified element is null and this
     *         set does not permit null elements
     * @throws IllegalArgumentException if some property of the specified element
     *         prevents it from being added to this set
     */
    boolean add(E e);


    /**
     * Removes the specified element from this set if it is present
     * (optional operation).  More formally, removes an element <tt>e</tt>
     * such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>, if
     * this set contains such an element.  Returns <tt>true</tt> if this set
     * contained the element (or equivalently, if this set changed as a
     * result of the call).  (This set will not contain the element once the
     * call returns.)
     *
     * <p>
     *  从此集合中删除指定元素(如果存在)(可选操作)。
     * 更正式地,删除元素<tt> e </tt>,使得<tt>(o == null&nbsp;?&nbsp; e == null&nbsp;：&nbsp; o.equals(e))</tt> set包含这样的
     * 元素。
     *  从此集合中删除指定元素(如果存在)(可选操作)。如果此集合包含元素(或等效地,如果此集合作为调用的结果而更改),则返回<tt> true </tt>。 (这个集合在调用返回后不会包含元素。)。
     * 
     * 
     * @param o object to be removed from this set, if present
     * @return <tt>true</tt> if this set contained the specified element
     * @throws ClassCastException if the type of the specified element
     *         is incompatible with this set
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null and this
     *         set does not permit null elements
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws UnsupportedOperationException if the <tt>remove</tt> operation
     *         is not supported by this set
     */
    boolean remove(Object o);


    // Bulk Operations

    /**
     * Returns <tt>true</tt> if this set contains all of the elements of the
     * specified collection.  If the specified collection is also a set, this
     * method returns <tt>true</tt> if it is a <i>subset</i> of this set.
     *
     * <p>
     *  如果此集合包含指定集合的​​所有元素,则返回<tt> true </tt>。如果指定的集合也是集合,则此方法返回<tt> true </tt>,如果它是此集合的<i>子集</i>。
     * 
     * 
     * @param  c collection to be checked for containment in this set
     * @return <tt>true</tt> if this set contains all of the elements of the
     *         specified collection
     * @throws ClassCastException if the types of one or more elements
     *         in the specified collection are incompatible with this
     *         set
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified collection contains one
     *         or more null elements and this set does not permit null
     *         elements
     * (<a href="Collection.html#optional-restrictions">optional</a>),
     *         or if the specified collection is null
     * @see    #contains(Object)
     */
    boolean containsAll(Collection<?> c);

    /**
     * Adds all of the elements in the specified collection to this set if
     * they're not already present (optional operation).  If the specified
     * collection is also a set, the <tt>addAll</tt> operation effectively
     * modifies this set so that its value is the <i>union</i> of the two
     * sets.  The behavior of this operation is undefined if the specified
     * collection is modified while the operation is in progress.
     *
     * <p>
     *  将指定集合中的所有元素添加到此集合(如果它们尚未存在)(可选操作)。
     * 如果指定的集合也是一个集合,则<tt> addAll </tt>操作有效地修改此集合,以使其值为两个集合的<i> union </i>。如果在操作正在进行时修改指定的集合,则此操作的行为是未定义的。
     * 
     * 
     * @param  c collection containing elements to be added to this set
     * @return <tt>true</tt> if this set changed as a result of the call
     *
     * @throws UnsupportedOperationException if the <tt>addAll</tt> operation
     *         is not supported by this set
     * @throws ClassCastException if the class of an element of the
     *         specified collection prevents it from being added to this set
     * @throws NullPointerException if the specified collection contains one
     *         or more null elements and this set does not permit null
     *         elements, or if the specified collection is null
     * @throws IllegalArgumentException if some property of an element of the
     *         specified collection prevents it from being added to this set
     * @see #add(Object)
     */
    boolean addAll(Collection<? extends E> c);

    /**
     * Retains only the elements in this set that are contained in the
     * specified collection (optional operation).  In other words, removes
     * from this set all of its elements that are not contained in the
     * specified collection.  If the specified collection is also a set, this
     * operation effectively modifies this set so that its value is the
     * <i>intersection</i> of the two sets.
     *
     * <p>
     * 仅保留此集中包含在指定集合中的元素(可选操作)。换句话说,从此集合中删除未包含在指定集合中的所有元素。如果指定的集合也是一个集合,则此操作有效地修改此集合,以使其值为两个集合的<i>交集</i>。
     * 
     * 
     * @param  c collection containing elements to be retained in this set
     * @return <tt>true</tt> if this set changed as a result of the call
     * @throws UnsupportedOperationException if the <tt>retainAll</tt> operation
     *         is not supported by this set
     * @throws ClassCastException if the class of an element of this set
     *         is incompatible with the specified collection
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if this set contains a null element and the
     *         specified collection does not permit null elements
     *         (<a href="Collection.html#optional-restrictions">optional</a>),
     *         or if the specified collection is null
     * @see #remove(Object)
     */
    boolean retainAll(Collection<?> c);

    /**
     * Removes from this set all of its elements that are contained in the
     * specified collection (optional operation).  If the specified
     * collection is also a set, this operation effectively modifies this
     * set so that its value is the <i>asymmetric set difference</i> of
     * the two sets.
     *
     * <p>
     *  从此集合中删除包含在指定集合中的所有元素(可选操作)。如果指定的集合也是集合,则此操作有效地修改该集合,使得其值是两个集合的<i>非对称集合差异</i>。
     * 
     * 
     * @param  c collection containing elements to be removed from this set
     * @return <tt>true</tt> if this set changed as a result of the call
     * @throws UnsupportedOperationException if the <tt>removeAll</tt> operation
     *         is not supported by this set
     * @throws ClassCastException if the class of an element of this set
     *         is incompatible with the specified collection
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if this set contains a null element and the
     *         specified collection does not permit null elements
     *         (<a href="Collection.html#optional-restrictions">optional</a>),
     *         or if the specified collection is null
     * @see #remove(Object)
     * @see #contains(Object)
     */
    boolean removeAll(Collection<?> c);

    /**
     * Removes all of the elements from this set (optional operation).
     * The set will be empty after this call returns.
     *
     * <p>
     *  删除此集合中的所有元素(可选操作)。此调用返回后,集合将为空。
     * 
     * 
     * @throws UnsupportedOperationException if the <tt>clear</tt> method
     *         is not supported by this set
     */
    void clear();


    // Comparison and hashing

    /**
     * Compares the specified object with this set for equality.  Returns
     * <tt>true</tt> if the specified object is also a set, the two sets
     * have the same size, and every member of the specified set is
     * contained in this set (or equivalently, every member of this set is
     * contained in the specified set).  This definition ensures that the
     * equals method works properly across different implementations of the
     * set interface.
     *
     * <p>
     *  将指定的对象与此设置相比较以确保相等。
     * 返回<tt> true </tt>如果指定的对象也是一个集合,这两个集合具有相同的大小,并且指定集合的​​每个成员都包含在此集合中(或者等效地,此集合的每个成员都包含在指定集)。
     * 此定义确保equals方法在集合接口的不同实现中正常工作。
     * 
     * 
     * @param o object to be compared for equality with this set
     * @return <tt>true</tt> if the specified object is equal to this set
     */
    boolean equals(Object o);

    /**
     * Returns the hash code value for this set.  The hash code of a set is
     * defined to be the sum of the hash codes of the elements in the set,
     * where the hash code of a <tt>null</tt> element is defined to be zero.
     * This ensures that <tt>s1.equals(s2)</tt> implies that
     * <tt>s1.hashCode()==s2.hashCode()</tt> for any two sets <tt>s1</tt>
     * and <tt>s2</tt>, as required by the general contract of
     * {@link Object#hashCode}.
     *
     * <p>
     * 返回此集合的哈希码值。集合的哈希码被定义为集合中的元素的哈希码的总和,其中<tt> null </tt>元素的哈希码被定义为零。
     * 这可以确保<tt> s1.equals(s2)</tt>意味着任何两个集合<tt> s1 </tt>的<tt> s1.hashCode()== s2.hashCode()</tt> <tt> s2 </tt>
     * ,根据{@link Object#hashCode}的一般合同的要求。
     * 返回此集合的哈希码值。集合的哈希码被定义为集合中的元素的哈希码的总和,其中<tt> null </tt>元素的哈希码被定义为零。
     * 
     * 
     * @return the hash code value for this set
     * @see Object#equals(Object)
     * @see Set#equals(Object)
     */
    int hashCode();

    /**
     * Creates a {@code Spliterator} over the elements in this set.
     *
     * <p>The {@code Spliterator} reports {@link Spliterator#DISTINCT}.
     * Implementations should document the reporting of additional
     * characteristic values.
     *
     * @implSpec
     * The default implementation creates a
     * <em><a href="Spliterator.html#binding">late-binding</a></em> spliterator
     * from the set's {@code Iterator}.  The spliterator inherits the
     * <em>fail-fast</em> properties of the set's iterator.
     * <p>
     * The created {@code Spliterator} additionally reports
     * {@link Spliterator#SIZED}.
     *
     * @implNote
     * The created {@code Spliterator} additionally reports
     * {@link Spliterator#SUBSIZED}.
     *
     * <p>
     *  在此集合中的元素上创建{@code Spliterator}。
     * 
     *  <p> {@code Spliterator}报告{@link Spliterator#DISTINCT}。实施应记录附加特性值的报告。
     * 
     *  @implSpec默认实现从集合的{@code Iterator}中创建<em> <a href="Spliterator.html#binding">延迟绑定</a> </em>分隔符。
     * 分隔符继承集合的迭代器的<em> fail-fast </em>属性。
     * 
     * @return a {@code Spliterator} over the elements in this set
     * @since 1.8
     */
    @Override
    default Spliterator<E> spliterator() {
        return Spliterators.spliterator(this, Spliterator.DISTINCT);
    }
}
