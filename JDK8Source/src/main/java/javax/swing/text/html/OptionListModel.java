/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing.text.html;

import javax.swing.*;
import javax.swing.event.*;
import java.util.BitSet;
import java.io.Serializable;


/**
 * This class extends DefaultListModel, and also implements
 * the ListSelectionModel interface, allowing for it to store state
 * relevant to a SELECT form element which is implemented as a List.
 * If SELECT has a size attribute whose value is greater than 1,
 * or if allows multiple selection then a JList is used to
 * represent it and the OptionListModel is used as its model.
 * It also stores the initial state of the JList, to ensure an
 * accurate reset, if the user requests a reset of the form.
 *
 * <p>
 *  这个类扩展了DefaultListModel,并且实现了ListSelectionModel接口,允许它存储与作为List实现的SELECT表单元素相关的状态。
 * 如果SELECT具有值大于1的size属性,或者允许多选,则使用JList来表示它,并使用OptionListModel作为其模型。它还存储JList的初始状态,以确保精确复位,如果用户请求复位表单。
 * 
 * 
  @author Sunita Mani
 */

class OptionListModel<E> extends DefaultListModel<E> implements ListSelectionModel, Serializable {


    private static final int MIN = -1;
    private static final int MAX = Integer.MAX_VALUE;
    private int selectionMode = SINGLE_SELECTION;
    private int minIndex = MAX;
    private int maxIndex = MIN;
    private int anchorIndex = -1;
    private int leadIndex = -1;
    private int firstChangedIndex = MAX;
    private int lastChangedIndex = MIN;
    private boolean isAdjusting = false;
    private BitSet value = new BitSet(32);
    private BitSet initialValue = new BitSet(32);
    protected EventListenerList listenerList = new EventListenerList();

    protected boolean leadAnchorNotificationEnabled = true;

    public int getMinSelectionIndex() { return isSelectionEmpty() ? -1 : minIndex; }

    public int getMaxSelectionIndex() { return maxIndex; }

    public boolean getValueIsAdjusting() { return isAdjusting; }

    public int getSelectionMode() { return selectionMode; }

    public void setSelectionMode(int selectionMode) {
        switch (selectionMode) {
        case SINGLE_SELECTION:
        case SINGLE_INTERVAL_SELECTION:
        case MULTIPLE_INTERVAL_SELECTION:
            this.selectionMode = selectionMode;
            break;
        default:
            throw new IllegalArgumentException("invalid selectionMode");
        }
    }

    public boolean isSelectedIndex(int index) {
        return ((index < minIndex) || (index > maxIndex)) ? false : value.get(index);
    }

    public boolean isSelectionEmpty() {
        return (minIndex > maxIndex);
    }

    public void addListSelectionListener(ListSelectionListener l) {
        listenerList.add(ListSelectionListener.class, l);
    }

    public void removeListSelectionListener(ListSelectionListener l) {
        listenerList.remove(ListSelectionListener.class, l);
    }

    /**
     * Returns an array of all the <code>ListSelectionListener</code>s added
     * to this OptionListModel with addListSelectionListener().
     *
     * <p>
     *  返回使用addListSelectionListener()添加到此OptionListModel的所有<code> ListSelectionListener </code>数组。
     * 
     * 
     * @return all of the <code>ListSelectionListener</code>s added or an empty
     *         array if no listeners have been added
     * @since 1.4
     */
    public ListSelectionListener[] getListSelectionListeners() {
        return listenerList.getListeners(ListSelectionListener.class);
    }

    /**
     * Notify listeners that we are beginning or ending a
     * series of value changes
     * <p>
     *  通知侦听器我们正在开始或结束一系列值更改
     * 
     */
    protected void fireValueChanged(boolean isAdjusting) {
        fireValueChanged(getMinSelectionIndex(), getMaxSelectionIndex(), isAdjusting);
    }


    /**
     * Notify ListSelectionListeners that the value of the selection,
     * in the closed interval firstIndex,lastIndex, has changed.
     * <p>
     *  通知ListSelectionListeners选择的值在关闭间隔firstIndex,lastIndex中已更改。
     * 
     */
    protected void fireValueChanged(int firstIndex, int lastIndex) {
        fireValueChanged(firstIndex, lastIndex, getValueIsAdjusting());
    }

