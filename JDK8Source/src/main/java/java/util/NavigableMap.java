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
 * A {@link SortedMap} extended with navigation methods returning the
 * closest matches for given search targets. Methods
 * {@code lowerEntry}, {@code floorEntry}, {@code ceilingEntry},
 * and {@code higherEntry} return {@code Map.Entry} objects
 * associated with keys respectively less than, less than or equal,
 * greater than or equal, and greater than a given key, returning
 * {@code null} if there is no such key.  Similarly, methods
 * {@code lowerKey}, {@code floorKey}, {@code ceilingKey}, and
 * {@code higherKey} return only the associated keys. All of these
 * methods are designed for locating, not traversing entries.
 *
 * <p>A {@code NavigableMap} may be accessed and traversed in either
 * ascending or descending key order.  The {@code descendingMap}
 * method returns a view of the map with the senses of all relational
 * and directional methods inverted. The performance of ascending
 * operations and views is likely to be faster than that of descending
 * ones.  Methods {@code subMap}, {@code headMap},
 * and {@code tailMap} differ from the like-named {@code
 * SortedMap} methods in accepting additional arguments describing
 * whether lower and upper bounds are inclusive versus exclusive.
 * Submaps of any {@code NavigableMap} must implement the {@code
 * NavigableMap} interface.
 *
 * <p>This interface additionally defines methods {@code firstEntry},
 * {@code pollFirstEntry}, {@code lastEntry}, and
 * {@code pollLastEntry} that return and/or remove the least and
 * greatest mappings, if any exist, else returning {@code null}.
 *
 * <p>Implementations of entry-returning methods are expected to
 * return {@code Map.Entry} pairs representing snapshots of mappings
 * at the time they were produced, and thus generally do <em>not</em>
 * support the optional {@code Entry.setValue} method. Note however
 * that it is possible to change mappings in the associated map using
 * method {@code put}.
 *
 * <p>Methods
 * {@link #subMap(Object, Object) subMap(K, K)},
 * {@link #headMap(Object) headMap(K)}, and
 * {@link #tailMap(Object) tailMap(K)}
 * are specified to return {@code SortedMap} to allow existing
 * implementations of {@code SortedMap} to be compatibly retrofitted to
 * implement {@code NavigableMap}, but extensions and implementations
 * of this interface are encouraged to override these methods to return
 * {@code NavigableMap}.  Similarly,
 * {@link #keySet()} can be overriden to return {@code NavigableSet}.
 *
 * <p>This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * <p>
 *  {@link SortedMap}扩展了导航方法,返回给定搜索目标的最接近的匹配项。
 * 方法{@code lowerEntry},{@code floorEntry},{@code ceilingEntry}和{@code higherEntry}返回与分别小于,小于或等于,大于或等于的键
 * 相关联的{@code Map.Entry} ,并且大于给定的键,如果没有这样的键,则返回{@code null}。
 *  {@link SortedMap}扩展了导航方法,返回给定搜索目标的最接近的匹配项。
 * 类似地,{@code lowerKey},{@code floorKey},{@code ceilingKey}和{@code higherKey}方法只返回相关的键。
 * 所有这些方法都被设计用于定位,而不是遍历条目。
 * 
 * <p>可以按照升序或降序键访问和遍历{@code NavigableMap}。 {@code descendingMap}方法返回地图的视图,所有关系和定向方法的意义都被反转。
 * 上升操作和视图的性能可能比下降操作和视图的性能更快。
 * 方法{@code subMap},{@code headMap}和{@code tailMap}与接受其他参数描述下限和上限是否是包含性和排除性的命名方法{@code SortedMap}不同。
 * 任何{@code NavigableMap}的子图必须实现{@code NavigableMap}界面。
 * 
 *  <p>此接口还定义返回和/或删除最小和最大映射(如果存在)的方法{@code firstEntry},{@code pollFirstEntry},{@code lastEntry}和{@code pollLastEntry}
 * ,否则返回{@code null}。
 * 
 *  <p>返回方法的实现应该返回{@code Map.Entry}对,表示映射在它们被产生时的快照,因此通常不支持可选的{@code Entry.setValue}方法。
 * 注意,可以使用方法{@code put}更改关联映射中的映射。
 * 
 * <p>方法{@link #subMap(Object,Object)subMap(K,K)},{@link #headMap(Object)headMap(K)}和{@link #tailMap(Object)tailMap被指定为返回{@code SortedMap}
 * 以允许对现有的{@code SortedMap}实现进行兼容改进以实现{@code NavigableMap},但是鼓励这个接口的扩展和实现来覆盖这些方法以返回{@code NavigableMap }
 * 。
 * 类似地,{@link #keySet()}可以被覆盖以返回{@code NavigableSet}。
 * 
 *  <p>此接口是的成员
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 *  Java集合框架</a>。
 * 
 * 
 * @author Doug Lea
 * @author Josh Bloch
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 * @since 1.6
 */
public interface NavigableMap<K,V> extends SortedMap<K,V> {
    /**
     * Returns a key-value mapping associated with the greatest key
     * strictly less than the given key, or {@code null} if there is
     * no such key.
     *
     * <p>
     *  返回与严格小于给定键的最大键关联的键值映射,如果没有这样的键,则返回{@code null}。
     * 
     * 
     * @param key the key
     * @return an entry with the greatest key less than {@code key},
     *         or {@code null} if there is no such key
     * @throws ClassCastException if the specified key cannot be compared
     *         with the keys currently in the map
     * @throws NullPointerException if the specified key is null
     *         and this map does not permit null keys
     */
    Map.Entry<K,V> lowerEntry(K key);

    /**
     * Returns the greatest key strictly less than the given key, or
     * {@code null} if there is no such key.
     *
     * <p>
     *  返回最大的键严格小于给定的键,或{@code null}如果没有这样的键。
     * 
     * 
     * @param key the key
     * @return the greatest key less than {@code key},
     *         or {@code null} if there is no such key
     * @throws ClassCastException if the specified key cannot be compared
     *         with the keys currently in the map
     * @throws NullPointerException if the specified key is null
     *         and this map does not permit null keys
     */
    K lowerKey(K key);

    /**
     * Returns a key-value mapping associated with the greatest key
     * less than or equal to the given key, or {@code null} if there
     * is no such key.
     *
     * <p>
     *  返回与最小键小于或等于给定键相关联的键值映射,如果没有此键,则返回{@code null}。
     * 
     * 
     * @param key the key
     * @return an entry with the greatest key less than or equal to
     *         {@code key}, or {@code null} if there is no such key
     * @throws ClassCastException if the specified key cannot be compared
     *         with the keys currently in the map
     * @throws NullPointerException if the specified key is null
     *         and this map does not permit null keys
     */
    Map.Entry<K,V> floorEntry(K key);

    /**
     * Returns the greatest key less than or equal to the given key,
     * or {@code null} if there is no such key.
     *
     * <p>
     *  返回小于或等于给定键的最大键,如果没有这样的键,则返回{@code null}。
     * 
     * 
     * @param key the key
     * @return the greatest key less than or equal to {@code key},
     *         or {@code null} if there is no such key
     * @throws ClassCastException if the specified key cannot be compared
     *         with the keys currently in the map
     * @throws NullPointerException if the specified key is null
     *         and this map does not permit null keys
     */
    K floorKey(K key);

    /**
     * Returns a key-value mapping associated with the least key
     * greater than or equal to the given key, or {@code null} if
     * there is no such key.
     *
     * <p>
     *  返回与大于或等于给定键的最小键相关联的键值映射,如果没有此键,则返回{@code null}。
     * 
     * 
     * @param key the key
     * @return an entry with the least key greater than or equal to
     *         {@code key}, or {@code null} if there is no such key
     * @throws ClassCastException if the specified key cannot be compared
     *         with the keys currently in the map
     * @throws NullPointerException if the specified key is null
     *         and this map does not permit null keys
     */
    Map.Entry<K,V> ceilingEntry(K key);

    /**
     * Returns the least key greater than or equal to the given key,
     * or {@code null} if there is no such key.
     *
     * <p>
     *  返回大于或等于给定键的最小键,如果没有这样的键,则返回{@code null}。
     * 
     * 
     * @param key the key
     * @return the least key greater than or equal to {@code key},
     *         or {@code null} if there is no such key
     * @throws ClassCastException if the specified key cannot be compared
     *         with the keys currently in the map
     * @throws NullPointerException if the specified key is null
     *         and this map does not permit null keys
     */
    K ceilingKey(K key);

    /**
     * Returns a key-value mapping associated with the least key
     * strictly greater than the given key, or {@code null} if there
     * is no such key.
     *
     * <p>
     *  返回与严格大于给定键的最小键相关联的键值映射,如果没有此键,则返回{@code null}。
     * 
     * 
     * @param key the key
     * @return an entry with the least key greater than {@code key},
     *         or {@code null} if there is no such key
     * @throws ClassCastException if the specified key cannot be compared
     *         with the keys currently in the map
     * @throws NullPointerException if the specified key is null
     *         and this map does not permit null keys
     */
    Map.Entry<K,V> higherEntry(K key);

    /**
     * Returns the least key strictly greater than the given key, or
     * {@code null} if there is no such key.
     *
     * <p>
     * 返回最小键严格大于给定键,或{@code null}如果没有这样的键。
     * 
     * 
     * @param key the key
     * @return the least key greater than {@code key},
     *         or {@code null} if there is no such key
     * @throws ClassCastException if the specified key cannot be compared
     *         with the keys currently in the map
     * @throws NullPointerException if the specified key is null
     *         and this map does not permit null keys
     */
    K higherKey(K key);

    /**
     * Returns a key-value mapping associated with the least
     * key in this map, or {@code null} if the map is empty.
     *
     * <p>
     *  返回与此地图中最小键相关联的键值映射,如果地图为空,则返回{@code null}。
     * 
     * 
     * @return an entry with the least key,
     *         or {@code null} if this map is empty
     */
    Map.Entry<K,V> firstEntry();

    /**
     * Returns a key-value mapping associated with the greatest
     * key in this map, or {@code null} if the map is empty.
     *
     * <p>
     *  返回与此地图中的最大键相关联的键值映射,如果地图为空,则返回{@code null}。
     * 
     * 
     * @return an entry with the greatest key,
     *         or {@code null} if this map is empty
     */
    Map.Entry<K,V> lastEntry();

    /**
     * Removes and returns a key-value mapping associated with
     * the least key in this map, or {@code null} if the map is empty.
     *
     * <p>
     *  删除并返回与此地图中最小键相关联的键值映射,如果地图为空,则删除{@code null}。
     * 
     * 
     * @return the removed first entry of this map,
     *         or {@code null} if this map is empty
     */
    Map.Entry<K,V> pollFirstEntry();

    /**
     * Removes and returns a key-value mapping associated with
     * the greatest key in this map, or {@code null} if the map is empty.
     *
     * <p>
     *  删除并返回与此地图中最大键相关联的键值映射,如果地图为空,则为{@code null}。
     * 
     * 
     * @return the removed last entry of this map,
     *         or {@code null} if this map is empty
     */
    Map.Entry<K,V> pollLastEntry();

    /**
     * Returns a reverse order view of the mappings contained in this map.
     * The descending map is backed by this map, so changes to the map are
     * reflected in the descending map, and vice-versa.  If either map is
     * modified while an iteration over a collection view of either map
     * is in progress (except through the iterator's own {@code remove}
     * operation), the results of the iteration are undefined.
     *
     * <p>The returned map has an ordering equivalent to
     * <tt>{@link Collections#reverseOrder(Comparator) Collections.reverseOrder}(comparator())</tt>.
     * The expression {@code m.descendingMap().descendingMap()} returns a
     * view of {@code m} essentially equivalent to {@code m}.
     *
     * <p>
     *  返回此地图中包含的映射的逆序视图。下降地图由此地图提供支持,因此对地图的更改会反映在下降地图中,反之亦然。
     * 如果在对任一映射的集合视图进行迭代时(如果通过迭代器自己的{@code remove}操作除外)修改了任一映射,则迭代的结果是未定义的。
     * 
     *  <p>返回的地图具有等效于<tt> {@ link Collections#reverseOrder(Comparator)Collections.reverseOrder}(comparator()
     * )</tt>的排序。
     * 表达式{@code m.descendingMap()。descendingMap()}返回基本上等同于{@code m}的{@code m}的视图。
     * 
     * 
     * @return a reverse order view of this map
     */
    NavigableMap<K,V> descendingMap();

    /**
     * Returns a {@link NavigableSet} view of the keys contained in this map.
     * The set's iterator returns the keys in ascending order.
     * The set is backed by the map, so changes to the map are reflected in
     * the set, and vice-versa.  If the map is modified while an iteration
     * over the set is in progress (except through the iterator's own {@code
     * remove} operation), the results of the iteration are undefined.  The
     * set supports element removal, which removes the corresponding mapping
     * from the map, via the {@code Iterator.remove}, {@code Set.remove},
     * {@code removeAll}, {@code retainAll}, and {@code clear} operations.
     * It does not support the {@code add} or {@code addAll} operations.
     *
     * <p>
     * 返回此地图中包含的键的{@link NavigableSet}视图。集合的迭代器以升序返回键。该集合由映射支持,因此对映射的更改反映在集合中,反之亦然。
     * 如果在迭代集合的过程中修改映射(除非通过迭代器自己的{@code remove}操作),迭代的结果是未定义的。
     * 集合支持元素删除,通过{@code Iterator.remove},{@code Set.remove},{@code removeAll},{@code retainAll}和{@code clearAll}
     * 删除地图中的相应映射}操作。
     * 如果在迭代集合的过程中修改映射(除非通过迭代器自己的{@code remove}操作),迭代的结果是未定义的。它不支持{@code add}或{@code addAll}操作。
     * 
     * 
     * @return a navigable set view of the keys in this map
     */
    NavigableSet<K> navigableKeySet();

    /**
     * Returns a reverse order {@link NavigableSet} view of the keys contained in this map.
     * The set's iterator returns the keys in descending order.
     * The set is backed by the map, so changes to the map are reflected in
     * the set, and vice-versa.  If the map is modified while an iteration
     * over the set is in progress (except through the iterator's own {@code
     * remove} operation), the results of the iteration are undefined.  The
     * set supports element removal, which removes the corresponding mapping
     * from the map, via the {@code Iterator.remove}, {@code Set.remove},
     * {@code removeAll}, {@code retainAll}, and {@code clear} operations.
     * It does not support the {@code add} or {@code addAll} operations.
     *
     * <p>
     *  返回此地图中包含的键的逆序{@link NavigableSet}视图。集合的迭代器以递减的顺序返回键。该集合由映射支持,因此对映射的更改反映在集合中,反之亦然。
     * 如果在迭代集合的过程中修改映射(除非通过迭代器自己的{@code remove}操作),迭代的结果是未定义的。
     * 集合支持元素删除,通过{@code Iterator.remove},{@code Set.remove},{@code removeAll},{@code retainAll}和{@code clearAll}
     * 删除地图中的相应映射}操作。
     * 如果在迭代集合的过程中修改映射(除非通过迭代器自己的{@code remove}操作),迭代的结果是未定义的。它不支持{@code add}或{@code addAll}操作。
     * 
     * 
     * @return a reverse order navigable set view of the keys in this map
     */
    NavigableSet<K> descendingKeySet();

    /**
     * Returns a view of the portion of this map whose keys range from
     * {@code fromKey} to {@code toKey}.  If {@code fromKey} and
     * {@code toKey} are equal, the returned map is empty unless
     * {@code fromInclusive} and {@code toInclusive} are both true.  The
     * returned map is backed by this map, so changes in the returned map are
     * reflected in this map, and vice-versa.  The returned map supports all
     * optional map operations that this map supports.
     *
     * <p>The returned map will throw an {@code IllegalArgumentException}
     * on an attempt to insert a key outside of its range, or to construct a
     * submap either of whose endpoints lie outside its range.
     *
     * <p>
     * 返回此映射的键范围从{@code fromKey}到{@code toKey}的部分的视图。
     * 如果{@code fromKey}和{@code toKey}相等,返回的地图是空的,除非{@code fromInclusive}和{@code toInclusive}都为true。
     * 返回的地图由此地图提供支持,因此返回地图中的更改会反映在此地图中,反之亦然。返回的地图支持此地图支持的所有可选的地图操作。
     * 
     *  <p>返回的地图将尝试插入一个超出其范围的键的{@code IllegalArgumentException},或构造其端点位于其范围之外的子地图。
     * 
     * 
     * @param fromKey low endpoint of the keys in the returned map
     * @param fromInclusive {@code true} if the low endpoint
     *        is to be included in the returned view
     * @param toKey high endpoint of the keys in the returned map
     * @param toInclusive {@code true} if the high endpoint
     *        is to be included in the returned view
     * @return a view of the portion of this map whose keys range from
     *         {@code fromKey} to {@code toKey}
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
    NavigableMap<K,V> subMap(K fromKey, boolean fromInclusive,
                             K toKey,   boolean toInclusive);

    /**
     * Returns a view of the portion of this map whose keys are less than (or
     * equal to, if {@code inclusive} is true) {@code toKey}.  The returned
     * map is backed by this map, so changes in the returned map are reflected
     * in this map, and vice-versa.  The returned map supports all optional
     * map operations that this map supports.
     *
     * <p>The returned map will throw an {@code IllegalArgumentException}
     * on an attempt to insert a key outside its range.
     *
     * <p>
     *  返回此键值小于(或等于,如果{@code inclusive}为true){@code toKey}的地图部分的视图。返回的地图由此地图提供支持,因此返回地图中的更改会反映在此地图中,反之亦然。
     * 返回的地图支持此地图支持的所有可选的地图操作。
     * 
     *  <p>返回的地图将尝试在其范围之外插入一个键,将抛出{@code IllegalArgumentException}。
     * 
     * 
     * @param toKey high endpoint of the keys in the returned map
     * @param inclusive {@code true} if the high endpoint
     *        is to be included in the returned view
     * @return a view of the portion of this map whose keys are less than
     *         (or equal to, if {@code inclusive} is true) {@code toKey}
     * @throws ClassCastException if {@code toKey} is not compatible
     *         with this map's comparator (or, if the map has no comparator,
     *         if {@code toKey} does not implement {@link Comparable}).
     *         Implementations may, but are not required to, throw this
     *         exception if {@code toKey} cannot be compared to keys
     *         currently in the map.
     * @throws NullPointerException if {@code toKey} is null
     *         and this map does not permit null keys
     * @throws IllegalArgumentException if this map itself has a
     *         restricted range, and {@code toKey} lies outside the
     *         bounds of the range
     */
    NavigableMap<K,V> headMap(K toKey, boolean inclusive);

    /**
     * Returns a view of the portion of this map whose keys are greater than (or
     * equal to, if {@code inclusive} is true) {@code fromKey}.  The returned
     * map is backed by this map, so changes in the returned map are reflected
     * in this map, and vice-versa.  The returned map supports all optional
     * map operations that this map supports.
     *
     * <p>The returned map will throw an {@code IllegalArgumentException}
     * on an attempt to insert a key outside its range.
     *
     * <p>
     *  返回此键值大于(或等于,如果{@code inclusive}为true){@code fromKey}的地图部分的视图。返回的地图由此地图提供支持,因此返回地图中的更改会反映在此地图中,反之亦然。
     * 返回的地图支持此地图支持的所有可选的地图操作。
     * 
     * <p>返回的地图将尝试在其范围之外插入一个键,将抛出{@code IllegalArgumentException}。
     * 
     * 
     * @param fromKey low endpoint of the keys in the returned map
     * @param inclusive {@code true} if the low endpoint
     *        is to be included in the returned view
     * @return a view of the portion of this map whose keys are greater than
     *         (or equal to, if {@code inclusive} is true) {@code fromKey}
     * @throws ClassCastException if {@code fromKey} is not compatible
     *         with this map's comparator (or, if the map has no comparator,
     *         if {@code fromKey} does not implement {@link Comparable}).
     *         Implementations may, but are not required to, throw this
     *         exception if {@code fromKey} cannot be compared to keys
     *         currently in the map.
     * @throws NullPointerException if {@code fromKey} is null
     *         and this map does not permit null keys
     * @throws IllegalArgumentException if this map itself has a
     *         restricted range, and {@code fromKey} lies outside the
     *         bounds of the range
     */
    NavigableMap<K,V> tailMap(K fromKey, boolean inclusive);

    /**
     * {@inheritDoc}
     *
     * <p>Equivalent to {@code subMap(fromKey, true, toKey, false)}.
     *
     * <p>
     *  {@inheritDoc}
     * 
     *  <p>等同于{@code subMap(fromKey,true,toKey,false)}。
     * 
     * 
     * @throws ClassCastException       {@inheritDoc}
     * @throws NullPointerException     {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    SortedMap<K,V> subMap(K fromKey, K toKey);

    /**
     * {@inheritDoc}
     *
     * <p>Equivalent to {@code headMap(toKey, false)}.
     *
     * <p>
     *  {@inheritDoc}
     * 
     *  <p>等同于{@code headMap(toKey,false)}。
     * 
     * 
     * @throws ClassCastException       {@inheritDoc}
     * @throws NullPointerException     {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    SortedMap<K,V> headMap(K toKey);

    /**
     * {@inheritDoc}
     *
     * <p>Equivalent to {@code tailMap(fromKey, true)}.
     *
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @throws ClassCastException       {@inheritDoc}
     * @throws NullPointerException     {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    SortedMap<K,V> tailMap(K fromKey);
}
