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
 * Written by Doug Lea with assistance from members of JCP JSR-166
 * Expert Group and released to the public domain, as explained at
 * http://creativecommons.org/publicdomain/zero/1.0/
 * <p>
 *  由Doug Lea在JCP JSR-166专家组成员的帮助下撰写,并发布到公共领域,如http://creativecommons.org/publicdomain/zero/1.0/
 * 
 */

package java.util.concurrent;
import java.util.*;

/**
 * A {@link ConcurrentMap} supporting {@link NavigableMap} operations,
 * and recursively so for its navigable sub-maps.
 *
 * <p>This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * <p>
 *  支持{@link NavigableMap}操作的{@link ConcurrentMap},以及对其可导航子地图递归。
 * 
 *  <p>此接口是的成员
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 *  Java集合框架</a>。
 * 
 * 
 * @author Doug Lea
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 * @since 1.6
 */
public interface ConcurrentNavigableMap<K,V>
    extends ConcurrentMap<K,V>, NavigableMap<K,V>
{
    /**
    /* <p>
    /* 
     * @throws ClassCastException       {@inheritDoc}
     * @throws NullPointerException     {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    ConcurrentNavigableMap<K,V> subMap(K fromKey, boolean fromInclusive,
                                       K toKey,   boolean toInclusive);

    /**
    /* <p>
    /* 
     * @throws ClassCastException       {@inheritDoc}
     * @throws NullPointerException     {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    ConcurrentNavigableMap<K,V> headMap(K toKey, boolean inclusive);

    /**
    /* <p>
    /* 
     * @throws ClassCastException       {@inheritDoc}
     * @throws NullPointerException     {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    ConcurrentNavigableMap<K,V> tailMap(K fromKey, boolean inclusive);

    /**
    /* <p>
    /* 
     * @throws ClassCastException       {@inheritDoc}
     * @throws NullPointerException     {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    ConcurrentNavigableMap<K,V> subMap(K fromKey, K toKey);

    /**
    /* <p>
    /* 
     * @throws ClassCastException       {@inheritDoc}
     * @throws NullPointerException     {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    ConcurrentNavigableMap<K,V> headMap(K toKey);

    /**
    /* <p>
    /* 
     * @throws ClassCastException       {@inheritDoc}
     * @throws NullPointerException     {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    ConcurrentNavigableMap<K,V> tailMap(K fromKey);

    /**
     * Returns a reverse order view of the mappings contained in this map.
     * The descending map is backed by this map, so changes to the map are
     * reflected in the descending map, and vice-versa.
     *
     * <p>The returned map has an ordering equivalent to
     * {@link Collections#reverseOrder(Comparator) Collections.reverseOrder}{@code (comparator())}.
     * The expression {@code m.descendingMap().descendingMap()} returns a
     * view of {@code m} essentially equivalent to {@code m}.
     *
     * <p>
     *  返回此地图中包含的映射的逆序视图。下降地图由此地图提供支持,因此对地图的更改会反映在下降地图中,反之亦然。
     * 
     *  <p>返回的地图的排序等同于{@link Collections#reverseOrder(Comparator)Collections.reverseOrder}{@code(comparator())}
     * 。
     * 表达式{@code m.descendingMap()。descendingMap()}返回基本上等同于{@code m}的{@code m}的视图。
     * 
     * 
     * @return a reverse order view of this map
     */
    ConcurrentNavigableMap<K,V> descendingMap();

    /**
     * Returns a {@link NavigableSet} view of the keys contained in this map.
     * The set's iterator returns the keys in ascending order.
     * The set is backed by the map, so changes to the map are
     * reflected in the set, and vice-versa.  The set supports element
     * removal, which removes the corresponding mapping from the map,
     * via the {@code Iterator.remove}, {@code Set.remove},
     * {@code removeAll}, {@code retainAll}, and {@code clear}
     * operations.  It does not support the {@code add} or {@code addAll}
     * operations.
     *
     * <p>The view's iterators and spliterators are
     * <a href="package-summary.html#Weakly"><i>weakly consistent</i></a>.
     *
     * <p>
     *  返回此地图中包含的键的{@link NavigableSet}视图。集合的迭代器以升序返回键。该集合由映射支持,因此对映射的更改反映在集合中,反之亦然。
     * 集合支持元素删除,通过{@code Iterator.remove},{@code Set.remove},{@code removeAll},{@code retainAll}和{@code clearAll}
     * 删除地图中的相应映射}操作。
     *  返回此地图中包含的键的{@link NavigableSet}视图。集合的迭代器以升序返回键。该集合由映射支持,因此对映射的更改反映在集合中,反之亦然。
     * 它不支持{@code add}或{@code addAll}操作。
     * 
     *  <p>视图的迭代器和分隔符为<a href="package-summary.html#Weakly"> <i>弱一致</i> </a>。
     * 
     * 
     * @return a navigable set view of the keys in this map
     */
    public NavigableSet<K> navigableKeySet();

    /**
     * Returns a {@link NavigableSet} view of the keys contained in this map.
     * The set's iterator returns the keys in ascending order.
     * The set is backed by the map, so changes to the map are
     * reflected in the set, and vice-versa.  The set supports element
     * removal, which removes the corresponding mapping from the map,
     * via the {@code Iterator.remove}, {@code Set.remove},
     * {@code removeAll}, {@code retainAll}, and {@code clear}
     * operations.  It does not support the {@code add} or {@code addAll}
     * operations.
     *
     * <p>The view's iterators and spliterators are
     * <a href="package-summary.html#Weakly"><i>weakly consistent</i></a>.
     *
     * <p>This method is equivalent to method {@code navigableKeySet}.
     *
     * <p>
     * 返回此地图中包含的键的{@link NavigableSet}视图。集合的迭代器以升序返回键。该集合由映射支持,因此对映射的更改反映在集合中,反之亦然。
     * 集合支持元素删除,通过{@code Iterator.remove},{@code Set.remove},{@code removeAll},{@code retainAll}和{@code clearAll}
     * 删除地图中的相应映射}操作。
     * 返回此地图中包含的键的{@link NavigableSet}视图。集合的迭代器以升序返回键。该集合由映射支持,因此对映射的更改反映在集合中,反之亦然。
     * 它不支持{@code add}或{@code addAll}操作。
     * 
     *  <p>视图的迭代器和分隔符为<a href="package-summary.html#Weakly"> <i>弱一致</i> </a>。
     * 
     *  <p>此方法等同于{@code navigableKeySet}方法。
     * 
     * 
     * @return a navigable set view of the keys in this map
     */
    NavigableSet<K> keySet();

    /**
     * Returns a reverse order {@link NavigableSet} view of the keys contained in this map.
     * The set's iterator returns the keys in descending order.
     * The set is backed by the map, so changes to the map are
     * reflected in the set, and vice-versa.  The set supports element
     * removal, which removes the corresponding mapping from the map,
     * via the {@code Iterator.remove}, {@code Set.remove},
     * {@code removeAll}, {@code retainAll}, and {@code clear}
     * operations.  It does not support the {@code add} or {@code addAll}
     * operations.
     *
     * <p>The view's iterators and spliterators are
     * <a href="package-summary.html#Weakly"><i>weakly consistent</i></a>.
     *
     * <p>
     * 
     * @return a reverse order navigable set view of the keys in this map
     */
    public NavigableSet<K> descendingKeySet();
}
