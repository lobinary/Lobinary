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
import java.beans.*;
import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;
import sun.swing.DefaultLookup;

/**
 * Provides the Synth L&amp;F UI delegate for
 * {@link javax.swing.JOptionPane}.
 *
 * <p>
 *  为{@link javax.swing.JOptionPane}提供Synth L&amp; F UI委托。
 * 
 * 
 * @author James Gosling
 * @author Scott Violet
 * @author Amy Fowler
 * @since 1.7
 */
public class SynthOptionPaneUI extends BasicOptionPaneUI implements
                                PropertyChangeListener, SynthUI {
    private SynthStyle style;

    /**
     * Creates a new UI object for the given component.
     *
     * <p>
     *  为给定组件创建一个新的UI对象。
     * 
     * 
     * @param x component to create UI object for
     * @return the UI object
     */
    public static ComponentUI createUI(JComponent x) {
        return new SynthOptionPaneUI();
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    @Override
    protected void installDefaults() {
        updateStyle(optionPane);
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    @Override
    protected void installListeners() {
        super.installListeners();
        optionPane.addPropertyChangeListener(this);
    }

    private void updateStyle(JComponent c) {
        SynthContext context = getContext(c, ENABLED);
        SynthStyle oldStyle = style;

        style = SynthLookAndFeel.updateStyle(context, this);
        if (style != oldStyle) {
            minimumSize = (Dimension)style.get(context,
                                               "OptionPane.minimumSize");
            if (minimumSize == null) {
                minimumSize = new Dimension(262, 90);
            }
            if (oldStyle != null) {
                uninstallKeyboardActions();
                installKeyboardActions();
            }
        }
        context.dispose();
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    @Override
    protected void uninstallDefaults() {
        SynthContext context = getContext(optionPane, ENABLED);

        style.uninstallDefaults(context);
        context.dispose();
        style = null;
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    @Override
    protected void uninstallListeners() {
        super.uninstallListeners();
        optionPane.removePropertyChangeListener(this);
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    @Override
    protected void installComponents() {
        optionPane.add(createMessageArea());

        Container separator = createSeparator();
        if (separator != null) {
            optionPane.add(separator);
            SynthContext context = getContext(optionPane, ENABLED);
            optionPane.add(Box.createVerticalStrut(context.getStyle().
                       getInt(context, "OptionPane.separatorPadding", 6)));
            context.dispose();
        }
        optionPane.add(createButtonArea());
        optionPane.applyComponentOrientation(optionPane.getComponentOrientation());
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    @Override
    public SynthContext getContext(JComponent c) {
        return getContext(c, getComponentState(c));
    }

    private SynthContext getContext(JComponent c, int state) {
        return SynthContext.getContext(c, style, state);
    }

    private int getComponentState(JComponent c) {
        return SynthLookAndFeel.getComponentState(c);
    }

    /**
     * Notifies this UI delegate to repaint the specified component.
     * This method paints the component background, then calls
     * the {@link #paint(SynthContext,Graphics)} method.
     *
     * <p>In general, this method does not need to be overridden by subclasses.
     * All Look and Feel rendering code should reside in the {@code paint} method.
     *
     * <p>
     *  通知此UI代理重新绘制指定的组件。此方法绘制组件背景,然后调用{@link #paint(SynthContext,Graphics)}方法。
     * 
     *  <p>通常,此方法不需要被子类覆盖。所有Look and Feel渲染代码应该驻留在{@code paint}方法中。
     * 
     * 
     * @param g the {@code Graphics} object used for painting
     * @param c the component being painted
     * @see #paint(SynthContext,Graphics)
     */
    @Override
    public void update(Graphics g, JComponent c) {
        SynthContext context = getContext(c);

        SynthLookAndFeel.update(context, g);
        context.getPainter().paintOptionPaneBackground(context,
                          g, 0, 0, c.getWidth(), c.getHeight());
        paint(context, g);
        context.dispose();
    }

    /**
     * Paints the specified component according to the Look and Feel.
     * <p>This method is not used by Synth Look and Feel.
     * Painting is handled by the {@link #paint(SynthContext,Graphics)} method.
     *
     * <p>
     *  根据外观来绘制指定的组件。 <p>此方法不被Synth Look and Feel使用。绘画由{@link #paint(SynthContext,Graphics)}方法处理。
     * 
     * 
     * @param g the {@code Graphics} object used for painting
     * @param c the component being painted
     * @see #paint(SynthContext,Graphics)
     */
    @Override
    public void paint(Graphics g, JComponent c) {
        SynthContext context = getContext(c);

        paint(context, g);
        context.dispose();
    }

    /**
     * Paints the specified component. This implementation does nothing.
     *
     * <p>
     *  绘制指定的组件。这个实现什么也不做。
     * 
     * 
     * @param context context for the component being painted
     * @param g the {@code Graphics} object used for painting
     * @see #update(Graphics,JComponent)
     */
    protected void paint(SynthContext context, Graphics g) {
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
        context.getPainter().paintOptionPaneBorder(context, g, x, y, w, h);
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    @Override
    public void propertyChange(PropertyChangeEvent e) {
        if (SynthLookAndFeel.shouldUpdateStyle(e)) {
            updateStyle((JOptionPane)e.getSource());
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    @Override
    protected boolean getSizeButtonsToSameWidth() {
        return DefaultLookup.getBoolean(optionPane, this,
                                        "OptionPane.sameSizeButtons", true);
    }

    /**
     * Called from {@link #installComponents} to create a {@code Container}
     * containing the body of the message. The icon is the created by calling
     * {@link #addIcon}.
     * <p>
     *  从{@link #installComponents}调用以创建包含邮件正文的{@code Container}。该图标是通过调用{@link #addIcon}创建的。
     * 
     */
    @Override
    protected Container createMessageArea() {
        JPanel top = new JPanel();
        top.setName("OptionPane.messageArea");
        top.setLayout(new BorderLayout());

        /* Fill the body. */
        Container          body = new JPanel(new GridBagLayout());
        Container          realBody = new JPanel(new BorderLayout());

        body.setName("OptionPane.body");
        realBody.setName("OptionPane.realBody");

        if (getIcon() != null) {
            JPanel sep = new JPanel();
            sep.setName("OptionPane.separator");
            sep.setPreferredSize(new Dimension(15, 1));
            realBody.add(sep, BorderLayout.BEFORE_LINE_BEGINS);
        }
        realBody.add(body, BorderLayout.CENTER);

        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = cons.gridy = 0;
        cons.gridwidth = GridBagConstraints.REMAINDER;
        cons.gridheight = 1;

        SynthContext context = getContext(optionPane, ENABLED);
        cons.anchor = context.getStyle().getInt(context,
                      "OptionPane.messageAnchor", GridBagConstraints.CENTER);
        context.dispose();

        cons.insets = new Insets(0,0,3,0);

        addMessageComponents(body, cons, getMessage(),
                          getMaxCharactersPerLineCount(), false);
        top.add(realBody, BorderLayout.CENTER);

        addIcon(top);
        return top;
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     */
    @Override
    protected Container createSeparator() {
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);

        separator.setName("OptionPane.separator");
        return separator;
    }
}
