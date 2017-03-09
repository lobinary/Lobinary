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
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.BasicDesktopIconUI;
import java.beans.*;


/**
 * Provides the Synth L&amp;F UI delegate for a minimized internal frame on a desktop.
 *
 * <p>
 *  为桌面上最小化的内部框架提供Synth L&amp; F UI委托。
 * 
 * 
 * @author Joshua Outwater
 * @since 1.7
 */
public class SynthDesktopIconUI extends BasicDesktopIconUI
                                implements SynthUI, PropertyChangeListener {
    private SynthStyle style;
    private Handler handler = new Handler();

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
    public static ComponentUI createUI(JComponent c)    {
        return new SynthDesktopIconUI();
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    @Override
    protected void installComponents() {
        if (UIManager.getBoolean("InternalFrame.useTaskBar")) {
            iconPane = new JToggleButton(frame.getTitle(), frame.getFrameIcon()) {
                @Override public String getToolTipText() {
                    return getText();
                }

                @Override public JPopupMenu getComponentPopupMenu() {
                    return frame.getComponentPopupMenu();
                }
            };
            ToolTipManager.sharedInstance().registerComponent(iconPane);
            iconPane.setFont(desktopIcon.getFont());
            iconPane.setBackground(desktopIcon.getBackground());
            iconPane.setForeground(desktopIcon.getForeground());
        } else {
            iconPane = new SynthInternalFrameTitlePane(frame);
            iconPane.setName("InternalFrame.northPane");
        }
        desktopIcon.setLayout(new BorderLayout());
        desktopIcon.add(iconPane, BorderLayout.CENTER);
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
        desktopIcon.addPropertyChangeListener(this);

        if (iconPane instanceof JToggleButton) {
            frame.addPropertyChangeListener(this);
            ((JToggleButton)iconPane).addActionListener(handler);
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    @Override
    protected void uninstallListeners() {
        if (iconPane instanceof JToggleButton) {
            ((JToggleButton)iconPane).removeActionListener(handler);
            frame.removePropertyChangeListener(this);
        }
        desktopIcon.removePropertyChangeListener(this);
        super.uninstallListeners();
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    @Override
    protected void installDefaults() {
        updateStyle(desktopIcon);
    }

    private void updateStyle(JComponent c) {
        SynthContext context = getContext(c, ENABLED);
        style = SynthLookAndFeel.updateStyle(context, this);
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
        SynthContext context = getContext(desktopIcon, ENABLED);
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
        context.getPainter().paintDesktopIconBackground(context, g, 0, 0,
                                                  c.getWidth(), c.getHeight());
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
     */
    @Override
    public void paintBorder(SynthContext context, Graphics g, int x,
                            int y, int w, int h) {
        context.getPainter().paintDesktopIconBorder(context, g, x, y, w, h);
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() instanceof JInternalFrame.JDesktopIcon) {
            if (SynthLookAndFeel.shouldUpdateStyle(evt)) {
                updateStyle((JInternalFrame.JDesktopIcon)evt.getSource());
            }
        } else if (evt.getSource() instanceof JInternalFrame) {
            JInternalFrame frame = (JInternalFrame)evt.getSource();
            if (iconPane instanceof JToggleButton) {
                JToggleButton button = (JToggleButton)iconPane;
                String prop = evt.getPropertyName();
                if (prop == "title") {
                    button.setText((String)evt.getNewValue());
                } else if (prop == "frameIcon") {
                    button.setIcon((Icon)evt.getNewValue());
                } else if (prop == JInternalFrame.IS_ICON_PROPERTY ||
                           prop == JInternalFrame.IS_SELECTED_PROPERTY) {
                    button.setSelected(!frame.isIcon() && frame.isSelected());
                }
            }
        }
    }

    private final class Handler implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            if (evt.getSource() instanceof JToggleButton) {
                // Either iconify the frame or deiconify and activate it.
                JToggleButton button = (JToggleButton)evt.getSource();
                try {
                    boolean selected = button.isSelected();
                    if (!selected && !frame.isIconifiable()) {
                        button.setSelected(true);
                    } else {
                        frame.setIcon(!selected);
                        if (selected) {
                            frame.setSelected(true);
                        }
                    }
                } catch (PropertyVetoException e2) {
                }
            }
        }
    }
}
