/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, 2013, Oracle and/or its affiliates. All rights reserved.
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
import java.io.IOException;
import java.util.Set;


/**
 * This interface represents a way to talk to an MBean server, whether
 * local or remote.  The {@link MBeanServer} interface, representing a
 * local MBean server, extends this interface.
 *
 *
 * <p>
 *  此接口表示与MBean服务器通信的方式,无论是本地还是远程。代表本地MBean服务器的{@link MBeanServer}接口扩展了此接口。
 * 
 * 
 * @since 1.5
 */
public interface MBeanServerConnection {
    /**
     * <p>Instantiates and registers an MBean in the MBean server.  The
     * MBean server will use its {@link
     * javax.management.loading.ClassLoaderRepository Default Loader
     * Repository} to load the class of the MBean.  An object name is
     * associated with the MBean.  If the object name given is null, the
     * MBean must provide its own name by implementing the {@link
     * javax.management.MBeanRegistration MBeanRegistration} interface
     * and returning the name from the {@link
     * MBeanRegistration#preRegister preRegister} method.</p>
     *
     * <p>This method is equivalent to {@link
     * #createMBean(String,ObjectName,Object[],String[])
     * createMBean(className, name, (Object[]) null, (String[])
     * null)}.</p>
     *
     * <p>
     *  <p>在MBean服务器中实例化并注册MBean。
     *  MBean服务器将使用其{@link javax.management.loading.ClassLoaderRepository Default Loader Repository}加载MBean的
     * 类。
     *  <p>在MBean服务器中实例化并注册MBean。对象名称与MBean相关联。
     * 如果对象名称为null,MBean必须通过实现{@link javax.management.MBeanRegistration MBeanRegistration}接口并返回{@link MBeanRegistration#preRegister preRegister}
     * 方法的名称来提供自己的名称。
     *  <p>在MBean服务器中实例化并注册MBean。对象名称与MBean相关联。</p>。
     * 
     *  <p>此方法相当于{@link #createMBean(String,ObjectName,Object [],String [])createMBean(className,name,(Object [])null,(String [])null)}
     * 。
     *  p>。
     * 
     * 
     * @param className The class name of the MBean to be instantiated.
     * @param name The object name of the MBean. May be null.
     *
     * @return An <CODE>ObjectInstance</CODE>, containing the
     * <CODE>ObjectName</CODE> and the Java class name of the newly
     * instantiated MBean.  If the contained <code>ObjectName</code>
     * is <code>n</code>, the contained Java class name is
     * <code>{@link #getMBeanInfo getMBeanInfo(n)}.getClassName()</code>.
     *
     * @exception ReflectionException Wraps a
     * <CODE>java.lang.ClassNotFoundException</CODE> or a
     * <CODE>java.lang.Exception</CODE> that occurred
     * when trying to invoke the MBean's constructor.
     * @exception InstanceAlreadyExistsException The MBean is already
     * under the control of the MBean server.
     * @exception MBeanRegistrationException The
     * <CODE>preRegister</CODE> (<CODE>MBeanRegistration</CODE>
     * interface) method of the MBean has thrown an exception. The
     * MBean will not be registered.
     * @exception RuntimeMBeanException If the MBean's constructor or its
     * {@code preRegister} or {@code postRegister} method threw
     * a {@code RuntimeException}. If the <CODE>postRegister</CODE>
     * (<CODE>MBeanRegistration</CODE> interface) method of the MBean throws a
     * <CODE>RuntimeException</CODE>, the <CODE>createMBean</CODE> method will
     * throw a <CODE>RuntimeMBeanException</CODE>, although the MBean creation
     * and registration succeeded. In such a case, the MBean will be actually
     * registered even though the <CODE>createMBean</CODE> method
     * threw an exception. Note that <CODE>RuntimeMBeanException</CODE> can
     * also be thrown by <CODE>preRegister</CODE>, in which case the MBean
     * will not be registered.
     * @exception RuntimeErrorException If the <CODE>postRegister</CODE>
     * (<CODE>MBeanRegistration</CODE> interface) method of the MBean throws an
     * <CODE>Error</CODE>, the <CODE>createMBean</CODE> method will
     * throw a <CODE>RuntimeErrorException</CODE>, although the MBean creation
     * and registration succeeded. In such a case, the MBean will be actually
     * registered even though the <CODE>createMBean</CODE> method
     * threw an exception.  Note that <CODE>RuntimeErrorException</CODE> can
     * also be thrown by <CODE>preRegister</CODE>, in which case the MBean
     * will not be registered.
     * @exception MBeanException The constructor of the MBean has
     * thrown an exception
     * @exception NotCompliantMBeanException This class is not a JMX
     * compliant MBean
     * @exception RuntimeOperationsException Wraps a
     * <CODE>java.lang.IllegalArgumentException</CODE>: The className
     * passed in parameter is null, the <CODE>ObjectName</CODE> passed
     * in parameter contains a pattern or no <CODE>ObjectName</CODE>
     * is specified for the MBean.
     * @exception IOException A communication problem occurred when
     * talking to the MBean server.
     * @see javax.management.MBeanRegistration
     */
    public ObjectInstance createMBean(String className, ObjectName name)
            throws ReflectionException, InstanceAlreadyExistsException,
                   MBeanRegistrationException, MBeanException,
                   NotCompliantMBeanException, IOException;

