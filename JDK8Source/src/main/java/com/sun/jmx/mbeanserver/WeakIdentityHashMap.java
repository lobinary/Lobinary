/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2008, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.jmx.mbeanserver;

import static com.sun.jmx.mbeanserver.Util.*;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

import java.util.Map;


/**
 * <p>A map where keys are compared using identity comparison (like
 * IdentityHashMap) but where the presence of an object as a key in
 * the map does not prevent it being garbage collected (like
 * WeakHashMap).  This class does not implement the Map interface
 * because it is difficult to ensure correct semantics for iterators
 * over the entrySet().</p>
 *
 * <p>Because we do not implement Map, we do not copy the questionable
 * interface where you can call get(k) or remove(k) for any type of k,
 * which of course can only have an effect if k is of type K.</p>
 *
 * <p>This map does not support null keys.</p>
 * <p>
 *  <p>使用身份比较(如IdentityHashMap)比较键的映射,但是在映射中作为键的对象的存在不会阻止它被垃圾收集(如WeakHashMap)。
 * 这个类不实现Map接口,因为很难确保在entrySet()上的迭代器的正确语义。</p>。
 * 
 *  <p>因为我们不实现Map,所以我们不会复制可疑的接口,你可以为任何类型的k调用get(k)或remove(k),当然,只有当k是K类型。</p>
 * 
 *  <p>此地图不支持空键。</p>
 * 
 */
/*
 * The approach
 * is to wrap each key in a WeakReference and use the wrapped value as
 * a key in an ordinary HashMap.  The WeakReference has to be a
 * subclass IdentityWeakReference (IWR) where two IWRs are equal if
 * they refer to the same object.  This enables us to find the entry
 * again.
 * <p>
 *  方法是将每个键包含在WeakReference中,并将包装的值用作普通HashMap中的键。
 *  WeakReference必须是一个子类IdentityWeakReference(IWR),如果它们引用同一个对象,两个IWR是相等的。这使我们能够再次找到条目。
 * 
 */
class WeakIdentityHashMap<K, V> {
    private WeakIdentityHashMap() {}

    static <K, V> WeakIdentityHashMap<K, V> make() {
        return new WeakIdentityHashMap<K, V>();
    }

    V get(K key) {
        expunge();
        WeakReference<K> keyref = makeReference(key);
        return map.get(keyref);
    }

    public V put(K key, V value) {
        expunge();
        if (key == null)
            throw new IllegalArgumentException("Null key");
        WeakReference<K> keyref = makeReference(key, refQueue);
        return map.put(keyref, value);
    }

    public V remove(K key) {
        expunge();
        WeakReference<K> keyref = makeReference(key);
        return map.remove(keyref);
    }

    private void expunge() {
        Reference<? extends K> ref;
        while ((ref = refQueue.poll()) != null)
            map.remove(ref);
    }

    private WeakReference<K> makeReference(K referent) {
        return new IdentityWeakReference<K>(referent);
    }

    private WeakReference<K> makeReference(K referent, ReferenceQueue<K> q) {
        return new IdentityWeakReference<K>(referent, q);
    }

    /**
     * WeakReference where equals and hashCode are based on the
     * referent.  More precisely, two objects are equal if they are
     * identical or if they both have the same non-null referent.  The
     * hashCode is the value the original referent had.  Even if the
     * referent is cleared, the hashCode remains.  Thus, objects of
     * this class can be used as keys in hash-based maps and sets.
     * <p>
     *  WeakReference其中equals和hashCode基于引用。更准确地说,如果两个对象是相同的或者如果它们都具有相同的非空指示符,则它们是相等的。 hashCode是原始指示对象的值。
     * 即使指针被清除,hashCode仍然保留。因此,该类的对象可以用作基于散列的映射和集合中的键。
     */
    private static class IdentityWeakReference<T> extends WeakReference<T> {
        IdentityWeakReference(T o) {
            this(o, null);
        }

        IdentityWeakReference(T o, ReferenceQueue<T> q) {
            super(o, q);
            this.hashCode = (o == null) ? 0 : System.identityHashCode(o);
        }

        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof IdentityWeakReference<?>))
                return false;
            IdentityWeakReference<?> wr = (IdentityWeakReference<?>) o;
            Object got = get();
            return (got != null && got == wr.get());
        }

        public int hashCode() {
            return hashCode;
        }

        private final int hashCode;
    }

    private Map<WeakReference<K>, V> map = newMap();
    private ReferenceQueue<K> refQueue = new ReferenceQueue<K>();
}
