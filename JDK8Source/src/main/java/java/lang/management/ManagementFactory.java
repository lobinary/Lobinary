/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.lang.management;
import javax.management.DynamicMBean;
import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerFactory;
import javax.management.MBeanServerPermission;
import javax.management.NotificationEmitter;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MalformedObjectNameException;
import javax.management.MBeanRegistrationException;
import javax.management.NotCompliantMBeanException;
import javax.management.StandardEmitterMBean;
import javax.management.StandardMBean;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.security.AccessController;
import java.security.Permission;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import javax.management.JMX;
import sun.management.ManagementFactoryHelper;
import sun.management.ExtendedPlatformComponent;

/**
 * The {@code ManagementFactory} class is a factory class for getting
 * managed beans for the Java platform.
 * This class consists of static methods each of which returns
 * one or more <i>platform MXBeans</i> representing
 * the management interface of a component of the Java virtual
 * machine.
 *
 * <h3><a name="MXBean">Platform MXBeans</a></h3>
 * <p>
 * A platform MXBean is a <i>managed bean</i> that
 * conforms to the <a href="../../../javax/management/package-summary.html">JMX</a>
 * Instrumentation Specification and only uses a set of basic data types.
 * A JMX management application and the {@linkplain
 * #getPlatformMBeanServer platform MBeanServer}
 * can interoperate without requiring classes for MXBean specific
 * data types.
 * The data types being transmitted between the JMX connector
 * server and the connector client are
 * {@linkplain javax.management.openmbean.OpenType open types}
 * and this allows interoperation across versions.
 * See <a href="../../../javax/management/MXBean.html#MXBean-spec">
 * the specification of MXBeans</a> for details.
 *
 * <a name="MXBeanNames"></a>
 * <p>Each platform MXBean is a {@link PlatformManagedObject}
 * and it has a unique
 * {@link javax.management.ObjectName ObjectName} for
 * registration in the platform {@code MBeanServer} as returned by
 * by the {@link PlatformManagedObject#getObjectName getObjectName}
 * method.
 *
 * <p>
 * An application can access a platform MXBean in the following ways:
 * <h4>1. Direct access to an MXBean interface</h4>
 * <blockquote>
 * <ul>
 *     <li>Get an MXBean instance by calling the
 *         {@link #getPlatformMXBean(Class) getPlatformMXBean} or
 *         {@link #getPlatformMXBeans(Class) getPlatformMXBeans} method
 *         and access the MXBean locally in the running
 *         virtual machine.
 *         </li>
 *     <li>Construct an MXBean proxy instance that forwards the
 *         method calls to a given {@link MBeanServer MBeanServer} by calling
 *         the {@link #getPlatformMXBean(MBeanServerConnection, Class)} or
 *         {@link #getPlatformMXBeans(MBeanServerConnection, Class)} method.
 *         The {@link #newPlatformMXBeanProxy newPlatformMXBeanProxy} method
 *         can also be used to construct an MXBean proxy instance of
 *         a given {@code ObjectName}.
 *         A proxy is typically constructed to remotely access
 *         an MXBean of another running virtual machine.
 *         </li>
 * </ul>
 * <h4>2. Indirect access to an MXBean interface via MBeanServer</h4>
 * <ul>
 *     <li>Go through the platform {@code MBeanServer} to access MXBeans
 *         locally or a specific <tt>MBeanServerConnection</tt> to access
 *         MXBeans remotely.
 *         The attributes and operations of an MXBean use only
 *         <em>JMX open types</em> which include basic data types,
 *         {@link javax.management.openmbean.CompositeData CompositeData},
 *         and {@link javax.management.openmbean.TabularData TabularData}
 *         defined in
 *         {@link javax.management.openmbean.OpenType OpenType}.
 *         The mapping is specified in
 *         the {@linkplain javax.management.MXBean MXBean} specification
 *         for details.
 *        </li>
 * </ul>
 * </blockquote>
 *
 * <p>
 * The {@link #getPlatformManagementInterfaces getPlatformManagementInterfaces}
 * method returns all management interfaces supported in the Java virtual machine
 * including the standard management interfaces listed in the tables
 * below as well as the management interfaces extended by the JDK implementation.
 * <p>
 * A Java virtual machine has a single instance of the following management
 * interfaces:
 *
 * <blockquote>
 * <table border summary="The list of Management Interfaces and their single instances">
 * <tr>
 * <th>Management Interface</th>
 * <th>ObjectName</th>
 * </tr>
 * <tr>
 * <td> {@link ClassLoadingMXBean} </td>
 * <td> {@link #CLASS_LOADING_MXBEAN_NAME
 *             java.lang:type=ClassLoading}</td>
 * </tr>
 * <tr>
 * <td> {@link MemoryMXBean} </td>
 * <td> {@link #MEMORY_MXBEAN_NAME
 *             java.lang:type=Memory}</td>
 * </tr>
 * <tr>
 * <td> {@link ThreadMXBean} </td>
 * <td> {@link #THREAD_MXBEAN_NAME
 *             java.lang:type=Threading}</td>
 * </tr>
 * <tr>
 * <td> {@link RuntimeMXBean} </td>
 * <td> {@link #RUNTIME_MXBEAN_NAME
 *             java.lang:type=Runtime}</td>
 * </tr>
 * <tr>
 * <td> {@link OperatingSystemMXBean} </td>
 * <td> {@link #OPERATING_SYSTEM_MXBEAN_NAME
 *             java.lang:type=OperatingSystem}</td>
 * </tr>
 * <tr>
 * <td> {@link PlatformLoggingMXBean} </td>
 * <td> {@link java.util.logging.LogManager#LOGGING_MXBEAN_NAME
 *             java.util.logging:type=Logging}</td>
 * </tr>
 * </table>
 * </blockquote>
 *
 * <p>
 * A Java virtual machine has zero or a single instance of
 * the following management interfaces.
 *
 * <blockquote>
 * <table border summary="The list of Management Interfaces and their single instances">
 * <tr>
 * <th>Management Interface</th>
 * <th>ObjectName</th>
 * </tr>
 * <tr>
 * <td> {@link CompilationMXBean} </td>
 * <td> {@link #COMPILATION_MXBEAN_NAME
 *             java.lang:type=Compilation}</td>
 * </tr>
 * </table>
 * </blockquote>
 *
 * <p>
 * A Java virtual machine may have one or more instances of the following
 * management interfaces.
 * <blockquote>
 * <table border summary="The list of Management Interfaces and their single instances">
 * <tr>
 * <th>Management Interface</th>
 * <th>ObjectName</th>
 * </tr>
 * <tr>
 * <td> {@link GarbageCollectorMXBean} </td>
 * <td> {@link #GARBAGE_COLLECTOR_MXBEAN_DOMAIN_TYPE
 *             java.lang:type=GarbageCollector}<tt>,name=</tt><i>collector's name</i></td>
 * </tr>
 * <tr>
 * <td> {@link MemoryManagerMXBean} </td>
 * <td> {@link #MEMORY_MANAGER_MXBEAN_DOMAIN_TYPE
 *             java.lang:type=MemoryManager}<tt>,name=</tt><i>manager's name</i></td>
 * </tr>
 * <tr>
 * <td> {@link MemoryPoolMXBean} </td>
 * <td> {@link #MEMORY_POOL_MXBEAN_DOMAIN_TYPE
 *             java.lang:type=MemoryPool}<tt>,name=</tt><i>pool's name</i></td>
 * </tr>
 * <tr>
 * <td> {@link BufferPoolMXBean} </td>
 * <td> {@code java.nio:type=BufferPool,name=}<i>pool name</i></td>
 * </tr>
 * </table>
 * </blockquote>
 *
 * <p>
 *  {@code ManagementFactory}类是一个工厂类,用于获取Java平台的托管bean。
 * 该类由静态方法组成,每个静态方法返回表示Java虚拟机的组件的管理接口的一个或多个平台MXBeans </i>。
 * 
 *  <h3> <a name="MXBean">平台MXBeans </a> </h3>
 * <p>
 *  平台MXBean是符合<a href="../../../javax/management/package-summary.html"> JMX </a>工具规范的<i>托管bean </i>并且仅使
 * 用一组基本数据类型。
 *  JMX管理应用程序和{@linkplain #getPlatformMBeanServer平台MBeanServer}可以互操作,而不需要MXBean特定数据类型的类。
 * 在JMX连接器服务器和连接器客户端之间传输的数据类型是{@linkplain javax.management.openmbean.OpenType open types},这允许跨版本进行交互操作。
 * 有关详细信息,请参见<a href="../../../javax/management/MXBean.html#MXBean-spec"> MXBeans规范</a>。
 * 
 *  <a name="MXBeanNames"> </a> <p>每个平台MXBean是一个{@link PlatformManagedObject},它在平台{@code MBeanServer}中注册
 * 有一个唯一的{@link javax.management.ObjectName ObjectName}由{@link PlatformManagedObject#getObjectName getObjectName}
 * 方法返回。
 * 
 * <p>
 * 应用程序可以通过以下方式访问MXBean平台：<h4> 1。直接访问MXBean接口</h4>
 * <blockquote>
 * <ul>
 *  <li>通过调用{@link #getPlatformMXBean(Class)getPlatformMXBean}或{@link #getPlatformMXBeans(Class)getPlatformMXBeans}
 * 方法获取MXBean实例,并在正在运行的虚拟机中本地访问MXBean。
 * </li>
 *  <li>通过调用{@link #getPlatformMXBean(MBeanServerConnection,Class)}或{@link #getPlatformMXBeans(MBeanServerConnection,Class)}
 * 方法,构造一个MXBean代理实例,以将方法调用转发给给定的{@link MBeanServer MBeanServer}。
 *  {@link #newPlatformMXBeanProxy newPlatformMXBeanProxy}方法也可用于构造给定{@code ObjectName}的MXBean代理实例。
 * 代理通常被构造为远程访问另一正在运行的虚拟机的MXBean。
 * </li>
 * </ul>
 *  <h4> 2。通过MBeanServer </h4>间接访问MXBean接口
 * <ul>
 *  <li>通过平台{@code MBeanServer}访问本地MXBeans或特定的<tt> MBeanServerConnection </tt>以远程访问MXBeans。
 *  MXBean的属性和操作只使用包含基本数据类型的{emlink} JMX打开类型</em>,{@link javax.management.openmbean.CompositeData CompositeData}
 * 和{@link javax.management.openmbean.TabularData TabularData }在{@link javax.management.openmbean.OpenType OpenType}
 * 中定义。
 *  <li>通过平台{@code MBeanServer}访问本地MXBeans或特定的<tt> MBeanServerConnection </tt>以远程访问MXBeans。
 * 有关详细信息,该映射在{@linkplain javax.management.MXBean MXBean}规范中指定。
 * </li>
 * </ul>
 * </blockquote>
 * 
 * <p>
 * {@link #getPlatformManagementInterfaces getPlatformManagementInterfaces}方法返回Java虚拟机中支持的所有管理接口,包括下表中列出
 * 的标准管理接口以及JDK实现扩展的管理接口。
 * <p>
 *  Java虚拟机具有以下管理接口的单个​​实例：
 * 
 * <blockquote>
 * <table border summary="The list of Management Interfaces and their single instances">
 * <tr>
 *  <th>管理接口</th> <th> ObjectName </th>
 * </tr>
 * <tr>
 *  <td> {@link ClassLoadingMXBean} </td> <td> {@link #CLASS_LOADING_MXBEAN_NAME java.lang：type = ClassLoading}
 *  </td>。
 * </tr>
 * <tr>
 *  <td> {@link MemoryMXBean} </td> <td> {@link #MEMORY_MXBEAN_NAME java.lang：type = Memory} </td>
 * </tr>
 * <tr>
 *  <td> {@link ThreadMXBean} </td> <td> {@link #THREAD_MXBEAN_NAME java.lang：type = Threading} </td>
 * </tr>
 * <tr>
 *  <td> {@link RuntimeMXBean} </td> <td> {@link #RUNTIME_MXBEAN_NAME java.lang：type = Runtime} </td>
 * </tr>
 * <tr>
 *  <td> {@link OperatingSystemMXBean} </td> <td> {@link #OPERATING_SYSTEM_MXBEAN_NAME java.lang：type = OperatingSystem}
 *  </td>。
 * </tr>
 * <tr>
 *  <td> {@link PlatformLoggingMXBean} </td> <td> {@link java.util.logging.LogManager#LOGGING_MXBEAN_NAME java.util.logging：type = Logging}
 *  </td>。
 * </tr>
 * </table>
 * </blockquote>
 * 
 * <p>
 *  Java虚拟机具有零个或以下管理接口的单个​​实例。
 * 
 * <blockquote>
 * <table border summary="The list of Management Interfaces and their single instances">
 * <tr>
 *  <th>管理接口</th> <th> ObjectName </th>
 * </tr>
 * <tr>
 *  <td> {@link CompilationMXBean} </td> <td> {@link #COMPILATION_MXBEAN_NAME java.lang：type = Compilation}
 *  </td>。
 * </tr>
 * </table>
 * </blockquote>
 * 
 * <p>
 *  Java虚拟机可以具有以下管理接口的一个或多个实例。
 * <blockquote>
 * <table border summary="The list of Management Interfaces and their single instances">
 * <tr>
 *  <th>管理接口</th> <th> ObjectName </th>
 * </tr>
 * <tr>
 * <td> {@link GarbageCollectorMXBean} </td> <td> {@link #GARBAGE_COLLECTOR_MXBEAN_DOMAIN_TYPE java.lang：type = GarbageCollector}
 *  <tt>,name = </tt> <i>收集者姓名</t> >。
 * </tr>
 * <tr>
 *  <td> {@link MemoryManagerMXBean} </td> <td> {@link #MEMORY_MANAGER_MXBEAN_DOMAIN_TYPE java.lang：type = MemoryManager}
 *  <tt>,name = </tt> <i>经理姓名</t> >。
 * </tr>
 * <tr>
 *  <td> {@link MemoryPoolMXBean} </td> <td> {@link #MEMORY_POOL_MXBEAN_DOMAIN_TYPE java.lang：type = MemoryPool}
 *  <tt>,name = </tt> <i>泳池名称</i> >。
 * </tr>
 * <tr>
 *  <td> {@link BufferPoolMXBean} </td> <td> {@code java.nio：type = BufferPool,name =} <i>池名称</i> </td>。
 * </tr>
 * </table>
 * </blockquote>
 * 
 * 
 * @see <a href="../../../javax/management/package-summary.html">
 *      JMX Specification</a>
 * @see <a href="package-summary.html#examples">
 *      Ways to Access Management Metrics</a>
 * @see javax.management.MXBean
 *
 * @author  Mandy Chung
 * @since   1.5
 */
