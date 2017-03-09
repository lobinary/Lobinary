/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2012, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.Component;
import java.awt.Window;

/**
 * The native peer interface for {@link KeyboardFocusManager}.
 * <p>
 *  {@link KeyboardFocusManager}的本地对等接口。
 * 
 */
public interface KeyboardFocusManagerPeer {

    /**
     * Sets the window that should become the focused window.
     *
     * <p>
     *  设置应成为关注窗口的窗口。
     * 
     * 
     * @param win the window that should become the focused window
     *
     */
    void setCurrentFocusedWindow(Window win);

    /**
     * Returns the currently focused window.
     *
     * <p>
     *  返回当前聚焦的窗口。
     * 
     * 
     * @return the currently focused window
     *
     * @see KeyboardFocusManager#getNativeFocusedWindow()
     */
    Window getCurrentFocusedWindow();

    /**
     * Sets the component that should become the focus owner.
     *
     * <p>
     *  设置应该成为焦点所有者的组件。
     * 
     * 
     * @param comp the component to become the focus owner
     *
     * @see KeyboardFocusManager#setNativeFocusOwner(Component)
     */
    void setCurrentFocusOwner(Component comp);

    /**
     * Returns the component that currently owns the input focus.
     *
     * <p>
     *  返回当前拥有输入焦点的组件。
     * 
     * 
     * @return the component that currently owns the input focus
     *
     * @see KeyboardFocusManager#getNativeFocusOwner()
     */
    Component getCurrentFocusOwner();

    /**
     * Clears the current global focus owner.
     *
     * <p>
     *  清除当前全局焦点所有者。
     * 
     * @param activeWindow
     *
     * @see KeyboardFocusManager#clearGlobalFocusOwner()
     */
    void clearGlobalFocusOwner(Window activeWindow);

}
