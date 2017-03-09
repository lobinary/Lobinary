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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Set;
import javax.swing.SwingUtilities;
import javax.swing.event.*;

/**
 * Component decorator that implements the view interface.  The
 * entire element is used to represent the component.  This acts
 * as a gateway from the display-only View implementations to
 * interactive lightweight components (ie it allows components
 * to be embedded into the View hierarchy).
 * <p>
 * The component is placed relative to the text baseline
 * according to the value returned by
 * <code>Component.getAlignmentY</code>.  For Swing components
 * this value can be conveniently set using the method
 * <code>JComponent.setAlignmentY</code>.  For example, setting
 * a value of <code>0.75</code> will cause 75 percent of the
 * component to be above the baseline, and 25 percent of the
 * component to be below the baseline.
 * <p>
 * This class is implemented to do the extra work necessary to
 * work properly in the presence of multiple threads (i.e. from
 * asynchronous notification of model changes for example) by
 * ensuring that all component access is done on the event thread.
 * <p>
 * The component used is determined by the return value of the
 * createComponent method.  The default implementation of this
 * method is to return the component held as an attribute of
 * the element (by calling StyleConstants.getComponent).  A
 * limitation of this behavior is that the component cannot
 * be used by more than one text component (i.e. with a shared
 * model).  Subclasses can remove this constraint by implementing
 * the createComponent to actually create a component based upon
 * some kind of specification contained in the attributes.  The
 * ObjectView class in the html package is an example of a
 * ComponentView implementation that supports multiple component
 * views of a shared model.
 *
 * <p>
 *  实现视图接口的组件装饰器。整个元素用于表示组件。这充当从仅显示的View实现到交互式轻量组件(即它允许组件嵌入View层次结构)的网关。
 * <p>
 *  组件根据<code> Component.getAlignmentY </code>返回的值相对于文本基线放置。
 * 对于Swing组件,可以使用方法<code> JComponent.setAlignmentY </code>方便地设置此值。
 * 例如,设置<code> 0.75 </code>的值将使75％的组件高于基线,25％的组件低于基线。
 * <p>
 *  该类被实现为在存在多个线程(例如,从模型变化的异步通知)的情况下通过确保在事件线程上完成所有组件访问来进行必要的额外工作。
 * <p>
 * 使用的组件由createComponent方法的返回值确定。此方法的默认实现是返回作为元素的属性持有的组件(通过调用StyleConstants.getComponent)。
 * 此行为的限制是组件不能由多于一个文本组件(即具有共享模型)使用。子类可以通过实现createComponent来删除此约束,以根据属性中包含的某种规范实际创建组件。
 *  html包中的ObjectView类是支持共享模型的多个组件视图的ComponentView实现的示例。
 * 
 * 
 * @author Timothy Prinzing
 */
public class ComponentView extends View  {

    /**
     * Creates a new ComponentView object.
     *
     * <p>
     *  创建一个新的ComponentView对象。
     * 
     * 
     * @param elem the element to decorate
     */
    public ComponentView(Element elem) {
        super(elem);
    }

    /**
     * Create the component that is associated with
     * this view.  This will be called when it has
     * been determined that a new component is needed.
     * This would result from a call to setParent or
     * as a result of being notified that attributes
     * have changed.
     * <p>
     *  创建与此视图相关联的组件。当确定需要新组件时,将调用此方法。这是由于调用setParent或者通知属性已更改的结果。
     * 
     */
    protected Component createComponent() {
        AttributeSet attr = getElement().getAttributes();
        Component comp = StyleConstants.getComponent(attr);
        return comp;
    }

    /**
     * Fetch the component associated with the view.
     * <p>
     *  获取与视图相关联的组件。
     * 
     */
    public final Component getComponent() {
        return createdC;
    }

    // --- View methods ---------------------------------------------

