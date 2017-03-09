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
import javax.management.MBeanParameterInfo;

/**
 * <p>Describes an operation of an Open MBean.</p>
 *
 * <p>This interface declares the same methods as the class {@link
 * javax.management.MBeanOperationInfo}.  A class implementing this
 * interface (typically {@link OpenMBeanOperationInfoSupport}) should
 * extend {@link javax.management.MBeanOperationInfo}.</p>
 *
 * <p>The {@link #getSignature()} method should return at runtime an
 * array of instances of a subclass of {@link MBeanParameterInfo}
 * which implements the {@link OpenMBeanParameterInfo} interface
 * (typically {@link OpenMBeanParameterInfoSupport}).</p>
 *
 *
 * <p>
 *  <p>描述了Open MBean的操作。</p>
 * 
 *  <p>此接口声明与类{@link javax.management.MBeanOperationInfo}相同的方法。
 * 实现此接口的类(通常为{@link OpenMBeanOperationInfoSupport})应该扩展{@link javax.management.MBeanOperationInfo}。
 * </p>。
 * 
 *  <p> {@link #getSignature()}方法应该在运行时返回实现{@link OpenMBeanParameterInfo}接口(通常为{@link OpenMBeanParameterInfoSupport}
 * )的{@link MBeanParameterInfo}子类的实例数组。
 * </p>。
 * 
 * 
 * @since 1.5
 */
public interface OpenMBeanOperationInfo  {

    // Re-declares fields and methods that are in class MBeanOperationInfo of JMX 1.0
    // (fields and methods will be removed when MBeanOperationInfo is made a parent interface of this interface)

    /**
     * Returns a human readable description of the operation
     * described by this <tt>OpenMBeanOperationInfo</tt> instance.
     *
     * <p>
     *  返回此<tt> OpenMBeanOperationInfo </tt>实例描述的操作的可读描述。
     * 
     * 
     * @return the description.
     */
    public String getDescription() ;

    /**
     * Returns the name of the operation
     * described by this <tt>OpenMBeanOperationInfo</tt> instance.
     *
     * <p>
     *  返回此<tt> OpenMBeanOperationInfo </tt>实例描述的操作的名称。
     * 
     * 
     * @return the name.
     */
    public String getName() ;

    /**
     * Returns an array of <tt>OpenMBeanParameterInfo</tt> instances
     * describing each parameter in the signature of the operation
     * described by this <tt>OpenMBeanOperationInfo</tt> instance.
     * Each instance in the returned array should actually be a
     * subclass of <tt>MBeanParameterInfo</tt> which implements the
     * <tt>OpenMBeanParameterInfo</tt> interface (typically {@link
     * OpenMBeanParameterInfoSupport}).
     *
     * <p>
     *  返回<tt> OpenMBeanParameterInfo </tt>实例的数组,描述由此<tt> OpenMBeanOperationInfo </tt>实例描述的操作的签名中的每个参数。
     * 返回数组中的每个实例实际上应该是实现<tt> OpenMBeanParameterInfo </tt>接口(通常为{@link OpenMBeanParameterInfoSupport})的<tt> 
     * MBeanParameterInfo </tt>的子类。
     *  返回<tt> OpenMBeanParameterInfo </tt>实例的数组,描述由此<tt> OpenMBeanOperationInfo </tt>实例描述的操作的签名中的每个参数。
     * 
     * 
     * @return the signature.
     */
    public MBeanParameterInfo[] getSignature() ;

    /**
     * Returns an <tt>int</tt> constant qualifying the impact of the
     * operation described by this <tt>OpenMBeanOperationInfo</tt>
     * instance.
     *
     * The returned constant is one of {@link
     * javax.management.MBeanOperationInfo#INFO}, {@link
     * javax.management.MBeanOperationInfo#ACTION}, {@link
     * javax.management.MBeanOperationInfo#ACTION_INFO}, or {@link
     * javax.management.MBeanOperationInfo#UNKNOWN}.
     *
     * <p>
     *  返回<tt> int </tt>常量,限定此<tt> OpenMBeanOperationInfo </tt>实例描述的操作的影响。
     * 
     * 返回的常数是{@link javax.management.MBeanOperationInfo#INFO},{@link javax.management.MBeanOperationInfo#ACTION}
     * ,{@link javax.management.MBeanOperationInfo#ACTION_INFO}或{@link javax.management。
     *  MBeanOperationInfo#UNKNOWN}。
     * 
     * 
     * @return the impact code.
     */
    public int getImpact() ;

