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
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Component;
import java.beans.ConstructorProperties;

/**
 * A class which implements a simple etched border which can
 * either be etched-in or etched-out.  If no highlight/shadow
 * colors are initialized when the border is created, then
 * these colors will be dynamically derived from the background
 * color of the component argument passed into the paintBorder()
 * method.
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
 *  实现简单蚀刻边界的类,其可以被蚀刻或蚀刻掉。如果在创建边框时没有初始化高亮/阴影颜色,则这些颜色将从传递到paintBorder()方法的组件参数的背景颜色动态导出。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author David Kloba
 * @author Amy Fowler
 */
public class EtchedBorder extends AbstractBorder
{
    /** Raised etched type. */
    public static final int RAISED  = 0;
    /** Lowered etched type. */
    public static final int LOWERED = 1;

    protected int etchType;
    protected Color highlight;
    protected Color shadow;

    /**
     * Creates a lowered etched border whose colors will be derived
     * from the background color of the component passed into
     * the paintBorder method.
     * <p>
     *  创建一个降低的蚀刻边框,其颜色将从传递到paintBorder方法的组件的背景颜色派生。
     * 
     */
    public EtchedBorder()    {
        this(LOWERED);
    }

    /**
     * Creates an etched border with the specified etch-type
     * whose colors will be derived
     * from the background color of the component passed into
     * the paintBorder method.
     * <p>
     *  创建具有指定蚀刻类型的蚀刻边框,其颜色将从传递到paintBorder方法的组件的背景颜色派生。
     * 
     * 
     * @param etchType the type of etch to be drawn by the border
     */
    public EtchedBorder(int etchType)    {
        this(etchType, null, null);
    }

    /**
     * Creates a lowered etched border with the specified highlight and
     * shadow colors.
     * <p>
     *  创建具有指定的高光和阴影颜色的蚀刻边框。
     * 
     * 
     * @param highlight the color to use for the etched highlight
     * @param shadow the color to use for the etched shadow
     */
    public EtchedBorder(Color highlight, Color shadow)    {
        this(LOWERED, highlight, shadow);
    }

    /**
     * Creates an etched border with the specified etch-type,
     * highlight and shadow colors.
     * <p>
     *  创建具有指定的蚀刻类型,高亮和阴影颜色的蚀刻边框。
     * 
     * 
     * @param etchType the type of etch to be drawn by the border
     * @param highlight the color to use for the etched highlight
     * @param shadow the color to use for the etched shadow
     */
    @ConstructorProperties({"etchType", "highlightColor", "shadowColor"})
    public EtchedBorder(int etchType, Color highlight, Color shadow)    {
        this.etchType = etchType;
        this.highlight = highlight;
        this.shadow = shadow;
    }

    /**
     * Paints the border for the specified component with the
     * specified position and size.
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
        int w = width;
        int h = height;

        g.translate(x, y);

        g.setColor(etchType == LOWERED? getShadowColor(c) : getHighlightColor(c));
        g.drawRect(0, 0, w-2, h-2);

        g.setColor(etchType == LOWERED? getHighlightColor(c) : getShadowColor(c));
        g.drawLine(1, h-3, 1, 1);
        g.drawLine(1, 1, w-3, 1);

        g.drawLine(0, h-1, w-1, h-1);
        g.drawLine(w-1, h-1, w-1, 0);

        g.translate(-x, -y);
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
     * Returns whether or not the border is opaque.
     * <p>
     * 返回边框是否不透明。
     * 
     */
    public boolean isBorderOpaque() { return true; }

    /**
     * Returns which etch-type is set on the etched border.
     * <p>
     *  返回在蚀刻边框上设置的蚀刻类型。
     * 
     */
    public int getEtchType() {
        return etchType;
    }

    /**
     * Returns the highlight color of the etched border
     * when rendered on the specified component.  If no highlight
     * color was specified at instantiation, the highlight color
     * is derived from the specified component's background color.
     * <p>
     *  返回在指定组件上呈现时蚀刻边框的突出显示颜色。如果在实例化时未指定高亮颜色,则高亮颜色来自指定组件的背景颜色。
     * 
     * 
     * @param c the component for which the highlight may be derived
     * @since 1.3
     */
    public Color getHighlightColor(Component c)   {
        return highlight != null? highlight :
                                       c.getBackground().brighter();
    }

    /**
     * Returns the highlight color of the etched border.
     * Will return null if no highlight color was specified
     * at instantiation.
     * <p>
     *  返回蚀刻边框的突出显示颜色。如果在实例化时未指定高亮颜色,则返回null。
     * 
     * 
     * @since 1.3
     */
    public Color getHighlightColor()   {
        return highlight;
    }

    /**
     * Returns the shadow color of the etched border
     * when rendered on the specified component.  If no shadow
     * color was specified at instantiation, the shadow color
     * is derived from the specified component's background color.
     * <p>
     *  返回在指定组件上呈现时蚀刻边框的阴影颜色。如果在实例化时未指定阴影颜色,则阴影颜色来自指定的组件的背景颜色。
     * 
     * 
     * @param c the component for which the shadow may be derived
     * @since 1.3
     */
    public Color getShadowColor(Component c)   {
        return shadow != null? shadow : c.getBackground().darker();
    }

    /**
     * Returns the shadow color of the etched border.
     * Will return null if no shadow color was specified
     * at instantiation.
     * <p>
     *  返回蚀刻边框的阴影颜色。如果在实例化时未指定阴影颜色,则返回null。
     * 
     * @since 1.3
     */
    public Color getShadowColor()   {
        return shadow;
    }

}
