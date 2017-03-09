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

import java.awt.Dimension;
import java.awt.SystemTray;

/**
 * The peer interface for {@link SystemTray}. This doesn't need to be
 * implemented if {@link SystemTray#isSupported()} returns false.
 * <p>
 *  {@link SystemTray}的对等接口。如果{@link SystemTray#isSupported()}返回false,则不需要实现。
 * 
 */
public interface SystemTrayPeer {

    /**
     * Returns the size of the system tray icon.
     *
     * <p>
     *  返回系统托盘图标的大小。
     * 
     * @return the size of the system tray icon
     *
     * @see SystemTray#getTrayIconSize()
     */
    Dimension getTrayIconSize();
}
