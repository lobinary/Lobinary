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

import javax.swing.JComponent;
import java.awt.Graphics;
import javax.swing.plaf.ComponentUI;


/**
 * Provides the Synth L&amp;F UI delegate for
 * {@link javax.swing.JCheckBox}.
 *
 * <p>
 *  为{@link javax.swing.JCheckBox}提供Synth L&amp; F UI委托。
 * 
 * 
 * @author Jeff Dinkins
 * @since 1.7
 */
public class SynthCheckBoxUI extends SynthRadioButtonUI {

    // ********************************
    //            Create PLAF
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
        return new SynthCheckBoxUI();
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    @Override
    protected String getPropertyPrefix() {
        return "CheckBox.";
    }

    @Override
    void paintBackground(SynthContext context, Graphics g, JComponent c) {
        context.getPainter().paintCheckBoxBackground(context, g, 0, 0,
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
        context.getPainter().paintCheckBoxBorder(context, g, x, y, w, h);
    }
}
