/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2007, Oracle and/or its affiliates. All rights reserved.
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
package java.awt.peer;

import java.awt.Adjustable;
import java.awt.ScrollPane;
import java.awt.ScrollPaneAdjustable;

/**
 * The peer interface for {@link ScrollPane}.
 *
 * The peer interfaces are intended only for use in porting
 * the AWT. They are not intended for use by application
 * developers, and developers should not implement peers
 * nor invoke any of the peer methods directly on the peer
 * instances.
 * <p>
 *  {@link ScrollPane}的对等接口。
 * 
 *  对等接口仅用于移植AWT。它们不是供应用程序开发人员使用的,并且开发人员不应实现对等体,也不应直接在对等实例上调用任何对等方法。
 * 
 */
public interface ScrollPanePeer extends ContainerPeer {

    /**
     * Returns the height of the horizontal scroll bar.
     *
     * <p>
     *  返回水平滚动条的高度。
     * 
     * 
     * @return the height of the horizontal scroll bar
     *
     * @see ScrollPane#getHScrollbarHeight()
     */
    int getHScrollbarHeight();

    /**
     * Returns the width of the vertical scroll bar.
     *
     * <p>
     *  返回垂直滚动条的宽度。
     * 
     * 
     * @return the width of the vertical scroll bar
     *
     * @see ScrollPane#getVScrollbarWidth()
     */
    int getVScrollbarWidth();

    /**
     * Sets the scroll position of the child.
     *
     * <p>
     *  设置子项的滚动位置。
     * 
     * 
     * @param x the X coordinate of the scroll position
     * @param y the Y coordinate of the scroll position
     *
     * @see ScrollPane#setScrollPosition(int, int)
     */
    void setScrollPosition(int x, int y);

    /**
     * Called when the child component changes its size.
     *
     * <p>
     *  当子组件更改其大小时调用。
     * 
     * 
     * @param w the new width of the child component
     * @param h the new height of the child component
     *
     * @see ScrollPane#layout()
     */
    void childResized(int w, int h);

    /**
     * Sets the unit increment of one of the scroll pane's adjustables.
     *
     * <p>
     *  设置滚动窗格的可调整项之一的单位增量。
     * 
     * 
     * @param adj the scroll pane adjustable object
     * @param u the unit increment
     *
     * @see ScrollPaneAdjustable#setUnitIncrement(int)
     */
    void setUnitIncrement(Adjustable adj, int u);

    /**
     * Sets the value for one of the scroll pane's adjustables.
     *
     * <p>
     *  设置滚动窗格的可调整值之一的值。
     * 
     * @param adj the scroll pane adjustable object
     * @param v the value to set
     */
    void setValue(Adjustable adj, int v);
}
