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
import java.awt.Component;
import java.io.Serializable;

/**
 * A class that implements an empty border with no size.
 * This provides a convenient base class from which other border
 * classes can be easily derived.
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
 *  一个实现没有大小的空边框的类。这提供了一个方便的基类,可以从其中轻松导出其他边界类。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author David Kloba
 */
@SuppressWarnings("serial")
public abstract class AbstractBorder implements Border, Serializable
{
    /**
     * This default implementation does no painting.
     * <p>
     *  此默认实现不绘画。
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
    }

    /**
     * This default implementation returns a new {@link Insets} object
     * that is initialized by the {@link #getBorderInsets(Component,Insets)}
     * method.
     * By default the {@code top}, {@code left}, {@code bottom},
     * and {@code right} fields are set to {@code 0}.
     *
     * <p>
     *  此默认实现返回由{@link #getBorderInsets(Component,Insets)}方法初始化的新{@link Insets}对象。
     * 默认情况下,{@code top},{@code left},{@code bottom}和{@code right}字段设置为{@code 0}。
     * 
     * 
     * @param c  the component for which this border insets value applies
     * @return a new {@link Insets} object
     */
    public Insets getBorderInsets(Component c)       {
        return getBorderInsets(c, new Insets(0, 0, 0, 0));
    }

    /**
     * Reinitializes the insets parameter with this Border's current Insets.
     * <p>
     *  使用此Border的当前Insets重新初始化insets参数。
     * 
     * 
     * @param c the component for which this border insets value applies
     * @param insets the object to be reinitialized
     * @return the <code>insets</code> object
     */
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = insets.top = insets.right = insets.bottom = 0;
        return insets;
    }

    /**
     * This default implementation returns false.
     * <p>
     *  此默认实现返回false。
     * 
     * 
     * @return false
     */
    public boolean isBorderOpaque() { return false; }

    /**
     * This convenience method calls the static method.
     * <p>
     *  这个方便的方法调用静态方法。
     * 
     * 
     * @param c the component for which this border is being computed
     * @param x the x position of the border
     * @param y the y position of the border
     * @param width the width of the border
     * @param height the height of the border
     * @return a <code>Rectangle</code> containing the interior coordinates
     */
    public Rectangle getInteriorRectangle(Component c, int x, int y, int width, int height) {
        return getInteriorRectangle(c, this, x, y, width, height);
    }

    /**
     * Returns a rectangle using the arguments minus the
     * insets of the border. This is useful for determining the area
     * that components should draw in that will not intersect the border.
     * <p>
     *  使用参数减去边框的插入来返回一个矩形。这对于确定组件应该绘制的区域(不会与边框相交)很有用。
     * 
     * 
     * @param c the component for which this border is being computed
     * @param b the <code>Border</code> object
     * @param x the x position of the border
     * @param y the y position of the border
     * @param width the width of the border
     * @param height the height of the border
     * @return a <code>Rectangle</code> containing the interior coordinates
     */
    public static Rectangle getInteriorRectangle(Component c, Border b, int x, int y, int width, int height) {
        Insets insets;
        if(b != null)
            insets = b.getBorderInsets(c);
        else
            insets = new Insets(0, 0, 0, 0);
        return new Rectangle(x + insets.left,
                                    y + insets.top,
                                    width - insets.right - insets.left,
                                    height - insets.top - insets.bottom);
    }

    /**
     * Returns the baseline.  A return value less than 0 indicates the border
     * does not have a reasonable baseline.
     * <p>
     * The default implementation returns -1.  Subclasses that support
     * baseline should override appropriately.  If a value &gt;= 0 is
     * returned, then the component has a valid baseline for any
     * size &gt;= the minimum size and <code>getBaselineResizeBehavior</code>
     * can be used to determine how the baseline changes with size.
     *
     * <p>
     *  返回基线。返回值小于0表示边界没有合理的基线。
     * <p>
     * 默认实现返回-1。支持基线的子类应适当覆盖。
     * 如果返回值> = 0,则组件对于任何大小&gt; =最小大小具有有效基线,并且<code> getBaselineResizeBehavior </code>可以用于确定基线如何随大小改变。
     * 
     * 
     * @param c <code>Component</code> baseline is being requested for
     * @param width the width to get the baseline for
     * @param height the height to get the baseline for
     * @return the baseline or &lt; 0 indicating there is no reasonable
     *         baseline
     * @throws IllegalArgumentException if width or height is &lt; 0
     * @see java.awt.Component#getBaseline(int,int)
     * @see java.awt.Component#getBaselineResizeBehavior()
     * @since 1.6
     */
    public int getBaseline(Component c, int width, int height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException(
                    "Width and height must be >= 0");
        }
        return -1;
    }

    /**
     * Returns an enum indicating how the baseline of a component
     * changes as the size changes.  This method is primarily meant for
     * layout managers and GUI builders.
     * <p>
     * The default implementation returns
     * <code>BaselineResizeBehavior.OTHER</code>, subclasses that support
     * baseline should override appropriately.  Subclasses should
     * never return <code>null</code>; if the baseline can not be
     * calculated return <code>BaselineResizeBehavior.OTHER</code>.  Callers
     * should first ask for the baseline using
     * <code>getBaseline</code> and if a value &gt;= 0 is returned use
     * this method.  It is acceptable for this method to return a
     * value other than <code>BaselineResizeBehavior.OTHER</code> even if
     * <code>getBaseline</code> returns a value less than 0.
     *
     * <p>
     *  返回指示组件基线随着大小变化而变化的枚举。此方法主要用于布局管理器和GUI构建器。
     * <p>
     *  默认实现返回<code> BaselineResizeBehavior.OTHER </code>,支持基线的子类应该适当覆盖。
     * 子类不应返回<code> null </code>;如果基线不能计算返回<code> BaselineResizeBehavior.OTHER </code>。
     * 调用者应首先使用<code> getBaseline </code>请求基线,如果返回值>&gt; = 0,则使用此方法。
     * 
     * @param c <code>Component</code> to return baseline resize behavior for
     * @return an enum indicating how the baseline changes as the border is
     *         resized
     * @see java.awt.Component#getBaseline(int,int)
     * @see java.awt.Component#getBaselineResizeBehavior()
     * @since 1.6
     */
    public Component.BaselineResizeBehavior getBaselineResizeBehavior(
            Component c) {
        if (c == null) {
            throw new NullPointerException("Component must be non-null");
        }
        return Component.BaselineResizeBehavior.OTHER;
    }

    /*
     * Convenience function for determining ComponentOrientation.
     * Helps us avoid having Munge directives throughout the code.
     * <p>
     * 此方法可以接受<code> BaselineResizeBehavior.OTHER </code>以外的值,即使<code> getBaseline </code>返回的值小于0也是如此。
     * 
     */
    static boolean isLeftToRight( Component c ) {
        return c.getComponentOrientation().isLeftToRight();
    }

}
