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
 * An abstract adapter class for receiving keyboard focus events.
 * The methods in this class are empty. This class exists as
 * convenience for creating listener objects.
 * <P>
 * Extend this class to create a <code>FocusEvent</code> listener
 * and override the methods for the events of interest. (If you implement the
 * <code>FocusListener</code> interface, you have to define all of
 * the methods in it. This abstract class defines null methods for them
 * all, so you can only have to define methods for events you care about.)
 * <P>
 * Create a listener object using the extended class and then register it with
 * a component using the component's <code>addFocusListener</code>
 * method. When the component gains or loses the keyboard focus,
 * the relevant method in the listener object is invoked,
 * and the <code>FocusEvent</code> is passed to it.
 *
 * <p>
 *  用于接收键盘焦点事件的抽象适配器类。此类中的方法为空。此类存在为方便创建侦听器对象。
 * <P>
 *  扩展此类以创建一个<code> FocusEvent </code>侦听器并覆盖所关注事件的方法。 (如果你实现<code> FocusListener </code>接口,你必须定义所有的方法。
 * 这个抽象类为它们定义了null方法,所以你只需要为你关心的事件定义方法。 )。
 * <P>
 *  使用扩展类创建一个侦听器对象,然后使用组件的<code> addFocusListener </code>方法将其注册到组件。
 * 当组件获得或失去键盘焦点时,将调用侦听器对象中的相关方法,并将<code> FocusEvent </code>传递给它。
 * 
 * @see FocusEvent
 * @see FocusListener
 * @see <a href="https://docs.oracle.com/javase/tutorial/uiswing/events/focuslistener.html">Tutorial: Writing a Focus Listener</a>
 *
 * @author Carl Quinn
 * @since 1.1
 */
public abstract class FocusAdapter implements FocusListener {
    /**
     * Invoked when a component gains the keyboard focus.
     * <p>
     * 
     */
    public void focusGained(FocusEvent e) {}

    /**
     * Invoked when a component loses the keyboard focus.
     * <p>
     *  当组件获得键盘焦点时调用。
     * 
     */
    public void focusLost(FocusEvent e) {}
}
