/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2003, Oracle and/or its affiliates. All rights reserved.
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

import java.util.EventListener;

/**
 * The <code>DragSourceListener</code> defines the
 * event interface for originators of
 * Drag and Drop operations to track the state of the user's gesture, and to
 * provide appropriate &quot;drag over&quot;
 * feedback to the user throughout the
 * Drag and Drop operation.
 * <p>
 * The drop site is <i>associated with the previous <code>dragEnter()</code>
 * invocation</i> if the latest invocation of <code>dragEnter()</code> on this
 * listener:
 * <ul>
 * <li>corresponds to that drop site and
 * <li> is not followed by a <code>dragExit()</code> invocation on this listener.
 * </ul>
 *
 * <p>
 *  <code> DragSourceListener </code>定义拖放操作的发起者的事件接口,以跟踪用户手势的状态,并提供适当的"拖动"在整个拖放操作中向用户反馈。
 * <p>
 *  如果在此侦听器上最近调用<code> dragEnter()</code>,则拖放站点与之前的<code> dragEnter()</code>调用</i>
 * <ul>
 *  <li>对应于该放置网站,<li>后面没有对此侦听器执行<code> dragExit()</code>调用。
 * </ul>
 * 
 * 
 * @since 1.2
 */

public interface DragSourceListener extends EventListener {

    /**
     * Called as the cursor's hotspot enters a platform-dependent drop site.
     * This method is invoked when all the following conditions are true:
     * <UL>
     * <LI>The cursor's hotspot enters the operable part of a platform-
     * dependent drop site.
     * <LI>The drop site is active.
     * <LI>The drop site accepts the drag.
     * </UL>
     *
     * <p>
     *  称为光标的热点进入平台相关的下载站点。当所有以下条件为真时调用此方法：
     * <UL>
     *  <LI>光标的热点进入平台相关的丢弃点的可操作部分。 <LI>放置站点处于活动状态。 <LI>放置站点接受拖动。
     * </UL>
     * 
     * 
     * @param dsde the <code>DragSourceDragEvent</code>
     */
    void dragEnter(DragSourceDragEvent dsde);

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
    void dragOver(DragSourceDragEvent dsde);

    /**
     * Called when the user has modified the drop gesture.
     * This method is invoked when the state of the input
     * device(s) that the user is interacting with changes.
     * Such devices are typically the mouse buttons or keyboard
     * modifiers that the user is interacting with.
     *
     * <p>
     * 当用户修改放置手势时调用。当用户正在与之交互的输入设备的状态改变时,调用该方法。这样的设备通常是用户正在与之交互的鼠标按钮或键盘修改器。
     * 
     * 
     * @param dsde the <code>DragSourceDragEvent</code>
     */
    void dropActionChanged(DragSourceDragEvent dsde);

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
    void dragExit(DragSourceEvent dse);

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
    void dragDropEnd(DragSourceDropEvent dsde);
}
