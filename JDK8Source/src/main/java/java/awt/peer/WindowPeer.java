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

/**
 * The peer interface for {@link Window}.
 *
 * The peer interfaces are intended only for use in porting
 * the AWT. They are not intended for use by application
 * developers, and developers should not implement peers
 * nor invoke any of the peer methods directly on the peer
 * instances.
 * <p>
 *  {@link Window}的对等接口。
 * 
 *  对等接口仅用于移植AWT。它们不是供应用程序开发人员使用的,并且开发人员不应实现对等体,也不应直接在对等实例上调用任何对等方法。
 * 
 */
public interface WindowPeer extends ContainerPeer {

    /**
     * Makes this window the topmost window on the desktop.
     *
     * <p>
     *  使此窗口成为桌面上的最顶层窗口。
     * 
     * 
     * @see Window#toFront()
     */
    void toFront();

    /**
     * Makes this window the bottommost window on the desktop.
     *
     * <p>
     *  使此窗口成为桌面上最底部的窗口。
     * 
     * 
     * @see Window#toBack()
     */
    void toBack();

    /**
     * Updates the window's always-on-top state.
     * Sets if the window should always stay
     * on top of all other windows or not.
     *
     * <p>
     *  更新窗口的始终在顶部状态。设置窗口是否应始终保持在所有其他窗口的顶部。
     * 
     * 
     * @see Window#getAlwaysOnTop()
     * @see Window#setAlwaysOnTop(boolean)
     */
    void updateAlwaysOnTopState();

    /**
     * Updates the window's focusable state.
     *
     * <p>
     *  更新窗口的可对焦状态。
     * 
     * 
     * @see Window#setFocusableWindowState(boolean)
     */
    void updateFocusableWindowState();

    /**
     * Sets if this window is blocked by a modal dialog or not.
     *
     * <p>
     *  设置此窗口是否被模态对话框阻止。
     * 
     * 
     * @param blocker the blocking modal dialog
     * @param blocked {@code true} to block the window, {@code false}
     *        to unblock it
     */
    void setModalBlocked(Dialog blocker, boolean blocked);

    /**
     * Updates the minimum size on the peer.
     *
     * <p>
     *  更新对等体的最小大小。
     * 
     * 
     * @see Window#setMinimumSize(Dimension)
     */
    void updateMinimumSize();

    /**
     * Updates the icons for the window.
     *
     * <p>
     *  更新窗口的图标。
     * 
     * 
     * @see Window#setIconImages(java.util.List)
     */
    void updateIconImages();

    /**
     * Sets the level of opacity for the window.
     *
     * <p>
     *  设置窗口的不透明度级别。
     * 
     * 
     * @see Window#setOpacity(float)
     */
    void setOpacity(float opacity);

    /**
     * Enables the per-pixel alpha support for the window.
     *
     * <p>
     *  启用窗口的每像素alpha支持。
     * 
     * 
     * @see Window#setBackground(Color)
     */
    void setOpaque(boolean isOpaque);

    /**
     * Updates the native part of non-opaque window.
     *
     * <p>
     *  更新非不透明窗口的本机部分。
     * 
     * 
     * @see Window#setBackground(Color)
     */
    void updateWindow();

    /**
     * Instructs the peer to update the position of the security warning.
     * <p>
     *  指示对等体更新安全警告的位置。
     */
    void repositionSecurityWarning();
}
