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
package javax.swing;


import java.awt.*;
import java.beans.ConstructorProperties;
import java.io.Serializable;

/**
 * A layout manager to arrange components over the top
 * of each other.  The requested size of the container
 * will be the largest requested size of the children,
 * taking alignment needs into consideration.
 *
 * The alignment is based upon what is needed to properly
 * fit the children in the allocation area.  The children
 * will be placed such that their alignment points are all
 * on top of each other.
 * <p>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running
 * the same version of Swing.  As of 1.4, support for long term storage
 * of all JavaBeans&trade;
 * has been added to the <code>java.beans</code> package.
 * Please see {@link java.beans.XMLEncoder}.
 *
 * <p>
 *  布局管理器,用于将组件布置在彼此的顶部。请求的容器大小将是所要求的最大的孩子的大小,考虑对齐需要。
 * 
 *  对齐是基于在分配区域中适当地适合孩子所需要的。孩子们将被放置,使他们的对齐点都在彼此的顶部。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author   Timothy Prinzing
 */
public class OverlayLayout implements LayoutManager2,Serializable {

    /**
     * Constructs a layout manager that performs overlay
     * arrangement of the children.  The layout manager
     * created is dedicated to the given container.
     *
     * <p>
     *  构造一个布局管理器,执行孩子的叠加安排。创建的布局管理器专用于给定的容器。
     * 
     * 
     * @param target  the container to do layout against
     */
    @ConstructorProperties({"target"})
    public OverlayLayout(Container target) {
        this.target = target;
    }

    /**
     * Returns the container that uses this layout manager.
     *
     * <p>
     *  返回使用此布局管理器的容器。
     * 
     * 
     * @return the container that uses this layout manager
     *
     * @since 1.6
     */
    public final Container getTarget() {
        return this.target;
    }

    /**
     * Indicates a child has changed its layout related information,
     * which causes any cached calculations to be flushed.
     *
     * <p>
     *  表示子项已更改其布局相关信息,这会导致刷新任何高速缓存的计算。
     * 
     * 
     * @param target the container
     */
    public void invalidateLayout(Container target) {
        checkContainer(target);
        xChildren = null;
        yChildren = null;
        xTotal = null;
        yTotal = null;
    }

    /**
     * Adds the specified component to the layout. Used by
     * this class to know when to invalidate layout.
     *
     * <p>
     *  将指定的组件添加到布局。由此类使用知道什么时候使布局无效。
     * 
     * 
     * @param name the name of the component
     * @param comp the the component to be added
     */
    public void addLayoutComponent(String name, Component comp) {
        invalidateLayout(comp.getParent());
    }

    /**
     * Removes the specified component from the layout. Used by
     * this class to know when to invalidate layout.
     *
     * <p>
     *  从布局中删除指定的组件。由此类使用知道什么时候使布局无效。
     * 
     * 
     * @param comp the component to remove
     */
    public void removeLayoutComponent(Component comp) {
        invalidateLayout(comp.getParent());
    }

    /**
     * Adds the specified component to the layout, using the specified
     * constraint object. Used by this class to know when to invalidate
     * layout.
     *
     * <p>
     * 使用指定的约束对象将指定的组件添加到布局。由此类使用知道什么时候使布局无效。
     * 
     * 
     * @param comp the component to be added
     * @param constraints  where/how the component is added to the layout.
     */
    public void addLayoutComponent(Component comp, Object constraints) {
        invalidateLayout(comp.getParent());
    }

    /**
     * Returns the preferred dimensions for this layout given the components
     * in the specified target container.  Recomputes the layout if it
     * has been invalidated.  Factors in the current inset setting returned
     * by getInsets().
     *
     * <p>
     *  给定指定目标容器中的组件时,返回此布局的首选维度。如果布局已失效,则重新计算布局。 getInsets()返回的当前插入设置中的因素。
     * 
     * 
     * @param target the component which needs to be laid out
     * @return a Dimension object containing the preferred dimensions
     * @see #minimumLayoutSize
     */
    public Dimension preferredLayoutSize(Container target) {
        checkContainer(target);
        checkRequests();

        Dimension size = new Dimension(xTotal.preferred, yTotal.preferred);
        Insets insets = target.getInsets();
        size.width += insets.left + insets.right;
        size.height += insets.top + insets.bottom;
        return size;
    }

