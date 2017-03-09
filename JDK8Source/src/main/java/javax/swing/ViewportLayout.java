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

import java.awt.AWTError;
import java.awt.LayoutManager;
import java.awt.Component;
import java.awt.Container;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.Insets;
import java.io.Serializable;

/**
 * The default layout manager for <code>JViewport</code>.
 * <code>ViewportLayout</code> defines
 * a policy for layout that should be useful for most applications.
 * The viewport makes its view the same size as the viewport,
 * however it will not make the view smaller than its minimum size.
 * As the viewport grows the view is kept bottom justified until
 * the entire view is visible, subsequently the view is kept top
 * justified.
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
 *  <code> JViewport </code>的默认布局管理器。 <code> ViewportLayout </code>定义了一个应用于大多数应用程序的布局策略。
 * 视口使其视图与视口的大小相同,但它不会使视图小于其最小大小。随着视口增长,视图保持底部对齐,直到整个视图可见,随后视图保持顶部对齐。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author Hans Muller
 */
public class ViewportLayout implements LayoutManager, Serializable
{
    // Single instance used by JViewport.
    static ViewportLayout SHARED_INSTANCE = new ViewportLayout();

    /**
     * Adds the specified component to the layout. Not used by this class.
     * <p>
     *  将指定的组件添加到布局。不被这个类使用。
     * 
     * 
     * @param name the name of the component
     * @param c the the component to be added
     */
    public void addLayoutComponent(String name, Component c) { }

    /**
     * Removes the specified component from the layout. Not used by
     * this class.
     * <p>
     *  从布局中删除指定的组件。不被这个类使用。
     * 
     * 
     * @param c the component to remove
     */
    public void removeLayoutComponent(Component c) { }


    /**
     * Returns the preferred dimensions for this layout given the components
     * in the specified target container.
     * <p>
     *  给定指定目标容器中的组件时,返回此布局的首选维度。
     * 
     * 
     * @param parent the component which needs to be laid out
     * @return a <code>Dimension</code> object containing the
     *          preferred dimensions
     * @see #minimumLayoutSize
     */
    public Dimension preferredLayoutSize(Container parent) {
        Component view = ((JViewport)parent).getView();
        if (view == null) {
            return new Dimension(0, 0);
        }
        else if (view instanceof Scrollable) {
            return ((Scrollable)view).getPreferredScrollableViewportSize();
        }
        else {
            return view.getPreferredSize();
        }
    }


    /**
     * Returns the minimum dimensions needed to layout the components
     * contained in the specified target container.
     *
     * <p>
     *  返回布局包含在指定目标容器中的组件所需的最小维度。
     * 
     * 
     * @param parent the component which needs to be laid out
     * @return a <code>Dimension</code> object containing the minimum
     *          dimensions
     * @see #preferredLayoutSize
     */
    public Dimension minimumLayoutSize(Container parent) {
        return new Dimension(4, 4);
    }


    /**
     * Called by the AWT when the specified container needs to be laid out.
     *
     * <p>
     *  当指定的容器需要布局时由AWT调用。
     * 
     * 
     * @param parent  the container to lay out
     *
     * @throws AWTError if the target isn't the container specified to the
     *                      <code>BoxLayout</code> constructor
     */
    public void layoutContainer(Container parent)
    {
        JViewport vp = (JViewport)parent;
        Component view = vp.getView();
        Scrollable scrollableView = null;

        if (view == null) {
            return;
        }
        else if (view instanceof Scrollable) {
            scrollableView = (Scrollable) view;
        }

        /* All of the dimensions below are in view coordinates, except
         * vpSize which we're converting.
         * <p>
         *  我们正在转换的vpSize。
         * 
         */

        Insets insets = vp.getInsets();
        Dimension viewPrefSize = view.getPreferredSize();
        Dimension vpSize = vp.getSize();
        Dimension extentSize = vp.toViewCoordinates(vpSize);
        Dimension viewSize = new Dimension(viewPrefSize);

        if (scrollableView != null) {
            if (scrollableView.getScrollableTracksViewportWidth()) {
                viewSize.width = vpSize.width;
            }
            if (scrollableView.getScrollableTracksViewportHeight()) {
                viewSize.height = vpSize.height;
            }
        }

        Point viewPosition = vp.getViewPosition();

        /* If the new viewport size would leave empty space to the
         * right of the view, right justify the view or left justify
         * the view when the width of the view is smaller than the
         * container.
         * <p>
         * 右视图,右对齐视图或在视图的宽度小于容器时左对齐视图。
         * 
         */
        if (scrollableView == null ||
            vp.getParent() == null ||
            vp.getParent().getComponentOrientation().isLeftToRight()) {
            if ((viewPosition.x + extentSize.width) > viewSize.width) {
                viewPosition.x = Math.max(0, viewSize.width - extentSize.width);
            }
        } else {
            if (extentSize.width > viewSize.width) {
                viewPosition.x = viewSize.width - extentSize.width;
            } else {
                viewPosition.x = Math.max(0, Math.min(viewSize.width - extentSize.width, viewPosition.x));
            }
        }

        /* If the new viewport size would leave empty space below the
         * view, bottom justify the view or top justify the view when
         * the height of the view is smaller than the container.
         * <p>
         *  视图,底部对齐视图或顶部对齐视图,当视图的高度小于容器。
         * 
         */
        if ((viewPosition.y + extentSize.height) > viewSize.height) {
            viewPosition.y = Math.max(0, viewSize.height - extentSize.height);
        }

        /* If we haven't been advised about how the viewports size
         * should change wrt to the viewport, i.e. if the view isn't
         * an instance of Scrollable, then adjust the views size as follows.
         *
         * If the origin of the view is showing and the viewport is
         * bigger than the views preferred size, then make the view
         * the same size as the viewport.
         * <p>
         *  应该将wrt更改为视口,即如果视图不是Scrollable的实例,则按如下所示调整视图大小。
         * 
         */
        if (scrollableView == null) {
            if ((viewPosition.x == 0) && (vpSize.width > viewPrefSize.width)) {
                viewSize.width = vpSize.width;
            }
            if ((viewPosition.y == 0) && (vpSize.height > viewPrefSize.height)) {
                viewSize.height = vpSize.height;
            }
        }
        vp.setViewPosition(viewPosition);
        vp.setViewSize(viewSize);
    }
}
