/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.management;


// java import
import java.util.Set;
import java.io.ObjectInputStream;

// RI import
import javax.management.loading.ClassLoaderRepository;

/**
 * <p>This is the interface for MBean manipulation on the agent
 * side. It contains the methods necessary for the creation,
 * registration, and deletion of MBeans as well as the access methods
 * for registered MBeans.  This is the core component of the JMX
 * infrastructure.</p>
 *
 * <p>User code does not usually implement this interface.  Instead,
 * an object that implements this interface is obtained with one of
 * the methods in the {@link javax.management.MBeanServerFactory} class.</p>
 *
 * <p>Every MBean which is added to the MBean server becomes
 * manageable: its attributes and operations become remotely
 * accessible through the connectors/adaptors connected to that MBean
 * server.  A Java object cannot be registered in the MBean server
 * unless it is a JMX compliant MBean.</p>
 *
 * <p id="notif">When an MBean is registered or unregistered in the
 * MBean server a {@link javax.management.MBeanServerNotification
 * MBeanServerNotification} Notification is emitted. To register an
 * object as listener to MBeanServerNotifications you should call the
 * MBean server method {@link #addNotificationListener
 * addNotificationListener} with <CODE>ObjectName</CODE> the
 * <CODE>ObjectName</CODE> of the {@link
 * javax.management.MBeanServerDelegate MBeanServerDelegate}.  This
 * <CODE>ObjectName</CODE> is: <BR>
 * <CODE>JMImplementation:type=MBeanServerDelegate</CODE>.</p>
 *
 * <p>An object obtained from the {@link
 * MBeanServerFactory#createMBeanServer(String) createMBeanServer} or
 * {@link MBeanServerFactory#newMBeanServer(String) newMBeanServer}
 * methods of the {@link MBeanServerFactory} class applies security
 * checks to its methods, as follows.</p>
 *
 * <p>First, if there is no security manager ({@link
 * System#getSecurityManager()} is null), then an implementation of
 * this interface is free not to make any checks.</p>
 *
 * <p>Assuming that there is a security manager, or that the
 * implementation chooses to make checks anyway, the checks are made
 * as detailed below.  In what follows, and unless otherwise specified,
 * {@code className} is the
 * string returned by {@link MBeanInfo#getClassName()} for the target
 * MBean.</p>
 *
 * <p>If a security check fails, the method throws {@link
 * SecurityException}.</p>
 *
 * <p>For methods that can throw {@link InstanceNotFoundException},
 * this exception is thrown for a non-existent MBean, regardless of
 * permissions.  This is because a non-existent MBean has no
 * <code>className</code>.</p>
 *
 * <ul>
 *
 * <li><p>For the {@link #invoke invoke} method, the caller's
 * permissions must imply {@link
 * MBeanPermission#MBeanPermission(String,String,ObjectName,String)
 * MBeanPermission(className, operationName, name, "invoke")}.</p>
 *
 * <li><p>For the {@link #getAttribute getAttribute} method, the
 * caller's permissions must imply {@link
 * MBeanPermission#MBeanPermission(String,String,ObjectName,String)
 * MBeanPermission(className, attribute, name, "getAttribute")}.</p>
 *
 * <li><p>For the {@link #getAttributes getAttributes} method, the
 * caller's permissions must imply {@link
 * MBeanPermission#MBeanPermission(String,String,ObjectName,String)
 * MBeanPermission(className, null, name, "getAttribute")}.
 * Additionally, for each attribute <em>a</em> in the {@link
 * AttributeList}, if the caller's permissions do not imply {@link
 * MBeanPermission#MBeanPermission(String,String,ObjectName,String)
 * MBeanPermission(className, <em>a</em>, name, "getAttribute")}, the
 * MBean server will behave as if that attribute had not been in the
 * supplied list.</p>
 *
 * <li><p>For the {@link #setAttribute setAttribute} method, the
 * caller's permissions must imply {@link
 * MBeanPermission#MBeanPermission(String,String,ObjectName,String)
 * MBeanPermission(className, attrName, name, "setAttribute")}, where
 * <code>attrName</code> is {@link Attribute#getName()
 * attribute.getName()}.</p>
 *
 * <li><p>For the {@link #setAttributes setAttributes} method, the
 * caller's permissions must imply {@link
 * MBeanPermission#MBeanPermission(String,String,ObjectName,String)
 * MBeanPermission(className, null, name, "setAttribute")}.
 * Additionally, for each attribute <em>a</em> in the {@link
 * AttributeList}, if the caller's permissions do not imply {@link
 * MBeanPermission#MBeanPermission(String,String,ObjectName,String)
 * MBeanPermission(className, <em>a</em>, name, "setAttribute")}, the
 * MBean server will behave as if that attribute had not been in the
 * supplied list.</p>
 *
 * <li><p>For the <code>addNotificationListener</code> methods,
 * the caller's permissions must imply {@link
 * MBeanPermission#MBeanPermission(String,String,ObjectName,String)
 * MBeanPermission(className, null, name,
 * "addNotificationListener")}.</p>
 *
 * <li><p>For the <code>removeNotificationListener</code> methods,
 * the caller's permissions must imply {@link
 * MBeanPermission#MBeanPermission(String,String,ObjectName,String)
 * MBeanPermission(className, null, name,
 * "removeNotificationListener")}.</p>
 *
 * <li><p>For the {@link #getMBeanInfo getMBeanInfo} method, the
 * caller's permissions must imply {@link
 * MBeanPermission#MBeanPermission(String,String,ObjectName,String)
 * MBeanPermission(className, null, name, "getMBeanInfo")}.</p>
 *
 * <li><p>For the {@link #getObjectInstance getObjectInstance} method,
 * the caller's permissions must imply {@link
 * MBeanPermission#MBeanPermission(String,String,ObjectName,String)
 * MBeanPermission(className, null, name, "getObjectInstance")}.</p>
 *
 * <li><p>For the {@link #isInstanceOf isInstanceOf} method, the
 * caller's permissions must imply {@link
 * MBeanPermission#MBeanPermission(String,String,ObjectName,String)
 * MBeanPermission(className, null, name, "isInstanceOf")}.</p>
 *
 * <li><p>For the {@link #queryMBeans queryMBeans} method, the
 * caller's permissions must imply {@link
 * MBeanPermission#MBeanPermission(String,String,ObjectName,String)
 * MBeanPermission(null, null, null, "queryMBeans")}.
 * Additionally, for each MBean <em>n</em> that matches <code>name</code>,
 * if the caller's permissions do not imply {@link
 * MBeanPermission#MBeanPermission(String,String,ObjectName,String)
 * MBeanPermission(className, null, <em>n</em>, "queryMBeans")}, the
 * MBean server will behave as if that MBean did not exist.</p>
 *
 * <p>Certain query elements perform operations on the MBean server.
 * If the caller does not have the required permissions for a given
 * MBean, that MBean will not be included in the result of the query.
 * The standard query elements that are affected are {@link
 * Query#attr(String)}, {@link Query#attr(String,String)}, and {@link
 * Query#classattr()}.</p>
 *
 * <li><p>For the {@link #queryNames queryNames} method, the checks
 * are the same as for <code>queryMBeans</code> except that
 * <code>"queryNames"</code> is used instead of
 * <code>"queryMBeans"</code> in the <code>MBeanPermission</code>
 * objects.  Note that a <code>"queryMBeans"</code> permission implies
 * the corresponding <code>"queryNames"</code> permission.</p>
 *
 * <li><p>For the {@link #getDomains getDomains} method, the caller's
 * permissions must imply {@link
 * MBeanPermission#MBeanPermission(String,String,ObjectName,String)
 * MBeanPermission(null, null, null, "getDomains")}.  Additionally,
 * for each domain <var>d</var> in the returned array, if the caller's
 * permissions do not imply {@link
 * MBeanPermission#MBeanPermission(String,String,ObjectName,String)
 * MBeanPermission(null, null, new ObjectName("<var>d</var>:x=x"),
 * "getDomains")}, the domain is eliminated from the array.  Here,
 * <code>x=x</code> is any <var>key=value</var> pair, needed to
 * satisfy ObjectName's constructor but not otherwise relevant.</p>
 *
 * <li><p>For the {@link #getClassLoader getClassLoader} method, the
 * caller's permissions must imply {@link
 * MBeanPermission#MBeanPermission(String,String,ObjectName,String)
 * MBeanPermission(className, null, loaderName,
 * "getClassLoader")}.</p>
 *
 * <li><p>For the {@link #getClassLoaderFor getClassLoaderFor} method,
 * the caller's permissions must imply {@link
 * MBeanPermission#MBeanPermission(String,String,ObjectName,String)
 * MBeanPermission(className, null, mbeanName,
 * "getClassLoaderFor")}.</p>
 *
 * <li><p>For the {@link #getClassLoaderRepository
 * getClassLoaderRepository} method, the caller's permissions must
 * imply {@link
 * MBeanPermission#MBeanPermission(String,String,ObjectName,String)
 * MBeanPermission(null, null, null, "getClassLoaderRepository")}.</p>
 *
 * <li><p>For the deprecated <code>deserialize</code> methods, the
 * required permissions are the same as for the methods that replace
 * them.</p>
 *
 * <li><p>For the <code>instantiate</code> methods, the caller's
 * permissions must imply {@link
 * MBeanPermission#MBeanPermission(String,String,ObjectName,String)
 * MBeanPermission(className, null, null, "instantiate")},
 * where {@code className} is the name of the class which is to
 * be instantiated.</p>
 *
 * <li><p>For the {@link #registerMBean registerMBean} method, the
 * caller's permissions must imply {@link
 * MBeanPermission#MBeanPermission(String,String,ObjectName,String)
 * MBeanPermission(className, null, name, "registerMBean")}.
 *
 * <p>If the <code>MBeanPermission</code> check succeeds, the MBean's
 * class is validated by checking that its {@link
 * java.security.ProtectionDomain ProtectionDomain} implies {@link
 * MBeanTrustPermission#MBeanTrustPermission(String)
 * MBeanTrustPermission("register")}.</p>
 *
 * <p>Finally, if the <code>name</code> argument is null, another
 * <code>MBeanPermission</code> check is made using the
 * <code>ObjectName</code> returned by {@link
 * MBeanRegistration#preRegister MBeanRegistration.preRegister}.</p>
 *
 * <li><p>For the <code>createMBean</code> methods, the caller's
 * permissions must imply the permissions needed by the equivalent
 * <code>instantiate</code> followed by
 * <code>registerMBean</code>.</p>
 *
 * <li><p>For the {@link #unregisterMBean unregisterMBean} method,
 * the caller's permissions must imply {@link
 * MBeanPermission#MBeanPermission(String,String,ObjectName,String)
 * MBeanPermission(className, null, name, "unregisterMBean")}.</p>
 *
 * </ul>
 *
 * <p>
 *  <p>这是代理端的MBean操作界面。它包含创建,注册和删除MBeans所需的方法以及注册MBean的访问方法。这是JMX基础架构的核心组件。</p>
 * 
 *  <p>用户代码通常不实现此接口。相反,实现此接口的对象是通过{@link javax.management.MBeanServerFactory}类中的一个方法获得的。</p>
 * 
 *  <p>添加到MBean服务器的每个MBean都变得易于管理：其属性和操作可通过连接到该MBean服务器的连接器/适配器远程访问。
 * 除非是符合JMX的MBean,否则不能在MBean服务器中注册Java对象。</p>。
 * 
 *  <p id ="notif">当MBean在MBean服务器中注册或注销时,会发出{@link javax.management.MBeanServerNotification MBeanServerNotification}
 * 通知。
 * 要将对象注册为监听器到MBeanServerNotifications,您应该使用<CODE> ObjectName </CODE>来调用MBean服务器方法{@link #addNotificationListener addNotificationListener}
 *  {@link javax.management.MBeanServerDelegate MBeanServerDelegate}。
 * 此<CODE> ObjectName </CODE>是：<BR> <CODE> JMImplementation：type = MBeanServerDelegate </CODE>。</p>。
 * 
 * <p>从{@link MBeanServerFactory}类的{@link MBeanServerFactory#createMBeanServer(String)createMBeanServer}
 * 或{@link MBeanServerFactory#newMBeanServer(String)newMBeanServer}方法获得的对象会对其方法应用安全检查,如下所示。
 *  </p>。
 * 
 *  <p>首先,如果没有安全管理器({@link System#getSecurityManager()}为null),那么这个接口的实现是免费的,不进行任何检查。</p>
 * 
 *  <p>假设有一个安全管理器,或者实现选择进行检查,检查是如下详述的。
 * 在下面的内容中,除非另有规定,{@code className}是目标MBean的{@link MBeanInfo#getClassName()}返回的字符串。</p>。
 * 
 *  <p>如果安全检查失败,该方法将抛出{@link SecurityException}。</p>
 * 
 *  <p>对于可以抛出{@link InstanceNotFoundException}的方法,无论权限如何,对不存在的MBean都抛出此异常。
 * 这是因为不存在的MBean没有<code> className </code>。</p>。
 * 
 * <ul>
 * 
 *  <li> <p>对于{@link #invoke invoke}方法,调用者的权限必须暗示{@link MBeanPermission#MBeanPermission(String,String,ObjectName,String)MBeanPermission(className,operationName,name,"invoke")}
 *  。
 * </p>。
 * 
 *  <li> <p>对于{@link #getAttribute getAttribute}方法,调用者的权限必须暗示{@link MBeanPermission#MBeanPermission(String,String,ObjectName,String)MBeanPermission(className,attribute,name,"getAttribute")}
 *  。
 * </p>。
 * 
 * <li> <p>对于{@link #getAttributes getAttributes}方法,调用者的权限必须暗示{@link MBeanPermission#MBeanPermission(String,String,ObjectName,String)MBeanPermission(className,null,name,"getAttribute")}
 *  。
 * 此外,对于{@link AttributeList}中的每个属性</em>,如果调用者的权限不暗示{@link MBeanPermission#MBeanPermission(String,String,ObjectName,String)MBeanPermission(className,<em> a </em>,name,"getAttribute")}
 * ,MBean服务器的行为就像该属性没有在提供的列表中一样。
 * </p>。
 * 
 *  <li> <p>对于{@link #setAttribute setAttribute}方法,调用者的权限必须暗示{@link MBeanPermission#MBeanPermission(String,String,ObjectName,String)MBeanPermission(className,attrName,name,"setAttribute")}
 *  ,其中<code> attrName </code>是{@link Attribute#getName()attribute.getName()}。
 * </p>。
 * 
 *  <li> <p>对于{@link #setAttributes setAttributes}方法,调用者的权限必须暗示{@link MBeanPermission#MBeanPermission(String,String,ObjectName,String)MBeanPermission(className,null,name,"setAttribute")}
 *  。
 * 此外,对于{@link AttributeList}中的每个属性</em>,如果调用者的权限不暗示{@link MBeanPermission#MBeanPermission(String,String,ObjectName,String)MBeanPermission(className,<em> a </em>,name,"setAttribute")}
 * ,MBean服务器的行为就像该属性没有在提供的列表中一样。
 * </p>。
 * 
 * <li> <p>对于<code> addNotificationListener </code>方法,调用者的权限必须暗示{@link MBeanPermission#MBeanPermission(String,String,ObjectName,String)MBeanPermission(className,null,name,"addNotificationListener") }
 * 。
 * </p>。
 * 
 *  <li> <p>对于<code> removeNotificationListener </code>方法,调用者的权限必须暗示{@link MBeanPermission#MBeanPermission(String,String,ObjectName,String)MBeanPermission(className,null,name,"removeNotificationListener") }
 * 。
 * </p>。
 * 
 *  <li> <p>对于{@link #getMBeanInfo getMBeanInfo}方法,调用者的权限必须暗示{@link MBeanPermission#MBeanPermission(String,String,ObjectName,String)MBeanPermission(className,null,name,"getMBeanInfo")}
 *  。
 * </p>。
 * 
 *  <li> <p>对于{@link #getObjectInstance getObjectInstance}方法,调用者的权限必须暗示{@link MBeanPermission#MBeanPermission(String,String,ObjectName,String)MBeanPermission(className,null,name,"getObjectInstance")}
 *  。
 * </p>。
 * 
 *  <li> <p>对于{@link #isInstanceOf isInstanceOf}方法,调用者的权限必须暗示{@link MBeanPermission#MBeanPermission(String,String,ObjectName,String)MBeanPermission(className,null,name,"isInstanceOf")}
 *  。
 * </p>。
 * 
 * <li> <p>对于{@link #queryMBeans queryMBeans}方法,调用者的权限必须暗示{@link MBeanPermission#MBeanPermission(String,String,ObjectName,String)MBeanPermission(null,null,null,"queryMBeans")}
 *  。
 * 此外,对于与<code> name </code>匹配的每个MBean n </em>,如果调用者的权限不暗示{@link MBeanPermission#MBeanPermission(String,String,ObjectName,String)MBeanPermission(className, null,<em> n </em>,"queryMBeans")}
 * ,MBean服务器将表现为该MBean不存在。
 * </p>。
 * 
 *  <p>某些查询元素在MBean服务器上执行操作。如果调用者不具有给定MBean的所需权限,则该MBean将不包括在查询的结果中。
 * 受影响的标准查询元素是{@link Query#attr(String)},{@link Query#attr(String,String)}和{@link Query#classattr()}。
 * </p>。
 * 
 *  <li> <p>对于{@link #queryNames queryNames}方法,除了使用<code>"queryNames"</code>代替<code>之前,<code> queryMBean
 * s </code> >"queryMBeans"</code>在<code> MBeanPermission </code>对象中。
 * 请注意,<code>"queryMBeans"</code>权限意味着相应的<code>"queryNames"</code>权限。</p>。
 * 
 * <li> <p>对于{@link #getDomains getDomains}方法,调用者的权限必须暗示{@link MBeanPermission#MBeanPermission(String,String,ObjectName,String)MBeanPermission(null,null,null,"getDomains")}
 *  。
 * 此外,对于返回数组中的每个域<var> d </var>,如果调用者的权限不暗示{@link MBeanPermission#MBeanPermission(String,String,ObjectName,String)MBeanPermission(null,null,new ObjectName <var> d </var>：x = x"),"getDomains")}
 * ,域从数组中删除。
 * 这里,<code> x = x </code>是任何<var> key = value </var>对,需要满足ObjectName的构造函数,。
 * 
 *  <li> <p>对于{@link #getClassLoader getClassLoader}方法,调用者的权限必须暗示{@link MBeanPermission#MBeanPermission(String,String,ObjectName,String)MBeanPermission(className,null,loaderName,"getClassLoader")}
 *  。
 * </p>。
 * 
 *  <li> <p>对于{@link #getClassLoaderFor getClassLoaderFor}方法,调用者的权限必须暗示{@link MBeanPermission#MBeanPermission(String,String,ObjectName,String)MBeanPermission(className,null,mbeanName,"getClassLoaderFor")}
 *  。
 * </p>。
 * 
 *  <li> <p>对于{@link #getClassLoaderRepository getClassLoaderRepository}方法,调用者的权限必须暗示{@link MBeanPermission#MBeanPermission(String,String,ObjectName,String)MBeanPermission(null,null,null,"getClassLoaderRepository")}
 *  。
 * </p>。
 * 
 * <li> <p>对于已弃用的<code> deserialize </code>方法,所需的权限与替换它们的方法相同。</p>
 * 
 *  <li> <p>对于<code> instantiate </code>方法,调用者的权限必须暗示{@link MBeanPermission#MBeanPermission(String,String,ObjectName,String)MBeanPermission(className,null,null,"instantiate") }
 * ,其中{@code className}是要实例化的类的名称。
 * </p>。
 * 
 *  <li> <p>对于{@link #registerMBean registerMBean}方法,调用者的权限必须暗示{@link MBeanPermission#MBeanPermission(String,String,ObjectName,String)MBeanPermission(className,null,name,"registerMBean")}
 *  。
 * 
 * 
 * @since 1.5
 */

