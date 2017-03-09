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

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.io.Serializable;

/**
 * An object that maps keys to values.  A map cannot contain duplicate keys;
 * each key can map to at most one value.
 *
 * <p>This interface takes the place of the <tt>Dictionary</tt> class, which
 * was a totally abstract class rather than an interface.
 *
 * <p>The <tt>Map</tt> interface provides three <i>collection views</i>, which
 * allow a map's contents to be viewed as a set of keys, collection of values,
 * or set of key-value mappings.  The <i>order</i> of a map is defined as
 * the order in which the iterators on the map's collection views return their
 * elements.  Some map implementations, like the <tt>TreeMap</tt> class, make
 * specific guarantees as to their order; others, like the <tt>HashMap</tt>
 * class, do not.
 *
 * <p>Note: great care must be exercised if mutable objects are used as map
 * keys.  The behavior of a map is not specified if the value of an object is
 * changed in a manner that affects <tt>equals</tt> comparisons while the
 * object is a key in the map.  A special case of this prohibition is that it
 * is not permissible for a map to contain itself as a key.  While it is
 * permissible for a map to contain itself as a value, extreme caution is
 * advised: the <tt>equals</tt> and <tt>hashCode</tt> methods are no longer
 * well defined on such a map.
 *
 * <p>All general-purpose map implementation classes should provide two
 * "standard" constructors: a void (no arguments) constructor which creates an
 * empty map, and a constructor with a single argument of type <tt>Map</tt>,
 * which creates a new map with the same key-value mappings as its argument.
 * In effect, the latter constructor allows the user to copy any map,
 * producing an equivalent map of the desired class.  There is no way to
 * enforce this recommendation (as interfaces cannot contain constructors) but
 * all of the general-purpose map implementations in the JDK comply.
 *
 * <p>The "destructive" methods contained in this interface, that is, the
 * methods that modify the map on which they operate, are specified to throw
 * <tt>UnsupportedOperationException</tt> if this map does not support the
 * operation.  If this is the case, these methods may, but are not required
 * to, throw an <tt>UnsupportedOperationException</tt> if the invocation would
 * have no effect on the map.  For example, invoking the {@link #putAll(Map)}
 * method on an unmodifiable map may, but is not required to, throw the
 * exception if the map whose mappings are to be "superimposed" is empty.
 *
 * <p>Some map implementations have restrictions on the keys and values they
 * may contain.  For example, some implementations prohibit null keys and
 * values, and some have restrictions on the types of their keys.  Attempting
 * to insert an ineligible key or value throws an unchecked exception,
 * typically <tt>NullPointerException</tt> or <tt>ClassCastException</tt>.
 * Attempting to query the presence of an ineligible key or value may throw an
 * exception, or it may simply return false; some implementations will exhibit
 * the former behavior and some will exhibit the latter.  More generally,
 * attempting an operation on an ineligible key or value whose completion
 * would not result in the insertion of an ineligible element into the map may
 * throw an exception or it may succeed, at the option of the implementation.
 * Such exceptions are marked as "optional" in the specification for this
 * interface.
 *
 * <p>Many methods in Collections Framework interfaces are defined
 * in terms of the {@link Object#equals(Object) equals} method.  For
 * example, the specification for the {@link #containsKey(Object)
 * containsKey(Object key)} method says: "returns <tt>true</tt> if and
 * only if this map contains a mapping for a key <tt>k</tt> such that
 * <tt>(key==null ? k==null : key.equals(k))</tt>." This specification should
 * <i>not</i> be construed to imply that invoking <tt>Map.containsKey</tt>
 * with a non-null argument <tt>key</tt> will cause <tt>key.equals(k)</tt> to
 * be invoked for any key <tt>k</tt>.  Implementations are free to
 * implement optimizations whereby the <tt>equals</tt> invocation is avoided,
 * for example, by first comparing the hash codes of the two keys.  (The
 * {@link Object#hashCode()} specification guarantees that two objects with
 * unequal hash codes cannot be equal.)  More generally, implementations of
 * the various Collections Framework interfaces are free to take advantage of
 * the specified behavior of underlying {@link Object} methods wherever the
 * implementor deems it appropriate.
 *
 * <p>Some map operations which perform recursive traversal of the map may fail
 * with an exception for self-referential instances where the map directly or
 * indirectly contains itself. This includes the {@code clone()},
 * {@code equals()}, {@code hashCode()} and {@code toString()} methods.
 * Implementations may optionally handle the self-referential scenario, however
 * most current implementations do not do so.
 *
 * <p>This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * <p>
 *  将键映射到值的对象。地图不能包含重复键;每个键可以映射到至多一个值。
 * 
 *  <p>此界面取代了<tt>字典</tt>类,这是一个完全抽象的类,而不是一个接口。
 * 
 *  <p> <tt>地图</tt>界面提供了三个<i>集合视图</i>,允许将地图内容视为一组键,值集合或键值映射集。地图的<i>顺序</i>定义为地图集合视图上的迭代器返回其元素的顺序。
 * 一些映射实现,如<tt> TreeMap </tt>类,对它们的顺序做出特定保证;其他,像<tt> HashMap </tt>类,不要。
 * 
 *  <p>注意：如果可变对象用作地图键,则必须非常小心。如果对象的值以影响<tt>等于</tt>比较的方式更改,而对象是映射中的键,则不指定映射的行为。这种禁止的特殊情况是,地图不允许将自身作为关键字。
 * 虽然允许一个映射包含自身作为一个值,建议非常小心：<tt> equals </tt>和<tt> hashCode </tt>方法在这样的映射上不再被很好地定义。
 * 
 * <p>所有通用地图实现类都应提供两个"标准"构造函数：一个void(无参数)构造函数,用于创建一个空映射,以及一个构造函数,其单个参数类型为<tt> Map </tt>创建具有与其参数相同的键值映射的新
 * 地图。
 * 实际上,后一个构造函数允许用户复制任何映射,产生期望类的等效映射。没有办法强制执行这个建议(因为接口不能包含构造函数),但JDK中的所有通用映射实现都符合。
 * 
 *  <p>此接口中包含的"破坏性"方法,即修改其所操作的映射的方法,如果此映射不支持该操作,则指定为抛出<tt> UnsupportedOperationException </tt>。
 * 如果是这种情况,如果调用对地图没有影响,这些方法可以但不是必须抛出<tt> UnsupportedOperationException </tt>。
 * 例如,如果映射要"叠加"的映射为空,则调用不可修改映射上的{@link #putAll(Map)}方法可以(但不是必须)抛出异常。
 * 
 * <p>一些地图实现对它们可能包含的键和值有限制。例如,一些实现禁止空键和值,并且一些实施例对它们的键的类型具有限制。
 * 尝试插入不合格的键或值会抛出未检查的异常,通常为<tt> NullPointerException </tt>或<tt> ClassCastException </tt>。
 * 尝试查询不合格的键或值的存在可能会抛出异常,或者它可能只是返回false;一些实现将展示前一行为,一些将展示后者。
 * 更一般地,尝试对不合格键或值的操作,其完成不会导致将不合格元素插入到映射中可能抛出异常或者它可以成功,在实现的选择。这种异常在此接口的规范中标记为"可选"。
 * 
 * <p> Collections框架接口中的许多方法都是根据{@link Object#equals(Object)equals}方法来定义的。
 * 例如,{@link #containsKey(Object)containsKey(Object key)}方法的规范说：当且仅当这个映射包含关键字<tt> k的映射时,返回<tt> true </tt>
 *  </tt>,使得<tt>(key == null?k == null：key.equals(k))</tt>。
 * <p> Collections框架接口中的许多方法都是根据{@link Object#equals(Object)equals}方法来定义的。
 * 此规范不应被解释为暗示使用非空参数<tt>键</tt>调用<tt> Map.containsKey </tt>会导致<tt> key.equals k)</tt>来为任何键<tt> k </tt>调用。
 * <p> Collections框架接口中的许多方法都是根据{@link Object#equals(Object)equals}方法来定义的。
 * 实现可以自由地实现优化,从而避免<tt>等于</t>>调用,例如,首先比较两个密钥的散列码。 ({@ link Object#hashCode()}规范保证具有不相等的哈希码的两个对象不能相等。
 * )更一般地,各种集合框架接口的实现可以利用底层{@link对象}方法,无论实现者认为合适。
 * 
 *  <p>执行地图递归遍历的一些映射操作可能会失败,自变量直接或间接包含自身的自引用实例的异常。
 * 这包括{@code clone()},{@code equals()},{@code hashCode()}和{@code toString()}方法。
 * 实现可以可选地处理自引用场景,然而大多数当前实现不这样做。
 * 
 * <p>此接口是的成员
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 *  Java集合框架</a>。
 * 
 * 
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 *
 * @author  Josh Bloch
 * @see HashMap
 * @see TreeMap
 * @see Hashtable
 * @see SortedMap
 * @see Collection
 * @see Set
 * @since 1.2
 */
