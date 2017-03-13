/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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
/*
/* <p>
/* 
 * @author    IBM Corp.
 *
 * Copyright IBM Corp. 1999-2000.  All rights reserved.
 */

package javax.management.modelmbean;

import javax.management.Descriptor;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.RuntimeOperationsException;
import javax.management.MBeanException;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;

/**
 * This interface is implemented by the ModelMBeanInfo for every ModelMBean. An implementation of this interface
 * must be shipped with every JMX Agent.
 * <P>
 * Java resources wishing to be manageable instantiate the ModelMBean using the MBeanServer's
 * createMBean method.  The resource then sets the ModelMBeanInfo and Descriptors for the ModelMBean
 * instance. The attributes, operations, and notifications exposed via the ModelMBeanInfo for the
 * ModelMBean comprise the management interface and are accessible
 * from MBeans, connectors/adaptors like other MBeans. Through the Descriptors, values and methods in
 * the managed application can be defined and mapped to attributes and operations of the ModelMBean.
 * This mapping can be defined during development in a file or dynamically and
 * programmatically at runtime.
 * <P>
 * Every ModelMBean which is instantiated in the MBeanServer becomes manageable:
 * its attributes, operations, and notifications
 * become remotely accessible through the connectors/adaptors connected to that MBeanServer.
 * A Java object cannot be registered in the MBeanServer unless it is a JMX compliant MBean.
 * By instantiating a ModelMBean, resources are guaranteed that the MBean is valid.
 *
 * MBeanException and RuntimeOperationsException must be thrown on every public method.  This allows
 *  for wrapping exceptions from distributed communications (RMI, EJB, etc.)
 *
 * <p>
 *  此接口由每个ModelMBean的ModelMBeanInfo实现此接口的实现必须随每个JMX Agent一起提供
 * <P>
 * 希望可管理的Java资源使用MBeanServer的createMBean方法实例化ModelMBean该资源然后设置ModelMBean实例的ModelMBeanInfo和Descriptors通过M
 * odelMBean的ModelMBeanInfo显示的属性,操作和通知包括管理接口,可从MBean,连接器/适配器通过描述符,可以定义受管应用程序中的值和方法,并将其映射到ModelMBean的属性和操
 * 作。
 * 此映射可以在开发期间在文件中定义,也可以在运行时以动态和编程方式定义。
 * <P>
 * 在MBeanServer中实例化的每个ModelMBean变得易于管理：其属性,操作和通知可通过连接到该MBeanServer的连接器/适配器远程访问除非是符合JMX的MBean,否则不能在MBeanS
 * erver中注册Java对象通过实例化ModelMBean ,保证MBean有效的资源。
 * 
 *  必须在每个公共方法上抛出MBeanException和RuntimeOperationsException这允许从分布式通信(RMI,EJB等)包装异常,
 * 
 * 
 * @since 1.5
 */

public interface ModelMBeanInfo
{


    /**
     * Returns a Descriptor array consisting of all
     * Descriptors for the ModelMBeanInfo of type inDescriptorType.
     *
     * <p>
     *  返回一个描述符数组,该数组由类型inDescriptorType的ModelMBeanInfo的所有描述符组成
     * 
     * 
     * @param inDescriptorType value of descriptorType field that must be set for the descriptor
     * to be returned.  Must be "mbean", "attribute", "operation", "constructor" or "notification".
     * If it is null or empty then all types will be returned.
     *
     * @return Descriptor array containing all descriptors for the ModelMBean if type inDescriptorType.
     *
     * @exception MBeanException Wraps a distributed communication Exception.
     * @exception RuntimeOperationsException Wraps an IllegalArgumentException when the descriptorType in parameter is
     * not one of: "mbean", "attribute", "operation", "constructor", "notification", empty or null.
     *
     * @see #setDescriptors
     */
    public Descriptor[] getDescriptors(String inDescriptorType)
            throws MBeanException, RuntimeOperationsException;

