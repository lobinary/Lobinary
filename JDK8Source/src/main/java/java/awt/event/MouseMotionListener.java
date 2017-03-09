/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.awt.event;

import java.util.EventListener;

/**
 * The listener interface for receiving mouse motion events on a component.
 * (For clicks and other mouse events, use the <code>MouseListener</code>.)
 * <P>
 * The class that is interested in processing a mouse motion event
 * either implements this interface (and all the methods it
 * contains) or extends the abstract <code>MouseMotionAdapter</code> class
 * (overriding only the methods of interest).
 * <P>
 * The listener object created from that class is then registered with a
 * component using the component's <code>addMouseMotionListener</code>
 * method. A mouse motion event is generated when the mouse is moved
 * or dragged. (Many such events will be generated). When a mouse motion event
 * occurs, the relevant method in the listener object is invoked, and
 * the <code>MouseEvent</code> is passed to it.
 *
 * <p>
 *  用于在组件上接收鼠标运动事件的侦听器接口。 (对于点击和其他鼠标事件,请使用<code> MouseListener </code>。)
 * <P>
 *  有兴趣处理鼠标运动事件的类实现这个接口(和它包含的所有方法)或扩展抽象<code> MouseMotionAdapter </code>类(只覆盖感兴趣的方法)。
 * <P>
 *  然后使用组件的<code> addMouseMotionListener </code>方法向该组件注册从该类创建的侦听器对象。鼠标移动或拖动时会产生鼠标移动事件。 (将生成许多此类事件)。
 * 当发生鼠标运动事件时,调用侦听器对象中的相关方法,并将<code> MouseEvent </code>传递给它。
 * 
 * 
 * @author Amy Fowler
 *
 * @see MouseMotionAdapter
 * @see MouseEvent
 * @see <a href="https://docs.oracle.com/javase/tutorial/uiswing/events/mousemotionlistener.html">Tutorial: Writing a Mouse Motion Listener</a>
 *
 * @since 1.1
 */
public interface MouseMotionListener extends EventListener {

    /**
     * Invoked when a mouse button is pressed on a component and then
     * dragged.  <code>MOUSE_DRAGGED</code> events will continue to be
     * delivered to the component where the drag originated until the
     * mouse button is released (regardless of whether the mouse position
     * is within the bounds of the component).
     * <p>
     * Due to platform-dependent Drag&amp;Drop implementations,
     * <code>MOUSE_DRAGGED</code> events may not be delivered during a native
     * Drag&amp;Drop operation.
     * <p>
     *  在组件上按下鼠标按钮然后拖动时调用。 <code> MOUSE_DRAGGED </code>事件将继续传递到发生拖动的组件,直到鼠标按钮释放(无论鼠标位置是否在组件的边界内)。
     * <p>
     *  由于平台相关的Drag&amp; Drop实现,可能不会在本地Drag&amp; Drop操作期间传递<code> MOUSE_DRAGGED </code>事件。
     * 
     */
    public void mouseDragged(MouseEvent e);

    /**
     * Invoked when the mouse cursor has been moved onto a component
     * but no buttons have been pushed.
     * <p>
     */
    public void mouseMoved(MouseEvent e);

}
