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
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.MBeanNotificationInfo;



/**
 * <p>Describes an Open MBean: an Open MBean is recognized as such if
 * its {@link javax.management.DynamicMBean#getMBeanInfo()
 * getMBeanInfo()} method returns an instance of a class which
 * implements the {@link OpenMBeanInfo} interface, typically {@link
 * OpenMBeanInfoSupport}.</p>
 *
 * <p>This interface declares the same methods as the class {@link
 * javax.management.MBeanInfo}.  A class implementing this interface
 * (typically {@link OpenMBeanInfoSupport}) should extend {@link
 * javax.management.MBeanInfo}.</p>
 *
 * <p>The {@link #getAttributes()}, {@link #getOperations()} and
 * {@link #getConstructors()} methods of the implementing class should
 * return at runtime an array of instances of a subclass of {@link
 * MBeanAttributeInfo}, {@link MBeanOperationInfo} or {@link
 * MBeanConstructorInfo} respectively which implement the {@link
 * OpenMBeanAttributeInfo}, {@link OpenMBeanOperationInfo} or {@link
 * OpenMBeanConstructorInfo} interface respectively.
 *
 *
 * <p>
 *  <p>描述一个打开的MBean：如果一个打开的MBean的{@link javax.management.DynamicMBean#getMBeanInfo()getMBeanInfo()}方法返回一
 * 个实现{@link OpenMBeanInfo}接口的类的实例,通常为{@link OpenMBeanInfoSupport}。
 * </p>。
 * 
 *  <p>此接口声明与类{@link javax.management.MBeanInfo}相同的方法。
 * 实现此接口的类(通常为{@link OpenMBeanInfoSupport})应该扩展{@link javax.management.MBeanInfo}。</p>。
 * 
 *  <p>实现类的{@link #getAttributes()},{@link #getOperations()}和{@link #getConstructors()}方法应该在运行时返回一个{@link分别实现{@link OpenMBeanAttributeInfo}
 * ,{@link OpenMBeanOperationInfo}或{@link OpenMBeanConstructorInfo}接口的MBeanAttributeInfo},{@link MBeanOperationInfo}
 * 或{@link MBeanConstructorInfo}。
 * 
 * 
 * @since 1.5
 */
public interface OpenMBeanInfo {

    // Re-declares the methods that are in class MBeanInfo of JMX 1.0
    // (methods will be removed when MBeanInfo is made a parent interface of this interface)

    /**
     * Returns the fully qualified Java class name of the open MBean
     * instances this <tt>OpenMBeanInfo</tt> describes.
     *
     * <p>
     *  返回此<tt> OpenMBeanInfo </tt>描述的打开MBean实例的标准Java类名称。
     * 
     * 
     * @return the class name.
     */
    public String getClassName() ;

    /**
     * Returns a human readable description of the type of open MBean
     * instances this <tt>OpenMBeanInfo</tt> describes.
     *
     * <p>
     *  返回此<tt> OpenMBeanInfo </tt>描述的打开MBean实例类型的可读描述。
     * 
     * 
     * @return the description.
     */
    public String getDescription() ;

    /**
     * Returns an array of <tt>OpenMBeanAttributeInfo</tt> instances
     * describing each attribute in the open MBean described by this
     * <tt>OpenMBeanInfo</tt> instance.  Each instance in the returned
     * array should actually be a subclass of
     * <tt>MBeanAttributeInfo</tt> which implements the
     * <tt>OpenMBeanAttributeInfo</tt> interface (typically {@link
     * OpenMBeanAttributeInfoSupport}).
     *
     * <p>
     * 返回<tt> OpenMBeanAttributeInfo </tt>实例的数组,该数组描述由此<tt> OpenMBeanInfo </tt>实例描述的打开MBean中的每个属性。
     * 返回数组中的每个实例实际上应该是实现<tt> OpenMBeanAttributeInfo </tt>接口(通常为{@link OpenMBeanAttributeInfoSupport})的<tt> 
     * MBeanAttributeInfo </tt>的子类。
     * 返回<tt> OpenMBeanAttributeInfo </tt>实例的数组,该数组描述由此<tt> OpenMBeanInfo </tt>实例描述的打开MBean中的每个属性。
     * 
     * 
     * @return the attribute array.
     */
    public MBeanAttributeInfo[] getAttributes() ;

    /**
     * Returns an array of <tt>OpenMBeanOperationInfo</tt> instances
     * describing each operation in the open MBean described by this
     * <tt>OpenMBeanInfo</tt> instance.  Each instance in the returned
     * array should actually be a subclass of
     * <tt>MBeanOperationInfo</tt> which implements the
     * <tt>OpenMBeanOperationInfo</tt> interface (typically {@link
     * OpenMBeanOperationInfoSupport}).
     *
     * <p>
     *  返回<tt> OpenMBeanOperationInfo </tt>实例的数组,描述此<tt> OpenMBeanInfo </tt>实例描述的打开MBean中的每个操作。
     * 返回数组中的每个实例实际上应该是实现<tt> OpenMBeanOperationInfo </tt>接口(通常为{@link OpenMBeanOperationInfoSupport})的<tt> 
     * MBeanOperationInfo </tt>的子类。
     *  返回<tt> OpenMBeanOperationInfo </tt>实例的数组,描述此<tt> OpenMBeanInfo </tt>实例描述的打开MBean中的每个操作。
     * 
     * 
     * @return the operation array.
     */
    public MBeanOperationInfo[] getOperations() ;

