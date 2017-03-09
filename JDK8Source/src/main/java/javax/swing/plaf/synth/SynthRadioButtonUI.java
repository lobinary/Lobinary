/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.*;

/**
 * Provides the Synth L&amp;F UI delegate for
 * {@link javax.swing.JRadioButton}.
 *
 * <p>
 *  为{@link javax.swing.JRadioButton}提供Synth L&amp; F UI委托。
 * 
 * 
 * @author Jeff Dinkins
 * @since 1.7
 */
public class SynthRadioButtonUI extends SynthToggleButtonUI {

    // ********************************
    //        Create PLAF
    // ********************************
    /**
     * Creates a new UI object for the given component.
     *
     * <p>
     *  为给定组件创建一个新的UI对象。
     * 
     * 
     * @param b component to create UI object for
     * @return the UI object
     */
    public static ComponentUI createUI(JComponent b) {
        return new SynthRadioButtonUI();
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    @Override
    protected String getPropertyPrefix() {
        return "RadioButton.";
    }

    /**
     * Returns the Icon used in calculating the
     * preferred/minimum/maximum size.
     * <p>
     *  返回用于计算首选/最小/最大大小的图标。
     * 
     */
    @Override
    protected Icon getSizingIcon(AbstractButton b) {
        return getIcon(b);
    }

    @Override
    void paintBackground(SynthContext context, Graphics g, JComponent c) {
        context.getPainter().paintRadioButtonBackground(context, g, 0, 0,
                                                c.getWidth(), c.getHeight());
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     */
    @Override
    public void paintBorder(SynthContext context, Graphics g, int x,
                            int y, int w, int h) {
        context.getPainter().paintRadioButtonBorder(context, g, x, y, w, h);
    }
}