public class ManagementFactory {
    // A class with only static fields and methods.
    private ManagementFactory() {};

    /**
     * String representation of the
     * <tt>ObjectName</tt> for the {@link ClassLoadingMXBean}.
     * <p>
     *  {@link ClassLoadingMXBean}的<tt> ObjectName </tt>的字符串表示形式。
     * 
     */
    public final static String CLASS_LOADING_MXBEAN_NAME =
        "java.lang:type=ClassLoading";

    /**
     * String representation of the
     * <tt>ObjectName</tt> for the {@link CompilationMXBean}.
     * <p>
     *  {@link CompilationMXBean}的<tt> ObjectName </tt>的字符串表示形式。
     * 
     */
    public final static String COMPILATION_MXBEAN_NAME =
        "java.lang:type=Compilation";

    /**
     * String representation of the
     * <tt>ObjectName</tt> for the {@link MemoryMXBean}.
     * <p>
     *  {@link MemoryMXBean}的<tt> ObjectName </tt>的字符串表示形式。
     * 
     */
    public final static String MEMORY_MXBEAN_NAME =
        "java.lang:type=Memory";

    /**
     * String representation of the
     * <tt>ObjectName</tt> for the {@link OperatingSystemMXBean}.
     * <p>
     *  {@link OperatingSystemMXBean}的<tt> ObjectName </tt>的字符串表示形式。
     * 
     */
    public final static String OPERATING_SYSTEM_MXBEAN_NAME =
        "java.lang:type=OperatingSystem";

