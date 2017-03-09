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

import javax.management.openmbean.CompositeData;

/**
 * The management interface for the memory system of
 * the Java virtual machine.
 *
 * <p> A Java virtual machine has a single instance of the implementation
 * class of this interface.  This instance implementing this interface is
 * an <a href="ManagementFactory.html#MXBean">MXBean</a>
 * that can be obtained by calling
 * the {@link ManagementFactory#getMemoryMXBean} method or
 * from the {@link ManagementFactory#getPlatformMBeanServer
 * platform <tt>MBeanServer</tt>} method.
 *
 * <p>The <tt>ObjectName</tt> for uniquely identifying the MXBean for
 * the memory system within an MBeanServer is:
 * <blockquote>
 *    {@link ManagementFactory#MEMORY_MXBEAN_NAME
 *           <tt>java.lang:type=Memory</tt>}
 * </blockquote>
 *
 * It can be obtained by calling the
 * {@link PlatformManagedObject#getObjectName} method.
 *
 * <h3> Memory </h3>
 * The memory system of the Java virtual machine manages
 * the following kinds of memory:
 *
 * <h3> 1. Heap </h3>
 * The Java virtual machine has a <i>heap</i> that is the runtime
 * data area from which memory for all class instances and arrays
 * are allocated.  It is created at the Java virtual machine start-up.
 * Heap memory for objects is reclaimed by an automatic memory management
 * system which is known as a <i>garbage collector</i>.
 *
 * <p>The heap may be of a fixed size or may be expanded and shrunk.
 * The memory for the heap does not need to be contiguous.
 *
 * <h3> 2. Non-Heap Memory</h3>
 * The Java virtual machine manages memory other than the heap
 * (referred as <i>non-heap memory</i>).
 *
 * <p> The Java virtual machine has a <i>method area</i> that is shared
 * among all threads.
 * The method area belongs to non-heap memory.  It stores per-class structures
 * such as a runtime constant pool, field and method data, and the code for
 * methods and constructors.  It is created at the Java virtual machine
 * start-up.
 *
 * <p> The method area is logically part of the heap but a Java virtual
 * machine implementation may choose not to either garbage collect
 * or compact it.  Similar to the heap, the method area may be of a
 * fixed size or may be expanded and shrunk.  The memory for the
 * method area does not need to be contiguous.
 *
 * <p>In addition to the method area, a Java virtual machine
 * implementation may require memory for internal processing or
 * optimization which also belongs to non-heap memory.
 * For example, the JIT compiler requires memory for storing the native
 * machine code translated from the Java virtual machine code for
 * high performance.
 *
 * <h3>Memory Pools and Memory Managers</h3>
 * {@link MemoryPoolMXBean Memory pools} and
 * {@link MemoryManagerMXBean memory managers} are the abstract entities
 * that monitor and manage the memory system
 * of the Java virtual machine.
 *
 * <p>A memory pool represents a memory area that the Java virtual machine
 * manages.  The Java virtual machine has at least one memory pool
 * and it may create or remove memory pools during execution.
 * A memory pool can belong to either the heap or the non-heap memory.
 *
 * <p>A memory manager is responsible for managing one or more memory pools.
 * The garbage collector is one type of memory manager responsible
 * for reclaiming memory occupied by unreachable objects.  A Java virtual
 * machine may have one or more memory managers.   It may
 * add or remove memory managers during execution.
 * A memory pool can be managed by more than one memory manager.
 *
 * <h3>Memory Usage Monitoring</h3>
 *
 * Memory usage is a very important monitoring attribute for the memory system.
 * The memory usage, for example, could indicate:
 * <ul>
 *   <li>the memory usage of an application,</li>
 *   <li>the workload being imposed on the automatic memory management system,</li>
 *   <li>potential memory leakage.</li>
 * </ul>
 *
 * <p>
 * The memory usage can be monitored in three ways:
 * <ul>
 *   <li>Polling</li>
 *   <li>Usage Threshold Notification</li>
 *   <li>Collection Usage Threshold Notification</li>
 * </ul>
 *
 * Details are specified in the {@link MemoryPoolMXBean} interface.
 *
 * <p>The memory usage monitoring mechanism is intended for load-balancing
 * or workload distribution use.  For example, an application would stop
 * receiving any new workload when its memory usage exceeds a
 * certain threshold. It is not intended for an application to detect
 * and recover from a low memory condition.
 *
 * <h3>Notifications</h3>
 *
 * <p>This <tt>MemoryMXBean</tt> is a
 * {@link javax.management.NotificationEmitter NotificationEmitter}
 * that emits two types of memory {@link javax.management.Notification
 * notifications} if any one of the memory pools
 * supports a <a href="MemoryPoolMXBean.html#UsageThreshold">usage threshold</a>
 * or a <a href="MemoryPoolMXBean.html#CollectionThreshold">collection usage
 * threshold</a> which can be determined by calling the
 * {@link MemoryPoolMXBean#isUsageThresholdSupported} and
 * {@link MemoryPoolMXBean#isCollectionUsageThresholdSupported} methods.
 * <ul>
 *   <li>{@link MemoryNotificationInfo#MEMORY_THRESHOLD_EXCEEDED
 *       usage threshold exceeded notification} - for notifying that
 *       the memory usage of a memory pool is increased and has reached
 *       or exceeded its
 *       <a href="MemoryPoolMXBean.html#UsageThreshold"> usage threshold</a> value.
 *       </li>
 *   <li>{@link MemoryNotificationInfo#MEMORY_COLLECTION_THRESHOLD_EXCEEDED
 *       collection usage threshold exceeded notification} - for notifying that
 *       the memory usage of a memory pool is greater than or equal to its
 *       <a href="MemoryPoolMXBean.html#CollectionThreshold">
 *       collection usage threshold</a> after the Java virtual machine
 *       has expended effort in recycling unused objects in that
 *       memory pool.</li>
 * </ul>
 *
 * <p>
 * The notification emitted is a {@link javax.management.Notification}
 * instance whose {@link javax.management.Notification#setUserData
 * user data} is set to a {@link CompositeData CompositeData}
 * that represents a {@link MemoryNotificationInfo} object
 * containing information about the memory pool when the notification
 * was constructed. The <tt>CompositeData</tt> contains the attributes
 * as described in {@link MemoryNotificationInfo#from
 * MemoryNotificationInfo}.
 *
 * <hr>
 * <h3>NotificationEmitter</h3>
 * The <tt>MemoryMXBean</tt> object returned by
 * {@link ManagementFactory#getMemoryMXBean} implements
 * the {@link javax.management.NotificationEmitter NotificationEmitter}
 * interface that allows a listener to be registered within the
 * <tt>MemoryMXBean</tt> as a notification listener.
 *
 * Below is an example code that registers a <tt>MyListener</tt> to handle
 * notification emitted by the <tt>MemoryMXBean</tt>.
 *
 * <blockquote><pre>
 * class MyListener implements javax.management.NotificationListener {
 *     public void handleNotification(Notification notif, Object handback) {
 *         // handle notification
 *         ....
 *     }
 * }
 *
 * MemoryMXBean mbean = ManagementFactory.getMemoryMXBean();
 * NotificationEmitter emitter = (NotificationEmitter) mbean;
 * MyListener listener = new MyListener();
 * emitter.addNotificationListener(listener, null, null);
 * </pre></blockquote>
 *
 * <p>
 *  Java虚拟机的内存系统的管理接口。
 * 
 *  <p> Java虚拟机具有此接口的实现类的单个实例。
 * 实现此接口的实例是<a href="ManagementFactory.html#MXBean"> MXBean </a>,可通过调用{@link ManagementFactory#getMemoryMXBean}
 * 方法或从{@link ManagementFactory#getPlatformMBeanServer platform < tt> MBeanServer </tt>}方法。
 *  <p> Java虚拟机具有此接口的实现类的单个实例。
 * 
 *  <p>用于唯一标识MBeanServer内存系统的MXBean的<tt> ObjectName </tt>是：
 * <blockquote>
 *  {@link ManagementFactory#MEMORY_MXBEAN_NAME <tt> java.lang：type = Memory </tt>}
 * </blockquote>
 * 
 *  它可以通过调用{@link PlatformManagedObject#getObjectName}方法获得。
 * 
 *  <h3>内存</h3> Java虚拟机的内存系统管理以下类型的内存：
 * 
 *  <h3> 1.堆</h3> Java虚拟机有一个<i>堆</i>,它是分配所有类实例和数组的内存的运行时数据区。它是在Java虚拟机启动时创建的。
 * 对象的堆内存由被称为"垃圾收集器"的自动内存管理系统回收。
 * 
 *  <p>堆可以是固定大小,也可以是展开和缩小。堆的内存不需要是连续的。
 * 
 * <h3> 2.非堆内存</h3> Java虚拟机管理除堆以外的内存(称为<i>非堆内存</i>)。
 * 
 *  <p> Java虚拟机具有在所有线程之间共享的<i>方法区域</i>。方法区域属于非堆内存。它存储每类结构,例如运行时常量池,字段和方法数据,以及方法和构造函数的代码。
 * 它是在Java虚拟机启动时创建的。
 * 
 *  <p>方法区域在逻辑上是堆的一部分,但是Java虚拟机实现可能选择不进行垃圾回收或压缩它。类似于堆,方法区域可以是固定大小或可以被扩展和收缩。方法区域的内存不需要是连续的。
 * 
 *  <p>除了方法区域,Java虚拟机实现可能需要内存用于内部处理或优化,这也属于非堆内存。例如,JIT编译器需要内存来存储从Java虚拟机代码翻译的本地机器代码,以实现高性能。
 * 
 *  <h3>内存池和内存管理器</h3> {@link MemoryPoolMXBean内存池}和{@link MemoryManagerMXBean内存管理器}是监视和管理Java虚拟机内存系统的抽象实
 * 体。
 * 
 * <p>内存池表示Java虚拟机管理的内存区域。 Java虚拟机至少有一个内存池,它可以在执行过程中创建或删除内存池。内存池可以属于堆或非堆内存。
 * 
 *  <p>内存管理器负责管理一个或多个内存池。垃圾回收器是一种类型的内存管理器,负责回收由不可达对象占用的内存。 Java虚拟机可以具有一个或多个存储器管理器。它可以在执行期间添加或删除内存管理器。
 * 内存池可以由多个内存管理器管理。
 * 
 *  <h3>内存使用监控</h3>
 * 
 *  内存使用是内存系统的一个非常重要的监视属性。例如,内存使用可以指示：
 * <ul>
 *  <li>应用程式的记忆体使用量,</li> <li>自动内存管理系统上的工作量,</li> <li>潜在的内存泄漏。</li>
 * </ul>
 * 
 * <p>
 *  可以通过三种方式监视内存使用情况：
 * <ul>
 *  <li>轮询</li> <li>使用阈值通知</li> <li>集合使用阈值通知</li>
 * </ul>
 * 
 *  详细信息在{@link MemoryPoolMXBean}接口中指定。
 * 
 *  <p>内存使用情况监控机制用于负载平衡或工作负载分配使用。例如,当应用程序的内存使用超过某个阈值时,应用程序将停止接收任何新的工作负载。它不适用于应用程序检测和从低内存条件恢复。
 * 
 * <h3>通知</h3>
 * 
 *  <p>此<tt> MemoryMXBean </tt>是一个{@link javax.management.NotificationEmitter NotificationEmitter},它发出两种
 * 类型的内存{@link javax.management.Notification通知},如果任何一个内存池支持< a href ="MemoryPoolMXBean.html#UsageThreshold">
 * 使用阈值</a>或<a href="MemoryPoolMXBean.html#CollectionThreshold">集合使用阈值</a>,可以通过调用{@link MemoryPoolMXBean# isUsageThresholdSupported}
 * 和{@link MemoryPoolMXBean#isCollectionUsageThresholdSupported}方法。
 * <ul>
 *  <li> {@ link MemoryNotificationInfo#MEMORY_THRESHOLD_EXCEEDED超过使用情况阈值通知}  - 用于通知内存池的内存使用率已增加,并已达到或超过
 * 其<a href="MemoryPoolMXBean.html#UsageThreshold">使用阈值</a >值。
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
public interface MemoryMXBean extends PlatformManagedObject {
    /**
     * Returns the approximate number of objects for which
     * finalization is pending.
     *
     * <p>
     * </li>
     *  <li> {@ link MemoryNotificationInfo#MEMORY_COLLECTION_THRESHOLD_EXCEEDED集合使用情况阈值已超过通知}  - 用于通知内存池的内存
     * 使用量大于或等于其。
     * <a href="MemoryPoolMXBean.html#CollectionThreshold">
     *  收集使用阈值</a>,之后Java虚拟机已耗尽了对该内存池中未使用的对象的回收。</li>
     * </ul>
     * 
     * <p>
     * 发出的通知是{@link javax.management.Notification}实例,其{@link javax.management.Notification#setUserData用户数据}设
     * 置为{@link CompositeData CompositeData},表示包含信息的{@link MemoryNotificationInfo}对象关于构建通知时的内存池。
     *  <tt> CompositeData </tt>包含{@link MemoryNotificationInfo#from MemoryNotificationInfo}中描述的属性。
     * 
     * <hr>
     *  <h3> NotificationEmitter </h3> {@link ManagementFactory#getMemoryMXBean}返回的<tt> MemoryMXBean </tt>对象
     * 实现{@link javax.management.NotificationEmitter NotificationEmitter}接口,允许侦听器在< tt> MemoryMXBean </tt>作为
     * 通知侦听器。
     * 
     *  以下是注册<tt> MyListener </tt>以处理由<tt> MemoryMXBean </tt>发出的通知的示例代码。
     * 
     *  <blockquote> <pre> class MyListener implements javax.management.NotificationListener {public void handleNotification(Notification notif,Object handback){// handle notification ....}
     * }。
     * 
     *  MemoryMXBean mbean = ManagementFactory.getMemoryMXBean(); NotificationEmitter emitter =(Notification
     * Emitter)mbean; MyListener listener = new MyListener(); emitter.addNotificationListener(listener,null,
     * null); </pre> </blockquote>。
     * 
     * 
     * @return the approximate number objects for which finalization
     * is pending.
     */
    public int getObjectPendingFinalizationCount();

    /**
     * Returns the current memory usage of the heap that
     * is used for object allocation.  The heap consists
     * of one or more memory pools.  The <tt>used</tt>
     * and <tt>committed</tt> size of the returned memory
     * usage is the sum of those values of all heap memory pools
     * whereas the <tt>init</tt> and <tt>max</tt> size of the
     * returned memory usage represents the setting of the heap
     * memory which may not be the sum of those of all heap
     * memory pools.
     * <p>
     * The amount of used memory in the returned memory usage
     * is the amount of memory occupied by both live objects
     * and garbage objects that have not been collected, if any.
     *
     * <p>
     * <b>MBeanServer access</b>:<br>
     * The mapped type of <tt>MemoryUsage</tt> is
     * <tt>CompositeData</tt> with attributes as specified in
     * {@link MemoryUsage#from MemoryUsage}.
     *
     * <p>
     *  返回最终化待处理的对象的近似数量。
     * 
     * 
     * @return a {@link MemoryUsage} object representing
     * the heap memory usage.
     */
    public MemoryUsage getHeapMemoryUsage();

    /**
     * Returns the current memory usage of non-heap memory that
     * is used by the Java virtual machine.
     * The non-heap memory consists of one or more memory pools.
     * The <tt>used</tt> and <tt>committed</tt> size of the
     * returned memory usage is the sum of those values of
     * all non-heap memory pools whereas the <tt>init</tt>
     * and <tt>max</tt> size of the returned memory usage
     * represents the setting of the non-heap
     * memory which may not be the sum of those of all non-heap
     * memory pools.
     *
     * <p>
     * <b>MBeanServer access</b>:<br>
     * The mapped type of <tt>MemoryUsage</tt> is
     * <tt>CompositeData</tt> with attributes as specified in
     * {@link MemoryUsage#from MemoryUsage}.
     *
     * <p>
     * 返回用于对象分配的堆的当前内存使用情况。堆由一个或多个内存池组成。
     * 所返回的内存使用量的<tt> used </tt>和<tt> committed </tt>大小是所有堆内存池的值之和,而<tt> init </tt>和<tt> max </tt>返回的内存使用量的大
     * 小表示堆内存的设置,可能不是所有堆内存池的总和。
     * 返回用于对象分配的堆的当前内存使用情况。堆由一个或多个内存池组成。
     * <p>
     *  在返回的内存使用中使用的内存量是活动对象和未收集的垃圾对象(如果有的话)占用的内存量。
     * 
     * <p>
     *  <b> MBeanServer访问</b>：<br> <tt> MemoryUsage </tt>的映射类型是具有{@link MemoryUsage#from MemoryUsage}中指定的属性的
     * <tt> CompositeData </tt>。
     * 
     * 
     * @return a {@link MemoryUsage} object representing
     * the non-heap memory usage.
     */
    public MemoryUsage getNonHeapMemoryUsage();

    /**
     * Tests if verbose output for the memory system is enabled.
     *
     * <p>
     *  返回Java虚拟机使用的非堆内存的当前内存使用情况。非堆内存由一个或多个内存池组成。
     * 所返回的内存使用量的<tt> used </tt>和<tt> committed </tt>大小是所有非堆内存池的值之和,而<tt> init </tt>和<tt > max </tt>返回的内存使用量
     * 的大小表示非堆内存的设置,这可能不是所有非堆内存池的总和。
     *  返回Java虚拟机使用的非堆内存的当前内存使用情况。非堆内存由一个或多个内存池组成。
     * 
     * <p>
     *  <b> MBeanServer访问</b>：<br> <tt> MemoryUsage </tt>的映射类型是<tt> CompositeData </tt>,具有{@link MemoryUsage#from MemoryUsage}
     * 中指定的属性。
     * 
     * 
     * @return <tt>true</tt> if verbose output for the memory
     * system is enabled; <tt>false</tt> otherwise.
     */
    public boolean isVerbose();

    /**
     * Enables or disables verbose output for the memory
     * system.  The verbose output information and the output stream
     * to which the verbose information is emitted are implementation
     * dependent.  Typically, a Java virtual machine implementation
     * prints a message whenever it frees memory at garbage collection.
     *
     * <p>
     * Each invocation of this method enables or disables verbose
     * output globally.
     *
     * <p>
     *  测试是否启用了内存系统的详细输出。
     * 
     * 
     * @param value <tt>true</tt> to enable verbose output;
     *              <tt>false</tt> to disable.
     *
     * @exception  java.lang.SecurityException if a security manager
     *             exists and the caller does not have
     *             ManagementPermission("control").
     */
    public void setVerbose(boolean value);

    /**
     * Runs the garbage collector.
     * The call <code>gc()</code> is effectively equivalent to the
     * call:
     * <blockquote><pre>
     * System.gc()
     * </pre></blockquote>
     *
     * <p>
     * 启用或禁用内存系统的详细输出。详细输出信息和发送详细信息的输出流是实现相关的。通常,Java虚拟机实现在垃圾收集时释放内存时打印一条消息。
     * 
     * <p>
     *  每次调用此方法都会全局启用或禁用详细输出。
     * 
     * @see     java.lang.System#gc()
     */
    public void gc();

}