public interface Map<K,V> {
    // Query Operations

    /**
     * Returns the number of key-value mappings in this map.  If the
     * map contains more than <tt>Integer.MAX_VALUE</tt> elements, returns
     * <tt>Integer.MAX_VALUE</tt>.
     *
     * <p>
     *  返回此地图中的键值映射的数量。如果地图包含的元素超过<tt> Integer.MAX_VALUE </tt>,则返回<tt> Integer.MAX_VALUE </tt>。
     * 
     * 
     * @return the number of key-value mappings in this map
     */
    int size();

    /**
     * Returns <tt>true</tt> if this map contains no key-value mappings.
     *
     * <p>
     *  如果此地图不包含键值映射,则返回<tt> true </tt>。
     * 
     * 
     * @return <tt>true</tt> if this map contains no key-value mappings
     */
    boolean isEmpty();

    /**
     * Returns <tt>true</tt> if this map contains a mapping for the specified
     * key.  More formally, returns <tt>true</tt> if and only if
     * this map contains a mapping for a key <tt>k</tt> such that
     * <tt>(key==null ? k==null : key.equals(k))</tt>.  (There can be
     * at most one such mapping.)
     *
     * <p>
     *  如果此地图包含指定键的映射,则返回<tt> true </tt>。
     * 更正式地,如果且仅当此映射包含关键字<tt> k </tt>的映射,使得<tt>(key == null?k == null：key)时,返回<tt> true </tt>。
     *  equals(k))</tt>。 (最多只能有一个这样的映射。)。
     * 
     * 
     * @param key key whose presence in this map is to be tested
     * @return <tt>true</tt> if this map contains a mapping for the specified
     *         key
     * @throws ClassCastException if the key is of an inappropriate type for
     *         this map
     * (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified key is null and this map
     *         does not permit null keys
     * (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     */
    boolean containsKey(Object key);

    /**
     * Returns <tt>true</tt> if this map maps one or more keys to the
     * specified value.  More formally, returns <tt>true</tt> if and only if
     * this map contains at least one mapping to a value <tt>v</tt> such that
     * <tt>(value==null ? v==null : value.equals(v))</tt>.  This operation
     * will probably require time linear in the map size for most
     * implementations of the <tt>Map</tt> interface.
     *
     * <p>
     *  如果此映射将一个或多个键映射到指定的值,则返回<tt> true </tt>。
     * 更正式地,如果且仅当此映射包含至少一个映射到<tt> v </tt>的值时,返回<tt> true </tt>,使得<tt>(value == null?v == null： value.equals(
     * v))</tt>。
     *  如果此映射将一个或多个键映射到指定的值,则返回<tt> true </tt>。对于<tt> Map </tt>接口的大多数实现,此操作可能需要地图大小的时间线性。
     * 
     * 
     * @param value value whose presence in this map is to be tested
     * @return <tt>true</tt> if this map maps one or more keys to the
     *         specified value
     * @throws ClassCastException if the value is of an inappropriate type for
     *         this map
     * (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified value is null and this
     *         map does not permit null values
     * (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     */
    boolean containsValue(Object value);

    /**
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * <p>More formally, if this map contains a mapping from a key
     * {@code k} to a value {@code v} such that {@code (key==null ? k==null :
     * key.equals(k))}, then this method returns {@code v}; otherwise
     * it returns {@code null}.  (There can be at most one such mapping.)
     *
     * <p>If this map permits null values, then a return value of
     * {@code null} does not <i>necessarily</i> indicate that the map
     * contains no mapping for the key; it's also possible that the map
     * explicitly maps the key to {@code null}.  The {@link #containsKey
     * containsKey} operation may be used to distinguish these two cases.
     *
     * <p>
     *  返回指定键映射到的值,如果此映射不包含键的映射,则返回{@code null}。
     * 
     *  更正式地说,如果此映射包含从密钥{@code k}到值{@code v}的映射,使得{@code(key == null?k == null：key.equals(k) )},那么这个方法返回{@code v}
     * ;否则返回{@code null}。
     *  (最多只能有一个这样的映射。)。
     * 
     * <p>如果此映射允许空值,则{@code null}的返回值不一定表示该映射不包含键的映射;也有可能映射将键明确映射到{@code null}。
     *  {@link #containsKey containsKey}操作可用于区分这两种情况。
     * 
     * 
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or
     *         {@code null} if this map contains no mapping for the key
     * @throws ClassCastException if the key is of an inappropriate type for
     *         this map
     * (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified key is null and this map
     *         does not permit null keys
     * (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     */
    V get(Object key);

    // Modification Operations

    /**
     * Associates the specified value with the specified key in this map
     * (optional operation).  If the map previously contained a mapping for
     * the key, the old value is replaced by the specified value.  (A map
     * <tt>m</tt> is said to contain a mapping for a key <tt>k</tt> if and only
     * if {@link #containsKey(Object) m.containsKey(k)} would return
     * <tt>true</tt>.)
     *
     * <p>
     *  将指定的值与此映射中的指定键相关联(可选操作)。如果映射先前包含键的映射,则旧值将替换为指定的值。
     *  (当且仅当{@link #containsKey(Object)m.containsKey(k)}将返回<tt> k </tt>时,映射<tt> m </tt> tt> true </tt>。)。
     * 
     * 
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with <tt>key</tt>, or
     *         <tt>null</tt> if there was no mapping for <tt>key</tt>.
     *         (A <tt>null</tt> return can also indicate that the map
     *         previously associated <tt>null</tt> with <tt>key</tt>,
     *         if the implementation supports <tt>null</tt> values.)
     * @throws UnsupportedOperationException if the <tt>put</tt> operation
     *         is not supported by this map
     * @throws ClassCastException if the class of the specified key or value
     *         prevents it from being stored in this map
     * @throws NullPointerException if the specified key or value is null
     *         and this map does not permit null keys or values
     * @throws IllegalArgumentException if some property of the specified key
     *         or value prevents it from being stored in this map
     */
    V put(K key, V value);

