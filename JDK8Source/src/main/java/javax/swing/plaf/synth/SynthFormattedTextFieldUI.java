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

import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

/**
 * Provides the Synth L&amp;F UI delegate for
 * {@link javax.swing.JFormattedTextField}.
 *
 * <p>
 *  为{@link javax.swing.JFormattedTextField}提供Synth L&amp; F UI委托。
 * 
 * 
 * @since 1.7
 */
public class SynthFormattedTextFieldUI extends SynthTextFieldUI {
    /**
     * Creates a UI for a JFormattedTextField.
     *
     * <p>
     *  为JFormattedTextField创建一个UI。
     * 
     * 
     * @param c the formatted text field
     * @return the UI
     */
    public static ComponentUI createUI(JComponent c) {
        return new SynthFormattedTextFieldUI();
    }

    /**
     * Fetches the name used as a key to lookup properties through the
     * UIManager.  This is used as a prefix to all the standard
     * text properties.
     *
     * <p>
     *  获取用作通过UIManager查找属性的键的名称。这用作所有标准文本属性的前缀。
     * 
     * 
     * @return the name "FormattedTextField"
     */
    @Override
    protected String getPropertyPrefix() {
        return "FormattedTextField";
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    @Override
    void paintBackground(SynthContext context, Graphics g, JComponent c) {
        context.getPainter().paintFormattedTextFieldBackground(context, g, 0,
                             0, c.getWidth(), c.getHeight());
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     */
    @Override
    public void paintBorder(SynthContext context, Graphics g, int x,
                            int y, int w, int h) {
        context.getPainter().paintFormattedTextFieldBorder(context, g, x, y,
                                                           w, h);
    }
}
