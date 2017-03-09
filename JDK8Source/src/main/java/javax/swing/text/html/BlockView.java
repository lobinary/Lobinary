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
package javax.swing.text.html;

import java.util.Enumeration;
import java.awt.*;
import javax.swing.SizeRequirements;
import javax.swing.border.*;
import javax.swing.event.DocumentEvent;
import javax.swing.text.*;

/**
 * A view implementation to display a block (as a box)
 * with CSS specifications.
 *
 * <p>
 *  用CSS规范显示块(作为框)的视图实现。
 * 
 * 
 * @author  Timothy Prinzing
 */
public class BlockView extends BoxView  {

    /**
     * Creates a new view that represents an
     * html box.  This can be used for a number
     * of elements.
     *
     * <p>
     *  创建一个表示html框的新视图。这可以用于多个元素。
     * 
     * 
     * @param elem the element to create a view for
     * @param axis either View.X_AXIS or View.Y_AXIS
     */
    public BlockView(Element elem, int axis) {
        super(elem, axis);
    }

    /**
     * Establishes the parent view for this view.  This is
     * guaranteed to be called before any other methods if the
     * parent view is functioning properly.
     * <p>
     * This is implemented
     * to forward to the superclass as well as call the
     * {@link #setPropertiesFromAttributes()}
     * method to set the paragraph properties from the css
     * attributes.  The call is made at this time to ensure
     * the ability to resolve upward through the parents
     * view attributes.
     *
     * <p>
     *  为此视图建立父视图。如果父视图正常工作,这保证在任何其他方法之前被调用。
     * <p>
     *  这被实现为转发到超类以及调用{@link #setPropertiesFromAttributes()}方法从css属性设置段落属性。此时进行调用以确保通过父视图属性向上分辨的能力。
     * 
     * 
     * @param parent the new parent, or null if the view is
     *  being removed from a parent it was previously added
     *  to
     */
    public void setParent(View parent) {
        super.setParent(parent);
        if (parent != null) {
            setPropertiesFromAttributes();
        }
    }

    /**
     * Calculate the requirements of the block along the major
     * axis (i.e. the axis along with it tiles).  This is implemented
     * to provide the superclass behavior and then adjust it if the
     * CSS width or height attribute is specified and applicable to
     * the axis.
     * <p>
     *  计算沿着主轴(即与其并列的轴)的块的要求。这被实现以提供超类行为,然后如果CSS宽度或高度属性被指定并适用于轴,则对其进行调整。
     * 
     */
    protected SizeRequirements calculateMajorAxisRequirements(int axis, SizeRequirements r) {
        if (r == null) {
            r = new SizeRequirements();
        }
        if (! spanSetFromAttributes(axis, r, cssWidth, cssHeight)) {
            r = super.calculateMajorAxisRequirements(axis, r);
        }
        else {
            // Offset by the margins so that pref/min/max return the
            // right value.
            SizeRequirements parentR = super.calculateMajorAxisRequirements(
                                      axis, null);
            int margin = (axis == X_AXIS) ? getLeftInset() + getRightInset() :
                                            getTopInset() + getBottomInset();
            r.minimum -= margin;
            r.preferred -= margin;
            r.maximum -= margin;
            constrainSize(axis, r, parentR);
        }
        return r;
    }

