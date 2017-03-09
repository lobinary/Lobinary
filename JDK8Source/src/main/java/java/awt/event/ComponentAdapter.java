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
 * An abstract adapter class for receiving component events.
 * The methods in this class are empty. This class exists as
 * convenience for creating listener objects.
 * <P>
 * Extend this class to create a <code>ComponentEvent</code> listener
 * and override the methods for the events of interest. (If you implement the
 * <code>ComponentListener</code> interface, you have to define all of
 * the methods in it. This abstract class defines null methods for them
 * all, so you can only have to define methods for events you care about.)
 * <P>
 * Create a listener object using your class and then register it with a
 * component using the component's <code>addComponentListener</code>
 * method. When the component's size, location, or visibility
 * changes, the relevant method in the listener object is invoked,
 * and the <code>ComponentEvent</code> is passed to it.
 *
 * <p>
 *  用于接收组件事件的抽象适配器类。此类中的方法为空。此类存在为方便创建侦听器对象。
 * <P>
 *  扩展此类以创建一个<code> ComponentEvent </code>侦听器并覆盖所关注事件的方法。
 *  (如果你实现<code> ComponentListener </code>接口,你必须定义所有的方法。这个抽象类为它们定义了null方法,所以你只需要为你关心的事件定义方法。 )。
 * <P>
 *  使用您的类创建一个监听器对象,然后使用组件的<code> addComponentListener </code>方法将其注册到组件。
 * 当组件的大小,位置或可见性更改时,将调用侦听器对象中的相关方法,并将<code> ComponentEvent </code>传递给它。
 * 
 * 
 * @see ComponentEvent
 * @see ComponentListener
 * @see <a href="https://docs.oracle.com/javase/tutorial/uiswing/events/componentlistener.html">Tutorial: Writing a Component Listener</a>
 *
 * @author Carl Quinn
 * @since 1.1
 */
public abstract class ComponentAdapter implements ComponentListener {
    /**
     * Invoked when the component's size changes.
     * <p>
     *  当组件的大小更改时调用。
     * 
     */
    public void componentResized(ComponentEvent e) {}

    /**
     * Invoked when the component's position changes.
     * <p>
     *  当组件的位置更改时调用。
     * 
     */
    public void componentMoved(ComponentEvent e) {}

    /**
     * Invoked when the component has been made visible.
     * <p>
     *  在组件可见时调用。
     * 
     */
    public void componentShown(ComponentEvent e) {}

    /**
     * Invoked when the component has been made invisible.
     * <p>
     *  当组件被隐藏时调用。
     */
    public void componentHidden(ComponentEvent e) {}
}
