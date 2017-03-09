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

import java.io.InvalidObjectException;

/**
 * This class implements the <tt>Set</tt> interface, backed by a hash table
 * (actually a <tt>HashMap</tt> instance).  It makes no guarantees as to the
 * iteration order of the set; in particular, it does not guarantee that the
 * order will remain constant over time.  This class permits the <tt>null</tt>
 * element.
 *
 * <p>This class offers constant time performance for the basic operations
 * (<tt>add</tt>, <tt>remove</tt>, <tt>contains</tt> and <tt>size</tt>),
 * assuming the hash function disperses the elements properly among the
 * buckets.  Iterating over this set requires time proportional to the sum of
 * the <tt>HashSet</tt> instance's size (the number of elements) plus the
 * "capacity" of the backing <tt>HashMap</tt> instance (the number of
 * buckets).  Thus, it's very important not to set the initial capacity too
 * high (or the load factor too low) if iteration performance is important.
 *
 * <p><strong>Note that this implementation is not synchronized.</strong>
 * If multiple threads access a hash set concurrently, and at least one of
 * the threads modifies the set, it <i>must</i> be synchronized externally.
 * This is typically accomplished by synchronizing on some object that
 * naturally encapsulates the set.
 *
 * If no such object exists, the set should be "wrapped" using the
 * {@link Collections#synchronizedSet Collections.synchronizedSet}
 * method.  This is best done at creation time, to prevent accidental
 * unsynchronized access to the set:<pre>
 *   Set s = Collections.synchronizedSet(new HashSet(...));</pre>
 *
 * <p>The iterators returned by this class's <tt>iterator</tt> method are
 * <i>fail-fast</i>: if the set is modified at any time after the iterator is
 * created, in any way except through the iterator's own <tt>remove</tt>
 * method, the Iterator throws a {@link ConcurrentModificationException}.
 * Thus, in the face of concurrent modification, the iterator fails quickly
 * and cleanly, rather than risking arbitrary, non-deterministic behavior at
 * an undetermined time in the future.
 *
 * <p>Note that the fail-fast behavior of an iterator cannot be guaranteed
 * as it is, generally speaking, impossible to make any hard guarantees in the
 * presence of unsynchronized concurrent modification.  Fail-fast iterators
 * throw <tt>ConcurrentModificationException</tt> on a best-effort basis.
 * Therefore, it would be wrong to write a program that depended on this
 * exception for its correctness: <i>the fail-fast behavior of iterators
 * should be used only to detect bugs.</i>
 *
 * <p>This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * <p>
 *  此类实现了由散列表(实际上是一个<tt> HashMap </tt>实例)支持的<tt> Set </tt>接口。它不保证集合的迭代顺序;特别是,它不保证该订单将随时间保持恒定。
 * 此类允许<tt> null </tt>元素。
 * 
 *  <p>此类为基本操作(<tt>添加</tt>,<tt>删除</tt>,<tt>包含</tt>和<tt>大小</tt>)提供恒定时间性能,假设散列函数在这些桶之间正确地分散元素。
 * 在该集合上迭代需要与<tt> HashSet </tt>实例的大小(元素数量)加上后端的"capacity"之和成比例的时间<tt> HashMap </tt>实例)。
 * 因此,如果迭代性能很重要,不要将初始容量设置得太高(或负载系数太低)。
 * 
 *  <p> <strong>请注意,此实现未同步。</strong>如果多个线程同时访问哈希集,并且至少有一个线程修改了集合,则<i>必须</i> 。这通常通过在自然地封装集合的某个对象上同步来实现。
 * 
 * 如果不存在这样的对象,那么应该使用{@link Collections#synchronizeSet Collections.synchronizedSet}方法来"包装"该集合。
 * 这最好在创建时完成,以防止意外的不同步访问集合：<pre> Set s = Collections.synchronizedSet(new HashSet(...)); </pre>。
 * 
 *  <p>此类的<tt>迭代器</tt>方法返回的迭代器<i> fail-fast </i>：如果在创建迭代器之后的任何时间修改集合,迭代器自己的<tt> remove </tt>方法,Iterator会
 * 抛出一个{@link ConcurrentModificationException}。
 * 因此,面对并发修改,迭代器快速而干净地失败,而不是在将来的未确定时间冒任意的,非确定性行为的风险。
 * 
 *  <p>请注意,迭代器的故障快速行为不能得到保证,因为一般来说,在不同步并发修改的情况下不可能做出任何硬的保证。
 * 故障快速迭代器在尽力而为的基础上抛出<tt> ConcurrentModificationException </tt>。
 * 因此,编写依赖于此异常的程序的正确性是错误的：<i>迭代器的故障快速行为应该仅用于检测错误。</i>。
 * 
 *  <p>此类是的成员
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 *  Java集合框架</a>。
 * 
 * 
 * @param <E> the type of elements maintained by this set
 *
 * @author  Josh Bloch
 * @author  Neal Gafter
 * @see     Collection
 * @see     Set
 * @see     TreeSet
 * @see     HashMap
 * @since   1.2
 */