    /**
    /* <p>
    /* 
     * @param firstIndex The first index in the interval.
     * @param lastIndex The last index in the interval.
     * @param isAdjusting True if this is the final change in a series of them.
     * @see EventListenerList
     */
    protected void fireValueChanged(int firstIndex, int lastIndex, boolean isAdjusting)
    {
        Object[] listeners = listenerList.getListenerList();
        ListSelectionEvent e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ListSelectionListener.class) {
                if (e == null) {
                    e = new ListSelectionEvent(this, firstIndex, lastIndex, isAdjusting);
                }
                ((ListSelectionListener)listeners[i+1]).valueChanged(e);
            }
        }
    }

    private void fireValueChanged() {
        if (lastChangedIndex == MIN) {
            return;
        }
        /* Change the values before sending the event to the
         * listeners in case the event causes a listener to make
         * another change to the selection.
         * <p>
         *  如果事件导致侦听器对选择进行另一个更改,则可以使用侦听器。
         * 
         */
        int oldFirstChangedIndex = firstChangedIndex;
        int oldLastChangedIndex = lastChangedIndex;
        firstChangedIndex = MAX;
        lastChangedIndex = MIN;
        fireValueChanged(oldFirstChangedIndex, oldLastChangedIndex);
    }


    // Update first and last change indices
    private void markAsDirty(int r) {
        firstChangedIndex = Math.min(firstChangedIndex, r);
        lastChangedIndex =  Math.max(lastChangedIndex, r);
    }

    // Set the state at this index and update all relevant state.
    private void set(int r) {
        if (value.get(r)) {
            return;
        }
        value.set(r);
        Option option = (Option)get(r);
        option.setSelection(true);
        markAsDirty(r);

        // Update minimum and maximum indices
        minIndex = Math.min(minIndex, r);
        maxIndex = Math.max(maxIndex, r);
    }

    // Clear the state at this index and update all relevant state.
    private void clear(int r) {
        if (!value.get(r)) {
            return;
        }
        value.clear(r);
        Option option = (Option)get(r);
        option.setSelection(false);
        markAsDirty(r);

        // Update minimum and maximum indices
        /*
           If (r > minIndex) the minimum has not changed.
           The case (r < minIndex) is not possible because r'th value was set.
           We only need to check for the case when lowest entry has been cleared,
           and in this case we need to search for the first value set above it.
        /* <p>
        /*  如果(r> minIndex)的最小值没有改变。情况(r <minIndex)不可能,因为设置了r'th值。我们只需要检查最低条目已被清除的情况,在这种情况下,我们需要搜索上面的第一个值集合。
        /* 
        */
        if (r == minIndex) {
            for(minIndex = minIndex + 1; minIndex <= maxIndex; minIndex++) {
                if (value.get(minIndex)) {
                    break;
                }
            }
        }
        /*
           If (r < maxIndex) the maximum has not changed.
           The case (r > maxIndex) is not possible because r'th value was set.
           We only need to check for the case when highest entry has been cleared,
           and in this case we need to search for the first value set below it.
        /* <p>
        /* 如果(r <maxIndex)的最大值没有改变。情况(r> maxIndex)是不可能的,因为设置了r'th值。我们只需要检查最高条目被清除的情况,在这种情况下,我们需要搜索下面设置的第一个值。
        /* 
        */
        if (r == maxIndex) {
            for(maxIndex = maxIndex - 1; minIndex <= maxIndex; maxIndex--) {
                if (value.get(maxIndex)) {
                    break;
                }
            }
        }
        /* Performance note: This method is called from inside a loop in
           changeSelection() but we will only iterate in the loops
           above on the basis of one iteration per deselected cell - in total.
           Ie. the next time this method is called the work of the previous
           deselection will not be repeated.

           We also don't need to worry about the case when the min and max
           values are in their unassigned states. This cannot happen because
           this method's initial check ensures that the selection was not empty
           and therefore that the minIndex and maxIndex had 'real' values.

           If we have cleared the whole selection, set the minIndex and maxIndex
           to their cannonical values so that the next set command always works
           just by using Math.min and Math.max.
        /* <p>
        /*  changeSelection(),但是我们只会在每个取消选择的单元格的一次迭代的基础上迭代上面的循环。也就是说。下次调用此方法时,以前取消选择的工作将不会重复。
        /* 
        /*  我们也不需要担心最小和最大值处于未分配状态的情况。这不可能发生,因为此方法的初始检查确保选择不为空,因此minIndex和maxIndex具有"真实"值。
        /* 
        /*  如果我们清除了整个选择,将minIndex和maxIndex设置为它们的大炮值,以便下一个设置命令总是使用Math.min和Math.max工作。
        /* 
        */
        if (isSelectionEmpty()) {
            minIndex = MAX;
            maxIndex = MIN;
        }
    }

    /**
     * Sets the value of the leadAnchorNotificationEnabled flag.
     * <p>
     *  设置leadAnchorNotificationEnabled标志的值。
     * 
     * 
     * @see             #isLeadAnchorNotificationEnabled()
     */
    public void setLeadAnchorNotificationEnabled(boolean flag) {
        leadAnchorNotificationEnabled = flag;
    }

    /**
     * Returns the value of the leadAnchorNotificationEnabled flag.
     * When leadAnchorNotificationEnabled is true the model
     * generates notification events with bounds that cover all the changes to
     * the selection plus the changes to the lead and anchor indices.
     * Setting the flag to false causes a norrowing of the event's bounds to
     * include only the elements that have been selected or deselected since
     * the last change. Either way, the model continues to maintain the lead
     * and anchor variables internally. The default is true.
     * <p>
     *  返回leadAnchorNotificationEnabled标志的值。
     * 当leadAnchorNotificationEnabled为true时,模型生成具有边界的通知事件,该边界覆盖对选择的所有更改以及前导和锚索引的更改。
     * 将标志设置为false将导致事件边界的去除仅包括自上次更改以来已选择或取消选择的元素。无论哪种方式,模型继续在内部维护前导和锚定变量。默认值为true。
     * 
     * 
     * @return          the value of the leadAnchorNotificationEnabled flag
     * @see             #setLeadAnchorNotificationEnabled(boolean)
     */
    public boolean isLeadAnchorNotificationEnabled() {
        return leadAnchorNotificationEnabled;
    }

    private void updateLeadAnchorIndices(int anchorIndex, int leadIndex) {
        if (leadAnchorNotificationEnabled) {
            if (this.anchorIndex != anchorIndex) {
                if (this.anchorIndex != -1) { // The unassigned state.
                    markAsDirty(this.anchorIndex);
                }
                markAsDirty(anchorIndex);
            }

            if (this.leadIndex != leadIndex) {
                if (this.leadIndex != -1) { // The unassigned state.
                    markAsDirty(this.leadIndex);
                }
                markAsDirty(leadIndex);
            }
        }
        this.anchorIndex = anchorIndex;
        this.leadIndex = leadIndex;
    }

    private boolean contains(int a, int b, int i) {
        return (i >= a) && (i <= b);
    }

    private void changeSelection(int clearMin, int clearMax,
                                 int setMin, int setMax, boolean clearFirst) {
        for(int i = Math.min(setMin, clearMin); i <= Math.max(setMax, clearMax); i++) {

            boolean shouldClear = contains(clearMin, clearMax, i);
            boolean shouldSet = contains(setMin, setMax, i);

            if (shouldSet && shouldClear) {
                if (clearFirst) {
                    shouldClear = false;
                }
                else {
                    shouldSet = false;
                }
            }

            if (shouldSet) {
                set(i);
            }
            if (shouldClear) {
                clear(i);
            }
        }
        fireValueChanged();
    }

   /*   Change the selection with the effect of first clearing the values
    *   in the inclusive range [clearMin, clearMax] then setting the values
    *   in the inclusive range [setMin, setMax]. Do this in one pass so
    *   that no values are cleared if they would later be set.
    * <p>
    * 在包含范围[clearMin,clearMax]中,然后设置包含范围[setMin,setMax]中的值。在一遍中执行此操作,以便如果稍后将设置值,则不清除任何值。
    * 
    */
    private void changeSelection(int clearMin, int clearMax, int setMin, int setMax) {
        changeSelection(clearMin, clearMax, setMin, setMax, true);
    }

    public void clearSelection() {
        removeSelectionInterval(minIndex, maxIndex);
    }

    public void setSelectionInterval(int index0, int index1) {
        if (index0 == -1 || index1 == -1) {
            return;
        }

        if (getSelectionMode() == SINGLE_SELECTION) {
            index0 = index1;
        }

        updateLeadAnchorIndices(index0, index1);

        int clearMin = minIndex;
        int clearMax = maxIndex;
        int setMin = Math.min(index0, index1);
        int setMax = Math.max(index0, index1);
        changeSelection(clearMin, clearMax, setMin, setMax);
    }

    public void addSelectionInterval(int index0, int index1)
    {
        if (index0 == -1 || index1 == -1) {
            return;
        }

        if (getSelectionMode() != MULTIPLE_INTERVAL_SELECTION) {
            setSelectionInterval(index0, index1);
            return;
        }

        updateLeadAnchorIndices(index0, index1);

        int clearMin = MAX;
        int clearMax = MIN;
        int setMin = Math.min(index0, index1);
        int setMax = Math.max(index0, index1);
        changeSelection(clearMin, clearMax, setMin, setMax);
    }


    public void removeSelectionInterval(int index0, int index1)
    {
        if (index0 == -1 || index1 == -1) {
            return;
        }

        updateLeadAnchorIndices(index0, index1);

        int clearMin = Math.min(index0, index1);
        int clearMax = Math.max(index0, index1);
        int setMin = MAX;
        int setMax = MIN;
        changeSelection(clearMin, clearMax, setMin, setMax);
    }

    private void setState(int index, boolean state) {
        if (state) {
            set(index);
        }
        else {
            clear(index);
        }
    }

    /**
     * Insert length indices beginning before/after index. If the value
     * at index is itself selected, set all of the newly inserted
     * items, otherwise leave them unselected. This method is typically
     * called to sync the selection model with a corresponding change
     * in the data model.
     * <p>
     *  插入在索引之前/之后开始的长度索引。如果索引处的值本身被选择,则设置所有新插入的项目,否则不选择它们。通常调用此方法以使选择模型与数据模型中的对应变化同步。
     * 
     */
    public void insertIndexInterval(int index, int length, boolean before)
    {
        /* The first new index will appear at insMinIndex and the last
         * one will appear at insMaxIndex
         * <p>
         *  一个将出现在insMaxIndex
         * 
         */
        int insMinIndex = (before) ? index : index + 1;
        int insMaxIndex = (insMinIndex + length) - 1;

        /* Right shift the entire bitset by length, beginning with
         * index-1 if before is true, index+1 if it's false (i.e. with
         * insMinIndex).
         * <p>
         *  index-1如果before为true,则索引+ 1如果为假(即使用insMinIndex)。
         * 
         */
        for(int i = maxIndex; i >= insMinIndex; i--) {
            setState(i + length, value.get(i));
        }

        /* Initialize the newly inserted indices.
        /* <p>
         */
        boolean setInsertedValues = value.get(index);
        for(int i = insMinIndex; i <= insMaxIndex; i++) {
            setState(i, setInsertedValues);
        }
    }


    /**
     * Remove the indices in the interval index0,index1 (inclusive) from
     * the selection model.  This is typically called to sync the selection
     * model width a corresponding change in the data model.  Note
     * that (as always) index0 can be greater than index1.
     * <p>
     *  从选择模型中删除区间index0,index1(包括)中的索引。这通常被称为将选择模型宽度与数据模型中的对应变化同步。注意(一如既往)index0可以大于index1。
     * 
     */
    public void removeIndexInterval(int index0, int index1)
    {
        int rmMinIndex = Math.min(index0, index1);
        int rmMaxIndex = Math.max(index0, index1);
        int gapLength = (rmMaxIndex - rmMinIndex) + 1;

        /* Shift the entire bitset to the left to close the index0, index1
         * gap.
         * <p>
         *  间隙。
         * 
         */
        for(int i = rmMinIndex; i <= maxIndex; i++) {
            setState(i, value.get(i + gapLength));
        }
    }


    public void setValueIsAdjusting(boolean isAdjusting) {
        if (isAdjusting != this.isAdjusting) {
            this.isAdjusting = isAdjusting;
            this.fireValueChanged(isAdjusting);
        }
    }


    public String toString() {
        String s =  ((getValueIsAdjusting()) ? "~" : "=") + value.toString();
        return getClass().getName() + " " + Integer.toString(hashCode()) + " " + s;
    }

    /**
     * Returns a clone of the receiver with the same selection.
     * <code>listenerLists</code> are not duplicated.
     *
     * <p>
     *  返回具有相同选择的接收器的克隆。 <code> listenerLists </code>不重复。
     * 
     * 
     * @return a clone of the receiver
     * @exception CloneNotSupportedException if the receiver does not
     *    both (a) implement the <code>Cloneable</code> interface
     *    and (b) define a <code>clone</code> method
     */
    public Object clone() throws CloneNotSupportedException {
        OptionListModel clone = (OptionListModel)super.clone();
        clone.value = (BitSet)value.clone();
        clone.listenerList = new EventListenerList();
        return clone;
    }

    public int getAnchorSelectionIndex() {
        return anchorIndex;
    }

    public int getLeadSelectionIndex() {
        return leadIndex;
    }

    /**
     * Set the anchor selection index, leaving all selection values unchanged.
     *
     * <p>
     *  设置锚选择索引,保留所有选择值不变。
     * 
     * 
     * @see #getAnchorSelectionIndex
     * @see #setLeadSelectionIndex
     */
    public void setAnchorSelectionIndex(int anchorIndex) {
        this.anchorIndex = anchorIndex;
    }

    /**
     * Set the lead selection index, ensuring that values between the
     * anchor and the new lead are either all selected or all deselected.
     * If the value at the anchor index is selected, first clear all the
     * values in the range [anchor, oldLeadIndex], then select all the values
     * values in the range [anchor, newLeadIndex], where oldLeadIndex is the old
     * leadIndex and newLeadIndex is the new one.
     * <p>
     * If the value at the anchor index is not selected, do the same thing in reverse,
     * selecting values in the old range and deselecting values in the new one.
     * <p>
     * Generate a single event for this change and notify all listeners.
     * For the purposes of generating minimal bounds in this event, do the
     * operation in a single pass; that way the first and last index inside the
     * ListSelectionEvent that is broadcast will refer to cells that actually
     * changed value because of this method. If, instead, this operation were
     * done in two steps the effect on the selection state would be the same
     * but two events would be generated and the bounds around the changed values
     * would be wider, including cells that had been first cleared and only
     * to later be set.
     * <p>
     * This method can be used in the mouseDragged() method of a UI class
     * to extend a selection.
     *
     * <p>
     *  设置潜在客户选择索引,确保锚定和新潜在客户之间的值全部选中或全部取消选择。
     * 如果选择锚索引的值,首先清除[anchor,oldLeadIndex]范围内的所有值,然后选择[anchor,newLeadIndex]范围内的所有值,其中oldLeadIndex是旧的leadInde
     * x,newLeadIndex是新的一。
     *  设置潜在客户选择索引,确保锚定和新潜在客户之间的值全部选中或全部取消选择。
     * <p>
     * 如果未选择锚索引处的值,则反向执行相同的操作,选择旧范围中的值,并取消选择新值中的值。
     * <p>
     *  为此更改生成单个事件,并通知所有侦听器。为了在此事件中生成最小边界,在单次通过中执行操作;那样广播的ListSelectionEvent中的第一个和最后一个索引将引用由于此方法实际更改值的单元格。
     * 如果相反,该操作在两个步骤中完成,则对选择状态的影响将是相同的,但是将生成两个事件,并且围绕所改变的值的边界将更宽,包括首先被清除并且稍后将被清除的单元组。
     * 
     * @see #getLeadSelectionIndex
     * @see #setAnchorSelectionIndex
     */
    public void setLeadSelectionIndex(int leadIndex) {
        int anchorIndex = this.anchorIndex;
        if (getSelectionMode() == SINGLE_SELECTION) {
            anchorIndex = leadIndex;
        }

        int oldMin = Math.min(this.anchorIndex, this.leadIndex);
        int oldMax = Math.max(this.anchorIndex, this.leadIndex);
        int newMin = Math.min(anchorIndex, leadIndex);
        int newMax = Math.max(anchorIndex, leadIndex);
        if (value.get(this.anchorIndex)) {
            changeSelection(oldMin, oldMax, newMin, newMax);
        }
        else {
            changeSelection(newMin, newMax, oldMin, oldMax, false);
        }
        this.anchorIndex = anchorIndex;
        this.leadIndex = leadIndex;
    }


    /**
     * This method is responsible for storing the state
     * of the initial selection.  If the selectionMode
     * is the default, i.e allowing only for SINGLE_SELECTION,
     * then the very last OPTION that has the selected
     * attribute set wins.
     * <p>
     * <p>
     *  此方法可以在UI类的mouseDragged()方法中使用,以扩展选择。
     * 
     */
    public void setInitialSelection(int i) {
        if (initialValue.get(i)) {
            return;
        }
        if (selectionMode == SINGLE_SELECTION) {
            // reset to empty
            initialValue.and(new BitSet());
        }
        initialValue.set(i);
    }

    /**
     * Fetches the BitSet that represents the initial
     * set of selected items in the list.
     * <p>
     *  此方法负责存储初始选择的状态。如果selectionMode是默认值,即只允许SINGLE_SELECTION,那么具有所选属性的最后一个OPTION设置为wins。
     * 
     */
    public BitSet getInitialSelection() {
        return initialValue;
    }
}
