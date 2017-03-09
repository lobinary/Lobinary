/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 1999, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.Insets;
import java.awt.Point;

/**
 * During DnD operations it is possible that a user may wish to drop the
 * subject of the operation on a region of a scrollable GUI control that is
 * not currently visible to the user.
 * <p>
 * In such situations it is desirable that the GUI control detect this
 * and institute a scroll operation in order to make obscured region(s)
 * visible to the user. This feature is known as autoscrolling.
 * <p>
 * If a GUI control is both an active <code>DropTarget</code>
 * and is also scrollable, it
 * can receive notifications of autoscrolling gestures by the user from
 * the DnD system by implementing this interface.
 * <p>
 * An autoscrolling gesture is initiated by the user by keeping the drag
 * cursor motionless with a border region of the <code>Component</code>,
 * referred to as
 * the "autoscrolling region", for a predefined period of time, this will
 * result in repeated scroll requests to the <code>Component</code>
 * until the drag <code>Cursor</code> resumes its motion.
 *
 * <p>
 *  在DnD操作期间,用户可能希望在可滚动GUI控件的当前对用户不可见的区域上放下操作的主题。
 * <p>
 *  在这种情况下,期望GUI控制检测到这一点并且进行滚动操作以便使得被遮蔽的区域对于用户可见。此功能称为自动滚动。
 * <p>
 *  如果GUI控件既是活动的<code> DropTarget </code>并且也是可滚动的,则它可以通过实现该接口从DnD系统接收用户自动滚动手势的通知。
 * <p>
 *  自动滚动手势由用户通过保持拖动光标与<code> Component </code>(被称为"自动滚动区域")的边界区域静止预定义的时间段来启动,这将导致重复将请求滚动到<code> Componen
 * t </code>,直到拖动<code> Cursor </code>恢复其运动。
 * 
 * 
 * @since 1.2
 */

public interface Autoscroll {

    /**
     * This method returns the <code>Insets</code> describing
     * the autoscrolling region or border relative
     * to the geometry of the implementing Component.
     * <P>
     * This value is read once by the <code>DropTarget</code>
     * upon entry of the drag <code>Cursor</code>
     * into the associated <code>Component</code>.
     * <P>
     * <p>
     * 
     * @return the Insets
     */

    public Insets getAutoscrollInsets();

    /**
     * notify the <code>Component</code> to autoscroll
     * <P>
     * <p>
     *  此方法返回描述相对于实现组件的几何的自动滚动区域或边框的<code> Insets </code>。
     * <P>
     *  在将<code> Cursor </code>拖动到相关联的<code> Component </code>中时,通过<code> DropTarget </code>读取一次该值。
     * <P>
     * 
     * @param cursorLocn A <code>Point</code> indicating the
     * location of the cursor that triggered this operation.
     */

    public void autoscroll(Point cursorLocn);

}
