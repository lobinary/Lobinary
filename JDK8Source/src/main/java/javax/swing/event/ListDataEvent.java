/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.swing.event;

import java.util.EventObject;


/**
 * Defines an event that encapsulates changes to a list.
 * <p>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running
 * the same version of Swing.  As of 1.4, support for long term storage
 * of all JavaBeans&trade;
 * has been added to the <code>java.beans</code> package.
 * Please see {@link java.beans.XMLEncoder}.
 *
 * <p>
 *  定义将更改封装到列表的事件。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author Hans Muller
 */
@SuppressWarnings("serial")
public class ListDataEvent extends EventObject
{
    /** Identifies one or more changes in the lists contents. */
    public static final int CONTENTS_CHANGED = 0;
    /** Identifies the addition of one or more contiguous items to the list */
    public static final int INTERVAL_ADDED = 1;
    /** Identifies the removal of one or more contiguous items from the list */
    public static final int INTERVAL_REMOVED = 2;

    private int type;
    private int index0;
    private int index1;

    /**
     * Returns the event type. The possible values are:
     * <ul>
     * <li> {@link #CONTENTS_CHANGED}
     * <li> {@link #INTERVAL_ADDED}
     * <li> {@link #INTERVAL_REMOVED}
     * </ul>
     *
     * <p>
     *  返回事件类型。可能的值为：
     * <ul>
     *  <li> {@link #CONTENTS_CHANGED} <li> {@link #INTERVAL_ADDED} <li> {@link #INTERVAL_REMOVED}
     * </ul>
     * 
     * 
     * @return an int representing the type value
     */
    public int getType() { return type; }

    /**
     * Returns the lower index of the range. For a single
     * element, this value is the same as that returned by {@link #getIndex1}.

     *
     * <p>
     *  返回范围的较低索引。对于单个元素,此值与{@link#getIndex1}返回的值相同。
     * 
     * 
     * @return an int representing the lower index value
     */
    public int getIndex0() { return index0; }
    /**
     * Returns the upper index of the range. For a single
     * element, this value is the same as that returned by {@link #getIndex0}.
     *
     * <p>
     *  返回范围的上限索引。对于单个元素,此值与{@link#getIndex0}返回的值相同。
     * 
     * 
     * @return an int representing the upper index value
     */
    public int getIndex1() { return index1; }

    /**
     * Constructs a ListDataEvent object. If index0 is &gt;
     * index1, index0 and index1 will be swapped such that
     * index0 will always be &lt;= index1.
     *
     * <p>
     *  构造一个ListDataEvent对象。如果index0> index1,index0和index1将被交换,使得index0将始终为&lt; = index1。
     * 
     * 
     * @param source  the source Object (typically <code>this</code>)
     * @param type    an int specifying {@link #CONTENTS_CHANGED},
     *                {@link #INTERVAL_ADDED}, or {@link #INTERVAL_REMOVED}
     * @param index0  one end of the new interval
     * @param index1  the other end of the new interval
     */
    public ListDataEvent(Object source, int type, int index0, int index1) {
        super(source);
        this.type = type;
        this.index0 = Math.min(index0, index1);
        this.index1 = Math.max(index0, index1);
    }

    /**
     * Returns a string representation of this ListDataEvent. This method
     * is intended to be used only for debugging purposes, and the
     * content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not
     * be <code>null</code>.
     *
     * <p>
     *  返回此ListDataEvent的字符串表示形式。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * @since 1.4
     * @return  a string representation of this ListDataEvent.
     */
    public String toString() {
        return getClass().getName() +
        "[type=" + type +
        ",index0=" + index0 +
        ",index1=" + index1 + "]";
    }
}