    /**
     * The real paint behavior occurs naturally from the association
     * that the component has with its parent container (the same
     * container hosting this view).  This is implemented to do nothing.
     *
     * <p>
     *  真正的绘画行为自然地发生在组件与其父容器(托管此视图的相同容器)之间的关联。这被实现为什么也不做。
     * 
     * 
     * @param g the graphics context
     * @param a the shape
     * @see View#paint
     */
    public void paint(Graphics g, Shape a) {
        if (c != null) {
            Rectangle alloc = (a instanceof Rectangle) ?
                (Rectangle) a : a.getBounds();
            c.setBounds(alloc.x, alloc.y, alloc.width, alloc.height);
        }
    }

    /**
     * Determines the preferred span for this view along an
     * axis.  This is implemented to return the value
     * returned by Component.getPreferredSize along the
     * axis of interest.
     *
     * <p>
     *  确定沿着轴的此视图的首选跨度。这被实现为返回由Component.getPreferredSize沿着感兴趣的轴返回的值。
     * 
     * 
     * @param axis may be either View.X_AXIS or View.Y_AXIS
     * @return   the span the view would like to be rendered into &gt;=0.
     *           Typically the view is told to render into the span
     *           that is returned, although there is no guarantee.
     *           The parent may choose to resize or break the view.
     * @exception IllegalArgumentException for an invalid axis
     */
    public float getPreferredSpan(int axis) {
        if ((axis != X_AXIS) && (axis != Y_AXIS)) {
            throw new IllegalArgumentException("Invalid axis: " + axis);
        }
        if (c != null) {
            Dimension size = c.getPreferredSize();
            if (axis == View.X_AXIS) {
                return size.width;
            } else {
                return size.height;
            }
        }
        return 0;
    }

    /**
     * Determines the minimum span for this view along an
     * axis.  This is implemented to return the value
     * returned by Component.getMinimumSize along the
     * axis of interest.
     *
     * <p>
     * 确定沿轴的此视图的最小跨度。这被实现为返回由Component.getMinimumSize返回的值沿着感兴趣的轴。
     * 
     * 
     * @param axis may be either View.X_AXIS or View.Y_AXIS
     * @return   the span the view would like to be rendered into &gt;=0.
     *           Typically the view is told to render into the span
     *           that is returned, although there is no guarantee.
     *           The parent may choose to resize or break the view.
     * @exception IllegalArgumentException for an invalid axis
     */
    public float getMinimumSpan(int axis) {
        if ((axis != X_AXIS) && (axis != Y_AXIS)) {
            throw new IllegalArgumentException("Invalid axis: " + axis);
        }
        if (c != null) {
            Dimension size = c.getMinimumSize();
            if (axis == View.X_AXIS) {
                return size.width;
            } else {
                return size.height;
            }
        }
        return 0;
    }

    /**
     * Determines the maximum span for this view along an
     * axis.  This is implemented to return the value
     * returned by Component.getMaximumSize along the
     * axis of interest.
     *
     * <p>
     *  确定沿轴的此视图的最大跨度。这被实现为返回由Component.getMaximumSize返回的值沿着感兴趣的轴。
     * 
     * 
     * @param axis may be either View.X_AXIS or View.Y_AXIS
     * @return   the span the view would like to be rendered into &gt;=0.
     *           Typically the view is told to render into the span
     *           that is returned, although there is no guarantee.
     *           The parent may choose to resize or break the view.
     * @exception IllegalArgumentException for an invalid axis
     */
    public float getMaximumSpan(int axis) {
        if ((axis != X_AXIS) && (axis != Y_AXIS)) {
            throw new IllegalArgumentException("Invalid axis: " + axis);
        }
        if (c != null) {
            Dimension size = c.getMaximumSize();
            if (axis == View.X_AXIS) {
                return size.width;
            } else {
                return size.height;
            }
        }
        return 0;
    }

    /**
     * Determines the desired alignment for this view along an
     * axis.  This is implemented to give the alignment of the
     * embedded component.
     *
     * <p>
     *  确定沿着轴的该视图的期望对准。这被实现以给出嵌入式组件的对齐。
     * 
     * 
     * @param axis may be either View.X_AXIS or View.Y_AXIS
     * @return the desired alignment.  This should be a value
     *   between 0.0 and 1.0 where 0 indicates alignment at the
     *   origin and 1.0 indicates alignment to the full span
     *   away from the origin.  An alignment of 0.5 would be the
     *   center of the view.
     */
    public float getAlignment(int axis) {
        if (c != null) {
            switch (axis) {
            case View.X_AXIS:
                return c.getAlignmentX();
            case View.Y_AXIS:
                return c.getAlignmentY();
            }
        }
        return super.getAlignment(axis);
    }

