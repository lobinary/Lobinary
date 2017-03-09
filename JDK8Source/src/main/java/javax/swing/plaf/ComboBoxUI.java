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

package javax.swing.plaf;

import javax.swing.JComboBox;

/**
 * Pluggable look and feel interface for JComboBox.
 *
 * <p>
 *  JComboBox的可插拔外观界面。
 * 
 * 
 * @author Arnaud Weber
 * @author Tom Santos
 */
public abstract class ComboBoxUI extends ComponentUI {

    /**
     * Set the visibility of the popup
     * <p>
     *  设置弹出窗口的可见性
     * 
     */
    public abstract void setPopupVisible( JComboBox c, boolean v );

    /**
     * Determine the visibility of the popup
     * <p>
     *  确定弹出窗口的可见性
     * 
     */
    public abstract boolean isPopupVisible( JComboBox c );

    /**
     * Determine whether or not the combo box itself is traversable
     * <p>
     *  确定组合框本身是否可遍历
     */
    public abstract boolean isFocusTraversable( JComboBox c );
}
