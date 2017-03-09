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

package javax.swing;

import java.awt.Dimension;
import java.awt.Rectangle;


/**
 * An interface that provides information to a scrolling container
 * like JScrollPane.  A complex component that's likely to be used
 * as a viewing a JScrollPane viewport (or other scrolling container)
 * should implement this interface.
 *
 * <p>
 *  向JScrollPane等滚动容器提供信息的接口。可能用作查看JScrollPane视口(或其他滚动容器)的复杂组件应实现此接口。
 * 
 * 
 * @see JViewport
 * @see JScrollPane
 * @see JScrollBar
 * @author Hans Muller
 */
public interface Scrollable
{
    /**
     * Returns the preferred size of the viewport for a view component.
     * For example, the preferred size of a <code>JList</code> component
     * is the size required to accommodate all of the cells in its list.
     * However, the value of <code>preferredScrollableViewportSize</code>
     * is the size required for <code>JList.getVisibleRowCount</code> rows.
     * A component without any properties that would affect the viewport
     * size should just return <code>getPreferredSize</code> here.
     *
     * <p>
     *  返回视图组件的视口的首选大小。例如,<code> JList </code>组件的优选大小是容纳其列表中的所有单元所需的大小。
     * 但是,<code> preferredScrollableViewportSize </code>的值是<code> JList.getVisibleRowCount </code>行所需的大小。
     * 没有任何会影响视口大小的属性的组件应该在这里返回<code> getPreferredSize </code>。
     * 
     * 
     * @return the preferredSize of a <code>JViewport</code> whose view
     *    is this <code>Scrollable</code>
     * @see JViewport#getPreferredSize
     */
    Dimension getPreferredScrollableViewportSize();


    /**
     * Components that display logical rows or columns should compute
     * the scroll increment that will completely expose one new row
     * or column, depending on the value of orientation.  Ideally,
     * components should handle a partially exposed row or column by
     * returning the distance required to completely expose the item.
     * <p>
     * Scrolling containers, like JScrollPane, will use this method
     * each time the user requests a unit scroll.
     *
     * <p>
     *  显示逻辑行或列的组件应计算滚动增量,这将完全暴露一个新行或列,具体取决于定向值。理想情况下,组件应通过返回完全暴露项目所需的距离来处理部分暴露的行或列。
     * <p>
     *  滚动容器(如JScrollPane)将在每次用户请求单元滚动时使用此方法。
     * 
     * 
     * @param visibleRect The view area visible within the viewport
     * @param orientation Either SwingConstants.VERTICAL or SwingConstants.HORIZONTAL.
     * @param direction Less than zero to scroll up/left, greater than zero for down/right.
     * @return The "unit" increment for scrolling in the specified direction.
     *         This value should always be positive.
     * @see JScrollBar#setUnitIncrement
     */
    int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction);


    /**
     * Components that display logical rows or columns should compute
     * the scroll increment that will completely expose one block
     * of rows or columns, depending on the value of orientation.
     * <p>
     * Scrolling containers, like JScrollPane, will use this method
     * each time the user requests a block scroll.
     *
     * <p>
     *  显示逻辑行或列的组件应计算滚动增量,这将完全暴露一个行或列的块,具体取决于定向的值。
     * <p>
     * 滚动容器,如JScrollPane,将在每次用户请求块滚动时使用此方法。
     * 
     * 
     * @param visibleRect The view area visible within the viewport
     * @param orientation Either SwingConstants.VERTICAL or SwingConstants.HORIZONTAL.
     * @param direction Less than zero to scroll up/left, greater than zero for down/right.
     * @return The "block" increment for scrolling in the specified direction.
     *         This value should always be positive.
     * @see JScrollBar#setBlockIncrement
     */
    int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction);


    /**
     * Return true if a viewport should always force the width of this
     * <code>Scrollable</code> to match the width of the viewport.
     * For example a normal
     * text view that supported line wrapping would return true here, since it
     * would be undesirable for wrapped lines to disappear beyond the right
     * edge of the viewport.  Note that returning true for a Scrollable
     * whose ancestor is a JScrollPane effectively disables horizontal
     * scrolling.
     * <p>
     * Scrolling containers, like JViewport, will use this method each
     * time they are validated.
     *
     * <p>
     *  如果视口应始终强制此<code> Scrollable </code>的宽度匹配视口的宽度,则返回true。
     * 例如,支持行包装的普通文本视图在这里返回true,因为对于包装的行消失在视口的右边缘之外是不希望的。注意,对于其祖先是JScrollPane的Scrollable,返回true将有效地禁用水平滚动。
     * <p>
     *  滚动容器(如JViewport)将在每次验证时使用此方法。
     * 
     * 
     * @return True if a viewport should force the Scrollables width to match its own.
     */
    boolean getScrollableTracksViewportWidth();

    /**
     * Return true if a viewport should always force the height of this
     * Scrollable to match the height of the viewport.  For example a
     * columnar text view that flowed text in left to right columns
     * could effectively disable vertical scrolling by returning
     * true here.
     * <p>
     * Scrolling containers, like JViewport, will use this method each
     * time they are validated.
     *
     * <p>
     * 
     * @return True if a viewport should force the Scrollables height to match its own.
     */
    boolean getScrollableTracksViewportHeight();
}
