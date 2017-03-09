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

import java.io.PrintStream;
import java.util.Vector;
import java.awt.*;
import javax.swing.event.DocumentEvent;
import javax.swing.SizeRequirements;

/**
 * A view that arranges its children into a box shape by tiling
 * its children along an axis.  The box is somewhat like that
 * found in TeX where there is alignment of the
 * children, flexibility of the children is considered, etc.
 * This is a building block that might be useful to represent
 * things like a collection of lines, paragraphs,
 * lists, columns, pages, etc.  The axis along which the children are tiled is
 * considered the major axis.  The orthogonal axis is the minor axis.
 * <p>
 * Layout for each axis is handled separately by the methods
 * <code>layoutMajorAxis</code> and <code>layoutMinorAxis</code>.
 * Subclasses can change the layout algorithm by
 * reimplementing these methods.    These methods will be called
 * as necessary depending upon whether or not there is cached
 * layout information and the cache is considered
 * valid.  These methods are typically called if the given size
 * along the axis changes, or if <code>layoutChanged</code> is
 * called to force an updated layout.  The <code>layoutChanged</code>
 * method invalidates cached layout information, if there is any.
 * The requirements published to the parent view are calculated by
 * the methods <code>calculateMajorAxisRequirements</code>
 * and  <code>calculateMinorAxisRequirements</code>.
 * If the layout algorithm is changed, these methods will
 * likely need to be reimplemented.
 *
 * <p>
 *  一个视图,通过沿着一个轴平铺它的孩子将它的孩子排列成一个盒子形状。该框有点像在TeX中找到的,其中存在孩子的对齐,孩子的灵活性被考虑等。
 * 这是可能有用于表示诸如线,段落,列表,列的集合的构建块,页面等。孩子们被平铺的轴被认为是主轴。正交轴是短轴。
 * <p>
 *  每个轴的布局由方法<code> layoutMajorAxis </code>和<code> layoutMinorAxis </code>分别处理。子类可以通过重新实现这些方法来更改布局算法。
 * 这些方法将根据是否有缓存的布局信息以及缓存是否有效来进行调用。如果沿轴的给定大小改变,或者如果调用<code> layoutChanged </code>以强制更新布局,则通常调用这些方法。
 *  <code> layoutChanged </code>方法无效缓存的布局信息,如果有的话。
 * 发布到父视图的要求通过方法<code> calculateMajorAxisRequirements </code>和<code> calculateMinorAxisRequirements </code>
 * 计算。
 *  <code> layoutChanged </code>方法无效缓存的布局信息,如果有的话。如果布局算法改变,这些方法可能需要重新实现。
 * 
 * 
 * @author  Timothy Prinzing
 */
public class BoxView extends CompositeView {

    /**
     * Constructs a <code>BoxView</code>.
     *
     * <p>
     *  构造一个<code> BoxView </code>。
     * 
     * 
     * @param elem the element this view is responsible for
     * @param axis either <code>View.X_AXIS</code> or <code>View.Y_AXIS</code>
     */
    public BoxView(Element elem, int axis) {
        super(elem);
        tempRect = new Rectangle();
        this.majorAxis = axis;

        majorOffsets = new int[0];
        majorSpans = new int[0];
        majorReqValid = false;
        majorAllocValid = false;
        minorOffsets = new int[0];
        minorSpans = new int[0];
        minorReqValid = false;
        minorAllocValid = false;
    }

    /**
     * Fetches the tile axis property.  This is the axis along which
     * the child views are tiled.
     *
     * <p>
     * 获取切片轴属性。这是子视图沿其平铺的轴。
     * 
     * 
     * @return the major axis of the box, either
     *  <code>View.X_AXIS</code> or <code>View.Y_AXIS</code>
     *
     * @since 1.3
     */
    public int getAxis() {
        return majorAxis;
    }

    /**
     * Sets the tile axis property.  This is the axis along which
     * the child views are tiled.
     *
     * <p>
     *  设置切片轴属性。这是子视图沿其平铺的轴。
     * 
     * 
     * @param axis either <code>View.X_AXIS</code> or <code>View.Y_AXIS</code>
     *
     * @since 1.3
     */
    public void setAxis(int axis) {
        boolean axisChanged = (axis != majorAxis);
        majorAxis = axis;
        if (axisChanged) {
            preferenceChanged(null, true, true);
        }
    }

    /**
     * Invalidates the layout along an axis.  This happens
     * automatically if the preferences have changed for
     * any of the child views.  In some cases the layout
     * may need to be recalculated when the preferences
     * have not changed.  The layout can be marked as
     * invalid by calling this method.  The layout will
     * be updated the next time the <code>setSize</code> method
     * is called on this view (typically in paint).
     *
     * <p>
     *  使沿轴的布局无效。如果任何子视图的首选项已更改,则会自动进行此操作。在某些情况下,当首选项未更改时,可能需要重新计算布局。可以通过调用此方法将布局标记为无效。
     * 布局将在下次在此视图上调用<code> setSize </code>方法时更新(通常在绘画中)。
     * 
     * 
     * @param axis either <code>View.X_AXIS</code> or <code>View.Y_AXIS</code>
     *
     * @since 1.3
     */
    public void layoutChanged(int axis) {
        if (axis == majorAxis) {
            majorAllocValid = false;
        } else {
            minorAllocValid = false;
        }
    }

    /**
     * Determines if the layout is valid along the given axis.
     *
     * <p>
     *  确定布局是否沿给定轴有效。
     * 
     * 
     * @param axis either <code>View.X_AXIS</code> or <code>View.Y_AXIS</code>
     *
     * @since 1.4
     */
    protected boolean isLayoutValid(int axis) {
        if (axis == majorAxis) {
            return majorAllocValid;
        } else {
            return minorAllocValid;
        }
    }

