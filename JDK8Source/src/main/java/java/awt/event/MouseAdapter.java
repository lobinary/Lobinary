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
 * An abstract adapter class for receiving mouse events.
 * The methods in this class are empty. This class exists as
 * convenience for creating listener objects.
 * <P>
 * Mouse events let you track when a mouse is pressed, released, clicked,
 * moved, dragged, when it enters a component, when it exits and
 * when a mouse wheel is moved.
 * <P>
 * Extend this class to create a {@code MouseEvent}
 * (including drag and motion events) or/and {@code MouseWheelEvent}
 * listener and override the methods for the events of interest. (If you implement the
 * {@code MouseListener},
 * {@code MouseMotionListener}
 * interface, you have to define all of
 * the methods in it. This abstract class defines null methods for them
 * all, so you can only have to define methods for events you care about.)
 * <P>
 * Create a listener object using the extended class and then register it with
 * a component using the component's {@code addMouseListener}
 * {@code addMouseMotionListener}, {@code addMouseWheelListener}
 * methods.
 * The relevant method in the listener object is invoked  and the {@code MouseEvent}
 * or {@code MouseWheelEvent}  is passed to it in following cases:
 * <ul>
 * <li>when a mouse button is pressed, released, or clicked (pressed and  released)
 * <li>when the mouse cursor enters or exits the component
 * <li>when the mouse wheel rotated, or mouse moved or dragged
 * </ul>
 *
 * <p>
 *  用于接收鼠标事件的抽象适配器类。此类中的方法为空。此类存在为方便创建侦听器对象。
 * <P>
 *  鼠标事件让您跟踪鼠标被按下,释放,点击,移动,拖动,进入组件,退出时和移动鼠标滚轮时。
 * <P>
 *  扩展此类以创建{@code MouseEvent}(包括拖动和运动事件)或/和{@code MouseWheelEvent}侦听器,并覆盖感兴趣的事件的方法。
 *  (如果你实现了{@code MouseListener},{@code MouseMotionListener}接口,你必须定义所有的方法,这个抽象类为它们定义了null方法,所以你只需要定义事件的方
 * 法关心。
 *  扩展此类以创建{@code MouseEvent}(包括拖动和运动事件)或/和{@code MouseWheelEvent}侦听器,并覆盖感兴趣的事件的方法。)。
 * <P>
 *  使用扩展类创建一个监听器对象,然后使用该组件的{@code addMouseListener} {@code addMouseMotionListener},{@code addMouseWheelListener}
 * 方法向组件注册它。
 * 调用侦听器对象中的相关方法,并在以下情况下传递{@code MouseEvent}或{@code MouseWheelEvent}：。
 * <ul>
 *  <li>当鼠标滚轮旋转或鼠标移动或拖动时,鼠标光标进入或退出组件<li>时,按下,释放或点击(按下并释放)鼠标按钮<li>
 * </ul>
 * 
 * 
 * @author Carl Quinn
 * @author Andrei Dmitriev
 *
 * @see MouseEvent
 * @see MouseWheelEvent
 * @see MouseListener
 * @see MouseMotionListener
 * @see MouseWheelListener
 * @see <a href="https://docs.oracle.com/javase/tutorial/uiswing/events/mouselistener.html">Tutorial: Writing a Mouse Listener</a>
 *
 * @since 1.1
 */
public abstract class MouseAdapter implements MouseListener, MouseWheelListener, MouseMotionListener {
    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public void mouseClicked(MouseEvent e) {}

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public void mousePressed(MouseEvent e) {}

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public void mouseReleased(MouseEvent e) {}

    /**
     * {@inheritDoc}
     * <p>
     * {@inheritDoc}
     * 
     */
    public void mouseEntered(MouseEvent e) {}

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public void mouseExited(MouseEvent e) {}

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @since 1.6
     */
    public void mouseWheelMoved(MouseWheelEvent e){}

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @since 1.6
     */
    public void mouseDragged(MouseEvent e){}

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * @since 1.6
     */
    public void mouseMoved(MouseEvent e){}
}