    /**
     * String representation of the
     * <tt>ObjectName</tt> for the {@link RuntimeMXBean}.
     * <p>
     *  {@link RuntimeMXBean}的<tt> ObjectName </tt>的字符串表示形式。
     * 
     */
    public final static String RUNTIME_MXBEAN_NAME =
        "java.lang:type=Runtime";

    /**
     * String representation of the
     * <tt>ObjectName</tt> for the {@link ThreadMXBean}.
     * <p>
     *  {@link ThreadMXBean}的<tt> ObjectName </tt>的字符串表示形式。
     * 
     */
    public final static String THREAD_MXBEAN_NAME =
        "java.lang:type=Threading";

    /**
     * The domain name and the type key property in
     * the <tt>ObjectName</tt> for a {@link GarbageCollectorMXBean}.
     * The unique <tt>ObjectName</tt> for a <tt>GarbageCollectorMXBean</tt>
     * can be formed by appending this string with
     * "<tt>,name=</tt><i>collector's name</i>".
     * <p>
     *  {@link GarbageCollectorMXBean}的<tt> ObjectName </tt>中的域名和类型键属性。
     * 可以通过将此字符串附加"<tt>,name = </tt> <i>收集器名称</i>"来形成<tt> GarbageCollectorMXBean </tt>的唯一<tt> ObjectName </tt>
     * 。
     *  {@link GarbageCollectorMXBean}的<tt> ObjectName </tt>中的域名和类型键属性。
     * 
     */
    public final static String GARBAGE_COLLECTOR_MXBEAN_DOMAIN_TYPE =
        "java.lang:type=GarbageCollector";

    /**
     * The domain name and the type key property in
     * the <tt>ObjectName</tt> for a {@link MemoryManagerMXBean}.
     * The unique <tt>ObjectName</tt> for a <tt>MemoryManagerMXBean</tt>
     * can be formed by appending this string with
     * "<tt>,name=</tt><i>manager's name</i>".
     * <p>
     * {@link MemoryManagerMXBean}的<tt> ObjectName </tt>中的域名和类型键属性。
     * 可以通过将此字符串附加"<tt>,name = </tt> <i>经理姓名</i>"来形成<tt> MemoryManagerMXBean </tt>的唯一<tt> ObjectName </tt>。
     * 
     */
    public final static String MEMORY_MANAGER_MXBEAN_DOMAIN_TYPE=
        "java.lang:type=MemoryManager";

