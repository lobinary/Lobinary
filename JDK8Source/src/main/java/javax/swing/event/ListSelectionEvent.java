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
import javax.swing.*;


/**
 * An event that characterizes a change in selection. The change is limited to a
 * a single inclusive interval. The selection of at least one index within the
 * range will have changed. A decent {@code ListSelectionModel} implementation
 * will keep the range as small as possible. {@code ListSelectionListeners} will
 * generally query the source of the event for the new selected status of each
 * potentially changed row.
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
 *  表征选择变化的事件。该更改被限制为单个包含区间。该范围内至少一个索引的选择将会改变。一个体面的{@code ListSelectionModel}实现将保持尽可能小的范围。
 *  {@code ListSelectionListeners}通常会查询每个潜在更改行的新选定状态的事件来源。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author Hans Muller
 * @author Ray Ryan
 * @see ListSelectionModel
 */
public class ListSelectionEvent extends EventObject
{
    private int firstIndex;
    private int lastIndex;
    private boolean isAdjusting;

    /**
     * Represents a change in selection status between {@code firstIndex} and
     * {@code lastIndex}, inclusive. {@code firstIndex} is less than or equal to
     * {@code lastIndex}. The selection of at least one index within the range will
     * have changed.
     *
     * <p>
     *  表示{@code firstIndex}和{@code lastIndex}(含)之间的选择状态更改。 {@code firstIndex}小于或等于{@code lastIndex}。
     * 该范围内至少一个索引的选择将会改变。
     * 
     * 
     * @param firstIndex the first index in the range, &lt;= lastIndex
     * @param lastIndex the last index in the range, &gt;= firstIndex
     * @param isAdjusting whether or not this is one in a series of
     *        multiple events, where changes are still being made
     */
    public ListSelectionEvent(Object source, int firstIndex, int lastIndex,
                              boolean isAdjusting)
    {
        super(source);
        this.firstIndex = firstIndex;
        this.lastIndex = lastIndex;
        this.isAdjusting = isAdjusting;
    }

    /**
     * Returns the index of the first row whose selection may have changed.
     * {@code getFirstIndex() &lt;= getLastIndex()}
     *
     * <p>
     *  返回其选择可能已更改的第一行的索引。 {@code getFirstIndex()&lt; = getLastIndex()}
     * 
     * 
     * @return the first row whose selection value may have changed,
     *         where zero is the first row
     */
    public int getFirstIndex() { return firstIndex; }

    /**
     * Returns the index of the last row whose selection may have changed.
     * {@code getLastIndex() &gt;= getFirstIndex()}
     *
     * <p>
     *  返回其选择可能已更改的最后一行的索引。 {@code getLastIndex()&gt; = getFirstIndex()}
     * 
     * 
     * @return the last row whose selection value may have changed,
     *         where zero is the first row
     */
    public int getLastIndex() { return lastIndex; }

    /**
     * Returns whether or not this is one in a series of multiple events,
     * where changes are still being made. See the documentation for
     * {@link javax.swing.ListSelectionModel#setValueIsAdjusting} for
     * more details on how this is used.
     *
     * <p>
     * 返回这是否是一系列多个事件中的一个,其中仍在进行更改。
     * 有关如何使用它的更多详细信息,请参阅{@link javax.swing.ListSelectionModel#setValueIsAdjusting}的文档。
     * 
     * 
     * @return {@code true} if this is one in a series of multiple events,
     *         where changes are still being made
     */
    public boolean getValueIsAdjusting() { return isAdjusting; }

    /**
     * Returns a {@code String} that displays and identifies this
     * object's properties.
     *
     * <p>
     * 
     * @return a String representation of this object
     */
    public String toString() {
        String properties =
            " source=" + getSource() +
            " firstIndex= " + firstIndex +
            " lastIndex= " + lastIndex +
            " isAdjusting= " + isAdjusting +
            " ";
        return getClass().getName() + "[" + properties + "]";
    }
}
