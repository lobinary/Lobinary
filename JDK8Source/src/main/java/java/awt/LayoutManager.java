/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2005, Oracle and/or its affiliates. All rights reserved.
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
 * Defines the interface for classes that know how to lay out
 * <code>Container</code>s.
 * <p>
 * Swing's painting architecture assumes the children of a
 * <code>JComponent</code> do not overlap.  If a
 * <code>JComponent</code>'s <code>LayoutManager</code> allows
 * children to overlap, the <code>JComponent</code> must override
 * <code>isOptimizedDrawingEnabled</code> to return false.
 *
 * <p>
 *  定义知道如何布局<code> Container </code>的类的接口。
 * <p>
 *  Swing的绘画架构假设<code> JComponent </code>的子代不重叠。
 * 如果<code> JComponent </code>的<code> LayoutManager </code>允许子项重叠,则<code> JComponent </code>必须覆盖<code> i
 * sOptimizedDrawingEnabled </code>才能返回false。
 *  Swing的绘画架构假设<code> JComponent </code>的子代不重叠。
 * 
 * 
 * @see Container
 * @see javax.swing.JComponent#isOptimizedDrawingEnabled
 *
 * @author      Sami Shaio
 * @author      Arthur van Hoff
 */
public interface LayoutManager {
    /**
     * If the layout manager uses a per-component string,
     * adds the component <code>comp</code> to the layout,
     * associating it
     * with the string specified by <code>name</code>.
     *
     * <p>
     *  如果布局管理器使用每组件字符串,则将组件<code> comp </code>添加到布局,将其与由<code> name </code>指定的字符串相关联。
     * 
     * 
     * @param name the string to be associated with the component
     * @param comp the component to be added
     */
    void addLayoutComponent(String name, Component comp);

    /**
     * Removes the specified component from the layout.
     * <p>
     *  从布局中删除指定的组件。
     * 
     * 
     * @param comp the component to be removed
     */
    void removeLayoutComponent(Component comp);

    /**
     * Calculates the preferred size dimensions for the specified
     * container, given the components it contains.
     * <p>
     *  计算指定容器的首选大小维度,给定其包含的组件。
     * 
     * 
     * @param parent the container to be laid out
     *
     * @see #minimumLayoutSize
     */
    Dimension preferredLayoutSize(Container parent);

    /**
     * Calculates the minimum size dimensions for the specified
     * container, given the components it contains.
     * <p>
     *  在指定容器包含的组件的情况下,计算指定容器的最小大小维。
     * 
     * 
     * @param parent the component to be laid out
     * @see #preferredLayoutSize
     */
    Dimension minimumLayoutSize(Container parent);

    /**
     * Lays out the specified container.
     * <p>
     *  退出指定的容器。
     * 
     * @param parent the container to be laid out
     */
    void layoutContainer(Container parent);
}