    /**
     * Sets the parent for a child view.
     * The parent calls this on the child to tell it who its
     * parent is, giving the view access to things like
     * the hosting Container.  The superclass behavior is
     * executed, followed by a call to createComponent if
     * the parent view parameter is non-null and a component
     * has not yet been created. The embedded components parent
     * is then set to the value returned by <code>getContainer</code>.
     * If the parent view parameter is null, this view is being
     * cleaned up, thus the component is removed from its parent.
     * <p>
     * The changing of the component hierarchy will
     * touch the component lock, which is the one thing
     * that is not safe from the View hierarchy.  Therefore,
     * this functionality is executed immediately if on the
     * event thread, or is queued on the event queue if
     * called from another thread (notification of change
     * from an asynchronous update).
     *
     * <p>
     *  设置子视图的父级。父对象调用这个子对象来告诉它的父对象是什么,让视图访问像托管容器这样的东西。执行超类行为,然后调用createComponent,如果父视图参数为非空,并且尚未创建组件。
     * 嵌入的组件parent然后设置为<code> getContainer </code>返回的值。如果父视图参数为null,则此视图正在被清理,因此组件将从其父视图中删除。
     * <p>
     *  组件层次结构的更改将触及组件锁定,这是从View层次结构中不安全的一件事情。因此,如果在事件线程上,则该函数立即执行,或者如果从另一线程调用(从异步更新改变的通知),则在事件队列上排队。
     * 
     * 
     * @param p the parent
     */
    public void setParent(View p) {
        super.setParent(p);
        if (SwingUtilities.isEventDispatchThread()) {
            setComponentParent();
        } else {
            Runnable callSetComponentParent = new Runnable() {
                public void run() {
                    Document doc = getDocument();
                    try {
                        if (doc instanceof AbstractDocument) {
                            ((AbstractDocument)doc).readLock();
                        }
                        setComponentParent();
                        Container host = getContainer();
                        if (host != null) {
                            preferenceChanged(null, true, true);
                            host.repaint();
                        }
                    } finally {
                        if (doc instanceof AbstractDocument) {
                            ((AbstractDocument)doc).readUnlock();
                        }
                    }
                }
            };
            SwingUtilities.invokeLater(callSetComponentParent);
        }
    }

    /**
     * Set the parent of the embedded component
     * with assurance that it is thread-safe.
     * <p>
     *  设置嵌入式组件的父级,确保它是线程安全的。
     * 
     */
    void setComponentParent() {
        View p = getParent();
        if (p != null) {
            Container parent = getContainer();
            if (parent != null) {
                if (c == null) {
                    // try to build a component
                    Component comp = createComponent();
                    if (comp != null) {
                        createdC = comp;
                        c = new Invalidator(comp);
                    }
                }
                if (c != null) {
                    if (c.getParent() == null) {
                        // components associated with the View tree are added
                        // to the hosting container with the View as a constraint.
                        parent.add(c, this);
                        parent.addPropertyChangeListener("enabled", c);
                    }
                }
            }
        } else {
            if (c != null) {
                Container parent = c.getParent();
                if (parent != null) {
                    // remove the component from its hosting container
                    parent.remove(c);
                    parent.removePropertyChangeListener("enabled", c);
                }
            }
        }
    }

