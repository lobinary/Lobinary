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

import java.util.Vector;
import java.awt.*;
import javax.swing.event.*;
import javax.swing.SwingConstants;

/**
 * <code>CompositeView</code> is an abstract <code>View</code>
 * implementation which manages one or more child views.
 * (Note that <code>CompositeView</code> is intended
 * for managing relatively small numbers of child views.)
 * <code>CompositeView</code> is intended to be used as
 * a starting point for <code>View</code> implementations,
 * such as <code>BoxView</code>, that will contain child
 * <code>View</code>s. Subclasses that wish to manage the
 * collection of child <code>View</code>s should use the
 * {@link #replace} method.  As <code>View</code> invokes
 * <code>replace</code> during <code>DocumentListener</code>
 * notification, you normally won't need to directly
 * invoke <code>replace</code>.
 *
 * <p>While <code>CompositeView</code>
 * does not impose a layout policy on its child <code>View</code>s,
 * it does allow for inseting the child <code>View</code>s
 * it will contain.  The insets can be set by either
 * {@link #setInsets} or {@link #setParagraphInsets}.
 *
 * <p>In addition to the abstract methods of
 * {@link javax.swing.text.View},
 * subclasses of <code>CompositeView</code> will need to
 * override:
 * <ul>
 * <li>{@link #isBefore} - Used to test if a given
 *     <code>View</code> location is before the visual space
 *     of the <code>CompositeView</code>.
 * <li>{@link #isAfter} - Used to test if a given
 *     <code>View</code> location is after the visual space
 *     of the <code>CompositeView</code>.
 * <li>{@link #getViewAtPoint} - Returns the view at
 *     a given visual location.
 * <li>{@link #childAllocation} - Returns the bounds of
 *     a particular child <code>View</code>.
 *     <code>getChildAllocation</code> will invoke
 *     <code>childAllocation</code> after offseting
 *     the bounds by the <code>Inset</code>s of the
 *     <code>CompositeView</code>.
 * </ul>
 *
 * <p>
 *  <code> CompositeView </code>是一个抽象的<code> View </code>实现,它管理一个或多个子视图。
 *  (注意<code> CompositeView </code>用于管理相对较小数量的子视图。
 * )<code> CompositeView </code>旨在用作<code> View </code>实现的起点,例如<code> BoxView </code>,其将包含子<code> View </code>
 * 。
 *  (注意<code> CompositeView </code>用于管理相对较小数量的子视图。希望管理子<code> View </code>集合的子类应使用{@link #replace}方法。
 * 由于<code> View </code>在<code> DocumentListener </code>通知期间调用<code>替换</code>,所以通常不需要直接调用<code> replace 
 * </code>。
 *  (注意<code> CompositeView </code>用于管理相对较小数量的子视图。希望管理子<code> View </code>集合的子类应使用{@link #replace}方法。
 * 
 *  <p>虽然<code> CompositeView </code>没有对其子<code> View </code>强加布局策略,但它允许插入子<code> View </code> 。
 * 插入可以通过{@link #setInsets}或{@link #setParagraphInsets}设置。
 * 
 *  <p>除了{@link javax.swing.text.View}的抽象方法之外,<code> CompositeView </code>的子类还需要覆盖：
 * <ul>
 * <li> {@ link #isBefore}  - 用于测试给定的<code> View </code>位置是否在<code> CompositeView </code>的可视空间之前。
 *  <li> {@ link #isAfter}  - 用于测试给定的<code> View </code>位置是否位于<code> CompositeView </code>的可视空间之后。
 *  <li> {@ link #getViewAtPoint}  - 返回给定视觉位置的视图。
 *  <li> {@ link #childAllocation}  - 传回特定子项的边界<code> View </code>。
 *  <code> getChildAllocation </code>将在偏离<code> CompositeView </code>的<code> Inset </code>之后调用<code> chi
 * ldAllocation </code>。
 *  <li> {@ link #childAllocation}  - 传回特定子项的边界<code> View </code>。
 * </ul>
 * 
 * 
 * @author  Timothy Prinzing
 */