    /**
     * <p>Instantiates and registers an MBean in the MBean server.  The
     * class loader to be used is identified by its object name. An
     * object name is associated with the MBean. If the object name of
     * the loader is null, the ClassLoader that loaded the MBean
     * server will be used.  If the MBean's object name given is null,
     * the MBean must provide its own name by implementing the {@link
     * javax.management.MBeanRegistration MBeanRegistration} interface
     * and returning the name from the {@link
     * MBeanRegistration#preRegister preRegister} method.</p>
     *
     * <p>This method is equivalent to {@link
     * #createMBean(String,ObjectName,ObjectName,Object[],String[])
     * createMBean(className, name, loaderName, (Object[]) null,
     * (String[]) null)}.</p>
     *
     * <p>
     * <p>在MBean服务器中实例化并注册MBean。要使用的类加载器由其对象名称标识。对象名称与MBean相关联。如果加载器的对象名称为null,将使用加载MBean服务器的ClassLoader。
     * 如果MBean的对象名称为null,则MBean必须通过实现{@link javax.management.MBeanRegistration MBeanRegistration}接口并返回{@link MBeanRegistration#preRegister preRegister}
     * 方法的名称来提供自己的名称。
     * <p>在MBean服务器中实例化并注册MBean。要使用的类加载器由其对象名称标识。对象名称与MBean相关联。如果加载器的对象名称为null,将使用加载MBean服务器的ClassLoader。
     * </p>。
     * 
     *  <p>此方法相当于{@link #createMBean(String,ObjectName,ObjectName,Object [],String [])createMBean(className,name,loaderName,(Object [])null,(String [])null) }
     * 。
     * </p>。
     * 
     * 
     * @param className The class name of the MBean to be instantiated.
     * @param name The object name of the MBean. May be null.
     * @param loaderName The object name of the class loader to be used.
     *
     * @return An <CODE>ObjectInstance</CODE>, containing the
     * <CODE>ObjectName</CODE> and the Java class name of the newly
     * instantiated MBean.  If the contained <code>ObjectName</code>
     * is <code>n</code>, the contained Java class name is
     * <code>{@link #getMBeanInfo getMBeanInfo(n)}.getClassName()</code>.
     *
     * @exception ReflectionException Wraps a
     * <CODE>java.lang.ClassNotFoundException</CODE> or a
     * <CODE>java.lang.Exception</CODE> that occurred when trying to
     * invoke the MBean's constructor.
     * @exception InstanceAlreadyExistsException The MBean is already
     * under the control of the MBean server.
     * @exception MBeanRegistrationException The
     * <CODE>preRegister</CODE> (<CODE>MBeanRegistration</CODE>
     * interface) method of the MBean has thrown an exception. The
     * MBean will not be registered.
     * @exception RuntimeMBeanException If the MBean's constructor or its
     * {@code preRegister} or {@code postRegister} method threw
     * a {@code RuntimeException}. If the <CODE>postRegister</CODE>
     * (<CODE>MBeanRegistration</CODE> interface) method of the MBean throws a
     * <CODE>RuntimeException</CODE>, the <CODE>createMBean</CODE> method will
     * throw a <CODE>RuntimeMBeanException</CODE>, although the MBean creation
     * and registration succeeded. In such a case, the MBean will be actually
     * registered even though the <CODE>createMBean</CODE> method
     * threw an exception.  Note that <CODE>RuntimeMBeanException</CODE> can
     * also be thrown by <CODE>preRegister</CODE>, in which case the MBean
     * will not be registered.
     * @exception RuntimeErrorException If the <CODE>postRegister</CODE>
     * (<CODE>MBeanRegistration</CODE> interface) method of the MBean throws an
     * <CODE>Error</CODE>, the <CODE>createMBean</CODE> method will
     * throw a <CODE>RuntimeErrorException</CODE>, although the MBean creation
     * and registration succeeded. In such a case, the MBean will be actually
     * registered even though the <CODE>createMBean</CODE> method
     * threw an exception.  Note that <CODE>RuntimeErrorException</CODE> can
     * also be thrown by <CODE>preRegister</CODE>, in which case the MBean
     * will not be registered.
     * @exception MBeanException The constructor of the MBean has
     * thrown an exception
     * @exception NotCompliantMBeanException This class is not a JMX
     * compliant MBean
     * @exception InstanceNotFoundException The specified class loader
     * is not registered in the MBean server.
     * @exception RuntimeOperationsException Wraps a
     * <CODE>java.lang.IllegalArgumentException</CODE>: The className
     * passed in parameter is null, the <CODE>ObjectName</CODE> passed
     * in parameter contains a pattern or no <CODE>ObjectName</CODE>
     * is specified for the MBean.
     * @exception IOException A communication problem occurred when
     * talking to the MBean server.
     * @see javax.management.MBeanRegistration
     */
    public ObjectInstance createMBean(String className, ObjectName name,
                                      ObjectName loaderName)
            throws ReflectionException, InstanceAlreadyExistsException,
                   MBeanRegistrationException, MBeanException,
                   NotCompliantMBeanException, InstanceNotFoundException,
                   IOException;


    /**
     * Instantiates and registers an MBean in the MBean server.  The
     * MBean server will use its {@link
     * javax.management.loading.ClassLoaderRepository Default Loader
     * Repository} to load the class of the MBean.  An object name is
     * associated with the MBean.  If the object name given is null, the
     * MBean must provide its own name by implementing the {@link
     * javax.management.MBeanRegistration MBeanRegistration} interface
     * and returning the name from the {@link
     * MBeanRegistration#preRegister preRegister} method.
     *
     * <p>
     *  在MBean服务器中实例化和注册MBean。
     *  MBean服务器将使用其{@link javax.management.loading.ClassLoaderRepository Default Loader Repository}加载MBean的
     * 类。
     *  在MBean服务器中实例化和注册MBean。对象名称与MBean相关联。
     * 如果给定的对象名为null,MBean必须通过实现{@link javax.management.MBeanRegistration MBeanRegistration}接口并从{@link MBeanRegistration#preRegister preRegister}
     * 方法返回名称来提供自己的名称。
     *  在MBean服务器中实例化和注册MBean。对象名称与MBean相关联。
     * 
     * 
     * @param className The class name of the MBean to be instantiated.
     * @param name The object name of the MBean. May be null.
     * @param params An array containing the parameters of the
     * constructor to be invoked.
     * @param signature An array containing the signature of the
     * constructor to be invoked.
     *
     * @return An <CODE>ObjectInstance</CODE>, containing the
     * <CODE>ObjectName</CODE> and the Java class name of the newly
     * instantiated MBean.  If the contained <code>ObjectName</code>
     * is <code>n</code>, the contained Java class name is
     * <code>{@link #getMBeanInfo getMBeanInfo(n)}.getClassName()</code>.
     *
     * @exception ReflectionException Wraps a
     * <CODE>java.lang.ClassNotFoundException</CODE> or a
     * <CODE>java.lang.Exception</CODE> that occurred when trying to
     * invoke the MBean's constructor.
     * @exception InstanceAlreadyExistsException The MBean is already
     * under the control of the MBean server.
     * @exception MBeanRegistrationException The
     * <CODE>preRegister</CODE> (<CODE>MBeanRegistration</CODE>
     * interface) method of the MBean has thrown an exception. The
     * MBean will not be registered.
     * @exception RuntimeMBeanException If the MBean's constructor or its
     * {@code preRegister} or {@code postRegister} method threw
     * a {@code RuntimeException}. If the <CODE>postRegister</CODE>
     * (<CODE>MBeanRegistration</CODE> interface) method of the MBean throws a
     * <CODE>RuntimeException</CODE>, the <CODE>createMBean</CODE> method will
     * throw a <CODE>RuntimeMBeanException</CODE>, although the MBean creation
     * and registration succeeded. In such a case, the MBean will be actually
     * registered even though the <CODE>createMBean</CODE> method
     * threw an exception.  Note that <CODE>RuntimeMBeanException</CODE> can
     * also be thrown by <CODE>preRegister</CODE>, in which case the MBean
     * will not be registered.
     * @exception RuntimeErrorException If the <CODE>postRegister</CODE>
     * (<CODE>MBeanRegistration</CODE> interface) method of the MBean throws an
     * <CODE>Error</CODE>, the <CODE>createMBean</CODE> method will
     * throw a <CODE>RuntimeErrorException</CODE>, although the MBean creation
     * and registration succeeded. In such a case, the MBean will be actually
     * registered even though the <CODE>createMBean</CODE> method
     * threw an exception.  Note that <CODE>RuntimeErrorException</CODE> can
     * also be thrown by <CODE>preRegister</CODE>, in which case the MBean
     * will not be registered.
     * @exception MBeanException The constructor of the MBean has
     * thrown an exception
     * @exception NotCompliantMBeanException This class is not a JMX
     * compliant MBean
     * @exception RuntimeOperationsException Wraps a
     * <CODE>java.lang.IllegalArgumentException</CODE>: The className
     * passed in parameter is null, the <CODE>ObjectName</CODE> passed
     * in parameter contains a pattern or no <CODE>ObjectName</CODE>
     * is specified for the MBean.
     * @exception IOException A communication problem occurred when
     * talking to the MBean server.
     * @see javax.management.MBeanRegistration
     */
    public ObjectInstance createMBean(String className, ObjectName name,
                                      Object params[], String signature[])
            throws ReflectionException, InstanceAlreadyExistsException,
                   MBeanRegistrationException, MBeanException,
                   NotCompliantMBeanException, IOException;