public class HashSet<E>
    extends AbstractSet<E>
    implements Set<E>, Cloneable, java.io.Serializable
{
    static final long serialVersionUID = -5024744406713321676L;

    private transient HashMap<E,Object> map;

    // Dummy value to associate with an Object in the backing Map
    private static final Object PRESENT = new Object();

    /**
     * Constructs a new, empty set; the backing <tt>HashMap</tt> instance has
     * default initial capacity (16) and load factor (0.75).
     * <p>
     *  构造一个新的空集;支持<tt> HashMap </tt>实例具有默认初始容量(16)和负载系数(0.75)。
     * 
     */
    public HashSet() {
        map = new HashMap<>();
    }

    /**
     * Constructs a new set containing the elements in the specified
     * collection.  The <tt>HashMap</tt> is created with default load factor
     * (0.75) and an initial capacity sufficient to contain the elements in
     * the specified collection.
     *
     * <p>
     * 构造包含指定集合中的元素的新集。使用默认负载因子(0.75)和足以包含指定集合中的元素的初始容量创建<tt> HashMap </tt>。
     * 
     * 
     * @param c the collection whose elements are to be placed into this set
     * @throws NullPointerException if the specified collection is null
     */
    public HashSet(Collection<? extends E> c) {
        map = new HashMap<>(Math.max((int) (c.size()/.75f) + 1, 16));
        addAll(c);
    }

    /**
     * Constructs a new, empty set; the backing <tt>HashMap</tt> instance has
     * the specified initial capacity and the specified load factor.
     *
     * <p>
     *  构造一个新的空集;支持<tt> HashMap </tt>实例具有指定的初始容量和指定的负载因子。
     * 
     * 
     * @param      initialCapacity   the initial capacity of the hash map
     * @param      loadFactor        the load factor of the hash map
     * @throws     IllegalArgumentException if the initial capacity is less
     *             than zero, or if the load factor is nonpositive
     */
    public HashSet(int initialCapacity, float loadFactor) {
        map = new HashMap<>(initialCapacity, loadFactor);
    }

    /**
     * Constructs a new, empty set; the backing <tt>HashMap</tt> instance has
     * the specified initial capacity and default load factor (0.75).
     *
     * <p>
     *  构造一个新的空集;支持<tt> HashMap </tt>实例具有指定的初始容量和默认负载因子(0.75)。
     * 
     * 
     * @param      initialCapacity   the initial capacity of the hash table
     * @throws     IllegalArgumentException if the initial capacity is less
     *             than zero
     */
    public HashSet(int initialCapacity) {
        map = new HashMap<>(initialCapacity);
    }

    /**
     * Constructs a new, empty linked hash set.  (This package private
     * constructor is only used by LinkedHashSet.) The backing
     * HashMap instance is a LinkedHashMap with the specified initial
     * capacity and the specified load factor.
     *
     * <p>
     *  构造一个新的,空的链接散列集。 (此包私有构造函数仅由LinkedHashSet使用。)支持HashMap实例是具有指定的初始容量和指定负载因子的LinkedHashMap。
     * 
     * 
     * @param      initialCapacity   the initial capacity of the hash map
     * @param      loadFactor        the load factor of the hash map
     * @param      dummy             ignored (distinguishes this
     *             constructor from other int, float constructor.)
     * @throws     IllegalArgumentException if the initial capacity is less
     *             than zero, or if the load factor is nonpositive
     */
    HashSet(int initialCapacity, float loadFactor, boolean dummy) {
        map = new LinkedHashMap<>(initialCapacity, loadFactor);
    }

    /**
     * Returns an iterator over the elements in this set.  The elements
     * are returned in no particular order.
     *
     * <p>
     *  返回此集合中的元素的迭代器。元素不按特定顺序返回。
     * 
     * 
     * @return an Iterator over the elements in this set
     * @see ConcurrentModificationException
     */
    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }

    /**
     * Returns the number of elements in this set (its cardinality).
     *
     * <p>
     *  返回此集合中的元素数(其基数)。
     * 
     * 
     * @return the number of elements in this set (its cardinality)
     */
    public int size() {
        return map.size();
    }

    /**
     * Returns <tt>true</tt> if this set contains no elements.
     *
     * <p>
     *  如果此集合不包含元素,则返回<tt> true </tt>。
     * 
     * 
     * @return <tt>true</tt> if this set contains no elements
     */
    public boolean isEmpty() {
        return map.isEmpty();
    }

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
     */
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    /**
     * Adds the specified element to this set if it is not already present.
     * More formally, adds the specified element <tt>e</tt> to this set if
     * this set contains no element <tt>e2</tt> such that
     * <tt>(e==null&nbsp;?&nbsp;e2==null&nbsp;:&nbsp;e.equals(e2))</tt>.
     * If this set already contains the element, the call leaves the set
     * unchanged and returns <tt>false</tt>.
     *
     * <p>
     * 如果指定的元素不存在,则将其添加到此集合。
     * 更正式地,如果此集合不包含元素<tt> e2 </tt>,则将指定的元素<tt> e </tt>添加到此集合中,以使<tt>(e == null&nbsp;?&nbsp; e2 == null&nbsp
     *  ;：e.equals(e2))</tt>。
     * 如果指定的元素不存在,则将其添加到此集合。如果此集合已包含元素,则调用使集合保持不变,并返回<tt> false </tt>。
     * 
     * 
     * @param e element to be added to this set
     * @return <tt>true</tt> if this set did not already contain the specified
     * element
     */
    public boolean add(E e) {
        return map.put(e, PRESENT)==null;
    }

    /**
     * Removes the specified element from this set if it is present.
     * More formally, removes an element <tt>e</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>,
     * if this set contains such an element.  Returns <tt>true</tt> if
     * this set contained the element (or equivalently, if this set
     * changed as a result of the call).  (This set will not contain the
     * element once the call returns.)
     *
     * <p>
     *  从此集合中删除指定的元素(如果存在)。
     * 更正式地,删除元素<tt> e </tt>,使得<tt>(o == null&nbsp;?&nbsp; e == null&nbsp;：&nbsp; o.equals(e))</tt> set包含这样的
     * 元素。
     *  从此集合中删除指定的元素(如果存在)。如果此集合包含元素(或等效地,如果此集合作为调用的结果而更改),则返回<tt> true </tt>。 (这个集合在调用返回后不会包含元素。)。
     * 
     * 
     * @param o object to be removed from this set, if present
     * @return <tt>true</tt> if the set contained the specified element
     */
    public boolean remove(Object o) {
        return map.remove(o)==PRESENT;
    }

    /**
     * Removes all of the elements from this set.
     * The set will be empty after this call returns.
     * <p>
     *  删除此集合中的所有元素。此调用返回后,集合将为空。
     * 
     */
    public void clear() {
        map.clear();
    }

    /**
     * Returns a shallow copy of this <tt>HashSet</tt> instance: the elements
     * themselves are not cloned.
     *
     * <p>
     *  返回此<tt> HashSet </tt>实例的浅表副本：元素本身未克隆。
     * 
     * 
     * @return a shallow copy of this set
     */
    @SuppressWarnings("unchecked")
    public Object clone() {
        try {
            HashSet<E> newSet = (HashSet<E>) super.clone();
            newSet.map = (HashMap<E, Object>) map.clone();
            return newSet;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    /**
     * Save the state of this <tt>HashSet</tt> instance to a stream (that is,
     * serialize it).
     *
     * <p>
     *  将此<tt> HashSet </tt>实例的状态保存到流(即,将其序列化)。
     * 
     * 
     * @serialData The capacity of the backing <tt>HashMap</tt> instance
     *             (int), and its load factor (float) are emitted, followed by
     *             the size of the set (the number of elements it contains)
     *             (int), followed by all of its elements (each an Object) in
     *             no particular order.
     */
    private void writeObject(java.io.ObjectOutputStream s)
        throws java.io.IOException {
        // Write out any hidden serialization magic
        s.defaultWriteObject();

        // Write out HashMap capacity and load factor
        s.writeInt(map.capacity());
        s.writeFloat(map.loadFactor());

        // Write out size
        s.writeInt(map.size());

        // Write out all elements in the proper order.
        for (E e : map.keySet())
            s.writeObject(e);
    }

    /**
     * Reconstitute the <tt>HashSet</tt> instance from a stream (that is,
     * deserialize it).
     * <p>
     *  从流重新构建<tt> HashSet </tt>实例(即,反序列化它)。
     * 
     */
    private void readObject(java.io.ObjectInputStream s)
        throws java.io.IOException, ClassNotFoundException {
        // Read in any hidden serialization magic
        s.defaultReadObject();

        // Read capacity and verify non-negative.
        int capacity = s.readInt();
        if (capacity < 0) {
            throw new InvalidObjectException("Illegal capacity: " +
                                             capacity);
        }

        // Read load factor and verify positive and non NaN.
        float loadFactor = s.readFloat();
        if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
            throw new InvalidObjectException("Illegal load factor: " +
                                             loadFactor);
        }

        // Read size and verify non-negative.
        int size = s.readInt();
        if (size < 0) {
            throw new InvalidObjectException("Illegal size: " +
                                             size);
        }

        // Set the capacity according to the size and load factor ensuring that
        // the HashMap is at least 25% full but clamping to maximum capacity.
        capacity = (int) Math.min(size * Math.min(1 / loadFactor, 4.0f),
                HashMap.MAXIMUM_CAPACITY);

        // Create backing HashMap
        map = (((HashSet<?>)this) instanceof LinkedHashSet ?
               new LinkedHashMap<E,Object>(capacity, loadFactor) :
               new HashMap<E,Object>(capacity, loadFactor));

        // Read in all elements in the proper order.
        for (int i=0; i<size; i++) {
            @SuppressWarnings("unchecked")
                E e = (E) s.readObject();
            map.put(e, PRESENT);
        }
    }

    /**
     * Creates a <em><a href="Spliterator.html#binding">late-binding</a></em>
     * and <em>fail-fast</em> {@link Spliterator} over the elements in this
     * set.
     *
     * <p>The {@code Spliterator} reports {@link Spliterator#SIZED} and
     * {@link Spliterator#DISTINCT}.  Overriding implementations should document
     * the reporting of additional characteristic values.
     *
     * <p>
     *  在此集合中的元素上创建<em> <a href="Spliterator.html#binding">延迟绑定</a> </em>和<em>快速失败</em> {@link Spliterator} 
     * 。
     * 
     * 
     * @return a {@code Spliterator} over the elements in this set
     * @since 1.8
     */
    public Spliterator<E> spliterator() {
        return new HashMap.KeySpliterator<E,Object>(map, 0, -1, 0, 0);
    }
}
