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

import java.util.function.Consumer;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.io.IOException;

/**
 * <p>Hash table and linked list implementation of the <tt>Map</tt> interface,
 * with predictable iteration order.  This implementation differs from
 * <tt>HashMap</tt> in that it maintains a doubly-linked list running through
 * all of its entries.  This linked list defines the iteration ordering,
 * which is normally the order in which keys were inserted into the map
 * (<i>insertion-order</i>).  Note that insertion order is not affected
 * if a key is <i>re-inserted</i> into the map.  (A key <tt>k</tt> is
 * reinserted into a map <tt>m</tt> if <tt>m.put(k, v)</tt> is invoked when
 * <tt>m.containsKey(k)</tt> would return <tt>true</tt> immediately prior to
 * the invocation.)
 *
 * <p>This implementation spares its clients from the unspecified, generally
 * chaotic ordering provided by {@link HashMap} (and {@link Hashtable}),
 * without incurring the increased cost associated with {@link TreeMap}.  It
 * can be used to produce a copy of a map that has the same order as the
 * original, regardless of the original map's implementation:
 * <pre>
 *     void foo(Map m) {
 *         Map copy = new LinkedHashMap(m);
 *         ...
 *     }
 * </pre>
 * This technique is particularly useful if a module takes a map on input,
 * copies it, and later returns results whose order is determined by that of
 * the copy.  (Clients generally appreciate having things returned in the same
 * order they were presented.)
 *
 * <p>A special {@link #LinkedHashMap(int,float,boolean) constructor} is
 * provided to create a linked hash map whose order of iteration is the order
 * in which its entries were last accessed, from least-recently accessed to
 * most-recently (<i>access-order</i>).  This kind of map is well-suited to
 * building LRU caches.  Invoking the {@code put}, {@code putIfAbsent},
 * {@code get}, {@code getOrDefault}, {@code compute}, {@code computeIfAbsent},
 * {@code computeIfPresent}, or {@code merge} methods results
 * in an access to the corresponding entry (assuming it exists after the
 * invocation completes). The {@code replace} methods only result in an access
 * of the entry if the value is replaced.  The {@code putAll} method generates one
 * entry access for each mapping in the specified map, in the order that
 * key-value mappings are provided by the specified map's entry set iterator.
 * <i>No other methods generate entry accesses.</i>  In particular, operations
 * on collection-views do <i>not</i> affect the order of iteration of the
 * backing map.
 *
 * <p>The {@link #removeEldestEntry(Map.Entry)} method may be overridden to
 * impose a policy for removing stale mappings automatically when new mappings
 * are added to the map.
 *
 * <p>This class provides all of the optional <tt>Map</tt> operations, and
 * permits null elements.  Like <tt>HashMap</tt>, it provides constant-time
 * performance for the basic operations (<tt>add</tt>, <tt>contains</tt> and
 * <tt>remove</tt>), assuming the hash function disperses elements
 * properly among the buckets.  Performance is likely to be just slightly
 * below that of <tt>HashMap</tt>, due to the added expense of maintaining the
 * linked list, with one exception: Iteration over the collection-views
 * of a <tt>LinkedHashMap</tt> requires time proportional to the <i>size</i>
 * of the map, regardless of its capacity.  Iteration over a <tt>HashMap</tt>
 * is likely to be more expensive, requiring time proportional to its
 * <i>capacity</i>.
 *
 * <p>A linked hash map has two parameters that affect its performance:
 * <i>initial capacity</i> and <i>load factor</i>.  They are defined precisely
 * as for <tt>HashMap</tt>.  Note, however, that the penalty for choosing an
 * excessively high value for initial capacity is less severe for this class
 * than for <tt>HashMap</tt>, as iteration times for this class are unaffected
 * by capacity.
 *
 * <p><strong>Note that this implementation is not synchronized.</strong>
 * If multiple threads access a linked hash map concurrently, and at least
 * one of the threads modifies the map structurally, it <em>must</em> be
 * synchronized externally.  This is typically accomplished by
 * synchronizing on some object that naturally encapsulates the map.
 *
 * If no such object exists, the map should be "wrapped" using the
 * {@link Collections#synchronizedMap Collections.synchronizedMap}
 * method.  This is best done at creation time, to prevent accidental
 * unsynchronized access to the map:<pre>
 *   Map m = Collections.synchronizedMap(new LinkedHashMap(...));</pre>
 *
 * A structural modification is any operation that adds or deletes one or more
 * mappings or, in the case of access-ordered linked hash maps, affects
 * iteration order.  In insertion-ordered linked hash maps, merely changing
 * the value associated with a key that is already contained in the map is not
 * a structural modification.  <strong>In access-ordered linked hash maps,
 * merely querying the map with <tt>get</tt> is a structural modification.
 * </strong>)
 *
 * <p>The iterators returned by the <tt>iterator</tt> method of the collections
 * returned by all of this class's collection view methods are
 * <em>fail-fast</em>: if the map is structurally modified at any time after
 * the iterator is created, in any way except through the iterator's own
 * <tt>remove</tt> method, the iterator will throw a {@link
 * ConcurrentModificationException}.  Thus, in the face of concurrent
 * modification, the iterator fails quickly and cleanly, rather than risking
 * arbitrary, non-deterministic behavior at an undetermined time in the future.
 *
 * <p>Note that the fail-fast behavior of an iterator cannot be guaranteed
 * as it is, generally speaking, impossible to make any hard guarantees in the
 * presence of unsynchronized concurrent modification.  Fail-fast iterators
 * throw <tt>ConcurrentModificationException</tt> on a best-effort basis.
 * Therefore, it would be wrong to write a program that depended on this
 * exception for its correctness:   <i>the fail-fast behavior of iterators
 * should be used only to detect bugs.</i>
 *
 * <p>The spliterators returned by the spliterator method of the collections
 * returned by all of this class's collection view methods are
 * <em><a href="Spliterator.html#binding">late-binding</a></em>,
 * <em>fail-fast</em>, and additionally report {@link Spliterator#ORDERED}.
 *
 * <p>This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @implNote
 * The spliterators returned by the spliterator method of the collections
 * returned by all of this class's collection view methods are created from
 * the iterators of the corresponding collections.
 *
 * <p>
 *  <p> <tt> Map </tt>界面的散列表和链接列表实现,具有可预测的迭代顺序。此实现不同于<tt> HashMap </tt>,因为它维护一个贯穿其所有条目的双向链表。
 * 此链接列表定义了迭代排序,通常是将键插入到地图中的顺序(<i>插入顺序</i>)。请注意,如果将<i>重新插入</i>键插入地图,则插入顺序不会受到影响。
 * 如果<tt> m.containsKey时调用<tt> m.put(k,v)</tt>,则将<tt> k </tt>重新插入到映射<tt> m </tt> k)</tt>将在调用之前立即返回<tt> t
 * rue </tt>。
 * 此链接列表定义了迭代排序,通常是将键插入到地图中的顺序(<i>插入顺序</i>)。请注意,如果将<i>重新插入</i>键插入地图,则插入顺序不会受到影响。)。
 * 
 *  <p>此实施方案使客户端免受{@link HashMap}(和{@link Hashtable})提供的未指定的,通常无序的顺序,而不会增加与{@link TreeMap}相关联的成本。
 * 它可以用于产生与原始地图具有相同顺序的地图的副本,而不管原始地图的实现：。
 * <pre>
 *  void foo(Map m){Map copy = new LinkedHashMap(m); ...}
 * </pre>
 *  如果模块在输入上获取映射,将其复制,然后返回其顺序由副本的顺序确定的结果,则此技术特别有用。 (客户通常喜欢以与提交相同的顺序返回)。
 * 
 * <p>提供了一个特殊的{@link #LinkedHashMap(int,float,boolean)构造函数}来创建一个链接的散列图,其迭代顺序是其条目最后访问的顺序,从最近访问到最近访问,最近(<i>
 * 存取顺序</i>)。
 * 这种地图非常适合构建LRU缓存。
 * 调用{@code put},{@code putIfAbsent},{@code get},{@code getOrDefault},{@code compute},{@code computeIfAbsent}
 * ,{@code computeIfPresent}或{@code merge}方法导致对相应条目的访问(假设它在调用完成后存在)。
 * 这种地图非常适合构建LRU缓存。如果值被替换,{@code replace}方法只会导致条目的访问。
 *  {@code putAll}方法按照指定映射的条目集迭代器提供键值映射的顺序,为指定映射中的每个映射生成一个条目访问。 <i>没有其他方法生成条目访问。
 * </i>具体来说,对集合视图的操作不会影响背景图的迭代顺序。
 * 
 *  <p> {@link #removeEldestEntry(Map.Entry)}方法可能会被覆盖,以便在将新映射添加到地图时自动删除过时映射的策略。
 * 
 * <p>此类提供所有可选的<tt> Map </tt>操作,并允许空元素。
 * 像<tt> HashMap </tt>,它为基本操作(<tt> add </tt>,<tt>包含</tt>和<tt>删除</tt>)提供了恒定时间性能,散列函数在这些桶之间适当地分散元素。
 * 由于维护链接列表的额外费用,性能可能略低于<tt> HashMap </tt>,除了一个例外：迭代在<tt> LinkedHashMap </tt>需要与映射的<i>尺寸</i>成比例的时间,而不管其容
 * 量如何。
 * 像<tt> HashMap </tt>,它为基本操作(<tt> add </tt>,<tt>包含</tt>和<tt>删除</tt>)提供了恒定时间性能,散列函数在这些桶之间适当地分散元素。
 * 在<tt> HashMap </tt>上的迭代可能更昂贵,需要与其<i>容量成比例的时间</i>。
 * 
 *  <p>链接的散列图具有影响其性能的两个参数：<i>初始容量</i>和<i>负载因子</i>。它们的定义与<tt> HashMap </tt>一样。
 * 但是,请注意,对于这个类别选择过高的初始容量值的惩罚对于<tt> HashMap </tt>来说不太严格,因为这个类的迭代时间不受容量的影响。
 * 
 *  <p> <strong>请注意,此实现未同步。</strong>如果多个线程同时访问链接的散列图,并且至少有一个线程在结构上修改了地图,则必须<em> </em>外部同步。
 * 这通常通过在自然地封装映射的某个对象上同步来实现。
 * 
 * 如果不存在这样的对象,那么应该使用{@link Collections#synchronizeMap Collections.synchronizedMap}方法来"包装"映射。
 * 这最好在创建时完成,以防止意外的不同步访问地图：<pre> Map m = Collections.synchronizedMap(new LinkedHashMap(...)); </pre>。
 * 
 *  结构修改是添加或删除一个或多个映射的任何操作,或者在访问有序链接的散列图的情况下,影响迭代顺序。在插入排序的链接散列图中,仅改变与已经包含在映射中的键相关联的值不是结构修改。
 *  <strong>在访问有序链接散列图中,仅使用<tt> get </tt>查询地图是一种结构修改。 </strong>)。
 * 
 *  <p>由所有此类的集合视图方法返回的集合的<tt> iterator </tt>方法返回的迭代器<em> fail-fast </em>：如果地图在任何时候被结构修改在创建迭代器之后,除了通过迭代器自
 * 己的<tt> remove </tt>方法,迭代器将抛出一个{@link ConcurrentModificationException}。
 * 因此,面对并发修改,迭代器快速而干净地失败,而不是在将来的未确定时间冒任意的,非确定性行为的风险。
 * 
 * <p>请注意,迭代器的故障快速行为不能得到保证,因为一般来说,在不同步并发修改的情况下不可能做出任何硬的保证。
 * 故障快速迭代器在尽力而为的基础上抛出<tt> ConcurrentModificationException </tt>。
 * 因此,编写依赖于此异常的程序的正确性是错误的：<i>迭代器的故障快速行为应该仅用于检测错误。</i>。
 * 
 *  <p>由此类的所有集合视图方法返回的集合的spliterator方法返回的分隔符为<em> <a href="Spliterator.html#binding">延迟绑定</a> </em> <em>
 *  fail-fast </em>,并另外报告{@link Spliterator#ORDERED}。
 * 
 *  <p>此类是的成员
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 *  Java集合框架</a>。
 * 
 *  @implNote由所有此类的集合视图方法返回的集合的spliterator方法返回的分隔符是从相应集合的迭代器创建的。
 * 
 * 
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 *
 * @author  Josh Bloch
 * @see     Object#hashCode()
 * @see     Collection
 * @see     Map
 * @see     HashMap
 * @see     TreeMap
 * @see     Hashtable
 * @since   1.4
 */
