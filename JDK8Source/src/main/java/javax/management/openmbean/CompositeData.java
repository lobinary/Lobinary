/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2007, Oracle and/or its affiliates. All rights reserved.
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


package javax.management.openmbean;


// java import
//
import java.util.Collection;

// jmx import
//


/**
 * The <tt>CompositeData</tt> interface specifies the behavior of a specific type of complex <i>open data</i> objects
 * which represent <i>composite data</i> structures.
 *
 *
 * <p>
 *  <tt> CompositeData </tt>接口指定表示复合数据</i>结构的特定类型的复杂<i>开放数据</i>对象的行为。
 * 
 * 
 * @since 1.5
 */
public interface CompositeData {


    /**
     * Returns the <i>composite type </i> of this <i>composite data</i> instance.
     *
     * <p>
     *  返回此<i>复合数据</i>实例的<i>复合类型</i>。
     * 
     * 
     * @return the type of this CompositeData.
     */
    public CompositeType getCompositeType();

    /**
     * Returns the value of the item whose name is <tt>key</tt>.
     *
     * <p>
     *  返回名称为<tt>键</tt>的项目的值。
     * 
     * 
     * @param key the name of the item.
     *
     * @return the value associated with this key.
     *
     * @throws IllegalArgumentException  if <tt>key</tt> is a null or empty String.
     *
     * @throws InvalidKeyException  if <tt>key</tt> is not an existing item name for this <tt>CompositeData</tt> instance.
     */
    public Object get(String key) ;

    /**
     * Returns an array of the values of the items whose names are specified by <tt>keys</tt>, in the same order as <tt>keys</tt>.
     *
     * <p>
     *  以与<tt>键</tt>相同的顺序返回其名称由<tt>键</tt>指定的项目的值的数组。
     * 
     * 
     * @param keys the names of the items.
     *
     * @return the values corresponding to the keys.
     *
     * @throws IllegalArgumentException  if an element in <tt>keys</tt> is a null or empty String.
     *
     * @throws InvalidKeyException  if an element in <tt>keys</tt> is not an existing item name for this <tt>CompositeData</tt> instance.
     */
    public Object[] getAll(String[] keys) ;

    /**
     * Returns <tt>true</tt> if and only if this <tt>CompositeData</tt> instance contains
     * an item whose name is <tt>key</tt>.
     * If <tt>key</tt> is a null or empty String, this method simply returns false.
     *
     * <p>
     *  当且仅当此<tt> CompositeData </tt>实例包含名称为<tt>键</tt>的项目时,返回<tt> true </tt>。
     * 如果<tt>键</tt>是空或空字符串,此方法只返回false。
     * 
     * 
     * @param key the key to be tested.
     *
     * @return true if this <tt>CompositeData</tt> contains the key.
     */
    public boolean containsKey(String key) ;

    /**
     * Returns <tt>true</tt> if and only if this <tt>CompositeData</tt> instance contains an item
     * whose value is <tt>value</tt>.
     *
     * <p>
     *  当且仅当此<tt> CompositeData </tt>实例包含值为<tt> value </tt>的项目时,返回<tt> true </tt>。
     * 
     * 
     * @param value the value to be tested.
     *
     * @return true if this <tt>CompositeData</tt> contains the value.
     */
    public boolean containsValue(Object value) ;

    /**
     * Returns an unmodifiable Collection view of the item values contained in this <tt>CompositeData</tt> instance.
     * The returned collection's iterator will return the values in the ascending lexicographic order of the corresponding
     * item names.
     *
     * <p>
     *  返回此<tt> CompositeData </tt>实例中包含的项值的不可修改的Collection视图。返回的集合的迭代器将以相应项目名称的升序字典顺序返回值。
     * 
     * 
     * @return the values.
     */
    public Collection<?> values() ;