    /**
     * Provides a mapping from the coordinate space of the model to
     * that of the view.
     *
     * <p>
     *  提供从模型的坐标空间到视图的坐标空间的映射。
     * 
     * 
     * @param pos the position to convert &gt;=0
     * @param a the allocated region to render into
     * @return the bounding box of the given position is returned
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
     * 提供从视图坐标空间到模型的逻辑坐标空间的映射。
     * 
     * 
     * @param x the X coordinate &gt;=0
     * @param y the Y coordinate &gt;=0
     * @param a the allocated region to render into
     * @return the location within the model that best represents
     *    the given point in the view
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

    private Component createdC;
    private Invalidator c;

    /**
     * This class feeds the invalidate back to the
     * hosting View.  This is needed to get the View
     * hierarchy to consider giving the component
     * a different size (i.e. layout may have been
     * cached between the associated view and the
     * container hosting this component).
     * <p>
     *  这个类将无效对象返回到托管View。这是需要获得View层次结构考虑给组件不同的大小(即布局可能已被缓存在相关联的视图和托管此组件的容器之间)。
     * 
     */
    class Invalidator extends Container implements PropertyChangeListener {

        // NOTE: When we remove this class we are going to have to some
        // how enforce setting of the focus traversal keys on the children
        // so that they don't inherit them from the JEditorPane. We need
        // to do this as JEditorPane has abnormal bindings (it is a focus cycle
        // root) and the children typically don't want these bindings as well.

        Invalidator(Component child) {
            setLayout(null);
            add(child);
            cacheChildSizes();
        }

        /**
         * The components invalid layout needs
         * to be propagated through the view hierarchy
         * so the views (which position the component)
         * can have their layout recomputed.
         * <p>
         *  组件无效布局需要通过视图层次结构传播,因此视图(定位组件)可以重新计算其布局。
         * 
         */
        public void invalidate() {
            super.invalidate();
            if (getParent() != null) {
                preferenceChanged(null, true, true);
            }
        }

        public void doLayout() {
            cacheChildSizes();
        }

        public void setBounds(int x, int y, int w, int h) {
            super.setBounds(x, y, w, h);
            if (getComponentCount() > 0) {
                getComponent(0).setSize(w, h);
            }
            cacheChildSizes();
        }

        public void validateIfNecessary() {
            if (!isValid()) {
                validate();
             }
        }

        private void cacheChildSizes() {
            if (getComponentCount() > 0) {
                Component child = getComponent(0);
                min = child.getMinimumSize();
                pref = child.getPreferredSize();
                max = child.getMaximumSize();
                yalign = child.getAlignmentY();
                xalign = child.getAlignmentX();
            } else {
                min = pref = max = new Dimension(0, 0);
            }
        }

        /**
         * Shows or hides this component depending on the value of parameter
         * <code>b</code>.
         * <p>
         *  根据参数<code> b </code>的值显示或隐藏此组件。
         * 
         * 
         * @param b If <code>true</code>, shows this component;
         * otherwise, hides this component.
         * @see #isVisible
         * @since JDK1.1
         */
        public void setVisible(boolean b) {
            super.setVisible(b);
            if (getComponentCount() > 0) {
                getComponent(0).setVisible(b);
            }
        }

        /**
         * Overridden to fix 4759054. Must return true so that content
         * is painted when inside a CellRendererPane which is normally
         * invisible.
         * <p>
         *  重写以修复4759054.必须返回true,以便在通常不可见的CellRendererPane内部绘制内容。
         */
        public boolean isShowing() {
            return true;
        }

        public Dimension getMinimumSize() {
            validateIfNecessary();
            return min;
        }

        public Dimension getPreferredSize() {
            validateIfNecessary();
            return pref;
        }

        public Dimension getMaximumSize() {
            validateIfNecessary();
            return max;
        }

        public float getAlignmentX() {
            validateIfNecessary();
            return xalign;
        }

        public float getAlignmentY() {
            validateIfNecessary();
            return yalign;
        }

        public Set<AWTKeyStroke> getFocusTraversalKeys(int id) {
            return KeyboardFocusManager.getCurrentKeyboardFocusManager().
                    getDefaultFocusTraversalKeys(id);
        }

        public void propertyChange(PropertyChangeEvent ev) {
            Boolean enable = (Boolean) ev.getNewValue();
            if (getComponentCount() > 0) {
                getComponent(0).setEnabled(enable);
            }
        }

        Dimension min;
        Dimension pref;
        Dimension max;
        float yalign;
        float xalign;

    }

}
