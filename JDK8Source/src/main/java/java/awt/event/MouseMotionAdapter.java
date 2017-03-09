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
 * An abstract adapter class for receiving mouse motion events.
 * The methods in this class are empty. This class exists as
 * convenience for creating listener objects.
 * <P>
 * Mouse motion events occur when a mouse is moved or dragged.
 * (Many such events will be generated in a normal program.
 * To track clicks and other mouse events, use the MouseAdapter.)
 * <P>
 * Extend this class to create a <code>MouseEvent</code> listener
 * and override the methods for the events of interest. (If you implement the
 * <code>MouseMotionListener</code> interface, you have to define all of
 * the methods in it. This abstract class defines null methods for them
 * all, so you can only have to define methods for events you care about.)
 * <P>
 * Create a listener object using the extended class and then register it with
 * a component using the component's <code>addMouseMotionListener</code>
 * method. When the mouse is moved or dragged, the relevant method in the
 * listener object is invoked and the <code>MouseEvent</code> is passed to it.
 *
 * <p>
 *  用于接收鼠标运动事件的抽象适配器类。此类中的方法为空。此类存在为方便创建侦听器对象。
 * <P>
 *  鼠标移动或拖动时发生鼠标移动事件。 (许多这样的事件将在正常程序中生成。要跟踪点击和其他鼠标事件,请使用MouseAdapter。)
 * <P>
 *  扩展这个类来创建一个<code> MouseEvent </code>监听器并覆盖感兴趣的事件的方法。
 *  (如果你实现<code> MouseMotionListener </code>接口,你必须定义所有的方法。这个抽象类为它们定义了所有的方法,所以你只需要为你关心的事件定义方法。 )。
 * <P>
 *  使用扩展类创建一个侦听器对象,然后使用组件的<code> addMouseMotionListener </code>方法将其注册到组件。
 * 
 * @author Amy Fowler
 *
 * @see MouseEvent
 * @see MouseMotionListener
 * @see <a href="https://docs.oracle.com/javase/tutorial/uiswing/events/mousemotionlistener.html">Tutorial: Writing a Mouse Motion Listener</a>
 *
 * @since 1.1
 */
public abstract class MouseMotionAdapter implements MouseMotionListener {
    /**
     * Invoked when a mouse button is pressed on a component and then
     * dragged.  Mouse drag events will continue to be delivered to
     * the component where the first originated until the mouse button is
     * released (regardless of whether the mouse position is within the
     * bounds of the component).
     * <p>
     * 当鼠标被移动或拖动时,监听器对象中的相关方法被调用,并且<code> MouseEvent </code>被传递给它。
     * 
     */
    public void mouseDragged(MouseEvent e) {}

    /**
     * Invoked when the mouse button has been moved on a component
     * (with no buttons no down).
     * <p>
     *  在组件上按下鼠标按钮然后拖动时调用。鼠标拖动事件将继续传递到第一个发起的组件,直到鼠标按钮释放(不管鼠标位置是否在组件的边界内)。
     * 
     */
    public void mouseMoved(MouseEvent e) {}
}
