/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 1999, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing.event;


import java.util.EventListener;


/**
 * Defines a listener for menu events.
 *
 * <p>
 *  定义菜单事件的侦听器。
 * 
 * 
 * @author Georges Saab
 */
public interface MenuListener extends EventListener {
    /**
     * Invoked when a menu is selected.
     *
     * <p>
     *  选择菜单时调用。
     * 
     * 
     * @param e  a MenuEvent object
     */
    void menuSelected(MenuEvent e);
    /**
     * Invoked when the menu is deselected.
     *
     * <p>
     *  取消选择菜单时调用。
     * 
     * 
     * @param e  a MenuEvent object
     */
    void menuDeselected(MenuEvent e);
    /**
     * Invoked when the menu is canceled.
     *
     * <p>
     *  菜单取消时调用。
     * 
     * @param e  a MenuEvent object
     */
    void menuCanceled(MenuEvent e);
}