public abstract class CompositeView extends View {

    /**
     * Constructs a <code>CompositeView</code> for the given element.
     *
     * <p>
     *  为给定元素构造一个<code> CompositeView </code>。
     * 
     * 
     * @param elem  the element this view is responsible for
     */
    public CompositeView(Element elem) {
        super(elem);
        children = new View[1];
        nchildren = 0;
        childAlloc = new Rectangle();
    }

    /**
     * Loads all of the children to initialize the view.
     * This is called by the {@link #setParent}
     * method.  Subclasses can reimplement this to initialize
     * their child views in a different manner.  The default
     * implementation creates a child view for each
     * child element.
     *
     * <p>
     *  加载所有子项以初始化视图。这由{@link #setParent}方法调用。子类可以重新实现这一点,以不同的方式初始化它们的子视图。默认实现为每个子元素创建一个子视图。
     * 
     * 
     * @param f the view factory
     * @see #setParent
     */
    protected void loadChildren(ViewFactory f) {
        if (f == null) {
            // No factory. This most likely indicates the parent view
            // has changed out from under us, bail!
            return;
        }
        Element e = getElement();
        int n = e.getElementCount();
        if (n > 0) {
            View[] added = new View[n];
            for (int i = 0; i < n; i++) {
                added[i] = f.create(e.getElement(i));
            }
            replace(0, 0, added);
        }
    }

    // --- View methods ---------------------------------------------

    /**
     * Sets the parent of the view.
     * This is reimplemented to provide the superclass
     * behavior as well as calling the <code>loadChildren</code>
     * method if this view does not already have children.
     * The children should not be loaded in the
     * constructor because the act of setting the parent
     * may cause them to try to search up the hierarchy
     * (to get the hosting <code>Container</code> for example).
     * If this view has children (the view is being moved
     * from one place in the view hierarchy to another),
     * the <code>loadChildren</code> method will not be called.
     *
     * <p>
     *  设置视图的父级。这被重新实现以提供超类行为以及调用<code> loadChildren </code>方法(如果此视图尚未具有子代)。
     * 不应该在构造函数中加载子代,因为设置父代的行为可能会导致他们尝试在层次结构中搜索(例如获取托管<code> Container </code>)。
     * 如果此视图具有子代(视图正从视图层次结构中的一个位置移动到另一个位置),则不会调用<code> loadChildren </code>方法。
     * 
     * 
     * @param parent the parent of the view, <code>null</code> if none
     */
    public void setParent(View parent) {
        super.setParent(parent);
        if ((parent != null) && (nchildren == 0)) {
            ViewFactory f = getViewFactory();
            loadChildren(f);
        }
    }

    /**
     * Returns the number of child views of this view.
     *
     * <p>
     * 返回此视图的子视图数。
     * 
     * 
     * @return the number of views &gt;= 0
     * @see #getView
     */
    public int getViewCount() {
        return nchildren;
    }

    /**
     * Returns the n-th view in this container.
     *
     * <p>
     *  返回此容器中的第n个视图。
     * 
     * 
     * @param n the number of the desired view, &gt;= 0 &amp;&amp; &lt; getViewCount()
     * @return the view at index <code>n</code>
     */
    public View getView(int n) {
        return children[n];
    }

