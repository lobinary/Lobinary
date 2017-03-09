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
import java.lang.Comparable; // to be substituted for jdk1.1.x


// jmx import
//


/**
 * <p>Describes a parameter used in one or more operations or
 * constructors of an open MBean.</p>
 *
 * <p>This interface declares the same methods as the class {@link
 * javax.management.MBeanParameterInfo}.  A class implementing this
 * interface (typically {@link OpenMBeanParameterInfoSupport}) should
 * extend {@link javax.management.MBeanParameterInfo}.</p>
 *
 *
 * <p>
 *  <p>描述在打开的MBean的一个或多个操作或构造函数中使用的参数。</p>
 * 
 *  <p>此接口声明与类{@link javax.management.MBeanParameterInfo}相同的方法。
 * 实现此接口的类(通常为{@link OpenMBeanParameterInfoSupport})应该扩展{@link javax.management.MBeanParameterInfo}。
 * </p>。
 * 
 * 
 * @since 1.5
 */
public interface OpenMBeanParameterInfo {


    // Re-declares methods that are in class MBeanParameterInfo of JMX 1.0
    // (these will be removed when MBeanParameterInfo is made a parent interface of this interface)

    /**
     * Returns a human readable description of the parameter
     * described by this <tt>OpenMBeanParameterInfo</tt> instance.
     *
     * <p>
     *  返回此<tt> OpenMBeanParameterInfo </tt>实例描述的参数的可读描述。
     * 
     * 
     * @return the description.
     */
    public String getDescription() ;

    /**
     * Returns the name of the parameter
     * described by this <tt>OpenMBeanParameterInfo</tt> instance.
     *
     * <p>
     *  返回此<tt> OpenMBeanParameterInfo </tt>实例描述的参数的名称。
     * 
     * 
     * @return the name.
     */
    public String getName() ;


    // Now declares methods that are specific to open MBeans
    //

    /**
     * Returns the <i>open type</i> of the values of the parameter
     * described by this <tt>OpenMBeanParameterInfo</tt> instance.
     *
     * <p>
     *  返回此<tt> OpenMBeanParameterInfo </tt>实例描述的参数值的<i>打开类型</i>。
     * 
     * 
     * @return the open type.
     */
    public OpenType<?> getOpenType() ;

    /**
     * Returns the default value for this parameter, if it has one, or
     * <tt>null</tt> otherwise.
     *
     * <p>
     *  返回此参数的默认值(如果有),否则返回<tt> null </tt>。
     * 
     * 
     * @return the default value.
     */
    public Object getDefaultValue() ;

    /**
     * Returns the set of legal values for this parameter, if it has
     * one, or <tt>null</tt> otherwise.
     *
     * <p>
     *  返回此参数的合法值集(如果有),否则返回<tt> null </tt>。
     * 
     * 
     * @return the set of legal values.
     */
    public Set<?> getLegalValues() ;

    /**
     * Returns the minimal value for this parameter, if it has one, or
     * <tt>null</tt> otherwise.
     *
     * <p>
     *  返回此参数的最小值(如果有),否则返回<tt> null </tt>。
     * 
     * 
     * @return the minimum value.
     */
    public Comparable<?> getMinValue() ;

    /**
     * Returns the maximal value for this parameter, if it has one, or
     * <tt>null</tt> otherwise.
     *
     * <p>
     *  返回此参数的最大值(如果有),否则返回<tt> null </tt>。
     * 
     * 
     * @return the maximum value.
     */
    public Comparable<?> getMaxValue() ;

    /**
     * Returns <tt>true</tt> if this parameter has a specified default
     * value, or <tt>false</tt> otherwise.
     *
     * <p>
     *  如果此参数具有指定的默认值,则返回<tt> true </tt>,否则返回<tt> false </tt>。
     * 
     * 
     * @return true if there is a default value.
     */
    public boolean hasDefaultValue() ;

    /**
     * Returns <tt>true</tt> if this parameter has a specified set of
     * legal values, or <tt>false</tt> otherwise.
     *
     * <p>
     *  如果此参数具有指定的合法值集合,则返回<tt> true </tt>,否则返回<tt> false </tt>。
     * 
     * 
     * @return true if there is a set of legal values.
     */
    public boolean hasLegalValues() ;

    /**
     * Returns <tt>true</tt> if this parameter has a specified minimal
     * value, or <tt>false</tt> otherwise.
     *
     * <p>
     *  如果此参数具有指定的最小值,则返回<tt> true </tt>,否则返回<tt> false </tt>。
     * 
     * 
     * @return true if there is a minimum value.
     */
    public boolean hasMinValue() ;