    /**
     * Adds or replaces descriptors in the ModelMBeanInfo.
     *
     * <p>
     *  在ModelMBeanInfo中添加或替换描述符
     * 
     * 
     * @param inDescriptors The descriptors to be set in the ModelMBeanInfo. Null
     * elements of the list will be ignored.  All descriptors must have name and descriptorType fields.
     *
     * @exception RuntimeOperationsException Wraps an IllegalArgumentException for a null or invalid descriptor.
     * @exception MBeanException Wraps a distributed communication Exception.
     *
     * @see #getDescriptors
     */
    public void setDescriptors(Descriptor[] inDescriptors)
            throws MBeanException, RuntimeOperationsException;

    /**
     * Returns a Descriptor requested by name and descriptorType.
     *
     * <p>
     *  返回由名称和descriptorType请求的描述符
     * 
     * 
     * @param inDescriptorName The name of the descriptor.
     * @param inDescriptorType The type of the descriptor being
     * requested.  If this is null or empty then all types are
     * searched. Valid types are 'mbean', 'attribute', 'constructor'
     * 'operation', and 'notification'. This value will be equal to
     * the 'descriptorType' field in the descriptor that is returned.
     *
     * @return Descriptor containing the descriptor for the ModelMBean
     * with the same name and descriptorType.  If no descriptor is
     * found, null is returned.
     *
     * @exception MBeanException Wraps a distributed communication Exception.
     * @exception RuntimeOperationsException Wraps an IllegalArgumentException for a null descriptor name or null or invalid type.
     * The type must be "mbean","attribute", "constructor", "operation", or "notification".
     *
     * @see #setDescriptor
     */

    public Descriptor getDescriptor(String inDescriptorName, String inDescriptorType)
            throws MBeanException, RuntimeOperationsException;

    /**
     * Sets descriptors in the info array of type inDescriptorType
     * for the ModelMBean.  The setDescriptor method of the
     * corresponding ModelMBean*Info will be called to set the
     * specified descriptor.
     *
     * <p>
     * 在infoMB数据类型的info数组中设置描述符为ModelMBean类型inDescriptorType将调用相应的ModelMBean * Info的setDescriptor方法来设置指定的描述符
     * 。
     * 
     * 
     * @param inDescriptor The descriptor to be set in the
     * ModelMBean. It must NOT be null.  All descriptors must have
     * name and descriptorType fields.
     * @param inDescriptorType The type of the descriptor being
     * set. If this is null then the descriptorType field in the
     * descriptor is used. If specified this value must be set in
     * the descriptorType field in the descriptor. Must be
     * "mbean","attribute", "constructor", "operation", or
     * "notification".
     *
     * @exception RuntimeOperationsException Wraps an
     * IllegalArgumentException for illegal or null arguments or
     * if the name field of the descriptor is not found in the
     * corresponding MBeanAttributeInfo or MBeanConstructorInfo or
     * MBeanNotificationInfo or MBeanOperationInfo.
     * @exception MBeanException Wraps a distributed communication
     * Exception.
     *
     * @see #getDescriptor
     */

    public void setDescriptor(Descriptor inDescriptor, String inDescriptorType)
            throws MBeanException, RuntimeOperationsException;


