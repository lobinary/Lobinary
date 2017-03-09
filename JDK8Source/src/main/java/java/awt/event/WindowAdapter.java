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

/**
 * An abstract adapter class for receiving window events.
 * The methods in this class are empty. This class exists as
 * convenience for creating listener objects.
 * <P>
 * Extend this class to create a <code>WindowEvent</code> listener
 * and override the methods for the events of interest. (If you implement the
 * <code>WindowListener</code> interface, you have to define all of
 * the methods in it. This abstract class defines null methods for them
 * all, so you can only have to define methods for events you care about.)
 * <P>
 * Create a listener object using the extended class and then register it with
 * a Window using the window's <code>addWindowListener</code>
 * method. When the window's status changes by virtue of being opened,
 * closed, activated or deactivated, iconified or deiconified,
 * the relevant method in the listener
 * object is invoked, and the <code>WindowEvent</code> is passed to it.
 *
 * <p>
 *  用于接收窗口事件的抽象适配器类。此类中的方法为空。此类存在为方便创建侦听器对象。
 * <P>
 *  扩展这个类来创建一个<code> WindowEvent </code>监听器,并覆盖感兴趣的事件的方法。
 *  (如果你实现<code> WindowListener </code>接口,你必须定义它的所有方法。这个抽象类为它们定义了空方法,所以你只需要为你所关心的事件定义方法。 )。
 * <P>
 *  使用扩展类创建一个监听器对象,然后使用窗口的<code> addWindowListener </code>方法向窗口注册它。
 * 当窗口的状态由于被打开,关闭,激活或去激活,被图标化或去模糊化而改变时,调用监听器对象中的相关方法,并且将<code> WindowEvent </code>传递给它。
 * 
 * 
 * @see WindowEvent
 * @see WindowListener
 * @see <a href="https://docs.oracle.com/javase/tutorial/uiswing/events/windowlistener.html">Tutorial: Writing a Window Listener</a>
 *
 * @author Carl Quinn
 * @author Amy Fowler
 * @author David Mendenhall
 * @since 1.1
 */
public abstract class WindowAdapter
    implements WindowListener, WindowStateListener, WindowFocusListener
{
    /**
     * Invoked when a window has been opened.
     * <p>
     *  在打开窗口时调用。
     * 
     */
    public void windowOpened(WindowEvent e) {}

    /**
     * Invoked when a window is in the process of being closed.
     * The close operation can be overridden at this point.
     * <p>
     *  当窗口处于关闭过程中时调用。此时可以覆盖关闭操作。
     * 
     */
    public void windowClosing(WindowEvent e) {}

    /**
     * Invoked when a window has been closed.
     * <p>
     *  在窗口关闭时调用。
     * 
     */
    public void windowClosed(WindowEvent e) {}

    /**
     * Invoked when a window is iconified.
     * <p>
     *  在窗口被图标化时调用。
     * 
     */
    public void windowIconified(WindowEvent e) {}

    /**
     * Invoked when a window is de-iconified.
     * <p>
     *  在窗口取消图标化时调用。
     * 
     */
    public void windowDeiconified(WindowEvent e) {}

    /**
     * Invoked when a window is activated.
     * <p>
     *  激活窗口时调用。
     * 
     */
    public void windowActivated(WindowEvent e) {}

    /**
     * Invoked when a window is de-activated.
     * <p>
     *  在窗口被禁用时调用。
     * 
     */
    public void windowDeactivated(WindowEvent e) {}

    /**
     * Invoked when a window state is changed.
     * <p>
     *  当窗口状态更改时调用。
     * 
     * 
     * @since 1.4
     */
    public void windowStateChanged(WindowEvent e) {}

    /**
     * Invoked when the Window is set to be the focused Window, which means
     * that the Window, or one of its subcomponents, will receive keyboard
     * events.
     *
     * <p>
     * 当窗口设置为关注的窗口时调用,这意味着窗口或其子组件之一将接收键盘事件。
     * 
     * 
     * @since 1.4
     */
    public void windowGainedFocus(WindowEvent e) {}

    /**
     * Invoked when the Window is no longer the focused Window, which means
     * that keyboard events will no longer be delivered to the Window or any of
     * its subcomponents.
     *
     * <p>
     *  当窗口不再是焦点窗口时调用,这意味着键盘事件将不再传递到窗口或其任何子组件。
     * 
     * @since 1.4
     */
    public void windowLostFocus(WindowEvent e) {}
}
