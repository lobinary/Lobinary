/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2006, Oracle and/or its affiliates. All rights reserved.
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

package javax.swing;

import javax.swing.event.*;

/**
 * This interface represents the current state of the
 * selection for any of the components that display a
 * list of values with stable indices.  The selection is
 * modeled as a set of intervals, each interval represents
 * a contiguous range of selected list elements.
 * The methods for modifying the set of selected intervals
 * all take a pair of indices, index0 and index1, that represent
 * a closed interval, i.e. the interval includes both index0 and
 * index1.
 *
 * <p>
 *  此接口表示显示具有稳定索引的值列表的任何组件的选择的当前状态。选择被建模为一组间隔,每个间隔表示所选列表元素的连续范围。
 * 用于修改所选间隔集合的方法都采用表示闭合间隔的一对索引index0和index1,即,间隔包括index0和index1。
 * 
 * 
 * @author Hans Muller
 * @author Philip Milne
 * @see DefaultListSelectionModel
 */

public interface ListSelectionModel
{
    /**
     * A value for the selectionMode property: select one list index
     * at a time.
     *
     * <p>
     *  selectionMode属性的值：一次选择一个列表索引。
     * 
     * 
     * @see #setSelectionMode
     */
    int SINGLE_SELECTION = 0;

    /**
     * A value for the selectionMode property: select one contiguous
     * range of indices at a time.
     *
     * <p>
     *  selectionMode属性的值：一次选择一个连续的索引范围。
     * 
     * 
     * @see #setSelectionMode
     */
    int SINGLE_INTERVAL_SELECTION = 1;

    /**
     * A value for the selectionMode property: select one or more
     * contiguous ranges of indices at a time.
     *
     * <p>
     *  selectionMode属性的值：一次选择一个或多个连续的索引范围。
     * 
     * 
     * @see #setSelectionMode
     */
    int MULTIPLE_INTERVAL_SELECTION = 2;


    /**
     * Changes the selection to be between {@code index0} and {@code index1}
     * inclusive. {@code index0} doesn't have to be less than or equal to
     * {@code index1}.
     * <p>
     * In {@code SINGLE_SELECTION} selection mode, only the second index
     * is used.
     * <p>
     * If this represents a change to the current selection, then each
     * {@code ListSelectionListener} is notified of the change.
     *
     * <p>
     *  将选择更改为{@code index0}和{@code index1}(含)之间。 {@code index0}不必小于或等于{@code index1}。
     * <p>
     *  在{@code SINGLE_SELECTION}选择模式中,仅使用第二个索引。
     * <p>
     *  如果这代表对当前选择的更改,则会向每个{@code ListSelectionListener}通知更改。
     * 
     * 
     * @param index0 one end of the interval.
     * @param index1 other end of the interval
     * @see #addListSelectionListener
     */
    void setSelectionInterval(int index0, int index1);


    /**
     * Changes the selection to be the set union of the current selection
     * and the indices between {@code index0} and {@code index1} inclusive.
     * {@code index0} doesn't have to be less than or equal to {@code index1}.
     * <p>
     * In {@code SINGLE_SELECTION} selection mode, this is equivalent
     * to calling {@code setSelectionInterval}, and only the second index
     * is used. In {@code SINGLE_INTERVAL_SELECTION} selection mode, this
     * method behaves like {@code setSelectionInterval}, unless the given
     * interval is immediately adjacent to or overlaps the existing selection,
     * and can therefore be used to grow the selection.
     * <p>
     * If this represents a change to the current selection, then each
     * {@code ListSelectionListener} is notified of the change.
     *
     * <p>
     *  将选择更改为{@code index0}和{@code index1}(含)之间的当前选择和索引的集合并集。 {@code index0}不必小于或等于{@code index1}。
     * <p>
     * 在{@code SINGLE_SELECTION}选择模式下,这相当于调用{@code setSelectionInterval},并且仅使用第二个索引。
     * 在{@code SINGLE_INTERVAL_SELECTION}选择模式中,此方法的行为类似于{@code setSelectionInterval},除非给定的间隔与现有选择直接相邻或重叠,因此可
     * 用于增大选择。
     * 在{@code SINGLE_SELECTION}选择模式下,这相当于调用{@code setSelectionInterval},并且仅使用第二个索引。
     * <p>
     *  如果这代表对当前选择的更改,则会向每个{@code ListSelectionListener}通知更改。
     * 
     * 
     * @param index0 one end of the interval.
     * @param index1 other end of the interval
     * @see #addListSelectionListener
     * @see #setSelectionInterval
     */
    void addSelectionInterval(int index0, int index1);


