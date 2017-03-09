/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * The listener interface for receiving <code>WindowEvents</code>, including
 * <code>WINDOW_GAINED_FOCUS</code> and <code>WINDOW_LOST_FOCUS</code> events.
 * The class that is interested in processing a <code>WindowEvent</code>
 * either implements this interface (and
 * all the methods it contains) or extends the abstract
 * <code>WindowAdapter</code> class (overriding only the methods of interest).
 * The listener object created from that class is then registered with a
 * <code>Window</code>
 * using the <code>Window</code>'s <code>addWindowFocusListener</code> method.
 * When the <code>Window</code>'s
 * status changes by virtue of it being opened, closed, activated, deactivated,
 * iconified, or deiconified, or by focus being transfered into or out of the
 * <code>Window</code>, the relevant method in the listener object is invoked,
 * and the <code>WindowEvent</code> is passed to it.
 *
 * <p>
 *  用于接收<code> WindowEvents </code>的侦听器接口,包括<code> WINDOW_GAINED_FOCUS </code>和<code> WINDOW_LOST_FOCUS 
 * </code>事件。
 * 对处理<code> WindowEvent </code>感兴趣的类实现这个接口(和它包含的所有方法)或扩展抽象<code> WindowAdapter </code>类(只覆盖感兴趣的方法)。
 * 然后使用<code> Window </code>的<code> addWindowFocusListener </code>方法将从该类创建的侦听器对象注册到<code> Window </code>
 * 当<code> Window </code>的状态由于被打开,关闭,激活,去激活,图标化或者去着色,或者通过焦点转移到或者移出<code> Window </code> ,则调用侦听器对象中的相关方法,
 * 并将<code> WindowEvent </code>传递给它。
 * 对处理<code> WindowEvent </code>感兴趣的类实现这个接口(和它包含的所有方法)或扩展抽象<code> WindowAdapter </code>类(只覆盖感兴趣的方法)。
 * 
 * @author David Mendenhall
 *
 * @see WindowAdapter
 * @see WindowEvent
 * @see <a href="https://docs.oracle.com/javase/tutorial/uiswing/events/windowlistener.html">Tutorial: Writing a Window Listener</a>
 *
 * @since 1.4
 */
public interface WindowFocusListener extends EventListener {

    /**
     * Invoked when the Window is set to be the focused Window, which means
     * that the Window, or one of its subcomponents, will receive keyboard
     * events.
     * <p>
     * 
     */
    public void windowGainedFocus(WindowEvent e);

    /**
     * Invoked when the Window is no longer the focused Window, which means
     * that keyboard events will no longer be delivered to the Window or any of
     * its subcomponents.
     * <p>
     *  当窗口设置为关注的窗口时调用,这意味着窗口或其子组件之一将接收键盘事件。
     * 
     */
    public void windowLostFocus(WindowEvent e);
}
