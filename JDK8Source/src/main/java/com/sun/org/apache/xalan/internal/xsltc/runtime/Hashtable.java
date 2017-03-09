/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2001-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有2001-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: Hashtable.java,v 1.2.4.1 2005/09/06 11:05:18 pvedula Exp $
 * <p>
 *  $ Id：Hashtable.java,v 1.2.4.1 2005/09/06 11:05:18 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.runtime;

import java.util.Enumeration;

/**
 * IMPORTANT NOTE:
 * This code was taken from Sun's Java1.1 JDK java.util.HashTable.java
 * All "synchronized" keywords and some methods we do not need have been
 * all been removed.
 * <p>
 *  重要说明：此代码取自Sun的Java1.1 JDK java.util.HashTable.java所有"synchronized"关键字和一些我们不需要的方法都已被删除。
 * 
 */

/**
 * Object that wraps entries in the hash-table
 * <p>
 *  将条目包装在哈希表中的对象
 * 
 * 
 * @author Morten Jorgensen
 */
class HashtableEntry {
    int hash;
    Object key;
    Object value;
    HashtableEntry next;

    protected Object clone() {
        HashtableEntry entry = new HashtableEntry();
        entry.hash = hash;
        entry.key = key;
        entry.value = value;
        entry.next = (next != null) ? (HashtableEntry)next.clone() : null;
        return entry;
    }
}

/**
 * The main hash-table implementation
 * <p>
 *  主哈希表实现
 * 
 */
public class Hashtable {

    private transient HashtableEntry table[]; // hash-table entries
    private transient int count;              // number of entries
    private int threshold;                    // current size of hash-tabke
    private float loadFactor;                 // load factor

    /**
     * Constructs a new, empty hashtable with the specified initial
     * capacity and the specified load factor.
     * <p>
     *  构造具有指定初始容量和指定负载因子的新的空白hashtable。
     * 
     */
    public Hashtable(int initialCapacity, float loadFactor) {
        if (initialCapacity <= 0) initialCapacity = 11;
        if (loadFactor <= 0.0) loadFactor = 0.75f;
        this.loadFactor = loadFactor;
        table = new HashtableEntry[initialCapacity];
        threshold = (int)(initialCapacity * loadFactor);
    }

    /**
     * Constructs a new, empty hashtable with the specified initial capacity
     * and default load factor.
     * <p>
     *  构造具有指定初始容量和默认负载因子的新的空白hashtable。
     * 
     */
    public Hashtable(int initialCapacity) {
        this(initialCapacity, 0.75f);
    }

    /**
     * Constructs a new, empty hashtable with a default capacity and load
     * factor.
     * <p>
     *  构造一个具有默认容量和负载因子的新的空白hashtable。
     * 
     */
    public Hashtable() {
        this(101, 0.75f);
    }

    /**
     * Returns the number of keys in this hashtable.
     * <p>
     *  返回此散列表中的键数。
     * 
     */
    public int size() {
        return count;
    }

    /**
     * Tests if this hashtable maps no keys to values.
     * <p>
     *  测试这个散列表是否将值映射到值。
     * 
     */
    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * Returns an enumeration of the keys in this hashtable.
     * <p>
     *  返回此散列表中的键的枚举。
     * 
     */
    public Enumeration keys() {
        return new HashtableEnumerator(table, true);
    }

    /**
     * Returns an enumeration of the values in this hashtable.
     * Use the Enumeration methods on the returned object to fetch the elements
     * sequentially.
     * <p>
     * 返回此散列表中的值的枚举。对返回的对象使用枚举方法以顺序获取元素。
     * 
     */
    public Enumeration elements() {
        return new HashtableEnumerator(table, false);
    }

