/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, Oracle and/or its affiliates. All rights reserved.
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

package javax.script;

import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.util.Set;

/**
 * A simple implementation of Bindings backed by
 * a <code>HashMap</code> or some other specified <code>Map</code>.
 *
 * <p>
 *  由<code> HashMap </code>或其他指定的<code> Map </code>支持的绑定的简单实现。
 * 
 * 
 * @author Mike Grogan
 * @since 1.6
 */
public class SimpleBindings implements Bindings {

    /**
     * The <code>Map</code> field stores the attributes.
     * <p>
     *  <code> Map </code>字段存储属性。
     * 
     */
    private Map<String,Object> map;

    /**
     * Constructor uses an existing <code>Map</code> to store the values.
     * <p>
     *  构造函数使用现有的<code> Map </code>来存储值。
     * 
     * 
     * @param m The <code>Map</code> backing this <code>SimpleBindings</code>.
     * @throws NullPointerException if m is null
     */
    public SimpleBindings(Map<String,Object> m) {
        if (m == null) {
            throw new NullPointerException();
        }
        this.map = m;
    }

    /**
     * Default constructor uses a <code>HashMap</code>.
     * <p>
     *  默认构造函数使用一个<code> HashMap </code>。
     * 
     */
    public SimpleBindings() {
        this(new HashMap<String,Object>());
    }

    /**
     * Sets the specified key/value in the underlying <code>map</code> field.
     *
     * <p>
     *  在底层的<code> map </code>字段中设置指定的键/值。
     * 
     * 
     * @param name Name of value
     * @param value Value to set.
     *
     * @return Previous value for the specified key.  Returns null if key was previously
     * unset.
     *
     * @throws NullPointerException if the name is null.
     * @throws IllegalArgumentException if the name is empty.
     */
    public Object put(String name, Object value) {
        checkKey(name);
        return map.put(name,value);
    }

    /**
     * <code>putAll</code> is implemented using <code>Map.putAll</code>.
     *
     * <p>
     *  <code> putAll </code>是使用<code> Map.putAll </code>实现的。
     * 
     * 
     * @param toMerge The <code>Map</code> of values to add.
     *
     * @throws NullPointerException
     *         if toMerge map is null or if some key in the map is null.
     * @throws IllegalArgumentException
     *         if some key in the map is an empty String.
     */
    public void putAll(Map<? extends String, ? extends Object> toMerge) {
        if (toMerge == null) {
            throw new NullPointerException("toMerge map is null");
        }
        for (Map.Entry<? extends String, ? extends Object> entry : toMerge.entrySet()) {
            String key = entry.getKey();
            checkKey(key);
            put(key, entry.getValue());
        }
    }

    /** {@inheritDoc} */
    public void clear() {
        map.clear();
    }

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
     * @param key key whose presence in this map is to be tested.
     * @return <tt>true</tt> if this map contains a mapping for the specified
     *         key.
     *
     * @throws NullPointerException if key is null
     * @throws ClassCastException if key is not String
     * @throws IllegalArgumentException if key is empty String
     */
    public boolean containsKey(Object key) {
        checkKey(key);
        return map.containsKey(key);
    }

    /** {@inheritDoc} */
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    /** {@inheritDoc} */
    public Set<Map.Entry<String, Object>> entrySet() {
        return map.entrySet();
    }

    /**
     * Returns the value to which this map maps the specified key.  Returns
     * <tt>null</tt> if the map contains no mapping for this key.  A return
     * value of <tt>null</tt> does not <i>necessarily</i> indicate that the
     * map contains no mapping for the key; it's also possible that the map
     * explicitly maps the key to <tt>null</tt>.  The <tt>containsKey</tt>
     * operation may be used to distinguish these two cases.
     *
     * <p>More formally, if this map contains a mapping from a key
     * <tt>k</tt> to a value <tt>v</tt> such that <tt>(key==null ? k==null :
     * key.equals(k))</tt>, then this method returns <tt>v</tt>; otherwise
     * it returns <tt>null</tt>.  (There can be at most one such mapping.)
     *
     * <p>
     *  返回此映射映射指定键的值。如果映射不包含此键的映射,则返回<tt> null </tt>。
     * 返回值<tt> null </tt>不一定表示该映射不包含键的映射;还有可能映射将键明确映射到<tt> null </tt>。 <tt> containsKey </tt>操作可用于区分这两种情况。
     * 
     * <p>更正式地说,如果此映射包含从键<tt> k </tt>到值<tt> v </tt>的映射,使得<tt>(key == null?k == null： key.equals(k))</tt>,则此方
     * 法返回<tt> v </tt>;否则返回<tt> null </tt>。
     *  (最多只能有一个这样的映射。)。
     * 
     * 
     * @param key key whose associated value is to be returned.
     * @return the value to which this map maps the specified key, or
     *         <tt>null</tt> if the map contains no mapping for this key.
     *
     * @throws NullPointerException if key is null
     * @throws ClassCastException if key is not String
     * @throws IllegalArgumentException if key is empty String
     */
    public Object get(Object key) {
        checkKey(key);
        return map.get(key);
    }

    /** {@inheritDoc} */
    public boolean isEmpty() {
        return map.isEmpty();
    }

    /** {@inheritDoc} */
    public Set<String> keySet() {
        return map.keySet();
    }

    /**
     * Removes the mapping for this key from this map if it is present
     * (optional operation).   More formally, if this map contains a mapping
     * from key <tt>k</tt> to value <tt>v</tt> such that
     * <code>(key==null ?  k==null : key.equals(k))</code>, that mapping
     * is removed.  (The map can contain at most one such mapping.)
     *
     * <p>Returns the value to which the map previously associated the key, or
     * <tt>null</tt> if the map contained no mapping for this key.  (A
     * <tt>null</tt> return can also indicate that the map previously
     * associated <tt>null</tt> with the specified key if the implementation
     * supports <tt>null</tt> values.)  The map will not contain a mapping for
     * the specified  key once the call returns.
     *
     * <p>
     *  如果此映射存在,则从此映射中删除此映射的映射(可选操作)。
     * 更正式地,如果该映射包含从键<tt> k </tt>到值<tt> v </tt>的映射,使得<code>(key == null?k == null：key.equals ))</code>,则删除该映
     * 射。
     *  如果此映射存在,则从此映射中删除此映射的映射(可选操作)。 (地图最多只能包含一个这样的映射。)。
     * 
     * 
     * @param key key whose mapping is to be removed from the map.
     * @return previous value associated with specified key, or <tt>null</tt>
     *         if there was no mapping for key.
     *
     * @throws NullPointerException if key is null
     * @throws ClassCastException if key is not String
     * @throws IllegalArgumentException if key is empty String
     */
    public Object remove(Object key) {
        checkKey(key);
        return map.remove(key);
    }

    /** {@inheritDoc} */
    public int size() {
        return map.size();
    }

    /** {@inheritDoc} */
    public Collection<Object> values() {
        return map.values();
    }

    private void checkKey(Object key) {
        if (key == null) {
            throw new NullPointerException("key can not be null");
        }
        if (!(key instanceof String)) {
            throw new ClassCastException("key should be a String");
        }
        if (key.equals("")) {
            throw new IllegalArgumentException("key can not be empty");
        }
    }
}
