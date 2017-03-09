/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.Window;
import java.awt.Point;

/**
 * Peer interface for {@link MouseInfo}. This is used to get some additional
 * information about the mouse.
 *
 * The peer interfaces are intended only for use in porting
 * the AWT. They are not intended for use by application
 * developers, and developers should not implement peers
 * nor invoke any of the peer methods directly on the peer
 * instances.
 * <p>
 *  {@link MouseInfo}的对等接口。这用于获取有关鼠标的一些其他信息。
 * 
 *  对等接口仅用于移植AWT。它们不是供应用程序开发人员使用的,并且开发人员不应实现对等体,也不应直接在对等实例上调用任何对等方法。
 * 
 */
public interface MouseInfoPeer {

    /**
     * This method does two things: it fills the point fields with
     * the current coordinates of the mouse cursor and returns the
     * number of the screen device where the pointer is located.
     * The number of the screen device is only returned for independent
     * devices (which are not parts of a virtual screen device).
     * For virtual screen devices, 0 is returned.
     * Mouse coordinates are also calculated depending on whether
     * or not the screen device is virtual. For virtual screen
     * devices, pointer coordinates are calculated in the virtual
     * coordinate system. Otherwise, coordinates are calculated in
     * the coordinate system of the screen device where the pointer
     * is located.
     * See java.awt.GraphicsConfiguration documentation for more
     * details about virtual screen devices.
     * <p>
     *  此方法有两件事：它用鼠标光标的当前坐标填充点字段,并返回指针所在的屏幕设备的编号。屏幕设备的编号仅针对独立设备(不是虚拟屏幕设备的一部分)返回。对于虚拟屏幕设备,返回0。
     * 还根据屏幕设备是否是虚拟的来计算鼠标坐标。对于虚拟屏幕设备,在虚拟坐标系中计算指针坐标。否则,在指针所在的屏幕设备的坐标系中计算坐标。
     * 有关虚拟屏幕设备的更多详细信息,请参阅java.awt.GraphicsConfiguration文档。
     * 
     */
    int fillPointWithCoords(Point point);

    /**
     * Returns whether or not the window is located under the mouse
     * pointer. The window is considered to be under the mouse pointer
     * if it is showing on the screen, and the mouse pointer is above
     * the part of the window that is not obscured by any other windows.
     * <p>
     */
    boolean isWindowUnderMouse(Window w);

}
