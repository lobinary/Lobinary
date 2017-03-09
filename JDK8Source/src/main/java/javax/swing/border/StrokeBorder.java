/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2010, 2011, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.beans.ConstructorProperties;

/**
 * A class which implements a border of an arbitrary stroke.
 * <p>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases. The current serialization support is
 * appropriate for short term storage or RMI
 * between applications running the same version of Swing.
 * As of 1.4, support for long term storage of all JavaBeans&trade;
 * has been added to the <code>java.beans</code> package.
 * Please see {@link java.beans.XMLEncoder}.
 *
 * <p>
 *  实现任意笔划边框的类。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author Sergey A. Malenkov
 *
 * @since 1.7
 */
public class StrokeBorder extends AbstractBorder {
    private final BasicStroke stroke;
    private final Paint paint;

    /**
     * Creates a border of the specified {@code stroke}.
     * The component's foreground color will be used to render the border.
     *
     * <p>
     *  创建指定{@code stroke}的边框。组件的前景颜色将用于渲染边框。
     * 
     * 
     * @param stroke  the {@link BasicStroke} object used to stroke a shape
     *
     * @throws NullPointerException if the specified {@code stroke} is {@code null}
     */
    public StrokeBorder(BasicStroke stroke) {
        this(stroke, null);
    }

    /**
     * Creates a border of the specified {@code stroke} and {@code paint}.
     * If the specified {@code paint} is {@code null},
     * the component's foreground color will be used to render the border.
     *
     * <p>
     *  创建指定的{@code stroke}和{@code paint}的边框。如果指定的{@code paint}是{@code null},组件的前景颜色将用于渲染边框。
     * 
     * 
     * @param stroke  the {@link BasicStroke} object used to stroke a shape
     * @param paint   the {@link Paint} object used to generate a color
     *
     * @throws NullPointerException if the specified {@code stroke} is {@code null}
     */
    @ConstructorProperties({ "stroke", "paint" })
    public StrokeBorder(BasicStroke stroke, Paint paint) {
        if (stroke == null) {
            throw new NullPointerException("border's stroke");
        }
        this.stroke = stroke;
        this.paint = paint;
    }

    /**
     * Paints the border for the specified component
     * with the specified position and size.
     * If the border was not specified with a {@link Paint} object,
     * the component's foreground color will be used to render the border.
     * If the component's foreground color is not available,
     * the default color of the {@link Graphics} object will be used.
     *
     * <p>
     *  以指定的位置和大小绘制指定组件的边框。如果未使用{@link Paint}对象指定边框,则组件的前景颜色将用于渲染边框。如果组件的前景色不可用,将使用{@link Graphics}对象的默认颜色。
     * 
     * 
     * @param c       the component for which this border is being painted
     * @param g       the paint graphics
     * @param x       the x position of the painted border
     * @param y       the y position of the painted border
     * @param width   the width of the painted border
     * @param height  the height of the painted border
     *
     * @throws NullPointerException if the specified {@code g} is {@code null}
     */
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        float size = this.stroke.getLineWidth();
        if (size > 0.0f) {
            g = g.create();
            if (g instanceof Graphics2D) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setStroke(this.stroke);
                g2d.setPaint(this.paint != null ? this.paint : c == null ? null : c.getForeground());
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                     RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.draw(new Rectangle2D.Float(x + size / 2, y + size / 2, width - size, height - size));
            }
            g.dispose();
        }
    }

    /**
     * Reinitializes the {@code insets} parameter
     * with this border's current insets.
     * Every inset is the smallest (closest to negative infinity) integer value
     * that is greater than or equal to the line width of the stroke
     * that is used to paint the border.
     *
     * <p>
     *  使用此边框的当前插图重新初始化{@code insets}参数。每个插图都是最小(最接近负无穷大)的整数值,大于或等于用于绘制边框的笔触的线宽。
     * 
     * 
     * @param c       the component for which this border insets value applies
     * @param insets  the {@code Insets} object to be reinitialized
     * @return the reinitialized {@code insets} parameter
     *
     * @throws NullPointerException if the specified {@code insets} is {@code null}
     *
     * @see Math#ceil
     */
    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        int size = (int) Math.ceil(this.stroke.getLineWidth());
        insets.set(size, size, size, size);
        return insets;
    }

    /**
     * Returns the {@link BasicStroke} object used to stroke a shape
     * during the border rendering.
     *
     * <p>
     * 返回用于在边框渲染过程中绘制形状的{@link BasicStroke}对象。
     * 
     * 
     * @return the {@link BasicStroke} object
     */
    public BasicStroke getStroke() {
        return this.stroke;
    }

    /**
     * Returns the {@link Paint} object used to generate a color
     * during the border rendering.
     *
     * <p>
     *  返回用于在边框渲染过程中生成颜色的{@link Paint}对象。
     * 
     * @return the {@link Paint} object or {@code null}
     *         if the {@code paint} parameter is not set
     */
    public Paint getPaint() {
        return this.paint;
    }
}
