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
 * The listener interface for receiving container events.
 * The class that is interested in processing a container event
 * either implements this interface (and all the methods it
 * contains) or extends the abstract <code>ContainerAdapter</code> class
 * (overriding only the methods of interest).
 * The listener object created from that class is then registered with a
 * component using the component's <code>addContainerListener</code>
 * method. When the container's contents change because a component
 * has been added or removed, the relevant method in the listener object
 * is invoked, and the <code>ContainerEvent</code> is passed to it.
 * <P>
 * Container events are provided for notification purposes ONLY;
 * The AWT will automatically handle add and remove operations
 * internally so the program works properly regardless of
 * whether the program registers a {@code ContainerListener} or not.
 *
 * <p>
 *  用于接收容器事件的侦听器接口。对处理容器事件感兴趣的类实现这个接口(和它包含的所有方法)或扩展抽象<code> ContainerAdapter </code>类(只覆盖感兴趣的方法)。
 * 然后使用组件的<code> addContainerListener </code>方法向该组件注册从该类创建的侦听器对象。
 * 当容器的内容更改,因为已经添加或删除了组件时,将调用侦听器对象中的相关方法,并将<code> ContainerEvent </code>传递给它。
 * <P>
 *  集装箱事件仅用于通知目的; AWT将在内部自动处理添加和删除操作,所以程序无论程序是否注册{@code ContainerListener}都能正常工作。
 * 
 * 
 * @see ContainerAdapter
 * @see ContainerEvent
 * @see <a href="https://docs.oracle.com/javase/tutorial/uiswing/events/containerlistener.html">Tutorial: Writing a Container Listener</a>
 *
 * @author Tim Prinzing
 * @author Amy Fowler
 * @since 1.1
 */
public interface ContainerListener extends EventListener {
    /**
     * Invoked when a component has been added to the container.
     * <p>
     */
    public void componentAdded(ContainerEvent e);

    /**
     * Invoked when a component has been removed from the container.
     * <p>
     *  当组件已添加到容器时调用。
     * 
     */
    public void componentRemoved(ContainerEvent e);

}