    /**
     * Returns an array of <tt>OpenMBeanConstructorInfo</tt> instances
     * describing each constructor in the open MBean described by this
     * <tt>OpenMBeanInfo</tt> instance.  Each instance in the returned
     * array should actually be a subclass of
     * <tt>MBeanConstructorInfo</tt> which implements the
     * <tt>OpenMBeanConstructorInfo</tt> interface (typically {@link
     * OpenMBeanConstructorInfoSupport}).
     *
     * <p>
     *  返回<tt> OpenMBeanConstructorInfo </tt>实例的数组,该数组描述由此<tt> OpenMBeanInfo </tt>实例描述的打开MBean中的每个构造函数。
     * 返回数组中的每个实例实际上应该是实现<tt> OpenMBeanConstructorInfo </tt>接口(通常为{@link OpenMBeanConstructorInfoSupport})的<tt>
     *  MBeanConstructorInfo </tt>的子类。
     *  返回<tt> OpenMBeanConstructorInfo </tt>实例的数组,该数组描述由此<tt> OpenMBeanInfo </tt>实例描述的打开MBean中的每个构造函数。
     * 
     * 
     * @return the constructor array.
     */
    public MBeanConstructorInfo[] getConstructors() ;

    /**
     * Returns an array of <tt>MBeanNotificationInfo</tt> instances
     * describing each notification emitted by the open MBean
     * described by this <tt>OpenMBeanInfo</tt> instance.
     *
     * <p>
     *  返回<tt> MBeanNotificationInfo </tt>实例的数组,该数组描述由此<tt> OpenMBeanInfo </tt>实例描述的打开MBean发出的每个通知。
     * 
     * 
     * @return the notification array.
     */
    public MBeanNotificationInfo[] getNotifications() ;


    // commodity methods
    //

    /**
     * Compares the specified <var>obj</var> parameter with this <code>OpenMBeanInfo</code> instance for equality.
     * <p>
     * Returns <tt>true</tt> if and only if all of the following statements are true:
     * <ul>
     * <li><var>obj</var> is non null,</li>
     * <li><var>obj</var> also implements the <code>OpenMBeanInfo</code> interface,</li>
     * <li>their class names are equal</li>
     * <li>their infos on attributes, constructors, operations and notifications are equal</li>
     * </ul>
     * This ensures that this <tt>equals</tt> method works properly for <var>obj</var> parameters which are
     * different implementations of the <code>OpenMBeanInfo</code> interface.
     * <br>&nbsp;
     * <p>
     *  将指定的<var> obj </var>参数与此<code> OpenMBeanInfo </code>实例相比较。
     * <p>
     *  当且仅当所有以下语句都为真时返回<tt> true </tt>：
     * <ul>
     * <li> <var> obj </var>不为空,</li> <li> <var> obj </var>也实现<code> OpenMBeanInfo </code>他们的类名相等</li> <li>其
     * 属性,构造函数,操作和通知的信息相等</li>。
     * </ul>
     *  这可以确保<tt> equals </tt>方法对<var> obj </var>参数正常工作,这些参数是<code> OpenMBeanInfo </code>接口的不同实现。
     *  <br>&nbsp;。
     * 
     * 
     * @param  obj  the object to be compared for equality with this <code>OpenMBeanInfo</code> instance;
     *
     * @return  <code>true</code> if the specified object is equal to this <code>OpenMBeanInfo</code> instance.
     */
    public boolean equals(Object obj);

    /**
     * Returns the hash code value for this <code>OpenMBeanInfo</code> instance.
     * <p>
     * The hash code of an <code>OpenMBeanInfo</code> instance is the sum of the hash codes
     * of all elements of information used in <code>equals</code> comparisons
     * (ie: its class name, and its infos on attributes, constructors, operations and notifications,
     * where the hashCode of each of these arrays is calculated by a call to
     *  <tt>new java.util.HashSet(java.util.Arrays.asList(this.getSignature)).hashCode()</tt>).
     * <p>
     * This ensures that <code> t1.equals(t2) </code> implies that <code> t1.hashCode()==t2.hashCode() </code>
     * for any two <code>OpenMBeanInfo</code> instances <code>t1</code> and <code>t2</code>,
     * as required by the general contract of the method
     * {@link Object#hashCode() Object.hashCode()}.
     * <p>
     *
     * <p>
     *  返回此<code> OpenMBeanInfo </code>实例的哈希码值。
     * <p>
     *  <code> OpenMBeanInfo </code>实例的哈希码是<code> equals </code>比较中使用的所有信息元素的哈希码的总和(即：它的类名称及其属性信息,构造函数,操作和通知
     * ,其中每个数组的hashCode是通过调用<tt> new java.util.HashSet(java.util.Arrays.asList(this.getSignature))。
     * hashCode()</tt >)。
     * <p>
     *  这确保<code> t1.equals(t2)</code>意味着任何两个<code> OpenMBeanInfo </code>实例的<code> t1.hashCode()== t2.hashCo
     * de()</根据方法{@link Object#hashCode()Object.hashCode()}的一般合同的要求,代码> t1 </code>和<code> t2 </code>。
     * 
     * @return  the hash code value for this <code>OpenMBeanInfo</code> instance
     */
    public int hashCode();

    /**
     * Returns a string representation of this <code>OpenMBeanInfo</code> instance.
     * <p>
     * The string representation consists of the name of this class (ie <code>javax.management.openmbean.OpenMBeanInfo</code>),
     * the MBean class name,
     * and the string representation of infos on attributes, constructors, operations and notifications of the described MBean.
     *
     * <p>
     * <p>
     * 
     * 
     * @return  a string representation of this <code>OpenMBeanInfo</code> instance
     */
    public String toString();

}
