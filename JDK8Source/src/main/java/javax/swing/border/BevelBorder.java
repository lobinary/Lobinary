/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing.border;

import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Component;
import java.beans.ConstructorProperties;

/**
 * A class which implements a simple two-line bevel border.
 * <p>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running
 * the same version of Swing.  As of 1.4, support for long term storage
 * of all JavaBeans&trade;
 * has been added to the <code>java.beans</code> package.
 * Please see {@link java.beans.XMLEncoder}.
 *
 * <p>
 *  实现一个简单的两行斜面边界的类。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author David Kloba
 */
public class BevelBorder extends AbstractBorder
{
    /** Raised bevel type. */
    public static final int RAISED  = 0;
    /** Lowered bevel type. */
    public static final int LOWERED = 1;

    protected int bevelType;
    protected Color highlightOuter;
    protected Color highlightInner;
    protected Color shadowInner;
    protected Color shadowOuter;

    /**
     * Creates a bevel border with the specified type and whose
     * colors will be derived from the background color of the
     * component passed into the paintBorder method.
     * <p>
     *  创建具有指定类型的斜角边框,其颜色将从传递到paintBorder方法的组件的背景颜色派生。
     * 
     * 
     * @param bevelType the type of bevel for the border
     */
    public BevelBorder(int bevelType) {
        this.bevelType = bevelType;
    }

    /**
     * Creates a bevel border with the specified type, highlight and
     * shadow colors.
     * <p>
     *  创建具有指定类型,高亮和阴影颜色的斜角边框。
     * 
     * 
     * @param bevelType the type of bevel for the border
     * @param highlight the color to use for the bevel highlight
     * @param shadow the color to use for the bevel shadow
     */
    public BevelBorder(int bevelType, Color highlight, Color shadow) {
        this(bevelType, highlight.brighter(), highlight, shadow, shadow.brighter());
    }

    /**
     * Creates a bevel border with the specified type, highlight and
     * shadow colors.
     *
     * <p>
     *  创建具有指定类型,高亮和阴影颜色的斜角边框。
     * 
     * 
     * @param bevelType the type of bevel for the border
     * @param highlightOuterColor the color to use for the bevel outer highlight
     * @param highlightInnerColor the color to use for the bevel inner highlight
     * @param shadowOuterColor the color to use for the bevel outer shadow
     * @param shadowInnerColor the color to use for the bevel inner shadow
     */
    @ConstructorProperties({"bevelType", "highlightOuterColor", "highlightInnerColor", "shadowOuterColor", "shadowInnerColor"})
    public BevelBorder(int bevelType, Color highlightOuterColor,
                       Color highlightInnerColor, Color shadowOuterColor,
                       Color shadowInnerColor) {
        this(bevelType);
        this.highlightOuter = highlightOuterColor;
        this.highlightInner = highlightInnerColor;
        this.shadowOuter = shadowOuterColor;
        this.shadowInner = shadowInnerColor;
    }