    /**
     * <p>Instantiates and registers an MBean in the MBean server.  The
     * class loader to be used is identified by its object name. An
     * object name is associated with the MBean. If the object name of
     * the loader is not specified, the ClassLoader that loaded the
     * MBean server will be used.  If the MBean object name given is
     * null, the MBean must provide its own name by implementing the
     * {@link javax.management.MBeanRegistration MBeanRegistration}
     * interface and returning the name from the {@link
     * MBeanRegistration#preRegister preRegister} method.
     *
     * <p>
     * <p>在MBean服务器中实例化并注册MBean。要使用的类加载器由其对象名称标识。对象名称与MBean相关联。如果未指定加载器的对象名称,则将使用加载MBean服务器的ClassLoader。
     * 如果MBean对象名称为null,MBean必须通过实现{@link javax.management.MBeanRegistration MBeanRegistration}接口并从{@link MBeanRegistration#preRegister preRegister}
     * 方法返回名称来提供自己的名称。
     * <p>在MBean服务器中实例化并注册MBean。要使用的类加载器由其对象名称标识。对象名称与MBean相关联。如果未指定加载器的对象名称,则将使用加载MBean服务器的ClassLoader。
     * 
     * 
     * @param className The class name of the MBean to be instantiated.
     * @param name The object name of the MBean. May be null.
     * @param params An array containing the parameters of the
     * constructor to be invoked.
     * @param signature An array containing the signature of the
     * constructor to be invoked.
     * @param loaderName The object name of the class loader to be used.
     *
     * @return An <CODE>ObjectInstance</CODE>, containing the
     * <CODE>ObjectName</CODE> and the Java class name of the newly
     * instantiated MBean.  If the contained <code>ObjectName</code>
     * is <code>n</code>, the contained Java class name is
     * <code>{@link #getMBeanInfo getMBeanInfo(n)}.getClassName()</code>.
     *
     * @exception ReflectionException Wraps a
     * <CODE>java.lang.ClassNotFoundException</CODE> or a
     * <CODE>java.lang.Exception</CODE> that occurred when trying to
     * invoke the MBean's constructor.
     * @exception InstanceAlreadyExistsException The MBean is already
     * under the control of the MBean server.
     * @exception MBeanRegistrationException The
     * <CODE>preRegister</CODE> (<CODE>MBeanRegistration</CODE>
     * interface) method of the MBean has thrown an exception. The
     * MBean will not be registered.
     * @exception RuntimeMBeanException The MBean's constructor or its
     * {@code preRegister} or {@code postRegister} method threw
     * a {@code RuntimeException}. If the <CODE>postRegister</CODE>
     * (<CODE>MBeanRegistration</CODE> interface) method of the MBean throws a
     * <CODE>RuntimeException</CODE>, the <CODE>createMBean</CODE> method will
     * throw a <CODE>RuntimeMBeanException</CODE>, although the MBean creation
     * and registration succeeded. In such a case, the MBean will be actually
     * registered even though the <CODE>createMBean</CODE> method
     * threw an exception.  Note that <CODE>RuntimeMBeanException</CODE> can
     * also be thrown by <CODE>preRegister</CODE>, in which case the MBean
     * will not be registered.
     * @exception RuntimeErrorException If the <CODE>postRegister</CODE> method
     * (<CODE>MBeanRegistration</CODE> interface) method of the MBean throws an
     * <CODE>Error</CODE>, the <CODE>createMBean</CODE> method will
     * throw a <CODE>RuntimeErrorException</CODE>, although the MBean creation
     * and registration succeeded. In such a case, the MBean will be actually
     * registered even though the <CODE>createMBean</CODE> method
     * threw an exception.  Note that <CODE>RuntimeErrorException</CODE> can
     * also be thrown by <CODE>preRegister</CODE>, in which case the MBean
     * will not be registered.
     * @exception MBeanException The constructor of the MBean has
     * thrown an exception
     * @exception NotCompliantMBeanException This class is not a JMX
     * compliant MBean
     * @exception InstanceNotFoundException The specified class loader
     * is not registered in the MBean server.
     * @exception RuntimeOperationsException Wraps a
     * <CODE>java.lang.IllegalArgumentException</CODE>: The className
     * passed in parameter is null, the <CODE>ObjectName</CODE> passed
     * in parameter contains a pattern or no <CODE>ObjectName</CODE>
     * is specified for the MBean.
     * @exception IOException A communication problem occurred when
     * talking to the MBean server.
     * @see javax.management.MBeanRegistration
     */
    public ObjectInstance createMBean(String className, ObjectName name,
                                      ObjectName loaderName, Object params[],
                                      String signature[])
            throws ReflectionException, InstanceAlreadyExistsException,
                   MBeanRegistrationException, MBeanException,
                   NotCompliantMBeanException, InstanceNotFoundException,
                   IOException;

