/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2006, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing.plaf.nimbus;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.swing.*;
import javax.swing.plaf.synth.SynthContext;
import javax.swing.plaf.synth.SynthPainter;
import javax.swing.plaf.synth.SynthConstants;

import javax.swing.Painter;


class SynthPainterImpl extends SynthPainter {
    private NimbusStyle style;

    SynthPainterImpl(NimbusStyle style) {
        this.style = style;
    }

    /**
     * Paint the provided painter using the provided transform at the specified
     * position and size. Handles if g is a non 2D Graphics by painting via a
     * BufferedImage.
     * <p>
     *  使用在指定位置和大小提供的变换绘制提供的绘图器。如果g是通过BufferedImage绘制的非2D图形,则处理。
     * 
     */
    private void paint(Painter p, SynthContext ctx, Graphics g, int x, int y,
                       int w, int h, AffineTransform transform) {
        if (p != null) {
            if (g instanceof Graphics2D){
                Graphics2D gfx = (Graphics2D)g;
                if (transform!=null){
                    gfx.transform(transform);
                }
                gfx.translate(x, y);
                p.paint(gfx, ctx.getComponent(), w, h);
                gfx.translate(-x, -y);
                if (transform!=null){
                    try {
                        gfx.transform(transform.createInverse());
                    } catch (NoninvertibleTransformException e) {
                        // this should never happen as we are in control of all
                        // calls into this method and only ever pass in simple
                        // transforms of rotate, flip and translates
                        e.printStackTrace();
                    }
                }
            } else {
                // use image if we are printing to a Java 1.1 PrintGraphics as
                // it is not a instance of Graphics2D
                BufferedImage img = new BufferedImage(w,h,
                        BufferedImage.TYPE_INT_ARGB);
                Graphics2D gfx = img.createGraphics();
                if (transform!=null){
                    gfx.transform(transform);
                }
                p.paint(gfx, ctx.getComponent(), w, h);
                gfx.dispose();
                g.drawImage(img,x,y,null);
                img = null;
            }
        }
    }

    private void paintBackground(SynthContext ctx, Graphics g, int x, int y,
                                 int w, int h, AffineTransform transform) {
        // if the background color of the component is 100% transparent
        // then we should not paint any background graphics. This is a solution
        // for there being no way of turning off Nimbus background painting as
        // basic components are all non-opaque by default.
        Component c = ctx.getComponent();
        Color bg = (c != null) ? c.getBackground() : null;
        if (bg == null || bg.getAlpha() > 0){
            Painter backgroundPainter = style.getBackgroundPainter(ctx);
            if (backgroundPainter != null) {
                paint(backgroundPainter, ctx, g, x, y, w, h,transform);
            }
        }
    }

    private void paintForeground(SynthContext ctx, Graphics g, int x, int y,
                                 int w, int h, AffineTransform transform) {
        Painter foregroundPainter = style.getForegroundPainter(ctx);
        if (foregroundPainter != null) {
            paint(foregroundPainter, ctx, g, x, y, w, h,transform);
        }
    }

    private void paintBorder(SynthContext ctx, Graphics g, int x, int y, int w,
                             int h, AffineTransform transform) {
        Painter borderPainter = style.getBorderPainter(ctx);
        if (borderPainter != null) {
            paint(borderPainter, ctx, g, x, y, w, h,transform);
        }
    }

    private void paintBackground(SynthContext ctx, Graphics g, int x, int y, int w, int h, int orientation) {
        Component c = ctx.getComponent();
        boolean ltr = c.getComponentOrientation().isLeftToRight();
        // Don't RTL flip JSpliders as they handle it internaly
        if (ctx.getComponent() instanceof JSlider) ltr = true;

        if (orientation == SwingConstants.VERTICAL && ltr) {
            AffineTransform transform = new AffineTransform();
            transform.scale(-1, 1);
            transform.rotate(Math.toRadians(90));
            paintBackground(ctx, g, y, x, h, w, transform);
        } else if (orientation == SwingConstants.VERTICAL) {
            AffineTransform transform = new AffineTransform();
            transform.rotate(Math.toRadians(90));
            transform.translate(0,-(x+w));
            paintBackground(ctx, g, y, x, h, w, transform);
        } else if (orientation == SwingConstants.HORIZONTAL && ltr) {
            paintBackground(ctx, g, x, y, w, h, null);
        } else {
            //horizontal and right-to-left orientation
            AffineTransform transform = new AffineTransform();
            transform.translate(x,y);
            transform.scale(-1, 1);
            transform.translate(-w,0);
            paintBackground(ctx, g, 0, 0, w, h, transform);
        }
    }

    private void paintBorder(SynthContext ctx, Graphics g, int x, int y, int w, int h, int orientation) {
        Component c = ctx.getComponent();
        boolean ltr = c.getComponentOrientation().isLeftToRight();
        if (orientation == SwingConstants.VERTICAL && ltr) {
            AffineTransform transform = new AffineTransform();
            transform.scale(-1, 1);
            transform.rotate(Math.toRadians(90));
            paintBorder(ctx, g, y, x, h, w, transform);
        } else if (orientation == SwingConstants.VERTICAL) {
            AffineTransform transform = new AffineTransform();
            transform.rotate(Math.toRadians(90));
            transform.translate(0, -(x + w));
            paintBorder(ctx, g, y, 0, h, w, transform);
        } else if (orientation == SwingConstants.HORIZONTAL && ltr) {
            paintBorder(ctx, g, x, y, w, h, null);
        } else {
            //horizontal and right-to-left orientation
            paintBorder(ctx, g, x, y, w, h, null);
        }
    }