    /**
     * Paints a child.  By default
     * that is all it does, but a subclass can use this to paint
     * things relative to the child.
     *
     * <p>
     *  涂一个孩子。默认情况下,它是它所做的一切,但一个子类可以使用它来绘制相对于孩子的东西。
     * 
     * 
     * @param g the graphics context
     * @param alloc the allocated region to paint into
     * @param index the child index, &gt;= 0 &amp;&amp; &lt; getViewCount()
     */
    protected void paintChild(Graphics g, Rectangle alloc, int index) {
        View child = getView(index);
        child.paint(g, alloc);
    }

    // --- View methods ---------------------------------------------

    /**
     * Invalidates the layout and resizes the cache of
     * requests/allocations.  The child allocations can still
     * be accessed for the old layout, but the new children
     * will have an offset and span of 0.
     *
     * <p>
     *  使布局无效,并调整请求/分配的缓存大小。仍然可以为旧布局访问子分配,但新的子代将具有0的偏移和跨度。
     * 
     * 
     * @param index the starting index into the child views to insert
     *   the new views; this should be a value &gt;= 0 and &lt;= getViewCount
     * @param length the number of existing child views to remove;
     *   This should be a value &gt;= 0 and &lt;= (getViewCount() - offset)
     * @param elems the child views to add; this value can be
     *   <code>null</code>to indicate no children are being added
     *   (useful to remove)
     */
    public void replace(int index, int length, View[] elems) {
        super.replace(index, length, elems);

        // invalidate cache
        int nInserted = (elems != null) ? elems.length : 0;
        majorOffsets = updateLayoutArray(majorOffsets, index, nInserted);
        majorSpans = updateLayoutArray(majorSpans, index, nInserted);
        majorReqValid = false;
        majorAllocValid = false;
        minorOffsets = updateLayoutArray(minorOffsets, index, nInserted);
        minorSpans = updateLayoutArray(minorSpans, index, nInserted);
        minorReqValid = false;
        minorAllocValid = false;
    }

    /**
     * Resizes the given layout array to match the new number of
     * child views.  The current number of child views are used to
     * produce the new array.  The contents of the old array are
     * inserted into the new array at the appropriate places so that
     * the old layout information is transferred to the new array.
     *
     * <p>
     *  调整给定布局数组的大小以匹配新的子视图数。子视图的当前数量用于生成新数组。将旧数组的内容插入到新数组中的适当位置,以便将旧布局信息传输到新数组。
     * 
     * 
     * @param oldArray the original layout array
     * @param offset location where new views will be inserted
     * @param nInserted the number of child views being inserted;
     *          therefore the number of blank spaces to leave in the
     *          new array at location <code>offset</code>
     * @return the new layout array
     */
    int[] updateLayoutArray(int[] oldArray, int offset, int nInserted) {
        int n = getViewCount();
        int[] newArray = new int[n];

        System.arraycopy(oldArray, 0, newArray, 0, offset);
        System.arraycopy(oldArray, offset,
                         newArray, offset + nInserted, n - nInserted - offset);
        return newArray;
    }

    /**
     * Forwards the given <code>DocumentEvent</code> to the child views
     * that need to be notified of the change to the model.
     * If a child changed its requirements and the allocation
     * was valid prior to forwarding the portion of the box
     * from the starting child to the end of the box will
     * be repainted.
     *
     * <p>
     * 将给定的<code> DocumentEvent </code>转发给需要通知模型更改的子视图。如果儿童改变其要求并且在转发之前分配是有效的,则从初始孩子到盒子结束的盒子的部分将被重画。
     * 
     * 
     * @param ec changes to the element this view is responsible
     *  for (may be <code>null</code> if there were no changes)
     * @param e the change information from the associated document
     * @param a the current allocation of the view
     * @param f the factory to use to rebuild if the view has children
     * @see #insertUpdate
     * @see #removeUpdate
     * @see #changedUpdate
     * @since 1.3
     */
    protected void forwardUpdate(DocumentEvent.ElementChange ec,
                                 DocumentEvent e, Shape a, ViewFactory f) {
        boolean wasValid = isLayoutValid(majorAxis);
        super.forwardUpdate(ec, e, a, f);

        // determine if a repaint is needed
        if (wasValid && (! isLayoutValid(majorAxis))) {
            // Repaint is needed because one of the tiled children
            // have changed their span along the major axis.  If there
            // is a hosting component and an allocated shape we repaint.
            Component c = getContainer();
            if ((a != null) && (c != null)) {
                int pos = e.getOffset();
                int index = getViewIndexAtPosition(pos);
                Rectangle alloc = getInsideAllocation(a);
                if (majorAxis == X_AXIS) {
                    alloc.x += majorOffsets[index];
                    alloc.width -= majorOffsets[index];
                } else {
                    alloc.y += minorOffsets[index];
                    alloc.height -= minorOffsets[index];
                }
                c.repaint(alloc.x, alloc.y, alloc.width, alloc.height);
            }
        }
    }

