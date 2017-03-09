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
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.plaf.ComponentUI;

/**
 * Provides the Synth L&amp;F UI delegate for
 * {@link javax.swing.JPasswordField}.
 *
 * <p>
 *  为{@link javax.swing.JPasswordField}提供Synth L&amp; F UI委托。
 * 
 * 
 * @author  Shannon Hickey
 * @since 1.7
 */
public class SynthPasswordFieldUI extends SynthTextFieldUI {

    /**
     * Creates a UI for a JPasswordField.
     *
     * <p>
     *  为JPasswordField创建一个UI。
     * 
     * 
     * @param c the JPasswordField
     * @return the UI
     */
    public static ComponentUI createUI(JComponent c) {
        return new SynthPasswordFieldUI();
    }

    /**
     * Fetches the name used as a key to look up properties through the
     * UIManager.  This is used as a prefix to all the standard
     * text properties.
     *
     * <p>
     *  获取用作通过UIManager查找属性的键的名称。这用作所有标准文本属性的前缀。
     * 
     * 
     * @return the name ("PasswordField")
     */
    @Override
    protected String getPropertyPrefix() {
        return "PasswordField";
    }

    /**
     * Creates a view (PasswordView) for an element.
     *
     * <p>
     *  为元素创建视图(PasswordView)。
     * 
     * 
     * @param elem the element
     * @return the view
     */
    @Override
    public View create(Element elem) {
        return new PasswordView(elem);
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    @Override
    void paintBackground(SynthContext context, Graphics g, JComponent c) {
        context.getPainter().paintPasswordFieldBackground(context, g, 0, 0,
                                                c.getWidth(), c.getHeight());
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    @Override
    public void paintBorder(SynthContext context, Graphics g, int x,
                            int y, int w, int h) {
        context.getPainter().paintPasswordFieldBorder(context, g, x, y, w, h);
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     */
    @Override
    protected void installKeyboardActions() {
        super.installKeyboardActions();
        ActionMap map = SwingUtilities.getUIActionMap(getComponent());
        if (map != null && map.get(DefaultEditorKit.selectWordAction) != null) {
            Action a = map.get(DefaultEditorKit.selectLineAction);
            if (a != null) {
                map.put(DefaultEditorKit.selectWordAction, a);
            }
        }
    }
}
