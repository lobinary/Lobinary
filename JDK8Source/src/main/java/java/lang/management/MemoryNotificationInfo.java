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
import sun.management.MemoryNotifInfoCompositeData;

/**
 * The information about a memory notification.
 *
 * <p>
 * A memory notification is emitted by {@link MemoryMXBean}
 * when the Java virtual machine detects that the memory usage
 * of a memory pool is exceeding a threshold value.
 * The notification emitted will contain the memory notification
 * information about the detected condition:
 * <ul>
 *   <li>The name of the memory pool.</li>
 *   <li>The memory usage of the memory pool when the notification
 *       was constructed.</li>
 *   <li>The number of times that the memory usage has crossed
 *       a threshold when the notification was constructed.
 *       For usage threshold notifications, this count will be the
 *       {@link MemoryPoolMXBean#getUsageThresholdCount usage threshold
 *       count}.  For collection threshold notifications,
 *       this count will be the
 *       {@link MemoryPoolMXBean#getCollectionUsageThresholdCount
 *       collection usage threshold count}.
 *       </li>
 * </ul>
 *
 * <p>
 * A {@link CompositeData CompositeData} representing
 * the <tt>MemoryNotificationInfo</tt> object
 * is stored in the
 * {@link javax.management.Notification#setUserData user data}
 * of a {@link javax.management.Notification notification}.
 * The {@link #from from} method is provided to convert from
 * a <tt>CompositeData</tt> to a <tt>MemoryNotificationInfo</tt>
 * object. For example:
 *
 * <blockquote><pre>
 *      Notification notif;
 *
 *      // receive the notification emitted by MemoryMXBean and set to notif
 *      ...
 *
 *      String notifType = notif.getType();
 *      if (notifType.equals(MemoryNotificationInfo.MEMORY_THRESHOLD_EXCEEDED) ||
 *          notifType.equals(MemoryNotificationInfo.MEMORY_COLLECTION_THRESHOLD_EXCEEDED)) {
 *          // retrieve the memory notification information
 *          CompositeData cd = (CompositeData) notif.getUserData();
 *          MemoryNotificationInfo info = MemoryNotificationInfo.from(cd);
 *          ....
 *      }
 * </pre></blockquote>
 *
 * <p>
 * The types of notifications emitted by <tt>MemoryMXBean</tt> are:
 * <ul>
 *   <li>A {@link #MEMORY_THRESHOLD_EXCEEDED
 *       usage threshold exceeded notification}.
 *       <br>This notification will be emitted when
 *       the memory usage of a memory pool is increased and has reached
 *       or exceeded its
 *       <a href="MemoryPoolMXBean.html#UsageThreshold"> usage threshold</a> value.
 *       Subsequent crossing of the usage threshold value does not cause
 *       further notification until the memory usage has returned
 *       to become less than the usage threshold value.
 *       <p></li>
 *   <li>A {@link #MEMORY_COLLECTION_THRESHOLD_EXCEEDED
 *       collection usage threshold exceeded notification}.
 *       <br>This notification will be emitted when
 *       the memory usage of a memory pool is greater than or equal to its
 *       <a href="MemoryPoolMXBean.html#CollectionThreshold">
 *       collection usage threshold</a> after the Java virtual machine
 *       has expended effort in recycling unused objects in that
 *       memory pool.</li>
 * </ul>
 *
 * <p>
 *  有关内存通知的信息。
 * 
 * <p>
 *  当Java虚拟机检测到内存池的内存使用率超过阈值时,由{@link MemoryMXBean}发出内存通知。发出的通知将包含有关检测到的条件的内存通知信息：
 * <ul>
 *  <li>内存池的名称。</li> <li>构建通知时内存池的内存使用情况。</li> <li>内存使用量超过阈值的次数通知被构造。
 * 对于使用阈值通知,此计数将为{@link MemoryPoolMXBean#getUsageThresholdCount用量阈值计数}。
 * 对于收集阈值通知,此计数将为{@link MemoryPoolMXBean#getCollectionUsageThresholdCount集合使用阈值计数}。
 * </li>
 * </ul>
 * 
 * <p>
 *  表示<tt> MemoryNotificationInfo </tt>对象的{@link CompositeData CompositeData}存储在{@link javax.management.Notification通知}
 * 的{@link javax.management.Notification#setUserData用户数据}中。
 *  {@link #from from}方法用于将<tt> CompositeData </tt>转换为<tt> MemoryNotificationInfo </tt>对象。例如：。
 * 
 *  <blockquote> <pre>通知通知;
 * 
 *  //接收MemoryMXBean发出的通知并设置为notif ...
 * 
 * String notifType = notif.getType(); if(notifType.equals(MemoryNotificationInfo.MEMORY_THRESHOLD_EXCEE
 * DED)|| notifType.equals(MemoryNotificationInfo.MEMORY_COLLECTION_THRESHOLD_EXCEEDED)){//检索内存通知信息CompositeData cd =(CompositeData)notif.getUserData(); MemoryNotificationInfo info = MemoryNotificationInfo.from(cd); ....}
 *  </pre> </blockquote>。
 * 
 * <p>
 *  <tt> MemoryMXBean </tt>发出的通知类型有：
 * <ul>
 *  <li>已超过{@link #MEMORY_THRESHOLD_EXCEEDED个使用情况阈值通知}。
 *  <br>当内存池的内存使用量增加并已达到或超过其<a href="MemoryPoolMXBean.html#UsageThreshold">使用阈值</a>值时,将发出此通知。
 * 随后的使用阈值的交叉不引起进一步的通知,直到存储器使用已经返回到变得小于使用阈值。
 *  <p> </li> <li>一个{@link #MEMORY_COLLECTION_THRESHOLD_EXCEEDED个收集使用情况阈值已超过通知}。
 *  <br>当内存池的内存使用量大于或等于其时,将发出此通知。
 * <a href="MemoryPoolMXBean.html#CollectionThreshold">
 *  收集使用阈值</a>,之后Java虚拟机已耗尽了对该内存池中未使用的对象的回收。</li>
 * </ul>
 * 
 * 
 * @author  Mandy Chung
 * @since   1.5
 *
 */
