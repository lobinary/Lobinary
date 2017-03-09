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

import javax.swing.*;
import javax.swing.text.JTextComponent;
import javax.swing.border.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import java.awt.*;
import java.awt.event.ContainerListener;
import java.awt.event.ContainerEvent;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;

/**
 * Provides the Synth L&amp;F UI delegate for
 * {@link javax.swing.JScrollPane}.
 *
 * <p>
 *  为{@link javax.swing.JScrollPane}提供Synth L&amp; F UI委托。
 * 
 * 
 * @author Scott Violet
 * @since 1.7
 */
public class SynthScrollPaneUI extends BasicScrollPaneUI
                               implements PropertyChangeListener, SynthUI {
    private SynthStyle style;
    private boolean viewportViewHasFocus = false;
    private ViewportViewFocusHandler viewportViewFocusHandler;

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
        return new SynthScrollPaneUI();
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
        context.getPainter().paintScrollPaneBackground(context,
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
        Border vpBorder = scrollpane.getViewportBorder();
        if (vpBorder != null) {
            Rectangle r = scrollpane.getViewportBorderBounds();
            vpBorder.paintBorder(scrollpane, g, r.x, r.y, r.width, r.height);
        }
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
        context.getPainter().paintScrollPaneBorder(context, g, x, y, w, h);
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    @Override
    protected void installDefaults(JScrollPane scrollpane) {
        updateStyle(scrollpane);
    }

    private void updateStyle(JScrollPane c) {
        SynthContext context = getContext(c, ENABLED);
        SynthStyle oldStyle = style;

        style = SynthLookAndFeel.updateStyle(context, this);
        if (style != oldStyle) {
            Border vpBorder = scrollpane.getViewportBorder();
            if ((vpBorder == null) ||( vpBorder instanceof UIResource)) {
                scrollpane.setViewportBorder(new ViewportBorder(context));
            }
            if (oldStyle != null) {
                uninstallKeyboardActions(c);
                installKeyboardActions(c);
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
    protected void installListeners(JScrollPane c) {
        super.installListeners(c);
        c.addPropertyChangeListener(this);
        if (UIManager.getBoolean("ScrollPane.useChildTextComponentFocus")){
            viewportViewFocusHandler = new ViewportViewFocusHandler();
            c.getViewport().addContainerListener(viewportViewFocusHandler);
            Component view = c.getViewport().getView();
            if (view instanceof JTextComponent) {
                view.addFocusListener(viewportViewFocusHandler);
            }
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    @Override
    protected void uninstallDefaults(JScrollPane c) {
        SynthContext context = getContext(c, ENABLED);

        style.uninstallDefaults(context);
        context.dispose();

        if (scrollpane.getViewportBorder() instanceof UIResource) {
            scrollpane.setViewportBorder(null);
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    @Override
    protected void uninstallListeners(JComponent c) {
        super.uninstallListeners(c);
        c.removePropertyChangeListener(this);
        if (viewportViewFocusHandler != null) {
            JViewport viewport = ((JScrollPane) c).getViewport();
            viewport.removeContainerListener(viewportViewFocusHandler);
            if (viewport.getView()!= null) {
                viewport.getView().removeFocusListener(viewportViewFocusHandler);
            }
            viewportViewFocusHandler = null;
        }
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
        int baseState = SynthLookAndFeel.getComponentState(c);
        if (viewportViewFocusHandler!=null && viewportViewHasFocus){
            baseState = baseState | FOCUSED;
        }
        return baseState;
    }

    public void propertyChange(PropertyChangeEvent e) {
        if (SynthLookAndFeel.shouldUpdateStyle(e)) {
            updateStyle(scrollpane);
        }
    }



    private class ViewportBorder extends AbstractBorder implements UIResource {
        private Insets insets;

        ViewportBorder(SynthContext context) {
            this.insets = (Insets)context.getStyle().get(context,
                                            "ScrollPane.viewportBorderInsets");
            if (this.insets == null) {
                this.insets = SynthLookAndFeel.EMPTY_UIRESOURCE_INSETS;
            }
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y,
                            int width, int height) {
            JComponent jc = (JComponent)c;
            SynthContext context = getContext(jc);
            SynthStyle style = context.getStyle();
            if (style == null) {
                assert false: "SynthBorder is being used outside after the " +
                              " UI has been uninstalled";
                return;
            }
            context.getPainter().paintViewportBorder(context, g, x, y, width,
                                                     height);
            context.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            if (insets == null) {
                return new Insets(this.insets.top, this.insets.left,
                                  this.insets.bottom, this.insets.right);
            }
            insets.top = this.insets.top;
            insets.bottom = this.insets.bottom;
            insets.left = this.insets.left;
            insets.right = this.insets.left;
            return insets;
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }
    }

    /**
     * Handle keeping track of the viewport's view's focus
     * <p>
     *  处理跟踪视口的视图焦点
     */
    private class ViewportViewFocusHandler implements ContainerListener,
            FocusListener{
        public void componentAdded(ContainerEvent e) {
            if (e.getChild() instanceof JTextComponent) {
                e.getChild().addFocusListener(this);
                viewportViewHasFocus = e.getChild().isFocusOwner();
                scrollpane.repaint();
            }
        }

        public void componentRemoved(ContainerEvent e) {
            if (e.getChild() instanceof JTextComponent) {
                e.getChild().removeFocusListener(this);
            }
        }

        public void focusGained(FocusEvent e) {
            viewportViewHasFocus = true;
            scrollpane.repaint();
        }

        public void focusLost(FocusEvent e) {
            viewportViewHasFocus = false;
            scrollpane.repaint();
        }
    }
}
