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
import java.util.Set;
import java.util.Collection;

// jmx import
//


/**
 * The <tt>TabularData</tt> interface specifies the behavior of a specific type of complex <i>open data</i> objects
 * which represent <i>tabular data</i> structures.
 *
 * <p>
 *  <tt> TabularData </tt>接口指定表示表格数据</i>结构的特定类型的复杂<i>开放数据</i>对象的行为。
 * 
 * 
 * @since 1.5
 */
public interface TabularData /*extends Map*/ {


public interface TabularData /* <p>
public interface TabularData /* 
    /* *** TabularData specific information methods *** */


    /**
     * Returns the <i>tabular type</i> describing this
     * <tt>TabularData</tt> instance.
     *
     * <p>
     *  返回描述此<tt> TabularData </tt>实例的<i>表格类型</i>。
     * 
     * 
     * @return the tabular type.
     */
    public TabularType getTabularType();


    /**
     * Calculates the index that would be used in this <tt>TabularData</tt> instance to refer to the specified
     * composite data <var>value</var> parameter if it were added to this instance.
     * This method checks for the type validity of the specified <var>value</var>,
     * but does not check if the calculated index is already used to refer to a value in this <tt>TabularData</tt> instance.
     *
     * <p>
     *  计算要在此<tt> TabularData </tt>实例中使用的索引,以指向指定的复合数据<var> value </var>参数(如果已添加到此实例)。
     * 此方法检查指定<var> value </var>的类型有效性,但不检查计算的索引是否已用于引用此<tt> TabularData </tt>实例中的值。
     * 
     * 
     * @param  value                      the composite data value whose index in this
     *                                    <tt>TabularData</tt> instance is to be calculated;
     *                                    must be of the same composite type as this instance's row type;
     *                                    must not be null.
     *
     * @return the index that the specified <var>value</var> would have in this <tt>TabularData</tt> instance.
     *
     * @throws NullPointerException       if <var>value</var> is <tt>null</tt>
     *
     * @throws InvalidOpenTypeException   if <var>value</var> does not conform to this <tt>TabularData</tt> instance's
     *                                    row type definition.
     */
    public Object[] calculateIndex(CompositeData value) ;




    /* *** Content information query methods *** */

    /**
     * Returns the number of <tt>CompositeData</tt> values (ie the
     * number of rows) contained in this <tt>TabularData</tt>
     * instance.
     *
     * <p>
     *  返回此<tt> TabularData </tt>实例中包含的<tt> CompositeData </tt>值(即行数)的数量。
     * 
     * 
     * @return the number of values contained.
     */
    public int size() ;

    /**
     * Returns <tt>true</tt> if the number of <tt>CompositeData</tt>
     * values (ie the number of rows) contained in this
     * <tt>TabularData</tt> instance is zero.
     *
     * <p>
     *  如果此<tt> TabularData </tt>实例中包含的<tt> CompositeData </tt>值(即行数)为零,则返回<tt> true </tt>
     * 
     * 
     * @return true if this <tt>TabularData</tt> is empty.
     */
    public boolean isEmpty() ;

    /**
     * Returns <tt>true</tt> if and only if this <tt>TabularData</tt> instance contains a <tt>CompositeData</tt> value
     * (ie a row) whose index is the specified <var>key</var>. If <var>key</var> is <tt>null</tt> or does not conform to
     * this <tt>TabularData</tt> instance's <tt>TabularType</tt> definition, this method simply returns <tt>false</tt>.
     *
     * <p>
     *  当且仅当此<tt> TabularData </tt>实例包含其索引为指定<var> key </tt>的<tt> CompositeData </tt>值(即行)时,返回<tt> true </tt>
     *  var>。
     * 如果<var> key </var>是<tt> null </tt>或不符合此<tt> TabularData </tt>实例的<tt> TabularType </tt> false </tt>。
     * 
     * 
     * @param  key  the index value whose presence in this <tt>TabularData</tt> instance is to be tested.
     *
     * @return  <tt>true</tt> if this <tt>TabularData</tt> indexes a row value with the specified key.
     */
    public boolean containsKey(Object[] key) ;