    private void paintForeground(SynthContext ctx, Graphics g, int x, int y, int w, int h, int orientation) {
        Component c = ctx.getComponent();
        boolean ltr = c.getComponentOrientation().isLeftToRight();
        if (orientation == SwingConstants.VERTICAL && ltr) {
            AffineTransform transform = new AffineTransform();
            transform.scale(-1, 1);
            transform.rotate(Math.toRadians(90));
            paintForeground(ctx, g, y, x, h, w, transform);
        } else if (orientation == SwingConstants.VERTICAL) {
            AffineTransform transform = new AffineTransform();
            transform.rotate(Math.toRadians(90));
            transform.translate(0, -(x + w));
            paintForeground(ctx, g, y, 0, h, w, transform);
        } else if (orientation == SwingConstants.HORIZONTAL && ltr) {
            paintForeground(ctx, g, x, y, w, h, null);
        } else {
            //horizontal and right-to-left orientation
            paintForeground(ctx, g, x, y, w, h, null);
        }
    }

    /**
     * Paints the background of an arrow button. Arrow buttons are created by
     * some components, such as <code>JScrollBar</code>.
     *
     * <p>
     *  描绘一个箭头按钮的背景。箭头按钮由某些组件创建,例如<code> JScrollBar </code>。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintArrowButtonBackground(SynthContext context,
                                           Graphics g, int x, int y,
                                           int w, int h) {
        if (context.getComponent().getComponentOrientation().isLeftToRight()){
            paintBackground(context, g, x, y, w, h, null);
        } else {
            AffineTransform transform = new AffineTransform();
            transform.translate(x,y);
            transform.scale(-1, 1);
            transform.translate(-w,0);
            paintBackground(context, g, 0, 0, w, h, transform);
        }
    }

    /**
     * Paints the border of an arrow button. Arrow buttons are created by
     * some components, such as <code>JScrollBar</code>.
     *
     * <p>
     *  绘制箭头按钮的边框。箭头按钮由某些组件创建,例如<code> JScrollBar </code>。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintArrowButtonBorder(SynthContext context,
                                       Graphics g, int x, int y,
                                       int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the foreground of an arrow button. This method is responsible
     * for drawing a graphical representation of a direction, typically
     * an arrow. Arrow buttons are created by
     * some components, such as <code>JScrollBar</code>
     *
     * <p>
     *  绘制箭头按钮的前景。此方法负责绘制方向(通常为箭头)的图形表示。箭头按钮由某些组件创建,例如<code> JScrollBar </code>
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     * @param direction One of SwingConstants.NORTH, SwingConstants.SOUTH
     *                  SwingConstants.EAST or SwingConstants.WEST
     */
    public void paintArrowButtonForeground(SynthContext context,
                                           Graphics g, int x, int y,
                                           int w, int h,
                                           int direction) {
        //assume that the painter is arranged with the arrow pointing... LEFT?
        String compName = context.getComponent().getName();
        boolean ltr = context.getComponent().
                getComponentOrientation().isLeftToRight();
        // The hard coding for spinners here needs to be replaced by a more
        // general method for disabling rotation
        if ("Spinner.nextButton".equals(compName) ||
                "Spinner.previousButton".equals(compName)) {
            if (ltr){
                paintForeground(context, g, x, y, w, h, null);
            } else {
                AffineTransform transform = new AffineTransform();
                transform.translate(w, 0);
                transform.scale(-1, 1);
                paintForeground(context, g, x, y, w, h, transform);
            }
        } else if (direction == SwingConstants.WEST) {
            paintForeground(context, g, x, y, w, h, null);
        } else if (direction == SwingConstants.NORTH) {
            if (ltr){
                AffineTransform transform = new AffineTransform();
                transform.scale(-1, 1);
                transform.rotate(Math.toRadians(90));
                paintForeground(context, g, y, 0, h, w, transform);
            } else {
                AffineTransform transform = new AffineTransform();
                transform.rotate(Math.toRadians(90));
                transform.translate(0, -(x + w));
                paintForeground(context, g, y, 0, h, w, transform);
            }
        } else if (direction == SwingConstants.EAST) {
            AffineTransform transform = new AffineTransform();
            transform.translate(w, 0);
            transform.scale(-1, 1);
            paintForeground(context, g, x, y, w, h, transform);
        } else if (direction == SwingConstants.SOUTH) {
            if (ltr){
                AffineTransform transform = new AffineTransform();
                transform.rotate(Math.toRadians(-90));
                transform.translate(-h, 0);
                paintForeground(context, g, y, x, h, w, transform);
            } else {
                AffineTransform transform = new AffineTransform();
                transform.scale(-1, 1);
                transform.rotate(Math.toRadians(-90));
                transform.translate(-(h+y), -(w+x));
                paintForeground(context, g, y, x, h, w, transform);
            }
        }
    }

