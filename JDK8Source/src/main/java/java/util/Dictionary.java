/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2004, Oracle and/or its affiliates. All rights reserved.
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
 * The <code>Dictionary</code> class is the abstract parent of any
 * class, such as <code>Hashtable</code>, which maps keys to values.
 * Every key and every value is an object. In any one <tt>Dictionary</tt>
 * object, every key is associated with at most one value. Given a
 * <tt>Dictionary</tt> and a key, the associated element can be looked up.
 * Any non-<code>null</code> object can be used as a key and as a value.
 * <p>
 * As a rule, the <code>equals</code> method should be used by
 * implementations of this class to decide if two keys are the same.
 * <p>
 * <strong>NOTE: This class is obsolete.  New implementations should
 * implement the Map interface, rather than extending this class.</strong>
 *
 * <p>
 *  <code> Dictionary </code>类是任何类的抽象父类,例如<code> Hashtable </code>,它将键映射到值。每个键和每个值都是一个对象。
 * 在任何一个<tt>字典</tt>对象中,每个键与最多一个值相关联。给定<tt>字典</tt>和键,可以查找关联的元素。任何非<code> null </code>对象都可以用作键和值。
 * <p>
 *  作为规则,应该由该类的实现使用<code> equals </code>方法来决定两个键是否相同。
 * <p>
 *  <strong>注意：此类已过时。新的实现应该实现Map接口,而不是扩展此类。</strong>
 * 
 * 
 * @author  unascribed
 * @see     java.util.Map
 * @see     java.lang.Object#equals(java.lang.Object)
 * @see     java.lang.Object#hashCode()
 * @see     java.util.Hashtable
 * @since   JDK1.0
 */
public abstract
class Dictionary<K,V> {
    /**
     * Sole constructor.  (For invocation by subclass constructors, typically
     * implicit.)
     * <p>
     *  唯一构造函数。 (对于子类构造函数的调用,通常是隐式的。)
     * 
     */
    public Dictionary() {
    }

    /**
     * Returns the number of entries (distinct keys) in this dictionary.
     *
     * <p>
     *  返回此字典中的条目数(不同键)。
     * 
     * 
     * @return  the number of keys in this dictionary.
     */
    abstract public int size();

    /**
     * Tests if this dictionary maps no keys to value. The general contract
     * for the <tt>isEmpty</tt> method is that the result is true if and only
     * if this dictionary contains no entries.
     *
     * <p>
     *  测试此字典是否将值映射到键值。 <tt> isEmpty </tt>方法的一般合同是,当且仅当此字典不包含任何条目时,结果为true。
     * 
     * 
     * @return  <code>true</code> if this dictionary maps no keys to values;
     *          <code>false</code> otherwise.
     */
    abstract public boolean isEmpty();

    /**
     * Returns an enumeration of the keys in this dictionary. The general
     * contract for the keys method is that an <tt>Enumeration</tt> object
     * is returned that will generate all the keys for which this dictionary
     * contains entries.
     *
     * <p>
     *  返回此词典中键的枚举。 keys方法的一般约定是返回一个<tt> Enumeration </tt>对象,将生成此字典包含条目的所有键。
     * 
     * 
     * @return  an enumeration of the keys in this dictionary.
     * @see     java.util.Dictionary#elements()
     * @see     java.util.Enumeration
     */
    abstract public Enumeration<K> keys();

    /**
     * Returns an enumeration of the values in this dictionary. The general
     * contract for the <tt>elements</tt> method is that an
     * <tt>Enumeration</tt> is returned that will generate all the elements
     * contained in entries in this dictionary.
     *
     * <p>
     * 返回此词典中的值的枚举。 <tt>元素</tt>方法的一般合同是返回<tt>枚举</tt>,它将生成此词典中条目中包含的所有元素。
     * 
     * 
     * @return  an enumeration of the values in this dictionary.
     * @see     java.util.Dictionary#keys()
     * @see     java.util.Enumeration
     */
    abstract public Enumeration<V> elements();

    /**
     * Returns the value to which the key is mapped in this dictionary.
     * The general contract for the <tt>isEmpty</tt> method is that if this
     * dictionary contains an entry for the specified key, the associated
     * value is returned; otherwise, <tt>null</tt> is returned.
     *
     * <p>
     *  返回键在此字典中映射到的值。 <tt> isEmpty </tt>方法的一般合同是,如果此字典包含指定键的条目,则返回关联的值;否则返回<tt> null </tt>。
     * 
     * 
     * @return  the value to which the key is mapped in this dictionary;
     * @param   key   a key in this dictionary.
     *          <code>null</code> if the key is not mapped to any value in
     *          this dictionary.
     * @exception NullPointerException if the <tt>key</tt> is <tt>null</tt>.
     * @see     java.util.Dictionary#put(java.lang.Object, java.lang.Object)
     */
    abstract public V get(Object key);

    /**
     * Maps the specified <code>key</code> to the specified
     * <code>value</code> in this dictionary. Neither the key nor the
     * value can be <code>null</code>.
     * <p>
     * If this dictionary already contains an entry for the specified
     * <tt>key</tt>, the value already in this dictionary for that
     * <tt>key</tt> is returned, after modifying the entry to contain the
     *  new element. <p>If this dictionary does not already have an entry
     *  for the specified <tt>key</tt>, an entry is created for the
     *  specified <tt>key</tt> and <tt>value</tt>, and <tt>null</tt> is
     *  returned.
     * <p>
     * The <code>value</code> can be retrieved by calling the
     * <code>get</code> method with a <code>key</code> that is equal to
     * the original <code>key</code>.
     *
     * <p>
     *  将指定的<code>键</code>映射到此字典中指定的<code>值</code>。键和值都不能为<code> null </code>。
     * <p>
     *  如果此字典已包含指定<tt>键</tt>的条目,则在修改条目以包含新元素后,会返回<tt>键</tt>的此字典中的值。
     *  <p>如果此字典没有指定<tt>键</tt>的条目,则会为指定的<tt>键</tt>和<tt>值</tt>创建一个条目, <tt> null </tt>。
     * <p>
     * 
     * @param      key     the hashtable key.
     * @param      value   the value.
     * @return     the previous value to which the <code>key</code> was mapped
     *             in this dictionary, or <code>null</code> if the key did not
     *             have a previous mapping.
     * @exception  NullPointerException  if the <code>key</code> or
     *               <code>value</code> is <code>null</code>.
     * @see        java.lang.Object#equals(java.lang.Object)
     * @see        java.util.Dictionary#get(java.lang.Object)
     */
    abstract public V put(K key, V value);

    /**
     * Removes the <code>key</code> (and its corresponding
     * <code>value</code>) from this dictionary. This method does nothing
     * if the <code>key</code> is not in this dictionary.
     *
     * <p>
     *  可以通过使用等于原始<code>键</code>的<code>键</code>调用<code> get </code>方法来检索<code> value </code>。
     * 
     * 
     * @param   key   the key that needs to be removed.
     * @return  the value to which the <code>key</code> had been mapped in this
     *          dictionary, or <code>null</code> if the key did not have a
     *          mapping.
     * @exception NullPointerException if <tt>key</tt> is <tt>null</tt>.
     */
    abstract public V remove(Object key);
}