    /**
     * Returns <tt>true</tt> if and only if this <tt>TabularData</tt> instance contains the specified
     * <tt>CompositeData</tt> value. If <var>value</var> is <tt>null</tt> or does not conform to
     * this <tt>TabularData</tt> instance's row type definition, this method simply returns <tt>false</tt>.
     *
     * <p>
     * 当且仅当此<tt> TabularData </tt>实例包含指定的<tt> CompositeData </tt>值时,返回<tt> true </tt>。
     * 如果<var> value </var>为<tt> null </tt>或不符合此<tt> TabularData </tt>实例的行类型定义,此方法只返回<tt> false </tt>。
     * 
     * 
     * @param  value  the row value whose presence in this <tt>TabularData</tt> instance is to be tested.
     *
     * @return  <tt>true</tt> if this <tt>TabularData</tt> instance contains the specified row value.
     */
    public boolean containsValue(CompositeData value) ;

    /**
     * Returns the <tt>CompositeData</tt> value whose index is
     * <var>key</var>, or <tt>null</tt> if there is no value mapping
     * to <var>key</var>, in this <tt>TabularData</tt> instance.
     *
     * <p>
     *  如果没有值映射到<var> key </var>,则返回其索引为<var> key </var>或<tt> null </tt>的<tt> CompositeData </tt> <tt> Tabul
     * arData </tt>实例。
     * 
     * 
     * @param key the key of the row to return.
     *
     * @return the value corresponding to <var>key</var>.
     *
     * @throws NullPointerException if the <var>key</var> is
     * <tt>null</tt>
     * @throws InvalidKeyException if the <var>key</var> does not
     * conform to this <tt>TabularData</tt> instance's *
     * <tt>TabularType</tt> definition
     */
    public CompositeData get(Object[] key) ;




    /* *** Content modification operations (one element at a time) *** */


    /**
     * Adds <var>value</var> to this <tt>TabularData</tt> instance.
     * The composite type of <var>value</var> must be the same as this
     * instance's row type (ie the composite type returned by
     * <tt>this.getTabularType().{@link TabularType#getRowType
     * getRowType()}</tt>), and there must not already be an existing
     * value in this <tt>TabularData</tt> instance whose index is the
     * same as the one calculated for the <var>value</var> to be
     * added. The index for <var>value</var> is calculated according
     * to this <tt>TabularData</tt> instance's <tt>TabularType</tt>
     * definition (see <tt>TabularType.{@link
     * TabularType#getIndexNames getIndexNames()}</tt>).
     *
     * <p>
     *  向此<tt> TabularData </tt>实例添加<var> value </var>。
     *  <var> value </var>的复合类型必须与此实例的行类型(即<tt> this.getTabularType()返回的复合类型相同){@ link TabularType#getRowType getRowType()}
     *  </tt >),且此<tt> TabularData </tt>实例中必须不存在现有值,该实例的索引与为要添加的<var> value </var>计算的值相同。
     *  向此<tt> TabularData </tt>实例添加<var> value </var>。
     *  <var> value </var>的索引是根据此<tt> TabularData </tt>实例的<tt> TabularType </tt>定义计算的(参见<tt> TabularType。
     * {@ link TabularType#getIndexNames getIndexNames } </tt>)。
     * 
     * 
     * @param  value                      the composite data value to be added as a new row to this <tt>TabularData</tt> instance;
     *                                    must be of the same composite type as this instance's row type;
     *                                    must not be null.
     *
     * @throws NullPointerException       if <var>value</var> is <tt>null</tt>
     * @throws InvalidOpenTypeException   if <var>value</var> does not conform to this <tt>TabularData</tt> instance's
     *                                    row type definition.
     * @throws KeyAlreadyExistsException  if the index for <var>value</var>, calculated according to
     *                                    this <tt>TabularData</tt> instance's <tt>TabularType</tt> definition
     *                                    already maps to an existing value in the underlying HashMap.
     */
    public void put(CompositeData value) ;

