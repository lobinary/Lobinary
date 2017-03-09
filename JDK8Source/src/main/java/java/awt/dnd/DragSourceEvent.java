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

package java.awt.dnd;

import java.awt.Point;

import java.util.EventObject;

/**
 * This class is the base class for
 * <code>DragSourceDragEvent</code> and
 * <code>DragSourceDropEvent</code>.
 * <p>
 * <code>DragSourceEvent</code>s are generated whenever the drag enters, moves
 * over, or exits a drop site, when the drop action changes, and when the drag
 * ends. The location for the generated <code>DragSourceEvent</code> specifies
 * the mouse cursor location in screen coordinates at the moment this event
 * occurred.
 * <p>
 * In a multi-screen environment without a virtual device, the cursor location is
 * specified in the coordinate system of the <i>initiator</i>
 * <code>GraphicsConfiguration</code>. The <i>initiator</i>
 * <code>GraphicsConfiguration</code> is the <code>GraphicsConfiguration</code>
 * of the <code>Component</code> on which the drag gesture for the current drag
 * operation was recognized. If the cursor location is outside the bounds of
 * the initiator <code>GraphicsConfiguration</code>, the reported coordinates are
 * clipped to fit within the bounds of that <code>GraphicsConfiguration</code>.
 * <p>
 * In a multi-screen environment with a virtual device, the location is specified
 * in the corresponding virtual coordinate system. If the cursor location is
 * outside the bounds of the virtual device the reported coordinates are
 * clipped to fit within the bounds of the virtual device.
 *
 * <p>
 *  这个类是<code> DragSourceDragEvent </code>和<code> DragSourceDropEvent </code>的基类。
 * <p>
 *  每当拖动进入,移动或退出放置站点时,拖放操作更改时,以及拖动结束时,都会生成<code> DragSourceEvent </code>。
 * 生成的<code> DragSourceEvent </code>的位置指定此事件发生时鼠标在屏幕坐标中的位置。
 * <p>
 *  在没有虚拟设备的多屏幕环境中,光标位置在<i>启动器</i> <code> GraphicsConfiguration </code>的坐标系中指定。
 *  <i>启动器</i> <code> GraphicsConfiguration </code>是其上识别当前拖动操作的拖动手势的<code> Component </code>的<code> Grap
 * hicsConfiguration </code>。
 *  在没有虚拟设备的多屏幕环境中,光标位置在<i>启动器</i> <code> GraphicsConfiguration </code>的坐标系中指定。
 * 如果光标位置在发起者<code> GraphicsConfiguration </code>的边界之外,则所报告的坐标被修剪以适合<code> GraphicsConfiguration </code>
 * 的边界。
 *  在没有虚拟设备的多屏幕环境中,光标位置在<i>启动器</i> <code> GraphicsConfiguration </code>的坐标系中指定。
 * <p>
 *  在具有虚拟设备的多屏幕环境中,在相应的虚拟坐标系中指定位置。如果光标位置在虚拟设备的界限之外,则所报告的坐标被修剪以适合在虚拟设备的界限内。
 * 
 * 
 * @since 1.2
 */

public class DragSourceEvent extends EventObject {

    private static final long serialVersionUID = -763287114604032641L;

    /**
     * The <code>boolean</code> indicating whether the cursor location
     * is specified for this event.
     *
     * <p>
     * <code> boolean </code>指示是否为此事件指定了光标位置。
     * 
     * 
     * @serial
     */
    private final boolean locationSpecified;

    /**
     * The horizontal coordinate for the cursor location at the moment this
     * event occurred if the cursor location is specified for this event;
     * otherwise zero.
     *
     * <p>
     *  如果为此事件指定了光标位置,则此事件发生时光标位置的水平坐标;否则为零。
     * 
     * 
     * @serial
     */
    private final int x;

