/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.*;

import sun.awt.EmbeddedFrame;

/**
 * The peer interface for {@link Frame}. This adds a couple of frame specific
 * methods to the {@link WindowPeer} interface.
 *
 * The peer interfaces are intended only for use in porting
 * the AWT. They are not intended for use by application
 * developers, and developers should not implement peers
 * nor invoke any of the peer methods directly on the peer
 * instances.
 * <p>
 *  {@link Frame}的对等接口。这会为{@link WindowPeer}接口添加几种特定于帧的方法。
 * 
 *  对等接口仅用于移植AWT。它们不是供应用程序开发人员使用的,并且开发人员不应实现对等体,也不应直接在对等实例上调用任何对等方法。
 * 
 */
public interface FramePeer extends WindowPeer {

    /**
     * Sets the title on the frame.
     *
     * <p>
     *  设置框架上的标题。
     * 
     * 
     * @param title the title to set
     *
     * @see Frame#setTitle(String)
     */
    void setTitle(String title);

    /**
     * Sets the menu bar for the frame.
     *
     * <p>
     *  设置框架的菜单栏。
     * 
     * 
     * @param mb the menu bar to set
     *
     * @see Frame#setMenuBar(MenuBar)
     */
    void setMenuBar(MenuBar mb);

    /**
     * Sets if the frame should be resizable or not.
     *
     * <p>
     *  设置帧是否应该可调整大小。
     * 
     * 
     * @param resizeable {@code true} when the frame should be resizable,
     *        {@code false} if not
     *
     * @see Frame#setResizable(boolean)
     */
    void setResizable(boolean resizeable);

    /**
     * Changes the state of the frame.
     *
     * <p>
     *  更改框架的状态。
     * 
     * 
     * @param state the new state
     *
     * @see Frame#setExtendedState(int)
     */
    void setState(int state);

    /**
     * Returns the current state of the frame.
     *
     * <p>
     *  返回帧的当前状态。
     * 
     * 
     * @return the current state of the frame
     *
     * @see Frame#getExtendedState()
     */
    int getState();

    /**
     * Sets the bounds of the frame when it becomes maximized.
     *
     * <p>
     *  设置帧变为最大时的边界。
     * 
     * 
     * @param bounds the maximized bounds of the frame
     *
     * @see Frame#setMaximizedBounds(Rectangle)
     */
    void setMaximizedBounds(Rectangle bounds);

    /**
     * Sets the size and location for embedded frames. (On embedded frames,
     * setLocation() and setBounds() always set the frame to (0,0) for
     * backwards compatibility.
     *
     * <p>
     *  设置嵌入框的大小和位置。 (在嵌入式框架上,setLocation()和setBounds()总是将帧设置为(0,0)为向后兼容性。
     * 
     * 
     * @param x the X location
     * @param y the Y location
     * @param width the width of the frame
     * @param height the height of the frame
     *
     * @see EmbeddedFrame#setBoundsPrivate(int, int, int, int)
     */
    // TODO: This is only used in EmbeddedFrame, and should probably be moved
    // into an EmbeddedFramePeer which would extend FramePeer
    void setBoundsPrivate(int x, int y, int width, int height);

    /**
     * Returns the size and location for embedded frames. (On embedded frames,
     * setLocation() and setBounds() always set the frame to (0,0) for
     * backwards compatibility.
     *
     * <p>
     *  返回嵌入框的大小和位置。 (在嵌入式框架上,setLocation()和setBounds()总是将帧设置为(0,0)为向后兼容性。
     * 
     * 
     * @return the bounds of an embedded frame
     *
     * @see EmbeddedFrame#getBoundsPrivate()
     */
    // TODO: This is only used in EmbeddedFrame, and should probably be moved
    // into an EmbeddedFramePeer which would extend FramePeer
    Rectangle getBoundsPrivate();

    /**
     * Requests the peer to emulate window activation.
     *
     * <p>
     *  请求对等体模拟窗口激活。
     * 
     * @param activate activate or deactivate the window
     */
    void emulateActivation(boolean activate);
}
