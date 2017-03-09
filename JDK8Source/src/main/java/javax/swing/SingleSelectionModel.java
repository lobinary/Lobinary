/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2002, Oracle and/or its affiliates. All rights reserved.
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
 * A model that supports at most one indexed selection.
 *
 * <p>
 *  最多支持一个索引选择的模型。
 * 
 * 
 * @author Dave Moore
 */
public interface SingleSelectionModel {
    /**
     * Returns the model's selection.
     *
     * <p>
     *  返回模型的选择。
     * 
     * 
     * @return  the model's selection, or -1 if there is no selection
     * @see     #setSelectedIndex
     */
    public int getSelectedIndex();

    /**
     * Sets the model's selected index to <I>index</I>.
     *
     * Notifies any listeners if the model changes
     *
     * <p>
     *  将模型的所选索引设置为<I>索引</I>。
     * 
     *  如果模型更改,则通知任何侦听器
     * 
     * 
     * @param index an int specifying the model selection
     * @see   #getSelectedIndex
     * @see   #addChangeListener
     */
    public void setSelectedIndex(int index);

    /**
     * Clears the selection (to -1).
     * <p>
     *  清除选择(设置为-1)。
     * 
     */
    public void clearSelection();

    /**
     * Returns true if the selection model currently has a selected value.
     * <p>
     *  如果选择模型当前具有选定的值,则返回true。
     * 
     * 
     * @return true if a value is currently selected
     */
    public boolean isSelected();

    /**
     * Adds <I>listener</I> as a listener to changes in the model.
     * <p>
     *  将<I>侦听器</I>添加为侦听器以添加模型中的更改。
     * 
     * 
     * @param listener the ChangeListener to add
     */
    void addChangeListener(ChangeListener listener);

    /**
     * Removes <I>listener</I> as a listener to changes in the model.
     * <p>
     *  删除<I>侦听器</I>作为模型中的更改的侦听器。
     * 
     * @param listener the ChangeListener to remove
     */
    void removeChangeListener(ChangeListener listener);
}
