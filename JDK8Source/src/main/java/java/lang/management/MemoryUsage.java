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
import sun.management.MemoryUsageCompositeData;

/**
 * A <tt>MemoryUsage</tt> object represents a snapshot of memory usage.
 * Instances of the <tt>MemoryUsage</tt> class are usually constructed
 * by methods that are used to obtain memory usage
 * information about individual memory pool of the Java virtual machine or
 * the heap or non-heap memory of the Java virtual machine as a whole.
 *
 * <p> A <tt>MemoryUsage</tt> object contains four values:
 * <table summary="Describes the MemoryUsage object content">
 * <tr>
 * <td valign=top> <tt>init</tt> </td>
 * <td valign=top> represents the initial amount of memory (in bytes) that
 *      the Java virtual machine requests from the operating system
 *      for memory management during startup.  The Java virtual machine
 *      may request additional memory from the operating system and
 *      may also release memory to the system over time.
 *      The value of <tt>init</tt> may be undefined.
 * </td>
 * </tr>
 * <tr>
 * <td valign=top> <tt>used</tt> </td>
 * <td valign=top> represents the amount of memory currently used (in bytes).
 * </td>
 * </tr>
 * <tr>
 * <td valign=top> <tt>committed</tt> </td>
 * <td valign=top> represents the amount of memory (in bytes) that is
 *      guaranteed to be available for use by the Java virtual machine.
 *      The amount of committed memory may change over time (increase
 *      or decrease).  The Java virtual machine may release memory to
 *      the system and <tt>committed</tt> could be less than <tt>init</tt>.
 *      <tt>committed</tt> will always be greater than
 *      or equal to <tt>used</tt>.
 * </td>
 * </tr>
 * <tr>
 * <td valign=top> <tt>max</tt> </td>
 * <td valign=top> represents the maximum amount of memory (in bytes)
 *      that can be used for memory management. Its value may be undefined.
 *      The maximum amount of memory may change over time if defined.
 *      The amount of used and committed memory will always be less than
 *      or equal to <tt>max</tt> if <tt>max</tt> is defined.
 *      A memory allocation may fail if it attempts to increase the
 *      used memory such that <tt>used &gt; committed</tt> even
 *      if <tt>used &lt;= max</tt> would still be true (for example,
 *      when the system is low on virtual memory).
 * </td>
 * </tr>
 * </table>
 *
 * Below is a picture showing an example of a memory pool:
 *
 * <pre>
 *        +----------------------------------------------+
 *        +////////////////           |                  +
 *        +////////////////           |                  +
 *        +----------------------------------------------+
 *
 *        |--------|
 *           init
 *        |---------------|
 *               used
 *        |---------------------------|
 *                  committed
 *        |----------------------------------------------|
 *                            max
 * </pre>
 *
 * <h3>MXBean Mapping</h3>
 * <tt>MemoryUsage</tt> is mapped to a {@link CompositeData CompositeData}
 * with attributes as specified in the {@link #from from} method.
 *
 * <p>
 *  <tt> MemoryUsage </tt>对象表示内存使用情况的快照。
 *  <tt> MemoryUsage </tt>类的实例通常由用于获取有关Java虚拟机的单个内存池或整个Java虚拟机的堆或非堆内存的内存使用信息的方法构造。
 * 
 *  <p> A <tt> MemoryUsage </tt>对象包含四个值：
 * <table summary="Describes the MemoryUsage object content">
 * <tr>
 *  <td valign = top> <tt> init </tt> </td> <td valign = top>表示Java虚拟机在启动期间从操作系统请求内存管理的初始内存量(以字节为单位)。
 *  Java虚拟机可以从操作系统请求额外的存储器,并且还可以随着时间释放对系统的存储器。 <tt> init </tt>的值可能未定义。
 * </td>
 * </tr>
 * <tr>
 *  <td valign = top> <tt> used </tt> </td> <td valign = top>表示当前使用的内存量(以字节为单位)。
 * </td>
 * </tr>
 * <tr>
 *  <td valign = top> <tt>提交</tt> </td> <td valign = top>表示保证可供Java虚拟机使用的内存量(以字节为单位)。
 * 提交的内存量可能随时间(增加或减少)而改变。 Java虚拟机可以向系统释放内存,并且<tt>提交</tt>可以小于<tt> init </tt>。
 *  <tt>提交</tt>将始终大于或等于<tt> used </tt>。
 * </td>
 * </tr>
 * <tr>
 * <td valign = top> <tt> max </tt> </td> <td valign = top>表示可用于内存管理的最大内存量(以字节为单位)。其值可能未定义。
 * 如果已定义,最大内存量可能会随时间改变。如果定义<tt> max </tt>,则已使用和已提交内存的总数将始终小于或等于<tt> max </tt>。
 * 如果存储器分配试图增加所使用的存储器,使得<tt> used>提交</tt>,即使<tt> used&lt; = max </tt>仍然为真(例如,当系统在虚拟内存上为低时)。
 * </td>
 * </tr>
 * </table>
 * 
 *  下面是一个显示内存池示例的图片：
 * 
 * <pre>
 *  + ---------------------------------------------- + + / /////////////// | + + //////////////// | + + 
 * ---------------------------------------------- +。
 * 
 *  | -------- | init | --------------- |使用| --------------------------- |承诺| --------------------------
 * -------------------- |最大。
 * </pre>
 * 
 *  <h3> MXBean映射</h3> <tt> MemoryUsage </tt>映射到具有{@link #from from}方法中指定的属性的{@link CompositeData CompositeData}
 * 。
 * 
 * 
 * @author   Mandy Chung
 * @since   1.5
 */