    /**
     * Unregisters an MBean from the MBean server. The MBean is
     * identified by its object name. Once the method has been
     * invoked, the MBean may no longer be accessed by its object
     * name.
     *
     * <p>
     *  从MBean服务器取消注册MBean。 MBean由其对象名称标识。一旦该方法被调用,MBean可能不再被其对象名访问。
     * 
     * 
     * @param name The object name of the MBean to be unregistered.
     *
     * @exception InstanceNotFoundException The MBean specified is not
     * registered in the MBean server.
     * @exception MBeanRegistrationException The preDeregister
     * ((<CODE>MBeanRegistration</CODE> interface) method of the MBean
     * has thrown an exception.
     * @exception RuntimeMBeanException If the <CODE>postDeregister</CODE>
     * (<CODE>MBeanRegistration</CODE> interface) method of the MBean throws a
     * <CODE>RuntimeException</CODE>, the <CODE>unregisterMBean</CODE> method
     * will throw a <CODE>RuntimeMBeanException</CODE>, although the MBean
     * unregistration succeeded. In such a case, the MBean will be actually
     * unregistered even though the <CODE>unregisterMBean</CODE> method
     * threw an exception.  Note that <CODE>RuntimeMBeanException</CODE> can
     * also be thrown by <CODE>preDeregister</CODE>, in which case the MBean
     * will remain registered.
     * @exception RuntimeErrorException If the <CODE>postDeregister</CODE>
     * (<CODE>MBeanRegistration</CODE> interface) method of the MBean throws an
     * <CODE>Error</CODE>, the <CODE>unregisterMBean</CODE> method will
     * throw a <CODE>RuntimeErrorException</CODE>, although the MBean
     * unregistration succeeded. In such a case, the MBean will be actually
     * unregistered even though the <CODE>unregisterMBean</CODE> method
     * threw an exception.  Note that <CODE>RuntimeMBeanException</CODE> can
     * also be thrown by <CODE>preDeregister</CODE>, in which case the MBean
     * will remain registered.
     * @exception RuntimeOperationsException Wraps a
     * <CODE>java.lang.IllegalArgumentException</CODE>: The object
     * name in parameter is null or the MBean you are when trying to
     * unregister is the {@link javax.management.MBeanServerDelegate
     * MBeanServerDelegate} MBean.
     * @exception IOException A communication problem occurred when
     * talking to the MBean server.
     * @see javax.management.MBeanRegistration
     */
    public void unregisterMBean(ObjectName name)
            throws InstanceNotFoundException, MBeanRegistrationException,
                   IOException;

    /**
     * Gets the <CODE>ObjectInstance</CODE> for a given MBean
     * registered with the MBean server.
     *
     * <p>
     *  获取向MBean服务器注册的给定MBean的<CODE> ObjectInstance </CODE>。
     * 
     * 
     * @param name The object name of the MBean.
     *
     * @return The <CODE>ObjectInstance</CODE> associated with the MBean
     * specified by <VAR>name</VAR>.  The contained <code>ObjectName</code>
     * is <code>name</code> and the contained class name is
     * <code>{@link #getMBeanInfo getMBeanInfo(name)}.getClassName()</code>.
     *
     * @exception InstanceNotFoundException The MBean specified is not
     * registered in the MBean server.
     * @exception IOException A communication problem occurred when
     * talking to the MBean server.
     */
    public ObjectInstance getObjectInstance(ObjectName name)
            throws InstanceNotFoundException, IOException;

    /**
     * Gets MBeans controlled by the MBean server. This method allows
     * any of the following to be obtained: All MBeans, a set of
     * MBeans specified by pattern matching on the
     * <CODE>ObjectName</CODE> and/or a Query expression, a specific
     * MBean. When the object name is null or no domain and key
     * properties are specified, all objects are to be selected (and
     * filtered if a query is specified). It returns the set of
     * <CODE>ObjectInstance</CODE> objects (containing the
     * <CODE>ObjectName</CODE> and the Java Class name) for the
     * selected MBeans.
     *
     * <p>
     *  获取MBean服务器控制的MBean。
     * 此方法允许获取以下任何内容：所有MBean,通过<CODE> ObjectName </CODE>和/或Query表达式(特定MBean)上的模式匹配指定的一组MBean。
     * 当对象名称为null或未指定域和键属性时,将选择所有对象(如果指定了查询,则会对其进行过滤)。
     * 它返回所选MBean的<CODE> ObjectInstance </CODE>对象集(包含<CODE> ObjectName </CODE>和Java类名称)。
     * 
     * 
     * @param name The object name pattern identifying the MBeans to
     * be retrieved. If null or no domain and key properties are
     * specified, all the MBeans registered will be retrieved.
     * @param query The query expression to be applied for selecting
     * MBeans. If null no query expression will be applied for
     * selecting MBeans.
     *
     * @return A set containing the <CODE>ObjectInstance</CODE>
     * objects for the selected MBeans.  If no MBean satisfies the
     * query an empty list is returned.
     *
     * @exception IOException A communication problem occurred when
     * talking to the MBean server.
     */
    public Set<ObjectInstance> queryMBeans(ObjectName name, QueryExp query)
            throws IOException;

    /**
     * Gets the names of MBeans controlled by the MBean server. This
     * method enables any of the following to be obtained: The names
     * of all MBeans, the names of a set of MBeans specified by
     * pattern matching on the <CODE>ObjectName</CODE> and/or a Query
     * expression, a specific MBean name (equivalent to testing
     * whether an MBean is registered). When the object name is null
     * or no domain and key properties are specified, all objects are
     * selected (and filtered if a query is specified). It returns the
     * set of ObjectNames for the MBeans selected.
     *
     * <p>
     * 获取MBean服务器控制的MBean的名称。
     * 此方法允许获取以下任何内容：所有MBean的名称,通过<CODE> ObjectName </CODE>和/或Query表达式上的模式匹配指定的一组MBeans的名称,特定MBean名称相当于测试MBe
     * an是否注册)。
     * 获取MBean服务器控制的MBean的名称。当对象名称为null或未指定域和键属性时,将选择所有对象(如果指定了查询,则会对其进行过滤)。它返回所选MBeans的ObjectNames集合。
     * 
     * 
     * @param name The object name pattern identifying the MBean names
     * to be retrieved. If null or no domain and key properties are
     * specified, the name of all registered MBeans will be retrieved.
     * @param query The query expression to be applied for selecting
     * MBeans. If null no query expression will be applied for
     * selecting MBeans.
     *
     * @return A set containing the ObjectNames for the MBeans
     * selected.  If no MBean satisfies the query, an empty list is
     * returned.
     *
     * @exception IOException A communication problem occurred when
     * talking to the MBean server.
     */
    public Set<ObjectName> queryNames(ObjectName name, QueryExp query)
            throws IOException;



