/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 1999, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing.text.html;

import java.util.Enumeration;
import java.awt.*;
import javax.swing.text.*;

/**
 * A view implementation to display an html list
 *
 * <p>
 *  用于显示html列表的视图实现
 * 
 * 
 * @author  Timothy Prinzing
 */
public class ListView extends BlockView  {

    /**
     * Creates a new view that represents a list element.
     *
     * <p>
     *  创建表示列表元素的新视图。
     * 
     * 
     * @param elem the element to create a view for
     */
    public ListView(Element elem) {
        super(elem, View.Y_AXIS);
    }

    /**
     * Calculates the desired shape of the list.
     *
     * <p>
     *  计算列表的所需形状。
     * 
     * 
     * @return the desired span
     * @see View#getPreferredSpan
     */
    public float getAlignment(int axis) {
        switch (axis) {
        case View.X_AXIS:
            return 0.5f;
        case View.Y_AXIS:
            return 0.5f;
        default:
            throw new IllegalArgumentException("Invalid axis: " + axis);
        }
    }

    /**
     * Renders using the given rendering surface and area on that
     * surface.
     *
     * <p>
     *  使用给定的渲染表面和该表面上的区域渲染。
     * 
     * 
     * @param g the rendering surface to use
     * @param allocation the allocated region to render into
     * @see View#paint
     */
    public void paint(Graphics g, Shape allocation) {
        super.paint(g, allocation);
        Rectangle alloc = allocation.getBounds();
        Rectangle clip = g.getClipBounds();
        // Since listPainter paints in the insets we have to check for the
        // case where the child is not painted because the paint region is
        // to the left of the child. This assumes the ListPainter paints in
        // the left margin.
        if ((clip.x + clip.width) < (alloc.x + getLeftInset())) {
            Rectangle childRect = alloc;
            alloc = getInsideAllocation(allocation);
            int n = getViewCount();
            int endY = clip.y + clip.height;
            for (int i = 0; i < n; i++) {
                childRect.setBounds(alloc);
                childAllocation(i, childRect);
                if (childRect.y < endY) {
                    if ((childRect.y + childRect.height) >= clip.y) {
                        listPainter.paint(g, childRect.x, childRect.y,
                                          childRect.width, childRect.height,
                                          this, i);
                    }
                }
                else {
                    break;
                }
            }
        }
    }

    /**
     * Paints one of the children; called by paint().  By default
     * that is all it does, but a subclass can use this to paint
     * things relative to the child.
     *
     * <p>
     *  画一个孩子;调用paint()。默认情况下,它是它所做的,但一个子类可以使用它来绘制相对于孩子的东西。
     * 
     * @param g the graphics context
     * @param alloc the allocated region to render the child into
     * @param index the index of the child
     */
    protected void paintChild(Graphics g, Rectangle alloc, int index) {
        listPainter.paint(g, alloc.x, alloc.y, alloc.width, alloc.height, this, index);
        super.paintChild(g, alloc, index);
    }

    protected void setPropertiesFromAttributes() {
        super.setPropertiesFromAttributes();
        listPainter = getStyleSheet().getListPainter(getAttributes());
    }

    private StyleSheet.ListPainter listPainter;
}