/* DELETED:
 *
 * <li><p>For the {@link #isRegistered isRegistered} method, the
 * caller's permissions must imply {@link
 * MBeanPermission#MBeanPermission(String,String,ObjectName,String)
 * MBeanPermission(null, null, name, "isRegistered")}.</p>
 * <p>
 *  <p>如果<code> MBeanPermission </code>检查成功,MBean的类将通过检查其{@link java.security.ProtectionDomain ProtectionDomain}
 * 隐含{@link MBeanTrustPermission#MBeanTrustPermission(String)MBeanTrustPermission("register" )}。
 * </p>。
 * 
 *  <p>最后,如果<code> name </code>参数为null,则使用{@link MBeanRegistration#preRegister MBeanRegistration}返回的<code>
 *  ObjectName </code>进行另一个<code> MBeanPermission </code> .preRegister}。
 * </p>。
 * 
 *  <li> <p>对于<code> createMBean </code>方法,调用者的权限必须暗示等效的<code>实例化</code>后面跟<code> registerMBean </code> 
 * p>。
 * 
 * <li> <p>对于{@link #unregisterMBean unregisterMBean}方法,调用者的权限必须暗示{@link MBeanPermission#MBeanPermission(String,String,ObjectName,String)MBeanPermission(className,null,name,"unregisterMBean")}
 *  。
 * </p>。
 * 
 * </ul>
 * 
 */