    /**
     * Tests if some key maps into the specified value in this hashtable.
     * This operation is more expensive than the <code>containsKey</code>
     * method.
     * <p>
     *  测试某些键是否映射到此散列表中的指定值。此操作比<code> containsKey </code>方法更昂贵。
     * 
     */
    public boolean contains(Object value) {

        if (value == null) throw new NullPointerException();

        int i;
        HashtableEntry e;
        HashtableEntry tab[] = table;

        for (i = tab.length ; i-- > 0 ;) {
            for (e = tab[i] ; e != null ; e = e.next) {
                if (e.value.equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Tests if the specified object is a key in this hashtable.
     * <p>
     *  测试指定的对象是否是此散列表中的键。
     * 
     */
    public boolean containsKey(Object key) {
        HashtableEntry e;
        HashtableEntry tab[] = table;
        int hash = key.hashCode();
        int index = (hash & 0x7FFFFFFF) % tab.length;

        for (e = tab[index] ; e != null ; e = e.next)
            if ((e.hash == hash) && e.key.equals(key))
                return true;

        return false;
    }

    /**
     * Returns the value to which the specified key is mapped in this hashtable.
     * <p>
     *  返回此散列表中指定键映射到的值。
     * 
     */
    public Object get(Object key) {
        HashtableEntry e;
        HashtableEntry tab[] = table;
        int hash = key.hashCode();
        int index = (hash & 0x7FFFFFFF) % tab.length;

        for (e = tab[index] ; e != null ; e = e.next)
            if ((e.hash == hash) && e.key.equals(key))
                return e.value;

        return null;
    }

    /**
     * Rehashes the contents of the hashtable into a hashtable with a
     * larger capacity. This method is called automatically when the
     * number of keys in the hashtable exceeds this hashtable's capacity
     * and load factor.
     * <p>
     *  将散列表的内容重新散列为容量较大的散列表。当哈希表中的键数量超过此哈希表的容量和负载系数时,将自动调用此方法。
     * 
     */
    protected void rehash() {
        HashtableEntry e, old;
        int i, index;
        int oldCapacity = table.length;
        HashtableEntry oldTable[] = table;

        int newCapacity = oldCapacity * 2 + 1;
        HashtableEntry newTable[] = new HashtableEntry[newCapacity];

        threshold = (int)(newCapacity * loadFactor);
        table = newTable;

        for (i = oldCapacity ; i-- > 0 ;) {
            for (old = oldTable[i] ; old != null ; ) {
                e = old;
                old = old.next;
                index = (e.hash & 0x7FFFFFFF) % newCapacity;
                e.next = newTable[index];
                newTable[index] = e;
            }
        }
    }

    /**
     * Maps the specified <code>key</code> to the specified
     * <code>value</code> in this hashtable. Neither the key nor the
     * value can be <code>null</code>.
     * <p>
     * The value can be retrieved by calling the <code>get</code> method
     * with a key that is equal to the original key.
     * <p>
     *  将指定的<code>键</code>映射到此散列表中指定的<code>值</code>。键和值都不能为<code> null </code>。
     * <p>
     *  可以通过使用等于原键的键调用<code> get </code>方法来检索该值。
     * 
     */
    public Object put(Object key, Object value) {
        // Make sure the value is not null
        if (value == null) throw new NullPointerException();

        // Makes sure the key is not already in the hashtable.
        HashtableEntry e;
        HashtableEntry tab[] = table;
        int hash = key.hashCode();
        int index = (hash & 0x7FFFFFFF) % tab.length;

        for (e = tab[index] ; e != null ; e = e.next) {
            if ((e.hash == hash) && e.key.equals(key)) {
                Object old = e.value;
                e.value = value;
                return old;
            }
        }

        // Rehash the table if the threshold is exceeded
        if (count >= threshold) {
            rehash();
            return put(key, value);
        }

        // Creates the new entry.
        e = new HashtableEntry();
        e.hash = hash;
        e.key = key;
        e.value = value;
        e.next = tab[index];
        tab[index] = e;
        count++;
        return null;
    }

    /**
     * Removes the key (and its corresponding value) from this
     * hashtable. This method does nothing if the key is not in the hashtable.
     * <p>
     *  从此散列表中删除键(及其对应的值)。如果密钥不在散列表中,此方法不执行任何操作。
     * 
     */
    public Object remove(Object key) {
        HashtableEntry e, prev;
        HashtableEntry tab[] = table;
        int hash = key.hashCode();
        int index = (hash & 0x7FFFFFFF) % tab.length;
        for (e = tab[index], prev = null ; e != null ; prev = e, e = e.next) {
            if ((e.hash == hash) && e.key.equals(key)) {
                if (prev != null)
                    prev.next = e.next;
                else
                    tab[index] = e.next;
                count--;
                return e.value;
            }
        }
        return null;
    }

    /**
     * Clears this hashtable so that it contains no keys.
     * <p>
     *  清除此散列表,以使其不包含键。
     * 
     */
    public void clear() {
        HashtableEntry tab[] = table;
        for (int index = tab.length; --index >= 0; )
            tab[index] = null;
        count = 0;
    }

    /**
     * Returns a rather long string representation of this hashtable.
     * Handy for debugging - leave it here!!!
     * <p>
     *  返回此散列表的相当长的字符串表示形式。方便调试 - 留在这里！
     * 
     */
    public String toString() {
        int i;
        int max = size() - 1;
        StringBuffer buf = new StringBuffer();
        Enumeration k = keys();
        Enumeration e = elements();
        buf.append("{");

        for (i = 0; i <= max; i++) {
            String s1 = k.nextElement().toString();
            String s2 = e.nextElement().toString();
            buf.append(s1).append('=').append(s2);
            if (i < max) buf.append(", ");
        }
        buf.append("}");
        return buf.toString();
    }

    /**
     * A hashtable enumerator class.  This class should remain opaque
     * to the client. It will use the Enumeration interface.
     * <p>
     *  散列表枚举类。这个类应该对客户端保持不透明。它将使用Enumeration接口。
     */
    class HashtableEnumerator implements Enumeration {
        boolean keys;
        int index;
        HashtableEntry table[];
        HashtableEntry entry;

        HashtableEnumerator(HashtableEntry table[], boolean keys) {
            this.table = table;
            this.keys = keys;
            this.index = table.length;
        }

        public boolean hasMoreElements() {
            if (entry != null) {
                return true;
            }
            while (index-- > 0) {
                if ((entry = table[index]) != null) {
                    return true;
                }
            }
            return false;
        }

        public Object nextElement() {
            if (entry == null) {
                while ((index-- > 0) && ((entry = table[index]) == null));
            }
            if (entry != null) {
                HashtableEntry e = entry;
                entry = e.next;
                return keys ? e.key : e.value;
            }
            return null;
        }
    }

}
