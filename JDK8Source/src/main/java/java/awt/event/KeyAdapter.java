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
 * An abstract adapter class for receiving keyboard events.
 * The methods in this class are empty. This class exists as
 * convenience for creating listener objects.
 * <P>
 * Extend this class to create a <code>KeyEvent</code> listener
 * and override the methods for the events of interest. (If you implement the
 * <code>KeyListener</code> interface, you have to define all of
 * the methods in it. This abstract class defines null methods for them
 * all, so you can only have to define methods for events you care about.)
 * <P>
 * Create a listener object using the extended class and then register it with
 * a component using the component's <code>addKeyListener</code>
 * method. When a key is pressed, released, or typed,
 * the relevant method in the listener object is invoked,
 * and the <code>KeyEvent</code> is passed to it.
 *
 * <p>
 *  用于接收键盘事件的抽象适配器类。此类中的方法为空。此类存在为方便创建侦听器对象。
 * <P>
 *  扩展这个类来创建一个<code> KeyEvent </code>监听器,并覆盖感兴趣的事件的方法。 (如果你实现<code> KeyListener </code>接口,你必须定义所有的方法。
 * 这个抽象类为它们定义了所有的方法,所以你只需要为你关心的事件定义方法。 )。
 * <P>
 *  使用扩展类创建一个侦听器对象,然后使用组件的<code> addKeyListener </code>方法将其注册到组件。
 * 当按下,释放或键入一个键时,将调用侦听器对象中的相关方法,并将<code> KeyEvent </code>传递给它。
 * 
 * 
 * @author Carl Quinn
 *
 * @see KeyEvent
 * @see KeyListener
 * @see <a href="https://docs.oracle.com/javase/tutorial/uiswing/events/keylistener.html">Tutorial: Writing a Key Listener</a>
 *
 * @since 1.1
 */
public abstract class KeyAdapter implements KeyListener {
    /**
     * Invoked when a key has been typed.
     * This event occurs when a key press is followed by a key release.
     * <p>
     *  在键入键时调用。当按键后按键释放时,会发生此事件。
     * 
     */
    public void keyTyped(KeyEvent e) {}

    /**
     * Invoked when a key has been pressed.
     * <p>
     *  在按下键时调用。
     * 
     */
    public void keyPressed(KeyEvent e) {}

    /**
     * Invoked when a key has been released.
     * <p>
     *  在释放键时调用。
     */
    public void keyReleased(KeyEvent e) {}
}
