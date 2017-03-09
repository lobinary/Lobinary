/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2014, Oracle and/or its affiliates. All rights reserved.
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
 * The listener interface for receiving keyboard events (keystrokes).
 * The class that is interested in processing a keyboard event
 * either implements this interface (and all the methods it
 * contains) or extends the abstract <code>KeyAdapter</code> class
 * (overriding only the methods of interest).
 * <P>
 * The listener object created from that class is then registered with a
 * component using the component's <code>addKeyListener</code>
 * method. A keyboard event is generated when a key is pressed, released,
 * or typed. The relevant method in the listener
 * object is then invoked, and the <code>KeyEvent</code> is passed to it.
 *
 * <p>
 *  用于接收键盘事件(击键)的侦听器接口。有兴趣处理键盘事件的类实现这个接口(和它包含的所有方法)或扩展抽象<code> KeyAdapter </code>类(只覆盖感兴趣的方法)。
 * <P>
 *  然后使用组件的<code> addKeyListener </code>方法将从该类创建的侦听器对象注册到组件。按键,释放或键入时会生成键盘事件。
 * 然后调用侦听器对象中的相关方法,并将<code> KeyEvent </code>传递给它。
 * 
 * 
 * @author Carl Quinn
 *
 * @see KeyAdapter
 * @see KeyEvent
 * @see <a href="https://docs.oracle.com/javase/tutorial/uiswing/events/keylistener.html">Tutorial: Writing a Key Listener</a>
 *
 * @since 1.1
 */
public interface KeyListener extends EventListener {

    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     * <p>
     *  在键入键时调用。有关键入类型事件的定义,请参阅{@link KeyEvent}的类描述。
     * 
     */
    public void keyTyped(KeyEvent e);

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     * <p>
     *  在按下键时调用。有关键按下事件的定义,请参阅{@link KeyEvent}的类描述。
     * 
     */
    public void keyPressed(KeyEvent e);

    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of
     * a key released event.
     * <p>
     *  在释放键时调用。有关键释放事件的定义,请参阅{@link KeyEvent}的类描述。
     */
    public void keyReleased(KeyEvent e);
}