    /**
     * <p>Returns the ModelMBean's descriptor which contains MBean wide
     * policies.  This descriptor contains metadata about the MBean and default
     * policies for persistence and caching.</p>
     *
     * <P id="descriptor">
     * The fields in the descriptor are defined, but not limited to, the
     * following.  Note that when the Type in this table is Number, a String
     * that is the decimal representation of a Long can also be used.</P>
     *
     * <table border="1" cellpadding="5" summary="ModelMBean Fields">
     * <tr><th>Name</th><th>Type</th><th>Meaning</th></tr>
     * <tr><td>name</td><td>String</td>
     *     <td>MBean name.</td></tr>
     * <tr><td>descriptorType</td><td>String</td>
     *     <td>Must be "mbean".</td></tr>
     * <tr><td>displayName</td><td>String</td>
     *     <td>Name of MBean to be used in displays.</td></tr>
     * <tr><td>persistPolicy</td><td>String</td>
     *     <td>One of: OnUpdate|OnTimer|NoMoreOftenThan|OnUnregister|Always|Never.
     *         See the section "MBean Descriptor Fields" in the JMX specification
     *         document.</td></tr>
     * <tr><td>persistLocation</td><td>String</td>
     *     <td>The fully qualified directory name where the MBean should be
     *         persisted (if appropriate).</td></tr>
     * <tr><td>persistFile</td><td>String</td>
     *     <td>File name into which the MBean should be persisted.</td></tr>
     * <tr><td>persistPeriod</td><td>Number</td>
     *     <td>Frequency of persist cycle in seconds, for OnTime and
     *         NoMoreOftenThan PersistPolicy</td></tr>
     * <tr><td>currencyTimeLimit</td><td>Number</td>
     *     <td>How long cached value is valid: &lt;0 never, =0 always,
     *         &gt;0 seconds.</td></tr>
     * <tr><td>log</td><td>String</td>
     *     <td>t: log all notifications, f: log no notifications.</td></tr>
     * <tr><td>logfile</td><td>String</td>
     *     <td>Fully qualified filename to log events to.</td></tr>
     * <tr><td>visibility</td><td>Number</td>
     *     <td>1-4 where 1: always visible 4: rarely visible.</td></tr>
     * <tr><td>export</td><td>String</td>
     *     <td>Name to be used to export/expose this MBean so that it is
     *         findable by other JMX Agents.</td></tr>
     * <tr><td>presentationString</td><td>String</td>
     *     <td>XML formatted string to allow presentation of data to be
     *         associated with the MBean.</td></tr>
     * </table>
     *
     * <P>
     * The default descriptor is: name=className,descriptorType="mbean", displayName=className,
     *  persistPolicy="never",log="F",visibility="1"
     * If the descriptor does not contain all these fields, they will be added with these default values.
     *
     * <p><b>Note:</b> because of inconsistencies in previous versions of
     * this specification, it is recommended not to use negative or zero
     * values for <code>currencyTimeLimit</code>.  To indicate that a
     * cached value is never valid, omit the
     * <code>currencyTimeLimit</code> field.  To indicate that it is
     * always valid, use a very large number for this field.</p>
     *
     * <p>
     *  <p>返回包含MBean宽策略的ModelMBean描述符此描述符包含有关MBean的元数据以及用于持久性和高速缓存的默认策略</p>
     * 
     * <P id="descriptor">
     *  描述符中的字段被定义,但不限于,以下注意,当该表中的类型是Number时,也可以使用作为Long的十进制表示的String </P>
     * 
     * <table border="1" cellpadding="5" summary="ModelMBean Fields">
     * <tr> <th>名称</th> <th>类型</th> <th>含义</th> </tr> <tr> <td>名称</td> <td>字符串</td> <td> MBean名称</td> </tr> 
     * <tr> <td> descriptorType </td> <td>字符串</td> <td>必须为"mbean"</td> </tr> <tr > <td> displayName </td> <td>
     * 字符串</td> <td>要在显示中使用的MBean的名称</td> </tr> <tr> <td> persistPolicy </td> <td>字符串</td> <td>之一：OnUpdate |
     *  OnTimer | NoMoreOftenThan | OnUnregister | Always | Never |从JMX规范文档</td> </tr> <tr> <td> persistLoca
     * tion中查看"MBean Descriptor Fields" </td> </td> </td> </td> </td> String </td> </td> td> String </td> <td>
     * 应保留MBean的文件名</td> <td>持续周期</td> <td>持续周期的频率(以秒为单位)OnTime和NoMoreOftenThan PersistPolicy </td> </tr> <tr>
     *  <td> currencyTimeLimit </td> <td>数字</td> <td>缓存值的有效时间：<0 never,= 0 always,> 0 seconds </td> </tr> <tr>
     *  <td>日志</td> <td>字符串</td> <td> t：记录所有通知,f：不记录日志</td> </tr> td> <td> String </td> <td> </td> <td>字符串</td>
     *  <td>完全限定文件名以记录事件</td> </tr> 1-4其中1：始终可见4：很少可见</td> </tr> <tr> <td>导出</td> <td>字符串</td> <td>这个MBean,以
     * 便它可以被其他JMX代理找到</td> </span> </td> </td> </td> </td> </td> presentationString </td> tr>。
     * </table>
     * 
     * <P>
     * 默认描述符是：name = className,descriptorType ="mbean",displayName = className,persistPolicy ="never",log ="
     * F",visibility ="1"如果描述符不包含所有这些字段,与这些默认值。
     * 
     *  <p> <b>注意：</b>由于本规范之前版本的不一致,建议不要对<code> currencyTimeLimit </code>使用负值或零值。
     * 表示高速缓存的值从不有效,省略<code> currencyTimeLimit </code>字段要指示它始终有效,请对此字段使用非常大的数字</p>。
     * 
     * 
     * @return the MBean descriptor.
     *
     * @exception MBeanException Wraps a distributed communication
     * Exception.
     *
     * @exception RuntimeOperationsException a {@link
     * RuntimeException} occurred while getting the descriptor.
     *
     * @see #setMBeanDescriptor
     */
    public Descriptor getMBeanDescriptor()
            throws MBeanException, RuntimeOperationsException;

