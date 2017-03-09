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

/**
 * The management interface for the runtime system of
 * the Java virtual machine.
 *
 * <p> A Java virtual machine has a single instance of the implementation
 * class of this interface.  This instance implementing this interface is
 * an <a href="ManagementFactory.html#MXBean">MXBean</a>
 * that can be obtained by calling
 * the {@link ManagementFactory#getRuntimeMXBean} method or
 * from the {@link ManagementFactory#getPlatformMBeanServer
 * platform <tt>MBeanServer</tt>} method.
 *
 * <p>The <tt>ObjectName</tt> for uniquely identifying the MXBean for
 * the runtime system within an MBeanServer is:
 * <blockquote>
 *    {@link ManagementFactory#RUNTIME_MXBEAN_NAME
 *           <tt>java.lang:type=Runtime</tt>}
 * </blockquote>
 *
 * It can be obtained by calling the
 * {@link PlatformManagedObject#getObjectName} method.
 *
 * <p> This interface defines several convenient methods for accessing
 * system properties about the Java virtual machine.
 *
 * <p>
 *  Java虚拟机的运行时系统的管理接口。
 * 
 *  <p> Java虚拟机具有此接口的实现类的单个实例。
 * 实现此接口的实例是<a href="ManagementFactory.html#MXBean"> MXBean </a>,可以通过调用{@link ManagementFactory#getRuntimeMXBean}
 * 方法或从{@link ManagementFactory#getPlatformMBeanServer platform < tt> MBeanServer </tt>}方法。
 *  <p> Java虚拟机具有此接口的实现类的单个实例。
 * 
 *  <p>用于唯一标识MBeanServer中运行时系统的MXBean的<tt> ObjectName </tt>是：
 * <blockquote>
 *  {@link ManagementFactory#RUNTIME_MXBEAN_NAME <tt> java.lang：type = Runtime </tt>}
 * </blockquote>
 * 
 *  它可以通过调用{@link PlatformManagedObject#getObjectName}方法获得。
 * 
 *  <p>此接口定义了几个方便的方法来访问有关Java虚拟机的系统属性。
 * 
 * 
 * @see ManagementFactory#getPlatformMXBeans(Class)
 * @see <a href="../../../javax/management/package-summary.html">
 *      JMX Specification.</a>
 * @see <a href="package-summary.html#examples">
 *      Ways to Access MXBeans</a>
 *
 * @author  Mandy Chung
 * @since   1.5
 */
public interface RuntimeMXBean extends PlatformManagedObject {
    /**
     * Returns the name representing the running Java virtual machine.
     * The returned name string can be any arbitrary string and
     * a Java virtual machine implementation can choose
     * to embed platform-specific useful information in the
     * returned name string.  Each running virtual machine could have
     * a different name.
     *
     * <p>
     *  返回表示正在运行的Java虚拟机的名称。返回的名称字符串可以是任何任意字符串,Java虚拟机实现可以选择在返回的名称字符串中嵌入平台特定的有用信息。每个正在运行的虚拟机可以有不同的名称。
     * 
     * 
     * @return the name representing the running Java virtual machine.
     */
    public String getName();

    /**
     * Returns the Java virtual machine implementation name.
     * This method is equivalent to {@link System#getProperty
     * System.getProperty("java.vm.name")}.
     *
     * <p>
     *  返回Java虚拟机实现名称。此方法等效于{@link System#getProperty System.getProperty("java.vm.name")}。
     * 
     * 
     * @return the Java virtual machine implementation name.
     *
     * @throws  java.lang.SecurityException
     *     if a security manager exists and its
     *     <code>checkPropertiesAccess</code> method doesn't allow access
     *     to this system property.
     * @see java.lang.SecurityManager#checkPropertyAccess(java.lang.String)
     * @see java.lang.System#getProperty
     */
    public String getVmName();