    /**
     * Paints the background of a button.
     *
     * <p>
     *  绘制按钮的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintButtonBackground(SynthContext context,
                                      Graphics g, int x, int y,
                                      int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of a button.
     *
     * <p>
     *  绘制按钮的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintButtonBorder(SynthContext context,
                                  Graphics g, int x, int y,
                                  int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of a check box menu item.
     *
     * <p>
     *  描绘复选框菜单项的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintCheckBoxMenuItemBackground(SynthContext context,
                                                Graphics g, int x, int y,
                                                int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of a check box menu item.
     *
     * <p>
     *  绘制复选框菜单项的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintCheckBoxMenuItemBorder(SynthContext context,
                                            Graphics g, int x, int y,
                                            int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of a check box.
     *
     * <p>
     *  绘制复选框的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintCheckBoxBackground(SynthContext context,
                                        Graphics g, int x, int y,
                                        int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of a check box.
     *
     * <p>
     *  绘制复选框的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintCheckBoxBorder(SynthContext context,
                                    Graphics g, int x, int y,
                                    int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of a color chooser.
     *
     * <p>
     *  绘制颜色选择器的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintColorChooserBackground(SynthContext context,
                                            Graphics g, int x, int y,
                                            int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of a color chooser.
     *
     * <p>
     *  绘制颜色选择器的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintColorChooserBorder(SynthContext context,
                                        Graphics g, int x, int y,
                                        int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of a combo box.
     *
     * <p>
     *  绘制组合框的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintComboBoxBackground(SynthContext context,
                                        Graphics g, int x, int y,
                                        int w, int h) {
        if (context.getComponent().getComponentOrientation().isLeftToRight()){
            paintBackground(context, g, x, y, w, h, null);
        } else {
            AffineTransform transform = new AffineTransform();
            transform.translate(x,y);
            transform.scale(-1, 1);
            transform.translate(-w,0);
            paintBackground(context, g, 0, 0, w, h, transform);
        }
    }

    /**
     * Paints the border of a combo box.
     *
     * <p>
     *  绘制组合框的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintComboBoxBorder(SynthContext context,
                                        Graphics g, int x, int y,
                                        int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of a desktop icon.
     *
     * <p>
     *  描绘桌面图标的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintDesktopIconBackground(SynthContext context,
                                        Graphics g, int x, int y,
                                        int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of a desktop icon.
     *
     * <p>
     *  绘制桌面图标的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintDesktopIconBorder(SynthContext context,
                                           Graphics g, int x, int y,
                                           int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of a desktop pane.
     *
     * <p>
     *  绘制桌面窗格的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintDesktopPaneBackground(SynthContext context,
                                           Graphics g, int x, int y,
                                           int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of a desktop pane.
     *
     * <p>
     *  绘制桌面窗格的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintDesktopPaneBorder(SynthContext context,
                                       Graphics g, int x, int y,
                                       int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of an editor pane.
     *
     * <p>
     *  描绘编辑器窗格的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintEditorPaneBackground(SynthContext context,
                                          Graphics g, int x, int y,
                                          int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of an editor pane.
     *
     * <p>
     *  绘制编辑器窗格的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintEditorPaneBorder(SynthContext context,
                                      Graphics g, int x, int y,
                                      int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of a file chooser.
     *
     * <p>
     *  描绘文件选择器的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintFileChooserBackground(SynthContext context,
                                          Graphics g, int x, int y,
                                          int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of a file chooser.
     *
     * <p>
     *  绘制文件选择器的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintFileChooserBorder(SynthContext context,
                                      Graphics g, int x, int y,
                                      int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of a formatted text field.
     *
     * <p>
     * 绘制格式化文本字段的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintFormattedTextFieldBackground(SynthContext context,
                                          Graphics g, int x, int y,
                                          int w, int h) {
        if (context.getComponent().getComponentOrientation().isLeftToRight()){
            paintBackground(context, g, x, y, w, h, null);
        } else {
            AffineTransform transform = new AffineTransform();
            transform.translate(x,y);
            transform.scale(-1, 1);
            transform.translate(-w,0);
            paintBackground(context, g, 0, 0, w, h, transform);
        }
    }

    /**
     * Paints the border of a formatted text field.
     *
     * <p>
     *  绘制格式化文本字段的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintFormattedTextFieldBorder(SynthContext context,
                                      Graphics g, int x, int y,
                                      int w, int h) {
        if (context.getComponent().getComponentOrientation().isLeftToRight()){
            paintBorder(context, g, x, y, w, h, null);
        } else {
            AffineTransform transform = new AffineTransform();
            transform.translate(x,y);
            transform.scale(-1, 1);
            transform.translate(-w,0);
            paintBorder(context, g, 0, 0, w, h, transform);
        }
    }

    /**
     * Paints the background of an internal frame title pane.
     *
     * <p>
     *  绘制内部框架标题窗格的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintInternalFrameTitlePaneBackground(SynthContext context,
                                          Graphics g, int x, int y,
                                          int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of an internal frame title pane.
     *
     * <p>
     *  绘制内部框架标题窗格的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintInternalFrameTitlePaneBorder(SynthContext context,
                                      Graphics g, int x, int y,
                                      int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of an internal frame.
     *
     * <p>
     *  描绘内部框架的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintInternalFrameBackground(SynthContext context,
                                          Graphics g, int x, int y,
                                          int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of an internal frame.
     *
     * <p>
     *  描绘内部框架的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintInternalFrameBorder(SynthContext context,
                                      Graphics g, int x, int y,
                                      int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of a label.
     *
     * <p>
     *  描绘标签的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintLabelBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of a label.
     *
     * <p>
     *  绘制标签的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintLabelBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of a list.
     *
     * <p>
     *  描述列表的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintListBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of a list.
     *
     * <p>
     *  描画列表的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintListBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of a menu bar.
     *
     * <p>
     *  绘制菜单栏的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintMenuBarBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of a menu bar.
     *
     * <p>
     *  绘制菜单栏的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintMenuBarBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of a menu item.
     *
     * <p>
     *  描绘菜单项的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintMenuItemBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of a menu item.
     *
     * <p>
     *  绘制菜单项的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintMenuItemBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of a menu.
     *
     * <p>
     *  描绘菜单的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintMenuBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of a menu.
     *
     * <p>
     *  绘制菜单的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintMenuBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of an option pane.
     *
     * <p>
     *  绘制选项窗格的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintOptionPaneBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of an option pane.
     *
     * <p>
     *  绘制选项窗格的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintOptionPaneBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of a panel.
     *
     * <p>
     *  描绘面板的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintPanelBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of a panel.
     *
     * <p>
     *  绘制面板的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintPanelBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of a password field.
     *
     * <p>
     *  描绘密码字段的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintPasswordFieldBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of a password field.
     *
     * <p>
     *  绘制密码字段的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintPasswordFieldBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of a popup menu.
     *
     * <p>
     *  绘制弹出菜单的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintPopupMenuBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of a popup menu.
     *
     * <p>
     *  绘制弹出菜单的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintPopupMenuBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of a progress bar.
     *
     * <p>
     *  绘制进度条的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintProgressBarBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of a progress bar. This implementation invokes the
     * method of the same name without the orientation.
     *
     * <p>
     *  绘制进度条的背景。这个实现调用没有方向的同名方法。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     * @param orientation one of <code>JProgressBar.HORIZONTAL</code> or
     *                    <code>JProgressBar.VERTICAL</code>
     * @since 1.6
     */
    public void paintProgressBarBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h, int orientation) {
        paintBackground(context, g, x, y, w, h, orientation);
    }

    /**
     * Paints the border of a progress bar.
     *
     * <p>
     *  绘制进度条的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintProgressBarBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of a progress bar. This implementation invokes the
     * method of the same name without the orientation.
     *
     * <p>
     *  绘制进度条的边框。这个实现调用没有方向的同名方法。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     * @param orientation one of <code>JProgressBar.HORIZONTAL</code> or
     *                    <code>JProgressBar.VERTICAL</code>
     * @since 1.6
     */
    public void paintProgressBarBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h, int orientation) {
        paintBorder(context, g, x, y, w, h, orientation);
    }

    /**
     * Paints the foreground of a progress bar. is responsible for
     * providing an indication of the progress of the progress bar.
     *
     * <p>
     *  绘制进度条的前台。负责提供进度条进度的指示。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     * @param orientation one of <code>JProgressBar.HORIZONTAL</code> or
     *                    <code>JProgressBar.VERTICAL</code>
     */
    public void paintProgressBarForeground(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h, int orientation) {
        paintForeground(context, g, x, y, w, h, orientation);
    }

    /**
     * Paints the background of a radio button menu item.
     *
     * <p>
     *  描绘单选按钮菜单项的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintRadioButtonMenuItemBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of a radio button menu item.
     *
     * <p>
     *  绘制单选按钮菜单项的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintRadioButtonMenuItemBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of a radio button.
     *
     * <p>
     * 描绘单选按钮的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintRadioButtonBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of a radio button.
     *
     * <p>
     *  绘制单选按钮的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintRadioButtonBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of a root pane.
     *
     * <p>
     *  绘制根窗格的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintRootPaneBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of a root pane.
     *
     * <p>
     *  绘制根窗格的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintRootPaneBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of a scrollbar.
     *
     * <p>
     *  描绘滚动条的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintScrollBarBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of a scrollbar. This implementation invokes the
     * method of the same name without the orientation.
     *
     * <p>
     *  描绘滚动条的背景。这个实现调用没有方向的同名方法。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     * @param orientation Orientation of the JScrollBar, one of
     *                    <code>JScrollBar.HORIZONTAL</code> or
     *                    <code>JScrollBar.VERTICAL</code>
     * @since 1.6
     */
    public void paintScrollBarBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h, int orientation) {
        paintBackground(context, g, x, y, w, h, orientation);
    }

    /**
     * Paints the border of a scrollbar.
     *
     * <p>
     *  绘制滚动条的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintScrollBarBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of a scrollbar. This implementation invokes the
     * method of the same name without the orientation.
     *
     * <p>
     *  绘制滚动条的边框。这个实现调用没有方向的同名方法。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     * @param orientation Orientation of the JScrollBar, one of
     *                    <code>JScrollBar.HORIZONTAL</code> or
     *                    <code>JScrollBar.VERTICAL</code>
     * @since 1.6
     */
    public void paintScrollBarBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h, int orientation) {
        paintBorder(context, g, x, y, w, h, orientation);
    }

    /**
     * Paints the background of the thumb of a scrollbar. The thumb provides
     * a graphical indication as to how much of the Component is visible in a
     * <code>JScrollPane</code>.
     *
     * <p>
     *  描绘滚动条的缩略图的背景。拇指提供了一个图形指示,表明在<code> JScrollPane </code>中可以看到组件的多少。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     * @param orientation Orientation of the JScrollBar, one of
     *                    <code>JScrollBar.HORIZONTAL</code> or
     *                    <code>JScrollBar.VERTICAL</code>
     */
    public void paintScrollBarThumbBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h, int orientation) {
        paintBackground(context, g, x, y, w, h, orientation);
    }

    /**
     * Paints the border of the thumb of a scrollbar. The thumb provides
     * a graphical indication as to how much of the Component is visible in a
     * <code>JScrollPane</code>.
     *
     * <p>
     *  绘制滚动条的缩略图的边框。拇指提供了一个图形指示,表明在<code> JScrollPane </code>中可以看到组件的多少。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     * @param orientation Orientation of the JScrollBar, one of
     *                    <code>JScrollBar.HORIZONTAL</code> or
     *                    <code>JScrollBar.VERTICAL</code>
     */
    public void paintScrollBarThumbBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h, int orientation) {
        paintBorder(context, g, x, y, w, h, orientation);
    }

    /**
     * Paints the background of the track of a scrollbar. The track contains
     * the thumb.
     *
     * <p>
     *  描绘滚动条轨迹的背景。轨道包含拇指。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintScrollBarTrackBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of the track of a scrollbar. The track contains
     * the thumb. This implementation invokes the method of the same name without
     * the orientation.
     *
     * <p>
     *  描绘滚动条轨迹的背景。轨道包含拇指。这个实现调用没有方向的同名方法。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     * @param orientation Orientation of the JScrollBar, one of
     *                    <code>JScrollBar.HORIZONTAL</code> or
     *                    <code>JScrollBar.VERTICAL</code>
     * @since 1.6
     */
    public void paintScrollBarTrackBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h, int orientation) {
        paintBackground(context, g, x, y, w, h, orientation);
    }

    /**
     * Paints the border of the track of a scrollbar. The track contains
     * the thumb.
     *
     * <p>
     *  绘制滚动条轨道的边框。轨道包含拇指。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintScrollBarTrackBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of the track of a scrollbar. The track contains
     * the thumb. This implementation invokes the method of the same name without
     * the orientation.
     *
     * <p>
     *  绘制滚动条轨道的边框。轨道包含拇指。这个实现调用没有方向的同名方法。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     * @param orientation Orientation of the JScrollBar, one of
     *                    <code>JScrollBar.HORIZONTAL</code> or
     *                    <code>JScrollBar.VERTICAL</code>
     * @since 1.6
     */
    public void paintScrollBarTrackBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h, int orientation) {
        paintBorder(context, g, x, y, w, h, orientation);
    }

    /**
     * Paints the background of a scroll pane.
     *
     * <p>
     *  绘制滚动窗格的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintScrollPaneBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of a scroll pane.
     *
     * <p>
     *  绘制滚动窗格的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintScrollPaneBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of a separator.
     *
     * <p>
     *  绘制分隔符的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintSeparatorBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of a separator. This implementation invokes the
     * method of the same name without the orientation.
     *
     * <p>
     * 绘制分隔符的背景。这个实现调用没有方向的同名方法。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     * @param orientation One of <code>JSeparator.HORIZONTAL</code> or
     *                           <code>JSeparator.VERTICAL</code>
     * @since 1.6
     */
    public void paintSeparatorBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h, int orientation) {
        paintBackground(context, g, x, y, w, h, orientation);
    }

    /**
     * Paints the border of a separator.
     *
     * <p>
     *  绘制分隔符的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintSeparatorBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of a separator. This implementation invokes the
     * method of the same name without the orientation.
     *
     * <p>
     *  绘制分隔符的边框。这个实现调用没有方向的同名方法。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     * @param orientation One of <code>JSeparator.HORIZONTAL</code> or
     *                           <code>JSeparator.VERTICAL</code>
     * @since 1.6
     */
    public void paintSeparatorBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h, int orientation) {
        paintBorder(context, g, x, y, w, h, orientation);
    }

    /**
     * Paints the foreground of a separator.
     *
     * <p>
     *  绘制分隔符的前景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     * @param orientation One of <code>JSeparator.HORIZONTAL</code> or
     *                           <code>JSeparator.VERTICAL</code>
     */
    public void paintSeparatorForeground(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h, int orientation) {
        paintForeground(context, g, x, y, w, h, orientation);
    }

    /**
     * Paints the background of a slider.
     *
     * <p>
     *  绘制滑块的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintSliderBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of a slider. This implementation invokes the
     * method of the same name without the orientation.
     *
     * <p>
     *  绘制滑块的背景。这个实现调用没有方向的同名方法。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     * @param orientation One of <code>JSlider.HORIZONTAL</code> or
     *                           <code>JSlider.VERTICAL</code>
     * @since 1.6
     */
    public void paintSliderBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h, int orientation) {
        paintBackground(context, g, x, y, w, h, orientation);
    }

    /**
     * Paints the border of a slider.
     *
     * <p>
     *  绘制滑块的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintSliderBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of a slider. This implementation invokes the
     * method of the same name without the orientation.
     *
     * <p>
     *  绘制滑块的边框。这个实现调用没有方向的同名方法。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     * @param orientation One of <code>JSlider.HORIZONTAL</code> or
     *                           <code>JSlider.VERTICAL</code>
     * @since 1.6
     */
    public void paintSliderBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h, int orientation) {
        paintBorder(context, g, x, y, w, h, orientation);
    }

    /**
     * Paints the background of the thumb of a slider.
     *
     * <p>
     *  绘制滑块的缩略图的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     * @param orientation One of <code>JSlider.HORIZONTAL</code> or
     *                           <code>JSlider.VERTICAL</code>
     */
    public void paintSliderThumbBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h, int orientation) {
        if (context.getComponent().getClientProperty(
                "Slider.paintThumbArrowShape") == Boolean.TRUE){
            if (orientation == JSlider.HORIZONTAL){
                orientation = JSlider.VERTICAL;
            } else {
                orientation = JSlider.HORIZONTAL;
            }
            paintBackground(context, g, x, y, w, h, orientation);
        } else {
            paintBackground(context, g, x, y, w, h, orientation);
        }
    }

    /**
     * Paints the border of the thumb of a slider.
     *
     * <p>
     *  绘制滑块的缩略图的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     * @param orientation One of <code>JSlider.HORIZONTAL</code> or
     *                           <code>JSlider.VERTICAL</code>
     */
    public void paintSliderThumbBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h, int orientation) {
        paintBorder(context, g, x, y, w, h, orientation);
    }

    /**
     * Paints the background of the track of a slider.
     *
     * <p>
     *  描绘滑块轨迹的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintSliderTrackBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of the track of a slider. This implementation invokes
     * the method of the same name without the orientation.
     *
     * <p>
     *  描绘滑块轨迹的背景。这个实现调用没有方向的同名方法。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     * @param orientation One of <code>JSlider.HORIZONTAL</code> or
     *                           <code>JSlider.VERTICAL</code>
     * @since 1.6
     */
    public void paintSliderTrackBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h, int orientation) {
        paintBackground(context, g, x, y, w, h, orientation);
    }

    /**
     * Paints the border of the track of a slider.
     *
     * <p>
     *  绘制滑块轨迹的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintSliderTrackBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of the track of a slider. This implementation invokes the
     * method of the same name without the orientation.
     *
     * <p>
     *  绘制滑块轨迹的边框。这个实现调用没有方向的同名方法。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     * @param orientation One of <code>JSlider.HORIZONTAL</code> or
     *                           <code>JSlider.VERTICAL</code>
     * @since 1.6
     */
    public void paintSliderTrackBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h, int orientation) {
        paintBorder(context, g, x, y, w, h, orientation);
    }

    /**
     * Paints the background of a spinner.
     *
     * <p>
     *  绘制旋转器的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintSpinnerBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of a spinner.
     *
     * <p>
     *  绘制微调框的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintSpinnerBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of the divider of a split pane.
     *
     * <p>
     *  绘制分割窗格的分隔线的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintSplitPaneDividerBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of the divider of a split pane. This implementation
     * invokes the method of the same name without the orientation.
     *
     * <p>
     *  绘制分割窗格的分隔线的背景。这个实现调用没有方向的同名方法。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     * @param orientation One of <code>JSplitPane.HORIZONTAL_SPLIT</code> or
     *                           <code>JSplitPane.VERTICAL_SPLIT</code>
     * @since 1.6
     */
    public void paintSplitPaneDividerBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h, int orientation) {
       if (orientation == JSplitPane.HORIZONTAL_SPLIT) {
            AffineTransform transform = new AffineTransform();
            transform.scale(-1, 1);
            transform.rotate(Math.toRadians(90));
            paintBackground(context, g, y, x, h, w, transform);
       } else {
            paintBackground(context, g, x, y, w, h, null);
        }
    }

    /**
     * Paints the foreground of the divider of a split pane.
     *
     * <p>
     *  绘制拆分窗格的分隔符的前景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     * @param orientation One of <code>JSplitPane.HORIZONTAL_SPLIT</code> or
     *                           <code>JSplitPane.VERTICAL_SPLIT</code>
     */
    public void paintSplitPaneDividerForeground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h, int orientation) {
        paintForeground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the divider, when the user is dragging the divider, of a
     * split pane.
     *
     * <p>
     *  在分割窗格中绘制分隔线,当用户拖动分隔线时。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     * @param orientation One of <code>JSplitPane.HORIZONTAL_SPLIT</code> or
     *                           <code>JSplitPane.VERTICAL_SPLIT</code>
     */
    public void paintSplitPaneDragDivider(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h, int orientation) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of a split pane.
     *
     * <p>
     *  绘制拆分窗格的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintSplitPaneBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of a split pane.
     *
     * <p>
     * 绘制拆分窗格的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintSplitPaneBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of a tabbed pane.
     *
     * <p>
     *  绘制选项卡式窗格的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintTabbedPaneBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of a tabbed pane.
     *
     * <p>
     *  绘制选项卡式窗格的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintTabbedPaneBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of the area behind the tabs of a tabbed pane.
     *
     * <p>
     *  在选项卡式窗格的选项卡后面绘制区域的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintTabbedPaneTabAreaBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of the area behind the tabs of a tabbed pane.
     * This implementation invokes the method of the same name without the
     * orientation.
     *
     * <p>
     *  在选项卡式窗格的选项卡后面绘制区域的背景。这个实现调用没有方向的同名方法。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     * @param orientation One of <code>JTabbedPane.TOP</code>,
     *                    <code>JTabbedPane.LEFT</code>,
     *                    <code>JTabbedPane.BOTTOM</code>, or
     *                    <code>JTabbedPane.RIGHT</code>
     * @since 1.6
     */
    public void paintTabbedPaneTabAreaBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h, int orientation) {
        if (orientation == JTabbedPane.LEFT) {
            AffineTransform transform = new AffineTransform();
            transform.scale(-1, 1);
            transform.rotate(Math.toRadians(90));
            paintBackground(context, g, y, x, h, w, transform);
        } else if (orientation == JTabbedPane.RIGHT) {
            AffineTransform transform = new AffineTransform();
            transform.rotate(Math.toRadians(90));
            transform.translate(0, -(x + w));
            paintBackground(context, g, y, 0, h, w, transform);
        } else if (orientation == JTabbedPane.BOTTOM) {
            AffineTransform transform = new AffineTransform();
            transform.translate(x,y);
            transform.scale(1, -1);
            transform.translate(0,-h);
            paintBackground(context, g, 0, 0, w, h, transform);
        } else {
            paintBackground(context, g, x, y, w, h, null);
        }
    }

    /**
     * Paints the border of the area behind the tabs of a tabbed pane.
     *
     * <p>
     *  在选项卡式窗格的选项卡后面绘制区域的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintTabbedPaneTabAreaBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of the area behind the tabs of a tabbed pane. This
     * implementation invokes the method of the same name without the orientation.
     *
     * <p>
     *  在选项卡式窗格的选项卡后面绘制区域的边框。这个实现调用没有方向的同名方法。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     * @param orientation One of <code>JTabbedPane.TOP</code>,
     *                    <code>JTabbedPane.LEFT</code>,
     *                    <code>JTabbedPane.BOTTOM</code>, or
     *                    <code>JTabbedPane.RIGHT</code>
     * @since 1.6
     */
    public void paintTabbedPaneTabAreaBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h, int orientation) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of a tab of a tabbed pane.
     *
     * <p>
     *  绘制选项卡式窗格的选项卡的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     * @param tabIndex Index of tab being painted.
     */
    public void paintTabbedPaneTabBackground(SynthContext context, Graphics g,
                                         int x, int y, int w, int h,
                                         int tabIndex) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of a tab of a tabbed pane. This implementation
     * invokes the method of the same name without the orientation.
     *
     * <p>
     *  绘制选项卡式窗格的选项卡的背景。这个实现调用没有方向的同名方法。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     * @param tabIndex Index of tab being painted.
     * @param orientation One of <code>JTabbedPane.TOP</code>,
     *                    <code>JTabbedPane.LEFT</code>,
     *                    <code>JTabbedPane.BOTTOM</code>, or
     *                    <code>JTabbedPane.RIGHT</code>
     * @since 1.6
     */
    public void paintTabbedPaneTabBackground(SynthContext context, Graphics g,
                                         int x, int y, int w, int h,
                                         int tabIndex, int orientation) {
        if (orientation == JTabbedPane.LEFT) {
            AffineTransform transform = new AffineTransform();
            transform.scale(-1, 1);
            transform.rotate(Math.toRadians(90));
            paintBackground(context, g, y, x, h, w, transform);
        } else if (orientation == JTabbedPane.RIGHT) {
            AffineTransform transform = new AffineTransform();
            transform.rotate(Math.toRadians(90));
            transform.translate(0, -(x + w));
            paintBackground(context, g, y, 0, h, w, transform);
        } else if (orientation == JTabbedPane.BOTTOM) {
            AffineTransform transform = new AffineTransform();
            transform.translate(x,y);
            transform.scale(1, -1);
            transform.translate(0,-h);
            paintBackground(context, g, 0, 0, w, h, transform);
        } else {
            paintBackground(context, g, x, y, w, h, null);
        }
    }

    /**
     * Paints the border of a tab of a tabbed pane.
     *
     * <p>
     *  绘制选项卡式窗格的选项卡的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     * @param tabIndex Index of tab being painted.
     */
    public void paintTabbedPaneTabBorder(SynthContext context, Graphics g,
                                         int x, int y, int w, int h,
                                         int tabIndex) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of a tab of a tabbed pane. This implementation invokes
     * the method of the same name without the orientation.
     *
     * <p>
     *  绘制选项卡式窗格的选项卡的边框。这个实现调用没有方向的同名方法。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     * @param tabIndex Index of tab being painted.
     * @param orientation One of <code>JTabbedPane.TOP</code>,
     *                    <code>JTabbedPane.LEFT</code>,
     *                    <code>JTabbedPane.BOTTOM</code>, or
     *                    <code>JTabbedPane.RIGHT</code>
     * @since 1.6
     */
    public void paintTabbedPaneTabBorder(SynthContext context, Graphics g,
                                         int x, int y, int w, int h,
                                         int tabIndex, int orientation) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of the area that contains the content of the
     * selected tab of a tabbed pane.
     *
     * <p>
     *  绘制包含选项卡式窗格的所选选项卡内容的区域的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintTabbedPaneContentBackground(SynthContext context,
                                         Graphics g, int x, int y, int w,
                                         int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of the area that contains the content of the
     * selected tab of a tabbed pane.
     *
     * <p>
     *  绘制包含选项卡式窗格的所选选项卡内容的区域的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintTabbedPaneContentBorder(SynthContext context, Graphics g,
                                         int x, int y, int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of the header of a table.
     *
     * <p>
     *  描绘表头的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintTableHeaderBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of the header of a table.
     *
     * <p>
     *  描画表头的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintTableHeaderBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of a table.
     *
     * <p>
     *  描绘表的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintTableBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of a table.
     *
     * <p>
     *  绘制表的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintTableBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of a text area.
     *
     * <p>
     *  描绘文本区域的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintTextAreaBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of a text area.
     *
     * <p>
     *  绘制文本区域的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintTextAreaBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of a text pane.
     *
     * <p>
     *  描绘文本窗格的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintTextPaneBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of a text pane.
     *
     * <p>
     *  绘制文本窗格的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintTextPaneBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of a text field.
     *
     * <p>
     *  描绘文本字段的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintTextFieldBackground(SynthContext context,
                                          Graphics g, int x, int y,
                                          int w, int h) {
        if (context.getComponent().getComponentOrientation().isLeftToRight()){
            paintBackground(context, g, x, y, w, h, null);
        } else {
            AffineTransform transform = new AffineTransform();
            transform.translate(x,y);
            transform.scale(-1, 1);
            transform.translate(-w,0);
            paintBackground(context, g, 0, 0, w, h, transform);
        }
    }

    /**
     * Paints the border of a text field.
     *
     * <p>
     *  绘制文本字段的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintTextFieldBorder(SynthContext context,
                                      Graphics g, int x, int y,
                                      int w, int h) {
        if (context.getComponent().getComponentOrientation().isLeftToRight()){
            paintBorder(context, g, x, y, w, h, null);
        } else {
            AffineTransform transform = new AffineTransform();
            transform.translate(x,y);
            transform.scale(-1, 1);
            transform.translate(-w,0);
            paintBorder(context, g, 0, 0, w, h, transform);
        }
    }

    /**
     * Paints the background of a toggle button.
     *
     * <p>
     * 描绘切换按钮的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintToggleButtonBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of a toggle button.
     *
     * <p>
     *  绘制切换按钮的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintToggleButtonBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of a tool bar.
     *
     * <p>
     *  绘制工具栏的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintToolBarBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of a tool bar. This implementation invokes the
     * method of the same name without the orientation.
     *
     * <p>
     *  绘制工具栏的背景。这个实现调用没有方向的同名方法。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     * @param orientation One of <code>JToolBar.HORIZONTAL</code> or
     *                           <code>JToolBar.VERTICAL</code>
     * @since 1.6
     */
    public void paintToolBarBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h, int orientation) {
        paintBackground(context, g, x, y, w, h, orientation);
    }

    /**
     * Paints the border of a tool bar.
     *
     * <p>
     *  绘制工具栏的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintToolBarBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of a tool bar. This implementation invokes the
     * method of the same name without the orientation.
     *
     * <p>
     *  绘制工具栏的边框。这个实现调用没有方向的同名方法。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     * @param orientation One of <code>JToolBar.HORIZONTAL</code> or
     *                           <code>JToolBar.VERTICAL</code>
     * @since 1.6
     */
    public void paintToolBarBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h, int orientation) {
        paintBorder(context, g, x, y, w, h, orientation);
    }

    /**
     * Paints the background of the tool bar's content area.
     *
     * <p>
     *  描绘工具栏内容区域的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintToolBarContentBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of the tool bar's content area. This implementation
     * invokes the method of the same name without the orientation.
     *
     * <p>
     *  描绘工具栏内容区域的背景。这个实现调用没有方向的同名方法。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     * @param orientation One of <code>JToolBar.HORIZONTAL</code> or
     *                           <code>JToolBar.VERTICAL</code>
     * @since 1.6
     */
    public void paintToolBarContentBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h, int orientation) {
        paintBackground(context, g, x, y, w, h, orientation);
    }

    /**
     * Paints the border of the content area of a tool bar.
     *
     * <p>
     *  绘制工具栏的内容区域的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintToolBarContentBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of the content area of a tool bar. This implementation
     * invokes the method of the same name without the orientation.
     *
     * <p>
     *  绘制工具栏的内容区域的边框。这个实现调用没有方向的同名方法。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     * @param orientation One of <code>JToolBar.HORIZONTAL</code> or
     *                           <code>JToolBar.VERTICAL</code>
     * @since 1.6
     */
    public void paintToolBarContentBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h, int orientation) {
        paintBorder(context, g, x, y, w, h, orientation);
    }

    /**
     * Paints the background of the window containing the tool bar when it
     * has been detached from its primary frame.
     *
     * <p>
     *  绘制包含工具栏的窗口与主框架分离时的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintToolBarDragWindowBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of the window containing the tool bar when it
     * has been detached from its primary frame. This implementation invokes the
     * method of the same name without the orientation.
     *
     * <p>
     *  绘制包含工具栏的窗口与主框架分离时的背景。这个实现调用没有方向的同名方法。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     * @param orientation One of <code>JToolBar.HORIZONTAL</code> or
     *                           <code>JToolBar.VERTICAL</code>
     * @since 1.6
     */
    public void paintToolBarDragWindowBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h, int orientation) {
        paintBackground(context, g, x, y, w, h, orientation);
    }

    /**
     * Paints the border of the window containing the tool bar when it
     * has been detached from it's primary frame.
     *
     * <p>
     *  绘制包含工具栏的窗口已从主框架分离时的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintToolBarDragWindowBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of the window containing the tool bar when it
     * has been detached from it's primary frame. This implementation invokes the
     * method of the same name without the orientation.
     *
     * <p>
     *  绘制包含工具栏的窗口已从主框架分离时的边框。这个实现调用没有方向的同名方法。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     * @param orientation One of <code>JToolBar.HORIZONTAL</code> or
     *                           <code>JToolBar.VERTICAL</code>
     * @since 1.6
     */
    public void paintToolBarDragWindowBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h, int orientation) {
        paintBorder(context, g, x, y, w, h, orientation);
    }

    /**
     * Paints the background of a tool tip.
     *
     * <p>
     *  绘制工具提示的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintToolTipBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of a tool tip.
     *
     * <p>
     *  绘制刀尖的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintToolTipBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of a tree.
     *
     * <p>
     *  画一棵树的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintTreeBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of a tree.
     *
     * <p>
     * 画一棵树的边界。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintTreeBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the background of the row containing a cell in a tree.
     *
     * <p>
     *  在树中绘制包含单元格的行的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintTreeCellBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of the row containing a cell in a tree.
     *
     * <p>
     *  在树中绘制包含单元格的行的边框。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintTreeCellBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }

    /**
     * Paints the focus indicator for a cell in a tree when it has focus.
     *
     * <p>
     *  在树中的单元格具有焦点时绘制聚焦指示符。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintTreeCellFocus(SynthContext context,
                                   Graphics g, int x, int y,
                                   int w, int h) {
        //TODO
    }

    /**
     * Paints the background of the viewport.
     *
     * <p>
     *  绘制视口的背景。
     * 
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintViewportBackground(SynthContext context,
                                     Graphics g, int x, int y,
                                     int w, int h) {
        paintBackground(context, g, x, y, w, h, null);
    }

    /**
     * Paints the border of a viewport.
     *
     * <p>
     *  绘制视口的边框。
     * 
     * @param context SynthContext identifying the <code>JComponent</code> and
     *        <code>Region</code> to paint to
     * @param g <code>Graphics</code> to paint to
     * @param x X coordinate of the area to paint to
     * @param y Y coordinate of the area to paint to
     * @param w Width of the area to paint to
     * @param h Height of the area to paint to
     */
    public void paintViewportBorder(SynthContext context,
                                 Graphics g, int x, int y,
                                 int w, int h) {
        paintBorder(context, g, x, y, w, h, null);
    }
}
