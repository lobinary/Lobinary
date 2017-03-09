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
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A {@link java.util.Map} providing thread safety and atomicity
 * guarantees.
 *
 * <p>Memory consistency effects: As with other concurrent
 * collections, actions in a thread prior to placing an object into a
 * {@code ConcurrentMap} as a key or value
 * <a href="package-summary.html#MemoryVisibility"><i>happen-before</i></a>
 * actions subsequent to the access or removal of that object from
 * the {@code ConcurrentMap} in another thread.
 *
 * <p>This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * <p>
 *  {@link java.util.Map}提供线程安全性和原子性保证。
 * 
 *  <p>内存一致性效果：与其他并发集合一样,在将对象放入{@code ConcurrentMap}作为键或值之前,线程中的操作<a href="package-summary.html#MemoryVisibility">
 *  <i > happens-before </i> </a>之后,从另一个线程中的{@code ConcurrentMap}访问或删除该对象。
 * 
 *  <p>此接口是的成员
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 *  Java集合框架</a>。
 * 
 * 
 * @since 1.5
 * @author Doug Lea
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 */
public interface ConcurrentMap<K, V> extends Map<K, V> {

    /**
     * {@inheritDoc}
     *
     * @implNote This implementation assumes that the ConcurrentMap cannot
     * contain null values and {@code get()} returning null unambiguously means
     * the key is absent. Implementations which support null values
     * <strong>must</strong> override this default implementation.
     *
     * <p>
     *  {@inheritDoc}
     * 
     *  @implNote这个实现假设ConcurrentMap不能包含空值,并且{@code get()}明确返回null意味着键不存在。
     * 支持空值<strong>的实现必须</strong>覆盖此默认实现。
     * 
     * 
     * @throws ClassCastException {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     * @since 1.8
     */
    @Override
    default V getOrDefault(Object key, V defaultValue) {
        V v;
        return ((v = get(key)) != null) ? v : defaultValue;
    }

