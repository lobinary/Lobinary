/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2008, Oracle and/or its affiliates. All rights reserved.
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
 * The management interface for the operating system on which
 * the Java virtual machine is running.
 *
 * <p> A Java virtual machine has a single instance of the implementation
 * class of this interface.  This instance implementing this interface is
 * an <a href="ManagementFactory.html#MXBean">MXBean</a>
 * that can be obtained by calling
 * the {@link ManagementFactory#getOperatingSystemMXBean} method or
 * from the {@link ManagementFactory#getPlatformMBeanServer
 * platform <tt>MBeanServer</tt>} method.
 *
 * <p>The <tt>ObjectName</tt> for uniquely identifying the MXBean for
 * the operating system within an MBeanServer is:
 * <blockquote>
 *    {@link ManagementFactory#OPERATING_SYSTEM_MXBEAN_NAME
 *      <tt>java.lang:type=OperatingSystem</tt>}
 * </blockquote>
 *
 * It can be obtained by calling the
 * {@link PlatformManagedObject#getObjectName} method.
 *
 * <p> This interface defines several convenient methods for accessing
 * system properties about the operating system on which the Java
 * virtual machine is running.
 *
 * <p>
 *  用于运行Java虚拟机的操作系统的管理接口。
 * 
 *  <p> Java虚拟机具有此接口的实现类的单个实例。
 * 实现此接口的实例是<a href="ManagementFactory.html#MXBean"> MXBean </a>,可以通过调用{@link ManagementFactory#getOperatingSystemMXBean}
 * 方法或从{@link ManagementFactory#getPlatformMBeanServer platform < tt> MBeanServer </tt>}方法。
 *  <p> Java虚拟机具有此接口的实现类的单个实例。
 * 
 *  <p>用于唯一标识MBeanServer中操作系统的MXBean的<tt> ObjectName </tt>是：
 * <blockquote>
 *  {@link ManagementFactory#OPERATING_SYSTEM_MXBEAN_NAME <tt> java.lang：type = OperatingSystem </tt>}
 * </blockquote>
 * 
 *  它可以通过调用{@link PlatformManagedObject#getObjectName}方法获得。
 * 
 *  <p>此接口定义了几个方便的方法来访问有关运行Java虚拟机的操作系统的系统属性。
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
public interface OperatingSystemMXBean extends PlatformManagedObject {
    /**
     * Returns the operating system name.
     * This method is equivalent to <tt>System.getProperty("os.name")</tt>.
     *
     * <p>
     *  返回操作系统名称。此方法等效于<tt> System.getProperty("os.name")</tt>。
     * 
     * 
     * @return the operating system name.
     *
     * @throws  java.lang.SecurityException
     *     if a security manager exists and its
     *     <code>checkPropertiesAccess</code> method doesn't allow access
     *     to this system property.
     * @see java.lang.SecurityManager#checkPropertyAccess(java.lang.String)
     * @see java.lang.System#getProperty
     */
    public String getName();

    /**
     * Returns the operating system architecture.
     * This method is equivalent to <tt>System.getProperty("os.arch")</tt>.
     *
     * <p>
     *  返回操作系统体系结构。此方法等效于<tt> System.getProperty("os.arch")</tt>。
     * 
     * 
     * @return the operating system architecture.
     *
     * @throws  java.lang.SecurityException
     *     if a security manager exists and its
     *     <code>checkPropertiesAccess</code> method doesn't allow access
     *     to this system property.
     * @see java.lang.SecurityManager#checkPropertyAccess(java.lang.String)
     * @see java.lang.System#getProperty
     */
    public String getArch();

    /**
     * Returns the operating system version.
     * This method is equivalent to <tt>System.getProperty("os.version")</tt>.
     *
     * <p>
     *  返回操作系统版本。此方法等效于<tt> System.getProperty("os.version")</tt>。
     * 
     * 
     * @return the operating system version.
     *
     * @throws  java.lang.SecurityException
     *     if a security manager exists and its
     *     <code>checkPropertiesAccess</code> method doesn't allow access
     *     to this system property.
     * @see java.lang.SecurityManager#checkPropertyAccess(java.lang.String)
     * @see java.lang.System#getProperty
     */
    public String getVersion();

    /**
     * Returns the number of processors available to the Java virtual machine.
     * This method is equivalent to the {@link Runtime#availableProcessors()}
     * method.
     * <p> This value may change during a particular invocation of
     * the virtual machine.
     *
     * <p>
     * 返回Java虚拟机可用的处理器数。此方法等效于{@link Runtime#availableProcessors()}方法。 <p>此值可能会在虚拟机的特定调用期间更改。
     * 
     * 
     * @return  the number of processors available to the virtual
     *          machine; never smaller than one.
     */
    public int getAvailableProcessors();

    /**
     * Returns the system load average for the last minute.
     * The system load average is the sum of the number of runnable entities
     * queued to the {@linkplain #getAvailableProcessors available processors}
     * and the number of runnable entities running on the available processors
     * averaged over a period of time.
     * The way in which the load average is calculated is operating system
     * specific but is typically a damped time-dependent average.
     * <p>
     * If the load average is not available, a negative value is returned.
     * <p>
     * This method is designed to provide a hint about the system load
     * and may be queried frequently.
     * The load average may be unavailable on some platform where it is
     * expensive to implement this method.
     *
     * <p>
     *  返回最后一分钟的系统负载平均值。
     * 系统负载平均值是排队到{@linkplain #getAvailableProcessors可用处理器}的可运行实体的数量和在一段时间内在可用处理器上运行的可运行实体的数量的总和。
     * 计算负载平均值的方式是操作系统特定的,但通常是阻尼的时间相关平均值。
     * <p>
     * 
     * @return the system load average; or a negative value if not available.
     *
     * @since 1.6
     */
    public double getSystemLoadAverage();
}
