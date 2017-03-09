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


// jmx import
//


/**
 * <p>Describes an attribute of an open MBean.</p>
 *
 * <p>This interface declares the same methods as the class {@link
 * javax.management.MBeanAttributeInfo}.  A class implementing this
 * interface (typically {@link OpenMBeanAttributeInfoSupport}) should
 * extend {@link javax.management.MBeanAttributeInfo}.</p>
 *
 *
 * <p>
 *  <p>描述打开的MBean的属性。</p>
 * 
 *  <p>此接口声明与类{@link javax.management.MBeanAttributeInfo}相同的方法。
 * 实现此接口的类(通常为{@link OpenMBeanAttributeInfoSupport})应该扩展{@link javax.management.MBeanAttributeInfo}。
 * </p>。
 * 
 * 
 * @since 1.5
 */
public interface OpenMBeanAttributeInfo extends OpenMBeanParameterInfo {


    // Re-declares the methods that are in class MBeanAttributeInfo of JMX 1.0
    // (these will be removed when MBeanAttributeInfo is made a parent interface of this interface)

    /**
     * Returns <tt>true</tt> if the attribute described by this <tt>OpenMBeanAttributeInfo</tt> instance is readable,
     * <tt>false</tt> otherwise.
     *
     * <p>
     *  如果此<tt> OpenMBeanAttributeInfo </tt>实例描述的属性可读,则返回<tt> true </tt>,否则返回<tt> false </tt>。
     * 
     * 
     * @return true if the attribute is readable.
     */
    public boolean isReadable() ;

    /**
     * Returns <tt>true</tt> if the attribute described by this <tt>OpenMBeanAttributeInfo</tt> instance is writable,
     * <tt>false</tt> otherwise.
     *
     * <p>
     *  如果此<tt> OpenMBeanAttributeInfo </tt>实例描述的属性可写,则返回<tt> true </tt>,否则返回<tt> false </tt>。
     * 
     * 
     * @return true if the attribute is writable.
     */
    public boolean isWritable() ;

    /**
     * Returns <tt>true</tt> if the attribute described by this <tt>OpenMBeanAttributeInfo</tt> instance
     * is accessed through a <tt>is<i>XXX</i></tt> getter (applies only to <tt>boolean</tt> and <tt>Boolean</tt> values),
     * <tt>false</tt> otherwise.
     *
     * <p>
     *  如果通过<tt>是<i> XXX </i> </tt> getter访问此<tt> OpenMBeanAttributeInfo </tt>实例描述的属性,则返回<tt> true </tt> tt>
     *  boolean </tt>和<tt> Boolean </tt>值),<tt> false </tt>。
     * 
     * 
     * @return true if the attribute is accessed through <tt>is<i>XXX</i></tt>.
     */
    public boolean isIs() ;


    // commodity methods
    //

    /**
     * Compares the specified <var>obj</var> parameter with this <code>OpenMBeanAttributeInfo</code> instance for equality.
     * <p>
     * Returns <tt>true</tt> if and only if all of the following statements are true:
     * <ul>
     * <li><var>obj</var> is non null,</li>
     * <li><var>obj</var> also implements the <code>OpenMBeanAttributeInfo</code> interface,</li>
     * <li>their names are equal</li>
     * <li>their open types are equal</li>
     * <li>their access properties (isReadable, isWritable and isIs) are equal</li>
     * <li>their default, min, max and legal values are equal.</li>
     * </ul>
     * This ensures that this <tt>equals</tt> method works properly for <var>obj</var> parameters which are
     * different implementations of the <code>OpenMBeanAttributeInfo</code> interface.
     * <br>&nbsp;
     * <p>
     *  将指定的<var> obj </var>参数与此<code> OpenMBeanAttributeInfo </code>实例相比较。
     * <p>
     *  当且仅当所有以下语句都为真时返回<tt> true </tt>：
     * <ul>
     *  </li> <li> <var> obj </var>也实现<code> OpenMBeanAttributeInfo </code>接口,</li> <li> </li> <li>其访问属性(isR
     * eadable,isWritable和isIs)相等</li> <li>它们的默认值,最小值,最大值和法定值值相等。
     * </li>。
     * </ul>
     * 这可以确保<tt>等于</tt>方法对于<var> obj </var>参数正常工作,这些参数是<code> OpenMBeanAttributeInfo </code>接口的不同实现。
     *  <br>&nbsp;。
     * 
     * 
     * @param  obj  the object to be compared for equality with this <code>OpenMBeanAttributeInfo</code> instance;
     *
     * @return  <code>true</code> if the specified object is equal to this <code>OpenMBeanAttributeInfo</code> instance.
     */
    public boolean equals(Object obj);

    /**
     * Returns the hash code value for this <code>OpenMBeanAttributeInfo</code> instance.
     * <p>
     * The hash code of an <code>OpenMBeanAttributeInfo</code> instance is the sum of the hash codes
     * of all elements of information used in <code>equals</code> comparisons
     * (ie: its name, its <i>open type</i>, and its default, min, max and legal values).
     * <p>
     * This ensures that <code> t1.equals(t2) </code> implies that <code> t1.hashCode()==t2.hashCode() </code>
     * for any two <code>OpenMBeanAttributeInfo</code> instances <code>t1</code> and <code>t2</code>,
     * as required by the general contract of the method
     * {@link Object#hashCode() Object.hashCode()}.
     * <p>
     *
     * <p>
     *  返回此<code> OpenMBeanAttributeInfo </code>实例的哈希码值。
     * <p>
     *  <code> OpenMBeanAttributeInfo </code>实例的哈希码是<code> equals </code>比较中使用的所有信息元素的哈希码的总和(即：其名称,其<i> / i>
     * 及其默认值,min,max和legal值)。
     * <p>
     *  这确保<code> t1.equals(t2)</code>意味着任何两个<code> OpenMBeanAttributeInfo </code>实例</code>的<code> t1.hashCo
     * de()== t2.hashCode根据方法{@link Object#hashCode()Object.hashCode()}的一般合同的要求,代码> t1 </code>和<code> t2 </code>
     * 。
     * 
     * @return  the hash code value for this <code>OpenMBeanAttributeInfo</code> instance
     */
    public int hashCode();

    /**
     * Returns a string representation of this <code>OpenMBeanAttributeInfo</code> instance.
     * <p>
     * The string representation consists of the name of this class (ie <code>javax.management.openmbean.OpenMBeanAttributeInfo</code>),
     * the string representation of the name and open type of the described attribute,
     * and the string representation of its default, min, max and legal values.
     *
     * <p>
     * <p>
     * 
     * 
     * @return  a string representation of this <code>OpenMBeanAttributeInfo</code> instance
     */
    public String toString();


    // methods specific to open MBeans are inherited from
    // OpenMBeanParameterInfo
    //

}
