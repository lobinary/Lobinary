/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, 2012, Oracle and/or its affiliates. All rights reserved.
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

package javax.swing.plaf.synth;

import javax.swing.*;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import java.awt.*;


/**
 * Synth's ComboPopup.
 *
 * <p>
 *  Synth的ComboPopup。
 * 
 * 
 * @author Scott Violet
 */
class SynthComboPopup extends BasicComboPopup {
    public SynthComboPopup( JComboBox combo ) {
        super(combo);
    }

    /**
     * Configures the list which is used to hold the combo box items in the
     * popup. This method is called when the UI class
     * is created.
     *
     * <p>
     *  配置用于在弹出窗口中保存组合框项目的列表。创建UI类时调用此方法。
     * 
     * 
     * @see #createList
     */
    @Override
    protected void configureList() {
        list.setFont( comboBox.getFont() );
        list.setCellRenderer( comboBox.getRenderer() );
        list.setFocusable( false );
        list.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        int selectedIndex = comboBox.getSelectedIndex();
        if ( selectedIndex == -1 ) {
            list.clearSelection();
        }
        else {
            list.setSelectedIndex( selectedIndex );
            list.ensureIndexIsVisible( selectedIndex );
        }
        installListListeners();
    }

    /**
     * @inheritDoc
     *
     * Overridden to take into account any popup insets specified in
     * SynthComboBoxUI
     * <p>
     *  @inheritDoc
     * 
     */
    @Override
    protected Rectangle computePopupBounds(int px, int py, int pw, int ph) {
        ComboBoxUI ui = comboBox.getUI();
        if (ui instanceof SynthComboBoxUI) {
            SynthComboBoxUI sui = (SynthComboBoxUI) ui;
            if (sui.popupInsets != null) {
                Insets i = sui.popupInsets;
                return super.computePopupBounds(
                        px + i.left,
                        py + i.top,
                        pw - i.left - i.right,
                        ph - i.top - i.bottom);
            }
        }
        return super.computePopupBounds(px, py, pw, ph);
    }
}