    /**
     * The domain name and the type key property in
     * the <tt>ObjectName</tt> for a {@link MemoryPoolMXBean}.
     * The unique <tt>ObjectName</tt> for a <tt>MemoryPoolMXBean</tt>
     * can be formed by appending this string with
     * <tt>,name=</tt><i>pool's name</i>.
     * <p>
     *  {@link MemoryPoolMXBean}的<tt> ObjectName </tt>中的域名和类型键属性。
     * 可以通过将此字符串附加到<tt>,name = </tt> <i>池名称</i>来形成<tt> MemoryPoolMXBean </tt>的唯一<tt> ObjectName </tt>。
     * 
     */
    public final static String MEMORY_POOL_MXBEAN_DOMAIN_TYPE=
        "java.lang:type=MemoryPool";

    /**
     * Returns the managed bean for the class loading system of
     * the Java virtual machine.
     *
     * <p>
     *  返回Java虚拟机的类加载系统的受管Bean。
     * 
     * 
     * @return a {@link ClassLoadingMXBean} object for
     * the Java virtual machine.
     */
    public static ClassLoadingMXBean getClassLoadingMXBean() {
        return ManagementFactoryHelper.getClassLoadingMXBean();
    }

    /**
     * Returns the managed bean for the memory system of
     * the Java virtual machine.
     *
     * <p>
     *  返回Java虚拟机的内存系统的受管Bean。
     * 
     * 
     * @return a {@link MemoryMXBean} object for the Java virtual machine.
     */
    public static MemoryMXBean getMemoryMXBean() {
        return ManagementFactoryHelper.getMemoryMXBean();
    }

    /**
     * Returns the managed bean for the thread system of
     * the Java virtual machine.
     *
     * <p>
     *  返回Java虚拟机的线程系统的受管Bean。
     * 
     * 
     * @return a {@link ThreadMXBean} object for the Java virtual machine.
     */
    public static ThreadMXBean getThreadMXBean() {
        return ManagementFactoryHelper.getThreadMXBean();
    }

    /**
     * Returns the managed bean for the runtime system of
     * the Java virtual machine.
     *
     * <p>
     *  返回Java虚拟机的运行时系统的受管bean。
     * 
     * 
     * @return a {@link RuntimeMXBean} object for the Java virtual machine.

     */
    public static RuntimeMXBean getRuntimeMXBean() {
        return ManagementFactoryHelper.getRuntimeMXBean();
    }

    /**
     * Returns the managed bean for the compilation system of
     * the Java virtual machine.  This method returns <tt>null</tt>
     * if the Java virtual machine has no compilation system.
     *
     * <p>
     *  返回Java虚拟机的编译系统的受管bean。如果Java虚拟机没有编译系统,此方法返回<tt> null </tt>。
     * 
     * 
     * @return a {@link CompilationMXBean} object for the Java virtual
     *   machine or <tt>null</tt> if the Java virtual machine has
     *   no compilation system.
     */
    public static CompilationMXBean getCompilationMXBean() {
        return ManagementFactoryHelper.getCompilationMXBean();
    }

    /**
     * Returns the managed bean for the operating system on which
     * the Java virtual machine is running.
     *
     * <p>
     *  返回运行Java虚拟机的操作系统的受管Bean。
     * 
     * 
     * @return an {@link OperatingSystemMXBean} object for
     * the Java virtual machine.
     */
    public static OperatingSystemMXBean getOperatingSystemMXBean() {
        return ManagementFactoryHelper.getOperatingSystemMXBean();
    }

    /**
     * Returns a list of {@link MemoryPoolMXBean} objects in the
     * Java virtual machine.
     * The Java virtual machine can have one or more memory pools.
     * It may add or remove memory pools during execution.
     *
     * <p>
     *  返回Java虚拟机中的{@link MemoryPoolMXBean}对象的列表。 Java虚拟机可以有一个或多个内存池。它可以在执行期间添加或删除内存池。
     * 
     * 
     * @return a list of <tt>MemoryPoolMXBean</tt> objects.
     *
     */
    public static List<MemoryPoolMXBean> getMemoryPoolMXBeans() {
        return ManagementFactoryHelper.getMemoryPoolMXBeans();
    }

    /**
     * Returns a list of {@link MemoryManagerMXBean} objects
     * in the Java virtual machine.
     * The Java virtual machine can have one or more memory managers.
     * It may add or remove memory managers during execution.
     *
     * <p>
     *  返回Java虚拟机中的{@link MemoryManagerMXBean}对象列表。 Java虚拟机可以具有一个或多个内存管理器。它可以在执行期间添加或删除内存管理器。
     * 
     * 
     * @return a list of <tt>MemoryManagerMXBean</tt> objects.
     *
     */
    public static List<MemoryManagerMXBean> getMemoryManagerMXBeans() {
        return ManagementFactoryHelper.getMemoryManagerMXBeans();
    }


    /**
     * Returns a list of {@link GarbageCollectorMXBean} objects
     * in the Java virtual machine.
     * The Java virtual machine may have one or more
     * <tt>GarbageCollectorMXBean</tt> objects.
     * It may add or remove <tt>GarbageCollectorMXBean</tt>
     * during execution.
     *
     * <p>
     * 返回Java虚拟机中的{@link GarbageCollectorMXBean}对象的列表。 Java虚拟机可以具有一个或多个<tt> GarbageCollectorMXBean </tt>对象。
     * 它可以在执行期间添加或删除<tt> GarbageCollectorMXBean </tt>。
     * 
     * 
     * @return a list of <tt>GarbageCollectorMXBean</tt> objects.
     *
     */
    public static List<GarbageCollectorMXBean> getGarbageCollectorMXBeans() {
        return ManagementFactoryHelper.getGarbageCollectorMXBeans();
    }

