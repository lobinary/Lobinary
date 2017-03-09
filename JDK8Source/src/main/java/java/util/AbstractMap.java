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
import java.util.Map.Entry;

/**
 * This class provides a skeletal implementation of the <tt>Map</tt>
 * interface, to minimize the effort required to implement this interface.
 *
 * <p>To implement an unmodifiable map, the programmer needs only to extend this
 * class and provide an implementation for the <tt>entrySet</tt> method, which
 * returns a set-view of the map's mappings.  Typically, the returned set
 * will, in turn, be implemented atop <tt>AbstractSet</tt>.  This set should
 * not support the <tt>add</tt> or <tt>remove</tt> methods, and its iterator
 * should not support the <tt>remove</tt> method.
 *
 * <p>To implement a modifiable map, the programmer must additionally override
 * this class's <tt>put</tt> method (which otherwise throws an
 * <tt>UnsupportedOperationException</tt>), and the iterator returned by
 * <tt>entrySet().iterator()</tt> must additionally implement its
 * <tt>remove</tt> method.
 *
 * <p>The programmer should generally provide a void (no argument) and map
 * constructor, as per the recommendation in the <tt>Map</tt> interface
 * specification.
 *
 * <p>The documentation for each non-abstract method in this class describes its
 * implementation in detail.  Each of these methods may be overridden if the
 * map being implemented admits a more efficient implementation.
 *
 * <p>This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * <p>
 *  此类提供了<tt> Map </tt>接口的骨架实现,以最小化实现此接口所需的工作量。
 * 
 *  <p>要实现不可修改的映射,程序员只需要扩展此类并为<tt> entrySet </tt>方法提供实现,该方法返回映射映射的集合视图。
 * 通常,返回的集合将依次在<tt> AbstractSet </tt>上实现。
 * 此集合不应支持<tt>添加</tt>或<tt>删除</tt>方法,并且其迭代器不应支持<tt> remove </tt>方法。
 * 
 *  <p>要实现可修改的映射,程序员必须另外覆盖此类的<tt> put </tt>方法(否则会抛出<tt> UnsupportedOperationException </tt>)和<tt> entryS
 * et ).iterator()</tt>必须另外实现其<tt> remove </tt>方法。
 * 
 *  <p>根据<tt> Map </tt>接口规范中的建议,程序员通常应提供一个void(无参数)和映射构造函数。
 * 
 *  <p>此类中每个非抽象方法的文档详细描述了它的实现。如果实现的映射承认更有效的实现,则这些方法中的每一个都可以被重写。
 * 
 *  <p>此类是的成员
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 *  Java集合框架</a>。
 * 
 * 
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 *
 * @author  Josh Bloch
 * @author  Neal Gafter
 * @see Map
 * @see Collection
 * @since 1.2
 */

public abstract class AbstractMap<K,V> implements Map<K,V> {
    /**
     * Sole constructor.  (For invocation by subclass constructors, typically
     * implicit.)
     * <p>
     * 唯一构造函数。 (对于子类构造函数的调用,通常是隐式的。)
     * 
     */
    protected AbstractMap() {
    }

    // Query Operations

    /**
     * {@inheritDoc}
     *
     * @implSpec
     * This implementation returns <tt>entrySet().size()</tt>.
     * <p>
     *  {@inheritDoc}
     * 
     *  @implSpec此实现返回<tt> entrySet()。size()</tt>。
     * 
     */
    public int size() {
        return entrySet().size();
    }