    /**
     * Replaces child views.  If there are no views to remove
     * this acts as an insert.  If there are no views to
     * add this acts as a remove.  Views being removed will
     * have the parent set to <code>null</code>,
     * and the internal reference to them removed so that they
     * may be garbage collected.
     *
     * <p>
     *  替换子视图。如果没有视图要删除,则作为插入。如果没有视图添加这个行为作为删除。正在删除的视图将父设置为<code> null </code>,并且删除它们的内部引用,以便它们可以被垃圾回收。
     * 
     * 
     * @param offset the starting index into the child views to insert
     *   the new views; &gt;= 0 and &lt;= getViewCount
     * @param length the number of existing child views to remove;
     *   this should be a value &gt;= 0 and &lt;= (getViewCount() - offset)
     * @param views the child views to add; this value can be
     *  <code>null</code>
     *   to indicate no children are being added (useful to remove)
     */
    public void replace(int offset, int length, View[] views) {
        // make sure an array exists
        if (views == null) {
            views = ZERO;
        }

        // update parent reference on removed views
        for (int i = offset; i < offset + length; i++) {
            if (children[i].getParent() == this) {
                // in FlowView.java view might be referenced
                // from two super-views as a child. see logicalView
                children[i].setParent(null);
            }
            children[i] = null;
        }

        // update the array
        int delta = views.length - length;
        int src = offset + length;
        int nmove = nchildren - src;
        int dest = src + delta;
        if ((nchildren + delta) >= children.length) {
            // need to grow the array
            int newLength = Math.max(2*children.length, nchildren + delta);
            View[] newChildren = new View[newLength];
            System.arraycopy(children, 0, newChildren, 0, offset);
            System.arraycopy(views, 0, newChildren, offset, views.length);
            System.arraycopy(children, src, newChildren, dest, nmove);
            children = newChildren;
        } else {
            // patch the existing array
            System.arraycopy(children, src, children, dest, nmove);
            System.arraycopy(views, 0, children, offset, views.length);
        }
        nchildren = nchildren + delta;

        // update parent reference on added views
        for (int i = 0; i < views.length; i++) {
            views[i].setParent(this);
        }
    }

