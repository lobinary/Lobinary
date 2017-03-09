/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, Oracle and/or its affiliates. All rights reserved.
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
 * An abstract adapter class for receiving ancestor moved and resized events.
 * The methods in this class are empty. This class exists as a
 * convenience for creating listener objects.
 * <p>
 * Extend this class and override the method for the event of interest. (If
 * you implement the <code>HierarchyBoundsListener</code> interface, you have
 * to define both methods in it. This abstract class defines null methods for
 * them both, so you only have to define the method for the event you care
 * about.)
 * <p>
 * Create a listener object using your class and then register it with a
 * Component using the Component's <code>addHierarchyBoundsListener</code>
 * method. When the hierarchy to which the Component belongs changes by
 * resize or movement of an ancestor, the relevant method in the listener
 * object is invoked, and the <code>HierarchyEvent</code> is passed to it.
 *
 * <p>
 *  用于接收祖先移动和调整大小的事件的抽象适配器类。此类中的方法为空。此类存在为方便创建侦听器对象。
 * <p>
 *  扩展此类并覆盖感兴趣的事件的方法。
 *  (如果你实现<code> HierarchyBoundsListener </code>接口,你必须定义两个方法,这个抽象类为它们定义了空方法,所以你只需要为你所关心的事件定义方法。)。
 * <p>
 *  使用您的类创建一个监听器对象,然后使用Component的<code> addHierarchyBoundsListener </code>方法向组件注册它。
 * 当组件所属的层次结构通过调整祖先大小或移动而改变时,将调用侦听器对象中的相关方法,并将<code> HierarchyEvent </code>传递给它。
 * 
 * @author      David Mendenhall
 * @see         HierarchyBoundsListener
 * @see         HierarchyEvent
 * @since       1.3
 */
public abstract class HierarchyBoundsAdapter implements HierarchyBoundsListener
{
    /**
     * Called when an ancestor of the source is moved.
     * <p>
     * 
     */
    public void ancestorMoved(HierarchyEvent e) {}

    /**
     * Called when an ancestor of the source is resized.
     * <p>
     *  当源的祖先被移动时调用。
     * 
     */
    public void ancestorResized(HierarchyEvent e) {}
}
