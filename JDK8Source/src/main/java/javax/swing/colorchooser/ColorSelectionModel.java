/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2001, Oracle and/or its affiliates. All rights reserved.
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

package javax.swing.colorchooser;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.Color;

/**
 * A model that supports selecting a <code>Color</code>.
 *
 * <p>
 *  支持选择<code> Color </code>的模型。
 * 
 * 
 * @author Steve Wilson
 *
 * @see java.awt.Color
 */
public interface ColorSelectionModel {
    /**
     * Returns the selected <code>Color</code> which should be
     * non-<code>null</code>.
     *
     * <p>
     *  返回所选的<code> Color </code>,它应该是非<code> null </code>。
     * 
     * 
     * @return  the selected <code>Color</code>
     * @see     #setSelectedColor
     */
    Color getSelectedColor();

    /**
     * Sets the selected color to <code>color</code>.
     * Note that setting the color to <code>null</code>
     * is undefined and may have unpredictable results.
     * This method fires a state changed event if it sets the
     * current color to a new non-<code>null</code> color.
     *
     * <p>
     *  将所选颜色设置为<code> color </code>。请注意,将颜色设置为<code> null </code>是未定义的,并且可能具有不可预测的结果。
     * 如果将当前颜色设置为新的非<code> null </code>颜色,则此方法触发状态更改事件。
     * 
     * 
     * @param color the new <code>Color</code>
     * @see   #getSelectedColor
     * @see   #addChangeListener
     */
    void setSelectedColor(Color color);

    /**
     * Adds <code>listener</code> as a listener to changes in the model.
     * <p>
     *  将<code> listener </code>添加为监听器,用于模型中的更改。
     * 
     * 
     * @param listener the <code>ChangeListener</code> to be added
     */
    void addChangeListener(ChangeListener listener);

    /**
     * Removes <code>listener</code> as a listener to changes in the model.
     * <p>
     *  删除<code> listener </code>作为模型中的更改的侦听器。
     * 
     * @param listener the <code>ChangeListener</code> to be removed
     */
    void removeChangeListener(ChangeListener listener);
}