    /**
     * Checks whether an MBean, identified by its object name, is
     * already registered with the MBean server.
     *
     * <p>
     *  检查由其对象名称标识的MBean是否已向MBean服务器注册。
     * 
     * 
     * @param name The object name of the MBean to be checked.
     *
     * @return True if the MBean is already registered in the MBean
     * server, false otherwise.
     *
     * @exception RuntimeOperationsException Wraps a
     * <CODE>java.lang.IllegalArgumentException</CODE>: The object
     * name in parameter is null.
     * @exception IOException A communication problem occurred when
     * talking to the MBean server.
     */
    public boolean isRegistered(ObjectName name)
            throws IOException;


    /**
     * Returns the number of MBeans registered in the MBean server.
     *
     * <p>
     *  返回在MBean服务器中注册的MBean数。
     * 
     * 
     * @return the number of MBeans registered.
     *
     * @exception IOException A communication problem occurred when
     * talking to the MBean server.
     */
    public Integer getMBeanCount()
            throws IOException;

    /**
     * Gets the value of a specific attribute of a named MBean. The MBean
     * is identified by its object name.
     *
     * <p>
     *  获取命名MBean的特定属性的值。 MBean由其对象名称标识。
     * 
     * 
     * @param name The object name of the MBean from which the
     * attribute is to be retrieved.
     * @param attribute A String specifying the name of the attribute
     * to be retrieved.
     *
     * @return  The value of the retrieved attribute.
     *
     * @exception AttributeNotFoundException The attribute specified
     * is not accessible in the MBean.
     * @exception MBeanException Wraps an exception thrown by the
     * MBean's getter.
     * @exception InstanceNotFoundException The MBean specified is not
     * registered in the MBean server.
     * @exception ReflectionException Wraps a
     * <CODE>java.lang.Exception</CODE> thrown when trying to invoke
     * the setter.
     * @exception RuntimeOperationsException Wraps a
     * <CODE>java.lang.IllegalArgumentException</CODE>: The object
     * name in parameter is null or the attribute in parameter is
     * null.
     * @exception IOException A communication problem occurred when
     * talking to the MBean server.
     *
     * @see #setAttribute
     */
    public Object getAttribute(ObjectName name, String attribute)
            throws MBeanException, AttributeNotFoundException,
                   InstanceNotFoundException, ReflectionException,
                   IOException;


    /**
     * <p>Retrieves the values of several attributes of a named MBean. The MBean
     * is identified by its object name.</p>
     *
     * <p>If one or more attributes cannot be retrieved for some reason, they
     * will be omitted from the returned {@code AttributeList}.  The caller
     * should check that the list is the same size as the {@code attributes}
     * array.  To discover what problem prevented a given attribute from being
     * retrieved, call {@link #getAttribute getAttribute} for that attribute.</p>
     *
     * <p>Here is an example of calling this method and checking that it
     * succeeded in retrieving all the requested attributes:</p>
     *
     * <pre>
     * String[] attrNames = ...;
     * AttributeList list = mbeanServerConnection.getAttributes(objectName, attrNames);
     * if (list.size() == attrNames.length)
     *     System.out.println("All attributes were retrieved successfully");
     * else {
     *     {@code List<String>} missing = new {@code ArrayList<String>}(<!--
     * -->{@link java.util.Arrays#asList Arrays.asList}(attrNames));
     *     for (Attribute a : list.asList())
     *         missing.remove(a.getName());
     *     System.out.println("Did not retrieve: " + missing);
     * }
     * </pre>
     *
     * <p>
     *  <p>检索命名MBean的几个属性的值。 MBean由其对象名称标识。</p>
     * 
     *  <p>如果由于某种原因无法检索一个或多个属性,则会从返回的{@code AttributeList}中省略这些属性。调用者应该检查列表与{@code attributes}数组的大小相同。
     * 要发现阻止给定属性被检索的问题,请为该属性调用{@link #getAttribute getAttribute}。</p>。
     * 
     *  <p>以下是调用此方法并检查其是否成功检索到所有请求的属性的示例：</p>
     * 
     * <pre>
     * String [] attrNames = ...; AttributeList list = mbeanServerConnection.getAttributes(objectName,attrNa
     * mes); if(list.size()== attrNames.length)System.out.println("所有属性已成功检索"); else {{@code List <String>} 
     * missing = new {@code ArrayList <String>}(<！ -   - > {@ link java.util.Arrays#asList Arrays.asList}(at
     * trNames)); for(属性a：list.asList())missing.remove(a.getName()); System.out.println("Did not retrieve："+
     *  missing); }}。
     * </pre>
     * 
     * 
     * @param name The object name of the MBean from which the
     * attributes are retrieved.
     * @param attributes A list of the attributes to be retrieved.
     *
     * @return The list of the retrieved attributes.
     *
     * @exception InstanceNotFoundException The MBean specified is not
     * registered in the MBean server.
     * @exception ReflectionException An exception occurred when
     * trying to invoke the getAttributes method of a Dynamic MBean.
     * @exception RuntimeOperationsException Wrap a
     * <CODE>java.lang.IllegalArgumentException</CODE>: The object
     * name in parameter is null or attributes in parameter is null.
     * @exception IOException A communication problem occurred when
     * talking to the MBean server.
     *
     * @see #setAttributes
     */
    public AttributeList getAttributes(ObjectName name, String[] attributes)
            throws InstanceNotFoundException, ReflectionException,
                   IOException;


    /**
     * Sets the value of a specific attribute of a named MBean. The MBean
     * is identified by its object name.
     *
     * <p>
     *  设置命名MBean的特定属性的值。 MBean由其对象名称标识。
     * 
     * 
     * @param name The name of the MBean within which the attribute is
     * to be set.
     * @param attribute The identification of the attribute to be set
     * and the value it is to be set to.
     *
     * @exception InstanceNotFoundException The MBean specified is not
     * registered in the MBean server.
     * @exception AttributeNotFoundException The attribute specified
     * is not accessible in the MBean.
     * @exception InvalidAttributeValueException The value specified
     * for the attribute is not valid.
     * @exception MBeanException Wraps an exception thrown by the
     * MBean's setter.
     * @exception ReflectionException Wraps a
     * <CODE>java.lang.Exception</CODE> thrown when trying to invoke
     * the setter.
     * @exception RuntimeOperationsException Wraps a
     * <CODE>java.lang.IllegalArgumentException</CODE>: The object
     * name in parameter is null or the attribute in parameter is
     * null.
     * @exception IOException A communication problem occurred when
     * talking to the MBean server.
     *
     * @see #getAttribute
     */
    public void setAttribute(ObjectName name, Attribute attribute)
            throws InstanceNotFoundException, AttributeNotFoundException,
                   InvalidAttributeValueException, MBeanException,
                   ReflectionException, IOException;