    /**
     * This is called by a child to indicate its
     * preferred span has changed.  This is implemented to
     * throw away cached layout information so that new
     * calculations will be done the next time the children
     * need an allocation.
     *
     * <p>
     *  这由孩子调用以指示其首选跨度已更改。这被实现为丢弃高速缓存的布局信息,以便在下一次子进程需要分配时进行新的计算。
     * 
     * 
     * @param child the child view
     * @param width true if the width preference should change
     * @param height true if the height preference should change
     */
    public void preferenceChanged(View child, boolean width, boolean height) {
        boolean majorChanged = (majorAxis == X_AXIS) ? width : height;
        boolean minorChanged = (majorAxis == X_AXIS) ? height : width;
        if (majorChanged) {
            majorReqValid = false;
            majorAllocValid = false;
        }
        if (minorChanged) {
            minorReqValid = false;
            minorAllocValid = false;
        }
        super.preferenceChanged(child, width, height);
    }

    /**
     * Gets the resize weight.  A value of 0 or less is not resizable.
     *
     * <p>
     *  获取调整大小权重。值为0或更小不可调整大小。
     * 
     * 
     * @param axis may be either <code>View.X_AXIS</code> or
     *          <code>View.Y_AXIS</code>
     * @return the weight
     * @exception IllegalArgumentException for an invalid axis
     */
    public int getResizeWeight(int axis) {
        checkRequests(axis);
        if (axis == majorAxis) {
            if ((majorRequest.preferred != majorRequest.minimum) ||
                (majorRequest.preferred != majorRequest.maximum)) {
                return 1;
            }
        } else {
            if ((minorRequest.preferred != minorRequest.minimum) ||
                (minorRequest.preferred != minorRequest.maximum)) {
                return 1;
            }
        }
        return 0;
    }

    /**
     * Sets the size of the view along an axis.  This should cause
     * layout of the view along the given axis.
     *
     * <p>
     *  设置沿轴的视图大小。这应该导致沿给定轴的视图的布局。
     * 
     * 
     * @param axis may be either <code>View.X_AXIS</code> or
     *          <code>View.Y_AXIS</code>
     * @param span the span to layout to >= 0
     */
    void setSpanOnAxis(int axis, float span) {
        if (axis == majorAxis) {
            if (majorSpan != (int) span) {
                majorAllocValid = false;
            }
            if (! majorAllocValid) {
                // layout the major axis
                majorSpan = (int) span;
                checkRequests(majorAxis);
                layoutMajorAxis(majorSpan, axis, majorOffsets, majorSpans);
                majorAllocValid = true;

                // flush changes to the children
                updateChildSizes();
            }
        } else {
            if (((int) span) != minorSpan) {
                minorAllocValid = false;
            }
            if (! minorAllocValid) {
                // layout the minor axis
                minorSpan = (int) span;
                checkRequests(axis);
                layoutMinorAxis(minorSpan, axis, minorOffsets, minorSpans);
                minorAllocValid = true;

                // flush changes to the children
                updateChildSizes();
            }
        }
    }

    /**
     * Propagates the current allocations to the child views.
     * <p>
     *  将当前分配传播到子视图。
     * 
     */
    void updateChildSizes() {
        int n = getViewCount();
        if (majorAxis == X_AXIS) {
            for (int i = 0; i < n; i++) {
                View v = getView(i);
                v.setSize((float) majorSpans[i], (float) minorSpans[i]);
            }
        } else {
            for (int i = 0; i < n; i++) {
                View v = getView(i);
                v.setSize((float) minorSpans[i], (float) majorSpans[i]);
            }
        }
    }

    /**
     * Returns the size of the view along an axis.  This is implemented
     * to return zero.
     *
     * <p>
     *  返回沿轴的视图大小。这被实现为返回零。
     * 
     * 
     * @param axis may be either <code>View.X_AXIS</code> or
     *          <code>View.Y_AXIS</code>
     * @return the current span of the view along the given axis, >= 0
     */
    float getSpanOnAxis(int axis) {
        if (axis == majorAxis) {
            return majorSpan;
        } else {
            return minorSpan;
        }
    }

    /**
     * Sets the size of the view.  This should cause
     * layout of the view if the view caches any layout
     * information.  This is implemented to call the
     * layout method with the sizes inside of the insets.
     *
     * <p>
     *  设置视图的大小。如果视图缓存任何布局信息,这应该导致视图的布局。这被实现为调用具有插入内部大小的布局方法。
     * 
     * 
     * @param width the width &gt;= 0
     * @param height the height &gt;= 0
     */
    public void setSize(float width, float height) {
        layout(Math.max(0, (int)(width - getLeftInset() - getRightInset())),
               Math.max(0, (int)(height - getTopInset() - getBottomInset())));
    }

    /**
     * Renders the <code>BoxView</code> using the given
     * rendering surface and area
     * on that surface.  Only the children that intersect
     * the clip bounds of the given <code>Graphics</code>
     * will be rendered.
     *
     * <p>
     *  使用该表面上给定的渲染表面和区域渲染<code> BoxView </code>。只有与给定<code> Graphics </code>的剪辑边界相交的子节点才会被渲染。
     * 
     * 
     * @param g the rendering surface to use
     * @param allocation the allocated region to render into
     * @see View#paint
     */
    public void paint(Graphics g, Shape allocation) {
        Rectangle alloc = (allocation instanceof Rectangle) ?
                           (Rectangle)allocation : allocation.getBounds();
        int n = getViewCount();
        int x = alloc.x + getLeftInset();
        int y = alloc.y + getTopInset();
        Rectangle clip = g.getClipBounds();
        for (int i = 0; i < n; i++) {
            tempRect.x = x + getOffset(X_AXIS, i);
            tempRect.y = y + getOffset(Y_AXIS, i);
            tempRect.width = getSpan(X_AXIS, i);
            tempRect.height = getSpan(Y_AXIS, i);
            int trx0 = tempRect.x, trx1 = trx0 + tempRect.width;
            int try0 = tempRect.y, try1 = try0 + tempRect.height;
            int crx0 = clip.x, crx1 = crx0 + clip.width;
            int cry0 = clip.y, cry1 = cry0 + clip.height;
            // We should paint views that intersect with clipping region
            // even if the intersection has no inside points (is a line).
            // This is needed for supporting views that have zero width, like
            // views that contain only combining marks.
            if ((trx1 >= crx0) && (try1 >= cry0) && (crx1 >= trx0) && (cry1 >= try0)) {
                paintChild(g, tempRect, i);
            }
        }
    }

