/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2001, 2002,2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有2001,2002,2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.util;


/**
 * This class is an unsynchronized hash table primary used for String
 * to Object mapping.
 * <p>
 * The hash code uses the same algorithm as SymbolTable class.
 *
 * <p>
 *  此类是用于字符串到对象映射的不同步哈希表主要。
 * <p>
 *  哈希码使用与SymbolTable类相同的算法。
 * 
 * 
 * @author Elena Litani
 * @version $Id: SymbolHash.java,v 1.7 2010-11-01 04:40:14 joehw Exp $
 */
public class SymbolHash {

    //
    // Constants
    //

    /** Default table size. */
    protected int fTableSize = 101;

    //
    // Data
    //

    /** Buckets. */
    protected Entry[] fBuckets;

    /** Number of elements. */
    protected int fNum = 0;

    //
    // Constructors
    //

    /** Constructs a key table with the default size. */
    public SymbolHash() {
        fBuckets = new Entry[fTableSize];
    }

    /**
     * Constructs a key table with a given size.
     *
     * <p>
     *  构造具有给定大小的键表。
     * 
     * 
     * @param size  the size of the key table.
     */
    public SymbolHash(int size) {
        fTableSize = size;
        fBuckets = new Entry[fTableSize];
    }

    //
    // Public methods
    //

    /**
     * Adds the key/value mapping to the key table. If the key already exists,
     * the previous value associated with this key is overwritten by the new
     * value.
     *
     * <p>
     *  将键/值映射添加到键表。如果键已经存在,则与此键相关联的先前值将被新值覆盖。
     * 
     * 
     * @param key
     * @param value
     */
    public void put(Object key, Object value) {
        int bucket = (key.hashCode() & 0x7FFFFFFF) % fTableSize;
        Entry entry = search(key, bucket);

        // replace old value
        if (entry != null) {
            entry.value = value;
        }
        // create new entry
        else {
            entry = new Entry(key, value, fBuckets[bucket]);
            fBuckets[bucket] = entry;
            fNum++;
        }
    }

    /**
     * Get the value associated with the given key.
     *
     * <p>
     *  获取与给定键相关联的值。
     * 
     * 
     * @param key
     * @return the value associated with the given key.
     */
    public Object get(Object key) {
        int bucket = (key.hashCode() & 0x7FFFFFFF) % fTableSize;
        Entry entry = search(key, bucket);
        if (entry != null) {
            return entry.value;
        }
        return null;
    }

    /**
     * Get the number of key/value pairs stored in this table.
     *
     * <p>
     *  获取存储在此表中的键/值对的数量。
     * 
     * 
     * @return the number of key/value pairs stored in this table.
     */
    public int getLength() {
        return fNum;
    }

    /**
     * Add all values to the given array. The array must have enough entry.
     *
     * <p>
     *  将所有值添加到给定数组。数组必须有足够的条目。
     * 
     * 
     * @param elements  the array to store the elements
     * @param from      where to start store element in the array
     * @return          number of elements copied to the array
     */
    public int getValues(Object[] elements, int from) {
        for (int i=0, j=0; i<fTableSize && j<fNum; i++) {
            for (Entry entry = fBuckets[i]; entry != null; entry = entry.next) {
                elements[from+j] = entry.value;
                j++;
            }
        }
        return fNum;
    }

    /**
     * Return key/value pairs of all entries in the map
     * <p>
     *  返回映射中所有条目的键/值对
     * 
     */
    public Object[] getEntries() {
        Object[] entries = new Object[fNum << 1];
        for (int i=0, j=0; i<fTableSize && j<fNum << 1; i++) {
            for (Entry entry = fBuckets[i]; entry != null; entry = entry.next) {
                entries[j] = entry.key;
                entries[++j] = entry.value;
                j++;
            }
        }
        return entries;
    }

    /**
     * Make a clone of this object.
     * <p>
     *  克隆此对象。
     * 
     */
    public SymbolHash makeClone() {
        SymbolHash newTable = new SymbolHash(fTableSize);
        newTable.fNum = fNum;
        for (int i = 0; i < fTableSize; i++) {
            if (fBuckets[i] != null)
                newTable.fBuckets[i] = fBuckets[i].makeClone();
        }
        return newTable;
    }

    /**
     * Remove all key/value assocaition. This tries to save a bit of GC'ing
     * by at least keeping the fBuckets array around.
     * <p>
     *  删除所有键/值关联。这试图通过至少保持fBuckets数组来节省一些GC'ing。
     * 
     */
    public void clear() {
        for (int i=0; i<fTableSize; i++) {
            fBuckets[i] = null;
        }
        fNum = 0;
    } // clear():  void

    protected Entry search(Object key, int bucket) {
        // search for identical key
        for (Entry entry = fBuckets[bucket]; entry != null; entry = entry.next) {
            if (key.equals(entry.key))
                return entry;
        }
        return null;
    }

    //
    // Classes
    //

    /**
     * This class is a key table entry. Each entry acts as a node
     * in a linked list.
     * <p>
     * 这个类是一个键表项。每个条目充当链接列表中的节点。
     */
    protected static final class Entry {
        // key/value
        public Object key;
        public Object value;
        /** The next entry. */
        public Entry next;

        public Entry() {
            key = null;
            value = null;
            next = null;
        }

        public Entry(Object key, Object value, Entry next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public Entry makeClone() {
            Entry entry = new Entry();
            entry.key = key;
            entry.value = value;
            if (next != null)
                entry.next = next.makeClone();
            return entry;
        }
    } // entry

} // class SymbolHash
