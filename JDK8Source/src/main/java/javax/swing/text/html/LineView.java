/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2003, Oracle and/or its affiliates. All rights reserved.
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
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.text.*;

/**
 * A view implementation to display an unwrapped
 * preformatted line.<p>
 * This subclasses ParagraphView, but this really only contains one
 * Row of text.
 *
 * <p>
 *  一个视图实现,显示一个解包的预格式化行。<p>这是ParagraphView的子类,但这只是一行文本。
 * 
 * 
 * @author  Timothy Prinzing
 */
class LineView extends ParagraphView {
    /** Last place painted at. */
    int tabBase;

    /**
     * Creates a LineView object.
     *
     * <p>
     *  创建LineView对象。
     * 
     * 
     * @param elem the element to wrap in a view
     */
    public LineView(Element elem) {
        super(elem);
    }

    /**
     * Preformatted lines are not suppressed if they
     * have only whitespace, so they are always visible.
     * <p>
     *  如果预格式化行只有空格,它们不会被抑制,因此它们总是可见的。
     * 
     */
    public boolean isVisible() {
        return true;
    }

    /**
     * Determines the minimum span for this view along an
     * axis.  The preformatted line should refuse to be
     * sized less than the preferred size.
     *
     * <p>
     *  确定沿轴的此视图的最小跨度。预格式化线应当拒绝尺寸小于优选尺寸。
     * 
     * 
     * @param axis may be either <code>View.X_AXIS</code> or
     *  <code>View.Y_AXIS</code>
     * @return  the minimum span the view can be rendered into
     * @see View#getPreferredSpan
     */
    public float getMinimumSpan(int axis) {
        return getPreferredSpan(axis);
    }

    /**
     * Gets the resize weight for the specified axis.
     *
     * <p>
     *  获取指定轴的调整大小权重。
     * 
     * 
     * @param axis may be either X_AXIS or Y_AXIS
     * @return the weight
     */
    public int getResizeWeight(int axis) {
        switch (axis) {
        case View.X_AXIS:
            return 1;
        case View.Y_AXIS:
            return 0;
        default:
            throw new IllegalArgumentException("Invalid axis: " + axis);
        }
    }

    /**
     * Gets the alignment for an axis.
     *
     * <p>
     *  获取轴的对齐方式。
     * 
     * 
     * @param axis may be either X_AXIS or Y_AXIS
     * @return the alignment
     */
    public float getAlignment(int axis) {
        if (axis == View.X_AXIS) {
            return 0;
        }
        return super.getAlignment(axis);
    }

    /**
     * Lays out the children.  If the layout span has changed,
     * the rows are rebuilt.  The superclass functionality
     * is called after checking and possibly rebuilding the
     * rows.  If the height has changed, the
     * <code>preferenceChanged</code> method is called
     * on the parent since the vertical preference is
     * rigid.
     *
     * <p>
     *  放下孩子。如果布局跨度已更改,则将重新构建行。在检查并可能重建行之后调用超类功能。
     * 如果高度已更改,则父级调用<code> preferenceChanged </code>方法,因为垂直首选项是刚性的。
     * 
     * 
     * @param width  the width to lay out against >= 0.  This is
     *   the width inside of the inset area.
     * @param height the height to lay out against >= 0 (not used
     *   by paragraph, but used by the superclass).  This
     *   is the height inside of the inset area.
     */
    protected void layout(int width, int height) {
        super.layout(Integer.MAX_VALUE - 1, height);
    }

    /**
     * Returns the next tab stop position given a reference position.
     * This view implements the tab coordinate system, and calls
     * <code>getTabbedSpan</code> on the logical children in the process
     * of layout to determine the desired span of the children.  The
     * logical children can delegate their tab expansion upward to
     * the paragraph which knows how to expand tabs.
     * <code>LabelView</code> is an example of a view that delegates
     * its tab expansion needs upward to the paragraph.
     * <p>
     * This is implemented to try and locate a <code>TabSet</code>
     * in the paragraph element's attribute set.  If one can be
     * found, its settings will be used, otherwise a default expansion
     * will be provided.  The base location for for tab expansion
     * is the left inset from the paragraphs most recent allocation
     * (which is what the layout of the children is based upon).
     *
     * <p>
     *  返回给定参考位置的下一个制表位停止位置。此视图实现了选项卡坐标系统,并在布局过程中对逻辑子项调用<code> getTabbedSpan </code>,以确定子项的期望跨度。
     * 逻辑子代可以将其标签扩展向上委托给知道如何展开标签的段落。 <code> LabelView </code>是一个视图的示例,它将其选项卡扩展需求向上委托给段落。
     * <p>
     * 这被实现来尝试并且在段落元素的属性集中定位<code> TabSet </code>。如果可以找到,将使用其设置,否则将提供默认扩展。
     * 用于选项卡扩展的基本位置是段落最近分配(这是子项的布局基于)的左插入。
     * 
     * @param x the X reference position
     * @param tabOffset the position within the text stream
     *   that the tab occurred at >= 0.
     * @return the trailing end of the tab expansion >= 0
     * @see TabSet
     * @see TabStop
     * @see LabelView
     */
    public float nextTabStop(float x, int tabOffset) {
        // If the text isn't left justified, offset by 10 pixels!
        if (getTabSet() == null &&
            StyleConstants.getAlignment(getAttributes()) ==
            StyleConstants.ALIGN_LEFT) {
            return getPreTab(x, tabOffset);
        }
        return super.nextTabStop(x, tabOffset);
    }

    /**
     * Returns the location for the tab.
     * <p>
     * 
     */
    protected float getPreTab(float x, int tabOffset) {
        Document d = getDocument();
        View v = getViewAtPosition(tabOffset, null);
        if ((d instanceof StyledDocument) && v != null) {
            // Assume f is fixed point.
            Font f = ((StyledDocument)d).getFont(v.getAttributes());
            Container c = getContainer();
            FontMetrics fm = (c != null) ? c.getFontMetrics(f) :
                Toolkit.getDefaultToolkit().getFontMetrics(f);
            int width = getCharactersPerTab() * fm.charWidth('W');
            int tb = (int)getTabBase();
            return (float)((((int)x - tb) / width + 1) * width + tb);
        }
        return 10.0f + x;
    }

    /**
    /* <p>
    /*  返回选项卡的位置。
    /* 
    /* 
     * @return number of characters per tab, 8.
     */
    protected int getCharactersPerTab() {
        return 8;
    }
}
