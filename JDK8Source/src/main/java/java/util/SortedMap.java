/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved.
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
 * A {@link Map} that further provides a <em>total ordering</em> on its keys.
 * The map is ordered according to the {@linkplain Comparable natural
 * ordering} of its keys, or by a {@link Comparator} typically
 * provided at sorted map creation time.  This order is reflected when
 * iterating over the sorted map's collection views (returned by the
 * {@code entrySet}, {@code keySet} and {@code values} methods).
 * Several additional operations are provided to take advantage of the
 * ordering.  (This interface is the map analogue of {@link SortedSet}.)
 *
 * <p>All keys inserted into a sorted map must implement the {@code Comparable}
 * interface (or be accepted by the specified comparator).  Furthermore, all
 * such keys must be <em>mutually comparable</em>: {@code k1.compareTo(k2)} (or
 * {@code comparator.compare(k1, k2)}) must not throw a
 * {@code ClassCastException} for any keys {@code k1} and {@code k2} in
 * the sorted map.  Attempts to violate this restriction will cause the
 * offending method or constructor invocation to throw a
 * {@code ClassCastException}.
 *
 * <p>Note that the ordering maintained by a sorted map (whether or not an
 * explicit comparator is provided) must be <em>consistent with equals</em> if
 * the sorted map is to correctly implement the {@code Map} interface.  (See
 * the {@code Comparable} interface or {@code Comparator} interface for a
 * precise definition of <em>consistent with equals</em>.)  This is so because
 * the {@code Map} interface is defined in terms of the {@code equals}
 * operation, but a sorted map performs all key comparisons using its
 * {@code compareTo} (or {@code compare}) method, so two keys that are
 * deemed equal by this method are, from the standpoint of the sorted map,
 * equal.  The behavior of a tree map <em>is</em> well-defined even if its
 * ordering is inconsistent with equals; it just fails to obey the general
 * contract of the {@code Map} interface.
 *
 * <p>All general-purpose sorted map implementation classes should provide four
 * "standard" constructors. It is not possible to enforce this recommendation
 * though as required constructors cannot be specified by interfaces. The
 * expected "standard" constructors for all sorted map implementations are:
 * <ol>
 *   <li>A void (no arguments) constructor, which creates an empty sorted map
 *   sorted according to the natural ordering of its keys.</li>
 *   <li>A constructor with a single argument of type {@code Comparator}, which
 *   creates an empty sorted map sorted according to the specified comparator.</li>
 *   <li>A constructor with a single argument of type {@code Map}, which creates
 *   a new map with the same key-value mappings as its argument, sorted
 *   according to the keys' natural ordering.</li>
 *   <li>A constructor with a single argument of type {@code SortedMap}, which
 *   creates a new sorted map with the same key-value mappings and the same
 *   ordering as the input sorted map.</li>
 * </ol>
 *
 * <p><strong>Note</strong>: several methods return submaps with restricted key
 * ranges. Such ranges are <em>half-open</em>, that is, they include their low
 * endpoint but not their high endpoint (where applicable).  If you need a
 * <em>closed range</em> (which includes both endpoints), and the key type
 * allows for calculation of the successor of a given key, merely request
 * the subrange from {@code lowEndpoint} to
 * {@code successor(highEndpoint)}.  For example, suppose that {@code m}
 * is a map whose keys are strings.  The following idiom obtains a view
 * containing all of the key-value mappings in {@code m} whose keys are
 * between {@code low} and {@code high}, inclusive:<pre>
 *   SortedMap&lt;String, V&gt; sub = m.subMap(low, high+"\0");</pre>
 *
 * A similar technique can be used to generate an <em>open range</em>
 * (which contains neither endpoint).  The following idiom obtains a
 * view containing all of the key-value mappings in {@code m} whose keys
 * are between {@code low} and {@code high}, exclusive:<pre>
 *   SortedMap&lt;String, V&gt; sub = m.subMap(low+"\0", high);</pre>
 *
 * <p>This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * <p>
 *  {@link Map},进一步在其键上提供<em>总排序</em>。
 * 映射根据其键的{@linkplain Comparable natural ordering}或者通常在排序的映射创建时间提供的{@link Comparator}排序。
 * 当迭代排序的地图的集合视图(由{@code entrySet},{@code keySet}和{@code values}方法返回)时,会反映此顺序。提供若干附加操作以利用排序。
 *  (此接口是{@link SortedSet}的地图模拟。)。
 * 
 *  <p>插入到排序映射中的所有键必须实现{@code Comparable}接口(或被指定的比较器接受)。
 * 此外,所有这些键必须是相互比较的：</em>：{@code k1.compareTo(k2)}(或{@code comparator.compare(k1,k2)})不能抛出{@code ClassCastException }
 * 对于排序映射中的任何键{@code k1}和{@code k2}。
 *  <p>插入到排序映射中的所有键必须实现{@code Comparable}接口(或被指定的比较器接受)。
 * 尝试违反此限制将导致冒犯的方法或构造函数调用抛出一个{@code ClassCastException}。
 * 
 * <p>请注意,如果排序的映射要正确实现{@code Map}接口,则由排序映射(不管是否提供显式比较器)维护的排序必须与equals </em>一致。
 *  (请参阅{@code Comparable}接口或{@code Comparator}接口以获取与等于</em>一致的精确定义。
 * )这是因为{@code Map}接口是根据{@code equals}操作,但是有序映射使用其{@code compareTo}(或{@code compare})方法执行所有密钥比较,因此从排序的角度
 * 来看,被该方法认为相等的两个密钥地图,平等。
 *  (请参阅{@code Comparable}接口或{@code Comparator}接口以获取与等于</em>一致的精确定义。
 * 即使树的映射的排序与equals不一致,它也是明确定义的行为; <em> </em>它只是不服从{@code Map}接口的一般合同。
 * 
 *  <p>所有通用排序映射实现类应提供四个"标准"构造函数。不能强制实施此建议,尽管接口不能指定所需的构造函数。所有排序映射实现的预期"标准"构造函数是：
 * <ol>
 * <li>一个void(无参数)构造函数,它根据其键的自然排序创建一个空的排序图。
 * </li> <li>一个构造函数,其单个参数类型为{@code Comparator} </li> <li>具有类型为{@code Map}的单个参数的构造函数,它创建具有与其参数相同的键值映射的新映射
 * ,按照</li> <li>具有类型为{@code SortedMap}的单个参数的构造函数,它创建具有相同键值映射和与输入排序映射相同排序的新排序映射。
 * <li>一个void(无参数)构造函数,它根据其键的自然排序创建一个空的排序图。</li>。
 * </ol>
 * 
 *  <p> <strong>注意</strong>：几种方法返回受限键范围的子图。这样的范围是半开的,即它们包括它们的低端点,但不包括它们的高端点(如果适用)。
 * 如果您需要<em>封闭范围</em>(包括两个端点),并且密钥类型允许计算给定密钥的后继,只需要将子范围从{@code lowEndpoint}请求到{@code后续(highEndpoint)}。
 * 例如,假设{@code m}是键是字符串的映射。
 * 以下成语获得包含{@code m}中的所有键值映射的视图,其中键的{@code low}和{@code high}之间,包括：<pre> SortedMap&lt; String,V&gt; sub =
 *  m.subMap(low,high +"\ 0"); </pre>。
 * 例如,假设{@code m}是键是字符串的映射。
 * 
 * 类似的技术可以用于生成开放范围</em>(其不包含端点)。
 * 以下成语获得包含{@code m}中的所有键值映射的视图,其中键的{@code low}和{@code high}之间是排除的：<pre> SortedMap <String,V> sub = m.su
 * bMap(low +"\ 0",high); </pre>。
 * 类似的技术可以用于生成开放范围</em>(其不包含端点)。
 * 
 *  <p>此接口是的成员
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 *  Java集合框架</a>。
 * 
 * 
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 *
 * @author  Josh Bloch
 * @see Map
 * @see TreeMap
 * @see SortedSet
 * @see Comparator
 * @see Comparable
 * @see Collection
 * @see ClassCastException
 * @since 1.2
 */

public interface SortedMap<K,V> extends Map<K,V> {
    /**
     * Returns the comparator used to order the keys in this map, or
     * {@code null} if this map uses the {@linkplain Comparable
     * natural ordering} of its keys.
     *
     * <p>
     *  返回用于对此地图中的键排序的比较器,如果此地图使用其键的{@linkplain Comparable natural ordering},则返回{@code null}。
     * 
     * 
     * @return the comparator used to order the keys in this map,
     *         or {@code null} if this map uses the natural ordering
     *         of its keys
     */
    Comparator<? super K> comparator();

    /**
     * Returns a view of the portion of this map whose keys range from
     * {@code fromKey}, inclusive, to {@code toKey}, exclusive.  (If
     * {@code fromKey} and {@code toKey} are equal, the returned map
     * is empty.)  The returned map is backed by this map, so changes
     * in the returned map are reflected in this map, and vice-versa.
     * The returned map supports all optional map operations that this
     * map supports.
     *
     * <p>The returned map will throw an {@code IllegalArgumentException}
     * on an attempt to insert a key outside its range.
     *
     * <p>
     *  返回此地图部分的视图,其键的范围从{@code fromKey}(含)到{@code toKey},排除。 (如果{@code fromKey}和{@code toKey}相等,则返回的映射为空。
     * )返回的映射由此映射支持,因此返回的映射中的更改将反映在此映射中,反之亦然。返回的地图支持此地图支持的所有可选的地图操作。
     * 
     *  <p>返回的地图将尝试在其范围之外插入一个键,将抛出{@code IllegalArgumentException}。
     * 
     * 
     * @param fromKey low endpoint (inclusive) of the keys in the returned map
     * @param toKey high endpoint (exclusive) of the keys in the returned map
     * @return a view of the portion of this map whose keys range from
     *         {@code fromKey}, inclusive, to {@code toKey}, exclusive
     * @throws ClassCastException if {@code fromKey} and {@code toKey}
     *         cannot be compared to one another using this map's comparator
     *         (or, if the map has no comparator, using natural ordering).
     *         Implementations may, but are not required to, throw this
     *         exception if {@code fromKey} or {@code toKey}
     *         cannot be compared to keys currently in the map.
     * @throws NullPointerException if {@code fromKey} or {@code toKey}
     *         is null and this map does not permit null keys
     * @throws IllegalArgumentException if {@code fromKey} is greater than
     *         {@code toKey}; or if this map itself has a restricted
     *         range, and {@code fromKey} or {@code toKey} lies
     *         outside the bounds of the range
     */
    SortedMap<K,V> subMap(K fromKey, K toKey);

    /**
     * Returns a view of the portion of this map whose keys are
     * strictly less than {@code toKey}.  The returned map is backed
     * by this map, so changes in the returned map are reflected in
     * this map, and vice-versa.  The returned map supports all
     * optional map operations that this map supports.
     *
     * <p>The returned map will throw an {@code IllegalArgumentException}
     * on an attempt to insert a key outside its range.
     *
     * <p>
     *  返回此键值严格小于{@code toKey}的地图部分的视图。返回的地图由此地图提供支持,因此返回地图中的更改会反映在此地图中,反之亦然。返回的地图支持此地图支持的所有可选的地图操作。
     * 
     *  <p>返回的地图将尝试在其范围之外插入一个键,将抛出{@code IllegalArgumentException}。
     * 
     * 
     * @param toKey high endpoint (exclusive) of the keys in the returned map
     * @return a view of the portion of this map whose keys are strictly
     *         less than {@code toKey}
     * @throws ClassCastException if {@code toKey} is not compatible
     *         with this map's comparator (or, if the map has no comparator,
     *         if {@code toKey} does not implement {@link Comparable}).
     *         Implementations may, but are not required to, throw this
     *         exception if {@code toKey} cannot be compared to keys
     *         currently in the map.
     * @throws NullPointerException if {@code toKey} is null and
     *         this map does not permit null keys
     * @throws IllegalArgumentException if this map itself has a
     *         restricted range, and {@code toKey} lies outside the
     *         bounds of the range
     */
    SortedMap<K,V> headMap(K toKey);

    /**
     * Returns a view of the portion of this map whose keys are
     * greater than or equal to {@code fromKey}.  The returned map is
     * backed by this map, so changes in the returned map are
     * reflected in this map, and vice-versa.  The returned map
     * supports all optional map operations that this map supports.
     *
     * <p>The returned map will throw an {@code IllegalArgumentException}
     * on an attempt to insert a key outside its range.
     *
     * <p>
     * 返回此键映射大于或等于{@code fromKey}的地图部分的视图。返回的地图由此地图提供支持,因此返回地图中的更改会反映在此地图中,反之亦然。返回的地图支持此地图支持的所有可选的地图操作。
     * 
     *  <p>返回的地图将尝试在其范围之外插入一个键,将抛出{@code IllegalArgumentException}。
     * 
     * 
     * @param fromKey low endpoint (inclusive) of the keys in the returned map
     * @return a view of the portion of this map whose keys are greater
     *         than or equal to {@code fromKey}
     * @throws ClassCastException if {@code fromKey} is not compatible
     *         with this map's comparator (or, if the map has no comparator,
     *         if {@code fromKey} does not implement {@link Comparable}).
     *         Implementations may, but are not required to, throw this
     *         exception if {@code fromKey} cannot be compared to keys
     *         currently in the map.
     * @throws NullPointerException if {@code fromKey} is null and
     *         this map does not permit null keys
     * @throws IllegalArgumentException if this map itself has a
     *         restricted range, and {@code fromKey} lies outside the
     *         bounds of the range
     */
    SortedMap<K,V> tailMap(K fromKey);

    /**
     * Returns the first (lowest) key currently in this map.
     *
     * <p>
     *  返回此地图中当前的第一个(最低)键。
     * 
     * 
     * @return the first (lowest) key currently in this map
     * @throws NoSuchElementException if this map is empty
     */
    K firstKey();

    /**
     * Returns the last (highest) key currently in this map.
     *
     * <p>
     *  返回此地图中当前的最后(最高)键。
     * 
     * 
     * @return the last (highest) key currently in this map
     * @throws NoSuchElementException if this map is empty
     */
    K lastKey();

    /**
     * Returns a {@link Set} view of the keys contained in this map.
     * The set's iterator returns the keys in ascending order.
     * The set is backed by the map, so changes to the map are
     * reflected in the set, and vice-versa.  If the map is modified
     * while an iteration over the set is in progress (except through
     * the iterator's own {@code remove} operation), the results of
     * the iteration are undefined.  The set supports element removal,
     * which removes the corresponding mapping from the map, via the
     * {@code Iterator.remove}, {@code Set.remove},
     * {@code removeAll}, {@code retainAll}, and {@code clear}
     * operations.  It does not support the {@code add} or {@code addAll}
     * operations.
     *
     * <p>
     *  返回此地图中包含的键的{@link Set}视图。集合的迭代器以升序返回键。该集合由映射支持,因此对映射的更改反映在集合中,反之亦然。
     * 如果在迭代集合的过程中修改映射(除非通过迭代器自己的{@code remove}操作),迭代的结果是未定义的。
     * 集合支持元素删除,通过{@code Iterator.remove},{@code Set.remove},{@code removeAll},{@code retainAll}和{@code clearAll}
     * 删除地图中的相应映射}操作。
     * 如果在迭代集合的过程中修改映射(除非通过迭代器自己的{@code remove}操作),迭代的结果是未定义的。它不支持{@code add}或{@code addAll}操作。
     * 
     * 
     * @return a set view of the keys contained in this map, sorted in
     *         ascending order
     */
    Set<K> keySet();

    /**
     * Returns a {@link Collection} view of the values contained in this map.
     * The collection's iterator returns the values in ascending order
     * of the corresponding keys.
     * The collection is backed by the map, so changes to the map are
     * reflected in the collection, and vice-versa.  If the map is
     * modified while an iteration over the collection is in progress
     * (except through the iterator's own {@code remove} operation),
     * the results of the iteration are undefined.  The collection
     * supports element removal, which removes the corresponding
     * mapping from the map, via the {@code Iterator.remove},
     * {@code Collection.remove}, {@code removeAll},
     * {@code retainAll} and {@code clear} operations.  It does not
     * support the {@code add} or {@code addAll} operations.
     *
     * <p>
     * 返回此地图中包含的值的{@link Collection}视图。集合的迭代器以相应键的升序返回值。集合由地图支持,因此对地图的更改会反映在集合中,反之亦然。
     * 如果在对集合进行迭代期间修改映射(除非通过迭代器自己的{@code remove}操作),迭代的结果是未定义的。
     * 集合支持元素删除,通过{@code Iterator.remove},{@code Collection.remove},{@code removeAll},{@code retainAll}和{@code clear}
     * 从地图中删除相应的映射操作。
     * 如果在对集合进行迭代期间修改映射(除非通过迭代器自己的{@code remove}操作),迭代的结果是未定义的。它不支持{@code add}或{@code addAll}操作。
     * 
     * 
     * @return a collection view of the values contained in this map,
     *         sorted in ascending key order
     */
    Collection<V> values();

    /**
     * Returns a {@link Set} view of the mappings contained in this map.
     * The set's iterator returns the entries in ascending key order.
     * The set is backed by the map, so changes to the map are
     * reflected in the set, and vice-versa.  If the map is modified
     * while an iteration over the set is in progress (except through
     * the iterator's own {@code remove} operation, or through the
     * {@code setValue} operation on a map entry returned by the
     * iterator) the results of the iteration are undefined.  The set
     * supports element removal, which removes the corresponding
     * mapping from the map, via the {@code Iterator.remove},
     * {@code Set.remove}, {@code removeAll}, {@code retainAll} and
     * {@code clear} operations.  It does not support the
     * {@code add} or {@code addAll} operations.
     *
     * <p>
     * 返回此地图中包含的映射的{@link Set}视图。集合的迭代器以递增键的顺序返回条目。该集合由映射支持,因此对映射的更改反映在集合中,反之亦然。
     * 如果在迭代集合的过程中修改映射(除了通过迭代器自己的{@code remove}操作或通过对迭代器返回的映射条目的{@code setValue}操作)迭代的结果未定义。
     * 集合支持元素删除,它通过{@code Iterator.remove},{@code Set.remove},{@code removeAll},{@code retainAll}和{@code clear}
     * 从地图中删除相应的映射。
     * 
     * @return a set view of the mappings contained in this map,
     *         sorted in ascending key order
     */
    Set<Map.Entry<K, V>> entrySet();
}