    /**
     * Returns the Java virtual machine implementation vendor.
     * This method is equivalent to {@link System#getProperty
     * System.getProperty("java.vm.vendor")}.
     *
     * <p>
     * 返回Java虚拟机实现供应商。此方法等同于{@link System#getProperty System.getProperty("java.vm.vendor")}。
     * 
     * 
     * @return the Java virtual machine implementation vendor.
     *
     * @throws  java.lang.SecurityException
     *     if a security manager exists and its
     *     <code>checkPropertiesAccess</code> method doesn't allow access
     *     to this system property.
     * @see java.lang.SecurityManager#checkPropertyAccess(java.lang.String)
     * @see java.lang.System#getProperty
     */
    public String getVmVendor();

    /**
     * Returns the Java virtual machine implementation version.
     * This method is equivalent to {@link System#getProperty
     * System.getProperty("java.vm.version")}.
     *
     * <p>
     *  返回Java虚拟机实现版本。此方法等效于{@link System#getProperty System.getProperty("java.vm.version")}。
     * 
     * 
     * @return the Java virtual machine implementation version.
     *
     * @throws  java.lang.SecurityException
     *     if a security manager exists and its
     *     <code>checkPropertiesAccess</code> method doesn't allow access
     *     to this system property.
     * @see java.lang.SecurityManager#checkPropertyAccess(java.lang.String)
     * @see java.lang.System#getProperty
     */
    public String getVmVersion();

    /**
     * Returns the Java virtual machine specification name.
     * This method is equivalent to {@link System#getProperty
     * System.getProperty("java.vm.specification.name")}.
     *
     * <p>
     *  返回Java虚拟机规范名称。此方法等效于{@link System#getProperty System.getProperty("java.vm.specification.name")}。
     * 
     * 
     * @return the Java virtual machine specification name.
     *
     * @throws  java.lang.SecurityException
     *     if a security manager exists and its
     *     <code>checkPropertiesAccess</code> method doesn't allow access
     *     to this system property.
     * @see java.lang.SecurityManager#checkPropertyAccess(java.lang.String)
     * @see java.lang.System#getProperty
     */
    public String getSpecName();

    /**
     * Returns the Java virtual machine specification vendor.
     * This method is equivalent to {@link System#getProperty
     * System.getProperty("java.vm.specification.vendor")}.
     *
     * <p>
     *  返回Java虚拟机规范供应商。此方法等效于{@link System#getProperty System.getProperty("java.vm.specification.vendor")}。
     * 
     * 
     * @return the Java virtual machine specification vendor.
     *
     * @throws  java.lang.SecurityException
     *     if a security manager exists and its
     *     <code>checkPropertiesAccess</code> method doesn't allow access
     *     to this system property.
     * @see java.lang.SecurityManager#checkPropertyAccess(java.lang.String)
     * @see java.lang.System#getProperty
     */
    public String getSpecVendor();

    /**
     * Returns the Java virtual machine specification version.
     * This method is equivalent to {@link System#getProperty
     * System.getProperty("java.vm.specification.version")}.
     *
     * <p>
     *  返回Java虚拟机规范版本。此方法等同于{@link System#getProperty System.getProperty("java.vm.specification.version")}。
     * 
     * 
     * @return the Java virtual machine specification version.
     *
     * @throws  java.lang.SecurityException
     *     if a security manager exists and its
     *     <code>checkPropertiesAccess</code> method doesn't allow access
     *     to this system property.
     * @see java.lang.SecurityManager#checkPropertyAccess(java.lang.String)
     * @see java.lang.System#getProperty
     */
    public String getSpecVersion();


    /**
     * Returns the version of the specification for the management interface
     * implemented by the running Java virtual machine.
     *
     * <p>
     *  返回正在运行的Java虚拟机实现的管理接口的规范版本。
     * 
     * 
     * @return the version of the specification for the management interface
     * implemented by the running Java virtual machine.
     */
    public String getManagementSpecVersion();