    /**
     * <p>Sets the values of several attributes of a named MBean. The MBean is
     * identified by its object name.</p>
     *
     * <p>If one or more attributes cannot be set for some reason, they will be
     * omitted from the returned {@code AttributeList}.  The caller should check
     * that the input {@code AttributeList} is the same size as the output one.
     * To discover what problem prevented a given attribute from being retrieved,
     * it will usually be possible to call {@link #setAttribute setAttribute}
     * for that attribute, although this is not guaranteed to work.  (For
     * example, the values of two attributes may have been rejected because
     * they were inconsistent with each other.  Setting one of them alone might
     * be allowed.)
     *
     * <p>Here is an example of calling this method and checking that it
     * succeeded in setting all the requested attributes:</p>
     *
     * <pre>
     * AttributeList inputAttrs = ...;
     * AttributeList outputAttrs = mbeanServerConnection.setAttributes(<!--
     * -->objectName, inputAttrs);
     * if (inputAttrs.size() == outputAttrs.size())
     *     System.out.println("All attributes were set successfully");
     * else {
     *     {@code List<String>} missing = new {@code ArrayList<String>}();
     *     for (Attribute a : inputAttrs.asList())
     *         missing.add(a.getName());
     *     for (Attribute a : outputAttrs.asList())
     *         missing.remove(a.getName());
     *     System.out.println("Did not set: " + missing);
     * }
     * </pre>
     *
     * <p>
     *  <p>设置命名MBean的多个属性的值。 MBean由其对象名称标识。</p>
     * 
     *  <p>如果由于某种原因不能设置一个或多个属性,则从返回的{@code AttributeList}中将忽略它们。调用者应该检查输入{@code AttributeList}是否与输出一样大。
     * 要发现阻止给定属性被检索的问题,通常可以为该属性调用{@link #setAttribute setAttribute},虽然这不能保证工作。
     *  (例如,两个属性的值可能已被拒绝,因为它们彼此不一致,可能只允许单独设置其中一个)。
     * 
     *  <p>以下是调用此方法并检查其是否成功设置所有请求的属性的示例：</p>
     * 
     * <pre>
     * AttributeList inputAttrs = ...; AttributeList outputAttrs = mbeanServerConnection.setAttributes(<！ -   - >
     *  objectName,inputAttrs); if(inputAttrs.size()== outputAttrs.size())System.out.println("All attributes
     *  were set successfully"); else {{@code List <String>} missing = new {@code ArrayList <String>}(); for
     * (属性a：inputAttrs.asList())missing.add(a.getName()); for(Attribute a：outputAttrs.asList())missing.remov
     * e(a.getName()); System.out.println("Did not set："+ missing); }}。
     * </pre>
     * 
     * 
     * @param name The object name of the MBean within which the
     * attributes are to be set.
     * @param attributes A list of attributes: The identification of
     * the attributes to be set and the values they are to be set to.
     *
     * @return The list of attributes that were set, with their new
     * values.
     *
     * @exception InstanceNotFoundException The MBean specified is not
     * registered in the MBean server.
     * @exception ReflectionException An exception occurred when
     * trying to invoke the getAttributes method of a Dynamic MBean.
     * @exception RuntimeOperationsException Wraps a
     * <CODE>java.lang.IllegalArgumentException</CODE>: The object
     * name in parameter is null or attributes in parameter is null.
     * @exception IOException A communication problem occurred when
     * talking to the MBean server.
     *
     * @see #getAttributes
     */
    public AttributeList setAttributes(ObjectName name,
                                       AttributeList attributes)
        throws InstanceNotFoundException, ReflectionException, IOException;

    /**
     * <p>Invokes an operation on an MBean.</p>
     *
     * <p>Because of the need for a {@code signature} to differentiate
     * possibly-overloaded operations, it is much simpler to invoke operations
     * through an {@linkplain JMX#newMBeanProxy(MBeanServerConnection, ObjectName,
     * Class) MBean proxy} where possible.  For example, suppose you have a
     * Standard MBean interface like this:</p>
     *
     * <pre>
     * public interface FooMBean {
     *     public int countMatches(String[] patterns, boolean ignoreCase);
     * }
     * </pre>
     *
     * <p>The {@code countMatches} operation can be invoked as follows:</p>
     *
     * <pre>
     * String[] myPatterns = ...;
     * int count = (Integer) mbeanServerConnection.invoke(
     *         objectName,
     *         "countMatches",
     *         new Object[] {myPatterns, true},
     *         new String[] {String[].class.getName(), boolean.class.getName()});
     * </pre>
     *
     * <p>Alternatively, it can be invoked through a proxy as follows:</p>
     *
     * <pre>
     * String[] myPatterns = ...;
     * FooMBean fooProxy = JMX.newMBeanProxy(
     *         mbeanServerConnection, objectName, FooMBean.class);
     * int count = fooProxy.countMatches(myPatterns, true);
     * </pre>
     *
     * <p>
     *  <p>调用MBean上的操作。</p>
     * 
     *  <p>由于需要使用{@code signature}来区分可能的重载操作,因此在可能的情况下通过{@linkplain JMX#newMBeanProxy(MBeanServerConnection,ObjectName,Class)MBean代理}
     * 调用操作要简单得多。
     * 例如,假设您有一个标准MBean界面,如下所示：</p>。
     * 
     * <pre>
     *  public interface FooMBean {public int countMatches(String [] patterns,boolean ignoreCase); }}
     * </pre>
     * 
     *  <p> {@code countMatches}操作的调用方式如下：</p>
     * 
     * <pre>
     *  String [] myPatterns = ...; int count =(Integer)mbeanServerConnection.invoke(objectName,"countMatche
     * s",new Object [] {m​​yPatterns,true},new String [] {String []。
     * class.getName(),boolean.class.getName ;。
     * </pre>
     * 
     *  <p>或者,它可以通过代理调用如下：</p>
     * 
     * <pre>
     *  String [] myPatterns = ...; FooMBean fooProxy = JMX.newMBeanProxy(mbeanServerConnection,objectName,F
     * ooMBean.class); int count = fooProxy.countMatches(myPatterns,true);。
     * </pre>
     * 
     * 
     * @param name The object name of the MBean on which the method is
     * to be invoked.
     * @param operationName The name of the operation to be invoked.
     * @param params An array containing the parameters to be set when
     * the operation is invoked
     * @param signature An array containing the signature of the
     * operation, an array of class names in the format returned by
     * {@link Class#getName()}. The class objects will be loaded using the same
     * class loader as the one used for loading the MBean on which the
     * operation was invoked.
     *
     * @return The object returned by the operation, which represents
     * the result of invoking the operation on the MBean specified.
     *
     * @exception InstanceNotFoundException The MBean specified is not
     * registered in the MBean server.
     * @exception MBeanException Wraps an exception thrown by the
     * MBean's invoked method.
     * @exception ReflectionException Wraps a
     * <CODE>java.lang.Exception</CODE> thrown while trying to invoke
     * the method.
     * @exception IOException A communication problem occurred when
     * talking to the MBean server.
     *
     */
    public Object invoke(ObjectName name, String operationName,
                         Object params[], String signature[])
            throws InstanceNotFoundException, MBeanException,
                   ReflectionException, IOException;



