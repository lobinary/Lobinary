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
 * The management interface for a memory manager.
 * A memory manager manages one or more memory pools of the
 * Java virtual machine.
 *
 * <p> A Java virtual machine has one or more memory managers.
 * An instance implementing this interface is
 * an <a href="ManagementFactory.html#MXBean">MXBean</a>
 * that can be obtained by calling
 * the {@link ManagementFactory#getMemoryManagerMXBeans} method or
 * from the {@link ManagementFactory#getPlatformMBeanServer
 * platform <tt>MBeanServer</tt>} method.
 *
 * <p>The <tt>ObjectName</tt> for uniquely identifying the MXBean for
 * a memory manager within an MBeanServer is:
 * <blockquote>
 *   {@link ManagementFactory#MEMORY_MANAGER_MXBEAN_DOMAIN_TYPE
 *    <tt>java.lang:type=MemoryManager</tt>}<tt>,name=</tt><i>manager's name</i>
 * </blockquote>
 *
 * It can be obtained by calling the
 * {@link PlatformManagedObject#getObjectName} method.
 *
 * <p>
 *  内存管理器的管理接口。存储器管理器管理Java虚拟机的一个或多个存储器池。
 * 
 *  <p> Java虚拟机具有一个或多个内存管理器。
 * 实现此接口的实例是<a href="ManagementFactory.html#MXBean"> MXBean </a>,可以通过调用{@link ManagementFactory#getMemoryManagerMXBeans}
 * 方法或从{@link ManagementFactory#getPlatformMBeanServer platform < tt> MBeanServer </tt>}方法。
 *  <p> Java虚拟机具有一个或多个内存管理器。
 * 
 *  <p>用于唯一标识MBeanServer内存管理器的MXBean的<tt> ObjectName </tt>是：
 * <blockquote>
 *  {@link ManagementFactory#MEMORY_MANAGER_MXBEAN_DOMAIN_TYPE <tt> java.lang：type = MemoryManager </tt>}
 *  <tt>,name = </tt> <i>经理姓名</i>。
 * </blockquote>
 * 
 * @see ManagementFactory#getPlatformMXBeans(Class)
 * @see MemoryMXBean
 *
 * @see <a href="../../../javax/management/package-summary.html">
 *      JMX Specification.</a>
 * @see <a href="package-summary.html#examples">
 *      Ways to Access MXBeans</a>
 *
 * @author  Mandy Chung
 * @since   1.5
 */
public interface MemoryManagerMXBean extends PlatformManagedObject {
    /**
     * Returns the name representing this memory manager.
     *
     * <p>
     * 
     *  它可以通过调用{@link PlatformManagedObject#getObjectName}方法获得。
     * 
     * 
     * @return the name of this memory manager.
     */
    public String getName();

    /**
     * Tests if this memory manager is valid in the Java virtual
     * machine.  A memory manager becomes invalid once the Java virtual
     * machine removes it from the memory system.
     *
     * <p>
     *  返回表示此内存管理器的名称。
     * 
     * 
     * @return <tt>true</tt> if the memory manager is valid in the
     *               Java virtual machine;
     *         <tt>false</tt> otherwise.
     */
    public boolean isValid();

    /**
     * Returns the name of memory pools that this memory manager manages.
     *
     * <p>
     *  测试此内存管理器在Java虚拟机中是否有效。一旦Java虚拟机将其从内存系统中删除,内存管理器就会失效。
     * 
     * 
     * @return an array of <tt>String</tt> objects, each is
     * the name of a memory pool that this memory manager manages.
     */
    public String[] getMemoryPoolNames();
}