public class MemoryUsage {
    private final long init;
    private final long used;
    private final long committed;
    private final long max;

    /**
     * Constructs a <tt>MemoryUsage</tt> object.
     *
     * <p>
     *  构造<tt> MemoryUsage </tt>对象。
     * 
     * 
     * @param init      the initial amount of memory in bytes that
     *                  the Java virtual machine allocates;
     *                  or <tt>-1</tt> if undefined.
     * @param used      the amount of used memory in bytes.
     * @param committed the amount of committed memory in bytes.
     * @param max       the maximum amount of memory in bytes that
     *                  can be used; or <tt>-1</tt> if undefined.
     *
     * @throws IllegalArgumentException if
     * <ul>
     * <li> the value of <tt>init</tt> or <tt>max</tt> is negative
     *      but not <tt>-1</tt>; or</li>
     * <li> the value of <tt>used</tt> or <tt>committed</tt> is negative;
     *      or</li>
     * <li> <tt>used</tt> is greater than the value of <tt>committed</tt>;
     *      or</li>
     * <li> <tt>committed</tt> is greater than the value of <tt>max</tt>
     *      <tt>max</tt> if defined.</li>
     * </ul>
     */
    public MemoryUsage(long init,
                       long used,
                       long committed,
                       long max) {
        if (init < -1) {
            throw new IllegalArgumentException( "init parameter = " +
                init + " is negative but not -1.");
        }
        if (max < -1) {
            throw new IllegalArgumentException( "max parameter = " +
                max + " is negative but not -1.");
        }
        if (used < 0) {
            throw new IllegalArgumentException( "used parameter = " +
                used + " is negative.");
        }
        if (committed < 0) {
            throw new IllegalArgumentException( "committed parameter = " +
                committed + " is negative.");
        }
        if (used > committed) {
            throw new IllegalArgumentException( "used = " + used +
                " should be <= committed = " + committed);
        }
        if (max >= 0 && committed > max) {
            throw new IllegalArgumentException( "committed = " + committed +
                " should be < max = " + max);
        }

        this.init = init;
        this.used = used;
        this.committed = committed;
        this.max = max;
    }

    /**
     * Constructs a <tt>MemoryUsage</tt> object from a
     * {@link CompositeData CompositeData}.
     * <p>
     *  从{@link CompositeData CompositeData}构造一个<tt> MemoryUsage </tt>对象。
     * 
     */
    private MemoryUsage(CompositeData cd) {
        // validate the input composite data
        MemoryUsageCompositeData.validateCompositeData(cd);

        this.init = MemoryUsageCompositeData.getInit(cd);
        this.used = MemoryUsageCompositeData.getUsed(cd);
        this.committed = MemoryUsageCompositeData.getCommitted(cd);
        this.max = MemoryUsageCompositeData.getMax(cd);
    }

    /**
     * Returns the amount of memory in bytes that the Java virtual machine
     * initially requests from the operating system for memory management.
     * This method returns <tt>-1</tt> if the initial memory size is undefined.
     *
     * <p>
     *  返回Java虚拟机最初请求操作系统进行内存管理的内存量(以字节为单位)。如果初始内存大小未定义,此方法返回<tt> -1 </tt>。
     * 
     * 
     * @return the initial size of memory in bytes;
     * <tt>-1</tt> if undefined.
     */
    public long getInit() {
        return init;
    }