    /**
     * {@inheritDoc}
     *
     * @implSpec
     * This implementation returns <tt>size() == 0</tt>.
     * <p>
     *  {@inheritDoc}
     * 
     *  @implSpec此实现返回<tt> size()== 0 </tt>。
     * 
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * {@inheritDoc}
     *
     * @implSpec
     * This implementation iterates over <tt>entrySet()</tt> searching
     * for an entry with the specified value.  If such an entry is found,
     * <tt>true</tt> is returned.  If the iteration terminates without
     * finding such an entry, <tt>false</tt> is returned.  Note that this
     * implementation requires linear time in the size of the map.
     *
     * <p>
     *  {@inheritDoc}
     * 
     *  @implSpec此实现在<tt> entrySet()</tt>中迭代搜索具有指定值的条目。如果找到这样的条目,则返回<tt> true </tt>。
     * 如果迭代在没有找到这样的条目的情况下终止,则返回<tt> false </tt>。请注意,此实现需要地图大小的线性时间。
     * 
     * 
     * @throws ClassCastException   {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     */
    public boolean containsValue(Object value) {
        Iterator<Entry<K,V>> i = entrySet().iterator();
        if (value==null) {
            while (i.hasNext()) {
                Entry<K,V> e = i.next();
                if (e.getValue()==null)
                    return true;
            }
        } else {
            while (i.hasNext()) {
                Entry<K,V> e = i.next();
                if (value.equals(e.getValue()))
                    return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     *
     * @implSpec
     * This implementation iterates over <tt>entrySet()</tt> searching
     * for an entry with the specified key.  If such an entry is found,
     * <tt>true</tt> is returned.  If the iteration terminates without
     * finding such an entry, <tt>false</tt> is returned.  Note that this
     * implementation requires linear time in the size of the map; many
     * implementations will override this method.
     *
     * <p>
     *  {@inheritDoc}
     * 
     *  @implSpec此实现在<tt> entrySet()</tt>中搜索具有指定键的条目。如果找到这样的条目,则返回<tt> true </tt>。
     * 如果迭代在没有找到这样的条目的情况下终止,则返回<tt> false </tt>。注意,这个实现需要地图大小的线性时间;许多实现将覆盖此方法。
     * 
     * 
     * @throws ClassCastException   {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     */
    public boolean containsKey(Object key) {
        Iterator<Map.Entry<K,V>> i = entrySet().iterator();
        if (key==null) {
            while (i.hasNext()) {
                Entry<K,V> e = i.next();
                if (e.getKey()==null)
                    return true;
            }
        } else {
            while (i.hasNext()) {
                Entry<K,V> e = i.next();
                if (key.equals(e.getKey()))
                    return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     *
     * @implSpec
     * This implementation iterates over <tt>entrySet()</tt> searching
     * for an entry with the specified key.  If such an entry is found,
     * the entry's value is returned.  If the iteration terminates without
     * finding such an entry, <tt>null</tt> is returned.  Note that this
     * implementation requires linear time in the size of the map; many
     * implementations will override this method.
     *
     * <p>
     *  {@inheritDoc}
     * 
     *  @implSpec此实现在<tt> entrySet()</tt>中搜索具有指定键的条目。如果找到这样的条目,则返回条目的值。
     * 如果迭代在没有找到这样的条目的情况下终止,则返回<tt> null </tt>。注意,这个实现需要地图大小的线性时间;许多实现将覆盖此方法。
     * 
     * 
     * @throws ClassCastException            {@inheritDoc}
     * @throws NullPointerException          {@inheritDoc}
     */
    public V get(Object key) {
        Iterator<Entry<K,V>> i = entrySet().iterator();
        if (key==null) {
            while (i.hasNext()) {
                Entry<K,V> e = i.next();
                if (e.getKey()==null)
                    return e.getValue();
            }
        } else {
            while (i.hasNext()) {
                Entry<K,V> e = i.next();
                if (key.equals(e.getKey()))
                    return e.getValue();
            }
        }
        return null;
    }


    // Modification Operations

    /**
     * {@inheritDoc}
     *
     * @implSpec
     * This implementation always throws an
     * <tt>UnsupportedOperationException</tt>.
     *
     * <p>
     *  {@inheritDoc}
     * 
     *  @implSpec此实现总是引发一个<tt> UnsupportedOperationException </tt>。
     * 
     * 
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException            {@inheritDoc}
     * @throws NullPointerException          {@inheritDoc}
     * @throws IllegalArgumentException      {@inheritDoc}
     */
    public V put(K key, V value) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     *
     * @implSpec
     * This implementation iterates over <tt>entrySet()</tt> searching for an
     * entry with the specified key.  If such an entry is found, its value is
     * obtained with its <tt>getValue</tt> operation, the entry is removed
     * from the collection (and the backing map) with the iterator's
     * <tt>remove</tt> operation, and the saved value is returned.  If the
     * iteration terminates without finding such an entry, <tt>null</tt> is
     * returned.  Note that this implementation requires linear time in the
     * size of the map; many implementations will override this method.
     *
     * <p>Note that this implementation throws an
     * <tt>UnsupportedOperationException</tt> if the <tt>entrySet</tt>
     * iterator does not support the <tt>remove</tt> method and this map
     * contains a mapping for the specified key.
     *
     * <p>
     *  {@inheritDoc}
     * 
     * @implSpec此实现在<tt> entrySet()</tt>中搜索具有指定键的条目。
     * 如果找到这样的条目,则其值通过其<tt> getValue </tt>操作获得,该条目从迭代器的<tt>删除</tt>操作的集合(和后备映射)将返回保存的值。
     * 如果迭代在没有找到这样的条目的情况下终止,则返回<tt> null </tt>。注意,这个实现需要地图大小的线性时间;许多实现将覆盖此方法。
     * 
     *  <p>请注意,如果<tt> entrySet </tt>迭代器不支持<tt> remove </tt>方法,此实现会抛出<tt> UnsupportedOperationException </tt>
     * ,并且此映射包含指定键。
     * 
     * 
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException            {@inheritDoc}
     * @throws NullPointerException          {@inheritDoc}
     */
    public V remove(Object key) {
        Iterator<Entry<K,V>> i = entrySet().iterator();
        Entry<K,V> correctEntry = null;
        if (key==null) {
            while (correctEntry==null && i.hasNext()) {
                Entry<K,V> e = i.next();
                if (e.getKey()==null)
                    correctEntry = e;
            }
        } else {
            while (correctEntry==null && i.hasNext()) {
                Entry<K,V> e = i.next();
                if (key.equals(e.getKey()))
                    correctEntry = e;
            }
        }

        V oldValue = null;
        if (correctEntry !=null) {
            oldValue = correctEntry.getValue();
            i.remove();
        }
        return oldValue;
    }


    // Bulk Operations

    /**
     * {@inheritDoc}
     *
     * @implSpec
     * This implementation iterates over the specified map's
     * <tt>entrySet()</tt> collection, and calls this map's <tt>put</tt>
     * operation once for each entry returned by the iteration.
     *
     * <p>Note that this implementation throws an
     * <tt>UnsupportedOperationException</tt> if this map does not support
     * the <tt>put</tt> operation and the specified map is nonempty.
     *
     * <p>
     *  {@inheritDoc}
     * 
     *  @implSpec此实现循环遍历指定映射的<tt> entrySet()</tt>集合,并为迭代返回的每个条目调用此映射的<tt> put </tt>操作一次。
     * 
     *  <p>请注意,如果此地图不支持<tt> put </tt>操作,且指定的地图不为空,则此实现会抛出<tt> UnsupportedOperationException </tt>。
     * 
     * 
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException            {@inheritDoc}
     * @throws NullPointerException          {@inheritDoc}
     * @throws IllegalArgumentException      {@inheritDoc}
     */
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> e : m.entrySet())
            put(e.getKey(), e.getValue());
    }

    /**
     * {@inheritDoc}
     *
     * @implSpec
     * This implementation calls <tt>entrySet().clear()</tt>.
     *
     * <p>Note that this implementation throws an
     * <tt>UnsupportedOperationException</tt> if the <tt>entrySet</tt>
     * does not support the <tt>clear</tt> operation.
     *
     * <p>
     *  {@inheritDoc}
     * 
     *  @implSpec此实现调用<tt> entrySet()。clear()</tt>。
     * 
     *  <p>请注意,如果<tt> entrySet </tt>不支持<tt>清除</tt>操作,则此实现会抛出<tt> UnsupportedOperationException </tt>。
     * 
     * 
     * @throws UnsupportedOperationException {@inheritDoc}
     */
    public void clear() {
        entrySet().clear();
    }


    // Views

    /**
     * Each of these fields are initialized to contain an instance of the
     * appropriate view the first time this view is requested.  The views are
     * stateless, so there's no reason to create more than one of each.
     * <p>
     * 这些字段中的每一个都被初始化为包含第一次请求此视图的适当视图的实例。视图是无状态的,因此没有理由创建多个。
     * 
     */
    transient volatile Set<K>        keySet;
    transient volatile Collection<V> values;

    /**
     * {@inheritDoc}
     *
     * @implSpec
     * This implementation returns a set that subclasses {@link AbstractSet}.
     * The subclass's iterator method returns a "wrapper object" over this
     * map's <tt>entrySet()</tt> iterator.  The <tt>size</tt> method
     * delegates to this map's <tt>size</tt> method and the
     * <tt>contains</tt> method delegates to this map's
     * <tt>containsKey</tt> method.
     *
     * <p>The set is created the first time this method is called,
     * and returned in response to all subsequent calls.  No synchronization
     * is performed, so there is a slight chance that multiple calls to this
     * method will not all return the same set.
     * <p>
     *  {@inheritDoc}
     * 
     *  @implSpec这个实现返回一个子类{@link AbstractSet}的集合。子类的iterator方法在此映射的<tt> entrySet()</tt>迭代器上返回一个"包装器对象"。
     *  <tt> size </tt>方法委托给此地图的<tt> size </tt>方法,而<tt>包含</tt>方法委托给此地图的<tt> containsKey </tt>。
     * 
     *  <p>该集是在第一次调用此方法时创建的,并且作为对所有后续调用的响应而返回。不执行同步,因此有一个小的机会,多次调用此方法不会都返回相同的集合。
     * 
     */
    public Set<K> keySet() {
        if (keySet == null) {
            keySet = new AbstractSet<K>() {
                public Iterator<K> iterator() {
                    return new Iterator<K>() {
                        private Iterator<Entry<K,V>> i = entrySet().iterator();

                        public boolean hasNext() {
                            return i.hasNext();
                        }

                        public K next() {
                            return i.next().getKey();
                        }

                        public void remove() {
                            i.remove();
                        }
                    };
                }

                public int size() {
                    return AbstractMap.this.size();
                }

                public boolean isEmpty() {
                    return AbstractMap.this.isEmpty();
                }

                public void clear() {
                    AbstractMap.this.clear();
                }

                public boolean contains(Object k) {
                    return AbstractMap.this.containsKey(k);
                }
            };
        }
        return keySet;
    }

    /**
     * {@inheritDoc}
     *
     * @implSpec
     * This implementation returns a collection that subclasses {@link
     * AbstractCollection}.  The subclass's iterator method returns a
     * "wrapper object" over this map's <tt>entrySet()</tt> iterator.
     * The <tt>size</tt> method delegates to this map's <tt>size</tt>
     * method and the <tt>contains</tt> method delegates to this map's
     * <tt>containsValue</tt> method.
     *
     * <p>The collection is created the first time this method is called, and
     * returned in response to all subsequent calls.  No synchronization is
     * performed, so there is a slight chance that multiple calls to this
     * method will not all return the same collection.
     * <p>
     *  {@inheritDoc}
     * 
     *  @implSpec此实现返回一个子类{@link AbstractCollection}的集合。
     * 子类的iterator方法在此映射的<tt> entrySet()</tt>迭代器上返回一个"包装器对象"。
     *  <tt> size </tt>方法委托给此地图的<tt> size </tt>方法,而<tt>包含</tt>方法委托给此地图的<tt> containsValue </tt>。
     * 
     *  <p>该集合是在第一次调用此方法时创建的,并且作为对所有后续调用的响应而返回。不会执行同步,因此很有可能多次调用此方法不会都返回相同的集合。
     * 
     */
    public Collection<V> values() {
        if (values == null) {
            values = new AbstractCollection<V>() {
                public Iterator<V> iterator() {
                    return new Iterator<V>() {
                        private Iterator<Entry<K,V>> i = entrySet().iterator();

                        public boolean hasNext() {
                            return i.hasNext();
                        }

                        public V next() {
                            return i.next().getValue();
                        }

                        public void remove() {
                            i.remove();
                        }
                    };
                }

                public int size() {
                    return AbstractMap.this.size();
                }

                public boolean isEmpty() {
                    return AbstractMap.this.isEmpty();
                }

                public void clear() {
                    AbstractMap.this.clear();
                }

                public boolean contains(Object v) {
                    return AbstractMap.this.containsValue(v);
                }
            };
        }
        return values;
    }

    public abstract Set<Entry<K,V>> entrySet();


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
     * @implSpec
     * This implementation first checks if the specified object is this map;
     * if so it returns <tt>true</tt>.  Then, it checks if the specified
     * object is a map whose size is identical to the size of this map; if
     * not, it returns <tt>false</tt>.  If so, it iterates over this map's
     * <tt>entrySet</tt> collection, and checks that the specified map
     * contains each mapping that this map contains.  If the specified map
     * fails to contain such a mapping, <tt>false</tt> is returned.  If the
     * iteration completes, <tt>true</tt> is returned.
     *
     * <p>
     * 将指定的对象与此映射进行比较以实现相等性。如果给定对象也是一个映射,并且这两个映射表示相同的映射,则返回<tt> true </tt>。更正式地,如果<tt> m1.entrySet()。
     * equals(m2.entrySet())</tt>,则两个映射<tt> m1 </tt>和<tt> m2 </tt>这可以确保<tt>等于</tt>方法在<tt> Map </tt>界面的不同实现中正
     * 常工作。
     * 将指定的对象与此映射进行比较以实现相等性。如果给定对象也是一个映射,并且这两个映射表示相同的映射,则返回<tt> true </tt>。更正式地,如果<tt> m1.entrySet()。
     * 
     *  @implSpec这个实现首先检查指定的对象是否是这个映射;如果是,则返回<tt> true </tt>。
     * 然后,它检查指定的对象是否是大小与此映射的大小相同的映射;如果不是,则返回<tt> false </tt>。
     * 如果是,它会迭代此地图的<tt> entrySet </tt>集合,并检查指定的地图是否包含此地图包含的每个映射。如果指定的映射不能包含这样的映射,则返回<tt> false </tt>。
     * 如果迭代完成,则返回<tt> true </tt>。
     * 
     * 
     * @param o object to be compared for equality with this map
     * @return <tt>true</tt> if the specified object is equal to this map
     */
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof Map))
            return false;
        Map<?,?> m = (Map<?,?>) o;
        if (m.size() != size())
            return false;

        try {
            Iterator<Entry<K,V>> i = entrySet().iterator();
            while (i.hasNext()) {
                Entry<K,V> e = i.next();
                K key = e.getKey();
                V value = e.getValue();
                if (value == null) {
                    if (!(m.get(key)==null && m.containsKey(key)))
                        return false;
                } else {
                    if (!value.equals(m.get(key)))
                        return false;
                }
            }
        } catch (ClassCastException unused) {
            return false;
        } catch (NullPointerException unused) {
            return false;
        }

        return true;
    }

    /**
     * Returns the hash code value for this map.  The hash code of a map is
     * defined to be the sum of the hash codes of each entry in the map's
     * <tt>entrySet()</tt> view.  This ensures that <tt>m1.equals(m2)</tt>
     * implies that <tt>m1.hashCode()==m2.hashCode()</tt> for any two maps
     * <tt>m1</tt> and <tt>m2</tt>, as required by the general contract of
     * {@link Object#hashCode}.
     *
     * @implSpec
     * This implementation iterates over <tt>entrySet()</tt>, calling
     * {@link Map.Entry#hashCode hashCode()} on each element (entry) in the
     * set, and adding up the results.
     *
     * <p>
     *  返回此地图的哈希码值。地图的哈希码定义为地图的<tt> entrySet()</tt>视图中每个条目的哈希码的总和。
     * 这确保对于任何两个映射<tt> m1 </tt>,<tt> m1.equals(m2)</tt>意味着<tt> m1.hashCode()== m2.hashCode()</tt> <tt> m2 </tt>
     * ,根据{@link Object#hashCode}的一般合同的要求。
     *  返回此地图的哈希码值。地图的哈希码定义为地图的<tt> entrySet()</tt>视图中每个条目的哈希码的总和。
     * 
     *  @implSpec此实现在<tt> entrySet()</tt>中迭代,在集合中的每个元素(条目)上调用{@link Map.Entry#hashCode hashCode()},并将结果相加。
     * 
     * 
     * @return the hash code value for this map
     * @see Map.Entry#hashCode()
     * @see Object#equals(Object)
     * @see Set#equals(Object)
     */
    public int hashCode() {
        int h = 0;
        Iterator<Entry<K,V>> i = entrySet().iterator();
        while (i.hasNext())
            h += i.next().hashCode();
        return h;
    }

    /**
     * Returns a string representation of this map.  The string representation
     * consists of a list of key-value mappings in the order returned by the
     * map's <tt>entrySet</tt> view's iterator, enclosed in braces
     * (<tt>"{}"</tt>).  Adjacent mappings are separated by the characters
     * <tt>", "</tt> (comma and space).  Each key-value mapping is rendered as
     * the key followed by an equals sign (<tt>"="</tt>) followed by the
     * associated value.  Keys and values are converted to strings as by
     * {@link String#valueOf(Object)}.
     *
     * <p>
     * 返回此地图的字符串表示形式。字符串表示由按照地图的<tt> entrySet </tt>视图的迭代器返回的顺序列表(<tt>"{}"</tt>)中的键值映射列表组成。
     * 相邻的映射由字符<tt>","</tt>(逗号和空格)分隔。每个键值映射均呈现为键,后跟等号(<tt>"="</tt>),后跟相关值。
     * 键和值将通过{@link String#valueOf(Object)}转换为字符串。
     * 
     * 
     * @return a string representation of this map
     */
    public String toString() {
        Iterator<Entry<K,V>> i = entrySet().iterator();
        if (! i.hasNext())
            return "{}";

        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (;;) {
            Entry<K,V> e = i.next();
            K key = e.getKey();
            V value = e.getValue();
            sb.append(key   == this ? "(this Map)" : key);
            sb.append('=');
            sb.append(value == this ? "(this Map)" : value);
            if (! i.hasNext())
                return sb.append('}').toString();
            sb.append(',').append(' ');
        }
    }

    /**
     * Returns a shallow copy of this <tt>AbstractMap</tt> instance: the keys
     * and values themselves are not cloned.
     *
     * <p>
     *  返回此<tt> AbstractMap </tt>实例的浅表副本：键和值本身未克隆。
     * 
     * 
     * @return a shallow copy of this map
     */
    protected Object clone() throws CloneNotSupportedException {
        AbstractMap<?,?> result = (AbstractMap<?,?>)super.clone();
        result.keySet = null;
        result.values = null;
        return result;
    }

    /**
     * Utility method for SimpleEntry and SimpleImmutableEntry.
     * Test for equality, checking for nulls.
     *
     * NB: Do not replace with Object.equals until JDK-8015417 is resolved.
     * <p>
     *  SimpleEntry和SimpleImmutableEntry的实用方法。测试相等,检查null。
     * 
     *  NB：直到JDK-8015417解决,不要替换Object.equals。
     * 
     */
    private static boolean eq(Object o1, Object o2) {
        return o1 == null ? o2 == null : o1.equals(o2);
    }

    // Implementation Note: SimpleEntry and SimpleImmutableEntry
    // are distinct unrelated classes, even though they share
    // some code. Since you can't add or subtract final-ness
    // of a field in a subclass, they can't share representations,
    // and the amount of duplicated code is too small to warrant
    // exposing a common abstract class.


    /**
     * An Entry maintaining a key and a value.  The value may be
     * changed using the <tt>setValue</tt> method.  This class
     * facilitates the process of building custom map
     * implementations. For example, it may be convenient to return
     * arrays of <tt>SimpleEntry</tt> instances in method
     * <tt>Map.entrySet().toArray</tt>.
     *
     * <p>
     *  保持键和值的条目。可以使用<tt> setValue </tt>方法更改该值。此类有助于构建自定义地图实现的过程。例如,可以方便地返回方法<tt> Map.entrySet()。
     * toArray </tt>中的<tt> SimpleEntry </tt>实例的数组。
     * 
     * 
     * @since 1.6
     */
    public static class SimpleEntry<K,V>
        implements Entry<K,V>, java.io.Serializable
    {
        private static final long serialVersionUID = -8499721149061103585L;

        private final K key;
        private V value;

        /**
         * Creates an entry representing a mapping from the specified
         * key to the specified value.
         *
         * <p>
         *  创建表示从指定键到指定值的映射的条目。
         * 
         * 
         * @param key the key represented by this entry
         * @param value the value represented by this entry
         */
        public SimpleEntry(K key, V value) {
            this.key   = key;
            this.value = value;
        }

        /**
         * Creates an entry representing the same mapping as the
         * specified entry.
         *
         * <p>
         *  创建表示与指定条目相同的映射的条目。
         * 
         * 
         * @param entry the entry to copy
         */
        public SimpleEntry(Entry<? extends K, ? extends V> entry) {
            this.key   = entry.getKey();
            this.value = entry.getValue();
        }

        /**
         * Returns the key corresponding to this entry.
         *
         * <p>
         *  返回与此条目对应的键。
         * 
         * 
         * @return the key corresponding to this entry
         */
        public K getKey() {
            return key;
        }

        /**
         * Returns the value corresponding to this entry.
         *
         * <p>
         *  返回与此条目对应的值。
         * 
         * 
         * @return the value corresponding to this entry
         */
        public V getValue() {
            return value;
        }

        /**
         * Replaces the value corresponding to this entry with the specified
         * value.
         *
         * <p>
         *  将与此条目对应的值替换为指定的值。
         * 
         * 
         * @param value new value to be stored in this entry
         * @return the old value corresponding to the entry
         */
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        /**
         * Compares the specified object with this entry for equality.
         * Returns {@code true} if the given object is also a map entry and
         * the two entries represent the same mapping.  More formally, two
         * entries {@code e1} and {@code e2} represent the same mapping
         * if<pre>
         *   (e1.getKey()==null ?
         *    e2.getKey()==null :
         *    e1.getKey().equals(e2.getKey()))
         *   &amp;&amp;
         *   (e1.getValue()==null ?
         *    e2.getValue()==null :
         *    e1.getValue().equals(e2.getValue()))</pre>
         * This ensures that the {@code equals} method works properly across
         * different implementations of the {@code Map.Entry} interface.
         *
         * <p>
         * 将指定的对象与此条目进行比较以确保相等。如果给定对象也是一个映射条目,并且两个条目表示相同的映射,则返回{@code true}。
         * 更正式地,如果<pre>(e1.getKey()== null?e2.getKey()== null：e1.getKey()。
         * equals()方法,两个条目{@code e1}和{@code e2} (e2.getKey()))&amp;&amp; (e1.getValue()== null?e2.getValue()== n
         * ull：e1.getValue()。
         * 更正式地,如果<pre>(e1.getKey()== null?e2.getKey()== null：e1.getKey()。
         * equals(e2.getValue()))</pre>这可以确保{@code equals}跨越{@code Map.Entry}接口的不同实现。
         * 
         * 
         * @param o object to be compared for equality with this map entry
         * @return {@code true} if the specified object is equal to this map
         *         entry
         * @see    #hashCode
         */
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry))
                return false;
            Map.Entry<?,?> e = (Map.Entry<?,?>)o;
            return eq(key, e.getKey()) && eq(value, e.getValue());
        }