    private static MBeanServer platformMBeanServer;
    /**
     * Returns the platform {@link javax.management.MBeanServer MBeanServer}.
     * On the first call to this method, it first creates the platform
     * {@code MBeanServer} by calling the
     * {@link javax.management.MBeanServerFactory#createMBeanServer
     * MBeanServerFactory.createMBeanServer}
     * method and registers each platform MXBean in this platform
     * {@code MBeanServer} with its
     * {@link PlatformManagedObject#getObjectName ObjectName}.
     * This method, in subsequent calls, will simply return the
     * initially created platform {@code MBeanServer}.
     * <p>
     * MXBeans that get created and destroyed dynamically, for example,
     * memory {@link MemoryPoolMXBean pools} and
     * {@link MemoryManagerMXBean managers},
     * will automatically be registered and deregistered into the platform
     * {@code MBeanServer}.
     * <p>
     * If the system property {@code javax.management.builder.initial}
     * is set, the platform {@code MBeanServer} creation will be done
     * by the specified {@link javax.management.MBeanServerBuilder}.
     * <p>
     * It is recommended that this platform MBeanServer also be used
     * to register other application managed beans
     * besides the platform MXBeans.
     * This will allow all MBeans to be published through the same
     * {@code MBeanServer} and hence allow for easier network publishing
     * and discovery.
     * Name conflicts with the platform MXBeans should be avoided.
     *
     * <p>
     *  返回平台{@link javax.management.MBeanServer MBeanServer}。
     * 在第一次调用此方法时,它首先通过调用{@link javax.management.MBeanServerFactory#createMBeanServer MBeanServerFactory.createMBeanServer}
     * 方法创建平台{@code MBeanServer},并在此平台{@code MBeanServer}中注册每个平台MXBean其{@link PlatformManagedObject#getObjectName ObjectName}
     * 。
     *  返回平台{@link javax.management.MBeanServer MBeanServer}。这种方法,在后续调用中,将简单地返回最初创建的平台{@code MBeanServer}。
     * <p>
     *  动态创建和销毁的MXBeans(例如内存{@link MemoryPoolMXBean pools}和{@link MemoryManagerMXBean管理器})将自动注册并注销到平台{@code MBeanServer}
     * 中。
     * <p>
     *  如果设置了系统属性{@code javax.management.builder.initial},则平台{@code MBeanServer}创建将由指定的{@link javax.management.MBeanServerBuilder}
     * 完成。
     * <p>
     * 建议此平台MBeanServer也用于注册除平台MXBeans之外的其他应用程序管理的bean。
     * 这将允许所有MBean通过相同的{@code MBeanServer}发布,因此允许更容易的网络发布和发现。名称与平台MXBeans冲突应该避免。
     * 
     * 
     * @return the platform {@code MBeanServer}; the platform
     *         MXBeans are registered into the platform {@code MBeanServer}
     *         at the first time this method is called.
     *
     * @exception SecurityException if there is a security manager
     * and the caller does not have the permission required by
     * {@link javax.management.MBeanServerFactory#createMBeanServer}.
     *
     * @see javax.management.MBeanServerFactory
     * @see javax.management.MBeanServerFactory#createMBeanServer
     */
    public static synchronized MBeanServer getPlatformMBeanServer() {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            Permission perm = new MBeanServerPermission("createMBeanServer");
            sm.checkPermission(perm);
        }