    /**
     * Removes the <tt>CompositeData</tt> value whose index is <var>key</var> from this <tt>TabularData</tt> instance,
     * and returns the removed value, or returns <tt>null</tt> if there is no value whose index is <var>key</var>.
     *
     * <p>
     *  从此<tt> TabularData </tt>实例中删除其索引为<var> key </var>的<tt> CompositeData </tt>值,并返回<tt> null </tt>如果没有其索
     * 引为<var> key </var>的值。
     * 
     * 
     * @param  key  the index of the value to get in this <tt>TabularData</tt> instance;
     *              must be valid with this <tt>TabularData</tt> instance's row type definition;
     *              must not be null.
     *
     * @return previous value associated with specified key, or <tt>null</tt>
     *         if there was no mapping for key.
     *
     * @throws NullPointerException  if the <var>key</var> is <tt>null</tt>
     * @throws InvalidKeyException   if the <var>key</var> does not conform to this <tt>TabularData</tt> instance's
     *                               <tt>TabularType</tt> definition
     */
    public CompositeData remove(Object[] key) ;




    /* ***   Content modification bulk operations   *** */


    /**
     * Add all the elements in <var>values</var> to this <tt>TabularData</tt> instance.
     * If any  element in <var>values</var> does not satisfy the constraints defined in {@link #put(CompositeData) <tt>put</tt>},
     * or if any two elements in <var>values</var> have the same index calculated according to this <tt>TabularData</tt>
     * instance's <tt>TabularType</tt> definition, then an exception describing the failure is thrown
     * and no element of <var>values</var> is added,  thus leaving this <tt>TabularData</tt> instance unchanged.
     *
     * <p>
     * 将<var> values </var>中的所有元素添加到此<tt> TabularData </tt>实例。
     * 如果<var> values </var>中的任何元素不满足{@link #put(CompositeData)<tt> put </tt>}中定义的约束,或者<var> values </var >具
     * 有根据此<tt> TabularData </tt>实例的<tt> TabularType </tt>定义计算的相同索引,则抛出描述失败的异常,并且不添加任何<var>值</var>的元素,因此使此<tt>
     *  TabularData </tt>实例保持不变。
     * 将<var> values </var>中的所有元素添加到此<tt> TabularData </tt>实例。
     * 
     * 
     * @param  values  the array of composite data values to be added as new rows to this <tt>TabularData</tt> instance;
     *                 if <var>values</var> is <tt>null</tt> or empty, this method returns without doing anything.
     *
     * @throws NullPointerException       if an element of <var>values</var> is <tt>null</tt>
     * @throws InvalidOpenTypeException   if an element of <var>values</var> does not conform to
     *                                    this <tt>TabularData</tt> instance's row type definition
     * @throws KeyAlreadyExistsException  if the index for an element of <var>values</var>, calculated according to
     *                                    this <tt>TabularData</tt> instance's <tt>TabularType</tt> definition
     *                                    already maps to an existing value in this instance,
     *                                    or two elements of <var>values</var> have the same index.
     */
    public void putAll(CompositeData[] values) ;

    /**
     * Removes all <tt>CompositeData</tt> values (ie rows) from this <tt>TabularData</tt> instance.
     * <p>
     *  从此<tt> TabularData </tt>实例中移除所有<tt> CompositeData </tt>值(即行)。
     * 
     */
    public void clear();




    /* ***   Collection views of the keys and values   *** */


    /**
     * Returns a set view of the keys (ie the index values) of the
     * {@code CompositeData} values (ie the rows) contained in this
     * {@code TabularData} instance. The returned {@code Set} is a
     * {@code Set<List<?>>} but is declared as a {@code Set<?>} for
     * compatibility reasons. The returned set can be used to iterate
     * over the keys.
     *
     * <p>
     *  返回此{@code TabularData}实例中包含的{@code CompositeData}值(即行)的键(即索引值)的集视图。
     * 返回的{@code Set}是一个{@code Set <List <?>>},但出于兼容性原因被声明为{@code Set <?>}。返回的集合可以用于遍历键。
     * 
     * 
     * @return a set view ({@code Set<List<?>>}) of the index values
     * used in this {@code TabularData} instance.
     */
    public Set<?> keySet();