    /**
     * Calculate the requirements of the block along the minor
     * axis (i.e. the axis orthogonal to the axis along with it tiles).
     * This is implemented
     * to provide the superclass behavior and then adjust it if the
     * CSS width or height attribute is specified and applicable to
     * the axis.
     * <p>
     *  计算块沿着短轴(即,与轴一起正交的轴及其贴片)的要求。这被实现以提供超类行为,然后如果CSS宽度或高度属性被指定并适用于轴,则对其进行调整。
     * 
     */
    protected SizeRequirements calculateMinorAxisRequirements(int axis, SizeRequirements r) {
        if (r == null) {
            r = new SizeRequirements();
        }

        if (! spanSetFromAttributes(axis, r, cssWidth, cssHeight)) {

            /*
             * The requirements were not directly specified by attributes, so
             * compute the aggregate of the requirements of the children.  The
             * children that have a percentage value specified will be treated
             * as completely stretchable since that child is not limited in any
             * way.
             * <p>
             * 这些需求不是由属性直接指定的,因此请计算子项的需求的聚合。具有指定百分比值的孩子将被视为完全可伸展的,因为该孩子不以任何方式受到限制。
             * 
             */
/*
            int min = 0;
            long pref = 0;
            int max = 0;
            int n = getViewCount();
            for (int i = 0; i < n; i++) {
                View v = getView(i);
                min = Math.max((int) v.getMinimumSpan(axis), min);
                pref = Math.max((int) v.getPreferredSpan(axis), pref);
                if (
                max = Math.max((int) v.getMaximumSpan(axis), max);

            }
            r.preferred = (int) pref;
            r.minimum = min;
            r.maximum = max;
/* <p>
/*  int min = 0; long pref = 0; int max = 0; int n = getViewCount(); for(int i = 0; i <n; i ++){View v = getView(i); min = Math.max((int)v.getMinimumSpan(axis),min); pref = Math.max((int)v.getPreferredSpan(axis),pref); if(max = Math.max((int)v.getMaximumSpan(axis),max);。
/* 
/*  } r.preferred =(int)pref; r.minimum = min; r.maximum = max;
/* 
            */
            r = super.calculateMinorAxisRequirements(axis, r);
        }
        else {
            // Offset by the margins so that pref/min/max return the
            // right value.
            SizeRequirements parentR = super.calculateMinorAxisRequirements(
                                      axis, null);
            int margin = (axis == X_AXIS) ? getLeftInset() + getRightInset() :
                                            getTopInset() + getBottomInset();
            r.minimum -= margin;
            r.preferred -= margin;
            r.maximum -= margin;
            constrainSize(axis, r, parentR);
        }

        /*
         * Set the alignment based upon the CSS properties if it is
         * specified.  For X_AXIS this would be text-align, for
         * Y_AXIS this would be vertical-align.
         * <p>
         *  如果指定了CSS属性,请根据CSS属性设置对齐方式。对于X_AXIS这将是文本对齐,对于Y_AXIS这将是vertical-align。
         * 
         */
        if (axis == X_AXIS) {
            Object o = getAttributes().getAttribute(CSS.Attribute.TEXT_ALIGN);
            if (o != null) {
                String align = o.toString();
                if (align.equals("center")) {
                    r.alignment = 0.5f;
                } else if (align.equals("right")) {
                    r.alignment = 1.0f;
                } else {
                    r.alignment = 0.0f;
                }
            }
        }
        // Y_AXIS TBD
        return r;
    }

    boolean isPercentage(int axis, AttributeSet a) {
        if (axis == X_AXIS) {
            if (cssWidth != null) {
                return cssWidth.isPercentage();
            }
        } else {
            if (cssHeight != null) {
                return cssHeight.isPercentage();
            }
        }
        return false;
    }

    /**
     * Adjust the given requirements to the CSS width or height if
     * it is specified along the applicable axis.  Return true if the
     * size is exactly specified, false if the span is not specified
     * in an attribute or the size specified is a percentage.
     * <p>
     *  如果沿适用的轴指定,则将给定的要求调整为CSS宽度或高度。如果大小完全指定,则返回true,如果未在属性中指定span或指定的大小为百分比,则返回false。
     * 
     */
    static boolean spanSetFromAttributes(int axis, SizeRequirements r,
                                         CSS.LengthValue cssWidth,
                                         CSS.LengthValue cssHeight) {
        if (axis == X_AXIS) {
            if ((cssWidth != null) && (! cssWidth.isPercentage())) {
                r.minimum = r.preferred = r.maximum = (int) cssWidth.getValue();
                return true;
            }
        } else {
            if ((cssHeight != null) && (! cssHeight.isPercentage())) {
                r.minimum = r.preferred = r.maximum = (int) cssHeight.getValue();
                return true;
            }
        }
        return false;
    }