        if (platformMBeanServer == null) {
            platformMBeanServer = MBeanServerFactory.createMBeanServer();
            for (PlatformComponent pc : PlatformComponent.values()) {
                List<? extends PlatformManagedObject> list =
                    pc.getMXBeans(pc.getMXBeanInterface());
                for (PlatformManagedObject o : list) {
                    // Each PlatformComponent represents one management
                    // interface. Some MXBean may extend another one.
                    // The MXBean instances for one platform component
                    // (returned by pc.getMXBeans()) might be also
                    // the MXBean instances for another platform component.
                    // e.g. com.sun.management.GarbageCollectorMXBean
                    //
                    // So need to check if an MXBean instance is registered
                    // before registering into the platform MBeanServer
                    if (!platformMBeanServer.isRegistered(o.getObjectName())) {
                        addMXBean(platformMBeanServer, o);
                    }
                }
            }
            HashMap<ObjectName, DynamicMBean> dynmbeans =
                    ManagementFactoryHelper.getPlatformDynamicMBeans();
            for (Map.Entry<ObjectName, DynamicMBean> e : dynmbeans.entrySet()) {
                addDynamicMBean(platformMBeanServer, e.getValue(), e.getKey());
            }
            for (final PlatformManagedObject o :
                                       ExtendedPlatformComponent.getMXBeans()) {
                if (!platformMBeanServer.isRegistered(o.getObjectName())) {
                    addMXBean(platformMBeanServer, o);
                }
            }
        }
        return platformMBeanServer;
    }

    /**
     * Returns a proxy for a platform MXBean interface of a
     * given <a href="#MXBeanNames">MXBean name</a>
     * that forwards its method calls through the given
     * <tt>MBeanServerConnection</tt>.
     *
     * <p>This method is equivalent to:
     * <blockquote>
     * {@link java.lang.reflect.Proxy#newProxyInstance
     *        Proxy.newProxyInstance}<tt>(mxbeanInterface.getClassLoader(),
     *        new Class[] { mxbeanInterface }, handler)</tt>
     * </blockquote>
     *
     * where <tt>handler</tt> is an {@link java.lang.reflect.InvocationHandler
     * InvocationHandler} to which method invocations to the MXBean interface
     * are dispatched. This <tt>handler</tt> converts an input parameter
     * from an MXBean data type to its mapped open type before forwarding
     * to the <tt>MBeanServer</tt> and converts a return value from
     * an MXBean method call through the <tt>MBeanServer</tt>
     * from an open type to the corresponding return type declared in
     * the MXBean interface.
     *
     * <p>
     * If the MXBean is a notification emitter (i.e.,
     * it implements
     * {@link javax.management.NotificationEmitter NotificationEmitter}),
     * both the <tt>mxbeanInterface</tt> and <tt>NotificationEmitter</tt>
     * will be implemented by this proxy.
     *
     * <p>
     * <b>Notes:</b>
     * <ol>
     * <li>Using an MXBean proxy is a convenience remote access to
     * a platform MXBean of a running virtual machine.  All method
     * calls to the MXBean proxy are forwarded to an
     * <tt>MBeanServerConnection</tt> where
     * {@link java.io.IOException IOException} may be thrown
     * when the communication problem occurs with the connector server.
     * An application remotely accesses the platform MXBeans using
     * proxy should prepare to catch <tt>IOException</tt> as if
     * accessing with the <tt>MBeanServerConnector</tt> interface.</li>
     *
     * <li>When a client application is designed to remotely access MXBeans
     * for a running virtual machine whose version is different than
     * the version on which the application is running,
     * it should prepare to catch
     * {@link java.io.InvalidObjectException InvalidObjectException}
     * which is thrown when an MXBean proxy receives a name of an
     * enum constant which is missing in the enum class loaded in
     * the client application. </li>
     *
     * <li>{@link javax.management.MBeanServerInvocationHandler
     * MBeanServerInvocationHandler} or its
     * {@link javax.management.MBeanServerInvocationHandler#newProxyInstance
     * newProxyInstance} method cannot be used to create
     * a proxy for a platform MXBean. The proxy object created
     * by <tt>MBeanServerInvocationHandler</tt> does not handle
     * the properties of the platform MXBeans described in
     * the <a href="#MXBean">class specification</a>.
     *</li>
     * </ol>
     *
     * <p>
     *  返回给定<a href="#MXBeanNames"> MXBean名称</a>的平台MXBean接口的代理,该代理通过给定的<tt> MBeanServerConnection </tt>转发其方法
     * 调用。
     * 
     *  <p>此方法等效于：
     * <blockquote>
     *  {@link java.lang.reflect.Proxy#newProxyInstance Proxy.newProxyInstance} <tt>(mxbeanInterface.getClas
     * sLoader(),new Class [] {m​​xbeanInterface},handler)</tt>。
     * </blockquote>
     * 
     *  其中<tt>处理程序</tt>是调用MXBean接口的方法调用的{@link java.lang.reflect.InvocationHandler InvocationHandler}。
     * 此<tt>处理程序</tt>在转发到<tt> MBeanServer </tt>之前将输入参数从MXBean数据类型转换为其映射的打开类型,并通过<tt>转换来自MXBean方法调用的返回值, MBea
     * nServer </tt>从开放类型更改为在MXBean接口中声明的相应返回类型。
     *  其中<tt>处理程序</tt>是调用MXBean接口的方法调用的{@link java.lang.reflect.InvocationHandler InvocationHandler}。
     * 
     * <p>
     *  如果MXBean是通知发射器(即,它实现{@link javax.management.NotificationEmitter NotificationEmitter}),则<tt> mxbeanIn
     * terface </tt>和<tt> NotificationEmitter </tt>将由此代理实现。
     * 
     * <p>
     *  <b>注意：</b>
     * <ol>
     * <li>使用MXBean代理可方便地远程访问正在运行的虚拟机的平台MXBean。
     * 将MXBean代理的所有方法调用转发到<tt> MBeanServerConnection </tt>,当连接器服务器发生通信问题时,可能会抛出{@link java.io.IOException IOException}
     * 。
     * <li>使用MXBean代理可方便地远程访问正在运行的虚拟机的平台MXBean。
     * 应用程序远程访问平台MXBeans使用代理应准备捕获<tt> IOException </tt>,就像使用<tt> MBeanServerConnector </tt>界面访问。</li>。
     * 
     *  <li>当客户端应用程序设计为远程访问其版本与运行应用程序的版本不同的正在运行的虚拟机的MXBeans时,应准备捕获抛出的{@link java.io.InvalidObjectException InvalidObjectException}
     * 当MXBean代理接收到在客户端应用程序中加载的枚举类中缺少的枚举常量的名称。
     *  </li>。
     * 
     *  <li> {@ link javax.management.MBeanServerInvocationHandler MBeanServerInvocationHandler}或其{@link javax.management.MBeanServerInvocationHandler#newProxyInstance newProxyInstance}
     * 方法不能用于为平台MXBean创建代理。
     * 由<tt> MBeanServerInvocationHandler </tt>创建的代理对象不处理<a href="#MXBean">类规范</a>中描述的平台MXBeans的属性。
     * /li>
     * </ol>
     * 
     * @param connection the <tt>MBeanServerConnection</tt> to forward to.
     * @param mxbeanName the name of a platform MXBean within
     * <tt>connection</tt> to forward to. <tt>mxbeanName</tt> must be
     * in the format of {@link ObjectName ObjectName}.
     * @param mxbeanInterface the MXBean interface to be implemented
     * by the proxy.
     * @param <T> an {@code mxbeanInterface} type parameter
     *
     * @return a proxy for a platform MXBean interface of a
     * given <a href="#MXBeanNames">MXBean name</a>
     * that forwards its method calls through the given
     * <tt>MBeanServerConnection</tt>, or {@code null} if not exist.
     *
     * @throws IllegalArgumentException if
     * <ul>
     * <li><tt>mxbeanName</tt> is not with a valid
     *     {@link ObjectName ObjectName} format, or</li>
     * <li>the named MXBean in the <tt>connection</tt> is
     *     not a MXBean provided by the platform, or</li>
     * <li>the named MXBean is not registered in the
     *     <tt>MBeanServerConnection</tt>, or</li>
     * <li>the named MXBean is not an instance of the given
     *     <tt>mxbeanInterface</tt></li>
     * </ul>
     *
     * @throws java.io.IOException if a communication problem
     * occurred when accessing the <tt>MBeanServerConnection</tt>.
     */
    public static <T> T
        newPlatformMXBeanProxy(MBeanServerConnection connection,
                               String mxbeanName,
                               Class<T> mxbeanInterface)
            throws java.io.IOException {

        // Only allow MXBean interfaces from rt.jar loaded by the
        // bootstrap class loader
        final Class<?> cls = mxbeanInterface;
        ClassLoader loader =
            AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
                public ClassLoader run() {
                    return cls.getClassLoader();
                }
            });
        if (!sun.misc.VM.isSystemDomainLoader(loader)) {
            throw new IllegalArgumentException(mxbeanName +
                " is not a platform MXBean");
        }

        try {
            final ObjectName objName = new ObjectName(mxbeanName);
            // skip the isInstanceOf check for LoggingMXBean
            String intfName = mxbeanInterface.getName();
            if (!connection.isInstanceOf(objName, intfName)) {
                throw new IllegalArgumentException(mxbeanName +
                    " is not an instance of " + mxbeanInterface);
            }

            final Class[] interfaces;
            // check if the registered MBean is a notification emitter
            boolean emitter = connection.isInstanceOf(objName, NOTIF_EMITTER);

            // create an MXBean proxy
            return JMX.newMXBeanProxy(connection, objName, mxbeanInterface,
                                      emitter);
        } catch (InstanceNotFoundException|MalformedObjectNameException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Returns the platform MXBean implementing
     * the given {@code mxbeanInterface} which is specified
     * to have one single instance in the Java virtual machine.
     * This method may return {@code null} if the management interface
     * is not implemented in the Java virtual machine (for example,
     * a Java virtual machine with no compilation system does not
     * implement {@link CompilationMXBean});
     * otherwise, this method is equivalent to calling:
     * <pre>
     *    {@link #getPlatformMXBeans(Class)
     *      getPlatformMXBeans(mxbeanInterface)}.get(0);
     * </pre>
     *
     * <p>
     * 
     * 
     * @param mxbeanInterface a management interface for a platform
     *     MXBean with one single instance in the Java virtual machine
     *     if implemented.
     * @param <T> an {@code mxbeanInterface} type parameter
     *
     * @return the platform MXBean that implements
     * {@code mxbeanInterface}, or {@code null} if not exist.
     *
     * @throws IllegalArgumentException if {@code mxbeanInterface}
     * is not a platform management interface or
     * not a singleton platform MXBean.
     *
     * @since 1.7
     */
    public static <T extends PlatformManagedObject>
            T getPlatformMXBean(Class<T> mxbeanInterface) {
        PlatformComponent pc = PlatformComponent.getPlatformComponent(mxbeanInterface);
        if (pc == null) {
            T mbean = ExtendedPlatformComponent.getMXBean(mxbeanInterface);
            if (mbean != null) {
                return mbean;
            }
            throw new IllegalArgumentException(mxbeanInterface.getName() +
                " is not a platform management interface");
        }
        if (!pc.isSingleton())
            throw new IllegalArgumentException(mxbeanInterface.getName() +
                " can have zero or more than one instances");

        return pc.getSingletonMXBean(mxbeanInterface);
    }

    /**
     * Returns the list of platform MXBeans implementing
     * the given {@code mxbeanInterface} in the Java
     * virtual machine.
     * The returned list may contain zero, one, or more instances.
     * The number of instances in the returned list is defined
     * in the specification of the given management interface.
     * The order is undefined and there is no guarantee that
     * the list returned is in the same order as previous invocations.
     *
     * <p>
     * 返回平台MXBean实现给定的{@code mxbeanInterface},它被指定在Java虚拟机中有一个单一实例。
     * 如果管理接口未在Java虚拟机中实现(例如,没有编译系统的Java虚拟机不实现{@link CompilationMXBean}),此方法可能返回{@code null};否则,此方法等效于调用：。
     * <pre>
     *  {@link #getPlatformMXBeans(Class)getPlatformMXBeans(mxbeanInterface)}。get(0);
     * </pre>
     * 
     * 
     * @param mxbeanInterface a management interface for a platform
     *                        MXBean
     * @param <T> an {@code mxbeanInterface} type parameter
     *
     * @return the list of platform MXBeans that implement
     * {@code mxbeanInterface}.
     *
     * @throws IllegalArgumentException if {@code mxbeanInterface}
     * is not a platform management interface.
     *
     * @since 1.7
     */
    public static <T extends PlatformManagedObject> List<T>
            getPlatformMXBeans(Class<T> mxbeanInterface) {
        PlatformComponent pc = PlatformComponent.getPlatformComponent(mxbeanInterface);
        if (pc == null) {
            T mbean = ExtendedPlatformComponent.getMXBean(mxbeanInterface);
            if (mbean != null) {
                return Collections.singletonList(mbean);
            }
            throw new IllegalArgumentException(mxbeanInterface.getName() +
                " is not a platform management interface");
        }
        return Collections.unmodifiableList(pc.getMXBeans(mxbeanInterface));
    }

    /**
     * Returns the platform MXBean proxy for
     * {@code mxbeanInterface} which is specified to have one single
     * instance in a Java virtual machine and the proxy will
     * forward the method calls through the given {@code MBeanServerConnection}.
     * This method may return {@code null} if the management interface
     * is not implemented in the Java virtual machine being monitored
     * (for example, a Java virtual machine with no compilation system
     * does not implement {@link CompilationMXBean});
     * otherwise, this method is equivalent to calling:
     * <pre>
     *     {@link #getPlatformMXBeans(MBeanServerConnection, Class)
     *        getPlatformMXBeans(connection, mxbeanInterface)}.get(0);
     * </pre>
     *
     * <p>
     *  返回在Java虚拟机中实现给定{@code mxbeanInterface}的平台MXBeans的列表。返回的列表可以包含零个,一个或多个实例。返回列表中的实例数在给定管理接口的规范中定义。
     * 顺序是未定义的,并且不能保证返回的列表与先前调用的顺序相同。
     * 
     * 
     * @param connection the {@code MBeanServerConnection} to forward to.
     * @param mxbeanInterface a management interface for a platform
     *     MXBean with one single instance in the Java virtual machine
     *     being monitored, if implemented.
     * @param <T> an {@code mxbeanInterface} type parameter
     *
     * @return the platform MXBean proxy for
     * forwarding the method calls of the {@code mxbeanInterface}
     * through the given {@code MBeanServerConnection},
     * or {@code null} if not exist.
     *
     * @throws IllegalArgumentException if {@code mxbeanInterface}
     * is not a platform management interface or
     * not a singleton platform MXBean.
     * @throws java.io.IOException if a communication problem
     * occurred when accessing the {@code MBeanServerConnection}.
     *
     * @see #newPlatformMXBeanProxy
     * @since 1.7
     */
    public static <T extends PlatformManagedObject>
            T getPlatformMXBean(MBeanServerConnection connection,
                                Class<T> mxbeanInterface)
        throws java.io.IOException
    {
        PlatformComponent pc = PlatformComponent.getPlatformComponent(mxbeanInterface);
        if (pc == null) {
            T mbean = ExtendedPlatformComponent.getMXBean(mxbeanInterface);
            if (mbean != null) {
                ObjectName on = mbean.getObjectName();
                return ManagementFactory.newPlatformMXBeanProxy(connection,
                                                                on.getCanonicalName(),
                                                                mxbeanInterface);
            }
            throw new IllegalArgumentException(mxbeanInterface.getName() +
                " is not a platform management interface");
        }
        if (!pc.isSingleton())
            throw new IllegalArgumentException(mxbeanInterface.getName() +
                " can have zero or more than one instances");
        return pc.getSingletonMXBean(connection, mxbeanInterface);
    }

    /**
     * Returns the list of the platform MXBean proxies for
     * forwarding the method calls of the {@code mxbeanInterface}
     * through the given {@code MBeanServerConnection}.
     * The returned list may contain zero, one, or more instances.
     * The number of instances in the returned list is defined
     * in the specification of the given management interface.
     * The order is undefined and there is no guarantee that
     * the list returned is in the same order as previous invocations.
     *
     * <p>
     *  返回{@code mxbeanInterface}的平台MXBean代理,该代理指定在Java虚拟机中有一个实例,代理将通过给定的{@code MBeanServerConnection}转发方法调用
     * 。
     * 如果在被监视的Java虚拟机中没有实现管理接口(例如,没有编译系统的Java虚拟机不实现{@link CompilationMXBean}),此方法可能返回{@code null};否则,此方法等效于调
     * 用：。
     * <pre>
     * {@link #getPlatformMXBeans(MBeanServerConnection,Class)getPlatformMXBeans(connection,mxbeanInterface)}
     * 。
     * get(0);。
     * </pre>
     * 
     * 
     * @param connection the {@code MBeanServerConnection} to forward to.
     * @param mxbeanInterface a management interface for a platform
     *                        MXBean
     * @param <T> an {@code mxbeanInterface} type parameter
     *
     * @return the list of platform MXBean proxies for
     * forwarding the method calls of the {@code mxbeanInterface}
     * through the given {@code MBeanServerConnection}.
     *
     * @throws IllegalArgumentException if {@code mxbeanInterface}
     * is not a platform management interface.
     *
     * @throws java.io.IOException if a communication problem
     * occurred when accessing the {@code MBeanServerConnection}.
     *
     * @see #newPlatformMXBeanProxy
     * @since 1.7
     */
    public static <T extends PlatformManagedObject>
            List<T> getPlatformMXBeans(MBeanServerConnection connection,
                                       Class<T> mxbeanInterface)
        throws java.io.IOException
    {
        PlatformComponent pc = PlatformComponent.getPlatformComponent(mxbeanInterface);
        if (pc == null) {
            T mbean = ExtendedPlatformComponent.getMXBean(mxbeanInterface);
            if (mbean != null) {
                ObjectName on = mbean.getObjectName();
                T proxy = ManagementFactory.newPlatformMXBeanProxy(connection,
                            on.getCanonicalName(), mxbeanInterface);
                return Collections.singletonList(proxy);
            }
            throw new IllegalArgumentException(mxbeanInterface.getName() +
                " is not a platform management interface");
        }
        return Collections.unmodifiableList(pc.getMXBeans(connection, mxbeanInterface));
    }

    /**
     * Returns the set of {@code Class} objects, subinterface of
     * {@link PlatformManagedObject}, representing
     * all management interfaces for
     * monitoring and managing the Java platform.
     *
     * <p>
     *  返回平台MXBean代理的列表,用于通过给定的{@code MBeanServerConnection}转发{@code mxbeanInterface}的方法调用。
     * 返回的列表可以包含零个,一个或多个实例。返回列表中的实例数在给定管理接口的规范中定义。顺序是未定义的,并且不能保证返回的列表与先前调用的顺序相同。
     * 
     * 
     * @return the set of {@code Class} objects, subinterface of
     * {@link PlatformManagedObject} representing
     * the management interfaces for
     * monitoring and managing the Java platform.
     *
     * @since 1.7
     */
    public static Set<Class<? extends PlatformManagedObject>>
           getPlatformManagementInterfaces()
    {
        Set<Class<? extends PlatformManagedObject>> result =
            new HashSet<>();
        for (PlatformComponent component: PlatformComponent.values()) {
            result.add(component.getMXBeanInterface());
        }
        return Collections.unmodifiableSet(result);
    }

    private static final String NOTIF_EMITTER =
        "javax.management.NotificationEmitter";

    /**
     * Registers an MXBean.
     * <p>
     *  返回{@code Class}对象的子集,{@link PlatformManagedObject}的子接口,代表用于监视和管理Java平台的所有管理接口。
     * 
     */
    private static void addMXBean(final MBeanServer mbs, final PlatformManagedObject pmo) {
        // Make DynamicMBean out of MXBean by wrapping it with a StandardMBean
        try {
            AccessController.doPrivileged(new PrivilegedExceptionAction<Void>() {
                public Void run() throws InstanceAlreadyExistsException,
                                         MBeanRegistrationException,
                                         NotCompliantMBeanException {
                    final DynamicMBean dmbean;
                    if (pmo instanceof DynamicMBean) {
                        dmbean = DynamicMBean.class.cast(pmo);
                    } else if (pmo instanceof NotificationEmitter) {
                        dmbean = new StandardEmitterMBean(pmo, null, true, (NotificationEmitter) pmo);
                    } else {
                        dmbean = new StandardMBean(pmo, null, true);
                    }

                    mbs.registerMBean(dmbean, pmo.getObjectName());
                    return null;
                }
            });
        } catch (PrivilegedActionException e) {
            throw new RuntimeException(e.getException());
        }
    }

    /**
     * Registers a DynamicMBean.
     * <p>
     *  注册MXBean。
     * 
     */
    private static void addDynamicMBean(final MBeanServer mbs,
                                        final DynamicMBean dmbean,
                                        final ObjectName on) {
        try {
            AccessController.doPrivileged(new PrivilegedExceptionAction<Void>() {
                @Override
                public Void run() throws InstanceAlreadyExistsException,
                                         MBeanRegistrationException,
                                         NotCompliantMBeanException {
                    mbs.registerMBean(dmbean, on);
                    return null;
                }
            });
        } catch (PrivilegedActionException e) {
            throw new RuntimeException(e.getException());
        }
    }
}