    /**
     * Returns the amount of used memory in bytes.
     *
     * <p>
     * 返回使用的内存量(以字节为单位)。
     * 
     * 
     * @return the amount of used memory in bytes.
     *
     */
    public long getUsed() {
        return used;
    };

    /**
     * Returns the amount of memory in bytes that is committed for
     * the Java virtual machine to use.  This amount of memory is
     * guaranteed for the Java virtual machine to use.
     *
     * <p>
     *  返回为Java虚拟机提交以供使用的内存量(以字节为单位)。这个内存量保证供Java虚拟机使用。
     * 
     * 
     * @return the amount of committed memory in bytes.
     *
     */
    public long getCommitted() {
        return committed;
    };

    /**
     * Returns the maximum amount of memory in bytes that can be
     * used for memory management.  This method returns <tt>-1</tt>
     * if the maximum memory size is undefined.
     *
     * <p> This amount of memory is not guaranteed to be available
     * for memory management if it is greater than the amount of
     * committed memory.  The Java virtual machine may fail to allocate
     * memory even if the amount of used memory does not exceed this
     * maximum size.
     *
     * <p>
     *  返回可用于内存管理的最大内存量(以字节为单位)。如果最大内存大小未定义,此方法返回<tt> -1 </tt>。
     * 
     *  <p>如果内存量大于提交的内存量,则无法保证此内存量可用于内存管理。即使已使用的内存量未超过此最大大小,Java虚拟机也可能无法分配内存。
     * 
     * 
     * @return the maximum amount of memory in bytes;
     * <tt>-1</tt> if undefined.
     */
    public long getMax() {
        return max;
    };

    /**
     * Returns a descriptive representation of this memory usage.
     * <p>
     *  返回此内存使用情况的描述性表示。
     * 
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("init = " + init + "(" + (init >> 10) + "K) ");
        buf.append("used = " + used + "(" + (used >> 10) + "K) ");
        buf.append("committed = " + committed + "(" +
                   (committed >> 10) + "K) " );
        buf.append("max = " + max + "(" + (max >> 10) + "K)");
        return buf.toString();
    }

    /**
     * Returns a <tt>MemoryUsage</tt> object represented by the
     * given <tt>CompositeData</tt>. The given <tt>CompositeData</tt>
     * must contain the following attributes:
     *
     * <blockquote>
     * <table border summary="The attributes and the types the given CompositeData contains">
     * <tr>
     *   <th align=left>Attribute Name</th>
     *   <th align=left>Type</th>
     * </tr>
     * <tr>
     *   <td>init</td>
     *   <td><tt>java.lang.Long</tt></td>
     * </tr>
     * <tr>
     *   <td>used</td>
     *   <td><tt>java.lang.Long</tt></td>
     * </tr>
     * <tr>
     *   <td>committed</td>
     *   <td><tt>java.lang.Long</tt></td>
     * </tr>
     * <tr>
     *   <td>max</td>
     *   <td><tt>java.lang.Long</tt></td>
     * </tr>
     * </table>
     * </blockquote>
     *
     * <p>
     *  返回由给定的<tt> CompositeData </tt>表示的<tt> MemoryUsage </tt>对象。给定的<tt> CompositeData </tt>必须包含以下属性：
     * 
     * <blockquote>
     * <table border summary="The attributes and the types the given CompositeData contains">
     * <tr>
     *  <th align = left>属性名称</th> <th align = left>键入</th>
     * </tr>
     * <tr>
     *  <td> init </td> <td> <tt> java.lang.Long </tt> </td>
     * </tr>
     * <tr>
     * 
     * @param cd <tt>CompositeData</tt> representing a <tt>MemoryUsage</tt>
     *
     * @throws IllegalArgumentException if <tt>cd</tt> does not
     *   represent a <tt>MemoryUsage</tt> with the attributes described
     *   above.
     *
     * @return a <tt>MemoryUsage</tt> object represented by <tt>cd</tt>
     *         if <tt>cd</tt> is not <tt>null</tt>;
     *         <tt>null</tt> otherwise.
     */
    public static MemoryUsage from(CompositeData cd) {
        if (cd == null) {
            return null;
        }

        if (cd instanceof MemoryUsageCompositeData) {
            return ((MemoryUsageCompositeData) cd).getMemoryUsage();
        } else {
            return new MemoryUsage(cd);
        }

    }
}