public class MemoryNotificationInfo {
    private final String poolName;
    private final MemoryUsage usage;
    private final long count;

    /**
     * Notification type denoting that
     * the memory usage of a memory pool has
     * reached or exceeded its
     * <a href="MemoryPoolMXBean.html#UsageThreshold"> usage threshold</a> value.
     * This notification is emitted by {@link MemoryMXBean}.
     * Subsequent crossing of the usage threshold value does not cause
     * further notification until the memory usage has returned
     * to become less than the usage threshold value.
     * The value of this notification type is
     * <tt>java.management.memory.threshold.exceeded</tt>.
     * <p>
     * 表示内存池的内存使用量已达到或超过其<a href="MemoryPoolMXBean.html#UsageThreshold">使用阈值</a>值的通知类型。
     * 此通知由{@link MemoryMXBean}发出。随后的使用阈值的交叉不引起进一步的通知,直到存储器使用已经返回到变得小于使用阈值。
     * 此通知类型的值为<tt> java.management.memory.threshold.exceeded </tt>。
     * 
     */
    public static final String MEMORY_THRESHOLD_EXCEEDED =
        "java.management.memory.threshold.exceeded";

    /**
     * Notification type denoting that
     * the memory usage of a memory pool is greater than or equal to its
     * <a href="MemoryPoolMXBean.html#CollectionThreshold">
     * collection usage threshold</a> after the Java virtual machine
     * has expended effort in recycling unused objects in that
     * memory pool.
     * This notification is emitted by {@link MemoryMXBean}.
     * The value of this notification type is
     * <tt>java.management.memory.collection.threshold.exceeded</tt>.
     * <p>
     *  通知类型表示内存池的内存使用量大于或等于其
     * <a href="MemoryPoolMXBean.html#CollectionThreshold">
     *  集合使用阈值</a>,在Java虚拟机耗尽了回收该内存池中未使用的对象的努力。此通知由{@link MemoryMXBean}发出。
     * 此通知类型的值为<tt> java.management.memory.collection.threshold.exceeded </tt>。
     * 
     */
    public static final String MEMORY_COLLECTION_THRESHOLD_EXCEEDED =
        "java.management.memory.collection.threshold.exceeded";

    /**
     * Constructs a <tt>MemoryNotificationInfo</tt> object.
     *
     * <p>
     *  构造一个<tt> MemoryNotificationInfo </tt>对象。
     * 
     * 
     * @param poolName The name of the memory pool which triggers this notification.
     * @param usage Memory usage of the memory pool.
     * @param count The threshold crossing count.
     */
    public MemoryNotificationInfo(String poolName,
                                  MemoryUsage usage,
                                  long count) {
        if (poolName == null) {
            throw new NullPointerException("Null poolName");
        }
        if (usage == null) {
            throw new NullPointerException("Null usage");
        }

        this.poolName = poolName;
        this.usage = usage;
        this.count = count;
    }