    /**
     * Removes the mapping for a key from this map if it is present
     * (optional operation).   More formally, if this map contains a mapping
     * from key <tt>k</tt> to value <tt>v</tt> such that
     * <code>(key==null ?  k==null : key.equals(k))</code>, that mapping
     * is removed.  (The map can contain at most one such mapping.)
     *
     * <p>Returns the value to which this map previously associated the key,
     * or <tt>null</tt> if the map contained no mapping for the key.
     *
     * <p>If this map permits null values, then a return value of
     * <tt>null</tt> does not <i>necessarily</i> indicate that the map
     * contained no mapping for the key; it's also possible that the map
     * explicitly mapped the key to <tt>null</tt>.
     *
     * <p>The map will not contain a mapping for the specified key once the
     * call returns.
     *
     * <p>
     *  如果该映射存在,则从此映射中删除该映射(可选操作)。
     * 更正式地,如果该映射包含从键<tt> k </tt>到值<tt> v </tt>的映射,使得<code>(key == null?k == null：key.equals ))</code>,则删除该映
     * 射。
     *  如果该映射存在,则从此映射中删除该映射(可选操作)。 (地图最多只能包含一个这样的映射。)。
     * 
     *  <p>返回此地图先前与键相关联的值,如果地图未包含键的映射,则返回<tt> null </tt>。
     * 
     *  <p>如果此映射允许空值,则<tt> null </tt>的返回值不一定表示该映射不包含键的映射;还有可能映射将键明确映射到<tt> null </tt>。
     * 
     *  <p>在调用返回后,地图将不包含指定键的映射。
     * 
     * 
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with <tt>key</tt>, or
     *         <tt>null</tt> if there was no mapping for <tt>key</tt>.
     * @throws UnsupportedOperationException if the <tt>remove</tt> operation
     *         is not supported by this map
     * @throws ClassCastException if the key is of an inappropriate type for
     *         this map
     * (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified key is null and this
     *         map does not permit null keys
     * (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     */
    V remove(Object key);


    // Bulk Operations

    /**
     * Copies all of the mappings from the specified map to this map
     * (optional operation).  The effect of this call is equivalent to that
     * of calling {@link #put(Object,Object) put(k, v)} on this map once
     * for each mapping from key <tt>k</tt> to value <tt>v</tt> in the
     * specified map.  The behavior of this operation is undefined if the
     * specified map is modified while the operation is in progress.
     *
     * <p>
     * 将指定映射中的所有映射复制到此映射(可选操作)。
     * 对于从键<tt> k </tt>到值<tt>的每个映射,此调用的效果等同于在该映射上调用{@link #put(Object,Object)put(k,v) v </tt>。
     * 如果在操作正在进行时修改指定的映射,则此操作的行为是未定义的。
     * 
     * 
     * @param m mappings to be stored in this map
     * @throws UnsupportedOperationException if the <tt>putAll</tt> operation
     *         is not supported by this map
     * @throws ClassCastException if the class of a key or value in the
     *         specified map prevents it from being stored in this map
     * @throws NullPointerException if the specified map is null, or if
     *         this map does not permit null keys or values, and the
     *         specified map contains null keys or values
     * @throws IllegalArgumentException if some property of a key or value in
     *         the specified map prevents it from being stored in this map
     */
    void putAll(Map<? extends K, ? extends V> m);

    /**
     * Removes all of the mappings from this map (optional operation).
     * The map will be empty after this call returns.
     *
     * <p>
     *  从此映射中删除所有映射(可选操作)。此调用返回后,地图将为空。
     * 
     * 
     * @throws UnsupportedOperationException if the <tt>clear</tt> operation
     *         is not supported by this map
     */
    void clear();