public class LinkedHashMap<K,V>
    extends HashMap<K,V>
    implements Map<K,V>
{

    /*
     * Implementation note.  A previous version of this class was
     * internally structured a little differently. Because superclass
     * HashMap now uses trees for some of its nodes, class
     * LinkedHashMap.Entry is now treated as intermediary node class
     * that can also be converted to tree form. The name of this
     * class, LinkedHashMap.Entry, is confusing in several ways in its
     * current context, but cannot be changed.  Otherwise, even though
     * it is not exported outside this package, some existing source
     * code is known to have relied on a symbol resolution corner case
     * rule in calls to removeEldestEntry that suppressed compilation
     * errors due to ambiguous usages. So, we keep the name to
     * preserve unmodified compilability.
     *
     * The changes in node classes also require using two fields
     * (head, tail) rather than a pointer to a header node to maintain
     * the doubly-linked before/after list. This class also
     * previously used a different style of callback methods upon
     * access, insertion, and removal.
     * <p>
     * 实施说明。这个类的先前版本在内部结构有点不同。因为超类HashMap现在对其一些节点使用树,所以类LinkedHashMap.Entry现在被视为中间节点类,也可以转换为树形式。
     * 此类的名称LinkedHashMap.Entry在其当前上下文中以多种方式混淆,但不能更改。
     * 否则,即使它没有被导出到这个包之外,一些现有的源代码已经依赖于在调用removeEldestEntry中的符号解析角规规则,该规则抑制了由于不明确的用法而导致的编译错误。
     * 因此,我们保留名称以保持未修改的可编译性。
     * 
     *  节点类中的变化还需要使用两个字段(头,尾)而不是指向头节点的指针,以维持列表之前/之后的双重链接。此类以前在访问,插入和删除时使用了不同风格的回调方法。
     * 
     */

    /**
     * HashMap.Node subclass for normal LinkedHashMap entries.
     * <p>
     *  正常LinkedHashMap条目的HashMap.Node子类。
     * 
     */
    static class Entry<K,V> extends HashMap.Node<K,V> {
        Entry<K,V> before, after;
        Entry(int hash, K key, V value, Node<K,V> next) {
            super(hash, key, value, next);
        }
    }

    private static final long serialVersionUID = 3801124242820219131L;

    /**
     * The head (eldest) of the doubly linked list.
     * <p>
     *  双头链表的头部(最长的)。
     * 
     */
    transient LinkedHashMap.Entry<K,V> head;

    /**
     * The tail (youngest) of the doubly linked list.
     * <p>
     *  双向链表的尾部(最小的)。
     * 
     */
    transient LinkedHashMap.Entry<K,V> tail;

    /**
     * The iteration ordering method for this linked hash map: <tt>true</tt>
     * for access-order, <tt>false</tt> for insertion-order.
     *
     * <p>
     *  此链接哈希映射的迭代排序方法：<tt> true </tt>表示访问顺序,<tt> false </tt>表示插入顺序。
     * 
     * 
     * @serial
     */
    final boolean accessOrder;

    // internal utilities

    // link at the end of list
    private void linkNodeLast(LinkedHashMap.Entry<K,V> p) {
        LinkedHashMap.Entry<K,V> last = tail;
        tail = p;
        if (last == null)
            head = p;
        else {
            p.before = last;
            last.after = p;
        }
    }

    // apply src's links to dst
    private void transferLinks(LinkedHashMap.Entry<K,V> src,
                               LinkedHashMap.Entry<K,V> dst) {
        LinkedHashMap.Entry<K,V> b = dst.before = src.before;
        LinkedHashMap.Entry<K,V> a = dst.after = src.after;
        if (b == null)
            head = dst;
        else
            b.after = dst;
        if (a == null)
            tail = dst;
        else
            a.before = dst;
    }

    // overrides of HashMap hook methods

    void reinitialize() {
        super.reinitialize();
        head = tail = null;
    }

    Node<K,V> newNode(int hash, K key, V value, Node<K,V> e) {
        LinkedHashMap.Entry<K,V> p =
            new LinkedHashMap.Entry<K,V>(hash, key, value, e);
        linkNodeLast(p);
        return p;
    }

    Node<K,V> replacementNode(Node<K,V> p, Node<K,V> next) {
        LinkedHashMap.Entry<K,V> q = (LinkedHashMap.Entry<K,V>)p;
        LinkedHashMap.Entry<K,V> t =
            new LinkedHashMap.Entry<K,V>(q.hash, q.key, q.value, next);
        transferLinks(q, t);
        return t;
    }

    TreeNode<K,V> newTreeNode(int hash, K key, V value, Node<K,V> next) {
        TreeNode<K,V> p = new TreeNode<K,V>(hash, key, value, next);
        linkNodeLast(p);
        return p;
    }

    TreeNode<K,V> replacementTreeNode(Node<K,V> p, Node<K,V> next) {
        LinkedHashMap.Entry<K,V> q = (LinkedHashMap.Entry<K,V>)p;
        TreeNode<K,V> t = new TreeNode<K,V>(q.hash, q.key, q.value, next);
        transferLinks(q, t);
        return t;
    }

    void afterNodeRemoval(Node<K,V> e) { // unlink
        LinkedHashMap.Entry<K,V> p =
            (LinkedHashMap.Entry<K,V>)e, b = p.before, a = p.after;
        p.before = p.after = null;
        if (b == null)
            head = a;
        else
            b.after = a;
        if (a == null)
            tail = b;
        else
            a.before = b;
    }

    void afterNodeInsertion(boolean evict) { // possibly remove eldest
        LinkedHashMap.Entry<K,V> first;
        if (evict && (first = head) != null && removeEldestEntry(first)) {
            K key = first.key;
            removeNode(hash(key), key, null, false, true);
        }
    }

    void afterNodeAccess(Node<K,V> e) { // move node to last
        LinkedHashMap.Entry<K,V> last;
        if (accessOrder && (last = tail) != e) {
            LinkedHashMap.Entry<K,V> p =
                (LinkedHashMap.Entry<K,V>)e, b = p.before, a = p.after;
            p.after = null;
            if (b == null)
                head = a;
            else
                b.after = a;
            if (a != null)
                a.before = b;
            else
                last = b;
            if (last == null)
                head = p;
            else {
                p.before = last;
                last.after = p;
            }
            tail = p;
            ++modCount;
        }
    }

    void internalWriteEntries(java.io.ObjectOutputStream s) throws IOException {
        for (LinkedHashMap.Entry<K,V> e = head; e != null; e = e.after) {
            s.writeObject(e.key);
            s.writeObject(e.value);
        }
    }

    /**
     * Constructs an empty insertion-ordered <tt>LinkedHashMap</tt> instance
     * with the specified initial capacity and load factor.
     *
     * <p>
     *  构造具有指定的初始容量和负载因子的空插入顺序的<tt> LinkedHashMap </tt>实例。
     * 
     * 
     * @param  initialCapacity the initial capacity
     * @param  loadFactor      the load factor
     * @throws IllegalArgumentException if the initial capacity is negative
     *         or the load factor is nonpositive
     */
    public LinkedHashMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
        accessOrder = false;
    }

    /**
     * Constructs an empty insertion-ordered <tt>LinkedHashMap</tt> instance
     * with the specified initial capacity and a default load factor (0.75).
     *
     * <p>
     * 构造具有指定初始容量和默认负载系数(0.75)的空插入顺序<tt> LinkedHashMap </tt>实例。
     * 
     * 
     * @param  initialCapacity the initial capacity
     * @throws IllegalArgumentException if the initial capacity is negative
     */
    public LinkedHashMap(int initialCapacity) {
        super(initialCapacity);
        accessOrder = false;
    }

    /**
     * Constructs an empty insertion-ordered <tt>LinkedHashMap</tt> instance
     * with the default initial capacity (16) and load factor (0.75).
     * <p>
     *  构造具有默认初始容量(16)和负载系数(0.75)的空插入顺序<tt> LinkedHashMap </tt>实例。
     * 
     */
    public LinkedHashMap() {
        super();
        accessOrder = false;
    }

    /**
     * Constructs an insertion-ordered <tt>LinkedHashMap</tt> instance with
     * the same mappings as the specified map.  The <tt>LinkedHashMap</tt>
     * instance is created with a default load factor (0.75) and an initial
     * capacity sufficient to hold the mappings in the specified map.
     *
     * <p>
     *  构造与指定映射具有相同映射的插入排序的<tt> LinkedHashMap </tt>实例。
     *  <tt> LinkedHashMap </tt>实例使用默认负载系数(0.75)和足以在指定映射中保存映射的初始容量创建。
     * 
     * 
     * @param  m the map whose mappings are to be placed in this map
     * @throws NullPointerException if the specified map is null
     */
    public LinkedHashMap(Map<? extends K, ? extends V> m) {
        super();
        accessOrder = false;
        putMapEntries(m, false);
    }

    /**
     * Constructs an empty <tt>LinkedHashMap</tt> instance with the
     * specified initial capacity, load factor and ordering mode.
     *
     * <p>
     *  使用指定的初始容量,负载系数和排序模式构造一个空的<tt> LinkedHashMap </tt>实例。
     * 
     * 
     * @param  initialCapacity the initial capacity
     * @param  loadFactor      the load factor
     * @param  accessOrder     the ordering mode - <tt>true</tt> for
     *         access-order, <tt>false</tt> for insertion-order
     * @throws IllegalArgumentException if the initial capacity is negative
     *         or the load factor is nonpositive
     */
    public LinkedHashMap(int initialCapacity,
                         float loadFactor,
                         boolean accessOrder) {
        super(initialCapacity, loadFactor);
        this.accessOrder = accessOrder;
    }


    /**
     * Returns <tt>true</tt> if this map maps one or more keys to the
     * specified value.
     *
     * <p>
     *  如果此映射将一个或多个键映射到指定的值,则返回<tt> true </tt>。
     * 
     * 
     * @param value value whose presence in this map is to be tested
     * @return <tt>true</tt> if this map maps one or more keys to the
     *         specified value
     */
    public boolean containsValue(Object value) {
        for (LinkedHashMap.Entry<K,V> e = head; e != null; e = e.after) {
            V v = e.value;
            if (v == value || (value != null && value.equals(v)))
                return true;
        }
        return false;
    }

    /**
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * <p>More formally, if this map contains a mapping from a key
     * {@code k} to a value {@code v} such that {@code (key==null ? k==null :
     * key.equals(k))}, then this method returns {@code v}; otherwise
     * it returns {@code null}.  (There can be at most one such mapping.)
     *
     * <p>A return value of {@code null} does not <i>necessarily</i>
     * indicate that the map contains no mapping for the key; it's also
     * possible that the map explicitly maps the key to {@code null}.
     * The {@link #containsKey containsKey} operation may be used to
     * distinguish these two cases.
     * <p>
     *  返回指定键映射到的值,如果此映射不包含键的映射,则返回{@code null}。
     * 
     *  更正式地说,如果此映射包含从密钥{@code k}到值{@code v}的映射,使得{@code(key == null?k == null：key.equals(k) )},那么这个方法返回{@code v}
     * ;否则返回{@code null}。
     *  (最多只能有一个这样的映射。)。
     * 
     *  <p> {@code null}的返回值不一定</i>表示地图不包含键的映射;也有可能映射将键明确映射到{@code null}。
     *  {@link #containsKey containsKey}操作可用于区分这两种情况。
     * 
     */
    public V get(Object key) {
        Node<K,V> e;
        if ((e = getNode(hash(key), key)) == null)
            return null;
        if (accessOrder)
            afterNodeAccess(e);
        return e.value;
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public V getOrDefault(Object key, V defaultValue) {
       Node<K,V> e;
       if ((e = getNode(hash(key), key)) == null)
           return defaultValue;
       if (accessOrder)
           afterNodeAccess(e);
       return e.value;
   }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public void clear() {
        super.clear();
        head = tail = null;
    }

    /**
     * Returns <tt>true</tt> if this map should remove its eldest entry.
     * This method is invoked by <tt>put</tt> and <tt>putAll</tt> after
     * inserting a new entry into the map.  It provides the implementor
     * with the opportunity to remove the eldest entry each time a new one
     * is added.  This is useful if the map represents a cache: it allows
     * the map to reduce memory consumption by deleting stale entries.
     *
     * <p>Sample use: this override will allow the map to grow up to 100
     * entries and then delete the eldest entry each time a new entry is
     * added, maintaining a steady state of 100 entries.
     * <pre>
     *     private static final int MAX_ENTRIES = 100;
     *
     *     protected boolean removeEldestEntry(Map.Entry eldest) {
     *        return size() &gt; MAX_ENTRIES;
     *     }
     * </pre>
     *
     * <p>This method typically does not modify the map in any way,
     * instead allowing the map to modify itself as directed by its
     * return value.  It <i>is</i> permitted for this method to modify
     * the map directly, but if it does so, it <i>must</i> return
     * <tt>false</tt> (indicating that the map should not attempt any
     * further modification).  The effects of returning <tt>true</tt>
     * after modifying the map from within this method are unspecified.
     *
     * <p>This implementation merely returns <tt>false</tt> (so that this
     * map acts like a normal map - the eldest element is never removed).
     *
     * <p>
     * 如果此地图应删除其最长的条目,则返回<tt> true </tt>。在向地图中插入新条目后,通过<tt> put </tt>和<tt> putAll </tt>调用此方法。
     * 它为执行者提供了在每次添加新的条目时移除最长条目的机会。如果映射表示高速缓存,这是有用的：它允许映射通过删除过时的条目来减少内存消耗。
     * 
     *  <p>示例使用：此覆盖将允许地图长达100个条目,然后在每次添加新条目时删除最长条目,维护100个条目的稳定状态。
     * <pre>
     *  private static final int MAX_ENTRIES = 100;
     * 
     *  protected boolean removeEldestEntry(Map.Entry eldest){return size()&gt; MAX_ENTRIES; }}
     * </pre>
     * 
     *  <p>此方法通常不会以任何方式修改地图,而是允许地图根据其返回值指示来修改本身。
     *  <i> <i> </i> </i>是允许此方法直接修改地图,但如果是这样,则<i>必须</i>返回<tt> false </tt>尝试任何进一步修改)。
     * 未指定在此方法中修改地图后返回<tt> true </tt>的效果。
     * 
     *  <p>此实现仅返回<tt> false </tt>(以便此映射表现为法线贴图 - 最早的元素不会被移除)。
     * 
     * 
     * @param    eldest The least recently inserted entry in the map, or if
     *           this is an access-ordered map, the least recently accessed
     *           entry.  This is the entry that will be removed it this
     *           method returns <tt>true</tt>.  If the map was empty prior
     *           to the <tt>put</tt> or <tt>putAll</tt> invocation resulting
     *           in this invocation, this will be the entry that was just
     *           inserted; in other words, if the map contains a single
     *           entry, the eldest entry is also the newest.
     * @return   <tt>true</tt> if the eldest entry should be removed
     *           from the map; <tt>false</tt> if it should be retained.
     */
    protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
        return false;
    }

    /**
     * Returns a {@link Set} view of the keys contained in this map.
     * The set is backed by the map, so changes to the map are
     * reflected in the set, and vice-versa.  If the map is modified
     * while an iteration over the set is in progress (except through
     * the iterator's own <tt>remove</tt> operation), the results of
     * the iteration are undefined.  The set supports element removal,
     * which removes the corresponding mapping from the map, via the
     * <tt>Iterator.remove</tt>, <tt>Set.remove</tt>,
     * <tt>removeAll</tt>, <tt>retainAll</tt>, and <tt>clear</tt>
     * operations.  It does not support the <tt>add</tt> or <tt>addAll</tt>
     * operations.
     * Its {@link Spliterator} typically provides faster sequential
     * performance but much poorer parallel performance than that of
     * {@code HashMap}.
     *
     * <p>
     * 返回此地图中包含的键的{@link Set}视图。该集合由映射支持,因此对映射的更改反映在集合中,反之亦然。
     * 如果在迭代集合的过程中修改映射(除非通过迭代器自己的<tt> remove </tt>操作),迭代的结果是未定义的。
     * 集合支持元素删除,它通过<tt> Iterator.remove </tt>,<tt> Set.remove </tt>,<tt> removeAll </tt>,<tt从地图中删除相应的映射> ret
     * ainAll </tt>和<tt>清除</tt>操作。
     * 如果在迭代集合的过程中修改映射(除非通过迭代器自己的<tt> remove </tt>操作),迭代的结果是未定义的。它不支持<tt>添加</tt>或<tt> addAll </tt>操作。
     * 它的{@link Spliterator}通常提供更快的顺序性能,但是比{@code HashMap}的并行性能差得多。
     * 
     * 
     * @return a set view of the keys contained in this map
     */
    public Set<K> keySet() {
        Set<K> ks;
        return (ks = keySet) == null ? (keySet = new LinkedKeySet()) : ks;
    }

    final class LinkedKeySet extends AbstractSet<K> {
        public final int size()                 { return size; }
        public final void clear()               { LinkedHashMap.this.clear(); }
        public final Iterator<K> iterator() {
            return new LinkedKeyIterator();
        }
        public final boolean contains(Object o) { return containsKey(o); }
        public final boolean remove(Object key) {
            return removeNode(hash(key), key, null, false, true) != null;
        }
        public final Spliterator<K> spliterator()  {
            return Spliterators.spliterator(this, Spliterator.SIZED |
                                            Spliterator.ORDERED |
                                            Spliterator.DISTINCT);
        }
        public final void forEach(Consumer<? super K> action) {
            if (action == null)
                throw new NullPointerException();
            int mc = modCount;
            for (LinkedHashMap.Entry<K,V> e = head; e != null; e = e.after)
                action.accept(e.key);
            if (modCount != mc)
                throw new ConcurrentModificationException();
        }
    }

    /**
     * Returns a {@link Collection} view of the values contained in this map.
     * The collection is backed by the map, so changes to the map are
     * reflected in the collection, and vice-versa.  If the map is
     * modified while an iteration over the collection is in progress
     * (except through the iterator's own <tt>remove</tt> operation),
     * the results of the iteration are undefined.  The collection
     * supports element removal, which removes the corresponding
     * mapping from the map, via the <tt>Iterator.remove</tt>,
     * <tt>Collection.remove</tt>, <tt>removeAll</tt>,
     * <tt>retainAll</tt> and <tt>clear</tt> operations.  It does not
     * support the <tt>add</tt> or <tt>addAll</tt> operations.
     * Its {@link Spliterator} typically provides faster sequential
     * performance but much poorer parallel performance than that of
     * {@code HashMap}.
     *
     * <p>
     * 返回此地图中包含的值的{@link Collection}视图。集合由地图支持,因此对地图的更改会反映在集合中,反之亦然。
     * 如果在集合的迭代正在进行时修改映射(除非通过迭代器自己的<tt> remove </tt>操作),迭代的结果是未定义的。
     * 集合支持元素删除,通过<tt> Iterator.remove </tt>,<tt> Collection.remove </tt>,<tt> removeAll </tt>,<tt从地图中删除相应的映射>
     *  retainAll </tt>和<tt>清除</tt>操作。
     * 如果在集合的迭代正在进行时修改映射(除非通过迭代器自己的<tt> remove </tt>操作),迭代的结果是未定义的。它不支持<tt>添加</tt>或<tt> addAll </tt>操作。
     * 它的{@link Spliterator}通常提供更快的顺序性能,但是比{@code HashMap}的并行性能差得多。
     * 
     * 
     * @return a view of the values contained in this map
     */
    public Collection<V> values() {
        Collection<V> vs;
        return (vs = values) == null ? (values = new LinkedValues()) : vs;
    }

    final class LinkedValues extends AbstractCollection<V> {
        public final int size()                 { return size; }
        public final void clear()               { LinkedHashMap.this.clear(); }
        public final Iterator<V> iterator() {
            return new LinkedValueIterator();
        }
        public final boolean contains(Object o) { return containsValue(o); }
        public final Spliterator<V> spliterator() {
            return Spliterators.spliterator(this, Spliterator.SIZED |
                                            Spliterator.ORDERED);
        }
        public final void forEach(Consumer<? super V> action) {
            if (action == null)
                throw new NullPointerException();
            int mc = modCount;
            for (LinkedHashMap.Entry<K,V> e = head; e != null; e = e.after)
                action.accept(e.value);
            if (modCount != mc)
                throw new ConcurrentModificationException();
        }
    }

    /**
     * Returns a {@link Set} view of the mappings contained in this map.
     * The set is backed by the map, so changes to the map are
     * reflected in the set, and vice-versa.  If the map is modified
     * while an iteration over the set is in progress (except through
     * the iterator's own <tt>remove</tt> operation, or through the
     * <tt>setValue</tt> operation on a map entry returned by the
     * iterator) the results of the iteration are undefined.  The set
     * supports element removal, which removes the corresponding
     * mapping from the map, via the <tt>Iterator.remove</tt>,
     * <tt>Set.remove</tt>, <tt>removeAll</tt>, <tt>retainAll</tt> and
     * <tt>clear</tt> operations.  It does not support the
     * <tt>add</tt> or <tt>addAll</tt> operations.
     * Its {@link Spliterator} typically provides faster sequential
     * performance but much poorer parallel performance than that of
     * {@code HashMap}.
     *
     * <p>
     * 返回此地图中包含的映射的{@link Set}视图。该集合由映射支持,因此对映射的更改反映在集合中,反之亦然。
     * 如果在对迭代器执行迭代(即通过迭代器自己的<tt> remove </tt>操作,或通过对迭代器返回的映射条目执行<tt> setValue </tt>操作) )迭代的结果是未定义的。
     * 集合支持元素删除,它通过<tt> Iterator.remove </tt>,<tt> Set.remove </tt>,<tt> removeAll </tt>,<tt从地图中删除相应的映射> ret
     * ainAll </tt>和<tt>清除</tt>操作。
     * 
     * @return a set view of the mappings contained in this map
     */
    public Set<Map.Entry<K,V>> entrySet() {
        Set<Map.Entry<K,V>> es;
        return (es = entrySet) == null ? (entrySet = new LinkedEntrySet()) : es;
    }

    final class LinkedEntrySet extends AbstractSet<Map.Entry<K,V>> {
        public final int size()                 { return size; }
        public final void clear()               { LinkedHashMap.this.clear(); }
        public final Iterator<Map.Entry<K,V>> iterator() {
            return new LinkedEntryIterator();
        }
        public final boolean contains(Object o) {
            if (!(o instanceof Map.Entry))
                return false;
            Map.Entry<?,?> e = (Map.Entry<?,?>) o;
            Object key = e.getKey();
            Node<K,V> candidate = getNode(hash(key), key);
            return candidate != null && candidate.equals(e);
        }
        public final boolean remove(Object o) {
            if (o instanceof Map.Entry) {
                Map.Entry<?,?> e = (Map.Entry<?,?>) o;
                Object key = e.getKey();
                Object value = e.getValue();
                return removeNode(hash(key), key, value, true, true) != null;
            }
            return false;
        }
        public final Spliterator<Map.Entry<K,V>> spliterator() {
            return Spliterators.spliterator(this, Spliterator.SIZED |
                                            Spliterator.ORDERED |
                                            Spliterator.DISTINCT);
        }
        public final void forEach(Consumer<? super Map.Entry<K,V>> action) {
            if (action == null)
                throw new NullPointerException();
            int mc = modCount;
            for (LinkedHashMap.Entry<K,V> e = head; e != null; e = e.after)
                action.accept(e);
            if (modCount != mc)
                throw new ConcurrentModificationException();
        }
    }

    // Map overrides

    public void forEach(BiConsumer<? super K, ? super V> action) {
        if (action == null)
            throw new NullPointerException();
        int mc = modCount;
        for (LinkedHashMap.Entry<K,V> e = head; e != null; e = e.after)
            action.accept(e.key, e.value);
        if (modCount != mc)
            throw new ConcurrentModificationException();
    }

    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        if (function == null)
            throw new NullPointerException();
        int mc = modCount;
        for (LinkedHashMap.Entry<K,V> e = head; e != null; e = e.after)
            e.value = function.apply(e.key, e.value);
        if (modCount != mc)
            throw new ConcurrentModificationException();
    }

    // Iterators

    abstract class LinkedHashIterator {
        LinkedHashMap.Entry<K,V> next;
        LinkedHashMap.Entry<K,V> current;
        int expectedModCount;

        LinkedHashIterator() {
            next = head;
            expectedModCount = modCount;
            current = null;
        }

        public final boolean hasNext() {
            return next != null;
        }

        final LinkedHashMap.Entry<K,V> nextNode() {
            LinkedHashMap.Entry<K,V> e = next;
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
            if (e == null)
                throw new NoSuchElementException();
            current = e;
            next = e.after;
            return e;
        }

        public final void remove() {
            Node<K,V> p = current;
            if (p == null)
                throw new IllegalStateException();
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
            current = null;
            K key = p.key;
            removeNode(hash(key), key, null, false, false);
            expectedModCount = modCount;
        }
    }

    final class LinkedKeyIterator extends LinkedHashIterator
        implements Iterator<K> {
        public final K next() { return nextNode().getKey(); }
    }

    final class LinkedValueIterator extends LinkedHashIterator
        implements Iterator<V> {
        public final V next() { return nextNode().value; }
    }

    final class LinkedEntryIterator extends LinkedHashIterator
        implements Iterator<Map.Entry<K,V>> {
        public final Map.Entry<K,V> next() { return nextNode(); }
    }


}
