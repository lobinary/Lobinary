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
 * The listener interface for receiving window events.
 * The class that is interested in processing a window event
 * either implements this interface (and all the methods it
 * contains) or extends the abstract <code>WindowAdapter</code> class
 * (overriding only the methods of interest).
 * The listener object created from that class is then registered with a
 * Window using the window's <code>addWindowListener</code>
 * method. When the window's status changes by virtue of being opened,
 * closed, activated or deactivated, iconified or deiconified,
 * the relevant method in the listener object is invoked, and the
 * <code>WindowEvent</code> is passed to it.
 *
 * <p>
 *  用于接收窗口事件的侦听器接口。有兴趣处理窗口事件的类实现这个接口(和它包含的所有方法)或扩展抽象<code> WindowAdapter </code>类(只覆盖感兴趣的方法)。
 * 然后使用窗口的<code> addWindowListener </code>方法将从该类创建的侦听器对象注册到一个窗口。
 * 当窗口的状态由于被打开,关闭,激活或去激活,被图标化或去模糊化而改变时,调用监听器对象中的相关方法,并且将<code> WindowEvent </code>传递给它。
 * 
 * 
 * @author Carl Quinn
 *
 * @see WindowAdapter
 * @see WindowEvent
 * @see <a href="https://docs.oracle.com/javase/tutorial/uiswing/events/windowlistener.html">Tutorial: How to Write Window Listeners</a>
 *
 * @since 1.1
 */
public interface WindowListener extends EventListener {
    /**
     * Invoked the first time a window is made visible.
     * <p>
     *  第一次调用窗口可见。
     * 
     */
    public void windowOpened(WindowEvent e);

    /**
     * Invoked when the user attempts to close the window
     * from the window's system menu.
     * <p>
     *  当用户尝试从窗口的系统菜单关闭窗口时调用。
     * 
     */
    public void windowClosing(WindowEvent e);

    /**
     * Invoked when a window has been closed as the result
     * of calling dispose on the window.
     * <p>
     *  由于在窗口上调用dispose而关闭窗口时调用。
     * 
     */
    public void windowClosed(WindowEvent e);

    /**
     * Invoked when a window is changed from a normal to a
     * minimized state. For many platforms, a minimized window
     * is displayed as the icon specified in the window's
     * iconImage property.
     * <p>
     *  当窗口从正常更改为最小化状态时调用。对于许多平台,最小化的窗口显示为在窗口的iconImage属性中指定的图标。
     * 
     * 
     * @see java.awt.Frame#setIconImage
     */
    public void windowIconified(WindowEvent e);

    /**
     * Invoked when a window is changed from a minimized
     * to a normal state.
     * <p>
     *  当窗口从最小化更改为正常状态时调用。
     * 
     */
    public void windowDeiconified(WindowEvent e);

    /**
     * Invoked when the Window is set to be the active Window. Only a Frame or
     * a Dialog can be the active Window. The native windowing system may
     * denote the active Window or its children with special decorations, such
     * as a highlighted title bar. The active Window is always either the
     * focused Window, or the first Frame or Dialog that is an owner of the
     * focused Window.
     * <p>
     * 当窗口设置为活动窗口时调用。只有框架或对话框可以是活动窗口。本地加窗系统可以表示活动窗口或其具有特殊装饰的子项,例如突出显示的标题栏。
     * 活动窗口始终是关注的窗口,或者是焦点窗口的所有者的第一个框架或对话框。
     * 
     */
    public void windowActivated(WindowEvent e);

    /**
     * Invoked when a Window is no longer the active Window. Only a Frame or a
     * Dialog can be the active Window. The native windowing system may denote
     * the active Window or its children with special decorations, such as a
     * highlighted title bar. The active Window is always either the focused
     * Window, or the first Frame or Dialog that is an owner of the focused
     * Window.
     * <p>
     *  当窗口不再是活动窗口时调用。只有框架或对话框可以是活动窗口。本地加窗系统可以表示活动窗口或其具有特殊装饰的子项,例如突出显示的标题栏。
     * 活动窗口始终是关注的窗口,或者是焦点窗口的所有者的第一个框架或对话框。
     */
    public void windowDeactivated(WindowEvent e);
}
