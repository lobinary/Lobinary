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
 * The listener interface for receiving hierarchy changed events.
 * The class that is interested in processing a hierarchy changed event
 * should implement this interface.
 * The listener object created from that class is then registered with a
 * Component using the Component's <code>addHierarchyListener</code>
 * method. When the hierarchy to which the Component belongs changes, the
 * <code>hierarchyChanged</code> method in the listener object is invoked,
 * and the <code>HierarchyEvent</code> is passed to it.
 * <p>
 * Hierarchy events are provided for notification purposes ONLY;
 * The AWT will automatically handle changes to the hierarchy internally so
 * that GUI layout, displayability, and visibility work properly regardless
 * of whether a program registers a <code>HierarchyListener</code> or not.
 *
 * <p>
 *  用于接收层次结构已更改事件的侦听器接口。对处理层次结构改变事件感兴趣的类应该实现这个接口。
 * 然后使用组件的<code> addHierarchyListener </code>方法向该组件注册从该类创建的侦听器对象。
 * 当组件所属的层次结构发生改变时,将调用侦听器对象中的<code> hierarchicalChanged </code>方法,并将<code> HierarchyEvent </code>传递给它。
 * <p>
 *  层次结构事件仅用于通知目的; AWT将自动处理内部对层次结构的更改,以便GUI布局,可显示性和可见性正常工作,而不管程序是否注册<code> HierarchyListener </code>。
 * 
 * @author      David Mendenhall
 * @see         HierarchyEvent
 * @since       1.3
 */
public interface HierarchyListener extends EventListener {
    /**
     * Called when the hierarchy has been changed. To discern the actual
     * type of change, call <code>HierarchyEvent.getChangeFlags()</code>.
     *
     * <p>
     * 
     * 
     * @see HierarchyEvent#getChangeFlags()
     */
    public void hierarchyChanged(HierarchyEvent e);
}