    /**
     * Compares the specified <var>obj</var> parameter with this
     * <code>CompositeData</code> instance for equality.
     * <p>
     * Returns <tt>true</tt> if and only if all of the following statements are true:
     * <ul>
     * <li><var>obj</var> is non null,</li>
     * <li><var>obj</var> also implements the <code>CompositeData</code> interface,</li>
     * <li>their composite types are equal</li>
     * <li>their contents, i.e. (name, value) pairs are equal. If a value contained in
     * the content is an array, the value comparison is done as if by calling
     * the {@link java.util.Arrays#deepEquals(Object[], Object[]) deepEquals} method
     * for arrays of object reference types or the appropriate overloading of
     * {@code Arrays.equals(e1,e2)} for arrays of primitive types</li>
     * </ul>
     * <p>
     * This ensures that this <tt>equals</tt> method works properly for
     * <var>obj</var> parameters which are different implementations of the
     * <code>CompositeData</code> interface, with the restrictions mentioned in the
     * {@link java.util.Collection#equals(Object) equals}
     * method of the <tt>java.util.Collection</tt> interface.
     *
     * <p>
     *  将指定的<var> obj </var>参数与此<code> CompositeData </code>实例进行比较以确保相等。
     * <p>
     *  当且仅当所有以下语句都为真时返回<tt> true </tt>：
     * <ul>
     * <li> <var> obj </var>不为空,</li> <li> <var> obj </var>也实现<code> CompositeData </code>它们的复合类型是相等的</li> <li>
     * 它们的内容,即(名称,值)对是相等的。
     * 如果内容中包含的值是数组,则值比较是通过为对象引用类型数组调用{@link java.util.Arrays#deepEquals(Object [],Object [])deepEquals}对原始类
     * 型数组{@code Arrays.equals(e1,e2)}的适当重载</li>。
     * </ul>
     * <p>
     *  这可以确保<tt>等于</tt>方法适用于<var> obj </var>参数,这是<code> CompositeData </code>接口的不同实现,具有{@link java.util.Collection#equals(Object)equals}
     * 方法的<tt> java.util.Collection </tt>接口。
     * 
     * 
     * @param  obj  the object to be compared for equality with this
     * <code>CompositeData</code> instance.
     * @return  <code>true</code> if the specified object is equal to this
     * <code>CompositeData</code> instance.
     */
    public boolean equals(Object obj) ;

    /**
     * Returns the hash code value for this <code>CompositeData</code> instance.
     * <p>
     * The hash code of a <code>CompositeData</code> instance is the sum of the hash codes
     * of all elements of information used in <code>equals</code> comparisons
     * (ie: its <i>composite type</i> and all the item values).
     * <p>
     * This ensures that <code> t1.equals(t2) </code> implies that <code> t1.hashCode()==t2.hashCode() </code>
     * for any two <code>CompositeData</code> instances <code>t1</code> and <code>t2</code>,
     * as required by the general contract of the method
     * {@link Object#hashCode() Object.hashCode()}.
     * <p>
     * Each item value's hash code is added to the returned hash code.
     * If an item value is an array,
     * its hash code is obtained as if by calling the
     * {@link java.util.Arrays#deepHashCode(Object[]) deepHashCode} method
     * for arrays of object reference types or the appropriate overloading
     * of {@code Arrays.hashCode(e)} for arrays of primitive types.
     *
     * <p>
     *  返回此<> CompositeData </code>实例的哈希码值。
     * <p>
     *  <code> CompositeData </code>实例的哈希码是<code> equals </code>比较中使用的所有信息元素的哈希码的总和(即：其<i>复合类型</i>和所有项目值)。
     * <p>
     *  这确保<code> t1.equals(t2)</code>意味着任何两个<code> CompositeData </code>实例的<code> t1.hashCode()== t2.hashCo
     * de()</代码> t1 </code>和<code> t2 </code>,这是方法{@link Object#hashCode()Object.hashCode()}的一般合同的要求。
     * <p>
     * 每个项的值的哈希码被添加到返回的哈希码。
     * 如果一个项目值是一个数组,它的哈希码是通过调用对象引用类型数组的{@link java.util.Arrays#deepHashCode(Object [])deepHashCode}方法或{@code Arrays.hashCode(e)}
     * 
     * @return the hash code value for this <code>CompositeData</code> instance
     */
    public int hashCode() ;

    /**
     * Returns a string representation of this <code>CompositeData</code> instance.
     * <p>
     * The string representation consists of the name of the implementing class,
     * the string representation of the composite type of this instance, and the string representation of the contents
     * (ie list the itemName=itemValue mappings).
     *
     * <p>
     * 。
     * 每个项的值的哈希码被添加到返回的哈希码。
     * 
     * 
     * @return  a string representation of this <code>CompositeData</code> instance
     */
    public String toString() ;

}