   /**
     * {@inheritDoc}
     *
     * @implSpec The default implementation is equivalent to, for this
     * {@code map}:
     * <pre> {@code
     * for ((Map.Entry<K, V> entry : map.entrySet())
     *     action.accept(entry.getKey(), entry.getValue());
     * }</pre>
     *
     * @implNote The default implementation assumes that
     * {@code IllegalStateException} thrown by {@code getKey()} or
     * {@code getValue()} indicates that the entry has been removed and cannot
     * be processed. Operation continues for subsequent entries.
     *
     * <p>
     *  {@inheritDoc}
     * 
     *  @implSpec对于这个{@code map},默认实现是等效的：<pre> {@code for((Map.Entry <K,V> entry：map.entrySet())action.accept(entry.getKey ),entry.getValue());}
     *  </pre>。
     * 
     *  @implNote默认实现假设{@code getKey()}或{@code getValue()}抛出的{@code IllegalStateException}表示该条目已删除,无法处理。
     * 操作继续进行后续条目。
     * 
     * 
     * @throws NullPointerException {@inheritDoc}
     * @since 1.8
     */
    @Override
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
                continue;
            }
            action.accept(k, v);
        }
    }

    /**
     * If the specified key is not already associated
     * with a value, associate it with the given value.
     * This is equivalent to
     *  <pre> {@code
     * if (!map.containsKey(key))
     *   return map.put(key, value);
     * else
     *   return map.get(key);
     * }</pre>
     *
     * except that the action is performed atomically.
     *
     * @implNote This implementation intentionally re-abstracts the
     * inappropriate default provided in {@code Map}.
     *
     * <p>
     * 如果指定的键尚未与某个值相关联,请将其与给定的值相关联。
     * 这相当于<pre> {@code if(！map.containsKey(key))return map.put(key,value); else return map.get(key); } </pre>
     * 。
     * 如果指定的键尚未与某个值相关联,请将其与给定的值相关联。
     * 
     *  除了该动作是以原子方式执行的。
     * 
     *  @implNote此实现有意重新抽象{@code Map}中提供的不当默认值。
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
     * @throws ClassCastException if the class of the specified key or value
     *         prevents it from being stored in this map
     * @throws NullPointerException if the specified key or value is null,
     *         and this map does not permit null keys or values
     * @throws IllegalArgumentException if some property of the specified key
     *         or value prevents it from being stored in this map
     */
     V putIfAbsent(K key, V value);

    /**
     * Removes the entry for a key only if currently mapped to a given value.
     * This is equivalent to
     *  <pre> {@code
     * if (map.containsKey(key) && Objects.equals(map.get(key), value)) {
     *   map.remove(key);
     *   return true;
     * } else
     *   return false;
     * }</pre>
     *
     * except that the action is performed atomically.
     *
     * @implNote This implementation intentionally re-abstracts the
     * inappropriate default provided in {@code Map}.
     *
     * <p>
     *  仅当当前映射到给定值时,才删除键的条目。
     * 这相当于<pre> {@code if(map.containsKey(key)&& Objects.equals(map.get(key),value)){map.remove(key); return true; }
     *  else return false; } </pre>。
     *  仅当当前映射到给定值时,才删除键的条目。
     * 
     *  除了该动作是以原子方式执行的。
     * 
     *  @implNote此实现有意重新抽象{@code Map}中提供的不当默认值。
     * 
     * 
     * @param key key with which the specified value is associated
     * @param value value expected to be associated with the specified key
     * @return {@code true} if the value was removed
     * @throws UnsupportedOperationException if the {@code remove} operation
     *         is not supported by this map
     * @throws ClassCastException if the key or value is of an inappropriate
     *         type for this map
     *         (<a href="../Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified key or value is null,
     *         and this map does not permit null keys or values
     *         (<a href="../Collection.html#optional-restrictions">optional</a>)
     */
    boolean remove(Object key, Object value);

    /**
     * Replaces the entry for a key only if currently mapped to a given value.
     * This is equivalent to
     *  <pre> {@code
     * if (map.containsKey(key) && Objects.equals(map.get(key), oldValue)) {
     *   map.put(key, newValue);
     *   return true;
     * } else
     *   return false;
     * }</pre>
     *
     * except that the action is performed atomically.
     *
     * @implNote This implementation intentionally re-abstracts the
     * inappropriate default provided in {@code Map}.
     *
     * <p>
     *  仅当当前映射到给定值时替换键的条目。
     * 这等效于<pre> {@code if(map.containsKey(key)&& Objects.equals(map.get(key),oldValue)){map.put(key,newValue); return true; }
     *  else return false; } </pre>。
     *  仅当当前映射到给定值时替换键的条目。
     * 
     *  除了该动作是以原子方式执行的。
     * 
     *  @implNote此实现有意重新抽象{@code Map}中提供的不当默认值。
     * 
     * 
     * @param key key with which the specified value is associated
     * @param oldValue value expected to be associated with the specified key
     * @param newValue value to be associated with the specified key
     * @return {@code true} if the value was replaced
     * @throws UnsupportedOperationException if the {@code put} operation
     *         is not supported by this map
     * @throws ClassCastException if the class of a specified key or value
     *         prevents it from being stored in this map
     * @throws NullPointerException if a specified key or value is null,
     *         and this map does not permit null keys or values
     * @throws IllegalArgumentException if some property of a specified key
     *         or value prevents it from being stored in this map
     */
    boolean replace(K key, V oldValue, V newValue);

    /**
     * Replaces the entry for a key only if currently mapped to some value.
     * This is equivalent to
     *  <pre> {@code
     * if (map.containsKey(key)) {
     *   return map.put(key, value);
     * } else
     *   return null;
     * }</pre>
     *
     * except that the action is performed atomically.
     *
     * @implNote This implementation intentionally re-abstracts the
     * inappropriate default provided in {@code Map}.
     *
     * <p>
     *  仅当当前映射到某个值时替换键的条目。
     * 这相当于<pre> {@code if(map.containsKey(key)){return map.put(key,value); } else return null; } </pre>。
     * 
     *  除了该动作是以原子方式执行的。
     * 
     * @implNote此实现有意重新抽象{@code Map}中提供的不当默认值。
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
     * @throws ClassCastException if the class of the specified key or value
     *         prevents it from being stored in this map
     * @throws NullPointerException if the specified key or value is null,
     *         and this map does not permit null keys or values
     * @throws IllegalArgumentException if some property of the specified key
     *         or value prevents it from being stored in this map
     */
    V replace(K key, V value);

    /**
     * {@inheritDoc}
     *
     * @implSpec
     * <p>The default implementation is equivalent to, for this {@code map}:
     * <pre> {@code
     * for ((Map.Entry<K, V> entry : map.entrySet())
     *     do {
     *        K k = entry.getKey();
     *        V v = entry.getValue();
     *     } while(!replace(k, v, function.apply(k, v)));
     * }</pre>
     *
     * The default implementation may retry these steps when multiple
     * threads attempt updates including potentially calling the function
     * repeatedly for a given key.
     *
     * <p>This implementation assumes that the ConcurrentMap cannot contain null
     * values and {@code get()} returning null unambiguously means the key is
     * absent. Implementations which support null values <strong>must</strong>
     * override this default implementation.
     *
     * <p>
     *  {@inheritDoc}
     * 
     *  @implSpec <p>对于这个{@code map},默认实现是等价的：<pre> {@code for((Map.Entry <K,V> entry：map.entrySet())do {K k = entry.getKey(); V v = entry.getValue();}
     *  while(！replace(k,v,function.apply(k,v)));} </pre>。
     * 
     *  默认实现可以在多个线程尝试更新时重试这些步骤,包括针对给定键可能重复地调用该函数。
     * 
     *  <p>此实现假定ConcurrentMap不能包含空值,而{@code get()}返回null明确意味着键不存在。支持空值<strong>的实现必须</strong>覆盖此默认实现。
     * 
     * 
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     * @throws ClassCastException {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     * @since 1.8
     */
    @Override
    default void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        Objects.requireNonNull(function);
        forEach((k,v) -> {
            while(!replace(k, v, function.apply(k, v))) {
                // v changed or k is gone
                if ( (v = get(k)) == null) {
                    // k is no longer in the map.
                    break;
                }
            }
        });
    }

    /**
     * {@inheritDoc}
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
     *         return map.putIfAbsent(key, newValue);
     * }
     * }</pre>
     *
     * The default implementation may retry these steps when multiple
     * threads attempt updates including potentially calling the mapping
     * function multiple times.
     *
     * <p>This implementation assumes that the ConcurrentMap cannot contain null
     * values and {@code get()} returning null unambiguously means the key is
     * absent. Implementations which support null values <strong>must</strong>
     * override this default implementation.
     *
     * <p>
     *  {@inheritDoc}
     * 
     *  @implSpec默认实现等效于此{@code map}的以下步骤,然后返回当前值或{@code null}(如果现在缺少)：
     * 
     *  <pre> {@code if(map.get(key)== null){V newValue = mappingFunction.apply(key); if(newValue！= null)return map.putIfAbsent(key,newValue); }
     * } </pre>。
     * 
     *  默认实现可以在多个线程尝试更新时重试这些步骤,包括可能多次调用映射函数。
     * 
     * <p>此实现假定ConcurrentMap不能包含空值,而{@code get()}返回null明确意味着键不存在。支持空值<strong>的实现必须</strong>覆盖此默认实现。
     * 
     * 
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     * @since 1.8
     */
    @Override
    default V computeIfAbsent(K key,
            Function<? super K, ? extends V> mappingFunction) {
        Objects.requireNonNull(mappingFunction);
        V v, newValue;
        return ((v = get(key)) == null &&
                (newValue = mappingFunction.apply(key)) != null &&
                (v = putIfAbsent(key, newValue)) == null) ? newValue : v;
    }

    /**
     * {@inheritDoc}
     *
     * @implSpec
     * The default implementation is equivalent to performing the following
     * steps for this {@code map}, then returning the current value or
     * {@code null} if now absent. :
     *
     * <pre> {@code
     * if (map.get(key) != null) {
     *     V oldValue = map.get(key);
     *     V newValue = remappingFunction.apply(key, oldValue);
     *     if (newValue != null)
     *         map.replace(key, oldValue, newValue);
     *     else
     *         map.remove(key, oldValue);
     * }
     * }</pre>
     *
     * The default implementation may retry these steps when multiple threads
     * attempt updates including potentially calling the remapping function
     * multiple times.
     *
     * <p>This implementation assumes that the ConcurrentMap cannot contain null
     * values and {@code get()} returning null unambiguously means the key is
     * absent. Implementations which support null values <strong>must</strong>
     * override this default implementation.
     *
     * <p>
     *  {@inheritDoc}
     * 
     *  @implSpec默认实现等效于对此{@code map}执行以下步骤,然后返回当前值或{@code null}(如果现在不存在)。 ：
     * 
     *  <pre> {@code if(map.get(key)！= null){V oldValue = map.get(key); V newValue = remappingFunction.apply(key,oldValue); if(newValue！= null)map.replace(key,oldValue,newValue); else map.remove(key,oldValue); }
     * } </pre>。
     * 
     *  当多个线程尝试更新时,默认实现可以重试这些步骤,包括潜在地多次调用重映射功能。
     * 
     *  <p>此实现假定ConcurrentMap不能包含空值,而{@code get()}返回null明确意味着键不存在。支持空值<strong>的实现必须</strong>覆盖此默认实现。
     * 
     * 
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     * @since 1.8
     */
    @Override
    default V computeIfPresent(K key,
            BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        V oldValue;
        while((oldValue = get(key)) != null) {
            V newValue = remappingFunction.apply(key, oldValue);
            if (newValue != null) {
                if (replace(key, oldValue, newValue))
                    return newValue;
            } else if (remove(key, oldValue))
               return null;
        }
        return oldValue;
    }

    /**
     * {@inheritDoc}
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
     *       map.replace(key, oldValue, newValue);
     *    else
     *       map.remove(key, oldValue);
     * } else {
     *    if (newValue != null)
     *       map.putIfAbsent(key, newValue);
     *    else
     *       return null;
     * }
     * }</pre>
     *
     * The default implementation may retry these steps when multiple
     * threads attempt updates including potentially calling the remapping
     * function multiple times.
     *
     * <p>This implementation assumes that the ConcurrentMap cannot contain null
     * values and {@code get()} returning null unambiguously means the key is
     * absent. Implementations which support null values <strong>must</strong>
     * override this default implementation.
     *
     * <p>
     *  {@inheritDoc}
     * 
     *  @implSpec默认实现等效于对此{@code map}执行以下步骤,然后返回当前值或{@code null}(如果不存在)：
     * 
     * <pre> {@code V oldValue = map.get(key); V newValue = remappingFunction.apply(key,oldValue); if(newValue！= null){if(newValue！= null)map.replace(key,oldValue,newValue); else map.remove(key,oldValue); }
     *  else {if(newValue！= null)map.putIfAbsent(key,newValue); else return null; }} </pre>。
     * 
     *  当多个线程尝试更新时,默认实现可以重试这些步骤,包括潜在地多次调用重映射功能。
     * 
     *  <p>此实现假定ConcurrentMap不能包含空值,而{@code get()}返回null明确意味着键不存在。支持空值<strong>的实现必须</strong>覆盖此默认实现。
     * 
     * 
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     * @since 1.8
     */
    @Override
    default V compute(K key,
            BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        V oldValue = get(key);
        for(;;) {
            V newValue = remappingFunction.apply(key, oldValue);
            if (newValue == null) {
                // delete mapping
                if (oldValue != null || containsKey(key)) {
                    // something to remove
                    if (remove(key, oldValue)) {
                        // removed the old value as expected
                        return null;
                    }

                    // some other value replaced old value. try again.
                    oldValue = get(key);
                } else {
                    // nothing to do. Leave things as they were.
                    return null;
                }
            } else {
                // add or replace old mapping
                if (oldValue != null) {
                    // replace
                    if (replace(key, oldValue, newValue)) {
                        // replaced as expected.
                        return newValue;
                    }

                    // some other value replaced old value. try again.
                    oldValue = get(key);
                } else {
                    // add (replace if oldValue was null)
                    if ((oldValue = putIfAbsent(key, newValue)) == null) {
                        // replaced
                        return newValue;
                    }

                    // some other value replaced old value. try again.
                }
            }
        }
    }


    /**
     * {@inheritDoc}
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
     * <p>The default implementation may retry these steps when multiple
     * threads attempt updates including potentially calling the remapping
     * function multiple times.
     *
     * <p>This implementation assumes that the ConcurrentMap cannot contain null
     * values and {@code get()} returning null unambiguously means the key is
     * absent. Implementations which support null values <strong>must</strong>
     * override this default implementation.
     *
     * <p>
     *  {@inheritDoc}
     * 
     *  @implSpec默认实现等效于对此{@code map}执行以下步骤,然后返回当前值或{@code null}(如果不存在)：
     * 
     *  <pre> {@code V oldValue = map.get(key); V newValue =(oldValue == null)? value：remappingFunction.apply(oldValue,value); if(newValue == null)map.remove(key); else map.put(key,newValue); }
     *  </pre>。
     * 
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     * @since 1.8
     */
    @Override
    default V merge(K key, V value,
            BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        Objects.requireNonNull(value);
        V oldValue = get(key);
        for (;;) {
            if (oldValue != null) {
                V newValue = remappingFunction.apply(oldValue, value);
                if (newValue != null) {
                    if (replace(key, oldValue, newValue))
                        return newValue;
                } else if (remove(key, oldValue)) {
                    return null;
                }
                oldValue = get(key);
            } else {
                if ((oldValue = putIfAbsent(key, value)) == null) {
                    return value;
                }
            }
        }
    }
}