    /**
     * Performs layout for the minor axis of the box (i.e. the
     * axis orthogonal to the axis that it represents). The results
     * of the layout (the offset and span for each children) are
     * placed in the given arrays which represent the allocations to
     * the children along the minor axis.
     *
     * <p>
     *  执行箱子短轴(即与它代表的轴正交的轴)的布局。布局的结果(每个子节点的偏移和跨度)被放置在给定的数组中,这些数组表示沿着短轴的子节点的分配。
     * 
     * 
     * @param targetSpan the total span given to the view, which
     *  would be used to layout the children.
     * @param axis the axis being layed out
     * @param offsets the offsets from the origin of the view for
     *  each of the child views; this is a return value and is
     *  filled in by the implementation of this method
     * @param spans the span of each child view; this is a return
     *  value and is filled in by the implementation of this method
     */
    protected void layoutMinorAxis(int targetSpan, int axis, int[] offsets, int[] spans) {
        int n = getViewCount();
        Object key = (axis == X_AXIS) ? CSS.Attribute.WIDTH : CSS.Attribute.HEIGHT;
        for (int i = 0; i < n; i++) {
            View v = getView(i);
            int min = (int) v.getMinimumSpan(axis);
            int max;

            // check for percentage span
            AttributeSet a = v.getAttributes();
            CSS.LengthValue lv = (CSS.LengthValue) a.getAttribute(key);
            if ((lv != null) && lv.isPercentage()) {
                // bound the span to the percentage specified
                min = Math.max((int) lv.getValue(targetSpan), min);
                max = min;
            } else {
                max = (int)v.getMaximumSpan(axis);
            }

            // assign the offset and span for the child
            if (max < targetSpan) {
                // can't make the child this wide, align it
                float align = v.getAlignment(axis);
                offsets[i] = (int) ((targetSpan - max) * align);
                spans[i] = max;
            } else {
                // make it the target width, or as small as it can get.
                offsets[i] = 0;
                spans[i] = Math.max(min, targetSpan);
            }
        }
    }


    /**
     * Renders using the given rendering surface and area on that
     * surface.  This is implemented to delegate to the css box
     * painter to paint the border and background prior to the
     * interior.
     *
     * <p>
     *  使用给定的渲染表面和该表面上的区域渲染。这被实现委托给css盒子画家在内部之前绘制边界和背景。
     * 
     * 
     * @param g the rendering surface to use
     * @param allocation the allocated region to render into
     * @see View#paint
     */
    public void paint(Graphics g, Shape allocation) {
        Rectangle a = (Rectangle) allocation;
        painter.paint(g, a.x, a.y, a.width, a.height, this);
        super.paint(g, a);
    }

    /**
     * Fetches the attributes to use when rendering.  This is
     * implemented to multiplex the attributes specified in the
     * model with a StyleSheet.
     * <p>
     * 获取渲染时要使用的属性。这被实现为将在模型中指定的属性与StyleSheet复用。
     * 
     */
    public AttributeSet getAttributes() {
        if (attr == null) {
            StyleSheet sheet = getStyleSheet();
            attr = sheet.getViewAttributes(this);
        }
        return attr;
    }

    /**
     * Gets the resize weight.
     *
     * <p>
     *  获取调整大小权重。
     * 
     * 
     * @param axis may be either X_AXIS or Y_AXIS
     * @return the weight
     * @exception IllegalArgumentException for an invalid axis
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
     * Gets the alignment.
     *
     * <p>
     *  获取对齐。
     * 
     * 
     * @param axis may be either X_AXIS or Y_AXIS
     * @return the alignment
     */
    public float getAlignment(int axis) {
        switch (axis) {
        case View.X_AXIS:
            return 0;
        case View.Y_AXIS:
            if (getViewCount() == 0) {
                return 0;
            }
            float span = getPreferredSpan(View.Y_AXIS);
            View v = getView(0);
            float above = v.getPreferredSpan(View.Y_AXIS);
            float a = (((int)span) != 0) ? (above * v.getAlignment(View.Y_AXIS)) / span: 0;
            return a;
        default:
            throw new IllegalArgumentException("Invalid axis: " + axis);
        }
    }