    /**
     * Changes the selection to be the set difference of the current selection
     * and the indices between {@code index0} and {@code index1} inclusive.
     * {@code index0} doesn't have to be less than or equal to {@code index1}.
     * <p>
     * In {@code SINGLE_INTERVAL_SELECTION} selection mode, if the removal
     * would produce two disjoint selections, the removal is extended through
     * the greater end of the selection. For example, if the selection is
     * {@code 0-10} and you supply indices {@code 5,6} (in any order) the
     * resulting selection is {@code 0-4}.
     * <p>
     * If this represents a change to the current selection, then each
     * {@code ListSelectionListener} is notified of the change.
     *
     * <p>
     *  将选择更改为当前选择和{@code index0}和{@code index1}(含)之间的索引的设置差异。 {@code index0}不必小于或等于{@code index1}。
     * <p>
     *  在{@code SINGLE_INTERVAL_SELECTION}选择模式中,如果移除会产生两个不相交的选择,则移除会延伸到选择的较大一端。
     * 例如,如果选择是{@code 0-10},并且提供索引{@code 5,6}(以任何顺序),结果选择是{@code 0-4}。
     * <p>
     *  如果这代表对当前选择的更改,则会向每个{@code ListSelectionListener}通知更改。
     * 
     * 
     * @param index0 one end of the interval.
     * @param index1 other end of the interval
     * @see #addListSelectionListener
     */
    void removeSelectionInterval(int index0, int index1);


    /**
     * Returns the first selected index or -1 if the selection is empty.
     * <p>
     *  返回第一个选定的索引,如果选择为空,则返回-1。
     * 
     */
    int getMinSelectionIndex();


    /**
     * Returns the last selected index or -1 if the selection is empty.
     * <p>
     *  返回最后选择的索引,如果选择为空,则返回-1。
     * 
     */
    int getMaxSelectionIndex();


    /**
     * Returns true if the specified index is selected.
     * <p>
     *  如果选择了指定的索引,则返回true。
     * 
     */
    boolean isSelectedIndex(int index);


    /**
     * Return the first index argument from the most recent call to
     * setSelectionInterval(), addSelectionInterval() or removeSelectionInterval().
     * The most recent index0 is considered the "anchor" and the most recent
     * index1 is considered the "lead".  Some interfaces display these
     * indices specially, e.g. Windows95 displays the lead index with a
     * dotted yellow outline.
     *
     * <p>
     * 将最近一次调用的第一个索引参数返回到setSelectionInterval(),addSelectionInterval()或removeSelectionInterval()。
     * 最近的index0被认为是"锚",最近的index1被认为是"lead"。一些接口特别显示这些索引,例如。 Windows95显示带有黄色虚线轮廓的销售线索索引。
     * 
     * 
     * @see #getLeadSelectionIndex
     * @see #setSelectionInterval
     * @see #addSelectionInterval
     */
    int getAnchorSelectionIndex();


    /**
     * Set the anchor selection index.
     *
     * <p>
     *  设置锚选择索引。
     * 
     * 
     * @see #getAnchorSelectionIndex
     */
    void setAnchorSelectionIndex(int index);


