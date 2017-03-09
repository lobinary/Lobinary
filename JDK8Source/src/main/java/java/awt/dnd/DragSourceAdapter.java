/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, 2003, Oracle and/or its affiliates. All rights reserved.
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

/**
 * An abstract adapter class for receiving drag source events. The methods in
 * this class are empty. This class exists only as a convenience for creating
 * listener objects.
 * <p>
 * Extend this class to create a <code>DragSourceEvent</code> listener
 * and override the methods for the events of interest. (If you implement the
 * <code>DragSourceListener</code> interface, you have to define all of
 * the methods in it. This abstract class defines null methods for them
 * all, so you only have to define methods for events you care about.)
 * <p>
 * Create a listener object using the extended class and then register it with
 * a <code>DragSource</code>. When the drag enters, moves over, or exits
 * a drop site, when the drop action changes, and when the drag ends, the
 * relevant method in the listener object is invoked, and the
 * <code>DragSourceEvent</code> is passed to it.
 * <p>
 * The drop site is <i>associated with the previous <code>dragEnter()</code>
 * invocation</i> if the latest invocation of <code>dragEnter()</code> on this
 * adapter corresponds to that drop site and is not followed by a
 * <code>dragExit()</code> invocation on this adapter.
 *
 * <p>
 *  用于接收拖动源事件的抽象适配器类。此类中的方法为空。此类仅作为创建侦听器对象的方便存在。
 * <p>
 *  扩展此类以创建一个<code> DragSourceEvent </code>侦听器并覆盖所关注事件的方法。
 *  (如果你实现<code> DragSourceListener </code>接口,你必须定义所有的方法,这个抽象类为它们定义了null方法,所以你只需要为你关心的事件定义方法。)。
 * <p>
 *  使用扩展类创建一个监听器对象,然后使用<code> DragSource </code>注册它。
 * 当拖动进入,移动或退出放置站点时,拖放操作更改时,拖动结束时,将调用侦听器对象中的相关方法,并将<code> DragSourceEvent </code>传递给它。
 * <p>
 *  如果此适配器上的<code> dragEnter()</code>的最新调用对应于该删除站点,则拖放站点与先前<code> dragEnter()</code>调用</i>在此适配器上不会跟有<code>
 *  dragExit()</code>调用。
 * 
 * 
 * @see DragSourceEvent
 * @see DragSourceListener
 * @see DragSourceMotionListener
 *
 * @author David Mendenhall
 * @since 1.4
 */
public abstract class DragSourceAdapter
    implements DragSourceListener, DragSourceMotionListener {

    /**
     * Called as the cursor's hotspot enters a platform-dependent drop site.
     * This method is invoked when all the following conditions are true:
     * <UL>
     * <LI>The cursor's hotspot enters the operable part of
     * a platform-dependent drop site.
     * <LI>The drop site is active.
     * <LI>The drop site accepts the drag.
     * </UL>
     *
     * <p>
     *  称为光标的热点进入平台相关的下载站点。当所有以下条件为真时调用此方法：
     * <UL>
     * <LI>光标的热点进入平台相关的下载站点的可操作部分。 <LI>放置站点处于活动状态。 <LI>放置站点接受拖动。
     * </UL>
     * 
     * 
     * @param dsde the <code>DragSourceDragEvent</code>
     */
    public void dragEnter(DragSourceDragEvent dsde) {}

    /**
     * Called as the cursor's hotspot moves over a platform-dependent drop site.
     * This method is invoked when all the following conditions are true:
     * <UL>
     * <LI>The cursor's hotspot has moved, but still intersects the
     * operable part of the drop site associated with the previous
     * dragEnter() invocation.
     * <LI>The drop site is still active.
     * <LI>The drop site accepts the drag.
     * </UL>
     *
     * <p>
     *  当游标的热点移动到与平台相关的下载站点时被调用。当所有以下条件为真时调用此方法：
     * <UL>
     *  <LI>光标的热点已移动,但仍与与上一个dragEnter()调用相关联的放置网站的可操作部分相交。 <LI>放置网站仍然有效。 <LI>放置站点接受拖动。
     * </UL>
     * 
     * 
     * @param dsde the <code>DragSourceDragEvent</code>
     */
    public void dragOver(DragSourceDragEvent dsde) {}

    /**
     * Called whenever the mouse is moved during a drag operation.
     *
     * <p>
     *  在拖动操作期间移动鼠标时调用。
     * 
     * 
     * @param dsde the <code>DragSourceDragEvent</code>
     */
    public void dragMouseMoved(DragSourceDragEvent dsde) {}

    /**
     * Called when the user has modified the drop gesture.
     * This method is invoked when the state of the input
     * device(s) that the user is interacting with changes.
     * Such devices are typically the mouse buttons or keyboard
     * modifiers that the user is interacting with.
     *
     * <p>
     *  当用户修改放置手势时调用。当用户正在与之交互的输入设备的状态改变时,调用该方法。这样的设备通常是用户正在与之交互的鼠标按钮或键盘修改器。
     * 
     * 
     * @param dsde the <code>DragSourceDragEvent</code>
     */
    public void dropActionChanged(DragSourceDragEvent dsde) {}

    /**
     * Called as the cursor's hotspot exits a platform-dependent drop site.
     * This method is invoked when any of the following conditions are true:
     * <UL>
     * <LI>The cursor's hotspot no longer intersects the operable part
     * of the drop site associated with the previous dragEnter() invocation.
     * </UL>
     * OR
     * <UL>
     * <LI>The drop site associated with the previous dragEnter() invocation
     * is no longer active.
     * </UL>
     * OR
     * <UL>
     * <LI> The drop site associated with the previous dragEnter() invocation
     * has rejected the drag.
     * </UL>
     *
     * <p>
     *  称为光标的热点退出平台相关的下载站点。当满足以下任一条件时,将调用此方法：
     * <UL>
     *  <LI>游标的热点不再与与上一次dragEnter()调用相关联的放置网站的可操作部分相交。
     * </UL>
     *  要么
     * <UL>
     *  <LI>与上一个dragEnter()调用相关联的放置站点不再处于活动状态。
     * </UL>
     *  要么
     * 
     * @param dse the <code>DragSourceEvent</code>
     */
    public void dragExit(DragSourceEvent dse) {}

    /**
     * This method is invoked to signify that the Drag and Drop
     * operation is complete. The getDropSuccess() method of
     * the <code>DragSourceDropEvent</code> can be used to
     * determine the termination state. The getDropAction() method
     * returns the operation that the drop site selected
     * to apply to the Drop operation. Once this method is complete, the
     * current <code>DragSourceContext</code> and
     * associated resources become invalid.
     *
     * <p>
     * <UL>
     *  <LI>与上一个dragEnter()调用相关联的放置站点已拒绝拖动。
     * </UL>
     * 
     * 
     * @param dsde the <code>DragSourceDropEvent</code>
     */
    public void dragDropEnd(DragSourceDropEvent dsde) {}
}