    /**
     * The vertical coordinate for the cursor location at the moment this event
     * occurred if the cursor location is specified for this event;
     * otherwise zero.
     *
     * <p>
     *  如果为此事件指定了光标位置,则此事件发生时光标位置的垂直坐标;否则为零。
     * 
     * 
     * @serial
     */
    private final int y;

    /**
     * Construct a <code>DragSourceEvent</code>
     * given a specified <code>DragSourceContext</code>.
     * The coordinates for this <code>DragSourceEvent</code>
     * are not specified, so <code>getLocation</code> will return
     * <code>null</code> for this event.
     *
     * <p>
     *  构造一个指定<code> DragSourceContext </code>的<code> DragSourceEvent </code>。
     * 未指定此<code> DragSourceEvent </code>的坐标,因此<code> getLocation </code>将为此事件返回<code> null </code>。
     * 
     * 
     * @param dsc the <code>DragSourceContext</code>
     *
     * @throws IllegalArgumentException if <code>dsc</code> is <code>null</code>.
     *
     * @see #getLocation
     */

    public DragSourceEvent(DragSourceContext dsc) {
        super(dsc);
        locationSpecified = false;
        this.x = 0;
        this.y = 0;
    }

    /**
     * Construct a <code>DragSourceEvent</code> given a specified
     * <code>DragSourceContext</code>, and coordinates of the cursor
     * location.
     *
     * <p>
     *  构造一个<code> DragSourceEvent </code>,指定一个指定的<code> DragSourceContext </code>和光标位置的坐标。
     * 
     * 
     * @param dsc the <code>DragSourceContext</code>
     * @param x   the horizontal coordinate for the cursor location
     * @param y   the vertical coordinate for the cursor location
     *
     * @throws IllegalArgumentException if <code>dsc</code> is <code>null</code>.
     *
     * @since 1.4
     */
    public DragSourceEvent(DragSourceContext dsc, int x, int y) {
        super(dsc);
        locationSpecified = true;
        this.x = x;
        this.y = y;
    }

    /**
     * This method returns the <code>DragSourceContext</code> that
     * originated the event.
     * <P>
     * <p>
     *  此方法返回发起事件的<code> DragSourceContext </code>。
     * <P>
     * 
     * @return the <code>DragSourceContext</code> that originated the event
     */

    public DragSourceContext getDragSourceContext() {
        return (DragSourceContext)getSource();
    }

    /**
     * This method returns a <code>Point</code> indicating the cursor
     * location in screen coordinates at the moment this event occurred, or
     * <code>null</code> if the cursor location is not specified for this
     * event.
     *
     * <p>
     *  此方法在此事件发生时返回指示屏幕坐标中光标位置的<code> Point </code>,如果未为此事件指定光标位置,则返回<code> null </code>。
     * 
     * 
     * @return the <code>Point</code> indicating the cursor location
     *         or <code>null</code> if the cursor location is not specified
     * @since 1.4
     */
    public Point getLocation() {
        if (locationSpecified) {
            return new Point(x, y);
        } else {
            return null;
        }
    }

    /**
     * This method returns the horizontal coordinate of the cursor location in
     * screen coordinates at the moment this event occurred, or zero if the
     * cursor location is not specified for this event.
     *
     * <p>
     *  此方法返回此事件发生时屏幕坐标中光标位置的水平坐标,如果未为此事件指定光标位置,则返回零。
     * 
     * 
     * @return an integer indicating the horizontal coordinate of the cursor
     *         location or zero if the cursor location is not specified
     * @since 1.4
     */
    public int getX() {
        return x;
    }

    /**
     * This method returns the vertical coordinate of the cursor location in
     * screen coordinates at the moment this event occurred, or zero if the
     * cursor location is not specified for this event.
     *
     * <p>
     *  此方法返回此事件发生时屏幕坐标中光标位置的垂直坐标,如果未为此事件指定光标位置,则返回零。
     * 
     * @return an integer indicating the vertical coordinate of the cursor
     *         location or zero if the cursor location is not specified
     * @since 1.4
     */
    public int getY() {
        return y;
    }
}