    /**
     * Sets the ModelMBean's descriptor.  This descriptor contains default, MBean wide
     * metadata about the MBean and default policies for persistence and caching. This operation
     * does a complete replacement of the descriptor, no merging is done. If the descriptor to
     * set to is null then the default descriptor will be created.
     * The default descriptor is: name=className,descriptorType="mbean", displayName=className,
     *  persistPolicy="never",log="F",visibility="1"
     * If the descriptor does not contain all these fields, they will be added with these default values.
     *
     * See {@link #getMBeanDescriptor getMBeanDescriptor} method javadoc for description of valid field names.
     *
     * <p>
     * 设置ModelMBean的描述符此描述符包含有关MBean的默认MBean宽度元数据和用于持久性和高速缓存的默认策略此操作完全替换描述符,不进行合并如果要设置的描述符为null,则默认描述符将为crea
     * ted默认描述符是：name = className,descriptorType ="mbean",displayName = className,persistPolicy ="never",log
     *  ="F",visibility ="1"如果描述符不包含所有这些字段,添加了这些默认值。
     * 
     *  有关有效字段名称的说明,请参见{@link #getMBeanDescriptor getMBeanDescriptor}方法javadoc
     * 
     * 
     * @param inDescriptor the descriptor to set.
     *
     * @exception MBeanException Wraps a distributed communication Exception.
     * @exception RuntimeOperationsException Wraps an IllegalArgumentException  for invalid descriptor.
     *
     *
     * @see #getMBeanDescriptor
     */

    public void setMBeanDescriptor(Descriptor inDescriptor)
            throws MBeanException, RuntimeOperationsException;


    /**
     * Returns a ModelMBeanAttributeInfo requested by name.
     *
     * <p>
     *  返回按名称请求的ModelMBeanAttributeInfo
     * 
     * 
     * @param inName The name of the ModelMBeanAttributeInfo to get.
     * If no ModelMBeanAttributeInfo exists for this name null is returned.
     *
     * @return the attribute info for the named attribute, or null
     * if there is none.
     *
     * @exception MBeanException Wraps a distributed communication
     * Exception.
     * @exception RuntimeOperationsException Wraps an
     * IllegalArgumentException for a null attribute name.
     *
     */

    public ModelMBeanAttributeInfo getAttribute(String inName)
            throws MBeanException, RuntimeOperationsException;


    /**
     * Returns a ModelMBeanOperationInfo requested by name.
     *
     * <p>
     *  返回按名称请求的ModelMBeanOperationInfo
     * 
     * 
     * @param inName The name of the ModelMBeanOperationInfo to get.
     * If no ModelMBeanOperationInfo exists for this name null is returned.
     *
     * @return the operation info for the named operation, or null
     * if there is none.
     *
     * @exception MBeanException Wraps a distributed communication Exception.
     * @exception RuntimeOperationsException Wraps an IllegalArgumentException for a null operation name.
     *
     */

    public ModelMBeanOperationInfo getOperation(String inName)
            throws MBeanException, RuntimeOperationsException;