    /**
     * Returns the minimum dimensions needed to lay out the components
     * contained in the specified target container.  Recomputes the layout
     * if it has been invalidated, and factors in the current inset setting.
     *
     * <p>
     *  返回布局包含在指定目标容器中的组件所需的最小维度。如果布局已失效,则重新计算布局,并在当前插入设置中重新计算因子。
     * 
     * 
     * @param target the component which needs to be laid out
     * @return a Dimension object containing the minimum dimensions
     * @see #preferredLayoutSize
     */
    public Dimension minimumLayoutSize(Container target) {
        checkContainer(target);
        checkRequests();

        Dimension size = new Dimension(xTotal.minimum, yTotal.minimum);
        Insets insets = target.getInsets();
        size.width += insets.left + insets.right;
        size.height += insets.top + insets.bottom;
        return size;
    }

    /**
     * Returns the maximum dimensions needed to lay out the components
     * contained in the specified target container.  Recomputes the
     * layout if it has been invalidated, and factors in the inset setting
     * returned by <code>getInset</code>.
     *
     * <p>
     *  返回布置包含在指定目标容器中的组件所需的最大尺寸。如果布局已被无效,则重新计算布局,并且由<code> getInset </code>返回插入设置中的因子。
     * 
     * 
     * @param target the component that needs to be laid out
     * @return a <code>Dimension</code> object containing the maximum
     *         dimensions
     * @see #preferredLayoutSize
     */
    public Dimension maximumLayoutSize(Container target) {
        checkContainer(target);
        checkRequests();

        Dimension size = new Dimension(xTotal.maximum, yTotal.maximum);
        Insets insets = target.getInsets();
        size.width += insets.left + insets.right;
        size.height += insets.top + insets.bottom;
        return size;
    }

    /**
     * Returns the alignment along the x axis for the container.
     *
     * <p>
     *  返回容器沿x轴的对齐方式。
     * 
     * 
     * @param target the container
     * @return the alignment &gt;= 0.0f &amp;&amp; &lt;= 1.0f
     */
    public float getLayoutAlignmentX(Container target) {
        checkContainer(target);
        checkRequests();
        return xTotal.alignment;
    }

    /**
     * Returns the alignment along the y axis for the container.
     *
     * <p>
     *  返回容器沿y轴的对齐。
     * 
     * 
     * @param target the container
     * @return the alignment &gt;= 0.0f &amp;&amp; &lt;= 1.0f
     */
    public float getLayoutAlignmentY(Container target) {
        checkContainer(target);
        checkRequests();
        return yTotal.alignment;
    }

    /**
     * Called by the AWT when the specified container needs to be laid out.
     *
     * <p>
     *  当指定的容器需要布局时由AWT调用。
     * 
     * @param target  the container to lay out
     *
     * @exception AWTError  if the target isn't the container specified to the
     *                      constructor
     */
    public void layoutContainer(Container target) {
        checkContainer(target);
        checkRequests();

        int nChildren = target.getComponentCount();
        int[] xOffsets = new int[nChildren];
        int[] xSpans = new int[nChildren];
        int[] yOffsets = new int[nChildren];
        int[] ySpans = new int[nChildren];

        // determine the child placements
        Dimension alloc = target.getSize();
        Insets in = target.getInsets();
        alloc.width -= in.left + in.right;
        alloc.height -= in.top + in.bottom;
        SizeRequirements.calculateAlignedPositions(alloc.width, xTotal,
                                                   xChildren, xOffsets,
                                                   xSpans);
        SizeRequirements.calculateAlignedPositions(alloc.height, yTotal,
                                                   yChildren, yOffsets,
                                                   ySpans);

        // flush changes to the container
        for (int i = 0; i < nChildren; i++) {
            Component c = target.getComponent(i);
            c.setBounds(in.left + xOffsets[i], in.top + yOffsets[i],
                        xSpans[i], ySpans[i]);
        }
    }

    void checkContainer(Container target) {
        if (this.target != target) {
            throw new AWTError("OverlayLayout can't be shared");
        }
    }

    void checkRequests() {
        if (xChildren == null || yChildren == null) {
            // The requests have been invalidated... recalculate
            // the request information.
            int n = target.getComponentCount();
            xChildren = new SizeRequirements[n];
            yChildren = new SizeRequirements[n];
            for (int i = 0; i < n; i++) {
                Component c = target.getComponent(i);
                Dimension min = c.getMinimumSize();
                Dimension typ = c.getPreferredSize();
                Dimension max = c.getMaximumSize();
                xChildren[i] = new SizeRequirements(min.width, typ.width,
                                                    max.width,
                                                    c.getAlignmentX());
                yChildren[i] = new SizeRequirements(min.height, typ.height,
                                                    max.height,
                                                    c.getAlignmentY());
            }

            xTotal = SizeRequirements.getAlignedSizeRequirements(xChildren);
            yTotal = SizeRequirements.getAlignedSizeRequirements(yChildren);
        }
    }

    private Container target;
    private SizeRequirements[] xChildren;
    private SizeRequirements[] yChildren;
    private SizeRequirements xTotal;
    private SizeRequirements yTotal;

}