    /**
     * Returns a collection view of the {@code CompositeData} values
     * (ie the rows) contained in this {@code TabularData} instance.
     * The returned {@code Collection} is a {@code Collection<CompositeData>}
     * but is declared as a {@code Collection<?>} for compatibility reasons.
     * The returned collection can be used to iterate over the values.
     *
     * <p>
     *  返回此{@code TabularData}实例中包含的{@code CompositeData}值(即行)的集合视图。
     * 返回的{@code Collection}是一个{@code Collection <CompositeData>},但出于兼容性原因被声明为{@code Collection <?>}。
     * 返回的集合可以用于对值进行迭代。
     * 
     * 
     * @return a collection view ({@code Collection<CompositeData>})
     * of the rows contained in this {@code TabularData} instance.
     */
    public Collection<?> values();




    /* ***  Commodity methods from java.lang.Object  *** */


    /**
     * Compares the specified <var>obj</var> parameter with this <code>TabularData</code> instance for equality.
     * <p>
     * Returns <tt>true</tt> if and only if all of the following statements are true:
     * <ul>
     * <li><var>obj</var> is non null,</li>
     * <li><var>obj</var> also implements the <code>TabularData</code> interface,</li>
     * <li>their row types are equal</li>
     * <li>their contents (ie index to value mappings) are equal</li>
     * </ul>
     * This ensures that this <tt>equals</tt> method works properly for <var>obj</var> parameters which are
     * different implementations of the <code>TabularData</code> interface.
     * <br>&nbsp;
     * <p>
     *  将指定的<var> obj </var>参数与此<code> TabularData </code>实例进行比较以确保相等。
     * <p>
     *  当且仅当所有以下语句都为真时返回<tt> true </tt>：
     * <ul>
     * <li> <var> obj </var>不为空,</li> <li> <var> obj </var>也实现<code> TabularData </code>它们的行类型相等</li> <li>它们
     * 的内容(即索引到值映射)相等</li>。
     * </ul>
     *  这可以确保<tt> equals </tt>方法对<var> obj </var>参数正常工作,这些参数是<code> TabularData </code>接口的不同实现。 <br>&nbsp;
     * 
     * 
     * @param  obj  the object to be compared for equality with this <code>TabularData</code> instance;
     *
     * @return  <code>true</code> if the specified object is equal to this <code>TabularData</code> instance.
     */
    public boolean equals(Object obj);

    /**
     * Returns the hash code value for this <code>TabularData</code> instance.
     * <p>
     * The hash code of a <code>TabularData</code> instance is the sum of the hash codes
     * of all elements of information used in <code>equals</code> comparisons
     * (ie: its <i>tabular type</i> and its content, where the content is defined as all the index to value mappings).
     * <p>
     * This ensures that <code> t1.equals(t2) </code> implies that <code> t1.hashCode()==t2.hashCode() </code>
     * for any two <code>TabularDataSupport</code> instances <code>t1</code> and <code>t2</code>,
     * as required by the general contract of the method
     * {@link Object#hashCode() Object.hashCode()}.
     *
     * <p>
     *  返回此<c> </cf> TabularData </code>实例的哈希码值。
     * <p>
     *  <code> TabularData </code>实例的哈希码是<code> equals </code>比较中使用的所有信息元素的哈希码的总和(即：其<i>表格类型</i>及其内容,其中内容被定义
     * 为所有索引到值映射)。
     * <p>
     *  这确保<code> t1.equals(t2)</code>意味着任何两个<code> TabularDataSupport </code>实例的<code> t1.hashCode()== t2.h
     * ashCode()</代码> t1 </code>和<code> t2 </code>,这是方法{@link Object#hashCode()Object.hashCode()}的一般合同的要求。
     * 
     * @return  the hash code value for this <code>TabularDataSupport</code> instance
     */
    public int hashCode();

    /**
     * Returns a string representation of this <code>TabularData</code> instance.
     * <p>
     * The string representation consists of the name of the implementing class,
     * and the tabular type of this instance.
     *
     * <p>
     * 
     * 
     * @return  a string representation of this <code>TabularData</code> instance
     */
    public String toString();

}