        /**
         * Returns the hash code value for this map entry.  The hash code
         * of a map entry {@code e} is defined to be: <pre>
         *   (e.getKey()==null   ? 0 : e.getKey().hashCode()) ^
         *   (e.getValue()==null ? 0 : e.getValue().hashCode())</pre>
         * This ensures that {@code e1.equals(e2)} implies that
         * {@code e1.hashCode()==e2.hashCode()} for any two Entries
         * {@code e1} and {@code e2}, as required by the general
         * contract of {@link Object#hashCode}.
         *
         * <p>
         *  返回此映射条目的哈希码值。映射项{@code e}的哈希码定义为：<pre>(e.getKey()== null?0：e.getKey()。
         * hashCode())^(e.getValue()= = null?0：e.getValue()。
         * hashCode())</pre>这确保{@code e1.equals(e2)}意味着{@code e1.hashCode()== e2.hashCode对于任何两个条目{@code e1}和{@code e2}
         * ,根据{@link Object#hashCode}的一般合同的要求。
         * hashCode())^(e.getValue()= = null?0：e.getValue()。
         * 
         * 
         * @return the hash code value for this map entry
         * @see    #equals
         */
        public int hashCode() {
            return (key   == null ? 0 :   key.hashCode()) ^
                   (value == null ? 0 : value.hashCode());
        }

