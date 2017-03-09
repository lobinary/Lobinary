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
package javax.swing.text;

import java.awt.*;
import javax.swing.Icon;
import javax.swing.event.*;

/**
 * Icon decorator that implements the view interface.  The
 * entire element is used to represent the icon.  This acts
 * as a gateway from the display-only View implementations to
 * interactive lightweight icons (that is, it allows icons
 * to be embedded into the View hierarchy.  The parent of the icon
 * is the container that is handed out by the associated view
 * factory.
 *
 * <p>
 *  实现视图接口的图标装饰器。整个元素用于表示图标。这充当从仅显示的View实现到交互式轻量级图标的网关(即,它允许将图标嵌入到View层次结构中。图标的父级是由关联的视图工厂发出的容器。
 * 
 * 
 * @author Timothy Prinzing
 */
public class IconView extends View  {

    /**
     * Creates a new icon view that represents an element.
     *
     * <p>
     *  创建表示元素的新图标视图。
     * 
     * 
     * @param elem the element to create a view for
     */
    public IconView(Element elem) {
        super(elem);
        AttributeSet attr = elem.getAttributes();
        c = StyleConstants.getIcon(attr);
    }

    // --- View methods ---------------------------------------------

    /**
     * Paints the icon.
     * The real paint behavior occurs naturally from the association
     * that the icon has with its parent container (the same
     * container hosting this view), so this simply allows us to
     * position the icon properly relative to the view.  Since
     * the coordinate system for the view is simply the parent
     * containers, positioning the child icon is easy.
     *
     * <p>
     *  绘制图标。真正的涂料行为自然地发生在图标与其父容器(容纳该视图的相同容器)的关联中,因此这仅仅允许我们相对于视图正确地定位图标。由于视图的坐标系只是父容器,所以定位子图标很容易。
     * 
     * 
     * @param g the rendering surface to use
     * @param a the allocated region to render into
     * @see View#paint
     */
    public void paint(Graphics g, Shape a) {
        Rectangle alloc = a.getBounds();
        c.paintIcon(getContainer(), g, alloc.x, alloc.y);
    }

    /**
     * Determines the preferred span for this view along an
     * axis.
     *
     * <p>
     *  确定沿着轴的此视图的首选跨度。
     * 
     * 
     * @param axis may be either View.X_AXIS or View.Y_AXIS
     * @return  the span the view would like to be rendered into
     *           Typically the view is told to render into the span
     *           that is returned, although there is no guarantee.
     *           The parent may choose to resize or break the view.
     * @exception IllegalArgumentException for an invalid axis
     */
    public float getPreferredSpan(int axis) {
        switch (axis) {
        case View.X_AXIS:
            return c.getIconWidth();
        case View.Y_AXIS:
            return c.getIconHeight();
        default:
            throw new IllegalArgumentException("Invalid axis: " + axis);
        }
    }

    /**
     * Determines the desired alignment for this view along an
     * axis.  This is implemented to give the alignment to the
     * bottom of the icon along the y axis, and the default
     * along the x axis.
     *
     * <p>
     *  确定沿着轴的该视图的期望对准。这是为了使对齐到图标的底部沿y轴,默认沿着x轴。
     * 
     * 
     * @param axis may be either View.X_AXIS or View.Y_AXIS
     * @return the desired alignment &gt;= 0.0f &amp;&amp; &lt;= 1.0f.  This should be
     *   a value between 0.0 and 1.0 where 0 indicates alignment at the
     *   origin and 1.0 indicates alignment to the full span
     *   away from the origin.  An alignment of 0.5 would be the
     *   center of the view.
     */
    public float getAlignment(int axis) {
        switch (axis) {
        case View.Y_AXIS:
            return 1;
        default:
            return super.getAlignment(axis);
        }
    }

    /**
     * Provides a mapping from the document model coordinate space
     * to the coordinate space of the view mapped to it.
     *
     * <p>
     *  提供从文档模型坐标空间到映射到其的视图的坐标空间的映射。
     * 
     * 
     * @param pos the position to convert &gt;= 0
     * @param a the allocated region to render into
     * @return the bounding box of the given position
     * @exception BadLocationException  if the given position does not
     *   represent a valid location in the associated document
     * @see View#modelToView
     */
    public Shape modelToView(int pos, Shape a, Position.Bias b) throws BadLocationException {
        int p0 = getStartOffset();
        int p1 = getEndOffset();
        if ((pos >= p0) && (pos <= p1)) {
            Rectangle r = a.getBounds();
            if (pos == p1) {
                r.x += r.width;
            }
            r.width = 0;
            return r;
        }
        throw new BadLocationException(pos + " not in range " + p0 + "," + p1, pos);
    }

    /**
     * Provides a mapping from the view coordinate space to the logical
     * coordinate space of the model.
     *
     * <p>
     *  提供从视图坐标空间到模型的逻辑坐标空间的映射。
     * 
     * @param x the X coordinate &gt;= 0
     * @param y the Y coordinate &gt;= 0
     * @param a the allocated region to render into
     * @return the location within the model that best represents the
     *  given point of view &gt;= 0
     * @see View#viewToModel
     */
    public int viewToModel(float x, float y, Shape a, Position.Bias[] bias) {
        Rectangle alloc = (Rectangle) a;
        if (x < alloc.x + (alloc.width / 2)) {
            bias[0] = Position.Bias.Forward;
            return getStartOffset();
        }
        bias[0] = Position.Bias.Backward;
        return getEndOffset();
    }

    // --- member variables ------------------------------------------------

    private Icon c;
}