    // Views

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
     *
     * <p>
     *  返回此地图中包含的键的{@link Set}视图。该集合由映射支持,因此对映射的更改反映在集合中,反之亦然。
     * 如果在迭代集合的过程中修改映射(除非通过迭代器自己的<tt> remove </tt>操作),迭代的结果是未定义的。
     * 集合支持元素删除,它通过<tt> Iterator.remove </tt>,<tt> Set.remove </tt>,<tt> removeAll </tt>,<tt从地图中删除相应的映射> ret
     * ainAll </tt>和<tt>清除</tt>操作。
     * 如果在迭代集合的过程中修改映射(除非通过迭代器自己的<tt> remove </tt>操作),迭代的结果是未定义的。它不支持<tt>添加</tt>或<tt> addAll </tt>操作。
     * 
     * 
     * @return a set view of the keys contained in this map
     */
    Set<K> keySet();

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
     *
     * <p>
     * 返回此地图中包含的值的{@link Collection}视图。集合由地图支持,因此对地图的更改会反映在集合中,反之亦然。
     * 如果在集合的迭代正在进行时修改映射(除非通过迭代器自己的<tt> remove </tt>操作),迭代的结果是未定义的。
     * 集合支持元素删除,通过<tt> Iterator.remove </tt>,<tt> Collection.remove </tt>,<tt> removeAll </tt>,<tt从地图中删除相应的映射>
     *  retainAll </tt>和<tt>清除</tt>操作。
     * 如果在集合的迭代正在进行时修改映射(除非通过迭代器自己的<tt> remove </tt>操作),迭代的结果是未定义的。它不支持<tt>添加</tt>或<tt> addAll </tt>操作。
     * 
     * 
     * @return a collection view of the values contained in this map
     */
    Collection<V> values();

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
     *
     * <p>
     *  返回此地图中包含的映射的{@link Set}视图。该集合由映射支持,因此对映射的更改反映在集合中,反之亦然。
     * 如果在对迭代器执行迭代(即通过迭代器自己的<tt> remove </tt>操作,或通过对迭代器返回的映射条目执行<tt> setValue </tt>操作) )迭代的结果是未定义的。
     * 集合支持元素删除,它通过<tt> Iterator.remove </tt>,<tt> Set.remove </tt>,<tt> removeAll </tt>,<tt从地图中删除相应的映射> ret
     * ainAll </tt>和<tt>清除</tt>操作。
     * 如果在对迭代器执行迭代(即通过迭代器自己的<tt> remove </tt>操作,或通过对迭代器返回的映射条目执行<tt> setValue </tt>操作) )迭代的结果是未定义的。
     * 它不支持<tt>添加</tt>或<tt> addAll </tt>操作。
     * 
     * 
     * @return a set view of the mappings contained in this map
     */
    Set<Map.Entry<K, V>> entrySet();

    /**
     * A map entry (key-value pair).  The <tt>Map.entrySet</tt> method returns
     * a collection-view of the map, whose elements are of this class.  The
     * <i>only</i> way to obtain a reference to a map entry is from the
     * iterator of this collection-view.  These <tt>Map.Entry</tt> objects are
     * valid <i>only</i> for the duration of the iteration; more formally,
     * the behavior of a map entry is undefined if the backing map has been
     * modified after the entry was returned by the iterator, except through
     * the <tt>setValue</tt> operation on the map entry.
     *
     * <p>
     * 地图条目(键值对)。 <tt> Map.entrySet </tt>方法返回地图的集合视图,其元素是此类的元素。获取对映射条目的引用的<i>仅</i>方式来自此集合视图的迭代器。
     * 这些<tt> Map.Entry </tt>对象在迭代期间仅有效<i> </i>;更正式地说,如果在迭代器返回条目之后修改了后台映射,则映射条目的行为是未定义的,除非通过映射条目上的<tt> setVa
     * lue </tt>操作。
     * 地图条目(键值对)。 <tt> Map.entrySet </tt>方法返回地图的集合视图,其元素是此类的元素。获取对映射条目的引用的<i>仅</i>方式来自此集合视图的迭代器。
     * 
     * 
     * @see Map#entrySet()
     * @since 1.2
     */
    interface Entry<K,V> {
        /**
         * Returns the key corresponding to this entry.
         *
         * <p>
         *  返回与此条目对应的键。
         * 
         * 
         * @return the key corresponding to this entry
         * @throws IllegalStateException implementations may, but are not
         *         required to, throw this exception if the entry has been
         *         removed from the backing map.
         */
        K getKey();

        /**
         * Returns the value corresponding to this entry.  If the mapping
         * has been removed from the backing map (by the iterator's
         * <tt>remove</tt> operation), the results of this call are undefined.
         *
         * <p>
         *  返回与此条目对应的值。如果已从映射(通过迭代器的<tt> remove </tt>操作)中删除映射,则此调用的结果未定义。
         * 
         * 
         * @return the value corresponding to this entry
         * @throws IllegalStateException implementations may, but are not
         *         required to, throw this exception if the entry has been
         *         removed from the backing map.
         */
        V getValue();

        /**
         * Replaces the value corresponding to this entry with the specified
         * value (optional operation).  (Writes through to the map.)  The
         * behavior of this call is undefined if the mapping has already been
         * removed from the map (by the iterator's <tt>remove</tt> operation).
         *
         * <p>
         *  将与此条目对应的值替换为指定值(可选操作)。 (写入到映射。)如果映射已经从映射中移除(通过迭代器的<tt> remove </tt>操作),则此调用的行为是未定义的。
         * 
         * 
         * @param value new value to be stored in this entry
         * @return old value corresponding to the entry
         * @throws UnsupportedOperationException if the <tt>put</tt> operation
         *         is not supported by the backing map
         * @throws ClassCastException if the class of the specified value
         *         prevents it from being stored in the backing map
         * @throws NullPointerException if the backing map does not permit
         *         null values, and the specified value is null
         * @throws IllegalArgumentException if some property of this value
         *         prevents it from being stored in the backing map
         * @throws IllegalStateException implementations may, but are not
         *         required to, throw this exception if the entry has been
         *         removed from the backing map.
         */
        V setValue(V value);

        /**
         * Compares the specified object with this entry for equality.
         * Returns <tt>true</tt> if the given object is also a map entry and
         * the two entries represent the same mapping.  More formally, two
         * entries <tt>e1</tt> and <tt>e2</tt> represent the same mapping
         * if<pre>
         *     (e1.getKey()==null ?
         *      e2.getKey()==null : e1.getKey().equals(e2.getKey()))  &amp;&amp;
         *     (e1.getValue()==null ?
         *      e2.getValue()==null : e1.getValue().equals(e2.getValue()))
         * </pre>
         * This ensures that the <tt>equals</tt> method works properly across
         * different implementations of the <tt>Map.Entry</tt> interface.
         *
         * <p>
         *  将指定的对象与此条目进行比较以确保相等。如果给定对象也是映射条目,并且这两个条目表示相同的映射,则返回<tt> true </tt>。
         * 更正式地,如果<pre>(e1.getKey()== null?e2.getKey()== null：e1,则两个条目<tt> e1 </tt>和<tt> e2 </tt> .getKey()。
         * equals(e2.getKey()))&amp;&amp; (e1.getValue()== null?e2.getValue()== null：e1.getValue()。
         * equals(e2.getValue()))。
         * </pre>
         * 这可以确保<tt>等于</tt>方法在<tt> Map.Entry </tt>界面的不同实现中正常工作。
         * 
         * 
         * @param o object to be compared for equality with this map entry
         * @return <tt>true</tt> if the specified object is equal to this map
         *         entry
         */
        boolean equals(Object o);

        /**
         * Returns the hash code value for this map entry.  The hash code
         * of a map entry <tt>e</tt> is defined to be: <pre>
         *     (e.getKey()==null   ? 0 : e.getKey().hashCode()) ^
         *     (e.getValue()==null ? 0 : e.getValue().hashCode())
         * </pre>
         * This ensures that <tt>e1.equals(e2)</tt> implies that
         * <tt>e1.hashCode()==e2.hashCode()</tt> for any two Entries
         * <tt>e1</tt> and <tt>e2</tt>, as required by the general
         * contract of <tt>Object.hashCode</tt>.
         *
         * <p>
         *  返回此映射条目的哈希码值。映射条目<tt> e </tt>的哈希码定义为：<pre>(e.getKey()== null?0：e.getKey()。
         * hashCode())^(e.getValue ()== null?0：e.getValue()。hashCode())。
         * </pre>
         *  这确保对于任何两个条目<tt> e1 </tt>,<tt> e1.equals(e2)</tt>意味着<tt> e1.hashCode()== e2.hashCode()</tt> <tt> e2 </tt>
         * ,根据<tt> Object.hashCode </tt>的一般合同的要求。
         * 
         * 
         * @return the hash code value for this map entry
         * @see Object#hashCode()
         * @see Object#equals(Object)
         * @see #equals(Object)
         */
        int hashCode();

        /**
         * Returns a comparator that compares {@link Map.Entry} in natural order on key.
         *
         * <p>The returned comparator is serializable and throws {@link
         * NullPointerException} when comparing an entry with a null key.
         *
         * <p>
         *  返回一个比较器,比较{@link Map.Entry}键的自然顺序。
         * 
         *  <p>返回的比较器是可序列化的,并且在将一个条目与一个空键比较时会抛出{@link NullPointerException}。
         * 
         * 
         * @param  <K> the {@link Comparable} type of then map keys
         * @param  <V> the type of the map values
         * @return a comparator that compares {@link Map.Entry} in natural order on key.
         * @see Comparable
         * @since 1.8
         */
        public static <K extends Comparable<? super K>, V> Comparator<Map.Entry<K,V>> comparingByKey() {
            return (Comparator<Map.Entry<K, V>> & Serializable)
                (c1, c2) -> c1.getKey().compareTo(c2.getKey());
        }

        /**
         * Returns a comparator that compares {@link Map.Entry} in natural order on value.
         *
         * <p>The returned comparator is serializable and throws {@link
         * NullPointerException} when comparing an entry with null values.
         *
         * <p>
         *  返回一个比较器,比较{@link Map.Entry}的自然顺序。
         * 
         *  <p>返回的比较器是可序列化的,并且在将一个条目与空值比较时会抛出{@link NullPointerException}。
         * 
         * 
         * @param <K> the type of the map keys
         * @param <V> the {@link Comparable} type of the map values
         * @return a comparator that compares {@link Map.Entry} in natural order on value.
         * @see Comparable
         * @since 1.8
         */
        public static <K, V extends Comparable<? super V>> Comparator<Map.Entry<K,V>> comparingByValue() {
            return (Comparator<Map.Entry<K, V>> & Serializable)
                (c1, c2) -> c1.getValue().compareTo(c2.getValue());
        }

        /**
         * Returns a comparator that compares {@link Map.Entry} by key using the given
         * {@link Comparator}.
         *
         * <p>The returned comparator is serializable if the specified comparator
         * is also serializable.
         *
         * <p>
         *  返回一个比较器,通过使用给定的{@link比较器}比较{@link Map.Entry}。
         * 
         *  <p>如果指定的比较器也是可串行化的,返回的比较器是可序列化的。
         * 
         * 
         * @param  <K> the type of the map keys
         * @param  <V> the type of the map values
         * @param  cmp the key {@link Comparator}
         * @return a comparator that compares {@link Map.Entry} by the key.
         * @since 1.8
         */
        public static <K, V> Comparator<Map.Entry<K, V>> comparingByKey(Comparator<? super K> cmp) {
            Objects.requireNonNull(cmp);
            return (Comparator<Map.Entry<K, V>> & Serializable)
                (c1, c2) -> cmp.compare(c1.getKey(), c2.getKey());
        }

        /**
         * Returns a comparator that compares {@link Map.Entry} by value using the given
         * {@link Comparator}.
         *
         * <p>The returned comparator is serializable if the specified comparator
         * is also serializable.
         *
         * <p>
         *  返回一个比较器,使用给定的{@link Comparator}比较{@link Map.Entry}的值。
         * 
         *  <p>如果指定的比较器也是可串行化的,返回的比较器是可序列化的。
         * 
         * 
         * @param  <K> the type of the map keys
         * @param  <V> the type of the map values
         * @param  cmp the value {@link Comparator}
         * @return a comparator that compares {@link Map.Entry} by the value.
         * @since 1.8
         */
        public static <K, V> Comparator<Map.Entry<K, V>> comparingByValue(Comparator<? super V> cmp) {
            Objects.requireNonNull(cmp);
            return (Comparator<Map.Entry<K, V>> & Serializable)
                (c1, c2) -> cmp.compare(c1.getValue(), c2.getValue());
        }
    }

    // Comparison and hashing

    /**
     * Compares the specified object with this map for equality.  Returns
     * <tt>true</tt> if the given object is also a map and the two maps
     * represent the same mappings.  More formally, two maps <tt>m1</tt> and
     * <tt>m2</tt> represent the same mappings if
     * <tt>m1.entrySet().equals(m2.entrySet())</tt>.  This ensures that the
     * <tt>equals</tt> method works properly across different implementations
     * of the <tt>Map</tt> interface.
     *
     * <p>
     * 将指定的对象与此映射进行比较以实现相等性。如果给定对象也是一个映射,并且这两个映射表示相同的映射,则返回<tt> true </tt>。更正式地,如果<tt> m1.entrySet()。
     * equals(m2.entrySet())</tt>,则两个映射<tt> m1 </tt>和<tt> m2 </tt>这可以确保<tt>等于</tt>方法在<tt> Map </tt>界面的不同实现中正
     * 常工作。
     * 将指定的对象与此映射进行比较以实现相等性。如果给定对象也是一个映射,并且这两个映射表示相同的映射,则返回<tt> true </tt>。更正式地,如果<tt> m1.entrySet()。
     * 
     * 
     * @param o object to be compared for equality with this map
     * @return <tt>true</tt> if the specified object is equal to this map
     */
    boolean equals(Object o);

    /**
     * Returns the hash code value for this map.  The hash code of a map is
     * defined to be the sum of the hash codes of each entry in the map's
     * <tt>entrySet()</tt> view.  This ensures that <tt>m1.equals(m2)</tt>
     * implies that <tt>m1.hashCode()==m2.hashCode()</tt> for any two maps
     * <tt>m1</tt> and <tt>m2</tt>, as required by the general contract of
     * {@link Object#hashCode}.
     *
     * <p>
     *  返回此地图的哈希码值。地图的哈希码定义为地图的<tt> entrySet()</tt>视图中每个条目的哈希码的总和。
     * 这确保对于任何两个映射<tt> m1 </tt>,<tt> m1.equals(m2)</tt>意味着<tt> m1.hashCode()== m2.hashCode()</tt> <tt> m2 </tt>
     * ,根据{@link Object#hashCode}的一般合同的要求。
     *  返回此地图的哈希码值。地图的哈希码定义为地图的<tt> entrySet()</tt>视图中每个条目的哈希码的总和。
     * 
     * 
     * @return the hash code value for this map
     * @see Map.Entry#hashCode()
     * @see Object#equals(Object)
     * @see #equals(Object)
     */
    int hashCode();

    // Defaultable methods

    /**
     * Returns the value to which the specified key is mapped, or
     * {@code defaultValue} if this map contains no mapping for the key.
     *
     * @implSpec
     * The default implementation makes no guarantees about synchronization
     * or atomicity properties of this method. Any implementation providing
     * atomicity guarantees must override this method and document its
     * concurrency properties.
     *
     * <p>
     *  返回指定键映射到的值,如果此映射不包含键的映射,则返回{@code defaultValue}。
     * 
     *  @implSpec默认实现不保证此方法的同步或原子性属性。任何提供原子性保证的实现必须覆盖此方法并记录其并发属性。
     * 
     * 
     * @param key the key whose associated value is to be returned
     * @param defaultValue the default mapping of the key
     * @return the value to which the specified key is mapped, or
     * {@code defaultValue} if this map contains no mapping for the key
     * @throws ClassCastException if the key is of an inappropriate type for
     * this map
     * (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified key is null and this map
     * does not permit null keys
     * (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @since 1.8
     */
    default V getOrDefault(Object key, V defaultValue) {
        V v;
        return (((v = get(key)) != null) || containsKey(key))
            ? v
            : defaultValue;
    }

    /**
     * Performs the given action for each entry in this map until all entries
     * have been processed or the action throws an exception.   Unless
     * otherwise specified by the implementing class, actions are performed in
     * the order of entry set iteration (if an iteration order is specified.)
     * Exceptions thrown by the action are relayed to the caller.
     *
     * @implSpec
     * The default implementation is equivalent to, for this {@code map}:
     * <pre> {@code
     * for (Map.Entry<K, V> entry : map.entrySet())
     *     action.accept(entry.getKey(), entry.getValue());
     * }</pre>
     *
     * The default implementation makes no guarantees about synchronization
     * or atomicity properties of this method. Any implementation providing
     * atomicity guarantees must override this method and document its
     * concurrency properties.
     *
     * <p>
     *  对此映射中的每个条目执行给定的操作,直到所有条目都已处理或操作抛出异常。除非实现类另有规定,否则将按入口集迭代(如果指定了迭代顺序)的顺序执行操作。由操作抛出的异常中继到调用者。
     * 
     * @implSpec默认实现等效于{@code map}：<pre> {@code for(Map.Entry <K,V> entry：map.entrySet())action.accept(entry.getKey ,entry.getValue()); }
     *  </pre>。
     * 
     *  默认实现不保证此方法的同步或原子性属性。任何提供原子性保证的实现必须覆盖此方法并记录其并发属性。
     * 
     * 
     * @param action The action to be performed for each entry
     * @throws NullPointerException if the specified action is null
     * @throws ConcurrentModificationException if an entry is found to be
     * removed during iteration
     * @since 1.8
     */
    default void forEach(BiConsumer<? super K, ? super V> action) {
        Objects.requireNonNull(action);
        for (Map.Entry<K, V> entry : entrySet()) {
            K k;
            V v;
            try {
                k = entry.getKey();
                v = entry.getValue();
            } catch(IllegalStateException ise) {
                // this usually means the entry is no longer in the map.
                throw new ConcurrentModificationException(ise);
            }
            action.accept(k, v);
        }
    }

    /**
     * Replaces each entry's value with the result of invoking the given
     * function on that entry until all entries have been processed or the
     * function throws an exception.  Exceptions thrown by the function are
     * relayed to the caller.
     *
     * @implSpec
     * <p>The default implementation is equivalent to, for this {@code map}:
     * <pre> {@code
     * for (Map.Entry<K, V> entry : map.entrySet())
     *     entry.setValue(function.apply(entry.getKey(), entry.getValue()));
     * }</pre>
     *
     * <p>The default implementation makes no guarantees about synchronization
     * or atomicity properties of this method. Any implementation providing
     * atomicity guarantees must override this method and document its
     * concurrency properties.
     *
     * <p>
     *  将每个条目的值替换为调用该条目上的给定函数的结果,直到所有条目都已处理或函数抛出异常。函数抛出的异常被中继到调用者。
     * 
     *  @implSpec <p>对于这个{@code map},默认实现是等同的：<pre> {@code for(Map.Entry <K,V> entry：map.entrySet())entry.setValue apply(entry.getKey(),entry.getValue())); }
     *  </pre>。
     * 
     *  <p>默认实现不保证此方法的同步或原子性属性。任何提供原子性保证的实现必须覆盖此方法并记录其并发属性。
     * 
     * 
     * @param function the function to apply to each entry
     * @throws UnsupportedOperationException if the {@code set} operation
     * is not supported by this map's entry set iterator.
     * @throws ClassCastException if the class of a replacement value
     * prevents it from being stored in this map
     * @throws NullPointerException if the specified function is null, or the
     * specified replacement value is null, and this map does not permit null
     * values
     * @throws ClassCastException if a replacement value is of an inappropriate
     *         type for this map
     *         (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if function or a replacement value is null,
     *         and this map does not permit null keys or values
     *         (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws IllegalArgumentException if some property of a replacement value
     *         prevents it from being stored in this map
     *         (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws ConcurrentModificationException if an entry is found to be
     * removed during iteration
     * @since 1.8
     */
    default void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        Objects.requireNonNull(function);
        for (Map.Entry<K, V> entry : entrySet()) {
            K k;
            V v;
            try {
                k = entry.getKey();
                v = entry.getValue();
            } catch(IllegalStateException ise) {
                // this usually means the entry is no longer in the map.
                throw new ConcurrentModificationException(ise);
            }

            // ise thrown from function is not a cme.
            v = function.apply(k, v);

            try {
                entry.setValue(v);
            } catch(IllegalStateException ise) {
                // this usually means the entry is no longer in the map.
                throw new ConcurrentModificationException(ise);
            }
        }
    }

    /**
     * If the specified key is not already associated with a value (or is mapped
     * to {@code null}) associates it with the given value and returns
     * {@code null}, else returns the current value.
     *
     * @implSpec
     * The default implementation is equivalent to, for this {@code
     * map}:
     *
     * <pre> {@code
     * V v = map.get(key);
     * if (v == null)
     *     v = map.put(key, value);
     *
     * return v;
     * }</pre>
     *
     * <p>The default implementation makes no guarantees about synchronization
     * or atomicity properties of this method. Any implementation providing
     * atomicity guarantees must override this method and document its
     * concurrency properties.
     *
     * <p>
     *  如果指定的键尚未与某个值相关联(或映射到{@code null}),则将其与给定值相关联并返回{@code null},否则返回当前值。
     * 
     *  @implSpec对于这个{@code map},默认实现是等价的：
     * 
     *  <pre> {@code V v = map.get(key); if(v == null)v = map.put(key,value);
     * 
     *  return v; } </pre>
     * 
     * <p>默认实现不保证此方法的同步或原子性属性。任何提供原子性保证的实现必须覆盖此方法并记录其并发属性。
     * 
     * 
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with the specified key, or
     *         {@code null} if there was no mapping for the key.
     *         (A {@code null} return can also indicate that the map
     *         previously associated {@code null} with the key,
     *         if the implementation supports null values.)
     * @throws UnsupportedOperationException if the {@code put} operation
     *         is not supported by this map
     *         (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws ClassCastException if the key or value is of an inappropriate
     *         type for this map
     *         (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified key or value is null,
     *         and this map does not permit null keys or values
     *         (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws IllegalArgumentException if some property of the specified key
     *         or value prevents it from being stored in this map
     *         (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @since 1.8
     */
    default V putIfAbsent(K key, V value) {
        V v = get(key);
        if (v == null) {
            v = put(key, value);
        }

        return v;
    }

    /**
     * Removes the entry for the specified key only if it is currently
     * mapped to the specified value.
     *
     * @implSpec
     * The default implementation is equivalent to, for this {@code map}:
     *
     * <pre> {@code
     * if (map.containsKey(key) && Objects.equals(map.get(key), value)) {
     *     map.remove(key);
     *     return true;
     * } else
     *     return false;
     * }</pre>
     *
     * <p>The default implementation makes no guarantees about synchronization
     * or atomicity properties of this method. Any implementation providing
     * atomicity guarantees must override this method and document its
     * concurrency properties.
     *
     * <p>
     *  仅当指定键的条目当前映射到指定值时,才删除该条目。
     * 
     *  @implSpec对于这个{@code map},默认实现是等价的：
     * 
     *  <pre> {@code if(map.containsKey(key)&& Objects.equals(map.get(key),value)){map.remove(key); return true; }
     *  else return false; } </pre>。
     * 
     *  <p>默认实现不保证此方法的同步或原子性属性。任何提供原子性保证的实现必须覆盖此方法并记录其并发属性。
     * 
     * 
     * @param key key with which the specified value is associated
     * @param value value expected to be associated with the specified key
     * @return {@code true} if the value was removed
     * @throws UnsupportedOperationException if the {@code remove} operation
     *         is not supported by this map
     *         (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws ClassCastException if the key or value is of an inappropriate
     *         type for this map
     *         (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified key or value is null,
     *         and this map does not permit null keys or values
     *         (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @since 1.8
     */
    default boolean remove(Object key, Object value) {
        Object curValue = get(key);
        if (!Objects.equals(curValue, value) ||
            (curValue == null && !containsKey(key))) {
            return false;
        }
        remove(key);
        return true;
    }

    /**
     * Replaces the entry for the specified key only if currently
     * mapped to the specified value.
     *
     * @implSpec
     * The default implementation is equivalent to, for this {@code map}:
     *
     * <pre> {@code
     * if (map.containsKey(key) && Objects.equals(map.get(key), value)) {
     *     map.put(key, newValue);
     *     return true;
     * } else
     *     return false;
     * }</pre>
     *
     * The default implementation does not throw NullPointerException
     * for maps that do not support null values if oldValue is null unless
     * newValue is also null.
     *
     * <p>The default implementation makes no guarantees about synchronization
     * or atomicity properties of this method. Any implementation providing
     * atomicity guarantees must override this method and document its
     * concurrency properties.
     *
     * <p>
     *  仅当当前映射到指定值时替换指定键的条目。
     * 
     *  @implSpec对于这个{@code map},默认实现是等价的：
     * 
     *  <pre> {@code if(map.containsKey(key)&& Objects.equals(map.get(key),value)){map.put(key,newValue); return true; }
     *  else return false; } </pre>。
     * 
     *  如果oldValue为null,除非newValue也为null,否则默认实现不会为不支持null值的映射抛出NullPointerException。
     * 
     *  <p>默认实现不保证此方法的同步或原子性属性。任何提供原子性保证的实现必须覆盖此方法并记录其并发属性。
     * 
     * 
     * @param key key with which the specified value is associated
     * @param oldValue value expected to be associated with the specified key
     * @param newValue value to be associated with the specified key
     * @return {@code true} if the value was replaced
     * @throws UnsupportedOperationException if the {@code put} operation
     *         is not supported by this map
     *         (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws ClassCastException if the class of a specified key or value
     *         prevents it from being stored in this map
     * @throws NullPointerException if a specified key or newValue is null,
     *         and this map does not permit null keys or values
     * @throws NullPointerException if oldValue is null and this map does not
     *         permit null values
     *         (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws IllegalArgumentException if some property of a specified key
     *         or value prevents it from being stored in this map
     * @since 1.8
     */
    default boolean replace(K key, V oldValue, V newValue) {
        Object curValue = get(key);
        if (!Objects.equals(curValue, oldValue) ||
            (curValue == null && !containsKey(key))) {
            return false;
        }
        put(key, newValue);
        return true;
    }

    /**
     * Replaces the entry for the specified key only if it is
     * currently mapped to some value.
     *
     * @implSpec
     * The default implementation is equivalent to, for this {@code map}:
     *
     * <pre> {@code
     * if (map.containsKey(key)) {
     *     return map.put(key, value);
     * } else
     *     return null;
     * }</pre>
     *
     * <p>The default implementation makes no guarantees about synchronization
     * or atomicity properties of this method. Any implementation providing
     * atomicity guarantees must override this method and document its
     * concurrency properties.
      *
      * <p>
      * 仅当指定键的条目当前映射到某个值时,才替换该条目。
      * 
      *  @implSpec对于这个{@code map},默认实现是等价的：
      * 
      *  <pre> {@code if(map.containsKey(key)){return map.put(key,value); } else return null; } </pre>
      * 
      *  <p>默认实现不保证此方法的同步或原子性属性。任何提供原子性保证的实现必须覆盖此方法并记录其并发属性。
      * 
      * 
     * @param key key with which the specified value is associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with the specified key, or
     *         {@code null} if there was no mapping for the key.
     *         (A {@code null} return can also indicate that the map
     *         previously associated {@code null} with the key,
     *         if the implementation supports null values.)
     * @throws UnsupportedOperationException if the {@code put} operation
     *         is not supported by this map
     *         (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws ClassCastException if the class of the specified key or value
     *         prevents it from being stored in this map
     *         (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified key or value is null,
     *         and this map does not permit null keys or values
     * @throws IllegalArgumentException if some property of the specified key
     *         or value prevents it from being stored in this map
     * @since 1.8
     */
    default V replace(K key, V value) {
        V curValue;
        if (((curValue = get(key)) != null) || containsKey(key)) {
            curValue = put(key, value);
        }
        return curValue;
    }

    /**
     * If the specified key is not already associated with a value (or is mapped
     * to {@code null}), attempts to compute its value using the given mapping
     * function and enters it into this map unless {@code null}.
     *
     * <p>If the function returns {@code null} no mapping is recorded. If
     * the function itself throws an (unchecked) exception, the
     * exception is rethrown, and no mapping is recorded.  The most
     * common usage is to construct a new object serving as an initial
     * mapped value or memoized result, as in:
     *
     * <pre> {@code
     * map.computeIfAbsent(key, k -> new Value(f(k)));
     * }</pre>
     *
     * <p>Or to implement a multi-value map, {@code Map<K,Collection<V>>},
     * supporting multiple values per key:
     *
     * <pre> {@code
     * map.computeIfAbsent(key, k -> new HashSet<V>()).add(v);
     * }</pre>
     *
     *
     * @implSpec
     * The default implementation is equivalent to the following steps for this
     * {@code map}, then returning the current value or {@code null} if now
     * absent:
     *
     * <pre> {@code
     * if (map.get(key) == null) {
     *     V newValue = mappingFunction.apply(key);
     *     if (newValue != null)
     *         map.put(key, newValue);
     * }
     * }</pre>
     *
     * <p>The default implementation makes no guarantees about synchronization
     * or atomicity properties of this method. Any implementation providing
     * atomicity guarantees must override this method and document its
     * concurrency properties. In particular, all implementations of
     * subinterface {@link java.util.concurrent.ConcurrentMap} must document
     * whether the function is applied once atomically only if the value is not
     * present.
     *
     * <p>
     *  如果指定的键尚未与某个值相关联(或映射到{@code null}),则尝试使用给定的映射函数计算其值,并将其输入到此映射中,除非{@code null}。
     * 
     *  <p>如果函数返回{@code null},则不记录映射。如果函数本身抛出一个(未检查的)异常,则抛出异常,并且不记录映射。最常见的用法是构造一个用作初始映射值或记忆结果的新对象,如：
     * 
     *  <pre> {@code map.computeIfAbsent(key,k  - > new Value(f(k))); } </pre>
     * 
     *  <p>或实施多值地图,{@code Map <K,Collection <V >>},支持每个键的多个值：
     * 
     *  <pre> {@code map.computeIfAbsent(key,k  - > new HashSet <V>())。add(v); } </pre>
     * 
     *  @implSpec默认实现等效于此{@code map}的以下步骤,然后返回当前值或{@code null}(如果现在缺少)：
     * 
     * <pre> {@code if(map.get(key)== null){V newValue = mappingFunction.apply(key); if(newValue！= null)map.put(key,newValue); }
     * } </pre>。
     * 
     *  <p>默认实现不保证此方法的同步或原子性属性。任何提供原子性保证的实现必须覆盖此方法并记录其并发属性。
     * 特别地,子接口{@link java.util.concurrent.ConcurrentMap}的所有实现都必须记录该函数是否仅在值不存在时原子地应用。
     * 
     * 
     * @param key key with which the specified value is to be associated
     * @param mappingFunction the function to compute a value
     * @return the current (existing or computed) value associated with
     *         the specified key, or null if the computed value is null
     * @throws NullPointerException if the specified key is null and
     *         this map does not support null keys, or the mappingFunction
     *         is null
     * @throws UnsupportedOperationException if the {@code put} operation
     *         is not supported by this map
     *         (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws ClassCastException if the class of the specified key or value
     *         prevents it from being stored in this map
     *         (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @since 1.8
     */
    default V computeIfAbsent(K key,
            Function<? super K, ? extends V> mappingFunction) {
        Objects.requireNonNull(mappingFunction);
        V v;
        if ((v = get(key)) == null) {
            V newValue;
            if ((newValue = mappingFunction.apply(key)) != null) {
                put(key, newValue);
                return newValue;
            }
        }

        return v;
    }

    /**
     * If the value for the specified key is present and non-null, attempts to
     * compute a new mapping given the key and its current mapped value.
     *
     * <p>If the function returns {@code null}, the mapping is removed.  If the
     * function itself throws an (unchecked) exception, the exception is
     * rethrown, and the current mapping is left unchanged.
    *
     * @implSpec
     * The default implementation is equivalent to performing the following
     * steps for this {@code map}, then returning the current value or
     * {@code null} if now absent:
     *
     * <pre> {@code
     * if (map.get(key) != null) {
     *     V oldValue = map.get(key);
     *     V newValue = remappingFunction.apply(key, oldValue);
     *     if (newValue != null)
     *         map.put(key, newValue);
     *     else
     *         map.remove(key);
     * }
     * }</pre>
     *
     * <p>The default implementation makes no guarantees about synchronization
     * or atomicity properties of this method. Any implementation providing
     * atomicity guarantees must override this method and document its
     * concurrency properties. In particular, all implementations of
     * subinterface {@link java.util.concurrent.ConcurrentMap} must document
     * whether the function is applied once atomically only if the value is not
     * present.
     *
     * <p>
     *  如果指定键的值存在且非空,则尝试计算给定键和其当前映射值的新映射。
     * 
     *  <p>如果函数返回{@code null},则会删除映射。如果函数本身抛出一个(未检查的)异常,则异常被重新抛出,并且当前映射保持不变。
     * 
     *  @implSpec默认实现等效于对此{@code map}执行以下步骤,然后返回当前值或{@code null}(如果现在缺少)：
     * 
     *  <pre> {@code if(map.get(key)！= null){V oldValue = map.get(key); V newValue = remappingFunction.apply(key,oldValue); if(newValue！= null)map.put(key,newValue); else map.remove(key); }
     * } </pre>。
     * 
     * <p>默认实现不保证此方法的同步或原子性属性。任何提供原子性保证的实现必须覆盖此方法并记录其并发属性。
     * 特别地,子接口{@link java.util.concurrent.ConcurrentMap}的所有实现都必须记录该函数是否仅在值不存在时原子地应用。
     * 
     * 
     * @param key key with which the specified value is to be associated
     * @param remappingFunction the function to compute a value
     * @return the new value associated with the specified key, or null if none
     * @throws NullPointerException if the specified key is null and
     *         this map does not support null keys, or the
     *         remappingFunction is null
     * @throws UnsupportedOperationException if the {@code put} operation
     *         is not supported by this map
     *         (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws ClassCastException if the class of the specified key or value
     *         prevents it from being stored in this map
     *         (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @since 1.8
     */
    default V computeIfPresent(K key,
            BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        V oldValue;
        if ((oldValue = get(key)) != null) {
            V newValue = remappingFunction.apply(key, oldValue);
            if (newValue != null) {
                put(key, newValue);
                return newValue;
            } else {
                remove(key);
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Attempts to compute a mapping for the specified key and its current
     * mapped value (or {@code null} if there is no current mapping). For
     * example, to either create or append a {@code String} msg to a value
     * mapping:
     *
     * <pre> {@code
     * map.compute(key, (k, v) -> (v == null) ? msg : v.concat(msg))}</pre>
     * (Method {@link #merge merge()} is often simpler to use for such purposes.)
     *
     * <p>If the function returns {@code null}, the mapping is removed (or
     * remains absent if initially absent).  If the function itself throws an
     * (unchecked) exception, the exception is rethrown, and the current mapping
     * is left unchanged.
     *
     * @implSpec
     * The default implementation is equivalent to performing the following
     * steps for this {@code map}, then returning the current value or
     * {@code null} if absent:
     *
     * <pre> {@code
     * V oldValue = map.get(key);
     * V newValue = remappingFunction.apply(key, oldValue);
     * if (oldValue != null ) {
     *    if (newValue != null)
     *       map.put(key, newValue);
     *    else
     *       map.remove(key);
     * } else {
     *    if (newValue != null)
     *       map.put(key, newValue);
     *    else
     *       return null;
     * }
     * }</pre>
     *
     * <p>The default implementation makes no guarantees about synchronization
     * or atomicity properties of this method. Any implementation providing
     * atomicity guarantees must override this method and document its
     * concurrency properties. In particular, all implementations of
     * subinterface {@link java.util.concurrent.ConcurrentMap} must document
     * whether the function is applied once atomically only if the value is not
     * present.
     *
     * <p>
     *  尝试计算指定键及其当前映射值的映射(如果没有当前映射,则为{@code null})。例如,要创建或附加一个{@code String} msg到值映射：
     * 
     *  <pre> {@code map.compute(key,(k,v) - >(v == null)?msg：v.concat(msg))}方法{@link #merge merge }通常更容易用于这
     * 样的目的。
     * )。
     * 
     *  <p>如果函数返回{@code null},则会删除映射(如果初始不存在则保持不存在)。如果函数本身抛出一个(未检查的)异常,则异常被重新抛出,并且当前映射保持不变。
     * 
     *  @implSpec默认实现等效于对此{@code map}执行以下步骤,然后返回当前值或{@code null}(如果不存在)：
     * 
     *  <pre> {@code V oldValue = map.get(key); V newValue = remappingFunction.apply(key,oldValue); if(oldValue！= null){if(newValue！= null)map.put(key,newValue); else map.remove(key); }
     *  else {if(newValue！= null)map.put(key,newValue); else return null; }} </pre>。
     * 
     * <p>默认实现不保证此方法的同步或原子性属性。任何提供原子性保证的实现必须覆盖此方法并记录其并发属性。
     * 特别地,子接口{@link java.util.concurrent.ConcurrentMap}的所有实现都必须记录该函数是否仅在值不存在时原子地应用。
     * 
     * 
     * @param key key with which the specified value is to be associated
     * @param remappingFunction the function to compute a value
     * @return the new value associated with the specified key, or null if none
     * @throws NullPointerException if the specified key is null and
     *         this map does not support null keys, or the
     *         remappingFunction is null
     * @throws UnsupportedOperationException if the {@code put} operation
     *         is not supported by this map
     *         (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws ClassCastException if the class of the specified key or value
     *         prevents it from being stored in this map
     *         (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @since 1.8
     */
    default V compute(K key,
            BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        V oldValue = get(key);

        V newValue = remappingFunction.apply(key, oldValue);
        if (newValue == null) {
            // delete mapping
            if (oldValue != null || containsKey(key)) {
                // something to remove
                remove(key);
                return null;
            } else {
                // nothing to do. Leave things as they were.
                return null;
            }
        } else {
            // add or replace old mapping
            put(key, newValue);
            return newValue;
        }
    }

    /**
     * If the specified key is not already associated with a value or is
     * associated with null, associates it with the given non-null value.
     * Otherwise, replaces the associated value with the results of the given
     * remapping function, or removes if the result is {@code null}. This
     * method may be of use when combining multiple mapped values for a key.
     * For example, to either create or append a {@code String msg} to a
     * value mapping:
     *
     * <pre> {@code
     * map.merge(key, msg, String::concat)
     * }</pre>
     *
     * <p>If the function returns {@code null} the mapping is removed.  If the
     * function itself throws an (unchecked) exception, the exception is
     * rethrown, and the current mapping is left unchanged.
     *
     * @implSpec
     * The default implementation is equivalent to performing the following
     * steps for this {@code map}, then returning the current value or
     * {@code null} if absent:
     *
     * <pre> {@code
     * V oldValue = map.get(key);
     * V newValue = (oldValue == null) ? value :
     *              remappingFunction.apply(oldValue, value);
     * if (newValue == null)
     *     map.remove(key);
     * else
     *     map.put(key, newValue);
     * }</pre>
     *
     * <p>The default implementation makes no guarantees about synchronization
     * or atomicity properties of this method. Any implementation providing
     * atomicity guarantees must override this method and document its
     * concurrency properties. In particular, all implementations of
     * subinterface {@link java.util.concurrent.ConcurrentMap} must document
     * whether the function is applied once atomically only if the value is not
     * present.
     *
     * <p>
     * 
     * @param key key with which the resulting value is to be associated
     * @param value the non-null value to be merged with the existing value
     *        associated with the key or, if no existing value or a null value
     *        is associated with the key, to be associated with the key
     * @param remappingFunction the function to recompute a value if present
     * @return the new value associated with the specified key, or null if no
     *         value is associated with the key
     * @throws UnsupportedOperationException if the {@code put} operation
     *         is not supported by this map
     *         (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws ClassCastException if the class of the specified key or value
     *         prevents it from being stored in this map
     *         (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified key is null and this map
     *         does not support null keys or the value or remappingFunction is
     *         null
     * @since 1.8
     */
    default V merge(K key, V value,
            BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        Objects.requireNonNull(value);
        V oldValue = get(key);
        V newValue = (oldValue == null) ? value :
                   remappingFunction.apply(oldValue, value);
        if(newValue == null) {
            remove(key);
        } else {
            put(key, newValue);
        }
        return newValue;
    }
}
