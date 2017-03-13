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


import javax.swing.border.*;

import java.awt.LayoutManager;
import java.awt.Component;
import java.awt.Container;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.Insets;
import java.io.Serializable;


/**
 * The layout manager used by <code>JScrollPane</code>.
 * <code>JScrollPaneLayout</code> is
 * responsible for nine components: a viewport, two scrollbars,
 * a row header, a column header, and four "corner" components.
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
 *  <code> JScrollPane </code> <code> JScrollPaneLayout </code>使用的布局管理器负责九个组件：视口,两个滚动条,行标题,列标题和四个"角"组件
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将不与未来的Swing版本兼容当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 * 支持长期存储所有JavaBeans&trade;已添加到<code> javabeans </code>包中请参见{@link javabeansXMLEncoder}。
 * 
 * 
 * @see JScrollPane
 * @see JViewport
 *
 * @author Hans Muller
 */
public class ScrollPaneLayout
    implements LayoutManager, ScrollPaneConstants, Serializable
{

    /**
     * The scrollpane's viewport child.
     * Default is an empty <code>JViewport</code>.
     * <p>
     * 滚动条的视口子项默认是一个空的<code> JViewport </code>
     * 
     * 
     * @see JScrollPane#setViewport
     */
    protected JViewport viewport;


    /**
     * The scrollpane's vertical scrollbar child.
     * Default is a <code>JScrollBar</code>.
     * <p>
     *  滚动条的垂直滚动条子项默认是一个<code> JScrollBar </code>
     * 
     * 
     * @see JScrollPane#setVerticalScrollBar
     */
    protected JScrollBar vsb;


    /**
     * The scrollpane's horizontal scrollbar child.
     * Default is a <code>JScrollBar</code>.
     * <p>
     *  滚动条的水平滚动条子项默认是一个<code> JScrollBar </code>
     * 
     * 
     * @see JScrollPane#setHorizontalScrollBar
     */
    protected JScrollBar hsb;


    /**
     * The row header child.  Default is <code>null</code>.
     * <p>
     *  行标题子项默认值为<code> null </code>
     * 
     * 
     * @see JScrollPane#setRowHeader
     */
    protected JViewport rowHead;


    /**
     * The column header child.  Default is <code>null</code>.
     * <p>
     *  列标题子项默认值为<code> null </code>
     * 
     * 
     * @see JScrollPane#setColumnHeader
     */
    protected JViewport colHead;


    /**
     * The component to display in the lower left corner.
     * Default is <code>null</code>.
     * <p>
     *  要显示在左下角的组件默认为<code> null </code>
     * 
     * 
     * @see JScrollPane#setCorner
     */
    protected Component lowerLeft;


    /**
     * The component to display in the lower right corner.
     * Default is <code>null</code>.
     * <p>
     *  要显示在右下角的组件默认为<code> null </code>
     * 
     * 
     * @see JScrollPane#setCorner
     */
    protected Component lowerRight;


    /**
     * The component to display in the upper left corner.
     * Default is <code>null</code>.
     * <p>
     *  要显示在左上角的组件默认为<code> null </code>
     * 
     * 
     * @see JScrollPane#setCorner
     */
    protected Component upperLeft;


    /**
     * The component to display in the upper right corner.
     * Default is <code>null</code>.
     * <p>
     *  要显示在右上角的组件默认为<code> null </code>
     * 
     * 
     * @see JScrollPane#setCorner
     */
    protected Component upperRight;


    /**
     * The display policy for the vertical scrollbar.
     * The default is <code>ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED</code>.
     * <p>
     * This field is obsolete, please use the <code>JScrollPane</code> field instead.
     *
     * <p>
     * 垂直滚动条的显示策略默认为<code> ScrollPaneConstantsVERTICAL_SCROLLBAR_​​AS_NEEDED </code>
     * <p>
     *  此字段已过时,请改用<code> JScrollPane </code>字段
     * 
     * 
     * @see JScrollPane#setVerticalScrollBarPolicy
     */
    protected int vsbPolicy = VERTICAL_SCROLLBAR_AS_NEEDED;


    /**
     * The display policy for the horizontal scrollbar.
     * The default is <code>ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED</code>.
     * <p>
     * This field is obsolete, please use the <code>JScrollPane</code> field instead.
     *
     * <p>
     *  水平滚动条的显示策略默认为<code> ScrollPaneConstantsHORIZONTAL_SCROLLBAR_​​AS_NEEDED </code>
     * <p>
     *  此字段已过时,请改用<code> JScrollPane </code>字段
     * 
     * 
     * @see JScrollPane#setHorizontalScrollBarPolicy
     */
    protected int hsbPolicy = HORIZONTAL_SCROLLBAR_AS_NEEDED;


    /**
     * This method is invoked after the ScrollPaneLayout is set as the
     * LayoutManager of a <code>JScrollPane</code>.
     * It initializes all of the internal fields that
     * are ordinarily set by <code>addLayoutComponent</code>.  For example:
     * <pre>
     * ScrollPaneLayout mySPLayout = new ScrollPanelLayout() {
     *     public void layoutContainer(Container p) {
     *         super.layoutContainer(p);
     *         // do some extra work here ...
     *     }
     * };
     * scrollpane.setLayout(mySPLayout):
     * </pre>
     * <p>
     *  此方法在ScrollPaneLayout设置为<code> JScrollPane的LayoutManager之后被调用</code>它初始化通常由<code> addLayoutComponent 
     * </code>设置的所有内部字段例如：。
     * <pre>
     * ScrollPaneLayout mySPLayout = new ScrollPanelLayout(){public void layoutContainer(Container p){superlayoutContainer(p); // do some extra work here}
     * }; scrollpanesetLayout(mySPLayout)：。
     * </pre>
     */
    public void syncWithScrollPane(JScrollPane sp) {
        viewport = sp.getViewport();
        vsb = sp.getVerticalScrollBar();
        hsb = sp.getHorizontalScrollBar();
        rowHead = sp.getRowHeader();
        colHead = sp.getColumnHeader();
        lowerLeft = sp.getCorner(LOWER_LEFT_CORNER);
        lowerRight = sp.getCorner(LOWER_RIGHT_CORNER);
        upperLeft = sp.getCorner(UPPER_LEFT_CORNER);
        upperRight = sp.getCorner(UPPER_RIGHT_CORNER);
        vsbPolicy = sp.getVerticalScrollBarPolicy();
        hsbPolicy = sp.getHorizontalScrollBarPolicy();
    }


    /**
     * Removes an existing component.  When a new component, such as
     * the left corner, or vertical scrollbar, is added, the old one,
     * if it exists, must be removed.
     * <p>
     * This method returns <code>newC</code>. If <code>oldC</code> is
     * not equal to <code>newC</code> and is non-<code>null</code>,
     * it will be removed from its parent.
     *
     * <p>
     *  删除现有组件当添加新组件(例如左角或垂直滚动​​条)时,必须删除旧组件(如果存在)
     * <p>
     *  此方法返回<code> newC </code>如果<code> oldC </code>不等于<code> newC </code>且为非<code> null </code>其父
     * 
     * 
     * @param oldC the <code>Component</code> to replace
     * @param newC the <code>Component</code> to add
     * @return the <code>newC</code>
     */
    protected Component addSingletonComponent(Component oldC, Component newC)
    {
        if ((oldC != null) && (oldC != newC)) {
            oldC.getParent().remove(oldC);
        }
        return newC;
    }


    /**
     * Adds the specified component to the layout. The layout is
     * identified using one of:
     * <ul>
     * <li>ScrollPaneConstants.VIEWPORT
     * <li>ScrollPaneConstants.VERTICAL_SCROLLBAR
     * <li>ScrollPaneConstants.HORIZONTAL_SCROLLBAR
     * <li>ScrollPaneConstants.ROW_HEADER
     * <li>ScrollPaneConstants.COLUMN_HEADER
     * <li>ScrollPaneConstants.LOWER_LEFT_CORNER
     * <li>ScrollPaneConstants.LOWER_RIGHT_CORNER
     * <li>ScrollPaneConstants.UPPER_LEFT_CORNER
     * <li>ScrollPaneConstants.UPPER_RIGHT_CORNER
     * </ul>
     *
     * <p>
     *  将指定的组件添加到布局使用下列之一来标识布局：
     * <ul>
     * <LI> ScrollPaneConstantsVIEWPORT <LI> ScrollPaneConstantsVERTICAL_SCROLLBAR <LI> ScrollPaneConstantsH
     * ORIZONTAL_SCROLLBAR <LI> ScrollPaneConstantsROW_HEADER <LI> ScrollPaneConstantsCOLUMN_HEADER <LI> Scr
     * ollPaneConstantsLOWER_LEFT_CORNER <LI> ScrollPaneConstantsLOWER_RIGHT_CORNER <LI> ScrollPaneConstants
     * UPPER_LEFT_CORNER <LI> ScrollPaneConstantsUPPER_RIGHT_CORNER。
     * </ul>
     * 
     * 
     * @param s the component identifier
     * @param c the the component to be added
     * @exception IllegalArgumentException if <code>s</code> is an invalid key
     */
    public void addLayoutComponent(String s, Component c)
    {
        if (s.equals(VIEWPORT)) {
            viewport = (JViewport)addSingletonComponent(viewport, c);
        }
        else if (s.equals(VERTICAL_SCROLLBAR)) {
            vsb = (JScrollBar)addSingletonComponent(vsb, c);
        }
        else if (s.equals(HORIZONTAL_SCROLLBAR)) {
            hsb = (JScrollBar)addSingletonComponent(hsb, c);
        }
        else if (s.equals(ROW_HEADER)) {
            rowHead = (JViewport)addSingletonComponent(rowHead, c);
        }
        else if (s.equals(COLUMN_HEADER)) {
            colHead = (JViewport)addSingletonComponent(colHead, c);
        }
        else if (s.equals(LOWER_LEFT_CORNER)) {
            lowerLeft = addSingletonComponent(lowerLeft, c);
        }
        else if (s.equals(LOWER_RIGHT_CORNER)) {
            lowerRight = addSingletonComponent(lowerRight, c);
        }
        else if (s.equals(UPPER_LEFT_CORNER)) {
            upperLeft = addSingletonComponent(upperLeft, c);
        }
        else if (s.equals(UPPER_RIGHT_CORNER)) {
            upperRight = addSingletonComponent(upperRight, c);
        }
        else {
            throw new IllegalArgumentException("invalid layout key " + s);
        }
    }


    /**
     * Removes the specified component from the layout.
     *
     * <p>
     *  从布局中删除指定的组件
     * 
     * 
     * @param c the component to remove
     */
    public void removeLayoutComponent(Component c)
    {
        if (c == viewport) {
            viewport = null;
        }
        else if (c == vsb) {
            vsb = null;
        }
        else if (c == hsb) {
            hsb = null;
        }
        else if (c == rowHead) {
            rowHead = null;
        }
        else if (c == colHead) {
            colHead = null;
        }
        else if (c == lowerLeft) {
            lowerLeft = null;
        }
        else if (c == lowerRight) {
            lowerRight = null;
        }
        else if (c == upperLeft) {
            upperLeft = null;
        }
        else if (c == upperRight) {
            upperRight = null;
        }
    }


    /**
     * Returns the vertical scrollbar-display policy.
     *
     * <p>
     *  返回垂直滚动条显示策略
     * 
     * 
     * @return an integer giving the display policy
     * @see #setVerticalScrollBarPolicy
     */
    public int getVerticalScrollBarPolicy() {
        return vsbPolicy;
    }


    /**
     * Sets the vertical scrollbar-display policy. The options
     * are:
     * <ul>
     * <li>ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
     * <li>ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER
     * <li>ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS
     * </ul>
     * Note: Applications should use the <code>JScrollPane</code> version
     * of this method.  It only exists for backwards compatibility
     * with the Swing 1.0.2 (and earlier) versions of this class.
     *
     * <p>
     *  设置垂直滚动条显示策略选项为：
     * <ul>
     *  <li> ScrollPaneConstantsVERTICAL_SCROLLBAR_​​AS_NEEDED <li> ScrollPaneConstantsVERTICAL_SCROLLBAR_​​
     * NEVER <li> ScrollPaneConstantsVERTICAL_SCROLLBAR_​​ALWAYS。
     * </ul>
     * 注意：应用程序应该使用此方法的<code> JScrollPane </code>版本它只存在于与此类的Swing 102(和更早版本)版本的向后兼容性
     * 
     * 
     * @param x an integer giving the display policy
     * @exception IllegalArgumentException if <code>x</code> is an invalid
     *          vertical scroll bar policy, as listed above
     */
    public void setVerticalScrollBarPolicy(int x) {
        switch (x) {
        case VERTICAL_SCROLLBAR_AS_NEEDED:
        case VERTICAL_SCROLLBAR_NEVER:
        case VERTICAL_SCROLLBAR_ALWAYS:
                vsbPolicy = x;
                break;
        default:
            throw new IllegalArgumentException("invalid verticalScrollBarPolicy");
        }
    }


    /**
     * Returns the horizontal scrollbar-display policy.
     *
     * <p>
     *  返回水平滚动条显示策略
     * 
     * 
     * @return an integer giving the display policy
     * @see #setHorizontalScrollBarPolicy
     */
    public int getHorizontalScrollBarPolicy() {
        return hsbPolicy;
    }

    /**
     * Sets the horizontal scrollbar-display policy.
     * The options are:<ul>
     * <li>ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
     * <li>ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
     * <li>ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS
     * </ul>
     * Note: Applications should use the <code>JScrollPane</code> version
     * of this method.  It only exists for backwards compatibility
     * with the Swing 1.0.2 (and earlier) versions of this class.
     *
     * <p>
     *  设置水平滚动条显示策略选项为：<ul> <li> ScrollPaneConstantsHORIZONTAL_SCROLLBAR_​​AS_NEEDED <li> ScrollPaneConstant
     * sHORIZONTAL_SCROLLBAR_​​NEVER <li> ScrollPaneConstantsHORIZONTAL_SCROLLBAR_​​ALWAYS。
     * </ul>
     *  注意：应用程序应该使用此方法的<code> JScrollPane </code>版本它只存在于与此类的Swing 102(和更早版本)版本的向后兼容性
     * 
     * 
     * @param x an int giving the display policy
     * @exception IllegalArgumentException if <code>x</code> is not a valid
     *          horizontal scrollbar policy, as listed above
     */
    public void setHorizontalScrollBarPolicy(int x) {
        switch (x) {
        case HORIZONTAL_SCROLLBAR_AS_NEEDED:
        case HORIZONTAL_SCROLLBAR_NEVER:
        case HORIZONTAL_SCROLLBAR_ALWAYS:
                hsbPolicy = x;
                break;
        default:
            throw new IllegalArgumentException("invalid horizontalScrollBarPolicy");
        }
    }


    /**
     * Returns the <code>JViewport</code> object that displays the
     * scrollable contents.
     * <p>
     *  返回显示可滚动内容的<code> JViewport </code>对象
     * 
     * 
     * @return the <code>JViewport</code> object that displays the scrollable contents
     * @see JScrollPane#getViewport
     */
    public JViewport getViewport() {
        return viewport;
    }


    /**
     * Returns the <code>JScrollBar</code> object that handles horizontal scrolling.
     * <p>
     * 返回处理水平滚动的<code> JScrollBar </code>对象
     * 
     * 
     * @return the <code>JScrollBar</code> object that handles horizontal scrolling
     * @see JScrollPane#getHorizontalScrollBar
     */
    public JScrollBar getHorizontalScrollBar() {
        return hsb;
    }

    /**
     * Returns the <code>JScrollBar</code> object that handles vertical scrolling.
     * <p>
     *  返回处理垂直滚动的<code> JScrollBar </code>对象
     * 
     * 
     * @return the <code>JScrollBar</code> object that handles vertical scrolling
     * @see JScrollPane#getVerticalScrollBar
     */
    public JScrollBar getVerticalScrollBar() {
        return vsb;
    }


    /**
     * Returns the <code>JViewport</code> object that is the row header.
     * <p>
     *  返回作为行标题的<code> JViewport </code>对象
     * 
     * 
     * @return the <code>JViewport</code> object that is the row header
     * @see JScrollPane#getRowHeader
     */
    public JViewport getRowHeader() {
        return rowHead;
    }


    /**
     * Returns the <code>JViewport</code> object that is the column header.
     * <p>
     *  返回作为列标题的<code> JViewport </code>对象
     * 
     * 
     * @return the <code>JViewport</code> object that is the column header
     * @see JScrollPane#getColumnHeader
     */
    public JViewport getColumnHeader() {
        return colHead;
    }


    /**
     * Returns the <code>Component</code> at the specified corner.
     * <p>
     *  返回指定角落的<code> Component </code>
     * 
     * 
     * @param key the <code>String</code> specifying the corner
     * @return the <code>Component</code> at the specified corner, as defined in
     *         {@link ScrollPaneConstants}; if <code>key</code> is not one of the
     *          four corners, <code>null</code> is returned
     * @see JScrollPane#getCorner
     */
    public Component getCorner(String key) {
        if (key.equals(LOWER_LEFT_CORNER)) {
            return lowerLeft;
        }
        else if (key.equals(LOWER_RIGHT_CORNER)) {
            return lowerRight;
        }
        else if (key.equals(UPPER_LEFT_CORNER)) {
            return upperLeft;
        }
        else if (key.equals(UPPER_RIGHT_CORNER)) {
            return upperRight;
        }
        else {
            return null;
        }
    }


    /**
     * The preferred size of a <code>ScrollPane</code> is the size of the insets,
     * plus the preferred size of the viewport, plus the preferred size of
     * the visible headers, plus the preferred size of the scrollbars
     * that will appear given the current view and the current
     * scrollbar displayPolicies.
     * <p>Note that the rowHeader is calculated as part of the preferred width
     * and the colHeader is calculated as part of the preferred size.
     *
     * <p>
     *  <code> ScrollPane </code>的首选大小是插入的大小,加上视口的首选大小,加上可见标题的首选大小,以及在给定当前值的情况下出现的滚动条的首选大小视图和当前滚动条displayPol
     * icies <p>请注意,rowHeader计算为首选宽度的一部分,colHeader作为首选大小的一部分计算。
     * 
     * 
     * @param parent the <code>Container</code> that will be laid out
     * @return a <code>Dimension</code> object specifying the preferred size of the
     *         viewport and any scrollbars
     * @see ViewportLayout
     * @see LayoutManager
     */
    public Dimension preferredLayoutSize(Container parent)
    {
        /* Sync the (now obsolete) policy fields with the
         * JScrollPane.
         * <p>
         *  JScrollPane
         * 
         */
        JScrollPane scrollPane = (JScrollPane)parent;
        vsbPolicy = scrollPane.getVerticalScrollBarPolicy();
        hsbPolicy = scrollPane.getHorizontalScrollBarPolicy();

        Insets insets = parent.getInsets();
        int prefWidth = insets.left + insets.right;
        int prefHeight = insets.top + insets.bottom;

        /* Note that viewport.getViewSize() is equivalent to
         * viewport.getView().getPreferredSize() modulo a null
         * view or a view whose size was explicitly set.
         * <p>
         * viewportgetView()getPreferredSize()对空视图或其大小已明确设置的视图进行取模
         * 
         */

        Dimension extentSize = null;
        Dimension viewSize = null;
        Component view = null;

        if (viewport != null) {
            extentSize = viewport.getPreferredSize();
            view = viewport.getView();
            if (view != null) {
                viewSize = view.getPreferredSize();
            } else {
                viewSize = new Dimension(0, 0);
            }
        }

        /* If there's a viewport add its preferredSize.
        /* <p>
         */

        if (extentSize != null) {
            prefWidth += extentSize.width;
            prefHeight += extentSize.height;
        }

        /* If there's a JScrollPane.viewportBorder, add its insets.
        /* <p>
         */

        Border viewportBorder = scrollPane.getViewportBorder();
        if (viewportBorder != null) {
            Insets vpbInsets = viewportBorder.getBorderInsets(parent);
            prefWidth += vpbInsets.left + vpbInsets.right;
            prefHeight += vpbInsets.top + vpbInsets.bottom;
        }

        /* If a header exists and it's visible, factor its
         * preferred size in.
         * <p>
         *  首选尺寸
         * 
         */

        if ((rowHead != null) && rowHead.isVisible()) {
            prefWidth += rowHead.getPreferredSize().width;
        }

        if ((colHead != null) && colHead.isVisible()) {
            prefHeight += colHead.getPreferredSize().height;
        }

        /* If a scrollbar is going to appear, factor its preferred size in.
         * If the scrollbars policy is AS_NEEDED, this can be a little
         * tricky:
         *
         * - If the view is a Scrollable then scrollableTracksViewportWidth
         * and scrollableTracksViewportHeight can be used to effectively
         * disable scrolling (if they're true) in their respective dimensions.
         *
         * - Assuming that a scrollbar hasn't been disabled by the
         * previous constraint, we need to decide if the scrollbar is going
         * to appear to correctly compute the JScrollPanes preferred size.
         * To do this we compare the preferredSize of the viewport (the
         * extentSize) to the preferredSize of the view.  Although we're
         * not responsible for laying out the view we'll assume that the
         * JViewport will always give it its preferredSize.
         * <p>
         *  如果滚动条策略是AS_NEEDED,这可能有点棘手：
         * 
         *   - 如果视图是一个Scrollable,则scrollableTracksViewportWidth和scrollableTracksViewportHeight可用于有效地禁用滚动(如果它们是真的
         * )在各自的维度。
         * 
         *  - 假设滚动条没有被前一个约束禁用,我们需要确定滚动条是否会正确计算JScrollPanes首选大小要做到这一点,我们将viewport的preferredSize(extentSize)与prefe
         * rredSize的视图虽然我们不负责布局视图,我们将假设JViewport将总是给它的preferredSize。
         * 
         */

        if ((vsb != null) && (vsbPolicy != VERTICAL_SCROLLBAR_NEVER)) {
            if (vsbPolicy == VERTICAL_SCROLLBAR_ALWAYS) {
                prefWidth += vsb.getPreferredSize().width;
            }
            else if ((viewSize != null) && (extentSize != null)) {
                boolean canScroll = true;
                if (view instanceof Scrollable) {
                    canScroll = !((Scrollable)view).getScrollableTracksViewportHeight();
                }
                if (canScroll && (viewSize.height > extentSize.height)) {
                    prefWidth += vsb.getPreferredSize().width;
                }
            }
        }

        if ((hsb != null) && (hsbPolicy != HORIZONTAL_SCROLLBAR_NEVER)) {
            if (hsbPolicy == HORIZONTAL_SCROLLBAR_ALWAYS) {
                prefHeight += hsb.getPreferredSize().height;
            }
            else if ((viewSize != null) && (extentSize != null)) {
                boolean canScroll = true;
                if (view instanceof Scrollable) {
                    canScroll = !((Scrollable)view).getScrollableTracksViewportWidth();
                }
                if (canScroll && (viewSize.width > extentSize.width)) {
                    prefHeight += hsb.getPreferredSize().height;
                }
            }
        }

        return new Dimension(prefWidth, prefHeight);
    }


    /**
     * The minimum size of a <code>ScrollPane</code> is the size of the insets
     * plus minimum size of the viewport, plus the scrollpane's
     * viewportBorder insets, plus the minimum size
     * of the visible headers, plus the minimum size of the
     * scrollbars whose displayPolicy isn't NEVER.
     *
     * <p>
     *  <code> ScrollPane </code>的最小大小是插件的大小加上视口的最小大小,加上滚动条的viewportBorder插入,加上可见标头的最小大小加上其displayPolicy的滚动条
     * 的最小大小不是永远不会。
     * 
     * 
     * @param parent the <code>Container</code> that will be laid out
     * @return a <code>Dimension</code> object specifying the minimum size
     */
    public Dimension minimumLayoutSize(Container parent)
    {
        /* Sync the (now obsolete) policy fields with the
         * JScrollPane.
         * <p>
         *  JScrollPane
         * 
         */
        JScrollPane scrollPane = (JScrollPane)parent;
        vsbPolicy = scrollPane.getVerticalScrollBarPolicy();
        hsbPolicy = scrollPane.getHorizontalScrollBarPolicy();

        Insets insets = parent.getInsets();
        int minWidth = insets.left + insets.right;
        int minHeight = insets.top + insets.bottom;

        /* If there's a viewport add its minimumSize.
        /* <p>
         */

        if (viewport != null) {
            Dimension size = viewport.getMinimumSize();
            minWidth += size.width;
            minHeight += size.height;
        }

        /* If there's a JScrollPane.viewportBorder, add its insets.
        /* <p>
         */

        Border viewportBorder = scrollPane.getViewportBorder();
        if (viewportBorder != null) {
            Insets vpbInsets = viewportBorder.getBorderInsets(parent);
            minWidth += vpbInsets.left + vpbInsets.right;
            minHeight += vpbInsets.top + vpbInsets.bottom;
        }

        /* If a header exists and it's visible, factor its
         * minimum size in.
         * <p>
         *  最小尺寸
         * 
         */

        if ((rowHead != null) && rowHead.isVisible()) {
            Dimension size = rowHead.getMinimumSize();
            minWidth += size.width;
            minHeight = Math.max(minHeight, size.height);
        }

        if ((colHead != null) && colHead.isVisible()) {
            Dimension size = colHead.getMinimumSize();
            minWidth = Math.max(minWidth, size.width);
            minHeight += size.height;
        }

        /* If a scrollbar might appear, factor its minimum
         * size in.
         * <p>
         *  尺寸
         * 
         */

        if ((vsb != null) && (vsbPolicy != VERTICAL_SCROLLBAR_NEVER)) {
            Dimension size = vsb.getMinimumSize();
            minWidth += size.width;
            minHeight = Math.max(minHeight, size.height);
        }

        if ((hsb != null) && (hsbPolicy != HORIZONTAL_SCROLLBAR_NEVER)) {
            Dimension size = hsb.getMinimumSize();
            minWidth = Math.max(minWidth, size.width);
            minHeight += size.height;
        }

        return new Dimension(minWidth, minHeight);
    }


    /**
     * Lays out the scrollpane. The positioning of components depends on
     * the following constraints:
     * <ul>
     * <li> The row header, if present and visible, gets its preferred
     * width and the viewport's height.
     *
     * <li> The column header, if present and visible, gets its preferred
     * height and the viewport's width.
     *
     * <li> If a vertical scrollbar is needed, i.e. if the viewport's extent
     * height is smaller than its view height or if the <code>displayPolicy</code>
     * is ALWAYS, it's treated like the row header with respect to its
     * dimensions and is made visible.
     *
     * <li> If a horizontal scrollbar is needed, it is treated like the
     * column header (see the paragraph above regarding the vertical scrollbar).
     *
     * <li> If the scrollpane has a non-<code>null</code>
     * <code>viewportBorder</code>, then space is allocated for that.
     *
     * <li> The viewport gets the space available after accounting for
     * the previous constraints.
     *
     * <li> The corner components, if provided, are aligned with the
     * ends of the scrollbars and headers. If there is a vertical
     * scrollbar, the right corners appear; if there is a horizontal
     * scrollbar, the lower corners appear; a row header gets left
     * corners, and a column header gets upper corners.
     * </ul>
     *
     * <p>
     * 放出滚动条组件的定位取决于以下约束：
     * <ul>
     *  <li>行标题(如果存在且可见)获取其首选宽度和视口的高度
     * 
     *  <li>列标题(如果存在且可见)获取其首选高度和视口宽度
     * 
     *  <li>如果需要垂直滚动条,即如果视口的范围高度小于其视图高度,或者如果<code> displayPolicy </code>始终显示,则视为相对于其尺寸的行标题,可见
     * 
     *  <li>如果需要水平滚动条,则将其视为列标题(请参阅上面关于垂直滚动条的段落)
     * 
     *  <li>如果滚动面板有一个非<code> null </code> <code> viewportBorder </code>,那么空间将分配给
     * 
     * <li>视口在考虑先前的限制后获得可用空间
     * 
     *  <li>角落组件(如果提供)与滚动条和标题的末端对齐如果有垂直滚动条,则会出现右角;如果有一个水平滚动条,下角出现;一个行标题得到左角,一个列标题得到上角
     * </ul>
     * 
     * 
     * @param parent the <code>Container</code> to lay out
     */
    public void layoutContainer(Container parent)
    {
        /* Sync the (now obsolete) policy fields with the
         * JScrollPane.
         * <p>
         *  JScrollPane
         * 
         */
        JScrollPane scrollPane = (JScrollPane)parent;
        vsbPolicy = scrollPane.getVerticalScrollBarPolicy();
        hsbPolicy = scrollPane.getHorizontalScrollBarPolicy();

        Rectangle availR = scrollPane.getBounds();
        availR.x = availR.y = 0;

        Insets insets = parent.getInsets();
        availR.x = insets.left;
        availR.y = insets.top;
        availR.width -= insets.left + insets.right;
        availR.height -= insets.top + insets.bottom;

        /* Get the scrollPane's orientation.
        /* <p>
         */
        boolean leftToRight = SwingUtilities.isLeftToRight(scrollPane);

        /* If there's a visible column header remove the space it
         * needs from the top of availR.  The column header is treated
         * as if it were fixed height, arbitrary width.
         * <p>
         *  需要从顶部的availR列标题被视为固定的高度,任意宽度
         * 
         */

        Rectangle colHeadR = new Rectangle(0, availR.y, 0, 0);

        if ((colHead != null) && (colHead.isVisible())) {
            int colHeadHeight = Math.min(availR.height,
                                         colHead.getPreferredSize().height);
            colHeadR.height = colHeadHeight;
            availR.y += colHeadHeight;
            availR.height -= colHeadHeight;
        }

        /* If there's a visible row header remove the space it needs
         * from the left or right of availR.  The row header is treated
         * as if it were fixed width, arbitrary height.
         * <p>
         *  从availR的左侧或右侧行标题被视为固定宽度,任意高度
         * 
         */

        Rectangle rowHeadR = new Rectangle(0, 0, 0, 0);

        if ((rowHead != null) && (rowHead.isVisible())) {
            int rowHeadWidth = Math.min(availR.width,
                                        rowHead.getPreferredSize().width);
            rowHeadR.width = rowHeadWidth;
            availR.width -= rowHeadWidth;
            if ( leftToRight ) {
                rowHeadR.x = availR.x;
                availR.x += rowHeadWidth;
            } else {
                rowHeadR.x = availR.x + availR.width;
            }
        }

        /* If there's a JScrollPane.viewportBorder, remove the
         * space it occupies for availR.
         * <p>
         *  占用空间
         * 
         */

        Border viewportBorder = scrollPane.getViewportBorder();
        Insets vpbInsets;
        if (viewportBorder != null) {
            vpbInsets = viewportBorder.getBorderInsets(parent);
            availR.x += vpbInsets.left;
            availR.y += vpbInsets.top;
            availR.width -= vpbInsets.left + vpbInsets.right;
            availR.height -= vpbInsets.top + vpbInsets.bottom;
        }
        else {
            vpbInsets = new Insets(0,0,0,0);
        }


        /* At this point availR is the space available for the viewport
         * and scrollbars. rowHeadR is correct except for its height and y
         * and colHeadR is correct except for its width and x.  Once we're
         * through computing the dimensions  of these three parts we can
         * go back and set the dimensions of rowHeadR.height, rowHeadR.y,
         * colHeadR.width, colHeadR.x and the bounds for the corners.
         *
         * We'll decide about putting up scrollbars by comparing the
         * viewport views preferred size with the viewports extent
         * size (generally just its size).  Using the preferredSize is
         * reasonable because layout proceeds top down - so we expect
         * the viewport to be laid out next.  And we assume that the
         * viewports layout manager will give the view it's preferred
         * size.  One exception to this is when the view implements
         * Scrollable and Scrollable.getViewTracksViewport{Width,Height}
         * methods return true.  If the view is tracking the viewports
         * width we don't bother with a horizontal scrollbar, similarly
         * if view.getViewTracksViewport(Height) is true we don't bother
         * with a vertical scrollbar.
         * <p>
         * 和滚动条rowHeadR是正确的,除了它的高度和y和colHeadR是正确的,除了它的宽度和x一旦我们计算这三个部分的维度,我们可以回去设置rowHeadRheight,rowHeadRy,colHea
         * dRwidth,colHeadRx和角的边界。
         * 
         * 我们将决定通过比较视口视图首选大小和视口范围大小(通常只是它的大小)来放置滚动条。
         * 使用preferredSize是合理的,因为布局从上向下 - 所以我们期望视口被布置下一个我们假设视口布局管理器会给视图它的首选大小一个例外是当视图实现Scrollable和ScrollablegetV
         * iewTracksViewport {Width,Height}方法返回true如果视图是跟踪视口宽度,我们不打扰水平滚动条,类似地,如果viewgetViewTracksViewport(Height
         * )是true,我们不打扰垂直滚动条。
         * 我们将决定通过比较视口视图首选大小和视口范围大小(通常只是它的大小)来放置滚动条。
         * 
         */

        Component view = (viewport != null) ? viewport.getView() : null;
        Dimension viewPrefSize =
            (view != null) ? view.getPreferredSize()
                           : new Dimension(0,0);

        Dimension extentSize =
            (viewport != null) ? viewport.toViewCoordinates(availR.getSize())
                               : new Dimension(0,0);

        boolean viewTracksViewportWidth = false;
        boolean viewTracksViewportHeight = false;
        boolean isEmpty = (availR.width < 0 || availR.height < 0);
        Scrollable sv;
        // Don't bother checking the Scrollable methods if there is no room
        // for the viewport, we aren't going to show any scrollbars in this
        // case anyway.
        if (!isEmpty && view instanceof Scrollable) {
            sv = (Scrollable)view;
            viewTracksViewportWidth = sv.getScrollableTracksViewportWidth();
            viewTracksViewportHeight = sv.getScrollableTracksViewportHeight();
        }
        else {
            sv = null;
        }

        /* If there's a vertical scrollbar and we need one, allocate
         * space for it (we'll make it visible later). A vertical
         * scrollbar is considered to be fixed width, arbitrary height.
         * <p>
         *  空间(我们将使它以后可见)垂直滚动条被认为是固定宽度,任意高度
         * 
         */

        Rectangle vsbR = new Rectangle(0, availR.y - vpbInsets.top, 0, 0);

        boolean vsbNeeded;
        if (isEmpty) {
            vsbNeeded = false;
        }
        else if (vsbPolicy == VERTICAL_SCROLLBAR_ALWAYS) {
            vsbNeeded = true;
        }
        else if (vsbPolicy == VERTICAL_SCROLLBAR_NEVER) {
            vsbNeeded = false;
        }
        else {  // vsbPolicy == VERTICAL_SCROLLBAR_AS_NEEDED
            vsbNeeded = !viewTracksViewportHeight && (viewPrefSize.height > extentSize.height);
        }


        if ((vsb != null) && vsbNeeded) {
            adjustForVSB(true, availR, vsbR, vpbInsets, leftToRight);
            extentSize = viewport.toViewCoordinates(availR.getSize());
        }

        /* If there's a horizontal scrollbar and we need one, allocate
         * space for it (we'll make it visible later). A horizontal
         * scrollbar is considered to be fixed height, arbitrary width.
         * <p>
         * 空间(我们将使它以后可见)水平滚动条被认为是固定高度,任意宽度
         * 
         */

        Rectangle hsbR = new Rectangle(availR.x - vpbInsets.left, 0, 0, 0);
        boolean hsbNeeded;
        if (isEmpty) {
            hsbNeeded = false;
        }
        else if (hsbPolicy == HORIZONTAL_SCROLLBAR_ALWAYS) {
            hsbNeeded = true;
        }
        else if (hsbPolicy == HORIZONTAL_SCROLLBAR_NEVER) {
            hsbNeeded = false;
        }
        else {  // hsbPolicy == HORIZONTAL_SCROLLBAR_AS_NEEDED
            hsbNeeded = !viewTracksViewportWidth && (viewPrefSize.width > extentSize.width);
        }

        if ((hsb != null) && hsbNeeded) {
            adjustForHSB(true, availR, hsbR, vpbInsets);

            /* If we added the horizontal scrollbar then we've implicitly
             * reduced  the vertical space available to the viewport.
             * As a consequence we may have to add the vertical scrollbar,
             * if that hasn't been done so already.  Of course we
             * don't bother with any of this if the vsbPolicy is NEVER.
             * <p>
             *  减少视口可用的垂直空间因此,我们可能必须添加垂直滚动条,如果还没有这样做的话,当然我们不打扰任何这一点如果vsbPolicy是从来没有
             * 
             */
            if ((vsb != null) && !vsbNeeded &&
                (vsbPolicy != VERTICAL_SCROLLBAR_NEVER)) {

                extentSize = viewport.toViewCoordinates(availR.getSize());
                vsbNeeded = viewPrefSize.height > extentSize.height;

                if (vsbNeeded) {
                    adjustForVSB(true, availR, vsbR, vpbInsets, leftToRight);
                }
            }
        }

        /* Set the size of the viewport first, and then recheck the Scrollable
         * methods. Some components base their return values for the Scrollable
         * methods on the size of the Viewport, so that if we don't
         * ask after resetting the bounds we may have gotten the wrong
         * answer.
         * <p>
         *  方法一些组件将Scrollable方法的返回值基于视口的大小,因此如果我们在重置边界之后不询问,我们可能得到错误的答案
         * 
         */

        if (viewport != null) {
            viewport.setBounds(availR);

            if (sv != null) {
                extentSize = viewport.toViewCoordinates(availR.getSize());

                boolean oldHSBNeeded = hsbNeeded;
                boolean oldVSBNeeded = vsbNeeded;
                viewTracksViewportWidth = sv.
                                          getScrollableTracksViewportWidth();
                viewTracksViewportHeight = sv.
                                          getScrollableTracksViewportHeight();
                if (vsb != null && vsbPolicy == VERTICAL_SCROLLBAR_AS_NEEDED) {
                    boolean newVSBNeeded = !viewTracksViewportHeight &&
                                     (viewPrefSize.height > extentSize.height);
                    if (newVSBNeeded != vsbNeeded) {
                        vsbNeeded = newVSBNeeded;
                        adjustForVSB(vsbNeeded, availR, vsbR, vpbInsets,
                                     leftToRight);
                        extentSize = viewport.toViewCoordinates
                                              (availR.getSize());
                    }
                }
                if (hsb != null && hsbPolicy ==HORIZONTAL_SCROLLBAR_AS_NEEDED){
                    boolean newHSBbNeeded = !viewTracksViewportWidth &&
                                       (viewPrefSize.width > extentSize.width);
                    if (newHSBbNeeded != hsbNeeded) {
                        hsbNeeded = newHSBbNeeded;
                        adjustForHSB(hsbNeeded, availR, hsbR, vpbInsets);
                        if ((vsb != null) && !vsbNeeded &&
                            (vsbPolicy != VERTICAL_SCROLLBAR_NEVER)) {

                            extentSize = viewport.toViewCoordinates
                                         (availR.getSize());
                            vsbNeeded = viewPrefSize.height >
                                        extentSize.height;

                            if (vsbNeeded) {
                                adjustForVSB(true, availR, vsbR, vpbInsets,
                                             leftToRight);
                            }
                        }
                    }
                }
                if (oldHSBNeeded != hsbNeeded ||
                    oldVSBNeeded != vsbNeeded) {
                    viewport.setBounds(availR);
                    // You could argue that we should recheck the
                    // Scrollable methods again until they stop changing,
                    // but they might never stop changing, so we stop here
                    // and don't do any additional checks.
                }
            }
        }

        /* We now have the final size of the viewport: availR.
         * Now fixup the header and scrollbar widths/heights.
         * <p>
         *  现在修正标题和滚动条宽度/高度
         * 
         */
        vsbR.height = availR.height + vpbInsets.top + vpbInsets.bottom;
        hsbR.width = availR.width + vpbInsets.left + vpbInsets.right;
        rowHeadR.height = availR.height + vpbInsets.top + vpbInsets.bottom;
        rowHeadR.y = availR.y - vpbInsets.top;
        colHeadR.width = availR.width + vpbInsets.left + vpbInsets.right;
        colHeadR.x = availR.x - vpbInsets.left;

        /* Set the bounds of the remaining components.  The scrollbars
         * are made invisible if they're not needed.
         * <p>
         *  如果不需要它们是不可见的
         * 
         */

        if (rowHead != null) {
            rowHead.setBounds(rowHeadR);
        }

        if (colHead != null) {
            colHead.setBounds(colHeadR);
        }

        if (vsb != null) {
            if (vsbNeeded) {
                if (colHead != null &&
                    UIManager.getBoolean("ScrollPane.fillUpperCorner"))
                {
                    if ((leftToRight && upperRight == null) ||
                        (!leftToRight && upperLeft == null))
                    {
                        // This is used primarily for GTK L&F, which needs to
                        // extend the vertical scrollbar to fill the upper
                        // corner near the column header.  Note that we skip
                        // this step (and use the default behavior) if the
                        // user has set a custom corner component.
                        vsbR.y = colHeadR.y;
                        vsbR.height += colHeadR.height;
                    }
                }
                vsb.setVisible(true);
                vsb.setBounds(vsbR);
            }
            else {
                vsb.setVisible(false);
            }
        }

        if (hsb != null) {
            if (hsbNeeded) {
                if (rowHead != null &&
                    UIManager.getBoolean("ScrollPane.fillLowerCorner"))
                {
                    if ((leftToRight && lowerLeft == null) ||
                        (!leftToRight && lowerRight == null))
                    {
                        // This is used primarily for GTK L&F, which needs to
                        // extend the horizontal scrollbar to fill the lower
                        // corner near the row header.  Note that we skip
                        // this step (and use the default behavior) if the
                        // user has set a custom corner component.
                        if (leftToRight) {
                            hsbR.x = rowHeadR.x;
                        }
                        hsbR.width += rowHeadR.width;
                    }
                }
                hsb.setVisible(true);
                hsb.setBounds(hsbR);
            }
            else {
                hsb.setVisible(false);
            }
        }

        if (lowerLeft != null) {
            lowerLeft.setBounds(leftToRight ? rowHeadR.x : vsbR.x,
                                hsbR.y,
                                leftToRight ? rowHeadR.width : vsbR.width,
                                hsbR.height);
        }

        if (lowerRight != null) {
            lowerRight.setBounds(leftToRight ? vsbR.x : rowHeadR.x,
                                 hsbR.y,
                                 leftToRight ? vsbR.width : rowHeadR.width,
                                 hsbR.height);
        }

        if (upperLeft != null) {
            upperLeft.setBounds(leftToRight ? rowHeadR.x : vsbR.x,
                                colHeadR.y,
                                leftToRight ? rowHeadR.width : vsbR.width,
                                colHeadR.height);
        }

        if (upperRight != null) {
            upperRight.setBounds(leftToRight ? vsbR.x : rowHeadR.x,
                                 colHeadR.y,
                                 leftToRight ? vsbR.width : rowHeadR.width,
                                 colHeadR.height);
        }
    }

    /**
     * Adjusts the <code>Rectangle</code> <code>available</code> based on if
     * the vertical scrollbar is needed (<code>wantsVSB</code>).
     * The location of the vsb is updated in <code>vsbR</code>, and
     * the viewport border insets (<code>vpbInsets</code>) are used to offset
     * the vsb. This is only called when <code>wantsVSB</code> has
     * changed, eg you shouldn't invoke adjustForVSB(true) twice.
     * <p>
     * 基于是否需要垂直滚动条来调整<code> Rectangle </code> <code> </code>可用</code> vsb的位置在<code> vsbR </code>代码>和视口边界插入(<code>
     *  vpbInsets </code>)用于抵消vsb这只在<code> wantsVSB </code>发生改变时调用,例如,你不应该调用adjustForVSB两次。
     * 
     */
    private void adjustForVSB(boolean wantsVSB, Rectangle available,
                              Rectangle vsbR, Insets vpbInsets,
                              boolean leftToRight) {
        int oldWidth = vsbR.width;
        if (wantsVSB) {
            int vsbWidth = Math.max(0, Math.min(vsb.getPreferredSize().width,
                                                available.width));

            available.width -= vsbWidth;
            vsbR.width = vsbWidth;

            if( leftToRight ) {
                vsbR.x = available.x + available.width + vpbInsets.right;
            } else {
                vsbR.x = available.x - vpbInsets.left;
                available.x += vsbWidth;
            }
        }
        else {
            available.width += oldWidth;
        }
    }

    /**
     * Adjusts the <code>Rectangle</code> <code>available</code> based on if
     * the horizontal scrollbar is needed (<code>wantsHSB</code>).
     * The location of the hsb is updated in <code>hsbR</code>, and
     * the viewport border insets (<code>vpbInsets</code>) are used to offset
     * the hsb.  This is only called when <code>wantsHSB</code> has
     * changed, eg you shouldn't invoked adjustForHSB(true) twice.
     * <p>
     *  基于是否需要水平滚动条(<code> wantsHSB </code>)调整<code> Rectangle </code> <code>可用</code> hsb的位置在<code> hsbR <代码>
     * ,并且视口边界插入(<code> vpbInsets </code>)用于偏移hsb这仅在<code> wantsHSB </code>已更改时调用,例如,不应调用adjustForHSB两次。
     * 
     */
    private void adjustForHSB(boolean wantsHSB, Rectangle available,
                              Rectangle hsbR, Insets vpbInsets) {
        int oldHeight = hsbR.height;
        if (wantsHSB) {
            int hsbHeight = Math.max(0, Math.min(available.height,
                                              hsb.getPreferredSize().height));

            available.height -= hsbHeight;
            hsbR.y = available.y + available.height + vpbInsets.bottom;
            hsbR.height = hsbHeight;
        }
        else {
            available.height += oldHeight;
        }
    }



    /**
     * Returns the bounds of the border around the specified scroll pane's
     * viewport.
     *
     * <p>
     * 返回指定滚动窗格视口周围的边框的边界
     * 
     * 
     * @return the size and position of the viewport border
     * @deprecated As of JDK version Swing1.1
     *    replaced by <code>JScrollPane.getViewportBorderBounds()</code>.
     */
    @Deprecated
    public Rectangle getViewportBorderBounds(JScrollPane scrollpane) {
        return scrollpane.getViewportBorderBounds();
    }

    /**
     * The UI resource version of <code>ScrollPaneLayout</code>.
     * <p>
     *  <code> ScrollPaneLayout </code>的UI资源版本
     */
    public static class UIResource extends ScrollPaneLayout implements javax.swing.plaf.UIResource {}
}
