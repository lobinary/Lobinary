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
 * An abstract adapter class for receiving container events.
 * The methods in this class are empty. This class exists as
 * convenience for creating listener objects.
 * <P>
 * Extend this class to create a <code>ContainerEvent</code> listener
 * and override the methods for the events of interest. (If you implement the
 * <code>ContainerListener</code> interface, you have to define all of
 * the methods in it. This abstract class defines null methods for them
 * all, so you can only have to define methods for events you care about.)
 * <P>
 * Create a listener object using the extended class and then register it with
 * a component using the component's <code>addContainerListener</code>
 * method. When the container's contents change because a component has
 * been added or removed, the relevant method in the listener object is invoked,
 * and the <code>ContainerEvent</code> is passed to it.
 *
 * <p>
 *  用于接收容器事件的抽象适配器类。此类中的方法为空。此类存在为方便创建侦听器对象。
 * <P>
 *  扩展这个类来创建一个<code> ContainerEvent </code>监听器并覆盖感兴趣的事件的方法。
 *  (如果你实现<code> ContainerListener </code>接口,你必须定义所有的方法。这个抽象类为它们定义了null方法,所以你只需要为你关心的事件定义方法。 )。
 * <P>
 *  使用扩展类创建一个侦听器对象,然后使用组件的<code> addContainerListener </code>方法将其注册到组件。
 * 当容器的内容改变,因为已经添加或删除了一个组件时,监听器对象中的相关方法被调用,并且<code> ContainerEvent </code>被传递给它。
 * 
 * @see ContainerEvent
 * @see ContainerListener
 * @see <a href="https://docs.oracle.com/javase/tutorial/uiswing/events/containerlistener.html">Tutorial: Writing a Container Listener</a>
 *
 * @author Amy Fowler
 * @since 1.1
 */
public abstract class ContainerAdapter implements ContainerListener {
    /**
     * Invoked when a component has been added to the container.
     * <p>
     * 
     */
    public void componentAdded(ContainerEvent e) {}

    /**
     * Invoked when a component has been removed from the container.
     * <p>
     *  当组件已添加到容器时调用。
     * 
     */
    public void componentRemoved(ContainerEvent e) {}
}