    /**
     * Fetches the allocation for the given child view to
     * render into. This enables finding out where various views
     * are located.
     *
     * <p>
     *  获取要呈现的给定子视图的分配。这使得能够找到各种视图位于何处。
     * 
     * 
     * @param index the index of the child, &gt;= 0 &amp;&amp; &lt; getViewCount()
     * @param a  the allocation to this view
     * @return the allocation to the child
     */
    public Shape getChildAllocation(int index, Shape a) {
        Rectangle alloc = getInsideAllocation(a);
        childAllocation(index, alloc);
        return alloc;
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
     * @param b a bias value of either <code>Position.Bias.Forward</code>
     *  or <code>Position.Bias.Backward</code>
     * @return the bounding box of the given position
     * @exception BadLocationException  if the given position does
     *   not represent a valid location in the associated document
     * @see View#modelToView
     */
    public Shape modelToView(int pos, Shape a, Position.Bias b) throws BadLocationException {
        boolean isBackward = (b == Position.Bias.Backward);
        int testPos = (isBackward) ? Math.max(0, pos - 1) : pos;
        if(isBackward && testPos < getStartOffset()) {
            return null;
        }
        int vIndex = getViewIndexAtPosition(testPos);
        if ((vIndex != -1) && (vIndex < getViewCount())) {
            View v = getView(vIndex);
            if(v != null && testPos >= v.getStartOffset() &&
               testPos < v.getEndOffset()) {
                Shape childShape = getChildAllocation(vIndex, a);
                if (childShape == null) {
                    // We are likely invalid, fail.
                    return null;
                }
                Shape retShape = v.modelToView(pos, childShape, b);
                if(retShape == null && v.getEndOffset() == pos) {
                    if(++vIndex < getViewCount()) {
                        v = getView(vIndex);
                        retShape = v.modelToView(pos, getChildAllocation(vIndex, a), b);
                    }
                }
                return retShape;
            }
        }
        throw new BadLocationException("Position not represented by view",
                                       pos);
    }

    /**
     * Provides a mapping from the document model coordinate space
     * to the coordinate space of the view mapped to it.
     *
     * <p>
     *  提供从文档模型坐标空间到映射到其的视图的坐标空间的映射。
     * 
     * 
     * @param p0 the position to convert &gt;= 0
     * @param b0 the bias toward the previous character or the
     *  next character represented by p0, in case the
     *  position is a boundary of two views; either
     *  <code>Position.Bias.Forward</code> or
     *  <code>Position.Bias.Backward</code>
     * @param p1 the position to convert &gt;= 0
     * @param b1 the bias toward the previous character or the
     *  next character represented by p1, in case the
     *  position is a boundary of two views
     * @param a the allocated region to render into
     * @return the bounding box of the given position is returned
     * @exception BadLocationException  if the given position does
     *   not represent a valid location in the associated document
     * @exception IllegalArgumentException for an invalid bias argument
     * @see View#viewToModel
     */
    public Shape modelToView(int p0, Position.Bias b0, int p1, Position.Bias b1, Shape a) throws BadLocationException {
        if (p0 == getStartOffset() && p1 == getEndOffset()) {
            return a;
        }
        Rectangle alloc = getInsideAllocation(a);
        Rectangle r0 = new Rectangle(alloc);
        View v0 = getViewAtPosition((b0 == Position.Bias.Backward) ?
                                    Math.max(0, p0 - 1) : p0, r0);
        Rectangle r1 = new Rectangle(alloc);
        View v1 = getViewAtPosition((b1 == Position.Bias.Backward) ?
                                    Math.max(0, p1 - 1) : p1, r1);
        if (v0 == v1) {
            if (v0 == null) {
                return a;
            }
            // Range contained in one view
            return v0.modelToView(p0, b0, p1, b1, r0);
        }
        // Straddles some views.
        int viewCount = getViewCount();
        int counter = 0;
        while (counter < viewCount) {
            View v;
            // Views may not be in same order as model.
            // v0 or v1 may be null if there is a gap in the range this
            // view contains.
            if ((v = getView(counter)) == v0 || v == v1) {
                View endView;
                Rectangle retRect;
                Rectangle tempRect = new Rectangle();
                if (v == v0) {
                    retRect = v0.modelToView(p0, b0, v0.getEndOffset(),
                                             Position.Bias.Backward, r0).
                              getBounds();
                    endView = v1;
                }
                else {
                    retRect = v1.modelToView(v1.getStartOffset(),
                                             Position.Bias.Forward,
                                             p1, b1, r1).getBounds();
                    endView = v0;
                }

                // Views entirely covered by range.
                while (++counter < viewCount &&
                       (v = getView(counter)) != endView) {
                    tempRect.setBounds(alloc);
                    childAllocation(counter, tempRect);
                    retRect.add(tempRect);
                }

                // End view.
                if (endView != null) {
                    Shape endShape;
                    if (endView == v1) {
                        endShape = v1.modelToView(v1.getStartOffset(),
                                                  Position.Bias.Forward,
                                                  p1, b1, r1);
                    }
                    else {
                        endShape = v0.modelToView(p0, b0, v0.getEndOffset(),
                                                  Position.Bias.Backward, r0);
                    }
                    if (endShape instanceof Rectangle) {
                        retRect.add((Rectangle)endShape);
                    }
                    else {
                        retRect.add(endShape.getBounds());
                    }
                }
                return retRect;
            }
            counter++;
        }
        throw new BadLocationException("Position not represented by view", p0);
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
     * @param bias either <code>Position.Bias.Forward</code> or
     *  <code>Position.Bias.Backward</code>
     * @return the location within the model that best represents the
     *  given point in the view &gt;= 0
     * @see View#viewToModel
     */
    public int viewToModel(float x, float y, Shape a, Position.Bias[] bias) {
        Rectangle alloc = getInsideAllocation(a);
        if (isBefore((int) x, (int) y, alloc)) {
            // point is before the range represented
            int retValue = -1;

            try {
                retValue = getNextVisualPositionFrom(-1, Position.Bias.Forward,
                                                     a, EAST, bias);
            } catch (BadLocationException ble) { }
            catch (IllegalArgumentException iae) { }
            if(retValue == -1) {
                retValue = getStartOffset();
                bias[0] = Position.Bias.Forward;
            }
            return retValue;
        } else if (isAfter((int) x, (int) y, alloc)) {
            // point is after the range represented.
            int retValue = -1;
            try {
                retValue = getNextVisualPositionFrom(-1, Position.Bias.Forward,
                                                     a, WEST, bias);
            } catch (BadLocationException ble) { }
            catch (IllegalArgumentException iae) { }

            if(retValue == -1) {
                // NOTE: this could actually use end offset with backward.
                retValue = getEndOffset() - 1;
                bias[0] = Position.Bias.Forward;
            }
            return retValue;
        } else {
            // locate the child and pass along the request
            View v = getViewAtPoint((int) x, (int) y, alloc);
            if (v != null) {
              return v.viewToModel(x, y, alloc, bias);
            }
        }
        return -1;
    }

    /**
     * Provides a way to determine the next visually represented model
     * location that one might place a caret.  Some views may not be visible,
     * they might not be in the same order found in the model, or they just
     * might not allow access to some of the locations in the model.
     * This is a convenience method for {@link #getNextNorthSouthVisualPositionFrom}
     * and {@link #getNextEastWestVisualPositionFrom}.
     * This method enables specifying a position to convert
     * within the range of &gt;=0.  If the value is -1, a position
     * will be calculated automatically.  If the value &lt; -1,
     * the {@code BadLocationException} will be thrown.
     *
     * <p>
     *  提供一种方法来确定下一个可视地表示的模型位置,人们可以放置插入符号。某些视图可能不可见,它们可能不是在模型中找到的相同顺序,或者它们可能不允许访问模型中的一些位置。
     * 这是{@link #getNextNorthSouthVisualPositionFrom}和{@link #getNextEastWestVisualPositionFrom}的方便方法。
     * 该方法使得能够指定在> = 0的范围内转换的位置。如果值为-1,将自动计算位置。如果值&lt; -1,将抛出{@code BadLocationException}。
     * 
     * 
     * @param pos the position to convert
     * @param b a bias value of either <code>Position.Bias.Forward</code>
     *  or <code>Position.Bias.Backward</code>
     * @param a the allocated region to render into
     * @param direction the direction from the current position that can
     *  be thought of as the arrow keys typically found on a keyboard;
     *  this may be one of the following:
     *  <ul>
     *  <li><code>SwingConstants.WEST</code>
     *  <li><code>SwingConstants.EAST</code>
     *  <li><code>SwingConstants.NORTH</code>
     *  <li><code>SwingConstants.SOUTH</code>
     *  </ul>
     * @param biasRet an array containing the bias that was checked
     * @return the location within the model that best represents the next
     *  location visual position
     * @exception BadLocationException the given position is not a valid
     *                                 position within the document
     * @exception IllegalArgumentException if <code>direction</code> is invalid
     */
    public int getNextVisualPositionFrom(int pos, Position.Bias b, Shape a,
                                         int direction, Position.Bias[] biasRet)
      throws BadLocationException {
        if (pos < -1) {
            throw new BadLocationException("invalid position", pos);
        }
        Rectangle alloc = getInsideAllocation(a);

        switch (direction) {
        case NORTH:
            return getNextNorthSouthVisualPositionFrom(pos, b, a, direction,
                                                       biasRet);
        case SOUTH:
            return getNextNorthSouthVisualPositionFrom(pos, b, a, direction,
                                                       biasRet);
        case EAST:
            return getNextEastWestVisualPositionFrom(pos, b, a, direction,
                                                     biasRet);
        case WEST:
            return getNextEastWestVisualPositionFrom(pos, b, a, direction,
                                                     biasRet);
        default:
            throw new IllegalArgumentException("Bad direction: " + direction);
        }
    }

    /**
     * Returns the child view index representing the given
     * position in the model.  This is implemented to call the
     * <code>getViewIndexByPosition</code>
     * method for backward compatibility.
     *
     * <p>
     * 返回表示模型中给定位置的子视图索引。这被实现为调用<code> getViewIndexByPosition </code>方法向后兼容。
     * 
     * 
     * @param pos the position &gt;= 0
     * @return  index of the view representing the given position, or
     *   -1 if no view represents that position
     * @since 1.3
     */
    public int getViewIndex(int pos, Position.Bias b) {
        if(b == Position.Bias.Backward) {
            pos -= 1;
        }
        if ((pos >= getStartOffset()) && (pos < getEndOffset())) {
            return getViewIndexAtPosition(pos);
        }
        return -1;
    }

    // --- local methods ----------------------------------------------------


    /**
     * Tests whether a point lies before the rectangle range.
     *
     * <p>
     *  测试点是否位于矩形范围之前。
     * 
     * 
     * @param x the X coordinate &gt;= 0
     * @param y the Y coordinate &gt;= 0
     * @param alloc the rectangle
     * @return true if the point is before the specified range
     */
    protected abstract boolean isBefore(int x, int y, Rectangle alloc);

    /**
     * Tests whether a point lies after the rectangle range.
     *
     * <p>
     *  测试点是否位于矩形范围之后。
     * 
     * 
     * @param x the X coordinate &gt;= 0
     * @param y the Y coordinate &gt;= 0
     * @param alloc the rectangle
     * @return true if the point is after the specified range
     */
    protected abstract boolean isAfter(int x, int y, Rectangle alloc);

    /**
     * Fetches the child view at the given coordinates.
     *
     * <p>
     *  在给定的坐标处获取子视图。
     * 
     * 
     * @param x the X coordinate &gt;= 0
     * @param y the Y coordinate &gt;= 0
     * @param alloc the parent's allocation on entry, which should
     *   be changed to the child's allocation on exit
     * @return the child view
     */
    protected abstract View getViewAtPoint(int x, int y, Rectangle alloc);

    /**
     * Returns the allocation for a given child.
     *
     * <p>
     *  返回给定子项的分配。
     * 
     * 
     * @param index the index of the child, &gt;= 0 &amp;&amp; &lt; getViewCount()
     * @param a  the allocation to the interior of the box on entry,
     *   and the allocation of the child view at the index on exit.
     */
    protected abstract void childAllocation(int index, Rectangle a);

    /**
     * Fetches the child view that represents the given position in
     * the model.  This is implemented to fetch the view in the case
     * where there is a child view for each child element.
     *
     * <p>
     *  获取表示模型中给定位置的子视图。这被实现为在每个子元素存在子视图的情况下获取视图。
     * 
     * 
     * @param pos the position &gt;= 0
     * @param a  the allocation to the interior of the box on entry,
     *   and the allocation of the view containing the position on exit
     * @return  the view representing the given position, or
     *   <code>null</code> if there isn't one
     */
    protected View getViewAtPosition(int pos, Rectangle a) {
        int index = getViewIndexAtPosition(pos);
        if ((index >= 0) && (index < getViewCount())) {
            View v = getView(index);
            if (a != null) {
                childAllocation(index, a);
            }
            return v;
        }
        return null;
    }

    /**
     * Fetches the child view index representing the given position in
     * the model.  This is implemented to fetch the view in the case
     * where there is a child view for each child element.
     *
     * <p>
     *  获取表示模型中给定位置的子视图索引。这被实现为在每个子元素存在子视图的情况下获取视图。
     * 
     * 
     * @param pos the position &gt;= 0
     * @return  index of the view representing the given position, or
     *   -1 if no view represents that position
     */
    protected int getViewIndexAtPosition(int pos) {
        Element elem = getElement();
        return elem.getElementIndex(pos);
    }

    /**
     * Translates the immutable allocation given to the view
     * to a mutable allocation that represents the interior
     * allocation (i.e. the bounds of the given allocation
     * with the top, left, bottom, and right insets removed.
     * It is expected that the returned value would be further
     * mutated to represent an allocation to a child view.
     * This is implemented to reuse an instance variable so
     * it avoids creating excessive Rectangles.  Typically
     * the result of calling this method would be fed to
     * the <code>childAllocation</code> method.
     *
     * <p>
     *  将给予视图的不可变分配转换为表示内部分配的可变分配(即,删除了顶部,左侧,底部和右侧插入的给定分配的边界),期望返回的值将被进一步突变为表示对子视图的分配,这是为了重用一个实例变量,所以它避免创建过多
     * 的Rectangles。
     * 通常调用这个方法的结果将被送到<code> childAllocation </code>方法。
     * 
     * 
     * @param a the allocation given to the view
     * @return the allocation that represents the inside of the
     *   view after the margins have all been removed; if the
     *   given allocation was <code>null</code>,
     *   the return value is <code>null</code>
     */
    protected Rectangle getInsideAllocation(Shape a) {
        if (a != null) {
            // get the bounds, hopefully without allocating
            // a new rectangle.  The Shape argument should
            // not be modified... we copy it into the
            // child allocation.
            Rectangle alloc;
            if (a instanceof Rectangle) {
                alloc = (Rectangle) a;
            } else {
                alloc = a.getBounds();
            }

            childAlloc.setBounds(alloc);
            childAlloc.x += getLeftInset();
            childAlloc.y += getTopInset();
            childAlloc.width -= getLeftInset() + getRightInset();
            childAlloc.height -= getTopInset() + getBottomInset();
            return childAlloc;
        }
        return null;
    }

    /**
     * Sets the insets from the paragraph attributes specified in
     * the given attributes.
     *
     * <p>
     *  根据给定属性中指定的段属性设置插入。
     * 
     * 
     * @param attr the attributes
     */
    protected void setParagraphInsets(AttributeSet attr) {
        // Since version 1.1 doesn't have scaling and assumes
        // a pixel is equal to a point, we just cast the point
        // sizes to integers.
        top = (short) StyleConstants.getSpaceAbove(attr);
        left = (short) StyleConstants.getLeftIndent(attr);
        bottom = (short) StyleConstants.getSpaceBelow(attr);
        right = (short) StyleConstants.getRightIndent(attr);
    }

    /**
     * Sets the insets for the view.
     *
     * <p>
     *  设置视图的插入。
     * 
     * 
     * @param top the top inset &gt;= 0
     * @param left the left inset &gt;= 0
     * @param bottom the bottom inset &gt;= 0
     * @param right the right inset &gt;= 0
     */
    protected void setInsets(short top, short left, short bottom, short right) {
        this.top = top;
        this.left = left;
        this.right = right;
        this.bottom = bottom;
    }

    /**
     * Gets the left inset.
     *
     * <p>
     *  获取左插图。
     * 
     * 
     * @return the inset &gt;= 0
     */
    protected short getLeftInset() {
        return left;
    }

    /**
     * Gets the right inset.
     *
     * <p>
     *  获取正确的插图。
     * 
     * 
     * @return the inset &gt;= 0
     */
    protected short getRightInset() {
        return right;
    }

    /**
     * Gets the top inset.
     *
     * <p>
     *  获取顶部插入。
     * 
     * 
     * @return the inset &gt;= 0
     */
    protected short getTopInset() {
        return top;
    }

    /**
     * Gets the bottom inset.
     *
     * <p>
     *  获取底部插图。
     * 
     * 
     * @return the inset &gt;= 0
     */
    protected short getBottomInset() {
        return bottom;
    }

    /**
     * Returns the next visual position for the cursor, in either the
     * north or south direction.
     *
     * <p>
     * 返回光标在北或南方向的下一个视觉位置。
     * 
     * 
     * @param pos the position to convert &gt;= 0
     * @param b a bias value of either <code>Position.Bias.Forward</code>
     *  or <code>Position.Bias.Backward</code>
     * @param a the allocated region to render into
     * @param direction the direction from the current position that can
     *  be thought of as the arrow keys typically found on a keyboard;
     *  this may be one of the following:
     *  <ul>
     *  <li><code>SwingConstants.NORTH</code>
     *  <li><code>SwingConstants.SOUTH</code>
     *  </ul>
     * @param biasRet an array containing the bias that was checked
     * @return the location within the model that best represents the next
     *  north or south location
     * @exception BadLocationException
     * @exception IllegalArgumentException if <code>direction</code> is invalid
     * @see #getNextVisualPositionFrom
     *
     * @return the next position west of the passed in position
     */
    protected int getNextNorthSouthVisualPositionFrom(int pos, Position.Bias b,
                                                      Shape a, int direction,
                                                      Position.Bias[] biasRet)
                                                throws BadLocationException {
        return Utilities.getNextVisualPositionFrom(
                            this, pos, b, a, direction, biasRet);
    }

    /**
     * Returns the next visual position for the cursor, in either the
     * east or west direction.
     *
     * <p>
     *  返回光标在东或西方向的下一个视觉位置。
     * 
     * 
    * @param pos the position to convert &gt;= 0
     * @param b a bias value of either <code>Position.Bias.Forward</code>
     *  or <code>Position.Bias.Backward</code>
     * @param a the allocated region to render into
     * @param direction the direction from the current position that can
     *  be thought of as the arrow keys typically found on a keyboard;
     *  this may be one of the following:
     *  <ul>
     *  <li><code>SwingConstants.WEST</code>
     *  <li><code>SwingConstants.EAST</code>
     *  </ul>
     * @param biasRet an array containing the bias that was checked
     * @return the location within the model that best represents the next
     *  west or east location
     * @exception BadLocationException
     * @exception IllegalArgumentException if <code>direction</code> is invalid
     * @see #getNextVisualPositionFrom
     */
    protected int getNextEastWestVisualPositionFrom(int pos, Position.Bias b,
                                                    Shape a,
                                                    int direction,
                                                    Position.Bias[] biasRet)
                                                throws BadLocationException {
        return Utilities.getNextVisualPositionFrom(
                            this, pos, b, a, direction, biasRet);
    }

    /**
     * Determines in which direction the next view lays.
     * Consider the <code>View</code> at index n. Typically the
     * <code>View</code>s are layed out from left to right,
     * so that the <code>View</code> to the EAST will be
     * at index n + 1, and the <code>View</code> to the WEST
     * will be at index n - 1. In certain situations,
     * such as with bidirectional text, it is possible
     * that the <code>View</code> to EAST is not at index n + 1,
     * but rather at index n - 1, or that the <code>View</code>
     * to the WEST is not at index n - 1, but index n + 1.
     * In this case this method would return true, indicating the
     * <code>View</code>s are layed out in descending order.
     * <p>
     * This unconditionally returns false, subclasses should override this
     * method if there is the possibility for laying <code>View</code>s in
     * descending order.
     *
     * <p>
     *  确定下一个视图的放置方向。考虑索引n处的<code> View </code>。
     * 通常,从左到右布置<code> View </code>,以便到EAST的<code> View </code>将位于索引n + 1处,而<code> View </code >到WEST将在索引n-1
     * 处。
     *  确定下一个视图的放置方向。考虑索引n处的<code> View </code>。
     * 在某些情况下,例如具有双向文本,可能的是,到EAST的<code> View </code>不在索引n + 1,而是在索引n -  1,或者指向WEST的<code> View </code>不在索引n
     * 
     * @param position position into the model
     * @param bias either <code>Position.Bias.Forward</code> or
     *          <code>Position.Bias.Backward</code>
     * @return false
     */
    protected boolean flipEastAndWestAtEnds(int position,
                                            Position.Bias bias) {
        return false;
    }


    // ---- member variables ---------------------------------------------


    private static View[] ZERO = new View[0];

    private View[] children;
    private int nchildren;
    private short left;
    private short right;
    private short top;
    private short bottom;
    private Rectangle childAlloc;
}