    /**
     * Returns the fully qualified Java class name of the values
     * returned by the operation described by this
     * <tt>OpenMBeanOperationInfo</tt> instance.  This method should
     * return the same value as a call to
     * <tt>getReturnOpenType().getClassName()</tt>.
     *
     * <p>
     *  返回由此<tt> OpenMBeanOperationInfo </tt>实例描述的操作返回的值的标准Java类名。此方法应返回与调用<tt> getReturnOpenType()。
     * getClassName()</tt>相同的值。
     * 
     * 
     * @return the return type.
     */
    public String getReturnType() ;


    // Now declares methods that are specific to open MBeans
    //

    /**
     * Returns the <i>open type</i> of the values returned by the
     * operation described by this <tt>OpenMBeanOperationInfo</tt>
     * instance.
     *
     * <p>
     *  返回由此<tt> OpenMBeanOperationInfo </tt>实例描述的操作返回的值的<i>打开类型</i>。
     * 
     * 
     * @return the return type.
     */
    public OpenType<?> getReturnOpenType() ; // open MBean specific method


    // commodity methods
    //

    /**
     * Compares the specified <var>obj</var> parameter with this <code>OpenMBeanOperationInfo</code> instance for equality.
     * <p>
     * Returns <tt>true</tt> if and only if all of the following statements are true:
     * <ul>
     * <li><var>obj</var> is non null,</li>
     * <li><var>obj</var> also implements the <code>OpenMBeanOperationInfo</code> interface,</li>
     * <li>their names are equal</li>
     * <li>their signatures are equal</li>
     * <li>their return open types are equal</li>
     * <li>their impacts are equal</li>
     * </ul>
     * This ensures that this <tt>equals</tt> method works properly for <var>obj</var> parameters which are
     * different implementations of the <code>OpenMBeanOperationInfo</code> interface.
     * <br>&nbsp;
     * <p>
     *  将指定的<var> obj </var>参数与此<code> OpenMBeanOperationInfo </code>实例相比较。
     * <p>
     *  当且仅当所有以下语句都为真时返回<tt> true </tt>：
     * <ul>
     *  </li> <li> <var> obj </var>也实现<code> OpenMBeanOperationInfo </code>接口,</li> <li> </li> </li> <li>他们的
     * 回复打开类型相等</li> <li>他们的影响相等</li>。
     * </ul>
     *  这可以确保<tt> equals </tt>方法对<var> obj </var>参数正常工作,这些参数是<code> OpenMBeanOperationInfo </code>接口的不同实现。
     *  <br>&nbsp;。
     * 
     * 
     * @param  obj  the object to be compared for equality with this <code>OpenMBeanOperationInfo</code> instance;
     *
     * @return  <code>true</code> if the specified object is equal to this <code>OpenMBeanOperationInfo</code> instance.
     */
    public boolean equals(Object obj);

    /**
     * Returns the hash code value for this <code>OpenMBeanOperationInfo</code> instance.
     * <p>
     * The hash code of an <code>OpenMBeanOperationInfo</code> instance is the sum of the hash codes
     * of all elements of information used in <code>equals</code> comparisons
     * (ie: its name, return open type, impact and signature, where the signature hashCode is calculated by a call to
     *  <tt>java.util.Arrays.asList(this.getSignature).hashCode()</tt>).
     * <p>
     * This ensures that <code> t1.equals(t2) </code> implies that <code> t1.hashCode()==t2.hashCode() </code>
     * for any two <code>OpenMBeanOperationInfo</code> instances <code>t1</code> and <code>t2</code>,
     * as required by the general contract of the method
     * {@link Object#hashCode() Object.hashCode()}.
     * <p>
     *
     * <p>
     *  返回此<code> OpenMBeanOperationInfo </code>实例的哈希码值。
     * <p>
     * <code> OpenMBeanOperationInfo </code>实例的哈希码是<code> equals </code>比较中使用的所有信息元素的哈希码的总和(即：它的名称,返回开放类型,影响
     * 和签名,其中签名hashCode是通过调用<tt> java.util.Arrays.asList(this.getSignature).hashCode()</tt>)计算的。
     * <p>
     *  这确保<code> t1.equals(t2)</code>意味着任何两个<code> OpenMBeanOperationInfo </code>实例</code>的<code> t1.hashCo
     * de()== t2.hashCode代码> t1 </code>和<code> t2 </code>,这是方法{@link Object#hashCode()Object.hashCode()}的一般合
     * 同的要求。
     * 
     * @return  the hash code value for this <code>OpenMBeanOperationInfo</code> instance
     */
    public int hashCode();

    /**
     * Returns a string representation of this <code>OpenMBeanOperationInfo</code> instance.
     * <p>
     * The string representation consists of the name of this class (ie <code>javax.management.openmbean.OpenMBeanOperationInfo</code>),
     * and the name, signature, return open type and impact of the described operation.
     *
     * <p>
     * <p>
     * 
     * 
     * @return  a string representation of this <code>OpenMBeanOperationInfo</code> instance
     */
    public String toString();

}
