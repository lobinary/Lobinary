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
 * The management interface for the garbage collection of
 * the Java virtual machine.  Garbage collection is the process
 * that the Java virtual machine uses to find and reclaim unreachable
 * objects to free up memory space.  A garbage collector is one type of
 * {@link MemoryManagerMXBean memory manager}.
 *
 * <p> A Java virtual machine may have one or more instances of
 * the implementation class of this interface.
 * An instance implementing this interface is
 * an <a href="ManagementFactory.html#MXBean">MXBean</a>
 * that can be obtained by calling
 * the {@link ManagementFactory#getGarbageCollectorMXBeans} method or
 * from the {@link ManagementFactory#getPlatformMBeanServer
 * platform <tt>MBeanServer</tt>} method.
 *
 * <p>The <tt>ObjectName</tt> for uniquely identifying the MXBean for
 * a garbage collector within an MBeanServer is:
 * <blockquote>
 *   {@link ManagementFactory#GARBAGE_COLLECTOR_MXBEAN_DOMAIN_TYPE
 *    <tt>java.lang:type=GarbageCollector</tt>}<tt>,name=</tt><i>collector's name</i>
 * </blockquote>
 *
 * It can be obtained by calling the
 * {@link PlatformManagedObject#getObjectName} method.
 *
 * A platform usually includes additional platform-dependent information
 * specific to a garbage collection algorithm for monitoring.
 *
 * <p>
 *  用于Java虚拟机的垃圾回收的管理接口。垃圾收集是Java虚拟机用于查找和回收无法访问的对象以释放内存空间的过程。
 * 垃圾收集器是{@link MemoryManagerMXBean内存管理器}的一种类型。
 * 
 *  <p> Java虚拟机可能具有此接口的实现类的一个或多个实例。
 * 实现此接口的实例是<a href="ManagementFactory.html#MXBean"> MXBean </a>,可以通过调用{@link ManagementFactory#getGarbageCollectorMXBeans}
 * 方法或从{@link ManagementFactory#getPlatformMBeanServer platform < tt> MBeanServer </tt>}方法。
 *  <p> Java虚拟机可能具有此接口的实现类的一个或多个实例。
 * 
 *  <p>用于唯一标识MBeanServer中的垃圾回收器的MXBean的<tt> ObjectName </tt>是：
 * <blockquote>
 *  {@link ManagementFactory#GARBAGE_COLLECTOR_MXBEAN_DOMAIN_TYPE <tt> java.lang：type = GarbageCollector </tt>}
 *  <tt>,name = </tt> <i>。
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
public interface GarbageCollectorMXBean extends MemoryManagerMXBean {
    /**
     * Returns the total number of collections that have occurred.
     * This method returns <tt>-1</tt> if the collection count is undefined for
     * this collector.
     *
     * <p>
     * 
     *  它可以通过调用{@link PlatformManagedObject#getObjectName}方法获得。
     * 
     *  平台通常包括特定于用于监视的垃圾收集算法的附加的平台相关信息。
     * 
     * 
     * @return the total number of collections that have occurred.
     */
    public long getCollectionCount();

    /**
     * Returns the approximate accumulated collection elapsed time
     * in milliseconds.  This method returns <tt>-1</tt> if the collection
     * elapsed time is undefined for this collector.
     * <p>
     * The Java virtual machine implementation may use a high resolution
     * timer to measure the elapsed time.  This method may return the
     * same value even if the collection count has been incremented
     * if the collection elapsed time is very short.
     *
     * <p>
     *  返回已发生的集合的总数。如果此收集器的收集计数未定义,此方法返回<tt> -1 </tt>。
     * 
     * 
     * @return the approximate accumulated collection elapsed time
     * in milliseconds.
     */
    public long getCollectionTime();


}