public interface MBeanServer extends MBeanServerConnection {

    /**
     * {@inheritDoc}
     * <p>If this method successfully creates an MBean, a notification
     * is sent as described <a href="#notif">above</a>.</p>
     *
     * <p>
     *  <li> <p>对于{@link #isRegistered isRegistered}方法,调用者的权限必须暗示{@link MBeanPermission#MBeanPermission(String,String,ObjectName,String)MBeanPermission(null,null,name,"isRegistered")}
     *  。
     * </p>。
     * 
     * 
     * @throws RuntimeOperationsException {@inheritDoc}
     * @throws RuntimeMBeanException {@inheritDoc}
     * @throws RuntimeErrorException {@inheritDoc}
     */
    public ObjectInstance createMBean(String className, ObjectName name)
            throws ReflectionException, InstanceAlreadyExistsException,
                   MBeanRegistrationException, MBeanException,
                   NotCompliantMBeanException;

    /**
     * {@inheritDoc}
     * <p>If this method successfully creates an MBean, a notification
     * is sent as described <a href="#notif">above</a>.</p>
     *
     * <p>
     *  {@inheritDoc} <p>如果此方法成功创建了MBean,则会按照<a href="#notif">上述</a>所述发送通知。</p>
     * 
     * 
     * @throws RuntimeOperationsException {@inheritDoc}
     * @throws RuntimeMBeanException {@inheritDoc}
     * @throws RuntimeErrorException {@inheritDoc}
     */
    public ObjectInstance createMBean(String className, ObjectName name,
                                      ObjectName loaderName)
            throws ReflectionException, InstanceAlreadyExistsException,
                   MBeanRegistrationException, MBeanException,
                   NotCompliantMBeanException, InstanceNotFoundException;