    /**
     * Fetches the allocation for the given child view.
     * This enables finding out where various views
     * are located.  This is implemented to return
     * <code>null</code> if the layout is invalid,
     * otherwise the superclass behavior is executed.
     *
     * <p>
     *  获取给定子视图的分配。这使得能够找到各种视图位于何处。这被实现为如果布局无效则返回<code> null </code>,否则执行超类行为。
     * 
     * 
     * @param index the index of the child, &gt;= 0 &amp;&amp; &gt; getViewCount()
     * @param a  the allocation to this view
     * @return the allocation to the child; or <code>null</code>
     *          if <code>a</code> is <code>null</code>;
     *          or <code>null</code> if the layout is invalid
     */
    public Shape getChildAllocation(int index, Shape a) {
        if (a != null) {
            Shape ca = super.getChildAllocation(index, a);
            if ((ca != null) && (! isAllocationValid())) {
                // The child allocation may not have been set yet.
                Rectangle r = (ca instanceof Rectangle) ?
                    (Rectangle) ca : ca.getBounds();
                if ((r.width == 0) && (r.height == 0)) {
                    return null;
                }
            }
            return ca;
        }
        return null;
    }

    /**
     * Provides a mapping from the document model coordinate space
     * to the coordinate space of the view mapped to it.  This makes
     * sure the allocation is valid before calling the superclass.
     *
     * <p>
     * 提供从文档模型坐标空间到映射到其的视图的坐标空间的映射。这确保分配在调用超类之前有效。
     * 
     * 
     * @param pos the position to convert &gt;= 0
     * @param a the allocated region to render into
     * @return the bounding box of the given position
     * @exception BadLocationException  if the given position does
     *  not represent a valid location in the associated document
     * @see View#modelToView
     */
    public Shape modelToView(int pos, Shape a, Position.Bias b) throws BadLocationException {
        if (! isAllocationValid()) {
            Rectangle alloc = a.getBounds();
            setSize(alloc.width, alloc.height);
        }
        return super.modelToView(pos, a, b);
    }

    /**
     * Provides a mapping from the view coordinate space to the logical
     * coordinate space of the model.
     *
     * <p>
     *  提供从视图坐标空间到模型的逻辑坐标空间的映射。
     * 
     * 
     * @param x   x coordinate of the view location to convert &gt;= 0
     * @param y   y coordinate of the view location to convert &gt;= 0
     * @param a the allocated region to render into
     * @return the location within the model that best represents the
     *  given point in the view &gt;= 0
     * @see View#viewToModel
     */
    public int viewToModel(float x, float y, Shape a, Position.Bias[] bias) {
        if (! isAllocationValid()) {
            Rectangle alloc = a.getBounds();
            setSize(alloc.width, alloc.height);
        }
        return super.viewToModel(x, y, a, bias);
    }