        /**
         * Returns a String representation of this map entry.  This
         * implementation returns the string representation of this
         * entry's key followed by the equals character ("<tt>=</tt>")
         * followed by the string representation of this entry's value.
         *
         * <p>
         *  返回此地图条目的字符串表示形式。此实现返回此条目的键的字符串表示形式,后跟等号字符("<tt> = </tt>"),后跟此条目的值的字符串表示形式。
         * 
         * 
         * @return a String representation of this map entry
         */
        public String toString() {
            return key + "=" + value;
        }

    }

    /**
     * An Entry maintaining an immutable key and value.  This class
     * does not support method <tt>setValue</tt>.  This class may be
     * convenient in methods that return thread-safe snapshots of
     * key-value mappings.
     *
     * <p>
     *  一个条目维护一个不可变的键和值。此类不支持方法<tt> setValue </tt>。在返回键值映射的线程安全快照的方法中,此类可能很方便。
     * 
     * 
     * @since 1.6
     */
    public static class SimpleImmutableEntry<K,V>
        implements Entry<K,V>, java.io.Serializable
    {
        private static final long serialVersionUID = 7138329143949025153L;

        private final K key;
        private final V value;

        /**
         * Creates an entry representing a mapping from the specified
         * key to the specified value.
         *
         * <p>
         * 创建表示从指定键到指定值的映射的条目。
         * 
         * 
         * @param key the key represented by this entry
         * @param value the value represented by this entry
         */
        public SimpleImmutableEntry(K key, V value) {
            this.key   = key;
            this.value = value;
        }

        /**
         * Creates an entry representing the same mapping as the
         * specified entry.
         *
         * <p>
         *  创建表示与指定条目相同的映射的条目。
         * 
         * 
         * @param entry the entry to copy
         */
        public SimpleImmutableEntry(Entry<? extends K, ? extends V> entry) {
            this.key   = entry.getKey();
            this.value = entry.getValue();
        }

        /**
         * Returns the key corresponding to this entry.
         *
         * <p>
         *  返回与此条目对应的键。
         * 
         * 
         * @return the key corresponding to this entry
         */
        public K getKey() {
            return key;
        }

        /**
         * Returns the value corresponding to this entry.
         *
         * <p>
         *  返回与此条目对应的值。
         * 
         * 
         * @return the value corresponding to this entry
         */
        public V getValue() {
            return value;
        }

        /**
         * Replaces the value corresponding to this entry with the specified
         * value (optional operation).  This implementation simply throws
         * <tt>UnsupportedOperationException</tt>, as this class implements
         * an <i>immutable</i> map entry.
         *
         * <p>
         *  将与此条目对应的值替换为指定值(可选操作)。此实现只是抛出<tt> UnsupportedOperationException </tt>,因为此类实现了<i>不可变</i>映射条目。
         * 
         * 
         * @param value new value to be stored in this entry
         * @return (Does not return)
         * @throws UnsupportedOperationException always
         */
        public V setValue(V value) {
            throw new UnsupportedOperationException();
        }

        /**
         * Compares the specified object with this entry for equality.
         * Returns {@code true} if the given object is also a map entry and
         * the two entries represent the same mapping.  More formally, two
         * entries {@code e1} and {@code e2} represent the same mapping
         * if<pre>
         *   (e1.getKey()==null ?
         *    e2.getKey()==null :
         *    e1.getKey().equals(e2.getKey()))
         *   &amp;&amp;
         *   (e1.getValue()==null ?
         *    e2.getValue()==null :
         *    e1.getValue().equals(e2.getValue()))</pre>
         * This ensures that the {@code equals} method works properly across
         * different implementations of the {@code Map.Entry} interface.
         *
         * <p>
         *  将指定的对象与此条目进行比较以确保相等。如果给定对象也是一个映射条目,并且两个条目表示相同的映射,则返回{@code true}。
         * 更正式地,如果<pre>(e1.getKey()== null?e2.getKey()== null：e1.getKey()。
         * equals()方法,两个条目{@code e1}和{@code e2} (e2.getKey()))&amp;&amp; (e1.getValue()== null?e2.getValue()== n
         * ull：e1.getValue()。
         * 更正式地,如果<pre>(e1.getKey()== null?e2.getKey()== null：e1.getKey()。
         * equals(e2.getValue()))</pre>这可以确保{@code equals}跨越{@code Map.Entry}接口的不同实现。
         * 
         * 
         * @param o object to be compared for equality with this map entry
         * @return {@code true} if the specified object is equal to this map
         *         entry
         * @see    #hashCode
         */
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry))
                return false;
            Map.Entry<?,?> e = (Map.Entry<?,?>)o;
            return eq(key, e.getKey()) && eq(value, e.getValue());
        }

        /**
         * Returns the hash code value for this map entry.  The hash code
         * of a map entry {@code e} is defined to be: <pre>
         *   (e.getKey()==null   ? 0 : e.getKey().hashCode()) ^
         *   (e.getValue()==null ? 0 : e.getValue().hashCode())</pre>
         * This ensures that {@code e1.equals(e2)} implies that
         * {@code e1.hashCode()==e2.hashCode()} for any two Entries
         * {@code e1} and {@code e2}, as required by the general
         * contract of {@link Object#hashCode}.
         *
         * <p>
         *  返回此映射条目的哈希码值。映射项{@code e}的哈希码定义为：<pre>(e.getKey()== null?0：e.getKey()。
         * hashCode())^(e.getValue()= = null?0：e.getValue()。
         * hashCode())</pre>这确保{@code e1.equals(e2)}意味着{@code e1.hashCode()== e2.hashCode对于任何两个条目{@code e1}和{@code e2}
         * ,根据{@link Object#hashCode}的一般合同的要求。
         * hashCode())^(e.getValue()= = null?0：e.getValue()。
         * 
         * @return the hash code value for this map entry
         * @see    #equals
         */
        public int hashCode() {
            return (key   == null ? 0 :   key.hashCode()) ^
                   (value == null ? 0 : value.hashCode());
        }

        /**
         * Returns a String representation of this map entry.  This
         * implementation returns the string representation of this
         * entry's key followed by the equals character ("<tt>=</tt>")
         * followed by the string representation of this entry's value.
         *
         * <p>
         * 
         * 
         * @return a String representation of this map entry
         */
        public String toString() {
            return key + "=" + value;
        }

    }

}