    /**
     * Returns a ModelMBeanNotificationInfo requested by name.
     *
     * <p>
     * 返回按名称请求的ModelMBeanNotificationInfo
     * 
     * 
     * @param inName The name of the ModelMBeanNotificationInfo to get.
     * If no ModelMBeanNotificationInfo exists for this name null is returned.
     *
     * @return the info for the named notification, or null if there
     * is none.
     *
     * @exception MBeanException Wraps a distributed communication Exception.
     * @exception RuntimeOperationsException Wraps an IllegalArgumentException for a null notification name.
     *
     */
    public ModelMBeanNotificationInfo getNotification(String inName)
            throws MBeanException, RuntimeOperationsException;

    /**
     * Creates and returns a copy of this object.
     * <p>
     *  创建并返回此对象的副本
     * 
     */
    public java.lang.Object clone();

    /**
     * Returns the list of attributes exposed for management.
     * Each attribute is described by an <CODE>MBeanAttributeInfo</CODE> object.
     *
     * <p>
     *  返回为管理公开的属性列表每个属性由<CODE> MBeanAttributeInfo </CODE>对象
     * 
     * 
     * @return  An array of <CODE>MBeanAttributeInfo</CODE> objects.
     */
    public MBeanAttributeInfo[] getAttributes();

    /**
     * Returns the name of the Java class of the MBean described by
     * this <CODE>MBeanInfo</CODE>.
     *
     * <p>
     *  返回此<CODE> MBeanInfo </CODE>描述的MBean的Java类的名称
     * 
     * 
     * @return the Java class name.
     */
    public java.lang.String getClassName();

    /**
     * Returns the list of the public constructors  of the MBean.
     * Each constructor is described by an <CODE>MBeanConstructorInfo</CODE> object.
     *
     * <p>
     *  返回MBean的公共构造函数的列表每个构造函数由一个<CODE> MBeanConstructorInfo </CODE>对象
     * 
     * 
     * @return  An array of <CODE>MBeanConstructorInfo</CODE> objects.
     */
    public MBeanConstructorInfo[] getConstructors();

    /**
     * Returns a human readable description of the MBean.
     *
     * <p>
     *  返回MBean的人性化描述
     * 
     * 
     * @return the description.
     */
    public java.lang.String getDescription();

    /**
     * Returns the list of the notifications emitted by the MBean.
     * Each notification is described by an <CODE>MBeanNotificationInfo</CODE> object.
     * <P>
     * In addition to any notification specified by the application,
     * a ModelMBean may always send also two additional notifications:
     * <UL>
     * <LI> One with descriptor name "GENERIC" and displayName "jmx.modelmbean.generic"
     * <LI> Second is a standard attribute change notification
     *      with descriptor name "ATTRIBUTE_CHANGE" and displayName "jmx.attribute.change"
     * </UL>
     * Thus any implementation of ModelMBeanInfo should always add those two notifications
     * in addition to those specified by the application.
     *
     * <p>
     *  返回MBean发出的通知的列表每个通知由<CODE> MBeanNotificationInfo </CODE>对象描述
     * <P>
     *  除了应用程序指定的任何通知之外,ModelMBean还可以始终发送另外两个通知：
     * <UL>
     * <LI>具有描述符名称"GENERIC"和displayName"jmxmodelmbeangeneric"<LI>的第二个是具有描述符名称"ATTRIBUTE_CHANGE"和displayName"
     * jmxattributechange"的标准属性改变通知,。
     * 
     * @return  An array of <CODE>MBeanNotificationInfo</CODE> objects.
     */
    public MBeanNotificationInfo[] getNotifications();

    /**
     * Returns the list of operations  of the MBean.
     * Each operation is described by an <CODE>MBeanOperationInfo</CODE> object.
     *
     * <p>
     * </UL>
     *  因此,除了应用程序指定的通知之外,ModelMBeanInfo的任何实现都应该添加这两个通知
     * 
     * 
     * @return  An array of <CODE>MBeanOperationInfo</CODE> objects.
     */
    public MBeanOperationInfo[] getOperations();

}