    public void changedUpdate(DocumentEvent changes, Shape a, ViewFactory f) {
        super.changedUpdate(changes, a, f);
        int pos = changes.getOffset();
        if (pos <= getStartOffset() && (pos + changes.getLength()) >=
            getEndOffset()) {
            setPropertiesFromAttributes();
        }
    }

    /**
     * Determines the preferred span for this view along an
     * axis.
     *
     * <p>
     *  确定沿着轴的此视图的首选跨度。
     * 
     * 
     * @param axis may be either <code>View.X_AXIS</code>
     *           or <code>View.Y_AXIS</code>
     * @return   the span the view would like to be rendered into &gt;= 0;
     *           typically the view is told to render into the span
     *           that is returned, although there is no guarantee;
     *           the parent may choose to resize or break the view
     * @exception IllegalArgumentException for an invalid axis type
     */
    public float getPreferredSpan(int axis) {
        return super.getPreferredSpan(axis);
    }

    /**
     * Determines the minimum span for this view along an
     * axis.
     *
     * <p>
     *  确定沿轴的此视图的最小跨度。
     * 
     * 
     * @param axis may be either <code>View.X_AXIS</code>
     *           or <code>View.Y_AXIS</code>
     * @return  the span the view would like to be rendered into &gt;= 0;
     *           typically the view is told to render into the span
     *           that is returned, although there is no guarantee;
     *           the parent may choose to resize or break the view
     * @exception IllegalArgumentException for an invalid axis type
     */
    public float getMinimumSpan(int axis) {
        return super.getMinimumSpan(axis);
    }

    /**
     * Determines the maximum span for this view along an
     * axis.
     *
     * <p>
     *  确定沿轴的此视图的最大跨度。
     * 
     * 
     * @param axis may be either <code>View.X_AXIS</code>
     *           or <code>View.Y_AXIS</code>
     * @return   the span the view would like to be rendered into &gt;= 0;
     *           typically the view is told to render into the span
     *           that is returned, although there is no guarantee;
     *           the parent may choose to resize or break the view
     * @exception IllegalArgumentException for an invalid axis type
     */
    public float getMaximumSpan(int axis) {
        return super.getMaximumSpan(axis);
    }

    /**
     * Update any cached values that come from attributes.
     * <p>
     *  更新来自属性的任何缓存值。
     * 
     */
    protected void setPropertiesFromAttributes() {

        // update attributes
        StyleSheet sheet = getStyleSheet();
        attr = sheet.getViewAttributes(this);

        // Reset the painter
        painter = sheet.getBoxPainter(attr);
        if (attr != null) {
            setInsets((short) painter.getInset(TOP, this),
                      (short) painter.getInset(LEFT, this),
                      (short) painter.getInset(BOTTOM, this),
                      (short) painter.getInset(RIGHT, this));
        }

        // Get the width/height
        cssWidth = (CSS.LengthValue) attr.getAttribute(CSS.Attribute.WIDTH);
        cssHeight = (CSS.LengthValue) attr.getAttribute(CSS.Attribute.HEIGHT);
    }

    protected StyleSheet getStyleSheet() {
        HTMLDocument doc = (HTMLDocument) getDocument();
        return doc.getStyleSheet();
    }

    /**
     * Constrains <code>want</code> to fit in the minimum size specified
     * by <code>min</code>.
     * <p>
     *  约束<code>希望</code>适合由<code> min </code>指定的最小大小。
     */
    private void constrainSize(int axis, SizeRequirements want,
                               SizeRequirements min) {
        if (min.minimum > want.minimum) {
            want.minimum = want.preferred = min.minimum;
            want.maximum = Math.max(want.maximum, min.maximum);
        }
    }

    private AttributeSet attr;
    private StyleSheet.BoxPainter painter;

    private CSS.LengthValue cssWidth;
    private CSS.LengthValue cssHeight;

}
