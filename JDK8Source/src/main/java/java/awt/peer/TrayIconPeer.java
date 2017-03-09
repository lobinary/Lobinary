/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2007, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.SystemTray;
import java.awt.TrayIcon;

/**
 * The peer interface for the {@link TrayIcon}. This doesn't need to be
 * implemented if {@link SystemTray#isSupported()} returns false.
 * <p>
 *  {@link TrayIcon}的对等接口。如果{@link SystemTray#isSupported()}返回false,则不需要实现。
 * 
 */
public interface TrayIconPeer {

    /**
     * Disposes the tray icon and releases and resources held by it.
     *
     * <p>
     *  处理托盘图标及其所持有的发布和资源。
     * 
     * 
     * @see TrayIcon#removeNotify()
     */
    void dispose();

    /**
     * Sets the tool tip for the tray icon.
     *
     * <p>
     *  设置托盘图标的工具提示。
     * 
     * 
     * @param tooltip the tooltip to set
     *
     * @see TrayIcon#setToolTip(String)
     */
    void setToolTip(String tooltip);

    /**
     * Updates the icon image. This is supposed to display the current icon
     * from the TrayIcon component in the actual tray icon.
     *
     * <p>
     *  更新图标图像。这应该在实际托盘图标中显示来自TrayIcon组件的当前图标。
     * 
     * 
     * @see TrayIcon#setImage(java.awt.Image)
     * @see TrayIcon#setImageAutoSize(boolean)
     */
    void updateImage();

    /**
     * Displays a message at the tray icon.
     *
     * <p>
     *  在托盘图标上显示消息。
     * 
     * 
     * @param caption the message caption
     * @param text the actual message text
     * @param messageType the message type
     *
     * @see TrayIcon#displayMessage(String, String, java.awt.TrayIcon.MessageType)
     */
    void displayMessage(String caption, String text, String messageType);

    /**
     * Shows the popup menu of this tray icon at the specified position.
     *
     * <p>
     *  在指定位置显示此托盘图标的弹出式菜单。
     * 
     * @param x the X location for the popup menu
     * @param y the Y location for the popup menu
     */
    void showPopupMenu(int x, int y);
}