    /**
     * Paints the border for the specified component with the specified
     * position and size.
     * <p>
     *  以指定的位置和大小绘制指定组件的边框。
     * 
     * 
     * @param c the component for which this border is being painted
     * @param g the paint graphics
     * @param x the x position of the painted border
     * @param y the y position of the painted border
     * @param width the width of the painted border
     * @param height the height of the painted border
     */
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        if (bevelType == RAISED) {
             paintRaisedBevel(c, g, x, y, width, height);

        } else if (bevelType == LOWERED) {
             paintLoweredBevel(c, g, x, y, width, height);
        }
    }

    /**
     * Reinitialize the insets parameter with this Border's current Insets.
     * <p>
     *  使用此Border的当前Insets重新初始化insets参数。
     * 
     * 
     * @param c the component for which this border insets value applies
     * @param insets the object to be reinitialized
     */
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.set(2, 2, 2, 2);
        return insets;
    }

    /**
     * Returns the outer highlight color of the bevel border
     * when rendered on the specified component.  If no highlight
     * color was specified at instantiation, the highlight color
     * is derived from the specified component's background color.
     * <p>
     *  返回在指定组件上呈现时斜角边框的外部高亮颜色。如果在实例化时未指定高亮颜色,则高亮颜色来自指定组件的背景颜色。
     * 
     * 
     * @param c the component for which the highlight may be derived
     * @since 1.3
     */
    public Color getHighlightOuterColor(Component c)   {
        Color highlight = getHighlightOuterColor();
        return highlight != null? highlight :
                                       c.getBackground().brighter().brighter();
    }

    /**
     * Returns the inner highlight color of the bevel border
     * when rendered on the specified component.  If no highlight
     * color was specified at instantiation, the highlight color
     * is derived from the specified component's background color.
     * <p>
     * 返回在指定组件上呈现时斜角边框的内部高亮颜色。如果在实例化时未指定高亮颜色,则高亮颜色来自指定组件的背景颜色。
     * 
     * 
     * @param c the component for which the highlight may be derived
     * @since 1.3
     */
    public Color getHighlightInnerColor(Component c)   {
        Color highlight = getHighlightInnerColor();
        return highlight != null? highlight :
                                       c.getBackground().brighter();
    }

    /**
     * Returns the inner shadow color of the bevel border
     * when rendered on the specified component.  If no shadow
     * color was specified at instantiation, the shadow color
     * is derived from the specified component's background color.
     * <p>
     *  返回在指定组件上呈现时斜角边框的内部阴影颜色。如果在实例化时未指定阴影颜色,则阴影颜色来自指定的组件的背景颜色。
     * 
     * 
     * @param c the component for which the shadow may be derived
     * @since 1.3
     */
    public Color getShadowInnerColor(Component c)      {
        Color shadow = getShadowInnerColor();
        return shadow != null? shadow :
                                    c.getBackground().darker();
    }

    /**
     * Returns the outer shadow color of the bevel border
     * when rendered on the specified component.  If no shadow
     * color was specified at instantiation, the shadow color
     * is derived from the specified component's background color.
     * <p>
     *  返回在指定组件上呈现时斜角边框的外部阴影颜色。如果在实例化时未指定阴影颜色,则阴影颜色来自指定的组件的背景颜色。
     * 
     * 
     * @param c the component for which the shadow may be derived
     * @since 1.3
     */
    public Color getShadowOuterColor(Component c)      {
        Color shadow = getShadowOuterColor();
        return shadow != null? shadow :
                                    c.getBackground().darker().darker();
    }

    /**
     * Returns the outer highlight color of the bevel border.
     * Will return null if no highlight color was specified
     * at instantiation.
     * <p>
     *  返回斜角边框的外部高亮颜色。如果在实例化时未指定高亮颜色,则返回null。
     * 
     * 
     * @since 1.3
     */
    public Color getHighlightOuterColor()   {
        return highlightOuter;
    }

    /**
     * Returns the inner highlight color of the bevel border.
     * Will return null if no highlight color was specified
     * at instantiation.
     * <p>
     *  返回斜角边框的内部高亮颜色。如果在实例化时未指定高亮颜色,则返回null。
     * 
     * 
     * @since 1.3
     */
    public Color getHighlightInnerColor()   {
        return highlightInner;
    }

    /**
     * Returns the inner shadow color of the bevel border.
     * Will return null if no shadow color was specified
     * at instantiation.
     * <p>
     *  返回斜角边框的内部阴影颜色。如果在实例化时未指定阴影颜色,则返回null。
     * 
     * 
     * @since 1.3
     */
    public Color getShadowInnerColor()      {
        return shadowInner;
    }

    /**
     * Returns the outer shadow color of the bevel border.
     * Will return null if no shadow color was specified
     * at instantiation.
     * <p>
     *  返回斜角边框的外阴影颜色。如果在实例化时未指定阴影颜色,则返回null。
     * 
     * 
     * @since 1.3
     */
    public Color getShadowOuterColor()      {
        return shadowOuter;
    }

    /**
     * Returns the type of the bevel border.
     * <p>
     *  返回斜角边框的类型。
     * 
     */
    public int getBevelType()       {
        return bevelType;
    }

    /**
     * Returns whether or not the border is opaque.
     * <p>
     *  返回边框是否不透明。
     */
    public boolean isBorderOpaque() { return true; }

    protected void paintRaisedBevel(Component c, Graphics g, int x, int y,
                                    int width, int height)  {
        Color oldColor = g.getColor();
        int h = height;
        int w = width;

        g.translate(x, y);

        g.setColor(getHighlightOuterColor(c));
        g.drawLine(0, 0, 0, h-2);
        g.drawLine(1, 0, w-2, 0);

        g.setColor(getHighlightInnerColor(c));
        g.drawLine(1, 1, 1, h-3);
        g.drawLine(2, 1, w-3, 1);

        g.setColor(getShadowOuterColor(c));
        g.drawLine(0, h-1, w-1, h-1);
        g.drawLine(w-1, 0, w-1, h-2);

        g.setColor(getShadowInnerColor(c));
        g.drawLine(1, h-2, w-2, h-2);
        g.drawLine(w-2, 1, w-2, h-3);

        g.translate(-x, -y);
        g.setColor(oldColor);

    }

    protected void paintLoweredBevel(Component c, Graphics g, int x, int y,
                                        int width, int height)  {
        Color oldColor = g.getColor();
        int h = height;
        int w = width;

        g.translate(x, y);

        g.setColor(getShadowInnerColor(c));
        g.drawLine(0, 0, 0, h-1);
        g.drawLine(1, 0, w-1, 0);

        g.setColor(getShadowOuterColor(c));
        g.drawLine(1, 1, 1, h-2);
        g.drawLine(2, 1, w-2, 1);

        g.setColor(getHighlightOuterColor(c));
        g.drawLine(1, h-1, w-1, h-1);
        g.drawLine(w-1, 1, w-1, h-2);

        g.setColor(getHighlightInnerColor(c));
        g.drawLine(2, h-2, w-2, h-2);
        g.drawLine(w-2, 2, w-2, h-3);

        g.translate(-x, -y);
        g.setColor(oldColor);

    }

}