    /**
     * Returns the default domain used for naming the MBean.
     * The default domain name is used as the domain part in the ObjectName
     * of MBeans if no domain is specified by the user.
     *
     * <p>
     * 返回用于命名MBean的默认域。如果用户未指定域,则默认域名将用作MBeans的ObjectName中的域部分。
     * 
     * 
     * @return the default domain.
     *
     * @exception IOException A communication problem occurred when
     * talking to the MBean server.
     */
    public String getDefaultDomain()
            throws IOException;

    /**
     * <p>Returns the list of domains in which any MBean is currently
     * registered.  A string is in the returned array if and only if
     * there is at least one MBean registered with an ObjectName whose
     * {@link ObjectName#getDomain() getDomain()} is equal to that
     * string.  The order of strings within the returned array is
     * not defined.</p>
     *
     * <p>
     *  <p>返回当前注册了任何MBean的域列表。
     * 当且仅当至少有一个MBean注册了一个ObjectName,其{@link ObjectName#getDomain()getDomain()}等于该字符串时,字符串才在返回的数组中。
     * 未定义返回数组中的字符串顺序。</p>。
     * 
     * 
     * @return the list of domains.
     *
     * @exception IOException A communication problem occurred when
     * talking to the MBean server.
     *
     */
    public String[] getDomains()
            throws IOException;

    /**
     * <p>Adds a listener to a registered MBean.
     * Notifications emitted by the MBean will be forwarded to the listener.</p>
     *
     * <p>
     *  <p>将侦听器添加到注册的MBean。 MBean发出的通知将转发给侦听器。</p>
     * 
     * 
     * @param name The name of the MBean on which the listener should
     * be added.
     * @param listener The listener object which will handle the
     * notifications emitted by the registered MBean.
     * @param filter The filter object. If filter is null, no
     * filtering will be performed before handling notifications.
     * @param handback The context to be sent to the listener when a
     * notification is emitted.
     *
     * @exception InstanceNotFoundException The MBean name provided
     * does not match any of the registered MBeans.
     * @exception IOException A communication problem occurred when
     * talking to the MBean server.
     *
     * @see #removeNotificationListener(ObjectName, NotificationListener)
     * @see #removeNotificationListener(ObjectName, NotificationListener,
     * NotificationFilter, Object)
     */
    public void addNotificationListener(ObjectName name,
                                        NotificationListener listener,
                                        NotificationFilter filter,
                                        Object handback)
            throws InstanceNotFoundException, IOException;


    /**
     * <p>Adds a listener to a registered MBean.</p>
     *
     * <p>A notification emitted by an MBean will be forwarded by the
     * MBeanServer to the listener.  If the source of the notification
     * is a reference to an MBean object, the MBean server will
     * replace it by that MBean's ObjectName.  Otherwise the source is
     * unchanged.</p>
     *
     * <p>The listener object that receives notifications is the one
     * that is registered with the given name at the time this method
     * is called.  Even if it is subsequently unregistered, it will
     * continue to receive notifications.</p>
     *
     * <p>
     *  <p>将侦听器添加到注册的MBean。</p>
     * 
     *  <p> MBean发出的通知将由MBeanServer转发到侦听器。如果通知的来源是对MBean对象的引用,MBean服务器将用MBean的ObjectName替换它。否则源未更改。</p>
     * 
     *  <p>接收通知的侦听器对象是在调用此方法时使用给定名称注册的侦听器对象。即使后来未注册,它仍会继续接收通知。</p>
     * 
     * 
     * @param name The name of the MBean on which the listener should
     * be added.
     * @param listener The object name of the listener which will
     * handle the notifications emitted by the registered MBean.
     * @param filter The filter object. If filter is null, no
     * filtering will be performed before handling notifications.
     * @param handback The context to be sent to the listener when a
     * notification is emitted.
     *
     * @exception InstanceNotFoundException The MBean name of the
     * notification listener or of the notification broadcaster does
     * not match any of the registered MBeans.
     * @exception RuntimeOperationsException Wraps an {@link
     * IllegalArgumentException}.  The MBean named by
     * <code>listener</code> exists but does not implement the {@link
     * NotificationListener} interface.
     * @exception IOException A communication problem occurred when
     * talking to the MBean server.
     *
     * @see #removeNotificationListener(ObjectName, ObjectName)
     * @see #removeNotificationListener(ObjectName, ObjectName,
     * NotificationFilter, Object)
     */
    public void addNotificationListener(ObjectName name,
                                        ObjectName listener,
                                        NotificationFilter filter,
                                        Object handback)
            throws InstanceNotFoundException, IOException;


    /**
     * Removes a listener from a registered MBean.
     *
     * <P> If the listener is registered more than once, perhaps with
     * different filters or callbacks, this method will remove all
     * those registrations.
     *
     * <p>
     *  从注册的MBean中删除侦听器。
     * 
     *  <P>如果侦听器被注册了多次,可能使用不同的过滤器或回调,则此方法将删除所有这些注册。
     * 
     * 
     * @param name The name of the MBean on which the listener should
     * be removed.
     * @param listener The object name of the listener to be removed.
     *
     * @exception InstanceNotFoundException The MBean name provided
     * does not match any of the registered MBeans.
     * @exception ListenerNotFoundException The listener is not
     * registered in the MBean.
     * @exception IOException A communication problem occurred when
     * talking to the MBean server.
     *
     * @see #addNotificationListener(ObjectName, ObjectName,
     * NotificationFilter, Object)
     */
    public void removeNotificationListener(ObjectName name,
                                           ObjectName listener)
        throws InstanceNotFoundException, ListenerNotFoundException,
               IOException;

