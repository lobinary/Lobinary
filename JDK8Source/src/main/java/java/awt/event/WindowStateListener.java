/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, Oracle and/or its affiliates. All rights reserved.
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
 * The listener interface for receiving window state events.
 * <p>
 * The class that is interested in processing a window state event
 * either implements this interface (and all the methods it contains)
 * or extends the abstract <code>WindowAdapter</code> class
 * (overriding only the methods of interest).
 * <p>
 * The listener object created from that class is then registered with
 * a window using the <code>Window</code>'s
 * <code>addWindowStateListener</code> method.  When the window's
 * state changes by virtue of being iconified, maximized etc., the
 * <code>windowStateChanged</code> method in the listener object is
 * invoked, and the <code>WindowEvent</code> is passed to it.
 *
 * <p>
 *  用于接收窗口状态事件的侦听器接口。
 * <p>
 *  对处理窗口状态事件感兴趣的类实现这个接口(和它包含的所有方法)或扩展抽象<code> WindowAdapter </code>类(只覆盖感兴趣的方法)。
 * <p>
 *  然后使用<code> Window </code>的<code> addWindowStateListener </code>方法向该窗口注册从该类创建的侦听器对象。
 * 当窗口的状态由于被图标化,最大化等而改变时,调用监听器对象中的<code> windowStateChanged </code>方法,并且将<code> WindowEvent </code>传递给它。
 * 
 * @see java.awt.event.WindowAdapter
 * @see java.awt.event.WindowEvent
 *
 * @since 1.4
 */
public interface WindowStateListener extends EventListener {
    /**
     * Invoked when window state is changed.
     * <p>
     *  然后使用<code> Window </code>的<code> addWindowStateListener </code>方法向该窗口注册从该类创建的侦听器对象。
     * 
     */
    public void windowStateChanged(WindowEvent e);
}
