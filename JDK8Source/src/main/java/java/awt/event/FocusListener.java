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
 * The listener interface for receiving keyboard focus events on
 * a component.
 * The class that is interested in processing a focus event
 * either implements this interface (and all the methods it
 * contains) or extends the abstract <code>FocusAdapter</code> class
 * (overriding only the methods of interest).
 * The listener object created from that class is then registered with a
 * component using the component's <code>addFocusListener</code>
 * method. When the component gains or loses the keyboard focus,
 * the relevant method in the listener object
 * is invoked, and the <code>FocusEvent</code> is passed to it.
 *
 * <p>
 *  用于在组件上接收键盘焦点事件的侦听器接口。对处理焦点事件感兴趣的类实现这个接口(和它包含的所有方法)或扩展抽象<code> FocusAdapter </code>类(只覆盖感兴趣的方法)。
 * 然后使用该组件的<code> addFocusListener </code>方法向该组件注册从该类创建的侦听器对象。
 * 当组件获得或失去键盘焦点时,将调用侦听器对象中的相关方法,并将<code> FocusEvent </code>传递给它。
 * 
 * 
 * @see FocusAdapter
 * @see FocusEvent
 * @see <a href="https://docs.oracle.com/javase/tutorial/uiswing/events/focuslistener.html">Tutorial: Writing a Focus Listener</a>
 *
 * @author Carl Quinn
 * @since 1.1
 */
public interface FocusListener extends EventListener {

    /**
     * Invoked when a component gains the keyboard focus.
     * <p>
     *  当组件获得键盘焦点时调用。
     * 
     */
    public void focusGained(FocusEvent e);

    /**
     * Invoked when a component loses the keyboard focus.
     * <p>
     *  当组件丢失键盘焦点时调用。
     */
    public void focusLost(FocusEvent e);
}