    /**
     * Return the second index argument from the most recent call to
     * setSelectionInterval(), addSelectionInterval() or removeSelectionInterval().
     *
     * <p>
     *  将最近一次调用的第二个索引参数返回到setSelectionInterval(),addSelectionInterval()或removeSelectionInterval()。
     * 
     * 
     * @see #getAnchorSelectionIndex
     * @see #setSelectionInterval
     * @see #addSelectionInterval
     */
    int getLeadSelectionIndex();

    /**
     * Set the lead selection index.
     *
     * <p>
     *  设置潜在客户选择指数。
     * 
     * 
     * @see #getLeadSelectionIndex
     */
    void setLeadSelectionIndex(int index);

    /**
     * Change the selection to the empty set.  If this represents
     * a change to the current selection then notify each ListSelectionListener.
     *
     * <p>
     *  将选择更改为空集。如果这表示对当前选择的更改,则通知每个ListSelectionListener。
     * 
     * 
     * @see #addListSelectionListener
     */
    void clearSelection();

    /**
     * Returns true if no indices are selected.
     * <p>
     *  如果未选择索引,则返回true。
     * 
     */
    boolean isSelectionEmpty();

    /**
     * Insert length indices beginning before/after index.  This is typically
     * called to sync the selection model with a corresponding change
     * in the data model.
     * <p>
     *  插入在索引之前/之后开始的长度索引。这通常被称为将选择模型与数据模型中的对应变化同步。
     * 
     */
    void insertIndexInterval(int index, int length, boolean before);

    /**
     * Remove the indices in the interval index0,index1 (inclusive) from
     * the selection model.  This is typically called to sync the selection
     * model width a corresponding change in the data model.
     * <p>
     *  从选择模型中删除区间index0,index1(包括)中的索引。这通常被称为将选择模型宽度与数据模型中的对应变化同步。
     * 
     */
    void removeIndexInterval(int index0, int index1);

    /**
     * Sets the {@code valueIsAdjusting} property, which indicates whether
     * or not upcoming selection changes should be considered part of a single
     * change. The value of this property is used to initialize the
     * {@code valueIsAdjusting} property of the {@code ListSelectionEvent}s that
     * are generated.
     * <p>
     * For example, if the selection is being updated in response to a user
     * drag, this property can be set to {@code true} when the drag is initiated
     * and set to {@code false} when the drag is finished. During the drag,
     * listeners receive events with a {@code valueIsAdjusting} property
     * set to {@code true}. At the end of the drag, when the change is
     * finalized, listeners receive an event with the value set to {@code false}.
     * Listeners can use this pattern if they wish to update only when a change
     * has been finalized.
     * <p>
     * Setting this property to {@code true} begins a series of changes that
     * is to be considered part of a single change. When the property is changed
     * back to {@code false}, an event is sent out characterizing the entire
     * selection change (if there was one), with the event's
     * {@code valueIsAdjusting} property set to {@code false}.
     *
     * <p>
     *  设置{@code valueIsAdjusting}属性,指示是否将来的选择更改视为单个更改的一部分。
     * 此属性的值用于初始化生成的{@code ListSelectionEvent}的{@code valueIsAdjusting}属性。
     * <p>
     * 例如,如果选择是响应用户拖动而更新的,则当启动拖动时,此属性可设置为{@code true},并在完成拖动时设置为{@code false}。
     * 在拖动期间,侦听器接收带有{@code valueIsAdjusting}属性设置为{@code true}的事件。在拖动结束时,更改完成后,侦听器将接收一个值设置为{@code false}的事件。
     * 监听器可以使用此模式,如果他们希望仅在更改已完成时更新。
     * <p>
     *  将此属性设置为{@code true}会开始一系列更改,这些更改将被视为单个更改的一部分。
     * 当属性更改回{@code false}时,会发送一个事件来表示整个选择更改(如果有),并将事件的{@code valueIsAdjusting}属性设置为{@code false}。
     * 
     * 
     * @param valueIsAdjusting the new value of the property
     * @see #getValueIsAdjusting
     * @see javax.swing.event.ListSelectionEvent#getValueIsAdjusting
     */
    void setValueIsAdjusting(boolean valueIsAdjusting);

