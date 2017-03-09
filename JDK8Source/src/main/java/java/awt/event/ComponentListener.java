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
 * The listener interface for receiving component events.
 * The class that is interested in processing a component event
 * either implements this interface (and all the methods it
 * contains) or extends the abstract <code>ComponentAdapter</code> class
 * (overriding only the methods of interest).
 * The listener object created from that class is then registered with a
 * component using the component's <code>addComponentListener</code>
 * method. When the component's size, location, or visibility
 * changes, the relevant method in the listener object is invoked,
 * and the <code>ComponentEvent</code> is passed to it.
 * <P>
 * Component events are provided for notification purposes ONLY;
 * The AWT will automatically handle component moves and resizes
 * internally so that GUI layout works properly regardless of
 * whether a program registers a <code>ComponentListener</code> or not.
 *
 * <p>
 *  用于接收组件事件的侦听器接口。对处理组件事件感兴趣的类实现这个接口(和它包含的所有方法)或扩展抽象<code> ComponentAdapter </code>类(只覆盖感兴趣的方法)。
 * 然后使用组件的<code> addComponentListener </code>方法将从该类创建的侦听器对象注册到组件。
 * 当组件的大小,位置或可见性更改时,将调用侦听器对象中的相关方法,并将<code> ComponentEvent </code>传递给它。
 * <P>
 *  组件事件仅用于通知目的; AWT将自动处理组件移动和内部调整大小,使GUI布局正常工作,而不管程序是否注册一个<code> ComponentListener </code>。
 * 
 * 
 * @see ComponentAdapter
 * @see ComponentEvent
 * @see <a href="https://docs.oracle.com/javase/tutorial/uiswing/events/componentlistener.html">Tutorial: Writing a Component Listener</a>
 *
 * @author Carl Quinn
 * @since 1.1
 */
public interface ComponentListener extends EventListener {
    /**
     * Invoked when the component's size changes.
     * <p>
     *  当组件的大小更改时调用。
     * 
     */
    public void componentResized(ComponentEvent e);

    /**
     * Invoked when the component's position changes.
     * <p>
     *  当组件的位置更改时调用。
     * 
     */
    public void componentMoved(ComponentEvent e);

    /**
     * Invoked when the component has been made visible.
     * <p>
     *  在组件可见时调用。
     * 
     */
    public void componentShown(ComponentEvent e);

    /**
     * Invoked when the component has been made invisible.
     * <p>
     *  当组件被隐藏时调用。
     */
    public void componentHidden(ComponentEvent e);
}
