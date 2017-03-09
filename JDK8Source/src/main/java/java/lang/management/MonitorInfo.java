/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
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
import sun.management.MonitorInfoCompositeData;

/**
 * Information about an object monitor lock.  An object monitor is locked
 * when entering a synchronization block or method on that object.
 *
 * <h3>MXBean Mapping</h3>
 * <tt>MonitorInfo</tt> is mapped to a {@link CompositeData CompositeData}
 * with attributes as specified in
 * the {@link #from from} method.
 *
 * <p>
 *  有关对象监视器锁的信息。当在该对象上输入同步块或方法时,对象监视器将被锁定。
 * 
 *  <h3> MXBean映射</h3> <tt> MonitorInfo </tt>映射到具有{@link #from from}方法中指定的属性的{@link CompositeData CompositeData}
 * 。
 * 
 * 
 * @author  Mandy Chung
 * @since   1.6
 */
public class MonitorInfo extends LockInfo {

    private int    stackDepth;
    private StackTraceElement stackFrame;

    /**
     * Construct a <tt>MonitorInfo</tt> object.
     *
     * <p>
     *  构造<tt> MonitorInfo </tt>对象。
     * 
     * 
     * @param className the fully qualified name of the class of the lock object.
     * @param identityHashCode the {@link System#identityHashCode
     *                         identity hash code} of the lock object.
     * @param stackDepth the depth in the stack trace where the object monitor
     *                   was locked.
     * @param stackFrame the stack frame that locked the object monitor.
     * @throws IllegalArgumentException if
     *    <tt>stackDepth</tt> &ge; 0 but <tt>stackFrame</tt> is <tt>null</tt>,
     *    or <tt>stackDepth</tt> &lt; 0 but <tt>stackFrame</tt> is not
     *       <tt>null</tt>.
     */
    public MonitorInfo(String className,
                       int identityHashCode,
                       int stackDepth,
                       StackTraceElement stackFrame) {
        super(className, identityHashCode);
        if (stackDepth >= 0 && stackFrame == null) {
            throw new IllegalArgumentException("Parameter stackDepth is " +
                stackDepth + " but stackFrame is null");
        }
        if (stackDepth < 0 && stackFrame != null) {
            throw new IllegalArgumentException("Parameter stackDepth is " +
                stackDepth + " but stackFrame is not null");
        }
        this.stackDepth = stackDepth;
        this.stackFrame = stackFrame;
    }

    /**
     * Returns the depth in the stack trace where the object monitor
     * was locked.  The depth is the index to the <tt>StackTraceElement</tt>
     * array returned in the {@link ThreadInfo#getStackTrace} method.
     *
     * <p>
     *  返回对象监视器锁定的堆栈跟踪中的深度。深度是{@link ThreadInfo#getStackTrace}方法中返回的<tt> StackTraceElement </tt>数组的索引。
     * 
     * 
     * @return the depth in the stack trace where the object monitor
     *         was locked, or a negative number if not available.
     */
    public int getLockedStackDepth() {
        return stackDepth;
    }

    /**
     * Returns the stack frame that locked the object monitor.
     *
     * <p>
     *  返回锁定对象监视器的堆栈框架。
     * 
     * 
     * @return <tt>StackTraceElement</tt> that locked the object monitor,
     *         or <tt>null</tt> if not available.
     */
    public StackTraceElement getLockedStackFrame() {
        return stackFrame;
    }

    /**
     * Returns a <tt>MonitorInfo</tt> object represented by the
     * given <tt>CompositeData</tt>.
     * The given <tt>CompositeData</tt> must contain the following attributes
     * as well as the attributes specified in the
     * <a href="LockInfo.html#MappedType">
     * mapped type</a> for the {@link LockInfo} class:
     * <blockquote>
     * <table border summary="The attributes and their types the given CompositeData contains">
     * <tr>
     *   <th align=left>Attribute Name</th>
     *   <th align=left>Type</th>
     * </tr>
     * <tr>
     *   <td>lockedStackFrame</td>
     *   <td><tt>CompositeData as specified in the
     *       <a href="ThreadInfo.html#StackTrace">stackTrace</a>
     *       attribute defined in the {@link ThreadInfo#from
     *       ThreadInfo.from} method.
     *       </tt></td>
     * </tr>
     * <tr>
     *   <td>lockedStackDepth</td>
     *   <td><tt>java.lang.Integer</tt></td>
     * </tr>
     * </table>
     * </blockquote>
     *
     * <p>
     *  返回由给定的<tt> CompositeData </tt>表示的<tt> MonitorInfo </tt>对象。
     * 给定的<tt> CompositeData </tt>必须包含以下属性以及在中指定的属性。
     * <a href="LockInfo.html#MappedType">
     *  映射类型</a> {@link LockInfo}类：
     * <blockquote>
     * <table border summary="The attributes and their types the given CompositeData contains">
     * <tr>
     *  <th align = left>属性名称</th> <th align = left>键入</th>
     * </tr>
     * <tr>
     * 
     * @param cd <tt>CompositeData</tt> representing a <tt>MonitorInfo</tt>
     *
     * @throws IllegalArgumentException if <tt>cd</tt> does not
     *   represent a <tt>MonitorInfo</tt> with the attributes described
     *   above.

     * @return a <tt>MonitorInfo</tt> object represented
     *         by <tt>cd</tt> if <tt>cd</tt> is not <tt>null</tt>;
     *         <tt>null</tt> otherwise.
     */
    public static MonitorInfo from(CompositeData cd) {
        if (cd == null) {
            return null;
        }

        if (cd instanceof MonitorInfoCompositeData) {
            return ((MonitorInfoCompositeData) cd).getMonitorInfo();
        } else {
            MonitorInfoCompositeData.validateCompositeData(cd);
            String className = MonitorInfoCompositeData.getClassName(cd);
            int identityHashCode = MonitorInfoCompositeData.getIdentityHashCode(cd);
            int stackDepth = MonitorInfoCompositeData.getLockedStackDepth(cd);
            StackTraceElement stackFrame = MonitorInfoCompositeData.getLockedStackFrame(cd);
            return new MonitorInfo(className,
                                   identityHashCode,
                                   stackDepth,
                                   stackFrame);
        }
    }

}