    /**
     * {@inheritDoc}
     * <p>If this method successfully creates an MBean, a notification
     * is sent as described <a href="#notif">above</a>.</p>
     *
     * <p>
     *  {@inheritDoc} <p>如果此方法成功创建了MBean,则会按照<a href="#notif">上述</a>所述发送通知。</p>
     * 
     * 
     * @throws RuntimeOperationsException {@inheritDoc}
     * @throws RuntimeMBeanException {@inheritDoc}
     * @throws RuntimeErrorException {@inheritDoc}
     */
    public ObjectInstance createMBean(String className, ObjectName name,
                                      Object params[], String signature[])
            throws ReflectionException, InstanceAlreadyExistsException,
                   MBeanRegistrationException, MBeanException,
                   NotCompliantMBeanException;

    /**
     * {@inheritDoc}
     * <p>If this method successfully creates an MBean, a notification
     * is sent as described <a href="#notif">above</a>.</p>
     *
     * <p>
     *  {@inheritDoc} <p>如果此方法成功创建了MBean,则会按照<a href="#notif">上述</a>所述发送通知。</p>
     * 
     * 
     * @throws RuntimeOperationsException {@inheritDoc}
     * @throws RuntimeMBeanException {@inheritDoc}
     * @throws RuntimeErrorException {@inheritDoc}
     */
    public ObjectInstance createMBean(String className, ObjectName name,
                                      ObjectName loaderName, Object params[],
                                      String signature[])
            throws ReflectionException, InstanceAlreadyExistsException,
                   MBeanRegistrationException, MBeanException,
                   NotCompliantMBeanException, InstanceNotFoundException;