    /**
     * Returns the Java class path that is used by the system class loader
     * to search for class files.
     * This method is equivalent to {@link System#getProperty
     * System.getProperty("java.class.path")}.
     *
     * <p> Multiple paths in the Java class path are separated by the
     * path separator character of the platform of the Java virtual machine
     * being monitored.
     *
     * <p>
     *  返回系统类加载器用于搜索类文件的Java类路径。此方法等效于{@link System#getProperty System.getProperty("java.class.path")}。
     * 
     *  <p> Java类路径中的多个路径由正在监视的Java虚拟机的平台的路径分隔符分隔。
     * 
     * 
     * @return the Java class path.
     *
     * @throws  java.lang.SecurityException
     *     if a security manager exists and its
     *     <code>checkPropertiesAccess</code> method doesn't allow access
     *     to this system property.
     * @see java.lang.SecurityManager#checkPropertyAccess(java.lang.String)
     * @see java.lang.System#getProperty
     */
    public String getClassPath();

    /**
     * Returns the Java library path.
     * This method is equivalent to {@link System#getProperty
     * System.getProperty("java.library.path")}.
     *
     * <p> Multiple paths in the Java library path are separated by the
     * path separator character of the platform of the Java virtual machine
     * being monitored.
     *
     * <p>
     *  返回Java库路径。此方法等效于{@link System#getProperty System.getProperty("java.library.path")}。
     * 
     * <p> Java库路径中的多个路径由正在监视的Java虚拟机的平台的路径分隔符分隔。
     * 
     * 
     * @return the Java library path.
     *
     * @throws  java.lang.SecurityException
     *     if a security manager exists and its
     *     <code>checkPropertiesAccess</code> method doesn't allow access
     *     to this system property.
     * @see java.lang.SecurityManager#checkPropertyAccess(java.lang.String)
     * @see java.lang.System#getProperty
     */
    public String getLibraryPath();

    /**
     * Tests if the Java virtual machine supports the boot class path
     * mechanism used by the bootstrap class loader to search for class
     * files.
     *
     * <p>
     *  测试Java虚拟机是否支持引导类装入器用于搜索类文件的引导类路径机制。
     * 
     * 
     * @return <tt>true</tt> if the Java virtual machine supports the
     * class path mechanism; <tt>false</tt> otherwise.
     */
    public boolean isBootClassPathSupported();

    /**
     * Returns the boot class path that is used by the bootstrap class loader
     * to search for class files.
     *
     * <p> Multiple paths in the boot class path are separated by the
     * path separator character of the platform on which the Java
     * virtual machine is running.
     *
     * <p>A Java virtual machine implementation may not support
     * the boot class path mechanism for the bootstrap class loader
     * to search for class files.
     * The {@link #isBootClassPathSupported} method can be used
     * to determine if the Java virtual machine supports this method.
     *
     * <p>
     *  返回引导类装入器用于搜索类文件的引导类路径。
     * 
     *  <p>引导类路径中的多个路径由运行Java虚拟机的平台的路径分隔符分隔。
     * 
     *  <p> Java虚拟机实现可能不支持引导类路径机制,用于引导类加载器搜索类文件。 {@link #isBootClassPathSupported}方法可用于确定Java虚拟机是否支持此方法。
     * 
     * 
     * @return the boot class path.
     *
     * @throws java.lang.UnsupportedOperationException
     *     if the Java virtual machine does not support this operation.
     *
     * @throws  java.lang.SecurityException
     *     if a security manager exists and the caller does not have
     *     ManagementPermission("monitor").
     */
    public String getBootClassPath();

