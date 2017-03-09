/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2001, Oracle and/or its affiliates. All rights reserved.
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
package java.awt;

/**
 * Defines an interface for classes that know how to layout Containers
 * based on a layout constraints object.
 *
 * This interface extends the LayoutManager interface to deal with layouts
 * explicitly in terms of constraint objects that specify how and where
 * components should be added to the layout.
 * <p>
 * This minimal extension to LayoutManager is intended for tool
 * providers who wish to the creation of constraint-based layouts.
 * It does not yet provide full, general support for custom
 * constraint-based layout managers.
 *
 * <p>
 *  为知道如何根据布局约束对象布局容器的类定义接口。
 * 
 *  此接口扩展了LayoutManager接口,以便根据约束对象显式地处理布局,这些约束对象指定应如何以及将组件添加到布局的位置。
 * <p>
 *  这个LayoutManager的最小扩展面向希望创建基于约束的布局的工具提供者。它还没有为基于约束的布局管理器提供全面的支持。
 * 
 * 
 * @see LayoutManager
 * @see Container
 *
 * @author      Jonni Kanerva
 */
public interface LayoutManager2 extends LayoutManager {

    /**
     * Adds the specified component to the layout, using the specified
     * constraint object.
     * <p>
     *  使用指定的约束对象将指定的组件添加到布局。
     * 
     * 
     * @param comp the component to be added
     * @param constraints  where/how the component is added to the layout.
     */
    void addLayoutComponent(Component comp, Object constraints);

    /**
     * Calculates the maximum size dimensions for the specified container,
     * given the components it contains.
     * <p>
     *  在指定容器包含的组件的情况下计算指定容器的最大大小维度。
     * 
     * 
     * @see java.awt.Component#getMaximumSize
     * @see LayoutManager
     */
    public Dimension maximumLayoutSize(Container target);

    /**
     * Returns the alignment along the x axis.  This specifies how
     * the component would like to be aligned relative to other
     * components.  The value should be a number between 0 and 1
     * where 0 represents alignment along the origin, 1 is aligned
     * the furthest away from the origin, 0.5 is centered, etc.
     * <p>
     *  返回沿x轴的对齐。这指定了组件将如何相对于其他组件对齐。该值应为0和1之间的数字,其中0表示沿原点的对齐,1对齐距离原点最远,0.5为中心等。
     * 
     */
    public float getLayoutAlignmentX(Container target);

    /**
     * Returns the alignment along the y axis.  This specifies how
     * the component would like to be aligned relative to other
     * components.  The value should be a number between 0 and 1
     * where 0 represents alignment along the origin, 1 is aligned
     * the furthest away from the origin, 0.5 is centered, etc.
     * <p>
     *  返回沿y轴的对齐。这指定了组件将如何相对于其他组件对齐。该值应为0和1之间的数字,其中0表示沿原点的对齐,1对齐距离原点最远,0.5为中心等。
     * 
     */
    public float getLayoutAlignmentY(Container target);

    /**
     * Invalidates the layout, indicating that if the layout manager
     * has cached information it should be discarded.
     * <p>
     * 使布局无效,指示如果布局管理器具有缓存的信息,它应该被丢弃。
     */
    public void invalidateLayout(Container target);

}
