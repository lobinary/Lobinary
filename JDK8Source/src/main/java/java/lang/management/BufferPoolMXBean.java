/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2011, Oracle and/or its affiliates. All rights reserved.
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
 * The management interface for a buffer pool, for example a pool of
 * {@link java.nio.ByteBuffer#allocateDirect direct} or {@link
 * java.nio.MappedByteBuffer mapped} buffers.
 *
 * <p> A class implementing this interface is an
 * {@link javax.management.MXBean}. A Java
 * virtual machine has one or more implementations of this interface. The {@link
 * java.lang.management.ManagementFactory#getPlatformMXBeans getPlatformMXBeans}
 * method can be used to obtain the list of {@code BufferPoolMXBean} objects
 * representing the management interfaces for pools of buffers as follows:
 * <pre>
 *     List&lt;BufferPoolMXBean&gt; pools = ManagementFactory.getPlatformMXBeans(BufferPoolMXBean.class);
 * </pre>
 *
 * <p> The management interfaces are also registered with the platform {@link
 * javax.management.MBeanServer MBeanServer}. The {@link
 * javax.management.ObjectName ObjectName} that uniquely identifies the
 * management interface within the {@code MBeanServer} takes the form:
 * <pre>
 *     java.nio:type=BufferPool,name=<i>pool name</i>
 * </pre>
 * where <em>pool name</em> is the {@link #getName name} of the buffer pool.
 *
 * <p>
 *  缓冲池的管理接口,例如{@link java.nio.ByteBuffer#allocateDirect direct}或{@link java.nio.MappedByteBuffer mapped}
 * 缓冲区的池。
 * 
 *  <p>实现此接口的类是{@link javax.management.MXBean}。 Java虚拟机具有此接口的一个或多个实现。
 *  {@link java.lang.management.ManagementFactory#getPlatformMXBeans getPlatformMXBeans}方法可用于获取表示缓冲池池的管理
 * 接口的{@code BufferPoolMXBean}对象列表,如下所示：。
 *  <p>实现此接口的类是{@link javax.management.MXBean}。 Java虚拟机具有此接口的一个或多个实现。
 * <pre>
 *  List&lt; BufferPoolMXBean&gt; pools = ManagementFactory.getPlatformMXBeans(BufferPoolMXBean.class);。
 * </pre>
 * 
 *  <p>管理接口也在平台{@link javax.management.MBeanServer MBeanServer}中注册。
 * 唯一标识{@code MBeanServer}中的管理接口的{@link javax.management.ObjectName ObjectName}采用以下形式：。
 * <pre>
 *  java.nio：type = BufferPool,name = <i> pool name </i>
 * </pre>
 * 
 * @since   1.7
 */
public interface BufferPoolMXBean extends PlatformManagedObject {

    /**
     * Returns the name representing this buffer pool.
     *
     * <p>
     *  其中<em>池名称</em>是缓冲池的{@link #getName name}。
     * 
     * 
     * @return  The name of this buffer pool.
     */
    String getName();

    /**
     * Returns an estimate of the number of buffers in the pool.
     *
     * <p>
     *  返回表示此缓冲池的名称。
     * 
     * 
     * @return  An estimate of the number of buffers in this pool
     */
    long getCount();

    /**
     * Returns an estimate of the total capacity of the buffers in this pool.
     * A buffer's capacity is the number of elements it contains and the value
     * returned by this method is an estimate of the total capacity of buffers
     * in the pool in bytes.
     *
     * <p>
     *  返回池中缓冲区数的估计值。
     * 
     * 
     * @return  An estimate of the total capacity of the buffers in this pool
     *          in bytes
     */
    long getTotalCapacity();

    /**
     * Returns an estimate of the memory that the Java virtual machine is using
     * for this buffer pool. The value returned by this method may differ
     * from the estimate of the total {@link #getTotalCapacity capacity} of
     * the buffers in this pool. This difference is explained by alignment,
     * memory allocator, and other implementation specific reasons.
     *
     * <p>
     * 返回此池中缓冲区的总容量的估计值。缓冲区的容量是其包含的元素的数量,并且此方法返回的值是以字节为单位的池中缓冲区的总容量的估计值。
     * 
     * 
     * @return  An estimate of the memory that the Java virtual machine is using
     *          for this buffer pool in bytes, or {@code -1L} if an estimate of
     *          the memory usage is not available
     */
    long getMemoryUsed();
}