    /**
     * Returns {@code true} if the selection is undergoing a series of changes.
     *
     * <p>
     *  如果选择正在进行一系列更改,则返回{@code true}。
     * 
     * 
     * @return true if the selection is undergoing a series of changes
     * @see #setValueIsAdjusting
     */
    boolean getValueIsAdjusting();

    /**
     * Sets the selection mode. The following list describes the accepted
     * selection modes:
     * <ul>
     * <li>{@code ListSelectionModel.SINGLE_SELECTION} -
     *   Only one list index can be selected at a time. In this mode,
     *   {@code setSelectionInterval} and {@code addSelectionInterval} are
     *   equivalent, both replacing the current selection with the index
     *   represented by the second argument (the "lead").
     * <li>{@code ListSelectionModel.SINGLE_INTERVAL_SELECTION} -
     *   Only one contiguous interval can be selected at a time.
     *   In this mode, {@code addSelectionInterval} behaves like
     *   {@code setSelectionInterval} (replacing the current selection),
     *   unless the given interval is immediately adjacent to or overlaps
     *   the existing selection, and can therefore be used to grow it.
     * <li>{@code ListSelectionModel.MULTIPLE_INTERVAL_SELECTION} -
     *   In this mode, there's no restriction on what can be selected.
     * </ul>
     *
     * <p>
     *  设置选择模式。以下列表描述了接受的选择模式：
     * <ul>
     * <li> {@ code ListSelectionModel.SINGLE_SELECTION}  - 一次只能选择一个列表索引。
     * 在这种模式下,{@code setSelectionInterval}和{@code addSelectionInterval}是等价的,都将当前选择替换为由第二个参数("lead")表示的索引。
     *  <li> {@ code ListSelectionModel.SINGLE_INTERVAL_SELECTION}  - 一次只能选择一个连续的时间间隔。
     * 在这种模式下,{@code addSelectionInterval}的行为类似于{@code setSelectionInterval}(替换当前选择),除非给定的间隔与现有选择直接相邻或重叠,因此可
     * 以用来增长它。
     *  <li> {@ code ListSelectionModel.SINGLE_INTERVAL_SELECTION}  - 一次只能选择一个连续的时间间隔。
     *  <li> {@ code ListSelectionModel.MULTIPLE_INTERVAL_SELECTION}  - 在此模式下,对可选择的内容没有限制。
     * </ul>
     * 
     * @see #getSelectionMode
     * @throws IllegalArgumentException if the selection mode isn't
     *         one of those allowed
     */
    void setSelectionMode(int selectionMode);

    /**
     * Returns the current selection mode.
     *
     * <p>
     * 
     * 
     * @return the current selection mode
     * @see #setSelectionMode
     */
    int getSelectionMode();

    /**
     * Add a listener to the list that's notified each time a change
     * to the selection occurs.
     *
     * <p>
     *  返回当前选择模式。
     * 
     * 
     * @param x the ListSelectionListener
     * @see #removeListSelectionListener
     * @see #setSelectionInterval
     * @see #addSelectionInterval
     * @see #removeSelectionInterval
     * @see #clearSelection
     * @see #insertIndexInterval
     * @see #removeIndexInterval
     */
    void addListSelectionListener(ListSelectionListener x);

    /**
     * Remove a listener from the list that's notified each time a
     * change to the selection occurs.
     *
     * <p>
     *  将监听器添加到每当发生选择更改时通知的列表中。
     * 
     * 
     * @param x the ListSelectionListener
     * @see #addListSelectionListener
     */
    void removeListSelectionListener(ListSelectionListener x);
}
