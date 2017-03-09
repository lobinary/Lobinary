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
 * The listener interface for receiving "interesting" mouse events
 * (press, release, click, enter, and exit) on a component.
 * (To track mouse moves and mouse drags, use the
 * <code>MouseMotionListener</code>.)
 * <P>
 * The class that is interested in processing a mouse event
 * either implements this interface (and all the methods it
 * contains) or extends the abstract <code>MouseAdapter</code> class
 * (overriding only the methods of interest).
 * <P>
 * The listener object created from that class is then registered with a
 * component using the component's <code>addMouseListener</code>
 * method. A mouse event is generated when the mouse is pressed, released
 * clicked (pressed and released). A mouse event is also generated when
 * the mouse cursor enters or leaves a component. When a mouse event
 * occurs, the relevant method in the listener object is invoked, and
 * the <code>MouseEvent</code> is passed to it.
 *
 * <p>
 *  用于在组件上接收"有趣"鼠标事件(按,释放,单击,进入和退出)的侦听器接口。 (要跟踪鼠标移动和鼠标拖动,请使用<code> MouseMotionListener </code>。)
 * <P>
 *  有兴趣处理鼠标事件的类实现这个接口(和它包含的所有方法)或扩展抽象<code> MouseAdapter </code>类(只覆盖感兴趣的方法)。
 * <P>
 *  然后使用该组件的<code> addMouseListener </code>方法向该组件注册从该类创建的侦听器对象。当鼠标被按下时,鼠标事件被产生,释放被点击(按下并释放)。
 * 当鼠标光标进入或离开组件时,也会生成鼠标事件。当鼠标事件发生时,调用侦听器对象中的相关方法,并将<code> MouseEvent </code>传递给它。
 * 
 * 
 * @author Carl Quinn
 *
 * @see MouseAdapter
 * @see MouseEvent
 * @see <a href="https://docs.oracle.com/javase/tutorial/uiswing/events/mouselistener.html">Tutorial: Writing a Mouse Listener</a>
 *
 * @since 1.1
 */
public interface MouseListener extends EventListener {

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     * <p>
     *  在组件上单击(按下并释放)鼠标按钮时调用。
     * 
     */
    public void mouseClicked(MouseEvent e);

    /**
     * Invoked when a mouse button has been pressed on a component.
     * <p>
     *  在组件上按下鼠标按钮时调用。
     * 
     */
    public void mousePressed(MouseEvent e);

    /**
     * Invoked when a mouse button has been released on a component.
     * <p>
     *  在组件上释放鼠标按钮时调用。
     * 
     */
    public void mouseReleased(MouseEvent e);

    /**
     * Invoked when the mouse enters a component.
     * <p>
     *  当鼠标进入组件时调用。
     * 
     */
    public void mouseEntered(MouseEvent e);

    /**
     * Invoked when the mouse exits a component.
     * <p>
     *  在鼠标退出组件时调用。
     */
    public void mouseExited(MouseEvent e);
}
