/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2003, Oracle and/or its affiliates. All rights reserved.
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
 * The listener interface for receiving mouse wheel events on a component.
 * (For clicks and other mouse events, use the <code>MouseListener</code>.
 * For mouse movement and drags, use the <code>MouseMotionListener</code>.)
 * <P>
 * The class that is interested in processing a mouse wheel event
 * implements this interface (and all the methods it contains).
 * <P>
 * The listener object created from that class is then registered with a
 * component using the component's <code>addMouseWheelListener</code>
 * method. A mouse wheel event is generated when the mouse wheel is rotated.
 * When a mouse wheel event occurs, that object's <code>mouseWheelMoved</code>
 * method is invoked.
 * <p>
 * For information on how mouse wheel events are dispatched, see
 * the class description for {@link MouseWheelEvent}.
 *
 * <p>
 *  用于在组件上接收鼠标滚轮事件的侦听器接口。 (对于点击和其他鼠标事件,使用<code> MouseListener </code>。
 * 对于鼠标移动和拖动,使用<code> MouseMotionListener </code>。
 * <P>
 *  有兴趣处理鼠标滚轮事件的类实现此接口(及其包含的所有方法)。
 * <P>
 *  然后使用组件的<code> addMouseWheelListener </code>方法向该组件注册从该类创建的侦听器对象。鼠标滚轮事件在鼠标滚轮旋转时生成。
 * 当鼠标滚轮事件发生时,调用该对象的<code> mouseWheelMoved </code>方法。
 * 
 * @author Brent Christian
 * @see MouseWheelEvent
 * @since 1.4
 */
public interface MouseWheelListener extends EventListener {

    /**
     * Invoked when the mouse wheel is rotated.
     * <p>
     * <p>
     *  有关如何调度鼠标滚轮事件的信息,请参阅{@link MouseWheelEvent}的类描述。
     * 
     * 
     * @see MouseWheelEvent
     */
    public void mouseWheelMoved(MouseWheelEvent e);
}
