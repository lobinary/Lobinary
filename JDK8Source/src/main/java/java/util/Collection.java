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

import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * The root interface in the <i>collection hierarchy</i>.  A collection
 * represents a group of objects, known as its <i>elements</i>.  Some
 * collections allow duplicate elements and others do not.  Some are ordered
 * and others unordered.  The JDK does not provide any <i>direct</i>
 * implementations of this interface: it provides implementations of more
 * specific subinterfaces like <tt>Set</tt> and <tt>List</tt>.  This interface
 * is typically used to pass collections around and manipulate them where
 * maximum generality is desired.
 *
 * <p><i>Bags</i> or <i>multisets</i> (unordered collections that may contain
 * duplicate elements) should implement this interface directly.
 *
 * <p>All general-purpose <tt>Collection</tt> implementation classes (which
 * typically implement <tt>Collection</tt> indirectly through one of its
 * subinterfaces) should provide two "standard" constructors: a void (no
 * arguments) constructor, which creates an empty collection, and a
 * constructor with a single argument of type <tt>Collection</tt>, which
 * creates a new collection with the same elements as its argument.  In
 * effect, the latter constructor allows the user to copy any collection,
 * producing an equivalent collection of the desired implementation type.
 * There is no way to enforce this convention (as interfaces cannot contain
 * constructors) but all of the general-purpose <tt>Collection</tt>
 * implementations in the Java platform libraries comply.
 *
 * <p>The "destructive" methods contained in this interface, that is, the
 * methods that modify the collection on which they operate, are specified to
 * throw <tt>UnsupportedOperationException</tt> if this collection does not
 * support the operation.  If this is the case, these methods may, but are not
 * required to, throw an <tt>UnsupportedOperationException</tt> if the
 * invocation would have no effect on the collection.  For example, invoking
 * the {@link #addAll(Collection)} method on an unmodifiable collection may,
 * but is not required to, throw the exception if the collection to be added
 * is empty.
 *
 * <p><a name="optional-restrictions">
 * Some collection implementations have restrictions on the elements that
 * they may contain.</a>  For example, some implementations prohibit null elements,
 * and some have restrictions on the types of their elements.  Attempting to
 * add an ineligible element throws an unchecked exception, typically
 * <tt>NullPointerException</tt> or <tt>ClassCastException</tt>.  Attempting
 * to query the presence of an ineligible element may throw an exception,
 * or it may simply return false; some implementations will exhibit the former
 * behavior and some will exhibit the latter.  More generally, attempting an
 * operation on an ineligible element whose completion would not result in
 * the insertion of an ineligible element into the collection may throw an
 * exception or it may succeed, at the option of the implementation.
 * Such exceptions are marked as "optional" in the specification for this
 * interface.
 *
 * <p>It is up to each collection to determine its own synchronization
 * policy.  In the absence of a stronger guarantee by the
 * implementation, undefined behavior may result from the invocation
 * of any method on a collection that is being mutated by another
 * thread; this includes direct invocations, passing the collection to
 * a method that might perform invocations, and using an existing
 * iterator to examine the collection.
 *
 * <p>Many methods in Collections Framework interfaces are defined in
 * terms of the {@link Object#equals(Object) equals} method.  For example,
 * the specification for the {@link #contains(Object) contains(Object o)}
 * method says: "returns <tt>true</tt> if and only if this collection
 * contains at least one element <tt>e</tt> such that
 * <tt>(o==null ? e==null : o.equals(e))</tt>."  This specification should
 * <i>not</i> be construed to imply that invoking <tt>Collection.contains</tt>
 * with a non-null argument <tt>o</tt> will cause <tt>o.equals(e)</tt> to be
 * invoked for any element <tt>e</tt>.  Implementations are free to implement
 * optimizations whereby the <tt>equals</tt> invocation is avoided, for
 * example, by first comparing the hash codes of the two elements.  (The
 * {@link Object#hashCode()} specification guarantees that two objects with
 * unequal hash codes cannot be equal.)  More generally, implementations of
 * the various Collections Framework interfaces are free to take advantage of
 * the specified behavior of underlying {@link Object} methods wherever the
 * implementor deems it appropriate.
 *
 * <p>Some collection operations which perform recursive traversal of the
 * collection may fail with an exception for self-referential instances where
 * the collection directly or indirectly contains itself. This includes the
 * {@code clone()}, {@code equals()}, {@code hashCode()} and {@code toString()}
 * methods. Implementations may optionally handle the self-referential scenario,
 * however most current implementations do not do so.
 *
 * <p>This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @implSpec
 * The default method implementations (inherited or otherwise) do not apply any
 * synchronization protocol.  If a {@code Collection} implementation has a
 * specific synchronization protocol, then it must override default
 * implementations to apply that protocol.
 *
 * <p>
 *  <i>集合层次结构中的根接口</i>。集合表示一组对象,称为其<i>元素</i>。一些集合允许重复元素,而其他集合则不允许。有些是有序的,有些是无序的。
 *  JDK不提供这个接口的任何<i>直接</i>实现：它提供了更具体的子接口的实现,例如<tt> Set </tt>和<tt> List </tt>。
 * 此接口通常用于传递集合并在需要最大通用性的情况下操作它们。
 * 
 *  <p> <i>包裹</i>或<i> multisets </i>(可能包含重复元素的无序集合)应直接实现此接口。
 * 
 * <p>所有通用的<tt>集合</tt>实现类(通常通过其子接口间接实现<tt> Collection </tt>)应提供两个"标准"构造函数：void(无参数)构造函数,它创建一个空集合,以及一个具有<tt>
 *  Collection </tt>类型的单个参数的构造函数,它创建一个与其参数具有相同元素的新集合。
 * 实际上,后一个构造函数允许用户复制任何集合,产生所需实现类型的等效集合。
 * 没有办法强制执行此约定(因为接口不能包含构造函数),但是Java平台库中的所有通用目的<tt> Collection </tt>实现都符合。
 * 
 *  <p>此接口中包含的"破坏性"方法,即修改其操作的集合的方法,如果此集合不支持该操作,则指定抛出<tt> UnsupportedOperationException </tt>。
 * 如果是这种情况,如果调用对集合没有影响,则这些方法可以但不是必须抛出<tt> UnsupportedOperationException </tt>。
 * 例如,如果要添加的集合为空,则对不可修改的集合调用{@link #addAll(Collection)}方法可能会(但不是必须)抛出异常。
 * 
 * <p> <a name="optional-restrictions">一些集合实现对它们可能包含的元素有限制。例如,一些实现禁止null元素,有些实现对它们的元素类型有限制。
 * 尝试添加不合格元素会抛出未检查的异常,通常为<tt> NullPointerException </tt>或<tt> ClassCastException </tt>。
 * 尝试查询不合格元素的存在可能会抛出异常,或者它可能只是返回false;一些实现将展示前一行为,一些将展示后者。
 * 更一般地,尝试对不合格元素的操作可以抛出异常,或者它可以成功,在执行的选择时,其完成不会导致不合格元素插入到集合中。这种异常在此接口的规范中标记为"可选"。
 * 
 *  <p>每个集合都需要确定自己的同步策略。
 * 在没有通过实现的更强的保证时,未定义的行为可能由调用由另一线程突变的集合上的任何方法而导致;这包括直接调用,将​​集合传递到可能执行调用的方法,以及使用现有的迭代器来检查集合。
 * 
 * <p> Collections框架接口中的许多方法都是根据{@link Object#equals(Object)equals}方法来定义的。
 * 例如,{@ link #contains(Object)contains(Object o)}方法的规范说："当且仅当这个集合至少包含一个元素<tt> e <时,返回<tt> true </tt> / 
 * tt>,使得<tt>(o == null?e == null：o.equals(e))</tt>。
 * <p> Collections框架接口中的许多方法都是根据{@link Object#equals(Object)equals}方法来定义的。
 * 此规范不应被解释为暗示使用非空参数<tt> o </tt>调用<tt> Collection.contains </tt>将导致<tt> o.equals e)</tt>为任何元素<tt> e </tt>
 * 调用。
 * <p> Collections框架接口中的许多方法都是根据{@link Object#equals(Object)equals}方法来定义的。
 * 实现可以自由地实现优化,从而避免<tt>等于</tt>调用,例如,首先比较两个元素的散列码。 ({@ link Object#hashCode()}规范保证具有不相等的哈希码的两个对象不能相等。
 * )更一般地,各种集合框架接口的实现可以利用底层{@link对象}方法,无论实现者认为合适。
 * 
 * <p>对集合执行递归遍历的一些收集操作可能会失败,自集合直接或间接包含自身的自引用实例的异常。
 * 这包括{@code clone()},{@code equals()},{@code hashCode()}和{@code toString()}方法。
 * 实现可以可选地处理自引用场景,然而大多数当前实现不这样做。
 * 
 *  <p>此接口是的成员
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 *  Java集合框架</a>。
 * 
 *  @implSpec默认方法实现(继承或其他)不应用任何同步协议。如果{@code Collection}实现具有特定的同步协议,则它必须覆盖默认实现以应用该协议。
 * 
 * 
 * @param <E> the type of elements in this collection
 *
 * @author  Josh Bloch
 * @author  Neal Gafter
 * @see     Set
 * @see     List
 * @see     Map
 * @see     SortedSet
 * @see     SortedMap
 * @see     HashSet
 * @see     TreeSet
 * @see     ArrayList
 * @see     LinkedList
 * @see     Vector
 * @see     Collections
 * @see     Arrays
 * @see     AbstractCollection
 * @since 1.2
 */

public interface Collection<E> extends Iterable<E> {
    // Query Operations

    /**
     * Returns the number of elements in this collection.  If this collection
     * contains more than <tt>Integer.MAX_VALUE</tt> elements, returns
     * <tt>Integer.MAX_VALUE</tt>.
     *
     * <p>
     *  返回此集合中的元素数。如果此集合包含的元素超过<tt> Integer.MAX_VALUE </tt>,则返回<tt> Integer.MAX_VALUE </tt>。
     * 
     * 
     * @return the number of elements in this collection
     */
    int size();

    /**
     * Returns <tt>true</tt> if this collection contains no elements.
     *
     * <p>
     *  如果此集合不包含元素,则返回<tt> true </tt>。
     * 
     * 
     * @return <tt>true</tt> if this collection contains no elements
     */
    boolean isEmpty();

    /**
     * Returns <tt>true</tt> if this collection contains the specified element.
     * More formally, returns <tt>true</tt> if and only if this collection
     * contains at least one element <tt>e</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
     *
     * <p>
     *  如果此集合包含指定的元素,则返回<tt> true </tt>。
     * 更正式地说,如果且仅当此集合至少包含一个元素<tt> e </tt>,使得<tt>(o == null&nbsp;?&nbsp; e == null&nbsp; ：&nbsp; o.equals(e))
     * </tt>。
     *  如果此集合包含指定的元素,则返回<tt> true </tt>。
     * 
     * 
     * @param o element whose presence in this collection is to be tested
     * @return <tt>true</tt> if this collection contains the specified
     *         element
     * @throws ClassCastException if the type of the specified element
     *         is incompatible with this collection
     *         (<a href="#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null and this
     *         collection does not permit null elements
     *         (<a href="#optional-restrictions">optional</a>)
     */
    boolean contains(Object o);

    /**
     * Returns an iterator over the elements in this collection.  There are no
     * guarantees concerning the order in which the elements are returned
     * (unless this collection is an instance of some class that provides a
     * guarantee).
     *
     * <p>
     *  在此集合中的元素上返回一个迭代器。对于返回元素的顺序没有保证(除非这个集合是提供保证的某个类的实例)。
     * 
     * 
     * @return an <tt>Iterator</tt> over the elements in this collection
     */
    Iterator<E> iterator();

    /**
     * Returns an array containing all of the elements in this collection.
     * If this collection makes any guarantees as to what order its elements
     * are returned by its iterator, this method must return the elements in
     * the same order.
     *
     * <p>The returned array will be "safe" in that no references to it are
     * maintained by this collection.  (In other words, this method must
     * allocate a new array even if this collection is backed by an array).
     * The caller is thus free to modify the returned array.
     *
     * <p>This method acts as bridge between array-based and collection-based
     * APIs.
     *
     * <p>
     * 返回一个包含此集合中所有元素的数组。如果此集合对其元素由其迭代器返回的顺序作出任何保证,则此方法必须以相同的顺序返回元素。
     * 
     *  <p>返回的数组将是"安全的",因为没有对它的引用由此集合维护。 (换句话说,即使此集合由数组支持,此方法必须分配一个新数组)。因此调用者可以自由地修改返回的数组。
     * 
     *  <p>此方法充当基于阵列和基于集合的API之间的桥梁。
     * 
     * 
     * @return an array containing all of the elements in this collection
     */
    Object[] toArray();

    /**
     * Returns an array containing all of the elements in this collection;
     * the runtime type of the returned array is that of the specified array.
     * If the collection fits in the specified array, it is returned therein.
     * Otherwise, a new array is allocated with the runtime type of the
     * specified array and the size of this collection.
     *
     * <p>If this collection fits in the specified array with room to spare
     * (i.e., the array has more elements than this collection), the element
     * in the array immediately following the end of the collection is set to
     * <tt>null</tt>.  (This is useful in determining the length of this
     * collection <i>only</i> if the caller knows that this collection does
     * not contain any <tt>null</tt> elements.)
     *
     * <p>If this collection makes any guarantees as to what order its elements
     * are returned by its iterator, this method must return the elements in
     * the same order.
     *
     * <p>Like the {@link #toArray()} method, this method acts as bridge between
     * array-based and collection-based APIs.  Further, this method allows
     * precise control over the runtime type of the output array, and may,
     * under certain circumstances, be used to save allocation costs.
     *
     * <p>Suppose <tt>x</tt> is a collection known to contain only strings.
     * The following code can be used to dump the collection into a newly
     * allocated array of <tt>String</tt>:
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
     *  <p>如果此集合适合具有空余空间的指定数组(即,数组具有比此集合更多的元素),则紧跟收集结束后的数组中的元素将设置为<tt> null </tt >。
     *  (如果调用者知道此集合不包含任何<tt> null </tt>元素,则此方法仅用于确定此集合的长度</i>)。
     * 
     *  <p>如果此集合对其元素由其迭代器返回的顺序作出任何保证,则此方法必须以相同的顺序返回元素。
     * 
     * <p>与{@link #toArray()}方法类似,此方法充当基于数组和基于集合的API之间的桥梁。此外,该方法允许对输出阵列的运行时类型的精确控制,并且在某些情况下可以用于节省分配成本。
     * 
     *  <p>假设<tt> x </tt>是一个已知只包含字符串的集合。以下代码可用于将集合转储到新分配的<tt> String </tt>数组中：
     * 
     * <pre>
     *  String [] y = x.toArray(new String [0]); </pre>
     * 
     *  注意,<tt> toArray(new Object [0])</tt>在功能上与<tt> toArray()</tt>相同。
     * 
     * 
     * @param <T> the runtime type of the array to contain the collection
     * @param a the array into which the elements of this collection are to be
     *        stored, if it is big enough; otherwise, a new array of the same
     *        runtime type is allocated for this purpose.
     * @return an array containing all of the elements in this collection
     * @throws ArrayStoreException if the runtime type of the specified array
     *         is not a supertype of the runtime type of every element in
     *         this collection
     * @throws NullPointerException if the specified array is null
     */
    <T> T[] toArray(T[] a);

    // Modification Operations

    /**
     * Ensures that this collection contains the specified element (optional
     * operation).  Returns <tt>true</tt> if this collection changed as a
     * result of the call.  (Returns <tt>false</tt> if this collection does
     * not permit duplicates and already contains the specified element.)<p>
     *
     * Collections that support this operation may place limitations on what
     * elements may be added to this collection.  In particular, some
     * collections will refuse to add <tt>null</tt> elements, and others will
     * impose restrictions on the type of elements that may be added.
     * Collection classes should clearly specify in their documentation any
     * restrictions on what elements may be added.<p>
     *
     * If a collection refuses to add a particular element for any reason
     * other than that it already contains the element, it <i>must</i> throw
     * an exception (rather than returning <tt>false</tt>).  This preserves
     * the invariant that a collection always contains the specified element
     * after this call returns.
     *
     * <p>
     *  确保此集合包含指定的元素(可选操作)。如果此集合由于调用而更改,则返回<tt> true </tt>。 (如果此集合不允许重复且已包含指定的元素,则返回<tt> false </tt>。)<p>
     * 
     *  支持此操作的集合可能会对可以添加到此集合的元素设置限制。特别地,一些集合将拒绝添加<tt> null </tt>元素,并且其他集合将对可以添加的元素的类型施加限制。
     * 集合类应在其文档中明确指定对可添加哪些元素的任何限制。<p>。
     * 
     * 如果一个集合拒绝添加一个特定元素的任何原因,除了它已经包含该元素,它<i>必须</i>抛出一个异常(而不是返回<tt> false </tt>)。这保留了在调用返回之后集合始终包含指定元素的不变量。
     * 
     * 
     * @param e element whose presence in this collection is to be ensured
     * @return <tt>true</tt> if this collection changed as a result of the
     *         call
     * @throws UnsupportedOperationException if the <tt>add</tt> operation
     *         is not supported by this collection
     * @throws ClassCastException if the class of the specified element
     *         prevents it from being added to this collection
     * @throws NullPointerException if the specified element is null and this
     *         collection does not permit null elements
     * @throws IllegalArgumentException if some property of the element
     *         prevents it from being added to this collection
     * @throws IllegalStateException if the element cannot be added at this
     *         time due to insertion restrictions
     */
    boolean add(E e);

    /**
     * Removes a single instance of the specified element from this
     * collection, if it is present (optional operation).  More formally,
     * removes an element <tt>e</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>, if
     * this collection contains one or more such elements.  Returns
     * <tt>true</tt> if this collection contained the specified element (or
     * equivalently, if this collection changed as a result of the call).
     *
     * <p>
     *  从此集合中删除指定元素的单个实例(如果存在)(可选操作)。
     * 更正式地,删除元素<tt> e </tt>,使得<tt>(o == null&nbsp;?&nbsp; e == null&nbsp;：&nbsp; o.equals(e))</tt>集合包含一个或多个
     * 这样的元素。
     *  从此集合中删除指定元素的单个实例(如果存在)(可选操作)。如果此集合包含指定的元素(或等效地,如果此集合作为调用的结果而更改),则返回<tt> true </tt>。
     * 
     * 
     * @param o element to be removed from this collection, if present
     * @return <tt>true</tt> if an element was removed as a result of this call
     * @throws ClassCastException if the type of the specified element
     *         is incompatible with this collection
     *         (<a href="#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null and this
     *         collection does not permit null elements
     *         (<a href="#optional-restrictions">optional</a>)
     * @throws UnsupportedOperationException if the <tt>remove</tt> operation
     *         is not supported by this collection
     */
    boolean remove(Object o);


    // Bulk Operations

    /**
     * Returns <tt>true</tt> if this collection contains all of the elements
     * in the specified collection.
     *
     * <p>
     *  如果此集合包含指定集合中的所有元素,则返回<tt> true </tt>。
     * 
     * 
     * @param  c collection to be checked for containment in this collection
     * @return <tt>true</tt> if this collection contains all of the elements
     *         in the specified collection
     * @throws ClassCastException if the types of one or more elements
     *         in the specified collection are incompatible with this
     *         collection
     *         (<a href="#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified collection contains one
     *         or more null elements and this collection does not permit null
     *         elements
     *         (<a href="#optional-restrictions">optional</a>),
     *         or if the specified collection is null.
     * @see    #contains(Object)
     */
    boolean containsAll(Collection<?> c);

    /**
     * Adds all of the elements in the specified collection to this collection
     * (optional operation).  The behavior of this operation is undefined if
     * the specified collection is modified while the operation is in progress.
     * (This implies that the behavior of this call is undefined if the
     * specified collection is this collection, and this collection is
     * nonempty.)
     *
     * <p>
     *  将指定集合中的所有元素添加到此集合(可选操作)。如果在操作正在进行时修改指定的集合,则此操作的行为是未定义的。 (这意味着如果指定的集合是此集合,并且此集合是非空的,则此调用的行为是未定义的。)
     * 
     * 
     * @param c collection containing elements to be added to this collection
     * @return <tt>true</tt> if this collection changed as a result of the call
     * @throws UnsupportedOperationException if the <tt>addAll</tt> operation
     *         is not supported by this collection
     * @throws ClassCastException if the class of an element of the specified
     *         collection prevents it from being added to this collection
     * @throws NullPointerException if the specified collection contains a
     *         null element and this collection does not permit null elements,
     *         or if the specified collection is null
     * @throws IllegalArgumentException if some property of an element of the
     *         specified collection prevents it from being added to this
     *         collection
     * @throws IllegalStateException if not all the elements can be added at
     *         this time due to insertion restrictions
     * @see #add(Object)
     */
    boolean addAll(Collection<? extends E> c);

    /**
     * Removes all of this collection's elements that are also contained in the
     * specified collection (optional operation).  After this call returns,
     * this collection will contain no elements in common with the specified
     * collection.
     *
     * <p>
     *  删除所有也包含在指定集合中的集合的元素(可选操作)。此调用返回后,此集合将不包含与指定集合相同的元素。
     * 
     * 
     * @param c collection containing elements to be removed from this collection
     * @return <tt>true</tt> if this collection changed as a result of the
     *         call
     * @throws UnsupportedOperationException if the <tt>removeAll</tt> method
     *         is not supported by this collection
     * @throws ClassCastException if the types of one or more elements
     *         in this collection are incompatible with the specified
     *         collection
     *         (<a href="#optional-restrictions">optional</a>)
     * @throws NullPointerException if this collection contains one or more
     *         null elements and the specified collection does not support
     *         null elements
     *         (<a href="#optional-restrictions">optional</a>),
     *         or if the specified collection is null
     * @see #remove(Object)
     * @see #contains(Object)
     */
    boolean removeAll(Collection<?> c);

    /**
     * Removes all of the elements of this collection that satisfy the given
     * predicate.  Errors or runtime exceptions thrown during iteration or by
     * the predicate are relayed to the caller.
     *
     * @implSpec
     * The default implementation traverses all elements of the collection using
     * its {@link #iterator}.  Each matching element is removed using
     * {@link Iterator#remove()}.  If the collection's iterator does not
     * support removal then an {@code UnsupportedOperationException} will be
     * thrown on the first matching element.
     *
     * <p>
     * 删除此集合中满足给定谓词的所有元素。在迭代期间抛出的错误或运行时异常或谓词被传递给调用者。
     * 
     *  @implSpec默认实现使用其{@link #iterator}遍历集合的所有元素。使用{@link迭代器#remove()}删除每个匹配元素。
     * 如果集合的迭代器不支持删除,则会在第一个匹配元素上抛出{@code UnsupportedOperationException}。
     * 
     * 
     * @param filter a predicate which returns {@code true} for elements to be
     *        removed
     * @return {@code true} if any elements were removed
     * @throws NullPointerException if the specified filter is null
     * @throws UnsupportedOperationException if elements cannot be removed
     *         from this collection.  Implementations may throw this exception if a
     *         matching element cannot be removed or if, in general, removal is not
     *         supported.
     * @since 1.8
     */
    default boolean removeIf(Predicate<? super E> filter) {
        Objects.requireNonNull(filter);
        boolean removed = false;
        final Iterator<E> each = iterator();
        while (each.hasNext()) {
            if (filter.test(each.next())) {
                each.remove();
                removed = true;
            }
        }
        return removed;
    }

    /**
     * Retains only the elements in this collection that are contained in the
     * specified collection (optional operation).  In other words, removes from
     * this collection all of its elements that are not contained in the
     * specified collection.
     *
     * <p>
     *  仅保留此集合中包含在指定集合中的元素(可选操作)。换句话说,从此集合中删除不包含在指定集合中的所有元素。
     * 
     * 
     * @param c collection containing elements to be retained in this collection
     * @return <tt>true</tt> if this collection changed as a result of the call
     * @throws UnsupportedOperationException if the <tt>retainAll</tt> operation
     *         is not supported by this collection
     * @throws ClassCastException if the types of one or more elements
     *         in this collection are incompatible with the specified
     *         collection
     *         (<a href="#optional-restrictions">optional</a>)
     * @throws NullPointerException if this collection contains one or more
     *         null elements and the specified collection does not permit null
     *         elements
     *         (<a href="#optional-restrictions">optional</a>),
     *         or if the specified collection is null
     * @see #remove(Object)
     * @see #contains(Object)
     */
    boolean retainAll(Collection<?> c);

    /**
     * Removes all of the elements from this collection (optional operation).
     * The collection will be empty after this method returns.
     *
     * <p>
     *  从此集合中删除所有元素(可选操作)。此方法返回后,集合将为空。
     * 
     * 
     * @throws UnsupportedOperationException if the <tt>clear</tt> operation
     *         is not supported by this collection
     */
    void clear();


    // Comparison and hashing

    /**
     * Compares the specified object with this collection for equality. <p>
     *
     * While the <tt>Collection</tt> interface adds no stipulations to the
     * general contract for the <tt>Object.equals</tt>, programmers who
     * implement the <tt>Collection</tt> interface "directly" (in other words,
     * create a class that is a <tt>Collection</tt> but is not a <tt>Set</tt>
     * or a <tt>List</tt>) must exercise care if they choose to override the
     * <tt>Object.equals</tt>.  It is not necessary to do so, and the simplest
     * course of action is to rely on <tt>Object</tt>'s implementation, but
     * the implementor may wish to implement a "value comparison" in place of
     * the default "reference comparison."  (The <tt>List</tt> and
     * <tt>Set</tt> interfaces mandate such value comparisons.)<p>
     *
     * The general contract for the <tt>Object.equals</tt> method states that
     * equals must be symmetric (in other words, <tt>a.equals(b)</tt> if and
     * only if <tt>b.equals(a)</tt>).  The contracts for <tt>List.equals</tt>
     * and <tt>Set.equals</tt> state that lists are only equal to other lists,
     * and sets to other sets.  Thus, a custom <tt>equals</tt> method for a
     * collection class that implements neither the <tt>List</tt> nor
     * <tt>Set</tt> interface must return <tt>false</tt> when this collection
     * is compared to any list or set.  (By the same logic, it is not possible
     * to write a class that correctly implements both the <tt>Set</tt> and
     * <tt>List</tt> interfaces.)
     *
     * <p>
     *  将指定的对象与此集合比较以确保相等。 <p>
     * 
     * 虽然<tt> Collection </tt>界面未对<tt> Object.equals </tt>的一般合同增加任何规定,但是直接实现<tt> Collection </tt>界面的程序员字词,创建
     * 一个<tt>集合</tt>但不是<tt>设置</tt>或<tt>列表</tt>的类),如果他们选择覆盖<tt > Object.equals </tt>。
     * 没有必要这样做,并且最简单的动作过程是依赖于<tt> Object </tt>的实现,但是实现者可能希望实现"值比较"代替默认的"参考比较"。
     *  (<tt>列表</tt>和<tt>设置</tt>接口强制进行此类值比较。)<p>。
     * 
     *  <tt> Object.equals </tt>方法的一般契约声明,equals必须是对称的(换句话说,<tt> a.equals(b)</tt>当且仅当<tt> b.equals (a)</tt>)
     * 。
     *  <tt> List.equals </tt>和<tt> Set.equals </tt>的合约声明列表只等于其他列表,并设置为其他集合。
     * 因此,实现<tt> List </tt>或<tt> Set </tt>接口的集合类的自定义<tt> equals </tt>方法必须返回<tt> false </tt>此集合将与任何列表或集合进行比较。
     *  <tt> List.equals </tt>和<tt> Set.equals </tt>的合约声明列表只等于其他列表,并设置为其他集合。
     *  (同样的逻辑,不可能写一个正确实现<tt> Set </tt>和<tt> List </tt>接口的类)。
     * 
     * 
     * @param o object to be compared for equality with this collection
     * @return <tt>true</tt> if the specified object is equal to this
     * collection
     *
     * @see Object#equals(Object)
     * @see Set#equals(Object)
     * @see List#equals(Object)
     */
    boolean equals(Object o);

    /**
     * Returns the hash code value for this collection.  While the
     * <tt>Collection</tt> interface adds no stipulations to the general
     * contract for the <tt>Object.hashCode</tt> method, programmers should
     * take note that any class that overrides the <tt>Object.equals</tt>
     * method must also override the <tt>Object.hashCode</tt> method in order
     * to satisfy the general contract for the <tt>Object.hashCode</tt> method.
     * In particular, <tt>c1.equals(c2)</tt> implies that
     * <tt>c1.hashCode()==c2.hashCode()</tt>.
     *
     * <p>
     * 返回此集合的哈希码值。
     * 虽然<tt> Collection </tt>界面未对<tt> Object.hashCode </tt>方法的一般合同增加任何约定,但程序员应注意,任何覆盖<tt> Object.equals </tt>
     *  tt>方法还必须覆盖<tt> Object.hashCode </tt>方法,以满足<tt> Object.hashCode </tt>方法的一般合同。
     * 返回此集合的哈希码值。特别地,<tt> c1.equals(c2)</tt>意味着<tt> c1.hashCode()== c2.hashCode()</tt>。
     * 
     * 
     * @return the hash code value for this collection
     *
     * @see Object#hashCode()
     * @see Object#equals(Object)
     */
    int hashCode();

    /**
     * Creates a {@link Spliterator} over the elements in this collection.
     *
     * Implementations should document characteristic values reported by the
     * spliterator.  Such characteristic values are not required to be reported
     * if the spliterator reports {@link Spliterator#SIZED} and this collection
     * contains no elements.
     *
     * <p>The default implementation should be overridden by subclasses that
     * can return a more efficient spliterator.  In order to
     * preserve expected laziness behavior for the {@link #stream()} and
     * {@link #parallelStream()}} methods, spliterators should either have the
     * characteristic of {@code IMMUTABLE} or {@code CONCURRENT}, or be
     * <em><a href="Spliterator.html#binding">late-binding</a></em>.
     * If none of these is practical, the overriding class should describe the
     * spliterator's documented policy of binding and structural interference,
     * and should override the {@link #stream()} and {@link #parallelStream()}
     * methods to create streams using a {@code Supplier} of the spliterator,
     * as in:
     * <pre>{@code
     *     Stream<E> s = StreamSupport.stream(() -> spliterator(), spliteratorCharacteristics)
     * }</pre>
     * <p>These requirements ensure that streams produced by the
     * {@link #stream()} and {@link #parallelStream()} methods will reflect the
     * contents of the collection as of initiation of the terminal stream
     * operation.
     *
     * @implSpec
     * The default implementation creates a
     * <em><a href="Spliterator.html#binding">late-binding</a></em> spliterator
     * from the collections's {@code Iterator}.  The spliterator inherits the
     * <em>fail-fast</em> properties of the collection's iterator.
     * <p>
     * The created {@code Spliterator} reports {@link Spliterator#SIZED}.
     *
     * @implNote
     * The created {@code Spliterator} additionally reports
     * {@link Spliterator#SUBSIZED}.
     *
     * <p>If a spliterator covers no elements then the reporting of additional
     * characteristic values, beyond that of {@code SIZED} and {@code SUBSIZED},
     * does not aid clients to control, specialize or simplify computation.
     * However, this does enable shared use of an immutable and empty
     * spliterator instance (see {@link Spliterators#emptySpliterator()}) for
     * empty collections, and enables clients to determine if such a spliterator
     * covers no elements.
     *
     * <p>
     *  在此集合中的元素上创建{@link Spliterator}。
     * 
     *  实现应记录分割器报告的特征值。如果分割器报告{@link Spliterator#SIZED}并且此集合不包含元素,则不需要报告此类特征值。
     * 
     * <p>默认实现应该被子类覆盖,可以返回更高效的spliterator。
     * 为了保持{@link #stream()}和{@link #parallelStream()}}方法的预期延迟行为,分隔符应该具有{@code IMMUTABLE}或{@code CONCURRENT}
     * 的特性,或者<em> <a href="Spliterator.html#binding">延迟绑定</a> </em>。
     * <p>默认实现应该被子类覆盖,可以返回更高效的spliterator。
     * 如果这些都不可行,覆盖类应该描述分割器的绑定和结构干扰的文档化策略,并且应该重写{@link #stream()}和{@link #parallelStream()}方法来使用{ @code {E} s
     *  = StreamSupport.stream(() - > spliterator(),spliteratorCharacteristics)} </pre> <p>这些要求确保流{@ link #stream()}
     * 和{@link #parallelStream()}方法生成的集合将反映终端流操作启动时集合的内容。
     * <p>默认实现应该被子类覆盖,可以返回更高效的spliterator。
     * 
     *  @implSpec默认实现从集合的{@code Iterator}创建<em> <a href="Spliterator.html#binding">延迟绑定</a> </em>分隔符。
     * 分割器继承集合的迭代器的<em> fail-fast </em>属性。
     * <p>
     *  已创建的{@code Spliterator}报告{@link Spliterator#SIZED}。
     * 
     *  @implNote创建的{@code Spliterator}还报告{@link Spliterator#SUBSIZED}。
     * 
     * 
     * @return a {@code Spliterator} over the elements in this collection
     * @since 1.8
     */
    @Override
    default Spliterator<E> spliterator() {
        return Spliterators.spliterator(this, 0);
    }

    /**
     * Returns a sequential {@code Stream} with this collection as its source.
     *
     * <p>This method should be overridden when the {@link #spliterator()}
     * method cannot return a spliterator that is {@code IMMUTABLE},
     * {@code CONCURRENT}, or <em>late-binding</em>. (See {@link #spliterator()}
     * for details.)
     *
     * @implSpec
     * The default implementation creates a sequential {@code Stream} from the
     * collection's {@code Spliterator}.
     *
     * <p>
     * <p>如果分割器不覆盖任何元素,那么除{@code SIZED}和{@code SUBSIZED}之外的其他特征值的报告不会帮助客户端控制,专门化或简化计算。
     * 然而,这使得对于空集合能够共享使用不可变的和空的分裂器实例(参见{@link Spliterators#emptySpliterator()}),并且使得客户端能够确定这样的分裂器是否覆盖没有元素。
     * 
     * 
     * @return a sequential {@code Stream} over the elements in this collection
     * @since 1.8
     */
    default Stream<E> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    /**
     * Returns a possibly parallel {@code Stream} with this collection as its
     * source.  It is allowable for this method to return a sequential stream.
     *
     * <p>This method should be overridden when the {@link #spliterator()}
     * method cannot return a spliterator that is {@code IMMUTABLE},
     * {@code CONCURRENT}, or <em>late-binding</em>. (See {@link #spliterator()}
     * for details.)
     *
     * @implSpec
     * The default implementation creates a parallel {@code Stream} from the
     * collection's {@code Spliterator}.
     *
     * <p>
     *  返回以此集合作为其源的序列{@code Stream}。
     * 
     *  <p>当{@link #spliterator()}方法无法返回{@code IMMUTABLE},{@code CONCURRENT}或<em>延迟绑定</em>的分隔符时,应重写此方法。
     *  (有关详情,请参阅{@link #spliterator()}。)。
     * 
     *  @implSpec默认实现从集合的{@code Spliterator}创建一个顺序{@code Stream}。
     * 
     * 
     * @return a possibly parallel {@code Stream} over the elements in this
     * collection
     * @since 1.8
     */
    default Stream<E> parallelStream() {
        return StreamSupport.stream(spliterator(), true);
    }
}