    /**
     * <p>Registers a pre-existing object as an MBean with the MBean
     * server. If the object name given is null, the MBean must
     * provide its own name by implementing the {@link
     * javax.management.MBeanRegistration MBeanRegistration} interface
     * and returning the name from the {@link
     * MBeanRegistration#preRegister preRegister} method.
     *
     * <p>If this method successfully registers an MBean, a notification
     * is sent as described <a href="#notif">above</a>.</p>
     *
     * <p>
     *  {@inheritDoc} <p>如果此方法成功创建了MBean,则会按照<a href="#notif">上述</a>所述发送通知。</p>
     * 
     * 
     * @param object The  MBean to be registered as an MBean.
     * @param name The object name of the MBean. May be null.
     *
     * @return An <CODE>ObjectInstance</CODE>, containing the
     * <CODE>ObjectName</CODE> and the Java class name of the newly
     * registered MBean.  If the contained <code>ObjectName</code>
     * is <code>n</code>, the contained Java class name is
     * <code>{@link #getMBeanInfo getMBeanInfo(n)}.getClassName()</code>.
     *
     * @exception InstanceAlreadyExistsException The MBean is already
     * under the control of the MBean server.
     * @exception MBeanRegistrationException The
     * <CODE>preRegister</CODE> (<CODE>MBeanRegistration</CODE>
     * interface) method of the MBean has thrown an exception. The
     * MBean will not be registered.
     * @exception RuntimeMBeanException If the <CODE>postRegister</CODE>
     * (<CODE>MBeanRegistration</CODE> interface) method of the MBean throws a
     * <CODE>RuntimeException</CODE>, the <CODE>registerMBean</CODE> method will
     * throw a <CODE>RuntimeMBeanException</CODE>, although the MBean
     * registration succeeded. In such a case, the MBean will be actually
     * registered even though the <CODE>registerMBean</CODE> method
     * threw an exception.  Note that <CODE>RuntimeMBeanException</CODE> can
     * also be thrown by <CODE>preRegister</CODE>, in which case the MBean
     * will not be registered.
     * @exception RuntimeErrorException If the <CODE>postRegister</CODE>
     * (<CODE>MBeanRegistration</CODE> interface) method of the MBean throws an
     * <CODE>Error</CODE>, the <CODE>registerMBean</CODE> method will
     * throw a <CODE>RuntimeErrorException</CODE>, although the MBean
     * registration succeeded. In such a case, the MBean will be actually
     * registered even though the <CODE>registerMBean</CODE> method
     * threw an exception.  Note that <CODE>RuntimeErrorException</CODE> can
     * also be thrown by <CODE>preRegister</CODE>, in which case the MBean
     * will not be registered.
     * @exception NotCompliantMBeanException This object is not a JMX
     * compliant MBean
     * @exception RuntimeOperationsException Wraps a
     * <CODE>java.lang.IllegalArgumentException</CODE>: The object
     * passed in parameter is null or no object name is specified.
     * @see javax.management.MBeanRegistration
     */
    public ObjectInstance registerMBean(Object object, ObjectName name)
            throws InstanceAlreadyExistsException, MBeanRegistrationException,
                   NotCompliantMBeanException;

    /**
     * {@inheritDoc}
     *
     * <p>If this method successfully unregisters an MBean, a notification
     * is sent as described <a href="#notif">above</a>.</p>
     *
     * <p>
     *  <p>将预先存在的对象作为MBean注册到MBean服务器。
     * 如果给定的对象名为null,MBean必须通过实现{@link javax.management.MBeanRegistration MBeanRegistration}接口并从{@link MBeanRegistration#preRegister preRegister}
     * 方法返回名称来提供自己的名称。
     *  <p>将预先存在的对象作为MBean注册到MBean服务器。
     * 
     *  <p>如果此方法成功注册MBean,则会按照<a href="#notif">上述</a>中所述发送通知。</p>
     * 
     * 
     * @throws RuntimeOperationsException {@inheritDoc}
     * @throws RuntimeMBeanException {@inheritDoc}
     * @throws RuntimeErrorException {@inheritDoc}
     */
    public void unregisterMBean(ObjectName name)
            throws InstanceNotFoundException, MBeanRegistrationException;

