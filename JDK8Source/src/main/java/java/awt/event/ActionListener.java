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
 * The listener interface for receiving action events.
 * The class that is interested in processing an action event
 * implements this interface, and the object created with that
 * class is registered with a component, using the component's
 * <code>addActionListener</code> method. When the action event
 * occurs, that object's <code>actionPerformed</code> method is
 * invoked.
 *
 * <p>
 *  用于接收操作事件的侦听器接口。对处理动作事件感兴趣的类实现这个接口,并且使用该组件的<code> addActionListener </code>方法向该组件注册使用该类创建的对象。
 * 当操作事件发生时,该对象的<code> actionPerformed </code>方法被调用。
 * 
 * 
 * @see ActionEvent
 * @see <a href="https://docs.oracle.com/javase/tutorial/uiswing/events/actionlistener.html">How to Write an Action Listener</a>
 *
 * @author Carl Quinn
 * @since 1.1
 */
public interface ActionListener extends EventListener {

    /**
     * Invoked when an action occurs.
     * <p>
     */
    public void actionPerformed(ActionEvent e);

}
