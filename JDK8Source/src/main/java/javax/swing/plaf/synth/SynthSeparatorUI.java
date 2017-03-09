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

import java.beans.*;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.SeparatorUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.DimensionUIResource;

/**
 * Provides the Synth L&amp;F UI delegate for
 * {@link javax.swing.JSeparator}.
 *
 * <p>
 *  为{@link javax.swing.JSeparator}提供Synth L&amp; F UI委托。
 * 
 * 
 * @author Shannon Hickey
 * @author Joshua Outwater
 * @since 1.7
 */
public class SynthSeparatorUI extends SeparatorUI
                              implements PropertyChangeListener, SynthUI {
    private SynthStyle style;

    /**
     * Creates a new UI object for the given component.
     *
     * <p>
     *  为给定组件创建一个新的UI对象。
     * 
     * 
     * @param c component to create UI object for
     * @return the UI object
     */
    public static ComponentUI createUI(JComponent c) {
        return new SynthSeparatorUI();
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    @Override
    public void installUI(JComponent c) {
        installDefaults((JSeparator)c);
        installListeners((JSeparator)c);
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    @Override
    public void uninstallUI(JComponent c) {
        uninstallListeners((JSeparator)c);
        uninstallDefaults((JSeparator)c);
    }

    /**
     * Installs default setting. This method is called when a
     * {@code LookAndFeel} is installed.
     * <p>
     *  安装默认设置。当安装{@code LookAndFeel}时调用此方法。
     * 
     */
    public void installDefaults(JSeparator c) {
        updateStyle(c);
    }

    private void updateStyle(JSeparator sep) {
        SynthContext context = getContext(sep, ENABLED);
        SynthStyle oldStyle = style;

        style = SynthLookAndFeel.updateStyle(context, this);

        if (style != oldStyle) {
            if (sep instanceof JToolBar.Separator) {
                Dimension size = ((JToolBar.Separator)sep).getSeparatorSize();
                if (size == null || size instanceof UIResource) {
                    size = (DimensionUIResource)style.get(
                                      context, "ToolBar.separatorSize");
                    if (size == null) {
                        size = new DimensionUIResource(10, 10);
                    }
                    ((JToolBar.Separator)sep).setSeparatorSize(size);
                }
            }
        }

        context.dispose();
    }

    /**
     * Uninstalls default setting. This method is called when a
     * {@code LookAndFeel} is uninstalled.
     * <p>
     *  卸载默认设置。卸载{@code LookAndFeel}时调用此方法。
     * 
     */
    public void uninstallDefaults(JSeparator c) {
        SynthContext context = getContext(c, ENABLED);

        style.uninstallDefaults(context);
        context.dispose();
        style = null;
    }

    /**
     * Installs listeners. This method is called when a
     * {@code LookAndFeel} is installed.
     * <p>
     *  安装侦听器。当安装{@code LookAndFeel}时调用此方法。
     * 
     */
    public void installListeners(JSeparator c) {
        c.addPropertyChangeListener(this);
    }

    /**
     * Uninstalls listeners. This method is called when a
     * {@code LookAndFeel} is uninstalled.
     * <p>
     *  卸载侦听器。卸载{@code LookAndFeel}时调用此方法。
     * 
     */
    public void uninstallListeners(JSeparator c) {
        c.removePropertyChangeListener(this);
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

        JSeparator separator = (JSeparator)context.getComponent();
        SynthLookAndFeel.update(context, g);
        context.getPainter().paintSeparatorBackground(context,
                          g, 0, 0, c.getWidth(), c.getHeight(),
                          separator.getOrientation());
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
     * Paints the specified component.
     *
     * <p>
     *  绘制指定的组件。
     * 
     * 
     * @param context context for the component being painted
     * @param g the {@code Graphics} object used for painting
     * @see #update(Graphics,JComponent)
     */
    protected void paint(SynthContext context, Graphics g) {
        JSeparator separator = (JSeparator)context.getComponent();
        context.getPainter().paintSeparatorForeground(context, g, 0, 0,
                             separator.getWidth(), separator.getHeight(),
                             separator.getOrientation());
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
        JSeparator separator = (JSeparator)context.getComponent();
        context.getPainter().paintSeparatorBorder(context, g, x, y, w, h,
                                                  separator.getOrientation());
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    @Override
    public Dimension getPreferredSize(JComponent c) {
        SynthContext context = getContext(c);

        int thickness = style.getInt(context, "Separator.thickness", 2);
        Insets insets = c.getInsets();
        Dimension size;

        if (((JSeparator)c).getOrientation() == JSeparator.VERTICAL) {
            size = new Dimension(insets.left + insets.right + thickness,
                                 insets.top + insets.bottom);
        } else {
            size = new Dimension(insets.left + insets.right,
                                 insets.top + insets.bottom + thickness);
        }
        context.dispose();
        return size;
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    @Override
    public Dimension getMinimumSize(JComponent c) {
        return getPreferredSize(c);
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    @Override
    public Dimension getMaximumSize(JComponent c) {
        return new Dimension(Short.MAX_VALUE, Short.MAX_VALUE);
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     */
    @Override
    public SynthContext getContext(JComponent c) {
        return getContext(c, SynthLookAndFeel.getComponentState(c));
    }

    private SynthContext getContext(JComponent c, int state) {
        return SynthContext.getContext(c, style, state);
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (SynthLookAndFeel.shouldUpdateStyle(evt)) {
            updateStyle((JSeparator)evt.getSource());
        }
    }
}
