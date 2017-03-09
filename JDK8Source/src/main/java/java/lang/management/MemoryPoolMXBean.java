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
 * The management interface for a memory pool.  A memory pool
 * represents the memory resource managed by the Java virtual machine
 * and is managed by one or more {@link MemoryManagerMXBean memory managers}.
 *
 * <p> A Java virtual machine has one or more instances of the
 * implementation class of this interface.  An instance
 * implementing this interface is
 * an <a href="ManagementFactory.html#MXBean">MXBean</a>
 * that can be obtained by calling
 * the {@link ManagementFactory#getMemoryPoolMXBeans} method or
 * from the {@link ManagementFactory#getPlatformMBeanServer
 * platform <tt>MBeanServer</tt>} method.
 *
 * <p>The <tt>ObjectName</tt> for uniquely identifying the MXBean for
 * a memory pool within an <tt>MBeanServer</tt> is:
 * <blockquote>
 *    {@link ManagementFactory#MEMORY_POOL_MXBEAN_DOMAIN_TYPE
 *    <tt>java.lang:type=MemoryPool</tt>}<tt>,name=</tt><i>pool's name</i>
 * </blockquote>
 *
 * It can be obtained by calling the
 * {@link PlatformManagedObject#getObjectName} method.
 *
 * <h3>Memory Type</h3>
 * <p>The Java virtual machine has a heap for object allocation and also
 * maintains non-heap memory for the method area and the Java virtual
 * machine execution.  The Java virtual machine can have one or more
 * memory pools.  Each memory pool represents a memory area
 * of one of the following types:
 * <ul>
 *   <li>{@link MemoryType#HEAP heap}</li>
 *   <li>{@link MemoryType#NON_HEAP non-heap}</li>
 * </ul>
 *
 * <h3>Memory Usage Monitoring</h3>
 *
 * A memory pool has the following attributes:
 * <ul>
 *   <li><a href="#Usage">Memory usage</a></li>
 *   <li><a href="#PeakUsage">Peak memory usage</a></li>
 *   <li><a href="#UsageThreshold">Usage Threshold</a></li>
 *   <li><a href="#CollectionThreshold">Collection Usage Threshold</a>
 *       (only supported by some <em>garbage-collected</em> memory pools)</li>
 * </ul>
 *
 * <h3><a name="Usage">1. Memory Usage</a></h3>
 *
 * The {@link #getUsage} method provides an estimate
 * of the current usage of a memory pool.
 * For a garbage-collected memory pool, the amount of used memory
 * includes the memory occupied by all objects in the pool
 * including both <em>reachable</em> and <em>unreachable</em> objects.
 *
 * <p>In general, this method is a lightweight operation for getting
 * an approximate memory usage.  For some memory pools, for example,
 * when objects are not packed contiguously, this method may be
 * an expensive operation that requires some computation to determine
 * the current memory usage.  An implementation should document when
 * this is the case.
 *
 * <h3><a name="PeakUsage">2. Peak Memory Usage</a></h3>
 *
 * The Java virtual machine maintains the peak memory usage of a memory
 * pool since the virtual machine was started or the peak was reset.
 * The peak memory usage is returned by the {@link #getPeakUsage} method
 * and reset by calling the {@link #resetPeakUsage} method.
 *
 * <h3><a name="UsageThreshold">3. Usage Threshold</a></h3>
 *
 * Each memory pool has a manageable attribute
 * called the <i>usage threshold</i> which has a default value supplied
 * by the Java virtual machine.  The default value is platform-dependent.
 * The usage threshold can be set via the
 * {@link #setUsageThreshold setUsageThreshold} method.
 * If the threshold is set to a positive value, the usage threshold crossing
 * checking is enabled in this memory pool.
 * If the usage threshold is set to zero, usage
 * threshold crossing checking on this memory pool is disabled.
 * The {@link MemoryPoolMXBean#isUsageThresholdSupported} method can
 * be used to determine if this functionality is supported.
 * <p>
 * A Java virtual machine performs usage threshold crossing checking on a
 * memory pool basis at its best appropriate time, typically,
 * at garbage collection time.
 * Each memory pool maintains a {@link #getUsageThresholdCount
 * usage threshold count} that will get incremented
 * every time when the Java virtual machine
 * detects that the memory pool usage is crossing the threshold.
 * <p>
 * This manageable usage threshold attribute is designed for monitoring the
 * increasing trend of memory usage with low overhead.
 * Usage threshold may not be appropriate for some memory pools.
 * For example, a generational garbage collector, a common garbage collection
 * algorithm used in many Java virtual machine implementations,
 * manages two or more generations segregating objects by age.
 * Most of the objects are allocated in
 * the <em>youngest generation</em> (say a nursery memory pool).
 * The nursery memory pool is designed to be filled up and
 * collecting the nursery memory pool will free most of its memory space
 * since it is expected to contain mostly short-lived objects
 * and mostly are unreachable at garbage collection time.
 * In this case, it is more appropriate for the nursery memory pool
 * not to support a usage threshold.  In addition,
 * if the cost of an object allocation
 * in one memory pool is very low (for example, just atomic pointer exchange),
 * the Java virtual machine would probably not support the usage threshold
 * for that memory pool since the overhead in comparing the usage with
 * the threshold is higher than the cost of object allocation.
 *
 * <p>
 * The memory usage of the system can be monitored using
 * <a href="#Polling">polling</a> or
 * <a href="#ThresholdNotification">threshold notification</a> mechanisms.
 *
 * <ol type="a">
 *   <li><a name="Polling"><b>Polling</b></a>
 *       <p>
 *       An application can continuously monitor its memory usage
 *       by calling either the {@link #getUsage} method for all
 *       memory pools or the {@link #isUsageThresholdExceeded} method
 *       for those memory pools that support a usage threshold.
 *       Below is example code that has a thread dedicated for
 *       task distribution and processing.  At every interval,
 *       it will determine if it should receive and process new tasks based
 *       on its memory usage.  If the memory usage exceeds its usage threshold,
 *       it will redistribute all outstanding tasks to other VMs and
 *       stop receiving new tasks until the memory usage returns
 *       below its usage threshold.
 *
 *       <pre>
 *       // Assume the usage threshold is supported for this pool.
 *       // Set the threshold to myThreshold above which no new tasks
 *       // should be taken.
 *       pool.setUsageThreshold(myThreshold);
 *       ....
 *
 *       boolean lowMemory = false;
 *       while (true) {
 *          if (pool.isUsageThresholdExceeded()) {
 *              // potential low memory, so redistribute tasks to other VMs
 *              lowMemory = true;
 *              redistributeTasks();
 *              // stop receiving new tasks
 *              stopReceivingTasks();
 *          } else {
 *              if (lowMemory) {
 *                  // resume receiving tasks
 *                  lowMemory = false;
 *                  resumeReceivingTasks();
 *              }
 *              // processing outstanding task
 *              ...
 *          }
 *          // sleep for sometime
 *          try {
 *              Thread.sleep(sometime);
 *          } catch (InterruptedException e) {
 *              ...
 *          }
 *       }
 *       </pre>
 *
 * <hr>
 *       The above example does not differentiate the case where
 *       the memory usage has temporarily dropped below the usage threshold
 *       from the case where the memory usage remains above the threshold
 *       between two iterations.  The usage threshold count returned by
 *       the {@link #getUsageThresholdCount} method
 *       can be used to determine
 *       if the memory usage has returned below the threshold
 *       between two polls.
 *       <p>
 *       Below shows another example that takes some action if a
 *       memory pool is under low memory and ignores the memory usage
 *       changes during the action processing time.
 *
 *       <pre>
 *       // Assume the usage threshold is supported for this pool.
 *       // Set the threshold to myThreshold which determines if
 *       // the application will take some action under low memory condition.
 *       pool.setUsageThreshold(myThreshold);
 *
 *       int prevCrossingCount = 0;
 *       while (true) {
 *           // A busy loop to detect when the memory usage
 *           // has exceeded the threshold.
 *           while (!pool.isUsageThresholdExceeded() ||
 *                  pool.getUsageThresholdCount() == prevCrossingCount) {
 *               try {
 *                   Thread.sleep(sometime)
 *               } catch (InterruptException e) {
 *                   ....
 *               }
 *           }
 *
 *           // Do some processing such as check for memory usage
 *           // and issue a warning
 *           ....
 *
 *           // Gets the current threshold count. The busy loop will then
 *           // ignore any crossing of threshold happens during the processing.
 *           prevCrossingCount = pool.getUsageThresholdCount();
 *       }
 *       </pre><hr>
 *   </li>
 *   <li><a name="ThresholdNotification"><b>Usage Threshold Notifications</b></a>
 *       <p>
 *       Usage threshold notification will be emitted by {@link MemoryMXBean}.
 *       When the Java virtual machine detects that the memory usage of
 *       a memory pool has reached or exceeded the usage threshold
 *       the virtual machine will trigger the <tt>MemoryMXBean</tt> to emit an
 *       {@link MemoryNotificationInfo#MEMORY_THRESHOLD_EXCEEDED
 *       usage threshold exceeded notification}.
 *       Another usage threshold exceeded notification will not be
 *       generated until the usage has fallen below the threshold and
 *       then exceeded it again.
 *       <p>
 *       Below is an example code implementing the same logic as the
 *       first example above but using the usage threshold notification
 *       mechanism to detect low memory conditions instead of polling.
 *       In this example code, upon receiving notification, the notification
 *       listener notifies another thread to perform the actual action
 *       such as to redistribute outstanding tasks, stop receiving tasks,
 *       or resume receiving tasks.
 *       The <tt>handleNotification</tt> method should be designed to
 *       do a very minimal amount of work and return without delay to avoid
 *       causing delay in delivering subsequent notifications.  Time-consuming
 *       actions should be performed by a separate thread.
 *       The notification listener may be invoked by multiple threads
 *       concurrently; so the tasks performed by the listener
 *       should be properly synchronized.
 *
 *       <pre>
 *       class MyListener implements javax.management.NotificationListener {
 *            public void handleNotification(Notification notification, Object handback)  {
 *                String notifType = notification.getType();
 *                if (notifType.equals(MemoryNotificationInfo.MEMORY_THRESHOLD_EXCEEDED)) {
 *                    // potential low memory, notify another thread
 *                    // to redistribute outstanding tasks to other VMs
 *                    // and stop receiving new tasks.
 *                    lowMemory = true;
 *                    notifyAnotherThread(lowMemory);
 *                }
 *            }
 *       }
 *
 *       // Register MyListener with MemoryMXBean
 *       MemoryMXBean mbean = ManagementFactory.getMemoryMXBean();
 *       NotificationEmitter emitter = (NotificationEmitter) mbean;
 *       MyListener listener = new MyListener();
 *       emitter.addNotificationListener(listener, null, null);
 *
 *       // Assume this pool supports a usage threshold.
 *       // Set the threshold to myThreshold above which no new tasks
 *       // should be taken.
 *       pool.setUsageThreshold(myThreshold);
 *
 *       // Usage threshold detection is enabled and notification will be
 *       // handled by MyListener.  Continue for other processing.
 *       ....
 *
 *       </pre>
 * <hr>
 *       <p>
 *       There is no guarantee about when the <tt>MemoryMXBean</tt> will emit
 *       a threshold notification and when the notification will be delivered.
 *       When a notification listener is invoked, the memory usage of
 *       the memory pool may have crossed the usage threshold more
 *       than once.
 *       The {@link MemoryNotificationInfo#getCount} method returns the number
 *       of times that the memory usage has crossed the usage threshold
 *       at the point in time when the notification was constructed.
 *       It can be compared with the current usage threshold count returned
 *       by the {@link #getUsageThresholdCount} method to determine if
 *       such situation has occurred.
 *   </li>
 * </ol>
 *
 * <h3><a name="CollectionThreshold">4. Collection Usage Threshold</a></h3>
 *
 * Collection usage threshold is a manageable attribute only applicable
 * to some garbage-collected memory pools.
 * After a Java virtual machine has expended effort in reclaiming memory
 * space by recycling unused objects in a memory pool at garbage collection
 * time, some number of bytes in the memory pools that are garbaged
 * collected will still be in use.  The collection usage threshold
 * allows a value to be set for this number of bytes such
 * that if the threshold is exceeded,
 * a {@link MemoryNotificationInfo#MEMORY_THRESHOLD_EXCEEDED
 * collection usage threshold exceeded notification}
 * will be emitted by the {@link MemoryMXBean}.
 * In addition, the {@link #getCollectionUsageThresholdCount
 * collection usage threshold count} will then be incremented.
 *
 * <p>
 * The {@link MemoryPoolMXBean#isCollectionUsageThresholdSupported} method can
 * be used to determine if this functionality is supported.
 *
 * <p>
 * A Java virtual machine performs collection usage threshold checking
 * on a memory pool basis.  This checking is enabled if the collection
 * usage threshold is set to a positive value.
 * If the collection usage threshold is set to zero, this checking
 * is disabled on this memory pool.  Default value is zero.
 * The Java virtual machine performs the collection usage threshold
 * checking at garbage collection time.
 *
 * <p>
 * Some garbage-collected memory pools may
 * choose not to support the collection usage threshold.  For example,
 * a memory pool is only managed by a continuous concurrent garbage
 * collector.  Objects can be allocated in this memory pool by some thread
 * while the unused objects are reclaimed by the concurrent garbage
 * collector simultaneously.  Unless there is a well-defined
 * garbage collection time which is the best appropriate time
 * to check the memory usage, the collection usage threshold should not
 * be supported.
 *
 * <p>
 * The collection usage threshold is designed for monitoring the memory usage
 * after the Java virtual machine has expended effort in reclaiming
 * memory space.  The collection usage could also be monitored
 * by the polling and threshold notification mechanism
 * described above for the <a href="#UsageThreshold">usage threshold</a>
 * in a similar fashion.
 *
 * <p>
 *  内存池的管理接口。内存池表示由Java虚拟机管理的内存资源,由一个或多个{@link MemoryManagerMXBean内存管理器}管理。
 * 
 *  <p> Java虚拟机具有此接口的实现类的一个或多个实例。
 * 实现此接口的实例是<a href="ManagementFactory.html#MXBean"> MXBean </a>,可通过调用{@link ManagementFactory#getMemoryPoolMXBeans}
 * 方法或从{@link ManagementFactory#getPlatformMBeanServer platform < tt> MBeanServer </tt>}方法。
 *  <p> Java虚拟机具有此接口的实现类的一个或多个实例。
 * 
 *  <p> <tt> ObjectName </tt>用于唯一标识<tt> MBeanServer </tt>中的内存池的MXBean是：
 * <blockquote>
 *  {@link ManagementFactory#MEMORY_POOL_MXBEAN_DOMAIN_TYPE <tt> java.lang：type = MemoryPool </tt>} <tt>
 * ,name = </tt> <i>泳池名称</i>。
 * </blockquote>
 * 
 *  它可以通过调用{@link PlatformManagedObject#getObjectName}方法获得。
 * 
 *  <h3>内存类型</h3> <p> Java虚拟机具有用于对象分配的堆,并且还为方法区和Java虚拟机执行维护非堆内存。 Java虚拟机可以有一个或多个内存池。
 * 每个内存池表示以下类型之一的内存区域：。
 * <ul>
 *  <li> {@ link MemoryType#HEAP heap} </li> <li> {@ link MemoryType#NON_HEAP non-heap} </li>
 * </ul>
 * 
 *  <h3>内存使用监控</h3>
 * 
 * 内存池具有以下属性：
 * <ul>
 *  <li> <a href="#Usage">内存使用</a> </li> <li> <a href="#PeakUsage">峰值内存使用</a> </li> <li> <a href ="#UsageThreshold">
 * 使用阈值</a> </li> <li> <a href="#CollectionThreshold">集合使用阈值</a>(仅受某些<em>垃圾回收</em >内存池)</li>。
 * </ul>
 * 
 *  <h3> <a name="Usage"> 1。内存使用</a> </h3>
 * 
 *  {@link #getUsage}方法提供了内存池当前使用情况的估计值。
 * 对于垃圾收集的存储器池,所使用的存储器的量包括池中包括<em>可达到的<em>和<em>不可达的</em>对象的所有对象占用的存储器。
 * 
 *  <p>一般来说,这种方法是获取近似内存使用量的轻量级操作。对于一些存储器池,例如,当对象不是连续地打包时,该方法可能是昂贵的操作,其需要一些计算来确定当前存储器使用。实现应该记录这种情况。
 * 
 *  <h3> <a name="PeakUsage"> 2。峰值内存使用情况</a> </h3>
 * 
 *  由于虚拟机启动或峰值复位,Java虚拟机维护内存池的峰值内存使用率。
 * 内存使用的峰值由{@link #getPeakUsage}方法返回,并通过调用{@link #resetPeakUsage}方法重置。
 * 
 *  <h3> <a name="UsageThreshold"> 3。使用阈值</a> </h3>
 * 
 * 每个存储器池具有称为<i>使用阈值</i>的可管理属性,其具有由Java虚拟机提供的默认值。默认值是平台相关的。
 * 可以通过{@link #setUsageThreshold setUsageThreshold}方法设置使用阈值。如果阈值设置为正值,则在此内存池中启用使用阈值交叉检查。
 * 如果使用阈值设置为零,则禁用对此内存池的使用阈值交叉检查。 {@link MemoryPoolMXBean#isUsageThresholdSupported}方法可用于确定是否支持此功能。
 * <p>
 *  Java虚拟机在其最佳适当时间(通常在垃圾收集时)在存储器池的基础上执行使用阈值交叉检查。
 * 每个内存池维护{@link #getUsageThresholdCount用量阈值计数},每次当Java虚拟机检测到内存池使用率超过阈值时,它将增加。
 * <p>
 * 这个可管理的使用阈值属性设计用于以低开销监视内存使用的增长趋势。使用阈值可能不适用于某些内存池。例如,代数垃圾收集器,在许多Java虚拟机实现中使用的常见垃圾收集算法,管理两个或更多代按年龄分离对象。
 * 大多数对象分配在<em>最年轻的一代</em>(假设一个苗圃内存池)。
 * 苗圃内存池被设计为被填满,并且收集苗圃内存池将释放其大部分内存空间,因为它预期包含大多数短暂的对象,并且在垃圾收集时大多是不可达的。在这种情况下,更适合的是苗圃存储器池不支持使用阈值。
 * 此外,如果在一个存储器池中的对象分配的成本非常低(例如,仅仅是原子指针交换),Java虚拟机可能不支持该存储器池的使用阈值,因为在将该使用与阈值高于对象分配的成本。
 * 
 * <p>
 *  可以使用<a href="#Polling">轮询</a>或<a href="#ThresholdNotification">阈值通知</a>机制来监视系统的内存使用情况。
 * 
 * <ol type="a">
 *  <li> <a name="Polling"> <b>轮询</b> </a>
 * <p>
 * 应用程序可以通过调用所有内存池的{@link #getUsage}方法或支持使用阈值的内存池的{@link #isUsageThresholdExceeded}方法,持续监视其内存使用情况。
 * 下面是具有专用于任务分布和处理的线程的示例代码。在每个时间间隔,它将确定它是否应该根据其内存使用接收和处理新任务。
 * 如果内存使用量超过其使用阈值,它将重新分配所有未完成的任务到其他VM,并停止接收新任务,直到内存使用率回到低于其使用阈值。
 * 
 * <pre>
 *  //假设此池支持使用阈值。 //将阈值设置为myThreshold,在此之上不应采用任何新任务//。 pool.setUsageThreshold(myThreshold); ....
 * 
 *  boolean lowMemory = false; while(true){if(pool.isUsageThresholdExceeded()){//潜在的低内存,因此重新分配任务到其他VM lowMemory = true; redistributeTasks(); // stop receive new tasks stopReceivingTasks(); }
 *  else {if(lowMemory){//恢复接收任务lowMemory = false; resumeReceivingTasks(); } //处理未完成的任务...} // sleep for
 *  sometime try {Thread.sleep(sometime);} } catch(InterruptedException e){...}}。
 * </pre>
 * 
 * <hr>
 * 上述示例不区分存储器使用已经从存储器使用在两次迭代之间保持高于阈值的情况下暂时降低到使用阈值以下的情况。
 *  {@link #getUsageThresholdCount}方法返回的使用阈值计数可用于确定内存使用情况是否已在两次轮询之间返回到阈值以下。
 * <p>
 *  下面显示了另一个示例,如果内存池在低内存下并且在操作处理时间期间忽略内存使用情况更改,则需要执行一些操作。
 * 
 * <pre>
 *  //假设此池支持使用阈值。 //将阈值设置为myThreshold,确定应用程序是否在低内存条件下采取一些操作。 pool.setUsageThreshold(myThreshold);
 * 
 *  int prevCrossingCount = 0; while(true){//一个忙循环,用于检测内存使用情况//是否超过阈值。
 *  while(！pool.isUsageThresholdExceeded()|| pool.getUsageThresholdCount()== prevCrossingCount){try {Thread.sleep(sometime)}
 *  catch(InterruptException e){....}}。
 *  int prevCrossingCount = 0; while(true){//一个忙循环,用于检测内存使用情况//是否超过阈值。
 * 
 *  //做一些处理,如检查内存使用情况//并发出警告....
 * 
 *  //获取当前阈值计数。然后,忙循环将忽略在处理期间发生的阈值的任何交叉。
 *  prevCrossingCount = pool.getUsageThresholdCount(); } </pre> <hr>。
 * </li>
 *  <li> <a name="ThresholdNotification"> <b>使用阈值通知</b> </a>
 * <p>
 * 使用阈值通知将由{@link MemoryMXBean}发出。
 * 当Java虚拟机检测到内存池的内存使用量已达到或超过使用阈值时,虚拟机将触发<tt> MemoryMXBean </tt>发出{@link MemoryNotificationInfo#MEMORY_THRESHOLD_EXCEEDED超过使用率阈值通知}
 * 。
 * 使用阈值通知将由{@link MemoryMXBean}发出。在使用量下降到阈值以下,然后再次超过阈值时,才会生成另一个超过使用阈值的通知。
 * <p>
 *  下面是实现与上述第一个示例相同的逻辑的示例代码,但使用使用阈值通知机制来检测低内存条件,而不是轮询。
 * 在此示例代码中,在接收到通知时,通知侦听器通知另一线程执行实际动作,例如重新分配未完成任务,停止接收任务或恢复接收任务。
 *  <tt> handleNotification </tt>方法应设计为执行非常少量的工作,并且没有延迟地返回,以避免在传递后续通知时造成延迟。耗时的操作应该由单独的线程执行。
 * 通知侦听器可以由多个线程同时调用;因此侦听器执行的任务应该正确同步。
 * 
 * <pre>
 * class MyListener implements javax.management.NotificationListener {public void handleNotification(Notification notification,Object handback){String notifType = notification.getType(); if(notifType.equals(MemoryNotificationInfo.MEMORY_THRESHOLD_EXCEEDED)){//潜在低内存,通知另一个线程//将未完成的任务重新分配给其他VM //并停止接收新任务。
 *  lowMemory = true; notifyAnotherThread(lowMemory); }}}。
 * 
 *  // Register MyListener with MemoryMXBean MemoryMXBean mbean = ManagementFactory.getMemoryMXBean(); N
 * otificationEmitter emitter =(NotificationEmitter)mbean; MyListener listener = new MyListener(); emitt
 * er.addNotificationListener(listener,null,null);。
 * 
 *  //假设此池支持使用阈值。 //将阈值设置为myThreshold,在此之上不应采用任何新任务//。 pool.setUsageThreshold(myThreshold);
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
public interface MemoryPoolMXBean extends PlatformManagedObject {
    /**
     * Returns the name representing this memory pool.
     *
     * <p>
     * 
     *  //使用阈值检测已启用,通知将由MyListener处理。继续进行其他处理。 ....
     * 
     * </pre>
     * <hr>
     * <p>
     * 不能保证<tt> MemoryMXBean </tt>何时会发出阈值通知,以及何时会发送通知。当调用通知侦听器时,存储器池的存储器使用可能已超过使用阈值多于一次。
     *  {@link MemoryNotificationInfo#getCount}方法返回在构建通知的时间点内存使用量已超过使用阈值的次数。
     * 它可以与{@link #getUsageThresholdCount}方法返回的当前使用阈值计数进行比较,以确定是否发生了这种情况。
     * </li>
     * </ol>
     * 
     *  <h3> <a name="CollectionThreshold"> 4。集合使用阈值</a> </h3>
     * 
     *  集合使用阈值是一个可管理的属性,仅适用于一些垃圾回收的内存池。在Java虚拟机通过在垃圾收集时回收内存池中的未使用对象而花费了回收内存空间的努力之后,仍在使用收集垃圾邮件的内存池中的某些字节数。
     * 收集使用阈值允许为此字节数设置一个值,以便如果超过阈值,{@link MemoryMXBean}将发出{@link MemoryNotificationInfo#MEMORY_THRESHOLD_EXCEEDED集合使用阈值超过通知}
     * 。
     *  集合使用阈值是一个可管理的属性,仅适用于一些垃圾回收的内存池。在Java虚拟机通过在垃圾收集时回收内存池中的未使用对象而花费了回收内存空间的努力之后,仍在使用收集垃圾邮件的内存池中的某些字节数。
     * 此外,{@link #getCollectionUsageThresholdCount集合使用阈值计数}将增加。
     * 
     * <p>
     * {@link MemoryPoolMXBean#isCollectionUsageThresholdSupported}方法可用于确定是否支持此功能。
     * 
     * <p>
     *  Java虚拟机在内存池的基础上执行集合使用阈值检查。如果集合使用阈值设置为正值,则启用此检查。如果集合使用阈值设置为零,则在此内存池上禁用此检查。默认值为零。
     *  Java虚拟机在垃圾收集时执行收集使用阈值检查。
     * 
     * <p>
     *  一些垃圾收集的内存池可能选择不支持收集使用阈值。例如,内存池只由连续并发垃圾收集器管理。对象可以由一些线程分配在该内存池中,而未使用的对象由并发垃圾回收器同时回收。
     * 除非有一个明确定义的垃圾收集时间,这是检查内存使用情况的最合适的时间,否则不应该支持收集使用阈值。
     * 
     * <p>
     *  收集使用阈值设计用于在Java虚拟机耗尽回收内存空间的工作后监视内存使用情况。
     * 还可以通过类似方式针对<a href="#UsageThreshold">使用阈值</a>的上述轮询和阈值通知机制来监视收集使用。
     * 
     * 
     * @return the name of this memory pool.
     */
    public String getName();

    /**
     * Returns the type of this memory pool.
     *
     * <p>
     * <b>MBeanServer access</b>:<br>
     * The mapped type of <tt>MemoryType</tt> is <tt>String</tt>
     * and the value is the name of the <tt>MemoryType</tt>.
     *
     * <p>
     *  返回表示此内存池的名称。
     * 
     * 
     * @return the type of this memory pool.
     */
    public MemoryType getType();

    /**
     * Returns an estimate of the memory usage of this memory pool.
     * This method returns <tt>null</tt>
     * if this memory pool is not valid (i.e. no longer exists).
     *
     * <p>
     * This method requests the Java virtual machine to make
     * a best-effort estimate of the current memory usage of this
     * memory pool. For some memory pools, this method may be an
     * expensive operation that requires some computation to determine
     * the estimate.  An implementation should document when
     * this is the case.
     *
     * <p>This method is designed for use in monitoring system
     * memory usage and detecting low memory condition.
     *
     * <p>
     * <b>MBeanServer access</b>:<br>
     * The mapped type of <tt>MemoryUsage</tt> is
     * <tt>CompositeData</tt> with attributes as specified in
     * {@link MemoryUsage#from MemoryUsage}.
     *
     * <p>
     *  返回此内存池的类型。
     * 
     * <p>
     * <b> MBeanServer访问</b>：<br> <tt> MemoryType </tt>的映射类型为<tt> String </tt>,值为<tt> MemoryType </tt> 。
     * 
     * 
     * @return a {@link MemoryUsage} object; or <tt>null</tt> if
     * this pool not valid.
     */
    public MemoryUsage getUsage();

    /**
     * Returns the peak memory usage of this memory pool since the
     * Java virtual machine was started or since the peak was reset.
     * This method returns <tt>null</tt>
     * if this memory pool is not valid (i.e. no longer exists).
     *
     * <p>
     * <b>MBeanServer access</b>:<br>
     * The mapped type of <tt>MemoryUsage</tt> is
     * <tt>CompositeData</tt> with attributes as specified in
     * {@link MemoryUsage#from MemoryUsage}.
     *
     * <p>
     *  返回此内存池的内存使用情况的估计值。如果此内存池无效(即不再存在),此方法将返回<tt> null </tt>。
     * 
     * <p>
     *  此方法请求Java虚拟机对此内存池的当前内存使用情况进行尽力而为的估计。对于一些存储器池,该方法可能是昂贵的操作,其需要一些计算来确定估计。实现应该记录这种情况。
     * 
     *  <p>此方法设计用于监视系统内存使用情况和检测低内存条件。
     * 
     * <p>
     *  <b> MBeanServer访问</b>：<br> <tt> MemoryUsage </tt>的映射类型是<tt> CompositeData </tt>,具有{@link MemoryUsage#from MemoryUsage}
     * 中指定的属性。
     * 
     * 
     * @return a {@link MemoryUsage} object representing the peak
     * memory usage; or <tt>null</tt> if this pool is not valid.
     *
     */
    public MemoryUsage getPeakUsage();

    /**
     * Resets the peak memory usage statistic of this memory pool
     * to the current memory usage.
     *
     * <p>
     *  返回自Java虚拟机启动或峰值重置后此内存池的峰值内存使用情况。如果此内存池无效(即不再存在),此方法将返回<tt> null </tt>。
     * 
     * <p>
     *  <b> MBeanServer访问</b>：<br> <tt> MemoryUsage </tt>的映射类型是<tt> CompositeData </tt>,具有{@link MemoryUsage#from MemoryUsage}
     * 中指定的属性。
     * 
     * 
     * @throws java.lang.SecurityException if a security manager
     *         exists and the caller does not have
     *         ManagementPermission("control").
     */
    public void resetPeakUsage();

    /**
     * Tests if this memory pool is valid in the Java virtual
     * machine.  A memory pool becomes invalid once the Java virtual
     * machine removes it from the memory system.
     *
     * <p>
     *  将此内存池的峰值内存使用情况统计信息重置为当前内存使用情况。
     * 
     * 
     * @return <tt>true</tt> if the memory pool is valid in the running
     *              Java virtual machine;
     *         <tt>false</tt> otherwise.
     */
    public boolean isValid();

    /**
     * Returns the name of memory managers that manages this memory pool.
     * Each memory pool will be managed by at least one memory manager.
     *
     * <p>
     * 测试此内存池在Java虚拟机中是否有效。一旦Java虚拟机将其从内存系统中删除,内存池将无效。
     * 
     * 
     * @return an array of <tt>String</tt> objects, each is the name of
     * a memory manager managing this memory pool.
     */
    public String[] getMemoryManagerNames();

    /**
     * Returns the usage threshold value of this memory pool in bytes.
     * Each memory pool has a platform-dependent default threshold value.
     * The current usage threshold can be changed via the
     * {@link #setUsageThreshold setUsageThreshold} method.
     *
     * <p>
     *  返回管理此内存池的内存管理器的名称。每个内存池将由至少一个内存管理器管理。
     * 
     * 
     * @return the usage threshold value of this memory pool in bytes.
     *
     * @throws UnsupportedOperationException if this memory pool
     *         does not support a usage threshold.
     *
     * @see #isUsageThresholdSupported
     */
    public long getUsageThreshold();

    /**
     * Sets the threshold of this memory pool to the given <tt>threshold</tt>
     * value if this memory pool supports the usage threshold.
     * The usage threshold crossing checking is enabled in this memory pool
     * if the threshold is set to a positive value.
     * The usage threshold crossing checking is disabled
     * if it is set to zero.
     *
     * <p>
     *  返回此内存池的使用率阈值(以字节为单位)。每个内存池都有一个平台相关的默认阈值。可以通过{@link #setUsageThreshold setUsageThreshold}方法更改当前使用阈值。
     * 
     * 
     * @param threshold the new threshold value in bytes. Must be non-negative.
     *
     * @throws IllegalArgumentException if <tt>threshold</tt> is negative
     *         or greater than the maximum amount of memory for
     *         this memory pool if defined.
     *
     * @throws UnsupportedOperationException if this memory pool
     *         does not support a usage threshold.
     *
     * @throws java.lang.SecurityException if a security manager
     *         exists and the caller does not have
     *         ManagementPermission("control").
     *
     * @see #isUsageThresholdSupported
     * @see <a href="#UsageThreshold">Usage threshold</a>
     */
    public void setUsageThreshold(long threshold);

    /**
     * Tests if the memory usage of this memory pool
     * reaches or exceeds its usage threshold value.
     *
     * <p>
     *  如果此内存池支持使用阈值,则将此内存池的阈值设置为给定的<tt>阈值</tt>值。如果阈值设置为正值,则在此内存池中启用使用阈值交叉检查。如果设置为零,则禁用使用阈值交叉检查。
     * 
     * 
     * @return <tt>true</tt> if the memory usage of
     * this memory pool reaches or exceeds the threshold value;
     * <tt>false</tt> otherwise.
     *
     * @throws UnsupportedOperationException if this memory pool
     *         does not support a usage threshold.
     */
    public boolean isUsageThresholdExceeded();

    /**
     * Returns the number of times that the memory usage has crossed
     * the usage threshold.
     *
     * <p>
     *  测试此内存池的内存使用量是否达到或超过其使用阈值。
     * 
     * 
     * @return the number of times that the memory usage
     * has crossed its usage threshold value.
     *
     * @throws UnsupportedOperationException if this memory pool
     * does not support a usage threshold.
     */
    public long getUsageThresholdCount();

    /**
     * Tests if this memory pool supports usage threshold.
     *
     * <p>
     *  返回内存使用量已超过使用阈值的次数。
     * 
     * 
     * @return <tt>true</tt> if this memory pool supports usage threshold;
     * <tt>false</tt> otherwise.
     */
    public boolean isUsageThresholdSupported();

    /**
     * Returns the collection usage threshold value of this memory pool
     * in bytes.  The default value is zero. The collection usage
     * threshold can be changed via the
     * {@link #setCollectionUsageThreshold setCollectionUsageThreshold} method.
     *
     * <p>
     *  测试此内存池是否支持使用阈值。
     * 
     * 
     * @return the collection usage threshold of this memory pool in bytes.
     *
     * @throws UnsupportedOperationException if this memory pool
     *         does not support a collection usage threshold.
     *
     * @see #isCollectionUsageThresholdSupported
     */
    public long getCollectionUsageThreshold();

    /**
     * Sets the collection usage threshold of this memory pool to
     * the given <tt>threshold</tt> value.
     * When this threshold is set to positive, the Java virtual machine
     * will check the memory usage at its best appropriate time after it has
     * expended effort in recycling unused objects in this memory pool.
     * <p>
     * The collection usage threshold crossing checking is enabled
     * in this memory pool if the threshold is set to a positive value.
     * The collection usage threshold crossing checking is disabled
     * if it is set to zero.
     *
     * <p>
     *  返回此内存池的集合使用阈值(以字节为单位)。默认值为零。
     * 可以通过{@link #setCollectionUsageThreshold setCollectionUsageThreshold}方法更改收集使用阈值。
     * 
     * 
     * @param threshold the new collection usage threshold value in bytes.
     *              Must be non-negative.
     *
     * @throws IllegalArgumentException if <tt>threshold</tt> is negative
     *         or greater than the maximum amount of memory for
     *         this memory pool if defined.
     *
     * @throws UnsupportedOperationException if this memory pool
     *         does not support a collection usage threshold.
     *
     * @throws java.lang.SecurityException if a security manager
     *         exists and the caller does not have
     *         ManagementPermission("control").
     *
     * @see #isCollectionUsageThresholdSupported
     * @see <a href="#CollectionThreshold">Collection usage threshold</a>
     */
    public void setCollectionUsageThreshold(long threshold);

    /**
     * Tests if the memory usage of this memory pool after
     * the most recent collection on which the Java virtual
     * machine has expended effort has reached or
     * exceeded its collection usage threshold.
     * This method does not request the Java virtual
     * machine to perform any garbage collection other than its normal
     * automatic memory management.
     *
     * <p>
     * 将此内存池的收集使用阈值设置为给定的<tt>阈值</tt>值。当此阈值设置为正时,Java虚拟机将在耗尽回收此内存池中未使用的对象的工作后,在其最佳适当时间检查内存使用情况。
     * <p>
     *  如果阈值设置为正值,则在此内存池中启用收集使用阈值跨越检查。如果设置为零,则禁用收集使用阈值交叉检查。
     * 
     * 
     * @return <tt>true</tt> if the memory usage of this memory pool
     * reaches or exceeds the collection usage threshold value
     * in the most recent collection;
     * <tt>false</tt> otherwise.
     *
     * @throws UnsupportedOperationException if this memory pool
     *         does not support a usage threshold.
     */
    public boolean isCollectionUsageThresholdExceeded();

    /**
     * Returns the number of times that the Java virtual machine
     * has detected that the memory usage has reached or
     * exceeded the collection usage threshold.
     *
     * <p>
     *  测试在Java虚拟机耗用的最新集合之后此内存池的内存使用情况是否已达到或超过其收集使用阈值。此方法不会请求Java虚拟机执行除了其正常的自动内存管理之外的任何垃圾回收。
     * 
     * 
     * @return the number of times that the memory
     * usage has reached or exceeded the collection usage threshold.
     *
     * @throws UnsupportedOperationException if this memory pool
     *         does not support a collection usage threshold.
     *
     * @see #isCollectionUsageThresholdSupported
     */
    public long getCollectionUsageThresholdCount();

    /**
     * Returns the memory usage after the Java virtual machine
     * most recently expended effort in recycling unused objects
     * in this memory pool.
     * This method does not request the Java virtual
     * machine to perform any garbage collection other than its normal
     * automatic memory management.
     * This method returns <tt>null</tt> if the Java virtual
     * machine does not support this method.
     *
     * <p>
     * <b>MBeanServer access</b>:<br>
     * The mapped type of <tt>MemoryUsage</tt> is
     * <tt>CompositeData</tt> with attributes as specified in
     * {@link MemoryUsage#from MemoryUsage}.
     *
     * <p>
     *  返回Java虚拟机检测到内存使用量已达到或超过集合使用阈值的次数。
     * 
     * 
     * @return a {@link MemoryUsage} representing the memory usage of
     * this memory pool after the Java virtual machine most recently
     * expended effort in recycling unused objects;
     * <tt>null</tt> if this method is not supported.
     */
    public MemoryUsage getCollectionUsage();

    /**
     * Tests if this memory pool supports a collection usage threshold.
     *
     * <p>
     *  返回Java虚拟机最近消耗的内存使用量,以回收此内存池中未使用的对象。此方法不会请求Java虚拟机执行除了其正常的自动内存管理之外的任何垃圾回收。
     * 如果Java虚拟机不支持此方法,则此方法返回<tt> null </tt>。
     * 
     * <p>
     * <b> MBeanServer访问</b>：<br> <tt> MemoryUsage </tt>的映射类型是具有{@link MemoryUsage#from MemoryUsage}中指定的属性的<tt>
     * 
     * @return <tt>true</tt> if this memory pool supports the
     * collection usage threshold; <tt>false</tt> otherwise.
     */
    public boolean isCollectionUsageThresholdSupported();
}
