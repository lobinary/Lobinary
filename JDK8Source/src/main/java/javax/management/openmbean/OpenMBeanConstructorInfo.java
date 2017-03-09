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
 * <p>Describes a constructor of an Open MBean.</p>
 *
 * <p>This interface declares the same methods as the class {@link
 * javax.management.MBeanConstructorInfo}.  A class implementing this
 * interface (typically {@link OpenMBeanConstructorInfoSupport})
 * should extend {@link javax.management.MBeanConstructorInfo}.</p>
 *
 * <p>The {@link #getSignature()} method should return at runtime an
 * array of instances of a subclass of {@link MBeanParameterInfo}
 * which implements the {@link OpenMBeanParameterInfo} interface
 * (typically {@link OpenMBeanParameterInfoSupport}).</p>
 *
 *
 *
 * <p>
 *  <p>描述了Open MBean的构造函数。</p>
 * 
 *  <p>此接口声明与类{@link javax.management.MBeanConstructorInfo}相同的方法。
 * 实现此接口的类(通常为{@link OpenMBeanConstructorInfoSupport})应该扩展{@link javax.management.MBeanConstructorInfo}。
 *  <p>此接口声明与类{@link javax.management.MBeanConstructorInfo}相同的方法。</p>。
 * 
 *  <p> {@link #getSignature()}方法应该在运行时返回实现{@link OpenMBeanParameterInfo}接口(通常为{@link OpenMBeanParameterInfoSupport}
 * )的{@link MBeanParameterInfo}子类的实例数组。
 * </p>。
 * 
 * 
 * @since 1.5
 */
public interface OpenMBeanConstructorInfo {

    // Re-declares the methods that are in class MBeanConstructorInfo of JMX 1.0
    // (methods will be removed when MBeanConstructorInfo is made a parent interface of this interface)

    /**
     * Returns a human readable description of the constructor
     * described by this <tt>OpenMBeanConstructorInfo</tt> instance.
     *
     * <p>
     *  返回此<tt> OpenMBeanConstructorInfo </tt>实例描述的构造函数的可读描述。
     * 
     * 
     * @return the description.
     */
    public String getDescription() ;

    /**
     * Returns the name of the constructor
     * described by this <tt>OpenMBeanConstructorInfo</tt> instance.
     *
     * <p>
     *  返回此<tt> OpenMBeanConstructorInfo </tt>实例描述的构造函数的名称。
     * 
     * 
     * @return the name.
     */
    public String getName() ;

    /**
     * Returns an array of <tt>OpenMBeanParameterInfo</tt> instances
     * describing each parameter in the signature of the constructor
     * described by this <tt>OpenMBeanConstructorInfo</tt> instance.
     *
     * <p>
     *  返回<tt> OpenMBeanParameterInfo </tt>实例的数组,描述由此<tt> OpenMBeanConstructorInfo </tt>实例描述的构造函数的签名中的每个参数。
     * 
     * 
     * @return the signature.
     */
    public MBeanParameterInfo[] getSignature() ;


    // commodity methods
    //

    /**
     * Compares the specified <var>obj</var> parameter with this <code>OpenMBeanConstructorInfo</code> instance for equality.
     * <p>
     * Returns <tt>true</tt> if and only if all of the following statements are true:
     * <ul>
     * <li><var>obj</var> is non null,</li>
     * <li><var>obj</var> also implements the <code>OpenMBeanConstructorInfo</code> interface,</li>
     * <li>their names are equal</li>
     * <li>their signatures are equal.</li>
     * </ul>
     * This ensures that this <tt>equals</tt> method works properly for <var>obj</var> parameters which are
     * different implementations of the <code>OpenMBeanConstructorInfo</code> interface.
     * <br>&nbsp;
     * <p>
     *  将指定的<var> obj </var>参数与此<code> OpenMBeanConstructorInfo </code>实例相比较。
     * <p>
     *  当且仅当所有以下语句都为真时返回<tt> true </tt>：
     * <ul>
     * <li> <var> obj </var>不为空,</li> <li> <var> obj </var>也实现<code> OpenMBeanConstructorInfo </code>他们的姓名相同
     * </li> <li>他们的签名相等。
     * </li>。
     * </ul>
     *  这可以确保<tt> equals </tt>方法对于<var> obj </var>参数正常工作,这些参数是<code> OpenMBeanConstructorInfo </code>接口的不同实现
     * 。
     *  <br>&nbsp;。
     * 
     * 
     * @param  obj  the object to be compared for equality with this <code>OpenMBeanConstructorInfo</code> instance;
     *
     * @return  <code>true</code> if the specified object is equal to this <code>OpenMBeanConstructorInfo</code> instance.
     */
    public boolean equals(Object obj);

    /**
     * Returns the hash code value for this <code>OpenMBeanConstructorInfo</code> instance.
     * <p>
     * The hash code of an <code>OpenMBeanConstructorInfo</code> instance is the sum of the hash codes
     * of all elements of information used in <code>equals</code> comparisons
     * (ie: its name and signature, where the signature hashCode is calculated by a call to
     *  <tt>java.util.Arrays.asList(this.getSignature).hashCode()</tt>).
     * <p>
     * This ensures that <code> t1.equals(t2) </code> implies that <code> t1.hashCode()==t2.hashCode() </code>
     * for any two <code>OpenMBeanConstructorInfo</code> instances <code>t1</code> and <code>t2</code>,
     * as required by the general contract of the method
     * {@link Object#hashCode() Object.hashCode()}.
     * <p>
     *
     * <p>
     *  返回此<code> OpenMBeanConstructorInfo </code>实例的哈希码值。
     * <p>
     *  <code> OpenMBeanConstructorInfo </code>实例的哈希码是在<code> equals </code>比较中使用的所有信息元素的哈希码的总和(即：其名称和签名,其中签
     * 名hashCode是通过调用<tt> java.util.Arrays.asList(this.getSignature).hashCode()</tt>)计算。
     * <p>
     *  这确保<code> t1.equals(t2)</code>意味着任何两个<code> OpenMBeanConstructorInfo </code>实例的<code> t1.hashCode()=
     * = t2.hashCode()</代码> t1 </code>和<code> t2 </code>,这是方法{@link Object#hashCode()Object.hashCode()}的一般合同
     * 的要求。
     * 
     * @return  the hash code value for this <code>OpenMBeanConstructorInfo</code> instance
     */
    public int hashCode();

    /**
     * Returns a string representation of this <code>OpenMBeanConstructorInfo</code> instance.
     * <p>
     * The string representation consists of the name of this class (ie <code>javax.management.openmbean.OpenMBeanConstructorInfo</code>),
     * and the name and signature of the described constructor.
     *
     * <p>
     * <p>
     * 
     * 
     * @return  a string representation of this <code>OpenMBeanConstructorInfo</code> instance
     */
    public String toString();

}
