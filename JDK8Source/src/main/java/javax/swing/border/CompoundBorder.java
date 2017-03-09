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
import java.awt.Component;
import java.beans.ConstructorProperties;

/**
 * A composite Border class used to compose two Border objects
 * into a single border by nesting an inside Border object within
 * the insets of an outside Border object.
 *
 * For example, this class may be used to add blank margin space
 * to a component with an existing decorative border:
 *
 * <pre>
 *    Border border = comp.getBorder();
 *    Border margin = new EmptyBorder(10,10,10,10);
 *    comp.setBorder(new CompoundBorder(border, margin));
 * </pre>
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
 *  一个复合Border类,用于通过在一个外部Border对象的嵌套内嵌入一个内部Border对象,将两个Border对象组合成一个单个边框。
 * 
 *  例如,此类可用于向具有现有装饰边框的组件添加空白边距空间：
 * 
 * <pre>
 *  border border = comp.getBorder();边距margin = new EmptyBorder(10,10,10,10); comp.setBorder(new Compoun
 * dBorder(border,margin));。
 * </pre>
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author David Kloba
 */
@SuppressWarnings("serial")
public class CompoundBorder extends AbstractBorder {
    protected Border outsideBorder;
    protected Border insideBorder;

    /**
     * Creates a compound border with null outside and inside borders.
     * <p>
     *  创建带有空外部和内部边框的复合边框。
     * 
     */
    public CompoundBorder() {
        this.outsideBorder = null;
        this.insideBorder = null;
    }

    /**
     * Creates a compound border with the specified outside and
     * inside borders.  Either border may be null.
     * <p>
     *  使用指定的外部和内部边框创建复合边框。任一边界可以为null。
     * 
     * 
     * @param outsideBorder the outside border
     * @param insideBorder the inside border to be nested
     */
    @ConstructorProperties({"outsideBorder", "insideBorder"})
    public CompoundBorder(Border outsideBorder, Border insideBorder) {
        this.outsideBorder = outsideBorder;
        this.insideBorder = insideBorder;
    }

    /**
     * Returns whether or not the compound border is opaque.
     *
     * <p>
     *  返回复合边框是否不透明。
     * 
     * 
     * @return {@code true} if the inside and outside borders
     *         are each either {@code null} or opaque;
     *         or {@code false} otherwise
     */
    @Override
    public boolean isBorderOpaque() {
        return (outsideBorder == null || outsideBorder.isBorderOpaque()) &&
               (insideBorder == null || insideBorder.isBorderOpaque());
    }

    /**
     * Paints the compound border by painting the outside border
     * with the specified position and size and then painting the
     * inside border at the specified position and size offset by
     * the insets of the outside border.
     * <p>
     *  通过以指定的位置和大小绘制外边框来绘制复合边框,然后在指定位置和大小偏移的内边框上绘制外边框的插图。
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
        Insets  nextInsets;
        int px, py, pw, ph;

        px = x;
        py = y;
        pw = width;
        ph = height;

        if(outsideBorder != null) {
            outsideBorder.paintBorder(c, g, px, py, pw, ph);

            nextInsets = outsideBorder.getBorderInsets(c);
            px += nextInsets.left;
            py += nextInsets.top;
            pw = pw - nextInsets.right - nextInsets.left;
            ph = ph - nextInsets.bottom - nextInsets.top;
        }
        if(insideBorder != null)
            insideBorder.paintBorder(c, g, px, py, pw, ph);

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
        Insets  nextInsets;

        insets.top = insets.left = insets.right = insets.bottom = 0;
        if(outsideBorder != null) {
            nextInsets = outsideBorder.getBorderInsets(c);
            insets.top += nextInsets.top;
            insets.left += nextInsets.left;
            insets.right += nextInsets.right;
            insets.bottom += nextInsets.bottom;
        }
        if(insideBorder != null) {
            nextInsets = insideBorder.getBorderInsets(c);
            insets.top += nextInsets.top;
            insets.left += nextInsets.left;
            insets.right += nextInsets.right;
            insets.bottom += nextInsets.bottom;
        }
        return insets;
    }

    /**
     * Returns the outside border object.
     * <p>
     * 返回外部边界对象。
     * 
     */
    public Border getOutsideBorder() {
        return outsideBorder;
    }

    /**
     * Returns the inside border object.
     * <p>
     *  返回内部边框对象。
     */
    public Border getInsideBorder() {
        return insideBorder;
    }
}