    /**
     * Returns <tt>true</tt> if this parameter has a specified maximal
     * value, or <tt>false</tt> otherwise.
     *
     * <p>
     * 如果此参数具有指定的最大值,则返回<tt> true </tt>,否则返回<tt> false </tt>。
     * 
     * 
     * @return true if there is a maximum value.
     */
    public boolean hasMaxValue() ;

    /**
     * Tests whether <var>obj</var> is a valid value for the parameter
     * described by this <code>OpenMBeanParameterInfo</code> instance.
     *
     * <p>
     *  测试<var> obj </var>是否是由此<code> OpenMBeanParameterInfo </code>实例描述的参数的有效值。
     * 
     * 
     * @param obj the object to be tested.
     *
     * @return <code>true</code> if <var>obj</var> is a valid value
     * for the parameter described by this
     * <code>OpenMBeanParameterInfo</code> instance,
     * <code>false</code> otherwise.
     */
    public boolean isValue(Object obj) ;


    /**
     * Compares the specified <var>obj</var> parameter with this <code>OpenMBeanParameterInfo</code> instance for equality.
     * <p>
     * Returns <tt>true</tt> if and only if all of the following statements are true:
     * <ul>
     * <li><var>obj</var> is non null,</li>
     * <li><var>obj</var> also implements the <code>OpenMBeanParameterInfo</code> interface,</li>
     * <li>their names are equal</li>
     * <li>their open types are equal</li>
     * <li>their default, min, max and legal values are equal.</li>
     * </ul>
     * This ensures that this <tt>equals</tt> method works properly for <var>obj</var> parameters which are
     * different implementations of the <code>OpenMBeanParameterInfo</code> interface.
     * <br>&nbsp;
     * <p>
     *  将指定的<var> obj </var>参数与此<code> OpenMBeanParameterInfo </code>实例相比较。
     * <p>
     *  当且仅当所有以下语句都为真时返回<tt> true </tt>：
     * <ul>
     *  <li> <var> obj </var>不为空,</li> <li> <var> obj </var>也实现<code> OpenMBeanParameterInfo </code> </li> <li>
     * 其默认值,最小值,最大值和法定值相等。
     * </li>。
     * </ul>
     *  这可以确保<tt> equals </tt>方法对<var> obj </var>参数正常工作,这些参数是<code> OpenMBeanParameterInfo </code>接口的不同实现。
     *  <br>&nbsp;。
     * 
     * 
     * @param  obj  the object to be compared for equality with this <code>OpenMBeanParameterInfo</code> instance;
     *
     * @return  <code>true</code> if the specified object is equal to this <code>OpenMBeanParameterInfo</code> instance.
     */
    public boolean equals(Object obj);

    /**
     * Returns the hash code value for this <code>OpenMBeanParameterInfo</code> instance.
     * <p>
     * The hash code of an <code>OpenMBeanParameterInfo</code> instance is the sum of the hash codes
     * of all elements of information used in <code>equals</code> comparisons
     * (ie: its name, its <i>open type</i>, and its default, min, max and legal values).
     * <p>
     * This ensures that <code> t1.equals(t2) </code> implies that <code> t1.hashCode()==t2.hashCode() </code>
     * for any two <code>OpenMBeanParameterInfo</code> instances <code>t1</code> and <code>t2</code>,
     * as required by the general contract of the method
     * {@link Object#hashCode() Object.hashCode()}.
     * <p>
     *
     * <p>
     *  返回此<code> OpenMBeanParameterInfo </code>实例的哈希码值。
     * <p>
     *  <code> OpenMBeanParameterInfo </code>实例的哈希码是在<code> equals </code>比较中使用的所有信息元素的哈希码的总和(即：它的名称, / i>及其
     * 默认值,min,max和legal值)。
     * <p>
     * 这确保对于任何两个<code> OpenMBeanParameterInfo </code>实例,<code> t1.equals(t2)</code>意味着<code> t1.hashCode()==
     *  t2.hashCode()</代码> t1 </code>和<code> t2 </code>,这是方法{@link Object#hashCode()Object.hashCode()}的一般合同的
     * 要求。
     * 
     * @return  the hash code value for this <code>OpenMBeanParameterInfo</code> instance
     */
    public int hashCode();

    /**
     * Returns a string representation of this <code>OpenMBeanParameterInfo</code> instance.
     * <p>
     * The string representation consists of the name of this class (ie <code>javax.management.openmbean.OpenMBeanParameterInfo</code>),
     * the string representation of the name and open type of the described parameter,
     * and the string representation of its default, min, max and legal values.
     *
     * <p>
     * <p>
     * 
     * 
     * @return  a string representation of this <code>OpenMBeanParameterInfo</code> instance
     */
    public String toString();

}