    /**
     * <p>Removes a listener from a registered MBean.</p>
     *
     * <p>The MBean must have a listener that exactly matches the
     * given <code>listener</code>, <code>filter</code>, and
     * <code>handback</code> parameters.  If there is more than one
     * such listener, only one is removed.</p>
     *
     * <p>The <code>filter</code> and <code>handback</code> parameters
     * may be null if and only if they are null in a listener to be
     * removed.</p>
     *
     * <p>
     *  <p>从注册的MBean中删除侦听器。</p>
     * 
     * <p> MBean必须有一个与给定的<code>监听器</code>,<code>过滤器</code>和<code> handback </code>参数完全匹配的监听器。
     * 如果有多个此类侦听器,则只会删除一个。</p>。
     * 
     *  <p> <code> filter </code>和<code> handback </code>参数可能为null,如果且仅当它们在要删除的侦听器中为null。</p>
     * 
     * 
     * @param name The name of the MBean on which the listener should
     * be removed.
     * @param listener The object name of the listener to be removed.
     * @param filter The filter that was specified when the listener
     * was added.
     * @param handback The handback that was specified when the
     * listener was added.
     *
     * @exception InstanceNotFoundException The MBean name provided
     * does not match any of the registered MBeans.
     * @exception ListenerNotFoundException The listener is not
     * registered in the MBean, or it is not registered with the given
     * filter and handback.
     * @exception IOException A communication problem occurred when
     * talking to the MBean server.
     *
     * @see #addNotificationListener(ObjectName, ObjectName,
     * NotificationFilter, Object)
     *
     */
    public void removeNotificationListener(ObjectName name,
                                           ObjectName listener,
                                           NotificationFilter filter,
                                           Object handback)
            throws InstanceNotFoundException, ListenerNotFoundException,
                   IOException;


    /**
     * <p>Removes a listener from a registered MBean.</p>
     *
     * <P> If the listener is registered more than once, perhaps with
     * different filters or callbacks, this method will remove all
     * those registrations.
     *
     * <p>
     *  <p>从注册的MBean中删除侦听器。</p>
     * 
     *  <P>如果侦听器被注册了多次,可能使用不同的过滤器或回调,则此方法将删除所有这些注册。
     * 
     * 
     * @param name The name of the MBean on which the listener should
     * be removed.
     * @param listener The listener to be removed.
     *
     * @exception InstanceNotFoundException The MBean name provided
     * does not match any of the registered MBeans.
     * @exception ListenerNotFoundException The listener is not
     * registered in the MBean.
     * @exception IOException A communication problem occurred when
     * talking to the MBean server.
     *
     * @see #addNotificationListener(ObjectName, NotificationListener,
     * NotificationFilter, Object)
     */
    public void removeNotificationListener(ObjectName name,
                                           NotificationListener listener)
            throws InstanceNotFoundException, ListenerNotFoundException,
                   IOException;

    /**
     * <p>Removes a listener from a registered MBean.</p>
     *
     * <p>The MBean must have a listener that exactly matches the
     * given <code>listener</code>, <code>filter</code>, and
     * <code>handback</code> parameters.  If there is more than one
     * such listener, only one is removed.</p>
     *
     * <p>The <code>filter</code> and <code>handback</code> parameters
     * may be null if and only if they are null in a listener to be
     * removed.</p>
     *
     * <p>
     *  <p>从注册的MBean中删除侦听器。</p>
     * 
     *  <p> MBean必须有一个与给定的<code>监听器</code>,<code>过滤器</code>和<code> handback </code>参数完全匹配的监听器。
     * 如果有多个此类侦听器,则只会删除一个。</p>。
     * 
     *  <p> <code> filter </code>和<code> handback </code>参数可能为null,如果且仅当它们在要删除的侦听器中为null。</p>
     * 
     * 
     * @param name The name of the MBean on which the listener should
     * be removed.
     * @param listener The listener to be removed.
     * @param filter The filter that was specified when the listener
     * was added.
     * @param handback The handback that was specified when the
     * listener was added.
     *
     * @exception InstanceNotFoundException The MBean name provided
     * does not match any of the registered MBeans.
     * @exception ListenerNotFoundException The listener is not
     * registered in the MBean, or it is not registered with the given
     * filter and handback.
     * @exception IOException A communication problem occurred when
     * talking to the MBean server.
     *
     * @see #addNotificationListener(ObjectName, NotificationListener,
     * NotificationFilter, Object)
     *
     */
    public void removeNotificationListener(ObjectName name,
                                           NotificationListener listener,
                                           NotificationFilter filter,
                                           Object handback)
            throws InstanceNotFoundException, ListenerNotFoundException,
                   IOException;

    /**
     * This method discovers the attributes and operations that an
     * MBean exposes for management.
     *
     * <p>
     *  此方法发现MBean公开用于管理的属性和操作。
     * 
     * 
     * @param name The name of the MBean to analyze
     *
     * @return An instance of <CODE>MBeanInfo</CODE> allowing the
     * retrieval of all attributes and operations of this MBean.
     *
     * @exception IntrospectionException An exception occurred during
     * introspection.
     * @exception InstanceNotFoundException The MBean specified was
     * not found.
     * @exception ReflectionException An exception occurred when
     * trying to invoke the getMBeanInfo of a Dynamic MBean.
     * @exception IOException A communication problem occurred when
     * talking to the MBean server.
     */
    public MBeanInfo getMBeanInfo(ObjectName name)
            throws InstanceNotFoundException, IntrospectionException,
                   ReflectionException, IOException;


    /**
     * <p>Returns true if the MBean specified is an instance of the
     * specified class, false otherwise.</p>
     *
     * <p>If <code>name</code> does not name an MBean, this method
     * throws {@link InstanceNotFoundException}.</p>
     *
     * <p>Otherwise, let<br>
     * X be the MBean named by <code>name</code>,<br>
     * L be the ClassLoader of X,<br>
     * N be the class name in X's {@link MBeanInfo}.</p>
     *
     * <p>If N equals <code>className</code>, the result is true.</p>
     *
     * <p>Otherwise, if L successfully loads <code>className</code>
     * and X is an instance of this class, the result is true.
     *
     * <p>Otherwise, if L successfully loads both N and
     * <code>className</code>, and the second class is assignable from
     * the first, the result is true.</p>
     *
     * <p>Otherwise, the result is false.</p>
     *
     * <p>
     *  <p>如果指定的MBean是指定类的实例,则返回true,否则返回false。</p>
     * 
     *  <p>如果<code> name </code>未命名MBean,此方法会抛出{@link InstanceNotFoundException}。</p>
     * 
     *  <p>否则,让<br> X是由<code> name </code>命名的MBean,<br> L是X的ClassLoader,<br> N是X的{@link MBeanInfo}中的类名。
     *  </p>。
     * 
     *  <p>如果N等于<code> className </code>,结果为true。</p>
     * 
     * @param name The <CODE>ObjectName</CODE> of the MBean.
     * @param className The name of the class.
     *
     * @return true if the MBean specified is an instance of the
     * specified class according to the rules above, false otherwise.
     *
     * @exception InstanceNotFoundException The MBean specified is not
     * registered in the MBean server.
     * @exception IOException A communication problem occurred when
     * talking to the MBean server.
     *
     * @see Class#isInstance
     */
    public boolean isInstanceOf(ObjectName name, String className)
            throws InstanceNotFoundException, IOException;
}