    // doc comment inherited from MBeanServerConnection
    public ObjectInstance getObjectInstance(ObjectName name)
            throws InstanceNotFoundException;

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * <p>如果此方法成功取消注册MBean,则会按照<a href="#notif">上述</a>所述发送通知。</p>
     * 
     * 
      * @throws RuntimeOperationsException {@inheritDoc}
     */
    public Set<ObjectInstance> queryMBeans(ObjectName name, QueryExp query);

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
      * @throws RuntimeOperationsException {@inheritDoc}
    */
    public Set<ObjectName> queryNames(ObjectName name, QueryExp query);

    // doc comment inherited from MBeanServerConnection
    /**
    /* <p>
    /*  {@inheritDoc}
    /* 
    /* 
     * @throws RuntimeOperationsException {@inheritDoc}
     */
    public boolean isRegistered(ObjectName name);

    /**
     * Returns the number of MBeans registered in the MBean server.
     *
     * <p>
     * 
     * @return the number of registered MBeans, wrapped in an Integer.
     * If the caller's permissions are restricted, this number may
     * be greater than the number of MBeans the caller can access.
     */
    public Integer getMBeanCount();

    // doc comment inherited from MBeanServerConnection
    /**
    /* <p>
    /*  返回在MBean服务器中注册的MBean数。
    /* 
    /* 
     * @throws RuntimeOperationsException {@inheritDoc}
     */
    public Object getAttribute(ObjectName name, String attribute)
            throws MBeanException, AttributeNotFoundException,
                   InstanceNotFoundException, ReflectionException;

    // doc comment inherited from MBeanServerConnection
    /**
    /* <p>
    /* 
     * @throws RuntimeOperationsException {@inheritDoc}
     */
    public AttributeList getAttributes(ObjectName name, String[] attributes)
            throws InstanceNotFoundException, ReflectionException;

    // doc comment inherited from MBeanServerConnection
    /**
    /* <p>
    /* 
     * @throws RuntimeOperationsException {@inheritDoc}
     */
    public void setAttribute(ObjectName name, Attribute attribute)
            throws InstanceNotFoundException, AttributeNotFoundException,
                   InvalidAttributeValueException, MBeanException,
                   ReflectionException;

    // doc comment inherited from MBeanServerConnection
    /**
    /* <p>
    /* 
     * @throws RuntimeOperationsException {@inheritDoc}
     */
    public AttributeList setAttributes(ObjectName name,
                                       AttributeList attributes)
        throws InstanceNotFoundException, ReflectionException;

    // doc comment inherited from MBeanServerConnection
    public Object invoke(ObjectName name, String operationName,
                         Object params[], String signature[])
            throws InstanceNotFoundException, MBeanException,
                   ReflectionException;

    // doc comment inherited from MBeanServerConnection
    public String getDefaultDomain();

    // doc comment inherited from MBeanServerConnection
    public String[] getDomains();

