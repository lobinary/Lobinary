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

import java.util.EventListener;

/**
 * The listener interface for receiving ancestor moved and resized events.
 * The class that is interested in processing these events either implements
 * this interface (and all the methods it contains) or extends the abstract
 * <code>HierarchyBoundsAdapter</code> class (overriding only the method of
 * interest).
 * The listener object created from that class is then registered with a
 * Component using the Component's <code>addHierarchyBoundsListener</code>
 * method. When the hierarchy to which the Component belongs changes by
 * the resizing or movement of an ancestor, the relevant method in the listener
 * object is invoked, and the <code>HierarchyEvent</code> is passed to it.
 * <p>
 * Hierarchy events are provided for notification purposes ONLY;
 * The AWT will automatically handle changes to the hierarchy internally so
 * that GUI layout works properly regardless of whether a
 * program registers an <code>HierarchyBoundsListener</code> or not.
 *
 * <p>
 *  用于接收祖先移动和调整大小的事件的侦听器接口。
 * 对处理这些事件感兴趣的类或者实现这个接口(和它包含的所有方法)或者扩展抽象<code> HierarchyBoundsAdapter </code>类(只覆盖感兴趣的方法)。
 * 然后使用组件的<code> addHierarchyBoundsListener </code>方法向该组件注册从该类创建的侦听器对象。
 * 当组件所属的层次结构通过调整祖先大小或移动而改变时,将调用侦听器对象中的相关方法,并将<code> HierarchyEvent </code>传递给它。
 * <p>
 *  层次结构事件仅用于通知目的; AWT将自动处理内部对层次结构的更改,以便GUI布局正常工作,而不管程序是否注册<code> HierarchyBoundsListener </code>。
 * 
 * 
 * @author      David Mendenhall
 * @see         HierarchyBoundsAdapter
 * @see         HierarchyEvent
 * @since       1.3
 */
public interface HierarchyBoundsListener extends EventListener {
    /**
     * Called when an ancestor of the source is moved.
     * <p>
     */
    public void ancestorMoved(HierarchyEvent e);

    /**
     * Called when an ancestor of the source is resized.
     * <p>
     *  当源的祖先被移动时调用。
     * 
     */
    public void ancestorResized(HierarchyEvent e);
}