    /**
     * Returns the input arguments passed to the Java virtual machine
     * which does not include the arguments to the <tt>main</tt> method.
     * This method returns an empty list if there is no input argument
     * to the Java virtual machine.
     * <p>
     * Some Java virtual machine implementations may take input arguments
     * from multiple different sources: for examples, arguments passed from
     * the application that launches the Java virtual machine such as
     * the 'java' command, environment variables, configuration files, etc.
     * <p>
     * Typically, not all command-line options to the 'java' command
     * are passed to the Java virtual machine.
     * Thus, the returned input arguments may not
     * include all command-line options.
     *
     * <p>
     * <b>MBeanServer access</b>:<br>
     * The mapped type of {@code List<String>} is <tt>String[]</tt>.
     *
     * <p>
     *  返回传递给Java虚拟机的输入参数,该虚拟机不包括<tt> main </tt>方法的参数。如果Java虚拟机没有输入参数,此方法将返回一个空列表。
     * <p>
     *  一些Java虚拟机实现可能需要来自多个不同源的输入参数：例如,从启动Java虚拟机的应用程序(如"java"命令,环境变量,配置文件等)传递的参数。
     * <p>
     *  通常,并非"java"命令的所有命令行选项都传递到Java虚拟机。因此,返回的输入参数可能不包括所有命令行选项。
     * 
     * <p>
     * <b> MBeanServer访问</b>：<br> {@code List <String>}的映射类型为<tt> String [] </tt>。
     * 
     * 
     * @return a list of <tt>String</tt> objects; each element
     * is an argument passed to the Java virtual machine.
     *
     * @throws  java.lang.SecurityException
     *     if a security manager exists and the caller does not have
     *     ManagementPermission("monitor").
     */
    public java.util.List<String> getInputArguments();

    /**
     * Returns the uptime of the Java virtual machine in milliseconds.
     *
     * <p>
     *  返回Java虚拟机的正常运行时间(以毫秒为单位)。
     * 
     * 
     * @return uptime of the Java virtual machine in milliseconds.
     */
    public long getUptime();

    /**
     * Returns the start time of the Java virtual machine in milliseconds.
     * This method returns the approximate time when the Java virtual
     * machine started.
     *
     * <p>
     *  返回Java虚拟机的开始时间(以毫秒为单位)。此方法返回Java虚拟机启动时的大致时间。
     * 
     * 
     * @return start time of the Java virtual machine in milliseconds.
     *
     */
    public long getStartTime();

    /**
     * Returns a map of names and values of all system properties.
     * This method calls {@link System#getProperties} to get all
     * system properties.  Properties whose name or value is not
     * a <tt>String</tt> are omitted.
     *
     * <p>
     * <b>MBeanServer access</b>:<br>
     * The mapped type of {@code Map<String,String>} is
     * {@link javax.management.openmbean.TabularData TabularData}
     * with two items in each row as follows:
     * <blockquote>
     * <table border summary="Name and Type for each item">
     * <tr>
     *   <th>Item Name</th>
     *   <th>Item Type</th>
     *   </tr>
     * <tr>
     *   <td><tt>key</tt></td>
     *   <td><tt>String</tt></td>
     *   </tr>
     * <tr>
     *   <td><tt>value</tt></td>
     *   <td><tt>String</tt></td>
     *   </tr>
     * </table>
     * </blockquote>
     *
     * <p>
     *  返回所有系统属性的名称和值的映射。此方法调用{@link System#getProperties}以获取所有系统属性。省略其名称或值不是<tt> String </tt>的属性。
     * 
     * <p>
     *  <b> MBeanServer访问</b>：<br> {@code Map <String,String>}的映射类型是{@link javax.management.openmbean.TabularData TabularData}
     * ,每行中有两个项目,如下所示：。
     * <blockquote>
     * <table border summary="Name and Type for each item">
     * <tr>
     *  <th>项目名称</th> <th>项目类型</th>
     * </tr>
     * 
     * @return a map of names and values of all system properties.
     *
     * @throws  java.lang.SecurityException
     *     if a security manager exists and its
     *     <code>checkPropertiesAccess</code> method doesn't allow access
     *     to the system properties.
     */
    public java.util.Map<String, String> getSystemProperties();
}