    // doc comment inherited from MBeanServerConnection, plus:
    /**
     * {@inheritDoc}
     * If the source of the notification
     * is a reference to an MBean object, the MBean server will replace it
     * by that MBean's ObjectName.  Otherwise the source is unchanged.
     * <p>
     */
    public void addNotificationListener(ObjectName name,
                                        NotificationListener listener,
                                        NotificationFilter filter,
                                        Object handback)
            throws InstanceNotFoundException;

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}如果通知的来源是对MBean对象的引用,则MBean服务器将用该MBean的ObjectName替换它。否则源不变。
     * 
     * 
     * @throws RuntimeOperationsException {@inheritDoc}
     */
    public void addNotificationListener(ObjectName name,
                                        ObjectName listener,
                                        NotificationFilter filter,
                                        Object handback)
            throws InstanceNotFoundException;

    // doc comment inherited from MBeanServerConnection
    public void removeNotificationListener(ObjectName name,
                                           ObjectName listener)
        throws InstanceNotFoundException, ListenerNotFoundException;

    // doc comment inherited from MBeanServerConnection
    public void removeNotificationListener(ObjectName name,
                                           ObjectName listener,
                                           NotificationFilter filter,
                                           Object handback)
            throws InstanceNotFoundException, ListenerNotFoundException;

    // doc comment inherited from MBeanServerConnection
    public void removeNotificationListener(ObjectName name,
                                           NotificationListener listener)
            throws InstanceNotFoundException, ListenerNotFoundException;

    // doc comment inherited from MBeanServerConnection
    public void removeNotificationListener(ObjectName name,
                                           NotificationListener listener,
                                           NotificationFilter filter,
                                           Object handback)
            throws InstanceNotFoundException, ListenerNotFoundException;

    // doc comment inherited from MBeanServerConnection
    public MBeanInfo getMBeanInfo(ObjectName name)
            throws InstanceNotFoundException, IntrospectionException,
                   ReflectionException;


    // doc comment inherited from MBeanServerConnection
    public boolean isInstanceOf(ObjectName name, String className)
            throws InstanceNotFoundException;

    /**
     * <p>Instantiates an object using the list of all class loaders
     * registered in the MBean server's {@link
     * javax.management.loading.ClassLoaderRepository Class Loader
     * Repository}.  The object's class should have a public
     * constructor.  This method returns a reference to the newly
     * created object.  The newly created object is not registered in
     * the MBean server.</p>
     *
     * <p>This method is equivalent to {@link
     * #instantiate(String,Object[],String[])
     * instantiate(className, (Object[]) null, (String[]) null)}.</p>
     *
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @param className The class name of the object to be instantiated.
     *
     * @return The newly instantiated object.
     *
     * @exception ReflectionException Wraps a
     * <CODE>java.lang.ClassNotFoundException</CODE> or the
     * <CODE>java.lang.Exception</CODE> that occurred when trying to
     * invoke the object's constructor.
     * @exception MBeanException The constructor of the object has
     * thrown an exception
     * @exception RuntimeOperationsException Wraps a
     * <CODE>java.lang.IllegalArgumentException</CODE>: The className
     * passed in parameter is null.
     */
    public Object instantiate(String className)
            throws ReflectionException, MBeanException;


    /**
     * <p>Instantiates an object using the class Loader specified by its
     * <CODE>ObjectName</CODE>.  If the loader name is null, the
     * ClassLoader that loaded the MBean Server will be used.  The
     * object's class should have a public constructor.  This method
     * returns a reference to the newly created object.  The newly
     * created object is not registered in the MBean server.</p>
     *
     * <p>This method is equivalent to {@link
     * #instantiate(String,ObjectName,Object[],String[])
     * instantiate(className, loaderName, (Object[]) null, (String[])
     * null)}.</p>
     *
     * <p>
     *  <p>使用在MBean服务器的{@link javax.management.loading.ClassLoaderRepository Class Loader Repository}中注册的所有类
     * 加载器的列表来实例化对象。
     * 对象的类应该有一个public构造函数。此方法返回对新创建对象的引用。新创建的对象未在MBean服务器中注册。</p>。
     * 
     *  <p>此方法等效于{@link #instantiate(String,Object [],String [])instantiate(className,(Object [])null,(String [])null)}
     * 。
     * 
     * 
     * @param className The class name of the MBean to be instantiated.
     * @param loaderName The object name of the class loader to be used.
     *
     * @return The newly instantiated object.
     *
     * @exception ReflectionException Wraps a
     * <CODE>java.lang.ClassNotFoundException</CODE> or the
     * <CODE>java.lang.Exception</CODE> that occurred when trying to
     * invoke the object's constructor.
     * @exception MBeanException The constructor of the object has
     * thrown an exception.
     * @exception InstanceNotFoundException The specified class loader
     * is not registered in the MBeanServer.
     * @exception RuntimeOperationsException Wraps a
     * <CODE>java.lang.IllegalArgumentException</CODE>: The className
     * passed in parameter is null.
     */
    public Object instantiate(String className, ObjectName loaderName)
            throws ReflectionException, MBeanException,
                   InstanceNotFoundException;

    /**
     * <p>Instantiates an object using the list of all class loaders
     * registered in the MBean server {@link
     * javax.management.loading.ClassLoaderRepository Class Loader
     * Repository}.  The object's class should have a public
     * constructor.  The call returns a reference to the newly created
     * object.  The newly created object is not registered in the
     * MBean server.</p>
     *
     * <p>
     *  <p>使用由其<CODE> ObjectName </CODE>指定的类Loader实例化对象。如果加载程序名称为null,将使用加载MBean Server的ClassLoader。
     * 对象的类应该有一个public构造函数。此方法返回对新创建对象的引用。新创建的对象未在MBean服务器中注册。</p>。
     * 
     *  <p>此方法等效于{@link #instantiate(String,ObjectName,Object [],String [])instantiate(className,loaderName,(Object [])null,(String [])null)}
     * 。
     *  p>。
     * 
     * 
     * @param className The class name of the object to be instantiated.
     * @param params An array containing the parameters of the
     * constructor to be invoked.
     * @param signature An array containing the signature of the
     * constructor to be invoked.
     *
     * @return The newly instantiated object.
     *
     * @exception ReflectionException Wraps a
     * <CODE>java.lang.ClassNotFoundException</CODE> or the
     * <CODE>java.lang.Exception</CODE> that occurred when trying to
     * invoke the object's constructor.
     * @exception MBeanException The constructor of the object has
     * thrown an exception
     * @exception RuntimeOperationsException Wraps a
     * <CODE>java.lang.IllegalArgumentException</CODE>: The className
     * passed in parameter is null.
     */
    public Object instantiate(String className, Object params[],
                              String signature[])
            throws ReflectionException, MBeanException;

    /**
     * <p>Instantiates an object. The class loader to be used is
     * identified by its object name. If the object name of the loader
     * is null, the ClassLoader that loaded the MBean server will be
     * used.  The object's class should have a public constructor.
     * The call returns a reference to the newly created object.  The
     * newly created object is not registered in the MBean server.</p>
     *
     * <p>
     * <p>使用在MBean服务器{@link javax.management.loading.ClassLoaderRepository Class Loader Repository}中注册的所有类加载
     * 器的列表实例化对象。
     * 对象的类应该有一个public构造函数。该调用返回对新创建对象的引用。新创建的对象未在MBean服务器中注册。</p>。
     * 
     * 
     * @param className The class name of the object to be instantiated.
     * @param params An array containing the parameters of the
     * constructor to be invoked.
     * @param signature An array containing the signature of the
     * constructor to be invoked.
     * @param loaderName The object name of the class loader to be used.
     *
     * @return The newly instantiated object.
     *
     * @exception ReflectionException Wraps a <CODE>java.lang.ClassNotFoundException</CODE> or the <CODE>java.lang.Exception</CODE> that
     * occurred when trying to invoke the object's constructor.
     * @exception MBeanException The constructor of the object has
     * thrown an exception
     * @exception InstanceNotFoundException The specified class loader
     * is not registered in the MBean server.
     * @exception RuntimeOperationsException Wraps a
     * <CODE>java.lang.IllegalArgumentException</CODE>: The className
     * passed in parameter is null.
     */
    public Object instantiate(String className, ObjectName loaderName,
                              Object params[], String signature[])
            throws ReflectionException, MBeanException,
                   InstanceNotFoundException;

    /**
     * <p>De-serializes a byte array in the context of the class loader
     * of an MBean.</p>
     *
     * <p>
     *  <p>实例化对象。要使用的类加载器由其对象名称标识。如果加载器的对象名称为null,将使用加载MBean服务器的ClassLoader。对象的类应该有一个public构造函数。
     * 该调用返回对新创建对象的引用。新创建的对象未在MBean服务器中注册。</p>。
     * 
     * 
     * @param name The name of the MBean whose class loader should be
     * used for the de-serialization.
     * @param data The byte array to be de-sererialized.
     *
     * @return The de-serialized object stream.
     *
     * @exception InstanceNotFoundException The MBean specified is not
     * found.
     * @exception OperationsException Any of the usual Input/Output
     * related exceptions.
     *
     * @deprecated Use {@link #getClassLoaderFor getClassLoaderFor} to
     * obtain the appropriate class loader for deserialization.
     */
    @Deprecated
    public ObjectInputStream deserialize(ObjectName name, byte[] data)
            throws InstanceNotFoundException, OperationsException;


    /**
     * <p>De-serializes a byte array in the context of a given MBean
     * class loader.  The class loader is found by loading the class
     * <code>className</code> through the {@link
     * javax.management.loading.ClassLoaderRepository Class Loader
     * Repository}.  The resultant class's class loader is the one to
     * use.
     *
     * <p>
     *  <p>在MBean的类加载器的上下文中解序列化字节数组。</p>
     * 
     * 
     * @param className The name of the class whose class loader should be
     * used for the de-serialization.
     * @param data The byte array to be de-sererialized.
     *
     * @return  The de-serialized object stream.
     *
     * @exception OperationsException Any of the usual Input/Output
     * related exceptions.
     * @exception ReflectionException The specified class could not be
     * loaded by the class loader repository
     *
     * @deprecated Use {@link #getClassLoaderRepository} to obtain the
     * class loader repository and use it to deserialize.
     */
    @Deprecated
    public ObjectInputStream deserialize(String className, byte[] data)
            throws OperationsException, ReflectionException;


    /**
     * <p>De-serializes a byte array in the context of a given MBean
     * class loader.  The class loader is the one that loaded the
     * class with name "className".  The name of the class loader to
     * be used for loading the specified class is specified.  If null,
     * the MBean Server's class loader will be used.</p>
     *
     * <p>
     *  <p>在给定MBean类加载器的上下文中解序列化字节数组。
     * 通过{@link javax.management.loading.ClassLoaderRepository Class Loader Repository}加载类<code> className </code>
     * 可以找到类加载器。
     *  <p>在给定MBean类加载器的上下文中解序列化字节数组。结果类的类加载器是要使用的类加载器。
     * 
     * 
     * @param className The name of the class whose class loader should be
     * used for the de-serialization.
     * @param data The byte array to be de-sererialized.
     * @param loaderName The name of the class loader to be used for
     * loading the specified class.  If null, the MBean Server's class
     * loader will be used.
     *
     * @return  The de-serialized object stream.
     *
     * @exception InstanceNotFoundException The specified class loader
     * MBean is not found.
     * @exception OperationsException Any of the usual Input/Output
     * related exceptions.
     * @exception ReflectionException The specified class could not be
     * loaded by the specified class loader.
     *
     * @deprecated Use {@link #getClassLoader getClassLoader} to obtain
     * the class loader for deserialization.
     */
    @Deprecated
    public ObjectInputStream deserialize(String className,
                                         ObjectName loaderName,
                                         byte[] data)
            throws InstanceNotFoundException, OperationsException,
                   ReflectionException;

    /**
     * <p>Return the {@link java.lang.ClassLoader} that was used for
     * loading the class of the named MBean.</p>
     *
     * <p>
     *  <p>在给定MBean类加载器的上下文中解序列化字节数组。类加载器是加载类名为"className"的类加载器。指定要用于加载指定类的类加载器的名称。
     * 如果为null,将使用MBean Server的类装载器。</p>。
     * 
     * 
     * @param mbeanName The ObjectName of the MBean.
     *
     * @return The ClassLoader used for that MBean.  If <var>l</var>
     * is the MBean's actual ClassLoader, and <var>r</var> is the
     * returned value, then either:
     *
     * <ul>
     * <li><var>r</var> is identical to <var>l</var>; or
     * <li>the result of <var>r</var>{@link
     * ClassLoader#loadClass(String) .loadClass(<var>s</var>)} is the
     * same as <var>l</var>{@link ClassLoader#loadClass(String)
     * .loadClass(<var>s</var>)} for any string <var>s</var>.
     * </ul>
     *
     * What this means is that the ClassLoader may be wrapped in
     * another ClassLoader for security or other reasons.
     *
     * @exception InstanceNotFoundException if the named MBean is not found.
     *
     */
    public ClassLoader getClassLoaderFor(ObjectName mbeanName)
        throws InstanceNotFoundException;

    /**
     * <p>Return the named {@link java.lang.ClassLoader}.</p>
     *
     * <p>
     * <p>返回用于加载命名MBean的类的{@link java.lang.ClassLoader}。</p>
     * 
     * 
     * @param loaderName The ObjectName of the ClassLoader.  May be
     * null, in which case the MBean server's own ClassLoader is
     * returned.
     *
     * @return The named ClassLoader.  If <var>l</var> is the actual
     * ClassLoader with that name, and <var>r</var> is the returned
     * value, then either:
     *
     * <ul>
     * <li><var>r</var> is identical to <var>l</var>; or
     * <li>the result of <var>r</var>{@link
     * ClassLoader#loadClass(String) .loadClass(<var>s</var>)} is the
     * same as <var>l</var>{@link ClassLoader#loadClass(String)
     * .loadClass(<var>s</var>)} for any string <var>s</var>.
     * </ul>
     *
     * What this means is that the ClassLoader may be wrapped in
     * another ClassLoader for security or other reasons.
     *
     * @exception InstanceNotFoundException if the named ClassLoader is
     *    not found.
     *
     */
    public ClassLoader getClassLoader(ObjectName loaderName)
        throws InstanceNotFoundException;

    /**
     * <p>Return the ClassLoaderRepository for this MBeanServer.
     * <p>
     *  <p>返回命名的{@link java.lang.ClassLoader}。</p>
     * 
     * 
     * @return The ClassLoaderRepository for this MBeanServer.
     *
     */
    public ClassLoaderRepository getClassLoaderRepository();
}
