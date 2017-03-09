/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * <p>Hash table and linked list implementation of the <tt>Set</tt> interface,
 * with predictable iteration order.  This implementation differs from
 * <tt>HashSet</tt> in that it maintains a doubly-linked list running through
 * all of its entries.  This linked list defines the iteration ordering,
 * which is the order in which elements were inserted into the set
 * (<i>insertion-order</i>).  Note that insertion order is <i>not</i> affected
 * if an element is <i>re-inserted</i> into the set.  (An element <tt>e</tt>
 * is reinserted into a set <tt>s</tt> if <tt>s.add(e)</tt> is invoked when
 * <tt>s.contains(e)</tt> would return <tt>true</tt> immediately prior to
 * the invocation.)
 *
 * <p>This implementation spares its clients from the unspecified, generally
 * chaotic ordering provided by {@link HashSet}, without incurring the
 * increased cost associated with {@link TreeSet}.  It can be used to
 * produce a copy of a set that has the same order as the original, regardless
 * of the original set's implementation:
 * <pre>
 *     void foo(Set s) {
 *         Set copy = new LinkedHashSet(s);
 *         ...
 *     }
 * </pre>
 * This technique is particularly useful if a module takes a set on input,
 * copies it, and later returns results whose order is determined by that of
 * the copy.  (Clients generally appreciate having things returned in the same
 * order they were presented.)
 *
 * <p>This class provides all of the optional <tt>Set</tt> operations, and
 * permits null elements.  Like <tt>HashSet</tt>, it provides constant-time
 * performance for the basic operations (<tt>add</tt>, <tt>contains</tt> and
 * <tt>remove</tt>), assuming the hash function disperses elements
 * properly among the buckets.  Performance is likely to be just slightly
 * below that of <tt>HashSet</tt>, due to the added expense of maintaining the
 * linked list, with one exception: Iteration over a <tt>LinkedHashSet</tt>
 * requires time proportional to the <i>size</i> of the set, regardless of
 * its capacity.  Iteration over a <tt>HashSet</tt> is likely to be more
 * expensive, requiring time proportional to its <i>capacity</i>.
 *
 * <p>A linked hash set has two parameters that affect its performance:
 * <i>initial capacity</i> and <i>load factor</i>.  They are defined precisely
 * as for <tt>HashSet</tt>.  Note, however, that the penalty for choosing an
 * excessively high value for initial capacity is less severe for this class
 * than for <tt>HashSet</tt>, as iteration times for this class are unaffected
 * by capacity.
 *
 * <p><strong>Note that this implementation is not synchronized.</strong>
 * If multiple threads access a linked hash set concurrently, and at least
 * one of the threads modifies the set, it <em>must</em> be synchronized
 * externally.  This is typically accomplished by synchronizing on some
 * object that naturally encapsulates the set.
 *
 * If no such object exists, the set should be "wrapped" using the
 * {@link Collections#synchronizedSet Collections.synchronizedSet}
 * method.  This is best done at creation time, to prevent accidental
 * unsynchronized access to the set: <pre>
 *   Set s = Collections.synchronizedSet(new LinkedHashSet(...));</pre>
 *
 * <p>The iterators returned by this class's <tt>iterator</tt> method are
 * <em>fail-fast</em>: if the set is modified at any time after the iterator
 * is created, in any way except through the iterator's own <tt>remove</tt>
 * method, the iterator will throw a {@link ConcurrentModificationException}.
 * Thus, in the face of concurrent modification, the iterator fails quickly
 * and cleanly, rather than risking arbitrary, non-deterministic behavior at
 * an undetermined time in the future.
 *
 * <p>Note that the fail-fast behavior of an iterator cannot be guaranteed
 * as it is, generally speaking, impossible to make any hard guarantees in the
 * presence of unsynchronized concurrent modification.  Fail-fast iterators
 * throw <tt>ConcurrentModificationException</tt> on a best-effort basis.
 * Therefore, it would be wrong to write a program that depended on this
 * exception for its correctness:   <i>the fail-fast behavior of iterators
 * should be used only to detect bugs.</i>
 *
 * <p>This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * <p>
 *  <p> <tt> Set </tt>界面的散列表和链接列表实现,具有可预测的迭代顺序。此实现不同于<tt> HashSet </tt>,因为它维护一个贯穿其所有条目的双向链表。
 * 此链接列表定义迭代排序,它是元素插入集合的顺序(<i>插入顺序</i>)。请注意,如果元素<i>重新插入</i>到集合中,则插入顺序</i>不会受到影响。
 *  (<tt> s.contains(e))时调用<tt> s.add(e)</tt>时重新插入<tt> e </tt> </tt>会在调用之前立即返回<tt> true </tt>。)。
 * 
 *  <p>这个实现使客户端不受{@link HashSet}提供的未指定的,通常混乱的顺序,而不会增加与{@link TreeSet}相关的成本。
 * 它可以用于产生与原始序列具有相同顺序的集合的副本,而不考虑原始集合的实现：。
 * <pre>
 *  void foo(Set s){Set copy = new LinkedHashSet(s); ...}
 * </pre>
 *  如果模块在输入上获取一个集合,复制它,然后返回其顺序由副本的顺序确定的结果,则此技术特别有用。 (客户通常喜欢以与提交相同的顺序返回)。
 * 
 * <p>此类提供所有可选的<tt> Set </tt>操作,并允许空元素。
 * 与<tt> HashSet </tt>一样,它为基本操作(<tt> add </tt>,<tt>包含</tt>和<tt>删除</tt>)提供了恒定时间性能,散列函数在这些桶之间适当地分散元素。
 * 性能可能略低于<tt> HashSet </tt>,这是由于维护链接列表的额外费用,但有一个例外：对<tt> LinkedHashSet </tt>的迭代需要与<i>尺寸</i>,无论其容量如何。
 * 对<tt> HashSet </tt>的迭代可能更昂贵,需要与其<i>容量成比例的时间。
 * 
 *  <p>链接的哈希集具有影响其性能的两个参数：<i>初始容量</i>和<i>负载因子</i>。它们的定义与<tt> HashSet </tt>一样。
 * 然而,请注意,对于这个类别选择过高的初始容量值的惩罚对于<tt> HashSet </tt>来说不太严格,因为这个类的迭代时间不受容量的影响。
 * 
 *  <p> <strong>请注意,此实现未同步。</strong>如果多个线程同时访问链接的散列集,并且至少有一个线程修改集,则必须同步<em> </em>外部。
 * 这通常通过在自然地封装集合的某个对象上同步来实现。
 * 
 * 如果不存在这样的对象,那么应该使用{@link Collections#synchronizeSet Collections.synchronizedSet}方法来"包装"该集合。
 * 这最好在创建时完成,以防止意外的不同步访问集合：<pre> Set s = Collections.synchronizedSet(new LinkedHashSet(...)); </pre>。
 * 
 *  <p>此类的<tt>迭代器</tt>方法返回的迭代器是</em> fail-fast </em>：如果在创建迭代器之后的任何时间修改集合,迭代器自己的<tt> remove </tt>方法,迭代器将抛
 * 出一个{@link ConcurrentModificationException}。
 * 因此,面对并发修改,迭代器快速而干净地失败,而不是在将来的未确定时间冒任意的,非确定性行为的风险。
 * 
 *  <p>请注意,迭代器的故障快速行为不能得到保证,因为一般来说,在不同步并发修改的情况下不可能做出任何硬的保证。
 * 
 * @param <E> the type of elements maintained by this set
 *
 * @author  Josh Bloch
 * @see     Object#hashCode()
 * @see     Collection
 * @see     Set
 * @see     HashSet
 * @see     TreeSet
 * @see     Hashtable
 * @since   1.4
 */

public class LinkedHashSet<E>
    extends HashSet<E>
    implements Set<E>, Cloneable, java.io.Serializable {

    private static final long serialVersionUID = -2851667679971038690L;

    /**
     * Constructs a new, empty linked hash set with the specified initial
     * capacity and load factor.
     *
     * <p>
     * 故障快速迭代器在尽力而为的基础上抛出<tt> ConcurrentModificationException </tt>。
     * 因此,编写依赖于此异常的程序的正确性是错误的：<i>迭代器的故障快速行为应该仅用于检测错误。</i>。
     * 
     *  <p>此类是的成员
     * <a href="{@docRoot}/../technotes/guides/collections/index.html">
     *  Java集合框架</a>。
     * 
     * 
     * @param      initialCapacity the initial capacity of the linked hash set
     * @param      loadFactor      the load factor of the linked hash set
     * @throws     IllegalArgumentException  if the initial capacity is less
     *               than zero, or if the load factor is nonpositive
     */
    public LinkedHashSet(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor, true);
    }

    /**
     * Constructs a new, empty linked hash set with the specified initial
     * capacity and the default load factor (0.75).
     *
     * <p>
     *  构造具有指定的初始容量和负载因子的新的空链接哈希集。
     * 
     * 
     * @param   initialCapacity   the initial capacity of the LinkedHashSet
     * @throws  IllegalArgumentException if the initial capacity is less
     *              than zero
     */
    public LinkedHashSet(int initialCapacity) {
        super(initialCapacity, .75f, true);
    }

    /**
     * Constructs a new, empty linked hash set with the default initial
     * capacity (16) and load factor (0.75).
     * <p>
     * 构造具有指定初始容量和默认负载系数(0.75)的新的空链接散列集。
     * 
     */
    public LinkedHashSet() {
        super(16, .75f, true);
    }

    /**
     * Constructs a new linked hash set with the same elements as the
     * specified collection.  The linked hash set is created with an initial
     * capacity sufficient to hold the elements in the specified collection
     * and the default load factor (0.75).
     *
     * <p>
     *  使用默认初始容量(16)和负载系数(0.75)构造一个新的空链接散列集。
     * 
     * 
     * @param c  the collection whose elements are to be placed into
     *           this set
     * @throws NullPointerException if the specified collection is null
     */
    public LinkedHashSet(Collection<? extends E> c) {
        super(Math.max(2*c.size(), 11), .75f, true);
        addAll(c);
    }

    /**
     * Creates a <em><a href="Spliterator.html#binding">late-binding</a></em>
     * and <em>fail-fast</em> {@code Spliterator} over the elements in this set.
     *
     * <p>The {@code Spliterator} reports {@link Spliterator#SIZED},
     * {@link Spliterator#DISTINCT}, and {@code ORDERED}.  Implementations
     * should document the reporting of additional characteristic values.
     *
     * @implNote
     * The implementation creates a
     * <em><a href="Spliterator.html#binding">late-binding</a></em> spliterator
     * from the set's {@code Iterator}.  The spliterator inherits the
     * <em>fail-fast</em> properties of the set's iterator.
     * The created {@code Spliterator} additionally reports
     * {@link Spliterator#SUBSIZED}.
     *
     * <p>
     *  构造具有与指定集合相同的元素的新的链接哈希集。创建的链接哈希集的初始容量足以容纳指定集合中的元素和默认负载因子(0.75)。
     * 
     * 
     * @return a {@code Spliterator} over the elements in this set
     * @since 1.8
     */
    @Override
    public Spliterator<E> spliterator() {
        return Spliterators.spliterator(this, Spliterator.DISTINCT | Spliterator.ORDERED);
    }
}
