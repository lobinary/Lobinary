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

/**
 * Interface describing an object capable of rendering a border
 * around the edges of a swing component.
 * For examples of using borders see
 * <a href="https://docs.oracle.com/javase/tutorial/uiswing/components/border.htmll">How to Use Borders</a>,
 * a section in <em>The Java Tutorial.</em>
 * <p>
 * In the Swing component set, borders supercede Insets as the
 * mechanism for creating a (decorated or plain) area around the
 * edge of a component.
 * <p>
 * Usage Notes:
 * <ul>
 * <li>Use EmptyBorder to create a plain border (this mechanism
 *     replaces its predecessor, <code>setInsets</code>).
 * <li>Use CompoundBorder to nest multiple border objects, creating
 *     a single, combined border.
 * <li>Border instances are designed to be shared. Rather than creating
 *     a new border object using one of border classes, use the
 *     BorderFactory methods, which produces a shared instance of the
 *     common border types.
 * <li>Additional border styles include BevelBorder, SoftBevelBorder,
 *     EtchedBorder, LineBorder, TitledBorder, and MatteBorder.
 * <li>To create a new border class, subclass AbstractBorder.
 * </ul>
 *
 * <p>
 *  描述能够渲染围绕挥杆组件边缘的边框的对象的界面。
 * 有关使用边框的示例,请参阅<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/border.htmll">如何使用边框
 * </a>,<em>中的一节。
 *  描述能够渲染围绕挥杆组件边缘的边框的对象的界面。 Java教程。</em>。
 * <p>
 *  在Swing组件集中,边框取代Insets作为在组件边缘周围创建(装饰或平面)区域的机制。
 * <p>
 *  使用说明：
 * <ul>
 *  <li>使用EmptyBorder创建纯边界(此机制替换其前身,<code> setInsets </code>)。 <li>使用CompoundBorder嵌套多个边框对象,创建单个组合边框。
 *  <li>边框实例设计为共享。而不是使用边界类之一创建一个新的边界对象,使用BorderFactory方法,它产生一个共享边界类型的共享实例。
 *  <li>其他边框样式包括BevelBorder,SoftBevelBorder,EtchedBorder,LineBorder,TitledBorder和MatteBorder。
 * 
 * @author David Kloba
 * @author Amy Fowler
 * @see javax.swing.BorderFactory
 * @see EmptyBorder
 * @see CompoundBorder
 */
public interface Border
{
    /**
     * Paints the border for the specified component with the specified
     * position and size.
     * <p>
     *  <li>要创建新边框类,请将AbstractBorder子类化。
     * </ul>
     * 
     * 
     * @param c the component for which this border is being painted
     * @param g the paint graphics
     * @param x the x position of the painted border
     * @param y the y position of the painted border
     * @param width the width of the painted border
     * @param height the height of the painted border
     */
    void paintBorder(Component c, Graphics g, int x, int y, int width, int height);

    /**
     * Returns the insets of the border.
     * <p>
     *  以指定的位置和大小绘制指定组件的边框。
     * 
     * 
     * @param c the component for which this border insets value applies
     */
    Insets getBorderInsets(Component c);

    /**
     * Returns whether or not the border is opaque.  If the border
     * is opaque, it is responsible for filling in it's own
     * background when painting.
     * <p>
     *  返回边框的插入。
     * 
     */
    boolean isBorderOpaque();
}
