/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 1998, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing;

import java.awt.Graphics;
import java.awt.Component;


/**
 * A small fixed size picture, typically used to decorate components.
 *
 * <p>
 *  一个小的固定大小的图片,通常用于装饰组件。
 * 
 * 
 * @see ImageIcon
 */

public interface Icon
{
    /**
     * Draw the icon at the specified location.  Icon implementations
     * may use the Component argument to get properties useful for
     * painting, e.g. the foreground or background color.
     * <p>
     *  在指定位置绘制图标。图标实现可以使用Component参数来获得对绘画有用的属性,例如。前景或背景颜色。
     * 
     */
    void paintIcon(Component c, Graphics g, int x, int y);

    /**
     * Returns the icon's width.
     *
     * <p>
     *  返回图标的宽度。
     * 
     * 
     * @return an int specifying the fixed width of the icon.
     */
    int getIconWidth();

    /**
     * Returns the icon's height.
     *
     * <p>
     *  返回图标的高度。
     * 
     * @return an int specifying the fixed height of the icon.
     */
    int getIconHeight();
}