    /**
     * Determines the desired alignment for this view along an
     * axis.  This is implemented to give the total alignment
     * needed to position the children with the alignment points
     * lined up along the axis orthogonal to the axis that is
     * being tiled.  The axis being tiled will request to be
     * centered (i.e. 0.5f).
     *
     * <p>
     *  确定沿着轴的该视图的期望对准。这被实现为给出定位儿童所需的总对准,其中对准点沿着与正被平铺的轴线正交的轴线排列。平铺的轴将请求居中(即0.5f)。
     * 
     * 
     * @param axis may be either <code>View.X_AXIS</code>
     *   or <code>View.Y_AXIS</code>
     * @return the desired alignment &gt;= 0.0f &amp;&amp; &lt;= 1.0f; this should
     *   be a value between 0.0 and 1.0 where 0 indicates alignment at the
     *   origin and 1.0 indicates alignment to the full span
     *   away from the origin; an alignment of 0.5 would be the
     *   center of the view
     * @exception IllegalArgumentException for an invalid axis
     */
    public float getAlignment(int axis) {
        checkRequests(axis);
        if (axis == majorAxis) {
            return majorRequest.alignment;
        } else {
            return minorRequest.alignment;
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
        checkRequests(axis);
        float marginSpan = (axis == X_AXIS) ? getLeftInset() + getRightInset() :
            getTopInset() + getBottomInset();
        if (axis == majorAxis) {
            return ((float)majorRequest.preferred) + marginSpan;
        } else {
            return ((float)minorRequest.preferred) + marginSpan;
        }
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
        checkRequests(axis);
        float marginSpan = (axis == X_AXIS) ? getLeftInset() + getRightInset() :
            getTopInset() + getBottomInset();
        if (axis == majorAxis) {
            return ((float)majorRequest.minimum) + marginSpan;
        } else {
            return ((float)minorRequest.minimum) + marginSpan;
        }
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
        checkRequests(axis);
        float marginSpan = (axis == X_AXIS) ? getLeftInset() + getRightInset() :
            getTopInset() + getBottomInset();
        if (axis == majorAxis) {
            return ((float)majorRequest.maximum) + marginSpan;
        } else {
            return ((float)minorRequest.maximum) + marginSpan;
        }
    }

    // --- local methods ----------------------------------------------------

    /**
     * Are the allocations for the children still
     * valid?
     *
     * <p>
     *  子系统的分配是否仍然有效?
     * 
     * 
     * @return true if allocations still valid
     */
    protected boolean isAllocationValid() {
        return (majorAllocValid && minorAllocValid);
    }

    /**
     * Determines if a point falls before an allocated region.
     *
     * <p>
     *  确定点是否落在分配的区域之前。
     * 
     * 
     * @param x the X coordinate &gt;= 0
     * @param y the Y coordinate &gt;= 0
     * @param innerAlloc the allocated region; this is the area
     *   inside of the insets
     * @return true if the point lies before the region else false
     */
    protected boolean isBefore(int x, int y, Rectangle innerAlloc) {
        if (majorAxis == View.X_AXIS) {
            return (x < innerAlloc.x);
        } else {
            return (y < innerAlloc.y);
        }
    }

    /**
     * Determines if a point falls after an allocated region.
     *
     * <p>
     *  确定点是否落在分配的区域之后。
     * 
     * 
     * @param x the X coordinate &gt;= 0
     * @param y the Y coordinate &gt;= 0
     * @param innerAlloc the allocated region; this is the area
     *   inside of the insets
     * @return true if the point lies after the region else false
     */
    protected boolean isAfter(int x, int y, Rectangle innerAlloc) {
        if (majorAxis == View.X_AXIS) {
            return (x > (innerAlloc.width + innerAlloc.x));
        } else {
            return (y > (innerAlloc.height + innerAlloc.y));
        }
    }

    /**
     * Fetches the child view at the given coordinates.
     *
     * <p>
     *  在给定的坐标处获取子视图。
     * 
     * 
     * @param x the X coordinate &gt;= 0
     * @param y the Y coordinate &gt;= 0
     * @param alloc the parents inner allocation on entry, which should
     *   be changed to the child's allocation on exit
     * @return the view
     */
    protected View getViewAtPoint(int x, int y, Rectangle alloc) {
        int n = getViewCount();
        if (majorAxis == View.X_AXIS) {
            if (x < (alloc.x + majorOffsets[0])) {
                childAllocation(0, alloc);
                return getView(0);
            }
            for (int i = 0; i < n; i++) {
                if (x < (alloc.x + majorOffsets[i])) {
                    childAllocation(i - 1, alloc);
                    return getView(i - 1);
                }
            }
            childAllocation(n - 1, alloc);
            return getView(n - 1);
        } else {
            if (y < (alloc.y + majorOffsets[0])) {
                childAllocation(0, alloc);
                return getView(0);
            }
            for (int i = 0; i < n; i++) {
                if (y < (alloc.y + majorOffsets[i])) {
                    childAllocation(i - 1, alloc);
                    return getView(i - 1);
                }
            }
            childAllocation(n - 1, alloc);
            return getView(n - 1);
        }
    }

    /**
     * Allocates a region for a child view.
     *
     * <p>
     *  为子视图分配一个区域。
     * 
     * 
     * @param index the index of the child view to
     *   allocate, &gt;= 0 &amp;&amp; &lt; getViewCount()
     * @param alloc the allocated region
     */
    protected void childAllocation(int index, Rectangle alloc) {
        alloc.x += getOffset(X_AXIS, index);
        alloc.y += getOffset(Y_AXIS, index);
        alloc.width = getSpan(X_AXIS, index);
        alloc.height = getSpan(Y_AXIS, index);
    }

    /**
     * Perform layout on the box
     *
     * <p>
     *  在框上执行布局
     * 
     * 
     * @param width the width (inside of the insets) &gt;= 0
     * @param height the height (inside of the insets) &gt;= 0
     */
    protected void layout(int width, int height) {
        setSpanOnAxis(X_AXIS, width);
        setSpanOnAxis(Y_AXIS, height);
    }

    /**
     * Returns the current width of the box.  This is the width that
     * it was last allocated.
     * <p>
     *  返回框的当前宽度。这是最后分配的宽度。
     * 
     * 
     * @return the current width of the box
     */
    public int getWidth() {
        int span;
        if (majorAxis == X_AXIS) {
            span = majorSpan;
        } else {
            span = minorSpan;
        }
        span += getLeftInset() - getRightInset();
        return span;
    }

    /**
     * Returns the current height of the box.  This is the height that
     * it was last allocated.
     * <p>
     *  返回框的当前高度。这是它最后分配的高度。
     * 
     * 
     * @return the current height of the box
     */
    public int getHeight() {
        int span;
        if (majorAxis == Y_AXIS) {
            span = majorSpan;
        } else {
            span = minorSpan;
        }
        span += getTopInset() - getBottomInset();
        return span;
    }

    /**
     * Performs layout for the major axis of the box (i.e. the
     * axis that it represents). The results of the layout (the
     * offset and span for each children) are placed in the given
     * arrays which represent the allocations to the children
     * along the major axis.
     *
     * <p>
     *  执行箱子长轴(即它代表的轴)的布局。布局的结果(每个孩子的偏移和跨度)被放置在给定的数组中,这些数组表示沿着长轴的孩子的分配。
     * 
     * 
     * @param targetSpan the total span given to the view, which
     *  would be used to layout the children
     * @param axis the axis being layed out
     * @param offsets the offsets from the origin of the view for
     *  each of the child views; this is a return value and is
     *  filled in by the implementation of this method
     * @param spans the span of each child view; this is a return
     *  value and is filled in by the implementation of this method
     */
    protected void layoutMajorAxis(int targetSpan, int axis, int[] offsets, int[] spans) {
        /*
         * first pass, calculate the preferred sizes
         * and the flexibility to adjust the sizes.
         * <p>
         * 第一次通过,计算首选尺寸和灵活性来调整尺寸。
         * 
         */
        long preferred = 0;
        int n = getViewCount();
        for (int i = 0; i < n; i++) {
            View v = getView(i);
            spans[i] = (int) v.getPreferredSpan(axis);
            preferred += spans[i];
        }

        /*
         * Second pass, expand or contract by as much as possible to reach
         * the target span.
         * <p>
         *  第二遍,尽可能扩展或收缩到达目标跨度。
         * 
         */

        // determine the adjustment to be made
        long desiredAdjustment = targetSpan - preferred;
        float adjustmentFactor = 0.0f;
        int[] diffs = null;

        if (desiredAdjustment != 0) {
            long totalSpan = 0;
            diffs = new int[n];
            for (int i = 0; i < n; i++) {
                View v = getView(i);
                int tmp;
                if (desiredAdjustment < 0) {
                    tmp = (int)v.getMinimumSpan(axis);
                    diffs[i] = spans[i] - tmp;
                } else {
                    tmp = (int)v.getMaximumSpan(axis);
                    diffs[i] = tmp - spans[i];
                }
                totalSpan += tmp;
            }

            float maximumAdjustment = Math.abs(totalSpan - preferred);
                adjustmentFactor = desiredAdjustment / maximumAdjustment;
                adjustmentFactor = Math.min(adjustmentFactor, 1.0f);
                adjustmentFactor = Math.max(adjustmentFactor, -1.0f);
            }

        // make the adjustments
        int totalOffset = 0;
        for (int i = 0; i < n; i++) {
            offsets[i] = totalOffset;
            if (desiredAdjustment != 0) {
                float adjF = adjustmentFactor * diffs[i];
                spans[i] += Math.round(adjF);
            }
            totalOffset = (int) Math.min((long) totalOffset + (long) spans[i], Integer.MAX_VALUE);
        }
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
     *  would be used to layout the children
     * @param axis the axis being layed out
     * @param offsets the offsets from the origin of the view for
     *  each of the child views; this is a return value and is
     *  filled in by the implementation of this method
     * @param spans the span of each child view; this is a return
     *  value and is filled in by the implementation of this method
     */
    protected void layoutMinorAxis(int targetSpan, int axis, int[] offsets, int[] spans) {
        int n = getViewCount();
        for (int i = 0; i < n; i++) {
            View v = getView(i);
            int max = (int) v.getMaximumSpan(axis);
            if (max < targetSpan) {
                // can't make the child this wide, align it
                float align = v.getAlignment(axis);
                offsets[i] = (int) ((targetSpan - max) * align);
                spans[i] = max;
            } else {
                // make it the target width, or as small as it can get.
                int min = (int)v.getMinimumSpan(axis);
                offsets[i] = 0;
                spans[i] = Math.max(min, targetSpan);
            }
        }
    }

    /**
     * Calculates the size requirements for the major axis
     * <code>axis</code>.
     *
     * <p>
     *  计算长轴<code> axis </code>的大小要求。
     * 
     * 
     * @param axis the axis being studied
     * @param r the <code>SizeRequirements</code> object;
     *          if <code>null</code> one will be created
     * @return the newly initialized <code>SizeRequirements</code> object
     * @see javax.swing.SizeRequirements
     */
    protected SizeRequirements calculateMajorAxisRequirements(int axis, SizeRequirements r) {
        // calculate tiled request
        float min = 0;
        float pref = 0;
        float max = 0;

        int n = getViewCount();
        for (int i = 0; i < n; i++) {
            View v = getView(i);
            min += v.getMinimumSpan(axis);
            pref += v.getPreferredSpan(axis);
            max += v.getMaximumSpan(axis);
        }

        if (r == null) {
            r = new SizeRequirements();
        }
        r.alignment = 0.5f;
        r.minimum = (int) min;
        r.preferred = (int) pref;
        r.maximum = (int) max;
        return r;
    }

    /**
     * Calculates the size requirements for the minor axis
     * <code>axis</code>.
     *
     * <p>
     *  计算短轴<code> axis </code>的大小要求。
     * 
     * 
     * @param axis the axis being studied
     * @param r the <code>SizeRequirements</code> object;
     *          if <code>null</code> one will be created
     * @return the newly initialized <code>SizeRequirements</code> object
     * @see javax.swing.SizeRequirements
     */
    protected SizeRequirements calculateMinorAxisRequirements(int axis, SizeRequirements r) {
        int min = 0;
        long pref = 0;
        int max = Integer.MAX_VALUE;
        int n = getViewCount();
        for (int i = 0; i < n; i++) {
            View v = getView(i);
            min = Math.max((int) v.getMinimumSpan(axis), min);
            pref = Math.max((int) v.getPreferredSpan(axis), pref);
            max = Math.max((int) v.getMaximumSpan(axis), max);
        }

        if (r == null) {
            r = new SizeRequirements();
            r.alignment = 0.5f;
        }
        r.preferred = (int) pref;
        r.minimum = min;
        r.maximum = max;
        return r;
    }

    /**
     * Checks the request cache and update if needed.
     * <p>
     *  检查请求缓存,如果需要更新。
     * 
     * 
     * @param axis the axis being studied
     * @exception IllegalArgumentException if <code>axis</code> is
     *  neither <code>View.X_AXIS</code> nor <code>View.Y_AXIS</code>
     */
    void checkRequests(int axis) {
        if ((axis != X_AXIS) && (axis != Y_AXIS)) {
            throw new IllegalArgumentException("Invalid axis: " + axis);
        }
        if (axis == majorAxis) {
            if (!majorReqValid) {
                majorRequest = calculateMajorAxisRequirements(axis,
                                                              majorRequest);
                majorReqValid = true;
            }
        } else if (! minorReqValid) {
            minorRequest = calculateMinorAxisRequirements(axis, minorRequest);
            minorReqValid = true;
        }
    }

    /**
     * Computes the location and extent of each child view
     * in this <code>BoxView</code> given the <code>targetSpan</code>,
     * which is the width (or height) of the region we have to
     * work with.
     *
     * <p>
     *  在<code> BoxView </code>中给出每个子视图的位置和范围,其中给出了<code> targetSpan </code>,这是我们要处理的区域的宽度(或高度)。
     * 
     * 
     * @param targetSpan the total span given to the view, which
     *  would be used to layout the children
     * @param axis the axis being studied, either
     *          <code>View.X_AXIS</code> or <code>View.Y_AXIS</code>
     * @param offsets an empty array filled by this method with
     *          values specifying the location  of each child view
     * @param spans  an empty array filled by this method with
     *          values specifying the extent of each child view
     */
    protected void baselineLayout(int targetSpan, int axis, int[] offsets, int[] spans) {
        int totalAscent = (int)(targetSpan * getAlignment(axis));
        int totalDescent = targetSpan - totalAscent;

        int n = getViewCount();

        for (int i = 0; i < n; i++) {
            View v = getView(i);
            float align = v.getAlignment(axis);
            float viewSpan;

            if (v.getResizeWeight(axis) > 0) {
                // if resizable then resize to the best fit

                // the smallest span possible
                float minSpan = v.getMinimumSpan(axis);
                // the largest span possible
                float maxSpan = v.getMaximumSpan(axis);

                if (align == 0.0f) {
                    // if the alignment is 0 then we need to fit into the descent
                    viewSpan = Math.max(Math.min(maxSpan, totalDescent), minSpan);
                } else if (align == 1.0f) {
                    // if the alignment is 1 then we need to fit into the ascent
                    viewSpan = Math.max(Math.min(maxSpan, totalAscent), minSpan);
                } else {
                    // figure out the span that we must fit into
                    float fitSpan = Math.min(totalAscent / align,
                                             totalDescent / (1.0f - align));
                    // fit into the calculated span
                    viewSpan = Math.max(Math.min(maxSpan, fitSpan), minSpan);
                }
            } else {
                // otherwise use the preferred spans
                viewSpan = v.getPreferredSpan(axis);
            }

            offsets[i] = totalAscent - (int)(viewSpan * align);
            spans[i] = (int)viewSpan;
        }
    }

    /**
     * Calculates the size requirements for this <code>BoxView</code>
     * by examining the size of each child view.
     *
     * <p>
     *  通过检查每个子视图的大小来计算此<Box> BoxView </code>的大小要求。
     * 
     * 
     * @param axis the axis being studied
     * @param r the <code>SizeRequirements</code> object;
     *          if <code>null</code> one will be created
     * @return the newly initialized <code>SizeRequirements</code> object
     */
    protected SizeRequirements baselineRequirements(int axis, SizeRequirements r) {
        SizeRequirements totalAscent = new SizeRequirements();
        SizeRequirements totalDescent = new SizeRequirements();

        if (r == null) {
            r = new SizeRequirements();
        }

        r.alignment = 0.5f;

        int n = getViewCount();

        // loop through all children calculating the max of all their ascents and
        // descents at minimum, preferred, and maximum sizes
        for (int i = 0; i < n; i++) {
            View v = getView(i);
            float align = v.getAlignment(axis);
            float span;
            int ascent;
            int descent;

            // find the maximum of the preferred ascents and descents
            span = v.getPreferredSpan(axis);
            ascent = (int)(align * span);
            descent = (int)(span - ascent);
            totalAscent.preferred = Math.max(ascent, totalAscent.preferred);
            totalDescent.preferred = Math.max(descent, totalDescent.preferred);

            if (v.getResizeWeight(axis) > 0) {
                // if the view is resizable then do the same for the minimum and
                // maximum ascents and descents
                span = v.getMinimumSpan(axis);
                ascent = (int)(align * span);
                descent = (int)(span - ascent);
                totalAscent.minimum = Math.max(ascent, totalAscent.minimum);
                totalDescent.minimum = Math.max(descent, totalDescent.minimum);

                span = v.getMaximumSpan(axis);
                ascent = (int)(align * span);
                descent = (int)(span - ascent);
                totalAscent.maximum = Math.max(ascent, totalAscent.maximum);
                totalDescent.maximum = Math.max(descent, totalDescent.maximum);
            } else {
                // otherwise use the preferred
                totalAscent.minimum = Math.max(ascent, totalAscent.minimum);
                totalDescent.minimum = Math.max(descent, totalDescent.minimum);
                totalAscent.maximum = Math.max(ascent, totalAscent.maximum);
                totalDescent.maximum = Math.max(descent, totalDescent.maximum);
            }
        }

        // we now have an overall preferred, minimum, and maximum ascent and descent

        // calculate the preferred span as the sum of the preferred ascent and preferred descent
        r.preferred = (int)Math.min((long)totalAscent.preferred + (long)totalDescent.preferred,
                                    Integer.MAX_VALUE);

        // calculate the preferred alignment as the preferred ascent divided by the preferred span
        if (r.preferred > 0) {
            r.alignment = (float)totalAscent.preferred / r.preferred;
        }


        if (r.alignment == 0.0f) {
            // if the preferred alignment is 0 then the minimum and maximum spans are simply
            // the minimum and maximum descents since there's nothing above the baseline
            r.minimum = totalDescent.minimum;
            r.maximum = totalDescent.maximum;
        } else if (r.alignment == 1.0f) {
            // if the preferred alignment is 1 then the minimum and maximum spans are simply
            // the minimum and maximum ascents since there's nothing below the baseline
            r.minimum = totalAscent.minimum;
            r.maximum = totalAscent.maximum;
        } else {
            // we want to honor the preferred alignment so we calculate two possible minimum
            // span values using 1) the minimum ascent and the alignment, and 2) the minimum
            // descent and the alignment. We'll choose the larger of these two numbers.
            r.minimum = Math.round(Math.max(totalAscent.minimum / r.alignment,
                                          totalDescent.minimum / (1.0f - r.alignment)));
            // a similar calculation is made for the maximum but we choose the smaller number.
            r.maximum = Math.round(Math.min(totalAscent.maximum / r.alignment,
                                          totalDescent.maximum / (1.0f - r.alignment)));
        }

        return r;
    }

    /**
     * Fetches the offset of a particular child's current layout.
     * <p>
     *  获取特定孩子当前布局的偏移量。
     * 
     * 
     * @param axis the axis being studied
     * @param childIndex the index of the requested child
     * @return the offset (location) for the specified child
     */
    protected int getOffset(int axis, int childIndex) {
        int[] offsets = (axis == majorAxis) ? majorOffsets : minorOffsets;
        return offsets[childIndex];
    }

    /**
     * Fetches the span of a particular child's current layout.
     * <p>
     *  获取特定孩子当前布局的跨度。
     * 
     * 
     * @param axis the axis being studied
     * @param childIndex the index of the requested child
     * @return the span (width or height) of the specified child
     */
    protected int getSpan(int axis, int childIndex) {
        int[] spans = (axis == majorAxis) ? majorSpans : minorSpans;
        return spans[childIndex];
    }

    /**
     * Determines in which direction the next view lays.
     * Consider the View at index n. Typically the <code>View</code>s
     * are layed out from left to right, so that the <code>View</code>
     * to the EAST will be at index n + 1, and the <code>View</code>
     * to the WEST will be at index n - 1. In certain situations,
     * such as with bidirectional text, it is possible
     * that the <code>View</code> to EAST is not at index n + 1,
     * but rather at index n - 1, or that the <code>View</code>
     * to the WEST is not at index n - 1, but index n + 1.
     * In this case this method would return true,
     * indicating the <code>View</code>s are layed out in
     * descending order. Otherwise the method would return false
     * indicating the <code>View</code>s are layed out in ascending order.
     * <p>
     * If the receiver is laying its <code>View</code>s along the
     * <code>Y_AXIS</code>, this will will return the value from
     * invoking the same method on the <code>View</code>
     * responsible for rendering <code>position</code> and
     * <code>bias</code>. Otherwise this will return false.
     *
     * <p>
     * 确定下一个视图放置的方向。考虑索引n处的视图。
     * 通常,从左到右布置<code> View </code>,以便到EAST的<code> View </code>将位于索引n + 1处,而<code> View </code >到WEST将在索引n-1
     * 处。
     * 确定下一个视图放置的方向。考虑索引n处的视图。
     * 在某些情况下,例如具有双向文本,可能的是,到EAST的<code> View </code>不在索引n + 1,而是在索引n -  1,或者指向WEST的<code> View </code>不在索引n
     * -1,而是索引n + 1.在这种情况下,此方法将返回true,表示<code> View </code> s按降序排列。
     * 确定下一个视图放置的方向。考虑索引n处的视图。否则,该方法将返回false,表示<code> View </code>以升序排列。
     * <p>
     *  如果接收器沿着<code> Y_AXIS </code>放置<code> View </code>,这将返回调用负责呈现的<code> View </code>上的相同方法的值<code> posit
     * 
     * @param position position into the model
     * @param bias either <code>Position.Bias.Forward</code> or
     *          <code>Position.Bias.Backward</code>
     * @return true if the <code>View</code>s surrounding the
     *          <code>View</code> responding for rendering
     *          <code>position</code> and <code>bias</code>
     *          are layed out in descending order; otherwise false
     */
    protected boolean flipEastAndWestAtEnds(int position,
                                            Position.Bias bias) {
        if(majorAxis == Y_AXIS) {
            int testPos = (bias == Position.Bias.Backward) ?
                          Math.max(0, position - 1) : position;
            int index = getViewIndexAtPosition(testPos);
            if(index != -1) {
                View v = getView(index);
                if(v != null && v instanceof CompositeView) {
                    return ((CompositeView)v).flipEastAndWestAtEnds(position,
                                                                    bias);
                }
            }
        }
        return false;
    }

    // --- variables ------------------------------------------------

    int majorAxis;

    int majorSpan;
    int minorSpan;

    /*
     * Request cache
     * <p>
     * ion </code>和<code> bias </code>。
     * 否则这将返回false。
     * 
     */
    boolean majorReqValid;
    boolean minorReqValid;
    SizeRequirements majorRequest;
    SizeRequirements minorRequest;

    /*
     * Allocation cache
     * <p>
     *  请求高速缓存
     * 
     */
    boolean majorAllocValid;
    int[] majorOffsets;
    int[] majorSpans;
    boolean minorAllocValid;
    int[] minorOffsets;
    int[] minorSpans;

    /** used in paint. */
    Rectangle tempRect;
}
