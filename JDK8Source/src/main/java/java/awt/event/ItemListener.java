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
 * The listener interface for receiving item events.
 * The class that is interested in processing an item event
 * implements this interface. The object created with that
 * class is then registered with a component using the
 * component's <code>addItemListener</code> method. When an
 * item-selection event occurs, the listener object's
 * <code>itemStateChanged</code> method is invoked.
 *
 * <p>
 *  用于接收项目事件的侦听器接口。对处理项事件感兴趣的类实现此接口。然后使用该组件的<code> addItemListener </code>方法向该组件注册使用该类创建的对象。
 * 当项目选择事件发生时,监听器对象的<code> itemStateChanged </code>方法被调用。
 * 
 * 
 * @author Amy Fowler
 *
 * @see java.awt.ItemSelectable
 * @see ItemEvent
 * @see <a href="https://docs.oracle.com/javase/tutorial/uiswing/events/itemlistener.html">Tutorial: Writing an Item Listener</a>
 *
 * @since 1.1
 */
public interface ItemListener extends EventListener {

    /**
     * Invoked when an item has been selected or deselected by the user.
     * The code written for this method performs the operations
     * that need to occur when an item is selected (or deselected).
     * <p>
     */
    void itemStateChanged(ItemEvent e);

}
