/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2000, Oracle and/or its affiliates. All rights reserved.
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

import javax.swing.text.*;
import java.awt.*;

/**
 * This is the view associated with the html tag NOFRAMES.
 * This view has been written to ignore the contents of the
 * NOFRAMES tag.  The contents of the tag will only be visible
 * when the JTextComponent the view is contained in is editable.
 *
 * <p>
 *  这是与html标签NOFRAMES相关联的视图。此视图已编写为忽略NOFRAMES标记的内容。标签的内容只有在包含视图的JTextComponent是可编辑的时才可见。
 * 
 * 
 * @author  Sunita Mani
 */
class NoFramesView extends BlockView {

    /**
     * Creates a new view that represents an
     * html box.  This can be used for a number
     * of elements.  By default this view is not
     * visible.
     *
     * <p>
     *  创建一个表示html框的新视图。这可以用于多个元素。默认情况下,此视图不可见。
     * 
     * 
     * @param elem the element to create a view for
     * @param axis either View.X_AXIS or View.Y_AXIS
     */
    public NoFramesView(Element elem, int axis) {
        super(elem, axis);
        visible = false;
    }


    /**
     * If this view is not visible, then it returns.
     * Otherwise it invokes the superclass.
     *
     * <p>
     *  如果此视图不可见,则返回。否则它调用超类。
     * 
     * 
     * @param g the rendering surface to use
     * @param allocation the allocated region to render into
     * @see #isVisible
     * @see text.ParagraphView#paint
     */
    public void paint(Graphics g, Shape allocation) {
        Container host = getContainer();
        if (host != null &&
            visible != ((JTextComponent)host).isEditable()) {
            visible = ((JTextComponent)host).isEditable();
        }

        if (!isVisible()) {
            return;
        }
        super.paint(g, allocation);
    }


    /**
     * Determines if the JTextComponent that the view
     * is contained in is editable. If so, then this
     * view and all its child views are visible.
     * Once this has been determined, the superclass
     * is invoked to continue processing.
     *
     * <p>
     *  确定视图包含的JTextComponent是否可编辑。如果是,则此视图及其所有子视图都可见。一旦确定,就调用超类继续处理。
     * 
     * 
     * @param p the parent View.
     * @see BlockView#setParent
     */
    public void setParent(View p) {
        if (p != null) {
            Container host = p.getContainer();
            if (host != null) {
                visible = ((JTextComponent)host).isEditable();
            }
        }
        super.setParent(p);
    }

    /**
     * Returns a true/false value that represents
     * whether the view is visible or not.
     * <p>
     *  返回一个true / false值,表示视图是否可见。
     * 
     */
    public boolean isVisible() {
        return visible;
    }


    /**
     * Do nothing if the view is not visible, otherwise
     * invoke the superclass to perform layout.
     * <p>
     *  如果视图不可见,则不执行任何操作,否则调用超类来执行布局。
     * 
     */
    protected void layout(int width, int height) {
        if (!isVisible()) {
            return;
        }
        super.layout(width, height);
    }

    /**
     * Determines the preferred span for this view.  Returns
     * 0 if the view is not visible, otherwise it calls the
     * superclass method to get the preferred span.
     * axis.
     *
     * <p>
     *  确定此视图的首选跨度。如果视图不可见,则返回0,否则调用超类方法以获取首选跨度。轴。
     * 
     * 
     * @param axis may be either View.X_AXIS or View.Y_AXIS
     * @return   the span the view would like to be rendered into;
     *           typically the view is told to render into the span
     *           that is returned, although there is no guarantee;
     *           the parent may choose to resize or break the view
     * @see text.ParagraphView#getPreferredSpan
     */
    public float getPreferredSpan(int axis) {
        if (!visible) {
            return 0;
        }
        return super.getPreferredSpan(axis);
    }

    /**
     * Determines the minimum span for this view along an
     * axis.  Returns 0 if the view is not visible, otherwise
     * it calls the superclass method to get the minimum span.
     *
     * <p>
     *  确定沿轴的此视图的最小跨度。如果视图不可见,则返回0,否则调用超类方法以获取最小跨度。
     * 
     * 
     * @param axis may be either <code>View.X_AXIS</code> or
     *          <code>View.Y_AXIS</code>
     * @return  the minimum span the view can be rendered into
     * @see text.ParagraphView#getMinimumSpan
     */
    public float getMinimumSpan(int axis) {
        if (!visible) {
            return 0;
        }
        return super.getMinimumSpan(axis);
    }

    /**
     * Determines the maximum span for this view along an
     * axis.  Returns 0 if the view is not visible, otherwise
     * it calls the superclass method ot get the maximum span.
     *
     * <p>
     *  确定沿轴的此视图的最大跨度。如果视图不可见,则返回0,否则调用超类方法获取最大跨度。
     * 
     * @param axis may be either <code>View.X_AXIS</code> or
     *  <code>View.Y_AXIS</code>
     * @return  the maximum span the view can be rendered into
     * @see text.ParagraphView#getMaximumSpan
     */
    public float getMaximumSpan(int axis) {
        if (!visible) {
            return 0;
        }
        return super.getMaximumSpan(axis);
    }

    boolean visible;
}