    MemoryNotificationInfo(CompositeData cd) {
        MemoryNotifInfoCompositeData.validateCompositeData(cd);

        this.poolName = MemoryNotifInfoCompositeData.getPoolName(cd);
        this.usage = MemoryNotifInfoCompositeData.getUsage(cd);
        this.count = MemoryNotifInfoCompositeData.getCount(cd);
    }

    /**
     * Returns the name of the memory pool that triggers this notification.
     * The memory pool usage has crossed a threshold.
     *
     * <p>
     *  返回触发此通知的内存池的名称。内存池使用率已超过阈值。
     * 
     * 
     * @return the name of the memory pool that triggers this notification.
     */
    public String getPoolName() {
        return poolName;
    }

    /**
     * Returns the memory usage of the memory pool
     * when this notification was constructed.
     *
     * <p>
     *  返回构建此通知时内存池的内存使用情况。
     * 
     * 
     * @return the memory usage of the memory pool
     * when this notification was constructed.
     */
    public MemoryUsage getUsage() {
        return usage;
    }

    /**
     * Returns the number of times that the memory usage has crossed
     * a threshold when the notification was constructed.
     * For usage threshold notifications, this count will be the
     * {@link MemoryPoolMXBean#getUsageThresholdCount threshold
     * count}.  For collection threshold notifications,
     * this count will be the
     * {@link MemoryPoolMXBean#getCollectionUsageThresholdCount
     * collection usage threshold count}.
     *
     * <p>
     * 返回构建通知时内存使用量超过阈值的次数。对于使用阈值通知,此计数将为{@link MemoryPoolMXBean#getUsageThresholdCount阈值计数}。
     * 对于收集阈值通知,此计数将为{@link MemoryPoolMXBean#getCollectionUsageThresholdCount集合使用阈值计数}。
     * 
     * 
     * @return the number of times that the memory usage has crossed
     * a threshold when the notification was constructed.
     */
    public long getCount() {
        return count;
    }

    /**
     * Returns a <tt>MemoryNotificationInfo</tt> object represented by the
     * given <tt>CompositeData</tt>.
     * The given <tt>CompositeData</tt> must contain
     * the following attributes:
     * <blockquote>
     * <table border summary="The attributes and the types the given CompositeData contains">
     * <tr>
     *   <th align=left>Attribute Name</th>
     *   <th align=left>Type</th>
     * </tr>
     * <tr>
     *   <td>poolName</td>
     *   <td><tt>java.lang.String</tt></td>
     * </tr>
     * <tr>
     *   <td>usage</td>
     *   <td><tt>javax.management.openmbean.CompositeData</tt></td>
     * </tr>
     * <tr>
     *   <td>count</td>
     *   <td><tt>java.lang.Long</tt></td>
     * </tr>
     * </table>
     * </blockquote>
     *
     * <p>
     *  返回由给定的<tt> CompositeData </tt>表示的<tt> MemoryNotificationInfo </tt>对象。
     * 给定的<tt> CompositeData </tt>必须包含以下属性：。
     * <blockquote>
     * <table border summary="The attributes and the types the given CompositeData contains">
     * <tr>
     *  <th align = left>属性名称</th> <th align = left>键入</th>
     * </tr>
     * <tr>
     *  <td> poolName </td> <td> <tt> java.lang.String </tt> </td>
     * </tr>
     * 
     * @param cd <tt>CompositeData</tt> representing a
     *           <tt>MemoryNotificationInfo</tt>
     *
     * @throws IllegalArgumentException if <tt>cd</tt> does not
     *   represent a <tt>MemoryNotificationInfo</tt> object.
     *
     * @return a <tt>MemoryNotificationInfo</tt> object represented
     *         by <tt>cd</tt> if <tt>cd</tt> is not <tt>null</tt>;
     *         <tt>null</tt> otherwise.
     */
    public static MemoryNotificationInfo from(CompositeData cd) {
        if (cd == null) {
            return null;
        }

        if (cd instanceof MemoryNotifInfoCompositeData) {
            return ((MemoryNotifInfoCompositeData) cd).getMemoryNotifInfo();
        } else {
            return new MemoryNotificationInfo(cd);
        }
    }
}
