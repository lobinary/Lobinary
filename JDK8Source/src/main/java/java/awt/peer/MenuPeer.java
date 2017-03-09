/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 1998, Oracle and/or its affiliates. All rights reserved.
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
package java.awt.peer;

import java.awt.Menu;
import java.awt.MenuItem;

/**
 * The peer interface for menus. This is used by {@link Menu}.
 *
 * The peer interfaces are intended only for use in porting
 * the AWT. They are not intended for use by application
 * developers, and developers should not implement peers
 * nor invoke any of the peer methods directly on the peer
 * instances.
 * <p>
 *  菜单的对等接口。这是由{@link Menu}使用。
 * 
 *  对等接口仅用于移植AWT。它们不是供应用程序开发人员使用的,并且开发人员不应实现对等体,也不应直接在对等实例上调用任何对等方法。
 * 
 */
public interface MenuPeer extends MenuItemPeer {

    /**
     * Adds a separator (e.g. a horizontal line or similar) to the menu.
     *
     * <p>
     *  向菜单中添加分隔符(例如水平线或类似线)。
     * 
     * 
     * @see Menu#addSeparator()
     */
    void addSeparator();

    /**
     * Adds the specified menu item to the menu.
     *
     * <p>
     *  将指定的菜单项添加到菜单。
     * 
     * 
     * @param item the menu item to add
     *
     * @see Menu#add(MenuItem)
     */
    void addItem(MenuItem item);

    /**
     * Removes the menu item at the specified index.
     *
     * <p>
     *  删除指定索引处的菜单项。
     * 
     * @param index the index of the item to remove
     *
     * @see Menu#remove(int)
     */
    void delItem(int index);
}
